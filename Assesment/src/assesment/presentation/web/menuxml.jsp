<%@ page language="java" isErrorPage="true"
	import="java.util.*"
	import="assesment.business.*"
	import="assesment.communication.assesment.*"
%>
<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>
<%!
	AssesmentAccess sys;
%>
<%
	sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	String xml = "";
	if(sys.getUserSessionData().getFilter().getAssesment() != null) {
	    AssesmentData assesmentData = sys.getAssesmentReportFacade().findAssesment(sys.getUserSessionData().getFilter().getAssesment(),sys.getUserSessionData());
	 	Collection userModule = sys.getUserReportFacade().getUserModules(sys.getUserSessionData().getFilter().getLoginName(),sys.getUserSessionData().getFilter().getAssesment(),sys.getUserSessionData(),assesmentData.isInstantFeedback());
	    AssesmentXML assesmentXml = new AssesmentXML(sys);
	    xml = assesmentXml.generateXML(assesmentData,userModule,assesmentData.isInstantFeedback());
	}
	response.setContentType("text/xml");
	response.getWriter().write(xml);
%>
