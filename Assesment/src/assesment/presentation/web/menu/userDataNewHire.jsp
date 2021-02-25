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

		Collection sexList = new LinkedList();
		sexList.add(new OptionItem(messages.getText("user.sex.female"),String.valueOf(UserData.FEMALE)));
		sexList.add(new OptionItem(messages.getText("user.sex.male"),String.valueOf(UserData.MALE)));
		session.setAttribute("sexList",sexList);

		CountryConstants cc = new CountryConstants(messages);
        Collection list = new LinkedList();
        list.add(new OptionItem(messages.getText("generic.messages.select"),""));
        Iterator it = cc.getCountryIterator();
        while(it.hasNext()) {
            CountryData data = (CountryData)it.next();
            list.add(new OptionItem(data.getName(),data.getId()));
        }
		session.setAttribute("countryList",list);
		
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
		function onSelectStart(calendar, date) {
			var date2 = calendar.date;
			var time = date2.getTime();
	  		var input_day = document.getElementById("dayStart");
	  		var input_month = document.getElementById("monthStart");
	  		var input_year = document.getElementById("yearStart");
	  		var date3 = new Date(time);
	  		input_day.value = date3.print("%d");
	  		input_month.value = date3.print("%m");
	  		input_year.value= parseInt(date3.print("%Y"),10);
	  	}
		function onSelectExpiry(calendar, date) {
			var date2 = calendar.date;
			var time = date2.getTime();
	  		var input_day = document.getElementById("dayExpiry");
	  		var input_month = document.getElementById("monthExpiry");
	  		var input_year = document.getElementById("yearExpiry");
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
			<html:hidden property="birthDay" value="1"/>
			<html:hidden property="birthYear" value="1950"/>
			<html:hidden property="birthMonth" value="1"/>
			<html:hidden property="language" value="en"/>
			<table width="990" height="594" border="0" align="center" cellpadding="0" cellspacing="0"  class="tableaccesscode">
			  	<tr height="145">
			  		<td>&nbsp;</td>
			  	</tr>
			  	<tr>
    				<td align="center" valign="top">
    					<table width="60%" border="0" align="center" cellpadding="0" cellspacing="0" valign="top">
						    <tr  class="simpleTitle">
    							<th align="left" colspan="2"><%=messages.getText("generic.user.data")%></th>
							</tr>
    						<tr class="userdata"><td>&nbsp;</td></tr>
					      	<tr  class="userdata">
					        	<th width="224" align="left"> <%=messages.getText("user.data.nickname")%><span class="required">*</span></th>
        						<td align="right">
				            		<html:text property="loginname" size="25" styleClass="input"/>
					          	</td>
					      	</tr>
					      	<tr  class="userdata">
        						<th align="left"><%=messages.getText("user.data.pass")%><span class="required">*</span></th>
        						<td align="right">
           							<html:password property="password" size="25" styleClass="input"/>
          						</td>
      						</tr>
					      	<tr  class="userdata">
        						<th align="left"> <%=messages.getText("user.data.confirmpassword")%><span class="required">*</span></th>
        						<td align="right">
           							<html:password property="rePassword" size="25" styleClass="input"/>
          						</td>
      						</tr>
					      	<tr  class="userdata">
        						<th align="left" > <%=messages.getText("user.data.firstname")%><span class="required">*</span></th>
        						<td align="right">
           							<html:text property="firstName" size="25" styleClass="input"/>
          						</td>
      						</tr>
					      	<tr  class="userdata">
        						<th align="left"> <%=messages.getText("user.data.lastname")%><span class="required">*</span></th>
        						<td align="right">
           							<html:text property="lastName" size="25" styleClass="input"/>
          						</td>
      						</tr>
							<tr  class="userdata">
        						<th align="left" > <%=messages.getText("user.data.sex")%><span class="required">*</span></th>
        						<td align="right">
									<html:select property="sex" styleClass="input" style="width:160; ">
										<html:options collection="sexList" property="value" labelProperty="label"/>
									</html:select>
      							</td>
							</tr>
					      	<tr  class="userdata">
        						<th align="left" > <%=messages.getText("user.data.country")%><span class="required">*</span></th>
        						<td align="right">
									<html:select property="country" styleClass="input" style="width:160; ">
										<html:options collection="countryList" property="value" labelProperty="label"/>
									</html:select>
      							</td>
							</tr>
					      	<tr  class="userdata">
        						<th align="left" > <%=messages.getText("user.data.nationality")%><span class="required">*</span></th>
        						<td align="right">
									<html:select property="nationality" styleClass="input" style="width:160; ">
										<html:options collection="countryList" property="value" labelProperty="label"/>
									</html:select>
      							</td>
							</tr>
					      	<tr  class="userdata">
        						<th align="left"> 
        							<%=messages.getText("user.newhire.mail")%>
        							<span class="required">*</span>
								</th>
        						<td align="right">
           							<html:text property="email" size="35" styleClass="input"/>
          						</td>
      						</tr>
					      	<tr  class="userdata">
        						<th align="left"> 
        							<%=messages.getText("user.newhire.mailconfirmation")%>
        							<span class="required">*</span>
								</th>
        						<td align="right">
           							<html:text property="emailConfirmation" size="35" styleClass="input"/>
          						</td>
      						</tr>
					      	<tr  class="userdata">
        						<th align="left" > <%=messages.getText("user.data.language")%></th>
        						<td align="right">
        							<%=messages.getText("module.data.name.en")%>
      							</td>
							</tr>
					      	<tr  class="userdata"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
					      	<tr  class="userdata">
        						<td align="right" colspan="2">
						            <html:submit styleClass="input">
										<%=messages.getText("generic.messages.save")%>
									</html:submit>
						            <html:cancel styleClass="input">
										<%=messages.getText("generic.messages.cancel")%>
									</html:cancel>
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


