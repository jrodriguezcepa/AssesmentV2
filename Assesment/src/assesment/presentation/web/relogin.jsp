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
	new LogoutAction().logout(request);
	System.out.println(request.getContextPath()+"/index.jsp?user="+user+"&password="+password);
    response.sendRedirect(request.getContextPath()+"/index.jsp?user="+user+"&password="+password);
%>
</html:html>