<%@ page language="java"
	import="assesment.business.*"
	import="assesment.communication.language.*"
	import="assesment.communication.language.tables.*"
	import="assesment.communication.security.*"	
	import="assesment.communication.util.*"	
	import="assesment.communication.user.*"
	import="assesment.presentation.translator.web.util.*"	
	import="assesment.presentation.actions.user.*"
	import="assesment.communication.assesment.*"
	import="assesment.communication.corporation.*"
	import="java.util.*"
	import="assesment.presentation.translator.web.util.*"	
	import="assesment.presentation.translator.web.administration.user.*"
%>
<%@page contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html" 
%>

<%@ taglib uri="/WEB-INF/struts-bean.tld"
        prefix="bean" 
%>

<html>

	<head>
		<title> CEPA </title>

		
		<link href='<%=request.getContextPath()+"/util/css/estilo.css"%>' rel="stylesheet" type="text/css">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<style type="text/css">
		
		<!--
		body {
			background-color: #000000;
		}
		-->
		</style>
	</head>
<%! RequestDispatcher dispatcher; String context; String pathMsg; String pathGarbagecolector;%>
<%
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	Text messages = sys.getText();

	String thanks1 = messages.getText("messages.emea.thanks1");
	String thanks2 = messages.getText("messages.emea.thanks2");
	String outMessage = messages.getText("assesment.emea.cancel");
	
	context=request.getContextPath();
	pathMsg="/util/jsp/message.jsp";
	pathGarbagecolector="/util/jsp/garbagecolector.jsp";
	
	dispatcher=request.getRequestDispatcher(pathMsg);
	dispatcher.include(request,response);
	
	dispatcher=request.getRequestDispatcher(pathGarbagecolector);
	dispatcher.include(request,response);
	
	new LogoutAction().logout(request);
	
%>
	<form action="http://da.cepasafedrive.com" name="outForm">
	</form>
	<body>
		<table width="955" height="568" border="0" align="center" cellpadding="0" cellspacing="0"  class="tableEMEA">
		  	<tr height="225">
		  		<td>&nbsp;</td>
		  	</tr>
		  	<tr>
   				<td align="center" valign="top">
   					<table width="40%" border="0" align="center" cellpadding="0" cellspacing="0" valign="top">
				      	<tr align="center">
       						<th align="right"  class="menu"><%=thanks1%></th>
       						<td rowspan="2">
       							<img src="/assesment/util/imgs/flecha_boton.gif" onclick="javascript:document.forms['outForm'].submit();" class="cursor">
       						</td>
						</tr>
				      	<tr align="center">
       						<th align="right"  class="menu"><%=thanks2%></th>
						</tr>
   					</table>
   				</td>
  			</tr>
		</table>
	</body>
</html>


