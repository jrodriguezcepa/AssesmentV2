<%@page import="assesment.communication.language.Text"%>
<%@page import="assesment.business.AssesmentAccess"%>
<%@page import="java.util.Enumeration"%>
<%@ page language="java"
	import="assesment.presentation.translator.web.util.*"	
	import="assesment.presentation.translator.web.administration.user.*"
	import="assesment.communication.util.MD5"
%>


<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>

<html:html>
<%	
	String context=request.getContextPath();
	String pathMsg="/util/jsp/message.jsp";

	RequestDispatcher dispatcher=request.getRequestDispatcher(pathMsg);
	dispatcher.include(request,response);

	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	Text messages = sys.getText();
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
		    width: 90px;
		    height: 35px;
		}
		</style>
	</head>
	<body>
		<header style="height: 50px;">
		</header>		
		<section id="content" style="padding-top: 50px;">
			<section class="grid_container">
				<html:form action="/ForgotPassword">
					<fieldset id="username_block" class="active">
						<div>
							<div>
								<table style="width: 100%;">
		                     		<tr>
			                     		<td width="80%">	
											<label for="logo">
												<img src='images/main_logo_large.png'>
											</label>
			                     		</td>
			                     		<td width="20%" align="right">	
											<label for="logo">
												<a href="logout.jsp">
													<img src='images/error.png' style="width:40px">
												</a>
											</label>
			                     		</td>
		                     		<tr>
	                     		</table>
							</div>
							<div class="col-xs-12" align="left">
		                     	<label for="username">
			                     	<%=messages.getText("user.forgotpassword.insertdata")%>
								</label>
							</div>
							<div class="col-xs-12">
								<table>
		                     		<tr>
			                     		<td style="padding: 10px;">	
			                     			<img src='images/message.png' style="width:40px;">
			                     		</td>
			                     		<td valign="middle" style="padding: 10px;">
											<input type="text" name="email" class="textLogin" placeholder='<%=messages.getText("user.data.mail")%>'/>
			                     		</td>
		                     		<tr>
	                     		</table>
							</div>
							<div class="col-xs-12">
								<table>
		                     		<tr>
			                     		<td style="padding: 10px;">	
			                     			<img src='images/user.png' style="width:40px;">
			                     		</td>
			                     		<td valign="middle" style="padding: 10px;">
											<input type="text" name="user" class="textLogin" placeholder='<%=messages.getText("report.userresult.user")%>'/>
			                     		</td>
		                     		<tr>
	                     		</table>
							</div>
							<div class="col-xs-12" align="right">
								<html:submit styleClass="button" value='<%=messages.getText("generic.exception.send")%>' />
							</div>
						</div>
					</fieldset>
				</html:form>
			</section>
		</section>
		<script type="text/javascript" src="scripts/jquery-2.0.2.min.js"></script>
		<script type="text/javascript" src="scripts/assessment.js"></script>
	</body>
</html:html>
