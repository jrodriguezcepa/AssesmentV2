<!doctype html>
<%@page import="assesment.communication.assesment.GroupData"%>
<%@page import="assesment.presentation.translator.web.administration.user.LogoutAction"%>
<%@page import="assesment.presentation.translator.web.util.Util"%>
<%@page import="assesment.communication.assesment.AssesmentAttributes"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.ArrayList"%>
<%@page import="assesment.communication.question.QuestionData"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="assesment.persistence.user.tables.UserAssesmentData"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.LinkedList"%>
<%@page import="assesment.business.AssesmentAccess"%>
<%@page import="assesment.communication.assesment.AssesmentData"%>
<%@page import="assesment.communication.util.CountryConstants"%>
<%@page import="assesment.communication.util.CountryData"%>
<%@page import="assesment.communication.question.AnswerData"%>
<%@page import="java.util.Iterator"%>
<%@page import="assesment.communication.module.ModuleData"%>
<%@page import="assesment.communication.administration.UserAnswerData"%>
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
	ArrayList<UserAnswerData> answerDataList=new ArrayList<UserAnswerData>();

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
		.body{
			background-image: url("images/background.png");
			background-repeat: repeat;

		}
		.center{
		 	background-color: #ECECEC;
 			box-shadow: 0px 0px 10px #C8C8C8;		
  			margin: auto;
			width: 80%;
			max-width: 800px;
			padding: 10px;
			
		}
		.center2{
  			margin: auto;
			width: 80%;
			padding: 10px;
		}
		.header {
    		display: block;
    		padding: 15 px;
   			margin-left: auto;
    		margin-right: auto
    	}
		.column {
		 	float: left;
		 	text-align: center;
			width: 20%;
			padding: 5px;
		}
		.row::after { 
			content: "";
			clear: both;
			display: table;
		} 
		.buttonRed{
			background-color: red;
			color: white;
			font-family: 'Roboto Thin',"Helvetica Neue", Helvetica, Arial, "Pontano Sans", Verdana, sans-serif;
			font-size: 18px;
			margin-top: .3em;
	        border-radius: 0.2em;
			-moz-border-radius: 0.2em;
			-webkit-border-radius: 0.2em;
			border: 1px solid #999;
			text-align: center;
			width:90px;
		    height:35px;
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
		<form action="logout.jsp">
			<br>
			<br>
			<div>
            	<label for="accesscode"><%=Text.getMessage("generic.messages.session.expired")%></label>
			</div>
			<br>
			<br>
			<input type="submit" class="button" value='<%=Text.getMessage("generic.messages.accept")%>' />
			<br>
		</form>   	 		
   	 </body>		
<%	}else {
		UserSessionData userSessionData = sys.getUserSessionData();
		Text messages = sys.getText(); 
		Integer assesmentId = new Integer(request.getParameter("assesment"));
		session.setAttribute("assesment", assesmentId);
		
		RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
		dispatcher.include(request,response);
		AssesmentData assesment =  sys.getAssesmentReportFacade().findAssesment(assesmentId, userSessionData);
		//AssesmentData assesment = sys.getUserSessionData().getFilter().getAssessmentData();
		UserData userData = sys.getUserReportFacade().findUserByPrimaryKey(userSessionData.getFilter().getLoginName(),userSessionData);
		//int assessmentId = assesment.getId().intValue();
	    String logoName = "../flash/images/logo"+assesment.getCorporationId()+".png";
		String webinar="";
		if((String)session.getAttribute("webinarcode")!=null){
			webinar=(String)session.getAttribute("webinarcode");	
		}
		HashMap <Integer, String> answers=new HashMap<Integer, String>();
%>   
   	<body>
   		<header id="header">
      		<br><br>
	  	</header>
		<form name="logout" action="./logout.jsp" method="post"></form>
		<form name="back" action="./webinarList.jsp" method="post"></form>
		
		<br><br><br>
		
		<section class="center">
			<br>
			<img  class="header" src="images/main_logo.png" alt="cepa">
			<br>
			<div class=center2> 
				<label><h4><%=messages.getText(assesment.getName())%> </h4></label>
			</div>
			<html:form action="/AssesmentSaveAnswers">
				<html:hidden property="assesment" value='<%=String.valueOf(assesment.getId())%>'/>

				<section>
					<div class="center2">
						<div>
							<label>
								<h5><%=messages.getText("generic.data.webinarcode")%> </h5>
							</label>
						</div>
						<div>
	           				<input type="text" id="webinarCode" name="webinarCode" maxlength=6 value='<%=webinar %>'></input>
						</div>
						<div>
	           				<img src="images/lineWebinar.png" style="width: 100%">
						</div>
					</div>
<%		Iterator<ModuleData> modules = assesment.getModuleIterator();
		if (session.getAttribute("answersHash")!=null){
			answers=(HashMap<Integer, String>) session.getAttribute("answersHash");
		}
		int i=0;	
		while(modules.hasNext()) {
			ModuleData module = modules.next();
			Iterator<QuestionData> questions = module.getQuestionIterator();
%>
						<div class="center2"> 
							</br>
							<label>
								<h5><%=messages.getText(module.getKey())%> </h5>
							</label>
						</div>
<% 			while(questions.hasNext()) {
				QuestionData question = questions.next();
				Integer questionId=question.getId();
				if (session.getAttribute("answersHash")==null){
					answers.put(questionId, "");	
				}
				Integer questionType =question.getType();
				String answerId="answer";
				Integer moduleId=module.getId();
%>						<section>
							<div class="center2">
<%				if(questionType != QuestionData.YOU_TUBE_VIDEO && questionType != QuestionData.VIDEO) {
%>    							<div>
									<br><label><%=messages.getText(question.getKey())%></label>
								</div>	
<%				}
				if(questionType == QuestionData.COUNTRY) {
					CountryConstants cc = new CountryConstants(messages);
					Iterator countryIt = cc.getCountryIterator();
%>	    						
								<select name='<%=questionId%>' id='<%=questionId%>'>
					        		<option value="">---------</option>
<%					while(countryIt.hasNext()) {
						CountryData country = (CountryData)countryIt.next();
%>						            <option value='<%=country.getId()%>'> <%=country.getName()%></option>							
<%					}
%>								</select>
							
						
				
				<%	}else if(questionType == QuestionData.INCLUDED_OPTIONS) {
						Iterator answerIt = (assesmentId == AssesmentData.ANTP_MEXICO_RSMM || assesmentId == AssesmentData.TRANSPORTES_NIQUINI) ? question.getAnswerDisorder() : question.getAnswerIterator();			
						while(answerIt.hasNext()) {
							AnswerData answerData = (AnswerData)answerIt.next();
%>								<div>
									<label class="checkbox"><input name='<%=questionId%>' type="checkbox" value='<%=String.valueOf(answerData.getId())%>'></input><%=messages.getText(answerData.getKey())%></label>
								</div>
								</br>
<%						} 
					 }else if(questionType == QuestionData.EXCLUDED_OPTIONS) {
						Iterator answerIt = (assesmentId == AssesmentData.ANTP_MEXICO_RSMM || assesmentId == AssesmentData.TRANSPORTES_NIQUINI) ? question.getAnswerDisorder() : question.getAnswerIterator();			
						while(answerIt.hasNext()) {
							AnswerData answerData = (AnswerData)answerIt.next();
							if(answers.containsKey(questionId) && answers.get(questionId).equals(String.valueOf(answerData.getId()))){
%>								<label class="radio">
									<input type="radio" name='<%=questionId%>'  value='<%=String.valueOf(answerData.getId())%>' checked/><%=messages.getText(answerData.getKey())%>
								</label>
								</br>	
<%							}else{
%>								<label class="radio">
									<input type="radio" name='<%=questionId%>'  value='<%=String.valueOf(answerData.getId())%>'/><%=messages.getText(answerData.getKey())%>
								</label>
								</br>
<%						}}  
					}else if(questionType == QuestionData.LIST) {
%>				                <select name='<%=questionId%>' id='<%=questionId%>'>
	  				              	<option value="">---------</option>
<%						Iterator answerIt = (assesmentId == AssesmentData.ANTP_MEXICO_RSMM || assesmentId == AssesmentData.TRANSPORTES_NIQUINI) ? question.getAnswerDisorder() : question.getAnswerIterator();			
						while(answerIt.hasNext()) {
							AnswerData answerData = (AnswerData)answerIt.next();
%>									<option value='<%=String.valueOf(answerData.getId())%>'><%=messages.getText(answerData.getKey())%></option>
<%						}						
%>								</select>
<%					}else if(questionType == QuestionData.TEXTAREA) {
						if(answers.containsKey(questionId)){
%>          			    <textarea name='<%=questionId%>' cols="30" rows="5"><%=answers.get(questionId) %></textarea>
<%						}
					}else if(questionType == QuestionData.DATE || questionType == QuestionData.BIRTHDATE) { 
%>      			            <input type='<%=question.getHtmlType()%>' name='<%=questionId%>'value='<%=answers.get(questionId) %>'></input>
<%					}else if(questionType == QuestionData.KILOMETERS) {
%>      	        		   <input name='<%=questionId%>' type='<%=question.getHtmlType()%>' value='<%=answers.get(questionId) %>'></input>
<%					}else {
%>      			    <input name='<%=questionId%>' type='<%=question.getHtmlType()%>' value='<%=answers.get(questionId) %>'></input>
								<br>
<%	        		}%>	
						
							</div>
							<div>
		           				<img src="images/lineWebinar.png" style="width: 100%">
							</div>
						</section>
<% 				}
			}
%>					</section>
			
				<br>
				<div align="center">
			       	<html:submit value='<%=messages.getText("generic.messages.accept")%>' styleClass="button"/>
			       	<input type="button" value='<%=messages.getText("generic.messages.change")%>' class="buttonRed" onclick="document.forms['back'].submit();"/>
				</div>
			</html:form>
		</section>
<% 		}
%>	
   </body> 
</html>