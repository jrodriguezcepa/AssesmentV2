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
			background-color: #FFFFFF;
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
			<html:hidden property="assesment" value='<%=String.valueOf(AssesmentData.ANGLO_3)%>'/>
			<html:hidden property="role" value="anglobr"/>
			<html:hidden property="birthDay" value="1"/>
			<html:hidden property="birthMonth" value="1"/>
			<html:hidden property="birthYear" value="1970"/>
			<table width="990" height="594" border="0" align="center" cellpadding="0" cellspacing="0"  class="tableaccesscodeAnglo">
			  	<tr height="125">
			  		<td>&nbsp;</td>
			  	</tr>
			  	<tr>
    				<td align="center" valign="top">
    					<table width="60%" border="0" align="center" cellpadding="0" cellspacing="0" valign="top">
					      	<tr class="userdataAnglo" height="25">
					        	<th align="left"> 
									Usu&aacute;rio:<span class="required">*</span>
								</th>
        						<td align="right">
				            		<html:text property="loginname" size="25" styleClass="inputPepsico"/>
					          	</td>
					      	</tr>
					      	<tr class="userdataAnglo" height="25">
        						<th align="left">
									Senha:<span class="required">*</span>
								</th>
        						<td align="right">
           							<html:password property="password" size="25" styleClass="inputPepsico"/>
          						</td>
      						</tr>
					      	<tr class="userdataAnglo" height="25">
        						<th align="left">
									Confirmar senha:<span class="required">*</span>
								</th>
        						<td align="right">
           							<html:password property="rePassword" size="25" styleClass="inputPepsico"/>
          						</td>
      						</tr>
					      	<tr class="userdataAnglo" height="25">
        						<th align="left" >Nome:<span class="required">*</span></th>
        						<td align="right">
           							<html:text property="firstName" size="25" styleClass="inputPepsico"/>
          						</td>
      						</tr>
					      	<tr class="userdataAnglo" height="25">
        						<th align="left">Sobrenome:<span class="required">*</span></th>
        						<td align="right">
           							<html:text property="lastName" size="25" styleClass="inputPepsico"/>
          						</td>
      						</tr>
					      	<tr class="userdataAnglo" height="25">
        						<th align="left"> 
        							Email:<span class="required">*</span>
								</th>
        						<td align="right">
           							<html:text property="email" size="35" styleClass="inputPepsico"/>
          						</td>
      						</tr>
					      	<tr  class="userdataAnglo" height="25">
        						<th align="left"> 
        							Confirmar email:<span class="required">*</span>
								</th>
        						<td align="right">
           							<html:text property="emailConfirmation" size="35" styleClass="inputPepsico"/>
          						</td>
      						</tr>
					      	<tr  class="userdataAnglo" height="25">
        						<th align="left"> 
        							Telefone:
								</th>
        						<td align="right">
           							<html:text property="vehicle" size="35" styleClass="inputPepsico"/>
          						</td>
      						</tr>
      						<tr  class="userdataAnglo" height="25">
								<th align="left">Vencimento da CNH:</th>
								<td align="right">
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
					      	<tr class="userdataAnglo" height="25">
        						<th align="left" >Estado:<span class="required">*</span></th>
        						<td align="right">
									<html:select property="sex" styleClass="inputPepsico">
								        <html:option value="0">Selecione uma opc&ccedil;&atilde;o</html:option>
								        <html:option value="1">S&atilde;o Paulo</html:option>
								        <html:option value="2">Minas Gerais</html:option>
								        <html:option value="3">Rio Grande do Sul</html:option>
								        <html:option value="4">Paran&aacute;</html:option>
								        <html:option value="5">Rio de Janeiro</html:option>
								        <html:option value="6">Goi&aacute;s</html:option>
								        <html:option value="7">Santa Catarina</html:option>
								        <html:option value="8">Bahia</html:option>
								        <html:option value="9">Par&aacute;</html:option>
								        <html:option value="10">Distrito Federal</html:option>
								        <html:option value="11">Mato Grosso</html:option>
								        <html:option value="12">Maranh&atilde;o</html:option>
								        <html:option value="13">Pernambuco</html:option>
								        <html:option value="14">Cear&aacute;</html:option>
								        <html:option value="15">Mato Grosso do Sul</html:option>
								        <html:option value="16">Esp&iacute;rito Santo</html:option>
								        <html:option value="17">Amazonas</html:option>
								        <html:option value="18">Para&iacute;ba</html:option>
								        <html:option value="19">Piau&iacute;</html:option>
								        <html:option value="20">Rio Grande do Norte</html:option>
								        <html:option value="21">Rond&ocirc;nia</html:option>
								        <html:option value="22">Alagoas</html:option>
								        <html:option value="23">Sergipe</html:option>
								        <html:option value="24">Tocantins</html:option>
								        <html:option value="25">Amap&aacute;</html:option>
								        <html:option value="26">Acre</html:option>
								        <html:option value="27">Roraima</html:option>
									</html:select>
      							</td>
							</tr>
					      	<tr class="userdataAnglo" height="25">
        						<th align="left" >Opera&ccedil;&atilde;o:<span class="required">*</span></th>
        						<td align="right">
									<html:select property="country" styleClass="inputPepsico">
								        <html:option value="0">Selecione uma opc&ccedil;&atilde;o</html:option>
										<html:option value="1">AFB MINAS RIO - IMPLANTA&Ccedil;&Atilde;O</html:option>
        								<html:option value="2">AFB MINAS RIO - OPERA&Ccedil;&Atilde;O</html:option>
									</html:select>
      							</td>
							</tr>
					      	<tr class="userdataAnglo" height="25">
        						<th align="left" >Divis&atilde;o:<span class="required">*</span></th>
        						<td align="right">
									<html:select property="nationality" styleClass="inputPepsico">
								        <html:option value="0">Selecione uma opc&ccedil;&atilde;o</html:option>
								        <html:option value="1">BENEFICIAMENTO</html:option>
								        <html:option value="2">MINERODUTO</html:option>
								        <html:option value="3">ADM</html:option>
								        <html:option value="4">GEOLOGIA</html:option>
								        <html:option value="5">GEST&Atilde;O FUNDIARIA</html:option>
								        <html:option value="6">GPO</html:option>
								        <html:option value="7">MEIO AMBIENTE</html:option>
								        <html:option value="8">RCC</html:option>
									</html:select>
      							</td>
							</tr>
					      	<tr class="userdataAnglo" height="25">
        						<th align="left" >Subdivis&atilde;o:</th>
        						<td align="right">
									<html:select property="location" styleClass="inputPepsico">
								        <html:option value="0">Selecione uma opc&ccedil;&atilde;o</html:option>
								        <html:option value="1">ARG</html:option>
								        <html:option value="2">CRA</html:option>
								        <html:option value="3">K-WAY</html:option>
								        <html:option value="4">STA. BARBARA</html:option>
								        <html:option value="5">CCCC</html:option>
								        <html:option value="6">INTEGRAL</html:option>
								        <html:option value="7">LOGOS</html:option>
								        <html:option value="8">ADMINISTRA&Ccedil;&Atilde;O</html:option>
								        <html:option value="9">COMUNICA&Ccedil;&Atilde;O</html:option>
								        <html:option value="10">EXPANS&Atilde;O</html:option>
								        <html:option value="11">JURIDICO</html:option>
								        <html:option value="12">RH</html:option>
								        <html:option value="13">SEG EMPRESARIAL</html:option>
								        <html:option value="14">SSO</html:option>
								        <html:option value="15">SUPRIMENTOS</html:option>
								        <html:option value="16">TI</html:option>
								        <html:option value="17">SERVITEC</html:option>
								        <html:option value="18">AVALICON</html:option>
								        <html:option value="19">CARVALHO PEREIRA</html:option>
								        <html:option value="20">BENEFICIAMENTO</html:option>
								        <html:option value="21">CONFIABILIDADE</html:option>
								        <html:option value="22">MANUTEN&Ccedil;&Atilde;O</html:option>
								        <html:option value="23">MINA</html:option>
								        <html:option value="24">MINERODUTO</html:option>
								        <html:option value="25">EPA</html:option>
								        <html:option value="26">ERG</html:option>
								        <html:option value="27">GEO NATURA</html:option>
								        <html:option value="28">NOVA LUZ</html:option>
								        <html:option value="29">SCIENTIA</html:option>
								        <html:option value="30">URB TOPO</html:option>
								        <html:option value="31">RCC</html:option>
									</html:select>
      							</td>
							</tr>
					      	<tr height="15">
					      		<td></td>
					      	</tr>
					      	<tr height="25">
        						<td align="right" colspan="2">
						            <html:submit styleClass="inputPepsico">
										Aceitar
									</html:submit>
						            <html:cancel styleClass="inputPepsico">
										Voltar &agrave; sess&atilde;o
									</html:cancel>
          						</td>
      						</tr>
    					</table>
    				</td>
	  			</tr>
			</table>
			<html:hidden property="language" value="pt" />/
		</html:form>
	</body>
<%	}
%>	
</html:html>


