/**
 * Created on 20-nov-2008
 * CEPA
 * DataCenter 5
 */
package assesment.presentation.actions.administration;

import java.util.Collection;
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

public class AnswerUtilMonsantoBR extends AnswerUtil {

    public AnswerUtilMonsantoBR() {
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
            String name = user.getFirstName()+" "+user.getLastName();
            MailSender sender = new MailSender();
            Collection files = new LinkedList();
            
            String fileName = AssesmentData.FLASH_PATH+"/reports/resultado_"+login+".pdf";
            util.createUserReport(sys,fileName,assesment,user.getLoginName());
            body += "<tr><td> - "+messages.getText("report.feedback.generalreport")+"</td><tr>";
            files.add(fileName);

            fileName = AssesmentData.FLASH_PATH+"/reports/errores_"+login+".pdf";
            util.createUserErrorReport(sys,fileName,assesment,user.getLoginName());
            body += "<tr><td> - "+messages.getText("report.feedback.errorreport")+"</td><tr>";
            files.add(fileName);

            fileName = AssesmentData.FLASH_PATH+"/reports/comportamiento_"+login+".pdf";
            util.createPsiReport(sys,fileName,assesment,user.getLoginName());
            body += "<tr><td> - "+messages.getText("report.feedback.psireport")+"</td><tr>";
            files.add(fileName);

            
            String textMail = (user.getLanguage().equals("pt")) ? "Obrigado por sua colabora&ccedil;&atilde;o, sua contribui&ccedil;&atilde;o foi muito valiosa!" : "Gracias por su colaboraci&oacute;n, su contribuci&oacute;n fue muy valiosa!";
            if(!userReport.isResultRed(user.getLoginName(),assesment.getId(),userSessionData) && !userReport.isPsiRed(user.getLoginName(),assesment.getId(),userSessionData)) {
                fileName = AssesmentData.FLASH_PATH+"/reports/certificate_"+login+".pdf";
                util.createCertificate(fileName,user,assesment,sys);
                body += "<tr><td> - "+messages.getText("report.feedback.certificate")+"</td><tr>";
                files.add(fileName);
            }
            
            body += "<tr><td><table width='100%'><tr>";
            body += "<th>"+textMail+"</th>";
            body += "</tr></table></td></tr>";
            body += "</table>";
            send(sender,user.getEmail(),messages,body,files);
            send(sender,"federico.millan@cepasafedrive.com",messages,body,files);
            //send(sender,"jrodriguez@cepasafedrive.com",messages,body,files);
        }
    }

    public void resend(AssesmentAccess sys,AssesmentAttributes assesment, String login, String email) throws Exception {
        UsReportFacade userReport = sys.getUserReportFacade();
        Text messages = sys.getText();
        UserSessionData userSessionData = sys.getUserSessionData();
        assesment.presentation.actions.report.Util util = new assesment.presentation.actions.report.Util();
        String body = "<table>";

        UserData user = userReport.findUserByPrimaryKey(login,userSessionData);
        String name = user.getFirstName()+" "+user.getLastName();
        MailSender sender = new MailSender();
        Collection files = new LinkedList();
        
        String fileName = AssesmentData.FLASH_PATH+"/reports/resultado_"+login+".pdf";
        util.createUserReport(sys,fileName,assesment,user.getLoginName());
        body += "<tr><td> - "+messages.getText("report.feedback.generalreport")+"</td><tr>";
        files.add(fileName);

        fileName = AssesmentData.FLASH_PATH+"/reports/errores_"+login+".pdf";
        util.createUserErrorReport(sys,fileName,assesment,user.getLoginName());
        body += "<tr><td> - "+messages.getText("report.feedback.errorreport")+"</td><tr>";
        files.add(fileName);

        fileName = AssesmentData.FLASH_PATH+"/reports/comportamiento_"+login+".pdf";
        util.createPsiReport(sys,fileName,assesment,user.getLoginName());
        body += "<tr><td> - "+messages.getText("report.feedback.psireport")+"</td><tr>";
        files.add(fileName);

        
        String textMail = (user.getLanguage().equals("pt")) ? "Obrigado por sua colabora&ccedil;&atilde;o, sua contribui&ccedil;&atilde;o foi muito valiosa!" : "Gracias por su colaboraci&oacute;n, su contribuci&oacute;n fue muy valiosa!";
        if(!userReport.isResultRed(user.getLoginName(),assesment.getId(),userSessionData) && !userReport.isPsiRed(user.getLoginName(),assesment.getId(),userSessionData)) {
            fileName = AssesmentData.FLASH_PATH+"/reports/certificate_"+login+".pdf";
            util.createCertificate(fileName,user,assesment,sys);
            body += "<tr><td> - "+messages.getText("report.feedback.certificate")+"</td><tr>";
            files.add(fileName);
        }
        
        body += "<tr><td><table width='100%'><tr>";
        body += "<th>"+textMail+"</th>";
        body += "</tr></table></td></tr>";
        body += "</table>";

        sendEmail(user,assesment,sys,body,messages.getText("assessment.reports"),email,files,null);
    }

    private void send(MailSender sender,String address,Text messages,String body,Collection files) {
        try {
            String[] senderAddress = sender.getAvailableMailAddress();
            if(!Util.empty(senderAddress[0])) {
            	sender.send(address,FROM_NAME,senderAddress[0],senderAddress[1],messages.getText("assessment.reports"),body,files);
            }
        }catch(Exception e) {
            try {
                String[] senderAddress = sender.getAvailableMailAddress();
                if(!Util.empty(senderAddress[0])) {
                	sender.send(address,FROM_NAME,senderAddress[0],senderAddress[1],messages.getText("assessment.reports"),body,files);
                }
            }catch(Exception ex) {
            }
        }
    }

}
