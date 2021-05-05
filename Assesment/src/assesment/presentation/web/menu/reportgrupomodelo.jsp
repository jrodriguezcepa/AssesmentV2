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
	String role = userSessionData.getRole(); 
	String value = "";
	String firstName = "";
	String lastName = "";
	String userName = "";

	if(request.getParameter("value")!=null){
		value = request.getParameter("value");
	}	
	if(request.getParameter("firstName")!=null){
		firstName = request.getParameter("firstName");
	}	
	if(request.getParameter("lastName")!=null){
		lastName = request.getParameter("lastName");
	}	
	if(request.getParameter("userName")!=null){
		userName = request.getParameter("userName");
	}	
	String link="reportgrupomodelo.jsp";
	HashMap<Integer, int[]> graphs = new HashMap<Integer, int[]>();
	if(role.equals(SecurityConstants.ADMINISTRATOR) || role.equals(SecurityConstants.CLIENTGROUP_REPORTER)) {
		UsReportFacade usReport = sys.getUserReportFacade();
		AssesmentReportFacade assessmentReport = sys.getAssesmentReportFacade();
		UserData userData = usReport.findUserByPrimaryKey(userSessionData.getFilter().getLoginName(),userSessionData);
		int idGroup = 14;
		int colspan = 4;
		Integer[] cedis = sys.getCorporationReportFacade().findCediUser(userData.getLoginName(), userSessionData);
		Collection<UserData> users = usReport.findCediUsers(cedis, value, firstName, lastName, userName, sys.getUserSessionData());
		graphs.put(1, new int[]{0, 0, 0, 0});
		graphs.put(2, new int[]{0, 0, 0, 0});

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
		function openFoto(user) {
			var form = document.forms['fotos'];
			form.user.value = user;			
			form.submit();
		}
		</script>
		<style type="text/css">
			body {
				background-color: #FFFFFF;
				margin-left: 10px;
				margin-top: 10px;
				margin-right: 10px;
				margin-bottom: 0px;
			}
			.title {
				margin-left: 10px;
				margin-top: 20px;
				margin-bottom: 20px;
				color: #1D272D;
				font-family: Roboto, Helvetica, Arial, sans-serif;
				font-size: 2.5em;
			}
			.subtitle {
				margin-left: 10px;
				margin-top: 20px;
				margin-bottom: 20px;
				color: #1D272D;
				font-family: Roboto, Helvetica, Arial, sans-serif;
				font-size: 1.5em;
			}
			.tabla {
				margin-left: 10px;
				color: #1D272D;
				font-family: Roboto, Helvetica, Arial, sans-serif;
				font-size: 11;
				text-align: center;
				width:98%;
				padding: 0;
				border-spacing: 0;
			}
			.cell {
				min-width: 130px;
				border-color: #1D272D;
				border-width: 1px;
				border-style: solid;
				text-align: center;
				height: 30px;
			}
			.cellText {
				color: #1D272D;
				font-family: Roboto, Helvetica, Arial, sans-serif;
				font-size: 11;
				text-align: center;
				text-align: center;
			}
			.cell1 {
				background-color: white;
				border-color: #1D272D;
				border-width: 1px;
				border-style: solid;
				text-align: center;
				height: 30px;
			}
			.cellData {
				border-color: #1D272D;
				border-width: 1px;
				border-style: solid;
				text-align: left;
				padding: 5px;
				height: 30px;
			}
			.cellData1 {
				background-color: #DCDCDC;
				border-color: black;
				border-width: 1px;
				border-style: solid;
				text-align: left;
				padding: 5px;
				height: 30px;
			}
			.cellRed {
				background-color: red;
				color:white;
				border-color: #1D272D;
				border-width: 1px;
				border-style: solid;
				text-align: center;
				height: 30px;
				vertical-align: middle;
			}
			.cellYellow {
				background-color: yellow;
				border-color: #1D272D;
				border-width: 1px;
				border-style: solid;
				text-align: center;
				height: 30px;
			}
			.cellGreen {
				background-color: green;
				color:white;
				border-color: #1D272D;
				border-width: 1px;
				border-style: solid;
				text-align: center;
				height: 30px;
			}
			.searchText {
				color: #1D272D;
				font-family: Roboto, Helvetica, Arial, sans-serif;
				font-size: 13;
				text-align: left;
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
			<html:hidden property="group" value="<%=String.valueOf(GroupData.GRUPO_MODELO)%>" />
		</html:form>
		<form action="./viewFoto.jsp" name='fotos' method="post" target="_blank">
			<input type="hidden" name="user" />
		</form>	
		<div>
			<img src="./imgs/logocepa.jpg">
		</div>
		<br>
		<br>
		<div>
			<table width="100%">
				<tr>
					<td class="title" width="30%" align="left">
						Grupo Modelo
					</td>
					<td width="20%" align="center">
						<a href="javascript:document.forms['DownloadGroupReportForm'].submit()" style="text-decoration: none;">
							<span class="subtitle">
								<%=messages.getText("assesment.report.download")%>
							</span>
						</a>
					</td>
					<td width="20%" align="center">
						<a href="associateDA.jsp?type=1" style="text-decoration: none;">
							<span class="subtitle">
								Asociar DA
							</span>
						</a>
					</td>
					<td width="20%" align="center">
						<a href="associateDA.jsp?type=2" style="text-decoration: none;">
							<span class="subtitle">
								Asociar eBTW
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
		 <tr>
	    		<td width="100%" valign="top">
		    		<table width="100%" border="0" cellpadding="1" cellspacing="1">
		    			<tr>
		    				<div class="searchText" style="font-weight:600;"><%= messages.getText("generic.messages.search")%></div>
		    			
		    			</tr>
		    			
						<tr>
							<td align="left" class="searchText">
								<form action="<%=link%>" method="post">
									<span style="width:30px">Nombre</span>
									<input type="text" name="firstName" style="width: 200px;"  class="input" value='<%=firstName%>'/>
									<span style="width:30px">Apellido</span>
									<input type="text" name="lastName" style="width: 200px;"  class="input" value='<%=lastName%>'/>
									<span style="width:30px">Usuario</span>
									<input type="text" name="userName" style="width: 200px;"  class="input" value='<%=userName%>'/>
									<span style="width:30px"><%=messages.getText("generic.cedi")%></span>
									<input type="text" name="value" style="width: 200;"  class="input" value='<%=value%>'/>
									<input name="button" type="submit" value='<%=messages.getText("generic.messages.search")%>' class="input"/>
								</form>
							</td>
						</tr>
					</table>
					<jsp:include  page="component/utilitybox2bottom.jsp" />
				</td>
    		</tr>
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
					<th><%=messages.getText("user.data.nickname").toUpperCase()%></th>
					<th><%=messages.getText("user.data.firstname").toUpperCase()%></th>
					<th><%=messages.getText("user.data.lastname").toUpperCase()%></th>
					<th><%=messages.getText("user.data.mail").toUpperCase()%></th>
					<th>CEDI</th>
					<th>Posici&oacute;n</th>
					<th>Tipo de licencia</th>
					<th>Vigencia de la licencia</th>
					<th>Fecha de nacimiento</th>
					<th>Foto</th>
					<th>Driver Assessment</th>
					<th>eBTW</th>
				</tr>
<%			HashMap<String, HashMap<Integer, Object[]>> userResults = assessmentReport.getUserCediResults(cedis, sys.getUserSessionData());
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
					<td class="<%=cellName%>"><%=(user.getEmail() == null) ? "---" : user.getEmail() %></td>
					<td class="<%=cellName%>"><%=(user.getExtraData2() == null) ? "---" : user.getExtraData2() %></td>
<%				Object[] data0 = {null,null,null,null,null};
				if(values.containsKey(0)) {
					data0 = values.get(0);
				}
%>
					<td class="<%=cellName%>"><%=(data0[1] == null) ? "---" : messages.getText((String)data0[1])%></td>
					<td class="<%=cellName%>"><%=(data0[2] == null) ? "---" : messages.getText((String)data0[2])%></td>
					<td class="<%=cellName%>"><%=(data0[3] == null) ? "---" : Util.formatDate((Date)data0[3])%></td>
					<td class="<%=cellName%>"><%=(data0[4] == null) ? "---" : Util.formatDate((Date)data0[4])%></td>
<%				if(data0[0] != null) {
%>					<td class="cellGreen">
						<a href="javascript:openFoto('<%=user.getLoginName()%>')">
							<span style="color:white;">Ver</span>
						</a>
					</td>
<%				}else {
%>					<td class="<%=cellName%>">Pendiente</td>
<%				}
				if(values.containsKey(1)) {
					Object[] data = values.get(1);
					if(((Integer)data[0]).intValue() == 0) {
							graphs.get(1)[3]++;
%>					<td class="<%=cellName%>"><%=messages.getText("generic.report.pending")%></td>
<%					} else {
						String className = "cellGreen";
						String color = "white";
						int percent = 0;
						if(((Integer)data[2]).intValue() == 0 && ((Integer)data[3]).intValue() == 0) {
							percent = 100;
							graphs.get(1)[2]++;
						}else {
							percent = ((Integer)data[2]).intValue() * 100 / (((Integer)data[2]).intValue() + ((Integer)data[3]).intValue());
							if(percent < 50) {
								className = "cellRed";
								graphs.get(1)[0]++;
							} else if(percent < 70) {
								className = "cellYellow";
								color = "#1D272D";
								graphs.get(1)[1]++;
							} else {
								graphs.get(1)[2]++;
							}
						}
%>					<td class="<%=className%>">
						<table>
							<tr>
								<td width="75%" class="cellText">
									<a href='<%="javascript:generateReport(\""+user.getLoginName()+"\","+data[4]+",1);"%>'>
										<span style='color:<%=color%>;'><%=Util.formatDate((Date)data[1])+" ("+percent+"%)"%></span>
									</a>
								</td>
								<td width="25%"  class="cellText">
<%						if(className.equals("cellGreen")) {
%>									<a href='<%="javascript:generateReport(\""+user.getLoginName()+"\","+data[4]+",2);"%>'>
										<img src="./imgs/downloadw.png" style="margin: 3px; width:20px;">
									</a>
<%						}
%>								</td>
							</tr>
						</table>
					</td>
<%						}
				} else {
					graphs.get(1)[3]++;
%>					<td class="<%=cellName%>">
						No asociado
					</td>
<%				}
				if(values.containsKey(2)) {
					Object[] data = values.get(2);
					if(((Integer)data[0]).intValue() == 0) {
							graphs.get(2)[3]++;
%>					<td class="<%=cellName%>"><%=messages.getText("generic.report.pending")%></td>
<%					} else {
						String className = "cellGreen";
						String color = "white";
						int percent = 0;
						if(((Integer)data[2]).intValue() == 0 && ((Integer)data[3]).intValue() == 0) {
							percent = 100;
							graphs.get(2)[2]++;
						}else {
							percent = ((Integer)data[2]).intValue() * 100 / (((Integer)data[2]).intValue() + ((Integer)data[3]).intValue());
							if(percent < 50) {
								className = "cellRed";
								graphs.get(2)[0]++;
							} else if(percent < 70) {
								className = "cellYellow";
								color = "#1D272D";
								graphs.get(2)[1]++;
							} else {
								graphs.get(2)[2]++;
							}
						}
%>					<td class="<%=className%>">
						<table>
							<tr>
								<td width="75%" class="<%=className%>">
									<a href='<%="javascript:generateReport(\""+user.getLoginName()+"\","+data[4]+",1);"%>'>
										<span style='color:<%=color%>;'><%=Util.formatDate((Date)data[1])+" ("+percent+"%)"%></span>
									</a>
								</td>
								<td width="25%" class="<%=className%>">
<%						if(className.equals("cellGreen")) {
%>									<a href='<%="javascript:generateReport(\""+user.getLoginName()+"\",1516,2);"%>'>
										<img src="./imgs/downloadw.png" style="margin: 3px; width:20px;">
									</a>
<%						}
%>								</td>
							</tr>
						</table>
					</td>
<%						}
				} else {
					graphs.get(2)[3]++;
%>					<td class="<%=cellName%>">
						No asociado
					</td>
<%				}
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
<%			int[] v = {0, 0, 0, 0};
			int[] t = (int[])graphs.get(1);
%>			
			var d = new google.visualization.DataTable();
	        d.addColumn('string', 'V1');
	        d.addColumn('number', 'V2');
	 		d.addRow(['<%=messages.getText("generic.report.lowlevel")+" ("+t[0]+")"%>', <%=String.valueOf(t[0])%>]);
	 		d.addRow(['<%=messages.getText("generic.report.meddiumlevel")+" ("+t[1]+")"%>', <%=String.valueOf(t[1])%>]);
	 		d.addRow(['<%=messages.getText("generic.report.highlevel")+" ("+t[2]+")"%>', <%=String.valueOf(t[2])%>]);
			d.addRow(['<%=messages.getText("generic.report.pending")+" ("+t[3]+")"%>', <%=String.valueOf(t[3])%>]);
			data[<%=String.valueOf(1)%>] = d;
<%			for(int j = 0; j < 4; j++) {
				v[j] += t[j];
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
	</body>
<%	}
%>
	
</html:html>