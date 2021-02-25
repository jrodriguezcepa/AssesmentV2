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
	boolean check = false;
	Integer groupId = new Integer(request.getParameter("group"));
	String role = userSessionData.getRole(); 
	if(role.equals(SecurityConstants.ADMINISTRATOR) || role.equals(SecurityConstants.CLIENTGROUP_REPORTER)) {
		UsReportFacade usReport = sys.getUserReportFacade();
		AssesmentReportFacade assessmentReport = sys.getAssesmentReportFacade();
		UserData userData = usReport.findUserByPrimaryKey(userSessionData.getFilter().getLoginName(),userSessionData);
		GroupData group = (role.equals(SecurityConstants.ADMINISTRATOR)) ? assessmentReport.findGroup(groupId,sys.getUserSessionData()) : assessmentReport.getUserGroup(userSessionData.getFilter().getLoginName(),userSessionData);
		Collection<UserData> users = usReport.findGroupUsers(group.getId(), sys.getUserSessionData());
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
		function openCNH(user) {
			var form = document.forms['fotos'];
			form.user.value = user;			
			form.submit();
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
			.cellFotos {
				background-color: #CDCDCD;
				color: #3D3D3D;
				border-color: white;
				border-width: 1px;
				border-style: solid;
				text-align: center;
				height: 30px;
			}
		</style>
	</head>
	<body>
		<html:form action="/DownloadResult" target="_blank">
			<html:hidden property="assessment" />
			<html:hidden property="login" />
			<html:hidden property="reportType" />
		</html:form>	
		<html:form action="/DownloadGroup">
			<html:hidden property="group" value="<%=String.valueOf(group.getId())%>" />
		</html:form>
		<form action="./layout.jsp?refer=/assesment/viewGroup.jsp" name='back' method="post">
			<input type="hidden" name="id" value="<%=String.valueOf(groupId)%>" />
		</form>	
		<form action="./viewCNH.jsp" name='fotos' method="post" target="_blank">
			<input type="hidden" name="user" />
		</form>	
		<div>
			<img src="./imgs/logo.png">
		</div>
		<br>
		<br>
		<div>
			<table width="100%">
				<tr>
					<td class="title" width="50%" align="left">
						<%=messages.getText(group.getName())%>
					</td>
					<td width="20%" align="center">
						<a href="javascript:document.forms['DownloadGroupReportForm'].submit()" style="text-decoration: none;">
							<span class="subtitle">
								<%=messages.getText("assesment.report.download")%>
							</span>
						</a>
					</td>
					<td width="20%" align="center">
<%			String linkTotal = "javascript:openGraph(0,'"+messages.getText("report.users.total.count")+"')";
%>					
						<a href="<%=linkTotal%>" style="text-decoration: none;">
							<span class="subtitle">
								<%=messages.getText("report.users.total.count")%>
							</span>
						</a>
					</td>
<%			if(role.equals(SecurityConstants.ADMINISTRATOR)) {
%>					<td width="10%" align="center">
						<a href="javascript:document.forms['back'].submit();" style="text-decoration: none;">
							<span class="subtitle">
								<%=messages.getText("generic.messages.back")%>
							</span>
						</a>
					</td>
<%			} else {
%>					<td width="10%" align="center">
						<a href="logout.jsp" style="text-decoration: none;">
							<span class="subtitle">
								<%=messages.getText("generic.messages.logout")%>
							</span>
						</a>
					</td>
<%			} 
%>				</tr>
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
					<td colspan='6'></td>
<%			Iterator<CategoryData> itC = group.getOrderedCategories();
			int lenA = 0;
			for(int i = 0; i < 2 && itC.hasNext(); i++) {
				CategoryData c = itC.next();
				lenA += c.getAssesments().size();
%>					<td colspan="<%=String.valueOf(c.getAssesments().size())%>" class="cell"><%=messages.getText(c.getKey()) %></td>				
<%			}
%>				</tr>
				<tr>
					<td><%=messages.getText("user.data.nickname").toUpperCase()%></td>
					<td><%=messages.getText("user.data.firstname").toUpperCase()%></td>
					<td><%=messages.getText("user.data.lastname").toUpperCase()%></td>
					<td><%=messages.getText("user.data.mail").toUpperCase()%></td>
					<td style="min-width: 150px;"><%=messages.getText("module3872.question47017.text")%></td>
					<td style="min-width: 150px;"><%=messages.getText("module3872.question47022.text")%></td>
<%			itC = group.getOrderedCategories();
			AssesmentAttributes[] assessmentIds = new AssesmentAttributes[lenA];
			int index = 0;
			HashMap<Integer, int[]> graphs = new HashMap<Integer, int[]>();
			graphs.put(0, new int[]{0, 0, 0, 0});
			while(itC.hasNext() && index < lenA) {
				CategoryData c = itC.next();
				Iterator<AssesmentAttributes> itA = c.getOrderedAssesments();
				while(itA.hasNext() && index < lenA) {
					AssesmentAttributes a = itA.next();
					assessmentIds[index] = a;
					graphs.put(a.getId(), new int[]{0, 0, 0, 0});
					String link = "report.jsp?id="+a.getId();
					String link2 = "javascript:openGraph("+a.getId()+",'"+messages.getText(a.getName())+"')";
					index++;
%>					<td class="cell">
						<a href="<%=link2%>" style="text-decoration: none;">
							<img src="./imgs/graph.png"  width=15>
						</a>
						<a href='<%=link%>' style="text-decoration: none;">
							<span class="tabla">
								<%=messages.getText(a.getName()) %>
							</span>
						</a>
					</td>				
<%				}
			}
%>				</tr>
<%			HashMap<String, HashMap<Integer, Object[]>> userResults = assessmentReport.getUserGroupResults(group.getId(), sys.getUserSessionData());
			HashMap<String, HashMap<Integer, Object[]>> userSpecificQuestions = assessmentReport.getUserSpecificQuestion("47017,47022,47393,47394,47395,47396", null, sys.getUserSessionData());
			Iterator<UserData> it = users.iterator();
			boolean line = true;
			while(it.hasNext()) {
				UserData user = it.next();
				HashMap<Integer, Object[]> values = (userResults.containsKey(user.getLoginName())) ? userResults.get(user.getLoginName()) : new HashMap<Integer, Object[]>();
				String cellName = (line) ? "cellData1" : "cellData";
				line = !line;
%>				<tr>
					<td class="<%=cellName%>"><%=user.getLoginName() %></td>
					<td class="<%=cellName%>"><%=user.getFirstName() %></td>
					<td class="<%=cellName%>"><%=user.getLastName() %></td>
					<td class="<%=cellName%>"><%=user.getEmail() %></td>
<%				if(userSpecificQuestions.containsKey(user.getLoginName())) {
					HashMap<Integer, Object[]> values2 = userSpecificQuestions.get(user.getLoginName());
					if(values2.containsKey(47017)) { 
						String cellName2 = (((Integer)values2.get(47017)[1]).intValue() == 150115) ? "cellRed" : "cellGreen";
%>					<td class='<%=cellName2%>'><%=messages.getText((String)values2.get(47017)[2])%></td>
<%					}else {
%>					<td class="<%=cellName%>"><%=messages.getText("generic.report.pending")%></td>	
<%					}
					if(values2.containsKey(47022)) { 
						String cellName2 = (((Integer)values2.get(47022)[1]).intValue() == 150136) ? "cellRed" : "cellGreen";
%>					<td class='<%=cellName2%>'><%=messages.getText((String)values2.get(47022)[2])%></td>
<%					}else {
%>					<td class="<%=cellName%>"><%=messages.getText("generic.report.pending")%></td>	
<%					}
				}else {
%>					<td class="<%=cellName%>"><%=messages.getText("generic.report.pending")%></td>	
					<td class="<%=cellName%>"><%=messages.getText("generic.report.pending")%></td>	
<%				}
				for(int i = 0; i < assessmentIds.length; i++) {
					AssesmentAttributes assAtt = assessmentIds[i];
					if(assAtt.getId().intValue() == 1379) {
						if(userSpecificQuestions.containsKey(user.getLoginName())) {
							HashMap<Integer, Object[]> values2 = userSpecificQuestions.get(user.getLoginName());
							if(values2.containsKey(47393) || values2.containsKey(47394) || values2.containsKey(47395) || values2.containsKey(47396)) {
%>					<td class="cellFotos">
						<a href="javascript:openCNH('<%=user.getLoginName()%>')">
							<%=messages.getText("generic.view")%>
						</a>
					</td>
<%							}else {
%>					<td class="<%=cellName%>"><%=messages.getText("generic.report.pending")%></td>
<%							}
						}else {
%>					<td class="<%=cellName%>"><%=messages.getText("generic.report.pending")%></td>	
<%						}
					}else {
						if(values.containsKey(assAtt.getId())) {
							Object[] data = values.get(assAtt.getId());
							if(((Integer)data[0]).intValue() == 0) {
								graphs.get(assAtt.getId())[3]++;
%>					<td class="<%=cellName%>"><%=messages.getText("generic.report.pending")%></td>
<%							} else {
								String className = "cellGreen";
								int percent = ((Integer)data[2]).intValue() * 100 / (((Integer)data[2]).intValue() + ((Integer)data[3]).intValue());
								if(percent < assAtt.getYellow()) {
									className = "cellRed";
									graphs.get(assAtt.getId())[0]++;
								} else if(percent < assAtt.getGreen()) {
									className = "cellYellow";
									graphs.get(assAtt.getId())[1]++;
								} else {
									graphs.get(assAtt.getId())[2]++;
								}
%>					<td class="<%=className%>">
						<table>
							<tr>
								<td class="cellText" width="75%">
									<a href='<%="javascript:generateReport(\""+user.getLoginName()+"\","+assAtt.getId()+",1);"%>' class="cellText" style="text-decoration:none;">
										<%=Util.formatDate((Date)data[1])+" ("+percent+"%)"%>
									</a>
								</td>
<%								if(assAtt.isCertificate() && className.equals("cellGreen")) {
%>								<td width="25%">
									<a href='<%="javascript:generateReport(\""+user.getLoginName()+"\","+assAtt.getId()+",2);"%>'>
										<img src="./imgs/downloadw.png" style="margin: 3px; width:20px;">
									</a>
								</td>
<%								}	
%>							</tr>
						</table>
					</td>
<%							}
						} else {
							graphs.get(assAtt.getId())[3]++;
%>					<td class="<%=cellName%>"><%=messages.getText("generic.report.pending")%></td>
<%						}
					}
				}
%>				</tr>						
<%			}
%>			</table>
		</div>
		<script type="text/javascript" src="https://www.google.com/jsapi"></script>
		<script type="text/javascript">
		  google.charts.load('current', {'packages':['corechart']});
		  google.charts.setOnLoadCallback(drawChart);
		  var data = [];

		  function drawChart() {
<%		int[] v = {0, 0, 0, 0};
		for(int i = 0; i < assessmentIds.length; i++) {
			int[] t = (int[])graphs.get(assessmentIds[i].getId());
%>			
			var d = new google.visualization.DataTable();
	        d.addColumn('string', 'V1');
	        d.addColumn('number', 'V2');
	 		d.addRow(['<%=messages.getText("generic.report.lowlevel")+" ("+t[0]+")"%>', <%=String.valueOf(t[0])%>]);
	 		d.addRow(['<%=messages.getText("generic.report.meddiumlevel")+" ("+t[1]+")"%>', <%=String.valueOf(t[1])%>]);
	 		d.addRow(['<%=messages.getText("generic.report.highlevel")+" ("+t[2]+")"%>', <%=String.valueOf(t[2])%>]);
	 		d.addRow(['<%=messages.getText("generic.report.pending")+" ("+t[3]+")"%>', <%=String.valueOf(t[3])%>]);
	 		data[<%=String.valueOf(assessmentIds[i].getId())%>] = d;
<%			for(int j = 0; j < 4; j++) {
				v[j] += t[j];
			}
		}
%>
	 		
	 		d = new google.visualization.DataTable();
	        d.addColumn('string', 'V1');
	        d.addColumn('number', 'V2');
	 		d.addRow(['<%=messages.getText("generic.report.lowlevel")+" ("+v[0]+")"%>', <%=String.valueOf(v[0])%>]);
	 		d.addRow(['<%=messages.getText("generic.report.meddiumlevel")+" ("+v[1]+")"%>', <%=String.valueOf(v[1])%>]);
	 		d.addRow(['<%=messages.getText("generic.report.highlevel")+" ("+v[2]+")"%>', <%=String.valueOf(v[2])%>]);
	 		d.addRow(['<%=messages.getText("generic.report.pending")+" ("+v[3]+")"%>', <%=String.valueOf(v[3])%>]);
	 		data[0] = d;
		  }
		</script>
<%		}
	%>
	</body>
</html:html>