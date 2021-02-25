<%@ page language="java"
	import="assesment.presentation.translator.web.util.Util"
	import="assesment.business.*"	
	import="assesment.communication.language.*"
	import="assesment.communication.corporation.CediAttributes"
	
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html" 
%>

<%@ taglib uri="/WEB-INF/struts-bean.tld"
        prefix="bean" 
%>

<%	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	Text messages = sys.getText();
	String usuario = (String) session.getAttribute("usuario");
	String password = (String) session.getAttribute("password");
	Integer cedi = (Integer) session.getAttribute("cedi");
	if(cedi == null) {
		String c = request.getParameter("cedi");
		if(Util.isNumber(c))
			cedi = new Integer(c); 
	}
	String cediName="";
	if(cedi!=null){
		CediAttributes cediAttributes = sys.getCorporationReportFacade().findCediAttributes(new Integer(cedi),sys.getUserSessionData());
		cediName = cediAttributes.getName().toUpperCase();
	}
	session.removeAttribute("cedi");
	session.removeAttribute("usuario");
	session.removeAttribute("password");


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
	</head>
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
		    height:35px;
			text-align: center;
		}
		.buttonGray{
			background-color: gray;
			color: white;
			font-family: 'Roboto Thin',"Helvetica Neue", Helvetica, Arial, "Pontano Sans", Verdana, sans-serif;
			font-size: 18px;
			margin-top: .3em;
	        border-radius: 0.2em;
			-moz-border-radius: 0.2em;
			-webkit-border-radius: 0.2em;
			border: 1px solid #999;
		    height:35px;
			text-align: center;
		}
		</style>
	<body class="login">
		<form name="usuario" action="grupom_register.jsp">
			<input type="hidden" name="cedi" value="<%=cedi %>" />
		</form>
		<form name="cedi" action="grupom_code.jsp">
		</form>
		<form name="salir" action="logout.jsp">
		</form>
		<header id="header">
			<section class="grid_container">
				<h1 class="customer_logo" style="background-image: url('images/main_logo_large.png');">CEPA Driver Assessment</h1>
			</section>
		</header>

		<section id="content">
			<section class="grid_container">

				<form action="groupm_register.jsp">
					<fieldset id="username_block" class="active">
						<div>
							<label> CEDI: <b><%= cediName %></b></label>
						</div>
						<br>
						<div>
		                  	<h3>Usuario creado con éxito</h3>
						</div>
						<br>
						<div>
		                  	<label for="usuario"><b>Usuario:</b> <%=usuario%></label>
						</div>
						<br>
						<div>
		                  	<label for="password"><b>Contraseña:</b> <%=password%></label>
						</div>
						<br>
						<br>
						<input type="button" class="button" value='Crear otro usuario'  onclick="document.forms['usuario'].submit();" />
						<input type="button" class="buttonGray" value='Cambiar de CEDI'  onclick="document.forms['cedi'].submit();" />
						<input type="button" class="buttonRed" value='Salir' onclick="document.forms['salir'].submit();" />
						<br>
					</fieldset>
				</form>
			</section>
		</section>
		<script type="text/javascript" src="scripts/jquery-2.0.2.min.js"></script>
		<script type="text/javascript" src="scripts/assessment.js"></script>
	</body>
</html:html>
