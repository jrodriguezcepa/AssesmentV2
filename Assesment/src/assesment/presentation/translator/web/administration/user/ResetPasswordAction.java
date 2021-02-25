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
import assesment.communication.user.UserData;
import assesment.communication.util.MailSender;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

/**
 * @author fcarriquiry
 */
public class ResetPasswordAction extends AbstractAction {

    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        return mapping.findForward("cancel");
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));

        ResetPasswordForm resetForm = (ResetPasswordForm)form;
        String email = resetForm.getEmail();
        String language = resetForm.getLanguage();
        if(Util.empty(email) || email.equals("resetpassword")) {
            return mapping.findForward("back");
        }
        UserData userData = sys.getUserReportFacade().findUserByEmail(email,sys.getUserSessionData());
        MailSender sender = new MailSender();
        String forward = "nouser";
        if(userData != null) {
            String password = getMessage1(language,userData.getLoginName())+sys.getUserABMFacade().resetPassword(userData,sys.getUserSessionData());
            password += getMessage3(language);
            String title = getTitle1(language) ;
            String[] senderAddress = sender.getAvailableMailAddress();
            if(!Util.empty(senderAddress[0])) {
            	sender.send(email,"CEPA DATA CENTER",senderAddress[0],senderAddress[1],title,password);
            }
            forward = "reset";
        }
        return mapping.findForward(forward);
    }
    
    private String getTitle1(String language) {
        if(language.equals("es")) {
            return "Contraseña actualizada";
        }else if(language.equals("pt")) {
            return "Senha atualizada";
        }else {
            return "Reset Password";
        }
    }
    
    private String getMessage1(String language, String loginname) {
        String message = "";
        if(language.equals("es")) {
            message += "<b>Su password ha sido reseteada.</b><br>";
            message += "<b>url:</b> <a href=\"http://da.cepasafedrive.com/assesment/>da.cepasafedrive.com/assesment</a><br>";
            message += "<b>Login:</b> "+loginname;
            message += "<br>";
            message += "<b>Password: </b>";
        }else if(language.equals("pt")) {
            message += "<b>Sua password foi resseteada.</b><br>";
            message += "<b>url:</b> <a href=\"http://da.cepasafedrive.com/assesment/>da.cepasafedrive.com/assesment</a><br>";
            message += "<b>Login:</b> "+loginname;
            message += "<br>";
            message += "<b>Password: </b>";        
        }else {
            message += "<b>Your password has been reset.</b><br>";
            message += "<b>url:</b> <a href=\"http://da.cepasafedrive.com/assesment/>da.cepasafedrive.com/assesment</a><br>";
            message += "<b>Login:</b> "+loginname;
            message += "<br>";
            message += "<b>Password: </b>";
        }
        return Util.emailTranslation(message);
    }

    private String getTitle2(String language) {
        if(language.equals("es")) {
            return "Nuevo Usuario Data Center";
        }else if(language.equals("pt")) {
            return "New Data Center User ";
        }else {
            return "Novo Usuario Data Center";
        }
    }
    
    private String getMessage2(String language, String loginname) {
        String message = "";
        if(language.equals("es")) {
            message += Util.emailTranslation("<b>Un usuario ha sido creado con sus datos.</b><br>");
            message += "<b>url:</b> <a href=\"http://da.cepasafedrive.com/assesment/>da.cepasafedrive.com/assesment</a><br>";
            message += "<b>Login: </b>"+loginname+"<br> "+"<b>Password: </b>";
        }else if(language.equals("pt")) {
            message += Util.emailTranslation("<b>Usuário foi criado com seus dados.</b><br>");
            message += "<b>url:</b> <a href=\"http://da.cepasafedrive.com/assesment/>da.cepasafedrive.com/assesment</a><br>";
            message += "<b>Login:</b> "+loginname+"<br> "+"<b>Password: </b>";
        }else {
            message += Util.emailTranslation("<b>A user has been created with your data.</b><br>");
            message += "<b>url:</b> <a href=\"http://da.cepasafedrive.com/assesment/>da.cepasafedrive.com/assesment</a><br>";
            message += "<b>Login: </b>"+loginname+"<br> "+"<b>Password: </b>";
        }
        return message;
    }

    private String getMessage3(String language) {
        String message = "";
        if(language.equals("es")) {
            message += "<br><br>Recuerde que al ingresar al sistema la primera vez, &eacute;ste le solicitar&aacute; modificar su password, por razones de seguridad, de acuerdo con las pol&iacute;ticas de seguridad en el tratamiento de los datos de car&aacute;cter personal de Johnson & Johnson." +
                    "<br><br>Siguiendo con las recomendaciones resultantes del J&J Risk Assessment, las pol&iacute;ticas de password del Data Center fueron alineadas con J&J&#8217;s Worldwide Policies on Information Asset Protection (WWIAPs)." +
                    "<br><br>Estas pol&iacute;ticas exigen que su password: " +
                    "<br><br><b>- </b>Contenga 3 niveles de complejidad (Letras may&uacute;sculas, min&uacute;sculas, numerales y/o caracteres no alfanum&eacute;ricos ((@#$%^&+=))" +
                    "<br><b>- </b>Contenga al menos 6 caracteres " +
                    "<br><b>- </b>Expire cada 90 d&iacute;as m&aacute;ximo " +
                    "<br><b>- </b>Expire luego del primer logon (password inicial)" +
                    "<br><br><br>Saludos";
        }else if(language.equals("pt")) {
            message += "<br><br>Lembre-se que ao ingressar ao sistema pela primeira vez, o mesmo lhe solicitar&aacute; modificar sua password, por raz&otilde;es de seguran&ccedil;a, de acordo com as pol&iacute;ticas de seguran&ccedil;a no tratamento dos dados de car&aacute;ter pessoal de Johnson & Johnson." +
                    "<br><br>Atendendo as recomenda&ccedil;&otilde;es resultantes de J&J Risk Assessment, as pol&iacute;ticas de passwords do Data Center foram alinhadas com J&J&#8217;s Worldwide Policies on Information Asset Protection (WWIAPs)." +
                    "<br><br>Estas pol&iacute;ticas exigem que sua password:" +
                    "<br><br><b>- </b>Contenha 3 n&iacute;veis de complexidade (letras mai&uacute;sculas, min&uacute;sculas, n&uacute;meros e/ou caracteres n&atilde;o alfas-num&eacute;ricos ((@#$%^&+=))" +
                    "<br><b>- </b>Contenha ao menos 6 caracteres" +
                    "<br><b>- </b>Expire no m&aacute;ximo a cada 90 dias" +
                    "<br><b>- </b>Expire no primeiro login (password inicial)" +
                    "<br><br><br>Atenciosamente,";
        }else {
            message += "<br><br>Please, remember that after entering, the system will ask you to change the password that was assigned to you." +
                    "<br><br>Following recommendations resulting from a J&J Risk Assessment, Data Center password policies have been aligned with J&J&#8217;s Worldwide Policies on Information Asset Protection (WWIAPs)." +
                    "<br><br>This policies state that passwords should:" +
                    "<br><br><b>- </b>have 3 levels of complexity (English Upper Case Letters, English Lower Case Letters, Westernized Arabic Numerals, and/or Non-alphanumeric (@#$%^&+=))" +
                    "<br><b>- </b>have a minimum of 6 characters" +
                    "<br><b>- </b>expire every 90 days at a minimum" +
                    "<br><b>- </b>expire upon first logon (Initial passwords)" +
                    "<br><br><br>Best Regards.";
        }
        return message;
    }
}
