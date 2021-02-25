<%@page language="java"
	import="assesment.business.*"
	import="assesment.business.administration.user.*"
	import="assesment.communication.assesment.*"
	import="assesment.communication.language.*"
	import="assesment.communication.security.*"
	import="assesment.communication.administration.user.*"
	import="assesment.communication.module.*"
	import="java.util.*"
%>
<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>

<%
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess"); 
 	UserSessionData userSession=sys.getUserSessionData(); 
 	Text messages=sys.getText();
 	
 	if(userSession.getFilter().getAssesment() == null) {
 	    response.sendRedirect("noassesment.jsp");
 	}else {
	 	AssesmentData assesment = sys.getAssesmentReportFacade().findAssesment(userSession.getFilter().getAssesment(),userSession);
 		Collection userModule = sys.getUserReportFacade().getUserModules(userSession.getFilter().getLoginName(),userSession.getFilter().getAssesment(),userSession,assesment.isInstantFeedback());
%>


<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<LINK REL=StyleSheet HREF="util/css/estilo.css" TYPE="text/css">
		<link rel="STYLESHEET" type="text/css" href="./css/menu.css"> 
		<script type="text/javascript" src="./menu/js/dropdown.js"></script>
		<script language="JavaScript" src="./menu/js/refactor.js" type="text/javascript"></script>
 		<title>Assesment</title>
		<META http-equiv="Cache-Control" content="no-cache">
		<META http-equiv="Pragma" content="no-cache">
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
		 -->
		</style>
	</head>
	<body scroll="auto">
 		<table width="100%" height="90" border="0" cellpadding="0" cellspacing="0" class="default">
 	 		<form name="logout" action="./logout.jsp" method="post"></form>
			<tr id="top">
    			<td height="90" colspan="3" valign="top" >
    				<table width="100%" height="90" border="0" cellpadding="0" cellspacing="0" id="topo">
  						<tr>
				        	<td background="./imgs/top_bg2.gif" width="100%" colspan="3">
				        		&nbsp;
				        	</td>
  						</tr>
  						<tr>
						    <td width="4%" class="bgTop">
						    	<div id="menu_parent" align="center"><span class="style2"><%=messages.getText("generic.messages.preferences")%></span></div></td>
								<div id="menu_child" style="position: absolute; background: #e2e1e1; visibility: hidden;">
									<a href="javascript:openChangePassword(500,400)" style="display: block; width: 150px; height:25; text-align:center; vertical-align:middle;"  class="clA0"><%=messages.getText("generic.user.changepassword")%></a>
									<a style="display: block; width: 150px; height:25; text-align:center; vertical-align:middle;"  class="clA0" href="javascript:openChangeLanguage()"><%=messages.getText("generic.user.changelanguage")%></a>
								</div>
								<script type="text/javascript">
									at_attach("menu_parent", "menu_child", "hover", "y", "pointer");
								</script>
		    				<td width="35%" align="right" class="bgTop"><a href="#"   onClick="javascript:document.forms[0].submit()"><span class="style2" ><%=messages.getText("generic.messages.logout")%></span></a></td>
		    				<td width="2%" align="right" class="bgTop"><span class="style2" >&nbsp;</span></td>
					  	</tr>
					</table>
			  	</td>
			</tr>
	      	<tr>
				<jsp:include  page="/component/spaceline.jsp" />
	  		</tr>
  			<tr>
    			<td valign="middle" align="center" width="20%">&nbsp;</td>
    			<td valign="middle" align="center" width="60%" >
					<jsp:include  page='<%="/component/utilityboxtop.jsp?title="+messages.getText("assesment.modules")%>' />
					<table width="80%" cellpadding="0" cellspacing="0">
<%	Iterator it = assesment.getModuleIterator();
	while(it.hasNext()) {
	    ModuleData module = (ModuleData)it.next();
%>						<tr>
        					<td align="left" class="menu"> 
<%		if(!userModule.contains(module.getId())) {
%>      						<a href='<%="./answer.jsp?module="+String.valueOf(module.getId())%>'>
									<%=messages.getText(module.getKey())%>
								</a>  					
<%		}else {
%>								<%=messages.getText(module.getKey())%>
<%		}
%>							</td>
      					</tr>
<%	}
	if(assesment.isPsitest()) {
%>						<tr>
							<td align="left" class="menu"> 
<%		if(!sys.getUserReportFacade().isPsicologicDone(userSession.getFilter().getLoginName(),userSession.getFilter().getAssesment(),userSession)) {
%>      						<a href='<%="./answer.jsp?module="+String.valueOf(ModuleData.PSICO)%>'>
									<%=messages.getText("assesment.module.psicologic")%>
								</a>  					
<%		}else {
%>									<%=messages.getText("assesment.module.psicologic")%>
<%		}
%>							</td>
		</tr>
<%	}
%>   				</table>
					<jsp:include  page="/component/utilityboxbottom.jsp" />
    			</td>
    			<td valign="middle" align="center" width="20%">&nbsp;</td>
  			</tr>
		</table>
	</body>
</html>
<%	}
%>