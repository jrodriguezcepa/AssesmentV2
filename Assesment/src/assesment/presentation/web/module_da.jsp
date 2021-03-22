<!doctype html>
<%@page import="assesment.communication.assesment.GroupData"%>
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
	String language = (sys != null) ? sys.getUserSessionData().getLenguage() : System.getProperty("user.language");
	if(sys != null && sys.isTelematics()) {
		estilo = "styles/base_telematics.css";
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
	    AssesmentData assesment = sys.getUserSessionData().getFilter().getAssessmentData();
		if(Util.isNumber(module) && Integer.parseInt(module) == 0 
				&& !userSessionData.isPsiAccept() 
				&& sys.getUserReportFacade().findPsicoModuleResult(userSessionData.getFilter().getLoginName(),assesment.getId(),userSessionData).size() == 0) {
			response.sendRedirect("terms_psi.jsp?assessment="+assesment.getId());
		}else {		
			String moduleName = "";
			UserData userData = sys.getUserReportFacade().findUserByPrimaryKey(userSessionData.getFilter().getLoginName(),userSessionData);
			int assessmentId = assesment.getId().intValue();
		    String logoName = "../flash/images/logo"+assesment.getCorporationId()+".png";
		    Iterator it = assesment.getModuleIterator();
		    String[] modules = new String[assesment.getModules().size()]; 
		    int index = 0;
		    boolean finished = true;
			while(it.hasNext()) {
				ModuleData moduleData = (ModuleData)it.next();
				String link = "module_da.jsp?module="+moduleData.getId();
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
<%		if(userSessionData.isMutual()) {
%>            	<h1 class="customer_logo" style="background-image: url(images/mutual_logo.png);">Driver Assessment</h1>
					<div class="toolbar">
			   		<span class="username"><%=userData.getFirstName()+" "+userData.getLastName() %></span>
				</div>
<%	
		}else {
%>   
            <h1 class="customer_logo" style="background-image: url('<%=logoName%>');">Driver Assessment</h1>
<%		} %>
            <div class="toolbar">
<%		if(!userSessionData.isMutual()) {
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
            	<a class="title" href="module_da.jsp?module=0">
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
			if(module.equals("1079")) {
%>			<jsp:include page='survey_monsanto.jsp'/>			
<%			}else {
				String link = "module.jsp?module="+module; %>
         	<jsp:include page='<%=link%>'/>
<%      	}
		}else {
			if(assesment.isWebinar()) {
				boolean deleteResults = !sys.getUserReportFacade().isResultGreen(userSessionData.getFilter().getLoginName(), assesment.getId(), userSessionData);
				if(deleteResults && sys.getUserReportFacade().getFailedAssesments(userSessionData.getFilter().getLoginName(), assesment.getId(), userSessionData) < 2) {
					Collection list = new LinkedList();
					list.add(userSessionData.getFilter().getLoginName());
					sys.getAssesmentABMFacade().deleteQuestionTestResults(assesment.getId(), list, 1, userSessionData);
					sys.getUserABMFacade().failAssessment(assessmentId, userSessionData.getFilter().getLoginName(), null, userSessionData);
%>					<form>
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
									<%=messages.getText("generic.messages.repeatmsg2.1") %>
								</h2>
								<br>
								<div>
						  			<input class="button" value='<%=messages.getText("generic.messages.repeat")%>' onClick="javascript:document.forms['home'].submit()" />
								</div>
							</div>
						</fieldset>
					</form>
<%				} else {
%>					<form>
						<fieldset id="username_block" class="active">
							<div>
								<br>
								<br>
<%					if(deleteResults) {
%>								<h2 class="title">
									<%=messages.getText("assesment.webinar.notapprouved")%>
								</h2>
								<br>
								<br>
								<h2 class="title">
									<%=messages.getText("assesment.webinar.notapprouved2")%>
								</h2>
<%					}else {
						if(assessmentId == AssesmentData.HRD_WEBINAR) {
%>								<h2 class="title">
									<%=messages.getText("assesment.report.footermessage1")%>
								</h2>
								<br>
								<br>
								<h2 class="title">
									<%=messages.getText("assesment.report.footermessage3")%>
								</h2>
<%						}else {
%>								<h2 class="title">
									<%=messages.getText("assesment.webinar.footermessage1")%>
								</h2>
								<br>
								<br>
								<h2 class="title">
									<%=messages.getText("assesment.webinar.footermessage3")%>
								</h2>
<%						}
					}
%>								<br>
								<div>
					  				<input class="button" value='<%=messages.getText("generic.messages.logout")%>' onClick="javascript:document.forms['logout'].submit()" />
								</div>
							</div>
						</fieldset>
					</form>
<%				}
			}else {
				boolean deleteResults = false;
				if(assesment.isUntilApproved() || userData.isCoaching()) {
					deleteResults = !sys.getUserReportFacade().isResultGreen(userSessionData.getFilter().getLoginName(), assesment.getId(), userSessionData);
				}
				if(false && (userData.getLocation() == null || userData.getLocation().intValue() != -1)) {
					deleteResults = !sys.getUserReportFacade().isResultGreen(userSessionData.getFilter().getLoginName(), assesment.getId(), userSessionData);
					if(deleteResults) {
						userData.setLocation(-1);
						sys.getUserABMFacade().userUpdate(userData, userSessionData);
					}
				}
				boolean upmCharla = false;
				if(assessmentId == AssesmentData.UPM_CHARLA) {
					if(sys.getUserReportFacade().isResultRed(userSessionData.getFilter().getLoginName(), assesment.getId(), userSessionData)) {
						sys.getUserABMFacade().associateAssesment(userSessionData.getFilter().getLoginName(), AssesmentData.UPM_CHARLA_V2, userSessionData);
						userSessionData.getFilter().setAssesment(AssesmentData.UPM_CHARLA_V2);
						upmCharla = true;
					}
				}
				if(deleteResults) {
					Collection list = new LinkedList();
					list.add(userSessionData.getFilter().getLoginName());
					sys.getAssesmentABMFacade().deleteResults(assesment.getId(), list, 1, userSessionData);
					sys.getUserABMFacade().failAssessment(assessmentId, userSessionData.getFilter().getLoginName(), null, userSessionData);
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
<%				} else if(upmCharla) {
%>				<form>
					<fieldset id="username_block" class="active">
						<div>
							<br>
							<br>
							<h2 class="title">
								<span style="color: red;"><%=messages.getText("generic.messages.repeatmsg1") %></span>
							</h2>
							<br>
							<br>
							<h2 class="title">
								<span style="color: red;">Por favor realice la segunda parte de la evaluaci&oacute;n con el bot&oacute;n "Continuar".</span>
							</h2>
							<br>
							<div>
					  			<input class="buttonRed" value='<%=messages.getText("generic.messages.continue")%>' onClick="javascript:document.forms['home'].submit()" />
							</div>
							<br>
							<div>
					  			<input class="button" value='<%=messages.getText("generic.messages.logout")%>' onClick="javascript:document.forms['logout'].submit()" />
							</div>
						</div>
					</fieldset>
				</form>
<%				}else if(assessmentId == AssesmentData.SURVEY || assessmentId == AssesmentData.SAFETY_SURVEY) {
%>				<form>
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
<%				}else if(assessmentId == AssesmentData.HOJA_RUTA_2019) {
%>				<form>
					<fieldset id="username_block" class="active">
						<div>
							<br>
							<br>
							<h2 class="title">
								Agradecemos que se haya tomado el tiempo de responder este Assessment, en unos d&iacute;as, el personal de CEPA se pondr&aacute; en contacto con usted para dar seguimiento a la entrega del informe.
							</h2>
							<br>
							<h2 class="title">
								Saludos!
							</h2>
							<br>
							<div>
					  			<input class="button" value='<%=messages.getText("generic.messages.logout")%>' onClick="javascript:document.forms['logout'].submit()" />
							</div>
						</div>
					</fieldset>  
				</form>
<%				}else if(assessmentId == AssesmentData.MERCANCIAS_PELIGROSAS || assessmentId == AssesmentData.MERCANCIAS_PELIGROSAS_RENOVACION) {
%>				<form>
					<fieldset id="username_block" class="active">
						<div>
							<br>
							<br>
<%					if(sys.getUserReportFacade().isResultRed(userSessionData.getFilter().getLoginName(), assesment.getId(), userSessionData)) {
%>							<h2 class="title">
								Gracias por su participaci&oacute;n, usted <b>NO respondi&oacute; con &eacute;xito el test online</b> de la actividad, en breve el sistema enviar&aacute; por email su reporte.
							</h2>
							<br>
							<h2 class="title">
								Favor comunicarse con la administraci&oacute;n de CEPA.
							</h2>
<%					} else {
%>							<h2 class="title">
								Gracias por su participaci&oacute;n, respondi&oacute; con &eacute;xito el test y su resultado es <b>APROBADO</b>. 
							</h2>
							<br>
							<h2 class="title">
								En breve el sistema enviar&aacute; por email su reporte y certificado.
							</h2>
<%					}
%>							<br>
							<br>
							<div>
					  			<input class="button" value='<%=messages.getText("generic.messages.logout")%>' onClick="javascript:document.forms['logout'].submit()" />
							</div>
						</div>
					</fieldset>  
				</form>
<%				}else if(assesment.getModules().size() == 0){
%>				<form action="logout.jsp">
					<fieldset id="username_block" class="active">
						<br>
						<br>
						<div>
		                  	<label for="accesscode"><%=messages.getText("generic.messages.noassessment")%></label>
						</div>
						<br>
						<br>
						<html:submit styleClass="button" value='<%=messages.getText("generic.messages.logout")%>' />
						<br>
					</fieldset>
				</form>
<%				}else{
%>				<form>
					<fieldset id="username_block" class="active">
						<div>
							<br>
							<br>
							<h2 class="title">
								<%=messages.getText("assesment.report.footermessage1")%>
							</h2>
							<br>
<%					if(assessmentId == AssesmentData.MDP_2015) {
						HashMap results = sys.getUserReportFacade().findUsersResults(assesment.getId(),userSessionData.getFilter().getLoginName(),userSessionData);
						int total = 0;
						int correct = 0;
						it = results.values().iterator();
						while(it.hasNext()) {
							Object[] data = (Object[])it.next();
							total += ((Integer)data[1]).intValue() + ((Integer)data[2]).intValue();
							correct += ((Integer)data[2]).intValue();
						}
%>							<h3 class="title">
								<%="Usted ha contestado correctamente "+correct+" preguntas, de un total de "+total+" ("+Math.round(correct * 100 / total)+"%)"%>
							</h3>
							<br>
<%					}
					if(finished && assesment.isReportFeedback() && assessmentId != AssesmentData.ANTP_MEXICO_RSMM && assessmentId != AssesmentData.MUTUAL_RSMM) {
%>							<br>
							<h3 class="title">
				  				<%=messages.getText("generic.messages.viewresult1")%> 
				  				<a href="javascript:document.forms['results'].submit()" style="text-decoration: none;" >
				  					<span color="#CC0000"><%=messages.getText("generic.messages.viewresult2")%></span>
				  				</a>.
							</h3>
<%					}
					if(assessmentId == AssesmentData.TIMAC_BRASIL_EBTW_2020) {
%>							<hr>
							<br>
							<div>
				  				<input type="button" class="buttonGreen" value='<%=messages.getText("generic.messages.continue")%>' onClick='<%="javascript:enterDA("+AssesmentData.TIMAC_BRASIL_DA_2020+")"%>' />
							</div>
<%					} else {
						if(userSessionData.getRole().equals(UserData.MULTIASSESSMENT)) {
							Calendar c = Calendar.getInstance();
%>							<hr>
							<br>
							<div>
				  				<input type="button" class="buttonGreen" value='<%=messages.getText("generic.messages.continue")%>' onClick="javascript:document.forms['selectda'].submit()" />
							</div>
							<br>
							<hr>
<%						}
%>							<div>
				  				<input class="button" value='<%=messages.getText("generic.messages.logout")%>' onClick="javascript:document.forms['logout'].submit()" />
							</div>
<%					}
%>						</div>
					</fieldset>  
				</form>
<%				}
			}
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
<%		}
	}
%>   
</html>