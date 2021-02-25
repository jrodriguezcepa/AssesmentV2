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
	ArrayList<ArrayList<Object>> sheetResume = (ArrayList<ArrayList<Object>>) sys.getValue();
	
%>
	<head>
	</head>
	<form name="back" action="home.jsp">
	</form>
	<body>
		<html:form action="/DownloadCreateUsersFile">
			<jsp:include page='<%="../component/titlecomponent.jsp?title="+messages.getText("user.createdfile.createdusers")%>' />
				<tr>
					<td width="100%" valign="top" colspan="2">
						<jsp:include page='<%="../component/utilitybox2top.jsp?title="+messages.getText("user.createdfile.newusers")%>' />
						<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<%		boolean header = true;
		boolean line = true;
		for(ArrayList<Object> row : sheetResume ){
%>							<tr>
<%        	for(Object cellValue : row){
				if(header) {
%>								<td class="guide2"><%=String.valueOf(cellValue)%></td>
<%				}else {
%>								<td class='<%=(line) ? "linetwo" : "lineone" %>'><%=String.valueOf(cellValue)%></td>
<%				}
			}
			line = !line;
			header = false;
%>							</tr>
<%		}
%>
					</table> 
					<jsp:include page="../component/utilityboxbottom.jsp" />
				</td>
			</tr>
			<tr align="right" class="line">
				<td>
					<html:submit styleClass="button"><%=messages.getText("assesment.report.download")%></html:submit>
					<input type="button" class="button" value='<%=messages.getText("generic.messages.cancel")%>' onclick="javascript:document.forms['back'].submit();" ></input>
				</td>
			</tr>
		<jsp:include page='../component/titleend.jsp' />
	</html:form>
</body>
</html>
