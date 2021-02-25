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
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html" 
%>

<%@ taglib uri="/WEB-INF/struts-bean.tld"
        prefix="bean" 
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
	
<%
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	String check = Util.checkPermission(sys,SecurityConstants.BASF);
	if(check!=null) {
		response.sendRedirect(request.getContextPath()+check);
	}else {
		Text messages = sys.getText();

		RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
		dispatcher.include(request,response);
		
		String rg = request.getParameter("rg");
		String cpf = request.getParameter("cpf"); 
		
		UserData userData = sys.getUserReportFacade().findBasfUser(rg,cpf, sys.getUserSessionData());
%>
	<body>
		<form name="back" action="basfUser.jsp" >
		</form>

<%		if(userData != null) {
%>		  	
		<html:form action="/BasfExistingUser" >
			<html:hidden property="user" value='<%=userData.getLoginName()%>'/>
		</html:form>
		<table width="990" height="594" border="0" align="center" cellpadding="0" cellspacing="0"  class="tableaccessBasf">
		  	<tr height="100">
		  		<td>&nbsp;</td>
		  	</tr>
		  	<tr height="100" valign="bottom">
		  		<td width="200">&nbsp;</td>
		  		<th align="left" colspan="2"  width="490">
		  			<span style="font-family: Verdana; font-size: 1.9em; font-weight: bold;">
		  				BASF Assessment
		  			</span>
		  		</th>
		  		<td width="200">&nbsp;</td>
		  	</tr>
		  	<tr height="50" valign="top">
		  		<td width="200">&nbsp;</td>
		  		<td colspan="2">
		  			<span style="font-family: Verdana; font-size: 1.25em; font-weight: bold; color: #FF0000;">
		  				<%=messages.getText("assessment.basf.confirm") %>
		  			</span>
		  		</td>
		  	</tr>
		  	<tr valign="middle">
		  		<td width="200">&nbsp;</td>
		  		<th align="left" width="190">
		  			<%=messages.getText("assessment.basf.driver") %>:
				</th>
		  		<td align="left" width="300">
		  			<%= userData.getFirstName()+" "+userData.getLastName()%>
		  		</td>
		  		<td width="200">&nbsp;</td>
		  	</tr>
		  	<tr valign="middle">
		  		<td width="200">&nbsp;</td>
		  		<th align="left" width="190">
		  			<%=messages.getText("assessment.basf.rg") %>:
				</th>
		  		<td align="left" width="300">
		  			<%=rg %>
		  		</td>
		  		<td width="200">&nbsp;</td>
		  	</tr>
		  	<tr valign="middle">
		  		<td width="200">&nbsp;</td>
		  		<th align="left" width="190">
		  			<%=messages.getText("assessment.basf.cpf") %>:
				</th>
		  		<td align="left" width="300">
		  			<%=cpf%>
		  		</td>
		  		<td width="200">&nbsp;</td>
		  	</tr>
		  	<tr height="30" valign="top">
		  		<td width="200">&nbsp;</td>
		  	</tr>
		  	<tr height="100" valign="top">
		  		<td width="200">&nbsp;</td>
		  		<td align="right" colspan="2">
		  			<input type="button" value='<%=messages.getText("generic.messages.confirm")%>' onclick="javascript:document.forms['BasfExistingUserForm'].submit();" style="width: 150px; height: 30px; font-family: Verdana; font-size: 1.25em;"/>
		  			<input type="button" value='<%=messages.getText("generic.messages.back")%>' onclick="javascript:document.forms['back'].submit();" style="width: 100px; height: 30px; font-family: Verdana; font-size: 1.25em;"/>
		  		</td>
		  		<td width="200">&nbsp;</td>
		  	</tr>
		  	<tr height="100">
		  		<td>&nbsp;</td>
		  	</tr>
		</table>
<%	}else {
%>	
		<table width="990" height="594" border="0" align="center" cellpadding="0" cellspacing="0"  class="tableaccessBasf">
		  	<tr height="100">
		  		<td>&nbsp;</td>
		  	</tr>
		  	<tr height="100" valign="bottom">
		  		<td width="200">&nbsp;</td>
		  		<th align="left" colspan="2"  width="490">
		  			<span style="font-family: Verdana; font-size: 1.9em; font-weight: bold;">
		  				BASF Assessment
		  			</span>
		  		</th>
		  		<td width="200">&nbsp;</td>
		  	</tr>
		  	<tr height="50" valign="top">
		  		<td width="200">&nbsp;</td>
		  		<td colspan="2">
		  			<span style="font-family: Verdana; font-size: 1.25em; font-weight: bold; color: #FF0000;">
		  				<%=messages.getText("assessment.basf.usernotfound")%>
		  			</span>
		  		</td>
		  	</tr>
		  	<tr height="100" valign="top">
		  		<td width="200">&nbsp;</td>
		  	</tr>
		  	<tr height="200" valign="top">
		  		<td width="200">&nbsp;</td>
		  		<td align="center" colspan="2">
		  			<input type="button" value='<%=messages.getText("generic.messages.back")%>' onclick="javascript:document.forms['back'].submit();" style="width: 200px; height: 30px; font-family: Verdana; font-size: 1.25em;"/>
		  		</td>
		  		<td width="200">&nbsp;</td>
		  	</tr>
		  	<tr height="100">
		  		<td>&nbsp;</td>
		  	</tr>
		</table>
<%		}
%>


	</body>
<%	}
%>

</html:html>
