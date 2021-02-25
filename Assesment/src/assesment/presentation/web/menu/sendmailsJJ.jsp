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
	sys.getUserSessionData().getFilter().setAssesment(new Integer(11));
    AssesmentAttributes assesment = sys.getAssesmentReportFacade().findAssesmentAttributes(sys.getUserSessionData().getFilter().getAssesment(),sys.getUserSessionData());
 	
 	if(userSession.getFilter().getAssesment() != null) {
 	    
 		String[] users = {"AngelIvancich","gustavograhmann","hpadill1","talonso","avargas7","hserva","jpisano1","jirizar1","ccentron","GustavoMartinez","jtellech","mmessuti","dmelis","acennera","myanacul","aaceved2","razanza","jabad","Gdibiass","ljove","ecabas","clemencianemocon","Kwicker","cbernal","ggandull","Vanessa Lago","claupoo","jgomez9","phuertas","tarcal","rrodri22","grastelli","acuesta1","mbenite1","acanosa","Josantos","lriderel","mlatuga","mgdeibe","mabreut","cveloza","JaimeGarcia","pfernand","mcarril1","hmendoz2","mbrandao","miguelvelandia","lmendez","jtorrres","scampoo","rcarbon2","Gdelgad3","hroye","Alexandre Falleiros","MDRIVERA","vivianapaola","ctriana","pavarela","jravazzo","mmoral10","MartinFaes","Csarrigu","dzambra1","muribe","Jzurita","juanjo","hcastane","jlaro","acorale","Slezica","rgamez3","mduchate","aferrer2","jyusco","acampo20"};
 		String[] languages = {"es","es","es","es","es","es","es","en","es","es","es","es","es","es","es","es","es","es","es","en","es","es","es","es","es","pt","es","es","es","es","es","es","es","es","es","es","es","es","es","es","es","es","es","es","es","es","es","es","es","es","en","es","es","pt","es","es","es","es","es","es","es","es","es","es","es","es","es","en","es","es","es","pt","es","es","es"};
     	AnswerUtil answerUtil = new AnswerUtilJJ();
 		
 		for(int i = 2; i < users.length; i++) {
 		
 	    	try {
 	    		sys.getUserSessionData().getFilter().setLoginName(users[i]);
 	    		sys.getUserSessionData().setLenguage(languages[i]);
				answerUtil.feedback(sys,assesment);
	        
 	    	}catch(Exception e) {
 	    		e.printStackTrace();
 	    	}
 		}
 	}
 	

%>
