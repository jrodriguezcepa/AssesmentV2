<%@page import="assesment.presentation.translator.web.util.Util"%>
<%@page import="assesment.business.assesment.AssesmentReportFacade"%>
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

<%@page import="java.util.Collection"%>

<%@page import="java.util.LinkedList"%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>

<%@page import="assesment.communication.question.QuestionData"%><html:html>

<%
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	Text messages = sys.getText();
	UserSessionData userSessionData = sys.getUserSessionData();
	boolean check = false;
	String assessmentId=String.valueOf(AssesmentData.MUTUAL_DA);
	if (request.getParameter("id")!=null){
		assessmentId=(String)request.getParameter("id");

	}else if(session.getAttribute("assesmentId")!=null){
		assessmentId=(String)session.getAttribute("assesmentId");
	}
	session.setAttribute("assesmentId", assessmentId);
	String refresh="assesmentReport.jsp?id="+assessmentId;
	String report="reportAssesment.jsp?id="+assessmentId;
	String groupId = request.getParameter("group");
	Collection c = new LinkedList();
	if(userSessionData.getRole().equals(SecurityConstants.ADMINISTRATOR) || userSessionData.getRole().equals(SecurityConstants.CEPA_REPORTER) || userSessionData.getRole().equals(SecurityConstants.CLIENT_REPORTER)) {
		check = true;
	}
	if(check) {
		UserData userData = sys.getUserReportFacade().findUserByPrimaryKey(userSessionData.getFilter().getLoginName(),userSessionData);
		AssesmentReportFacade assessmentReport = sys.getAssesmentReportFacade();
		AssessmentReportData dataSource = (Util.isNumber(groupId)) ? assessmentReport.getAssessmentReport(new Integer(assessmentId),new Integer(groupId), sys.getUserSessionData()) : assessmentReport.getAssessmentReport(new Integer(assessmentId),sys.getUserSessionData());
		sys.setValue(dataSource);
	    AssesmentData assesment = dataSource.getAssessment();
	    String action=request.getParameter("action");
	    String logoName = "./flash/images/logo"+assesment.getCorporationId()+".png";
	    String next = (userSessionData.getRole().equals(SecurityConstants.ADMINISTRATOR)) ? "index.jsp" : "logout.jsp";
%>
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

		<link rel="stylesheet" href="styles/jquery-ui-1.10.3.custom.min.css">
		<!--[if lt IE 9]>
			<script type="text/javascript" src="scripts/html5shiv.min.js"></script>
		<![endif]-->

		<style type="text/css">	
			.col-1 {width: 15%;}
			.col-2 {width: 15%;}
			.col-3 {width: 15%;}
			.col-4 {width: 15%;}
			.col-5 {width: 15%;}
			.col-6 {width: 2%;}
			.col-7 {width: 3%;}
			.col-8 {width: 4.5%;}
			.col-9 {width:	1%;}
			.col-10 {width: 4.5%;}
			
			[class*="col-"] {
				float: left;
				padding: 1px;
			
			  }
			
			  .row {
				clear: both;
				position: relative;
				display: flex;
				justify-content: space-between;
				align-items: center;
			  }
			  .row::after {
				content: "";
				clear: both;
				display: table;
			  }
			  .row::before {
				content: "";
				clear: both;
				display: table;
			  }
			.row img{
				width: 60%;
			}
			.table, .table2 {
			    display: table;
  				table-layout: fixed;
				width: 60%;
				border-collapse: collapse;
				border-bottom: 1px solid #d6d6d6;
				margin-top: 5px;
			
			}
			
			.table th, .table2 th,  .table td, .table2 td {
				
				font-family: 'Roboto', sans-serif;
				font-weight: 500;
				font-size: 1vw;
				white-space: normal;
				color: #434343;
				background-color: #f7f7f7;
				border-top:1px solid #d8cccc;
				border-left:1px solid #a39999;
				border-right:1px solid #dcdcdc;
			}
			
			.table th, .table2 th {
				font-size: 0.8vw;
				font-weight: 600;
				letter-spacing: 0.01em;
				color: rgb(14, 13, 13);
				background-color: #e8e6e6;
				text-align:center;
				padding: 0.5px 10px 10px 2.5px;
				border-bottom: 1px solid #d6d6d6;
				white-space:normal;

			}
	
			.table td, .table2 td {
				padding:12px 5px 12px 5px;
				}
				
			.table .odd td, .table2 .odd td{
				background-color: #ededed;}
			
			.table img {
				float:left;
				width: 13px;
				height: auto;
			}
			.table2 img {
				float:left;
				width: 20px;
				height: auto;
			}	
			.thText{
				padding: 9.5px 0px 0px 7.5px;

		    }
			a.button {
				width: -moz-fit-content;
				width: fit-content;
				font-family: Arial, Helvetica, sans-serif;
				font-size: 1vw;
				font-weight: 500;
				text-decoration: none;
				text-align:center;
				letter-spacing: 0.10em;
				color:rgb(7, 6, 6);
				padding: 10px;
				border-radius: 10px;
				background-color:#eceeed;
				border-top: 1px solid #121312;
				border-right:1px solid #121312;
				border-bottom:1px solid #121312;
				border-left: 1px solid #121312;
				margin:3px;
				display:flex;
				align-items: center;
				white-space: nowrap;
			}
			
			a.button:hover {
				width: -moz-fit-content;
				width: fit-content;
				font-size: 1vw;
				font-weight: 500;
				display:flex;
				background-color:rgb(255, 255, 255);
				border-top: 1px solid #121312;
				border-right:1px solid #121312;
				border-bottom:1px solid #121312;
				border-left: 1px solid #121312;
				}
				
			.header {
				margin: auto;
			  }
			  #left {
				float:left;
				width:40%;
				text-align: left;
				
			}
			
			#right {
				float:right;
				width:60%;
				text-align: right;
			
			}
			#cepa {
				width:50%;
				left:0;
				float:left;
				padding:10px;
			
			}
			#logout_user{
				width:50%;
				display:flex;
				white-space: nowrap;
				align-items: center;
				float:right;
				font-size:2vw;
				font-family: Arial, Helvetica, sans-serif;
				text-align:right;
			}
			
			.title {
				clear: both;
				margin-right:auto;
				font-family: Arial, Helvetica, sans-serif;
				color:rgb(36, 32, 33);
				padding: 20px;
				font-size:3vw;
				text-align: center;
			}	
			.titleS {
				clear: both;
				margin-right:auto;
				font-family: Arial, Helvetica, sans-serif;
				color:rgb(36, 32, 33);
				padding: 1px;
				margin-bottom: 15px;
				font-size:2vw;
				text-align: center;
			}	
			.titleXS {
				clear: both;
				font-weight: 600;
				margin-right:auto;
				font-family: Arial, Helvetica, sans-serif;
				color:rgb(36, 32, 33);
				padding: 1px;
				margin-bottom: 15px;
				font-size:1vw;
				text-align: center;
			}	
			
			.titleXSQuestion {
				clear: both;
				font-weight: 600;
				margin-right:auto;
				font-family: Arial, Helvetica, sans-serif;
				color:rgb(36, 32, 33);
				padding: 1px;
				margin-bottom: 15px;
				font-size:1.5vw;
				text-align: left;
			}	
			.qstnTable{
				display: table;
  				table-layout: fixed;
  				width:90%;
  				margin-left:auto;
  				margin-right:auto;
  				
			}
			.button img {
				width: -moz-fit-content;
				width: fit-content;
				width: 12%;
			
			  }
			
			
			
			  #right_btn, #left_btn{
				  cursor: pointer;
			  }
			  #logout_icon{
			  	width:30px;
			  	height:auto;
			  	margin-top:10px;
			  	margin-left:20px;
			  }
		</style>
		<script type="text/javascript" src="./js/jquery.min.js"></script>
		<script type="text/javascript" src="./js/cepaglobal.js"></script>
		<script type="text/javascript" src="./js/angular.min.js"></script>
		<script type="text/javascript" src="./js/angular-route.js"></script>
		<script type="text/javascript" src="./js/angular-sanitize.min.js"></script>
		<script type="text/javascript" src="./js/plugins.js"></script>
		<script type="text/javascript" src="./js/jsapi.js"></script>
		<script type="text/javascript" src="./js/controllers.js"></script>	

    <!--Load the AJAX API-->
	    <!-- script type="text/javascript" src="https://www.google.com/jsapi"></script -->
		<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
      	<script type="text/javascript">

      // Load the Visualization API and the piechart package.
	      google.charts.load('current', {'packages':['corechart','table']});

      // Set a callback to run when the Google Visualization API is loaded.
      google.setOnLoadCallback(startCharts);

