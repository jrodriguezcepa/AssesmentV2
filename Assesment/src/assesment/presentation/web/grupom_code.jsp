<%@page import="assesment.communication.corporation.CediData"%>
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
		<link rel="stylesheet" href="styles/jquery-ui-1.10.3.custom.min.css">
		<script type="text/javascript">
			function changeLogin(value) {
				var div1 = document.getElementById('loginCedi');
				var div2 = document.getElementById('loginUsuario');
				if(value == 2) {
					div1.style.display='';
					div2.style.display='none';
				}else {
					div1.style.display='none';
					div2.style.display='';
				}
			}
		</script>
		<style type="text/css">
			.fondo{
				background-color: #EBEBEB;
			}
			.contenedor{
				width:100%;
			}
			.box{
				background-color: #F1F4F9;
				margin: 0 auto;
				margin-top:20px;
				width:412px;
				height:313px;
	        	border-radius: 1.2em;
				-moz-border-radius: 1.2em;
				-webkit-border-radius: 1.2em;
				border: 0px solid #C9C9C9;
				box-shadow: 2px 2px #CCCCCC;'
			}
			.text{
				background-color: #DDE4EA;
				padding-left:10px;
				font-family: 'Roboto Thin',"Helvetica Neue", Helvetica, Arial, "Pontano Sans", Verdana, sans-serif;
				font-size:1.2em;
				width:350px;
				height:50px;
	        	border-radius: 0.8em;
				-moz-border-radius: 0.8em;
				-webkit-border-radius: 0.8em;
				border: 0px solid #C9C9C9;
				margin:30px;
			}
			.texto{
				padding-left:10px;
				font-family: 'Roboto Thin',"Helvetica Neue", Helvetica, Arial, "Pontano Sans", Verdana, sans-serif;
				font-size:0.9em;
				margin:5px;
			}
			.button{
				background-color: #0F172A;
				color: #FFFFFF;
				padding-left:10px;
				font-family: 'Roboto Thin',"Helvetica Neue", Helvetica, Arial, "Pontano Sans", Verdana, sans-serif;
				font-size:0.8em;
				width:160px;
				height:50px;
	        	border-radius: 0.8em;
				-moz-border-radius: 0.8em;
				-webkit-border-radius: 0.8em;
				border: 0px solid #C9C9C9;
				margin-left:30px;
			}
			.logo {
				margin-left:30px;
				margin-rigth:30px;
				margin-top:20px;
				width:75px;
			}
		</style>
	</head>
	<body class="fondo">
		<form name="logout" action="./logout.jsp" method="post"></form>
		<header style="height: 50px;">
		</header>		
		<div class="contenedor">
			<div class="box">
				<div>
					<div style="float: left; width: 250px;">
						<img src="styles/grupomodelo/UI_Logo_CEPA.png" class="logo"/>
					</div>
					<div>
						<img src="styles/grupomodelo/UI_Logo_GMM.png" class="logo"/>
					</div>
				</div>
				<div>
					<div id="loginCedi">
						<html:form action="/CediAccess" style="padding-right: 0px;">
							<html:hidden property="company" value="<%=String.valueOf(CediData.GRUPO_MODELO)%>" />
							<div>
								<input name="code" class="text" placeholder="CODIGO CEDI"/>
							</div>
							<!-- div>
								<a href="javascript:changeLogin(1);">
									<span class="texto">
										Soy administrador de CEDIs.
									</span>
								</a>
							</div -->
							<div>
								<div style="float: left; width: 190px;">
									<html:submit styleClass="button" value="ENTRAR"/>
								</div>
								<div>
									<html:cancel styleClass="button" value="SALIR"/>
								</div>
							</div>
						</html:form>
					</div>
					<div id="loginUsuario" style="display: none;">
						<html:form action="/CediMultiAccess" style="padding-right: 0px;">
							<html:hidden property="company" value="<%=String.valueOf(CediData.GRUPO_MODELO)%>" />
							<div>
								<input name="user" class="text" placeholder="USUARIO"/>
							</div>
							<div>
								<input type="password" name="password" class="text" placeholder="CONTRASEÑA"/>
							</div>
							<div>
								<a href="javascript:changeLogin(2);">
									<span class="texto">
										Ingresar código CEDI.
									</span>
								</a>
							</div>
							<div>
								<div style="float: left; width: 190px;">
									<html:submit styleClass="button" value="ENTRAR"/>
								</div>
								<div>
									<html:cancel styleClass="button" value="SALIR"/>
								</div>
							</div>
						</html:form>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript" src="scripts/jquery-2.0.2.min.js"></script>
		<script type="text/javascript" src="scripts/assessment.js"></script>
	</body>
</html:html>
