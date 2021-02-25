/*
 * Created on 17-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import assesment.business.AssesmentAccess;
import assesment.business.administration.user.UsReportFacade;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.assesment.GroupData;
import assesment.communication.language.Text;
import assesment.communication.user.UserData;
import assesment.communication.util.MD5;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

public class UserAccessAction extends AbstractAction {

    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
        UserCreateForm createData = (UserCreateForm) form;
        switch(Integer.parseInt(createData.getAssesment())) {
        	case AssesmentData.ABITAB:
        		return mapping.findForward("reloadSession");
        	case AssesmentData.ANGLO: case AssesmentData.ANGLO_3:
        		return mapping.findForward("logoutAnglo");
        }
        return mapping.findForward("logout");
    }

    public ActionForward action(ActionMapping mapping, ActionForm createForm, HttpServletRequest request, HttpServletResponse response) throws Throwable {

        HttpSession session = request.getSession();
        AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
        Text messages = sys.getText();
        DynaActionForm createData = (DynaActionForm) createForm;
        String prefix = createData.getString("prefix");
        
        try {

            String code = createData.getString("code");
            if(code == null || code.trim().length() == 0) {
            	session.setAttribute("Msg", messages.getText("generic.messages.emptycpf"));
                return mapping.findForward("error_"+prefix);
            }

            String id = "";
            for(int i = 0; i < code.length(); i++) {
                char c = code.charAt(i); 
                if(Character.isLetter(c) || Character.isDigit(c)) {
            		id += String.valueOf(c);
                }
            }
        	UsReportFacade report = sys.getUserReportFacade();
        	UserSessionData userSessionData = sys.getUserSessionData();

        	if(prefix.equals("tmc")) {
        		String user = report.getCurrentTimacUser(id, userSessionData);
        		if(user != null) {
		            session.setAttribute("user",user);
		            session.setAttribute("password",new MD5().encriptar(user) );
		            return mapping.findForward("next");
	            }else {
	            	session.setAttribute("Msg", messages.getText("generic.messages.errorcpf"));
	                return mapping.findForward("error_"+prefix);
        		}
        	}else {
	            String user = prefix+"_"+id.toUpperCase();            
	            
	            if(user.length() > 6) {
	            	UserData userData = report.findUserByPrimaryKey(user, userSessionData);
	
	            	if(userData.getRole().equals(UserData.SYSTEMACCESS) || userData.getRole().equals(UserData.MULTIASSESSMENT) || userData.getRole().equals(UserData.GROUP_ASSESSMENT)) {
			            session.setAttribute("user",user);
			            session.setAttribute("password",new MD5().encriptar(id) );
			            return mapping.findForward("next");
	            	}
	            	
	            }else {
	            	session.setAttribute("Msg", messages.getText("generic.messages.errorcpf"));
	                return mapping.findForward("error_"+prefix);
	            }
        	}
        	
        }catch(Exception e) {
        	session.setAttribute("Msg", messages.getText("generic.messages.errorcpf"));
        	e.printStackTrace();
        }
        return mapping.findForward("error_"+prefix);
    }
}