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
import assesment.communication.assesment.FeedbackData;
import assesment.communication.corporation.CorporationData;
import assesment.communication.language.Text;
import assesment.communication.user.UserData;
import assesment.communication.util.MailSender;
import assesment.communication.ws.Identification;
import assesment.communication.ws.UserElearning;
import assesment.communication.ws.UserToRegister;
import assesment.presentation.translator.web.util.Util;

public class AnswerUtilAZ extends AnswerUtil {


	public AnswerUtilAZ() {
        super();
    }

    public void feedback(AssesmentAccess sys,AssesmentAttributes assesment) throws Exception {
        UsReportFacade userReport = sys.getUserReportFacade();
        Text messages = sys.getText();
        UserSessionData userSessionData = sys.getUserSessionData();
        String login = userSessionData.getFilter().getLoginName();
        assesment.presentation.actions.report.Util util = new assesment.presentation.actions.report.Util();
        String body = "<table>";
        body += "<tr><td align='left'><span style='font-family:Calibri;font-size:13;color:#000000'><b>Gracias por su colaboraci&oacute;n, su aporte ha sido muy valioso.</b></span></td></tr>";
        
        if(userReport.isAssessmentDone(login,assesment.getId(),userSessionData,assesment.isPsitest())) {
            UserData user = userReport.findUserByPrimaryKey(login,userSessionData);
            String name = user.getFirstName()+" "+user.getLastName();
            MailSender sender = new MailSender();
            Collection files = new LinkedList();
            
            String fileName = AssesmentData.FLASH_PATH+"/reports/resultadoAstrazeneca_"+name+".pdf";
            util.createUserReport(sys,fileName,assesment,user.getLoginName());
            files.add(fileName);

            fileName = AssesmentData.FLASH_PATH+"/reports/erroresAstrazeneca_"+name+".pdf";
            util.createUserErrorReport(sys,fileName,assesment,user.getLoginName());
            files.add(fileName);

            boolean quizGreen = userReport.isResultGreen(user.getLoginName(),assesment.getId(),userSessionData);
        	String redirect = null;
            if(quizGreen) {
            	fileName = AssesmentData.FLASH_PATH+"/reports/certificateAstrazeneca_"+name+".pdf";
            	util.createCertificate(fileName,user,assesment,sys);
            	files.add(fileName);
            }
            
        	Collection lessons = sys.getTagReportFacade().getAssociatedLessons(login,assesment.getId(),userSessionData);
            if(lessons.size() > 0){

            	body += "<tr><td align='left'><span style='font-family:Calibri;font-size:13;color:#000000;'>Continuando con el proceso de mejoramiento continuo, lo invitamos a utilizar la herramienta web e-learning.</span></td></tr>";
            	body += "<tr><td align='left'><span style='font-family:Calibri;font-size:13;color:#000000;'>El link a continuaci&oacute;n est&aacute; personalizado y contiene los m&oacute;dulos que debe completar, el mismo le servir&aacute; para ingresar a la herramienta cada vez que lo desee. Una vez termine los m&oacute;dulos y test final, recibir&aacute; un Certificado del curso e-Learning/Assessment en esta misma cuenta de email.</span></td></tr>";

            	Collection lessonsText = sys.getTagReportFacade().getTextAssociatedLessons(login,assesment.getId(),userSessionData);
            	Collections.sort((List)lessonsText);
            	Iterator it = lessonsText.iterator();
            	body += "<tr height='20'><td>&nbsp;</td></tr>";
            	while(it.hasNext()) {
            		body += "<tr><td><span style='font-family:Calibri;font-size:13;color:#000000'>-&nbsp;"+Util.formatHTML(messages.getText("assesment.elearning.lesson"+String.valueOf(it.next())))+"</span></td></tr>";
            	}
            	body += "<tr height='20'><td>&nbsp;</td></tr>";
            	
           		UserToRegister userToRegister = new UserToRegister();
            	String sex = "datatype.sex.male";
            	
            	// Dc5 Corporation Id del usuario, por defecto DEMO = 2
            	CorporationData cd = sys.getCorporationReportFacade().findCorporation(assesment.getCorporationId(), userSessionData);
            	Integer dc5corporationId = cd.getDc5id();
            	if (dc5corporationId == null) dc5corporationId = 2109;
            	
                UserElearning userElearning = new UserElearning("1970-01-01",42,
            			user.getEmail(), user.getFirstName(), user.getLanguage(),
            			user.getLastName(),sex, dc5corporationId);
                userToRegister.setUser(userElearning);
                
                // Lecciones que tomará el usuario:
                LinkedList<Integer> idLessons = new LinkedList<Integer>();	       
                idLessons.addAll(lessons);
                userToRegister.setIdLessons(idLessons);
                
                ElearningWSAssessmentService service = new ElearningWSAssessmentService();
    			ElearningWSAssessment port = service.getElearningWSAssessmentPort();	    			
    			String endpointURL = WEBSERVICE;	    			
    			BindingProvider bp = (BindingProvider)port;
    			bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointURL);
    			String idString = null;
    			
    			try{
    				idString = port.addUserToCourse(userToRegister.toString());
    			}catch(Exception e){
    				e.printStackTrace();
    			}
    			
    			
    			if (idString != null){
	    			Identification identification = new Identification(idString);
	    			if (identification != null){
	    				redirect = URL_REDIRECT+"?key="+identification.getKey()+"&id="+identification.getId();
	    				Boolean eLearningEnabled = new Boolean(true);
	    				sys.getUserABMFacade().setRedirect(user.getLoginName(),redirect,
	    						new Integer(identification.getId()),
	    						eLearningEnabled,
	    						assesment.getId(), userSessionData);
	    			}
    			}else{
    				System.out.println("Error al agregar usuario al Elearning:"+user.getLoginName());
    			}
            }

            if(redirect != null) {
            	body += "<tr><td><span style='font-family:Calibri;font-size:13;color:#000000'><b>Importante:</b> favor no compartir ni reenviar el link a continuaci&oacute;n el mismo es &uacute;nico y personalizado para usted. Gracias.</span></td></tr>";
            	body += "<tr><td><span style='font-family:Calibri;font-size:13;color:#000000'><b>Abrir este enlace con la aplicaci&oacute;n AZ Lite</b></span></td></tr>";
            	body += "<tr><td><a href='"+redirect+"'><span style='font-family:Calibri;font-size:13;color:#000000'><b>"+redirect+"</b></span></a></td></tr>";

            }
            body += "<tr><td><table width='100%'>";
            body += "</table></td></tr>";
            body += "</table>";
            
            String title = messages.getText("assessment.reports")+" - Astrazeneca - "+name;
            send(sender,user.getEmail(),messages,body,name,title,files);
            Iterator it = sys.getAssesmentReportFacade().getAssesmentFeedback(assesment.getId(),userSessionData).iterator();
            while(it.hasNext()) {
                FeedbackData feedback = (FeedbackData)it.next();
                send(sender,feedback.getEmail(),messages,body,name,title,files);
            }
           // send(sender,"jrodriguez@cepasafedrive.com",messages,body,name,title,files);
        }
    }

    public void resend(AssesmentAccess sys,AssesmentAttributes assesment, String login, String email) throws Exception {
        UsReportFacade userReport = sys.getUserReportFacade();
        Text messages = sys.getText();
        UserSessionData userSessionData = sys.getUserSessionData();
        assesment.presentation.actions.report.Util util = new assesment.presentation.actions.report.Util();
        String body = "<table>";
        body += "<tr><td align='left'><span style='font-family:Calibri;font-size:13;color:#000000'><b>Gracias por su colaboraci&oacute;n, su aporte ha sido muy valioso.</b></span></td></tr>";
        
        UserData user = userReport.findUserByPrimaryKey(login,userSessionData);
        String name = user.getFirstName()+" "+user.getLastName();
        MailSender sender = new MailSender();
        Collection files = new LinkedList();
        
        String fileName = AssesmentData.FLASH_PATH+"/reports/resultadoAstrazeneca_"+name+".pdf";
        util.createUserReport(sys,fileName,assesment,user.getLoginName());
        files.add(fileName);

        fileName = AssesmentData.FLASH_PATH+"/reports/erroresAstrazeneca_"+name+".pdf";
        util.createUserErrorReport(sys,fileName,assesment,user.getLoginName());
        files.add(fileName);

        boolean quizGreen = userReport.isResultGreen(user.getLoginName(),assesment.getId(),userSessionData);
    	String redirect = userReport.getRedirect(login, assesment.getId(), userSessionData);
        if(quizGreen) {
        	fileName = AssesmentData.FLASH_PATH+"/reports/certificateAstrazeneca_"+name+".pdf";
        	util.createCertificate(fileName,user,assesment,sys);
        	files.add(fileName);
        }
        
    	Collection lessons = sys.getTagReportFacade().getAssociatedLessons(login,assesment.getId(),userSessionData);
        if(lessons.size() > 0){

        	body += "<tr><td align='left'><span style='font-family:Calibri;font-size:13;color:#000000;'>Continuando con el proceso de mejoramiento continuo, lo invitamos a utilizar la herramienta web e-learning.</span></td></tr>";
        	body += "<tr><td align='left'><span style='font-family:Calibri;font-size:13;color:#000000;'>El link a continuaci&oacute;n est&aacute; personalizado y contiene los m&oacute;dulos que debe completar, el mismo le servir&aacute; para ingresar a la herramienta cada vez que lo desee. Una vez termine los m&oacute;dulos y test final, recibir&aacute; un Certificado del curso e-Learning/Assessment en esta misma cuenta de email.</span></td></tr>";

        	Collection lessonsText = sys.getTagReportFacade().getTextAssociatedLessons(login,assesment.getId(),userSessionData);
        	Collections.sort((List)lessonsText);
        	Iterator it = lessonsText.iterator();
        	body += "<tr height='20'><td>&nbsp;</td></tr>";
        	while(it.hasNext()) {
        		body += "<tr><td><span style='font-family:Calibri;font-size:13;color:#000000'>-&nbsp;"+Util.formatHTML(messages.getText("assesment.elearning.lesson"+String.valueOf(it.next())))+"</span></td></tr>";
        	}
        	body += "<tr height='20'><td>&nbsp;</td></tr>";

        }            	
           
        if(lessons.size() > 0 && redirect == null){
        	
       		UserToRegister userToRegister = new UserToRegister();
        	String sex = "datatype.sex.male";
        	
        	// Dc5 Corporation Id del usuario, por defecto DEMO = 2
        	CorporationData cd = sys.getCorporationReportFacade().findCorporation(assesment.getCorporationId(), userSessionData);
        	Integer dc5corporationId = cd.getDc5id();
        	if (dc5corporationId == null) dc5corporationId = 2109;
        	
            UserElearning userElearning = new UserElearning("1970-01-01",42,
        			user.getEmail(), user.getFirstName(), user.getLanguage(),
        			user.getLastName(),sex, dc5corporationId);
            userToRegister.setUser(userElearning);
            
            // Lecciones que tomará el usuario:
            LinkedList<Integer> idLessons = new LinkedList<Integer>();	       
            idLessons.addAll(lessons);
            userToRegister.setIdLessons(idLessons);
            
            ElearningWSAssessmentService service = new ElearningWSAssessmentService();
			ElearningWSAssessment port = service.getElearningWSAssessmentPort();	    			
			String endpointURL = WEBSERVICE;	    			
			BindingProvider bp = (BindingProvider)port;
			bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointURL);
			String idString = null;
			
			try{
				idString = port.addUserToCourse(userToRegister.toString());
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
			if (idString != null){
    			Identification identification = new Identification(idString);
    			if (identification != null){
    				redirect = URL_REDIRECT+"?key="+identification.getKey()+"&id="+identification.getId();
    				Boolean eLearningEnabled = new Boolean(true);
    				sys.getUserABMFacade().setRedirect(user.getLoginName(),redirect,
    						new Integer(identification.getId()),
    						eLearningEnabled,
    						assesment.getId(), userSessionData);
    			}
			}else{
				System.out.println("Error al agregar usuario al Elearning:"+user.getLoginName());
			}
        }

        if(redirect != null) {
        	body += "<tr><td><span style='font-family:Calibri;font-size:13;color:#000000'><b>Importante:</b> favor no compartir ni reenviar el link a continuaci&oacute;n el mismo es &uacute;nico y personalizado para usted. Gracias.</span></td></tr>";
        	body += "<tr><td><span style='font-family:Calibri;font-size:13;color:#000000'><b>Abrir este enlace con la aplicaci&oacute;n AZ Lite</b></span></td></tr>";
        	body += "<tr><td><a href='"+redirect+"'><span style='font-family:Calibri;font-size:13;color:#000000'><b>"+redirect+"</b></span></a></td></tr>";

        }
        body += "<tr><td><table width='100%'>";
        body += "</table></td></tr>";
        body += "</table>";
        
        String title = messages.getText("assessment.reports")+" - Astrazeneca - "+name;

        sendEmail(user,assesment,sys,body,title,email,files,null);
    }

    private void send(MailSender sender,String address,Text messages,String body,String name,String title,Collection files) {
        boolean sended = false;
        int count = 0;
        while(!sended && count < 10) {
	    	try {
	            String[] senderAddress = sender.getAvailableMailAddress();
	            if(!Util.empty(senderAddress[0])) {
	            	sender.send(address,FROM_NAME,senderAddress[0],senderAddress[1],title,body,files);
	            	sended = true;
	            }
	        }catch(Exception e) {
	        }
	        count++;
    	}
    }

}
