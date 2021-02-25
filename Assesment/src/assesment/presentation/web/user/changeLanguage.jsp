<%@page language="java" 
	import="assesment.business.*"
	import="assesment.business.administration.user.*" 
	import="assesment.business.administration.corporation.*" 
	import="assesment.communication.language.*"
	import="assesment.communication.language.tables.*"
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
	int level;String currentLanguage; Collection languages;
%>
<%
	
	pathMsg="/util/jsp/message.jsp";
	
	dispatcher=request.getRequestDispatcher(pathMsg);
	dispatcher.include(request,response);
	
	AssesmentAccess sys=(AssesmentAccess)session.getAttribute("AssesmentAccess");
	messages=sys.getText();

	level=Integer.parseInt((String)request.getParameter("level"));

	currentLanguage = sys.getUserSessionData().getLenguage();
	languages = sys.getLanguageReportFacade().findAllLanguage(sys.getUserSessionData());
	Iterator it = languages.iterator();
	Collection optionLanguages = new LinkedList();
	while(it.hasNext()) {
		LanguageData lang = (LanguageData)it.next();
		if(!lang.getName().equals(currentLanguage)) {
			optionLanguages.add(new OptionItem(messages.getText(lang.getName()),lang.getName()));
		}
	}
	session.setAttribute("optionLanguages",optionLanguages);
%>

<LINK REL=StyleSheet HREF="../util/css/estilo.css" TYPE="text/css">

<style type="text/css">
<!--
body {
	background-color: #F7F7F7;
}
-->
</style><head/>

<script>
	function init(level) {
		if(level == -1){
			self.opener.document.forms['home'].submit();
			window.close();
		}
	}
</script>
	<html:form action="/Switchlanguageuser" >
	  <body onLoad="init(<%=level%>)">
		  <table width='230' height="200" align='center' cellpadding="0" cellspacing="0" bgcolor="#F7F7F7">
			  <tr><td height="10">&nbsp;</td><td height="10">&nbsp;</td></tr>
				<tr class="lineone">
					<td height="30" align="left"><b><%=messages.getText("generic.user.currentlanguage")%></b></td>
					<td height="30" align="left"><%=messages.getText(currentLanguage)%></td>
				</tr>
				<tr><td height="10">&nbsp;</td><td height="10">&nbsp;</td></tr>
				<tr class="lineone">
					<td height="30" align="left"><b><%=messages.getText("generic.messages.changeto")%></b></td>
					<td height="30" align="left">
						<html:select property="language" styleClass="input">
			          		<html:options collection="optionLanguages" property="value" labelProperty="label" styleClass="input" />
						</html:select>
					</td>
				</tr>
				<tr><td height="10">&nbsp;</td><td height="10">&nbsp;</td></tr>
				<tr>	
					<td colspan="2">
						<div align="center">
							<html:submit value='<%=messages.getText("generic.messages.accept")%>' styleClass="input" />				    		  		
							<input type="button" value='<%=messages.getText("generic.messages.cancel")%>' onClick="javascript:window.close()" class="input"/>				    		  		
						</div>
					</td>
				</tr>
	  </table>		
	  </body>
	</html:form>	
</html:html>