<%@ page language="java"
	import="assesment.business.*"
	import="assesment.communication.language.*"
%>
<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>


<html>
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
		<link href="./util/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
		<!--[if lt IE 9]>
			<script type="text/javascript" src="scripts/html5shiv.min.js"></script>
		<![endif]-->
	</head>
	<body class="login">
		<header style="height: 50px;">
		</header>		
		<section id="content" style="padding-top: 50px;">
			<section class="grid_container">
				<form name="error" method="post" action="./login.jsp" >
					<fieldset id="username_block" class="active" style="min-width:800px;">
						<div class="container-fluid" style="min-width:700px;">
							<div class="row">
								<div class="col-xs-12" align="center" style="margin: 30px;">
									<label for="logo">
										<img src='images/main_logo_large.png'>
									</label>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12" align="center" style="margin: 20px;">
									<div class="col-xs-4" align="center" style="height: 250px; border-right: dotted;">
										<label for="es" style="valign:top; text-align:left; font-size:1.5em;">
											Se produjo un error en el proceso de Login, el usuario o clave no son correctos.
										</label>
									</div>
									<div class="col-xs-4" align="center" style="height: 250px; border-right: dotted;">
										<label for="pt" style="valign:top; text-align:left; font-size:1.5em;">
											Ocorreu um erro durante o processo de login, o nome de usuário e/ou senha estão incorretos.
										</label>
									</div>
									<div class="col-xs-4" align="center">
										<label for="en" style="valign:top; text-align:left; font-size:1.5em;">
											An error occurred during the Login proccess, the username or password is incorrect.
										</label>
									</div>
								</div>
							</div>
							<div class="row" align="center" style="margin: 30px;">
								<input type="button" class="button" value="Salir / Sair / Exit" onclick="javascript:document.forms['error'].submit();"/>
							</div>
						</div>
					</fieldset>
				</form>
			</section>
		</section>
	</body>
</html>
