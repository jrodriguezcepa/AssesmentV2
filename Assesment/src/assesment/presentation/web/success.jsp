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

<html>
<%! RequestDispatcher dispatcher; String context; String pathMsg; String pathGarbagecolector;%>
<%
	context=request.getContextPath();
	pathMsg="/util/jsp/message.jsp";
	
	dispatcher=request.getRequestDispatcher(pathMsg);
	dispatcher.include(request,response);
	
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	Text messages = sys.getText();

	String txt1 = "";
	switch(Integer.parseInt(request.getParameter("type"))) {
		case 1:
			txt1 = messages.getText("user.forgotpassword.emailsended");
			break;
		case 2:
			txt1 = messages.getText("user.forgotpassword.passwordchange");
			break;
		case 3:
			txt1 = messages.getText("user.forgotpassword.emailresended");
			break;
	}
	
	String txt2 = messages.getText("user.forgotpassword.backtologin");
	
	new LogoutAction().logout(request);
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
	</head>
	<body>
		<header style="height: 50px;">
		</header>		
		<section id="content" style="padding-top: 50px;">
			<section class="grid_container">
				<form name="back" method="post" action="index.jsp" >
					<fieldset id="username_block" class="active">
						<div>
							<div>
								<label for="logo">
									<img src='images/main_logo_large.png'>
								</label>
							</div>
							<div class="col-xs-12" align="left">
		                     	<label for="username">
			                     	<%=txt1%>
								</label>
							</div>
							<div class="col-xs-12" align="center">
								<input type="submit" class="button" value='<%=txt2%>' style="width:300px "/>
							</div>
						</div>
					</fieldset>
				</form>
			</section>
		</section>
		<script type="text/javascript" src="scripts/jquery-2.0.2.min.js"></script>
		<script type="text/javascript" src="scripts/assessment.js"></script>
	</body>
</html>
