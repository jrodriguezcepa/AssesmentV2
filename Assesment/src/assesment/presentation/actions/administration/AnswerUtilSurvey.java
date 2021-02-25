package assesment.presentation.actions.administration;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

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

public class AnswerUtilSurvey extends AnswerUtil {

	//private final String WEBSERVICE = "http://localhost:8080/WebServices2/ElearningWSAssessment?wsdl";
	//private final String URL_REDIRECT = "http://localhost/loadAssesment.php";
	
	
    public AnswerUtilSurvey() {
        super();
    }

    public void newFeedback(AssesmentAccess sys,AssesmentData assesment, String login, String email, boolean doFeedback, String redirect) throws Exception {
        UserSessionData userSessionData = sys.getUserSessionData();
        
        UsReportFacade userReport = sys.getUserReportFacade();
        
        Text messages = sys.getText();
        assesment.presentation.actions.report.Util util = new assesment.presentation.actions.report.Util();
        String body = "<table>";
        String emailTitle = "Fleet Survey Driver Trainning Certification Programm"; 
        if(userReport.isAssessmentDone(login,assesment.getId(),userSessionData,assesment.isPsitest())) {
            UserData user = userReport.findUserByPrimaryKey(login,userSessionData);

            MailSender sender = new MailSender();
            Collection<String> files = new LinkedList<String>();
            body += "<tr><td>";
            body += messages.getText("feedback.survey.thanks.message1");
            body += " <b>"+emailTitle+"</b> Powered by CEPA .";
            body += "</td><tr>";
            body += "<tr><td>"+messages.getText("feedback.survey.thanks.message2")+"</td><tr>";
            body += "<tr height=15><td></td></tr>";
            body += "<tr><td><img src='cid:figura1' alt=''></td></tr>";
            body += "</table>";
            String s = sys.getQuestionReportFacade().getEmailByQuestion(assesment.getId(),login,messages,userSessionData);
            if(s == null || s.length() == 0) 
            	s = user.getEmail();
            System.out.println(s+" --> "+body);
        	if(!Util.empty(s)) {
                boolean sended = false;
                System.out.println(s+" --> "+body);
                int count = 10;
                while(!sended && count > 0) {
                	try {
		                String[] senderAddress = sender.getAvailableMailAddress();
		                if(!Util.empty(senderAddress[0])) {
		                	sender.sendImage(s,FROM_NAME,senderAddress[0],senderAddress[1],emailTitle, files, body, AssesmentData.FLASH_PATH+"/images/email-survey.jpg");
		                	sended = true;
		                }
                	}catch (Exception e) {
						e.printStackTrace();
					}
		            count--;
                }
        	}
            
            if(doFeedback) {
	            Iterator it = sys.getAssesmentReportFacade().getAssesmentFeedback(assesment.getId(),userSessionData).iterator();
	            while(it.hasNext()) {
	                FeedbackData feedback = (FeedbackData)it.next();
	            	if(!Util.empty(feedback.getEmail())) {
	                    boolean sended = false;
	                    int count = 10;
	                    while(!sended && count > 0) {
	                    	try {
				                String[] senderAddress = sender.getAvailableMailAddress();
				                if(!Util.empty(senderAddress[0])) {
				                	sender.sendImage(feedback.getEmail(),FROM_NAME,senderAddress[0],senderAddress[1],emailTitle, files, body, AssesmentData.FLASH_PATH+"/images/email-survey.jpg");
				                	sended = true;
				                }
	                    	}catch (Exception e) {
	    						e.printStackTrace();
	    					}
	    		            count--;
	                    }
	            	}
	            }
	        }
        }
    }

	public void elearningRedirection(AssesmentAccess sys, String login, AssesmentData assesment) throws Exception {
		UserSessionData userSessionData = sys.getUserSessionData();
		UsReportFacade userReport = sys.getUserReportFacade();
		UserData user = userReport.findUserByPrimaryKey(login, userSessionData);
        String redirect = userReport.getRedirect(user.getLoginName(), assesment.getId(), userSessionData);
        boolean quizRed = userReport.isResultRed(user.getLoginName(),assesment.getId(),userSessionData);
        Collection lessons = sys.getTagReportFacade().getAssociatedLessons(user.getLoginName(),assesment.getId(),userSessionData);
        if(quizRed && lessons.size() > 0 && redirect == null){
       	
	       	UserToRegister userToRegister = new UserToRegister();
	       	String sex = "datatype.sex.male";
	       	
	       	// Dc5 Corporation Id del usuario, por defecto DEMO = 2
	       	CorporationData cd = sys.getCorporationReportFacade().findCorporation(assesment.getCorporationId(), userSessionData);
	       	Integer dc5corporationId = cd.getDc5id();
	       	if (dc5corporationId == null) 
	       		dc5corporationId = 2;
       	
           	UserElearning userElearning = new UserElearning(Util.formatDateElearning(user.getBirthDate()),user.getCountry(),
       			user.getExtraData2(), user.getFirstName(), user.getLanguage(),
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
	}
}
