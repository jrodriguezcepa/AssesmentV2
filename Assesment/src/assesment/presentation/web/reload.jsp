<%@ page language="java"
	import="assesment.presentation.translator.web.util.*"	
	import="assesment.presentation.translator.web.administration.user.*"
	import="assesment.communication.util.MD5"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>


<html:html>

<%	String user = request.getParameter("user");
	String password = new MD5().encriptar(user);
	session.removeAttribute("password");
	new LogoutAction().logout(request);
    response.sendRedirect(request.getContextPath()+"/index.jsp?user="+user+"&password="+password);
%>
</html:html>