/*
 * Created on 05-oct-2005
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

import assesment.business.AssesmentAccess;
import assesment.communication.language.Text;
import assesment.communication.user.UserData;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

/**
 * @author jrodriguez
 */
public class UserResetPasswordAction extends AbstractAction {
	
    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        Text messages = sys.getText();
			
        UserCreateForm updateData = (UserCreateForm) form;
		String user = updateData.getLoginname();
			
		String msg = validate(messages,form);
		if(msg != null) {
			session.setAttribute("Msg", msg);
            session.setAttribute("user",user);
			return mapping.findForward("back");
		}else {
			UserData data = new UserData(user,updateData.getPassword(),null,null,null,null,null,null);
            sys.getUserABMFacade().userResetPassword(data,sys.getUserSessionData());
            session.setAttribute("user",user);
            return mapping.findForward("view");
		}
	}
	
	private String validate(Text messages,ActionForm form) {
        UserCreateForm updateData = (UserCreateForm) form;
        if (!Util.empty(updateData.getPassword()) && !Util.empty(updateData.getRePassword())) {
            if (updateData.getPassword().compareTo(updateData.getRePassword()) != 0) {
                return messages.getText("generic user.userdata.passconfirm");
            }
        } else {
            return messages.getText("generic.user.userdata.pass.isempty");
        }
        return null;
	}


    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        session.setAttribute("user",((UserCreateForm) form).getLoginname());
        return mapping.findForward("view");
    }
}
