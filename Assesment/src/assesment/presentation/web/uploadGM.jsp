<%@page import="assesment.business.administration.user.UsReportFacade"%>
<%@page import="assesment.communication.user.UserData"%>
<%@page import="assesment.communication.assesment.AssesmentData"%>
<%@ page language="java" isErrorPage="true"
	import="java.util.*"
	import="assesment.communication.module.*"
	import="assesment.presentation.translator.web.util.*"
	import="assesment.communication.language.Text"
	import="assesment.communication.question.QuestionData"
%>
<%@page import="assesment.communication.administration.user.UserSessionData"%>
<%@page import="assesment.business.AssesmentAccess"%>
<%@page import="assesment.communication.util.CountryConstants"%>
<%@page import="assesment.communication.util.CountryData"%>
<%@page import="assesment.communication.question.AnswerData"%>
<%@page import="assesment.communication.administration.UserAnswerData"%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>

<html:html>
	<head>
		<meta charset="iso-8859-1">
		<meta http-equiv="X-UA-Compatible" content="IE=9; IE=EDGE" />
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<meta name="viewport" content="user-scalable=no, width=980" />
		<meta name="description" content="">
		<meta name="keywords" content="">
		<title>CEPA Driver Assessment</title>
		<link rel="shortcut icon" type="image/ico" href="images/favicon.ico">
		<link href="./util/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
		<style type="text/css">
			body {
				background-color: #EFEFEF;
				margin-left: 0px;
				margin-top: 0px;
				margin-right: 0px;
				margin-bottom: 0px;
			}
			.inputfile {
				width: 0.1px;
				height: 0.1px;
				opacity: 0;
				overflow: hidden;
				position: absolute;
				z-index: -1;
			}
			.back {
				font-family: Roboto,sans-serif;
    			font-size: 2.0em;
    			color: #405959;
			}
		</style>
	</head>
	<body>
<%
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	if(sys == null) {
		response.sendRedirect("logout.jsp");
	}else {
		
		RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
		dispatcher.include(request,response);

		UserSessionData userSessionData = sys.getUserSessionData();
		Text messages = sys.getText();
		
		String user = userSessionData.getFilter().getLoginName();
		UsReportFacade userReport = sys.getUserReportFacade();
		UserData userData = userReport.findUserByPrimaryKey(user,userSessionData);

		String assessmentId = request.getParameter("id");
		if(assessmentId == null) {
			assessmentId = (String)session.getAttribute("assessment");
		}
		
	    AssesmentData assesment = sys.getAssesmentReportFacade().findAssesment(new Integer(assessmentId), userSessionData);
		Iterator it = assesment.getModuleIterator();
		if(it.hasNext()) {
			ModuleData moduleData = (ModuleData)it.next();
	        HashMap hash = sys.getUserReportFacade().getModuleHashResult(user,moduleData.getId(),userSessionData);

			Integer moduleId = moduleData.getId();
			Iterator qIt = moduleData.getQuestionIterator();
%>
		<html:form action="/UploadFile"  enctype="multipart/form-data">
			<html:hidden property ="assessment" value="<%=assessmentId%>"/>
			<div class="container" style="width:100%; text-align:center;" >
				<div class="row">
					<div class="col-xs-12" style="margin-bottom:15px; background-color:#EFEFEF;">
						<div class="col-xs-12" align="right">
							<a href="group.jsp">
								<span class="back">
									<%=messages.getText("generic.messages.back")%>
								</span>
							</a>
							<img src="images/main_logo_large.png" style="padding: 20px;">
						</div>
						<div class="col-xs-12">
							<div class="col-xs-4" background-color:#EFEFEF;" >
								<div class="col-xs-12">
<%			if(qIt.hasNext()) {
				QuestionData q = (QuestionData)qIt.next();
				
				String picName = (hash.containsKey(q.getId())) ? "images/foto1gm_tick.png" : "images/foto1gm.png"; 
%>								<html:file property='file1' styleId="file1" styleClass="inputfile" onchange="document.forms['UploadFileForm'].submit()" accept=".jpg, .jpeg, .png, .pdf"/>
								<label for="file1"><img src='<%=picName %>' style="width:250px;"></label>
<%			}
%>								</div>
								<div class="col-xs-12">
									<h2>Ejemplo Fotografía</h2>
									<label for="file1"><img src='images/ejemploFoto.png' style="width:250px;"></label>
								</div>
							</div>
							<div class="col-xs-8">
								<div class="col-xs-12" align="left">
									<h1><%=Util.formatHTML("Estimado usuario")%></h1>
									<p><%=Util.formatHTML("Dentro de esta actividad tendrás la oportunidad de subir una fotografía de tu licencia para validar la información:")%></p>
									<p><%=Util.formatHTML("La fotografía debe ser lo más clara posible para que se pueda apreciar la siguiente información de manera correcta:")%></p> 
									<ul>
										<li><%=Util.formatHTML("Fotografía")%></li>
										<li><%=Util.formatHTML("Nombre completo")%></li>
										<li><%=Util.formatHTML("No de Licencia")%></li>
										<li><%=Util.formatHTML("CURP o RFC")%></li>
										<li><%=Util.formatHTML("Vigencia ")%></li>
										<li><%=Util.formatHTML("Antigüedad")%></li>
										<li><%=Util.formatHTML("TIPO")%></li>
									</ul>
									<h2><%=Util.formatHTML("Instrucciones para PC o Celular")%></h2>
									<p><b><%=Util.formatHTML("Desde la PC:")%></b> <%=Util.formatHTML(" Al darle click en la actividad te aparecerá el 'Explorador de Archivos' de la computadora, donde podrás elegir una fotografía previamente tomada y almacenada en tus archivos")%>.</p>
									<p><b><%=Util.formatHTML("Desde la Celular:")%></b> <%=Util.formatHTML(" Al darle click en la actividad te aparecerá la opción de tomar una fotografía con la cámara de tu celular, o te acceder a tu galería de fotos donde podrás elegir una fotografía previamente tomada y almacenada en tus archivos.")%></p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</html:form>
<%		}
	}
%>
	</body>
</html:html>
