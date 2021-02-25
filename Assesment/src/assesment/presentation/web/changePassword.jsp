<%@page language="java" 
	import="assesment.business.*"
	import="assesment.business.administration.user.*" 
	import="assesment.business.administration.corporation.*" 
	import="assesment.communication.language.*"
	import="assesment.communication.administration.user.tables.*"
	import="assesment.communication.administration.corporation.tables.*"
	import="assesment.presentation.translator.web.user.*"
	import="assesment.presentation.translator.web.util.*"
	import="java.util.*"
	import="org.apache.struts.action.*"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>

<html:html>	   

<%! RequestDispatcher dispatcher; String pathMsg;
	Text messages; String pathLanguage;
	int level;
%>
<%
		
	AssesmentAccess sys=(AssesmentAccess)session.getAttribute("AssesmentAccess");
	messages=sys.getText();

	level=Integer.parseInt((String)request.getParameter("level"));
%>

<LINK REL=StyleSheet HREF="./util/css/estilo.css" TYPE="text/css">
<style type="text/css">
<!--
body {
	background-color: #F7F7F7;
}
-->
</style>

<script>
	function fechar_janela() {
		window.close();
		
	}
</script>





<head/>
	<title><%=messages.getText("web.user.resetpassword.resetpassword")%></title>


	<html:form action="/ResetPasswordUser" >
	
  		<body>
	  		<table width='230' height="200" align='center' cellpadding="0" cellspacing="0" bgcolor="#F7F7F7">
<%			if(level == -1){
%>    			<tr>
					<th colspan="2" align="center"><%=messages.getText("generic.user.userdata.changepassword.success")%></th>
				</tr>
				<tr class="lineone">
					<td>
						<div align='center'>
							<input name='<%=messages.getText("generic.messages.accept")%>' type="button"  value='<%=messages.getText("generic.messages.accept")%>' onClick="fechar_janela()" class="input">									    		  		
						</div>	
					</td>										
				</tr>
<%			}else {
				if(session.getAttribute("Msg") != null){
%>    			<tr>
		  			<td width="10%" height="10">&nbsp;</td>
					<td colspan="2" align="center" class="red"><%=messages.getText((String)session.getAttribute("Msg"))%></td>
		  			<td width="10%" height="10">&nbsp;</td>
				</tr>
<%					session.removeAttribute("Msg");
				}
%>				  		
		  		<tr>
		  			<td width="10%" height="10">&nbsp;</td>
		  			<td width="40%" height="10">&nbsp;</td>
		  			<td width="40%" height="10">&nbsp;</td>
		  			<td width="10%" height="10">&nbsp;</td>
		  		</tr>
				<tr class="lineone">
		  			<td width="10%" height="10">&nbsp;</td>
					<td height="30" align="left"><b><%=messages.getText("generic.user.userdata.pass")%></b></td>
					<td height="30" align="left"><html:password property="password" styleClass="input" /></td>
		  			<td width="10%" height="10">&nbsp;</td>
				</tr>
				<tr><td height="10">&nbsp;</td><td height="10">&nbsp;</td></tr>
				<tr class="lineone">
		  			<td width="10%" height="10">&nbsp;</td>
					<td height="30" align="left"><b><%=messages.getText("web.user.create.confirmpassword")%></b></td>
					<td height="30" align="left"><html:password property="rePassword" styleClass="input"/> </td>										
		  			<td width="10%" height="10">&nbsp;</td>
				</tr>
				<tr><td height="10">&nbsp;</td><td height="10">&nbsp;</td></tr>
				<tr class="lineone">
		  			<td width="10%" height="10">&nbsp;</td>
					<td colspan="2">
						<div align='center'>
							<html:submit value='<%=messages.getText("generic.messages.accept")%>'styleClass="input" /> 
							<input name='<%=messages.getText("generic.messages.close")%>' type="button"  value='<%=messages.getText("generic.messages.cancel")%>' onClick="fechar_janela()" class="input">									    		  		
						</div>	
					</td>					 					
		  			<td width="10%" height="10">&nbsp;</td>
				</tr>
<%			}
%>			</table>
  		</body>
	</html:form>	
</html:html>