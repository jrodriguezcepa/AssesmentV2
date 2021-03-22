<%@page import="java.util.List"%>
<%@page import="java.util.Collections"%>
<%@page import="assesment.presentation.translator.web.util.Util"%>
<%@page import="assesment.communication.administration.UserMultiAnswerData"%>
<%@page import="assesment.communication.administration.MultiAnswerUserData"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
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
	boolean check = false;
	Integer assessmentId = new Integer(request.getParameter("id"));
	if(userSessionData.getRole().equals(SecurityConstants.ADMINISTRATOR) || userSessionData.getRole().equals(SecurityConstants.CLIENT_REPORTER)) {
		UserData userData = sys.getUserReportFacade().findUserByPrimaryKey(userSessionData.getFilter().getLoginName(),userSessionData);
		AssesmentAttributes assessment = sys.getAssesmentReportFacade().findAssesmentAttributes(assessmentId, userSessionData);
		Calendar c = Calendar.getInstance();
		Date to = c.getTime();
		c.add(Calendar.MONTH, -2);
		Date from = c.getTime();
		Collection<MultiAnswerUserData> users = sys.getUserReportFacade().findMultiAnswerUsers(assessmentId, from, to, sys.getUserSessionData());
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
      	<script type="text/javascript">

      // Load the Visualization API and the piechart package.
	      google.charts.load('current', {'packages':['corechart','table']});
		function generateReport(user, assessment, report) {
			var form = document.forms['DownloadResultReportForm'];
			form.login.value = user;			
			form.assessment.value = assessment;			
			form.reportType.value = report;
			form.submit();
		}
		function openGraph(index, titleStr) {

	        // Set chart options
	        var options = {'width':750,
       			  	'height':300,
       				pieHole: 0.7,
       				legend: {position: 'right', textStyle: {color: 'white', fontName: 'Roboto', fontSize: 10}},
       				'colors':['#E76768','#F5CD78','#3EC1D3','#A2A2A2'],
       				chartArea:{left:120,top:50,width:'70%',height:'85%'},
               		pieSliceTextStyle: { color: 'white', fontName: 'Roboto', fontSize: '10' },
               		title: titleStr,
               		titleTextStyle: { color: 'white', fontName: 'Roboto', fontSize: '16' },
               		backgroundColor: 'transparent'
               };

        	var chart = new google.visualization.PieChart(document.getElementById('graphContent'));
       		chart.draw(data[index], options);
       		document.getElementById('graphContent').style.display = '';
       		document.getElementById('closeContent').style.display = '';
		}
		function cerrarGraph() {
			document.getElementById('graphContent').style.display = 'none';
			document.getElementById('closeContent').style.display = 'none';
		}
		</script>
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
			.tablaError {
				margin-left: 5px;
				color: black;
				font-family: Roboto, Helvetica, Arial, sans-serif;
				font-size: 10;
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
				background-color: #3ED3C1;
				border-color: white;
				border-width: 1px;
				border-style: solid;
				text-align: center;
				height: 30px;
			}
		</style>
		<script type="text/javascript">
			function openError(id){
				var element = document.getElementById(id);
				if(element.style.display == '') {
					element.style.display = 'none';
				}else {
					element.style.display = '';
				}
			}
		</script>
	</head>
	<body>
		<html:form action="/DownloadMulti">
			<html:hidden property="assessment" value="<%=String.valueOf(assessmentId)%>" />
		</html:form>
		<div>
			<img src="./imgs/logo.png">
		</div>
		<br>
		<br>
		<div>
			<table width="100%">
				<tr>
					<td class="title" width="50%" align="left">
						<%=messages.getText(assessment.getName())%>
					</td>
					<td width="25%" align="center">
						<a href="javascript:document.forms['DownloadMultiReportForm'].submit()" style="text-decoration: none;">
							<span class="subtitle">
								<%=messages.getText("assesment.report.download")%>
							</span>
						</a>
					</td>
					<td width="25%" align="center">
						<a href="logout.jsp" style="text-decoration: none;">
							<span class="subtitle">
								<%=messages.getText("generic.messages.logout")%>
							</span>
						</a>
					</td>
				</tr>
			</table>
		</div>
		<br>
		<div id="closeContent" style="width: 100%; text-align: center; display:none;">
			<a href="javascript:cerrarGraph()" >
				<img src="imgs/cross.png">
			</a>
		</div>
		<div id="graphContent" style="width: 100%; text-align: center;">
		</div>
		<br>
		<div>
			<table class="tabla">
				<tr>
					<td><%=messages.getText("user.data.ci").toUpperCase()%></td>
					<td><%=messages.getText("user.data.firstname").toUpperCase()%></td>
					<td><%=messages.getText("user.data.lastname").toUpperCase()%></td>
					<td><%=messages.getText("assessment.enddate").toUpperCase()%></td>
					<td><%=messages.getText("assessment.result").toUpperCase()%></td>
				</tr>
<%			Collections.sort((List<MultiAnswerUserData>)users);
			Iterator<MultiAnswerUserData> it = users.iterator();
			boolean line = true;
			int index = 0;
			while(it.hasNext()) {
				index++;
				String link = "error_"+index;
				MultiAnswerUserData user = it.next();
				String cellName = (line) ? "cellData1" : "cellData";
				line = !line;
%>				<tr>
					<td class="<%=cellName%>"><%=user.getLoginName().replace("FY", "")%></td>
					<td class="<%=cellName%>"><%=user.getFirstName() %></td>
					<td class="<%=cellName%>"><%=user.getLastName() %></td>
					<td class="<%=cellName%>"><%=Util.formatDateHour(user.getEnd()) %></td>
<%				if(user.getResult().equals("approuved")) {
%>					<td class="cellGreen">
						<%=messages.getText("result."+user.getResult())%>
					</td>
<%				}else {
%>					<td class="cellRed">
						<a href="javascript:openError('<%=link%>');">
							<span style="color: white;"><%=messages.getText("result."+user.getResult())%></span>
						</a>
					</td>
<%				}
%>				</tr>						
<%				if(!user.getResult().equals("approuved")) {
%>				<tr id='<%=link%>' style="display:none;">
					<td colspan="5" style="background: #CCCCCC;">
						<table width="90%" align="center">
							<tr class="tablaError" style="background: #ACACAC;">
								<th width="75%"><%=messages.getText("generic.question")%></th>
								<th width="25%"><%=messages.getText("question.resultreport.answer")%></th>
							</tr>
<%					Iterator<UserMultiAnswerData> it2 = user.getAnswers().iterator();
					while(it2.hasNext()) {
						UserMultiAnswerData answer = it2.next();
%>							<tr class="tablaError" style="background: #FFFFFF;">
								<td width="75%"><%=messages.getText(answer.getQuestion())%></td>
								<td width="25%"><%=messages.getText(answer.getAnswer())%></td>
							</tr>
<%					}
%>
						</table>
					</td>
				</tr>
<%				}
			}
%>			</table>
		</div>
<%		}
%>
	</body>
</html:html>