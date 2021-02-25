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
    response.sendRedirect("https://www.veibraschevroletsjc.com.br/?ppc=Paid_Search_kw%3D%2BVeibras_campaign%3D71700000048678361--Insti--DDP-BAC198544%C2%BB19%C2%BBCHV3%C2%BBNA%C2%BBI%C2%BBG%C2%BBI%C2%BBBR%C2%BBZ%C2%BBG%C2%BBNA%C2%BBPRF%C2%BBNA%C2%BBOSB%C2%BBNA%C2%BB2%C2%BBNA%C2%BBX%C2%BBA%C2%BBPT%C2%BBS%C2%BBN%C2%BBO_AdGroup%3DInstitucional%20DEALER");
%>
</html:html>