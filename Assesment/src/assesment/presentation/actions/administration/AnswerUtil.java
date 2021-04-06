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
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.ws.BindingProvider;

import org.json.JSONArray;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import assesment.business.AssesmentAccess;
import assesment.business.administration.user.UsABMFacade;
import assesment.business.administration.user.UsABMFacadeBean;
import assesment.business.administration.user.UsReportFacade;
import assesment.communication.administration.SpecificAnswerData;
import assesment.communication.administration.UserAnswerData;
import assesment.communication.administration.UserMultiAssessmentData;
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
import assesment.communication.util.CountryConstants;
import assesment.communication.util.MailSender;
import assesment.communication.ws.Identification;
import assesment.communication.ws.UserElearning;
import assesment.communication.ws.UserToRegister;
import assesment.persistence.user.UsReport;
import assesment.presentation.translator.web.util.Util;
import ws.elearning.assessment.ElearningWSAssessment;
import ws.elearning.assessment.ElearningWSAssessmentService;

public class AnswerUtil {

    protected static final String FROM_NAME = "CEPA - Driver Assessment";
    protected final String WEBSERVICE = "http://webservices.cepasafedrive.com/WebServices2/ElearningWSAssessment?wsdl";
    protected final String URL_REDIRECT = "http://elearning.cepasafedrive.com/loadAssesment.php";
    
    public AnswerUtil() {
        super();
    }

    public static AnswerUtil getInstance(AssesmentData assesment) {
        AnswerUtil answerUtil = null;
        if(assesment.getCorporationId() == CorporationData.BASF) {
        	answerUtil = new AnswerUtilBasf();
        }else {
 	        switch(assesment.getId().intValue()) {
 	            case AssesmentData.MONSANTO_NEW_HIRE: case AssesmentData.NEW_HIRE:
 	                answerUtil = new AnswerUtilNewHire();
 	                break;
 	            case AssesmentData.JJ: case AssesmentData.JJ_2: case AssesmentData.JJ_3:  
 	            case AssesmentData.JJ_4:  case AssesmentData.JJ_5: case AssesmentData.JJ_6:  
 	            case AssesmentData.JJ_7: case AssesmentData.JJ_8:
 	                answerUtil = new AnswerUtilJJ();
 	                break;
 	            case AssesmentData.JANSSEN: case AssesmentData.JANSSEN_2:
 	                answerUtil = new AnswerUtilJanssen();
 	                break;
 	            case AssesmentData.FACEBOOK:
 	                answerUtil = new AnswerUtilFacebook();
 	                break;
 	            case AssesmentData.ABITAB:
 	                answerUtil = new AnswerUtilAbitab();
 	                break;
 	            case AssesmentData.MICHELIN: 
 	            case AssesmentData.MICHELIN_3:
 	            case AssesmentData.MICHELIN_5:
 	            case AssesmentData.MICHELIN_6:
 	            case AssesmentData.MICHELIN_7:
 	            case AssesmentData.MICHELIN_8:
 	            case AssesmentData.MICHELIN_9:
 	                answerUtil = new AnswerUtilMichelin();
 	                break;
 	            case AssesmentData.MONSANTO_BRAZIL: case AssesmentData.MONSANTO_ARGENTINA:
 	                answerUtil = new AnswerUtilMonsantoBR();
 	                break;
 	            case AssesmentData.PEPSICO: case AssesmentData.PEPSICO_CANDIDATOS: case AssesmentData.PEPSICO_CEPA_SYSTEM:  
 	                answerUtil = new AnswerUtilPepsico();
 	                break;
 	            case AssesmentData.MONSANTO_LAN: case AssesmentData.MAMUT_ANDINO:
 	            case AssesmentData.NALCO: case AssesmentData.DNB: case AssesmentData.TRANSMETA:
 	                answerUtil = new AnswerUtilMonsantoLAN();
 	                break;
 	            case AssesmentData.SCHERING:
 	                answerUtil = new AnswerUtilSchering();
 	                break;
 	            case AssesmentData.GM:
 	                answerUtil = new AnswerUtilGM();
 	                break;
 	            case AssesmentData.ACU:
 	                answerUtil = new AnswerUtilACU();
 	                break;
 	            case AssesmentData.DOW:
 	                answerUtil = new AnswerUtilDOW();
 	                break;
 	            case AssesmentData.IMESEVI:
 	                answerUtil = new AnswerUtilImesevi();
 	                break;
 	            case AssesmentData.ASTRAZENECA: 
 	            case AssesmentData.ASTRAZENECA_2:
 	            case AssesmentData.ASTRAZENECA_2013:
 	                answerUtil = new AnswerUtilAZ();
 	                break;
 	            case AssesmentData.NYCOMED:
 	                answerUtil = new AnswerUtilNycomed();
 	                break;
 	            case AssesmentData.ANGLO: case AssesmentData.ANGLO_3:
 	                answerUtil = new AnswerUtilAnglo();
 	                break;
 	            case AssesmentData.SANOFI_BRASIL:
 	                answerUtil = new AnswerUtilSanofi();
 	                break;
 	            case AssesmentData.COKE_DEUTSCHLAND:
 	                answerUtil = new AnswerUtilCoke();
 	                break;
 	            case AssesmentData.SURVEY:
 	                answerUtil = new AnswerUtilSurvey();
 	                break;
 	            case AssesmentData.DOW_TOJ:
 	                answerUtil = new AnswerUtilDOWTOJ();
 	                break;
 	            case AssesmentData.NEW_HIRE_V2:
 	                answerUtil = new AnswerUtilNewHire2();
 	                break;
 	            case AssesmentData.MONSANTO_SURVEY_BR_2016:
 	                answerUtil = new AnswerUtilMonsantoSurvey();
 	                break;
 	            case AssesmentData.BAYERMX_ELEARNINGPACK_V2: case AssesmentData.BAYERMX_ELEARNINGPACK_V2_REPETICION:
 	            case AssesmentData.BAYERMX_ELEARNINGPACK_VERSION2: case AssesmentData.BAYERMX_ELEARNINGPACK_VERSION2_REPETICION:
 	            case AssesmentData.BAYERMX_ELEARNINGPACK_MONITORES:
 	                answerUtil = new AnswerUtilElearningPackBayerMX();
 	                break;
 	            default:
 	                answerUtil = new AnswerUtil();
 	        }
        }
        return answerUtil;
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
            boolean psico = moduleId.equals(String.valueOf(ModuleData.PSICO)); 
            
            if((psico && sys.getUserReportFacade().isPsicologicDone(user,assesment,userSessionData)) || sys.getUserReportFacade().hasResults(user,new Integer(moduleId),userSessionData)) {
                int[] resultValues = {Integer.parseInt(moduleId),-1,-1};
                return resultValues;
            }
    
            ModuleData moduleData = null;
            int[] psicoResults = null;
            int[] questionIds = null;
            String[][] answerIds = null;
            int psicoId = -1;
            int testId = -1;
            if(psico) {
                psicoResults = new int[48];
                questionIds = new int[48];
                answerIds = new String[48][3];
                moduleData = sys.getModuleReportFacade().getPsicoModule(userSessionData);
                psicoId = createUser(sys);
                if(psicoId > 0) {
                    String[] personalData = sys.getQuestionReportFacade().getAgenSex(user,assesment,userSessionData);
                    if(personalData[0] == null || Util.isNumber(personalData[0])) {
                    	personalData[0] = "1";
                    }
                    try {
                    	int age = Integer.parseInt(personalData[1]); 
                    	if(age > 85 || age < 18) {
                    		personalData[1] = "30";
                    	}
                    }catch (Exception e) {
                		personalData[1] = "30";
					}
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
            }else {
                moduleData = sys.getModuleReportFacade().findModule(new Integer(moduleId),userSessionData);
            }
            
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
                    answerList.add(new UserAnswerData(questionData.getId(),getDate(valueNode.getFirstChild())));
                }else if(questionData.getType().intValue() == QuestionData.EMAIL) {
                    Node valueNode = questionNode.getFirstChild();
                    answerList.add(new UserAnswerData(questionData.getId(),valueNode.getFirstChild().getNodeValue()));
                }else if(questionData.getType().intValue() == QuestionData.EXCLUDED_OPTIONS
                        || questionData.getType().intValue() == QuestionData.LIST) {
                    Node valueNode = questionNode.getFirstChild();
                    Integer answer = new Integer(valueNode.getFirstChild().getNodeValue());
                    answerList.add(new UserAnswerData(questionData.getId(),answer));
                    if(psico) {
                        int qIndex = questionIds[questionData.getId().intValue()-1];
                        psicoResults[qIndex] = getAnswer(answer,answerIds[qIndex]);
                    }
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
                    answerList.add(new UserAnswerData(questionData.getId(),valueNode.getFirstChild().getNodeValue()));
                }
            }
            
