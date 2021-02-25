<%@page import="assesment.business.administration.user.UsReportFacade"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="assesment.presentation.translator.web.util.Util"%>
<%@page import="assesment.persistence.user.tables.UserAssesmentData"%>
<%@page import="java.util.Calendar"%>
<%@page import="assesment.communication.assesment.AssesmentAttributes"%>
<%@page import="assesment.communication.assesment.AssesmentData"%>
<%@page import="java.util.Iterator"%>
<%@page import="assesment.communication.administration.user.UserSessionData"%>
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
	Calendar c = Calendar.getInstance();
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
		<style type="text/css">
		.texto{
			font-family: 'Roboto Thin',"Helvetica Neue", Helvetica, Arial, "Pontano Sans", Verdana, sans-serif;
			font-size: 16px;
		}
		.buttonRed{
			background-color: red;
			color: white;
			font-family: 'Roboto Thin',"Helvetica Neue", Helvetica, Arial, "Pontano Sans", Verdana, sans-serif;
			font-size: 16px;
	        border: none;
	        border-radius: 2px 2px 2px 2px;
			-moz-border-radius: 2px 2px 2px 2px;
			-webkit-border-radius: 2px 2px 2px 2px;
			border: 0px solid #000000;
			width:100px;
		    height:30px;
		}
		.buttonYellow{
			background-color: #e1d600;
			color: black;
			font-family: 'Roboto Thin',"Helvetica Neue", Helvetica, Arial, "Pontano Sans", Verdana, sans-serif;
			font-size: 16px;
	        border: none;
	        border-radius: 2px 2px 2px 2px;
			-moz-border-radius: 2px 2px 2px 2px;
			-webkit-border-radius: 2px 2px 2px 2px;
			border: 1px solid #a0a0a0;
			width:100px;
		    height:30px;
		}
		.buttonGreen{
			background-color: green;
			color: white;
			font-family: 'Roboto Thin',"Helvetica Neue", Helvetica, Arial, "Pontano Sans", Verdana, sans-serif;
			font-size: 16px;
	        border: none;
	        border-radius: 2px 2px 2px 2px;
			-moz-border-radius: 2px 2px 2px 2px;
			-webkit-border-radius: 2px 2px 2px 2px;
			border: 0px solid #000000;
			width:100px;
		    height:30px;
		}
		</style>
		<script>
		function enterDA(assessmentId) {
			var form = document.forms['SelectAssessmentForm'];
			form.assessment.value = assessmentId;
			form.submit();
		}
		</script>
	</head>
	<html:form action="/SelectAssessment">
		<html:hidden property="assessment"/>
	</html:form>
	<body class="login">
		<header id="header">
			<section class="grid_container">
				<h1 class="customer_logo" style="background-image: url('images/main_logo_large.png');">CEPA Driver Assessment</h1>
			</section>
		</header>

		<section id="content">
			<section class="grid_container">
				<nav class="sections" data-min-rel-top="0" data-min-rel-bottom="0">
					<div class="score">
						<h2 class="title">CEPA Driver Assessment</h2>
					</div>
					<p class="copyright">
						CEPA Safe Drive &copy; 2020
					</p>
				</nav>
				<form action="logout.jsp">
					<fieldset id="username_block" class="active">
						<div>
		                  	<h2><%=messages.getText("messages.generic.selectassessment")%></h2>
						</div>
						<br>
<%			String login = sys.getUserSessionData().getFilter().getLoginName();
			UsReportFacade usReport = sys.getUserReportFacade();
			UserSessionData userSessionData = sys.getUserSessionData();
			Iterator assessments = usReport.findTotalUserAssesments(login, userSessionData).iterator();
		    while(assessments.hasNext()) {         		
%>						<div>
<%				UserAssesmentData userAssessment = (UserAssesmentData)assessments.next();
				AssesmentData assessment = userAssessment.getAssessment();
				StringTokenizer tokenizer = new StringTokenizer(messages.getText(assessment.getName()), "-");
				String name = tokenizer.nextToken().trim();
				if(tokenizer.hasMoreTokens())
					name = tokenizer.nextToken().trim();
				Calendar start = Calendar.getInstance();
				start.setTime(assessment.getStart());
				Calendar end = Calendar.getInstance();
				end.setTime(assessment.getEnd());
				if(userAssessment.getEnd() != null) {
%>								<table width="100%" cellspacing="2" cellpadding="2">
									<tr class="texto">
										<td width="48%" class="texto"><%=name%></td>
										<td width="4%"></td>
										<td width="48%" class="texto"><%=messages.getText("assesment.status.ended")%></td>
									</tr>
								</table>
<%				}else if(assessment.getStatus().intValue() != AssesmentAttributes.NO_EDITABLE) {
%>								<table width="100%" cellspacing="2" cellpadding="2">
									<tr>
										<td width="48%" class="texto"><%=name%></td>
										<td width="4%"></td>
										<td width="48%" class="texto"><%=messages.getText("generic.messages.notavailable") %></td>
									</tr>
								</table>							
<%				}else if(c.before(start)) {
%>								<table width="100%" cellspacing="2" cellpadding="2">
									<tr>
										<td width="48%" class="texto"><%=name%></td>
										<td width="4%"></td>
										<td width="48%" class="texto"><%=messages.getText("generic.messages.available") %>:	<%=Util.formatDate(assessment.getStart()) %></td>
									</tr>
								</table>						
<%				}else if(c.after(end)) {
%>								<table width="100%" cellspacing="2" cellpadding="2">
									<tr>
										<td width="48%" class="texto"><%=name%></td>
										<td width="4%"></td>
										<td width="48%"><input type="button" class="buttonRed" value='<%=messages.getText("assesment.status.ended")%>' ></td>
									</tr>
								</table>						
<%				}else {
					if(userAssessment.getAnswers() > 0){
%>								<table width="100%" cellspacing="2" cellpadding="2">
									<tr>
										<td width="48%" class="texto"><%=name%></td>
										<td width="4%"></td>
										<td width="48%"><input type="button" class="buttonYellow" value="<%=messages.getText("generic.messages.tocomplete")%>" onclick="enterDA(<%=String.valueOf(assessment.getId())%>)"></td>
									</tr>
								</table>
<%					}else {
						String dependency = usReport.checkDependencies(login, assessment.getId(), userSessionData);
						if(dependency == null) {
%>								<table width="100%" cellspacing="2" cellpadding="2">
									<tr>
										<td width="48%" class="texto"><%=name%></td>
										<td width="4%"></td>
										<td width="48%"><input type="button" class="buttonGreen" value="<%=messages.getText("generic.messages.start")%>" onclick="enterDA(<%=String.valueOf(assessment.getId())%>)"></td>
									</tr>
								</table>
<%						}else {
%>								<table width="100%" cellspacing="2" cellpadding="2">
									<tr>
										<td width="48%" class="texto"><%=name%></td>
										<td width="4%"></td>
										<td width="48%" class="texto"><%=messages.getText("generic.messages.dependency1")+" <b>"+messages.getText(dependency)+"</b>."%></td>
									</tr>
								</table>
<%	
						}
					}
				}
%>
						</div>
						<hr>
<%		    }
%>
						<br>
						<input type="submit" class="button" value='<%=messages.getText("generic.messages.logout")%>' />
						<br>
					</fieldset>
				</form>
			</section>
		</section>
		<script type="text/javascript" src="scripts/jquery-2.0.2.min.js"></script>
		<script type="text/javascript" src="scripts/assessment.js"></script>
	</body>
</html:html>
