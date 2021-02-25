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

public class AnswerUtilPepsico extends AnswerUtil {

    public AnswerUtilPepsico() {
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
            
            String fileName = AssesmentData.FLASH_PATH+"/reports/resultado_"+name+".pdf";
            util.createUserReport(sys,fileName,assesment,user.getLoginName());
            body += "<tr><td> - Reporte General</td><tr>";
            files.add(fileName);

            fileName = AssesmentData.FLASH_PATH+"/reports/errores_"+name+".pdf";
            util.createUserErrorReport(sys,fileName,assesment,user.getLoginName());
            body += "<tr><td> - Reporte de Errores</td><tr>";
            files.add(fileName);

            fileName = AssesmentData.FLASH_PATH+"/reports/comportamiento_"+name+".pdf";
            util.createPsiReport(sys,fileName,assesment,user.getLoginName());
            body += "<tr><td> - Comportamiento</td><tr>";
            files.add(fileName);

            fileName = AssesmentData.FLASH_PATH+"/reports/certificate_"+name+".pdf";
            util.createCertificate(fileName,user,assesment,sys);
            body += "<tr><td> - Certificado</td><tr>";
            files.add(fileName);

            
            body += "<tr><td><table width='100%'>";
            body += "<tr><th align='left'>Gracias por su colaboraci&oacute;n, su aporte ha sido muy valioso!!!</th></tr>";
            body += "<tr><td align='left'> De ser necesario su participaci&oacute;n en otras actividades nos estaremos comunicando con usted a la brevedad.</td></tr>";
            body += "</table></td></tr>";
            body += "</table>";
            
            String title = messages.getText("assessment.reports")+" - Pepsico México - "+name;

            send(sender,user.getEmail(),messages,body,name,title,files);
            send(sender,"federico.millan@cepasafedrive.com",messages,body,name,title,files);
            send(sender,"lorena.vargas@pepsico.com",messages,body,name,title,files);
        }
    }

    private void send(MailSender sender,String address,Text messages,String body,String name,String title,Collection files) {
    	int i = 0;
    	boolean sended = false;
    	while (!sended && i< 5) {
    		i++;
    		try {
    			String[] senderAddress = sender.getAvailableMailAddress();
    			if(!Util.empty(senderAddress[0])) {
    				sender.send(address,FROM_NAME,senderAddress[0],senderAddress[1],title,body,files);
    				sended = true;
    			}
    		}catch(Exception e) {
    			e.printStackTrace();
            }
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
        
        String fileName = AssesmentData.FLASH_PATH+"/reports/resultado_"+name+".pdf";
        util.createUserReport(sys,fileName,assesment,user.getLoginName());
        body += "<tr><td> - Reporte General</td><tr>";
        files.add(fileName);

        fileName = AssesmentData.FLASH_PATH+"/reports/errores_"+name+".pdf";
        util.createUserErrorReport(sys,fileName,assesment,user.getLoginName());
        body += "<tr><td> - Reporte de Errores</td><tr>";
        files.add(fileName);

        fileName = AssesmentData.FLASH_PATH+"/reports/comportamiento_"+name+".pdf";
        util.createPsiReport(sys,fileName,assesment,user.getLoginName());
        body += "<tr><td> - Comportamiento</td><tr>";
        files.add(fileName);

        fileName = AssesmentData.FLASH_PATH+"/reports/certificate_"+name+".pdf";
        util.createCertificate(fileName,user,assesment,sys);
        body += "<tr><td> - Certificado</td><tr>";
        files.add(fileName);

        
        body += "<tr><td><table width='100%'>";
        body += "<tr><th align='left'>Gracias por su colaboraci&oacute;n, su aporte ha sido muy valioso!!!</th></tr>";
        body += "<tr><td align='left'> De ser necesario su participaci&oacute;n en otras actividades nos estaremos comunicando con usted a la brevedad.</td></tr>";
        body += "</table></td></tr>";
        body += "</table>";
        
        String title = messages.getText("assessment.reports")+" - Pepsico México - "+name;
        sendEmail(user,assesment,sys,body,title,email,files,null);
    }
}
