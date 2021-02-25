<%@page language="java"
	import="assesment.business.*"
	import="java.util.*"
	import="assesment.communication.language.*"
	import="assesment.communication.security.*"
	import="assesment.communication.assesment.*"
	import="assesment.communication.module.*"
	import="assesment.presentation.translator.web.util.*"
	import="assesment.communication.corporation.*"
	import="assesment.communication.question.*"
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
	if(check!=null) {
		response.sendRedirect(request.getContextPath()+check);
	}else {
		session.setAttribute("url","question/view.jsp");

		RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
		dispatcher.include(request,response);
	
		Text messages=sys.getText();

		String target = request.getParameter("target");
		
		String id = request.getParameter("question");
		if(id == null) {
			id = (String)session.getAttribute("question");
		}
		QuestionData question = sys.getQuestionReportFacade().findQuestion(new Integer(id), sys.getUserSessionData(),QuestionData.GENERIC);
		ModuleAttribute module = sys.getModuleReportFacade().findGenericModuleAttributes(question.getModule(),sys.getUserSessionData());

		String[] questionTexts = sys.getLanguageReportFacade().findTexts(question.getKey(),sys.getUserSessionData());
		
%>		
	<head/>
	<script language="JavaScript">
		function confirmDelete(form,message) {
			if(confirm(message)) {
				form.submit();
			}
		}
	</script>
	<form action="./layout.jsp?refer=/module/genericview.jsp" name='back' method="post">
		<input type="hidden" name="module" value='<%=String.valueOf(question.getModule())%>' />
	</form>	
	<form action="./layout.jsp?refer=/question/genericcreate.jsp" name='edit' method="post">
		<input type="hidden" name="question" 		value='<%=id%>' />
		<input type="hidden" name="type" 		value="edit" />
	</form>	
	<html:form action="/GenericQuestionDelete" >
		<input type="hidden" name="question" 	value='<%=id%>' />
		<input type="hidden" name="target"		value='<%=target%>' />
  	</html:form>
		<body>
			<table width="600" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr class="guide1">
					<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("generic.question")%>' />
				</tr>
		      	<tr>
					<jsp:include  page="../component/spaceline.jsp" />
		  		</tr>
	  			<tr>			  		
					<jsp:include page="viewGenericOptions.jsp" />
				</tr>
				<tr>
					<jsp:include  page="../component/spaceline.jsp?colspan=3" />
				</tr>
		      	<tr>
		      		<td>
		    			<table width='100%' align='center' cellpadding="0" cellspacing="0">
					  		<tr>
								<td>
									<jsp:include  page='<%="../component/utilityboxtop.jsp?title="+messages.getText("generic.question.data")%>' />
									<table align='center' width='100%' cellpadding="0" cellspacing="0">
								  		<tr class="linetwo">
											<th align="left"><%=messages.getText("question.data.module")%></th>
											<td align="right">
						   						<%=messages.getText(module.getKey())%>              
											</td>
										</tr>
			      						<tr  class="space"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
			      						<tr  class="space">
			      							<td colspan="2">
												<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("question.data.text")%>' />
												<table align='center' width='100%' cellpadding="0" cellspacing="0">
											  		<tr class="lineone">
														<th align="left"><%=messages.getText("question.data.name.es")%></th>
														<td align="right">
									   						<%=questionTexts[0]%>
														</td>
													</tr>
						      						<tr  class="lineone"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
											  		<tr class="lineone">
														<th align="left"><%=messages.getText("question.data.name.en")%></th>
														<td align="right">
									   						<%=questionTexts[1]%>
														</td>
													</tr>
						      						<tr class="lineone"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
											  		<tr class="lineone">
														<th align="left"><%=messages.getText("question.data.name.pt")%></th>
														<td align="right">
									   						<%=questionTexts[2]%>
														</td>
													</tr>
												</table>
												<jsp:include  page="../component/utilitybox2bottom.jsp" />
											</td>
			      						</tr>
			      						<tr  class="space"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
								  		<tr class="linetwo">
											<th align="left"><%=messages.getText("question.data.image")%></th>
											<td align="right">
						   						<%=(Util.empty(question.getImage())) ? "---" : question.getImage()%>              
											</td>
										</tr>
			      						<tr  class="space"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
								  		<tr class="linetwo">
											<th align="left"><%=messages.getText("question.data.testtype")%></th>
											<td align="right">
												<%=(question.getTestType().intValue() == QuestionData.TEST_QUESTION) ? messages.getText("generic.messages.yes") : messages.getText("generic.messages.no")%>
											</td>
										</tr>
			      						<tr  class="space"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
								  		<tr class="linetwo">
											<th align="left"><%=messages.getText("question.data.type")%></th>
											<td align="right">
<%			switch(question.getType().intValue()) {
			    case QuestionData.TEXT:
%>					<%=messages.getText("question.type.text")%>
<%					break;
			    case QuestionData.DATE:
%>					<%=messages.getText("question.type.date")%>
<%					break;
			    case QuestionData.BIRTHDATE:
%>					<%=messages.getText("question.type.brithdate")%>
<%					break;
			    case QuestionData.EXCLUDED_OPTIONS:
%>					<%=messages.getText("question.type.excludedoptions")%>
<%					break;
			    case QuestionData.LIST:
%>					<%=messages.getText("question.type.list")%>
<%					break;
			    case QuestionData.KILOMETERS:
%>					<%=messages.getText("question.type.kilometers")%>
<%					break;
			    case QuestionData.OPTIONAL_DATE:
%>					<%=messages.getText("question.type.optionaldate")%>
<%					break;
			    case QuestionData.INCLUDED_OPTIONS:
%>					<%=messages.getText("question.type.includedoptions")%>
<%					break;
			    case QuestionData.EMAIL:
%>					<%=messages.getText("question.type.email")%>
<%					break;
			    case QuestionData.COUNTRY:
%>					<%=messages.getText("question.type.country")%>
<%					break;
			    case QuestionData.TEXTAREA:
%>					<%=messages.getText("question.type.textarea")%>
<%					break;
			}	
%>											</td>
										</tr>
									</table>
									<jsp:include  page="../component/utilityboxbottom.jsp" />
								</td>
							</tr>
					      	<tr>
								<jsp:include  page="../component/spaceline.jsp" />
					  		</tr>
<%			if(question.getType().intValue() == QuestionData.EXCLUDED_OPTIONS || question.getType().intValue() == QuestionData.LIST || question.getType().intValue() == QuestionData.KILOMETERS || question.getType().intValue() == QuestionData.INCLUDED_OPTIONS) {
%>					  		
      						<tr>
      							<td colspan="2">
									<jsp:include  page='<%="../component/utilityboxtop.jsp?title="+messages.getText("question.data.options")%>' />
									<table align='center' width='100%' cellpadding="0" cellspacing="0">
<%				Iterator it = question.getAnswerIterator();
				int index = 0;
				while(it.hasNext()) {
				    index++;
				    AnswerData answer = (AnswerData)it.next();	
				    String[] answerTexts = sys.getLanguageReportFacade().findTexts(answer.getKey(),sys.getUserSessionData());
%>										<tr>
			      							<td colspan="2">
												<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("question.data.option")+index%>' />
												<table align='center' width='100%' cellpadding="0" cellspacing="0">
													<tr class="lineone">
														<th align="left"><%=messages.getText("question.data.name.es")%></th>
														<td align="right">
											   				<%=answerTexts[0]%>
														</td>
													</tr>
											      	<tr  class="lineone"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
													<tr class="lineone">
														<th align="left"><%=messages.getText("question.data.name.en")%></th>
														<td align="right">
											   				<%=answerTexts[1]%>
														</td>
													</tr>
											      	<tr  class="lineone"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
													<tr class="lineone">
														<th align="left"><%=messages.getText("question.data.name.pt")%></th>
														<td align="right">
											   				<%=answerTexts[2]%>
														</td>
													</tr>
<%				if(question.getTestType().intValue() == QuestionData.TEST_QUESTION) {
%>											      	<tr  class="lineone"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
													<tr class="lineone">
														<th align="left"><%=messages.getText("question.data.resultvalue")%></th>
														<td align="right">
<%					switch(answer.getResultType().intValue()) {
			    		case AnswerData.CORRECT:
%>							<%=messages.getText("question.result.correct")%>
<%							break;
					    case AnswerData.INCORRECT:
%>							<%=messages.getText("question.result.incorrect")%>
<%							break;
					}	
%>														</td>
													</tr>
<%				}
%>												</table>
												<jsp:include  page="../component/utilitybox2bottom.jsp" />
											</td>
										</tr>
										<tr class="linetwo"><td>&nbsp;</td></tr>
<%				}				
%>									</table>	
									<jsp:include  page="../component/utilityboxbottom.jsp" />
								</td>
							</tr>
					      	<tr>
								<jsp:include  page="../component/spaceline.jsp" />
					  		</tr>
<%			}
%>					  		
						</table>	
					</td>
				</tr>
	  			<tr>			  		
					<jsp:include page="viewGenericOptions.jsp" />
				</tr>
				<tr>
					<jsp:include  page="../component/spaceline.jsp?colspan=3" />
				</tr>
			</table>
		</body>
<%	}	
%>
</html:html>
