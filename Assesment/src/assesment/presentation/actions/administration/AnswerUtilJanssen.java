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

public class AnswerUtilJanssen extends AnswerUtil {

	
    public AnswerUtilJanssen() {
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
            Collection files = new LinkedList();
            
            String fileName = AssesmentData.FLASH_PATH+"/reports/resultado_"+reference+".pdf";
            util.createUserReport(sys,fileName,assesment,user.getLoginName());
            body += "<tr><td> - "+messages.getText("report.feedback.generalreport")+"</td><tr>";
            files.add(fileName);

            fileName = AssesmentData.FLASH_PATH+"/reports/errores_"+reference+".pdf";
            util.createUserErrorReport(sys,fileName,assesment,user.getLoginName());
            body += "<tr><td> - "+messages.getText("report.feedback.errorreport")+"</td><tr>";
            files.add(fileName);

            fileName = AssesmentData.FLASH_PATH+"/reports/certificate_"+reference+".pdf";
            util.createCertificate(fileName,user,assesment,sys);
            body += "<tr><td> - "+messages.getText("report.feedback.certificate")+"</td><tr>";
            files.add(fileName);
            
        	body += "<tr><td><table width='100%'><tr>";
        	body += "<td><b>"+messages.getText("assesment.reporthtml.footermessage1")+"</b><br>"+messages.getText("assesment.reporthtml.footermessage2")+"</td>";
        	body += "</tr></table></td></tr>";
        	body += "</table>";
                
            String[] senderAddress = sender.getAvailableMailAddress();
            if(!Util.empty(senderAddress[0])) {
            	sender.send(user.getEmail(),FROM_NAME,senderAddress[0],senderAddress[1],messages.getText("assessment.reports")+" - Janssen Brasil - "+reference,body,files,AssesmentData.FLASH_PATH+"/images/logo5.jpg","logo5.jpg");
            }
//            sender.send("federico.millan@cepasafedrive.com",FROM_NAME,messages.getText("assessment.reports")+" - Janssen Brasil - "+reference,body,files,AssesmentData.FLASH_PATH+"/images/logo5.jpg","logo5.jpg");
//            sender.send("jrodriguez@cepasafedrive.com",FROM_NAME,messages.getText("assessment.reports")+" - Janssen Brasil - "+reference,body,files,AssesmentData.FLASH_PATH+"/images/logo5.jpg","logo5.jpg");
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

        fileName = AssesmentData.FLASH_PATH+"/reports/certificate_"+reference+".pdf";
        util.createCertificate(fileName,user,assesment,sys);
        body += "<tr><td> - "+messages.getText("report.feedback.certificate")+"</td><tr>";
        files.add(fileName);
        
    	body += "<tr><td><table width='100%'><tr>";
    	body += "<td><b>"+messages.getText("assesment.reporthtml.footermessage1")+"</b><br>"+messages.getText("assesment.reporthtml.footermessage2")+"</td>";
    	body += "</tr></table></td></tr>";
    	body += "</table>";

    	sendEmail(user,assesment,sys,body,messages.getText("assessment.reports")+" - Janssen Brasil - "+reference,email,files,AssesmentData.FLASH_PATH+"/images/logo5.jpg");

    }
 }
