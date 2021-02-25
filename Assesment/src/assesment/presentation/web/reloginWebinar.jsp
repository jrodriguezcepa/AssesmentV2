<%@ page language="java"
	import="assesment.presentation.translator.web.util.*"	
	import="assesment.presentation.translator.web.administration.user.*"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>

<html:html>

<%	String g = (String)session.getAttribute("g");
	session.removeAttribute("g");
	String p = (String)session.getAttribute("p");
	session.removeAttribute("p");
	String lng = (String)session.getAttribute("lng");
	session.removeAttribute("lng");
	String c = (String)session.getAttribute("c");
	session.removeAttribute("c");
	new LogoutAction().logout(request);
    response.sendRedirect(request.getContextPath()+"/index.jsp?g="+g+"&p="+p+"&lng="+lng+"&c="+c);
%>
</html:html>