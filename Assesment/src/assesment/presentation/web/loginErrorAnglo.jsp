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
		<link rel="STYLESHEET" type="text/css" href="./css/menu.css"> 
	</head>

		<style>
		<!--
		body {
			background-color: #000000;
		}
		-->
		</style>
	<body>
		<form name="error" method="post" action="./login.jsp" >
			<table width="990" height="594" border="0" align="center" cellpadding="0" cellspacing="0"  background="/assesment/util/imgs/error-login.jpg">
	  			<tr>
    				<td width="400" height="218">&nbsp;</td>
    				<td valign="bottom" align="center">
   						&nbsp;<img src='/assesment/util/imgs/back_es.jpg' border=0 align=center onclick="javascript:document.forms[0].submit()" class="cursor" onclick="javascript:vopenw()" >
					</td>
    				<td  width="400">&nbsp;</td>
			  	</tr>
  				<tr>
    				<td width="400" height="59">&nbsp;</td>
    				<td valign="bottom" align="center">
   						&nbsp;<img src='/assesment/util/imgs/back_pt.jpg' border=0 align=center onclick="javascript:document.forms[0].submit()" class="cursor" onclick="javascript:vopenw()" >
					</td>
    				<td width="400">&nbsp;</td>
  				</tr>
  				<tr>
    				<td colspan="3" height="27">&nbsp;</td>
    			</tr>	
  				<tr>
    				<td width="400">&nbsp;</td>
    				<td valign="top" align="center">
   						&nbsp;<img src='/assesment/util/imgs/back_en.jpg' border=0 align=center onclick="javascript:document.forms[0].submit()" class="cursor" onclick="javascript:vopenw()" >
					</td>
    				<td width="400">&nbsp;</td>
  				</tr>
			</table>
		</form>
	</body>
</html>
