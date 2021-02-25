<%@page language="java"
   errorPage="../exception.jsp"
   import="java.util.*"
   import="assesment.business.*"
   import="assesment.communication.language.*"
   import="assesment.communication.security.*"
   import="assesment.communication.language.tables.*"
   import="assesment.communication.administration.user.tables.*"
   import="assesment.communication.question.*"
   import="assesment.communication.module.*"
   import="assesment.presentation.translator.web.util.*"
   import="assesment.presentation.actions.user.*"
%>
<%@page contentType="text/html; charset=UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">


<%@page import="assesment.communication.assesment.AssesmentAttributes"%>
<%@page import="assesment.communication.user.UserData"%>
<%@page import="assesment.communication.administration.UserAnswerData"%><html>
<%
    AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	AssesmentAttributes attributes = sys.getAssesmentReportFacade().findAssesmentAttributes(new Integer(15),sys.getUserSessionData());
	String[] reportData = sys.getAssesmentReportFacade().findReportData(new Integer(15),sys.getUserSessionData());

	ModuleData module = sys.getModuleReportFacade().getPersonalDataModule(new Integer(15),sys.getUserSessionData());

	Collection answers = sys.getUserReportFacade().findModuleResult("emea555",module.getId(),sys.getUserSessionData());
       
%>

   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1250" />
      <title>Assessment</title>
      <style type="text/css">
<!--
.style14 {font-family: Arial, Helvetica, sans-serif; font-weight: bold; font-size: 12px; }
-->
</style>
	</head>
   	<body>
   		<table>
<%	Iterator it = answers.iterator();
	while(it.hasNext()) {
		UserAnswerData data = (UserAnswerData)it.next();
%>   		<tr>
   				<td>
   					<%=data.getText()%>
   				</td>
   			</tr>
<%	}
%>   	</table>
   	</body>
</html>
