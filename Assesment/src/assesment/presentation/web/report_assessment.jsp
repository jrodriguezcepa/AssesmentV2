<%@page import="assesment.presentation.translator.web.util.Util"%>
<%@page import="assesment.communication.assesment.AssesmentListData"%>
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
	UserSessionData userSessionData = sys.getUserSessionData();
	UserData userData = sys.getUserReportFacade().findUserByPrimaryKey(userSessionData.getFilter().getLoginName(),userSessionData);
%>

<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="assesment.communication.assesment.AssesmentAttributes"%>
<%@page import="assesment.communication.administration.user.UserSessionData"%>
<%@page import="assesment.communication.user.UserData"%>
<%@page import="assesment.communication.security.SecurityConstants"%>
<%@page import="assesment.communication.util.ListResult"%><html:html>
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
            	<div class="toolbar">
               		<span class="username"><%=userData.getFirstName()+" "+userData.getLastName() %></span>
               		<span class="exit"><a href="logout.jsp"><%=messages.getText("generic.messages.logout")%></a></span>
            	</div>
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
				<form name="selectDA" action="report.jsp">
					<fieldset id="username_block" class="active">
						<br>
						<br>
						<div>
		                  	<label for="accesscode"><%=messages.getText("generic.messages.selectassessment")%></label>
						</div>
						<br>
						<div>
		                  	<label for="accesscode">
		                  		<select name="id">
<%		if(userSessionData.getRole().equals(SecurityConstants.CEPA_REPORTER) || userSessionData.getRole().equals(SecurityConstants.ADMINISTRATOR)) {
			ListResult result = sys.getAssesmentReportFacade().findAssesments("","","0",sys.getUserSessionData());
			Iterator<AssesmentListData> it = Util.getAssessmentIterator(result.getElements(), messages);
			while(it.hasNext()) {
				AssesmentListData assesment = it.next();
%>					      			<option value='<%=String.valueOf(assesment.getId())%>'><%=(assesment.getName())%></option>
<%			}
		} else {
			Collection assessments = sys.getUserReportFacade().findUserReportAssesments(sys.getLoginUser(),sys.getUserSessionData());
			Iterator<AssesmentListData> it = Util.getAssessmentAttrIterator(result.getElements(), messages);
			while(it.hasNext()) {
				AssesmentListData assesment = it.next();
%>					      			<option value='<%=String.valueOf(assesment.getId())%>'><%=(assesment.getName())%></option>
<%			}
		}
%>		
		                  		</select>
		                  	</label>
						</div>
						<br>
						<input type="submit" class="button" value='<%=messages.getText("generic.messages.go")%>'/>
						<br>
						<br>
					</fieldset>
				</form>
			</section>
		</section>
		<script type="text/javascript" src="scripts/jquery-2.0.2.min.js"></script>
		<script type="text/javascript" src="scripts/assessment.js"></script>
	</body>
</html:html>
