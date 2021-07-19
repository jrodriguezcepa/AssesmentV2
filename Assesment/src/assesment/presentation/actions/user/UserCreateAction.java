/*
 * Created on 17-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.user;

import java.util.Date;

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
import assesment.communication.util.MailSender;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

public class UserCreateAction extends AbstractAction {

    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        return mapping.findForward("list");
    }

    public ActionForward action(ActionMapping mapping, ActionForm createForm, HttpServletRequest request, HttpServletResponse response) throws Throwable {

        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));

        Text messages = sys.getText();

        UserCreateForm createData = (UserCreateForm) createForm;
        String nick = createData.getLoginname();
        String firstName = createData.getFirstName();
        String lastName = createData.getLastName();
        String language = createData.getLanguage();
        String mail = createData.getEmail();
        String role = createData.getRole();
        String passwd = createData.getPassword();
        String fdm = createData.getFdm();
        String extradata = createData.getExtraData();
        String extradata2 = createData.getExtraData2();
        
        sys.getUserABMFacade().saveTimacUser("31960260944", "CESAE AUGUSTO","GUERRA FOES", "rodriguez.jme@gmail.com", sys.getUserSessionData());
        /*Date expiry = null;
        if(Integer.parseInt(createData.getExpiryType()) == UserData.WITH_EXPIRY) {
            expiry = Util.getDate(createData.getUserExpiryDay(),createData.getUserExpiryMonth(),createData.getUserExpiryYear());
        }*/

        try {
/*
            if(createData.getType().equals("password")) {
                if(Util.empty(mail)) {
                    session.setAttribute("Msg","user.error.emptymail");
                    return mapping.findForward("back");
                }
                passwd = sys.getUserABMFacade().getNewPass();
            }else {
                String msg = validate(messages,createForm);
                if (msg != null) {
                    session.setAttribute("Msg", msg);
                    createData.setPassword(null);
                    createData.setRePassword(null);
                    return mapping.findForward("back");
                }
                String valid = null;//UserData.validatePassword(passwd);
                if(valid != null) {
                    session.setAttribute("Msg",valid);
                    createData.setPassword(null);
                    createData.setRePassword(null);
                    return mapping.findForward("back");
                }
            }

            UserData data = new UserData(nick,passwd,firstName,lastName,language,mail,role,expiry);
            data.setBirthDate(Util.getDate(createData.getBirthDay(),createData.getBirthMonth(),createData.getBirthYear()));
            data.setSex(new Integer(createData.getSex()));
            data.setCountry(new Integer(createData.getCountry()));
            data.setNationality(new Integer(createData.getCountry()));
            data.setStartDate(Util.getDate(createData.getStartDay(),createData.getStartMonth(),createData.getStartYear()));
            data.setLicenseExpiry(Util.getDate(createData.getExpiryDay(),createData.getExpiryMonth(),createData.getExpiryYear()));
            data.setVehicle(createData.getVehicle());
            if(Util.isNumber(createData.getLocation()))
            	data.setLocation(new Integer(createData.getLocation()));
            if(!Util.empty(fdm) && Util.isNumber(fdm)) {
            	data.setDatacenter(new Integer(fdm));
            }
            data.setExtraData(extradata);
            data.setExtraData2(extradata2);
            if(role.equals(SecurityConstants.ACCESS_TO_SYSTEM) || role.equals(SecurityConstants.CLIENT_REPORTER)) {
                sys.getUserABMFacade().userCreate(data,new Integer(createData.getAssesment()),sys.getUserSessionData());
            } else if(role.equals(SecurityConstants.GROUP_ASSESSMENT) || role.equals(SecurityConstants.CLIENTGROUP_REPORTER)) {
                sys.getUserABMFacade().userGroupCreate(data,new Integer(createData.getGroup()),sys.getUserSessionData());
            } else if(role.equals(SecurityConstants.MULTI_ASSESSMENT)) {
                sys.getUserABMFacade().userCreate(data,createData.getAssesments(),sys.getUserSessionData());
            }else {
            	Integer a = null;
                sys.getUserABMFacade().userCreate(data,a,sys.getUserSessionData());
            }
            
            if(createData.getType().equals("password")) {
                MailSender sender = new MailSender();
                String password = getMessage1(language,nick)+passwd;
                String title = getTitle1(language);
                String[] senderAddress = sender.getAvailableMailAddress();
                if(!Util.empty(senderAddress[0])) {
                	sender.send(mail,"CEPA DATA CENTER",senderAddress[0],senderAddress[1],title,password);
                }
            }*/
            
            return mapping.findForward("list");
                
        }catch(Exception e) {
            Throwable thw = (e instanceof ServletException) ? ((ServletException)e).getRootCause() : e;
            while (thw.getCause() != null) {
                thw = thw.getCause();
            }
            if (thw.getClass().toString().indexOf("AlreadyExistsException") > 0) {
                session.setAttribute("Msg",messages.getText("user.error.alreadyexist"));
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
        if (Util.empty(createData.getLoginname())) {
            return messages.getText("generic.user.userdata.loginname.isinvalid");
        }
        if (!Util.empty(createData.getPassword()) && !Util.empty(createData.getRePassword())) {
            if (createData.getPassword().compareTo(createData.getRePassword()) != 0) {
                return messages.getText("generic user.userdata.passconfirm");
            }
        } else {
            return messages.getText("generic.user.userdata.pass.isempty");
        }
        try{
            if(!Util.checkDate(Integer.parseInt(createData.getBirthDay()),Integer.parseInt(createData.getBirthMonth()),Integer.parseInt(createData.getBirthYear()))) {
                return messages.getText("generic.user.invalidbirthdate");
            }
        }catch(Exception e) {
            return messages.getText("generic.user.invalidbirthdate");
        }
        if (Util.empty(createData.getFirstName())) {
            return messages.getText("generic.user.userdata.firstname.isempty");
        }
        if (Util.empty(createData.getLastName())) {
            return messages.getText("generic.user.userdata.lastname.isempty");
        }
        if (Util.empty(createData.getLanguage())) {
            return messages.getText("generic.user.userdata.language.isempty");
        }
        if(Integer.parseInt(createData.getExpiryType()) == UserData.WITH_EXPIRY) {
            try{
                if(!Util.checkDate(Integer.parseInt(createData.getUserExpiryDay()),Integer.parseInt(createData.getUserExpiryMonth()),Integer.parseInt(createData.getUserExpiryYear()))) {
                    return messages.getText("generic.user.invalidexpiry");
                }
            }catch(Exception e) {
                return messages.getText("generic.user.invalidexpiry");
            }
        }
        String fdm = createData.getFdm();
        if(!Util.empty(fdm)) {
        	if (!Util.isNumber(fdm)) {
        		return messages.getText("userdata.error.invalidfdmid");
        	}
        }
        return null;
    }

    private String getTitle1(String language) {
        if(language.equals("es")) {
            return "Nuevo usuario";
        }else if(language.equals("pt")) {
            return "Usuario novo";
        }else {
            return "New User";
        }
    }
    
    private String getMessage1(String language, String loginname) {
        String message = "";
        if(language.equals("es")) {
            message += "<b>Por su pedido, su usuario ha sido creado.</b><br>";
            message += "<b>url:</b> <a href=\"http://da.cepasafedrive.com/assesment/>da.cepasafedrive.com/assesment</a><br>";
            message += "<b>Login:</b> "+loginname;
            message += "<br>";
            message += "<b>Password: </b>";
        }else if(language.equals("pt")) {
            message += "<b>Segundo Solicitação, su usuario foi criado.</b><br>";
            message += "<b>url:</b> <a href=\"http://da.cepasafedrive.com/assesment/>da.cepasafedrive.com/assesment</a><br>";
            message += "<b>Login:</b> "+loginname;
            message += "<br>";
            message += "<b>Password: </b>";        
        }else {
            message += "<b>For your asking your user has been created.</b><br>";
            message += "<b>url:</b> <a href=\"http://da.cepasafedrive.com/assesment/>da.cepasafedrive.com/assesment</a><br>";
            message += "<b>Login:</b> "+loginname;
            message += "<br>";
            message += "<b>Password: </b>";
        }
        return Util.emailTranslation(message);
    }
}