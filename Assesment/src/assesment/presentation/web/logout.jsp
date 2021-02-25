<%@ page language="java"
	import="assesment.presentation.translator.web.util.*"	
	import="assesment.presentation.translator.web.administration.user.*"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>

<html:html>

<%! RequestDispatcher dispatcher; String context; String pathMsg; String pathGarbagecolector;%>
<%
	context=request.getContextPath();
	pathMsg="/util/jsp/message.jsp";
	pathGarbagecolector="/util/jsp/garbagecolector.jsp";
	
	dispatcher=request.getRequestDispatcher(pathMsg);
	dispatcher.include(request,response);
	
	dispatcher=request.getRequestDispatcher(pathGarbagecolector);
	dispatcher.include(request,response);

	new LogoutAction().logout(request);
	
	String next = "/index.jsp";
	if(!Util.empty(request.getParameter("assessment"))) {
		next += "?assessment="+request.getParameter("assessment");
	}
    response.sendRedirect(request.getContextPath()+next);
%>
</html:html>