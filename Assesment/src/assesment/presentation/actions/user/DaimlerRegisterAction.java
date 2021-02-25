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
import assesment.business.administration.user.UsABMFacade;
import assesment.business.administration.user.UsReportFacade;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.assesment.GroupData;
import assesment.communication.language.Text;
import assesment.communication.user.UserData;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

public class DaimlerRegisterAction extends AbstractAction {

    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        return mapping.findForward("logout");
    }

    public ActionForward action(ActionMapping mapping, ActionForm createForm, HttpServletRequest request, HttpServletResponse response) throws Throwable {

        HttpSession session = request.getSession();
        AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
        Text messages = sys.getText();
        
        try {

            DynaActionForm createData = (DynaActionForm) createForm;
			String firstName = createData.getString("firstName");
			String lastName = createData.getString("lastName");
			String email = createData.getString("email");
			String extra = createData.getString("extra");
			
			if(Util.empty(firstName)) {
            	session.setAttribute("Msg", "Ingrese Nombre");
                return mapping.findForward("error");
			}
			
			if(Util.empty(lastName)) {
            	session.setAttribute("Msg", "Ingrese Apellido");
                return mapping.findForward("error");
			}
			
			if(!Util.checkEmail(email)) {
            	session.setAttribute("Msg", "Email incorrecto");
                return mapping.findForward("error");
			}
			
			String user = createData.getString("user");
			String password = createData.getString("password");

			if(Util.empty(user) || !password.toLowerCase().equals("sprinter25")) {
            	session.setAttribute("Msg", "Usuario o contraseña incorrectos");
                return mapping.findForward("error");
			}
			
			UsReportFacade usReport = sys.getUserReportFacade();
			UserData userData = usReport.findUserByPrimaryKey(user, sys.getUserSessionData());
            if(userData == null) {
            	session.setAttribute("Msg", "Usuario o contraseña incorrectos");
                return mapping.findForward("error");
            }
            
            GroupData group = usReport.findUserGroup(user, sys.getUserSessionData());
            if(group == null || group.getId().intValue() != GroupData.DAIMLER) {
            	session.setAttribute("Msg", "Usuario o contraseña incorrectos");
	            return mapping.findForward("error");
	        }

            if(!userData.getFirstName().equals("Usuario") || !userData.getLastName().equals("Daimler") || !userData.getEmail().equals("cepasafedrive@gmail.com")) {
            	session.setAttribute("Msg", "Usuario ya registrado");
                return mapping.findForward("error");
            }
            
            userData.setFirstName(firstName);
            userData.setLastName(lastName);
            userData.setEmail(email);
            userData.setExtraData(extra);
            
            UsABMFacade usABM = sys.getUserABMFacade();
            UserSessionData userSessionData = sys.getUserSessionData();
            
            usABM.userUpdate(userData, userSessionData);
            usABM.resetPassword(userData, "Sprinter25", userSessionData);
            
            session.setAttribute("user",user);
		    session.setAttribute("password","Sprinter25");
		    return mapping.findForward("next");
            
        }catch(Exception e) {
        	session.setAttribute("Msg", "Usuario o contraseña incorrectos");
        	e.printStackTrace();
        }
        return mapping.findForward("error");
    }
}