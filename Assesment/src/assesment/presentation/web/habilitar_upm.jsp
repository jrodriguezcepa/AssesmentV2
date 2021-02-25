<%@page import="assesment.communication.assesment.AssesmentData"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="assesment.communication.security.SecurityConstants"%>
<%@page import="assesment.presentation.translator.web.util.Util"%>
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

<%	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	Text messages = sys.getText();
	
	RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
	dispatcher.include(request,response);

	AssesmentData data = sys.getAssesmentReportFacade().findAssesment(AssesmentData.UPM_CHARLA, sys.getUserSessionData());
%>
<html:html>
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
		.buttonRed{
			background-color: red;
			color: white;
			font-family: 'Roboto Thin',"Helvetica Neue", Helvetica, Arial, "Pontano Sans", Verdana, sans-serif;
			font-size: 24px;
			margin-top: .3em;
	        border-radius: 0.75em;
			-moz-border-radius: 0.75em;
			-webkit-border-radius: 0.75em;
			border: 1px solid #999;
			text-align: center;
			width:250px;
		    height:50px;
		}
		.buttonGreen{
			background-color: green;
			color: white;
			font-family: 'Roboto Thin',"Helvetica Neue", Helvetica, Arial, "Pontano Sans", Verdana, sans-serif;
			font-size: 24px;
			margin-top: .3em;
	        border-radius: 0.75em;
			-moz-border-radius: 0.75em;
			-webkit-border-radius: 0.75em;
			border: 1px solid #999;
			text-align: center;
			width:250px;
		    height:50px;
		}
		.red {
			color: red;
			font-family: 'Roboto Thin',"Helvetica Neue", Helvetica, Arial, "Pontano Sans", Verdana, sans-serif;
			font-size: 24px;
		}
		.green {
			color: green;
			font-family: 'Roboto Thin',"Helvetica Neue", Helvetica, Arial, "Pontano Sans", Verdana, sans-serif;
			font-size: 24px;
		}
		</style>
	</head>
	<body class="login">
		<form name="logout" action="./logout.jsp" method="post"></form>
		<header style="height: 50px;">
		</header>		
		<section id="content" style="padding-top: 50px;">
			<section class="grid_container">
				<html:form action="/HabilitarUPM" style="padding-right: 0px;">
					<fieldset id="username_block" class="active">
						<div>
							<label for="logo">
								<img src='images/main_logo_large.png'>
							</label>
						</div>
<%		if(data.isElearning()) {
%>									
						<html:hidden property="action" value="0"/>
						<div>
		                  	<label for="accesscode">
		                  		<span class="green">
		                  			Registro Habilitado
		                  		</span>
		                  	</label>
						</div>
						<div>
			  				<html:submit styleClass="buttonRed" value='Deshabilitar' />
						</div>
<%		} else {
%>						<html:hidden property="action" value="1"/>
						<div>
		                  	<label for="accesscode">
		                  		<span class="red">
		                  			Registro No Habilitado
		                  		</span>
		                  	</label>
						</div>
						<div>
			  				<html:submit styleClass="buttonGreen" value='Habilitar' />
						</div>
<%		} 
%>						
						<div>
			  				<input type="button" value='Salir' class="button" onclick="javascript:document.forms['logout'].submit();"/>
						</div>
					</fieldset>
				</html:form>
			</section>
		</section>
		<script type="text/javascript" src="scripts/jquery-2.0.2.min.js"></script>
		<script type="text/javascript" src="scripts/assessment.js"></script>
	</body>
</html:html>
