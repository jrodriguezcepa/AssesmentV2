<%@page language="java" 
	import="assesment.business.*"
	import="assesment.communication.language.*"
	import="assesment.communication.assesment.*"
%>
<%
	Text messages; 
	AssesmentAccess sys;
	sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	messages=sys.getText();
	int status = Integer.parseInt(request.getParameter("status"));
%>
				<td colspan="3" align="left">
					<table >
						<tr>
<%	if(status == AssesmentAttributes.EDITABLE) {
%>
							<td align="left"  class="texts">
								<a href="javascript:document.forms['edit'].submit();">
									<input type="button" class="button" value='<%=messages.getText("generic.messages.edit")%>' >
								</a>
							</td>
							<td align="left"  class="texts">
								<a href="javascript:document.forms['question'].submit()" class="texts">
									<input type="button" class="button" value='<%=messages.getText("module.action.newquestion")%>' >
								</a>
							</td>
							<td align="left"  class="texts">
								<a href="javascript:document.forms['import_question'].submit()" class="texts">
									<input type="button" class="button" value='<%=messages.getText("module.action.existingquestion")%>' >
								</a>
							</td>
							<td align="left"  class="texts">
								<a href="javascript:document.forms['order'].submit()" class="texts">
									<input type="button" class="button" value='<%=messages.getText("generic.messages.changequestionorder")%>' >
								</a>
							</td>
							<td align="left"  class="texts">
<%		String link = "javascript:confirmDelete(document.forms['ModuleDeleteForm'],'"+messages.getText("module.delete.confirm")+"')"; 
%>								<a href="<%=link%>" class="texts">
									<input type="button" class="button" value='<%=messages.getText("generic.messages.delete")%>' >
								</a>
							</td>
							<td align="left"  class="texts">
								<a href="javascript:document.forms['back'].submit()" class="texts">
									<input type="button" class="button" value='<%=messages.getText("generic.messages.cancel")%>' >
								</a>
							</td>
							<td align="left"  class="texts">
								<a href="javascript:document.forms['group'].submit()" class="texts">
									<input type="button" class="button" value='<%=messages.getText("question.data.group")%>' >
								</a>
							</td>
<%	}else {
%>	
							<td align="left"  class="texts">
								<a href="javascript:document.forms['back'].submit()" class="texts">
									<input type="button" class="button" value='<%=messages.getText("generic.messages.cancel")%>' >
								</a>
							</td>
<%	}
%>						</tr>
					</table>
				</td>
			