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

public class AnswerUtilJJ extends AnswerUtil {

	//private final String WEBSERVICE = "http://localhost:8080/WebServices2/ElearningWSAssessment?wsdl";
	//private final String URL_REDIRECT = "http://localhost/loadAssesment.php";
	
	
    public AnswerUtilJJ() {
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
            String reference = getReference(user,userReport,userSessionData);
            String name = user.getFirstName()+" "+user.getLastName();
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

            fileName = AssesmentData.FLASH_PATH+"/reports/comportamiento_"+reference+".pdf";
            util.createPsiReport(sys,fileName,assesment,user.getLoginName());
            body += "<tr><td> - "+messages.getText("report.feedback.psireport")+"</td><tr>";
            files.add(fileName);

            boolean quizGreen = userReport.isResultGreen(user.getLoginName(),assesment.getId(),userSessionData);
            
            if(quizGreen) {
                fileName = AssesmentData.FLASH_PATH+"/reports/certificate_"+reference+".pdf";
                util.createCertificate(fileName,user,assesment,sys);
                body += "<tr><td> - "+messages.getText("report.feedback.certificate")+"</td><tr>";
                files.add(fileName);

            }
            
        	String redirect = null;
            if(!quizGreen) {
            	Collection lessons = sys.getTagReportFacade().getAssociatedLessons(login,assesment.getId(),userSessionData);
                if(lessons.size() > 0){
	            	
	            	UserToRegister userToRegister = new UserToRegister();
	            	String sex = "datatype.sex.male";
	            	
	            	// Dc5 Corporation Id del usuario, por defecto DEMO = 2
	            	CorporationData cd = sys.getCorporationReportFacade().findCorporation(assesment.getCorporationId(), userSessionData);
	            	Integer dc5corporationId = cd.getDc5id();
	            	if (dc5corporationId == null) dc5corporationId = 2;
	            	
	                UserElearning userElearning = new UserElearning("1970-01-01",user.getCountry(),
	            			user.getEmail(), user.getFirstName(), user.getLanguage(),
	            			user.getLastName(),sex, dc5corporationId);
	                userToRegister.setUser(userElearning);
	                
	                // Lecciones que tomar√° el usuario:
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
            	
                /* Fin Secci√≥n: WebService */
            if(redirect == null) {
            	body += "<tr><td><table width='100%'><tr>";
            	body += "<td><b>"+messages.getText("assesment.reporthtml.footermessage1")+"</b><br>"+messages.getText("assesment.reporthtml.footermessage2")+"</td>";
            	body += "</tr></table></td></tr>";
            	body += "</table>";
            }else {
            	body += "<tr><td><table width='100%'>";
            	body += "<tr><td><b>"+messages.getText("assesment.reporthtml.footermessage1")+"</b></td></tr>";
            	body += "<tr><td><i><span style=\"font-size: 10pt; color: rgb(102, 102, 102); font-family: 'Verdana','sans-serif';\">"+messages.getText("assesment.reporthtml.footermessage4")+"</span></i></td></tr>";
            	body += "<tr><td>&nbsp;</td></tr>";
            	body += "<tr><td><a href=\""+redirect+"\">"+redirect+"</a></td></tr>";
            	body += "<tr><td>&nbsp;</td></tr>";
            	body += "<tr><td><i><span style=\"font-size: 10pt; color: rgb(102, 102, 102); font-family: 'Verdana','sans-serif';\">"+messages.getText("assesment.reporthtml.footermessage5")+"</span></i><a href=\"mailto:indesa@cepasafedrive.com\">indesa@cepasafedrive.com</a></td></tr>";
            	body += "<tr><td>&nbsp;</td></tr>";
            	body += "</table></td></tr>";
            	body += "</table>";
            }
                
            String[] senderAddress = sender.getAvailableMailAddress();
            Collection<String> cc = new LinkedList<String>(); 
            cc.add("federico.millan@cepasafedrive.com");
            cc.add("mirureta@cepasafedrive.com");
            if(!Util.empty(user.getExtraData3())) {
            	cc.add(user.getExtraData3());
            }
            if(!Util.empty(senderAddress[0])) {
            	sender.send(user.getExtraData2(),FROM_NAME,senderAddress[0],senderAddress[1],"CEPA Driver Assessment ñ Safe Fleet J&J ñ "+name,body,files,AssesmentData.FLASH_PATH+"/images/logo5.jpg","logo5.jpg",cc);
            	//sender.send("jrodriguez@cepasafedrive.com",FROM_NAME,senderAddress[0],senderAddress[1],"CEPA Driver Assessment ñ Safe Fleet J&J ñ "+name,body,files,AssesmentData.FLASH_PATH+"/images/logo5.jpg","logo5.jpg",cc);
            }
            
        }
    }

    public void feedback2(AssesmentAccess sys,AssesmentAttributes assesment) throws Exception {
        UsReportFacade userReport = sys.getUserReportFacade();
        Text messages = sys.getText();
        UserSessionData userSessionData = sys.getUserSessionData();
        String login = userSessionData.getFilter().getLoginName();
        assesment.presentation.actions.report.Util util = new assesment.presentation.actions.report.Util();
        String body = "<table>";
        if(userReport.isAssessmentDone(login,assesment.getId(),userSessionData,assesment.isPsitest())) {
            UserData user = userReport.findUserByPrimaryKey(login,userSessionData);
            String reference = getReference(user,userReport,userSessionData);
            String name = user.getFirstName()+" "+user.getLastName();
            MailSender sender = new MailSender();
            Collection files = new LinkedList();
/*            
            String fileName = AssesmentData.FLASH_PATH+"/reports/resultado_"+reference+".pdf";
            util.createUserReport(sys,fileName,assesment);
            body += "<tr><td> - "+messages.getText("report.feedback.generalreport")+"</td><tr>";
            files.add(fileName);

            fileName = AssesmentData.FLASH_PATH+"/reports/errores_"+reference+".pdf";
            util.createUserErrorReport(sys,fileName,assesment);
            body += "<tr><td> - "+messages.getText("report.feedback.errorreport")+"</td><tr>";
            files.add(fileName);

            fileName = AssesmentData.FLASH_PATH+"/reports/comportamiento_"+reference+".pdf";
            util.createPsiReport(sys,fileName,assesment);
            body += "<tr><td> - "+messages.getText("report.feedback.psireport")+"</td><tr>";
            files.add(fileName);
*/
            boolean quizGreen = userReport.isResultGreen(user.getLoginName(),assesment.getId(),userSessionData);
            
        	String redirect = null;
            if(!quizGreen) {
            	Collection lessons = sys.getTagReportFacade().getAssociatedLessons(login,assesment.getId(),userSessionData);
                if(lessons.size() > 0){
	            	
	            	UserToRegister userToRegister = new UserToRegister();
	            	String sex = "datatype.sex.male";
	            	
	            	// Dc5 Corporation Id del usuario, por defecto DEMO = 2
	            	CorporationData cd = sys.getCorporationReportFacade().findCorporation(assesment.getCorporationId(), userSessionData);
	            	Integer dc5corporationId = cd.getDc5id();
	            	if (dc5corporationId == null) dc5corporationId = 2;
	            	
	                UserElearning userElearning = new UserElearning("1970-01-01",32,
	            			user.getExtraData2(), user.getFirstName(), user.getLanguage(),
	            			user.getLastName(),sex, dc5corporationId);
	                userToRegister.setUser(userElearning);
	                
	                // Lecciones que tomar√° el usuario:
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
            
            if(redirect != null) {

            	body += "<tr><td><table width='100%'>";
            	body += "<tr><td><b>"+messages.getText("assesment.reporthtml.footermessage1")+"</b></td></tr>";
            	body += "<tr><td><i><span style=\"font-size: 10pt; color: rgb(102, 102, 102); font-family: 'Verdana','sans-serif';\">"+messages.getText("assesment.reporthtml.footermessage4")+"</span></i></td></tr>";
            	body += "<tr><td>&nbsp;</td></tr>";
            	body += "<tr><td><a href=\""+redirect+"\">"+redirect+"</a></td></tr>";
            	body += "<tr><td>&nbsp;</td></tr>";
            	body += "<tr><td><i><span style=\"font-size: 10pt; color: rgb(102, 102, 102); font-family: 'Verdana','sans-serif';\">"+messages.getText("assesment.reporthtml.footermessage5")+"</span></i><a href=\"mailto:indesa@cepasafedrive.com\">indesa@cepasafedrive.com</a></td></tr>";
            	body += "<tr><td>&nbsp;</td></tr>";
            	body += "</table></td></tr>";
            	body += "</table>";

            	body += "<tr><td><table width='100%'><tr>";
            	body += "<td><b>"+messages.getText("assesment.reporthtml.footermessage1")+"</b><br>"+messages.getText("assesment.reporthtml.footermessage2")+"</td>";
            	body += "</tr></table></td></tr>";
            	body += "</table>";
                
	            
            }
            
            String[] senderAddress = sender.getAvailableMailAddress();
            if(!Util.empty(senderAddress[0])) {
            	sender.send(user.getEmail(),FROM_NAME,senderAddress[0],senderAddress[1],"CEPA Driver Assessment ñ Safe Fleet J&J ñ "+name,body,files,AssesmentData.FLASH_PATH+"/images/logo5.jpg","logo5.jpg");
            }

            senderAddress = sender.getAvailableMailAddress();
            if(!Util.empty(senderAddress[0])) {
            	sender.send("federico.millan@cepasafedrive.com",FROM_NAME,senderAddress[0],senderAddress[1],"CEPA Driver Assessment ñ Safe Fleet J&J ñ "+name,body,files,AssesmentData.FLASH_PATH+"/images/logo5.jpg","logo5.jpg");
            }

            senderAddress = sender.getAvailableMailAddress();
            if(!Util.empty(senderAddress[0])) {
            	sender.send("mirureta@cepasafedrive.com",FROM_NAME,senderAddress[0],senderAddress[1],"CEPA Driver Assessment ñ Safe Fleet J&J ñ "+name,body,files,AssesmentData.FLASH_PATH+"/images/logo5.jpg","logo5.jpg");
            }
        }
    }
    
    public void createLink(AssesmentAccess sys,AssesmentAttributes assesment, String login) throws Exception {
        UserSessionData userSessionData = sys.getUserSessionData();
        UsReportFacade userReport = sys.getUserReportFacade();
        UserData user = userReport.findUserByPrimaryKey(login,userSessionData);
    	Collection lessons = sys.getTagReportFacade().getAssociatedLessons(login,assesment.getId(),userSessionData);
        if(lessons.size() > 0){
        	
        	UserToRegister userToRegister = new UserToRegister();
        	String sex = "datatype.sex.male";
        	
        	// Dc5 Corporation Id del usuario, por defecto DEMO = 2
        	CorporationData cd = sys.getCorporationReportFacade().findCorporation(assesment.getCorporationId(), userSessionData);
        	Integer dc5corporationId = cd.getDc5id();
        	if (dc5corporationId == null) dc5corporationId = 2;
        	
            UserElearning userElearning = new UserElearning("1970-01-01",32,
        			user.getExtraData2(), user.getFirstName(), user.getLanguage(),
        			user.getLastName(),sex, dc5corporationId);
            userToRegister.setUser(userElearning);
            
            // Lecciones que tomar√° el usuario:
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
    				String redirect = URL_REDIRECT+"?key="+identification.getKey()+"&id="+identification.getId();
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
    
    private String getChampion(UserData user) {
        switch(Integer.parseInt(user.getExtraData())) {
            case 4460:
                return "dsanto18@its.jnj.com";
            case 4461:
                switch (user.getCountry().intValue()) {
                    case 31: case 33: case 34: case 54:
                        return "SRomero@its.jnj.com";
                    case 32:
                        return "Jtota@its.jnj.com";
                    case 57: case 66: case 69:
                        return "SRolon@its.jnj.com";
                    case 56: case 67: case 59: case 61: case 63:
                        return "jaltaful@its.jnj.com";
                    case 55: case 37:
                        return "JCRUZ81@its.jnj.com";
                    case 42:
                        return "AOWEN2@its.jnj.com";
                    case 64:
                        return "LCardo@its.jnj.com";
                    case 39:
                        return "MLozano2@JNJVE.JNJ.com";
                }
            case 4462:
                switch (user.getCountry().intValue()) {
                    case 32:
                        return "JFERNAN7@its.jnj.com";
                    case 66: case 57: case 85:
                        return "famartin@its.jnj.com";
                    case 42:
                        return "MBENITE1@its.jnj.com";
                    case 55: case 64: case 37: case 39:
                        return "jyung1@its.jnj.com";
                    case 31: case 54: case 33:
                        return "ACASTRO@its.jnj.com";
            case 4463:
                switch (user.getCountry().intValue()) {
                    case 31: case 64: case 54:
                        return "gmarrese@its.jnj.com";
                    case 33:
                        return "SCALELLO@its.jnj.com";
                    case 32:
                        return "AMANZANO@its.jnj.com";
                    case 55:
                        return "GAGUILER@its.jnj.com";
                    case 42: case 57: case 66: case 69: case 56: case 67: case 59: case 61: case 62: case 63: case 85: case 170:
                        return "LCISNER2@its.jnj.com";
                    case 39:
                        return "lafrabot@its.jnj.com";
                }
                }
        }
        return null;
    }

    private String getReference(UserData user, UsReportFacade userReport, UserSessionData userSessionData) throws Exception {
        Integer code = userReport.getUserCode(user.getLoginName(),userSessionData);
        switch(Integer.parseInt(user.getExtraData())) {
            case 4460:
                return "VK"+String.valueOf(3000+code.intValue());
            case 4461:
                return "CN"+String.valueOf(2000+code.intValue());
            case 4462:
                return "MD&D"+String.valueOf(1000+code.intValue());
            case 4463:
                return "Janse"+String.valueOf(4000+code.intValue());
        }
        return null;
    }

    public void resend(AssesmentAccess sys,AssesmentAttributes assesment, String login, String email) throws Exception {
        UserSessionData userSessionData = sys.getUserSessionData();
        
        UsReportFacade userReport = sys.getUserReportFacade();
        
        Text messages = sys.getText();
        assesment.presentation.actions.report.Util util = new assesment.presentation.actions.report.Util();
        String body = "<table>";
        String emailTitle = "BTW3 " + messages.getText("assessment.reports") + " - "+assesment.getName(); 
        if(userReport.isAssessmentDone(login,assesment.getId(),userSessionData,assesment.isPsitest())) {
            UserData user = userReport.findUserByPrimaryKey(login,userSessionData);

            String name = user.getFirstName()+" "+user.getLastName();
            emailTitle += " - "+name;
            MailSender sender = new MailSender();
            Collection<String> files = new LinkedList<String>();
            if(assesment.isReportFeedback()) {
                String fileName = AssesmentData.FLASH_PATH+"/reports/resultado_"+name+".pdf";
                util.createTotalReport(fileName,user,(AssesmentData)assesment,sys);
                files.add(fileName);
            }
            boolean quizGreen = userReport.isResultGreen(login,assesment.getId(),userSessionData);
            if(isCertificate((AssesmentData)assesment,sys) && quizGreen) {
            	String fileName = AssesmentData.FLASH_PATH+"/reports/certificate_"+name+".pdf";
                util.createCertificate(fileName,user,assesment,sys);
                files.add(fileName);
            }
            
            body += "<tr><td>"+messages.getText("feedback.thanksmessage")+"</td><tr>";
            body += "<tr><td><a href='www.cepasafedrive.com'>www.cepasafedrive.com</a></td><tr>";
            body += "<tr><td><table width='100%'><tr>";            
            body += "<td><b>"+messages.getText("assesment.reporthtml.footermessage1")+"</b></td>";
            body += "</tr>";
            body += "</table></td></tr>";                   
            body += "<tr><td></td><tr>";
            body += "</table>";

            if(files.size() > 0) {
            	if(!Util.empty(email)) {
                    boolean sended = false;
                    int count = 10;
                    while(!sended && count > 0) {
                    	try {
			                String[] senderAddress = sender.getAvailableMailAddress();
			                if(!Util.empty(senderAddress[0])) {
			                	sender.send(email,FROM_NAME,senderAddress[0],senderAddress[1],emailTitle,body,files);
			                	sended = true;
			                }
                    	}catch (Exception e) {
    						e.printStackTrace();
    					}
    		            count--;
                    }
            	}
            }
            
            Iterator it = sys.getAssesmentReportFacade().getAssesmentFeedback(assesment.getId(),userSessionData).iterator();
            while(it.hasNext()) {
                FeedbackData feedback = (FeedbackData)it.next();
                Iterator reports = feedback.getReports().iterator();
                body = "<table>";
                body += "<tr><td>"+messages.getText("feedback.thanksmessage")+"</td><tr>";
                body += "<tr><td><a href='www.cepasafedrive.com'>www.cepasafedrive.com</a></td><tr>";
                body += "<tr><td><table width='100%'><tr>";            
                body += "<td><b>"+messages.getText("assesment.reporthtml.footermessage1")+"</b></td>";
                body += "</tr>";
                body += "</table></td></tr>";                   
                body += "<tr><td></td><tr>";
                body += "</table>";
                if(files.size() > 0) {
                    boolean sended = false;
                    int count = 10;
                    while(!sended && count > 0) {
                    	try {
			                String[] senderAddress = sender.getAvailableMailAddress();
			                if(!Util.empty(senderAddress[0])) {
			                	sender.send(feedback.getEmail(),FROM_NAME,senderAddress[0],senderAddress[1],emailTitle,body,files);
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

    public void newFeedback(AssesmentAccess sys,AssesmentData assesment, String login, String email, boolean doFeedback, String redirect) throws Exception {
        UserSessionData userSessionData = sys.getUserSessionData();
        
        UsReportFacade userReport = sys.getUserReportFacade();
        
        Text messages = sys.getText();
        assesment.presentation.actions.report.Util util = new assesment.presentation.actions.report.Util();
        String body = "<table>";
        String emailTitle = "BTW3 " + messages.getText("assessment.reports") + " - "+assesment.getName(); 
        if(userReport.isAssessmentDone(login,assesment.getId(),userSessionData,assesment.isPsitest())) {
            UserData user = userReport.findUserByPrimaryKey(login,userSessionData);

            String name = user.getFirstName()+" "+user.getLastName();
            emailTitle += " - "+name;
            MailSender sender = new MailSender();
            Collection<String> files = new LinkedList<String>();
            if(assesment.isReportFeedback()) {
                String fileName = AssesmentData.FLASH_PATH+"/reports/resultado_"+name+".pdf";
                util.createTotalReport(fileName,user,assesment,sys);
                files.add(fileName);
            }
            boolean quizGreen = userReport.isResultGreen(login,assesment.getId(),userSessionData);
            if(isCertificate(assesment,sys) && quizGreen) {
            	String fileName = AssesmentData.FLASH_PATH+"/reports/certificate_"+name+".pdf";
                util.createCertificate(fileName,user,assesment,sys);
                files.add(fileName);
            }
            
            Collection lessons = sys.getTagReportFacade().getAssociatedLessons(login,assesment.getId(),userSessionData);
            if(!quizGreen && lessons.size() > 0 && redirect == null){
           	
	           	UserToRegister userToRegister = new UserToRegister();
	           	String sex = "datatype.sex.male";
	           	
	           	// Dc5 Corporation Id del usuario, por defecto DEMO = 2
	           	CorporationData cd = sys.getCorporationReportFacade().findCorporation(assesment.getCorporationId(), userSessionData);
	           	Integer dc5corporationId = cd.getDc5id();
	           	if (dc5corporationId == null) dc5corporationId = 2;
	           	
	               UserElearning userElearning = new UserElearning("1970-01-01",user.getCountry(),
	           			user.getExtraData2(), user.getFirstName(), user.getLanguage(),
	           			user.getLastName(),sex, dc5corporationId);
	               userToRegister.setUser(userElearning);
	               
	               // Lecciones que tomar√° el usuario:
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

            body += "<tr><td>"+messages.getText("feedback.thanksmessage")+"</td><tr>";
            body += "<tr><td><a href='www.cepasafedrive.com'>www.cepasafedrive.com</a></td><tr>";
            body += "<tr><td><table width='100%'><tr>";            
            body += "<td><b>"+messages.getText("assesment.reporthtml.footermessage1")+"</b></td>";
            body += "</tr>";
    	    if(redirect != null) {
    	    	body += "<tr><td><i><span style=\"font-size: 10pt; color: rgb(102, 102, 102); font-family: 'Verdana','sans-serif';\">"+messages.getText("assesment.reporthtml.footermessage4")+"</span></i></td></tr>";
    	    	body += "<tr><td>&nbsp;</td></tr>";
    	    	body += "<tr><td><a href=\""+redirect+"\">"+redirect+"</a></td></tr>";
    	    	body += "<tr><td>&nbsp;</td></tr>";
    	    	body += "<tr><td><i><span style=\"font-size: 10pt; color: rgb(102, 102, 102); font-family: 'Verdana','sans-serif';\">"+messages.getText("assesment.reporthtml.footermessage5")+"</span></i><a href=\"mailto:indesa@cepasafedrive.com\">indesa@cepasafedrive.com</a></td></tr>";
    	    	body += "<tr><td>&nbsp;</td></tr>";
    	    }
            body += "</table></td></tr>";                   
            body += "<tr><td></td><tr>";
            body += "</table>";

            if(files.size() > 0) {
            	email = (user.getExtraData2() == null) ? user.getEmail() : user.getExtraData2();
            	if(!Util.empty(email)) {
                    boolean sended = false;
                    int count = 10;
                    while(!sended && count > 0) {
                    	try {
			                String[] senderAddress = sender.getAvailableMailAddress();
			                if(!Util.empty(senderAddress[0])) {
			                	sender.send(email,FROM_NAME,senderAddress[0],senderAddress[1],emailTitle,body,files);
			                	sended = true;
			                }
                    	}catch (Exception e) {
    						e.printStackTrace();
    					}
    		            count--;
                    }
            	}
            }
            
            if(doFeedback) {
	            Iterator it = sys.getAssesmentReportFacade().getAssesmentFeedback(assesment.getId(),userSessionData).iterator();
	            while(it.hasNext()) {
	                FeedbackData feedback = (FeedbackData)it.next();
	                Iterator reports = feedback.getReports().iterator();
	                body = "<table>";
	                body += "<tr><td>"+messages.getText("feedback.thanksmessage")+"</td><tr>";
	                body += "<tr><td><a href='www.cepasafedrive.com'>www.cepasafedrive.com</a></td><tr>";
	                body += "<tr><td><table width='100%'><tr>";            
	                body += "<td><b>"+messages.getText("assesment.reporthtml.footermessage1")+"</b></td>";
	                body += "</tr>";
            	    if(redirect != null) {
            	    	body += "<tr><td><i><span style=\"font-size: 10pt; color: rgb(102, 102, 102); font-family: 'Verdana','sans-serif';\">"+messages.getText("assesment.reporthtml.footermessage4")+"</span></i></td></tr>";
            	    	body += "<tr><td>&nbsp;</td></tr>";
            	    	body += "<tr><td><a href=\""+redirect+"\">"+redirect+"</a></td></tr>";
            	    	body += "<tr><td>&nbsp;</td></tr>";
            	    	body += "<tr><td><i><span style=\"font-size: 10pt; color: rgb(102, 102, 102); font-family: 'Verdana','sans-serif';\">"+messages.getText("assesment.reporthtml.footermessage5")+"</span></i><a href=\"mailto:indesa@cepasafedrive.com\">indesa@cepasafedrive.com</a></td></tr>";
            	    	body += "<tr><td>&nbsp;</td></tr>";
            	    }
	                body += "</table></td></tr>";                   
	                body += "<tr><td></td><tr>";
	                body += "</table>";
	                if(files.size() > 0) {
	                    boolean sended = false;
	                    int count = 10;
	                    while(!sended && count > 0) {
	                    	try {
				                String[] senderAddress = sender.getAvailableMailAddress();
				                if(!Util.empty(senderAddress[0])) {
				                	sender.send(feedback.getEmail(),FROM_NAME,senderAddress[0],senderAddress[1],emailTitle,body,files);
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
	       	Integer dc5corporationId = 4;
       	
           	UserElearning userElearning = new UserElearning(Util.formatDateElearning(user.getBirthDate()),user.getCountry(),
       			user.getEmail(), user.getFirstName(), user.getLanguage(),
       			user.getLastName(),sex, dc5corporationId);
           	userToRegister.setUser(userElearning);
           
           	// Lecciones que tomar√° el usuario:
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
