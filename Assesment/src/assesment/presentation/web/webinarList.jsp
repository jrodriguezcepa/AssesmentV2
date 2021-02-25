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
	session.removeAttribute("webinarcode");
	session.removeAttribute("answersHash");

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
			max-width: 500px;
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
		.right1-icon {
		  background: transparent;
		  background-image: url("images/right.png");
		  background-size: cover;
		  border: none;
		  width: 48px;
		  height: 50px;
		  cursor: pointer;
		  color: transparent;
		  margin-left: auto;
  		  margin-right: auto;
		}
		.right2-icon {
		  background: transparent;
		  background-image: url("images/right4.png");
		  background-size: cover;
		  border: none;
		  width: 48px;
		  height: 50px;
		  cursor: pointer;
		  color: transparent;
		  margin-left: auto;
  		  margin-right: auto;
		}
		.right3-icon {
		  background: transparent;
		  background-image: url("images/right3.png");
		  background-size: cover;
		  border: none;
		  width: 48px;
		  height: 50px;
		  cursor: pointer;
		  color: transparent;
		  margin-left: auto;
  		  margin-right: auto;
		}
		.right4-icon {
		  background: transparent;
		  background-image: url("images/right2.png");
		  background-size: cover;
		  border: none;
		  width: 48px;
		  height: 50px;
		  cursor: pointer;
		  color: transparent;
		  margin-left: auto;
  		  margin-right: auto;
		}
		.right5-icon {
		  background: transparent;
		  background-image: url("images/right5.png");
		  background-size: cover;
		  border: none;
		  width: 48px;
		  height: 50px;
		  cursor: pointer;
		  color: transparent;
		  margin-left: auto;
  		  margin-right: auto;
		}
		.right6-icon {
		  background: transparent;
		  background-image: url("images/right1.png");
		  background-size: cover;
		  border: none;
		  width: 48px;
		  height: 50px;
		  cursor: pointer;
		  color: transparent;
		  margin-left: auto;
  		  margin-right: auto;
		}
		A{
    		text-decoration: none;
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
			<input type="submit" class="button" value='<%=Text.getMessage("generic.messages.accept")%>' />
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

		
%>   
   <body>
      	<header id="header">
      		<br><br>
	  	</header>
		<form name="logout" action="./logout.jsp" method="post"></form>
		<br><br><br>
		
		<section class="center">
			<br>
			<input type="hidden" name="target" value="assesment"/>
			<div class="center2">
				<img  class="header" src="images/main_logo.png" alt="cepa">
				<br><br><br>
				<div>
				   	<a href="webinarForm.jsp?assesment=1149"> 
						<input class="right1-icon" />
						<span style="font-size: 1.2em"><%=messages.getText("assessment1149.name")%></span>
					</a>
					<br><br><br>
				</div>
				<div>
				   	<a href="webinarForm.jsp?assesment=1270"> 
						<input class="right2-icon" />
						<span style="font-size: 1.2em"><%=messages.getText("assessment1270.name")%></span>
					</a>
					<br><br><br>
				</div>
				<div>
				   	<a href="webinarForm.jsp?assesment=1324"> 
						<input class="right3-icon" />
						<span style="font-size: 1.2em"><%=messages.getText("assessment1324.name")%></span>
					</a>
					<br><br><br>
				</div>
				<div>
				   	<a href="webinarForm.jsp?assesment=1327"> 
						<input class="right4-icon" />
						<span style="font-size: 1.2em"><%=messages.getText("assessment1327.name")%></span>
					</a>
					<br><br><br>
				</div>
				<div>
				   	<a href="webinarForm.jsp?assesment=1471"> 
						<input class="right5-icon" />
						<span style="font-size: 1.2em"><%=messages.getText("assessment1471.name")%></span>
					</a>
					<br><br><br>
				</div>
			</div>
		</section>
	<% 	}%>	
   </body> 
</html>