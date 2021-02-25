/*
 * Created on 17-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.user;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import assesment.business.AssesmentAccess;
import assesment.communication.language.Text;
import assesment.communication.security.SecurityConstants;
import assesment.communication.user.UserData;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

public class ConfirmationAction extends AbstractAction {

    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        return mapping.findForward("logout");
    }

    public ActionForward action(ActionMapping mapping, ActionForm createForm, HttpServletRequest request, HttpServletResponse response) throws Throwable {

        HttpSession session = request.getSession();
        AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
        Text messages = sys.getText();

        UserCreateForm createData = (UserCreateForm) createForm;
        Integer assesment = new Integer(createData.getAssesment());

        String validate = validate(messages,createData);
        if(validate != null) {
            session.setAttribute("Msg", validate);
            session.setAttribute("assesment", assesment);
            createData.setPassword(null);
            createData.setRePassword(null);
            return mapping.findForward("back");
        }

        String nick = createData.getLoginname();
        String firstName = createData.getFirstName();
        String lastName = createData.getLastName();
        String language = createData.getLanguage();
        String mail = createData.getEmail();
        String role = SecurityConstants.ACCESS_TO_SYSTEM;
        String passwd = createData.getPassword();
        try {

            UserData data = new UserData(nick,passwd,firstName,lastName,language,mail,role,null);
            data.setBirthDate(Util.getDate(createData.getBirthDay(),createData.getBirthMonth(),createData.getBirthYear()));
            data.setSex(new Integer(createData.getSex()));
            data.setCountry(new Integer(createData.getCountry()));
            data.setExtraData(createData.getCompany());

            sys.getUserABMFacade().userCreateFromAC(data,assesment,createData.getRole(),sys.getUserSessionData());
            
            session.setAttribute("user",nick);
            session.setAttribute("password",passwd);
            return mapping.findForward("next");
                
        }catch(Exception e) {
            Throwable thw = (e instanceof ServletException) ? ((ServletException)e).getRootCause() : e;
            while (thw.getCause() != null) {
                thw = thw.getCause();
            }
            if (thw.getClass().toString().indexOf("AlreadyExistsException") > 0) {
                session.setAttribute("Msg",messages.getText("user.error.alreadyexist"));
                session.setAttribute("assesment", assesment);
                createData.setPassword(null);
                createData.setRePassword(null);
                return mapping.findForward("back");
            }else {
                createData.setPassword(null);
                createData.setRePassword(null);
                createData.setLoginname(null);
                throw e;
            }
        }
    }

    
    private String validate(Text messages,ActionForm form) {
        UserCreateForm createData = (UserCreateForm) form;
        if (!createData.getCountry().equals(createData.getCountryConfirmation())) {
            return messages.getText("generic.userdata.invalidcountry");
        }
        if (!createData.getCompany().equals(createData.getCompanyConfirmation())) {
            return messages.getText("generic.userdata.invalidcompany");
        }
        return null;
   }
}