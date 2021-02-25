/*
 * Created on 17-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.user;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import assesment.business.AssesmentAccess;
import assesment.communication.assesment.GroupData;
import assesment.communication.language.Text;
import assesment.communication.security.SecurityConstants;
import assesment.communication.user.UserData;
import assesment.communication.util.MailSender;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

public class UserGroupCreateAction extends AbstractAction {

    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        return mapping.findForward("back");
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
        String role = UserData.GROUP_ASSESSMENT;
        String passwd = createData.getPassword();
        String extradata = createData.getCompany();
        String extradata2 = createData.getExtraData2();

        int groupId = 0;
        try {
            groupId = Integer.parseInt(createData.getGroup());
        	UserData data = new UserData(nick,passwd,firstName,lastName,language,mail,role,null);
            data.setBirthDate(Calendar.getInstance().getTime());
            data.setSex(new Integer(2));
            if(groupId == GroupData.MERCADOLIBRE) {
            	extradata2 = nick;
            }
            if(groupId == GroupData.BAT) {
            	data.setCountry(new Integer(42));
            }else {
            	data.setCountry(new Integer(32));
            }
            data.setExtraData(extradata);
            data.setExtraData2(extradata2);
            sys.getUserABMFacade().userGroupCreate(data,new Integer(groupId),sys.getUserSessionData());
            
            session.setAttribute("user",nick);
            session.setAttribute("password",passwd);
            
            
            return mapping.findForward("next");
                
        }catch(Exception e) {
            Throwable thw = (e instanceof ServletException) ? ((ServletException)e).getRootCause() : e;
            while (thw.getCause() != null) {
                thw = thw.getCause();
            }
            if (thw.getClass().toString().indexOf("AlreadyExistsException") > 0) {
                if(groupId == GroupData.MERCADOLIBRE) {
                	session.setAttribute("Msg","CURP ya registrado");
                }else {
                	session.setAttribute("Msg",messages.getText("user.error.alreadyexist"));
                }
                createData.setPassword(null);
                createData.setRePassword(null);
                return mapping.findForward("back");
            }else {
                createData.setPassword(null);
                createData.setRePassword(null);
                createData.setLoginname(null);
                return mapping.findForward("back");
            }
        }
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