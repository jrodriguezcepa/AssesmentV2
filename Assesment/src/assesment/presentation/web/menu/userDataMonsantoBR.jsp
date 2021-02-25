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

		Collection locationList = new LinkedList();
		locationList.add(new OptionItem(messages.getText("monsantobr.userlocation.manufacturing"),String.valueOf(UserData.MANUFACTURING)));
		locationList.add(new OptionItem(messages.getText("monsantobr.userlocation.comercial"),String.valueOf(UserData.COMMERCIAL)));
		locationList.add(new OptionItem(messages.getText("monsantobr.userlocation.agroeste"),String.valueOf(UserData.AGROESTE)));
		session.setAttribute("locationList",locationList);
		
		session.setAttribute("UserDataForm",new UserCreateForm());
%>
	<body>
		<html:form action="/UserData" >
			<html:hidden property="assesment" value='<%=String.valueOf(assesment)%>'/>
			<html:hidden property="role" value='<%=accesscode%>'/>
			<html:hidden property="language" value='<%=sys.getUserSessionData().getLenguage()%>' />
			<html:hidden property="birthDay" value="1" />
			<html:hidden property="birthMonth" value="1" />
			<html:hidden property="birthYear" value="1980" />
            <html:hidden property="sex" value="1" />
            <html:hidden property="country" value="32" />
			
			<table width="990" height="594" border="0" align="center" cellpadding="0" cellspacing="0"  class="tableaccesscode">
			  	<tr height="155">
			  		<td>&nbsp;</td>
			  	</tr>
			  	<tr>
    				<td align="center" valign="top">
    					<table width="60%" border="0" align="center" cellpadding="0" cellspacing="0" valign="top">
					      	<tr  class="userdata">
					        	<th width="224" align="left"> <%=messages.getText("user.data.nickname")%><span class="required">*</span></th>
        						<td align="right">
				            		<html:text property="loginname" size="25" styleClass="input"/>
					          	</td>
					      	</tr>
    						<tr class="userdata"><td>&nbsp;</td></tr>
					      	<tr class="userdata">
        						<th align="left"><%=messages.getText("user.data.pass")%><span class="required">*</span></th>
        						<td align="right">
           							<html:password property="password" size="25" styleClass="input"/>
          						</td>
      						</tr>
    						<tr class="userdata"><td>&nbsp;</td></tr>
					      	<tr  class="userdata">
        						<th align="left"> <%=messages.getText("user.data.confirmpassword")%><span class="required">*</span></th>
        						<td align="right">
           							<html:password property="rePassword" size="25" styleClass="input"/>
          						</td>
      						</tr>
    						<tr class="userdata"><td>&nbsp;</td></tr>
					      	<tr  class="userdata">
        						<th align="left" > <%=messages.getText("user.data.firstname")%><span class="required">*</span></th>
        						<td align="right">
           							<html:text property="firstName" size="25" styleClass="input"/>
          						</td>
      						</tr>
    						<tr class="userdata"><td>&nbsp;</td></tr>
					      	<tr  class="userdata">
        						<th align="left"> <%=messages.getText("user.data.lastname")%><span class="required">*</span></th>
        						<td align="right">
           							<html:text property="lastName" size="25" styleClass="input"/>
          						</td>
      						</tr>
    						<tr class="userdata"><td>&nbsp;</td></tr>
					      	<tr  class="userdata">
        						<th align="left"> 
        							<%=messages.getText("user.data.mail")%>
        							<span class="required">*</span>
								</th>
        						<td align="right">
           							<html:text property="email" size="35" styleClass="input"/>
          						</td>
      						</tr>
    						<tr class="userdata"><td>&nbsp;</td></tr>
					      	<tr  class="userdata">
        						<th align="left"> 
        							<%=messages.getText("user.data.mailconfirmation")%>
        							<span class="required">*</span>
								</th>
        						<td align="right">
           							<html:text property="emailConfirmation" size="35" styleClass="input"/>
          						</td>
      						</tr>
    						<tr class="userdata"><td>&nbsp;</td></tr>
					      	<tr  class="userdata">
        						<th align="left" > <%=messages.getText("monsantobr.user.location")%><span class="required">*</span></th>
        						<td align="right">
									<html:select property="location" styleClass="input" style="width:160; ">
										<html:options collection="locationList" property="value" labelProperty="label"/>
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


