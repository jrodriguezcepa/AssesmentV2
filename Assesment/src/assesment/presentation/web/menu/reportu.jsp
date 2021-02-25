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

<%@page import="assesment.communication.question.QuestionData"%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>


<html:html>
<%
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	Text messages = sys.getText();
	UserSessionData userSessionData = sys.getUserSessionData();
	boolean check = false;
	String assessmentId = request.getParameter("a");
	String userLogin = request.getParameter("u");
	if(userSessionData.getRole().equals(SecurityConstants.ADMINISTRATOR) || userSessionData.getRole().equals(SecurityConstants.CEPA_REPORTER)) {
		check = true;
	}else if(userSessionData.getRole().equals(SecurityConstants.CLIENTGROUP_REPORTER)){
		GroupData group = sys.getAssesmentReportFacade().getUserGroup(userSessionData.getFilter().getLoginName(),userSessionData);
		check = group.containsAssessment(new Integer(assessmentId));
	}else if(userSessionData.getRole().equals(SecurityConstants.CLIENT_REPORTER)){
		Collection c = sys.getUserReportFacade().findUserReportAssesments(userSessionData.getFilter().getLoginName(),userSessionData);
		Iterator it = c.iterator();
		while(it.hasNext()) {
			if(((AssesmentAttributes)it.next()).getId().intValue() == Integer.parseInt(assessmentId)) {
				check = true;
			}
		}
	}
	
	if(check) {
		UserData userData = sys.getUserReportFacade().findUserByPrimaryKey(userSessionData.getFilter().getLoginName(),userSessionData);
		UserData user = sys.getUserReportFacade().findUserByPrimaryKey(userLogin,userSessionData);
		AssessmentReportData dataSource = sys.getUserReportFacade().getAssessmentReport(userLogin,new Integer(assessmentId),sys.getUserSessionData());
	    AssesmentData assesment = dataSource.getAssessment();
	    String logoName = "./flash/images/logo"+assesment.getCorporationId()+".png";
		UserReportData userReportData = (UserReportData)dataSource.getUsers().iterator().next();

		int greenPercent = Integer.parseInt(String.valueOf(Math.round(new Integer(dataSource.getGreen()).doubleValue() * 100.0 / new Integer(dataSource.getAnswerTestCount()))));
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

		<script type="text/javascript" src="./js/jquery.min.js"></script>
		<script type="text/javascript" src="./js/cepaglobal.js"></script>
		<script type="text/javascript" src="./js/angular.min.js"></script>
		<script type="text/javascript" src="./js/angular-route.js"></script>
		<script type="text/javascript" src="./js/angular-sanitize.min.js"></script>
		<script type="text/javascript" src="./js/plugins.js"></script>
		<script type="text/javascript" src="./js/jsapi.js"></script>
		<script type="text/javascript" src="./js/controllers.js"></script>	

	    <!-- script type="text/javascript" src="https://www.google.com/jsapi"></script -->
		<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
      	<script type="text/javascript">

      // Load the Visualization API and the piechart package.
	      google.charts.load('current', {'packages':['corechart','gauge']});

      // Set a callback to run when the Google Visualization API is loaded.
      google.setOnLoadCallback(startCharts);

  		var divHide = 'total';
 	 	function tabChange1(){
			$("#"+divHide).hide("slide", { direction: "right" }, "slow");
		 	$("#total").show("slide", { direction: "up" }, "slow");
		 	divHide = "total";
	    	drawPie();
	 	}
 	 	function tabChange2(){
			$("#"+divHide).hide("slide", { direction: "right" }, "slow");
		 	$("#barchart").show("slide", { direction: "up" }, "slow");
	    	drawChart1();
	    	drawChart2();
		 	divHide = "barchart";
	 	}

 	 	function tabChange4(){
			$("#"+divHide).hide("slide", { direction: "right" }, "slow");
		 	$("#psi").show("slide", { direction: "up" }, "slow");
			drawPiePsi(<%=String.valueOf(userReportData.getPsiPercent(AssessmentReportData.TOTAL))%>,'','psiTotalPsi',400,400);
			drawPiePsi(<%=String.valueOf(userReportData.getPsiPercent(AssessmentReportData.ATITUDE))%>,'<%=messages.getText("generic.report.psiatitude")%>','psiAtitude',250,250);
			drawPiePsi(<%=String.valueOf(userReportData.getPsiPercent(AssessmentReportData.STRESS))%>,'<%=messages.getText("generic.report.psistress")%>','psiStress',250,250);
		 	divHide = "psi";
	 	}

 	 	function tabChange5(){
			$("#"+divHide).hide("slide", { direction: "right" }, "slow");
		 	$("#questions").show("slide", { direction: "up" }, "slow");
		 	divHide = "questions";
	 	}
	 	
 	 	function startCharts() {
 	 	 	drawPie();
			drawPiePsi(<%=String.valueOf(userReportData.getPsiPercent(AssessmentReportData.TOTAL))%>,'','psiTotalPsi',400,400);
			drawPiePsi(<%=String.valueOf(userReportData.getPsiPercent(AssessmentReportData.ATITUDE))%>,'<%=messages.getText("generic.report.psiatitude")%>','psiAtitude',250,250);
			drawPiePsi(<%=String.valueOf(userReportData.getPsiPercent(AssessmentReportData.STRESS))%>,'<%=messages.getText("generic.report.psistress")%>','psiStress',250,250);
 	 	}
      // Callback that creates and populates a data table,
      // instantiates the pie chart, passes in the data and
      // draws it.
      function drawPie() {
        // Create the data table.
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Topping');
        data.addColumn('number', 'Slices');
        data.addRows([
          ['<%=messages.getText("report.userresult.wrong")%>', <%=String.valueOf(dataSource.getRed())%>],
          ['<%=messages.getText("report.userresult.right")%>', <%=String.valueOf(dataSource.getGreen())%>]
        ]);

        // Set chart options
        var options = {'width':450,
                       'colors':['red','green'],
                       chartArea:{left:50,top:100,width:'70%',height:'70%'},
                       legend:{position: 'top', textStyle: {color: '#555', fontName: 'Arial', fontSize: '14'}},
                       'height':450};

        // Instantiate and draw our chart, passing in some options.
        var chart = new google.visualization.PieChart(document.getElementById('pie'));
        chart.draw(data, options);
      }
      
      function drawChart1() {
    	  var data = google.visualization.arrayToDataTable([
    	  	['<%=messages.getText("generic.module")%>','<%=messages.getText("report.userresult.right")%>','<%=messages.getText("report.userresult.wrong")%>',   { role: 'annotation' } ]
<%			Iterator it = dataSource.getAssessment().getModuleIterator();
    	  	while(it.hasNext()) {
    	  		ModuleData module = (ModuleData)it.next();
    	  		ModuleReportData moduleReportData = dataSource.getModuleReportData(module.getId());
    	  		if(moduleReportData != null) {
%>    	  			, ['<%=messages.getText(module.getKey())%>',<%=String.valueOf(moduleReportData.getCorrectValue())%>, <%=String.valueOf(moduleReportData.getIncorrectValue())%>, '']
<%    	  		}
			}    	  	
%>    	  	]);	 
    	    var view = new google.visualization.DataView(data);
	        var options = {
	        		'title':'<%=messages.getText("generic.report.countresult")%>',
      			    titleTextStyle: {color: '#555', fontName: 'Arial', fontSize: '18', fontWidth: 'normal'},
	                width: 1000,
	                height: 500,
	                legend: { position: 'top', maxLines: 3 , textStyle: {color: '#555', fontName: 'Arial', fontSize: '14'}},
	                bar: { groupWidth: '75%' },
			        colors: ['green', 'red'],
	                isStacked: true,
	              };

            var columnchart = new google.visualization.ColumnChart(document.getElementById("barchart1"));
            columnchart.draw(view, options);
      }
      
      function drawChart2() {
    	  var data = google.visualization.arrayToDataTable([
    	  	['<%=messages.getText("generic.module")%>','<%=messages.getText("report.userresult.right")%>','<%=messages.getText("report.userresult.wrong")%>',   { role: 'annotation' } ]
<%			it = dataSource.getAssessment().getModuleIterator();
    	  	while(it.hasNext()) {
    	  		ModuleData module = (ModuleData)it.next();
    	  		ModuleReportData moduleReportData = dataSource.getModuleReportData(module.getId());
    	  		if(moduleReportData != null) {
%>    	  			, ['<%=messages.getText(module.getKey())%>',<%=String.valueOf(moduleReportData.getCorrect())%>,<%=String.valueOf(moduleReportData.getIncorrect())%>, '']
<%    	  		}
    	  	}    	  	
%>    	  ]);
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

      function drawPiePsi(value,label,divValue,width,height) {
    	  var data = google.visualization.arrayToDataTable([
			['Label', 'Value'],
			[label, value]
			]);

    	    var options = {
    	    	width: width, height: height,
				greenFrom: 0, greenTo: 60, greenColor: 'green',
    	        yellowFrom:60, yellowTo: 80, yellowColor: 'yellow',
				redFrom: 80, redTo: 100,  redColor: 'red',
    	       	max: 100,
    	       	majorTicks: 6,
    	        minorTicks: 5
    	    };

    	    var chart = new google.visualization.Gauge(document.getElementById(divValue));
    		chart.draw(data, options);
        }
      	function openReport(value) {
    		var form = document.forms['DownloadResultReportForm'];
    		form.reportType.value = value;
    		form.submit();
      	}
      </script>
	</head>
	<body>
      	<header id="header">
         	<section class="grid_container">
				<h1 class="customer_logo" style="background-image: url('images/main_logo_large.png');">CEPA Driver Assessment</h1>
            	<div class="toolbar">
               		<span class="username"><%=userData.getFirstName()+" "+userData.getLastName() %></span>
               		<span class="exit"><a href='<%="report.jsp?id="+assessmentId%>'><%=messages.getText("generic.messages.back")%></a></span>
            	</div>
         	</section>
      	</header>

		<form name="logout" action="./logout.jsp" method="post"></form>
		<html:form action="/DownloadResult" target="_blank">
			<html:hidden property="assessment" value="<%=String.valueOf(assesment.getId())%>" />
			<html:hidden property="login" value="<%=String.valueOf(user.getLoginName())%>" />
			<html:hidden property="reportType" />
		</html:form>	
    	<section id="content" ng-app="app" >
			<section class="grid_container">
				<form>
					<fieldset id="username_block" class="active">
						 <div class="titleReport" align="left">
						 	<div id="nameAssessment">
						 		<h1><%=messages.getText("generic.assesment")+": "+messages.getText(dataSource.getAssessment().getName())%></h1>
						 	</div>
						 	<div id="logoCorporation"><img src='<%=logoName%>'></div>
						 </div>
						 <div class="userReport" align="left">
					 		<h2><%=messages.getText("user.data.nickname")+": "+user.getFirstName()+" "+user.getLastName()%></h2>
<%				if(assesment.getId().intValue() == 223) {
%>					 		<h2><%="Ciudad: "+user.getEmail()%></h2>
<%				}else {
%>					 		<h2><%=messages.getText("user.data.mail")+": "+user.getEmail()%></h2>
<%				}
%>						 </div>
						 <div class="tabs" align="center">
							  <button type="button" onclick="tabChange1()" class="btn btn-default"><%=messages.getText("assesment.feedback.report1")%></button>
							  <button type="button" onclick="tabChange2()" class="btn btn-default"><%=messages.getText("generic.data.moduleresults")%></button>
							  <button type="button" onclick="tabChange5()" class="btn btn-default"><%=messages.getText("generic.report.errorquestions")%></button>
<%				if(assesment.isPsitest()) {
%>							  <button type="button" onclick="tabChange4()" class="btn btn-default"><%=messages.getText("assesment.module.psicologic")%></button>
<%				}
%>						</div>
						 <div class="tabs" align="right">
							<button type="button" onclick="openReport(1)" class="btn btn-default2"><%=messages.getText("report.feedback.generalreport")%></button>
<%				if(assesment.isCertificate() && greenPercent >= dataSource.getAssessment().getGreen().doubleValue()) {
%>							<button type="button" onclick="openReport(2)" class="btn btn-default2"><%=messages.getText("report.feedback.certificate")%></button>	
<%				}
%>						 </div>
	    			<!--Div that will hold the pie chart-->
	    				<div id="total">
	    					<div id="titlePage"><h1><%=messages.getText("assesment.feedback.report1")%></h1></div>
	    					<div>
		    					<div id="pie" ></div>
		    					<div id="resume">
		    						<div id="userState">
		    							<table border="1">
		    								<tr>
		    									<td class="headerTable"><%=messages.getText("generic.result")%></td>
		    									<td class="headerTable" align="center"><%=messages.getText("report.generalresult.count")%></td>
		    									<td class="headerTable" align="center"><%=messages.getText("report.userresult.percent")%></td>
		    								</tr>
		    								<tr class="cellTable">
		    									<td><%=messages.getText("report.userresult.right")%></td>
		    									<td align="center"><%=String.valueOf(dataSource.getGreen())%></td>
<%		String color = messages.getText("generic.report.highlevel");	
		if(greenPercent < dataSource.getAssessment().getYellow().doubleValue()) {
			color = messages.getText("generic.report.lowlevel");
		}else if(greenPercent < dataSource.getAssessment().getGreen().doubleValue()) {
			color = messages.getText("generic.report.meddiumlevel");
		}
%>
		    									<td align="center"><%=greenPercent+"%"%></td>
		    								</tr>
		    								<tr class="cellTable">
		    									<td><%=messages.getText("report.userresult.wrong")%></td>
		    									<td align="center"><%=String.valueOf(dataSource.getRed())%></td>
		    									<td align="center"><%=String.valueOf(100 - greenPercent)+"%"%></td>
		    								</tr>
		    							</table>
		    						</div>
		    						<div id="userResult">
		    							<h2><%=messages.getText("generic.report.level")+": "+color%></h2> 
		    						</div>
		    					</div>
		    				</div>
	    				</div>
	    				<div id="barchart" style="display: none;">
	    					<div id="titlePage"><h1><%=messages.getText("generic.data.moduleresults")%></h1></div>
		    				<div id="barchart1"></div>
		    				<div id="barchart2"></div>
		    			</div>
	    				<div id="questions" style="display: none;">
	    					<div id="titlePage"><h1><%=messages.getText("generic.report.errorquestions")%></h1></div>
<%		Iterator<ModuleReportData> itModules = dataSource.getModules().iterator();
		while(itModules.hasNext()) {
			ModuleReportData moduleReportData = itModules.next();
			ModuleData module = assesment.findModule(moduleReportData.getId());
			if(moduleReportData.getQuestionSize() > 0) {
%>		    				<div>
		    					<div id="titlePage"><h2><%=messages.getText(module.getKey())%></h2></div>
								<div>
<%				Iterator<QuestionReportData> itQuestions = moduleReportData.getQuestionIterator();
				while(itQuestions.hasNext()) {
					QuestionReportData questionReportData = itQuestions.next();
					QuestionData question = module.findQuestion(questionReportData.getId());
%>									<table border="1" class="wrongquestion">
										<tr><td class="headerTable"><%=messages.getText(question.getKey())%></td></tr>
										<tr><td class="wrongAnswer"><%=messages.getText(question.findAnswer(questionReportData.getAnswer()).getKey())%></td></tr>
										<tr><td class="correctAnswer"><%=messages.getText(question.findCorrectAnswer().getKey())%></td></tr>
									</table>
<%				
				}
%>								</div>
		    				</div>
<%			}
		}
%>	    				</div>
<%				if(assesment.isPsitest()) {
%>						<div id="psi" style="display: none;">
	    					<div id="titlePage"><h1><%=messages.getText("assesment.module.psicologic")%></h1></div>
	    					<div id="psiGraph">
		    					<div id="psiTotalPsi"></div>
		    					<div id="psiSubUsuario">
	    							<h2><%=messages.getText("generic.results")%></h2> 
	    							<table border="1">
	    								<tr>
	    									<td class="headerTablePsi"><%=messages.getText("generic.factor")%></td>
	    									<td class="headerTablePsi" align="center"><%=messages.getText("generic.report.level")%></td>
	    								</tr>
	    								<tr class="cellTable">
	    									<td><%=messages.getText("generic.report.psiatitude")%></td>
	    									<td align="center"><%=messages.getText(userReportData.getPsiLevel(AssessmentReportData.ATITUDE))%></td>
	    								</tr>
	    								<tr class="cellTable">
	    									<td><%=messages.getText("generic.report.psistress")%></td>
	    									<td align="center"><%=messages.getText(userReportData.getPsiLevel(AssessmentReportData.STRESS))%></td>
	    								</tr>
	    								<tr class="cellTable">
	    									<td><%=messages.getText("generic.report.psiglobal")%></td>
	    									<td align="center"><%=messages.getText(userReportData.getPsiLevel(AssessmentReportData.TOTAL))%></td>
	    								</tr>
	    							</table>
		    					</div>
	    						<div id="psiUserAt">
			    					<div id="psiAtitude" ></div>
	    						</div>
	    						<div id="psiUserSt">
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