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
	boolean redirect = false;
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	sys.getUserABMFacade().acceptTerms(sys.getUserSessionData());
	if(sys.getUserSessionData().getRole().equals(SecurityConstants.GROUP_ASSESSMENT))  {
		Integer group = sys.getUserSessionData().getFilter().getGroup();
		if(group != null && group.intValue() == GroupData.MERCADOLIVRE) {
			redirect = true;
			response.sendRedirect("newmodule.jsp?id="+AssesmentData.MERCADO_LIVRE_START);
		}
	}
	if(!redirect) {
		response.sendRedirect("index.jsp");
	}
%>
</html>