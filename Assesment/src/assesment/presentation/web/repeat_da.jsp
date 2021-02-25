<!doctype html>
<%@page import="assesment.communication.administration.UserMultiAssessmentData"%>
<%@page import="assesment.presentation.translator.web.administration.user.LogoutAction"%>
<%@page import="assesment.presentation.translator.web.util.Util"%>
<%@page import="assesment.communication.assesment.AssesmentAttributes"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="assesment.persistence.user.tables.UserAssesmentData"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.LinkedList"%>
<%@page import="assesment.business.AssesmentAccess"%>
<%@page import="assesment.communication.assesment.AssesmentData"%>
<%@page import="java.util.Iterator"%>
<%@page import="assesment.communication.module.ModuleData"%>
<%@page import="assesment.communication.administration.user.UserSessionData"%>
<%@page import="assesment.communication.language.Text"%>
<%@page import="assesment.communication.user.UserData"%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html" 
%>

<%@ taglib uri="/WEB-INF/struts-bean.tld"
        prefix="bean" 
%>

<%
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	String estilo = "styles/base.css";
	String language = System.getProperty("user.language");
	if(sys != null && sys.isTelematics()) {
		estilo = "styles/base_telematics.css";
		language = sys.getUserSessionData().getLenguage();
	}
%>


<%@page import="java.util.HashMap"%>
<html lang='<%=language%>'>
	<head>
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
		<link rel="stylesheet" href='<%=estilo%>'>
		<link rel="stylesheet" href="styles/jquery-ui-1.10.3.custom.min.css">
		<!--[if lt IE 9]>
			<script type="text/javascript" src="scripts/html5shiv.min.js"></script>
		<![endif]-->
		<style type="text/css">
		.texto{
			font-family: 'Roboto Thin',"Helvetica Neue", Helvetica, Arial, "Pontano Sans", Verdana, sans-serif;
			font-size: 16px;
		}
		.buttonRed{
			background-color: red;
			color: white;
			font-family: 'Roboto Thin',"Helvetica Neue", Helvetica, Arial, "Pontano Sans", Verdana, sans-serif;
			font-size: 16px;
	        border: none;
	        border-radius: 2px 2px 2px 2px;
			-moz-border-radius: 2px 2px 2px 2px;
			-webkit-border-radius: 2px 2px 2px 2px;
			border: 0px solid #000000;
			width:100px;
		    height:30px;
		}
		.buttonYellow{
			background-color: #e1d600;
			color: black;
			font-family: 'Roboto Thin',"Helvetica Neue", Helvetica, Arial, "Pontano Sans", Verdana, sans-serif;
			font-size: 16px;
	        border: none;
	        border-radius: 2px 2px 2px 2px;
			-moz-border-radius: 2px 2px 2px 2px;
			-webkit-border-radius: 2px 2px 2px 2px;
			border: 1px solid #a0a0a0;
			width:100px;
		    height:30px;
		}
		.buttonGreen{
			background-color: green;
			color: white;
			font-family: 'Roboto Thin',"Helvetica Neue", Helvetica, Arial, "Pontano Sans", Verdana, sans-serif;
			font-size: 16px;
	        border: none;
	        border-radius: 2px 2px 2px 2px;
			-moz-border-radius: 2px 2px 2px 2px;
			-webkit-border-radius: 2px 2px 2px 2px;
			border: 0px solid #000000;
			width:100px;
		    height:30px;
		}
		</style>
		<script>
		function enterDA(assessmentId) {
			var form = document.forms['SelectAssessmentForm'];
			form.assessment.value = assessmentId;
			form.submit();
		}
		</script>
	</head>
