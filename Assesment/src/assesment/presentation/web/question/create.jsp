<%@page import="assesment.business.question.QuestionReportFacade"%>
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
		if(target == null)
			target = "assessment";

		ModuleAttribute module = null;

		Collection esp = new LinkedList();
		esp.add(new OptionItem(messages.getText("generic.messages.empty"),""));
		esp.add(new OptionItem(messages.getText("question.file.new"),"new"));
		Collection eng = new LinkedList();
		eng.add(new OptionItem(messages.getText("generic.messages.empty"),""));
		eng.add(new OptionItem(messages.getText("question.file.new"),"new"));
		Collection por = new LinkedList();
		por.add(new OptionItem(messages.getText("generic.messages.empty"),""));
		por.add(new OptionItem(messages.getText("question.file.new"),"new"));

		QuestionReportFacade questionFacade = sys.getQuestionReportFacade();
		Collection<VideoData> videos = questionFacade.getVideos(sys.getUserSessionData());
		Collections.sort((List)videos);
		Iterator<VideoData> it = videos.iterator();
		while(it.hasNext()) {
			VideoData video = it.next();
			if(video.getLanguage().equals("es")) {
				esp.add(new OptionItem(video.getKey(),String.valueOf(video.getId())));
			} else if(video.getLanguage().equals("en")) {
				eng.add(new OptionItem(video.getKey(),String.valueOf(video.getId())));
			} else {
				por.add(new OptionItem(video.getKey(),String.valueOf(video.getId())));
			} 
		}
		session.setAttribute("esp",esp);
		session.setAttribute("eng",eng);
		session.setAttribute("por",por);
		
		QuestionForm form = null;
		boolean video = false;
		if(type.equals("create")) {
			String moduleId = request.getParameter("module");
			if(moduleId == null)
				moduleId = (String) session.getAttribute("moduleId");
		    module = sys.getModuleReportFacade().findModuleAttributes(new Integer(moduleId),sys.getUserSessionData());
		    form = new QuestionForm(String.valueOf(module.getId()),target);
		}else {
		    QuestionData question = questionFacade.findQuestion(new Integer(id),sys.getUserSessionData(),QuestionData.NORMAL);
		    module = sys.getModuleReportFacade().findModuleAttributes(question.getModule(),sys.getUserSessionData());
		    form = new QuestionForm(question,sys,target);
		    session.setAttribute("QuestionUpdateForm",form);
		    video = question.getType().intValue() == QuestionData.VIDEO;
		}
		
		String actionName = (type.equals("create")) ? "/QuestionCreate" : "/QuestionUpdate";
		String formName = (type.equals("create")) ? "QuestionCreateForm" : "QuestionUpdateForm";
		
		Collection types = new LinkedList();
		types.add(new OptionItem(messages.getText("generic.messages.select"),""));
		types.add(new OptionItem(messages.getText("question.type.text"),String.valueOf(QuestionData.TEXT)));
		types.add(new OptionItem(messages.getText("question.type.date"),String.valueOf(QuestionData.DATE)));
		types.add(new OptionItem(messages.getText("question.type.birthdate"),String.valueOf(QuestionData.BIRTHDATE)));
		types.add(new OptionItem(messages.getText("question.type.excludedoptions"),String.valueOf(QuestionData.EXCLUDED_OPTIONS)));
		types.add(new OptionItem(messages.getText("question.type.list"),String.valueOf(QuestionData.LIST)));	
		types.add(new OptionItem(messages.getText("question.type.kilometers"),String.valueOf(QuestionData.KILOMETERS)));
		types.add(new OptionItem(messages.getText("question.type.optionaldate"),String.valueOf(QuestionData.OPTIONAL_DATE)));
		types.add(new OptionItem(messages.getText("question.type.optionaldatena"),String.valueOf(QuestionData.OPTIONAL_DATE_NA)));
		types.add(new OptionItem(messages.getText("question.type.includedoptions"),String.valueOf(QuestionData.INCLUDED_OPTIONS)));
		types.add(new OptionItem(messages.getText("question.type.email"),String.valueOf(QuestionData.EMAIL)));
		types.add(new OptionItem(messages.getText("question.type.country"),String.valueOf(QuestionData.COUNTRY)));
		types.add(new OptionItem(messages.getText("question.type.textarea"),String.valueOf(QuestionData.TEXTAREA)));
		types.add(new OptionItem(messages.getText("question.type.youtube"),String.valueOf(QuestionData.YOU_TUBE_VIDEO)));
		types.add(new OptionItem(messages.getText("question.type.video"),String.valueOf(QuestionData.VIDEO)));
		session.setAttribute("types",types);

		
		String javascript1 = (type.equals("create")) ? "showOptions(document.forms['QuestionCreateForm'])" : "showOptions(document.forms['QuestionUpdateForm'])";
		String javascript2 = (type.equals("create")) ? "optionChange(document.forms['QuestionCreateForm'])" : "optionChange(document.forms['QuestionUpdateForm'])";
