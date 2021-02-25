<%@page language="java"
	import="assesment.business.*"
	import="java.util.*"
	import="assesment.communication.language.*"
	import="assesment.communication.security.*"
	import="assesment.communication.assesment.*"
	import="assesment.communication.module.*"
	import="assesment.presentation.translator.web.util.*"
	import="assesment.presentation.actions.question.*"
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
		session.setAttribute("url","question/create.jsp");

		RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
		dispatcher.include(request,response);
	
		Text messages=sys.getText();
		String type = request.getParameter("type");
		String value = "";

		String id = request.getParameter("question");
		if(Util.empty(id)) {
		    id = (String)session.getAttribute("question");
		}
		String target = request.getParameter("target");

		ModuleAttribute module = null;
		
		QuestionForm form = null;
		String[] texts = {"","",""};
		if(type.equals("create")) {
		    module = sys.getModuleReportFacade().findGenericModuleAttributes(new Integer(request.getParameter("module")),sys.getUserSessionData());
		    form = new QuestionForm(String.valueOf(module.getId()),target);
		}else {
		    QuestionData question = sys.getQuestionReportFacade().findQuestion(new Integer(id),sys.getUserSessionData(),QuestionData.GENERIC);
		    module = sys.getModuleReportFacade().findGenericModuleAttributes(question.getModule(),sys.getUserSessionData());
		    form = new QuestionForm(question,sys,target);
		    session.setAttribute("GenericQuestionUpdateForm",form);
		}
		
		String formName = (type.equals("create")) ? "/GenericQuestionCreate" : "/GenericQuestionUpdate";
		
		Collection types = new LinkedList();
		types.add(new OptionItem(messages.getText("generic.messages.select"),""));
		types.add(new OptionItem(messages.getText("question.type.text"),String.valueOf(QuestionData.TEXT)));
		types.add(new OptionItem(messages.getText("question.type.date"),String.valueOf(QuestionData.DATE)));
		types.add(new OptionItem(messages.getText("question.type.birthdate"),String.valueOf(QuestionData.BIRTHDATE)));
		types.add(new OptionItem(messages.getText("question.type.excludedoptions"),String.valueOf(QuestionData.EXCLUDED_OPTIONS)));
		types.add(new OptionItem(messages.getText("question.type.list"),String.valueOf(QuestionData.LIST)));	
		types.add(new OptionItem(messages.getText("question.type.kilometers"),String.valueOf(QuestionData.KILOMETERS)));
		types.add(new OptionItem(messages.getText("question.type.optionaldate"),String.valueOf(QuestionData.OPTIONAL_DATE)));
		types.add(new OptionItem(messages.getText("question.type.includedoptions"),String.valueOf(QuestionData.INCLUDED_OPTIONS)));
		types.add(new OptionItem(messages.getText("question.type.email"),String.valueOf(QuestionData.EMAIL)));
		types.add(new OptionItem(messages.getText("question.type.country"),String.valueOf(QuestionData.COUNTRY)));
		types.add(new OptionItem(messages.getText("question.type.textarea"),String.valueOf(QuestionData.TEXTAREA)));
		session.setAttribute("types",types);

		
		String javascript1 = (type.equals("create")) ? "showOptions(document.forms['GenericQuestionCreateForm'])" : "showOptions(document.forms['GenericQuestionUpdateForm'])";
		String javascript2 = (type.equals("create")) ? "optionChange(document.forms['GenericQuestionCreateForm'])" : "optionChange(document.forms['GenericQuestionUpdateForm'])";
%>		
	<head/>
	<script type="text/javascript">
		function showOptions(form) {
			if(form.type.options.selectedIndex == 4 || form.type.options.selectedIndex == 5 || form.type.options.selectedIndex == 8) {
				document.getElementById('options').style.display = 'block';
			}else {
				document.getElementById('options').style.display = 'none';
			}
		}
		function optionChange(form) {
			for(i = 3; i < 24; i++) {
				value = 'option'+i;
				if(form.optioncount.options.selectedIndex+3 > i) {
					document.getElementById(value).style.display = 'block';
				}else {
					document.getElementById(value).style.display = 'none';
				}
			}
		}
	</script>
	<body>
		<html:form action='<%=formName%>' enctype="multipart/form-data">
