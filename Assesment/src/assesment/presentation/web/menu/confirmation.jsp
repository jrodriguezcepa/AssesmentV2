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
	
		UserCreateForm form = (UserCreateForm)session.getAttribute("UserDataForm");
		
%>

	<body>
		<html:form action="/Confirmation" >
			<html:hidden property="loginname" value='<%=form.getLoginname()%>' />
			<html:hidden property="password" value='<%=form.getPassword()%>' />
			<html:hidden property="firstName" value='<%=form.getFirstName()%>' />
			<html:hidden property="lastName" value='<%=form.getLastName()%>' />
			<html:hidden property="birthDay" value='<%=form.getBirthDay()%>' />
			<html:hidden property="birthMonth" value='<%=form.getBirthMonth()%>' />
			<html:hidden property="birthYear" value='<%=form.getBirthYear()%>' />
			<html:hidden property="country" value='<%=form.getCountry()%>' />
			<html:hidden property="company" value='<%=form.getCompany()%>' />
			<html:hidden property="sex" value='<%=form.getSex()%>' />
			<html:hidden property="email" value='<%=form.getEmail()%>' />
			<html:hidden property="assesment" value='<%=form.getAssesment()%>' />
			<html:hidden property="language" value='<%=form.getLanguage()%>' />
			<html:hidden property="role" value='<%=form.getRole()%>' />
			<table width="990" height="594" border="0" align="center" cellpadding="0" cellspacing="0"  class="tableaccesscode">
			  	<tr height="225">
			  		<td>&nbsp;</td>
			  	</tr>
			  	<tr>
    				<td align="center" valign="top">
    					<table width="40%" border="0" align="center" cellpadding="0" cellspacing="0" valign="top">
					      	<tr  class="simpleTitle">
        						<th align="left" colspan="2"><%=messages.getText("user.data.confirmdata")%></th>
							</tr>
    						<tr class="userdata"><td>&nbsp;</td></tr>
					      	<tr  class="userdata">
        						<th align="left" > <%=messages.getText("user.data.country")%><span class="required">*</span></th>
        						<td align="right">
									<html:select property="countryConfirmation" styleClass="input" style="width:160; ">
										<html:options collection="countryList" property="value" labelProperty="label"/>
									</html:select>
      							</td>
							</tr>
    						<tr class="userdata"><td>&nbsp;</td></tr>
					      	<tr  class="userdata">
        						<th align="left" > <%=messages.getText("module63.question1119.text")%><span class="required">*</span></th>
        						<td align="right">
									<html:select property="companyConfirmation" styleClass="input" style="width:160; ">
										<html:options collection="companyList" property="value" labelProperty="label"/>
									</html:select>
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


