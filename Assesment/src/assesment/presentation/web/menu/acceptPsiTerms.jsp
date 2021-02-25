<%@page import="assesment.communication.assesment.AssesmentData"%>
<%@page import="assesment.communication.assesment.GroupData"%>
<%@page import="assesment.communication.security.SecurityConstants"%>
<%@page import="assesment.communication.util.Filter"%>
<%@page import="assesment.business.AssesmentAccess"%>
<%@ page language="java"
	import="assesment.presentation.translator.web.util.*"	
	import="assesment.presentation.translator.web.administration.user.*"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>

<html>

<%
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	sys.getUserSessionData().setPsiAccept(true);
	if(Integer.parseInt(request.getParameter("type")) == 1)
		response.sendRedirect("module_da.jsp?module=0");
	else
		response.sendRedirect("psimodule.jsp?id="+request.getParameter("assessment"));
%>
</html>