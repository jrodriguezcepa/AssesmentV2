<%@ page language="java" isErrorPage="true"
	import="java.util.*"
	import="assesment.business.*"
	import="assesment.communication.util.*"
	import="assesment.presentation.translator.web.util.*"
%>
<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>
<%!
	AssesmentAccess sys;
%>
<%
	sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
    CountryXML countryXml = new CountryXML(sys);
    String xml = countryXml.generateXML();
	response.setContentType("text/xml");
	response.getWriter().write(xml);
%>
