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
  
  <!-- **********************end JCalendar ********************************* -->
	<script language="javascript">
		function onSelectBirth(calendar, date) {
			var date2 = calendar.date;
			var time = date2.getTime();
	  		var input_day = document.getElementById("dayBirth");
	  		var input_month = document.getElementById("monthBirth");
	  		var input_year = document.getElementById("yearBirth");
	  		var date3 = new Date(time);
	  		input_day.value = date3.print("%d");
	  		input_month.value = date3.print("%m");
	  		input_year.value= parseInt(date3.print("%Y"),10);
	  	}
		function onSelectLicense(calendar, date) {
			var date2 = calendar.date;
			var time = date2.getTime();
	  		var input_day = document.getElementById("dayLicense");
	  		var input_month = document.getElementById("monthLicense");
	  		var input_year = document.getElementById("yearLicense");
	  		var date3 = new Date(time);
	  		input_day.value = date3.print("%d");
	  		input_month.value = date3.print("%m");
	  		input_year.value= parseInt(date3.print("%Y"),10);
	  	}
		function onSelectExpire(calendar, date) {
			var date2 = calendar.date;
			var time = date2.getTime();
	  		var input_day = document.getElementById("dayExpire");
	  		var input_month = document.getElementById("monthExpire");
	  		var input_year = document.getElementById("yearExpire");
	  		var date3 = new Date(time);
	  		input_day.value = date3.print("%d");
	  		input_month.value = date3.print("%m");
	  		input_year.value= parseInt(date3.print("%Y"),10);
	  	}
	</script>

	<body>
		<html:form action="/UserData" >
			<html:hidden property="assesment" value='<%=String.valueOf(AssesmentData.ABITAB)%>'/>
			<html:hidden property="role" value='<%=request.getParameter("accesscode")%>'/>
			<table width="990" height="594" border="0" align="center" cellpadding="0" cellspacing="0"  class="tableaccesscodeAbitab">
			  	<tr height="200">
			  		<td>&nbsp;</td>
			  	</tr>
			  	<tr>
    				<td align="center" valign="top">
    					<table width="60%" border="0" align="center" cellpadding="0" cellspacing="0" valign="top">
					      	<tr  class="userdataPepsico">
					        	<th align="left"> 
									C&eacute;dula de Identidad (sin espacios, puntos o guiones)<span class="required">*</span>
								</th>
        						<td align="right">
				            		<html:text property="loginname" size="25" styleClass="inputPepsico"/>
					          	</td>
					      	</tr>
					      	<tr  class="userdataPepsico">
        						<th align="left" >Nombre/s<span class="required">*</span></th>
        						<td align="right">
           							<html:text property="firstName" size="25" styleClass="inputPepsico"/>
          						</td>
      						</tr>
					      	<tr  class="userdataPepsico">
        						<th align="left">Apellido/s<span class="required">*</span></th>
        						<td align="right">
           							<html:text property="lastName" size="25" styleClass="inputPepsico"/>
          						</td>
      						</tr>
					      	<tr  class="userdataPepsico">
        						<th align="left"> 
        							email<span class="required">*</span>
								</th>
        						<td align="right">
           							<html:text property="email" size="35" styleClass="inputPepsico"/>
          						</td>
      						</tr>
					      	<tr  class="userdataPepsico">
        						<th align="left"> 
        							Confirma email<span class="required">*</span>
								</th>
        						<td align="right">
           							<html:text property="emailConfirmation" size="35" styleClass="inputPepsico"/>
          						</td>
      						</tr>
					      	<tr  class="userdataPepsico">
								<th align="left">Fecha de nacimiento<span class="required">*</span></th>
								<td align="right">
									<html:text property="birthDay" style="width:20;" styleClass="inputPepsico" styleId="dayBirth" />/
	           						<html:text property="birthMonth" style="width:20;" styleClass="inputPepsico" styleId="monthBirth" />/
	           						<html:text property="birthYear" style="width:40;" styleClass="inputPepsico" styleId="yearBirth"/>
	           						<html:img page="/component/jscalendar-1.0/img.gif" styleId="calendar_buttonBirth" style="cursor: pointer; border: 1px solid red;" onmouseover="this.style.background='red';" style="width:20;" align="middle" onmouseout="this.style.background=''"/>
	           						<script>
										Calendar.setup({
								        	inputArea     :    "yearBirth",
								        	ifFormat       :    "%d/%m/%Y",
								        	showsTime      :    true,
								        	timeFormat     :    "24",
								        	button         :    "calendar_buttonBirth",
								        	step           :    1,
								        	onSelect       :    onSelectBirth,
								        	singleClick    :    true
								    	});
								    </script>
	          					</td>
		          			</tr>
					      	<tr  class="userdataPepsico">
        						<th align="left"> 
        							Tel&eacute;fono particular
								</th>
        						<td align="right">
           							<html:text property="company" size="35" styleClass="inputPepsico"/>
          						</td>
      						</tr>
					      	<tr  class="userdataPepsico">
        						<th align="left"> 
        							Tel&eacute;fono celular
								</th>
        						<td align="right">
           							<html:text property="vehicle" size="35" styleClass="inputPepsico"/>
          						</td>
      						</tr>
					      	<tr  class="userdataPepsico"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
					      	<tr  class="userdataPepsico">
        						<td align="right" colspan="2">
						            <html:submit styleClass="inputPepsico">
										<%=messages.getText("generic.messages.save")%>
									</html:submit>
						            <html:cancel styleClass="inputPepsico">
										<%=messages.getText("generic.messages.reloadsession")%>
									</html:cancel>
          						</td>
      						</tr>
    					</table>
    				</td>
	  			</tr>
			</table>
			<html:hidden property="language" value="es" />/
		</html:form>
	</body>
<%	}
%>	
</html:html>


