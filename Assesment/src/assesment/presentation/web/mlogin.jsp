<%@ page language="java"
	import="assesment.presentation.translator.web.util.*"	
	import="assesment.presentation.translator.web.administration.user.*"
	import="assesment.communication.util.MD5"
%>


<!doctype html>
<html lang="en">
<%! RequestDispatcher dispatcher; String context; String pathMsg; String pathGarbagecolector;
	String userName; String password; String sessionExpired; String accesscode; 
%>

<%	userName="";
	password = "";
	accesscode = "";
	sessionExpired="";
	String body = "";

	if(!Util.empty(request.getParameter("session"))) {
		sessionExpired=request.getParameter("session");
	}
    if(!Util.empty(request.getParameter("user"))) {
	    userName = request.getParameter("user");
	} 
	if(!Util.empty(request.getParameter("password"))) {
	    password = request.getParameter("password");
	}
	if(!Util.empty(request.getParameter("emea"))) {
		userName = "accesscode";
		password = "accesscode";
		accesscode = request.getParameter("emea");
	} else if(!Util.empty(request.getParameter("telematics")) && !Util.empty(request.getParameter("assessment"))) {
		userName = "accesscode";
		password = "accesscode";
	} else if(!Util.empty(request.getParameter("generate")) && !Util.empty(request.getParameter("p"))) {
		userName = "generate";
		password = request.getParameter("p");
	} else if(!Util.empty(request.getParameter("g")) && !Util.empty(request.getParameter("p"))) {
		userName = "generate_"+request.getParameter("g");
		password = request.getParameter("p");
	} else if(!Util.empty(request.getParameter("ep")) && !Util.empty(request.getParameter("p")) && !Util.empty(request.getParameter("l"))) {
		userName = "epdemo_"+request.getParameter("ep")+request.getParameter("l");
		password = "epdemo_"+request.getParameter("p");
		System.out.println(userName+" - "+password);
	}else if(!Util.empty(request.getParameter("login"))) {
		MD5 md5 = new MD5();
		userName = md5.encriptar(request.getParameter("login"));
	    password = md5.encriptar(userName);
	}
	if(!Util.empty(request.getParameter("access"))) {
		userName = "accesscode";
		password = "accesscode";
		accesscode = request.getParameter("access");
	}
	if(!Util.empty(userName) && !Util.empty(password)) {
		body = "onload=enter('"+userName+"','"+password+"')"; 
	}
%>
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
		<link REL=StyleSheet HREF="./util/css/bootstrap.min.css" TYPE="text/css">
		<!--[if lt IE 9]>
			<script type="text/javascript" src="scripts/html5shiv.min.js"></script>
		<![endif]-->
	</head>
	<script type="text/javascript">
		function ac(){
      		document.forms['loginForm'].j_username.style.display = 'none';
      		document.forms['loginForm'].j_password.style.display = 'none';
      		document.forms['loginForm'].j_username.value = 'accesscode';
      		document.forms['loginForm'].j_password.value = 'accesscode';
    		javascript:document.forms['loginForm'].submit();      
  		}
  		function enter(user,password){
      		document.forms['loginForm'].j_username.value = user;
      		document.forms['loginForm'].j_password.value = password;
    		javascript:document.forms['loginForm'].submit();      
  		}
	</script>
	<body <%=body%>>
		<div class="container-fluid">
			<div class="row" align="center">
				<div class="col-xs-12">
					<img class="logo" src="images/main_logo_large.png">
				</div>
			</div>
			<div class="row" align="center" >
				<div class="col-xs-12" align="center" style="max-width:540px; background-color: #ffcd00;">
					<div class="col-xs-12" align="center">
						<span class="title">CEPA Driver Assessment</span>
					</div>
					<div class="col-xs-12" align="center">
						CEPA Safe Drive &copy; 2018
					</div>
				</div>
			</div>
			<div class="row" align="center">
				<div class="col-xs-12" align="center">
					<form name="loginForm" method="post" action="j_security_check" onSubmit="return enc()">
						<input type="hidden" name="accesscode" value='<%=accesscode%>'/>		
						<input type="hidden" name="telematics" value='<%=request.getParameter("telematics")%>'/>		
						<input type="hidden" name="assessment" value='<%=request.getParameter("assessment")%>'/>
						<fieldset>
							<div class="col-xs-12" align="center">
								<label for="username">Usu&aacute;rio / Usuario / User</label>
							</div>
							<div class="col-xs-12" align="center">
								<input id="j_username" name="j_username" type="text" >
							</div>
							<div class="col-xs-12" align="center">
								<label for="password">Senha / Clave / Password</label>
							</div>
							<div class="col-xs-12" align="center">
								<input id="j_password" name="j_password" type="password" >
							</div>
							<div class="col-xs-12" align="center">
								<input class="button" type="submit" value="Entrar / Enter" >
							</div>		
							<div class="col-xs-12" align="center">
								<p class="block">
				                     Se voc&ecirc; possui um c&oacute;digo de acesso, favor clicar aqu&iacute;:<br>
				                     Si Usted tiene c&oacute;digo de acceso por favor haga click aqu&iacute;:<br>
				                     If you have an access code, please click here:<br>
				                     <a href="javascript:ac();" class="switch">C&oacute;digo de Acceso / C&oacute;digo de Acesso / Access Code</a>
								</p>
							</div>
						</fieldset>
					</form>
				</div>
			</div>
		</div>
		<script type="text/javascript" src="scripts/jquery-2.0.2.min.js"></script>
		<script type="text/javascript" src="scripts/assessment.js"></script>
	</body>
</html>
