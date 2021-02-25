/*
 * Created on 17-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.user;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import assesment.business.AssesmentAccess;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.language.Text;
import assesment.communication.util.MD5;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

public class UserMonsantoEMEAAction extends AbstractAction {

    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        return mapping.findForward("logout");
    }

    public ActionForward action(ActionMapping mapping, ActionForm createForm, HttpServletRequest request, HttpServletResponse response) throws Throwable {

        HttpSession session = request.getSession();
        AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
        Text messages = sys.getText();

        try {

        	MD5 md5 = new MD5();
        	Collection accessCodes = sys.getUserReportFacade().getAccessCode(AssesmentData.MONSANTO_EMEA,sys.getUserSessionData());
        	boolean found = false;
        	Iterator it = accessCodes.iterator();
        	String accessCode = null;
        	while(!found && it.hasNext()) {
        		accessCode = (String)it.next();
        		if(accessCode.equals(((UserCreateForm)createForm).getLoginname())) {
        			found = true;
        		}
        	}
        	
        	if(found) {
	        	String language = ((UserCreateForm)createForm).getLanguage();
	        	if(Util.empty(language)) {
	        		session.setAttribute("Msg",messages.getText("user.monsantoemea.selectlanguage"));
	                return mapping.findForward("back");        		
	        	}
	            String[] data = sys.getUserABMFacade().createMonsantoEMEAUser(accessCode,language,sys.getUserSessionData());
	            session.setAttribute("user", data[0]);
	            session.setAttribute("password", data[1]);
	            return mapping.findForward("next");
        	}
                
        }catch(Exception e) {
        	e.printStackTrace();
        }
        return null;
    }
}