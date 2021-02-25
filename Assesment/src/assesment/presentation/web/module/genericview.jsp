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
		session.setAttribute("url","question/genericview.jsp");

		RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
		dispatcher.include(request,response);
	
		Text messages=sys.getText();

		String id = request.getParameter("module");
		if(Util.empty(id)) {
		    id = (String)session.getAttribute("module");
		}
		ModuleData module = sys.getModuleReportFacade().findGenericModule(new Integer(id),sys.getUserSessionData());
		String[] texts = sys.getLanguageReportFacade().findTexts(module.getKey(),sys.getUserSessionData());
		
%>
	<form action="./layout.jsp?refer=/module/genericcreate.jsp" name='edit' method="post">
		<input type="hidden" name="module" 		value='<%=id%>' />
		<input type="hidden" name="type" 		value="edit" />
	</form>	
	<form action="./layout.jsp?refer=/question/genericcreate.jsp" name='question' method="post">
		<input type="hidden" name="module" 			value='<%=id%>' />
		<input type="hidden" name="type" 			value="create" />
		<input type="hidden" name="target" 			value="module" />
	</form>	
	<html:form action="/GenericModuleDelete" >
		<input type="hidden" name="modules" 	value='<%=id%>'	/>
  	</html:form>
	<form action="./layout.jsp?refer=/module/genericlist.jsp" name='back' method="post">
	</form>	
	<form action="./layout.jsp?refer=/question/genericChangeOrder.jsp" name='order' method="post">
		<input type="hidden" name="module" 		value='<%=id%>' />
	</form>	
	<form action="./layout.jsp?refer=/question/genericGroup.jsp" name='group' method="post">
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
			<table width="600" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr class="guide1">
					<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("generic.module")+" "+messages.getText(module.getKey())%>' />
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
									<jsp:include  page='<%="../component/utilityboxtop.jsp?title="+messages.getText("generic.module.data")%>' />
									<table align='center' width='100%' cellpadding="0" cellspacing="0">
			      						<tr  class="space">
			      							<td colspan="2">
												<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("module.data.name")%>' />
												<table align='center' width='100%' cellpadding="0" cellspacing="0">
											  		<tr class="lineone">
														<th align="left"><%=messages.getText("module.data.name.es")%></th>
														<td align="right">
									   						<%=texts[0]%>
														</td>
													</tr>
						      						<tr  class="lineone"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
											  		<tr class="lineone">
														<th align="left"><%=messages.getText("module.data.name.en")%></th>
														<td align="right">
									   						<%=texts[1]%>
														</td>
													</tr>
						      						<tr class="lineone"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
											  		<tr class="lineone">
														<th align="left"><%=messages.getText("module.data.name.pt")%></th>
														<td align="right">
									   						<%=texts[2]%>
														</td>
													</tr>
												</table>
												<jsp:include  page="../component/utilitybox2bottom.jsp" />
											</td>
			      						</tr>
								      	<tr  class="space"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
      									<tr  class="linetwo">
        									<th align="left" > <%=messages.getText("module.data.type")%></th>
			        						<td align="right">
			        							<%=(module.getType() == null) ? "" : messages.getText("module.data.type"+String.valueOf(module.getType())) %>
			        						</td>
			        					</tr>
			      						<tr  class="space"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
								  		<tr>
											<td colspan="2">
												<jsp:include  page='<%="../component/utilityboxtop.jsp?title="+messages.getText("module.data.questions")%>' />
												<html:form action="/GenericQuestionDeleteList">	
												<html:hidden property="module" value='<%=id%>' />
												<html:hidden property="questions" />
												<table align='center' width='100%' cellpadding="0" cellspacing="0">