            int[] values = null;
            if(psico) {
                int executedId = executeTest(testId,psicoResults);
                if(executedId > 0) {
                    int evaluateId = evaluateTest(executedId);
                    if(evaluateId > -1) {
                    	values = getValues(evaluateId);
                    }else {
                    	values = new int[]{1,1,1,1,1,1};
                    }
                }
            }
            int[] resultValues = {Integer.parseInt(moduleId),0,0};
            int[] answerValues = sys.getUserABMFacade().saveModuleAnswers(user,assesment,answerList,psicoId,testId,userSessionData,psico,values,feedback);
            if(feedback) {
                resultValues[1] = answerValues[0]; 
                resultValues[2] = answerValues[1];
            }
            return resultValues;
        }catch(Exception e) {
        	throw new InvalidDataException(moduleId,moduleId);
        }
    }

    
    public void saveAnswers(AssesmentAccess sys,String answers,ModuleData moduleData,int[] questionIds,String[][] answerIds, int psicoId,int testId) throws Exception {
        Text messages = sys.getText();
        
        String user = sys.getUserSessionData().getFilter().getLoginName();
        Integer assesment = sys.getUserSessionData().getFilter().getAssesment();
        AssesmentAttributes assesmentAtt = sys.getAssesmentReportFacade().findAssesmentAttributes(assesment,sys.getUserSessionData());

        Collection<UserAnswerData> answerList = new LinkedList<UserAnswerData>();
        
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        docBuilderFactory.setNamespaceAware(true);
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        ByteArrayInputStream is = new ByteArrayInputStream(answers.getBytes());
        Document doc = docBuilder.parse(is);
        Node node = doc.getFirstChild();
        
        String moduleId = node.getAttributes().getNamedItem("id").getNodeValue();
        boolean psico = moduleId.equals(String.valueOf(ModuleData.PSICO)); 
        int[] psicoResults = new int[48];

        NodeList nodeList = node.getChildNodes();
        for(int i = 0; i < nodeList.getLength(); i++) {
            Node questionNode = nodeList.item(i);
            String questionId = questionNode.getAttributes().getNamedItem("id").getNodeValue();
            QuestionData questionData = getQuestionData(new Integer(questionId),moduleData);
            if(questionData.getType().intValue() == QuestionData.COUNTRY) {
                Node valueNode = questionNode.getFirstChild();
                if(!Util.isNumber(valueNode.getFirstChild().getNodeValue())) {
                    throw new InvalidDataException("Msg",messages.getText("test.result.invalidnumber")+messages.getText(questionData.getKey()));
                }
                UserAnswerData data = new UserAnswerData(questionData.getId());
                data.setCountry(new Integer(valueNode.getFirstChild().getNodeValue()));
                answerList.add(data);
            }else if(questionData.getType().intValue() == QuestionData.DATE || questionData.getType().intValue() == QuestionData.BIRTHDATE) {
                Node valueNode = questionNode.getFirstChild();
                answerList.add(new UserAnswerData(questionData.getId(),getDate(valueNode.getFirstChild())));
            }else if(questionData.getType().intValue() == QuestionData.EMAIL) {
                Node valueNode = questionNode.getFirstChild();
                if(!Util.checkEmail(valueNode.getFirstChild().getNodeValue())) {
                    throw new InvalidDataException("Msg",messages.getText("test.result.invalidemail")+messages.getText(questionData.getKey()));
                }
                answerList.add(new UserAnswerData(questionData.getId(),valueNode.getFirstChild().getNodeValue()));
            }else if(questionData.getType().intValue() == QuestionData.EXCLUDED_OPTIONS
                    || questionData.getType().intValue() == QuestionData.LIST) {
                Node valueNode = questionNode.getFirstChild();
                Integer answer = new Integer(valueNode.getFirstChild().getNodeValue());
                answerList.add(new UserAnswerData(questionData.getId(),answer));
                if(psico) {
                    int qIndex = questionIds[questionData.getId().intValue()-1];
                    psicoResults[qIndex] = getAnswer(answer,answerIds[qIndex]);
                }
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
                        data.setDistance(new Integer(component.getFirstChild().getNodeValue()));
                    }
                    if(component.getNodeName().equals("answer")) {
                        data.setText(component.getFirstChild().getNodeValue());
                    }
                    answerList.add(data);
                }
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
        
        int[] values = null;
        if(psico) {
            int executedId = executeTest(testId,psicoResults);
            if(executedId > 0) {
                int evaluateId = evaluateTest(executedId);
                if(evaluateId > -1) {
                	values = getValues(evaluateId);
                }else {
                	values = new int[]{1,1,1,1,1,1};
                }
            }
        }
        sys.getUserABMFacade().saveModuleAnswers(user,assesment,answerList,psicoId,testId,sys.getUserSessionData(),psico,values,assesmentAtt.isInstantFeedback());
    }

    protected int getAnswer(Integer answer, String[] values) {
        switch(answer.intValue() % 3) {
            case 1:
                if(values[0].equals("A")) {
                    return 0;
                }else if(values[1].equals("A")) {
                    return 1;
                }else {
                    return 2;
                }
            case 2:
                if(values[0].equals("B")) {
                    return 0;
                }else if(values[1].equals("B")) {
                    return 1;
                }else {
                    return 2;
                }
            case 0:
                if(values[0].equals("C")) {
                    return 0;
                }else if(values[1].equals("C")) {
                    return 1;
                }else {
                    return 2;
                }
        }
        return 0;
    }

    protected int[] getValues(int evaluateId) throws Exception {
        String content = readURL("http://www.psicologiatotal.com/wspsi2.php?xf=LeerEvaluacion&test_id="+evaluateId);
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        docBuilderFactory.setNamespaceAware(true);
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        ByteArrayInputStream is = new ByteArrayInputStream(content.getBytes());
        Document doc = docBuilder.parse(is);
        NodeList list = doc.getFirstChild().getChildNodes();
        int i = 0;
        boolean find = false;
        int[] values = new int[6];
        while(i < list.getLength() && !find) {
            Node node = list.item(i);
            if(node.getNodeName().equals("puntaje")) {
                find = true;
                NodeList list2 = node.getChildNodes();
                int j = 0;
                while(j < list2.getLength()) {
                    Node node2 = list2.item(j);
                    if(node2.getNodeName().startsWith("pr")) {
                        int index = Integer.parseInt(node2.getNodeName().substring(2)) - 1;
                        values[index] = Integer.parseInt(node2.getFirstChild().getNodeValue());
                    }
                    j++;
                }
            }
            i++;
        }
        return values;
    }

    protected int evaluateTest(int executedId) throws Exception {
    	try {
	        String content = readURL("http://www.psicologiatotal.com/wspsi2.php?xf=EvaluarTest&test_id="+executedId);
	        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	        docBuilderFactory.setNamespaceAware(true);
	        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
	        ByteArrayInputStream is = new ByteArrayInputStream(content.getBytes());
	        Document doc = docBuilder.parse(is);
	        return Integer.parseInt(doc.getFirstChild().getFirstChild().getFirstChild().getNodeValue());
    	}catch (Exception e) {
    		return -1;
    	}
    }

    protected int executeTest(int test, int[] psicoResults) throws Exception {
        String url = "http://www.psicologiatotal.com/wspsi2.php?xf=RecibirTest&test_id="+test;
        for(int i = 0; i < psicoResults.length; i++) {
            url += "&r["+String.valueOf(i)+"]="+String.valueOf(psicoResults[i]);
        }
        String content = readURL(url);
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        docBuilderFactory.setNamespaceAware(true);
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        ByteArrayInputStream is = new ByteArrayInputStream(content.getBytes());
        Document doc = docBuilder.parse(is);
        return Integer.parseInt(doc.getFirstChild().getFirstChild().getFirstChild().getNodeValue());
    }

    protected int createTest(int id, AssesmentAccess sys,String age,String sex) throws Exception {
    	String genero = (sex.equals("F") || sex.equals("M")) ? sex : "M";
    	int edad = 30;
    	try {
    		int ageValue = Integer.parseInt(age);
    		if(ageValue >= 18 && ageValue <= 85)
    			edad = ageValue;
    	} catch (Exception e) {
		}
        String content = readURL("http://www.psicologiatotal.com/wspsi2.php?xf=CrearTest&cliente="+id+"&edad="+edad+"&sexo="+genero);
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        docBuilderFactory.setNamespaceAware(true);
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        ByteArrayInputStream is = new ByteArrayInputStream(content.getBytes());
        Document doc = docBuilder.parse(is);
        return Integer.parseInt(doc.getFirstChild().getFirstChild().getFirstChild().getNodeValue());
    }

    protected int createUser(AssesmentAccess sys) throws Exception {
        //String content = readURL("http://www.psicologiatotal.com/wspsi2.php?xf=CrearCliente&nombre="+sys.getUserSessionData().getFilter().getLoginName()+"&idioma="+sys.getUserSessionData().getLenguage());
        String content = readURL("http://www.psicologiatotal.com/wspsi2.php?xf=CrearCliente&nombre="+String.valueOf(System.currentTimeMillis())+"&idioma="+sys.getUserSessionData().getLenguage());
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        docBuilderFactory.setNamespaceAware(true);
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        ByteArrayInputStream is = new ByteArrayInputStream(content.getBytes());
        Document doc = docBuilder.parse(is);
        return Integer.parseInt(doc.getFirstChild().getFirstChild().getFirstChild().getNodeValue());
    }

    protected String readURL(String url) throws Exception {
    	try {
	        URL yahoo = new URL(url);
	        BufferedReader in = new BufferedReader(
	        new InputStreamReader(yahoo.openStream()));
	        StringBuffer all = new StringBuffer();
	        String inputLine;
	        while ((inputLine = in.readLine()) != null) {
	            all.append(inputLine);
	        }
	        in.close();
	        return all.toString();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return "";
    }
    /**
     * @param valueNode
     */
    protected Date getDate(Node valueNode) {
        StringTokenizer tokenizer = new StringTokenizer(valueNode.getNodeValue(),"_");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,Integer.parseInt(tokenizer.nextToken()));
        c.set(Calendar.MONTH,Integer.parseInt(tokenizer.nextToken())-1);
        c.set(Calendar.DATE,Integer.parseInt(tokenizer.nextToken()));
        return c.getTime();
    }

    protected Date formatDate(String value) {
        StringTokenizer tokenizer = new StringTokenizer(value,"/");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DATE,Integer.parseInt(tokenizer.nextToken()));
        c.set(Calendar.MONTH,Integer.parseInt(tokenizer.nextToken())-1);
        c.set(Calendar.YEAR,Integer.parseInt(tokenizer.nextToken()));
        return c.getTime();
    }

    protected String formatDate(Date value) {
    	Calendar c = Calendar.getInstance();
    	c.setTime(value);
        return c.get(Calendar.DATE)+"/"+String.valueOf(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.YEAR);
    }

    protected String formatHour(Date value) {
    	Calendar c = Calendar.getInstance();
    	c.setTime(value);
        String s = c.get(Calendar.HOUR_OF_DAY)+":";
        if(c.get(Calendar.MINUTE) < 10) {
        	s += "0"+c.get(Calendar.MINUTE);
        }else {
        	s += c.get(Calendar.MINUTE);
        }
        return s;
    }

    protected Date formatDateGuion(String value) {
    	try {
    		if(value.contains("/")) {
		        StringTokenizer tokenizer = new StringTokenizer(value,"/");
		        Calendar c = Calendar.getInstance();
		        c.set(Calendar.DATE,Integer.parseInt(tokenizer.nextToken()));
		        c.set(Calendar.MONTH,Integer.parseInt(tokenizer.nextToken())-1);
		        int year = Integer.parseInt(tokenizer.nextToken());
		        if(year < 100) {
		        	year += 1900;
		        }
		        c.set(Calendar.YEAR,year);
		        return c.getTime();
    		}else {
		        StringTokenizer tokenizer = new StringTokenizer(value,"-");
		        Calendar c = Calendar.getInstance();
		        int year = Integer.parseInt(tokenizer.nextToken());
		        if(year < 100) {
		        	year += 1900;
		        }
		        c.set(Calendar.YEAR,year);
		        c.set(Calendar.MONTH,Integer.parseInt(tokenizer.nextToken())-1);
		        c.set(Calendar.DATE,Integer.parseInt(tokenizer.nextToken()));
		        return c.getTime();
    		}
    	}catch (Exception e) {
			return null;
		}
    }

    protected QuestionData getQuestionData(Integer questionId, ModuleData moduleData) {
        Iterator questions = moduleData.getQuestionIterator();
        while(questions.hasNext()) {
            QuestionData question = (QuestionData)questions.next();
            if(question.getId().intValue() == questionId.intValue()) {
                return question;
            }
        }
        return null;
    }

    public void feedback(AssesmentAccess sys,AssesmentAttributes assesment) throws Exception {
        boolean general = false;
        boolean psi = false;
        boolean errors = false;
        boolean personal = false;
        boolean certificate = false;
       
        
        UsReportFacade userReport = sys.getUserReportFacade();
        Text messages = sys.getText();
        UserSessionData userSessionData = sys.getUserSessionData();
        String login = userSessionData.getFilter().getLoginName();
        assesment.presentation.actions.report.Util util = new assesment.presentation.actions.report.Util();
        String body = "<table>";
        String emailTitle = messages.getText("assessment.reports");
        switch(assesment.getId().intValue()) {
	    	case AssesmentData.CEPA_DRIVING_SYSTEM:
	    		emailTitle += " CDS";
	    		break;
	    	case CorporationData.MICHELIN:
	    		emailTitle += " Michelin"; 
	    		break;
	    	default:
	    		emailTitle += " - "+messages.getText(assesment.getName()); 
			break;
	    }
        if(userReport.isAssessmentDone(login,assesment.getId(),userSessionData,assesment.isPsitest())) {
            UserData user = userReport.findUserByPrimaryKey(login,userSessionData);
            String name = user.getFirstName()+" "+user.getLastName();
            emailTitle += " - "+name;
            MailSender sender = new MailSender();
            Collection<String> files = new LinkedList<String>();
            if(assesment.isReportFeedback()) {
                String fileName = AssesmentData.FLASH_PATH+"/reports/resultado_"+name+".pdf";
                util.createUserReport(sys,fileName,assesment,user.getLoginName());
                general = true;
                files.add(fileName);
            }
            if(assesment.isErrorFeedback()) {
                String fileName = AssesmentData.FLASH_PATH+"/reports/errores_"+name+".pdf";
                util.createUserErrorReport(sys,fileName,assesment,user.getLoginName());
                errors = true;
                files.add(fileName);
            }
            if(assesment.isPsiFeedback()) {
                String fileName = AssesmentData.FLASH_PATH+"/reports/comportamiento_"+name+".pdf";
                util.createPsiReport(sys,fileName,assesment,user.getLoginName());
                psi = true;
                files.add(fileName);
            }
            //if(assesment.getId().intValue() == AssesmentData.ALLERGAN_1 || assesment.getId().intValue() == AssesmentData.BIMBO_MEXICO || assesment.getId().intValue() == AssesmentData.SANOFI_BRASIL || assesment.getId().intValue() == AssesmentData.UNILEVER) {
            if(assesment.isCertificate()) {
            	String fileName = AssesmentData.FLASH_PATH+"/reports/certificate_"+name+".pdf";
                certificate = true;
                util.createCertificate(fileName,user,assesment,sys);
                files.add(fileName);
            }
            body += "<tr><td>"+messages.getText("feedback.thanksmessage")+"</td><tr>";
            body += "<tr><td><a href='www.cepasafedrive.com'>www.cepasafedrive.com</a></td><tr>";
            body += "<tr><td></td><tr>";
            body += "</table>";

            if(files.size() > 0) {
            	String email = (!Util.empty(user.getEmail())) ? user.getEmail() : sys.getQuestionReportFacade().getEmailByQuestion(assesment.getId(),login,messages,userSessionData);
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
                files = new LinkedList<String>();
                body = "<table>";
                while(reports.hasNext()) {
                    int report = ((Integer)reports.next()).intValue(); 
                    switch(report) {
                        case FeedbackData.GENERAL_RESULT_REPORT:
                            String fileName = AssesmentData.FLASH_PATH+"/reports/resultado_"+name+".pdf";
                            if(!general) {
                                util.createUserReport(sys,fileName,assesment,user.getLoginName());
                                general = true;
                            }
                            files.add(fileName);
                            break;
                        case FeedbackData.PSI_RESULT_REPORT:
                            fileName = AssesmentData.FLASH_PATH+"/reports/comportamiento_"+name+".pdf";
                            if(!psi && assesment.isPsiFeedback()) {
                                util.createPsiReport(sys,fileName,assesment,user.getLoginName());
                                psi = true;
                            }
                            files.add(fileName);
                            break;
                        case FeedbackData.ERROR_RESULT_REPORT:
                            fileName = AssesmentData.FLASH_PATH+"/reports/errores_"+name+".pdf";
                            if(!errors) {
                                util.createUserErrorReport(sys,fileName,assesment,user.getLoginName());
                                errors = true;
                            }
                            files.add(fileName);
                            break;
                        case FeedbackData.PERSONAL_DATA_REPORT:
                            fileName = AssesmentData.FLASH_PATH+"/reports/datos_personales_"+name+".pdf";
                            if(!personal) {
                                util.createPersonalDataReport(sys,fileName,assesment,user.getLoginName());
                                personal = true;
                            }
                            files.add(fileName);
                            break;
                    }
                }
                if(assesment.isCertificate()) {
                	String fileName = AssesmentData.FLASH_PATH+"/reports/certificate_"+name+".pdf";
                	if(!certificate) {
                		util.createCertificate(fileName,user,assesment,sys);
                		certificate = true;
                	}
                    files.add(fileName);
                    
                    body += "<tr><td>"+messages.getText("feedback.thanksmessage")+"</td><tr>";
                    body += "<tr><td><a href='www.cepasafedrive.com'>www.cepasafedrive.com</a></td><tr>";
                    body += "<tr><td><table width='100%'><tr>";            
                    body += "<td><b>"+messages.getText("assesment.reporthtml.footermessage1")+"</b></td>";
                    body += "</tr></table></td></tr>";                   
                }
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

    public void newFeedback(AssesmentAccess sys,AssesmentData assesment, String redirect) throws Exception {
    	switch(assesment.getId().intValue()) {
			case AssesmentData.NUEVOS_EMPLEADOS_PM:
		    	newFeedback(sys, assesment, sys.getUserSessionData().getFilter().getLoginName(),"monserrat.kuri@contracted.pmi.com",true, redirect);
		    	break;
			case AssesmentData.CEPA_SYSTEM_TRACTOCAMIONES_RICA:
		    	newFeedback(sys, assesment, sys.getUserSessionData().getFilter().getLoginName(),"MSagaon@rica.com.mx",true, redirect);
		    	break;
			case AssesmentData.PHILLIP_MORRIS_MX_2015:
		    	newFeedback(sys, assesment, sys.getUserSessionData().getFilter().getLoginName(),"cepasafedrive@gmail.com",true, redirect);
		    	break;
            default:
        		newFeedback(sys, assesment, sys.getUserSessionData().getFilter().getLoginName(),null,true, redirect);
    	}
    }    
    
    public void newFeedback(AssesmentAccess sys,AssesmentData assesment, String login, String email, boolean doFeedback, String redirect) throws Exception {
        UsReportFacade userReport = sys.getUserReportFacade();
        UserSessionData userSessionData = sys.getUserSessionData();
        assesment.presentation.actions.report.Util util = new assesment.presentation.actions.report.Util();
        if(userReport.isAssessmentDone(login,assesment.getId(),userSessionData,assesment.isPsitest())) {
            UserData user = userReport.findUserByPrimaryKey(login,userSessionData);
            String name = user.getFirstName()+" "+user.getLastName();
            Collection<String> files = new LinkedList<String>();
            Collection<String> fileNames = new LinkedList<String>();
            Collection feedbackList = sys.getAssesmentReportFacade().getAssesmentFeedback(assesment.getId(),userSessionData);
            boolean sendEmail = assesment.isReportFeedback() || (doFeedback && feedbackList.size() > 0);
            if(sendEmail) {
                String fileName1 = AssesmentData.getFileName(assesment.getId(),userSessionData.getLenguage(),name,1)+".pdf";
                String fileName = AssesmentData.FLASH_PATH+"/reports/"+fileName1;
                util.createTotalReport(fileName,user,assesment,sys);
                files.add(fileName);
                fileNames.add(fileName1);
            }
            if(sendEmail && isCertificate(assesment,sys) && userReport.isResultGreen(login, assesment.getId(), userSessionData)) {
                String fileName1 = AssesmentData.getFileName(assesment.getId(),userSessionData.getLenguage(),name,2)+".pdf";
                String fileName = AssesmentData.FLASH_PATH+"/reports/"+fileName1;
                util.createCertificate(fileName,user,assesment,sys);
                files.add(fileName);
                fileNames.add(fileName1);
            }

            Collection<String> feedbacks = new LinkedList<String>();
            if(sendEmail) {
			    Iterator it = feedbackList.iterator();
			    if(!assesment.isReportFeedback()) {
				    if(it.hasNext()) {
				    	email = ((FeedbackData)it.next()).getEmail();
				    }
			    }
			    while(it.hasNext()) {
			        feedbacks.add(((FeedbackData)it.next()).getEmail());
			    }
	            sendEmail(sys, assesment, login, email, doFeedback, user, files, fileNames, redirect, feedbacks);
			}
			
        }
    }

	public void sendEmail(AssesmentAccess sys, AssesmentData assesment, String login, String email, boolean doFeedback, UserData user, Collection<String> files, Collection<String> fileNames, String redirect, Collection<String> feedbacks) throws Exception,RemoteException {
        UserSessionData userSessionData = sys.getUserSessionData();
        Text messages = sys.getText();
        MailSender sender = new MailSender();
        int assessment = assesment.getId().intValue();
    	boolean isElearningPackBYRMX = (assessment == AssesmentData.BAYERMX_ELEARNINGPACK_V2 || assessment == AssesmentData.BAYERMX_ELEARNINGPACK_V2_REPETICION || assessment == AssesmentData.BAYERMX_ELEARNINGPACK_VERSION2 || assessment == AssesmentData.BAYERMX_ELEARNINGPACK_VERSION2_REPETICION);
        String userName = user.getFirstName()+" "+user.getLastName();
        String assessmentName = messages.getText(assesment.getName());
		if(files.size() > 0) {
		    String body = "<table>";
			body += "<tr><td>"+messages.getText("feedback.thanksmessage")+"</td><tr>";
		    body += "<tr><td><a href='www.cepasafedrive.com'>www.cepasafedrive.com</a></td><tr>";
    	    if(redirect != null) {
    	    	body += "<tr><td><i><span style=\"font-size: 10pt; color: rgb(102, 102, 102); font-family: 'Verdana','sans-serif';\">"+messages.getText("assesment.reporthtml.footermessage4")+"</span></i></td></tr>";
    	    	body += "<tr><td>&nbsp;</td></tr>";
    	    	body += "<tr><td><a href=\""+redirect+"\">"+redirect+"</a></td></tr>";
    	    	body += "<tr><td>&nbsp;</td></tr>";
    	    	body += "<tr><td><i><span style=\"font-size: 10pt; color: rgb(102, 102, 102); font-family: 'Verdana','sans-serif';\">"+messages.getText("assesment.reporthtml.footermessage5")+"</span></i><a href=\"mailto:indesa@cepasafedrive.com\">indesa@cepasafedrive.com</a></td></tr>";
    	    	body += "<tr><td>&nbsp;</td></tr>";
    	    }
		    body += "<tr><td></td><tr>";
		    body += "</table>";
			if(email == null)
				email = (!Util.empty(user.getEmail())) ? user.getEmail() : sys.getQuestionReportFacade().getEmailByQuestion(assesment.getId(),login,messages,userSessionData);
			if(!Util.empty(email)) {
		        boolean sended = false;
		        int count = 10;
		        Collection<String> to = new LinkedList<String>();
		        to.add(email);
		        while(!sended && count > 0) {
		        	try {
	            		if(isElearningPackBYRMX) {
                        	sender.sendImageMandrill(to, "CEPA", "support@cepasafedrive.com", "Resultados eLeaning Pack - "+userName, body, files, fileNames, "support@cepasafedrive.com",  "CEPA", feedbacks, AssesmentData.FLASH_PATH+"/images/logo_road_safety.png");
                		} else if(assessmentName.toLowerCase().contains("elearning")){
                        	sender.sendImageMandrill(to, "CEPA", "support@cepasafedrive.com", assessmentName + " - "+ userName, body, files, fileNames, "support@cepasafedrive.com",  "CEPA", feedbacks, null);
                		} else {
                        	sender.sendImageMandrill(to, "CEPA", "support@cepasafedrive.com", assessmentName + " - "+ userName, body, files, fileNames, "support@cepasafedrive.com",  "CEPA", feedbacks, null);
                		}
	                	sended = true;
		        	}catch (Exception e) {
						e.printStackTrace();
					}
		            count--;
		        }
			}
		}
	}

	public void sendMultiEmail(AssesmentAccess sys, UserMultiAssessmentData answerData, UserData user) throws Exception,RemoteException {
        UserSessionData userSessionData = sys.getUserSessionData();
        Text messages = sys.getText();
        
        MailSender sender = new MailSender();
        String userName = user.getFirstName()+" "+user.getLastName();
        String assessmentName = messages.getText(answerData.getAssessment().getName());

        String result = (sys.getUserReportFacade().hasErrors(answerData.getId(), userSessionData)) ? "result.notapprouved" : "result.approuved";
        String body = "<div>";
		body += "<p>El conductor <b>"+userName+"</b> completó el cuestionario "+assessmentName+" el día <b>"+formatDate(answerData.getEndDate())+"</b> a la hora <b>"+formatHour(answerData.getEndDate())+"</b> con resultado <b>"+messages.getText(result)+"</b></p>";
		body += "</div>";

		boolean sended = false;
		int count = 10;
		Collection<String> to = new LinkedList<String>();
		to.add("base@fraylog.com.uy");
		to.add("cepasafedrive@gmail.com");
		while(!sended && count > 0) {
			try {
				sender.sendImageMandrill(to, "CEPA", "support@cepasafedrive.com", assessmentName+" - "+userName, body, new LinkedList(), new LinkedList(), "support@cepasafedrive.com",  "CEPA", new LinkedList(), null);
            	sended = true;
        	}catch (Exception e) {
				e.printStackTrace();
			}
            count--;
		}
	}

    protected boolean isCertificate(AssesmentData assessment, AssesmentAccess sys) {
    	try {
			switch(assessment.getId().intValue()) {
				case AssesmentData.PLATERO:
					return !sys.getUserReportFacade().isResultRed(sys.getUserSessionData().getFilter().getLoginName(), assessment.getId(), sys.getUserSessionData());
			}
			return assessment.isCertificate();
    	}catch (Exception e) {
    		e.printStackTrace();
    		return false;
    	}
	}

	public void resend(AssesmentAccess sys,AssesmentAttributes assesment, String login, String email) throws Exception {
        
        UsReportFacade userReport = sys.getUserReportFacade();
        Text messages = sys.getText();
        UserSessionData userSessionData = sys.getUserSessionData();
        assesment.presentation.actions.report.Util util = new assesment.presentation.actions.report.Util();
        String body = "<table>";
        String emailTitle = messages.getText("assessment.reports");
        switch(assesment.getId().intValue()) {
	    	case AssesmentData.CEPA_DRIVING_SYSTEM:
	    		emailTitle += " CDS";
	    		break;
	    	case CorporationData.MICHELIN:
	    		emailTitle += " Michelin"; 
	    		break;
	    	default:
	    		emailTitle += " - "+messages.getText(assesment.getName()); 
			break;
	    }
        UserData user = userReport.findUserByPrimaryKey(login,userSessionData);
        String name = user.getFirstName()+" "+user.getLastName();
        emailTitle += " - "+name;
        Collection<String> files = new LinkedList<String>();
        if(assesment.isReportFeedback()) {
            String fileName = AssesmentData.FLASH_PATH+"/reports/resultado_"+name+".pdf";
            util.createUserReport(sys,fileName,assesment,user.getLoginName());
            files.add(fileName);
        }
        if(assesment.isErrorFeedback()) {
            String fileName = AssesmentData.FLASH_PATH+"/reports/errores_"+name+".pdf";
            util.createUserErrorReport(sys,fileName,assesment,user.getLoginName());
            files.add(fileName);
        }
        if(assesment.isPsiFeedback()) {
            String fileName = AssesmentData.FLASH_PATH+"/reports/comportamiento_"+name+".pdf";
            util.createPsiReport(sys,fileName,assesment,user.getLoginName());
            files.add(fileName);
        }
        if(assesment.isCertificate()) {
        	String fileName = AssesmentData.FLASH_PATH+"/reports/certificate_"+name+".pdf";
            util.createCertificate(fileName,user,assesment,sys);
            files.add(fileName);
        }
        /*
        body += "<tr><td>"+messages.getText("feedback.thanksmessage")+"</td><tr>";
        body += "<tr><td><a href='www.cepasafedrive.com'>www.cepasafedrive.com</a></td><tr>";
        body += "<tr><td></td><tr>";
        body += "</table>";

        sendEmail(user,assesment,sys,body,emailTitle,email,files,null);
        */
    }

	protected void sendEmail(UserData user,AssesmentAttributes assesment, AssesmentAccess sys, String body, String emailTitle, String email, Collection<String> files, String image) throws Exception {
        MailSender sender = new MailSender();
        Text messages = sys.getText();
        UserSessionData userSessionData = sys.getUserSessionData();
        if(files.size() > 0) {
        	if(email == null) {
        		email = (!Util.empty(user.getEmail())) ? user.getEmail() : sys.getQuestionReportFacade().getEmailByQuestion(assesment.getId(),user.getLoginName(),messages,userSessionData);
        	}
        	if(!Util.empty(email)) {
                boolean sended = false;
                int count = 10;
                while(!sended && count > 0) {
                	try {
		                String[] senderAddress = sender.getAvailableMailAddress();
		                if(!Util.empty(senderAddress[0])) {
		                	if(image == null) {
		                		sender.send(email,FROM_NAME,senderAddress[0],senderAddress[1],emailTitle,body,files);
		                	}else {
		                		sender.sendImage(email,FROM_NAME,senderAddress[0],senderAddress[1],emailTitle,files,body,image);
		                	}
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


    public Object[] saveAnswers(AssesmentAccess sys,String moduleId,HashMap answers,boolean feedback) throws InvalidDataException {
        try {
        	long v = System.currentTimeMillis();
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
            UsABMFacade usABM = sys.getUserABMFacade();
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
                usABM.savePsicoAnswers(user,assesment.getId(),answerList,userSessionData);
                if(usReport.isPsicologicDone(user,assesment.getId(),userSessionData)) {
                	String[] s = usReport.getPSIanswers(user, assesment.getId(), userSessionData);
                    int[] values = null;
                    int psicoId = -1;
                    int testId = -1;
                    if(s[1] == null) {
	                	int[] psicoResults = null;
	                    int[] questionIds = null;
	                    String[][] answerIds = null;
	                    psicoResults = new int[48];
	                    questionIds = new int[48];
	                    answerIds = new String[48][3];
	                    moduleData = sys.getModuleReportFacade().getPsicoModule(userSessionData);
	                    try {
	                    	int assessmentId = assesment.getId().intValue(); 
	                    	if(assesment.isPsi()) {
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
			                    if(executedId > 0) {
			                        int evaluateId = evaluateTest(executedId);
			                        if(evaluateId > -1) {
			                        	values = getValues(evaluateId);
			    	                    if(s[0] != null)
			    	                    	usABM.savePSIaux(s[0], values, userSessionData);
			                        }else {
			                        	values = new int[]{1,1,1,1,1,1};
			                        }
			                    }
	                    	}else {
	                        	values = new int[]{1,1,1,1,1,1};
	                    	}
	                    }catch (Exception e) {
	                    	e.printStackTrace();
                        	values = new int[]{1,1,1,1,1,1};
						}
	                }else {
	                	StringTokenizer t = new StringTokenizer(s[1], "-");
	                	int index = 0;
	                	values = new int[6];
	                	while(t.hasMoreTokens()) {
	                		values[index] = Integer.parseInt(t.nextToken());
	                		index++;
	                	}
	                }
                    usABM.saveModuleAnswers(user,assesment.getId(),answerList,psicoId,testId,userSessionData,psico,values,feedback);
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
	                }else if(questionData.getType().intValue() == QuestionData.TEXT || questionData.getType().intValue() == QuestionData.TEXTAREA || questionData.getType().intValue() == QuestionData.YOU_TUBE_VIDEO || questionData.getType().intValue() == QuestionData.VIDEO) {
	                	valids.add(questionId.toString());
	                    answerList.add(new UserAnswerData(questionData.getId(),value));
	                }
	            }
	            sys.getUserABMFacade().saveModuleAnswers(user,assesment.getId(),answerList,0,0,userSessionData,false,null,feedback);
            }

            String[] valid = (String[])valids.toArray(new String[0]);
            String[][] error = (String[][])errors.toArray(new String[0][0]);

            return  new Object[]{valid,error};
        }catch(Exception e) {
        	throw new InvalidDataException(moduleId,moduleId);
        }
    }

    public Object[] saveAnswers(AssesmentAccess sys, AssesmentData assesment, String moduleId, HashMap answers, boolean feedback) throws InvalidDataException {
        try {
            String user = sys.getUserSessionData().getFilter().getLoginName();
            UserSessionData userSessionData = sys.getUserSessionData();
            Text messages = sys.getText();
            
            Collection<UserAnswerData> answerList = new LinkedList<UserAnswerData>();
    
            boolean psico = moduleId.equals(String.valueOf(ModuleData.PSICO)); 
            Collection<String> valids = new LinkedList<String>();
            Collection<String[]> errors = new LinkedList<String[]>();
            
    
            ModuleData moduleData = null;

            UsReportFacade usReport = sys.getUserReportFacade();
            UsABMFacade usABM = sys.getUserABMFacade();
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
                usABM.savePsicoAnswers(user,assesment.getId(),answerList,userSessionData);
                if(usReport.isPsicologicDone(user,assesment.getId(),userSessionData)) {
                	String[] s = usReport.getPSIanswers(user, assesment.getId(), userSessionData);
                    int[] values = null;
                    int psicoId = -1;
                    int testId = -1;
                    if(s[1] == null) {
	                	int[] psicoResults = null;
	                    int[] questionIds = null;
	                    String[][] answerIds = null;
	                    psicoResults = new int[48];
	                    questionIds = new int[48];
	                    answerIds = new String[48][3];
	                    moduleData = sys.getModuleReportFacade().getPsicoModule(userSessionData);
	                    try {
	                    		values = new int[]{1,1,1,1,1,1};
	                    }catch (Exception e) {
                        	values = new int[]{1,1,1,1,1,1};
						}
	                }else {
	                	StringTokenizer t = new StringTokenizer(s[1], "-");
	                	int index = 0;
	                	values = new int[6];
	                	while(t.hasMoreTokens()) {
	                		values[index] = Integer.parseInt(t.nextToken());
	                		index++;
	                	}
	                }
                    usABM.saveModuleAnswers(user,assesment.getId(),answerList,psicoId,testId,userSessionData,psico,values,feedback);
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
	                }else if(questionData.getType().intValue() == QuestionData.TEXT || questionData.getType().intValue() == QuestionData.TEXTAREA || questionData.getType().intValue() == QuestionData.YOU_TUBE_VIDEO || questionData.getType().intValue() == QuestionData.VIDEO) {
	                	valids.add(questionId.toString());
	                    answerList.add(new UserAnswerData(questionData.getId(),value));
	                }
	            }
	            sys.getUserABMFacade().saveModuleAnswers(user,assesment.getId(),answerList,0,0,userSessionData,false,null,feedback);
            }

            String[] valid = (String[])valids.toArray(new String[0]);
            String[][] error = (String[][])errors.toArray(new String[0][0]);
            return  new Object[]{valid,error};
        }catch(Exception e) {
        	throw new InvalidDataException(moduleId,moduleId);
        }
    }
    
	protected Integer[] getDistanceValues(String value) {
		try {
			Integer[] values = new Integer[2];
	        values[0] = new Integer(value.substring(0,value.length()-1));
	        String unitValue = value.substring(value.length()-1);
	        if(unitValue.equalsIgnoreCase("k")) {
	            values[1] = new Integer(0);
	        }else if(unitValue.equalsIgnoreCase("m")) {
	        	values[1] = new Integer(1);
	        }else {
	        	return null;
	        }
	        return values;
		}catch (Exception e) {
		}
		return null;
	}

	protected boolean isAdult(Date dateValue) {
		Calendar max = Calendar.getInstance();
		max.add(Calendar.YEAR, -100);
		Calendar now = Calendar.getInstance();
		now.add(Calendar.YEAR, -18);
		Calendar c = Calendar.getInstance();
		c.setTime(dateValue);
		return c.before(now) && max.before(c);
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
       			user.getEmail(), user.getFirstName(), user.getLanguage(),
       			user.getLastName(),sex, dc5corporationId);
           	userToRegister.setUser(userElearning);
           
           	// Lecciones que tomarÃ¡ el usuario:
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
	
	public void proccessPsi(String user, Integer assesmentId, AssesmentAccess sys) throws Exception  {
		UsReportFacade usReport = sys.getUserReportFacade();
		UsABMFacade usABM = sys.getUserABMFacade();
		UserSessionData userSessionData = sys.getUserSessionData();
    	String[] s = usReport.getPSIanswers(user, assesmentId, userSessionData);
        int[] values = null;
        int psicoId = -1;
        int testId = -1;
        if(s[1] == null) {
        	int[] psicoResults = null;
            int[] questionIds = null;
            String[][] answerIds = null;
            psicoResults = new int[48];
            questionIds = new int[48];
            answerIds = new String[48][3];
            ModuleData moduleData = sys.getModuleReportFacade().getPsicoModule(userSessionData);
            try {
            	int assessmentId = assesmentId.intValue(); 
            	if(assessmentId == AssesmentData.MINCIVIL_COLOMBIA || assessmentId == AssesmentData.CCFC || 
            			assessmentId == AssesmentData.SAFEFLEET_MEX || assessmentId == AssesmentData.SAFEFLEET_LATAM || 
               			assessmentId == AssesmentData.MONDELEZ_DA || assessmentId == AssesmentData.GUINEZ_INGENIERIA || 
            			assessmentId == AssesmentData.TIMAC_BRASIL_DA_2020 || assessmentId == AssesmentData.UPL_NEWHIRE
            			|| assessmentId == AssesmentData.LIGHT_VEHICLES_2020 || assessmentId == AssesmentData.MUTUAL_DA) {
                    psicoId = createUser(sys);
                    if(psicoId > 0) {
                        String[] personalData = sys.getQuestionReportFacade().getAgenSex(user,assesmentId,userSessionData);
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
                	HashMap psiAnswers = usReport.findPsicoModuleResult(user,assesmentId,userSessionData);
                	Iterator answerIt = psiAnswers.keySet().iterator();
                	while(answerIt.hasNext()) {
                		Integer key = (Integer)answerIt.next();
                		UserAnswerData answerData = (UserAnswerData)psiAnswers.get(key);
                        int qIndex = questionIds[answerData.getQuestion().intValue()-1];
                        psicoResults[qIndex] = getAnswer(answerData.getAnswer(),answerIds[qIndex]);
                	}
                    int executedId = executeTest(testId,psicoResults);
                    if(executedId > 0) {
                        int evaluateId = evaluateTest(executedId);
                        if(evaluateId > -1) {
                        	values = getValues(evaluateId);
    	                    if(s[0] != null)
    	                    	usABM.savePSIaux(s[0], values, userSessionData);
                        }else {
                        	values = new int[]{1,1,1,1,1,1};
                        }
                    }
            	}else {
                	values = new int[]{1,1,1,1,1,1};
            	}
            }catch (Exception e) {
            	e.printStackTrace();
            	values = new int[]{1,1,1,1,1,1};
			}
        }else {
        	StringTokenizer t = new StringTokenizer(s[1], "-");
        	int index = 0;
        	values = new int[6];
        	while(t.hasMoreTokens()) {
        		values[index] = Integer.parseInt(t.nextToken());
        		index++;
        	}
        }
        usABM.saveModuleAnswers(user,assesmentId,new LinkedList<UserAnswerData>(),psicoId,testId,userSessionData,true,values,false);

	}
}
