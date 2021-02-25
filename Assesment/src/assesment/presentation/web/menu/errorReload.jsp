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
	String check = Util.checkPermission(sys,SecurityConstants.ACCESSCODE);
	if(check!=null) {
		response.sendRedirect(request.getContextPath()+check);
	}else {
		Text messages = sys.getText();

		RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
		dispatcher.include(request,response);

%>
<!-- *************************JCalendar***********************************-->
 <!-- calendar stylesheet -->
  <link rel="stylesheet" type="text/css" media="all" href="/assesment/component/jscalendar-1.0/skins/aqua/theme.css" title="win2k-cold-1" />

  <!-- main calendar program -->
  <script type="text/javascript" src="/assesment/component/jscalendar-1.0/calendar.js"></script>

  <!-- language for the calendar-->
	<script type="text/javascript" src="/assesment/component/jscalendar-1.0/lang/calendar-es.js"></script>
  <!-- the following script defines the Calendar.setup helper function, which makes
       adding a calendar a matter of 1 or 2 lines of code. -->
  <script type="text/javascript" src="/assesment/component/jscalendar-1.0/calendar-setup.js"></script>
  
	<body>
		<center>
			<table width="990" height="594" border="0" align="center" cellpadding="0" cellspacing="0"  class="tablereloadSessionAbitab">
			  	<tr height="220">
			  		<td>&nbsp;</td>
			  	</tr>
			  	<tr>
    				<td align="center" valign="top">
    					<table width="35%" border="0" align="center" cellpadding="0" cellspacing="0" valign="top">
					      	<tr  class="userdataPepsico">
					        	<th align="left" colspan="2"> 
									No hay usuario registrado para esta c&eacute;dula y email
								</th>
					      	</tr>
					      	<tr  class="userdataPepsico"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
					      	<tr  class="userdataPepsico">
        						<td align="center" colspan="3">
						            <a href="reloadSessionAbitab.jsp">
										<%=messages.getText("generic.messages.back")%>
									</a>
						            <a href="userDataAbitab.jsp">
										<%=messages.getText("generic.messages.newuser")%>
									</a>
          						</td>
      						</tr>
    					</table>
    				</td>
	  			</tr>
			</table>
		</center>
	</body>
<%	}
%>	
</html:html>


