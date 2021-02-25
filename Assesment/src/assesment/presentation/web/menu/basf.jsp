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
	<body class="login">
		<header id="header">
			<section class="grid_container">
				<h1 class="customer_logo" style="background-image: url('images/main_logo_large.png');">CEPA Driver Assessment</h1>
			</section>
		</header>
		<form name="logout" action="./logout.jsp" method="post"></form>
		<form name="exists" action="basfUser.jsp"></form>
		<html:form action="/BasfNewUser"></html:form>
		<section id="content">
			<section class="grid_container">
				<nav class="sections" data-min-rel-top="0" data-min-rel-bottom="0">
					<div class="score">
						<h2 class="title">CEPA Driver Assessment</h2>
						<img class="picture" src="images/sample-images/home_da_foto.jpg" width="345">
					</div>
					<p class="copyright">
						CEPA Safe Drive &copy; 2020
					</p>
				</nav>
				<form>
					<fieldset id="username_block" class="active">
						<div>
						<h2 class="title">
			  				BASF Assessment
						</h2>
						<div>
							<br>
							<br>
							<br>
							<input class="button" value="Usuario novo" onClick="javascript:document.forms['BasfNewUser'].submit();" style="width: 250px; height: 50px; font-family: Verdana; font-size: 1.5em; font-weight: bold;"/>
				  			<input class="button" value="Usuario existente" onClick="javascript:document.forms['exists'].submit();" style="width: 250px; height: 50px; font-family: Verdana; font-size: 1.5em; font-weight: bold;"/>
							<br>
							<br>
						</div>
						<div>
				  			<input type="button" value='<%=messages.getText("generic.messages.logout")%>' onClick="javascript:document.forms['logout'].submit()" style="width: 100px; height: 30px; font-family: Verdana; font-size: 1.25em;"/>
						</div>
					</fieldset>
				</form>
			</section>
		</section>
		<script type="text/javascript" src="scripts/jquery-2.0.2.min.js"></script>
		<script type="text/javascript" src="scripts/assessment.js"></script>
	</body>
</html:html>


