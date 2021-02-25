/*
 * Created on 22-ago-2005
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
import assesment.communication.language.Text;
import assesment.communication.user.UserData;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

/**
 * @author fcarriquiry
 */
public class UserAssesmentFDMAction extends AbstractAction {

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        Text messages = sys.getText();

        DynaActionForm formData = (DynaActionForm) form;
        String user = formData.getString("user");
        String fdm = formData.getString("fdm");

		if (!Util.isNumber(fdm)) {
			session.setAttribute("Msg", messages.getText("userdata.error.invalidfdmid"));
			session.setAttribute("user",user);
			return mapping.findForward("back");
		}else {

            UsReportFacade userReport=sys.getUserReportFacade();
			UserData data=userReport.findUserByPrimaryKey(user,sys.getUserSessionData());
			data.setDatacenter(new Integer(fdm));
            
            sys.getUserABMFacade().userUpdate(data,false,sys.getUserSessionData());

            session.setAttribute("user",user);
			return mapping.findForward("view");
        }
	}


    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        session.setAttribute("user",((DynaActionForm) form).getStrings("user"));
        return mapping.findForward("view");
    }

}