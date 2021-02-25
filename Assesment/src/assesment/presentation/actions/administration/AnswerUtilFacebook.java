package assesment.presentation.actions.administration;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.xml.ws.BindingProvider;

import ws.elearning.assessment.ElearningWSAssessment;
import ws.elearning.assessment.ElearningWSAssessmentService;

import assesment.business.AssesmentAccess;
import assesment.business.administration.user.UsABMFacade;
import assesment.business.administration.user.UsReportFacade;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.language.Text;
import assesment.communication.user.UserData;
import assesment.communication.util.MailSender;
import assesment.communication.ws.Identification;
import assesment.communication.ws.UserElearning;
import assesment.communication.ws.UserToRegister;
import assesment.presentation.translator.web.util.Util;

public class AnswerUtilFacebook extends AnswerUtil {

	//private final String WEBSERVICE = "http://localhost:8080/WebServices2/ElearningWSAssessment?wsdl";
	//private final String URL_REDIRECT = "http://localhost/loadAssesment.php";
	
	
    public AnswerUtilFacebook() {
        super();
    }

    public void feedback(AssesmentAccess sys,AssesmentAttributes assesment) throws Exception {
        UsReportFacade userReport = sys.getUserReportFacade();
        Text messages = sys.getText();
        UserSessionData userSessionData = sys.getUserSessionData();
        String login = userSessionData.getFilter().getLoginName();
        assesment.presentation.actions.report.Util util = new assesment.presentation.actions.report.Util();
        if(userReport.isAssessmentDone(login,assesment.getId(),userSessionData,assesment.isPsitest())) {
            UserData user = userReport.findUserByPrimaryKey(login,userSessionData);
            String name = user.getFirstName()+" "+user.getLastName();
            MailSender sender = new MailSender();
            Collection files = new LinkedList();
            
     /*       String fileName = AssesmentData.FLASH_PATH+"/reports/resultado_"+user.getLoginName()+".pdf";
            util.createUserReport(sys,fileName,assesment);
            files.add(fileName);
*/
            String body = "<table>";
            body += "<tr><td><b><span style='font-size: 1.25em;color:#000000'>"+messages.getText("assessment.facebook.mailmessage1")+"</span></b></td></tr>";
        	Collection lessonsText = sys.getTagReportFacade().getTextAssociatedLessons(login,assesment.getId(),userSessionData);
        	Collection lessons = sys.getTagReportFacade().getAssociatedLessons(login,assesment.getId(),userSessionData);
            String redirect = null;
        	if(lessons.size() > 0){

            	UserToRegister userToRegister = new UserToRegister();
            	String sex = "datatype.sex.male";

            	Format dateFormat;	            	
            	dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            	
            	// Dc5 Corporation Id del usuario, por defecto DEMO = 2
            	Integer dc5corporationId = new Integer(1105);
            	
                UserElearning userElearning = new UserElearning(
                		dateFormat.format(user.getBirthDate()),user.getCountry(),
            			user.getEmail(), user.getFirstName(), user.getLanguage(),
            			user.getLastName(),sex, dc5corporationId);
                userToRegister.setUser(userElearning);
                
                // Lecciones que tomará el usuario:
                LinkedList<Integer> idLessons = new LinkedList<Integer>();	       
                idLessons.addAll(lessons);
                userToRegister.setIdLessons(idLessons);
                
                /* Invocacion al WebService */ 
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
    			
    			/* fin Invocación al WebService*/ 
    			
    			UsABMFacade userABMFacade = sys.getUserABMFacade();
    			if (idString != null){
        			Identification identification = new Identification(idString);
        			if (identification != null){
        				redirect = URL_REDIRECT+"?key="+identification.getKey()+"&id="+identification.getId();
        				// No se habilita el Elearning hasta que pague en Abitab.
        				Boolean eLearningEnabled = new Boolean(false);
        				userABMFacade.setRedirect(user.getLoginName(),redirect,
        						new Integer(identification.getId()),
        						eLearningEnabled,
        						assesment.getId(), userSessionData);
        				userABMFacade.userUpdate(user, userSessionData);
        			}
    			}else{
    				System.out.println("Error al agregar usuario al Elearning:"+user.getLoginName());
    			}
                 
    			if(!Util.empty(redirect)) {
	            	body += "<tr><td></td></tr>";
	            	body += "<tr><td><i><span style='font-size: 1.15em;color:#666666'>"+messages.getText("assessment.facebook.mailmessage2")+"</span></i></td></tr>";
	            	body += "<tr><td><i><span style='font-size: 1.15em;color:#666666'>"+messages.getText("assessment.facebook.mailmessage3")+"</span></i></td></tr>";
	            	Collections.sort((List)lessonsText);
	            	Iterator it = lessonsText.iterator();
	            	while(it.hasNext()) {
	            		body += "<tr><td><span style='font-size: 1.15em;color:#000000'>*&nbsp;"+messages.getText("assesment.elearning.lesson"+String.valueOf(it.next()))+"</span></td></tr>";
	            	}
	            	body += "<tr><td></td></tr>";
	            	body += "<tr><td><a href='"+redirect+"'>"+redirect+"</a></td></tr>";
	            	body += "<tr><td></td></tr>";
    			}
            }
            body += "</table>";
                
            String[] senderAddress = sender.getAvailableMailAddress();
            if(!Util.empty(senderAddress[0])) {
            	try {
            		sender.send(user.getEmail(),FROM_NAME,senderAddress[0],senderAddress[1],messages.getText("assessment.reports"),body,files);
            	}catch (Exception e) {
                    senderAddress = sender.getAvailableMailAddress();
                    if(!Util.empty(senderAddress[0])) {
                		sender.send(user.getEmail(),FROM_NAME,senderAddress[0],senderAddress[1],messages.getText("assessment.reports"),body,files);
                    }
				}
            }

            senderAddress = sender.getAvailableMailAddress();
            if(!Util.empty(senderAddress[0])) {
            	try {
            		sender.send("federico.millan@cepasafedrive.com",FROM_NAME,senderAddress[0],senderAddress[1],messages.getText("assessment.reports"),body,files);
            	}catch (Exception e) {
                    senderAddress = sender.getAvailableMailAddress();
                    if(!Util.empty(senderAddress[0])) {
                		sender.send("federico.millan@cepasafedrive.com",FROM_NAME,senderAddress[0],senderAddress[1],messages.getText("assessment.reports"),body,files);
                    }
				}
            }
            
        }
    }

