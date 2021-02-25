<%@page import="assesment.communication.util.ListResult"%>
<%@page language="java"
	import="assesment.business.*"
	import="java.util.*"
	import="assesment.communication.language.*"
	import="assesment.communication.security.*"
	import="assesment.communication.assesment.*"
	import="assesment.communication.module.*"
	import="assesment.presentation.translator.web.util.*"
	import="assesment.communication.corporation.*"
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
		String type = request.getParameter("type");
		session.setAttribute("url","assesment/createGroup.jsp?type="+type);

		RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
		dispatcher.include(request,response);
	
		Text messages=sys.getText();

		String id = request.getParameter("id");
		if(id == null) {
			id = (String)session.getAttribute("group");

		}

		GroupData group = sys.getAssesmentReportFacade().findGroup(new Integer(id), sys.getUserSessionData());

%>
	<head/>
	<script type="text/javascript">
		function modifyCategory(id) {
			var form = document.forms['updatecategory'];
			form.id.value = id;
			form.submit();
		}
		function deleteCategory(categoryId, msg) {
			if(confirm(msg)) {
				var form = document.forms['CategoryDeleteForm'];
				form.categoryId.value = categoryId;		
				form.submit();
			}
		}

		function changeDates(assessment) {
			var form = document.forms['dates'];
			form.assesment.value = assessment;
			form.submit();
		}
	</script>
	<form action="./layout.jsp?refer=/assesment/createGroup.jsp" name='edit' method="post">
		<input type="hidden" name="id" 		value='<%=id%>' />
		<input type="hidden" name="type" 		value="edit" />
	</form>	
	<form action="./layout.jsp?refer=/assesment/createCategory.jsp" name='addcategory' method="post">
		<input type="hidden" name="group" 			value='<%=id%>' />
		<input type="hidden" name="type" 			value="create" />
	</form>	
	<form action="./layout.jsp?refer=/assesment/updateCategory.jsp" name='updatecategory' method="post">
		<input type="hidden" name="id" 			/>
		<input type="hidden" name="type" 		value="edit" />
	</form>	
	<form action="./layout.jsp?refer=/assesment/groupList.jsp" name='cancel' method="post">
	</form>
	<form action="./layout.jsp?refer=/assesment/changedates.jsp" name='dates' method="post">
		<input type="hidden" name="assesment" />
		<input type="hidden" name="group" 		value='<%=id%>' />
	</form>
	<form action="./reportgroup.jsp" name='report' method="post">
		<input type="hidden" name="id" 	value='<%=id%>' />
	</form>
	<form action="./layout.jsp?refer=/assesment/userGroups.jsp" name='users' method="post">
		<input type="hidden" name="group" 	value='<%=id%>' />
	</form>
	<html:form action="/CategoryDelete">
		<input type="hidden" name="categoryId" />
		<input type="hidden" name="group" 	value='<%=id%>' />
	</html:form>		
		<body>
			<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("generic.group")%>' />
		  		<tr>			  		
					<jsp:include page='viewGroupOptions.jsp' />
				</tr>
		      	<tr>
		      		<td>
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.module.data")%>' />
							<table align='left' width='100%' cellpadding="2" cellspacing="2">
								<tr class="line">
									<td align="left"><%=messages.getText("assesment.data.name")%></td>
									<td align="right">
				   						<%=group.getName()%>              
									</td>
								</tr>
								<tr class="line">
									<td align="left">
					        		 	<%=messages.getText("assesment.data.corporation")%>
					        		 </td>
				        			<td align="right">
				   						<%=group.getCorporationName()%>              
					          		</td>
					          	</tr>
								<tr class="line">
									<td align="left">							        		 	
										<%=messages.getText("group.data.layout")%>
							        </td>
				        			<td align="right">
				   						<%=(group.getLayout().intValue() == GroupData.CLASIC) ? messages.getText("group.layout.clasic") : messages.getText("group.layout.group")%>              
					          		</td>
					          	</tr>
								<tr class="line">
					        		<td align="left" >
					        		 	<%=messages.getText("group.data.repeatable")%>
					        		 </td>
				        			<td align="right" >
					      				<%=(group.isRepeatable()) ? messages.getText("generic.messages.yes") : messages.getText("generic.messages.no")%>
					          		</td>
					          	</tr>
					     	</table>
						<jsp:include  page="../component/utilitybox2bottom.jsp" />
	          		</td>
	          	</tr>
				<tr>
					<td>
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("group.data.initialtext")%>' />
							<table align='left' width='100%' cellpadding="2" cellspacing="2">
						  		<tr class="line">
									<td align="left"><%=messages.getText("module.data.name.es")%></td>
									<td align="right">
				   						<%=(group.isInitialText()) ? messages.getText(group.getEsInitialText()) : "---"%>              
									</td>
								</tr>
						  		<tr class="line">
									<td align="left"><%=messages.getText("module.data.name.en")%></td>
									<td align="right">
				   						<%=(group.isInitialText()) ? messages.getText(group.getEnInitialText()) : "---"%>              
									</td>
								</tr>
						  		<tr class="line">
									<td align="left"><%=messages.getText("module.data.name.pt")%></td>
									<td align="right">
				   						<%=(group.isInitialText()) ? messages.getText(group.getPtInitialText()) : "---"%>              
									</td>
								</tr>
							</table>
						<jsp:include  page="../component/utilitybox2bottom.jsp" />
					</td>
				</tr>
    			<tr>
    				<td colspan="2">
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.group.background")%>' />
							<table align='center' width='100%' cellpadding="2" cellspacing="2">
						  		<tr class="line">
									<td align="left">
										<img src='<%="./flash/images/"+group.getImage()%>' width="500">
									</td>
								</tr>
							</table>
						<jsp:include  page="../component/utilitybox2bottom.jsp" />
					</td>
    			</tr>
		      	<tr>
		      		<td>
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.group.categories")%>' />
							<table align='center' width='100%' cellpadding="2" cellspacing="2">
