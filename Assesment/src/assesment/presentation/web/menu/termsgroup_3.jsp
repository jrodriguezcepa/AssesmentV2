<!doctype html>
<%@page import="assesment.business.AssesmentAccess"%>
<%@page import="assesment.communication.assesment.AssesmentData"%>
<%@page import="java.util.Iterator"%>
<%@page import="assesment.communication.module.ModuleData"%>
<%@page import="assesment.communication.administration.user.UserSessionData"%>
<%@page import="assesment.communication.language.Text"%>
<%@page import="assesment.communication.user.UserData"%>

<%@page import="java.util.HashMap"%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>


<html:html>

<%
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	UserSessionData userSessionData = sys.getUserSessionData();
	Text messages = sys.getText(); 
	UserData userData = sys.getUserReportFacade().findUserByPrimaryKey(userSessionData.getFilter().getLoginName(),userSessionData);
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
				font-size: 2.0em;
			}
			.tabla {
				margin-left: 10px;
				margin-bottom: 15px;
				color: white;
				font-family: Roboto, Helvetica, Arial, sans-serif;
				font-size: 11;
				text-align: left;
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
			.cellText {
				color: white;
				font-family: Roboto, Helvetica, Arial, sans-serif;
				font-size: 11;
				text-align: center;
				text-align: center;
			}
			.cell1 {
				background-color: #27313A;
				border-color: white;
				border-width: 1px;
				border-style: solid;
				text-align: center;
				height: 30px;
			}
			.cellData {
				border-color: white;
				border-width: 1px;
				border-style: solid;
				text-align: left;
				padding: 5px;
				height: 30px;
			}
			.cellData1 {
				background-color: #27313A;
				border-color: white;
				border-width: 1px;
				border-style: solid;
				text-align: left;
				padding: 5px;
				height: 30px;
			}
			.cellRed {
				background-color: #E76768;
				border-color: white;
				border-width: 1px;
				border-style: solid;
				text-align: center;
				height: 30px;
				vertical-align: middle;
			}
			.cellYellow {
				background-color: #F5CD78;
				border-color: white;
				border-width: 1px;
				border-style: solid;
				text-align: center;
				height: 30px;
			}
			.cellGreen {
				background-color: #3EC1D3;
				border-color: white;
				border-width: 1px;
				border-style: solid;
				text-align: center;
				height: 30px;
			}
		</style>
	</head>
	<body>
		<div style="width: 80%; margin-left: 40px;">
			<div>
				<img src="./imgs/logo.png">
			</div>
			<div class="title">
				<%=messages.getText("generic.terms.title") %>
			</div>
	        <form name="accept" action="acceptTerms.jsp" method="post">
 					
<%		for(int i = 1; i <= 5; i++) {
%>				<div class="tabla">
					<%=messages.getText("generic.terms.line"+i)%>
				</div>
<%		}
%>            	<br>	
				<div class="tabla" style="float: left;">
					<strong><%=messages.getText("generic.terms.read") %> </strong>
				</div>
    			<div style="float: right;">
     				<div style="float: left; margin-right: 15px;">
						<a href="javascript:document.forms['accept'].submit();">
							<img src="images/checkVerde.png" width="45"/>
						</a>
					</div>
     				<div style="float: right; margin-left: 15px; margin-right: 15px;">
						<a href="logout.jsp">
							<img src="images/checkRojo.png" width="45" />
						</a>  
					</div>
				</div>
	   		</form>
	   	</div>					
	</body>
</html:html>