/*
 * Created on 17-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.user;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.sun.org.apache.xml.internal.serializer.utils.StringToIntTable;

import assesment.business.AssesmentAccess;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.corporation.CediAttributes;
import assesment.communication.language.Text;
import assesment.communication.security.SecurityConstants;
import assesment.communication.user.UserData;
import assesment.communication.util.MailSender;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

public class CediRegisterAction extends AbstractAction {

    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        session.removeAttribute("cedi");
        return mapping.findForward("cancel");
    }

    public ActionForward action(ActionMapping mapping, ActionForm createForm, HttpServletRequest request, HttpServletResponse response) throws Throwable {

        HttpSession session = request.getSession();
        AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
        
        DynaActionForm createData = (DynaActionForm) createForm;
        if(!Util.isNumber(createData.getString("cedi"))) {
	        session.setAttribute("Msg", "CEDI INCORRECTO");
            return mapping.findForward("error");
        }
        Integer cedi = new Integer(createData.getString("cedi"));
        
    	String managerMail="";
        if(cedi!=null){
    		CediAttributes cediAttributes = sys.getCorporationReportFacade().findCediAttributes(cedi,sys.getUserSessionData());
    		managerMail = cediAttributes.getManagerMail();
    	}
        try {

            String nick = createData.getString("loginname").trim();
            
            String firstName = createData.getString("firstName");
            String lastName = createData.getString("lastName");
            String idNumber = createData.getString("idnumber");
            String mail = createData.getString("email");
            int type = Integer.parseInt(createData.getString("type"));
           // int ebtw = Integer.parseInt(createData.getString("ebtw"));
            int ebtw=0;
            String passwd = createData.getString("password");
            if(idNumber.length()!=0 && sys.getUserReportFacade().userExistsIdNumber(idNumber, sys.getUserSessionData())) {
		        session.setAttribute("Msg", "Ya existe usuario registrado con número de identificación");
                return mapping.findForward("error");	
            }
    		String msg = validate(sys.getText(),nick, passwd, createData.getString("rePassword"), firstName, lastName, mail); 
		    if (msg != null) {
		        session.setAttribute("Msg", msg);
                session.setAttribute("cedi", cedi);
                return mapping.findForward("error");
		    }


            
		    UserData data = new UserData(nick,passwd,firstName,lastName,"es",mail,SecurityConstants.GROUP_ASSESSMENT,null);
            Calendar c = Calendar.getInstance();
            c.add(Calendar.YEAR, -30);
        	data.setBirthDate(c.getTime());
        	data.setExtraData(String.valueOf(type));
        	data.setLocation(cedi);
        	data.setExtraData3(idNumber);
        	sys.getUserABMFacade().userCediCreate(data, type, ebtw, sys.getUserSessionData());
        	
            session.setAttribute("cedi", cedi);
            session.setAttribute("usuario", nick);
            session.setAttribute("password", passwd);
        	
            MailSender sender = new MailSender();
        	String mailBody = getBody(nick, passwd, type);
        	String title="Nuevo Usuario";
        	LinkedList to =new LinkedList();
            if(!Util.empty(mail)) {
            	to.add(mail);
            }
            if(!Util.empty(managerMail)) {
            	StringTokenizer token = new StringTokenizer(managerMail, ";");
            	while(token.hasMoreTokens()) {
            		to.add(token.nextToken().toLowerCase());
            	}
            }            
            sender.sendImageMandrill(to, "CEPA - Driver Assessment", "support@cepasafedrive.com", title, mailBody, new LinkedList(), new LinkedList(), "support@cepasafedrive.com",  "CEPA - Driver Assessment", new LinkedList(), null);
            
            
            return mapping.findForward("next");
            
        }catch(Exception e) {
            Throwable thw = (e instanceof ServletException) ? ((ServletException)e).getRootCause() : e;
            while (thw.getCause() != null) {
                thw = thw.getCause();
            }
            if (thw.getClass().toString().indexOf("AlreadyExistsException") > 0) {
                session.setAttribute("Msg", "Usuario ya existe");
                session.setAttribute("cedi", new Integer(cedi));
                return mapping.findForward("error");
            }else {
                throw e;
            }
        }
     }

    
    private String validate(Text messages, String nick, String passwd, String repasswd, String firstName, String lastName, String email) {
        if (Util.empty(nick)) {
            return messages.getText("generic.user.userdata.loginname.isinvalid");
        }
        if (nick.contains(" ")) {
            return "El usuario no puede contener espacios en blanco";
        }
        if (!Util.empty(passwd) && !Util.empty(repasswd)) {
            if (passwd.compareTo(repasswd) != 0) {
                return messages.getText("generic user.userdata.passconfirm");
            }
        } else {
            return messages.getText("generic.user.userdata.pass.isempty");
        }
        if (Util.empty(firstName)) {
            return messages.getText("generic.user.userdata.firstname.isempty");
        }
        if (Util.empty(lastName)) {
            return messages.getText("generic.user.userdata.lastname.isempty");
        }
        if (!Util.empty(email) && !Util.checkEmail(email)) {
            return "Email incorrecto";
        }
        return null;
    }

    private  String getBody(String loginname, String password, int type) {
    	String body;
       body = "	<html>"
       		+ "	<body>";
       body += "<div style='max-width:800px;margin-left:20px;font-family:arial;font-size:.92em;'>";
       body += "<p style=\"text-align:center;\"><b>"+Util.emailTranslation("INVITACIÓN a participar \"Grupo Modelo Driver Assessment Online\"")+"</b></p><div>";
       body += "<div style='max-width:800px;margin-left:20px;font-family:arial;font-size:.89em;'>";
       body += "<p style=\"text-align:justify;\">"+Util.emailTranslation("Como parte de las actividades de seguridad y prevención, Grupo Modelo ha decidido aplicar la herramienta CEPA Driver Assessment para los conductores y colaboradores de la organización.")+"</p>";
       if(type == 3) {
    	   body += "<p style=\"text-align:justify;\">"+Util.emailTranslation("Con los datos de acceso a continuación podrá acceder a la plataforma online. Tendrá la oportunidad de agregar una fotografía de la licencia dentro de la misma aplicación.")+"<p>";        
       }else {
    	   body += "<p style=\"text-align:justify;\">"+Util.emailTranslation("Con los datos de acceso a continuación podrá acceder a la plataforma online. La aplicación contiene un un cuestionario breve y tendrá la oportunidad de agregar una fotografía de la licencia dentro de la misma aplicación.")+"<p>";        
    	   body += "<p style=\"text-align:justify;\">"+Util.emailTranslation("Por favor tenga en cuenta que deberá completar cada uno de los módulos en su totalidad; de lo contrario, el sistema no lo tomará como concluido.")+"</p>";
       }
       body += "Link: "+" www.cepada.com";
       body += "<br>";
       body += "Usuario: "+loginname;
       body += "<br>";
       body += "Password: "+password;
       body += "<br>";
       body += "<div><p style=\"text-align:justify;\">"+Util.emailTranslation("Para aclarar dudas y/o consultas, por favor comunicarse directamente con: soportemx@cepamobility.com")+"</p></div>";
       if(type != 3) {
	       body += "<p><b>"+Util.emailTranslation("Información para el usuario:")+"</b></p>";
	       body += "<div><ul>" + 
	            		   "<li>"+Util.emailTranslation("El sistema guarda sus repuestas automáticamente, podrá retomar su sesión cuantas veces lo desee hasta completar la actividad;")+"</li>"+ 
	            		   "<li>"+Util.emailTranslation("Tiempo de ejecución: aproximadamente 30 minutos por actividad;")+"</li>"+ 
	            		   "<li>"+Util.emailTranslation("Requerimientos: se podrá realizar desde cualquier dispositivo con conexión a internet.")+"</li>" + 
	            		   "</ul></div>";
       }
       body += "<div><p style=\"text-align:center;\"><b>"+Util.emailTranslation("¡Gracias por su participación! ¡Su aporte es sumamente valioso!")+"</b></p></div><br>";
       body	+= "<div style='max-width:700px;'>";
       body	+= "<p><img src='http://ebtw.cepasafedrive.com/imgs/email_feedback.png' alt='feedback'  style='max-width:700px;'/></p>";
       body	+= "</div>";
       body	+= "</div>";
       body	+= "</body>";
       body	+="</html> ";
       return body;
	}
}