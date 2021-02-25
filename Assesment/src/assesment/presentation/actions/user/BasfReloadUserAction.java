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
import org.apache.struts.action.DynaActionForm;

import assesment.business.AssesmentAccess;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.corporation.CorporationData;
import assesment.communication.language.Text;
import assesment.communication.user.UserData;
import assesment.communication.util.MailSender;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

public class BasfReloadUserAction extends AbstractAction {

    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        return mapping.findForward("list");
    }

    public ActionForward action(ActionMapping mapping, ActionForm createForm, HttpServletRequest request, HttpServletResponse response) throws Throwable {
    	try {
    		
	        HttpSession session = request.getSession();
	        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
	        UserSessionData userSessionData = sys.getUserSessionData();
	
	        String login = ((DynaActionForm)createForm).getString("user");
	        Integer[] assessments = sys.getAssesmentReportFacade().getAvailableAssessments(login, CorporationData.BASF, userSessionData);
	        
	        Integer assessment = null;
	        if(assessments != null && assessments.length > 0) {
		        int x = (int)Math.round(Math.random()*(assessments.length-1));
		        assessment = assessments[x];
		        
		        sys.getUserABMFacade().basfUserAssessment(login,assessment,userSessionData);
		        
	        }else {
	        	assessments = sys.getAssesmentReportFacade().getAvailableAssessments(null, CorporationData.BASF, userSessionData);
		        int x = (int)Math.round(Math.random()*(assessments.length-1));
		        assessment = assessments[x];
		        
		        login = sys.getUserABMFacade().createBasfUser(assessment, login, userSessionData);
	        }

	        session.setAttribute("user",login);
            session.setAttribute("password",login);
            session.setAttribute("assessment",assessment);
        }catch(Exception e) {
        	e.printStackTrace();
        }
        return mapping.findForward("next");
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