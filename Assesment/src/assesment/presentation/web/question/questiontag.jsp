<%@page language="java"
	import="assesment.business.*"
	import="java.util.*"
	import="assesment.communication.tag.*"
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

		RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
		dispatcher.include(request,response);

		String tag = Util.getInValue(request,session,"tag");
		String answer = Util.getInValue(request,session,"answer");
		String question = Util.getInValue(request,session,"question");
		String assesment = Util.getInValue(request,session,"assesment");
		session.setAttribute("url","question/quesitontag.jsp?tag="+tag+"&answer="+answer+"&assesment="+assesment);

	
		AssesmentAttributes assesmentData = sys.getAssesmentReportFacade().findAssesmentAttributes(new Integer(assesment),sys.getUserSessionData());
		
		String[] answerTag = sys.getTagReportFacade().getAnswerTag(new Integer(tag),new Integer(answer),sys.getUserSessionData());
%>
	<head/>
	<html:form action="/UpdateAnswerTag">
		<html:hidden property="assesment" value='<%=assesment%>' />
		<html:hidden property="answer" value='<%=answer%>' />
		<html:hidden property="tag" value='<%=tag%>' />
		<html:hidden property="question" value='<%=question%>' />
		<body>
			<table width="600" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr class="guide1">
					<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("answer.associated.tag")%>' />
				</tr>
		      	<tr>
					<jsp:include  page="../component/spaceline.jsp" />
		  		</tr>
		      	<tr>
		      		<td>
						<jsp:include  page='<%="../component/utilityboxtop.jsp?title="+messages.getText("generic.assesment.tags")%>' />
						<table align='center' width='100%' cellpadding="0" cellspacing="0">
							<tr>
				        		<th align="left" class="linetwo"> <%=messages.getText("assesment.data.corporation")%></th>
			        			<td align="right" class="linetwo">
									<%=assesmentData.getName()%>
								</td>
				          	</tr>
			      			<tr  class="space"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
							<tr>
				        		<th align="left" class="linetwo"> <%=messages.getText("generic.question")%></th>
			        			<td align="right" class="linetwo">
									<%=messages.getText(answerTag[0])%>
								</td>
				          	</tr>
			      			<tr  class="space"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
							<tr>
				        		<th align="left" class="linetwo"> <%=messages.getText("question.resultreport.answer")%></th>
			        			<td align="right" class="linetwo">
									<%=messages.getText(answerTag[1])%>
								</td>
				          	</tr>
			      			<tr  class="space"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
							<tr>
				        		<th align="left" class="linetwo"> <%=messages.getText("generic.tag")%></th>
			        			<td align="right" class="linetwo">
									<%=messages.getText(answerTag[2])%>
								</td>
				          	</tr>
			      			<tr  class="space"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
							<tr>
				        		<th align="left" class="linetwo"> <%=messages.getText("assesment.tags.value")%></th>
			        			<td align="right" class="linetwo">
									<html:text property="value" styleClass="input" value='<%=answerTag[3]%>' />
								</td>
				          	</tr>
						</table>	
						<jsp:include  page="../component/utilityboxbottom.jsp" />
					</td>
				</tr>
		      	<tr>
					<jsp:include  page="../component/spaceline.jsp" />
		  		</tr>
				<tr>
					<td align="right" colspan="3">
						<html:submit styleClass="input" value='<%=messages.getText("generic.messages.save")%>' />
						<html:cancel styleClass="input" value='<%=messages.getText("generic.messages.cancel")%>' />
					</td>
				</tr>
		      	<tr>
					<jsp:include  page="../component/spaceline.jsp" />
		  		</tr>
			</table>
		</body>
	</html:form>
<%	}	
%>
</html:html>
