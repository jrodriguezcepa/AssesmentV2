package assesment.presentation.actions.administration;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.LinkedList;

import javax.xml.ws.BindingProvider;

import ws.elearning.assessment.ElearningWSAssessment;
import ws.elearning.assessment.ElearningWSAssessmentService;
import assesment.business.AssesmentAccess;
import assesment.business.administration.user.UsReportFacade;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.corporation.CorporationData;
import assesment.communication.language.Text;
import assesment.communication.user.UserData;
import assesment.communication.util.MailSender;
import assesment.communication.ws.Identification;
import assesment.communication.ws.UserElearning;
import assesment.communication.ws.UserToRegister;
import assesment.presentation.translator.web.util.Util;

public class AnswerUtilSchering extends AnswerUtil {

	//private final String WEBSERVICE = "http://localhost:8080/WebServices2/ElearningWSAssessment?wsdl";
	//private final String URL_REDIRECT = "http://localhost/loadAssesment.php";
	
	
    public AnswerUtilSchering() {
        super();
    }

    public void feedback(AssesmentAccess sys,AssesmentAttributes assesment) throws Exception {
        UsReportFacade userReport = sys.getUserReportFacade();
        UserSessionData userSessionData = sys.getUserSessionData();
        String login = userSessionData.getFilter().getLoginName();
        assesment.presentation.actions.report.Util util = new assesment.presentation.actions.report.Util();
        String body = "<table>";
        body += "<tr><th>Obrigado por sua aten&ccedil;&atilde;o. Sua colabora&ccedil;&atilde;o foi muito valiosa. Enviamos em anexo.</th></tr>";
        body += "<tr><td>&nbsp;</th></tr>";
        if(userReport.isAssessmentDone(login,assesment.getId(),userSessionData,assesment.isPsitest())) {
            UserData user = userReport.findUserByPrimaryKey(login,userSessionData);
            String reference = user.getFirstName()+" "+user.getLastName();
            MailSender sender = new MailSender();
            Collection files = new LinkedList();
            
            String fileName = AssesmentData.FLASH_PATH+"/reports/resultado_"+reference+".pdf";
            util.createUserReport(sys,fileName,assesment,user.getLoginName());
            body += "<tr><td> - Relat&oacute;rio Geral</td><tr>";
            files.add(fileName);

            fileName = AssesmentData.FLASH_PATH+"/reports/errores_"+reference+".pdf";
            util.createUserErrorReport(sys,fileName,assesment,user.getLoginName());
            body += "<tr><td> - Relat&oacute;rio de Erros</td><tr>";
            files.add(fileName);

            fileName = AssesmentData.FLASH_PATH+"/reports/comportamiento_"+reference+".pdf";
            util.createPsiReport(sys,fileName,assesment,user.getLoginName());
            body += "<tr><td> - Relat&oacute;rio de Perfil</td><tr>";
            files.add(fileName);

            fileName = AssesmentData.FLASH_PATH+"/reports/certificate_"+reference+".pdf";
            util.createCertificate(fileName,user,assesment,sys);
//            body += "<tr><td> - "+messages.getText("report.feedback.certificate")+"</td><tr>";
            files.add(fileName);
            
            	
        	body += "<tr><td><table width='100%'><tr>";
        	body += "</tr></table></td></tr>";
        	body += "</table>";
                
            String emailTitle = "CEPA Driver Assessment – Merck Brasil – "+reference; 
            String[] senderAddress = sender.getAvailableMailAddress();
            if(!Util.empty(senderAddress[0])) {
            	sender.send(user.getEmail(),FROM_NAME,senderAddress[0],senderAddress[1],emailTitle,body,files);
            }
        }
    }

