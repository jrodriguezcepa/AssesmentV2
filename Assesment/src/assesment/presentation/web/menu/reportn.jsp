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
<html:html>

<%
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	Text messages = sys.getText();
	UserSessionData userSessionData = sys.getUserSessionData();
	boolean check = false;
	String assessmentId = request.getParameter("id");
	Collection c = new LinkedList();
	if(userSessionData.getRole().equals(SecurityConstants.ADMINISTRATOR) || userSessionData.getRole().equals(SecurityConstants.CEPA_REPORTER)) {
		check = true;
	}else if(userSessionData.getRole().equals(SecurityConstants.CLIENT_REPORTER)){
		c = sys.getUserReportFacade().findUserReportAssesments(userSessionData.getFilter().getLoginName(),userSessionData);
		Iterator it = c.iterator();
		while(it.hasNext()) {
			if(((AssesmentAttributes)it.next()).getId().intValue() == Integer.parseInt(assessmentId)) {
				check = true;
			}
		}
	}
	
	if(check) {
		UserData userData = sys.getUserReportFacade().findUserByPrimaryKey(userSessionData.getFilter().getLoginName(),userSessionData);
		AssessmentReportData dataSource = sys.getAssesmentReportFacade().getAssessmentReport(new Integer(assessmentId),sys.getUserSessionData());
		sys.setValue(dataSource);
	    AssesmentData assesment = dataSource.getAssessment();
	    String logoName = "../flash/images/logo"+assesment.getCorporationId()+".png";
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
		<link rel="stylesheet" href="styles/fonts/pontano_sans.css">
		<link rel="stylesheet" href="styles/base.css">
		<link rel="stylesheet" href="styles/jquery-ui-1.10.3.custom.min.css">
		<!--[if lt IE 9]>
			<script type="text/javascript" src="scripts/html5shiv.min.js"></script>
		<![endif]-->

		<style type="text/css">
			#
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
        google.setOnLoadCallback(drawPie);
 	 	function startCharts() {
 	 	 	drawPie();
 	 	 	drawTable();
<%			for(int i = 1; i <= dataSource.getModules().size(); i++) {		
%>				drawTableQuestion<%=String.valueOf(i)%>();
<%			}		
			if(assesment.isPsitest()) {
%>			drawPiePsi(<%=dataSource.getPsiValue(AssessmentReportData.TOTAL,2)%>,<%=dataSource.getPsiValue(AssessmentReportData.TOTAL,1)%><%=dataSource.getPsiValue(AssessmentReportData.TOTAL,0)%>,'psiTotal',450,450);
	    	drawPiePsiTitle(<%=dataSource.getPsiValue(AssessmentReportData.ATITUDE,2)%>,<%=dataSource.getPsiValue(AssessmentReportData.ATITUDE,1)%><%=dataSource.getPsiValue(AssessmentReportData.ATITUDE,0)%>,'psiAtitude',500,300,'<%=messages.getText("generic.report.psiatitude")%>');
	    	drawPiePsiTitle(<%=dataSource.getPsiValue(AssessmentReportData.STRESS,2)%>,<%=dataSource.getPsiValue(AssessmentReportData.STRESS,1)%><%=dataSource.getPsiValue(AssessmentReportData.STRESS,0)%>,'psiStress',500,300,'<%=messages.getText("generic.report.psistress")%>');
<%			}
%>		}

 	      function drawPie() {

 	         // Create the data table.
 	         var data = new google.visualization.DataTable();
 	         data.addColumn('string', 'Topping');
 	         data.addColumn('number', 'Slices');
 	         data.addRows([
 	           ['Mushrooms', 3],
 	           ['Onions', 1],
 	           ['Olives', 1], 
 	           ['Zucchini', 1],
 	           ['Pepperoni', 2]
 	         ]);

 	         // Set chart options
 	         var options = {'title':'How Much Pizza I Ate Last Night',
 	                        'width':400,
 	                        'height':300};

 	         // Instantiate and draw our chart, passing in some options.
 	         var chart = new google.visualization.PieChart(document.getElementById('pie'));
 	         chart.draw(data, options);

 	       }
 	    	 	
      // Callback that creates and populates a data table,
      // instantiates the pie chart, passes in the data and
      // draws it.
      function drawPieViejo() {
        // Create the data table.
        var data = new google.visualization.DataTable();
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
                       chartArea:{left:50,top:100,width:'70%',height:'70%'},
                       legend:{position: 'top', textStyle: {color: '#555', fontName: 'Arial', fontSize: '14'}},
                       'height':450};

        // Instantiate and draw our chart, passing in some options.
        var chart = new google.visualization.PieChart(document.getElementById('pie'));
        chart.draw(data, options);
      }
      
      function drawChart1() {
    	  var data = google.visualization.arrayToDataTable([
    	  	['<%=messages.getText("generic.module")%>','<%=messages.getText("generic.report.highlevel")%>','<%=messages.getText("generic.report.meddiumlevel")%>','<%=messages.getText("generic.report.lowlevel")%>',   { role: 'annotation' } ],
<%			Iterator it = dataSource.getAssessment().getModuleIterator();
    	  	while(it.hasNext()) {
    	  		ModuleData module = (ModuleData)it.next();
    	  		ModuleReportData moduleReportData = dataSource.getModuleReportData(module.getId());
    	  		if(moduleReportData != null) {
%>    	  			['<%=messages.getText(module.getKey())%>',<%=String.valueOf(moduleReportData.getGreen())%>,<%=String.valueOf(moduleReportData.getYellow())%>, <%=String.valueOf(moduleReportData.getRed())%>, '']
      	  			<%=(it.hasNext()) ? "," : "]);"%>	 
<%    	  		}
    	  	}    	  	
%>    	  	
          	var view = new google.visualization.DataView(data);
	        var options = {
	        		'title':'<%=messages.getText("generic.report.usermodulelevel")%>',
      			    titleTextStyle: {color: '#555', fontName: 'Arial', fontSize: '18', fontWidth: 'normal'},
	                width: 1000,
	                height: 500,
	                legend: { position: 'top', maxLines: 3 , textStyle: {color: '#555', fontName: 'Arial', fontSize: '14'}},
	                bar: { groupWidth: '75%' },
			        colors: ['green', 'yellow', 'red'],
	                isStacked: true,
	              };

            var columnchart = new google.visualization.ColumnChart(document.getElementById("barchart1"));
            columnchart.draw(view, options);
      }
      
      function drawChart2() {
    	  var data = google.visualization.arrayToDataTable([
    	  	['<%=messages.getText("generic.module")%>','<%=messages.getText("module.resultreport.right")%>','<%=messages.getText("module.resultreport.wrong")%>',   { role: 'annotation' } ],
<%			it = dataSource.getAssessment().getModuleIterator();
    	  	while(it.hasNext()) {
    	  		ModuleData module = (ModuleData)it.next();
    	  		ModuleReportData moduleReportData = dataSource.getModuleReportData(module.getId());
    	  		if(moduleReportData != null) {
%>    	  			['<%=messages.getText(module.getKey())%>',<%=String.valueOf(moduleReportData.getCorrect())%>,<%=String.valueOf(moduleReportData.getIncorrect())%>, '']
      	  			<%=(it.hasNext()) ? "," : "]);"%>	 
<%    	  		}
    	  	}    	  	
%>    	  	
          	var view = new google.visualization.DataView(data);
	        var options = {
	        		'title':'<%=messages.getText("generic.report.percentresult")%>',
	     			titleTextStyle: {color: '#555', fontName: 'Arial', fontSize: '18', fontWidth: 'normal'},
	        		width: 1000,
	                height: 500,
	                legend: { position: 'top', maxLines: 3, textStyle: {color: '#555', fontName: 'Arial', fontSize: '14'} },
	                bar: { groupWidth: '75%' },
			        colors: ['green', 'red'],
	                isStacked: true,
	              };

            var columnchart = new google.visualization.ColumnChart(document.getElementById("barchart2"));
            columnchart.draw(view, options);
      }

      function drawTable() {
          var data = new google.visualization.DataTable();
          data.addColumn('string', '<%=messages.getText("user.data.firstname")%>');
          data.addColumn('string', '<%=messages.getText("user.data.lastname")%>');
          data.addColumn('string', '<%=messages.getText("user.data.mail")%>');
          data.addColumn('number', '<%=messages.getText("module.resultreport.right")%>');
          data.addColumn('number', '<%=messages.getText("module.resultreport.wrong")%>');
          data.addColumn('string', '<%=messages.getText("generic.report.level")%>');
<%		if(assesment.isPsitest()) {
%>			data.addColumn('string', '<%=messages.getText("assessment.psi")%>');
<%		}
%>          data.addRows(<%=String.valueOf(dataSource.getUsers().size())%>);
<%		Iterator<UserReportData> itUser = dataSource.getUsers().iterator();
		int index = 0;
		while(itUser.hasNext()) {
%>			<%=itUser.next().getLine(index, assesment.isPsitest(), String.valueOf(assesment.getId()),messages)%>
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
<%		if(assesment.isPsitest()) {
%>
      function drawPiePsi(red,yellow,green,divValue,width,height) {
          // Create the data table.
          var data = new google.visualization.DataTable();
          data.addColumn('string', 'Topping');
          data.addColumn('number', 'Slices');
          data.addRows([
            ['<%=messages.getText("question.result.red")%>', red],
            ['<%=messages.getText("question.result.yellow")%>', yellow],
            ['<%=messages.getText("question.result.green")%>', green]
          ]);

          // Set chart options
          var options = {'width':width,
                         'colors':['red','yellow','green'],
                         chartArea:{left:50,top:100,width:'70%',height:'70%'},
                         legend:{position: 'top', textStyle: {color: '#555', fontName: 'Arial', fontSize: '14'}},
                         'height':height};

          // Instantiate and draw our chart, passing in some options.
          var chart = new google.visualization.PieChart(document.getElementById(divValue));
          chart.draw(data, options);
        }
        
      function drawPiePsiTitle(red,yellow,green,divValue,width,height,title) {
          // Create the data table.
          var data = new google.visualization.DataTable();
          data.addColumn('string', 'Topping');
          data.addColumn('number', 'Slices');
          data.addRows([
            ['<%=messages.getText("question.result.red")%>', red],
            ['<%=messages.getText("question.result.yellow")%>', yellow],
            ['<%=messages.getText("question.result.green")%>', green]
          ]);

          // Set chart options
          var options = {'width':width,
	        		    'title':title,
   			   			 titleTextStyle: {color: '#555', fontName: 'Arial', fontSize: '16', fontWidth: 'normal'},
                         'colors':['red','yellow','green'],
                         chartArea:{left:50,top:100,width:'70%',height:'70%'},
                         legend:{position: 'top', textStyle: {color: '#555', fontName: 'Arial', fontSize: '14'}},
                         'height':height};

          // Instantiate and draw our chart, passing in some options.
          var chart = new google.visualization.PieChart(document.getElementById(divValue));
          chart.draw(data, options);
        }
<%			}		
%>      
      </script>
	</head>
	<body>
      	<header id="header">
         	<section class="grid_container">
				<h1 class="customer_logo" style="background-image: url('images/main_logo_large.png');">CEPA Driver Assessment</h1>
            	<div class="toolbar">
               		<span class="username"><%=userData.getFirstName()+" "+userData.getLastName() %></span>
               		<span class="exit">
               			<a href="logout.jsp"><%=messages.getText("generic.messages.logout")%></a>
               		</span>
            	</div>
         	</section>
      	</header>

		<form name="logout" action="./logout.jsp" method="post"></form>
		<html:form action="/DownloadUserResult">
		</html:form>
    	<section id="content" ng-app="app" >
			<section class="grid_container">
				<form name="assessments" action="./report_assessment.jsp" method="post">
					<fieldset id="username_block" class="active">
						 <div class="titleReport" align="left">
						 	<div id="nameAssessment"><h1 class="customer_logo" >Assessment: <%=dataSource.getAssessment().getName()%></h1></div>
						 	<div id="logoCorporation"><img src='<%=logoName%>'></div>
						 </div>
<%			if(userSessionData.getRole().equals(SecurityConstants.CEPA_REPORTER)  || userSessionData.getRole().equals(SecurityConstants.ADMINISTRATOR) || c.size() > 1) {
%>						 <div class="userReport" align="left">
					 		<input type="submit"  class="button" value="Cambiar de Assessment" />
						 </div>
<%			}
%>						 <div class="tabs" align="center">
							  <button type="button" onclick="tabChange1()" class="btn btn-default"><%=messages.getText("assesment.feedback.report1")%></button>
							  <button type="button" onclick="tabChange2()" class="btn btn-default"><%=messages.getText("generic.data.moduleresults")%></button>
							  <button type="button" onclick="tabChange3()" class="btn btn-default"><%=messages.getText("report.users.title")%></button>
							  <button type="button" onclick="tabChange5()" class="btn btn-default"><%=messages.getText("system.questions")%></button>
<%				if(assesment.isPsitest()) {
%>							  <button type="button" onclick="tabChange4()" class="btn btn-default"><%=messages.getText("assesment.module.psicologic")%></button>
<%				}
%>						</div>
	    			<!--Div that will hold the pie chart-->
	    				<div id="total">
	    					<div id="titlePage"><h1><%=messages.getText("assesment.feedback.report1")%></h1></div>
	    					<div>
		    					<div id="pie" ></div>
		    					<div id="resume">
		    						<div id="userState">
		    							<h2><%=messages.getText("generic.results.advance")%></h2> 
<%		int[][] advance = dataSource.getAdvance();
%>		    							
		    							<table border="1">
		    								<tr>
		    									<td class="headerTable"><%=messages.getText("assesment.data.status")%></td>
		    									<td class="headerTable" align="center"><%=messages.getText("report.generalresult.count")%></td>
		    									<td class="headerTable" align="center"><%=messages.getText("report.userresult.percent")%></td>
		    								</tr>
		    								<tr class="cellTable">
		    									<td><%=messages.getText("report.users.total.pending")%></td>
		    									<td align="center"><%=String.valueOf(advance[0][0])%></td>
		    									<td align="center"><%=String.valueOf(advance[1][0])+" %"%></td>
		    								</tr>
		    								<tr class="cellTable">
		    									<td><%=messages.getText("report.users.total.finalized")%></td>
		    									<td align="center"><%=String.valueOf(advance[0][1])%></td>
		    									<td align="center"><%=String.valueOf(advance[1][1])+" %"%></td>
		    								</tr>
		    								<tr>
		    									<td><%=messages.getText("report.generalresult.total")%></td>
		    									<td align="center"><%=String.valueOf(advance[0][0]+advance[0][1])%></td>
		    									<td align="center">100%</td>
		    								</tr>
		    							</table>
		    						</div>
<%		String[] percents = dataSource.getResultPercents();
%>		    							
		    						<div id="userResult">
		    							<h2><%=messages.getText("generic.report.levels")%></h2> 
		    							<table border="1">
		    								<tr>
		    									<td class="headerTable"><%=messages.getText("generic.report.level")%></td>
		    									<td class="headerTable" align="center"><%=messages.getText("report.generalresult.count")%></td>
		    									<td class="headerTable" align="center"><%=messages.getText("report.userresult.percent")%></td>
		    								</tr>
		    								<tr class="cellTable">
		    									<td><%=messages.getText("generic.report.lowlevel")%></td>
		    									<td align="center"><%=String.valueOf(dataSource.getRed())%></td>
		    									<td align="center"><%=percents[0]+" %"%></td>
		    								</tr>
		    								<tr class="cellTable">
		    									<td><%=messages.getText("generic.report.meddiumlevel")%></td>
		    									<td align="center"><%=String.valueOf(dataSource.getYellow())%></td>
		    									<td align="center"><%=percents[1]+" %"%></td>
		    								</tr>
		    								<tr class="cellTable">
		    									<td><%=messages.getText("generic.report.highlevel")%></td>
		    									<td align="center"><%=String.valueOf(dataSource.getGreen())%></td>
		    									<td align="center"><%=percents[2]+" %"%></td>
		    								</tr>
		    							</table>
		    						</div>
		    					</div>
		    				</div>
	    				</div>
	    				<div id="barchart" style="display: none;">
	    					<div id="titlePage"><h1><%=messages.getText("generic.data.moduleresults")%></h1></div>
		    				<div id="barchart1"></div>
		    				<div id="barchart2"></div>
		    			</div>
	    				<div id="users" style="display: none;">
	    					<div>
		    					<div id="titlePage">
		    						<h1><%=messages.getText("report.users.title")%></h1>
		    					</div>
		    					<div>
		    						<button type="button" value="Descargar" onclick="document.forms['DownloadUserReportForm'].submit();"></button>
		    					</div>
		    				</div>
		    				<div id="usertable"></div>
	    				</div>
	    				<div id="questions" style="display: none;">
	    					<div id="titlePage"><h1><%=messages.getText("generic.report.questionresults")%></h1></div>
<%		itModules = dataSource.getModules().iterator();
		qIndex = 1;
		while(itModules.hasNext()) {
			ModuleReportData module	= itModules.next();		
%>		    				<div>
		    					<div id="titlePage"><h2><%=messages.getText(module.getKey())%></h2></div>
								<div id="questiontable<%=String.valueOf(qIndex)%>"></div>
		    				</div>
<%			qIndex++;
		}
%>	    				</div>
<%				if(assesment.isPsitest()) {
%>						<div id="psi" style="display: none;">
	    					<div id="titlePage"><h1><%=messages.getText("assesment.module.psicologic")%></h1></div>
	    					<div id="psiGraph">
		    					<div id="psiTotal"></div>
		    					<div id="psiSub">
	    							<h2><%=messages.getText("generic.report.levels")%></h2>  
	    							<table border="1">
	    								<tr>
	    									<td class="headerTable"><%=messages.getText("generic.factor")%></td>
	    									<td class="headerTable" align="center"><%=messages.getText("question.result.red")%></td>
	    									<td class="headerTable" align="center"><%=messages.getText("question.result.yellow")%></td>
	    									<td class="headerTable" align="center"><%=messages.getText("question.result.green")%></td>
	    								</tr>
	    								<tr class="cellTable">
	    									<td><%=messages.getText("generic.report.psiatitude")%></td>
	    									<td align="center"><%=String.valueOf(dataSource.getPsiValue(AssessmentReportData.ATITUDE,2)) %></td>
	    									<td align="center"><%=String.valueOf(dataSource.getPsiValue(AssessmentReportData.ATITUDE,1)) %></td>
	    									<td align="center"><%=String.valueOf(dataSource.getPsiValue(AssessmentReportData.ATITUDE,0)) %></td>
	    								</tr>
	    								<tr class="cellTable">
	    									<td><%=messages.getText("generic.report.psistress")%></td>
	    									<td align="center"><%=String.valueOf(dataSource.getPsiValue(AssessmentReportData.STRESS,2)) %></td>
	    									<td align="center"><%=String.valueOf(dataSource.getPsiValue(AssessmentReportData.STRESS,1)) %></td>
	    									<td align="center"><%=String.valueOf(dataSource.getPsiValue(AssessmentReportData.STRESS,0)) %></td>
	    								</tr>
	    								<tr class="cellTable">
	    									<td><%=messages.getText("generic.report.psiglobal")%></td>
	    									<td align="center"><%=String.valueOf(dataSource.getPsiValue(AssessmentReportData.TOTAL,2)) %></td>
	    									<td align="center"><%=String.valueOf(dataSource.getPsiValue(AssessmentReportData.TOTAL,1)) %></td>
	    									<td align="center"><%=String.valueOf(dataSource.getPsiValue(AssessmentReportData.TOTAL,0)) %></td>
	    								</tr>
	    							</table>
		    					</div>
		    				</div>
	    					<div id="psiFactors">
	    						<div id="psiAt">
			    					<div id="psiAtitude" ></div>
	    						</div>
	    						<div id="psiSt">
			    					<div id="psiStress"></div>
	    						</div>
	    					</div>
	    				</div>
<%				}
%>	    			</fieldset>
	    		</form>
			</section>
		</section>
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