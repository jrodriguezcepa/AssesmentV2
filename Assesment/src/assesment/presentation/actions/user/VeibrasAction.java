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
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

public class VeibrasAction extends AbstractAction {

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
        
        try {

            DynaActionForm createData = (DynaActionForm) createForm;
            String code = createData.getString("code");
            if(code == null || code.trim().length() == 0) {
            	session.setAttribute("Msg", messages.getText("generic.messages.emptycpf"));
                return mapping.findForward("error");
            }

            String email = createData.getString("email");

            String user = "";
            for(int i = 0; i < code.length(); i++) {
            	String s = String.valueOf(code.charAt(i));
            	if(Util.isNumber(s)) 
            		user += s;
            }
            
            if(user.length() > 6) {
            	UsReportFacade report = sys.getUserReportFacade();
            	UserSessionData userSessionData = sys.getUserSessionData();
	            GroupData g = report.findUserGroup(user, userSessionData);
	            
	            if(g.getId().intValue() == GroupData.VEIBRAS) {
	            	
		            if(Util.checkEmail(email)) {
		            	UserData userData = report.findUserByPrimaryKey(user, userSessionData);
		            	userData.setEmail(email.toLowerCase());
		            	sys.getUserABMFacade().userUpdate(userData, userSessionData);
		            }
		            session.setAttribute("user",user);
		            session.setAttribute("password","vb"+user);
		            return mapping.findForward("next");
	            }else {
	            	session.setAttribute("Msg", messages.getText("generic.messages.errorcpf"));
	                return mapping.findForward("error");
	            }
            }else {
            	session.setAttribute("Msg", messages.getText("generic.messages.errorcpf"));
                return mapping.findForward("error");
            }
            
        }catch(Exception e) {
        	session.setAttribute("Msg", messages.getText("generic.messages.errorcpf"));
        	e.printStackTrace();
        }
        return mapping.findForward("error");
    }
}