    public void resend(AssesmentAccess sys,AssesmentAttributes assesment, String login, String email) throws Exception {
        UsReportFacade userReport = sys.getUserReportFacade();
        Text messages = sys.getText();
        UserSessionData userSessionData = sys.getUserSessionData();
        assesment.presentation.actions.report.Util util = new assesment.presentation.actions.report.Util();
        UserData user = userReport.findUserByPrimaryKey(login,userSessionData);

        String name = user.getFirstName()+" "+user.getLastName();
        MailSender sender = new MailSender();
        Collection files = new LinkedList();
        

        String body = "<table>";
        body += "<tr><td><b><span style='font-size: 1.25em;color:#000000'>"+messages.getText("assessment.facebook.mailmessage1")+"</span></b></td></tr>";
    	Collection lessonsText = sys.getTagReportFacade().getTextAssociatedLessons(login,assesment.getId(),userSessionData);
    	Collection lessons = sys.getTagReportFacade().getAssociatedLessons(login,assesment.getId(),userSessionData);
    	String redirect = userReport.getRedirect(login, assesment.getId(), userSessionData);
    	if(lessons.size() > 0){
			if(!Util.empty(redirect)) {
            	body += "<tr><td></td></tr>";
            	body += "<tr><td><i><span style='font-size: 1.15em;color:#666666'>"+messages.getText("assessment.facebook.mailmessage2")+"</span></i></td></tr>";
            	body += "<tr><td><i><span style='font-size: 1.15em;color:#666666'>"+messages.getText("assessment.facebook.mailmessage3")+"</span></i></td></tr>";
            	Collections.sort((List)lessonsText);
            	Iterator it = lessonsText.iterator();
            	while(it.hasNext()) {
            		body += "<tr><td><span style='font-size: 1.15em;color:#000000'>*&nbsp;"+messages.getText("assesment.elearning.lesson"+String.valueOf(it.next()))+"</span></td></tr>";
            	}
            	body += "<tr><td></td></tr>";
            	body += "<tr><td><a href='"+redirect+"'>"+redirect+"</a></td></tr>";
            	body += "<tr><td></td></tr>";
			}
        }
        body += "</table>";
    
        sendEmail(user,assesment,sys,body,messages.getText("assessment.reports"),email,files,null);
    }
}
