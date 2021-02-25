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
						<h1>AVISO DE PRIVACIDAD</h1>
   						<div class="cleaner"></div>
	         			<div class="scroll">
    						<div class="scrollContainer">
									<p><strong>RESPONSABLE DEL TRATAMIENTO DE SUS DATOS PERSONALES:</strong> Le informamos que el responsable de recabar y tratar los datos personales que nos proporcione es: <strong>ESTAYTESU, S.A</strong>. de <strong>C.V.</strong> (en adelante &#8220;CEPA&#8221;).</p>
						            <p><strong>DOMICILIO DEL RESPONSABLE:</strong> Mario Pani No. 400 Piso 1, Suite 100 col. Santa Fe. CP 05348, Delegaci&oacute;n Cuajimalpa. </p>
						            <p><strong>FINALIDADES DEL TRATAMIENTO DE LOS DATOS PERSONALES.</strong> Los datos personales que recabamos de usted, los utilizaremos para las siguientes finalidades: </p>
						            <p><strong>Primarias:</strong></p>
						            <p>&#8226; Para evaluar su desempe&ntilde;o, conocimientos, desempe&ntilde;o laboral y aptitudes, con la finalidad de cumplir con los requerimientos en seguridad vial;</p>
						            <p>&#8226; Evaluar el desempe&ntilde;o y conocimiento integral de la totalidad de una flotilla en seguridad vial;</p>
						            <p>&#8226; Realizar una evaluaci&oacute;n integral para determinar la segmentaci&oacute;n de una flotilla. </p>
						            <p><strong>DATOS PERSONALES QUE PODR&Aacute;N SER RECABADOS Y TRATADOS:</strong> CEPA recabar&aacute; los datos personales necesarios del usuario para poder realizar una evaluaci&oacute;n t&eacute;cnica y de aptitudes en seguridad vial del usuario, as&iacute; como obtener sus antecedentes laborales y de tr&aacute;nsito. Las categor&iacute;as de datos personales a recabar y sujetas a tratamiento son: (i) datos de identificaci&oacute;n; (ii) datos de contacto; (iii) datos acad&eacute;micos; y, (iv) datos laborales. CEPA podr&aacute; realizar las investigaciones y acciones que considere necesarias, a efecto de comprobar a trav&eacute;s de cualquier tercero, dependencia u autoridad, la veracidad de los datos que le fueron proporcionados. </p>
						            <p><strong>FORMA EN LAS QUE SE RECABAN LOS DATOS PERSONALES:</strong> Recabamos sus datos personales de forma directa cuando usted mismo nos los proporciona a trav&eacute;s de la evaluaci&oacute;n que se completa en l&iacute;nea en la p&aacute;gina http://da.cepadc.com. Adicionalmente otra informaci&oacute;n de usted podr&aacute; ser recabada de otras fuentes p&uacute;blicas permitidas por la ley, tales como directorios telef&oacute;nicos o de la red mundial de informaci&oacute;n Internet, incluyendo redes sociales. </p>
						            <p><strong>TRANSFERENCIAS DE LOS DATOS PERSONALES:</strong> Le informamos que sus datos personales pueden ser transferidos y tratados dentro y fuera del pa&iacute;s, por personas distintas a esta empresa. En ese sentido, su informaci&oacute;n puede ser compartida con:</p>
						            <p>a) Empresas filiales, afiliadas y subsidiarias los cuales operan bajo los mismos procesos y pol&iacute;ticas internas, en cuyo caso se apegar&aacute;n a lo establecido en el presente Aviso de Privacidad;</p>
						            <p>b) Con su empleador o futuro o probable empleador (o entidad similar), si es que nos proporciona sus datos personales derivado de la relaci&oacute;n existente entre CEPA y dicha entidad; y</p>
						            <p>c) Autoridades locales o federales quienes lo hayan solicitado como parte de un proceso legal o en caso espec&iacute;fico por mandato judicial. </p>
						            <p><strong>DERECHOS ARCO.</strong> Usted tiene derecho de acceder, rectificar y cancelar sus datos personales, as&iacute; como de oponerse al tratamiento de los mismos o revocar el consentimiento que para tal fin nos haya otorgado, a trav&eacute;s de los procedimientos que hemos implementado. Para conocer dichos procedimientos, los requisitos y plazos, se puede poner en contacto con nuestro departamento de datos personales en Mario Pani No. 400 Piso 1, Suite 100 col. Santa Fe. CP 05348, Delegaci&oacute;n Cuajimalpa, con, correo cepamexico@cepasafedrive.com, tel. (0155) 9000 2088 quien ser&aacute; el responsable de la recepci&oacute;n, registro y atenci&oacute;n de las solicitudes. </p>
						            <p><strong>LIMITACI&Oacute;N DE USO Y DIVULGACI&Oacute;N DE INFORMACI&Oacute;N PERSONAL.</strong> Con objeto de que usted pueda limitar el uso y divulgaci&oacute;n de su informaci&oacute;n personal, le ofrecemos los siguientes medios: Su inscripci&oacute;n en el Registro P&uacute;blico para Evitar Publicidad, que est&aacute; a cargo de la Procuradur&iacute;a Federal del Consumidor, con la finalidad de que sus datos personales no sean utilizados para recibir publicidad o promociones de empresas de bienes o servicios. Para mayor informaci&oacute;n sobre este registro, usted puede consultar el portal de Internet de la PROFECO, o bien ponerse en contacto directo con &eacute;sta.&nbsp; </p>
						            <p><strong>MECANISMOS REMOTOS DE COMUNICACI&Oacute;N ELECTR&Oacute;NICA QUE RECABAN SUS DATOS PERSONALES DE MANERA AUTOM&Aacute;TICA.</strong> Le informamos que en nuestra p&aacute;gina de Internet utilizamos cookies, web beacons y otras tecnolog&iacute;as a trav&eacute;s de las cuales es posible monitorear su comportamiento como usuario de Internet, as&iacute; como brindarle un mejor servicio y experiencia de usuario al navegar en nuestra p&aacute;gina. Los datos personales que obtenemos de estas tecnolog&iacute;as de rastreo son los siguientes: horario de navegaci&oacute;n, tiempo de navegaci&oacute;n en nuestra p&aacute;gina de Internet, secciones consultadas, y p&aacute;ginas de Internet accedidas previo a la nuestra. Estas tecnolog&iacute;as pueden ser deshabilitadas. Puede buscar informaci&oacute;n sobre los navegadores conocidos y averiguar c&oacute;mo ajustar las preferencias de las Cookies en el siguiente sitio web: Microsoft Internet Explorer: http://www.microsoft.com/info/cookies.htm </p>
						            <p>En el caso de empleo de Cookies, el bot&oacute;n de 'ayuda' que se encuentra en la barra de herramientas de la mayor&iacute;a de los navegadores, le dir&aacute; c&oacute;mo evitar aceptar nuevas Cookies, c&oacute;mo hacer que el navegador le notifique cuando recibe una nueva cookie o c&oacute;mo deshabilitar todas las Cookies.</p>
						            <p><strong>CAMBIOS EN EL AVISO DE PRIVACIDAD: </strong>El presente aviso de privacidad puede sufrir modificaciones, cambios o actualizaciones derivadas de nuevos requerimientos legales; de nuestras propias necesidades por los productos o servicios que ofrecemos; de nuestras pr&aacute;cticas de privacidad; de cambios en nuestro modelo de negocio, o por otras causas. Cualquier modificaci&oacute;n al presente aviso podr&aacute; consultarlo en este sitio web. </p>
						            <p><strong>INSTITUTO NACIONAL DE TRANSPARENCIA, ACCESO A LA INFORMACI&Oacute;N Y PROTECCI&Oacute;N DE DATOS PERSONALES.</strong> Si usted considera que su derecho a la protecci&oacute;n de sus datos personales ha sido lesionado por alguna conducta u omisi&oacute;n de nuestra parte, o presume alguna violaci&oacute;n a las disposiciones previstas en la Ley Federal de Protecci&oacute;n de Datos Personales en Posesi&oacute;n de los Particulares, su Reglamento y dem&aacute;s ordenamientos aplicables, podr&aacute; interponer su inconformidad o denuncia ante el Instituto Nacional de Transparencia, Acceso a la Informaci&oacute;n y Protecci&oacute;n de Datos Personales (INAI). Para mayor informaci&oacute;n, le sugerimos visitar su p&aacute;gina oficial de Internet www.inai.org.mx. </p>
						            <p>&nbsp;</p>
            					</div>
            				</div>
       						<div class="cleaner"></div>
       						<div style="float: right;">
       							<strong>  
									<a href="logout.jsp">No aceptar y salir del sistema</a> 
								</strong>
								<a href="logout.jsp"><img src="images/checked.png" width="35" height="35" /></a>  
								&nbsp; &nbsp;  
								<a href="javascript:document.forms['accept'].submit();">
									<strong>
										Aceptar y Continuar 
									</strong>
									<img src="images/checked2.png" width="35" height="35" />
								</a>
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