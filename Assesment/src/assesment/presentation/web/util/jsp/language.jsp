<%@page language="java"
	import="java.util.*"
	import="assesment.communication.language.*"
	import="assesment.business.AssesmentAccess"	
	import="assesment.communication.administration.user.*"
%>

<%!	UserSessionData userSessionData; Integer crpName; String lngName; Text messages; AssesmentAccess sys; 
%>
<%
	sys=(AssesmentAccess)session.getAttribute("AssesmentAccess");
	
	if(sys==null){
		response.sendRedirect("/login.jsp");	
	}
	else{
//		userSessionData=(UserSessionData)session.getAttribute("UserSessionData");
		userSessionData=sys.getUserSessionData();
		crpName=userSessionData.getFilter().getCorporationId();
		lngName=userSessionData.getLenguage();
//		messages=(Text)session.getAttribute("Text");
		messages=sys.getText();
	}
		
	
%>