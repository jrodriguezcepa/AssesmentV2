/**
 * Created on 20-nov-2008
 * CEPA
 * DataCenter 5
 */
package assesment.presentation.actions.administration;

import java.io.ByteArrayInputStream;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONArray;
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
import assesment.communication.exception.InvalidDataException;
import assesment.communication.language.Text;
import assesment.communication.module.ModuleData;
import assesment.communication.question.QuestionData;
import assesment.communication.user.UserData;
import assesment.communication.util.CountryConstants;
import assesment.communication.util.MailSender;
import assesment.presentation.translator.web.util.Util;

public class AnswerUtilNewHire extends AnswerUtil {

    public AnswerUtilNewHire() {
        super();
    }

    public int[] saveAnswers(AssesmentAccess sys,String answers,boolean feedback) throws InvalidDataException {
        String moduleId = "";
        try {
            String user = sys.getUserSessionData().getFilter().getLoginName();
            UserSessionData userSessionData = sys.getUserSessionData();
            
            Integer assesment = userSessionData.getFilter().getAssesment();
            
            Collection<UserAnswerData> answerList = new LinkedList<UserAnswerData>();
    
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            docBuilderFactory.setNamespaceAware(true);
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            ByteArrayInputStream is = new ByteArrayInputStream(answers.getBytes());
            Document doc = docBuilder.parse(is);
            Node node = doc.getFirstChild();
            
            moduleId = node.getAttributes().getNamedItem("id").getNodeValue();
    
            ModuleData moduleData = sys.getModuleReportFacade().findModule(new Integer(moduleId),userSessionData);
            
            NodeList nodeList = node.getChildNodes();
            int newHire = 0;
            for(int i = 0; i < nodeList.getLength(); i++) {
                Node questionNode = nodeList.item(i);
                String questionId = questionNode.getAttributes().getNamedItem("id").getNodeValue();
                QuestionData questionData = getQuestionData(new Integer(questionId),moduleData);
                if(questionData.getType().intValue() == QuestionData.COUNTRY) {
                    Node valueNode = questionNode.getFirstChild();
                    UserAnswerData data = new UserAnswerData(questionData.getId());
                    data.setCountry(new Integer(valueNode.getFirstChild().getNodeValue()));
                    answerList.add(data);
                }else if(questionData.getType().intValue() == QuestionData.DATE) {
                    Node valueNode = questionNode.getFirstChild();
                    answerList.add(new UserAnswerData(questionData.getId(),getDate(valueNode.getFirstChild())));
                }else if(questionData.getType().intValue() == QuestionData.EMAIL) {
                    Node valueNode = questionNode.getFirstChild();
                    answerList.add(new UserAnswerData(questionData.getId(),valueNode.getFirstChild().getNodeValue()));
                }else if(questionData.getType().intValue() == QuestionData.EXCLUDED_OPTIONS
                        || questionData.getType().intValue() == QuestionData.LIST) {
                    Node valueNode = questionNode.getFirstChild();
                    Integer answer = new Integer(valueNode.getFirstChild().getNodeValue());
                    answerList.add(new UserAnswerData(questionData.getId(),answer));

                    newHire += getValue(questionId,answer,assesment);
//      Puntaje para newHire                    
                    
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
                }else if(questionData.getType().intValue() == QuestionData.TEXT) {
                    Node valueNode = questionNode.getFirstChild();
                    answerList.add(new UserAnswerData(questionData.getId(),valueNode.getFirstChild().getNodeValue()));
                }
            }
            
            int[] resultValues = {Integer.parseInt(moduleId),0,0};
            int[] answerValues = sys.getUserABMFacade().saveModuleAnswers(user,assesment,answerList,new Integer(newHire),(moduleData.getType().intValue() == ModuleData.PERSONAL_DATA),feedback,userSessionData);
            if(feedback) {
                resultValues[1] = answerValues[0]; 
                resultValues[2] = answerValues[1];
            }
            return resultValues;
        }catch(Exception e) {
            throw new InvalidDataException(moduleId,moduleId);
        }
    }


