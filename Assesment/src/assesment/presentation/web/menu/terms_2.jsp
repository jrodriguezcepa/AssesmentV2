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
    AssesmentData assesment = sys.getAssesmentReportFacade().findAssesment(userSessionData.getFilter().getAssesment(), userSessionData);
    String logoName = "../flash/images/logo"+assesment.getCorporationId()+".png";
%>


<%@page import="java.util.HashMap"%><html lang='<%=userSessionData.getLenguage()%>'>
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
            	<h1 class="customer_logo" style="background-image: url('<%=logoName%>');">Driver Assessment</h1>
            	<div class="toolbar">
		       		<div class="cepa_logo">CEPA Safe Drive</div>
               		<span class="username"><%=userData.getFirstName()+" "+userData.getLastName() %></span>
            	</div>
         	</section>
      	</header>
		<form name="logout" action="./logout.jsp" method="post"></form>
		<section id="content" style="width: 80%; position: relative; padding: 10px;">
         	<section class="grid_container">
         		<form name="accept" action="acceptTerms.jsp" method="post">
	         		<fieldset id="username_block" class="active">
						<h1>Recomenda��es CEPA para Etapa Te�rica e Pr�tica</h1>
   						<div class="cleaner"></div>
	         			<div class="scroll">
    						<div class="scrollContainer">
							    <p>Todos os condutores devem apresentar a CNH dentro do prazo de validade legal para realiza��o das atividades.</p>
							    <p>Recomendamos a chegada ao local do treinamento com anteced�ncia de no m�nimo 20 minutos para o preenchimento de fichas e formul�rios.</p>
							    <p>Toler�ncia de 20 minutos de atraso para o treinamento. Atrasos superiores acarretar�o na invalida��o da etapa te�rica do treinamento.</p>
							    <p>Gestantes n�o dever�o realizar a etapa pr�tica em pista em fun��o dos movimentos bruscos poderem afetar a gesta��o. Treinamentos em vias p�blicas poder�o ser realizados pela gestante desde que n�o exista nenhuma contraindica��o m�dica.</p>
							    <p>Pessoas que sofreram procedimentos cir�rgicos recentemente n�o dever�o realiza a etapa pr�tica em pista em fun��o dos movimentos bruscos poderem afetar o processo de recupera��o do condutor. Treinamentos em vias p�blicas poder�o ser realizados por estes condutores desde que n�o exista nenhuma contraindica��o m�dica.</p>
							    <p>Quando um condutor com necessidades especiais for realizar o treinamento o CEPA deve ser comunicado antecipadamente para que sejam consideradas as adequa��es necess�rias para realiza��o deste.</p>
							    <p>Alguns cal�ados podem afetar negativamente o uso dos pedais e por isso n�o devem ser utilizados durante o treinamento (e tamb�m durante a condu��o cotidiana). Tais cal�ados s�o aqueles que n�o se firmam nos p�s (chinelos, sand�lias que n�o possuem tiras atr�s dos calcanhares) ou que comprometas a utiliza��o adequada dos pedais (sapatos de salto alto).</p>
							    <p>O certificado somente ser� disponibilizado on-line para os condutores que conclu�rem 100% das atividades te�ricas e pr�ticas. </p>
							    <p>&nbsp;</p>
            				</div>
            			</div>
       					<div class="cleaner"></div>
       						<div style="float: right;">
  								<strong>Li e estou de acordo as recomenda��es CEPA para atividades </strong>
								<a href="javascript:document.forms['accept'].submit();">
									<img src="images/checked2.png" width="35" height="35" />
								</a>
								&nbsp; &nbsp;  
								<a href="logout.jsp"><img src="images/checked.png" width="35" height="35" /></a>  
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