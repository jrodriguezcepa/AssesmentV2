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
		<!--[if lt IE 9]>
			<script type="text/javascript" src="scripts/html5shiv.min.js"></script>
		<![endif]-->
	</head>
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
				width:550px;
				height:330px;
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
			.cedi{
				padding-left:10px;
				color: #8E8E8E;
				font-family: 'Roboto Thin',"Helvetica Neue", Helvetica, Arial, "Pontano Sans", Verdana, sans-serif;
				font-size:1.0em;
				margin-left: 20px;
    			margin-top: 20px;
			}
			.button1{
				background-color: #0F172A;
				color: #FFFFFF;
				padding-left:10px;
				font-family: 'Roboto Thin',"Helvetica Neue", Helvetica, Arial, "Pontano Sans", Verdana, sans-serif;
				font-size:0.8em;
			    width: 130px;
			    height: 40px;
			    border-radius: 0.8em;
			    -moz-border-radius: 0.8em;
			    -webkit-border-radius: 0.8em;
			    border: 0px solid #C9C9C9;
			    margin-left: 30px;
			    margin-top: 10px;
			}
			.button2{
				background-color: #3A3A3A;
				color: #FFFFFF;
				padding-left:10px;
				font-family: 'Roboto Thin',"Helvetica Neue", Helvetica, Arial, "Pontano Sans", Verdana, sans-serif;
				font-size:0.8em;
			    width: 160px;
			    height: 40px;
			    border-radius: 0.8em;
			    -moz-border-radius: 0.8em;
			    -webkit-border-radius: 0.8em;
			    border: 0px solid #C9C9C9;
			    margin-left: 10px;
			    margin-top: 10px;
			}
			.button3{
				background-color: #5A5A5A;
				color: #FFFFFF;
				padding-left:10px;
				font-family: 'Roboto Thin',"Helvetica Neue", Helvetica, Arial, "Pontano Sans", Verdana, sans-serif;
				font-size:0.8em;
			    width: 130px;
			    height: 40px;
			    border-radius: 0.8em;
			    -moz-border-radius: 0.8em;
			    -webkit-border-radius: 0.8em;
			    border: 0px solid #C9C9C9;
			    margin-left: 10px;
			    margin-top: 10px;
			}
			.logo {
				margin-left:30px;
				margin-rigth:30px;
				margin-top:20px;
				width:75px;
			}
		</style>
	<body class="fondo">
		<form name="usuario" action="grupom_register.jsp">
			<input type="hidden" name="cedi" value="<%=cedi %>" />
		</form>
		<form name="cedi" action="grupom_code.jsp">
		</form>
		<form name="salir" action="logout.jsp">
		</form>
		<header style="height: 50px;">
		</header>		
		<form action="groupm_register.jsp">
			<div class="contenedor">
				<div class="box">
					<div>
						<div style="float: left; width: 380px;">
							<img src="styles/grupomodelo/UI_Logo_CEPA.png" class="logo"/>
						</div>
						<div>
							<img src="styles/grupomodelo/UI_Logo_GMM.png" class="logo"/>
						</div>
					</div>
					<div class="cedi"> 
						CEDI: <b><%= cediName %></b>
					</div>
					<div class="cedi"> 
	                  	<h3>Usuario creado con éxito</h3>
					</div>
					<div class="cedi"> 
		            	<b>Usuario:</b> <%=usuario%>
					</div>
					<div class="cedi"> 
	                  	<label for="password"><b>Contraseña:</b> <%=password%></label>
					</div>
					<div>
						<input type="button" class="button1" value='Crear otro usuario'  onclick="document.forms['usuario'].submit();" />
						<input type="button" class="button2" value='Cambiar de CEDI'  onclick="document.forms['cedi'].submit();" />
						<input type="button" class="button3" value='Salir' onclick="document.forms['salir'].submit();" />
						<br>
					</div>
				</div>
			</div>
		</form>
	</body>
</html:html>
