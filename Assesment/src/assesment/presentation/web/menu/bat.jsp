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
			color:#222222;
			width:50%;
			min-width:300px;
			border-width:2px;
			border-color:#000000;
			color:#000000;
			background-color:#FFFFFF;
			border-radius:0.5em;
			height:30px;
		}
		.buttonML {
			font-family:proxima-nova,Verdana;
			font-size:1.5em;
			font-weight:bold;
			color:#222222;
			width:200px;
			border-width:2px;
			border-color:#000000;
			background-color:#FFFFFF;
			border-radius:0.5em;
			height:30px;
		}
		</style>
		<script type="text/javascript">
		function saveUser() {
			var form = document.forms['UserGroupForm'];
			if(form.firstName.value.trim() == '') {
				alert('Ingrese Nombre');
			} else if(form.lastName.value.trim() == '') {
				alert('Ingrese Apellido');
			} else if(form.company.value.trim() == '') {
				alert('Ingrese Región');
			} else if(form.extraData2.value.trim() == '') {
				alert('Ingrese Cedis');
			} else if(form.loginname.value.trim() == '') {
				alert('Ingrese nombre de usuario');
			} else if(form.loginname.value.trim().length < 6) {
				alert('Nombre de usuario demasiado corto');
			} else {
				form.submit();
			}
		}
		</script>
		
	</head>
	<body style="margin:0; background-color:#D5D5D5;">
		<form name="logout" action="./logout.jsp" method="post"></form>
		<html:form action="/CreateUserGroup">
			<html:hidden property="language" value="es"/>
			<html:hidden property="group" value='<%=String.valueOf(GroupData.BAT)%>'/>
			<html:hidden property="password" value="654321"/>
			<html:hidden property="email" value="kitzia_mercado@bat.com"/>
			<div class="container" style="width:100%;margin:0;padding:0;">
				<div class="col-xs-12"  style="background-color:#D5D5D5; text-align:center;height:100%;">
					<div class="col-xs-12" style="padding-top:90px; padding-left:30px; ">
						<span style="font-family:proxima-nova, Verdana; font-size:3.0em; color:#000000; font-weight:bold;">
							<%=Util.formatHTML("¿Primera vez en el sistema?..crea tu usuario")%>
						</span>
					</div>
					<div class="col-xs-12" style="padding-top:20px; padding-left:30px; padding-left:30px;">
						<input type="text" name="firstName" placeholder='Nombre' class="inputML" />
					</div>
					<div class="col-xs-12" style="padding-top:10px; padding-left:30px; padding-left:30px;">
						<input type="text" name="lastName" placeholder='Apellido' class="inputML" />
					</div>
					<div class="col-xs-12" style="padding-top:10px; padding-left:30px; padding-left:30px;">
						<input type="text" name="company" placeholder="Región" class="inputML" />
					</div>
					<div class="col-xs-12" style="padding-top:10px; padding-left:30px; padding-left:30px;">
						<input type="text" name="extraData2" placeholder="Cedis" class="inputML" />
					</div>
					<div class="col-xs-12" style="padding-top:10px; padding-left:30px; padding-left:30px;">
						<input type="text" name="loginname" placeholder='Cree su nombre de usuario' class="inputML" />
					</div>
					<div class="col-xs-12" style="padding-top:20px; text-align:center;">
						<input type="button" value="Ya soy usuario registrado" onclick="javascript:document.forms['logout'].submit();" class="buttonML">
						<input type="button" value="Salvar" onclick="javascript:saveUser();" class="buttonML">
					</div>
					<div class="col-xs-12" style="padding-top:20px; padding-left:30px; padding-left:30px;">
						<a href="mailto:help@cepamobility.com">
							<span style="font-family:proxima-nova, Verdana; font-size:2.5em; color:#000000; font-weight:bold;">
								<%=Util.formatHTML("¡Ayuda! No recuerdo mi clave")%>
							</span>
						</a>
					</div>
				</div>
			</div>
		</html:form>
	</body>
<%	}
%>	
</html:html>


