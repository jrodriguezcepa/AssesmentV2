/*
 * Created on 17-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.translator.web.administration.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import assesment.business.AssesmentAccess;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.language.Text;
import assesment.communication.user.UserData;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

/**
 * @author jrodriguez
 */
public class ResetPasswordAction extends AbstractAction {

    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        return mapping.findForward("cancel");
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        Text messages = sys.getText();
        UserSessionData userSessionData = sys.getUserSessionData();
        
        ResetPasswordForm resetForm = (ResetPasswordForm)form;
        int type = Integer.parseInt(resetForm.getType());
    	UserData userData = sys.getUserReportFacade().findUserByPrimaryKey(resetForm.getUser(), userSessionData);
        switch(type) {
	        case 1:
	        	String password = resetForm.getPassword();
	            if (!Util.empty(password) && !Util.empty(resetForm.getConfirmation())) {
	                if (password.compareTo(resetForm.getConfirmation()) != 0) {
	                	session.setAttribute("Msg", messages.getText("generic user.userdata.passconfirm"));
	                } else {
	                	userData.setPassword(password);
	                	sys.getUserABMFacade().resetPassword(userData, new Integer(resetForm.getRecovery()), userSessionData);
	    	        	return mapping.findForward("success");
	                }
	            } else {
	            	session.setAttribute("Msg", messages.getText("generic.user.userdata.pass.isempty"));
	            }
	            resetForm.setPassword(null);
	            resetForm.setConfirmation(null);
	            session.setAttribute("key", resetForm.getKey());
                return mapping.findForward("back");
	        case 2:
	        	Util.sendPassword(userData, sys);
	        	return mapping.findForward("resend");
        }
        return null;
    }
}
