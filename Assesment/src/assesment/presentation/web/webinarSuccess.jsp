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
		 	background-color: #F1EDED;
 			box-shadow: 0px 0px 10px #C8C8C8;		
  			margin: auto;
			width: 80%;
			max-width:500px; 	
			padding: 30px;
		}
		.header {
    		display: block;
    		padding: 15 px;
   			margin-left: auto;
    		margin-right: auto
    	}
		.pt-icon {
		  background: transparent;
		  background-image: url("images/brazil.png");
		  background-size: cover;
		  border: none;
		  width: 68px;
		  height: 70px;
		  cursor: pointer;
		  color: transparent;
		  margin-left: auto;
  		  margin-right: auto;
		}
		.es-icon {
		  background: transparent;
		  background-image: url("images/spain.png");
		  background-size: cover;
		  border: none;
		  width: 68px;
		  height: 70px;
		  cursor: pointer;
		  color: transparent;
		  margin-left: auto;
  		  margin-right: auto;
		}
		.en-icon {
		  background: transparent;
		  background-image: url("images/united-kingdom.png"); 
		  background-size: cover;
		  border: none;
		  width: 68px;
		  height: 70px;
		  cursor: pointer;
		  color: transparent;
		  margin-left: auto;
  		  margin-right: auto;
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
			width:170px;
		    height:35px;
		}
		</style>

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
			<html:submit styleClass="button" value='<%=Text.getMessage("generic.messages.accept")%>' />
			<br>
		</form>   	 		
   	 </body>		
		
<%	}else {
		UserSessionData userSessionData = sys.getUserSessionData();
		Text messages = sys.getText(); 
		RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
		dispatcher.include(request,response);
		UserData userData = sys.getUserReportFacade().findUserByPrimaryKey(userSessionData.getFilter().getLoginName(),userSessionData);
	    String logoName = "../flash/images/logo2.png";
	    
	    String login = (String)session.getAttribute("webinaruser");
	    if(login==null){
			login=userSessionData.getFilter().getLoginName();
	    }
	    session.removeAttribute("webinaruser");
	    String assessmentId = (String)session.getAttribute("assesment");
	    session.removeAttribute("assessmentId");
	    boolean red = sys.getUserReportFacade().isResultRed(login, new Integer(assessmentId), userSessionData);
%>   
   	<body>
      	<header id="header">
      		<br><br>
	  	</header>
		<form name="logout" action="./logout.jsp" method="post"></form>
		<form name="repeat" action="./webinarForm.jsp" method="post">
			<input type="hidden" name="assesment" value="<%=assessmentId%>" />	
		</form>
		<form name="repeatGDC" action="./GDCForm.jsp" method="post">
			<input type="hidden" name="assesment" value="<%=assessmentId%>" />	
		</form>
		<br><br><br>
		
		<section class="center">
			<br>
			<img  class="header" src="images/main_logo.png" alt="cepa">
			<br><br><br><br>
			<section>
				<div class="center2">
			
<%		if(red) {
	System.out.println("entro a rojo");
%>					<h2 class="title">
						<%=messages.getText("assesment.webinar.notapprouved")%>
					</h2>
					<br>
					<br>
					<h2 class="title">
						<%=messages.getText("assesment.webinar.notapprouved2")%>
					</h2>
<%		}else {
			System.out.println("entro a otro color");
			if(Integer.parseInt(assessmentId) == AssesmentData.HRD_WEBINAR) {
%>					<h2 class="title">
						<%=messages.getText("assesment.report.footermessage1")%>
					</h2>
					<br>
					<br>
					<h2 class="title">
						<%=messages.getText("assesment.report.footermessage3")%>
					</h2>
<%			}else {
%>					<h2 class="title">
						<%=messages.getText("assesment.webinar.footermessage1")%>
					</h2>
					<br>
					<br>
					<h2 class="title">
						<%=messages.getText("assesment.webinar.footermessage3")%>
					</h2>
<%			}
		}
%>					<br><br>
					<div align="center">
<%		if(red) {
			if(Integer.parseInt(assessmentId)==AssesmentData.GDC){
%>			
				<input type="button" value='<%=messages.getText("generic.messages.repeat")%>' class="buttonRed" onclick="document.forms['repeatGDC'].submit();"/>	
<%			}else{
%>				<input type="button" value='<%=messages.getText("generic.messages.repeat")%>' class="buttonRed" onclick="document.forms['repeat'].submit();"/>				
<%			}
%>					    
<%		}
%>			    		<input type="button" value='<%=messages.getText("generic.messages.logout")%>' class="button" onclick="document.forms['logout'].submit();"/>
					</div>
					<br><br>
				</div>
			</section>
		</section>
	<% 	}%>	
   </body> 
</html>