<%		if(userSessionData.getFilter().getLoginName().startsWith("charla")) {
%>			setTimeout(function() { window.location.reload(true); },30000);
<%		}		
%>

  		var divHide = 'total';
 	 	
 	 	function startCharts() {
 	 	 	drawPie();
		}

      // Callback that creates and populates a data table,
      // instantiates the pie chart, passes in the data and
      // draws it.
      function drawPie() {
    	  // Create the data table.
<%     if(action.equals("4")){
%>
          var data = new google.visualization.DataTable();
          data.addColumn('string', 'Topping');
          data.addColumn('number', 'Slices');
          data.addRows([
            ['<%=messages.getText("question.result.red")%>', <%=dataSource.getPsiValue(AssessmentReportData.TOTAL,2)%>],
            ['<%=messages.getText("question.result.yellow")%>',<%=dataSource.getPsiValue(AssessmentReportData.TOTAL,1)%>],
            ['<%=messages.getText("question.result.green")%>', <%=dataSource.getPsiValue(AssessmentReportData.TOTAL,0)%>]
          ]);

          // Set chart options
          var options = {'width':450,
                         'colors':['red','yellow','green'],
                         chartArea:{left:50,top:50,width:'70%',height:'70%'},
                         legend:{position: 'top', textStyle: {color: '#555', fontName: 'Arial', fontSize: '14'}},
                         'height':450};

          // Instantiate and draw our chart, passing in some options.
          var chart = new google.visualization.PieChart(document.getElementById('pie'));
          chart.draw(data, options);
          
          var data2 = new google.visualization.DataTable();
          data2.addColumn('string', 'Topping');
          data2.addColumn('number', 'Slices');
          data2.addRows([
            ['<%=messages.getText("question.result.red")%>', <%=dataSource.getPsiValue(AssessmentReportData.ATITUDE,2)%>],
            ['<%=messages.getText("question.result.yellow")%>',<%=dataSource.getPsiValue(AssessmentReportData.ATITUDE,1)%>],
            ['<%=messages.getText("question.result.green")%>', <%=dataSource.getPsiValue(AssessmentReportData.ATITUDE,0)%>]
          ]);

          // Set chart options
          var options2 = {'width':300,
        		  		  'title':'Actitud',
	   				      titleTextStyle: {color: '#555', fontName: 'Arial', fontSize: '16', fontWidth: 'normal'},
                         'colors':['red','yellow','green'],
                         chartArea:{left:50,top:100,width:'70%',height:'70%'},
                         legend:{position: 'top', textStyle: {color: '#555', fontName: 'Arial', fontSize: '14'}},
                         'height':300};

          // Instantiate and draw our chart, passing in some options.
          var chart2 = new google.visualization.PieChart(document.getElementById('psiAtitude'));
          chart2.draw(data2, options2);
          
          var data3 = new google.visualization.DataTable();
          data3.addColumn('string', 'Topping');
          data3.addColumn('number', 'Slices');
          data3.addRows([
            ['<%=messages.getText("question.result.red")%>', <%=dataSource.getPsiValue(AssessmentReportData.STRESS,2)%>],
            ['<%=messages.getText("question.result.yellow")%>',<%=dataSource.getPsiValue(AssessmentReportData.STRESS,1)%>],
            ['<%=messages.getText("question.result.green")%>', <%=dataSource.getPsiValue(AssessmentReportData.STRESS,0)%>]
          ]);

          // Set chart options
          var options3 = {'width':300,
        		  		  'title':'Stress',
		   				   titleTextStyle: {color: '#555', fontName: 'Arial', fontSize: '16', fontWidth: 'normal'},
                         'colors':['red','yellow','green'],
                         chartArea:{left:50,top:100,width:'70%',height:'70%'},
                         legend:{position: 'top', textStyle: {color: '#555', fontName: 'Arial', fontSize: '14'}},
                         'height':300};

          // Instantiate and draw our chart, passing in some options.
          var chart3 = new google.visualization.PieChart(document.getElementById('psiStress'));
          chart3.draw(data3, options3);
          
<%    }
	else if (action.equals("2")){
%> 		var data = new google.visualization.DataTable();
		data.addColumn('string', 'Topping');
		data.addColumn('number', 'Slices');
		data.addRows([
		  ['<%=messages.getText("report.generalresult.lowlevel")%>', <%=String.valueOf(dataSource.getRed())%>],
		  ['<%=messages.getText("report.generalresult.meddiumlevel")%>', <%=String.valueOf(dataSource.getYellow())%>],
		  ['<%=messages.getText("report.generalresult.highlevel")%>', <%=String.valueOf(dataSource.getGreen())%>]
		]);
		
		// Set chart options
		var options = {'width':450,
		               'colors':['red','yellow','green'],
		               chartArea:{left:50,top:50,width:'70%',height:'70%'},
		               legend:{position: 'top', textStyle: {color: '#555', fontName: 'Arial', fontSize: '14'}},
		               'height':450};
		
		// Instantiate and draw our chart, passing in some options.
		var chart = new google.visualization.PieChart(document.getElementById('pie'));
		chart.draw(data, options);
<%}else if(action.equals("3")){
%>		
<%		for(int i = 1; i <= dataSource.getModules().size(); i++) {		
%>			drawTableQuestion<%=String.valueOf(i)%>();
<%		}		
%>
<%}
%>
      }
      
     

      function drawTable() {
          var data = new google.visualization.DataTable();
          data.addColumn('string', '<%=messages.getText("user.data.nickname")%>');
          data.addColumn('string', '<%=messages.getText("user.data.firstname")%>');
          data.addColumn('string', '<%=(dataSource.getAssessment().getId().intValue() == 462) ? "C.I." : messages.getText("user.data.lastname")%>');
<%		if(dataSource.getAssessment().isShowEmailWRT()) {
			if(dataSource.getAssessment().getId().intValue() == 223) {
%>          			data.addColumn('string', 'Ciudad');
<%			}else {
%>          			data.addColumn('string', '<%=messages.getText("user.data.mail")%>');
<%			}
		}
		QuestionData[] wrts = dataSource.getWRTQuestions();
		for(int i = 0; i < wrts.length && wrts[i] != null; i++) {
%>	          data.addColumn('string', '<%=messages.getText(wrts[i].getKey())%>');
<%		}
%>        data.addColumn('number', '<%=messages.getText("module.resultreport.right")%>');
          data.addColumn('number', '<%=messages.getText("module.resultreport.wrong")%>');
<%		if(Integer.parseInt(assessmentId) == AssesmentData.UPL_NEWHIRE) {
%>			data.addColumn('string', '<%=messages.getText("generic.newhire.driverprofile")%>');
			data.addColumn('string', '<%=messages.getText("generic.newhire.basictest")%>');
<%		}else {
%>          data.addColumn('string', '<%=messages.getText("generic.report.level")%>');
<%		}
		Collection totalUsers = dataSource.getTotalUsers();
		if(assesment.isPsitest()) {
%>			data.addColumn('string', '<%=messages.getText("assessment.psi")%>');
<%		}
%>
		data.addColumn('date', '<%=messages.getText("generic.report.ending")%>');
		data.addColumn('string', '<img src="./imgs/reporte_simple.jpg" height="20px;">');
<%		if(assesment.isCertificate()) {
%>			data.addColumn('string', '<img src="./imgs/certificate.jpg" height="20px;">');
<%		}
%>		data.addRows(<%=String.valueOf(totalUsers.size())%>);
<%		Iterator<UserReportData> itUser = totalUsers.iterator();
		int index = 0;
		while(itUser.hasNext()) {
%>			<%=itUser.next().getLine(index, assesment, wrts, messages)%>
<%			index++;
		}
%>
		var options = {'width':1000,
        	allowHtml: true};

			var table = new google.visualization.Table(document.getElementById('usertable'));

          table.draw(data, options);
        }