<%			Collection<CategoryData> categories = group.getCategories();
			Collections.sort((List)categories);
			Iterator<CategoryData> itC = categories.iterator();
			while(itC.hasNext()) {
				CategoryData category = itC.next();
%>
								<tr>
									<td align="left" class="line" width="100%">
										<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText(category.getKey())%>' />
											<table align='center' width='100%' cellpadding="0" cellspacing="0">
								      			<tr>
											        <td class="guide2" width="1%" align="left"></td>
											        <td class="guide2" width="59%" align="left"><%=messages.getText("assesment.data.name")%></td>
											        <td class="guide2" width="20%" align="center"><%=messages.getText("assesment.data.start")%></td>
											        <td class="guide2" width="20%" align="center"><%=messages.getText("assesment.data.end")%></td>
										        </tr>
<%				Iterator<AssesmentAttributes> itA = category.getOrderedAssesments();
				boolean linetwo = false;
				boolean empty = true;
				while(itA.hasNext()) {
					AssesmentAttributes assessmentData = itA.next();
					empty = false;
%>						            			<tr class='<%=(linetwo)?"linetwo":"lineone"%>'>
													<td align="left"></td>
													<td align="left">
														<a href='<%="javascript:changeDates("+assessmentData.getId()+")"%>'>
															<%=messages.getText(assessmentData.getName())%>
														</a>
													</td>
													<td align="center"><%=Util.formatDate(assessmentData.getStart()) %></td>
													<td align="center"><%=Util.formatDate(assessmentData.getEnd()) %></td>
												</tr>
<%					linetwo = !linetwo;	
				}
%>											</table>
										<jsp:include  page="../component/utilitybox2bottom.jsp" />
									</td>
								</tr>
								<tr>
									<td class="line" align="right">
										<input type="button" class="input" value='<%=messages.getText("generic.messages.modify")%>' onclick="modifyCategory(<%=String.valueOf(category.getId()) %>)" />
<%				if(empty) {
%>										<input type="button" class="input" value='<%=messages.getText("generic.messages.delete")%>' onclick="deleteCategory(<%=String.valueOf(category.getId()) %>, '<%=messages.getText("category.delete.confirm")%>')" />
<%				}
%>									</td>
								</tr>
<%			}
%>							</table>
						<jsp:include  page="../component/utilitybox2bottom.jsp" />
					</td>
				</tr>
				<tr>
					<td>
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("group.data.users")%>' />
<%			HashMap<String, Integer> users = sys.getUserReportFacade().getUserGroupCount(group.getId(), sys.getUserSessionData());
			if(users.size() == 0) {
%>							<table align='left' width='100%' cellpadding="2" cellspacing="2">
		  						<tr class="line">
									<td align="left"><%=messages.getText("group.users.nousers")%></td>
								</tr>
							</table>
<% 			}else {
%>							<table align='left' width='100%' cellpadding="2" cellspacing="2">
<%				Iterator<String> roles = users.keySet().iterator();
				while(roles.hasNext()) {
					String role = roles.next();
%>		  						<tr class="line">
									<td align="left"><%=messages.getText("role."+role+".name")%></td>
									<td align="right"><%=String.valueOf(users.get(role))%></td>
								</tr>
<%				}
%>		  						<tr class="line">
									<td colspan="2" align="right">
										<input type="button" class="input" value='<%=messages.getText("Abrir Informe")%>' onclick="javascript:document.forms['report'].submit();"/>
										<input type="button" class="input" value='<%=messages.getText("Ver Usuarios")%>'  onclick="javascript:document.forms['users'].submit();"/>
									</td>
								</tr>
							</table>
<%			}
%>						<jsp:include  page="../component/utilitybox2bottom.jsp" />
					</td>
				</tr>
		  		<tr>			  		
					<jsp:include page='viewGroupOptions.jsp' />
				</tr>
			</table>
		</body>
<%	}	
%>
</html:html>