    public Object[] saveAnswers(AssesmentAccess sys,String moduleId,HashMap answers,boolean feedback) throws InvalidDataException {
        try {
            String user = sys.getUserSessionData().getFilter().getLoginName();
            UserSessionData userSessionData = sys.getUserSessionData();
            Text messages = sys.getText();
            
            AssesmentData assesment = userSessionData.getFilter().getAssessmentData();
            Collection<UserAnswerData> answerList = new LinkedList<UserAnswerData>();
    
            boolean psico = moduleId.equals(String.valueOf(ModuleData.PSICO)); 
            Collection<String> valids = new LinkedList<String>();
            Collection<String[]> errors = new LinkedList<String[]>();
            
    
            ModuleData moduleData = null;

            UsReportFacade usReport = sys.getUserReportFacade();
            int newHire = 0;
            if(psico) {
                if(usReport.isPsicologicDone(user,assesment.getId(),userSessionData)) {
                    String[] valid = (String[])valids.toArray(new String[0]);
                    String[][] error = (String[][])errors.toArray(new String[0][0]);
                    return  new Object[]{valid,error};
                }
                Iterator it = answers.keySet().iterator();
                while(it.hasNext()) {
                	Integer questionId = (Integer)it.next();
                	String value = (String)answers.get(questionId);
                	valids.add(questionId.toString());
                    answerList.add(new UserAnswerData(questionId,new Integer(value)));
                }
                sys.getUserABMFacade().savePsicoAnswers(user,assesment.getId(),answerList,userSessionData);
                if(usReport.isPsicologicDone(user,assesment.getId(),userSessionData)) {
                    int[] psicoResults = null;
                    int[] questionIds = null;
                    String[][] answerIds = null;
                    int psicoId = -1;
                    int testId = -1;
                    psicoResults = new int[48];
                    questionIds = new int[48];
                    answerIds = new String[48][3];
                    moduleData = sys.getModuleReportFacade().getPsicoModule(userSessionData);
                    psicoId = createUser(sys);
                    if(psicoId > 0) {
                        String[] personalData = sys.getQuestionReportFacade().getAgenSex(user,assesment.getId(),userSessionData);
                        testId = createTest(psicoId,sys,personalData[1],personalData[0]);
                        if(testId > 0) {
                            String content = readURL("http://www.psicologiatotal.com/wspsi2.php?xf=LeerTest&test_id="+testId);
                            DocumentBuilderFactory psiDocBuilderFactory = DocumentBuilderFactory.newInstance();
                            psiDocBuilderFactory.setNamespaceAware(true);
                            DocumentBuilder psiDocBuilder = psiDocBuilderFactory.newDocumentBuilder();
                            ByteArrayInputStream isPSI = new ByteArrayInputStream(content.getBytes());
                            Document docPSI = psiDocBuilder.parse(isPSI);
                            NodeList list = docPSI.getFirstChild().getChildNodes();
                            int i = 0;
                            while(i < list.getLength()) {
                                Node nodePSI = list.item(i);
                                if(nodePSI.getNodeName().startsWith("pregunta")) {
                                    int index = Integer.parseInt(nodePSI.getNodeName().substring(9));
                                    questionIds[Integer.parseInt(nodePSI.getFirstChild().getNodeValue())-1] = index;
                                }else if(nodePSI.getNodeName().startsWith("respuesta")) {
                                    int indexX = Integer.parseInt(nodePSI.getNodeName().substring(10));
                                    NodeList list2 = nodePSI.getChildNodes();
                                    int j = 0;
                                    while(j < list2.getLength()) {
                                        Node node2 = list2.item(j);
                                        int indexY = Integer.parseInt(node2.getNodeName().substring(1));
                                        answerIds[indexX][indexY] = node2.getFirstChild().getNodeValue(); 
                                        j++;
                                    }
                                }
                                i++;
                            }
                                
                        }
                    }
                	HashMap psiAnswers = usReport.findPsicoModuleResult(user,assesment.getId(),userSessionData);
                	Iterator answerIt = psiAnswers.keySet().iterator();
                	while(answerIt.hasNext()) {
                		Integer key = (Integer)answerIt.next();
                		UserAnswerData answerData = (UserAnswerData)psiAnswers.get(key);
                        int qIndex = questionIds[answerData.getQuestion().intValue()-1];
                        psicoResults[qIndex] = getAnswer(answerData.getAnswer(),answerIds[qIndex]);
                	}
                    int executedId = executeTest(testId,psicoResults);
                    int[] values = null;
                    if(executedId > 0) {
                        int evaluateId = evaluateTest(executedId);
                        values = getValues(evaluateId);
                    }
	                sys.getUserABMFacade().saveModuleAnswers(user,assesment.getId(),answerList,psicoId,testId,userSessionData,psico,values,feedback);
                }
            }else {
        		CountryConstants cc = new CountryConstants();
                moduleData = assesment.findModule(new Integer(moduleId));
            
	            Iterator it = answers.keySet().iterator();
	            while(it.hasNext()) {
	            	Integer questionId = (Integer)it.next();
	            	String value = (String)answers.get(questionId);
	                QuestionData questionData = getQuestionData(new Integer(questionId),moduleData);
	                if(questionData.getType().intValue() == QuestionData.COUNTRY) {
	                    UserAnswerData data = new UserAnswerData(questionData.getId());
	                    if(cc.exists(value)) {
	                    	data.setCountry(new Integer(value));
	                    	valids.add(questionId.toString());
	                    	answerList.add(data);
	                    }else {
	                    	errors.add(new String[]{questionId.toString(),messages.getText("assessment.errors.wrongcountry")});
	                    }
	                }else if(questionData.getType().intValue() == QuestionData.DATE || questionData.getType().intValue() == QuestionData.BIRTHDATE) {
	                	Date dateValue = formatDateGuion(value);
	                	if(dateValue != null) {
	                		if(questionData.getType().intValue() == QuestionData.BIRTHDATE) {
	                			if(isAdult(dateValue)) {
		                			valids.add(questionId.toString());
		                			answerList.add(new UserAnswerData(questionData.getId(),dateValue));
	                			}else {
	    	                    	errors.add(new String[]{questionId.toString(),messages.getText("assessment.errors.wrongbirthdate")});
	    	                	}
	                		}else { 
	                			valids.add(questionId.toString());
	                			answerList.add(new UserAnswerData(questionData.getId(),dateValue));
	                		}
	                	}else {
	                    	errors.add(new String[]{questionId.toString(),messages.getText("assessment.errors.wrongdate")});
	                	}
	                }else if(questionData.getType().intValue() == QuestionData.EMAIL) {
	                	if(Util.checkEmail(value)) {
		                	valids.add(questionId.toString());
		                    answerList.add(new UserAnswerData(questionData.getId(),value));
	                	}else {
	                    	errors.add(new String[]{questionId.toString(),messages.getText("assessment.errors.wrongemail")});
	                	}
	                }else if(questionData.getType().intValue() == QuestionData.EXCLUDED_OPTIONS
	                        || questionData.getType().intValue() == QuestionData.LIST) {
	                	if(questionData.containsAnswer(value)) {
	                		valids.add(questionId.toString());
	                		answerList.add(new UserAnswerData(questionData.getId(),new Integer(value)));
	                        newHire += getValue(String.valueOf(questionId),new Integer(value),assesment.getId());
	                	}else {
	                    	errors.add(new String[]{questionId.toString(),messages.getText("assessment.errors.wronganswer")});
	                	}
	                }else if(questionData.getType().intValue() == QuestionData.INCLUDED_OPTIONS) {
	                	valids.add(questionId.toString());
	                	JSONArray answerArray = new JSONArray(value);
	                    Collection<Integer> options = new LinkedList<Integer>();
	                    boolean error = false;
	                	for(int i = 0; i < answerArray.length() && !error; i++) {
	                		String answerValue = (String)answerArray.get(i);
		                	if(questionData.containsAnswer(answerValue)) {
		                		options.add(new Integer(answerValue));
		                	}else {
		                		error = true;
			                    errors.add(new String[]{questionId.toString(),messages.getText("assessment.errors.wronganswer")});
			                }
	                    }
	                	if(!error)
	                		answerList.add(new UserAnswerData(questionData.getId(),options));
	                }else if(questionData.getType().intValue() == QuestionData.KILOMETERS) {
	                	Integer[] values = getDistanceValues(value);
	                	if(values != null) {
		                    UserAnswerData data = new UserAnswerData(questionData.getId());
		                    data.setText(values[0].toString());
	                        data.setDistance(values[1]);
		                	valids.add(questionId.toString());
		                    answerList.add(data);
	                    }else {
	                    	errors.add(new String[]{questionId.toString(),messages.getText("assessment.errors.wrongdistance")});
	                    }
	                }else if(questionData.getType().intValue() == QuestionData.OPTIONAL_DATE) {
	                    if(value.equals("0")) {
	                        answerList.add(new UserAnswerData(questionData.getId(),true));
	                    }else {
		                	Date dateValue = formatDateGuion(value);
		                	if(dateValue != null) {
		                		answerList.add(new UserAnswerData(questionData.getId(),dateValue));
		                	}else {
		                    	errors.add(new String[]{questionId.toString(),messages.getText("assessment.errors.wrongdate")});
		                	}
	                    }
	                	valids.add(questionId.toString());
	                }else if(questionData.getType().intValue() == QuestionData.TEXT || questionData.getType().intValue() == QuestionData.TEXTAREA) {
	                	valids.add(questionId.toString());
	                    answerList.add(new UserAnswerData(questionData.getId(),value));
	                }
	            }
	            sys.getUserABMFacade().saveModuleAnswers(user,assesment.getId(),answerList,0,0,userSessionData,false,null,feedback,newHire);

            }

            String[] valid = (String[])valids.toArray(new String[0]);
            String[][] error = (String[][])errors.toArray(new String[0][0]);
            return  new Object[]{valid,error};
        }catch(Exception e) {
        	throw new InvalidDataException(moduleId,moduleId);
        }
    }

