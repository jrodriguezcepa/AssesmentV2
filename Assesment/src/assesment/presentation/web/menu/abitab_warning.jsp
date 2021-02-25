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
		<form name="logout" id="logout" action="./logout.jsp" method="post">
		<table width="990" height="594" border="0" align="center" cellpadding="0" cellspacing="0" class="tableaccesscode">
			<tr>
    			<td align="center" valign="middle">
    				<table>
    					<tr>
    						<td class="menu">
    							Muchas gracias por su participaci&oacute;n!!!
    						</td>
    					</tr>
    					<tr>	
    						<td>
    							&nbsp;
    						</td>
    					</tr>
    					<tr>
    						<td class="menu">
    							Para continuar con la capacitaci&oacute;n podr&aacute; comprar un link 
    							<br>
    							para un e-learning personalizado en cualquier agencia Abitab del pa&iacute;s.
    						</td>
    					</tr>
    					<tr>
    						<td>
    							&nbsp;
    						</td>
    					</tr>
    					<tr>
    						<td>
    							&nbsp;
    						</td>
    					</tr>
    					<tr>
    						<td align="center">
    							<input type="button" value="Salir" title="Salir" onClick="javascript:document.forms[0].submit()" />
    						</td>
    					</tr>
    					
   					</table>
   				</td>
  			</tr>
		</table>
		</form>
	</body>
</html:html>


