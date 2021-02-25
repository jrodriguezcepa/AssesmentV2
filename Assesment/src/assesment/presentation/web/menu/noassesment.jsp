<%@page language="java"
	import="assesment.business.*"
	import="assesment.business.administration.user.*"
	import="assesment.communication.assesment.*"
	import="assesment.communication.language.*"
	import="assesment.communication.security.*"
	import="assesment.communication.administration.user.*"
	import="assesment.communication.module.*"
	import="java.util.*"
%>
<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>

<%
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess"); 
 	UserSessionData userSession=sys.getUserSessionData(); 
 	Text messages=sys.getText();
%>


<html>
	<head>
		<LINK REL=StyleSheet HREF="util/css/estilo.css" TYPE="text/css">
 		<title>Assesment</title>
		<META http-equiv="Cache-Control" content="no-cache">
		<META http-equiv="Pragma" content="no-cache">
		<style type="text/css">
		<!--
			body {
				margin-left: 0px;
				margin-top: 0px;
				margin-right: 0px;
				margin-bottom: 0px;
			}
			.contain {
				width: 100%; 
				height: 100%;
			}
		 -->
		</style>
	</head>
	<body scroll="auto">
 		<table width="100%" height="90" border="0" cellpadding="0" cellspacing="0" class="default">
 				<tr>
	   				<td colspan="3" align="center" class="txtloginBig">
						No tiene un assesment asociado
  					</td>
 				</tr>
 				<tr>
	   				<td colspan="3" align="center" class="textNoAssesment">
						<a href="logout.jsp">
							<input type="button" value="Salir" class="input">
						</a>
  					</td>
 				</tr>
		</table>
	</body>
</html>