<%		Iterator<ModuleReportData> itModules = dataSource.getModules().iterator();
		int qIndex = 1;
		while(itModules.hasNext()) {
			ModuleReportData module	= itModules.next();		
			index = 0;
%>      function drawTableQuestion<%=String.valueOf(qIndex)%>() {
          var data = new google.visualization.DataTable();
          data.addColumn('string', '<%=messages.getText("generic.question")%>');
          data.addColumn('number', '<%=messages.getText("module.resultreport.right")%>');
          data.addColumn('number', '<%=messages.getText("module.resultreport.wrong")%>');
          data.addRows(<%=String.valueOf(module.getQuestionSize())%>);
<%			Iterator<QuestionReportData> itQuestions = module.getQuestionIterator();
			while(itQuestions.hasNext()) {
%>				<%=itQuestions.next().getLine(index,messages)%>
<%				index++;
			}
%>
		 var options = {'title':'<%=messages.getText("system.questions")%>',
        	'width':1000,
        	allowHtml: true};

		 var table = new google.visualization.Table(document.getElementById('questiontable<%=String.valueOf(qIndex)%>'));

          table.draw(data, options);
        }
<%		qIndex++;
	}
%>
		function generateReport(valueL, report) {
			var form = document.forms['DownloadResultReportForm'];
			form.login.value = valueL;			
			form.reportType.value = report;
			form.submit();
		}
	


      </script>
	</head>
	<body>
	<header class="header">
		<div id="left">
			<div id="cepa">
				<img  width=70% src="images/main_logo_large.png" alt="cepa">
			</div>
		</div>
		<div id="right">
			<div  id="logout_user">
				<span > <%=userData.getFirstName() + " "+ userData.getLastName() %></span>
				<a href="<%=next%>" >
					<img id="logout_icon"  src="images/mutual_logout.png" alt="logout">
				</a>
			</div>
		</div>

	</br></br></br></br></br>
	</header>
	<main>
		<div class="title">
		Assessment: <%=messages.getText(dataSource.getAssessment().getName())%>
		</div>
		<div class="row">
			<div class="col-1"><a href='<%=refresh %>' class="button"><%=messages.getText("generic.data.refresh")%>  <img  src="images/mutual_refresh.png" alt="left"></a></div>
			<div class="col-2"><html:form action="/DownloadMutualReport" method="post"><a href="" class="button"><html:submit style="all:unset;"><%=messages.getText("generic.data.downloadxls")%> </html:submit><img  src="images/mutual_download.png" alt="left"></a></html:form></div>
			<div class="col-3"><a href='<%=report+"&action=2"%>' class="button"><%=messages.getText("generic.data.generalresults")%></a></div>
			<div class="col-4"><a href='<%=report+"&action=3"%>' class="button"><%=messages.getText("generic.data.questionsranking")%></a></div>
			<div class="col-5"><a href='<%=report+"&action=4"%>' class="button"><%=messages.getText("generic.data.behavtest")%></a></div>
			<div class="col-6"></div>
			<div class="col-7"></div>
			<div class="col-8"></div>
			<div class="col-9"></div>
			<div class="col-10"></div>
	
		</div>	
    	<section>
			<!--Div that will hold the pie chart-->
			<div id="total" >
