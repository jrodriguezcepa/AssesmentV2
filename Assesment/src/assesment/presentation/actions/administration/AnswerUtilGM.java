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

public class AnswerUtilGM extends AnswerUtil {

	//private final String WEBSERVICE = "http://localhost:8080/WebServices2/ElearningWSAssessment?wsdl";
	//private final String URL_REDIRECT = "http://localhost/loadAssesment.php";
	
	
    public AnswerUtilGM() {
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
            files.add(fileName);

            fileName = AssesmentData.FLASH_PATH+"/reports/errores_"+name+".pdf";
            util.createUserErrorReport(sys,fileName,assesment,user.getLoginName());
            files.add(fileName);

        	String redirect = null;
        	Collection lessons = sys.getTagReportFacade().getAssociatedLessons(login,assesment.getId(),userSessionData);
            if(lessons.size() > 0){
            	
            	UserToRegister userToRegister = new UserToRegister();
            	String sex = "datatype.sex.male";
            	if (user.getSex() == UserData.FEMALE){
            		 sex = "datatype.sex.female";
            	}
            	Format dateFormat;	            	
            	dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            	
            	Integer dc5corporationId = new Integer(2107);
            	
                UserElearning userElearning = new UserElearning(
                		dateFormat.format(user.getBirthDate()),user.getCountry().intValue(),
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
            	
                /* Fin Sección: WebService */
            if(redirect == null) {
                fileName = AssesmentData.FLASH_PATH+"/reports/certificate_"+name+".pdf";
                util.createCertificate(fileName,user,assesment,sys);
                files.add(fileName);

                body += "<tr><td><table width='100%'>";
            	body += "<tr><td>Gracias por su aporte, el mismo ha sido muy valioso.</td></tr>";
            	body += "<tr><td>Adjuntamos reportes y cerificado.</td></tr>";
            }else {
            	body += "<tr><td><table width='100%'>";
            	body += "<tr><td>A Continuaci&oacute;n, lo invitamos a utilizar la herramienta web CEPA e-learning.</td></tr>";
            	body += "<tr><td>&nbsp;</td></tr>";
            	body += "<tr><td>El link a continuaci&oacute;n est&aacute; personalizado y &uacute;nico, contiene los temas necesarios que surgen de la evaluaci&oacute;n Driver Assessment, el mismo le servirar&aacute; para ingresar a la herramienta cada vez que lo desee hasta el xx/xx/xxxx.</td></tr>"; 
            	body += "<tr><td>&nbsp;</td></tr>";
            	body += "<tr><td>&nbsp;</td></tr>";
            	body += "<tr><td><a href=\""+redirect+"\">"+redirect+"</a></td></tr>";
            	body += "<tr><td>&nbsp;</td></tr>";
            	body += "<tr><td>Una vez finalizados todos los m&oacute;dulo y test final recibir&aacute;n por email un certificado de CEPA Driver Assessment / e-Learning.</td></tr>";
            	body += "<tr><td>&nbsp;</td></tr>";
            }
        	body += "</table></td></tr>";
        	body += "</table>";
                
            String[] senderAddress = sender.getAvailableMailAddress();
            if(!Util.empty(senderAddress[0])) {
            	sender.send(user.getEmail(),FROM_NAME,senderAddress[0],senderAddress[1],messages.getText("assessment.reports"),body,files);
            }
//            sender.send("federico.millan@cepasafedrive.com",SMTP,FROM_NAME,FROM_DIR,PASSWORD,messages.getText("assessment.reports"),body,files,AssesmentData.FLASH_PATH+"/images/logo5.jpg","logo5.jpg");
//            sender.send("jrodriguez@cepasafedrive.com",SMTP,FROM_NAME,FROM_DIR,PASSWORD,messages.getText("assessment.reports"),body,files);
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
        files.add(fileName);

        fileName = AssesmentData.FLASH_PATH+"/reports/errores_"+name+".pdf";
        util.createUserErrorReport(sys,fileName,assesment,user.getLoginName());
        files.add(fileName);
    	String redirect = userReport.getRedirect(login, assesment.getId(), userSessionData);

        if(redirect == null) {
            fileName = AssesmentData.FLASH_PATH+"/reports/certificate_"+name+".pdf";
            util.createCertificate(fileName,user,assesment,sys);
            files.add(fileName);

            body += "<tr><td><table width='100%'>";
        	body += "<tr><td>Gracias por su aporte, el mismo ha sido muy valioso.</td></tr>";
        	body += "<tr><td>Adjuntamos reportes y cerificado.</td></tr>";
        }else {
        	body += "<tr><td><table width='100%'>";
        	body += "<tr><td>A Continuaci&oacute;n, lo invitamos a utilizar la herramienta web CEPA e-learning.</td></tr>";
        	body += "<tr><td>&nbsp;</td></tr>";
        	body += "<tr><td>El link a continuaci&oacute;n est&aacute; personalizado y &uacute;nico, contiene los temas necesarios que surgen de la evaluaci&oacute;n Driver Assessment, el mismo le servirar&aacute; para ingresar a la herramienta cada vez que lo desee hasta el xx/xx/xxxx.</td></tr>"; 
        	body += "<tr><td>&nbsp;</td></tr>";
        	body += "<tr><td>&nbsp;</td></tr>";
        	body += "<tr><td><a href=\""+redirect+"\">"+redirect+"</a></td></tr>";
        	body += "<tr><td>&nbsp;</td></tr>";
        	body += "<tr><td>Una vez finalizados todos los m&oacute;dulo y test final recibir&aacute;n por email un certificado de CEPA Driver Assessment / e-Learning.</td></tr>";
        	body += "<tr><td>&nbsp;</td></tr>";
        }
    	body += "</table></td></tr>";
    	body += "</table>";
            
        sendEmail(user,assesment,sys,body,messages.getText("assessment.reports"),email,files,null);
    }
}
