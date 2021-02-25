/**
 * Created on 14-nov-2007
 * CEPA
 * DataCenter 5
 */
package assesment.presentation.actions.administration;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.postgresql.translation.messages_cs;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import assesment.business.AssesmentAccess;
import assesment.business.administration.user.UsReportFacade;
import assesment.communication.administration.UserAnswerData;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.assesment.FeedbackData;
import assesment.communication.corporation.CorporationData;
import assesment.communication.exception.InvalidDataException;
import assesment.communication.language.Text;
import assesment.communication.module.ModuleData;
import assesment.communication.question.QuestionData;
import assesment.communication.user.UserData;
import assesment.communication.util.MailSender;
import assesment.presentation.translator.web.util.Util;

public class AnswerUtilBasf extends AnswerUtil {

    protected static final String FROM_NAME = "CEPA - Driver Assessment";
    protected final String WEBSERVICE = "http://webservices.cepasafedrive.com/WebServices2/ElearningWSAssessment?wsdl";
    protected final String URL_REDIRECT = "http://elearning.cepasafedrive.com/loadAssesment.php";
    
    public AnswerUtilBasf() {
        super();
    }

    public int[] saveAnswers(AssesmentAccess sys,String answers,boolean feedback) throws InvalidDataException {
        String moduleId = "";
        try {
            String user = sys.getUserSessionData().getFilter().getLoginName();
            UserSessionData userSessionData = sys.getUserSessionData();
            Text messages = sys.getText();
            
            UserData userData = sys.getUserReportFacade().findUserByPrimaryKey(user, userSessionData);
            Integer assesment = userSessionData.getFilter().getAssesment();
            
            Collection<UserAnswerData> answerList = new LinkedList<UserAnswerData>();
    
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            docBuilderFactory.setNamespaceAware(true);
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            ByteArrayInputStream is = new ByteArrayInputStream(answers.getBytes());
            Document doc = docBuilder.parse(is);
            Node node = doc.getFirstChild();
            
            moduleId = node.getAttributes().getNamedItem("id").getNodeValue();
    
            int[] psicoResults = null;
            int[] questionIds = null;
            String[][] answerIds = null;
            int psicoId = -1;
            int testId = -1;
            ModuleData moduleData = sys.getModuleReportFacade().findModule(new Integer(moduleId),userSessionData);
            
            NodeList nodeList = node.getChildNodes();
            for(int i = 0; i < nodeList.getLength(); i++) {
                Node questionNode = nodeList.item(i);
                String questionId = questionNode.getAttributes().getNamedItem("id").getNodeValue();
                QuestionData questionData = getQuestionData(new Integer(questionId),moduleData);
                if(questionData.getType().intValue() == QuestionData.COUNTRY) {
                    Node valueNode = questionNode.getFirstChild();
                    UserAnswerData data = new UserAnswerData(questionData.getId());
                    data.setCountry(new Integer(valueNode.getFirstChild().getNodeValue()));
                    answerList.add(data);
                }else if(questionData.getType().intValue() == QuestionData.DATE || questionData.getType().intValue() == QuestionData.BIRTHDATE) {
                    Node valueNode = questionNode.getFirstChild();
                    Date date = getDate(valueNode.getFirstChild());
                    answerList.add(new UserAnswerData(questionData.getId(),date));
                    userData.setBirthDate(date);
                }else if(questionData.getType().intValue() == QuestionData.EMAIL) {
                    Node valueNode = questionNode.getFirstChild();
                    answerList.add(new UserAnswerData(questionData.getId(),valueNode.getFirstChild().getNodeValue()));
                }else if(questionData.getType().intValue() == QuestionData.EXCLUDED_OPTIONS
                        || questionData.getType().intValue() == QuestionData.LIST) {
                    Node valueNode = questionNode.getFirstChild();
                    Integer answer = new Integer(valueNode.getFirstChild().getNodeValue());
                    answerList.add(new UserAnswerData(questionData.getId(),answer));
                }else if(questionData.getType().intValue() == QuestionData.INCLUDED_OPTIONS) {
                    NodeList answerListNodes = questionNode.getChildNodes();
                    Collection<Integer> options = new LinkedList<Integer>();
                    for(int j = 0; j < answerListNodes.getLength(); j++) {
                        Node component = answerListNodes.item(j);
                        options.add(new Integer(component.getFirstChild().getNodeValue()));
                    }
                    answerList.add(new UserAnswerData(questionData.getId(),options));
                }else if(questionData.getType().intValue() == QuestionData.KILOMETERS) {
                    UserAnswerData data = new UserAnswerData(questionData.getId());
                    NodeList answerListNodes = questionNode.getChildNodes();
                    for(int j = 0; j < answerListNodes.getLength(); j++) {
                        Node component = answerListNodes.item(j);
                        if(component.getNodeName().equals("units")) {
                            if(component.getFirstChild().getNodeValue().equals("k")) {
                                data.setDistance(new Integer(0));
                            }else {
                                data.setDistance(new Integer(1));
                            }
                        }
                        if(component.getNodeName().equals("answer")) {
                            data.setText(component.getFirstChild().getNodeValue());
                        }
                    }
                    answerList.add(data);
                }else if(questionData.getType().intValue() == QuestionData.OPTIONAL_DATE) {
                    Node valueNode = questionNode.getFirstChild();
                    if(valueNode.getFirstChild().getNodeValue().equals("0")) {
                        answerList.add(new UserAnswerData(questionData.getId(),true));
                    }else {
                        answerList.add(new UserAnswerData(questionData.getId(),getDate(valueNode.getFirstChild())));
                    }
                }else if(questionData.getType().intValue() == QuestionData.TEXT || questionData.getType().intValue() == QuestionData.TEXTAREA) {
                    Node valueNode = questionNode.getFirstChild();
                    String text = valueNode.getFirstChild().getNodeValue();
                    answerList.add(new UserAnswerData(questionData.getId(),text));
                    switch(questionData.getOrder()) {
	                	case 1:
	                		userData.setFirstName(text);
	                		break;
	                	case 2:
	                		userData.setLastName(text);
	                		break;
	                	case 4:
	                		userData.setExtraData(text);
	                		break;
	                	case 5:
	                		userData.setExtraData2(text);
	                		break;
	                	case 6:
	                		userData.setVehicle(text);
	                		break;
	                	case 7:
	                		userData.setExtraData3(text);
	                		break;
                    }
                }
            }
            
            int[] values = null;
            UserData existingUser = sys.getUserReportFacade().findBasfUser(userData.getExtraData(), userData.getExtraData2(), userSessionData);
            if(moduleData.getType().intValue() != ModuleData.PERSONAL_DATA  || existingUser == null) {
	            int[] resultValues = {Integer.parseInt(moduleId),0,0};
	            sys.getUserABMFacade().userUpdate(userData, userSessionData);
	            int[] answerValues = sys.getUserABMFacade().saveModuleAnswers(user,assesment,answerList,psicoId,testId,userSessionData,false,values,feedback);
	            if(feedback) {
	                resultValues[1] = answerValues[0]; 
	                resultValues[2] = answerValues[1];
	            }
	            return resultValues;
            }else {
            	throw new InvalidDataException(messages.getText("assessment.basferror.userrepeated"),moduleId);
            }
        }catch(Exception e) {
        	throw new InvalidDataException(moduleId,moduleId);
        }
    }


