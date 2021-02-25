/*
 * Created on 05-oct-2005
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
import assesment.business.administration.user.UsABMFacade;
import assesment.communication.user.UserData;
import assesment.presentation.actions.user.UserCreateForm;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

/**
 * @author jrodriguez
 */
public class UserResetOwnPasswordAction extends AbstractAction {
	
    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        return mapping.findForward("home");
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));

        UserCreateForm updateData = (UserCreateForm) form;
        String msg = validate(updateData);
        
        if (msg != null) {
            session.setAttribute("Msg", msg);
            return mapping.findForward("message");
        }else {
            String passwd = updateData.getPassword();
            String valid = UserData.validatePassword(updateData.getPassword());
            if(valid != null) {
                session.setAttribute("Msg", valid);
                return mapping.findForward("message");
            }
            UsABMFacade userABM = null;

            userABM = sys.getUserABMFacade();
            userABM.userResetOwnPassword(passwd,sys.getUserSessionData());
        }
        return mapping.findForward("home");
    }

    private String validate(UserCreateForm createData) {
        
        if(Util.empty(createData.getPassword())){
            return "generic.user.userdata.pass.isempty";
        }else{
            if(Util.empty(createData.getRePassword())){
                return "generic.user.userdata.confirmpass.isempty";
            }else{
                if(createData.getPassword().compareTo(createData.getRePassword())!=0){
                    return "generic.user.userdata.pass.confir.different";
                }
            }
        }
        return null;
    }
}