    public void feedback2(AssesmentAccess sys) throws Exception {
        UsReportFacade userReport = sys.getUserReportFacade();
        Text messages = sys.getText();
        UserSessionData userSessionData = sys.getUserSessionData();
        String login = "Roberto Murillo";
        assesment.presentation.actions.report.Util util = new assesment.presentation.actions.report.Util();
        String body = "<table>";
            UserData user = userReport.findUserByPrimaryKey(login,userSessionData);
            String name = user.getFirstName()+" "+user.getLastName();
            MailSender sender = new MailSender();
            Collection files = new LinkedList();
            
            
        	String redirect = null;
            	
                /* Fin SecciÃ³n: WebService */
            if(redirect == null) {
            	body += "<tr><td><table width='100%'><tr>";
            	body += "<td><b>"+messages.getText("assesment.reporthtml.footermessage1")+"</b><br>"+messages.getText("assesment.reporthtml.footermessage2")+"</td>";
            	body += "</tr></table></td></tr>";
            	body += "</table>";
            }else {
            	body += "<tr><td><table width='100%'>";
            	body += "<tr><td><b>"+messages.getText("assesment.reporthtml.footermessage1")+"</b></td></tr>";
            	body += "<tr><td><i><span style=\"font-size: 10pt; color: rgb(102, 102, 102); font-family: 'Verdana','sans-serif';\">"+messages.getText("assesment.reporthtml.footermessage4")+"</span></i></td></tr>";
            	body += "<tr><td>&nbsp;</td></tr>";
            	body += "<tr><td><a href=\""+redirect+"\">"+redirect+"</a></td></tr>";
            	body += "<tr><td>&nbsp;</td></tr>";
            	body += "<tr><td><i><span style=\"font-size: 10pt; color: rgb(102, 102, 102); font-family: 'Verdana','sans-serif';\">"+messages.getText("assesment.reporthtml.footermessage5")+"</span></i><a href=\"mailto:indesa@cepasafedrive.com\">indesa@cepasafedrive.com</a></td></tr>";
            	body += "<tr><td>&nbsp;</td></tr>";
            	body += "</table></td></tr>";
            	body += "</table>";
            }
                
//            sender.send(user.getEmail(),SMTP,FROM_NAME,FROM_DIR,PASSWORD,messages.getText("assessment.reports"),body,files,AssesmentData.FLASH_PATH+"/images/logo5.jpg","logo5.jpg");
//            sender.send("federico.millan@cepasafedrive.com",SMTP,FROM_NAME,FROM_DIR,PASSWORD,messages.getText("assessment.reports"),body,files,AssesmentData.FLASH_PATH+"/images/logo5.jpg","logo5.jpg");
//            sender.send("jrodriguez@cepasafedrive.com",FROM_NAME,messages.getText("assessment.reports"),body,files,AssesmentData.FLASH_PATH+"/images/logo5.jpg","logo5.jpg");
    }
    
    private String getDivision(UserData user) {
        switch(Integer.parseInt(user.getExtraData())) {
	        case 1: return "SAUDE FEMENINA";
	        case 2: return "Cardio";
	        case 3: return "Inmunología";	
	        case 4: return "Specialty VIRO";
	        case 5: return "Specialty ONCO";
	        case 6: return "Specialty ANESTESIA";
	        case 7: return "Administrativo";
	        case 8: return "Primary Care ";
        }
        return null;
    }

    public void resend(AssesmentAccess sys,AssesmentAttributes assesment, String login, String email) throws Exception {
        UsReportFacade userReport = sys.getUserReportFacade();
        UserSessionData userSessionData = sys.getUserSessionData();
        assesment.presentation.actions.report.Util util = new assesment.presentation.actions.report.Util();
        String body = "<table>";
        body += "<tr><th>Obrigado por sua aten&ccedil;&atilde;o. Sua colabora&ccedil;&atilde;o foi muito valiosa. Enviamos em anexo.</th></tr>";
        body += "<tr><td>&nbsp;</th></tr>";

        UserData user = userReport.findUserByPrimaryKey(login,userSessionData);
        String reference = user.getFirstName()+" "+user.getLastName();
        MailSender sender = new MailSender();
        Collection files = new LinkedList();
        
        String fileName = AssesmentData.FLASH_PATH+"/reports/resultado_"+reference+".pdf";
        util.createUserReport(sys,fileName,assesment,user.getLoginName());
        body += "<tr><td> - Relat&oacute;rio Geral</td><tr>";
        files.add(fileName);

        fileName = AssesmentData.FLASH_PATH+"/reports/errores_"+reference+".pdf";
        util.createUserErrorReport(sys,fileName,assesment,user.getLoginName());
        body += "<tr><td> - Relat&oacute;rio de Erros</td><tr>";
        files.add(fileName);

        fileName = AssesmentData.FLASH_PATH+"/reports/comportamiento_"+reference+".pdf";
        util.createPsiReport(sys,fileName,assesment,user.getLoginName());
        body += "<tr><td> - Relat&oacute;rio de Perfil</td><tr>";
        files.add(fileName);

        fileName = AssesmentData.FLASH_PATH+"/reports/certificate_"+reference+".pdf";
        util.createCertificate(fileName,user,assesment,sys);
        files.add(fileName);
        
        	
    	body += "<tr><td><table width='100%'><tr>";
    	body += "</tr></table></td></tr>";
    	body += "</table>";
            
        String emailTitle = "CEPA Driver Assessment – Merck Brasil – "+reference; 
        sendEmail(user,assesment,sys,body,emailTitle,email,files,null);
    }
}
