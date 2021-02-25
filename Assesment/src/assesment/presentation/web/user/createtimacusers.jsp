<%@page import="assesment.presentation.actions.user.UserCreateFileForm"%>
<%@page import="assesment.communication.language.tables.LanguageData"%>
<%@page language="java" 
	errorPage="../exception.jsp"
	import="java.util.*"
	import="assesment.business.*"
	import="assesment.communication.language.*"
	import="assesment.communication.security.*"
	import="assesment.communication.corporation.*"
	import="assesment.communication.util.*"
	import="assesment.presentation.translator.web.util.*"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<%!
	Text messages;	AssesmentAccess sys;
%>
<%
	sys=(AssesmentAccess)session.getAttribute("AssesmentAccess");
	String check = Util.checkPermission(sys,SecurityConstants.ADMINISTRATOR);
	
	RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
	dispatcher.include(request,response);

	if(check!=null) {
		response.sendRedirect(request.getContextPath()+check);
	} else {
		session.setAttribute("url","user/createfromfile.jsp");
	
		if(session.getAttribute("Msg")!=null){
			session.removeAttribute("Msg");
		}
		messages=sys.getText();
	
		UserCreateFileForm form = new UserCreateFileForm();
		session.setAttribute("UserCreateFileForm", form);

%>
<body>
	<html:form action="/CreateTimacUsersFile" enctype="multipart/form-data">
		<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("user.timcafile")%>' />
			<tr>
				<td align="right" colspan="2">
					<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("user.createfromfile.users")%>' />
	  				<table width="100%" border="0" align="center" cellpadding="5" cellspacing="5">
						<tr class="line">
							<td align="left">
								<%=messages.getText("user.selectfile")%>
							</td>
							<td align="right">
								<html:file property="file" styleClass="line"/>
							</td>
						</tr>
					</table>
					<jsp:include  page="../component/utilitybox2bottom.jsp" />
				</td>
			</tr>
			<tr>
				<td align="right" colspan="2">
					<html:submit value='<%=messages.getText("generic.messages.save")%>' styleClass="button" />
				</td>
			</tr>
		<jsp:include  page='../component/titleend.jsp' />
	</html:form>
</body>
<%
	}
%>
</html>
