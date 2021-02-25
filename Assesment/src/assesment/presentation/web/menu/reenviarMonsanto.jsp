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
 	userSession.getFilter().setAssesment(new Integer(AssesmentData.MONSANTO_ARGENTINA));
 	if(userSession.getFilter().getAssesment() != null) {
 	    
 	    AssesmentAttributes assesment = sys.getAssesmentReportFacade().findAssesmentAttributes(sys.getUserSessionData().getFilter().getAssesment(),sys.getUserSessionData());
 	   	String[] users = {"a7e656eb70a1800554b61755ce59a48f"};
 	    try {
 	        AnswerUtil answerUtil = new AnswerUtilMonsantoBR();

 	        for(int i = 0; i < users.length; i++) {
		        userSession.getFilter().setLoginName(users[i]);
				answerUtil.feedback(sys,assesment);
 	        }
 	    }catch(Exception e) {
 	    	e.printStackTrace();
 	    }
 	}
 	

%>
