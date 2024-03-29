<%@page import="assesment.business.administration.user.UsReportFacade"%>
<%@page import="assesment.communication.user.UserData"%>
<%@page import="assesment.communication.assesment.AssesmentData"%>
<%@ page language="java" isErrorPage="true"
	import="java.util.*"
	import="assesment.communication.module.*"
	import="assesment.presentation.translator.web.util.*"
	import="assesment.communication.language.Text"
	import="assesment.communication.question.QuestionData"
%>
<%@page import="assesment.communication.administration.user.UserSessionData"%>
<%@page import="assesment.business.AssesmentAccess"%>
<%@page import="assesment.communication.util.CountryConstants"%>
<%@page import="assesment.communication.util.CountryData"%>
<%@page import="assesment.communication.question.AnswerData"%>
<%@page import="assesment.communication.administration.UserAnswerData"%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>

<!doctype html>
	<html lang="en">
  		<head>
    		<meta charset="utf-8"/>
    		<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    		<link rel="shortcut icon" href="favicon.ico"/>
    		<meta name="viewport" content="width=device-width,initial-scale=1"/>
    		<meta name="theme-color" content="#405959"/>
    		<title>Driver Assessment</title>
		    <link rel="manifest" href="manifest.json"/>
    		<link href="static/css/fonts/roboto.css" rel="stylesheet"/>
    		<link href="static/css/fonts/font-awesome.min.css" rel="stylesheet"/>
    		<script src="static/js/jquery-3.4.1.min.js"></script>
    		<script src="static/js/bootstrap.bundle.min.js"></script>
    		<style>#assessment{display:none}</style>
			<link href="./static/css/main.css" rel="stylesheet">
  		</head>
		<body class="questions">

<%
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	if(sys == null) {
		response.sendRedirect("logout.jsp");
	}else {
		UserSessionData userSessionData = sys.getUserSessionData();
		Text messages = sys.getText();
		
		String user = userSessionData.getFilter().getLoginName();
		UsReportFacade userReport = sys.getUserReportFacade();
		UserData userData = userReport.findUserByPrimaryKey(user,userSessionData);

		String assessmentId = request.getParameter("id");
	    AssesmentData assesment = sys.getAssesmentReportFacade().findAssesment(new Integer(assessmentId), userSessionData);

	    HashMap hash = sys.getUserReportFacade().findPsicoModuleResult(userSessionData.getFilter().getLoginName(),assesment.getId(),userSessionData);
	    
	    if(hash.size() == 0 && !userSessionData.isPsiAccept()) {
			response.sendRedirect("termsgroup_psi.jsp?assessment="+assessmentId);
	    }else {
%>	    
			<noscript>You need to enable JavaScript to run this app.</noscript>
			<nav class="navbar bg-transparent-light fixed-top navbar-light shadow-sm">
      			<div class="container">
        			<div class="navbar-menu dropdown">
          				<a href="#" id="navbar-menu-toggle" role="button" data-toggle="dropdown" aria-haspopup="true"></a>
          				<div class="dropdown-menu">
<%
			Iterator it = assesment.getModuleIterator();
			boolean pending = false;
			Integer moduleId = null;
			while(it.hasNext()) {
				ModuleData moduleData = (ModuleData)it.next();
				Integer answered = userReport.getQuestionCount(user,moduleData.getId(),assesment.getId(),userSessionData);
				if(moduleId != null) {
					if(!moduleId.equals(moduleData.getId())) {
						if(moduleData.getQuestionSize() >= answered.intValue()) {
							pending = true;
						}
						String link = "newmodule.jsp?id="+assessmentId+"&module="+moduleData.getId();
%>    				<a class="dropdown-item active" href="<%=link%>"><%=messages.getText(moduleData.getKey()) %></a>
<%					}
				}else {
					if(moduleData.getQuestionSize() == answered.intValue()) {
						String link = "newmodule.jsp?id="+assessmentId+"&module="+moduleData.getId();
%>    				<a class="dropdown-item active" href="<%=link%>"><%=messages.getText(moduleData.getKey()) %></a>
<%					}else {
						moduleId = moduleData.getId();
					}
				}
			}
			if(assesment.isPsitest()) {
				String link = "psimodule.jsp?id="+assessmentId+"&module=0";
%>    				<a class="dropdown-item active" href="<%=link%>"><%=messages.getText(messages.getText("assesment.module.psicologic")) %></a>
<%			}
%>            				<a class="dropdown-item active" href="./group.jsp"><%=messages.getText("generic.messages.back")%></a>
          				</div>
        			</div>
        	        <div class="navbar-brand">
          				<!--img src="static/images/logo.png" width="75" height="80" alt=""-->
        			</div>
        			<div class="navbar-profile dropdown">
          				<a href="#" id="navbar-profile-toggle" role="button" data-toggle="dropdown" aria-haspopup="true">
            				<div class="label">
              					<span class="d-none d-sm-inline-block"><%=messages.getText("generic.messages.welcome")+", "+userData.getFirstName()%></span>
            				</div>
          				</a>
          				<div class="dropdown-menu dropdown-menu-right">
            				<div class="dropdown-header d-sm-none"><%=messages.getText("generic.messages.welcome")+", "+userData.getFirstName()%></div>
           					<a class="dropdown-item" href="./logout.jsp"><%=messages.getText("generic.messages.logout")%></a>
        				</div>
       				</div>
     			</div>
    		</nav>
    		<div id="root"></div>
<%			
			ModuleData moduleData = sys.getModuleReportFacade().getPsicoModule(userSessionData);
%>			<form id="assessment" action="resultPsi.jsp" data-module="<%=assessmentId%>" data-success-redirect='<%=(pending) ? "newmodule.jsp?id="+assessmentId : "group.jsp"%>'>
				<input type="hidden" name="assessment" value="<%=assessmentId%>"/>
<%			it = moduleData.getQuestionIterator();
			int lastGroup = -1;
		    while(it.hasNext()) {
	  	    	QuestionData question = (QuestionData)it.next();
	          	String questionId = "qw"+question.getId();
	          	String answerId = "q"+question.getId();
	          	int questionType = question.getType().intValue();
	          	int questionGroup = question.getGroup().intValue();
	          	UserAnswerData userAnswerData = null;
	          	String completed = "";
	          	if(hash.containsKey(question.getId())) {
	          		userAnswerData = (UserAnswerData)hash.get(question.getId());
	          		completed = "data-completed=\"true\"";
	          	}
	          	Iterator answerIt = question.getAnswerIterator();			
%>				<fieldset>
          			<div id='<%=questionId%>' data-type="3"  <%=completed%>>
          				<label for='<%=answerId%>'>
          					<%=messages.getText(question.getKey())%>
          				</label>
<%				while(answerIt.hasNext()) {
          			AnswerData answerData = (AnswerData)answerIt.next();
          			String selected = (userAnswerData != null && userAnswerData.getAnswer().equals(answerData.getId())) ? "checked" : "";	
%>						<label class="radio">
							<input type="radio" name='<%=answerId%>' value='<%=String.valueOf(answerData.getId())%>' <%=selected%>>
          					<%=messages.getText(answerData.getKey())%>
          				</label>
<%				}
%>					</div>
				</fieldset>							
<%			}
%>			</form>
<%      	if(!pending) {
%>			<script id="final_text" type="text/template">
				<%=messages.getText("assesment.reporthtml.footermessage1")%>
			</script>
<%			}
	    }
	}
