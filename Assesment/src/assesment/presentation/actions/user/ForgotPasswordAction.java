/*
 * Created on 17-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.user;

import java.util.Collection;

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
import assesment.communication.user.UserData;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

public class ForgotPasswordAction extends AbstractAction {

    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        return mapping.findForward("logout");
    }

    public ActionForward action(ActionMapping mapping, ActionForm createForm, HttpServletRequest request, HttpServletResponse response) throws Throwable {

        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        UserSessionData userSessionData = sys.getUserSessionData();
        UsReportFacade usReport = sys.getUserReportFacade();
        
        DynaActionForm createData = (DynaActionForm) createForm;
        String user = createData.getString("user").trim();
        String email = createData.getString("email").trim();
        if(!Util.empty(user)) {
        	UserData userData = usReport.findUserByPrimaryKey(user, userSessionData);
        	if(userData == null) {
                session.setAttribute("Msg","user.forgotpassword.notfounded");
                return mapping.findForward("back");
        	}else {
        		if(Util.empty(email)) {
        			if(userData.getEmail() != null && Util.checkEmail(userData.getEmail())) {
        				Util.sendPassword(userData, sys);
        				return mapping.findForward("next");
        			} else {
                        session.setAttribute("Msg","user.forgotpassword.wrongemailuser");
                        return mapping.findForward("back");
        			}
        		}else {
        			if(userData.getEmail() == null || !Util.checkEmail(userData.getEmail())) {
                        session.setAttribute("Msg","user.forgotpassword.wrongemailuser");
                        return mapping.findForward("back");
        			}else if(!email.equals(userData.getEmail())) {
                        session.setAttribute("Msg","user.forgotpassword.differentemails");
                        return mapping.findForward("back");
        			}else {
        				Util.sendPassword(userData, sys);
        				return mapping.findForward("next");
        			}
        		}
        	}
        } else if(!Util.empty(email) && Util.checkEmail(email)) {
        	Collection list = usReport.findForgotUserUserByEmail(email, userSessionData);
        	switch(list.size()) {
	        	case 0:
	                session.setAttribute("Msg","user.forgotpassword.notfounded");
	                return mapping.findForward("back");
	        	case 1:
	        		UserData userData = new UserData();
	        		userData.setLoginName((String)list.iterator().next());
	        		userData.setEmail(email);
    				Util.sendPassword(userData, sys);
    				return mapping.findForward("next");
	        	default:
	        		session.setAttribute("list", list);
    				return mapping.findForward("multiple");
        	}
        } else {
            session.setAttribute("Msg","user.forgotpassword.emptydata");
            return mapping.findForward("back");
        }
    }

}

