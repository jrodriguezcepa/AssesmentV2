package assesment.presentation.actions.administration;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import assesment.business.AssesmentAccess;
import assesment.business.administration.user.UsReportFacade;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.language.Text;
import assesment.communication.user.UserData;
import assesment.communication.util.MailSender;
import assesment.presentation.translator.web.util.Util;

public class AnswerUtilMichelin extends AnswerUtil {

	//private final String WEBSERVICE = "http://localhost:8080/WebServices2/ElearningWSAssessment?wsdl";
	//private final String URL_REDIRECT = "http://localhost/loadAssesment.php";
	
	
    public AnswerUtilMichelin() {
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
            String reference = user.getFirstName()+" "+user.getLastName();
            MailSender sender = new MailSender();
            Collection<String> files = new LinkedList<String>();
            
            String fileName;
            if(assesment.isReportFeedback()) {
            	fileName = AssesmentData.FLASH_PATH+"/reports/resultado_"+reference+".pdf";
            	util.createUserReport(sys,fileName,assesment,user.getLoginName());
            	body += "<tr><td> - "+messages.getText("report.feedback.generalreport")+"</td><tr>";
            	files.add(fileName);
            }

            if(assesment.isErrorFeedback()) {
            	fileName = AssesmentData.FLASH_PATH+"/reports/errores_"+reference+".pdf";
            	util.createUserErrorReport(sys,fileName,assesment,user.getLoginName());
            	body += "<tr><td> - "+messages.getText("report.feedback.errorreport")+"</td><tr>";
            	files.add(fileName);
            }

            if(assesment.isPsiFeedback()) {
            	fileName = AssesmentData.FLASH_PATH+"/reports/comportamiento_"+reference+".pdf";
            	util.createPsiReport(sys,fileName,assesment,user.getLoginName());
            	body += "<tr><td> - "+messages.getText("report.feedback.psireport")+"</td><tr>";
            	files.add(fileName);
            }

            fileName = AssesmentData.FLASH_PATH+"/reports/certificate_"+reference+".pdf";
            util.createCertificate(fileName,user,assesment,sys);
            body += "<tr><td> - "+messages.getText("report.feedback.certificate")+"</td><tr>";
            files.add(fileName);
            	
            body += "<tr><td><table width='100%'><tr>";            
            body += "<td><b>"+messages.getText("assesment.reporthtml.footermessage1")+"</b></td>";
            body += "</tr></table></td></tr>";
            body += "</table>";
                
            String[] senderAddress = sender.getAvailableMailAddress();
            if(!Util.empty(senderAddress[0])) {
            	sender.send(user.getEmail(),FROM_NAME,senderAddress[0],senderAddress[1],"CEPA Driver Assessment – Michelin – "+user.getFirstName()+" "+user.getLastName(),body,files,AssesmentData.FLASH_PATH+"/images/mailMichelin.jpg","mailMichelin.jpg");
            }
            senderAddress = sender.getAvailableMailAddress();
            if(!Util.empty(senderAddress[0])) {
            	sender.send("federico.millan@cepasafedrive.com",FROM_NAME,senderAddress[0],senderAddress[1],"CEPA Driver Assessment – Michelin – "+user.getFirstName()+" "+user.getLastName(),body,files,AssesmentData.FLASH_PATH+"/images/mailMichelin.jpg","mailMichelin.jpg");
            }
//            sender.send("jrodriguez@cepasafedrive.com",FROM_NAME,"CEPA Driver Assessment – Michelin – "+user.getFirstName()+" "+user.getLastName(),body,files,AssesmentData.FLASH_PATH+"/images/mailMichelin.jpg","mailMichelin.jpg");
        }
    }

    public void resend(AssesmentAccess sys,AssesmentAttributes assesment, String login, String email) throws Exception {
        UsReportFacade userReport = sys.getUserReportFacade();
        Text messages = sys.getText();
        UserSessionData userSessionData = sys.getUserSessionData();
        assesment.presentation.actions.report.Util util = new assesment.presentation.actions.report.Util();
        String body = "<table>";

        UserData user = userReport.findUserByPrimaryKey(login,userSessionData);
        String reference = user.getFirstName()+" "+user.getLastName();
        Collection<String> files = new LinkedList<String>();
        
        String fileName;
        if(assesment.isReportFeedback()) {
        	fileName = AssesmentData.FLASH_PATH+"/reports/resultado_"+reference+".pdf";
        	util.createUserReport(sys,fileName,assesment,user.getLoginName());
        	body += "<tr><td> - "+messages.getText("report.feedback.generalreport")+"</td><tr>";
        	files.add(fileName);
        }

        if(assesment.isErrorFeedback()) {
        	fileName = AssesmentData.FLASH_PATH+"/reports/errores_"+reference+".pdf";
        	util.createUserErrorReport(sys,fileName,assesment,user.getLoginName());
        	body += "<tr><td> - "+messages.getText("report.feedback.errorreport")+"</td><tr>";
        	files.add(fileName);
        }

        if(assesment.isPsiFeedback()) {
        	fileName = AssesmentData.FLASH_PATH+"/reports/comportamiento_"+reference+".pdf";
        	util.createPsiReport(sys,fileName,assesment,user.getLoginName());
        	body += "<tr><td> - "+messages.getText("report.feedback.psireport")+"</td><tr>";
        	files.add(fileName);
        }

        fileName = AssesmentData.FLASH_PATH+"/reports/certificate_"+reference+".pdf";
        util.createCertificate(fileName,user,assesment,sys);
        body += "<tr><td> - "+messages.getText("report.feedback.certificate")+"</td><tr>";
        files.add(fileName);
        	
        body += "<tr><td><table width='100%'><tr>";            
        body += "<td><b>"+messages.getText("assesment.reporthtml.footermessage1")+"</b></td>";
        body += "</tr></table></td></tr>";
        body += "</table>";
                
        sendEmail(user,assesment,sys,body,"CEPA Driver Assessment – Michelin – "+user.getFirstName()+" "+user.getLastName(),email,files,AssesmentData.FLASH_PATH+"/images/mailMichelin.jpg");
    }
}