%>
			<script>!function(f){function e(e){for(var r,t,n=e[0],o=e[1],u=e[2],l=0,a=[];l<n.length;l++)t=n[l],Object.prototype.hasOwnProperty.call(p,t)&&p[t]&&a.push(p[t][0]),p[t]=0;for(r in o)Object.prototype.hasOwnProperty.call(o,r)&&(f[r]=o[r]);for(s&&s(e);a.length;)a.shift()();return c.push.apply(c,u||[]),i()}function i(){for(var e,r=0;r<c.length;r++){for(var t=c[r],n=!0,o=1;o<t.length;o++){var u=t[o];0!==p[u]&&(n=!1)}n&&(c.splice(r--,1),e=l(l.s=t[0]))}return e}var t={},p={1:0},c=[];function l(e){if(t[e])return t[e].exports;var r=t[e]={i:e,l:!1,exports:{}};return f[e].call(r.exports,r,r.exports,l),r.l=!0,r.exports}l.m=f,l.c=t,l.d=function(e,r,t){l.o(e,r)||Object.defineProperty(e,r,{enumerable:!0,get:t})},l.r=function(e){"undefined"!=typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},l.t=function(r,e){if(1&e&&(r=l(r)),8&e)return r;if(4&e&&"object"==typeof r&&r&&r.__esModule)return r;var t=Object.create(null);if(l.r(t),Object.defineProperty(t,"default",{enumerable:!0,value:r}),2&e&&"string"!=typeof r)for(var n in r)l.d(t,n,function(e){return r[e]}.bind(null,n));return t},l.n=function(e){var r=e&&e.__esModule?function(){return e.default}:function(){return e};return l.d(r,"a",r),r},l.o=function(e,r){return Object.prototype.hasOwnProperty.call(e,r)},l.p="/";var r=window.webpackJsonpassessment=window.webpackJsonpassessment||[],n=r.push.bind(r);r.push=e,r=r.slice();for(var o=0;o<r.length;o++)e(r[o]);var s=n;i()}([])</script>
			<script src="./static/js/vendors~main.js">
			</script>
			<script src="./static/js/main.js">
			</script>
		</body>
	</html>
