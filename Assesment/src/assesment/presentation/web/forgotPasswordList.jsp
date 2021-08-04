<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
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
	
	Collection list = (Collection)session.getAttribute("list");
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
		    width: 180px;
		    height: 35px;
		}
		</style>
		<script>
			function doSend(userValue) {
				var form = document.forms['ForgotPasswordForm'];
				form.user.value = userValue;
				form.submit();
			}
		</script>
	</head>
	<body>
		<html:form action="/ForgotPassword">
			<html:hidden property="user" />
			<html:hidden property="email" />
		</html:form>
		<form name="logout" action="./logout.jsp">
		</form>
		<form name="back" action="./forgotPassword.jsp">
		</form>
		<header style="height: 50px;">
		</header>		
		<section id="content" style="padding-top: 50px;">
			<section class="grid_container">
				<form name="users">
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
		                     		<tr>
	                     		</table>
							</div>
							<div class="col-xs-12" align="left">
		                     	<label for="username">
			                     	<%=messages.getText("user.forgotpassword.multipleusers")%>
								</label>
							</div>
							<div class="col-xs-12" align="left">
		                     	<label for="username">
			                     	<%=messages.getText("user.forgotpassword.selectuser")%>
								</label>
							</div>
<%			Iterator it = list.iterator();
			while(it.hasNext()) {  
				String user = (String)it.next();
				String link = "javascript:doSend('"+user+"');";
%>							<div class="col-xs-12">
								<a href="<%=link%>" style="color:#555; text-decoration:none; padding:10px; font-size:1.4em;">
									<%="- "+user%>
	        	             	</a>
							</div>
<%			}
%>							<div class="col-xs-12" align="right">
								<input type="button" class="button" value='<%=messages.getText("user.forgotpassword.backtologin")%>' onclick="document.forms['logout'].submit();"/>
								<input type="button" class="buttonRed" value='<%=messages.getText("user.forgotpassword.backtosearch")%>' onclick="document.forms['back'].submit();"/>
							</div>
						</div>
					</fieldset>
				</form>
			</section>
		</section>
		<script type="text/javascript" src="scripts/jquery-2.0.2.min.js"></script>
		<script type="text/javascript" src="scripts/assessment.js"></script>
	</body>
</html:html>
