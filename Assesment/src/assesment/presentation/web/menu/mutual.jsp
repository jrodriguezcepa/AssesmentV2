<%@ page language="java"
	import="assesment.business.*"
	import="assesment.communication.language.*"
	import="assesment.communication.language.tables.*"
	import="assesment.communication.security.*"	
	import="assesment.communication.util.*"	
	import="assesment.communication.user.*"
	import="assesment.presentation.translator.web.util.*"	
	import="assesment.presentation.actions.user.*"
	import="assesment.communication.assesment.*"
	import="assesment.communication.corporation.*"
	import="java.util.*"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html" 
%>

<%@ taglib uri="/WEB-INF/struts-bean.tld"
        prefix="bean" 
%>

<html:html>
<%
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	String check = Util.checkPermission(sys,SecurityConstants.ACCESSCODE);
	if(check!=null) {
		response.sendRedirect(request.getContextPath()+check);
	}else {
		Text messages = sys.getText();

		RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
		dispatcher.include(request,response);
		
%>
	<head>
		<meta charset="iso-8859-1">
		<meta http-equiv="X-UA-Compatible" content="IE=9; IE=EDGE" />
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<meta name="viewport" content="user-scalable=no, width=980" />
		<meta name="description" content="">
		<meta name="keywords" content="">
		<title>CEPA Assessment</title>
		<link href="./util/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
	    <link href="./static/css/mainML.css" rel="stylesheet">
		<style>
		.inputML {
			margin-left:10px;
			padding-left:10px;
			font-family:proxima-nova,Verdana;
			font-size:1.5em;
			font-weight:bold;
			color:#333333;
			width:70%;
			border-width:1px;
			border-color:#000000;
			height:30px;
		}
		</style>
		<script type="text/javascript">
		function salvar() {
			var form = document.forms['UserGroupForm'];
			if(form.company.value.trim() == '') {
				alert('Ingrese Código de su empresa');
			} else if(form.firstName.value.trim() == '') {
				alert('Ingrese Nombre');
			} else if(form.lastName.value.trim() == '') {
				alert('Ingrese Apellido');
			} else if(form.email.value.trim() == '') {
				alert('Ingrese Email');
			} else if(form.email.value.trim() != form.loginname.value.trim()) {
				alert('Confirmación de Email incorrecta');
			} else if(form.password.value.trim().length < 6) {
				alert('Contraseña incorrecta');
			} else if(form.password.value.trim() != form.rePassword.value.trim()) {
				alert('Confirmación de contraseña incorrecta');
			} else {
				form.submit();
			}
		}
		</script>
		
	</head>
	<body style="margin:0;">
		<html:form action="/CreateUserMutual">
			<div class="container" style="width:100%; margin:0; padding:0;">
				<div class="col-xs-6" style="background-color:#FFFFF; text-align:center;height:100%;padding:0px;">
					<div class="col-xs-12" style="padding:0px;">
						<img src="./images/mutual.jpg" width="100%">
					</div>
				</div>
				<div class="col-xs-6"  style="background-color:#FFFFFF; text-align:left;height:100%;">
					<div class="col-xs-12" style="padding-top:90px; padding-left:30px; ">
						<span style="font-family:proxima-nova, Verdana; font-size:2.5em; color:#333333; font-weight:bold;">
							<%=Util.formatHTML("¿Primera vez en el sistema?...crea tu usuario")%>
						</span>
					</div>
					<div class="col-xs-12" style="padding-top:10px; padding-left:30px; padding-left:30px;">
						<input type="text" name="company" placeholder="CODIGO DE SU EMPRESA" class="inputML" />
					</div>
					<div class="col-xs-12" style="padding-top:20px; padding-left:30px; padding-left:30px;">
						<input type="text" name="firstName" placeholder='Nombre' class="inputML" />
					</div>
					<div class="col-xs-12" style="padding-top:10px; padding-left:30px; padding-left:30px;">
						<input type="text" name="lastName" placeholder='Apellido' class="inputML" />
					</div>
					<input type="hidden" name="extraData2"/>
					<div class="col-xs-12" style="padding-top:10px; padding-left:30px; padding-left:30px;">
						<input type="text" name="email" placeholder='<%=Util.formatHTML("Email (servirá como nombre de usuario)")%>' class="inputML" />
					</div>
					<div class="col-xs-12" style="padding-top:10px; padding-left:30px; padding-left:30px;">
						<input type="text" name="loginname" placeholder='<%=Util.formatHTML("Confirme su email")%>' class="inputML" />
					</div>
					<div class="col-xs-12" style="padding-top:10px; padding-left:30px; padding-left:30px;">
						<input type="password" name="password" placeholder='Cree su clave de acceso' class="inputML" />
					</div>
					<div class="col-xs-12" style="padding-top:10px; padding-left:30px; padding-left:30px;">
						<input type="password" name="rePassword" placeholder='Confirme su clave de acceso' class="inputML" />
					</div>
					<div class="col-xs-12" style="padding-top:20px; text-align:center;">
						<a href='<%="javascript:salvar();"%>'>
							<img src="./images/saveMutual.jpg" style="width:150px;">
						</a>
					</div>
					<div class="col-xs-12" style="padding-top:20px; padding-left:30px; padding-left:30px;">
						<span style="font-family:proxima-nova, Verdana; font-size:2.5em; color:#333333; font-weight:bold;">
							<a href="mailto:help@cepamobility.com">
								<img src="./images/candado_w.png"><%=Util.formatHTML("¡Ayuda! No recuerdo mi clave")%>
							</a>
						</span>
					</div>
					<div class="col-xs-12" style="padding-top:10px; padding-left:30px; padding-left:30px;">
						<span style="font-family:proxima-nova, Verdana; font-size:2.5em; color:#333333; font-weight:bold;">
							<a href="logout.jsp">
								<img src="./images/flecha.png"><%=Util.formatHTML("Ya soy usuario registrado del sistema")%>
							</a>
						</span>
					</div>
				</div>
			</div>
		</html:form>
	</body>
<%	}
%>	
</html:html>


