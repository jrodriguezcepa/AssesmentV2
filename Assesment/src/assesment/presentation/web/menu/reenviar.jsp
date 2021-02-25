<%@page language="java"
	import="assesment.business.*"
	import="assesment.business.administration.user.*"
	import="assesment.communication.assesment.*"
	import="assesment.communication.language.*"
	import="assesment.communication.security.*"
	import="assesment.communication.administration.user.*"
	import="assesment.communication.module.*"
	import="assesment.communication.exception.*"
	import="assesment.communication.util.*"
	import="assesment.presentation.actions.report.*"
	import="assesment.communication.user.*"
	import="assesment.presentation.actions.administration.*"
	import="assesment.communication.corporation.CorporationData"
	import="java.util.*"
	import="java.io.*"
%>
<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>

<%
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess"); 
 	UserSessionData userSession=sys.getUserSessionData(); 
 	Text messages=sys.getText();
 	

 	int[] assessments = {97,105,105,100,105,101,99,106,105,102,102,99,99,99,106,97,104,106,104,105,97,100,105,100,98,104,105,105,103,100,101,100,97,106,101,99,102,97,104,97,105,97,97,102,106,100,99,99,106,100,102,104,97,106,106,104,106,99,99,99,103,103,99,103,102,105,103,106,99,98,102,101,101,99,103,106,97,106,106,105,99,100,100,105};
 	String[] users = {"BASFUser_528","BASFUser_85","BASFUser_178","BASFUser_275","BASFUser_522","BASFUser_269","BASFUser_522","BASFUser_146","BASFUser_230","BASFUser_519","BASFUser_117","BASFUser_330","BASFUser_319","BASFUser_499","BASFUser_113","BASFUser_513","BASFUser_311","BASFUser_258","BASFUser_510","BASFUser_455","BASFUser_413","BASFUser_331","BASFUser_506","BASFUser_153","BASFUser_145","BASFUser_234","BASFUser_211","BASFUser_261","BASFUser_499","BASFUser_498","BASFUser_142","BASFUser_496","BASFUser_275","BASFUser_494","BASFUser_493","BASFUser_181","BASFUser_491","BASFUser_490","BASFUser_182","BASFUser_265","BASFUser_487","BASFUser_89","BASFUser_485","BASFUser_446","BASFUser_125","BASFUser_333","BASFUser_162","BASFUser_479","BASFUser_478","BASFUser_477","BASFUser_476","BASFUser_475","BASFUser_474","BASFUser_473","BASFUser_266","BASFUser_471","BASFUser_265","BASFUser_129","BASFUser_468","BASFUser_269","BASFUser_100","BASFUser_319","BASFUser_464","BASFUser_131","BASFUser_123","BASFUser_126","BASFUser_460","BASFUser_459","BASFUser_458","BASFUser_335","BASFUser_456","BASFUser_455","BASFUser_146","BASFUser_293","BASFUser_452","BASFUser_451","BASFUser_117","BASFUser_311","BASFUser_245","BASFUser_330","BASFUser_446","BASFUser_445","BASFUser_238","BASFUser_223"};
 	try {

        String loginname = sys.getUserSessionData().getFilter().getLoginName();

        for(int i = 0; i < users.length; i++) {
 		 	String login = users[i];
 		 	String assessmentId = String.valueOf(assessments[i]);

 		 	AssesmentAttributes assesment = sys.getAssesmentReportFacade().findAssesmentAttributes(new Integer(assessmentId),sys.getUserSessionData());
		
	        AnswerUtil answerUtil = null;
	        if(assesment.getCorporationId() == CorporationData.BASF) {
	        	answerUtil = new AnswerUtilBasf();
	        }else {
	 	        switch(assesment.getId().intValue()) {
	 	            case AssesmentData.MONSANTO_NEW_HIRE: case AssesmentData.NEW_HIRE:
	 	                answerUtil = new AnswerUtilNewHire();
	 	                break;
	 	            case AssesmentData.JJ: case AssesmentData.JJ_2: 
	 	            case AssesmentData.JJ_3: case 85:
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
	 	            case AssesmentData.ASTRAZENECA: case AssesmentData.ASTRAZENECA_2:
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
	 	            default:
	 	                answerUtil = new AnswerUtil();
	 	        }
 	        }
	        //answerUtil.refeedback(sys);	        
	        UsReportFacade userReport = sys.getUserReportFacade();
		    UserSessionData userSessionData = sys.getUserSessionData();
		    
	        sys.getUserSessionData().getFilter().setAssesment(new Integer(assessmentId));
	        
	        
	        sys.getUserSessionData().getFilter().setLoginName(login);
	 	    answerUtil.feedback(sys,assesment);
	 	    
	 	    System.out.println("Enviado "+login);
 		}
	    sys.getUserSessionData().getFilter().setLoginName(loginname);
%>
			Enviado
<%
	        
   	}catch(Exception e) {
 		e.printStackTrace();
 	}
%>
