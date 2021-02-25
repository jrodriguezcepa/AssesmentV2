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
		session.setAttribute("url","module/create.jsp");

		RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
		dispatcher.include(request,response);
	
		Text messages=sys.getText();

		String id = request.getParameter("module");
		if(Util.empty(id)) {
		    id = (String)session.getAttribute("module");
		}
		ModuleData module = sys.getModuleReportFacade().findModule(new Integer(id),sys.getUserSessionData());
		String[] texts = sys.getLanguageReportFacade().findTexts(module.getKey(),sys.getUserSessionData());
		AssesmentAttributes assesment = sys.getAssesmentReportFacade().findAssesmentAttributes(module.getAssesment(),sys.getUserSessionData());
		CorporationAttributes corporation = sys.getCorporationReportFacade().findCorporationAttributes(assesment.getCorporationId(),sys.getUserSessionData());
		String link = "";		
		String linkShow = "";		
		String linkHide = "";		
%>
	<form action="./layout.jsp?refer=/assesment/view.jsp" name='back' method="post">
		<input type="hidden" name="assesment" 		value='<%=String.valueOf(assesment.getId())%>' />
	</form>	
	<form action="./layout.jsp?refer=/module/create.jsp" name='edit' method="post">
		<input type="hidden" name="module" 		value='<%=id%>' />
		<input type="hidden" name="type" 		value="edit" />
	</form>	
	<form action="./layout.jsp?refer=/question/create.jsp" name='question' method="post">
		<input type="hidden" name="module" 			value='<%=id%>' />
		<input type="hidden" name="type" 			value="create" />
		<input type="hidden" name="target" 			value="module" />
	</form>	
	<form action="./layout.jsp?refer=/module/selectlist2.jsp" name='import_question' method="post">
		<input type="hidden" name="module" 		value='<%=id%>' />
		<input type="hidden" name="target" 		value="module" />
	</form>	
	<html:form action="/ModuleDelete" >
		<input type="hidden" name="module" 			value='<%=id%>'	/>
		<input type="hidden" name="assesment" 		value='<%=String.valueOf(assesment.getId())%>' />
		<input type="hidden" name="target" 			value="module" />
  	</html:form>
	<form action="./layout.jsp?refer=/question/changeOrder.jsp" name='order' method="post">
		<input type="hidden" name="module" 		value='<%=id%>' />
	</form>	
	<form action="./layout.jsp?refer=/question/group.jsp" name='group' method="post">
		<input type="hidden" name="module" 		value='<%=id%>' />
	</form>	
	<head/>
	<script type="text/javascript">
		function confirmDelete(form,message) {
			if(confirm(message)) {
				form.submit();
			}
		}
		function showQuestions(resume,questions) {
			document.getElementById(resume).style.display = 'none';
			document.getElementById(questions).style.display = 'block';
		}
		function hideQuestions(resume,questions) {
			document.getElementById(resume).style.display = 'block';
			document.getElementById(questions).style.display = 'none';
		}
		function deleteIFConfirm(form,msg){
			var elements=form.elements;
			var length=elements.length;
			var i;
			var valueCheckboxParamList="";
			var separator="<";
				
			for(i=0;i<length;i++){
				var element=elements[i];
				if(element.type.toLowerCase()=="checkbox"){
					if(element.checked){
						if(valueCheckboxParamList==""){
							valueCheckboxParamList=element.value;
						}else{
							valueCheckboxParamList=element.value+"<"+valueCheckboxParamList;
						}	
					}	
				} 
			}
			form.questions.value=valueCheckboxParamList;
			if(valueCheckboxParamList.length>0){
				if(confirm(msg)){
					form.submit();
				}
			}
		}
	</script>
		<body>
			<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("generic.module")+" "+messages.getText(module.getKey())%>' />
		  		<tr>			  		
					<jsp:include page='<%="viewOptions.jsp"+"?status="+String.valueOf(assesment.getStatus())%>' />
				</tr>
		      	<tr>
		      		<td>
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.module.data")%>' />
							<table align='center' width='100%' cellpadding="5" cellspacing="5">
						  		<tr class="line">
									<td align="left"><%=messages.getText("module.data.corporation")%></td>
									<td align="right">
				   						<%=corporation.getName()%>              
									</td>
								</tr>
						  		<tr class="line">
									<td align="left"><%=messages.getText("module.data.assesment")%></td>
									<td align="right">
				   						<%=messages.getText(assesment.getName())%>              
									</td>
								</tr>
						  		<tr class="line">
									<td align="left"><%=messages.getText("module.data.name")+" ("+messages.getText("module.data.name.es")+")"%></td>
									<td align="right">
				   						<%=texts[0]%>
									</td>
								</tr>
						  		<tr class="line">
									<td align="left"><%=messages.getText("module.data.name")+" ("+messages.getText("module.data.name.en")+")"%></td>
									<td align="right">
				   						<%=texts[1]%>
									</td>
								</tr>
						  		<tr class="line">
									<td align="left"><%=messages.getText("module.data.name")+" ("+messages.getText("module.data.name.pt")+")"%></td>
									<td align="right">
				   						<%=texts[2]%>
									</td>
								</tr>
								<tr  class="line">
 									<td align="left" > <%=messages.getText("module.data.type")%></td>
		    						<td align="right">
		    							<%=(module.getType() == null) ? "" : messages.getText("module.data.type"+String.valueOf(module.getType())) %>
		    						</td>
		    					</tr>
							</table>
						<jsp:include  page="../component/utilitybox2bottom.jsp" />
					</td>
				</tr>
		  		<tr>
					<td colspan="2">
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("module.data.questions")%>' />
						<html:form action="/QuestionDeleteList">	
							<html:hidden property="module" value='<%=id%>' />
							<html:hidden property="questions" />
							<table align='center' width='100%' cellpadding="0" cellspacing="0">
