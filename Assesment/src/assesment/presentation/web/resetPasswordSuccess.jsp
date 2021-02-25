<%@ page language="java"
	import="assesment.presentation.actions.user.*"	
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
	String message = "New User";
	if(request.getParameter("type").equals("1")) {
	    message = "User Reset Password";
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
		<form name="comeLogin" action="./login.jsp" method="post">
			<table width="700" height="386" border="0" align="center" cellpadding="0" cellspacing="0"  class="tablelogin">
			  	<tr>
    				<td height="100" colspan="3">&nbsp;</td>
	  			</tr>
  				<tr>
    				<td width="700"height="24" align="center" class="txtlogin"><%=message%></td>
  				</tr>
  				<tr>
    				<td width="700" height="24" align="center" class="txtlogin">Sua Password foi enviada - Your password has been sent</td>
  				</tr>
   				<tr>
    				<td colspan="2" align="center">
            			<input type="button" class="input" value="Voltar ao Login - Back to Login" onclick="javascript.document.forms['comeLogin'].submit();"/>
           			</td>
  				</tr>
			</table>
		</form>
	</body>
</html:html>


