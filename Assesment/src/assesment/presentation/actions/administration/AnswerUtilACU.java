/**
 * Created on 20-nov-2008
 * CEPA
 * DataCenter 5
 */
package assesment.presentation.actions.administration;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import assesment.business.AssesmentAccess;
import assesment.business.administration.user.UsReportFacade;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.assesment.FeedbackData;
import assesment.communication.language.Text;
import assesment.communication.user.UserData;
import assesment.communication.util.MailSender;
import assesment.presentation.translator.web.util.Util;

public class AnswerUtilACU extends AnswerUtil {

    public AnswerUtilACU() {
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
            //body += "<tr><td> - Reporte General</td><tr>";
            files.add(fileName);

            fileName = AssesmentData.FLASH_PATH+"/reports/errores_"+name+".pdf";
            util.createUserErrorReport(sys,fileName,assesment,user.getLoginName());
            //body += "<tr><td> - Reporte de Errores</td><tr>";
            files.add(fileName);
            
            fileName = AssesmentData.FLASH_PATH+"/reports/comportamiento_"+name+".pdf";
            util.createPsiReport(sys,fileName,assesment,user.getLoginName());
            files.add(fileName);

            fileName = AssesmentData.FLASH_PATH+"/reports/certificado_"+name+".pdf";
            util.createCertificate(fileName, user, assesment, sys);
            //body += "<tr><td> - Reporte de Errores</td><tr>";
            files.add(fileName);

            body += "<tr><td><table width='100%'>";
            body += "<tr><th align='left'>Muchas gracias por su participaci&oacute;n, adjunto le enviamos los resultados del test.</th></tr>";
            body += "</table></td></tr>";
            body += "</table>";
            
            String title = messages.getText("assessment.reports")+" - ACU - "+name;

            send(sender,user.getEmail(),messages,body,name,title,files,AssesmentData.FLASH_PATH+"/images/logos-acu-cepa.jpg","logo.jpg");
            System.out.println(user.getEmail());
            send(sender,"jrodriguez@cepasafedrive.com",messages,body,name,title,files,AssesmentData.FLASH_PATH+"/images/logos-acu-cepa.jpg","logo.jpg");
        /*    Iterator it = sys.getAssesmentReportFacade().getAssesmentFeedback(assesment.getId(),userSessionData).iterator();
            while(it.hasNext()) {
                FeedbackData feedback = (FeedbackData)it.next();
                send(sender,feedback.getEmail(),messages,body,name,title,files,AssesmentData.FLASH_PATH+"/images/logos-acu-cepa.jpg","logo.jpg");
            }*/
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
        Collection files = new LinkedList();
        
        String fileName = AssesmentData.FLASH_PATH+"/reports/resultado_"+name+".pdf";
        util.createUserReport(sys,fileName,assesment,user.getLoginName());
        files.add(fileName);

        fileName = AssesmentData.FLASH_PATH+"/reports/errores_"+name+".pdf";
        util.createUserErrorReport(sys,fileName,assesment,user.getLoginName());
        files.add(fileName);
        
        fileName = AssesmentData.FLASH_PATH+"/reports/comportamiento_"+name+".pdf";
        util.createPsiReport(sys,fileName,assesment,user.getLoginName());
        files.add(fileName);

        fileName = AssesmentData.FLASH_PATH+"/reports/certificado_"+name+".pdf";
        util.createCertificate(fileName, user,assesment , sys);
        files.add(fileName);

        body += "<tr><td><table width='100%'>";
        body += "<tr><th align='left'>Muchas gracias por su participaci&oacute;n, adjunto le enviamos los resultados del test.</th></tr>";
        body += "</table></td></tr>";
        body += "</table>";
        
        String title = messages.getText("assessment.reports")+" - ACU - "+name;
        
        sendEmail(user,assesment,sys,body,title,email,files,AssesmentData.FLASH_PATH+"/images/logos-acu-cepa.jpg");
    }

    private void send(MailSender sender,String address,Text messages,String body,String name,String title,Collection files,String image,String imageName) {
        try {
            String[] senderAddress = sender.getAvailableMailAddress();
            if(!Util.empty(senderAddress[0])) {
            	sender.send(address,FROM_NAME,senderAddress[0],senderAddress[1],title,body,files,image,imageName);
            }
        }catch(Exception e) {
            try {
                String[] senderAddress = sender.getAvailableMailAddress();
                if(!Util.empty(senderAddress[0])) {
                	sender.send(address,FROM_NAME,senderAddress[0],senderAddress[1],title,body,files,image,imageName);
                }
            }catch(Exception ex) {
            	ex.printStackTrace();
            }
        }
    }

}
