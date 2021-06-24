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
			body{
                background-color: rgb(241, 238, 238);
            }
            #rcorners1 {
                margin:auto;
                margin-bottom:1.5%;
                border-radius: 10px;
                background: #f8f8f8;
                padding: 20px;
                padding-bottom: 40px;
                width: 95%;
                height: 15px;
                box-shadow: 0 4px 8px 0 rgba(129, 128, 128, 0.2), 0 6px 10px 0 rgba(121, 120, 120, 0.19);
            }
              #rcorners2 {
                margin:auto;
                border-radius: 10px;
                background: #f8f8f8;
                width: 95%;
                padding:20px;
                padding-top: 20px;
                padding-bottom: 20px;
                box-shadow: 0 4px 8px 0 rgba(129, 128, 128, 0.2), 0 6px 10px 0 rgba(121, 120, 120, 0.19);
            }
            .textInput{
                margin-bottom:20px;
                margin-right:5px;

                width: 19%;
                height: 30px;
                border-radius: 5px;
                background-color: rgb(227, 230, 235);
                border-width: 0px;
                color: red;
                padding:5px;
            }
            .button{
                font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
                font-weight: 500;
                margin-left:30px;
                margin-bottom:20px;
                color: rgb(255, 255, 255);
                background-color: black;
                border-radius: 5px;
                width: 10%;
                height: 38px;
                padding:5px;
                border-width: 0px;
                cursor:pointer;

            }
            .button:hover{
                background-color: rgb(58, 55, 55);

            }
			.title {
				width:100%;
				margin:auto;
				color: #1D272D;
				font-family: Roboto, Helvetica, Arial, sans-serif;
				font-size: 2.5em;
				text-align:center;
			}
			.subtitle {
				margin-left: 10px;
				margin-top: 20px;
				margin-bottom: 20px;
				color: #1D272D;
				font-family: Roboto, Helvetica, Arial, sans-serif;
				font-size: 1.5em;
			}
            .table{
                border-collapse: collapse;
                font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
				font-size: 11;
				table-layout: fixed;
                width: 100%;
                margin: auto;

            }
            .table th{
                background: #f8f8f8;
 				word-wrap: break-word;   
            }
            .table td:first-child {
                border-top-left-radius: 10px;
                border-bottom-left-radius: 10px;

            }
            .table td:last-child {
                border-top-right-radius: 10px;
                border-bottom-right-radius: 10px;

            }
            .table  tr:nth-child(even) {

                background: #CCC
            }
            .table tr:nth-child(even):hover {

                background: rgb(243, 237, 237)
            }
            .table tr:nth-child(odd) {

                background:rgb(227, 230, 235);
             }
            .table tr:nth-child(odd):hover {

                background: rgb(243, 237, 237)
             }
            .table td{

                border-style:none;
                border-width: 0.5px;
                border-collapse:collapse;
                border-left-color: darkkhaki;
                padding-top:15px;
                padding-bottom:15px;
                padding-left:1px;
                padding-right:1px;
                text-align: center;
                white-space: nowrap;
    			height: 30px;
    			overflow:hidden;

             }
			.cell {
				min-width: 130px;
				border-style: none;
				text-align: center;
				height: 30px;
			}
			.cellText {
				text-align: center;
			}

			.cellRed {
				background-color: #F37566;
				color:white;
				border-style: none;
				text-align: center;
				vertical-align: middle;
			}
			.cellYellow {
				border-style:none;
                border-width: 0.5px;
                border-collapse:collapse;
                border-left-color: darkkhaki;
                padding-top:15px;
                padding-bottom:15px;
                padding-left:1px;
                padding-right:1px;
                text-align: center;
                white-space: nowrap;
    			height: 30px;	
				background-color: #EFE358;
			}
			.cellGreen {
			 	border-style:solid;
                border-width: 0.5px;
                border-left:0px;
                border-right:0px;
                border-top:0px;
                border-bottom:0.5px;
                border-collapse:collapse;
                border-bottom-color: grey;
                padding-top:15px;
                padding-bottom:15px;
                padding-left:1px;
                padding-right:1px;
                text-align: center;
                white-space: nowrap;
    			height: 30px;	
				background-color: #70C160;
				color:white;
				
			}
			
			.subTable{
                border-collapse: collapse;
                border-style:none;
                font-size:11;
                height:10px;
                margin:auto;
			}
			.subTable td{
				padding:0;
				word-wrap:break-word
				border-style: none;
				
			}
			.subTable img{
				width:10px;
				height:10px;
			}
			div.wrap {
			    width:90%;
			    height:50px;
			    position: relative;
			}
			
			.wrap img {
			     position: absolute;
			}
			
			.wrap img:nth-of-type(1) {
			    left: 0;
			}
			
			.wrap img:nth-of-type(2) {
			    right: 0;
			}
		</style>
	</head>
	<body>
		<html:form action="/DownloadResult" target="_blank">
			<html:hidden property="assessment" />
			<html:hidden property="login" />
			<html:hidden property="reportType" />
		</html:form>	
		<html:form action="/DownloadGrupoModelo">
			<html:hidden property="group" value="<%=String.valueOf(GroupData.GRUPO_MODELO)%>" />
		</html:form>
		<form action="./viewFoto.jsp" name='fotos' method="post" target="_blank">
			<input type="hidden" name="user" />
		</form>	
		<form action="./layout.jsp" name='back' method="post">
			<input type="hidden" name="refer" value="/assesment/viewGroup.jsp" />
			<input type="hidden" name="id" value="<%=String.valueOf(GroupData.GRUPO_MODELO)%>" />
		</form>	
		<div class="wrap">
			<img src="./images/main_logo_large.png">
			<img width="5%" src="./images/grupo-modelo.png">
		</div>
		<br>
		<br>
		<br>
		<div  class="title">
			Grupo Modelo
		</div>
		<br>
		 <div id="rcorners1">
		 	<form action="<%=link%>" method="post">
	            <input name="firstName" class="textInput" type="text" placeholder="Nombre" value='<%=firstName%>'>
	            <input name="lastName" class="textInput" type="text" placeholder="Apellido" value='<%=lastName%>'>
	            <input name="userName" class="textInput" type="text" placeholder="Usuario" value='<%=userName%>'>
	            <input name="value" class="textInput" type="text" placeholder="CEDI" value='<%=value%>'>
	            <input name="button" class="button"  type="submit" value="BUSCAR">
	         </form>
	         
        </div>
		<div id="rcorners2">
            <div> 
                <input class="button"  type="button" value="Descargar" onclick="javascript:document.forms['DownloadGroupReportForm'].submit()">
                <input class="button"  type="button" value="Asociar DA"  onclick="window.location.href='associateDA.jsp?type=1'">

                <input class="button"  type="button" value="Asociar eBTW"  onclick="window.location.href='associateDA.jsp?type=2'">
