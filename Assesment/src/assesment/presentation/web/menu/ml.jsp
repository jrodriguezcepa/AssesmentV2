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
		
		String group = request.getParameter("group");
		String lng = (Integer.parseInt(group) == 12) ? "es" : "pt";

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
			background-color:#EBEBEB;
			height:30px;
		}
		</style>
		<script type="text/javascript">
		function salvar_es() {
			var form = document.forms['UserGroupForm'];
			if(form.firstName.value.trim() == '') {
				alert('Ingrese Nombre');
			} else if(form.lastName.value.trim() == '') {
				alert('Ingrese Apellido');
			} else if(form.email.value.trim() == '') {
				alert('Ingrese Email');
			} else if(form.company.value.trim() == '') {
				alert('Ingrese Compañía/MLP');
			} else if(form.extraData2.value.trim() == '') {
				alert('Ingrese CURP');
			} else if(form.loginname.value.trim() != form.email.value.trim()) {
				alert('Confirmación de email incorrecta');
			} else if(form.password.value.trim().length < 6) {
				alert('Contraseña incorrecta');
			} else if(form.password.value.trim() != form.rePassword.value.trim()) {
				alert('Confirmación de contraseña incorrecta');
			} else {
				form.submit();
			}
		}
		function salvar_pt() {
			var form = document.forms['UserGroupForm'];
			if(form.firstName.value.trim() == '') {
				alert('Entrar Nome');
			} else if(form.lastName.value.trim() == '') {
				alert('Entrar Sobrenome');
			} else if(form.email.value.trim() == '') {
				alert('Entrar Email');
			} else if(form.company.value.trim() == '') {
				alert('Entrar Compañía/MLP');
			} else if(form.loginname.value.trim() != form.email.value.trim()) {
				alert('Confirmacao do email incorreta');
			} else if(form.password.value.trim().length < 6) {
				alert('Senha incorreta');
			} else if(form.password.value.trim() != form.rePassword.value.trim()) {
				alert('Confirmaciao do senha incorreta');
			} else {
				form.submit();
			}
		}
		</script>
		
	</head>
	<body style="margin:0;">
		<html:form action="/CreateUserGroup">
			<html:hidden property="language" value="<%=lng%>"/>
			<html:hidden property="group" value='<%=group%>'/>
			<div class="container" style="width:100%;margin:0;padding:0;">
				<div class="col-xs-5"  style="background-color:#F5E64D; text-align:center;height:100%;">
					<div class="col-xs-12" style="padding-top:10px; padding-left:20px; text-align:left;">
						<a href="logout.jsp">
							<img src="./images/cancel.png" style="width:40px;">
						</a>
					</div>
					<div class="col-xs-12" style="padding-top:70px;">
						<img src='<%="./images/logo_"+lng+".png"%>' width="75%">
					</div>
					<div class="col-xs-12" style="padding-top:40px;padding-left:60px;text-align:left;">
						<span style="font-family:proxima-nova,Verdana; font-size:3.5em; color:#333333; font-weight:bold;">
							<%=(lng.equals("es")) ? Util.formatHTML("¡Nuestra seguridad<br>es lo más importante!") : Util.formatHTML("Nossa segurança<br>é o mais importante!")%>
						</span>
					</div>
					<div class="col-xs-12" style="padding-top:30px;padding-left:40px;text-align:left;">
						<span style="font-family:proxima-nova,Verdana; font-size:2.2em; color:#333333; font-weight:bold;">
							<%=(lng.equals("es")) ? Util.formatHTML("Haz tu registro en el curso de<br>seguridad y fortalecer tu seguridad<br>en tus recorridos diarios.") : Util.formatHTML("Faça seu cadastro no curso de<br>segurança e aumente a segurança<br>em suas viagens diárias.")%>
						</span>
					</div>
				</div>
				<div class="col-xs-7"  style="background-color:#FFFFFF; text-align:left;height:100%;">
					<div class="col-xs-12" style="padding-top:90px; padding-left:30px; ">
						<span style="font-family:proxima-nova, Verdana; font-size:2.5em; color:#333333; font-weight:bold;">
							<%=(lng.equals("es")) ? Util.formatHTML("¿Primera vez en el sistema?..crea tu usuario") : Util.formatHTML("Primeira vez no sistema? ... crie seu usuário")%>
						</span>
					</div>
					<div class="col-xs-12" style="padding-top:20px; padding-left:30px; padding-left:30px;">
						<input type="text" name="firstName" placeholder='<%=(lng.equals("es")) ? "Nombre":"Nome"%>' class="inputML" />
					</div>
					<div class="col-xs-12" style="padding-top:10px; padding-left:30px; padding-left:30px;">
						<input type="text" name="lastName" placeholder='<%=(lng.equals("es")) ? "Apellido":"Sobrenome"%>' class="inputML" />
					</div>
					<div class="col-xs-12" style="padding-top:10px; padding-left:30px; padding-left:30px;">
						<input type="text" name="company" placeholder="Compañía/MLP" class="inputML" />
					</div>
<%			if(lng.equals("es")) {
%>					<div class="col-xs-12" style="padding-top:10px; padding-left:30px; padding-left:30px;">
						<input type="text" name="extraData2" placeholder="CURP" class="inputML" />
					</div>
<%			}else {
%>					<input type="hidden" name="extraData2"/>
<%			}
%>					
					<div class="col-xs-12" style="padding-top:10px; padding-left:30px; padding-left:30px;">
						<input type="text" name="email" placeholder="Email" class="inputML" />
					</div>
					<div class="col-xs-12" style="padding-top:10px; padding-left:30px; padding-left:30px;">
						<input type="text" name="loginname" placeholder='<%=(lng.equals("es")) ? "Por favor confirme su Email":"Por favor confirme seu email"%>' class="inputML" />
					</div>
					<div class="col-xs-12" style="padding-top:10px; padding-left:30px; padding-left:30px;">
						<input type="password" name="password" placeholder='<%=(lng.equals("es")) ? "Cree su contraseña de acceso":"Crie sua senha"%>' class="inputML" />
					</div>
					<div class="col-xs-12" style="padding-top:10px; padding-left:30px; padding-left:30px;">
						<input type="password" name="rePassword" placeholder='<%=(lng.equals("es")) ? "Confirme su contraseña de acceso":"Confirme sua senha"%>' class="inputML" />
					</div>
					<div class="col-xs-12" style="padding-top:20px; text-align:center;">
						<a href='<%="javascript:salvar_"+lng+"();"%>'>
							<img src="./images/save.png" style="width:150px;">
						</a>
					</div>
					<div class="col-xs-12" style="padding-top:20px; padding-left:30px; padding-left:30px;">
						<span style="font-family:proxima-nova, Verdana; font-size:2.5em; color:#333333; font-weight:bold;">
							<a href="mailto:help@cepamobility.com">
								<img src="./images/candado.png"><%=(lng.equals("es")) ? Util.formatHTML("¡Ayuda! No recuerdo mi clave") : Util.formatHTML("Ajuda! Não lembro minha senha")%>
							</a>
						</span>
					</div>
					<div class="col-xs-12" style="padding-top:10px; padding-left:30px; padding-left:30px;">
						<span style="font-family:proxima-nova, Verdana; font-size:2.5em; color:#333333; font-weight:bold;">
							<a href="logout.jsp">
								<img src="./images/flecha.png"><%=(lng.equals("es")) ? Util.formatHTML("Ya soy usuario registrado del sistema") : Util.formatHTML("Já sou um usuário registrado do sistema")%>
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


