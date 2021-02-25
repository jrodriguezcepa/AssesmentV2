/**
 * Created on 20-nov-2008
 * CEPA
 * DataCenter 5
 */
package assesment.presentation.actions.administration;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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

public class AnswerUtilSanofi extends AnswerUtil {


	public AnswerUtilSanofi() {
        super();
    }

    public void feedback(AssesmentAccess sys,AssesmentAttributes assesment) throws Exception {
        boolean general = false;
        boolean psi = false;
        boolean errors = false;
        boolean personal = false;
        boolean certificate = false;
       
        
        UsReportFacade userReport = sys.getUserReportFacade();
        Text messages = sys.getText();
        UserSessionData userSessionData = sys.getUserSessionData();
        String login = userSessionData.getFilter().getLoginName();
        assesment.presentation.actions.report.Util util = new assesment.presentation.actions.report.Util();
        String emailTitle = messages.getText("assessment.reports")+" Sanofi";
        if(userReport.isAssessmentDone(login,assesment.getId(),userSessionData,assesment.isPsitest())) {
            UserData user = userReport.findUserByPrimaryKey(login,userSessionData);
            String name = user.getFirstName()+" "+user.getLastName();
            emailTitle += " - "+name;
            MailSender sender = new MailSender();
            Collection<String> files = new LinkedList<String>();
            if(assesment.isReportFeedback()) {
                String fileName = AssesmentData.FLASH_PATH+"/reports/resultado_"+name+".pdf";
                util.createUserReport(sys,fileName,assesment,user.getLoginName());
                general = true;
                files.add(fileName);
            }
            if(assesment.isErrorFeedback()) {
                String fileName = AssesmentData.FLASH_PATH+"/reports/errores_"+name+".pdf";
                util.createUserErrorReport(sys,fileName,assesment,user.getLoginName());
                errors = true;
                files.add(fileName);
            }
            if(assesment.isPsiFeedback()) {
                String fileName = AssesmentData.FLASH_PATH+"/reports/comportamiento_"+name+".pdf";
                util.createPsiReport(sys,fileName,assesment,user.getLoginName());
                psi = true;
                files.add(fileName);
            }
            if(assesment.isCertificate()) {
            	String fileName = AssesmentData.FLASH_PATH+"/reports/certificate_"+name+".pdf";
                certificate = true;
                util.createCertificate(fileName,user,assesment,sys);
                files.add(fileName);
            }
            String body = "<table>";
            body += "<tr><td><div>" +
            		"<span><b>Obrigado por sua atenção. Sua colaboração foi muito valiosa.</b></span>" +
            		"<span><br></span>" +
            		"<span>Caso sua participação em outras atividades se faça necessária, entraremos em contato em breve.</span></div>"+
            		"</td><tr>"+
			"			<tr>" +
			"				<td>" +
			"					<img src=\"cid:figura1\" alt=\"\">" +
			"					<br>" +
			"				</td>" +
			"			</tr>" +
            "</table>";
            String[] senderAddress = sender.getAvailableMailAddress();
            if(!Util.empty(senderAddress[0])) {
            	sender.sendImage(user.getEmail(),FROM_NAME,senderAddress[0],senderAddress[1],emailTitle,files,body,AssesmentData.FLASH_PATH+"/images/logo_sanofi_brasil_foot.jpg");
            	sender.sendImage("federico.millan@cepasafedrive.com",FROM_NAME,senderAddress[0],senderAddress[1],emailTitle,files,body,AssesmentData.FLASH_PATH+"/images/logo_sanofi_brasil_foot.jpg");
            }
        }
    }

    public void resend(AssesmentAccess sys,AssesmentAttributes assesment, String login, String email) throws Exception {
        UsReportFacade userReport = sys.getUserReportFacade();
        Text messages = sys.getText();
        UserSessionData userSessionData = sys.getUserSessionData();
        assesment.presentation.actions.report.Util util = new assesment.presentation.actions.report.Util();
        String emailTitle = messages.getText("assessment.reports")+" Sanofi";

        UserData user = userReport.findUserByPrimaryKey(login,userSessionData);
        String name = user.getFirstName()+" "+user.getLastName();
        emailTitle += " - "+name;
        MailSender sender = new MailSender();
        Collection<String> files = new LinkedList<String>();
        if(assesment.isReportFeedback()) {
            String fileName = AssesmentData.FLASH_PATH+"/reports/resultado_"+name+".pdf";
            util.createUserReport(sys,fileName,assesment,user.getLoginName());
            files.add(fileName);
        }
        if(assesment.isErrorFeedback()) {
            String fileName = AssesmentData.FLASH_PATH+"/reports/errores_"+name+".pdf";
            util.createUserErrorReport(sys,fileName,assesment,user.getLoginName());
            files.add(fileName);
        }
        if(assesment.isPsiFeedback()) {
            String fileName = AssesmentData.FLASH_PATH+"/reports/comportamiento_"+name+".pdf";
            util.createPsiReport(sys,fileName,assesment,user.getLoginName());
            files.add(fileName);
        }
        if(assesment.isCertificate()) {
        	String fileName = AssesmentData.FLASH_PATH+"/reports/certificate_"+name+".pdf";
            util.createCertificate(fileName,user,assesment,sys);
            files.add(fileName);
        }
        String body = "<table>";
        body += "<tr><td><div>" +
        		"<span><b>Obrigado por sua atenção. Sua colaboração foi muito valiosa.</b></span>" +
        		"<span><br></span>" +
        		"<span>Caso sua participação em outras atividades se faça necessária, entraremos em contato em breve.</span></div>"+
        		"</td><tr>"+
		"			<tr>" +
		"				<td>" +
		"					<img src=\"cid:figura1\" alt=\"\">" +
		"					<br>" +
		"				</td>" +
		"			</tr>" +
        "</table>";

        sendEmail(user,assesment,sys,body,emailTitle,email,files,null);
    }

}
