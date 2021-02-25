<%@page import="assesment.presentation.translator.web.administration.user.LogoutAction"%>
<%@page language="java"
	import="assesment.business.*"
	import="assesment.business.administration.user.*"
	import="assesment.communication.assesment.*"
	import="assesment.communication.language.*"
	import="assesment.communication.security.*"
	import="assesment.communication.administration.user.*"
	import="assesment.communication.module.*"
	import="java.util.*"
%>
<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>
<%
	new LogoutAction().logout(request);
%>

<html lang='pt'>
	<head>
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
		<link rel="stylesheet" href='styles/base.css'>
		<link rel="stylesheet" href="styles/jquery-ui-1.10.3.custom.min.css">
		<!--[if lt IE 9]>
			<script type="text/javascript" src="scripts/html5shiv.min.js"></script>
		<![endif]-->
		<style type="text/css">
		.texto{
			font-family: 'Roboto Thin',"Helvetica Neue", Helvetica, Arial, "Pontano Sans", Verdana, sans-serif;
			font-size: 16px;
		}
		</style>
	</head>
	<body>
    	<header id="header">
        	<section class="grid_container">
            	<h1 class="customer_logo" style="background-image: url('../flash/images/logo29.png');">Driver Assessment</h1>
            	<div class="toolbar">
            	</div>
         	</section>
      	</header>
     	<section id="content">
   	 		<section class="grid_container">
				<form action="logout.jsp">
					<fieldset id="username_block" class="active">
						<br>
						<br>
						Erro de link
						<br>
						Consulte seu administrador
						<br>
					</fieldset>
				</form>   	 		
			</section>
   	 	</section>
   	 </body>		
</html>
