<%@ page language="java"
	import="assesment.business.*"
	import="assesment.communication.language.*"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
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
		<form name="error" method="post" action="./login.jsp" >
			<table width="499"  border="0" align="center" cellpadding="0" cellspacing="0" class="table">
  				<tr>
    				<td colspan="4"><img src='<%=request.getContextPath()+"/util/imgs/topo_login.jpg"%>' height="90" width="499"></td>
	  			</tr>
		  		<tr>
    				<td colspan="4">&nbsp;</td>
  				</tr>
	  			<tr>
    				<td width="4" height="24"><div align="center"></div></td>
    				<td width="417"><b><%=Text.getMessage("generic.error.permissions")%></b></td>
    				<td width="4"><div align="center"></div></td>
    				<td width="4"><div align="center"></div></td>
	  			</tr>
			</table>
		</form>
	</body>
</html>
