<%@page import="java.util.Calendar"%>
<%@page import="assesment.communication.administration.user.ForgotPasswordData"%>
<%@page import="assesment.communication.language.Text"%>
<%@page import="assesment.business.AssesmentAccess"%>
<%@ page language="java"
	import="assesment.presentation.translator.web.administration.user.*"	
	import="assesment.presentation.translator.web.util.*"
	import="org.apache.struts.action.*"	
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>

<html:html>
<%! RequestDispatcher dispatcher; String context; String pathMsg; String pathGarbagecolector;%>
<%
	context=request.getContextPath();
	pathMsg="/util/jsp/message.jsp";
	
	dispatcher=request.getRequestDispatcher(pathMsg);
	dispatcher.include(request,response);
	
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	Text messages = sys.getText();

	String key = request.getParameter("key");
	if(key == null) {
		key = (String)session.getAttribute("key");
	}
		
	ForgotPasswordData data = sys.getUserReportFacade().findPasswordRecovery(key, sys.getUserSessionData());
	Calendar c = Calendar.getInstance();
	c.add(Calendar.HOUR, -2);
%>
	<head>
		<meta charset="iso-8859-1">
		<meta http-equiv="X-UA-Compatible" content="IE=9; IE=EDGE" />
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<link rel="apple-touch-icon-precomposed" sizes="57x57" href="images/apple-touch-icon-57x57-precomposed.png" />
		<link rel="apple-touch-icon-precomposed" sizes="72x72" href="images/apple-touch-icon-72x72-precomposed.png" />
		<link rel="apple-touch-icon-precomposed" sizes="114x114" href="images/apple-touch-icon-114x114-precomposed.png" />
		<link rel="apple-touch-icon-precomposed" sizes="144x144" href="images/apple-touch-icon-144x144-precomposed.png" />
		<meta name="viewport" content="user-scalable=no, width=980" />
		<meta name="description" content="">
		<meta name="keywords" content="">
		<title>CEPA Driver Assessment</title>
		<link rel="shortcut icon" type="image/ico" href="images/favicon.ico">
		<link rel="stylesheet" href="styles/fonts/pontano_sans.css">
		<link rel="stylesheet" href="styles/base.css">
		<link rel="stylesheet" href="styles/jquery-ui-1.10.3.custom.min.css">
		<!--[if lt IE 9]>
			<script type="text/javascript" src="scripts/html5shiv.min.js"></script>
		<![endif]-->
		<style type="text/css">
		.buttonRed {
		    background-color: red;
		    color: white;
		    font-family: 'Roboto Thin',"Helvetica Neue", Helvetica, Arial, "Pontano Sans", Verdana, sans-serif;
		    font-size: 18px;
		    margin-top: .3em;
		    border-radius: 0.2em;
		    -moz-border-radius: 0.2em;
		    -webkit-border-radius: 0.2em;
		    border: 1px solid #999;
		    text-align: center;
		    width: 150px;
		    height: 35px;
		}
		</style>
	</head>
	<body>
		<header style="height: 50px;">
		</header>		
		<section id="content" style="padding-top: 50px;">
			<section class="grid_container">
				<html:form action="/ResetPassword">
					<html:hidden property="recovery" value="<%=String.valueOf(data.getId())%>" />
					<html:hidden property="key" value="<%=data.getKey()%>" />
					<html:hidden property="user" value="<%=data.getLogin()%>" />
					<fieldset id="username_block" class="active">
						<div>
							<div>
								<label for="logo">
									<img src='images/main_logo_large.png'>
								</label>
							</div>
<% 		if(data == null || data.getId() == null) {
%>							<div class="col-xs-12" align="left">
							 	<label for="username">
							     	<%=messages.getText("user.forgotpassword.expiredlink")%>
								</label>
							</div>
							<div class="col-xs-12" align="center">
								<html:cancel styleClass="buttonRed" value='<%=messages.getText("user.forgotpassword.backtologin")%>' style="background-color:red; color:white;"/>
							</div>
<%		}else {	
			if(!data.isUsed() && c.before(data.getDate())) {
%>							<html:hidden property="type" value="1" />
							<div class="col-xs-12" align="left">
		                     	<label for="username">
			                     	<%=messages.getText("user.forgotpassword.newdata")%>
								</label>
							</div>
							<div class="col-xs-12">
								<input type="password" name="password" class="textLogin" placeholder='<%=messages.getText("user.data.pass")%>'/>
							</div>
							<div class="col-xs-12">
								<input type="password" name="confirmation" class="textLogin" placeholder='<%=messages.getText("user.data.confirmpassword")%>'/>
							</div>
							<div class="col-xs-12" align="right">
								<html:submit styleClass="button" value='<%=messages.getText("generic.exception.send")%>' />
								<html:cancel styleClass="buttonRed" value='<%=messages.getText("generic.messages.back")%>' />
							</div>
<%			}else {
%>							<html:hidden property="type" value="2" />
							<div class="col-xs-12" align="left">
		                     	<label for="username">
			                     	<%=messages.getText("user.forgotpassword.expiredlink")%>
								</label>
							</div>
							<div class="col-xs-12" align="center">
								<html:submit styleClass="button" value='<%=messages.getText("user.forgotpassword.resendlink")%>' />
								<html:cancel styleClass="buttonRed" value='<%=messages.getText("user.forgotpassword.backtologin")%>' style="background-color:red; color:white;"/>
							</div>
<%			}
		}
%>
						</div>
					</fieldset>
				</html:form>
			</section>
		</section>
		<script type="text/javascript" src="scripts/jquery-2.0.2.min.js"></script>
		<script type="text/javascript" src="scripts/assessment.js"></script>
	</body>
</html:html>
