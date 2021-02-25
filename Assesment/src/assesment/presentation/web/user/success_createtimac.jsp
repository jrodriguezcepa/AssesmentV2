<%@page import="assesment.business.administration.user.UsABMFacade"%>
<%@page import="assesment.persistence.user.tables.UserPassword"%>
<%@page import="assesment.communication.user.UserData"%>
<%@page language="java" errorPage="../exception.jsp"
	import="java.util.*" import="assesment.business.*"
	import="assesment.communication.language.*"
	import="assesment.communication.security.*"
	import="assesment.communication.administration.*"
	import="assesment.communication.util.*"
	import="assesment.presentation.translator.web.util.*"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<%!
	Text messages;	AssesmentAccess sys;
%>
<%
	sys=(AssesmentAccess)session.getAttribute("AssesmentAccess");
	
	RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
	dispatcher.include(request,response);

	session.setAttribute("url","user/success_createfromfile.jsp");
	messages=sys.getText();
	String[][] result = (String[][]) sys.getValue();
	boolean line = true;
	
%>
	<head>
	</head>
	<form name="back" action="home.jsp">
	</form>
	<body>
			<jsp:include page='<%="../component/titlecomponent.jsp?title="+messages.getText("user.createdfile.createdusers")%>' />
				<tr>
					<td width="100%" valign="top" colspan="2">
						<jsp:include page='<%="../component/utilitybox2top.jsp?title="+messages.getText("user.createdfile.newusers")%>' />
						<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<%		String[] created = result[0];	
		for(int i = 0; i < created.length; i++){
%>							<tr>
								<td class='lineone'>- <%=created[i]%></td>
							</tr>
<%		}
%>						</table> 
						<jsp:include page="../component/utilityboxbottom.jsp" />
					</td>
				</tr>
				<tr>
					<td width="100%" valign="top" colspan="2">
						<jsp:include page='<%="../component/utilitybox2top.jsp?title="+messages.getText("user.createdfile.existusers")%>' />
						<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<%		String[] exist = result[1];	
		for(int i = 0; i < exist.length; i++){
%>							<tr>
								<td class='lineone'>- <%=exist[i]%></td>
							</tr>
<%		}
%>						</table> 
						<jsp:include page="../component/utilityboxbottom.jsp" />
					</td>
				</tr>
			<jsp:include page='../component/titleend.jsp' />
	</body>
</html>