<%
	if(sys == null) {
%>
	<body>
    	<header id="header">
        	<section class="grid_container">
            	<h1 class="customer_logo" style="background-image: url('../flash/images/logo29.png');">Driver Assessment</h1>
            	<div class="toolbar">
            	</div>
         	</section>
      	</header>
     	<section id="content">
   	 		<section class="grid_container">
				<form action="logout.jsp">
					<fieldset id="username_block" class="active">
						<br>
						<br>
						<div>
		                  	<label for="accesscode"><%=Text.getMessage("generic.messages.session.expired")%></label>
						</div>
						<br>
						<br>
						<html:submit styleClass="button" value='<%=Text.getMessage("generic.messages.accept")%>' />
						<br>
					</fieldset>
				</form>   	 		
			</section>
   	 	</section>
   	 </body>		
		
<%	}else {
		UserSessionData userSessionData = sys.getUserSessionData();
		Text messages = sys.getText(); 
		boolean telematics = sys.isTelematics();
		String module = request.getParameter("module");
		String moduleName = "";
		UserMultiAssessmentData answerData = null;
		if(userSessionData.getFilter().getMulti() == null) {
			answerData = sys.getAssesmentReportFacade().getMultiAssessment(userSessionData.getFilter().getLoginName(), sys.getUserSessionData().getFilter().getAssesment(), userSessionData);
			userSessionData.getFilter().setMulti(answerData.getId());
		}else {
			answerData = sys.getAssesmentReportFacade().getMultiAssessment(userSessionData);
		}
		UserData userData = answerData.getUser();
	    AssesmentData assesment = answerData.getAssessment();
		int assessmentId = answerData.getId();
	    String logoName = "../flash/images/logo"+assesment.getCorporationId()+".png";
	    Iterator it = assesment.getModuleIterator();
	    String[] modules = new String[assesment.getModules().size()];
	    int index = 0;
	    boolean finished = true;
		while(it.hasNext()) {
			ModuleData moduleData = (ModuleData)it.next();
			String link = "repeat_da.jsp?module="+moduleData.getId();
			String status = "class=\"";
			if(moduleData.getQuestionSize() == answerData.getAnswers().size()) {
				status += "completed";
			}
			status += " ";
			if(module != null) {
				if(Integer.parseInt(module) == moduleData.getId().intValue()) {
					status += "active";
					moduleName = messages.getText(moduleData.getKey());
					finished = false;
				}
			}else {
				if(moduleData.getQuestionSize() > answerData.getAnswers().size()) {
					module = moduleData.getId().toString();
					status += "active";
					moduleName = messages.getText(moduleData.getKey());
					finished = false;
				}
			}		
			status += "\"";
	        String value = "<li "+status+"><a class=\"title\" href=\""+link+"\">"+String.valueOf(index+1) + ". " + messages.getText(moduleData.getKey())+"</a></li>";
			modules[index] = value;
	        index++;
		}
%>   
	<html:form action="/SelectAssessment">
		<html:hidden property="assessment"/>
	</html:form>
   <body>
      <header id="header">
         <section class="grid_container">
            <h1 class="customer_logo" style="background-image: url('<%=logoName%>');">Driver Assessment</h1>
            <div class="toolbar">
<%		if(!telematics) {	
%>		       <div class="cepa_logo">CEPA Safe Drive</div>
               <span class="username"><%=userData.getFirstName()+" "+userData.getLastName() %></span>
<%		}
%>             <span class="exit"><a href="logout.jsp"><%=messages.getText("generic.messages.logout")%></a></span>
            </div>
         </section>
      </header>

	<form name="logout" action="./logout.jsp" method="post"></form>
<%		if(userData.getLoginName().startsWith("marsh2019_")) {
%>	<form name="selectda" action="./marsh.jsp" method="post"></form>
<%		} else {
%>	<form name="selectda" action="./select_da.jsp" method="post"></form>
<%		}
%>	<form name="home" action="./index.jsp" method="post">
		<input type="hidden" name="assessment" value="<%=String.valueOf(assesment.getId())%>"/>
	</form>
	<form name="results" action="./results.jsp" method="post">
	</form>
		
      <section id="content">
         <section class="grid_container">
<%		if(!sys.isTelematics()) {
%>            
            <nav class="sections" data-min-rel-top="0" data-min-rel-bottom="0">
               	<div class="score">
                  <h2 class="title"><%=((module == null || module.equals("0")) && assesment.isPsitest()) ? messages.getText("assesment.module.psicologic") : moduleName%></h2>
                  <span class="numbers">--/--</span>
                  <span class="percent"></span>
               	</div>
				<div class="picture_container"></div>
               	<ul class="sections unstyled">
<%
			for(int i = 0; i < modules.length; i++) {
%>				<%=modules[i]%>
<%			}
			if(assesment.isPsitest()) {
				String status = "class=\"";
				if(assesment.getPsiCount() != null && assesment.getPsiCount().intValue() >= 48) {
					status += "completed";
				}else {
					finished = false;
				}
				status += " ";
				if(module == null) {
					status += "active";
					module = "0";
				}
				status += "\"";
%>
            <li <%=status%>>
            	<a class="title" href="repeat_da.jsp?module=0">
            	<%=messages.getText("assesment.module.psicologic")%></a>
         	</li>
<%			}
			String linkPrivacy = "instructions/privacy_"+sys.getUserSessionData().getLenguage()+".htm";
			String linkInstructions = "instructions/instructions_"+sys.getUserSessionData().getLenguage()+".htm";
%>               
               	</ul>
               	<p class="copyright">
					<a href='<%=linkPrivacy%>' target="_overlay"><%=messages.getText("generic.messages.privacypolicy") %></a>
                  	&nbsp;|&nbsp;
                  	<a href='<%=linkInstructions%>' target="_overlay"><%=messages.getText("generic.messages.instructions") %></a>
					<br>
               		CEPA Safe Drive &copy; 2020
               	</p>
            </nav>
<%		}
		if(!finished && module != null) {
				String link = "moduleRepeat.jsp?module="+module+"&multi="+answerData.getId(); 
%>		 	<jsp:include page='<%=link%>'/>
<%      }else {
			boolean hasErrors = sys.getUserReportFacade().hasErrors(answerData.getId(), userSessionData);
			if(hasErrors) {
%>			<form>
				<fieldset id="username_block" class="active">
					<div>
						<br>
						<br>
						<h2 class="title">
							<span style="color:red;">
								<%=messages.getText("Estimado conductor, el resultado de sus respuestas hace necesario que se comunique con un supervisor antes de comenzar el próximo viaje. Agradecemos su colaboración.")%>
							</span>
						</h2>
						<br>
						<div>
				  			<input class="button" value='<%=messages.getText("generic.messages.logout")%>' onClick="javascript:document.forms['logout'].submit()" />
						</div>
					</div>
				</fieldset>  
			</form>
<%			}else{
%>			<form>
				<fieldset id="username_block" class="active">
					<div>
						<br>
						<br>
						<h2 class="title">
							<%=messages.getText("assesment.report.footermessage1")%>
						</h2>
						<br>
						<div>
				  			<input class="button" value='<%=messages.getText("generic.messages.logout")%>' onClick="javascript:document.forms['logout'].submit()" />
						</div>
					</div>
				</fieldset>  
			</form>
<%			}
		}
%>
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
		<script type="text/javascript" src="scripts/swfobject.js"></script>
   </body>
<%	}
%>   
</html>