    private int getValue(String questionId, Integer answer, Integer assesment) {
    	int newHire = 0;
		if(assesment.intValue() == AssesmentData.MONSANTO_NEW_HIRE) {
            switch(Integer.parseInt(questionId)) {
            	case 1407: //edad
            		switch(answer) {
	                    case 5607: 
	                        newHire += 4;
	                        break;
	                    case 5608: 
	                        newHire += 3;
	                        break;
	                    case 5609: 
	                        newHire += 2;
	                        break;
	                    case 5610: 
	                        newHire += 1;
	                        break;
	                }
	                break;
	            case 1370: // experiencia como conductor
	                switch(answer) {
	                    case 5354: 
	                        newHire += 5;
	                        break;
	                    case 5364: 
	                        newHire += 4;
	                        break;
	                    case 5363: 
	                        newHire += 3;
	                        break;
	                    case 5355: 
	                        newHire += 2;
	                        break;
	                    case 5360: 
	                        newHire += 1;
	                        break;
	                }
	                break;
	            case 1371:  // millas que recorre anualmente
	                switch(answer) {
	                    case 5615: 
	                        newHire += 1;
	                        break;
	                    case 5616: 
	                        newHire += 2;
	                        break;
	                    case 5617: 
	                        newHire += 3;
	                        break;
	                    case 5618: 
	                        newHire += 4;
	                        break;
	                    case 5619: 
	                        newHire += 5;
	                        break;
	                }
	                break;
	            case 1373: // cuso de manejo avanzado
	                switch(answer) {
	                    case 5379: 
	                        newHire += 2;   
	                        break;
	                    case 5382: 
	                        newHire += -2;
	                        break;
	                }
	                break;
	            case 1377: //  licencia suspendida
	                if(answer == 5387) {
	                    newHire += 3;
	                }
	                break;
	            case 1486: //  cantidad de multas en movimiento
	                switch(answer) {
	                    case 5771:
	                        newHire += 1;   
	                        break;
	                    case 5774:
	                        newHire += 2;   
	                        break;
	                    case 5770:
	                        newHire += 3;
	                        break;
	                    case 5775:
	                        newHire += 4;   
	                        break;
	                    case 5769:
	                        newHire += 5;
	                        break;
	                    case 5773:
	                        newHire += 6;   
	                        break;
	                }
	        }            
		}else if(assesment.intValue() == AssesmentData.NEW_HIRE) {
            switch(Integer.parseInt(questionId)) {
            	case 1841: //edad
            		switch(answer) {
		                case 7691: 
		                    newHire += 4;
		                    break;
		                case 7692: 
		                    newHire += 3;
		                    break;
		                case 7693: 
		                    newHire += 2;
		                    break;
		                case 7694: 
		                    newHire += 1;
		                    break;
	                }
	                break;
	            case 1854: // experiencia como conductor
	                switch(answer) {
	                    case 7773: 
	                        newHire += 5;
	                        break;
	                    case 7777: 
	                        newHire += 4;
	                        break;
	                    case 7776: 
	                        newHire += 3;
	                        break;
	                    case 7774: 
	                        newHire += 2;
	                        break;
	                    case 7775: 
	                        newHire += 1;
	                        break;
	                }
	                break;
	            case 1853:  // millas que recorre anualmente
	            	switch(answer) {
	                    case 7768: 
	                        newHire += 1;
	                        break;
	                    case 7769: 
	                        newHire += 2;
	                        break;
	                    case 7770: 
	                        newHire += 3;
	                        break;
	                    case 7771: 
	                        newHire += 4;
	                        break;
	                    case 7772: 
	                        newHire += 5;
	                        break;
	                }
	                break;
	            case 1851: // cuso de manejo avanzado
	                switch(answer) {
	                    case 7753: 
	                        newHire += 2;   
	                        break;
	                    case 7754: 
	                        newHire += -2;
	                        break;
	                }
	                break;
	            case 1849: //  licencia suspendida
	                if(answer == 7750) {
	                    newHire += 3;
	                }
	                break;
	            case 1848: //  cantidad de multas en movimiento
	                switch(answer) {
	                    case 7744:
	                        newHire += 1;   
	                        break;
	                    case 7747:
	                        newHire += 2;   
	                        break;
	                    case 7743:
	                        newHire += 3;
	                        break;
	                    case 7748:
	                        newHire += 4;   
	                        break;
	                    case 7742:
	                        newHire += 5;
	                        break;
	                    case 7746:
	                        newHire += 6;   
	                        break;
	                }
	        }            			
		}
		return newHire;
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
            Collection files = new LinkedList();
            
            String body = "<table>";
            body += "<tr><td>"+messages.getText("assesment.reporthtml.footermessage1")+"</td><tr>";
            body += "<tr><td>"+messages.getText("assesment.reporthtml.footermessage2")+"</td><tr>";

            String fileName = AssesmentData.FLASH_PATH+"/reports/new_hire_"+name+"_"+userSessionData.getLenguage()+".pdf";
            util.createNewHireReport(sys,fileName,assesment,user.getLoginName());
            files.add(fileName);
            
            body += "</table>";

            MailSender sender = new MailSender();
            boolean sended = false;
            int count = 10;
            while(!sended && count > 0) {
            	try {
		            String[] senderAddress = sender.getAvailableMailAddress();
		            if(!Util.empty(senderAddress[0])) {
		            	sender.send(user.getEmail(),FROM_NAME,senderAddress[0],senderAddress[1],messages.getText("assessment.reports")+" - "+name,body,files);
		            	sended = true;
		            }
            	}catch (Exception e) {
					e.printStackTrace();
				}
            	count--;
            }
            Iterator it = sys.getAssesmentReportFacade().getAssesmentFeedback(assesment.getId(),userSessionData).iterator();
            while(it.hasNext()) {
                FeedbackData feedback = (FeedbackData)it.next();
                if(files.size() > 0) {
                    sended = false;
                    count = 10;
                    while(!sended && count > 0) {
                    	try {
        		            String[] senderAddress = sender.getAvailableMailAddress();
        		            if(!Util.empty(senderAddress[0])) {
        		            	sender.send(feedback.getEmail(),FROM_NAME,senderAddress[0],senderAddress[1],messages.getText("assessment.reports")+" - "+name,body,files);
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

    public void resend(AssesmentAccess sys,AssesmentAttributes assesment, String login, String email) throws Exception {
        UsReportFacade userReport = sys.getUserReportFacade();
        Text messages = sys.getText();
        UserSessionData userSessionData = sys.getUserSessionData();
        assesment.presentation.actions.report.Util util = new assesment.presentation.actions.report.Util();
        UserData user = userReport.findUserByPrimaryKey(login,userSessionData);
        String name = user.getFirstName()+" "+user.getLastName();
        Collection files = new LinkedList();
        
        String body = "<table>";
        body += "<tr><td>"+messages.getText("assesment.reporthtml.footermessage1")+"</td><tr>";
        body += "<tr><td>"+messages.getText("assesment.reporthtml.footermessage2")+"</td><tr>";

        String fileName = AssesmentData.FLASH_PATH+"/reports/new_hire_"+name+"_"+userSessionData.getLenguage()+".pdf";
        util.createNewHireReport(sys,fileName,assesment,user.getLoginName());
        files.add(fileName);
        
        body += "</table>";
        sendEmail(user,assesment,sys,body,messages.getText("assessment.reports")+" - "+name,email,files,null);
    }
    
    public void newFeedback(AssesmentAccess sys,AssesmentData assesment, String login, String email, boolean doFeedback, String redirect) throws Exception {
        UsReportFacade userReport = sys.getUserReportFacade();
        UserSessionData userSessionData = sys.getUserSessionData();
        assesment.presentation.actions.report.Util util = new assesment.presentation.actions.report.Util();
        if(userReport.isAssessmentDone(login,assesment.getId(),userSessionData,assesment.isPsitest())) {
            UserData user = userReport.findUserByPrimaryKey(login,userSessionData);
            String name = user.getFirstName()+" "+user.getLastName();

            Collection<String> files = new LinkedList<String>();
            String fileName = AssesmentData.FLASH_PATH+"/reports/new_hire_"+name+"_"+userSessionData.getLenguage()+".pdf";
            util.createNewHireReport(sys,fileName,assesment,user.getLoginName());
            files.add(fileName);

            Collection<String> feedbacks = new LinkedList<String>();
			if(doFeedback) {
			    Iterator it = sys.getAssesmentReportFacade().getAssesmentFeedback(assesment.getId(),userSessionData).iterator();
			    while(it.hasNext()) {
			        feedbacks.add(((FeedbackData)it.next()).getEmail());
			    }
			}
			
            sendEmail(sys, assesment, login, email, doFeedback, user, files, files, redirect, feedbacks);
        }
    }

}