    public void feedback(AssesmentAccess sys,AssesmentAttributes assesment) throws Exception {
        UsReportFacade userReport = sys.getUserReportFacade();
        Text messages = sys.getText();
        UserSessionData userSessionData = sys.getUserSessionData();
        assesment.presentation.actions.report.Util util = new assesment.presentation.actions.report.Util();

        String login = userSessionData.getFilter().getLoginName();
        if(userReport.isAssessmentDone(login,assesment.getId(),userSessionData,assesment.isPsitest())) {
        	UserData user = userReport.findUserByPrimaryKey(login,userSessionData);
            MailSender sender = new MailSender();

            boolean quizGreen = userReport.isResultGreen(user.getLoginName(),assesment.getId(),userSessionData);
            
            String emailTitle = (quizGreen) ? "APTO" : "NÃO APTO";
            emailTitle += " - "+user.getFirstName()+" "+user.getLastName()+" - "+user.getVehicle()+" - "+user.getExtraData3()+" - "+user.getExtraData();
            String[] doorEmails = {"eferreira@cepasafedrive.com"};
            LinkedList cc = new LinkedList();
            cc.add("f.millan@cepasafedrive.com"); 
            cc.add("cecom.demarchi@basf.com"); 
            cc.add("portaria.3@basf.com"); 
            cc.add("eliezer.dantas@basf.com"); 
            cc.add("marcelo.biserra@basf.com"); 
            cc.add("fernando.franco@basf.com"); 
            cc.add("basfcepa@gmail.com");
            for(int i = 0; i < doorEmails.length; i++) {
                boolean sended = false;
                int count = 10;
                while(!sended && count > 0) {
                	try {
			            String[] senderAddress = sender.getAvailableMailAddress();
			            if(!Util.empty(senderAddress[0])) {
			            	//sender.send(doorEmails[i],cc,FROM_NAME,senderAddress[0],senderAddress[1],emailTitle,"",new LinkedList());
			            	sended = true;
			            }
                	}catch (Exception e) {
						e.printStackTrace();
					}
		            count--;
                }
            }
            String name = user.getFirstName()+" "+user.getLastName();

            String body = "<table>";
            Collection<String> files = new LinkedList<String>();
   /*             
            String fileName = AssesmentData.FLASH_PATH+"/reports/data_"+name+".pdf";
            util.createPersonalDataReport(sys,fileName,assesment);
            files.add(fileName);
*/
            String fileName = AssesmentData.FLASH_PATH+"/reports/resultado_"+name+".pdf";
            util.createUserReport(sys,fileName,assesment,user.getLoginName());
            files.add(fileName);

            fileName = AssesmentData.FLASH_PATH+"/reports/errores_"+name+".pdf";
            util.createUserErrorReport(sys,fileName,assesment,user.getLoginName());
            files.add(fileName);
            
            body += "<tr><td>"+messages.getText("feedback.thanksmessage")+"</td><tr>";
            body += "<tr><td><a href='www.cepasafedrive.com'>www.cepasafedrive.com</a></td><tr>";
            body += "<tr><td></td><tr>";
            body += "</table>";
	                
            String[] managerEmails = {"eferreira@cepasafedrive.com"};
            cc = new LinkedList();
            cc.add("f.millan@cepasafedrive.com"); 
            cc.add("fernando.franco@basf.com"); 
            cc.add("marcelo.biserra@basf.com");
            cc.add("thais.s.ribeiro@basf.com");
            cc.add("basfcepa@gmail.com");
            for(int i = 0; i < managerEmails.length; i++) {
                boolean sended = false;
                int count = 10;
                while(!sended && count > 0) {
                	try {
			            String[] senderAddress = sender.getAvailableMailAddress();
			            if(!Util.empty(senderAddress[0])) {
			            //	sender.send(managerEmails[i],cc,FROM_NAME,senderAddress[0],senderAddress[1],emailTitle,body,files);
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

    public void resend(AssesmentAccess sys,AssesmentAttributes assesment, String login, String email) throws Exception {
    	      
    	UsReportFacade userReport = sys.getUserReportFacade();
        Text messages = sys.getText();
        UserSessionData userSessionData = sys.getUserSessionData();
        assesment.presentation.actions.report.Util util = new assesment.presentation.actions.report.Util();

    	UserData user = userReport.findUserByPrimaryKey(login,userSessionData);
        MailSender sender = new MailSender();

        boolean quizGreen = userReport.isResultGreen(user.getLoginName(),assesment.getId(),userSessionData);
            
        String emailTitle = (quizGreen) ? "APTO" : "NÃO APTO";
        emailTitle += " - "+user.getFirstName()+" "+user.getLastName()+" - "+user.getVehicle()+" - "+user.getExtraData3()+" - "+user.getExtraData();
        
        if(email == null) {
        	String[] doorEmails = {"eferreira@cepasafedrive.com"};
            LinkedList cc = new LinkedList();
            cc.add("f.millan@cepasafedrive.com"); 
            cc.add("cecom.demarchi@basf.com"); 
            cc.add("portaria.3@basf.com"); 
            cc.add("eliezer.dantas@basf.com"); 
            cc.add("marcelo.biserra@basf.com"); 
            cc.add("fernando.franco@basf.com"); 
            cc.add("basfcepa@gmail.com");

            for(int i = 0; i < doorEmails.length; i++) {
                boolean sended = false;
                int count = 10;
                while(!sended && count > 0) {
                	try {
			            String[] senderAddress = sender.getAvailableMailAddress();
			            if(!Util.empty(senderAddress[0])) {
			//            	sender.send(doorEmails[i],cc,FROM_NAME,senderAddress[0],senderAddress[1],emailTitle,"",new LinkedList());
			            	sended = true;
			            }
                	}catch (Exception e) {
						e.printStackTrace();
					}
		            count--;
                }
            }
            
        }else {
            boolean sended = false;
            int count = 10;
            while(!sended && count > 0) {
            	try {
		            String[] senderAddress = sender.getAvailableMailAddress();
		            if(!Util.empty(senderAddress[0])) {
		      //      	sender.send(email,new LinkedList(),FROM_NAME,senderAddress[0],senderAddress[1],emailTitle,"",new LinkedList());
		            	sended = true;
		            }
            	}catch (Exception e) {
					e.printStackTrace();
				}
	            count--;
            }
        }
        
        String name = user.getFirstName()+" "+user.getLastName();

        String body = "<table>";
        Collection<String> files = new LinkedList<String>();

        String fileName = AssesmentData.FLASH_PATH+"/reports/resultado_"+name+".pdf";
        util.createUserReport(sys,fileName,assesment,user.getLoginName());
        files.add(fileName);

        fileName = AssesmentData.FLASH_PATH+"/reports/errores_"+name+".pdf";
        util.createUserErrorReport(sys,fileName,assesment,user.getLoginName());
        files.add(fileName);
        
        body += "<tr><td>"+messages.getText("feedback.thanksmessage")+"</td><tr>";
        body += "<tr><td><a href='www.cepasafedrive.com'>www.cepasafedrive.com</a></td><tr>";
        body += "<tr><td></td><tr>";
        body += "</table>";
                
        if(email == null) {
            String[] managerEmails = {"eferreira@cepasafedrive.com"};
            Collection cc = new LinkedList();
            cc.add("f.millan@cepasafedrive.com"); 
            cc.add("fernando.franco@basf.com"); 
            cc.add("marcelo.biserra@basf.com");
            cc.add("thais.s.ribeiro@basf.com");
            cc.add("basfcepa@gmail.com");
            for(int i = 0; i < managerEmails.length; i++) {
                boolean sended = false;
                int count = 10;
                while(!sended && count > 0) {
                	try {
			            String[] senderAddress = sender.getAvailableMailAddress();
			            if(!Util.empty(senderAddress[0])) {
			//            	sender.send(managerEmails[i],cc,FROM_NAME,senderAddress[0],senderAddress[1],emailTitle,body,files);
			            	sended = true;
			            }
                	}catch (Exception e) {
						e.printStackTrace();
					}
		            count--;
                }
            }
        }else {
            boolean sended = false;
            int count = 10;
            while(!sended && count > 0) {
            	try {
		            String[] senderAddress = sender.getAvailableMailAddress();
		            if(!Util.empty(senderAddress[0])) {
		//            	sender.send(email,new LinkedList(),FROM_NAME,senderAddress[0],senderAddress[1],emailTitle,body,files);
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
