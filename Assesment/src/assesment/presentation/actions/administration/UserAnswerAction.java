/*
 * Created on 17-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.administration;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import assesment.business.AssesmentAccess;
import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.exception.InvalidDataException;
import assesment.communication.module.ModuleData;
import assesment.communication.question.AnswerData;
import assesment.communication.question.QuestionData;
import assesment.communication.user.UserData;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

public class UserAnswerAction extends AbstractAction {

    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        int module = Integer.parseInt(((DynaActionForm)form).getString("module"));
        switch(sys.getUserSessionData().getFilter().getAssesment().intValue()) {
	        case AssesmentData.ASTRAZENECA_2: case AssesmentData.ASTRAZENECA_2013:
	        	return (module == 399) ? mapping.findForward("logout") : mapping.findForward("menuAZ");
	        case AssesmentData.DEMO_MOVILES:
	        	return (module == 522) ? mapping.findForward("logout") : mapping.findForward("menuMoviles");
        	default:
        		return mapping.findForward("menu");
        }
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        int assessment = sys.getUserSessionData().getFilter().getAssesment().intValue();
        
        try {
            String user = sys.getUserSessionData().getFilter().getLoginName();
            AssesmentAttributes assesmentAtt = sys.getAssesmentReportFacade().findAssesmentAttributes(assessment,sys.getUserSessionData());
        	
	        if(!sys.getUserReportFacade().isAssessmentDone(user,assessment,sys.getUserSessionData(),assesmentAtt.isPsitest())) {

	        	int module = Integer.parseInt(((DynaActionForm)form).getString("module"));
	            ModuleData moduleData = null;
	            boolean psico = module == ModuleData.PSICO; 
	            int[] questionIds = null;
	            String[][] answerIds = null;
	            int psicoId = -1;
	            int testId = -1;
	            if(psico) {
	                questionIds = new int[48];
	                answerIds = new String[48][3];
	                moduleData = sys.getModuleReportFacade().getPsicoModule(sys.getUserSessionData());
	                psicoId = createUser(sys);
	                if(psicoId > 0) {
	                    testId = createTest(psicoId,sys);
	                    if(testId > 0) {
	                        String content = readURL("http://www.psicologiatotal.com/wspsi2.php?xf=LeerTest&test_id="+testId);
	                        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	                        docBuilderFactory.setNamespaceAware(true);
	                        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
	                        ByteArrayInputStream is = new ByteArrayInputStream(content.getBytes());
	                        Document doc = docBuilder.parse(is);
	                        NodeList list = doc.getFirstChild().getChildNodes();
	                        int i = 0;
	                        while(i < list.getLength()) {
	                            Node node = list.item(i);
	                            if(node.getNodeName().startsWith("pregunta")) {
	                                int index = Integer.parseInt(node.getNodeName().substring(9));
	                                questionIds[Integer.parseInt(node.getFirstChild().getNodeValue())-1] = index;
	                            }else if(node.getNodeName().startsWith("respuesta")) {
	                                int indexX = Integer.parseInt(node.getNodeName().substring(10));
	                                NodeList list2 = node.getChildNodes();
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
	                moduleData = sys.getModuleReportFacade().findModule(new Integer(module),sys.getUserSessionData());
	            }
	            
	            String answers = getAnswers(moduleData,request);
	
	            AnswerUtil answerUtil = (assessment == AssesmentData.ASTRAZENECA_2 || assessment == AssesmentData.ASTRAZENECA_2013) ? new AnswerUtilAZ() : new AnswerUtil();
	            answerUtil.saveAnswers(sys,answers,moduleData,questionIds,answerIds,psicoId,testId);
	                        
		        if(sys.getUserReportFacade().isAssessmentDone(user,assessment,sys.getUserSessionData(),assesmentAtt.isPsitest())) {
		        	sys.getUserABMFacade().setEndDate(user,assessment,sys.getUserSessionData());
					answerUtil.feedback(sys,assesmentAtt);
		        }
	        }
        }catch(Exception e) {
        	session.setAttribute("Msg","Debe completar todas las preguntas");            
            switch(assessment) {
		        case AssesmentData.ASTRAZENECA_2:
		        case AssesmentData.ASTRAZENECA_2013:
		        	return mapping.findForward("backAZ");
		        case AssesmentData.DEMO_MOVILES:
		        	return mapping.findForward("backMoviles");
		    	default:
		    		return mapping.findForward("back");
		    }
        }

        switch(assessment) {
	        case AssesmentData.ASTRAZENECA_2:
	        case AssesmentData.ASTRAZENECA_2013:
	        	return mapping.findForward("menuAZ");
	        case AssesmentData.DEMO_MOVILES:
	        	return mapping.findForward("menuMoviles");
	    	default:
	    		return mapping.findForward("menu");
	    }
    }

    private int createTest(int id, AssesmentAccess sys) throws Exception {
        String content = readURL("http://www.psicologiatotal.com/wspsi2.php?xf=CrearTest&cliente="+id+"&edad=44&sexo=F");
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        docBuilderFactory.setNamespaceAware(true);
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        ByteArrayInputStream is = new ByteArrayInputStream(content.getBytes());
        Document doc = docBuilder.parse(is);
        return Integer.parseInt(doc.getFirstChild().getFirstChild().getFirstChild().getNodeValue());
    }

    private int createUser(AssesmentAccess sys) throws Exception {
        //String content = readURL("http://www.psicologiatotal.com/wspsi2.php?xf=CrearCliente&nombre="+sys.getUserSessionData().getFilter().getLoginName()+"&idioma="+sys.getUserSessionData().getLenguage());
        String content = readURL("http://www.psicologiatotal.com/wspsi2.php?xf=CrearCliente&nombre="+String.valueOf(System.currentTimeMillis())+"&idioma="+sys.getUserSessionData().getLenguage());
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        docBuilderFactory.setNamespaceAware(true);
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        ByteArrayInputStream is = new ByteArrayInputStream(content.getBytes());
        Document doc = docBuilder.parse(is);
        return Integer.parseInt(doc.getFirstChild().getFirstChild().getFirstChild().getNodeValue());
    }

    private String readURL(String url) throws Exception {
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
    }

    private String getAnswers(ModuleData moduleData, HttpServletRequest request) {
        String s = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<module id=\""+String.valueOf(moduleData.getId())+"\" >";
        Iterator it = moduleData.getQuestionIterator();
        while(it.hasNext()) {
            QuestionData question = (QuestionData)it.next();
            s += "<question id=\""+String.valueOf(question.getId())+"\">";
            if(question.getType().intValue() == QuestionData.DATE || question.getType().intValue() == QuestionData.BIRTHDATE) {
                String day = request.getParameter("questionDay"+String.valueOf(question.getId()));
                String month = request.getParameter("questionMonth"+String.valueOf(question.getId()));
                String year = request.getParameter("questionYear"+String.valueOf(question.getId()));
                if(Util.empty(day) || Util.empty(month) || Util.empty(year)) {
                    throw new InvalidDataException("question.result.emtpy","");
                }else {
                    s += "<answer>"+day+"_"+month+"_"+year+"</answer>";
                }
            }else if(question.getType().intValue() == QuestionData.OPTIONAL_DATE) {
                String permanent = request.getParameter("permanent"+String.valueOf(question.getId()));
                if(permanent != null && permanent.equals("on")) {
                    s += "<answer>0</answer>";
                }else {
	                String day = request.getParameter("questionDay"+String.valueOf(question.getId()));
	                String month = request.getParameter("questionMonth"+String.valueOf(question.getId()));
	                String year = request.getParameter("questionYear"+String.valueOf(question.getId()));
	                if(Util.empty(day) || Util.empty(month) || Util.empty(year)) {
	                    throw new InvalidDataException("question.result.emtpy","");
	                }else {
	                    s += "<answer>"+day+"_"+month+"_"+year+"</answer>";
	                }
                }
            }else if(question.getType().intValue() == QuestionData.TEXT
                    || question.getType().intValue() == QuestionData.EMAIL) {
                String value = request.getParameter("question"+String.valueOf(question.getId()));
                if(Util.empty(value)) {
                    throw new InvalidDataException("question.result.emtpy","");
                }else {
                    s += "<answer>"+value+"</answer>";
                }
            }else if(question.getType().intValue() == QuestionData.COUNTRY) {
                String value = request.getParameter("question"+String.valueOf(question.getId()));
                if(Util.empty(value)) {
                    throw new InvalidDataException("question.result.emtpy","");
                }else {
                    s += "<answer>"+value+"</answer>";
                }
            }else if(question.getType().intValue() == QuestionData.EXCLUDED_OPTIONS
                    || question.getType().intValue() == QuestionData.LIST
                    || question.getType().intValue() == QuestionData.KILOMETERS) {
                String value = request.getParameter("question"+String.valueOf(question.getId()));
                if(Util.empty(value)) {
                    throw new InvalidDataException("question.result.emtpy","");
                }else {
                    s += "<answer>"+value+"</answer>";
                    if(question.getType().intValue() == QuestionData.KILOMETERS) {
                        s += "<units>0</units>";
                    }else {
                        s += "<units>1</units>";
                    }
                }
            }else if(question.getType().intValue() == QuestionData.INCLUDED_OPTIONS) {
                Iterator<AnswerData> answers = question.getAnswerIterator();
                while(answers.hasNext()) {
                    AnswerData answer = answers.next();
                    if(request.getParameter("answer"+String.valueOf(answer.getId())) != null) {
                        if(request.getParameter("answer"+String.valueOf(answer.getId())).equals("on")) {
                            s += "<answer>"+String.valueOf(answer.getId())+"</answer>";
                        }
                    }
                }
            }
            s += "</question>";
        }
        s += "</module>";
        return s;
    }

}