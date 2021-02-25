<%@page language="java" 
	import="assesment.business.*"
	import="assesment.communication.language.*"
%>
<%
	Text messages; 
	AssesmentAccess sys;
	sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	messages=sys.getText();
%>
				<td colspan="3" align="left">
					<table>
						<tr>
							<td align="left"  class="texts">
								<a href="javascript:document.forms['edit'].submit();">
									<input type="button" class="button" value='<%=messages.getText("generic.messages.edit")%>' >
								</a>
							</td>
							<td align="left"  class="texts">
<%			String link = "javascript:confirmDelete(document.forms['CediDeleteForm'],'"+messages.getText("corporation."+request.getParameter("data")+".delete.confirm")+"')";
%>								<a href="<%=link%>" class="texts">
									<input type="button" class="button" value='<%=messages.getText("generic.messages.delete")%>' >
								</a>
							</td>
							<td align="left"  class="texts">
								<a href="javascript:document.forms['back'].submit()" class="texts">
									<input type="button" class="button" value='<%=messages.getText("generic.messages.cancel")%>' >
								</a>
							</td>
						</tr>
					</table>
				</td>
			