<%@ page language="java"
	import="assesment.business.*"	
	import="assesment.communication.language.*"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html" 
%>

<%@ taglib uri="/WEB-INF/struts-bean.tld"
        prefix="bean" 
%>
<%@ page pageEncoding="UTF-8" %>

<%	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");

	Text messages = sys.getText();

	RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
	dispatcher.include(request,response);
	
	int type = Integer.parseInt(request.getParameter("type"));
%>
<html:html>
	<head>
		<meta charset="UTF-8">
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
		.buttonRed{
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
			width:90px;
		    height:35px;
		}
		</style>
	</head>
	<body class="login">
		<form name="logout" action="./logout.jsp" method="post"></form>
		<header style="height: 50px;">
		</header>		
		<section id="content" style="padding-top: 50px;">
			<section class="grid_container">
				<html:form action="/AlRiyadah" style="padding-right: 0px;">
					<html:hidden property="type" value="<%=String.valueOf(type) %>" />
					<fieldset id="username_block" class="active">
						<div align="right">
							<label for="logo">
								<img src='images/main_logo_large.png'>
							</label>
						</div>
						<div align="right">
<% 			if(type == 1) {
%>		                  <label for="accesscode">اختبار المعرفة (A)</label>
<% 			} else if(type == 2) {
%>		                  <label for="accesscode">اختبار المعرفة (B)</label>
<% 			} else if(type == 3) {
%>		                  	<label for="accesscode">اختبار المعرفة النهائي</label>
<% 			}
%>						</div>
						<div align="right">
		                  	<label for="accesscode">أدخل الرمز الخاص بك</label>
							<html:text property="code" />
						</div>
						<div align="right">
							<div align="right">
								<input type="button" class="button" value="أدخل" onclick="javascript:document.forms['WebinarForm'].submit();"/>
				  				<input class="buttonRed" value='تسجيل خروج' onClick="javascript:document.forms['logout'].submit()" />
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
