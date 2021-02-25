<%@page language="java"
	import="assesment.business.*"
	import="assesment.business.administration.user.*"
	import="assesment.communication.language.*"
	import="assesment.communication.administration.user.tables.*"
	import="assesment.presentation.translator.web.user.*"
	import="assesment.communication.language.tables.*"
	import="assesment.communication.administration.corporation.tables.*"
	import="java.util.*"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>

<%
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	Text messages = sys.getText();
 	String area=request.getParameter("area");
	boolean open = (request.getParameter("window") != null);
		
	
	session.setAttribute("uid",sys.getUserSessionData().getFilter().getLoginName());

%>

<form name="home" action="/assesment/index.jsp" method="post">
	<input type="hidden" name="window" value="open">
</form>

<script>
	function openDivOrg(id) {
		window.open('./user/divOrgFilters.jsp?id='+id+'&level=1','', 'toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,width=500,height=500');	
	}
	function openDivGeo() {
		window.open('./user/divGeoFilters.jsp?level=1','', 'toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,width=500,height=500');	
	}
	function openChangePassword() {
		window.open('./user/changePassword.jsp?level=1','', 'toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,width=350,height=200');	
	}
	function openChangeLanguage() {
		window.open('./user/changeLanguage.jsp?level=1','', 'toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,width=350,height=200');	
	}
	function openChangeCorporation() {
		window.open('./user/changeCorporation.jsp?level=1','', 'toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,width=350,height=200');	
	}
</script>
	 <td width="15%" height="26" align="left" valign="middle"><center>
 		<div align="center" style="vertical-align:middle">
			<xmp b:backbase="true">
				<a b:action="trigger" b:event="open" b:target="id('win1')"><%=messages.getText("generic.messages.myinfo")%></a>
	    		<b:window id="win1" b:open='<%=String.valueOf(open)%>' style="position: absolute; width: 350px; height: 250px; top:70px; left:570px;" >
		   			<b:windowhead style=" height: 15px;">
		   				<div align="left"><%=messages.getText("generic.messages.myinfo")%></div>
					</b:windowhead>
					<b:windowbody>
				      	<p align="left"> <a b:action="js" b:event="open" b:value="openChangePassword()"><%=messages.getText("generic.user.changepassword")%></a> </p>
				      	<p align="left"> <a b:action="js" b:event="open" b:value="openChangeCorporation()"><%=messages.getText("generic.user.changecorporation")%></a> </p>
				      	<p align="left"> <a b:action="js" b:event="open" b:value="openChangeLanguage()"><%=messages.getText("generic.user.changelanguage")%></a> </p>
					</b:windowbody>
				</b:window>
		  	</xmp>	
		</div>
   </td>
