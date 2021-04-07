<%@page language="java"
	import="assesment.business.*"
	import="assesment.communication.language.*"
	import="assesment.communication.tag.*"
	import="assesment.communication.security.*"
	import="assesment.communication.administration.corporation.tables.*"
	import="assesment.communication.corporation.*"
	import="assesment.communication.assesment.*"
	import="assesment.communication.module.*"
	import="assesment.communication.question.*"
	import="assesment.business.assesment.AssesmentReportFacade"
	import="assesment.communication.report.UsersReportDataSource"
	import="assesment.presentation.translator.web.util.*"
	import="java.util.*"
	import="java.lang.*"
	errorPage="../exception.jsp"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>


<html:html>

<LINK REL=StyleSheet HREF="../util/css/estilo.css" TYPE="text/css">

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
	function confirmDeleteModule(form,message,id) {
		if(confirm(message)) {
			form.module.value = id;
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
	function feedback(email,form) {
		form.email.value = email;
		form.submit();
	}
	function createLink(value) {
		var form = document.forms['AssesmentLinkForm'];
		form.language.value = value;
		form.submit();
	}
</script>

<script language="JavaScript" src="./css/create_functions.js" type="text/javascript">
</script>


<%!	AssesmentData data;
   	AssesmentAccess sys; 
   	Text messages;
   	TextBKP messagesbkp;

%>
<%
	sys = (AssesmentAccess)session.getAttribute("AssesmentAccess"); 
	sys.loadTextBKP();
	String check = Util.checkPermission(sys,SecurityConstants.ADMINISTRATOR);
	if(check!=null) {
		response.sendRedirect(request.getContextPath()+check);
	}else {

	    session.setAttribute("url","assesment/view.jsp");

	    RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
		dispatcher.include(request,response);
		
		messages=sys.getText();
		messagesbkp=sys.getTextbkp();

		Integer id = null;
		if(!Util.empty(request.getParameter("assesment"))) {
			id = new Integer(request.getParameter("assesment"));
		}else {
			id = new Integer((String)session.getAttribute("assesment"));
		}
		
		AssesmentReportFacade report = sys.getAssesmentReportFacade();
		if(id != null){
			data = report.findAssesmentbkp(id,sys.getUserSessionData());
			String corporationName = data.getCorporationName();
			String[] texts = sys.getLanguageReportFacade().findTextsbkp(data.getName(),sys.getUserSessionData());
%>

<head/>

	<body>
		<form action="./layout.jsp?refer=/assesment/list.jsp" name='back' method="post">
		</form>	
		<form action="./layout.jsp?refer=/assesment/create.jsp" name='edit' method="post">
			<input type="hidden" name="assesment" 		value='<%=String.valueOf(id)%>' />
			<input type="hidden" name="type" 	value="edit" />
		</form>	
		<form action="./layout.jsp?refer=/module/create.jsp" name='module' method="post">
			<input type="hidden" name="assesment" 		value='<%=String.valueOf(id)%>' />
			<input type="hidden" name="type" 			value="create" />
		</form>	
		<form action="./layout.jsp?refer=/module/selectlist.jsp" name='import_module' method="post">
			<input type="hidden" name="assesment" 		value='<%=String.valueOf(id)%>' />
		</form>	
		<form action="./layout.jsp?refer=/assesment/feedback.jsp" name='create_feedback' method="post">
			<input type="hidden" name="assesment" 		value='<%=String.valueOf(id)%>' />
			<input type="hidden" name="type" 			value="create" />
		</form>	
		<form action="./layout.jsp?refer=/assesment/users.jsp" name='detail' method="post">
			<input type="hidden" name="assesment" 		value='<%=String.valueOf(id)%>' />
		</form>	
		<form action="./report.jsp" name='wrt' method="post" target="_blank">
			<input type="hidden" name="id" 		value='<%=String.valueOf(id)%>' />
		</form>	
		<form action="./layout.jsp?refer=/assesment/feedback.jsp" name='modify_feedback' method="post">
			<input type="hidden" name="assesment" 		value='<%=String.valueOf(id)%>' />
			<input type="hidden" name="email"/>
			<input type="hidden" name="type" 			value="edit" />
		</form>	
		<form action="./layout.jsp?refer=/assesment/tags.jsp" name='associate_tag' method="post">
			<input type="hidden" name="assesment" 		value='<%=String.valueOf(id)%>' />
		</form>	
		<html:form action="/DeleteFeedback" >
			<input type="hidden" name="assesment" 		value='<%=String.valueOf(id)%>' />
			<input type="hidden" name="email"/>
	  	</html:form>
		<html:form action="/AssesmentDelete" >
			<input type="hidden" name="assesment" 		value='<%=String.valueOf(id)%>' />
	  	</html:form>
		<html:form action="/ModuleDelete" >
			<input type="hidden" name="module" 			/>
			<input type="hidden" name="assesment" 		value='<%=String.valueOf(id)%>' />
			<input type="hidden" name="target" 			value="assesment" />
	  	</html:form>
		<form action="./layout.jsp?refer=/module/changeOrder.jsp" name='order' method="post">
			<input type="hidden" name="assesment" 		value='<%=String.valueOf(id)%>' />
		</form>	
		<html:form action="/DeleteAssesmentTag">
			<html:hidden property="assesment" value='<%=String.valueOf(id)%>' />
			<html:hidden property="tags" />
		</html:form>
		<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("generic.assesment")+" "+messages.getText(data.getName())%>' />
<%		if(data != null && data.isArchived()) {
%>			<tr>
				<td class="red" colspan="3"><%=messages.getText("messages.archivedassesment")%></td>
			</tr>
<%		}
%>	  		<tr>
    			<td width="80%" valign="top">
					<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.assesment.data")%>' />
    				<table width="100%" border="0" align="center" cellpadding="5" cellspacing="5">
						<tr class="line">
							<td align="left"><%=messages.getText("assesment.data.name")+" ("+messages.getText("es")+")"%></td>
							<td align="right">
						   		<%=texts[0]%>              
							</td>
				        </tr>
						<tr class="line">
							<td align="left"><%=messages.getText("assesment.data.name")+" ("+messages.getText("en")+")"%></td>
							<td align="right">
						   		<%=texts[1]%>              
							</td>
				        </tr>
						<tr class="line">
							<td align="left"><%=messages.getText("assesment.data.name")+" ("+messages.getText("pt")+")"%></td>
							<td align="right">
						   		<%=texts[2]%>              
							</td>
				        </tr>
						<tr class="line">
							<td align="left">
								<%=messages.getText("assesment.data.start")%>
							</td>
							<td align="right">
	       						<%=Util.formatDate(data.getStart())%>
          					</td>
						</tr>
						<tr class="line">
							<td align="left">
								<%=messages.getText("assesment.data.end")%>
          					</td>
							<td align="right">
	       						<%=Util.formatDate(data.getEnd())%>
          					</td>
          				</tr>
						<tr class="line">
							<td align="left">
								<%=messages.getText("assesment.data.status")%>
							</td>
	       					<td align="right">
	       						<%=messages.getText(data.getStatusName())%>
          					</td>
          				</tr>
						<tr class="line">
    						<td align="left"> 
    							<%=messages.getText("assesment.wrt.showemail")%>
							</td>
	       					<td align="right">
						   		<%=(data.isShowEmailWRT()) ? messages.getText("generic.messages.yes") : messages.getText("generic.messages.no")%>              
		  					</td>
          				</tr>
						<tr class="line">
    						<td align="left"> 
    							<%=messages.getText("assesment.module.psicologic")%>
							</td>
	       					<td align="right">
						   		<%=(data.isPsitest()) ? messages.getText("generic.messages.yes") : messages.getText("generic.messages.no")%>              
		  					</td>
      					</tr>
						<tr class="line">
    						<td align="left"> 
    							<%=messages.getText("assesment.module.repeatable")%>
							</td>
	       					<td align="right">
						   		<%=(data.isUntilApproved()) ? messages.getText("generic.messages.yes") : messages.getText("generic.messages.no")%>              
		  					</td>
      					</tr>
					</table>
					<jsp:include page="../component/utilitybox2bottom.jsp" />
				</td>
    			<td width="20%" valign="top" >
					<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("assesment.data.corporation")%>' />
    				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" height="150">
						<tr class="line">
							<td align="left">
       							<%=corporationName%>
          					</td>
				        </tr class="line">
						<tr>
							<td align="center">
								<img src='<%="../flash/images/logo"+data.getCorporationId()+".png"%>' width="150">
							</td>
						</tr>
					</table>
					<jsp:include page="../component/utilitybox2bottom.jsp" />
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("assesment.data.description")%>' />
					<table width="100%" cellpadding="0" cellspacing="0">	
						<tr class="line">
							<td align="left" colspan="2">
						   		<%=data.getDescription()%>              
							</td>
						</tr>
					</table>
					<jsp:include page="../component/utilitybox2bottom.jsp" />
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("assesment.data.certificate")%>' />
					<table width="100%" cellpadding="0" cellspacing="0">	
<%			if(!data.isCertificate()) {
%>						<tr>
							<td align="left" class="line">
								<%=messages.getText("generic.messages.no")%>              
							</td>
						</tr>	
<%			}else {
				if(((data.getCertificateImageES() != null && !data.getCertificateImageES().equals(""))
					|| (data.getCertificateImageEN() != null && !data.getCertificateImageEN().equals(""))
					|| (data.getCertificateImagePT() != null && !data.getCertificateImagePT().equals("")))) {
%>									
						<tr class="line">
							<td align="left">
								<%=messages.getText("assessment.certificate.images")%>              
							</td>
						</tr>	
					  	<tr  class="line">
					      	<td colspan="2">
				      			<table width="100%" cellpadding="2" cellspacing="2">
									<tr valign="top">
										<td class="line" align="center" width="33%">
											<%=messages.getText("assesment.data.certificate.es")%>
<%					if(data.getCertificateImageES() != null && !data.getCertificateImageES().equals("")) {
%>											<br>
											<img src='<%="../flash/images/"+data.getCertificateImageES() %>' width="200">														
<%					}
%>										</td>
										<td class="line" align="center" width="33%">
											<%=messages.getText("assesment.data.certificate.en")%>
<%					if(data.getCertificateImageEN() != null && !data.getCertificateImageEN().equals("")) {
%>											<br>
											<img src='<%="../flash/images/"+data.getCertificateImageEN() %>' width="200">														
<%					}
%>										</td>
										<td class="line" align="center" width="33%">
											<%=messages.getText("assesment.data.certificate.pt")%>
<%					if(data.getCertificateImagePT() != null && !data.getCertificateImagePT().equals("")) {
%>											<br>
											<img src='<%="../flash/images/"+data.getCertificateImagePT() %>' width="200">														
<%					}
%>										</td>
									</tr>
	      						</table>
		      				</td>
		      			</tr>
<%				}else {
%>						<tr height="30">
							<td align="left" class="line">
								<%=messages.getText("assesment.data.certificate")%>&nbsp;
								<%=messages.getText("assessment.certificate.default")%>              
							</td>
						</tr>	
<%				}
			}	
%>					</table>
					<jsp:include  page="../component/utilitybox2bottom.jsp" />
				</td>
			</tr>				
	  		<tr>
    			<td colspan="2">
    				<table width="100%" border="0" align="center" cellpadding="2" cellspacing="2">
    					<tr>
    						<td width="50%" valign="top">
								<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.assesment.resultrange")%>' />
			    				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" height="100">
									<tr class="line">
			    						<td align="left"> <%=messages.getText("report.generalresult.lowlevel")%></td>
				   						<td align="right">
									   		<%="0% - "+String.valueOf(data.getYellow())+"%"%>              
					  					</td>
			      					</tr>
									<tr class="line">
			    						<td align="left"> <%=messages.getText("report.generalresult.meddiumlevel")%></td>
				   						<td align="right">
									   		<%=String.valueOf(data.getYellow())+"% - "+String.valueOf(data.getGreen())+"%"%>              
					  					</td>
			      					</tr>
									<tr class="line">
			    						<td align="left"> <%=messages.getText("report.generalresult.highlevel")%></td>
				   						<td align="right">
									   		<%=String.valueOf(data.getGreen())+"% - 100%"%>              
					  					</td>
			      					</tr>
			      				</table>
								<jsp:include page="../component/utilitybox2bottom.jsp" />
			      			</td>
    						<td width="50%" valign="top">
								<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("assesment.data.attach")%>' />
								<table width="100%" cellpadding="0" cellspacing="0">	
									<tr class="line" style="height: 40px;">
										<td align="left"><%=messages.getText("es")%></td>
										<td align="right">
											<%=(data.isAttachesPDF()) ? messages.getText("generic.messages.yes") : messages.getText("generic.messages.no") %>
										</td>
									</tr>
									<tr class="line" style="height: 40px;">
										<td align="left"><%=messages.getText("en")%></td>
										<td align="right">
											<%=(data.isAttachenPDF()) ? messages.getText("generic.messages.yes") : messages.getText("generic.messages.no") %>
										</td>
									</tr>
									<tr class="line" style="height: 40px;">
										<td align="left"><%=messages.getText("pt")%></th>
										<td align="right">
											<%=(data.isAttachptPDF()) ? messages.getText("generic.messages.yes") : messages.getText("generic.messages.no") %>
										</td>
									</tr>
								</table>	
								<jsp:include  page="../component/utilitybox2bottom.jsp" />
			      			</td>
			      		</tr>
	  		<tr>
    			<td colspan="2">
					<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.assesment.feedback")%>' />
    				<table width="100%" border="0" align="center" cellpadding="2" cellspacing="2">
    					<tr class="line">
			    			<td align="left"> 
			    				<%=messages.getText("assesment.data.reportfeedback")%>:&nbsp;
								<%=(data.isReportFeedback()) ? messages.getText("generic.messages.yes") : messages.getText("generic.messages.no")%>              
					  		</td>
			      		</tr>
			      		<tr>
							<td>
								<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			      					<tr>
					    				<td class="guide2" width="100%"><%=messages.getText("generic.assesment.feedback")%></td>
					    			</tr>
<%			Iterator feedback = report.getAssesmentFeedback(id,sys.getUserSessionData()).iterator();
			if(!feedback.hasNext()) {
%>			      					<tr class='linetwo'>
										<td align="left">
											<%=messages.getText("assessment.nofeedback")%>
										</td>
									</tr>
<%			}else {
				boolean linetwo = false;
				while(feedback.hasNext()) {
				    FeedbackData data = (FeedbackData)feedback.next();
				    linetwo = !linetwo;		
%>			      					<tr class='<%=(linetwo)?"linetwo":"lineone"%>'>
										<td align="left">
											<%=data.getEmail()%>
										</td>
									</tr>
<%				}
			}
%>    							</table>
							</td>
						</tr>
					</table>
					<jsp:include page="../component/utilitybox2bottom.jsp" />
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("assesment.data.modules")%>' />
						<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<%			if(data.getModules().size() == 0) { 
%>			      			<tr>
					    		<td class="line"><%=messages.getText("generic.messages.notresult")%></td>
							</tr>
<%			}else {
    			Iterator moduleIt = data.getModuleIterator();
    			while(moduleIt.hasNext()) {
    			    ModuleData module = (ModuleData)moduleIt.next();
%>			      			<tr>
					    		<td class="line">
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr class="line">
											<td align="left">
    											<a href='<%="./layout.jsp?refer=/module/view.jsp?module="+String.valueOf(module.getId())%>'>
													<span style="color:#333333; font-size:18;"><%=" - "+messagesbkp.getText(module.getKey())%><span>
												</a>
											</td>
										</tr>
   										<tr>
    										<td>
    											<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<%					switch(module.getQuestions().size()) {
						case 0:
%>    												<tr  class="line">
														<td align="left" colspan="3"><%="   "+messages.getText("module.data.withoutquestions")%></td>
													</tr>
<%							break;
						case 1:
							String linkShow = "javascript:showQuestions('questionsResume"+String.valueOf(module.getId())+"','questionsDetail"+String.valueOf(module.getId())+"');";
%>    												<tr class="line">
														<td align="left" colspan="3">
															<div id='<%="questionsResume"+String.valueOf(module.getId())%>' style="display: block;">
						    									<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						    										<tr class="line">
																		<td align="left"><%="   "+messages.getText("module.data.onequestion")%></td>
																		<td align="right"><a href="<%=linkShow%>"><%=messages.getText("module.data.questions.view")%></a></td>
																	</tr>	
																</table>
															</div>
															<div id='<%="questionsDetail"+String.valueOf(module.getId())%>' style="display: none;">
						    									<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<%							Iterator it = module.getQuestionIterator();
							while(it.hasNext()) {
							    QuestionData question = (QuestionData)it.next();
							    String linkHide = "javascript:hideQuestions('questionsResume"+String.valueOf(module.getId())+"','questionsDetail"+String.valueOf(module.getId())+"');";
%>																	<tr class="line">
																		<td align="left" width="70%">
																			<a href='<%="./layout.jsp?refer=/question/view.jsp?question="+String.valueOf(question.getId())+"&target=assesment"%>' >
																				<%="- "+question.getKey(messagesbkp)%>
																			</a>
																		</td>
																		<td align="right" width="30%">
																			<a href="<%=linkHide%>"><%=messages.getText("module.data.questions.hide")%></a>
																		</td>
																	</tr>
<%							}
%>																</table>
															</div>
														</td>
													</tr>
<%							break;
						default:
							linkShow = "javascript:showQuestions('questionsResume"+String.valueOf(module.getId())+"','questionsDetail"+String.valueOf(module.getId())+"');";
%>    												<tr  class="line">
														<td align="left" colspan="3">
															<div id='<%="questionsResume"+String.valueOf(module.getId())%>' style="display: block;">
						    									<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						    										<tr class="line">
																		<td align="left" style="font-size:12;"><%="   "+String.valueOf(module.getQuestions().size())+" "+messages.getText("module.data.asociatedquestion")%></td>
																		<td align="right">
																			<a href="<%=linkShow%>">
																				<span style="font-size:12;"><%=messages.getText("module.data.questions.view")%></span>
																			</a>
																		</td>
																	</tr>	
																</table>
															</div>
															<div id='<%="questionsDetail"+String.valueOf(module.getId())%>' style="display: none;">
						    									<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<%							it = module.getQuestionIterator();
							boolean hide = true;
							while(it.hasNext()) {
							    QuestionData question = (QuestionData)it.next();			
%>																	<tr class="line">
<%								if(hide) {
    								hide = false;
    								String linkHide = "javascript:hideQuestions('questionsResume"+String.valueOf(module.getId())+"','questionsDetail"+String.valueOf(module.getId())+"');";
%>																		<td align="left" width="70%">
																			<a href='<%="./layout.jsp?refer=/question/view.jsp?question="+String.valueOf(question.getId())+"&module=assesment"%>' >
																				<%="   - "+question.getKey(messages)%>
																			</a>
																		</td>
																		<td align="right" width="30%">
																			<a href="<%=linkHide%>">
																				<span style="font-size:12;">
																					<%=messages.getText("module.data.questions.hide")%>
																				</span>
																			</a>
																		</td>
<%								}else {	
%>																		<td align="left" width="95%" colspan="2">
																			<a href='<%="./layout.jsp?refer=/question/view.jsp?question="+String.valueOf(question.getId())+"&module=assesment"%>' >
																				<%="   - "+question.getKey(messages)%>
																			</a>
																		</td>
<%								}
%>																	</tr>
<%							}
%>																</table>
															</div>
														</td>
													</tr>
<%						}
						if(data.getStatus().intValue() == AssesmentAttributes.EDITABLE) {
%>		
													<tr class="line">
			    										<td align="right" colspan="3">
			    											<a href='<%="./layout.jsp?refer=/module/selectlist2.jsp?module="+String.valueOf(module.getId())+"&target=assesment"%>'>
				    											<input type=button class="input" value='<%=messages.getText("module.action.existingquestion")%>' />
				    										</a>
			    											<a href='<%="./layout.jsp?refer=/question/create.jsp?type=create&module="+String.valueOf(module.getId())+"&target=assesment"%>'>
							    								<input type=button class="input" value='<%=messages.getText("module.action.newquestion")%>' />
			    											</a>
			   												<input type=button class="input" onclick='<%="javascript:confirmDeleteModule(document.forms['ModuleDeleteForm'],'"+messages.getText("module.delete.confirm")+"',"+String.valueOf(module.getId())+");"%>' value='<%=messages.getText("module.messages.delete")%>' />
			    										</td>
			    									</tr>
<%					}
%>												</table>
											</td>
										</tr>
									</table>
								</td>
							</tr>
<%    			}
			}
%>
						</table>
					<jsp:include page="../component/utilitybox2bottom.jsp" />
				</td>
			</tr>
			<tr>	
<%
        UsersReportDataSource dataSource = sys.getUserReportFacade().findUsersAssesmentbkp(new Integer(id),sys.getUserSessionData(),messages);
		int all = dataSource.getTotal();
%>  			<td valign="top" colspan="2">
					<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.data.users")%>' />
    				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" height="100">
    					<tr valign="top" class="line">
    						<td align="left"><%=messages.getText("report.users.total.count")%></td>
    						<td align="right"><%=all%></td>
    					</tr>			    					
<%		if(all > 0) {
%>    					<tr class="line">
    						<td align="left"><%=messages.getText("report.users.total.pending")%></td>
    						<td align="right"><%=dataSource.getNone()+dataSource.getPart()%></td>
    					</tr>			    					
    					<tr class="line">
    						<td align="left"><%=messages.getText("report.users.total.finalized")%></td>
    						<td align="right"><%=dataSource.getAll()%></td>
    					</tr>			    					
<%		}
%>			    	</table>
					<jsp:include page="../component/utilitybox2bottom.jsp" />
			 	</td>
			 </tr>
		<jsp:include  page='../component/titleend.jsp' />
	</body>
<%		}
	}
%>  			
</html:html>

