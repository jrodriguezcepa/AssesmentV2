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
import assesment.communication.language.Text;
import assesment.communication.user.UserData;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

public class UserUpdateAccessCodeAction extends AbstractAction {

    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        return mapping.findForward("home");
    }

    public ActionForward action(ActionMapping mapping, ActionForm createForm, HttpServletRequest request, HttpServletResponse response) throws Throwable {

        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        Text messages = sys.getText();

        DynaActionForm createData = (DynaActionForm) createForm;

        int used = Integer.parseInt(createData.getString("used"));

        String number = createData.getString("number");
        if(Util.empty(number)) {
            session.setAttribute("Msg", messages.getText("user.accesscode.emptynumber"));
            return mapping.findForward("back");
        }
        if(!Util.isNumber(number)) {
            session.setAttribute("Msg", messages.getText("user.accesscode.wrongnumber"));
            return mapping.findForward("back");
        }
        if(Integer.parseInt(number) < used) {
            session.setAttribute("Msg", messages.getText("user.accesscode.numberlessused"));
            return mapping.findForward("back");
        }
        int type = Integer.parseInt(createData.getString("type"));
        String extension = "0";
        if(type == UserData.WITH_EXPIRY) {
            extension = createData.getString("extension");
            if(Util.empty(extension)) {
                session.setAttribute("Msg", messages.getText("user.accesscode.emptyextension"));
                return mapping.findForward("back");
            }
        }
        
        String accesscode = createData.getString("accesscode").trim();

        sys.getAssesmentABMFacade().updateAccessCodeNumber(accesscode,new Integer(number),new Integer(extension),createData.getString("language"),sys.getUserSessionData());
        return mapping.findForward("success");
    }
}

