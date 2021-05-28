<%@page import="assesment.presentation.translator.web.util.Util"%>
<%@page import="assesment.business.administration.user.UsReportFacade"%>
<%@page import="assesment.communication.assesment.CategoryData"%>
<%@page import="assesment.communication.assesment.GroupData"%>
<%@page import="assesment.communication.user.UserData"%>
<%@page import="java.util.*"%>
<%@page import="assesment.communication.util.MD5"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="assesment.persistence.user.tables.UserAssesmentData"%>
<%@page import="assesment.communication.assesment.AssesmentAttributes"%>
<%@page import="assesment.communication.assesment.AssesmentData"%>
<%@page import="assesment.communication.administration.user.UserSessionData"%>
<%@ page language="java"
	import="assesment.business.*"	
	import="assesment.communication.language.*"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html" 
%>

<%@ taglib uri="/WEB-INF/struts-bean.tld"
        prefix="bean" 
%>


<%	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	Text messages = sys.getText();
	UserSessionData userSessionData = sys.getUserSessionData();

	Calendar c = Calendar.getInstance();
	UsReportFacade report = sys.getUserReportFacade();
	GroupData group = sys.getAssesmentReportFacade().getUserGroup(userSessionData.getFilter().getLoginName(),userSessionData);
	int gId = group.getId().intValue();
	if(gId == GroupData.MERCADOLIBRE || gId == GroupData.MERCADOLIVRE) {
		response.sendRedirect("mercadolibre.jsp");
	}else {
		String login = userSessionData.getFilter().getLoginName();
		UserData userData = report.findUserByPrimaryKey(login,userSessionData);
		HashMap<Integer, Date> map = report.getUserAssessmentStatus(login,userSessionData);
	
		String style = "./static/css/main.css";
		if(userSessionData.isDidiGroup()) {
			style = "./static/css/mainDidi.css";
		}

%>


<!doctype html>

<html lang="en">
  	<head>
    	<meta charset="utf-8"/>
	    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
	    <link rel="shortcut icon" href="favicon.ico"/>
	    <meta name="viewport" content="width=device-width,initial-scale=1"/>
	    <meta name="theme-color" content="#405959"/>
	    <title>Driver Assessment</title>
	
	    <link rel="manifest" href="manifest.json"/>
	    <link href="https://fonts.googleapis.com/css?family=Roboto:400,400i,500,500i&display=swap" rel="stylesheet">
	    <link href="https://fonts.googleapis.com/css?family=Roboto+Condensed:700&display=swap" rel="stylesheet">
	    <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
	    <script src="static/js/jquery-3.4.1.min.js" integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"></script>
	    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js" integrity="sha384-xrRywqdh3PHs8keKZN+8zzc5TX0GRTLCcmivcbNJWm2rs5C8PRhcEn3czEjhAO9o" crossorigin="anonymous"></script>
	    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
	    <!--  link href="./static/css/main.css" rel="stylesheet"-->
	    <link href="<%=style%>" rel="stylesheet">
		<script type="text/javascript">
			function enterDA(assessmentId) {
				var form = document.forms['SelectAssessmentForm'];
				form.assessment.value = assessmentId;
				form.submit();
			}
		</script>
		<style type="text/css">
			.lined1:after {
			    content: "";
			    display: block;
			    height: 1px;
			    width: 60%;
			    margin: .5rem 0;
			    background-color: white;
			}
		</style>
	</head>
		
  	</head>
  	<body class="home">
		<html:form action="/SelectAssessment">
			<html:hidden property="assessment"/>
		</html:form>
	    <noscript>You need to enable JavaScript to run this app.</noscript>
    	<nav class="navbar bg-transparent-light fixed-top navbar-light shadow-sm">
      		<div class="container">
        		<div class="navbar-menu dropdown">
          			<a href="#" id="navbar-menu-toggle" role="button" data-toggle="dropdown" aria-haspopup="true"></a>
          			<div class="dropdown-menu">
			            <a class="dropdown-item" href="#inicio"><%=messages.getText("assesment.data.start")%></a>
<% 		Iterator<CategoryData> it = group.getOrderedCategories();
		while(it.hasNext()) {
			CategoryData category = it.next();
			String href = "#category_"+category.getId();
%>			            <a class="dropdown-item" href="<%=href%>"><%=messages.getText(category.getKey())%></a>
<%		}
%>			            <a class="dropdown-item" href="logout.jsp"><%=messages.getText("generic.messages.logout") %></a>
          			</div>
        		</div>
        		</ul>
        		<div class="navbar-brand">
          			<!-- img src="static/images/logo.png" width="75" height="80" alt=""  -->
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
    	<main class="container main-content">
	      	<section id="inicio" class="row bg-image" <%=(group.getImage() != null) ?  "style='background-image: url(./flash/images/"+group.getImage()+");'" : ""%>>
        		<div class="col text">
<%		if(gId != GroupData.GALDERMA && gId != GroupData.DAIMLER && gId != GroupData.FMB_MENTORIA && !userSessionData.isDidiGroup()) {
			if(gId == GroupData.ACHE) {

%> 		  			<h4>
						<span style="color:#CF035C; text-shadow: 0 0 0 #CF035C;"><%=messages.getText("generic.messages.welcome")+" "+userData.getFirstName()+","%></span>
					</h4>
          			<h1>
          				<span style="color:#CF035C; text-shadow: 0 0 0 #CF035C;"><%=(group.isInitialText()) ? messages.getText("assessment.group.id"+group.getId()) : "Driver Assessment"%></span>
          			</h1>
<%			} else {
%> 		  			<h4><%=messages.getText("generic.messages.welcome")+" "+userData.getFirstName()+","%></h4>
          			<h1>
          				<%=(group.isInitialText()) ? messages.getText("assessment.group.id"+group.getId()) : "Driver Assessment"%>
          			</h1>
<%			} 
		}
%>				</div>
      		</section>
<%		it = group.getOrderedCategories();
		while(it.hasNext()) {
			CategoryData category = it.next();
			String href = "category_"+category.getId();
			String titleColor = (category.getTitleColor().intValue() == 1) ? "#405959" : "#FFFFFF";
			String itemColor = (category.getItemColor().intValue() == 1) ? "#405959" : "#FFFFFF";
%>        	<section id="<%=href%>" class="row" <%=(category.getImage() != null) ?  "style='background-image: url(./flash/images/"+category.getImage()+");'" : ""%>>
        		<div class="col">
          			<h2 style='color: <%=titleColor%>;'><%=messages.getText(category.getKey())%></h2>
          			<div class="row justify-content-center">
            			<div class="thumbs-slider card-deck col-10">
<% 				Iterator<AssesmentAttributes> it2 = category.getOrderedAssesments();
				while(it2.hasNext()) {
					Calendar today = Calendar.getInstance();
					AssesmentAttributes assessment = (AssesmentAttributes) it2.next();
					Calendar start = Calendar.getInstance();
					start.setTime(assessment.getStart());
					String link = assessment.getAssessmentLink(userData);
					String target = "";
					String txt = "";
					String color = "";
					if(link.startsWith("./newmodule.jsp")) {
						if(gId == GroupData.FMB_MENTORIA) {
							if(map.containsKey(assessment.getId())) {
								if(map.get(assessment.getId()) != null) {
									link += "&delete=1";	
									txt = messages.getText("generic.assessment.repeat").toUpperCase();
									color = "card badge-darkred";
								}else {
									txt = messages.getText("corporation.message.new").toUpperCase();
									color = "card badge-green";
								}
							}
						} else {
							if(start.after(today)) {
								link = "javascript:alert('"+messages.getText("generic.messages.soonavailable")+"')";
								txt = messages.getText("generic.messages.soon").toUpperCase();
								color = "card badge-orange";
							} else if(map.containsKey(assessment.getId())) {
								if(map.get(assessment.getId()) != null) {
									if((group.getId().equals(GroupData.GRUPO_MODELO) && assessment.isGMEvaluacion())
											|| ((assessment.getId() == AssesmentData.MONDELEZ_DA || assessment.getId() == AssesmentData.MONDELEZ_DA_V2) && (gId == GroupData.MONDELEZ_LIDERES || gId == GroupData.MONDELEZ_PROVISIONALDRIVERS || gId == GroupData.MONDELEZ_NEWDRIVERS))
											|| (assessment.getId() == AssesmentData.KOF_ASESORES || assessment.getId() == AssesmentData.KOF_COORDINADORES)) {
										boolean repeat = !sys.getUserReportFacade().isResultGreen(userData.getLoginName(), assessment.getId(), userSessionData);
										if(repeat) {
											repeat = sys.getUserReportFacade().getFailedAssesments(userSessionData.getFilter().getLoginName(), assessment.getId(), userSessionData) == 0;
										}
										if(repeat) {
											link += "&delete=1";	
											txt = messages.getText("generic.assessment.repeat").toUpperCase();
											color = "card badge-darkred";
										} else {
											link = "javascript:alert('"+messages.getText("generic.messages.complete")+"')";
											txt = messages.getText("generic.messages.complete").toUpperCase();
											color = "card badge-blue";
										}
									}else {
										if(assessment.isUntilApproved() || group.isRepeatable()) {
											if(sys.getUserReportFacade().isResultGreen(userData.getLoginName(), assessment.getId(), userSessionData)) {
												link = "javascript:alert('"+messages.getText("generic.messages.complete")+"')";
												txt = messages.getText("generic.messages.complete").toUpperCase();
												color = "card badge-blue";
											} else {
												link += "&delete=1";	
												txt = messages.getText("generic.assessment.repeat").toUpperCase();
												color = "card badge-darkred";
											}
										} else {
											link = "javascript:alert('"+messages.getText("generic.messages.complete")+"')";
											txt = messages.getText("generic.messages.complete").toUpperCase();
											color = "card badge-blue";
										}
									}
								}else {
									String dependency = report.checkDependencies(login, assessment.getId(), userSessionData);
									if(dependency == null) {
										today.add(Calendar.DATE, -14);
										if(start.after(today)) {
											txt = messages.getText("corporation.message.new").toUpperCase();
											color = "card badge-green";
										} else {
											txt = messages.getText("generic.report.pending").toUpperCase();
											color = "card badge-red";
										}
									}else {
										link = "javascript:alert('"+messages.getText(dependency)+"')";
										txt = messages.getText("generic.messages.notavailable").toUpperCase();
										color = "card badge-darkred";
									}
								}
							} else {
								today.add(Calendar.DATE, -14);
								if(start.after(today)) {
									txt = messages.getText("corporation.message.new").toUpperCase();
									color = "card badge-green";
								} else {
									txt = messages.getText("generic.report.pending").toUpperCase();
									color = "card badge-red";
								}
							}
						}
					}else {
						if(start.after(today)) {
							link = "javascript:alert('"+messages.getText("generic.messages.soonavailable")+"')";
							txt = messages.getText("generic.messages.soon").toUpperCase();
							color = "card badge-orange";
						} else { 
							today.add(Calendar.DATE, -14);
							link = messages.getText(link);
							if(start.after(today)) {
								txt = messages.getText("corporation.message.new").toUpperCase();
								color = "card badge-green";
							} else {
								txt = messages.getText("generic.messages.available").toUpperCase();
								color = "card badge-agreen";
							}
							if(!link.startsWith("./uploadmodule.jsp"))
								target = "target='blank'";
						}
					}
					if(group.getId().equals(GroupData.SUMITOMO) && link.startsWith("./newmodule.jsp")) {
						link = "javascript:enterDA("+assessment.getId()+")";
					}
					switch(category.getType().intValue()) {
						case 1:
%>							<div class="<%=color%>" data-badge="<%=txt%>">
								<a class="card-image-link icon-play-container" href="<%=link%>" <%=target%>>
									<img src='<%="flash/images/icon_"+assessment.getId()+".png"%>' class="card-img-top">
								</a>
								<div class="card-body">
									<h3 style='color: <%=itemColor%>;'><%=messages.getText(assessment.getName())%></h3>
								</div>
							</div>
<%							break;
						case 2:
%>  	            		<div class="slider-item">
	        					<a href="<%=link%>" class="circle d-block <%=color%>" data-badge="<%=txt%>" <%=target%>>
    	          					<div class="circle__content">
        	        					<div class="circle-icon">
            	      						<img src='<%="flash/images/icon_"+assessment.getId()+".png"%>'>
                						</div>
                						<h3 style='color: <%=itemColor%>;'><%=messages.getText(assessment.getName())%></h3>
              						</div>
        						</a>
      						</div>
<%							break;
						case 3:
%>							<div class="slider-item">
								<a href="<%=link%>" class="icon-play-white border-right-white d-block <%=color%>" data-badge="<%=txt%>" <%=target%>>
									<div class="icon-wheel-wrapper" style="height: 90px;">
										<i class="icon-wheel" style="height: 90px;"></i>
									</div>
									<h3 class="strong" style='color: <%=itemColor%>;'><%=messages.getText(assessment.getName())%></h3>
								</a>
							</div>
<%							break;
					}
				}
%>              
						</div>
            		</div>
<%				if(!it.hasNext()) {
%>		          	<footer class="row">
		            	<div class="social-links col text-left">
		              		<a class="social-link social-instagram" href="#">Instagram</a>
		              		<a class="social-link social-facebook" href="#">Facebook</a>
		              		<a class="social-link social-linkedin" href="#">LinkedIn</a>
		              		<a class="social-link social-youtube" href="#">YouTube</a>
		            	</div>
		            	<div class="brand col text-right">
		              		<a href="#" class="cepa-link">CEPA</a>
		            	</div>
		          	</footer>
<%				}
%>        		</div>
      		</section>
<%		}
%>      </main>
		<script>!function(f){function e(e){for(var r,t,n=e[0],o=e[1],u=e[2],l=0,a=[];l<n.length;l++)t=n[l],Object.prototype.hasOwnProperty.call(p,t)&&p[t]&&a.push(p[t][0]),p[t]=0;for(r in o)Object.prototype.hasOwnProperty.call(o,r)&&(f[r]=o[r]);for(s&&s(e);a.length;)a.shift()();return c.push.apply(c,u||[]),i()}function i(){for(var e,r=0;r<c.length;r++){for(var t=c[r],n=!0,o=1;o<t.length;o++){var u=t[o];0!==p[u]&&(n=!1)}n&&(c.splice(r--,1),e=l(l.s=t[0]))}return e}var t={},p={1:0},c=[];function l(e){if(t[e])return t[e].exports;var r=t[e]={i:e,l:!1,exports:{}};return f[e].call(r.exports,r,r.exports,l),r.l=!0,r.exports}l.m=f,l.c=t,l.d=function(e,r,t){l.o(e,r)||Object.defineProperty(e,r,{enumerable:!0,get:t})},l.r=function(e){"undefined"!=typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},l.t=function(r,e){if(1&e&&(r=l(r)),8&e)return r;if(4&e&&"object"==typeof r&&r&&r.__esModule)return r;var t=Object.create(null);if(l.r(t),Object.defineProperty(t,"default",{enumerable:!0,value:r}),2&e&&"string"!=typeof r)for(var n in r)l.d(t,n,function(e){return r[e]}.bind(null,n));return t},l.n=function(e){var r=e&&e.__esModule?function(){return e.default}:function(){return e};return l.d(r,"a",r),r},l.o=function(e,r){return Object.prototype.hasOwnProperty.call(e,r)},l.p="/";var r=window.webpackJsonpassessment=window.webpackJsonpassessment||[],n=r.push.bind(r);r.push=e,r=r.slice();for(var o=0;o<r.length;o++)e(r[o]);var s=n;i()}([])</script>
		<script src="./static/js/vendors~main.js">
		</script>
		<script src="./static/js/main.js">
		</script>
  	</body>
</html>

<%	}
%>