<%					switch(module.getQuestions().size()) {
						case 0:
%>    												<tr  class="linetwo"><td align="left" colspan="2"><%=messages.getText("module.data.withoutquestions")%></td></tr>
<%							break;
						case 1:
%>			    									<tr  class="linetwo">
														<td align="left" colspan="2">
															<div id='<%="questionsResume"+String.valueOf(module.getId())%>' style="display: none;">
			    												<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			    													<tr class="linetwo">
																		<td align="left"><%=messages.getText("module.data.onequestion")%></td>
																		<td align="right"><a href='<%="javascript:showQuestions('questionsResume"+String.valueOf(module.getId())+"','questionsDetail"+String.valueOf(module.getId())+"');"%>'><%=messages.getText("module.data.questions.view")%></a></td>
																	</tr>	
																</table>
															</div>
															<div id='<%="questionsDetail"+String.valueOf(module.getId())%>' style="display: block;">
						    									<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<%							Iterator it = module.getQuestionIterator();
							while(it.hasNext()) {
							    QuestionData question = (QuestionData)it.next();
%>																	<tr class="linetwo">
																		<td align="left" width="5%">
																			<input type="checkbox" value='<%=String.valueOf(question.getId())%>' />
																		</td>
																		<td align="left">
																			<a href='<%="./layout.jsp?refer=/question/genericview.jsp?question="+String.valueOf(question.getId())+"&target=module"%>' >
																				<%="- "+messages.getText(question.getKey())%>
																			</a>
																		</td>
																		<td align="right"><!-- a href='<%="javascript:hideQuestions('questionsResume"+String.valueOf(module.getId())+"','questionsDetail"+String.valueOf(module.getId())+"');"%>'><%=messages.getText("module.data.questions.hide")%></a--></td>
																	</tr>
<%							}
%>																	<tr class="linetwo">
																	</tr>
																</table>
															</div>
														</td>
													</tr>
<%							break;
						default:
%>    												<tr  class="linetwo">
														<td align="left" colspan="2">
															<div id='<%="questionsResume"+String.valueOf(module.getId())%>' style="display: none;">
			    												<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			    													<tr class="linetwo">
																		<td align="left"><%=String.valueOf(module.getQuestions().size())+" "+messages.getText("module.data.asociatedquestion")%></td>
																		<td align="right"><a href='<%="javascript:showQuestions('questionsResume"+String.valueOf(module.getId())+"','questionsDetail"+String.valueOf(module.getId())+"');"%>'><%=messages.getText("module.data.questions.view")%></a></td>
																	</tr>	
																</table>
															</div>
															<div id='<%="questionsDetail"+String.valueOf(module.getId())%>' style="display: block;">
						    									<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<%							it = module.getQuestionIterator();
							boolean hide = true;
							while(it.hasNext()) {
							    QuestionData question = (QuestionData)it.next();
%>																	<tr class="linetwo">
																		<td align="left" width="5%">
																			<input type="checkbox" value='<%=String.valueOf(question.getId())%>' />
																		</td>
<%								if(hide) {
    								hide = false;
%>																		<td align="left" width="70%">
																			<a href='<%="./layout.jsp?refer=/question/genericview.jsp?question="+String.valueOf(question.getId())+"&target=module"%>' >
																				<%="- "+messages.getText(question.getKey())%>
																			</a>
																		</td>
																		<td align="right" width="25%"><!--a href='<%="javascript:hideQuestions('questionsResume"+String.valueOf(module.getId())+"','questionsDetail"+String.valueOf(module.getId())+"');"%>'><%=messages.getText("module.data.questions.hide")%></a--></td>
<%								}else {
%>																		<td align="left" width="95%" colspan="2">
																			<a href='<%="./layout.jsp?refer=/question/genericview.jsp?question="+String.valueOf(question.getId())+"&target=module"%>' >
																				<%="- "+messages.getText(question.getKey())%>
																			</a>
																		</td>
<%								}
%>																	</tr>
<%							}
%>																	<tr class="linetwo">
																		<td align="right" colspan="3">&nbsp;</td>
																	</tr>
																	<tr class="linetwo">
																		<td align="right" colspan="3">
			                												<input name="button" type="button" onClick="javascript:deleteIFConfirm(document.forms['GenericQuestionDeleteListForm'],'<%=messages.getText("question.delete.confirm")%>');" value=<%=messages.getText("question.messages.delete")%> class="input"/>
																		</td>
																	</tr>
																</table>
															</div>
														</td>
													</tr>
<%					}
%>												</table>
												</html:form>	
												<jsp:include  page="../component/utilityboxbottom.jsp" />
											</td>
										</tr>
									</table>
									<jsp:include  page="../component/utilityboxbottom.jsp" />
								</td>
							</tr>
					      	<tr>
								<jsp:include  page="../component/spaceline.jsp" />
					  		</tr>
						</table>	
					</td>
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
			</table>
		</body>
<%	}	
%>
</html:html>
