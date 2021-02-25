<%@page language="java"
	import="assesment.business.*"
	import="java.util.*"
	import="assesment.communication.language.*"
	import="assesment.communication.security.*"
	import="assesment.communication.util.*"
	import="assesment.presentation.actions.assesment.*"
	import="assesment.presentation.translator.web.util.*"
	import="assesment.communication.assesment.*"
	errorPage="../exception.jsp"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>
<html:html>
	<LINK REL=StyleSheet HREF="../util/css/estilo.css" TYPE="text/css">
<%
	AssesmentAccess sys=(AssesmentAccess)session.getAttribute("AssesmentAccess");
	String check = Util.checkPermission(sys,SecurityConstants.ADMINISTRATOR);
	Text messages=sys.getText();

	if(check!=null) {
		response.sendRedirect(request.getContextPath()+check);
	}else {

		String assesment = request.getParameter("assesment");
		String type = request.getParameter("type");
		session.setAttribute("url","assesment/feedback.jsp?assesment="+assesment+"&type="+type);

	
		RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
		dispatcher.include(request,response);

		if(type.equals("edit")) {
		    FeedbackData data = sys.getAssesmentReportFacade().getFeedback(new Integer(assesment),request.getParameter("email"),sys.getUserSessionData());
		    FeedbackForm form = new FeedbackForm(assesment,data);
		    session.setAttribute("AssessmentFeedbackForm",form);
		}
%>
	<head/>
	<body>
		<html:form action="/UpdateFeedback" >
			<html:hidden property="assessment" value="<%=assesment%>" />
			<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("assessment.action.addfeedback")%>' />
		      	<tr>
		      		<td>
						<jsp:include  page='<%="../component/utilityboxtop.jsp?title="+messages.getText("generic.assesment.data")%>' />
						<table align='center' width='100%' cellpadding="0" cellspacing="0">
					  		<tr class="line">
								<td align="left">
									<%=messages.getText("assesment.data.email")%><span class="required">*</span>
								</td>
								<td align="right">
			   						<html:text property="email" size="50" styleClass="input"/>              
								</td>
							</tr>
	    				</table>
						<jsp:include  page="../component/utilitybox2bottom.jsp" />
					</td>
				</tr>
		      	<tr>
		      		<td align="right" class="line">
			            <html:submit styleClass="input"><%=messages.getText("generic.messages.save")%></html:submit>
			            <html:cancel styleClass="input"><%=messages.getText("generic.messages.cancel")%></html:cancel>
		      		</td>
		      	</tr>
			<jsp:include  page='../component/titleend.jsp' />
		</html:form>
	</body>
<%	}	
%>
</html:html>
