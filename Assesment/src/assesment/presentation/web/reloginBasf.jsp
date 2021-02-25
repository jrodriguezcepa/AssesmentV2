<%@ page language="java"
	import="assesment.presentation.translator.web.util.*"	
	import="assesment.presentation.translator.web.administration.user.*"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>

<html:html>

<%	String user = (String)session.getAttribute("user");
	session.removeAttribute("user");
	String password = (String)session.getAttribute("password");
	session.removeAttribute("password");
	String assessment = String.valueOf(session.getAttribute("assessment"));
	session.removeAttribute("assessment");
	new LogoutAction().logout(request);
    response.sendRedirect(request.getContextPath()+"/index.jsp?user="+user+"&password="+password+"&assessment="+assessment);
%>
</html:html>