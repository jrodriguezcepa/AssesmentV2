<%@ page language="java"
	import="assesment.presentation.translator.web.administration.user.*"	
	import="assesment.presentation.translator.web.util.*"
	import="org.apache.struts.action.*"	
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>

<html:html>
<%! RequestDispatcher dispatcher; String context; String pathMsg; String pathGarbagecolector;%>
<%
	context=request.getContextPath();
	pathMsg="/util/jsp/message.jsp";
	
	dispatcher=request.getRequestDispatcher(pathMsg);
	dispatcher.include(request,response);
	ResetPasswordForm form = new ResetPasswordForm();
	session.setAttribute("ResetPasswordForm",form);
	String message1 = "";
	if(!Util.empty(request.getParameter("message"))) {
	    if(Integer.parseInt(request.getParameter("message")) == 1) {
	        message1 = "E-mail no pode ser vazio - The e-mail can't be empty";
	    }else {
	        message1 = "E-mail nao cadastrado - E-mail not founded";
	    }
	}
%>

	<head>
		<title> CEPA </title>

		
		<link href='<%=context+"/util/css/estilo.css"%>' rel="stylesheet" type="text/css">
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"><style type="text/css">
		
		<!--
		body {
			background-color: #DDDDDD;
		}
		-->
		</style>

	</head>

	<body>
		<html:form action="/Resetpassword" method="post">
			<table width="700" height="386" border="0" align="center" cellpadding="0" cellspacing="0"  class="tablelogin">
			  	<tr>
    				<td height="120"  class="red" align="center" valign="bottom"><%=message1%></td>
	  			</tr>
				<tr>
    				<td height="20" class="txtlogin" align="center">
    					Insert e-mail&nbsp;
    					<html:text property="email" styleClass="input"/>
    				</td>
  				</tr>
			  	<tr>
    				<td height="70" align="center" valign="bottom">&nbsp;</td>
	  			</tr>
				<tr>
    				<td height="35" class="txtlogin" align="center">
    					<html:radio property="language" styleClass="input" value="es"/>Spanish&nbsp;
    					<html:radio property="language" styleClass="input" value="en" />English&nbsp;
    					<html:radio property="language" styleClass="input" value="pt"/>Portuguese
    				</td>
  				</tr>
				<tr>
    				<td align="center" valign="top">
            			<html:submit styleClass="input">Send</html:submit>
            			<html:cancel styleClass="input">Cancel</html:cancel>
           			</td>
  				</tr>
			</table>
		</html:form>
	</body>
</html:html>


