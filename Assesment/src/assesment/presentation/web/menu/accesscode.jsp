<%@ page language="java"
	import="assesment.business.*"	
	import="assesment.communication.language.*"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html" 
%>

<%@ taglib uri="/WEB-INF/struts-bean.tld"
        prefix="bean" 
%>

<%	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	Text messages = sys.getText();
%>
<html:html>

	<head>
		<title> CEPA </title>

		
		<link href='<%=request.getContextPath()+"/util/css/estilo.css"%>' rel="stylesheet" type="text/css">
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"><style type="text/css">
		
		<!--
		body {
			background-color: #000000;
		}
		-->
		</style>
	</head>

	<body>
		<html:form action="/AccessCode" >
			<table width="990" height="594" border="0" align="center" cellpadding="0" cellspacing="0" class="tableaccesscode">
			  	<tr>
    				<td align="center" valign="middle">
    					<table>
<%		if(request.getParameter("error") != null && request.getParameter("error").length() > 0) {
%>    						<tr>
    							<td class="redBig" align="center" valign="top">
    								<%=messages.getText("accesscode.error."+request.getParameter("error")) %>
    							</td>
    						</tr>	
<%		}
%>    						<tr>
    							<th class="input">
    								Access Code / Código de Acceso / Código de Aceso
    							</th>
    						</tr>
    						<tr>	
    							<td>
    								<html:text property="accesscode" size="50" styleClass="input" />
    							</td>
    						</tr>
    						<tr><td height="25"></td></tr>
    						<tr>
    							<td align="right" >
    								<html:submit value="Accept/Aceptar/Aceitar" styleClass="input" />
    								<html:cancel value="Cancel/Cancelar" styleClass="input" />
    							</td>
    						</tr>
    					</table>
    				</td>
	  			</tr>
			</table>
		</html:form>
	</body>
</html:html>