<%				if(action.equals("2")){	
%>	    			<div class="titleS"><%=messages.getText("assesment.feedback.report1")%></div>
<%				}else if(action.equals("4")){
%>	    			<div class="titleS"><%=messages.getText("assesment.module.psicologic")%></div>
<%				}
%>		    <div style="display:flex;width:100%;">
 			<div id="pie" style="float:left"></div>
 			<div id="resume" style="float:right">
<%				if(action.equals("2")){				
%>	    			<div id="userState">
					<span class="titleXS"><%=messages.getText("generic.results.advance")%></span> 
<%		int[][] advance = dataSource.getAdvance();
%>			
					<table class="table">
						<tr>
							<th ><%=messages.getText("assesment.data.status")%></th>
							<th  align="center"><%=messages.getText("report.generalresult.count")%></th>
							<th  align="center"><%=messages.getText("report.userresult.percent")%></th>
						</tr>
						<tr >
							<td><%=messages.getText("report.users.total.notstarted")%></td>
							<td align="center"><%=String.valueOf(advance[0][2])%></td>
							<td align="center"><%=String.valueOf(advance[1][2])+" %"%></td>
						</tr>
						<tr >
							<td><%=messages.getText("report.users.total.pending")%></td>
							<td align="center"><%=String.valueOf(advance[0][0])%></td>
							<td align="center"><%=String.valueOf(advance[1][0])+" %"%></td>
						</tr>
						<tr >
							<td><%=messages.getText("report.users.total.finalized")%></td>
							<td align="center"><%=String.valueOf(advance[0][1])%></td>
							<td align="center"><%=String.valueOf(advance[1][1])+" %"%></td>
						</tr>
						<tr>
							<td><%=messages.getText("report.generalresult.total")%></td>
							<td align="center"><%=String.valueOf(advance[0][0]+advance[0][1]+advance[0][2])%></td>
							<td align="center">100%</td>
						</tr>
					</table>
				</div>
		    							
<%	}
%>	
<%		String[] percents = dataSource.getResultPercents();
%>		    							
		    	<div id="userResult" style="width:100%;">
