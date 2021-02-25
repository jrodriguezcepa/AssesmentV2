<!doctype html>
<%@page import="java.nio.charset.Charset"%>
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
<%
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	UserSessionData userSessionData = sys.getUserSessionData();
	Text messages = sys.getText(); 
	boolean telematics = sys.isTelematics();
	String estilo = (telematics) ? "styles/base_telematics.css" : "styles/base.css";
	Charset charset = Charset.forName("UTF-8");
	/*if(userSessionData.getLenguage().equals("vt")) {
		charset = Charset.forName("Cp1258"); 
	}*/
			

%>


<%@page import="java.util.HashMap"%>
<html lang='vt'>
	<head>
		<meta  charset="windows-1258">
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
	String module = request.getParameter("module");
	String moduleName = "";
	UserData userData = sys.getUserReportFacade().findUserByPrimaryKey(userSessionData.getFilter().getLoginName(),userSessionData);
    AssesmentData assesment = sys.getUserSessionData().getFilter().getAssessmentData();
	int assessmentId = assesment.getId().intValue();
    String logoName = "../flash/images/logo"+assesment.getCorporationId()+".png";
    Iterator it = assesment.getModuleIterator();
    String[] modules = new String[assesment.getModules().size()];
    int index = 0;
    boolean finished = true;
	while(it.hasNext()) {
		ModuleData moduleData = (ModuleData)it.next();
		String link = "module_vt.jsp?module="+moduleData.getId();
		String status = "class=\"";
		if(moduleData.getQuestionSize() == moduleData.getAnswered().intValue()) {
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
			if(moduleData.getQuestionSize() > moduleData.getAnswered().intValue()) {
				module = moduleData.getId().toString();
				status += "active";
				moduleName = messages.getText(moduleData.getKey());
				finished = false;
			}
		}		
		status += "\"";
        String value = "<li "+status+"><a class=\"title\" href=\""+link+"\">"+String.valueOf(index+1) + ". " + new String(messages.getText(moduleData.getKey()).getBytes(charset))+"</a></li>";
		modules[index] = value;
        index++;
	}
%>   
   <body>
      <header id="header">
         <section class="grid_container">
            <h1 class="customer_logo" style="background-image: url('<%=logoName%>');">Driver Assessment</h1>
            <div class="toolbar">
