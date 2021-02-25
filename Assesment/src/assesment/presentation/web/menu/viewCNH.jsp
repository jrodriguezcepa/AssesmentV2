<%@page import="assesment.business.assesment.AssesmentReportFacade"%>
<%@page import="assesment.business.administration.user.UsReportFacade"%>
<%@page import="java.util.Date"%>
<%@page import="assesment.presentation.translator.web.util.Util"%>
<%@page import="java.util.HashMap"%>
<%@page import="assesment.communication.assesment.CategoryData"%>
<%@page import="assesment.communication.assesment.GroupData"%>
<%@page language="java"
	import="assesment.business.AssesmentAccess"
%>


<%@page import="assesment.communication.report.AssessmentReportData"%>


<%@page import="assesment.communication.assesment.AssesmentData"%>
<%@page import="assesment.communication.language.Text"%>


<%@page import="assesment.communication.user.UserData"%>
<%@page import="assesment.communication.administration.user.UserSessionData"%>
<%@page import="java.util.Iterator"%>
<%@page import="assesment.communication.report.UserReportData"%>
<%@page import="assesment.communication.module.ModuleData"%>
<%@page import="assesment.communication.report.ModuleReportData"%>
<%@page import="assesment.communication.report.QuestionReportData"%>
<%@page import="assesment.communication.security.SecurityConstants"%>
<%@page import="assesment.communication.assesment.AssesmentAttributes"%>
<%@page import="assesment.communication.question.QuestionData"%>

<%@page import="java.util.Collection"%>

<%@page import="java.util.LinkedList"%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>


<html:html>

<%
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	Text messages = sys.getText();
	UserSessionData userSessionData = sys.getUserSessionData();
	
	String user = request.getParameter("user");
	
	HashMap<String, HashMap<Integer, Object[]>> userSpecificQuestions = sys.getAssesmentReportFacade().getUserSpecificQuestion("47393,47394,47395,47396", user, sys.getUserSessionData());
%>
	<head>
		<meta charset="iso-8859-1">
		<meta http-equiv="X-UA-Compatible" content="IE=9; IE=EDGE" />
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<meta name="viewport" content="user-scalable=no, width=980" />
		<meta name="description" content="">
		<meta name="keywords" content="">
		<title>CEPA Driver Assessment</title>
		<link rel="shortcut icon" type="image/ico" href="images/favicon.ico">
		<!--[if lt IE 9]>
			<script type="text/javascript" src="scripts/html5shiv.min.js"></script>
		<![endif]-->
	    <!-- script type="text/javascript" src="https://www.google.com/jsapi"></script -->
		<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
		<style type="text/css">
			body {
				background-color: #1D272D;
				margin-left: 10px;
				margin-top: 10px;
				margin-right: 10px;
				margin-bottom: 0px;
			}
			.title {
				margin-left: 10px;
				margin-top: 20px;
				margin-bottom: 20px;
				color: white;
				font-family: Roboto, Helvetica, Arial, sans-serif;
				font-size: 2.5em;
			}
			.subtitle {
				margin-left: 10px;
				margin-top: 20px;
				margin-bottom: 20px;
				color: white;
				font-family: Roboto, Helvetica, Arial, sans-serif;
				font-size: 1.5em;
			}
			.tabla {
				margin-left: 10px;
				color: white;
				font-family: Roboto, Helvetica, Arial, sans-serif;
				font-size: 11;
				text-align: center;
				width:98%;
				padding: 0;
				border-spacing: 0;
			}
			.cell {
				min-width: 130px;
				border-color: white;
				border-width: 1px;
				border-style: solid;
				text-align: center;
				height: 30px;
			}
		</style>
	</head>
	<body>
		<div>
			<img src="./imgs/logo.png">
		</div>
		<br>
		<br>
		<div>
			<table width="100%">
<%		if(userSpecificQuestions.containsKey(user)) {
			HashMap<Integer, Object[]> values2 = userSpecificQuestions.get(user);
			if(values2.containsKey(47393)) { 
				String file = String.valueOf(values2.get(47393)[0]);
				if(file.toLowerCase().endsWith(".pdf")) {
%>				<tr>
					<td class="subtitle">
						<a href='<%="./flash/upload_images/"+file%>' target="_blank" style="text-decoration: none;">
							<img src="./imgs/pdf.png" style="width: 30px;">
							<span class="subtitle">
								<%=messages.getText("module3932.question47393.text")%>
							</span>
						</a>
					</td>
				</tr>
<%				} else {
%>				<tr>
					<td class="subtitle">
						<%=messages.getText("module3932.question47393.text")%>
					</td>
				</tr>
				<tr>
					<td>
						<img src='<%="./flash/upload_images/"+file%>' >
					</td>
				</tr>
<%				}
%>				<tr>
					<td>
						<hr style="color:white;">
					</td>
				</tr>
<%			}
			if(values2.containsKey(47394)) { 
				String file = String.valueOf(values2.get(47394)[0]);
				if(file.toLowerCase().endsWith(".pdf")) {
%>				<tr>
					<td class="subtitle">
						<a href='<%="./flash/upload_images/"+file%>' target="_blank" style="text-decoration: none;">
							<img src="./imgs/pdf.png" style="width: 30px;">
							<span class="subtitle">
								<%=messages.getText("module3932.question47394.text")%>
							</span>
						</a>
					</td>
				</tr>
<%				} else {
%>				<tr>
					<td class="subtitle">
						<%=messages.getText("module3932.question47394.text")%>
					</td>
				</tr>
				<tr>
					<td>
						<img src='<%="./flash/upload_images/"+file%>' >
					</td>
				</tr>
<%				}
%>				<tr>
					<td>
						<hr style="color:white;">
					</td>
				</tr>
<%			}
			if(values2.containsKey(47395)) { 
				String file = String.valueOf(values2.get(47395)[0]);
				if(file.toLowerCase().endsWith(".pdf")) {
%>				<tr>
					<td class="subtitle">
						<a href='<%="./flash/upload_images/"+file%>' target="_blank" style="text-decoration: none;">
							<img src="./imgs/pdf.png" style="width: 30px;">
							<span class="subtitle">
								<%=messages.getText("module3932.question47395.text")%>
							</span>
						</a>
					</td>
				</tr>
<%				} else {
%>				<tr>
					<td class="subtitle">
						<%=messages.getText("module3932.question47395.text")%>
					</td>
				</tr>
				<tr>
					<td>
						<img src='<%="./flash/upload_images/"+file%>' >
					</td>
				</tr>
<%				}
%>				<tr>
					<td>
						<hr style="color:white;">
					</td>
				</tr>
<%			}
			if(values2.containsKey(47396)) { 
				String file = String.valueOf(values2.get(47396)[0]);
				if(file.toLowerCase().endsWith(".pdf")) {
%>				<tr>
					<td class="subtitle">
						<a href='<%="./flash/upload_images/"+file%>' target="_blank" style="text-decoration: none;">
							<img src="./imgs/pdf.png" style="width: 30px;">
							<span class="subtitle">
								<%=messages.getText("module3932.question47396.text")%>
							</span>
						</a>
					</td>
				</tr>
<%				} else {
%>				<tr>
					<td class="subtitle">
						<%=messages.getText("module3932.question47396.text")%>
					</td>
				</tr>
				<tr>
					<td>
						<img src='<%="./flash/upload_images/"+file%>' >
					</td>
				</tr>
<%				}
%>				<tr>
					<td>
						<hr style="color:white;">
					</td>
				</tr>
<%			}
		}
%>			</table>
		</div>	
		
	</body>
</html:html>