<%		if(type.equals("edit")) { 
%>			<html:hidden property="question"/>
			<html:hidden property="order" />
<%		}else {
%>			<html:hidden property="module" value='<%=String.valueOf(module.getId())%>'/>
<%		}
%>	
			<html:hidden property="key"/>
			<html:hidden property="target" value='<%=target%>'/>
			<table width="600" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr class="guide1">
					<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("generic.question")%>' />
				</tr>
		      	<tr>
					<jsp:include  page="../component/spaceline.jsp" />
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
														<td align="right" width="80%">
									   						<html:text property="es_text" styleClass="input" style="width:100%;"/>
														</td>
													</tr>
						      						<tr  class="lineone"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
											  		<tr class="lineone">
														<th align="left"><%=messages.getText("question.data.name.en")%></th>
														<td align="right" width="80%">
									   						<html:text property="en_text" styleClass="input" style="width:100%;"/>     
														</td>
													</tr>
						      						<tr class="lineone"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
											  		<tr class="lineone">
														<th align="left"><%=messages.getText("question.data.name.pt")%></th>
														<td align="right" width="80%">
									   						<html:text property="pt_text" styleClass="input" style="width:100%;"/>     
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
						   						<html:file property="imageName" styleClass="input"></html:file>              
											</td>
										</tr>
			      						<tr  class="space"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
								  		<tr class="linetwo">
											<th align="left"><%=messages.getText("question.data.testtype")%></th>
											<td align="right">
	   											<html:select property="testType" styleClass="input">
									   				<html:option value='<%=String.valueOf(QuestionData.TEST_QUESTION)%>'><%=messages.getText("generic.messages.yes")%></html:option>
													<html:option value='<%=String.valueOf(QuestionData.NOT_TEST_QUESTION)%>'><%=messages.getText("generic.messages.no")%></html:option>
									   			</html:select>              
											</td>
										</tr>
			      						<tr  class="space"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
								  		<tr class="linetwo">
											<th align="left"><%=messages.getText("question.data.type")%></th>
											<td align="right">
				      							<html:select property="type" styleClass="input" onchange='<%=javascript1%>'>
					      							<html:options collection="types" property="value" labelProperty="label" styleClass="input"/>
						  						</html:select>   
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
      							<td colspan="2">
      								<div id="options" style='<%=(form.getType().equals(String.valueOf(QuestionData.EXCLUDED_OPTIONS)) || form.getType().equals(String.valueOf(QuestionData.LIST)) || form.getType().equals(String.valueOf(QuestionData.KILOMETERS)) || form.getType().equals(String.valueOf(QuestionData.INCLUDED_OPTIONS))) ? "display:block;" : "display: none;"%>' >
										<jsp:include  page='<%="../component/utilityboxtop.jsp?title="+messages.getText("question.data.options")%>' />
										<table align='center' width='100%' cellpadding="0" cellspacing="0">
									  		<tr class="linetwo">
												<th align="left"><%=messages.getText("question.data.optioncount")%></th>
												<td align="right">
							   						<html:select property="optioncount" styleClass="input"  onchange='<%=javascript2%>'>
<%							for(int i = 2; i <= 23; i++) { 
%>							   							<html:option value='<%=String.valueOf(i)%>'><%=String.valueOf(i)%></html:option>
<%							}
%>							   						</html:select>              
												</td>
											</tr>
				      						<tr  class="linetwo"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
<%							for(int i = 1; i <= 23; i++) {
    							boolean show = (i < 3);
    							if(type.equals("edit") && !show) {
    							    show = !Util.empty(form.getAnswer(i));
    							}
%>
											<html:hidden property='<%="key"+i%>' />
											<tr>
			      								<td colspan="2">
			      									<div id='<%="option"+i%>' style="display: <%=(show) ? "block" : "none" %>;">
														<table align='center' width='100%' cellpadding="0" cellspacing="0">
													  		<tr class="lineone">
													  			<td>
																	<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("question.data.option")+i%>' />
																	<table align='center' width='100%' cellpadding="0" cellspacing="0">
																  		<tr class="lineone">
																			<th align="left"><%=messages.getText("question.data.name.es")%></th>
																			<td align="right" width="80%">
											   									<html:text property='<%="es_answer"+i%>' styleClass="input" style="width:100%;"/>
																			</td>
																		</tr>
											      						<tr  class="lineone"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
																  		<tr class="lineone">
																			<th align="left"><%=messages.getText("question.data.name.en")%></th>
																			<td align="right" width="80%">
														   						<html:text property='<%="en_answer"+i%>' styleClass="input" style="width:100%;"/>     
																			</td>
																		</tr>
														      			<tr class="lineone"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
																		<tr class="lineone">
																			<th align="left"><%=messages.getText("question.data.name.pt")%></th>
																			<td align="right" width="80%">
									   											<html:text property='<%="pt_answer"+i%>' styleClass="input" style="width:100%;"/>     
																			</td>
																		</tr>
														      			<tr class="lineone"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
																		<tr class="lineone">
																			<th align="left"><%=messages.getText("question.data.resultvalue")%></th>
																			<td align="right">
									   											<html:select property='<%="answertype"+i%>' styleClass="input">
									   												<html:option value='<%=String.valueOf(AnswerData.CORRECT)%>'><%=messages.getText("question.result.correct")%></html:option>
									   												<html:option value='<%=String.valueOf(AnswerData.INCORRECT)%>'><%=messages.getText("question.result.incorrect")%></html:option>
									   											</html:select>              
																			</td>
																		</tr>
																	</table>
																	<jsp:include  page="../component/utilitybox2bottom.jsp" />
																</td>
															</tr>
															<tr class="linetwo">
																<td>&nbsp;</td>
															</tr>
														</table>	
						      						</div>
				      							</td>
	      									</tr>
<%							}				
%>										</table>	
										<jsp:include  page="../component/utilityboxbottom.jsp" />
      								</div>
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
		      		<td align="right" colspan="3">
			            <html:submit styleClass="input"><%=messages.getText("generic.messages.save")%></html:submit>
			            <html:cancel styleClass="input"><%=messages.getText("generic.messages.cancel")%></html:cancel>
		      		</td>
		      	</tr>
			</table>
		</html:form>
	</body>
<%	}	
%>
</html:html>
