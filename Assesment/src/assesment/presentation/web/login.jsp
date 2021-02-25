<%@page import="java.util.Enumeration"%>
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
    if(!Util.empty(request.getParameter("user")) && !Util.empty(request.getParameter("password"))) {
    	if(Util.isValidUser(request.getParameter("user"), request.getParameter("password"))) {
		    userName = request.getParameter("user");
		    password = request.getParameter("password");
    	}
	}
	if(!Util.empty(request.getParameter("user_ache"))) {
    	if(Util.isValidUser(request.getParameter("user_ache"), null)) {
		    userName = request.getParameter("user_ache");
		    password = Util.passwordAche(userName);
    	}
	}
	if(!Util.empty(request.getParameter("emea"))) {
    	if(Util.isValidAccessCode(request.getParameter("emea"))) {
			userName = "accesscode";
			password = "accesscode";
			accesscode = request.getParameter("emea");
    	}
	} else if(!Util.empty(request.getParameter("group"))) {
		userName = "accesscode";
		password = "accesscode";
		accesscode = "mercado.libre";
	} else if(!Util.empty(request.getParameter("ache"))) {
		userName = "accesscode";
		password = "accesscode";
		accesscode = "ache";
	} else if(!Util.empty(request.getParameter("telematics")) && !Util.empty(request.getParameter("assessment"))) {
		userName = "accesscode";
		password = "accesscode";
	} else if(!Util.empty(request.getParameter("generate")) && !Util.empty(request.getParameter("p"))) {
    	if(Util.isValidUser("generate", request.getParameter("user"))) {
			userName = "generate";
			password = request.getParameter("p");
    	}
	} else if(!Util.empty(request.getParameter("g")) && !Util.empty(request.getParameter("p"))) {
    	if(Util.isValidUser("generate_"+request.getParameter("g"), request.getParameter("user"))) {
			userName = "generate_"+request.getParameter("g");
			password = request.getParameter("p");
    	}
	} else if(!Util.empty(request.getParameter("groupac")) && !Util.empty(request.getParameter("p"))) {
    	if(Util.isValidUser("ggenerate_"+request.getParameter("groupac"), request.getParameter("user"))) {
			userName = "ggenerate_"+request.getParameter("groupac");
			password = request.getParameter("p");
    	}
	} else if(!Util.empty(request.getParameter("ep")) && !Util.empty(request.getParameter("p")) && !Util.empty(request.getParameter("l"))) {
		String lng = request.getParameter("l").toLowerCase();
    	if(Util.isValidUser("epdemo_"+request.getParameter("ep")+request.getParameter("l"), "epdemo_"+request.getParameter("p"))) {
			userName = "epdemo_"+request.getParameter("ep")+request.getParameter("l");
			password = "epdemo_"+request.getParameter("p");
		}
	}else if(!Util.empty(request.getParameter("login"))) {
		MD5 md5 = new MD5();
		userName = md5.encriptar(request.getParameter("login"));
	    password = md5.encriptar(userName);
	}
	if(!Util.empty(request.getParameter("access"))) {
    	if(Util.isValidAccessCode(request.getParameter("emea"))) {
			userName = "accesscode";
			password = "accesscode";
			accesscode = request.getParameter("access");
    	}
	}
	int className=1;
	if(!Util.empty(userName) && !Util.empty(password)) {
		className=0;
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
		<link rel="stylesheet" href="styles/base.css">
		<link rel="stylesheet" href="styles/jquery-ui-1.10.3.custom.min.css">
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
	<style>
		.login_1 {
			display: none;
		}
		.login_0 {
			display: block;
		}
	</style>
	<body class="login" <%=body%>>
		<header style="height: 50px;">
		</header>		
		<section id="content" style="padding-top: 50px;">
			<section class="grid_container">
				<form name="loginForm" method="post" action="j_security_check" onSubmit="return enc()" style="padding-right: 0px;">
					<input type="hidden" name="accesscode" value='<%=accesscode%>'/>		
					<input type="hidden" name="telematics" value='<%=request.getParameter("telematics")%>'/>		
					<input type="hidden" name="assessment" value='<%=request.getParameter("assessment")%>'/>
					<fieldset id="username_block" class="active">
						<div class='<%="login_"+String.valueOf(1-className)%>'>
							<div>
								<label for="logo">
									<img src='images/main_logo_large.png'>
								</label>
							</div>
							<div>
								<label for="username">Usu&aacute;rio / Usuario / User</label>
								<input id="j_username" name="j_username" type="text" >
							</div>
							<!--
							<div class="error">
								<label for="username">User / Usuario / Usu�rio</label>
								<div class="error_text">Debe ingresar un usuario correcto</div>
								<input id="username" name="username" type="text" >
							</div>
							-->
							<div>
								<label for="password">Senha / Clave / Password</label>
								<input id="j_password" name="j_password" type="password" >
							</div>
	
							<input class="button" type="submit" value="Entrar / Enter" >
	
							<p class="block">
			                     Se voc&ecirc; possui um c&oacute;digo de acesso, favor clicar aqu&iacute;:<br>
			                     Si Usted tiene c&oacute;digo de acceso por favor haga click aqu&iacute;:<br>
			                     If you have an access code, please click here:<br>
			                     <a href="javascript:ac();" class="switch">C&oacute;digo de Acceso / C&oacute;digo de Acesso / Access Code</a>
							</p>
						</div>
						<div class='<%="login_"+String.valueOf(className)%>' style="align-content: center;">
							<img src='images/wait.gif'>
						</div>
					</fieldset>
				</form>
			</section>
		</section>
		<script type="text/javascript" src="scripts/jquery-2.0.2.min.js"></script>
		<script type="text/javascript" src="scripts/assessment.js"></script>
	</body>
</html>
