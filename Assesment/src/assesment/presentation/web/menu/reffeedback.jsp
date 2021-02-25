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
 	
 	AssesmentAttributes assesment = sys.getAssesmentReportFacade().findAssesmentAttributes(new Integer(13),sys.getUserSessionData());
 	AnswerUtil answerUtil = new AnswerUtil();
	answerUtil.refeedback(sys,assesment);
 	

%>
