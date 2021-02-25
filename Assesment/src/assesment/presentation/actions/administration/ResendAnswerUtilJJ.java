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

public class ResendAnswerUtilJJ extends AnswerUtil {

	//private final String WEBSERVICE = "http://localhost:8080/WebServices2/ElearningWSAssessment?wsdl";
	//private final String URL_REDIRECT = "http://localhost/loadAssesment.php";
	
	
    public ResendAnswerUtilJJ() {
        super();
    }

    public void feedback(AssesmentAccess sys,AssesmentAttributes assesment) throws Exception {
        UsReportFacade userReport = sys.getUserReportFacade();
        Text messages = sys.getText();
        UserSessionData userSessionData = sys.getUserSessionData();
        String login = userSessionData.getFilter().getLoginName();
        assesment.presentation.actions.report.Util util = new assesment.presentation.actions.report.Util();
        String body = "<table>";
        if(userReport.isAssessmentDone(login,assesment.getId(),userSessionData,assesment.isPsitest())) {
            UserData user = userReport.findUserByPrimaryKey(login,userSessionData);
            String reference = getReference(user,userReport,userSessionData);
//            String name = user.getFirstName()+" "+user.getLastName();
            MailSender sender = new MailSender();
            Collection files = new LinkedList();
            
            String fileName = AssesmentData.FLASH_PATH+"/reports/resultado_"+reference+".pdf";
            util.createUserReport(sys,fileName,assesment,user.getLoginName());
            body += "<tr><td> - "+messages.getText("report.feedback.generalreport")+"</td><tr>";
            files.add(fileName);

            fileName = AssesmentData.FLASH_PATH+"/reports/errores_"+reference+".pdf";
            util.createUserErrorReport(sys,fileName,assesment,user.getLoginName());
            body += "<tr><td> - "+messages.getText("report.feedback.errorreport")+"</td><tr>";
            files.add(fileName);

            fileName = AssesmentData.FLASH_PATH+"/reports/comportamiento_"+reference+".pdf";
            util.createPsiReport(sys,fileName,assesment,user.getLoginName());
            body += "<tr><td> - "+messages.getText("report.feedback.psireport")+"</td><tr>";
            files.add(fileName);

            boolean quizGreen = userReport.isResultGreen(user.getLoginName(),assesment.getId(),userSessionData);
            
            if(quizGreen) {
                fileName = AssesmentData.FLASH_PATH+"/reports/certificate_"+reference+".pdf";
                util.createCertificate(fileName,user,assesment,sys);
                body += "<tr><td> - "+messages.getText("report.feedback.certificate")+"</td><tr>";
                files.add(fileName);

            }
            
        	body += "<tr><td><table width='100%'><tr>";
        	body += "<td><b>"+messages.getText("assesment.reporthtml.footermessage1")+"</b><br>"+messages.getText("assesment.reporthtml.footermessage2")+"</td>";
        	body += "</tr></table></td></tr>";
        	body += "</table>";
                
            String[] senderAddress = sender.getAvailableMailAddress();
            if(!Util.empty(senderAddress[0])) {
            	sender.send("jrodriguez@cepasafedrive.com",FROM_NAME,senderAddress[0],senderAddress[1],messages.getText("assessment.reports"),body,files,AssesmentData.FLASH_PATH+"/images/logo5.jpg","logo5.jpg");
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
            String reference = getReference(user,userReport,userSessionData);
            String name = user.getFirstName()+" "+user.getLastName();
            MailSender sender = new MailSender();
            Collection files = new LinkedList();
            
            
        	String redirect = null;
            	
                /* Fin Sección: WebService */
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
                
            String[] senderAddress = sender.getAvailableMailAddress();
            if(!Util.empty(senderAddress[0])) {
            	sender.send(user.getEmail(),FROM_NAME,senderAddress[0],senderAddress[1],messages.getText("assessment.reports"),body,files,AssesmentData.FLASH_PATH+"/images/logo5.jpg","logo5.jpg");
            }
//            sender.send("federico.millan@cepasafedrive.com",SMTP,FROM_NAME,FROM_DIR,PASSWORD,messages.getText("assessment.reports"),body,files,AssesmentData.FLASH_PATH+"/images/logo5.jpg","logo5.jpg");
//            sender.send("jrodriguez@cepasafedrive.com",FROM_NAME,messages.getText("assessment.reports"),body,files,AssesmentData.FLASH_PATH+"/images/logo5.jpg","logo5.jpg");
    }
    
    private String getChampion(UserData user) {
        switch(Integer.parseInt(user.getExtraData())) {
            case 4460:
                return "dsanto18@its.jnj.com";
            case 4461:
                switch (user.getCountry().intValue()) {
                    case 31: case 33: case 34: case 54:
                        return "SRomero@its.jnj.com";
                    case 32:
                        return "Jtota@its.jnj.com";
                    case 57: case 66: case 69:
                        return "SRolon@its.jnj.com";
                    case 56: case 67: case 59: case 61: case 63:
                        return "jaltaful@its.jnj.com";
                    case 55: case 37:
                        return "JCRUZ81@its.jnj.com";
                    case 42:
                        return "AOWEN2@its.jnj.com";
                    case 64:
                        return "LCardo@its.jnj.com";
                    case 39:
                        return "MLozano2@JNJVE.JNJ.com";
                }
            case 4462:
                switch (user.getCountry().intValue()) {
                    case 32:
                        return "JFERNAN7@its.jnj.com";
                    case 66: case 57: case 85:
                        return "famartin@its.jnj.com";
                    case 42:
                        return "MBENITE1@its.jnj.com";
                    case 55: case 64: case 37: case 39:
                        return "jyung1@its.jnj.com";
                    case 31: case 54: case 33:
                        return "ACASTRO@its.jnj.com";
            case 4463:
                switch (user.getCountry().intValue()) {
                    case 31: case 64: case 54:
                        return "gmarrese@its.jnj.com";
                    case 33:
                        return "SCALELLO@its.jnj.com";
                    case 32:
                        return "AMANZANO@its.jnj.com";
                    case 55:
                        return "GAGUILER@its.jnj.com";
                    case 42: case 57: case 66: case 69: case 56: case 67: case 59: case 61: case 62: case 63: case 85: case 170:
                        return "LCISNER2@its.jnj.com";
                    case 39:
                        return "lafrabot@its.jnj.com";
                }
                }
        }
        return null;
    }

    private String getReference(UserData user, UsReportFacade userReport, UserSessionData userSessionData) throws Exception {
        Integer code = userReport.getUserCode(user.getLoginName(),userSessionData);
        switch(Integer.parseInt(user.getExtraData())) {
            case 4460:
                return "VK"+String.valueOf(3000+code.intValue());
            case 4461:
                return "CN"+String.valueOf(2000+code.intValue());
            case 4462:
                return "MD&D"+String.valueOf(1000+code.intValue());
            case 4463:
                return "Janse"+String.valueOf(4000+code.intValue());
        }
        return null;
    }
}
