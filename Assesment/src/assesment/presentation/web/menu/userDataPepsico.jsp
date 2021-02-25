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

		Integer assesment = (Integer)session.getAttribute("assesment");
		AssesmentAttributes assesmentAttr = sys.getAssesmentReportFacade().findAssesmentAttributes(assesment,sys.getUserSessionData());
		int corporationId = assesmentAttr.getCorporationId().intValue();
		session.removeAttribute("assesment");
		
		String accesscode = (String)session.getAttribute("accesscode");
		session.removeAttribute("accesscode");

		CountryConstants cc = new CountryConstants(messages);
        Collection list = new LinkedList();
        list.add(new OptionItem(messages.getText("generic.messages.select"),""));
        Iterator it = cc.getCountryIterator();
        while(it.hasNext()) {
            CountryData data = (CountryData)it.next();
            list.add(new OptionItem(data.getName(),data.getId()));
        }
		session.setAttribute("countryList",list);
		
        Collection list3 = new LinkedList();
        list3.add(new OptionItem("Ninguna - Candidato","0"));
        list3.add(new OptionItem("Div 1","1"));
        list3.add(new OptionItem("Div 2","2"));
        list3.add(new OptionItem("Div 3","3"));
        list3.add(new OptionItem("Div 4","4"));
		session.setAttribute("companyList",list3);

		session.setAttribute("UserDataForm",new UserCreateForm());
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
			<html:hidden property="assesment" value='<%=String.valueOf(assesment)%>'/>
			<html:hidden property="role" value='<%=accesscode%>'/>
			<table width="990" height="594" border="0" align="center" cellpadding="0" cellspacing="0"  class="tableaccesscodePepsico">
			  	<tr height="145">
			  		<td>&nbsp;</td>
			  	</tr>
			  	<tr>
    				<td align="center" valign="top">
    					<table width="60%" border="0" align="center" cellpadding="0" cellspacing="0" valign="top">
					      	<tr  class="userdataPepsico"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
					      	<tr  class="userdataPepsico">
					        	<th align="left"> 
									Usuario (se recomienda usar letras min&uacute;sculas y n&uacute;meros sin espacios):<span class="required">*</span>
								</th>
        						<td align="right">
				            		<html:text property="loginname" size="25" styleClass="inputPepsico"/>
					          	</td>
					      	</tr>
					      	<tr  class="userdataPepsico">
        						<th align="left">
									Clave (se recomienda usar letras min&uacute;sculas y n&uacute;meros sin espacios):<span class="required">*</span>
								</th>
        						<td align="right">
           							<html:password property="password" size="25" styleClass="inputPepsico"/>
          						</td>
      						</tr>
					      	<tr  class="userdataPepsico">
        						<th align="left">
									Confirma clave:<span class="required">*</span>
								</th>
        						<td align="right">
           							<html:password property="rePassword" size="25" styleClass="inputPepsico"/>
          						</td>
      						</tr>
					      	<tr  class="userdataPepsico">
        						<th align="left" >Nombre/s:<span class="required">*</span></th>
        						<td align="right">
           							<html:text property="firstName" size="25" styleClass="inputPepsico"/>
          						</td>
      						</tr>
					      	<tr  class="userdataPepsico">
        						<th align="left">Apellido/s:<span class="required">*</span></th>
        						<td align="right">
           							<html:text property="lastName" size="25" styleClass="inputPepsico"/>
          						</td>
      						</tr>
					      	<tr  class="userdataPepsico">
        						<th align="left" >Pa&iacute;s de nacimiento:<span class="required">*</span></th>
        						<td align="right">
									<html:select property="country" styleClass="inputPepsico" style="width:160; ">
										<html:options collection="countryList" property="value" labelProperty="label"/>
									</html:select>
      							</td>
							</tr>
					      	<tr  class="userdataPepsico">
								<th align="left">Fecha de nacimiento:<span class="required">*</span></th>
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
        							e-mail:<span class="required">*</span>
								</th>
        						<td align="right">
           							<html:text property="email" size="35" styleClass="inputPepsico"/>
          						</td>
      						</tr>
					      	<tr  class="userdataPepsico">
        						<th align="left"> 
        							Confirme e-mail:<span class="required">*</span>
								</th>
        						<td align="right">
           							<html:text property="emailConfirmation" size="35" styleClass="inputPepsico"/>
          						</td>
      						</tr>
					      	<tr  class="userdataPepsico">
        						<th align="left"> 
        							Tel&eacute;fonos (celular y particular):
								</th>
        						<td align="right">
           							<html:text property="company" size="35" styleClass="inputPepsico"/>
          						</td>
      						</tr>
					      	<tr  class="userdataPepsico">
								<th align="left">Fecha de expedici&oacute;n de la licencia de conducir:</th>
								<td align="right">
									<html:text property="startDay" style="width:20;" styleClass="inputPepsico" styleId="dayLicense" />/
	           						<html:text property="startMonth" style="width:20;" styleClass="inputPepsico" styleId="monthLicense" />/
	           						<html:text property="startYear" style="width:40;" styleClass="inputPepsico" styleId="yearLicense"/>
	           						<html:img page="/component/jscalendar-1.0/img.gif" styleId="calendar_buttonLicense" style="cursor: pointer; border: 1px solid red;" onmouseover="this.style.background='red';" style="width:20;" align="middle" onmouseout="this.style.background=''"/>
	           						<script>
										Calendar.setup({
								        	inputArea     :    "yearLicense",
								        	ifFormat       :    "%d/%m/%Y",
								        	showsTime      :    true,
								        	timeFormat     :    "24",
								        	button         :    "calendar_buttonLicense",
								        	step           :    1,
								        	onSelect       :    onSelectLicense,
								        	singleClick    :    true
								    	});
								    </script>
	          					</td>
		          			</tr>
					      	<tr  class="userdataPepsico">
								<th align="left">Fecha de vencimiento de la licencia de conducir:</th>
								<td align="right">
	           						<html:checkbox property="expiryType">Sin Vencimiento</html:checkbox>
	           						&nbsp;&nbsp;
									<html:text property="expiryDay" style="width:20;" styleClass="inputPepsico" styleId="dayExpire" />/
	           						<html:text property="expiryMonth" style="width:20;" styleClass="inputPepsico" styleId="monthExpire" />/
	           						<html:text property="expiryYear" style="width:40;" styleClass="inputPepsico" styleId="yearExpire"/>
	           						<html:img page="/component/jscalendar-1.0/img.gif" styleId="calendar_buttonExpire" style="cursor: pointer; border: 1px solid red;" onmouseover="this.style.background='red';" style="width:20;" align="middle" onmouseout="this.style.background=''"/>
	           						<script>
										Calendar.setup({
								        	inputArea     :    "yearExpire",
								        	ifFormat       :    "%d/%m/%Y",
								        	showsTime      :    true,
								        	timeFormat     :    "24",
								        	button         :    "calendar_buttonExpire",
								        	step           :    1,
								        	onSelect       :    onSelectExpire,
								        	singleClick    :    true
								    	});
								    </script>
	          					</td>
		          			</tr>
					      	<tr  class="userdataPepsico">
        						<th align="left" >Antig&uuml;edad como empleado de PEPSICO M&eacute;xico aproximada:</th>
        						<td align="right">
									<html:select property="location" styleClass="inputPepsico" style="width:160; ">
										<html:option value="0">Candidato</html:option>
										<html:option value="1">Menos de 3 meses</html:option>
										<html:option value="2">Menos de 6 meses</html:option>
										<html:option value="3">1 a&ntilde;o</html:option>
										<html:option value="4">2 a&ntilde;os</html:option>
										<html:option value="5">3 a&ntilde;os</html:option>
										<html:option value="6">4 a&ntilde;os</html:option>
										<html:option value="7">5 a&ntilde;os</html:option>
										<html:option value="8">6 a&ntilde;os o m&aacute;s</html:option>
									</html:select>
      							</td>
							</tr>
					      	<tr  class="userdataPepsico">
        						<th align="left" >Divisi&oacute;n en la cual trabaja o va a trabajar:</th>
        						<td align="right">
									<html:select property="nationality" styleClass="inputPepsico" style="width:160; ">
										<html:options collection="companyList" property="value" labelProperty="label"/>
									</html:select>
      							</td>
							</tr>
					      	<tr  class="userdataPepsico"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
					      	<tr  class="userdataPepsico">
        						<td align="right" colspan="2">
						            <html:submit styleClass="inputPepsico">
										<%=messages.getText("generic.messages.save")%>
									</html:submit>
						            <html:cancel styleClass="inputPepsico">
										<%=messages.getText("generic.messages.cancel")%>
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


