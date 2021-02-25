<%@page import="assesment.communication.administration.user.UserSessionData"%>
<%@page import="assesment.business.administration.user.UsReportFacade"%>
<%@page import="java.util.Iterator"%>
<%@page import="assesment.persistence.user.tables.UserAssesmentData"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.LinkedList"%>
<%@page import="assesment.communication.language.Text"%>
<%@page import="assesment.business.AssesmentAccess"%>
<%@page import="java.util.Enumeration"%>
<%@ page language="java"
	import="assesment.presentation.translator.web.util.*"	
	import="assesment.presentation.translator.web.administration.user.*"
	import="assesment.communication.util.MD5"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html" 
%>

<%@ taglib uri="/WEB-INF/struts-bean.tld"
        prefix="bean" 
%>

<%	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	Text messages = sys.getText();
	String login = sys.getUserSessionData().getFilter().getLoginName();
	UsReportFacade usReport = sys.getUserReportFacade();
	UserSessionData userSessionData = sys.getUserSessionData();
	Iterator assessmentsIt = usReport.findTotalUserAssesments(login, userSessionData).iterator();
	Collection available = new LinkedList();
    while(assessmentsIt.hasNext()) {         		
		UserAssesmentData userAssessment = (UserAssesmentData)assessmentsIt.next();
		if(userAssessment.getEnd() == null)
			available.add(userAssessment.getAssessment().getId());
    }
	int[] assessments = {0, 502, 504, 503};
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
		<title>CEPA Assessment - Marsh</title>
		<link href="../util/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">

		<link rel="shortcut icon" type="image/ico" href="images/favicon.ico">
		<link rel="stylesheet" href="styles/fonts/pontano_sans.css">
		<link rel="stylesheet" href="styles/jquery-ui-1.10.3.custom.min.css">
		<!--[if lt IE 9]>
			<script type="text/javascript" src="scripts/html5shiv.min.js"></script>
		<![endif]-->	
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
	<form name="logout" action="logout.jsp">
	</form>
	<body>
		<div class="container" style="max-width:800px; text-align:center;" >
	  		<div class="row justify-content-md-center">
				<h2 class="title" style="padding:20px;"><img src="./imgs/marshHeader.png" ></h2>
<%		for(int i = 1; i <= 3; i++) {
			if(available.contains(new Integer(assessments[i]))) {
%>
				<div class="col-md-12" style="margin-bottom:15px">
					<div class="col-md-6" align="right">
						<a href='javascript:enterDA(<%=String.valueOf(assessments[i])%>)'>
							<img src='<%="./imgs/marshImage"+i+".png"%>' >
						</a>
					</div>
					<div class="col-md-6" align="left">
						<div class="col-md-12" align="left">
							<a href='javascript:enterDA(<%=String.valueOf(assessments[i])%>)'>
								<img src='<%="./imgs/marshTitle"+i+".png"%>' >
							</a>
						</div>
						<div class="col-md-12" align="left">
							<img src='<%="./imgs/marshText"+i+".png"%>' >
						</div>
					</div>
				</div>
<%			}else {
%>
				<div class="col-md-12" style="margin-bottom:15px">
					<div class="col-md-6" align="right">
						<img src='<%="./imgs/marshImage"+i+".png"%>' >
					</div>
					<div class="col-md-6" align="left">
						<div class="col-md-12" align="left">
							<img src='<%="./imgs/marshTitle"+i+".png"%>' >
						</div>
						<div class="col-md-12" align="left">
							<img src='<%="./imgs/marshText"+i+".png"%>' >
						</div>
					</div>
				</div>
<%			}
		}
%>				<div class="col-md-12" align="right" style="margin-bottom:15px; margin-right:30px;">
		  			<input type="button" value='<%=messages.getText("generic.messages.logout")%>' onClick="javascript:document.forms['logout'].submit()" />
				</div>
			</div>
		</div>
	</body>
</html:html>