%>		
	<head/>
	<script type="text/javascript">
		function showFileOptions(combo, div) {
			var form = document.forms['<%=formName%>'];
			var comboObj = form.elements[combo];
			var divObj = document.getElementById(div);
			if(comboObj.selectedIndex == 1) {
				divObj.style.display = 'block';
			}else {
				divObj.style.display = 'none';
			}
		}
		function showOptions(form) {
			if(form.type.options.selectedIndex == 4 || form.type.options.selectedIndex == 5 || form.type.options.selectedIndex == 9) {
				document.getElementById('options').style.display = 'block';
			}else {
				document.getElementById('options').style.display = 'none';
			}
			if(form.type.options.selectedIndex == 13) {
				document.getElementById('fileUploadStr').style.display = 'none';
				document.getElementById('fileUpload').style.display = 'none';
			}else {
				document.getElementById('fileUploadStr').style.display = 'block';
				document.getElementById('fileUpload').style.display = 'block';
			}				
			if(form.type.options.selectedIndex == 14) {
				document.getElementById('videos').style.display = 'block';
				document.getElementById('textos').style.display = 'none';
			}else {
				document.getElementById('videos').style.display = 'none';
				document.getElementById('textos').style.display = 'block';
			}
		}
		function optionChange(form) {
			for(i = 3; i < 31; i++) {
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
		<html:form action='<%=actionName%>'  enctype="multipart/form-data">
<%		if(type.equals("edit")) { 
%>			<html:hidden property="question" />
			<html:hidden property="order" />
			<html:hidden property="group" />
<%		}else {
%>			<html:hidden property="module" value='<%=String.valueOf(module.getId())%>'/>
			<html:hidden property="group" value="0" />
<%		}
%>	
			<html:hidden property="key" />
			<html:hidden property="target" value='<%=target%>'/>
			<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("generic.question")%>' />
		      	<tr>
		      		<td>
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.question.data")%>' />
							<table align='center' width='100%' cellpadding="0" cellspacing="0">
						  		<tr class="line">
									<td align="left"><%=messages.getText("question.data.module")%></td>
									<td align="right">
				   						<%=messages.getText(module.getKey())%>              
									</td>
								</tr>
						  		<tr class="line">
									<td align="left"><%=messages.getText("question.data.text")+" ("+messages.getText("question.data.name.es")+")"%></td>
									<td align="right">
				   						<html:text property="es_text" styleClass="input" style="width:500px;"/>
									</td>
								</tr>
						  		<tr class="line">
									<td align="left"><%=messages.getText("question.data.text")+" ("+messages.getText("question.data.name.en")+")"%></td>
									<td align="right">
				   						<html:text property="en_text" styleClass="input" style="width:500px;"/>     
									</td>
								</tr>
						  		<tr class="line">
									<td align="left"><%=messages.getText("question.data.text")+" ("+messages.getText("question.data.name.pt")+")"%></td>
									<td align="right">
				   						<html:text property="pt_text" styleClass="input" style="width:500px;"/>     
									</td>
								</tr>
						      	<tr>
						      		<td colspan="2">
							      		<div id="videos"  <%=(video) ? "" : "style='display:none;'"%>>
											<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("question.data.videos")%>' />
												<table align='center' width='100%' cellpadding="0" cellspacing="0">
											  		<tr class="line">
														<td align="left"><%=messages.getText("question.data.video.es")%></td>
														<td align="right">
							      							<html:select property="es_video" styleClass="input" onchange="showFileOptions('es_video','new_video_es')">
								      							<html:options collection="esp" property="value" labelProperty="label" styleClass="input"/>
									  						</html:select>   
														</td>
													</tr>
											  		<tr class="line">
											  			<td colspan="2">
											  				<div id="new_video_es" style="display: none;">
																<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("question.data.newvideo.es")%>' />
																<table align='center' width='100%' cellpadding="0" cellspacing="0">
															  		<tr class="line" style="height: 40px;">
																		<td align="left"><%=messages.getText("question.video.file")%></td>
																		<td align="right">
												   							<html:file property="es_videoFile" styleClass="line"></html:file>
												   						</td>
																	</tr>
															  		<tr class="line">
																		<td align="left"><%=messages.getText("question.video.descripcion")%></td>
																		<td align="right">
													   						<html:text property="es_videoKey" styleClass="input" style="width:300px;"/>     
																		</td>
																	</tr>
																</table>
																<jsp:include  page="../component/utilitybox2bottom.jsp" />
															</div>
														</td>
													</tr>
											  		<tr class="line">
														<td align="left"><%=messages.getText("question.data.video.en")%></td>
														<td align="right">
							      							<html:select property="en_video" styleClass="input" onchange="showFileOptions('en_video','new_video_en')">
								      							<html:options collection="eng" property="value" labelProperty="label" styleClass="input"/>
									  						</html:select>   
														</td>
													</tr>
											  		<tr class="line">
											  			<td colspan="2">
											  				<div id="new_video_en" style="display: none;">
																<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("question.data.newvideo.en")%>' />
																<table align='center' width='100%' cellpadding="0" cellspacing="0">
															  		<tr class="line" style="height: 40px;">
																		<td align="left"><%=messages.getText("question.video.file")%></td>
																		<td align="right">
												   							<html:file property="en_videoFile" styleClass="line"></html:file>
												   						</td>
																	</tr>
															  		<tr class="line">
																		<td align="left"><%=messages.getText("question.video.descripcion")%></td>
																		<td align="right">
													   						<html:text property="en_videoKey" styleClass="input" style="width:300px;"/>     
																		</td>
																	</tr>
																</table>
																<jsp:include  page="../component/utilitybox2bottom.jsp" />
															</div>
														</td>
													</tr>
											  		<tr class="line">
														<td align="left"><%=messages.getText("question.data.video.pt")%></td>
														<td align="right">
							      							<html:select property="pt_video" styleClass="input" onchange="showFileOptions('pt_video','new_video_pt')">
								      							<html:options collection="por" property="value" labelProperty="label" styleClass="input"/>
									  						</html:select>   
														</td>
													</tr>
											  		<tr class="line">
											  			<td colspan="2">
											  				<div id="new_video_pt" style="display: none;">
																<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("question.data.newvideo.pt")%>' />
																<table align='center' width='100%' cellpadding="0" cellspacing="0">
															  		<tr class="line" style="height: 40px;">
																		<td align="left"><%=messages.getText("question.video.file")%></td>
																		<td align="right">
												   							<html:file property="pt_videoFile" styleClass="line"></html:file>
												   						</td>
																	</tr>
															  		<tr class="line">
																		<td align="left"><%=messages.getText("question.video.descripcion")%></td>
																		<td align="right">
													   						<html:text property="pt_videoKey" styleClass="input" style="width:300px;"/>     
																		</td>
																	</tr>
																</table>
																<jsp:include  page="../component/utilitybox2bottom.jsp" />
															</div>
														</td>
													</tr>
												</table>
											<jsp:include  page="../component/utilitybox2bottom.jsp" />
										</div>
									</td>
							 	</tr>
						  		<tr class="line" style="height: 40px;">
									<td align="left">
										<div id="fileUploadStr">
											<%=messages.getText("question.data.image")%>
				   						</div>              
									</td>
									<td align="right">
										<div id="fileUpload">
				   							<html:file property="imageName" styleClass="line"></html:file>
				   						</div>              
									</td>
								</tr>
						  		<tr class="line">
									<td align="left"><%=messages.getText("question.data.testtype")%></td>
									<td align="right">
										<html:select property="testType" styleClass="input">
							   				<html:option value='<%=String.valueOf(QuestionData.TEST_QUESTION)%>'><%=messages.getText("generic.messages.yes")%></html:option>
											<html:option value='<%=String.valueOf(QuestionData.NOT_TEST_QUESTION)%>'><%=messages.getText("generic.messages.no")%></html:option>
							   			</html:select>              
									</td>
								</tr>
						  		<tr class="line">
									<td align="left"><%=messages.getText("question.data.showwrt")%></td>
									<td align="right">
													<html:select property="wrt" styleClass="input">
											<html:option value='false'><%=messages.getText("generic.messages.no")%></html:option>
							   				<html:option value='true'><%=messages.getText("generic.messages.yes")%></html:option>
							   			</html:select>              
									</td>
								</tr>
						  		<tr class="line">
									<td align="left"><%=messages.getText("question.data.type")%></td>
									<td align="right">
				    							<html:select property="type" styleClass="input" onchange='<%=javascript1%>'>
				     							<html:options collection="types" property="value" labelProperty="label" styleClass="input"/>
				  						</html:select>   
									</td>
								</tr>
							</table>
						<jsp:include  page="../component/utilitybox2bottom.jsp" />
					</td>
				</tr>
    			<tr>
    				<td>
    					<div id="options" style='<%=(form.getType().equals(String.valueOf(QuestionData.EXCLUDED_OPTIONS)) || form.getType().equals(String.valueOf(QuestionData.LIST)) || form.getType().equals(String.valueOf(QuestionData.KILOMETERS)) || form.getType().equals(String.valueOf(QuestionData.INCLUDED_OPTIONS))) ? "display:block;" : "display: none;"%>' >
							<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("question.data.options")%>' />
								<table align='center' width='100%' cellpadding="0" cellspacing="0">
									<tr class="line">
										<td align="left"><%=messages.getText("question.data.optioncount")%></td>
										<td align="right">
							   				<html:select property="optioncount" styleClass="input"  onchange='<%=javascript2%>'>
<%							for(int i = 2; i <= 30; i++) { 
%>							   					<html:option value='<%=String.valueOf(i)%>'><%=String.valueOf(i)%></html:option>
<%							}
%>							   				</html:select>              
										</td>
									</tr>
<%							for(int i = 1; i <= 30; i++) {
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
											  		<tr class="line">
											  			<td>
															<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("question.data.option")+i%>' />
															<table align='center' width='100%' cellpadding="0" cellspacing="0">
														  		<tr class="line">
																	<td align="left"><%=messages.getText("question.data.name.es")%></td>
																	<td align="right" width="80%">
									   									<html:text property='<%="es_answer"+i%>' styleClass="input" style="width:100%;"/>     
																	</td>
																</tr>
														  		<tr class="line">
																	<td align="left"><%=messages.getText("question.data.name.en")%></td>
																	<td align="right" width="80%">
												   						<html:text property='<%="en_answer"+i%>' styleClass="input" style="width:100%;"/>     
																	</td>
																</tr>
																<tr class="line">
																	<td align="left"><%=messages.getText("question.data.name.pt")%></td>
																	<td align="right" width="80%">
							   											<html:text property='<%="pt_answer"+i%>' styleClass="input" style="width:100%;"/>
																	</td>
																</tr>
																<tr class="line">
																	<td align="left"><%=messages.getText("question.data.resultvalue")%></td>
																	<td align="right">
							   											<html:select property='<%="answertype"+i%>' styleClass="input">
							   												<html:option value='<%=String.valueOf(AnswerData.CORRECT)%>'><%=messages.getText("question.result.correct")%></html:option>
							   												<html:option value='<%=String.valueOf(AnswerData.INCORRECT)%>'><%=messages.getText("question.result.incorrect")%></html:option>
							   											</html:select>              
																	</td>
																</tr>
																<tr class="line">
																	<td align="left"><%=messages.getText("generic.messages.points")%></td>
																	<td align="right">
							   											<html:text property='<%="answerpoint"+i%>' styleClass="input" style="width:20%;"/>
																	</td>
																</tr>
															</table>
															<jsp:include  page="../component/utilitybox2bottom.jsp" />
														</td>
													</tr>
												</table>	
				      						</div>
		      							</td>
     								</tr>
<%							}				
%>								</table>	
							<jsp:include  page="../component/utilitybox2bottom.jsp" />
      					</div>
					</td>
				</tr>
		      	<tr>
		      		<td align="right" colspan="3">
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