<% 		    	if(action.equals("3")){
%>	    			<div class="titleS" style="align:left;"><%=messages.getText("generic.report.questionresults")%></div>
<%					itModules = dataSource.getModules().iterator();
					qIndex = 1;
					while(itModules.hasNext()) {
						ModuleReportData module	= itModules.next();		
%>		    				<div>
								<br>
		    					<div class="titleXSQuestion"><%=messages.getText(module.getKey())%></div>
								<div class="qstnTable" id="questiontable<%=String.valueOf(qIndex)%>"></div>
		    				</div>
<%						qIndex++;
					}
				}
%>			    	
		    	
		    	
<%					if(action.equals("4")){				
%>
		    			<span class="titleXS"><%=messages.getText("generic.report.levels")%></span> 
						<table class="table">
							<tr>
								<th ><%=messages.getText("generic.factor")%></th>
								<th align="center"><%=messages.getText("question.result.red")%></th>
								<th align="center"><%=messages.getText("question.result.yellow")%></th>
								<th  align="center"><%=messages.getText("question.result.green")%></th>
							</tr>
							<tr >
								<td><%=messages.getText("generic.report.psiatitude")%></td>
								<td align="center"><%=String.valueOf(dataSource.getPsiValue(AssessmentReportData.ATITUDE,2)) %></td>
								<td align="center"><%=String.valueOf(dataSource.getPsiValue(AssessmentReportData.ATITUDE,1)) %></td>
								<td align="center"><%=String.valueOf(dataSource.getPsiValue(AssessmentReportData.ATITUDE,0)) %></td>
							</tr>
							<tr >
								<td><%=messages.getText("generic.report.psistress")%></td>
								<td align="center"><%=String.valueOf(dataSource.getPsiValue(AssessmentReportData.STRESS,2)) %></td>
								<td align="center"><%=String.valueOf(dataSource.getPsiValue(AssessmentReportData.STRESS,1)) %></td>
								<td align="center"><%=String.valueOf(dataSource.getPsiValue(AssessmentReportData.STRESS,0)) %></td>
							</tr>
							<tr >
								<td><%=messages.getText("generic.report.psiglobal")%></td>
								<td align="center"><%=String.valueOf(dataSource.getPsiValue(AssessmentReportData.TOTAL,2)) %></td>
								<td align="center"><%=String.valueOf(dataSource.getPsiValue(AssessmentReportData.TOTAL,1)) %></td>
								<td align="center"><%=String.valueOf(dataSource.getPsiValue(AssessmentReportData.TOTAL,0)) %></td>
							</tr>
						</table>
	    			<div id="psiFactors" style="display: flex;">
	    				<div id="psiAtitude" style="display:flex;"></div>
	    				<div id="psiStress" style="display:flex;"></div>
	    			</div>
<%					}else if (action.equals("2")){
%>    							    							
						<br>
						<span class="titleXS"><%=messages.getText("generic.report.levels")%></span> 
						<table class="table">
							<tr>
								<th><%=messages.getText("generic.report.level")%></th>
								<th align="center"><%=messages.getText("report.generalresult.count")%></th>
								<th align="center"><%=messages.getText("report.userresult.percent")%></th>
							</tr>
							<tr>
								<td><%=messages.getText("generic.report.lowlevel")%></td>
								<td align="center"><%=String.valueOf(dataSource.getRed())%></td>
								<td align="center"><%=percents[0]+" %"%></td>
							</tr>
							<tr>
								<td><%=messages.getText("generic.report.meddiumlevel")%></td>
								<td align="center"><%=String.valueOf(dataSource.getYellow())%></td>
								<td align="center"><%=percents[1]+" %"%></td>
							</tr>
							<tr>
								<td><%=messages.getText("generic.report.highlevel")%></td>
								<td align="center"><%=String.valueOf(dataSource.getGreen())%></td>
								<td align="center"><%=percents[2]+" %"%></td>
							</tr>
						</table>
		    		</div>
<%					}
%>
				</div>
			</div>
		</div>
				
		</section>
	</main>
		<script type="text/javascript" src="scripts/json2.min.js"></script>
		<!--[if lt IE 9]>
			<script type="text/javascript" src="scripts/jquery-1.11.1.min.js"></script>
		<![endif]-->
		<!--[if gte IE 9]><!-->
			<script type="text/javascript" src="scripts/jquery-2.0.2.min.js"></script>
		<!--<![endif]-->
		<script type="text/javascript" src="scripts/jquery-ui-1.10.3.custom.min.js"></script>
		<script type="text/javascript" src="scripts/jquery.ui.datepicker-pt.js"></script>
		<script type="text/javascript" src="scripts/jquery.ui.datepicker-es.js"></script>
		<script type="text/javascript" src="scripts/jquery.tadeouy.loggable.js"></script>
		<script type="text/javascript" src="scripts/jquery.tadeouy.assessmentController.js"></script>
		<script type="text/javascript" src="scripts/jquery.tadeouy.question.js"></script>
		<script type="text/javascript" src="scripts/jquery.tadeouy.card.js"></script>
		<script type="text/javascript" src="scripts/jquery.tadeouy.assessment.js"></script>
		<script type="text/javascript" src="scripts/assessment.js"></script>
	</body>
<%		}
%>
</html:html>