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
							<td align="left"  class="texts">
								<a href="javascript:document.forms['edit'].submit();">
									<input type="button" class="button" value='<%=messages.getText("generic.messages.edit")%>' >
								</a>
							</td>
<%	if(status == AssesmentAttributes.EDITABLE) {
%>							<td align="left"  class="texts">
								<a href="javascript:document.forms['module'].submit()" class="texts">
									<input type="button" class="button" value='<%=messages.getText("assesment.action.newmodule")%>' >
								</a>
							</td>
							<td align="left"  class="texts">
								<a href="javascript:document.forms['import_module'].submit()" class="texts">
									<input type="button" class="button" value='<%=messages.getText("assesment.action.existingmodule")%>' >
								</a>
							</td>
<%	}	
%>							<td align="left"  class="texts">
								<a href="javascript:document.forms['order'].submit()" class="texts">
									<input type="button" class="button" value='<%=messages.getText("generic.messages.changeorder")%>' >
								</a>
							</td>
<%	if(status == AssesmentAttributes.EDITABLE) {
		String link = "javascript:confirmDelete(document.forms['AssesmentDeleteForm'],'"+messages.getText("assesment.delete.confirm")+"')";
%>							<td align="left"  class="texts">
								<a href="<%=link%>" class="texts">
									<input type="button" class="button" value='<%=messages.getText("generic.messages.delete")%>' >
								</a>
							</td>
							<td align="left"  class="texts">
								<a href="javascript:document.forms['back'].submit()" class="texts">
									<input type="button" class="button" value='<%=messages.getText("generic.messages.cancel")%>' >
								</a>
							</td>
<%	}	
%>
							<td align="left"  class="texts">
								<a href="javascript:document.forms['create_feedback'].submit()" class="texts">
									<input type="button" class="button" value='<%=messages.getText("assessment.action.addfeedback")%>' >
				         		</a>
							</td>
						</tr>
					</table>
				</td>
			