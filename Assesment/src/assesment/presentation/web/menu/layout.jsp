<%@ page language="java"
	import="assesment.business.AssesmentAccess"
	import="assesment.communication.language.Text"
	import="assesment.presentation.translator.web.util.Util"
	import="java.util.Collection"
	import="java.util.Iterator"
	import="assesment.business.administration.user.*"
	import="assesment.communication.language.*"
	import="assesment.communication.administration.user.tables.*"
	import="assesment.presentation.translator.web.user.*"
	import="assesment.communication.language.tables.*"
	import="assesment.communication.administration.user.*"
	import="assesment.communication.administration.corporation.tables.*"
	import="assesment.communication.security.*"
%>
<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>
<%!
	String Reference;	
%>
<%
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	if(sys==null){
		response.sendRedirect("../assesment/index.jsp?session=1");
	}else {	
		UserSessionData userSession=sys.getUserSessionData();
		Text messages = sys.getText();		
		Reference = request.getParameter("refer");
		if(Reference == null) {
		    Reference = (String)session.getAttribute("url");  
		}
		String check = Util.checkPermission(sys,SecurityConstants.ADMINISTRATOR);
		if(check!=null) {
			response.sendRedirect(request.getContextPath()+check);
		}else { 		
%>
<html>
	<head>
		<title>Assesment</title>
		<LINK REL=StyleSheet HREF="util/css/estilo.css" TYPE="text/css">
		<link rel="STYLESHEET" type="text/css" href="./css/menu.css"> 
		<script type="text/javascript" src="./menu/js/dropdown.js"></script>
		<script type="text/javascript" src="./menu/js/functionPreferences.js"></script>

<!-- end testing-->
	<META http-equiv="Cache-Control" content="no-cache">
	<META http-equiv="Pragma" content="no-cache">
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.contain {
width: 100%; 
height: 100%;
}
.style1 {
	font-size: 4px;
	color: #FFFFFF;
}
 -->
	</style>
	
<!-- *************************JCalendar***********************************-->
 <!-- calendar stylesheet -->
  <link rel="stylesheet" type="text/css" media="all" href="/assesment/component/jscalendar-1.0/skins/aqua/theme.css" title="win2k-cold-1" />

  <!-- main calendar program -->
  <script type="text/javascript" src="/assesment/component/jscalendar-1.0/calendar.js"></script>

  <!-- language for the calendar-->
<% if (userSession.getLenguage().equals("es")){
%><script type="text/javascript" src="/assesment/component/jscalendar-1.0/lang/calendar-es.js"></script>
<% }else if(userSession.getLenguage().equals("en")){
%><script type="text/javascript" src="/assesment/component/jscalendar-1.0/lang/calendar-en.js"></script>
<% }else{
%><script type="text/javascript" src="/assesment/component/jscalendar-1.0/lang/calendar-pt.js"></script>
<%	} %>
  <!-- the following script defines the Calendar.setup helper function, which makes
       adding a calendar a matter of 1 or 2 lines of code. -->
  <script type="text/javascript" src="/assesment/component/jscalendar-1.0/calendar-setup.js"></script>
  
  <!-- **********************end JCalendar ********************************* -->
	</head>
	<body>

		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<form name="logout" action="./logout.jsp" method="post"></form>
			<form name="home" action="./layout.jsp" method="post"></form>
			<form name="texts" action="./layout.jsp" method="post">
				<input type="hidden" name="refer" value="/user/reloadText.jsp" />
			</form>
			<tr id="top">
	  			<td height="90" colspan="2" valign="top">
	   				<table width="100%" height="70" border="0" cellpadding="0" cellspacing="0" id="topo">
						<tr>
				        	<td background="./imgs/fondo.jpg" align="left">
				        		<a href="index.jsp">
					        		<img src="./imgs/main_logo.png" height="50" valign="bottom" style="vaº">
					        	</a>
				        	</td>
				        	<td background="./imgs/fondo.jpg" align="left">
				        		<a href="index.jsp">
					        		<img src="./imgs/logo.jpg" height="69" valign="bottom">
								</a>
				        	</td>
				        	<td background="./imgs/fondo.jpg" align="left" width="30%">
				        	</td>
						</tr>
					</table>
	   				<table width="100%" height="20" border="0" cellpadding="0" cellspacing="0" id="topo">
						<tr>
							<td width="90%" style="background-color: #868686;">
								<div id="header">
									<ul class="nav">
<%		if(userSession.checkPermission(SecurityConstants.ADMINISTRATOR)) {	
%>										<li><a href="layout.jsp?refer=/corporation/list.jsp"><%=messages.getText("generic.data.corporation")%></a></li>
										<li><a href=""><%=messages.getText("generic.data.company")%></a>
											<ul>
												<li><a href="layout.jsp?refer=/corporation/cediList.jsp"><%=messages.getText("generic.data.cedi")%></a></li>
												<li><a href="layout.jsp?refer=/corporation/mutualCompanyList.jsp"><%=messages.getText("generic.data.mutualcompany")%></a></li>
											</ul>
										</li>
										<li><a href="layout.jsp?refer=/assesment/groupList.jsp"><%=messages.getText("system.data.groups")%></a></li>
										<li><a href=""><%=messages.getText("generic.data.assesment")%></a>
											<ul>
												<li><a href="layout.jsp?refer=/assesment/list.jsp"><%=messages.getText("generic.actives")%></a></li>
												<li><a href="layout.jsp?refer=/assesment/listbkp.jsp"><%=messages.getText("generic.backedup")%></a></li>
											</ul>
										</li>
										<li><a href=""><%=messages.getText("generic.data.webinar")%></a>
											<ul>
												<li><a href="layout.jsp?refer=/assesment/webinarList.jsp"><%=messages.getText("generic.messages.course")%></a></li>
												<li><a href="layout.jsp?refer=/assesment/webinarUsers.jsp"><%=messages.getText("generic.data.users")%></a></li>
												<li><a href="layout.jsp?refer=/assesment/webinarErrors.jsp"><%=messages.getText("generic.messages.errors")%></a></li>
											</ul>
										</li>
										<li><a href=""><%=messages.getText("generic.data.users")%></a>
											<ul>
												<li><a href="layout.jsp?refer=/user/list.jsp"><%=messages.getText("generic.messages.management")%></a></li>
												<li><a href="layout.jsp?refer=/user/accesscodelist.jsp"><%=messages.getText("user.accesscodes")%></a></li>
												<li><a href="layout.jsp?refer=/user/createfromfile.jsp"><%=messages.getText("user.fromfile")%></a></li>
												<li><a href="layout.jsp?refer=/user/createtimacusers.jsp"><%=messages.getText("user.timcafile")%></a></li>
											</ul>
										</li>
										<li><a href=""><%=messages.getText("generic.data.reports")%></a>
											<ul>
												<li><a href="layout.jsp?refer=/report/assesment.jsp"><%=messages.getText("generic.data.assesment")%></a></li>
												<li><a href="layout.jsp?refer=/report/users.jsp"><%=messages.getText("generic.data.users")%></a></li>
												<li><a href="layout.jsp?refer=/report/java.jsp">JAVA</a></li>
												<li><a href=""><%=messages.getText("Generales")%></a>
													<ul>
														<li style="left: 60px;"><a href="layout.jsp?refer=/report/results.jsp"><%=messages.getText("generic.results.all")%></a></li>
														<li style="left: 60px;"><a href="layout.jsp?refer=/report/advance.jsp"><%=messages.getText("generic.results.advance")%></a></li>
														<li style="left: 60px;"><a href="layout.jsp?refer=/report/moduleresults.jsp"><%=messages.getText("generic.question.module.results")%></a></li>
														<li style="left: 60px;"><a href="layout.jsp?refer=/report/ranking.jsp"><%=messages.getText("generic.question.ranking")%></a></li>
														<li style="left: 60px;"><a href="layout.jsp?refer=/report/questionresults.jsp"><%=messages.getText("generic.question.results")%></a></li>
													</ul>
												</li>
												<li><a href=""><%=messages.getText("Usuario")%></a>
													<ul>
														<li style="left: 60px;"><a href="layout.jsp?refer=/report/userresults.jsp"><%=messages.getText("generic.results.all")%></a></li>
														<li style="left: 60px;"><a href="layout.jsp?refer=/report/usererrors.jsp"><%=messages.getText("generic.results.error")%></a></li>
														<li style="left: 60px;"><a href="layout.jsp?refer=/report/userpsiresults.jsp"><%=messages.getText("driver.result.psi")%></a></li>
														<li style="left: 60px;"><a href="layout.jsp?refer=/report/userpersonaldataresults.jsp"><%=messages.getText("driver.result.personaldata")%></a></li>
														<li style="left: 60px;"><a href="layout.jsp?refer=/report/newhire.jsp"><%=messages.getText("generic.results.newhire")%></a></li>
													</ul>
												</li>
											</ul>
										</li>
<%			}
%>
										<li><a href=""><%=messages.getText("generic.messages.preferences")%></a>
											<ul>
												<li><a href="javascript:openChangePassword(500,400)"><%=messages.getText("generic.user.changepassword")%></a></li>
												<li><a href="javascript:openChangeLanguage()"><%=messages.getText("generic.user.changelanguage")%></a></li>
												<li><a href="javascript:document.forms['texts'].submit();"><%=messages.getText("generic.messages.updatetexts")%></a></li>
											</ul>
										</li>							
									</ul>
								</div>
							</td>
							<td width="10%" style="background-color: #868686; padding-right: 10px;" >
								<a href="javascript:document.forms['logout'].submit()">
									<span style="color:#b92a2a; text-decoration:none;font-family:Roboto,Verdana,Arial, Helvetica, sans-serif;"><b><%=messages.getText("generic.messages.logout")%></b></span>
								</a>
							</td>
					  	</tr>
					</table>
				</td>
			</tr>
			<tr style="height: 25px;">
				<td>&nbsp;</td>
			</tr>
  			<tr>
    			<td valign="top">
    				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" valign="top">
      					<tr>
        					<td>
<%					if(!Util.empty(Reference)) {
%>        						<jsp:include page='<%= Reference %>'/>
<%					}
%>        					</td>
      					</tr>
    				</table>
    			</td>
  			</tr>
		</table>
	</body>
</html>
<%		}
	}
%>