<%	if(!telematics) {	
%>		       <div class="cepa_logo">CEPA Safe Drive</div>
               <span class="username"><%=userData.getFirstName()+" "+userData.getLastName() %></span>
<%	}
%>             <span class="exit"><a href="logout.jsp"><%=messages.getText("generic.messages.logout")%></a></span>
            </div>
         </section>
      </header>

	<form name="logout" action="./logout.jsp" method="post"></form>
	<form name="home" action="./index.jsp" method="post">
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
                  <h2 class="title"><%=((module == null || module.equals("0")) && assesment.isPsitest()) ? new String(messages.getText("assesment.module.psicologic").getBytes(charset)) : new String(moduleName.getBytes(charset))%></h2>
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
            	<a class="title" href="module_vt.jsp?module=0">
            		<%=new String(messages.getText("assesment.module.psicologic").getBytes(charset))%>
				</a>
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
			if(module.equals("1079")) {
%>			<jsp:include page='survey_monsanto.jsp'/>			
<%			}else {
				String link = "moduleT.jsp?module="+module; %>
         		<jsp:include page='<%=link%>'/>
<%      	}
		}else {
			boolean deleteResults = false;
			if(assessmentId == AssesmentData.BAYERMX_RIESGO_TRASERO || assessmentId == AssesmentData.BAYER_MEXICO || assessmentId == AssesmentData.BAYER_MEXICO_VELOCIDAD
					|| assessmentId == AssesmentData.ROCHE_MX_ELEARNINGPACK || assessmentId == AssesmentData.ROCHE_MX_VELOCIDAD || assessmentId == AssesmentData.ROCHE_MX_RIESGOTRASERO 
					|| assessmentId == AssesmentData.ROCHE_MX_INTERSECCIONES
					|| assessmentId == AssesmentData.BAYER_MEXICO_INTERSECCIONES  || assessmentId == AssesmentData.BAYER_MEXICO_ENTENDERCRC || assessmentId == AssesmentData.JJ_RIESGO_TRASERO || userData.isCoaching()) {
				deleteResults = !sys.getUserReportFacade().isResultGreen(userSessionData.getFilter().getLoginName(), assesment.getId(), userSessionData);
			}
			if(deleteResults) {
				Collection list = new LinkedList();
				list.add(userSessionData.getFilter().getLoginName());
				sys.getAssesmentABMFacade().deleteResults(assesment.getId(), list, 1, userSessionData);
%>				<form>
					<fieldset id="username_block" class="active">
						<div>
							<br>
							<br>
							<h2 class="title">
								<%=messages.getText("generic.messages.repeatmsg1") %>
							</h2>
							<br>
							<br>
							<h2 class="title">
								<%=messages.getText("generic.messages.repeatmsg2") %>
							</h2>
							<br>
							<div>
					  			<input class="button" value='<%=messages.getText("generic.messages.repeat")%>' onClick="javascript:document.forms['home'].submit()" />
							</div>
						</div>
					</fieldset>
				</form>
						
				
<%			}else if(assessmentId == AssesmentData.SURVEY) {
%>
			<form>
				<fieldset id="username_block" class="active">
					<div>
						<br>
						<br>
						<h2 class="title">
							<%=messages.getText("survey.messages.text1")%>
						</h2>
						<br>
						<h2 class="title">
							<%=messages.getText("survey.messages.text2")%>
						</h2>
						<br>
						<div>
				  			<input class="button" value='<%=messages.getText("generic.messages.logout")%>' onClick="javascript:document.forms['logout'].submit()" />
						</div>
					</div>
				</fieldset>  
			</form>
<%			}else{
%>
			<form>
				<fieldset id="username_block" class="active">
					<div>
						<br>
						<br>
						<h2 class="title">
							<%=messages.getText("assesment.report.footermessage1")%>
						</h2>
						<br>
<%				if(assessmentId == AssesmentData.MDP_2015) {
					HashMap results = sys.getUserReportFacade().findUsersResults(assesment.getId(),userSessionData.getFilter().getLoginName(),userSessionData);
					int total = 0;
					int correct = 0;
					it = results.values().iterator();
					while(it.hasNext()) {
						Object[] data = (Object[])it.next();
						total += ((Integer)data[1]).intValue() + ((Integer)data[2]).intValue();
						correct += ((Integer)data[2]).intValue();
					}
%>							
						<h3 class="title">
							<%="Usted ha contestado correctamente "+correct+" preguntas, de un total de "+total+" ("+Math.round(correct * 100 / total)+"%)"%>
						</h3>
						<br>
<%				
				}
				if(finished && assesment.isReportFeedback()) {
%>						<br>
						<h3 class="title">
				  			<%=messages.getText("generic.messages.viewresult1")%> 
			  				<a href="javascript:document.forms['results'].submit()" style="text-decoration: none;" >
			  					<span color="#CC0000"><%=messages.getText("generic.messages.viewresult2")%></span>
			  				</a>.
						</h3>
<%				}
				if(userSessionData.getRole().equals(UserData.MULTIASSESSMENT)) {
					Calendar c = Calendar.getInstance();
%>						<hr>
						<div>
		                  	<h2><%=messages.getText("assessment.users.associated")%></h2>
						</div>
						<br>
<%					Iterator assessments = sys.getUserReportFacade().findTotalUserAssesments(sys.getUserSessionData().getFilter().getLoginName(),sys.getUserSessionData()).iterator();
		    		while(assessments.hasNext()) {         		
%>						<div>
<%					UserAssesmentData userAssessment = (UserAssesmentData)assessments.next();
					AssesmentData assessment = userAssessment.getAssessment();
					StringTokenizer tokenizer = new StringTokenizer(assessment.getName(), "-");
					String name = tokenizer.nextToken().trim();
					if(tokenizer.hasMoreTokens())
						name = tokenizer.nextToken().trim();
						Calendar start = Calendar.getInstance();
						start.setTime(assessment.getStart());
						Calendar end = Calendar.getInstance();
						end.setTime(assessment.getEnd());
						if(userAssessment.getEnd() != null) {
%>								<table width="100%" cellspacing="2" cellpadding="2">
									<tr class="texto">
										<td width="48%" class="texto"><%=name%></td>
										<td width="4%"></td>
										<td width="48%" class="texto"><%=messages.getText("assesment.status.ended")%></td>
									</tr>
								</table>
<%						}else if(assessment.getStatus().intValue() != AssesmentAttributes.NO_EDITABLE) {
%>								<table width="100%" cellspacing="2" cellpadding="2">
									<tr>
										<td width="48%" class="texto"><%=name%></td>
										<td width="4%"></td>
										<td width="48%" class="texto"><%=messages.getText("generic.messages.notavailable") %></td>
									</tr>
								</table>							
<%						}else if(c.before(start)) {
%>								<table width="100%" cellspacing="2" cellpadding="2">
									<tr>
										<td width="48%" class="texto"><%=name%></td>
										<td width="4%"></td>
										<td width="48%" class="texto"><%=messages.getText("generic.messages.available") %>:	<%=Util.formatDate(assessment.getStart()) %></td>
									</tr>
								</table>						
<%						}else if(c.after(end)) {
%>								<table width="100%" cellspacing="2" cellpadding="2">
									<tr>
										<td width="48%" class="texto"><%=name%></td>
										<td width="4%"></td>
										<td width="48%"><input type="button" class="buttonRed" value="<%=messages.getText("generic.report.pending")%>" onclick="enterDA(<%=String.valueOf(assessment.getId())%>)"></td>
									</tr>
								</table>						
<%						}else {
							if(userAssessment.getAnswers() > 0){
%>								<table width="100%" cellspacing="2" cellpadding="2">
									<tr>
										<td width="48%" class="texto"><%=name%></td>
										<td width="4%"></td>
										<td width="48%"><input type="button" class="buttonYellow" value="<%=messages.getText("generic.messages.tocomplete")%>" onclick="enterDA(<%=String.valueOf(assessment.getId())%>)"></td>
									</tr>
								</table>
<%							}else {
%>								<table width="100%" cellspacing="2" cellpadding="2">
									<tr>
										<td width="48%" class="texto"><%=name%></td>
										<td width="4%"></td>
										<td width="48%"><input type="button" class="buttonGreen" value="<%=messages.getText("generic.messages.start")%>" onclick="enterDA(<%=String.valueOf(assessment.getId())%>)"></td>
									</tr>
								</table>
<%							}
						}
%>
						</div>
						<hr>
<%		    		}
				}
%>						<br>
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
</html>