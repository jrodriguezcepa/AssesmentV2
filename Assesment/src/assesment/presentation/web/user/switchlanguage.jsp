<%@page language="java"
	errorPage="../exception.jsp"
	import="assesment.communication.language.*"
	import="assesment.business.AssesmentAccess"
	import="assesment.business.language.LangReportFacade"
	import="java.util.*"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>


<%!
	AssesmentAccess sys; Text messages; RequestDispatcher dispatcher; String pathLanguage; 
	LangReportFacade lngReport; String lng; String target; String pathFindAllLanguage;
%>

<%
	sys=(AssesmentAccess)session.getAttribute("AssesmentAccess");
	target=(String)request.getParameter("target");

	pathLanguage="/util/jsp/language.jsp";
	
	dispatcher=request.getRequestDispatcher(pathLanguage);
	dispatcher.include(request,response);	
	
	pathFindAllLanguage="/language/findalllanguage.jsp";
	
	dispatcher=request.getRequestDispatcher(pathFindAllLanguage);
	dispatcher.include(request,response);	
	
   	if(sys==null){
		response.sendRedirect("/login.jsp");
	}
   	else{
   		messages=sys.getText();
%>

	<html:html> 
	<head/>
	<LINK REL=StyleSheet HREF="../util/css/estilo.css" TYPE="text/css">
	
	<body>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="default">
	 	<tr>
	 	<td>
	 		<html:form action="/Userswitchlanguage" >
				<html:hidden property="target" value="<%=target%>" />
				<html:select property="language" onchange='javascript:document.forms[0].submit()'>
					<html:options collection="languageOptionList" property="value" labelProperty="label"/>
				</html:select>
			</html:form>
		</td>
	    </tr>
		</table>
	</body>
	</html:html>

<%
	}
%>