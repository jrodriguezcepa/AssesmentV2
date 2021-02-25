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
<%
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	String check = Util.checkPermission(sys,SecurityConstants.BASF);
	if(check!=null) {
		response.sendRedirect(request.getContextPath()+check);
	}else {
		Text messages = sys.getText();

		RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
		dispatcher.include(request,response);
%>

<%@page import="assesment.communication.security.SecurityConstants"%>
<%@page import="assesment.presentation.translator.web.util.Util"%><html:html>
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
		function doSearch() {
			var form = document.forms['search'];
			if(form.cpf.value == '') {
				alert('Ingrese CPF');
			} else if(form.rg.value == '') {
				alert('Ingrese Número de Identidade / RG');
			} else {
				form.submit();
			}
		}
	</script>
	<body class="login">
		<header id="header">
			<section class="grid_container">
				<h1 class="customer_logo" style="background-image: url('images/main_logo_large.png');">CEPA Driver Assessment</h1>
			</section>
		</header>
		<form name="logout" action="./logout.jsp" method="post"></form>
		<form name="search" action="basfConfirmation.jsp" method="post"></form>
		<section id="content">
			<section class="grid_container">
				<nav class="sections" data-min-rel-top="0" data-min-rel-bottom="0">
					<div class="score">
						<h2 class="title">CEPA Driver Assessment</h2>
						<img class="picture" src="images/sample-images/home_da_foto.jpg">
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
						</div>
						<div>
							<label>
			  				<%=messages.getText("assessment.basf.insertdata") %>
							</label>
						</div>
						<div>
							<label>
				  			<%=messages.getText("assessment.basf.rg") %>:
							</label>
						</div>
						<div>
				  			<input type="text" name="rg" width="50" style="input"/>
						</div>
						<div>
							<label>
				  			<%=messages.getText("assessment.basf.cpf") %>:
							</label>
						</div>
						<div>
				  			<input type="text" name="cpf" width="50" style="input"/>
						</div>
						<div>
				  			<input class="button" value='<%=messages.getText("generic.messages.search")%>' onclick="javascript:doSearch();" style="width: 100px; height: 30px; font-family: Verdana; font-size: 1.25em;"/>
				  			<input type="button" value='<%=messages.getText("generic.messages.logout")%>' onclick="document.forms['logout'].submit();" style="width: 100px; height: 30px; font-family: Verdana; font-size: 1.25em;"/>
						</div>
					</fieldset>
				</form>
			</section>
		</section>
		<script type="text/javascript" src="scripts/jquery-2.0.2.min.js"></script>
		<script type="text/javascript" src="scripts/assessment.js"></script>
	</body>
</html:html>

<%	}
%>	


