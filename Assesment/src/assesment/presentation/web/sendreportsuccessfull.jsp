<%@ page language="java"
	import="assesment.business.*"
	import="assesment.communication.language.*"
	
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>

<%!
Text message; AssesmentAccess sys;
%>
<%
	sys=(AssesmentAccess)session.getAttribute("AssesmentAccess");
	message=(Text)session.getAttribute("Text");

%>

<html>
	<head>
		<title> CEPA </title>
		<link href='<%=request.getContextPath()+"/util/css/estilo.css"%>' rel="stylesheet" type="text/css">
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">
		</style>
	</head>

	<body>
			<table width="499"  border="0" align="center" cellpadding="0" cellspacing="0" class="table">
  				<tr>
    				<td width="417" class="guide1"><%="FeedBack"%></td>
    			</tr>
    			<tr height="70px">
    				<td>
    					<%=message.getText("Thank you for trust in the system")%>
    				</td>
    			</tr>
    	    </table>
		
	</body>
</html>
