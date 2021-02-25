<%@ page language="java"
	import="assesment.business.AssesmentAccess"
	import="assesment.communication.language.Text"
	import="assesment.presentation.translator.web.util.Util"
	import="java.util.Collection"
	import="java.util.Iterator"
	import="assesment.business.administration.user.*"
	import="assesment.communication.language.*"
	import="assesment.communication.administration.user.tables.*"
	import="assesment.presentation.translator.web.user.*"
	import="assesment.communication.language.tables.*"
	import="assesment.communication.administration.user.*"
	import="assesment.communication.administration.corporation.tables.*"
	import="assesment.communication.security.*"
%>
<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>
<%!
	String Reference;	
%>
<%
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	if(sys==null){
		response.sendRedirect("../assesment/index.jsp?session=1");
	}	
	UserSessionData userSession=sys.getUserSessionData();
	Reference = request.getParameter("refer");
	if(Reference == null) {
	    Reference = (String)session.getAttribute("url");  
	}
	String check = Util.checkPermission(sys,SecurityConstants.PEPSICO_CANDIDATOS);
	if(check!=null) {
		response.sendRedirect(request.getContextPath()+check);
	}else { 		
%>
<html>
	<head>
		<title>Assesment</title>
		<LINK REL=StyleSheet HREF="util/css/estilo.css" TYPE="text/css">
		<link rel="STYLESHEET" type="text/css" href="./css/menu.css"> 
		<META http-equiv="Cache-Control" content="no-cache">
		<META http-equiv="Pragma" content="no-cache">
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
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
.style1 {
	font-size: 4px;
	color: #FFFFFF;
}
 -->
		</style>
	
	</head>
	<body>

		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<form name="logout" action="./logout.jsp" method="post"></form>
			<form name="home" action="./layout.jsp" method="post"></form>
  			<tr>
    			<td colspan="2" valign="top">
					<%@ include file="./topo.jsp"%>
    			</td><!--end the top�s table -->
  			</tr>
  			<tr>
    			<td width="100%" height="60" valign="top">
    				<span class="style1"> </span>
      			</td>
  			</tr>
  			<tr>
    			<td valign="top" align="center">
    				<table width="800" border="0" align="center" cellpadding="0" cellspacing="0" valign="top">
      					<tr height="30">
			    			<td width="80%" height="40" align="left">
			    				<span class="menu">Listado de Estado de Cuentas</span>
			      			</td>
			    			<td width="100%" height="40" valign="top">
			    				<span class="style1">
									<html:form action="/JavaReport">
										<html:hidden property="type" value="4"/>
										<html:submit value='Exportar a EXCEL' styleClass="input"/>
			    					</html:form>
			    				</span>
			      			</td>
			  			</tr>
    				</table>
    			</td>
  			</tr>
  			<tr>
    			<td width="100%" height="20" valign="top">
    				<span class="style1"> </span>
      			</td>
  			</tr>
  			<tr>
    			<td valign="top" align="center">
    				<table width="800" border="0" align="center" cellpadding="0" cellspacing="0" valign="top">
      					<tr height="30">
        					<td class="guide2">Nombre</td>
        					<td class="guide2">Apellido</td>
        					<td class="guide2">Producto</td>
        					<td class="guide2">CEDIS</td>
        					<td class="guide2">Estado de la cuenta </td>
        					<td class="guide2">&Uacute;ltima fecha de acceso</td>
      					</tr>
<%			String[][] values = sys.getUserReportFacade().getCandidatos(sys.getUserSessionData());
			String line = "linetwo";
			for(int i = 0; i < values.length; i++) {
%>						<tr height="20">
<%				for(int j = 0; j < values[i].length; j++) {
%>        					<td  align="left" valign="middle" class='<%=line%>'><%=(values[i][j] == null) ? "---" : values[i][j]%></td>
<%				}
				line = (line.equals("linetwo")) ? "lineone" : "linetwo";
%>						</tr>
<%			}
%>    				</table>
    			</td>
  			</tr>
		</table>
	</body>
</html>
<%	}
%>