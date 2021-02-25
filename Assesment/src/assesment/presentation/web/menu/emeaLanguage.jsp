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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"><style type="text/css">
		
		<!--
		body {
			background-color: #000000;
		}
		-->
		</style>
	</head>
<%
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	String check = Util.checkPermission(sys,SecurityConstants.ACCESSCODE);
	if(check!=null) {
		response.sendRedirect(request.getContextPath()+check);
	}else {
		Text messages = sys.getText();

		RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
		dispatcher.include(request,response);
	
%>

	<body>
		<html:form action="/CreateUserEMEA" >
			<html:hidden property="loginname" value='<%=String.valueOf(request.getParameter("accesscode")) %>' />
			<table width="955" height="568" border="0" align="center" cellpadding="0" cellspacing="0"  class="tablelanguage">
			  	<tr height="225">
			  		<td>&nbsp;</td>
			  	</tr>
			  	<tr>
    				<td align="center" valign="top">
    					<table width="40%" border="0" align="center" cellpadding="0" cellspacing="0" valign="top">
    						<tr class="userdata" height="50"><td>&nbsp;</td></tr>
					      	<tr  class="userdata">
        						<th align="right"  class="txtlanguage">Select Language:</th>
        						<td align="left">
									<html:select property="language" styleClass="input" style="width:160; ">
										<html:option value="cz">Czech</html:option>
										<html:option value="en">English</html:option>
										<html:option value="fr">French</html:option>
										<html:option value="de">German</html:option>
										<html:option value="hu">Hungarian</html:option>
										<html:option value="it">Italian</html:option>
										<html:option value="pl">Polish</html:option>
										<html:option value="pt">Portuguese</html:option>
										<html:option value="ro">Romanian</html:option>
										<html:option value="ru">Russian</html:option>
										<html:option value="es">Spanish</html:option>
										<html:option value="tr">Turkish</html:option>
									</html:select>
      							</td>
							</tr>
    						<tr class="userdata"><td>&nbsp;</td></tr>
					      	<tr  class="userdata">
        						<td align="right" colspan="2" >
						            <html:submit styleClass="txtlanguage">
										Next
									</html:submit>
          						</td>
      						</tr>
    					</table>
    				</td>
	  			</tr>
			</table>
		</html:form>
	</body>
<%	}
%>	
</html:html>


