<%@page import="assesment.business.question.QuestionReportFacade"%>
<%@page language="java"
	import="assesment.business.*"
	import="java.util.*"
	import="assesment.communication.language.*"
	import="assesment.communication.tag.*"
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
		Integer assesmentId=(Integer)session.getAttribute("assessment");
		String id = request.getParameter("question");
		if(Util.empty(id)) {
		    id = (String)session.getAttribute("question");
		}
		QuestionReportFacade questionFacade = sys.getQuestionReportFacade(); 
		QuestionData question = questionFacade.findQuestionBKP(new Integer(id), sys.getUserSessionData(),QuestionData.NORMAL);
		Collection<VideoData> videos = new LinkedList();
		int questionType = question.getType().intValue();
		if(questionType == QuestionData.VIDEO) {
			videos = questionFacade.getVideos(sys.getUserSessionData());
		}
		
		ModuleAttribute module = sys.getModuleReportFacade().findModuleAttributesbkp(question.getModule(),sys.getUserSessionData());
		HashMap<String, String> messagesbkp = sys.getLanguageReportFacade().findAssessmentBKPTexts(assesmentId, sys.getUserSessionData());

%>		
	<head/>
	<script language="JavaScript">
		function confirmDeleteAssociation(form,msg){
			var elements=document.forms['tags'].elements;
			var length=elements.length;
			var list="";
			for(i=0;i<length;i++){
				var element=elements[i];
				if(element.type.toLowerCase()=="checkbox"){
					if(element.checked){
						if(list==""){
							list=element.value;
						}else{
							list=element.value+"<"+list;
						}	
					}	
				} 
			}
			if(list.length>0){
				if(confirm(msg)){
					form.tags.value=list;
					form.submit();
				}
			}
		}
		function confirmDelete(form,message) {
			if(confirm(message)) {
				form.submit();
			}
		}
	</script>

		<body>
			<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("generic.question")%>' />
	  			
		      	<tr>
		      		<td>
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.question.data")%>' />
						<table align='center' width='100%' cellpadding="0" cellspacing="0">
					  		<tr class="line">
								<td align="left"><%=messages.getText("question.data.module")%></td>
								<td align="right">
			   						<%=messagesbkp.get(module.getKey())%>              
								</td>
							</tr>
					  		<tr class="line">
								<td align="left"><%=messages.getText("question.data.text")%></td>
								<td align="right">
			   						<%=(questionType == QuestionData.VIDEO) ? Util.getVideo(question.getKey(),videos) : messagesbkp.get(question.getKey())%>
								</td>
							</tr>
					  	
					  		<tr class="line">
								<td align="left"><%=messages.getText("question.data.image")%></td>
								<td align="right">
			   						<%=(Util.empty(question.getImage())) ? "---" : question.getImage()%>              
								</td>
							</tr>
					  		<tr class="line">
								<td align="left"><%=messages.getText("question.data.testtype")%></td>
								<td align="right">
									<%=(question.getTestType().intValue() == QuestionData.TEST_QUESTION) ? messages.getText("generic.messages.yes") : messages.getText("generic.messages.no")%>
								</td>
							</tr>
					  		<tr class="line">
								<td align="left"><%=messages.getText("question.data.showwrt")%></td>
								<td align="right">
									<%=(question.isWrt()) ? messages.getText("generic.messages.yes") : messages.getText("generic.messages.no")%>
								</td>
							</tr>
					  		<tr class="line">
								<td align="left"><%=messages.getText("question.data.type")%></td>
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
			    case QuestionData.OPTIONAL_DATE_NA:
%>					<%=messages.getText("question.type.optionaldatena")%>
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
%>								</td>
						 	</tr>
						</table>
						<jsp:include  page="../component/utilitybox2bottom.jsp" />
					</td>
				</tr>
<%			if(question.getType().intValue() == QuestionData.EXCLUDED_OPTIONS || question.getType().intValue() == QuestionData.LIST || question.getType().intValue() == QuestionData.KILOMETERS || question.getType().intValue() == QuestionData.INCLUDED_OPTIONS) {
%>				<tr>
      				<td colspan="2">
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("question.data.options")%>' />
						<table align='center' width='100%' cellpadding="0" cellspacing="0">
<%				Iterator it = question.getAnswerIterator();
				int index = 0;
				while(it.hasNext()) {
				    index++;
				    AnswerData answer = (AnswerData)it.next();	
				    String[] answerTexts = sys.getLanguageReportFacade().findTexts(answer.getKey(),sys.getUserSessionData());
%>							<tr class="Line">
			      				<td colspan="2">
									<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("question.data.option")+index%>' />
										<table align='center' width='100%' cellpadding="0" cellspacing="0">
											<tr class="line">
												<td align="left"><%=messages.getText("question.data.text")%></td>
												<td align="right">
									   				<%=messagesbkp.get(answer.getKey())%>
												</td>
											</tr>
											
<%					if(question.getTestType().intValue() == QuestionData.TEST_QUESTION) {
%>											<tr class="line">
												<td align="left"><%=messages.getText("question.data.resultvalue")%></td>
												<td align="right">
<%						switch(answer.getResultType().intValue()) {
			    			case AnswerData.CORRECT:
%>								<%=messages.getText("question.result.correct")%>
<%								break;
					    	case AnswerData.INCORRECT:
%>								<%=messages.getText("question.result.incorrect")%>
<%								break;
						}	
%>												</td>
											</tr>
<%					}		
%>
										</table>
									<jsp:include  page="../component/utilitybox2bottom.jsp" />
								</td>
						 	</tr>
<%				}
%>					  		
						</table>
						<jsp:include  page="../component/utilitybox2bottom.jsp" />
					</td>
				</tr>
<%			}		
%>					  		
	  			
			</table>
		</body>
<%	}	
%>
</html:html>