<%			if(role.equals(SecurityConstants.ADMINISTRATOR)) {
%>                <input class="button"  type="button" value='<%=messages.getText("generic.messages.back").toUpperCase()%>'  onclick="javascript:document.forms['back'].submit();">
<%			} else {
%>                <input class="button"  type="button" value='<%=messages.getText("generic.messages.logout").toUpperCase()%>'  onclick="window.location.href='logout.jsp'">
<%			} 
%>         </div>
            <div>
			<table class="table">
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
					<th>Cuestionario Pendientes</th>
				</tr>
<%			HashMap<String, HashMap<Integer, Object[]>> userResults = assessmentReport.getUserCediResults(cedis, sys.getUserSessionData());
			Iterator<UserData> it = users.iterator();
			boolean line = true;
			while(it.hasNext()) {
				UserData user = it.next();
				HashMap<Integer, Object[]> values = (userResults.containsKey(user.getLoginName())) ? userResults.get(user.getLoginName()) : new HashMap<Integer, Object[]>();
				
%>				<tr>
					<td ><%=user.getLoginName() %></td>
					<td><%=user.getFirstName() %></td>
					<td><%=user.getLastName() %></td>
					<td><%=(user.getEmail() == null) ? "---" : user.getEmail() %></td>
					<td style="word-wrap:break-word"><%=(user.getExtraData2() == null) ? "---" : user.getExtraData2() %></td>
<%				Object[] data0 = {null,null,null,null,null};
				if(values.containsKey(0)) {
					data0 = values.get(0);
				}
%>
					<td style="word-wrap: break-word;"><%=(data0[1] == null) ? "---" : messages.getText((String)data0[1])%></td>
					<td style="word-wrap:break-word"><%=(data0[2] == null) ? "---" : messages.getText((String)data0[2])%></td>
					<td style="word-wrap:break-word"><%=(data0[3] == null) ? "---" : Util.formatDate((Date)data0[3])%></td>
					<td style="word-wrap:break-word"><%=(data0[4] == null) ? "---" : Util.formatDate((Date)data0[4])%></td>
<%				if(data0[0] != null) {
%>					<td class="cellGreen">
						<a href="javascript:openFoto('<%=user.getLoginName()%>')">
							<span style="color:white;">Ver</span>
						</a>
					</td>
<%				}else {
%>					<td>Pendiente</td>
<%				}
				if(values.containsKey(1)) {
					Object[] data = values.get(1);
					if(((Integer)data[0]).intValue() == 0) {
							graphs.get(1)[3]++;
%>					<td><%=messages.getText("generic.report.pending")%></td>
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
%>					<td class="<%=className%>" style="padding:0;word-wrap:break-word">
						<table class="subTable">
							<tr>
								<td width="75%" class="<%=className%>">
									<a href='<%="javascript:generateReport(\""+user.getLoginName()+"\","+data[4]+",1);"%>'>
										<span style='color:<%=color%>;'><%=Util.formatDate((Date)data[1])+" ("+percent+"%)"%></span>
									</a>
								</td>
								<td width="25%"  class="<%=className%>">
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
%>					<td>
						No asociado
					</td>
<%				}
				if(values.containsKey(2)) {
					Object[] data = values.get(2);
					if(((Integer)data[0]).intValue() == 0) {
							graphs.get(2)[3]++;
%>					<td><%=messages.getText("generic.report.pending")%></td>
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
%>					<td class="<%=className%>" style="padding:0">
						<table class="subTable">
							<tr>
								<td width="75%" class="<%=className%>">
									<a href='<%="javascript:generateReport(\""+user.getLoginName()+"\","+data[4]+",1);"%>'>
										<span style='color:<%=color%>;'><%=Util.formatDate((Date)data[1])+" ("+percent+"%)"%></span>
									</a>
								</td>
								<td width="25%" class="<%=className%>">
<%						if(className.equals("cellGreen")) {
%>									<a href='<%="javascript:generateReport(\""+user.getLoginName()+"\",1614,2);"%>'>
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
%>					<td>
						No asociado
					</td>
<%				}
				if(values.containsKey(3)) {
					Object[] data = values.get(3);
					if(((Integer)data[0]).intValue() == 0) {
%>					<td><%=messages.getText("generic.report.pending")%></td>
<%					} else {
						String className = "cellGreen";
						String color = "white";
						int percent = 0;
						if(((Integer)data[2]).intValue() == 0 && ((Integer)data[3]).intValue() == 0) {
							percent = 100;
						}else {
							percent = ((Integer)data[2]).intValue() * 100 / (((Integer)data[2]).intValue() + ((Integer)data[3]).intValue());
							if(percent < 50) {
								className = "cellRed";
							} else if(percent < 70) {
								className = "cellYellow";
								color = "#1D272D";
							}
						}
%>					<td class="<%=className%>" style="padding:0">
						<table class="subTable">
							<tr>
								<td width="100%" class="<%=className%>">
									<span style='color:<%=color%>;'><%=Util.formatDate((Date)data[1])+" ("+percent+"%)"%></span>
								</td>
							</tr>
						</table>
					</td>
<%						}
				} else {
%>					<td>
						No asociado
					</td>
<%				}
%>				</tr>						
<%			}
%>			</table>            
            </div>
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