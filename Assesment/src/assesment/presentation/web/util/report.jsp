<%@page language="java"
    errorPage="../exception.jsp"
	import="java.io.File"
	import="java.util.ArrayList"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html" 
%>

<%@ taglib uri="/WEB-INF/struts-bean.tld"
        prefix="bean" 
%>
<%	response.getWriter().write((String)session.getAttribute("resp"));
	session.removeAttribute("resp");
%>	

