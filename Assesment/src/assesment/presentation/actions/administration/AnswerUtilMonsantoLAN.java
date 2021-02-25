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

public class AnswerUtilMonsantoLAN extends AnswerUtil {

    public AnswerUtilMonsantoLAN() {
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

            fileName = AssesmentData.FLASH_PATH+"/reports/certificate_"+name+".pdf";
            util.createCertificate(fileName,user,assesment,sys);
            body += "<tr><td> - Certificado</td><tr>";
            files.add(fileName);

            
            body += "<tr><td><table width='100%'>";
            body += "<tr><th align='left'>Gracias por su colaboraci&oacute;n, su aporte ha sido muy valioso!!!</th></tr>";
            body += "<tr><td align='left'> De ser necesario su participaci&oacute;n en otras actividades nos estaremos comunicando con usted a la brevedad.</td></tr>";
            body += "</table></td></tr>";
            body += "</table>";
            
            String title = messages.getText("assessment.reports")+" - Monsanto LAN - "+name;
            if(assesment.getId().intValue() == AssesmentData.MAMUT_ANDINO) {
            	title = messages.getText("assessment.reports")+" - Mamut Andino - "+name;
            }
            if(assesment.getId().intValue() == AssesmentData.NALCO) {
            	title = messages.getText("assessment.reports")+" - Nalco - "+name;
            }
            if(assesment.getId().intValue() == AssesmentData.DNB) {
            	title = messages.getText("assessment.reports")+" - Dirección Nacional de Bomberos - "+name;
            }
            if(assesment.getId().intValue() == AssesmentData.TRANSMETA) {
            	title = messages.getText("assessment.reports")+" - Transmeta - "+name;
            }
            send(sender,user.getEmail(),messages,body,name,title,files);
            send(sender,"federico.millan@cepasafedrive.com",messages,body,name,title,files);
            //send(sender,"jrodriguez@cepasafedrive.com",messages,body,name,title,files);
        }
    }

    private void send(MailSender sender,String address,Text messages,String body,String name,String title,Collection files) {
        try {
            String[] senderAddress = sender.getAvailableMailAddress();
            if(!Util.empty(senderAddress[0])) {
            	sender.send(address,FROM_NAME,senderAddress[0],senderAddress[1],title,body,files);
            }
        }catch(Exception e) {
            try {
                String[] senderAddress = sender.getAvailableMailAddress();
                if(!Util.empty(senderAddress[0])) {
                	sender.send(address,FROM_NAME,senderAddress[0],senderAddress[1],title,body,files);
                }
            }catch(Exception ex) {
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

        fileName = AssesmentData.FLASH_PATH+"/reports/certificate_"+name+".pdf";
        util.createCertificate(fileName,user,assesment,sys);
        body += "<tr><td> - Certificado</td><tr>";
        files.add(fileName);

        
        body += "<tr><td><table width='100%'>";
        body += "<tr><th align='left'>Gracias por su colaboraci&oacute;n, su aporte ha sido muy valioso!!!</th></tr>";
        body += "<tr><td align='left'> De ser necesario su participaci&oacute;n en otras actividades nos estaremos comunicando con usted a la brevedad.</td></tr>";
        body += "</table></td></tr>";
        body += "</table>";
        
        String title = messages.getText("assessment.reports")+" - Monsanto LAN - "+name;
        if(assesment.getId().intValue() == AssesmentData.MAMUT_ANDINO) {
        	title = messages.getText("assessment.reports")+" - Mamut Andino - "+name;
        }
        if(assesment.getId().intValue() == AssesmentData.NALCO) {
        	title = messages.getText("assessment.reports")+" - Nalco - "+name;
        }
        if(assesment.getId().intValue() == AssesmentData.DNB) {
        	title = messages.getText("assessment.reports")+" - Dirección Nacional de Bomberos - "+name;
        }
        if(assesment.getId().intValue() == AssesmentData.TRANSMETA) {
        	title = messages.getText("assessment.reports")+" - Transmeta - "+name;
        }
        sendEmail(user,assesment,sys,body,title,email,files,null);
    }
}
