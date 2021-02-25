<%@page language="java"
	import="assesment.business.*"
	import="assesment.communication.language.*"
	import="assesment.communication.administration.user.*"
	import="assesment.communication.security.*"
%>

<%! String context;%>
<%
	context=request.getContextPath();
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	if(sys==null){
		response.sendRedirect("../login.jsp");
	}
	Text messages = sys.getText(); 
	UserSessionData userSession=sys.getUserSessionData(); 
%>
<html>
<head>
<link rel="STYLESHEET" type="text/css" href="./css/menu.css"> 
<script language="JavaScript" src="./js/slidemenu.js" type="text/javascript"></script>
<script language="JavaScript" src="./js/menu_config.js" type="text/javascript"></script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
 -->
</style>
</head>
<body>
	<table align="left" width="150" valign="top">
		<tr>
			<td>
				<script>
<%		if(userSession.checkPermission(SecurityConstants.ADMINISTRATOR)) {	
%>			CEPAMenu.makeMenu('top','<%=messages.getText("generic.data.corporation")%>','layout.jsp?refer=/corporation/list.jsp','_parent')
			CEPAMenu.makeMenu('top','<%=messages.getText("system.data.groups")%>','layout.jsp?refer=/assesment/groupList.jsp','_parent')
			CEPAMenu.makeMenu('top','<%=messages.getText("generic.data.assesment")%>','layout.jsp?refer=/assesment/list.jsp','_parent')
			CEPAMenu.makeMenu('top','<%=messages.getText("generic.data.users")%>','#')
				CEPAMenu.makeMenu('sub','<%=messages.getText("generic.messages.management")%>','layout.jsp?refer=/user/list.jsp','_parent')
				CEPAMenu.makeMenu('sub','<%=messages.getText("generic.messages.createfromdc")%>','layout.jsp?refer=/user/createfromdc.jsp','_parent')
				CEPAMenu.makeMenu('sub','<%=messages.getText("user.accesscodes")%>','layout.jsp?refer=/user/accesscodelist.jsp','_parent')
				CEPAMenu.makeMenu('sub','<%=messages.getText("user.fromfile")%>','layout.jsp?refer=/user/createfromfile.jsp','_parent')
			CEPAMenu.makeMenu('top','<%=messages.getText("generic.data.genericmodules")%>','layout.jsp?refer=/module/genericlist.jsp','_parent')
			CEPAMenu.makeMenu('top','<%=messages.getText("generic.data.reports")%>','#')
 				CEPAMenu.makeMenu('sub','<%=messages.getText("generic.data.assesment")%>','layout.jsp?refer=/report/assesment.jsp','_parent')
 				CEPAMenu.makeMenu('sub','<%=messages.getText("generic.data.users")%>','layout.jsp?refer=/report/users.jsp','_parent')
				CEPAMenu.makeMenu('sub','<%=messages.getText("Generales")%>','#')
 					CEPAMenu.makeMenu('sub2','<%=messages.getText("generic.results.all")%>','layout.jsp?refer=/report/results.jsp','_parent')
 					CEPAMenu.makeMenu('sub2','<%=messages.getText("generic.results.advance")%>','layout.jsp?refer=/report/advance.jsp','_parent')
 					CEPAMenu.makeMenu('sub2','<%=messages.getText("generic.question.module.results")%>','layout.jsp?refer=/report/moduleresults.jsp','_parent')
 					CEPAMenu.makeMenu('sub2','<%=messages.getText("generic.question.ranking")%>','layout.jsp?refer=/report/ranking.jsp','_parent')
 					CEPAMenu.makeMenu('sub2','<%=messages.getText("generic.question.results")%>','layout.jsp?refer=/report/questionresults.jsp','_parent')
				CEPAMenu.makeMenu('sub','<%=messages.getText("Usuario")%>','#')
	 				CEPAMenu.makeMenu('sub2','<%=messages.getText("generic.results.all")%>','layout.jsp?refer=/report/userresults.jsp','_parent')
	 				CEPAMenu.makeMenu('sub2','<%=messages.getText("generic.results.error")%>','layout.jsp?refer=/report/usererrors.jsp','_parent')
	 				CEPAMenu.makeMenu('sub2','<%=messages.getText("driver.result.psi")%>','layout.jsp?refer=/report/userpsiresults.jsp','_parent')
	 				CEPAMenu.makeMenu('sub2','<%=messages.getText("driver.result.personaldata")%>','layout.jsp?refer=/report/userpersonaldataresults.jsp','_parent')
	 				CEPAMenu.makeMenu('sub2','<%=messages.getText("generic.results.newhire")%>','layout.jsp?refer=/report/newhire.jsp','_parent')
 				CEPAMenu.makeMenu('sub','<%=messages.getText("generic.report.java")%>','layout.jsp?refer=/report/java.jsp','_parent')
<%		}
%> 		
		CEPAMenu.init()
				</script>
			</td>
		</tr>
	</table>
</body>
</html>