<%					switch(module.getQuestions().size()) {
						case 0:
%>    							<tr  class="line">
									<td align="left" colspan="2">
										<%=messages.getText("module.data.withoutquestions")%>
									</td>
								</tr>
<%							break;
						case 1:
							linkShow = "javascript:showQuestions('questionsResume"+String.valueOf(module.getId())+"','questionsDetail"+String.valueOf(module.getId())+"');";
							linkHide = "javascript:hideQuestions('questionsResume"+String.valueOf(module.getId())+"','questionsDetail"+String.valueOf(module.getId())+"');";
%>			    				<tr  class="line">
									<td align="left" colspan="2">
										<div id='<%="questionsResume"+String.valueOf(module.getId())%>' style="display: none;">
											<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
												<tr class="line">
													<td align="left"><%=messages.getText("module.data.onequestion")%></td>
													<td align="right"><a href="<%=linkShow%>"><%=messages.getText("module.data.questions.view")%></a></td>
												</tr>	
											</table>
										</div>
										<div id='<%="questionsDetail"+String.valueOf(module.getId())%>' style="display: block;">
						    				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<%							Iterator it = module.getQuestionIterator();
							while(it.hasNext()) {
							    QuestionData question = (QuestionData)it.next();
%>												<tr class="line">
<%								if(assesment.getStatus().intValue() == AssesmentAttributes.EDITABLE) {
%>													<td align="left" width="5%">
														<input type="checkbox" value='<%=String.valueOf(question.getId())%>' />
													</td>
<%								}
%>													<td align="left">
														<a href='<%="./layout.jsp?refer=/question/view.jsp?question="+String.valueOf(question.getId())+"&target=module"%>' >
															<%="- "+question.getKey(messages)%>
														</a>
													</td>
													<td align="right"><!--  a href="<%=linkHide%>"><%=messages.getText("module.data.questions.hide")%></a--></td>
												</tr>
<%							}
%>												<tr class="line">
													<td align="right" colspan="3">
<%							link = "javascript:deleteIFConfirm(document.forms['QuestionDeleteListForm'],'"+messages.getText("question.delete.confirm")+"');";
%>		   												<input name="button" type="button" onClick="<%=link%>" value=<%=messages.getText("question.messages.delete")%> class="input"/>
													</td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
<%							break;
						default:
							linkShow = "javascript:showQuestions('questionsResume"+String.valueOf(module.getId())+"','questionsDetail"+String.valueOf(module.getId())+"');";
							linkHide = "javascript:hideQuestions('questionsResume"+String.valueOf(module.getId())+"','questionsDetail"+String.valueOf(module.getId())+"');";
%>    							<tr  class="line">
									<td align="left" colspan="2">
										<div id='<%="questionsResume"+String.valueOf(module.getId())%>' style="display: block;">
											<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
												<tr class="line">
													<td align="left"><%=String.valueOf(module.getQuestions().size())+" "+messages.getText("module.data.asociatedquestion")%></td>
													<td align="right"><a href="<%=linkShow%>"><%=messages.getText("module.data.questions.view")%></a></td>
												</tr>	
											</table>
										</div>
										<div id='<%="questionsDetail"+String.valueOf(module.getId())%>' style="display: none;">
						    				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<%							it = module.getQuestionIterator();
							boolean hide = true;
							while(it.hasNext()) {
							    QuestionData question = (QuestionData)it.next();
%>												<tr class="line">
<%								if(assesment.getStatus().intValue() == AssesmentAttributes.EDITABLE) {
%>													<td align="left" width="5%">
														<input type="checkbox" value='<%=String.valueOf(question.getId())%>' />
													</td>
<%								}
								if(hide) {			
    								hide = false;
%>													<td align="left" width="70%">
														<a href='<%="./layout.jsp?refer=/question/view.jsp?question="+String.valueOf(question.getId())+"&target=module"%>' >
															<%="- "+question.getKey(messages)%>
														</a>
													</td>
													<td align="right" width="25%">
														<a href="<%=linkHide%>"><%=messages.getText("module.data.questions.hide")%></a>
													</td>
<%								}else {
%>													<td align="left" width="95%" colspan="2">
														<a href='<%="./layout.jsp?refer=/question/view.jsp?question="+String.valueOf(question.getId())+"&target=module"%>' >
															<%="- "+question.getKey(messages)%>
														</a>
													</td>
<%								}
%>												</tr>
<%							}
							if(assesment.getStatus().intValue() == AssesmentAttributes.EDITABLE) {
								link = "javascript:deleteIFConfirm(document.forms['QuestionDeleteListForm'],'"+messages.getText("question.delete.confirm")+"');";
%>												<tr class="line">
													<td align="right" colspan="3">
              												<input name="button" type="button" onClick="<%=link%>" value=<%=messages.getText("question.messages.delete")%> class="input"/>
													</td>
												</tr>
<%							}
%>											</table>
										</div>
									</td>
								</tr>
<%					}
%>							</table>
						</html:form>
						<jsp:include  page="../component/utilitybox2bottom.jsp" />
					</td>
				</tr>
		  		<tr>			  		
					<jsp:include page='<%="viewOptions.jsp"+"?status="+String.valueOf(assesment.getStatus())%>' />
				</tr>
			</table>
		</body>
<%	}	
%>
</html:html>
