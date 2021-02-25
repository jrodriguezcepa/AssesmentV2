<!doctype html>
<%@page import="assesment.business.AssesmentAccess"%>
<%@page import="assesment.communication.assesment.AssesmentData"%>
<%@page import="java.util.Iterator"%>
<%@page import="assesment.communication.module.ModuleData"%>
<%@page import="assesment.communication.administration.user.UserSessionData"%>
<%@page import="assesment.communication.language.Text"%>
<%@page import="assesment.communication.user.UserData"%>
<%
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	UserSessionData userSessionData = sys.getUserSessionData();
	Text messages = sys.getText(); 
	UserData userData = sys.getUserReportFacade().findUserByPrimaryKey(userSessionData.getFilter().getLoginName(),userSessionData);
%>


<%@page import="java.util.HashMap"%>

<html lang='<%=userSessionData.getLenguage()%>'>
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
		<link rel="stylesheet" href='styles/base_telematics.css'>
		<link rel="stylesheet" href="styles/jquery-ui-1.10.3.custom.min.css">
		
		<!--[if lt IE 9]>
			<script type="text/javascript" src="scripts/html5shiv.min.js"></script>
		<![endif]-->
	</head>
   	<body style="position: relative; margin-left: 10%; margin-right: 10%;">
      	<header id="header" style="width: 80%; position: relative;">
         	<section class="grid_container">
            	<h1 class="customer_logo" style="background-image: url(images/main_logo_large.png);">Driver Assessment</h1>
            	<div class="toolbar">
		       		<div class="cepa_logo">CEPA Mobility Care</div>
               		<span class="username"><%=userData.getFirstName()+" "+userData.getLastName() %></span>
            	</div>
         	</section>
      	</header>
		<form name="logout" action="./logout.jsp" method="post"></form>
		<section id="content" style="width: 80%; position: relative; padding: 10px;">
         	<section class="grid_container">
         		<form name="accept" action="acceptTerms.jsp" method="post">
	         		<fieldset id="username_block" class="active">
						<h1><%=messages.getText("generic.terms.title") %></h1>
   						<div class="cleaner"></div>
	         			<div class="scroll">
    						<div class="scrollContainer">
<%		for(int i = 1; i <= 5; i++) {
%>							    <p><%=messages.getText("generic.terms.line"+i)%></p>
<%		}
%>            				</div>
            			</div>
       					<div class="cleaner"></div>
       						<div style="float: left;">
  								<strong><%=messages.getText("generic.terms.read") %> </strong>
  							</div>
       						<div style="float: right;">
       						<div style="float: left; margin-right: 15px;">
								<a href="javascript:document.forms['accept'].submit();">
									<img src="images/checkVerde.png" width="45"/>
								</a>
							</div>
       						<div style="float: right; margin-left: 15px;">
								<a href="logout.jsp">
									<img src="images/checkRojo.png" width="45" />
								</a>  
							</div>
						</div>
    	        	</fieldset>
    	        </form>					
         	</section>
      	</section>
		<script type="text/javascript" src="scripts/json2.min.js"></script>
		<!--[if lt IE 9]>
			<script type="text/javascript" src="scripts/jquery-1.11.1.min.js"></script>
		<![endif]-->
		<!--[if gte IE 9]><!-->
			<script type="text/javascript" src="scripts/jquery-2.0.2.min.js"></script>
		<!--<![endif]-->
		<script type="text/javascript" src="scripts/jquery-ui-1.10.3.custom.min.js"></script>
		<script type="text/javascript" src="scripts/jquery.ui.datepicker-pt.js"></script>
		<script type="text/javascript" src="scripts/jquery.ui.datepicker-es.js"></script>
		<script type="text/javascript" src="scripts/jquery.tadeouy.loggable.js"></script>
		<script type="text/javascript" src="scripts/jquery.tadeouy.assessmentController.js"></script>
		<script type="text/javascript" src="scripts/jquery.tadeouy.question.js"></script>
		<script type="text/javascript" src="scripts/jquery.tadeouy.card.js"></script>
		<script type="text/javascript" src="scripts/jquery.tadeouy.assessment.js"></script>
		<script type="text/javascript" src="scripts/assessment.js"></script>
   </body>
</html>