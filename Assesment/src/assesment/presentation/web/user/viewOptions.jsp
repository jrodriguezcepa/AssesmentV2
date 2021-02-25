<%@page import="assesment.communication.user.UserData"%>
<%@page language="java" 
	import="assesment.business.*"
	import="assesment.communication.language.*"
%>
<%
	Text messages; 
	AssesmentAccess sys;
	sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	messages=sys.getText();
	String user = request.getParameter("user");
	String role = request.getParameter("role");
%>					
				<td colspan="3" align="left">
					<table >
						<tr>
							<td align="left"  class="texts">
								<a href="javascript:document.forms['edit'].submit();">
									<input type="button" class="button" value='<%=messages.getText("generic.messages.edit")%>' >
								</a>
							</td>
				      		<td align="left" class="texts">
								<a href='<%="layout.jsp?refer=/user/resetPassword.jsp?user="+user%>'>
									<input type="button" class="button" value='<%=messages.getText("user.data.resetpassword")%>' >
								</a>
							</td>
				      		<td align="left" class="texts">
								<a href='<%="layout.jsp?refer=/user/assesment.jsp?user="+user%>'>
									<input type="button" class="button" value='<%=messages.getText("user.assesment.asociate")%>' >
								</a>
							</td>
					      	<td align="left" class="texts">
<%			String link = "javascript:doSubmitConfirm(document.forms['UserDeleteForm'],'"+user+"','users','"+messages.getText("user.delete.confirm")+"');";
%>								<a href="<%=link%>">
									<input type="button" class="button" value='<%=messages.getText("generic.messages.delete")%>' >										
								</a>
							</td>
					      	<td align="left" class="texts">
								<a href='<%="layout.jsp?refer=/user/linkFDM.jsp?user="+user%>'>
									<input type="button" class="button" value='<%=messages.getText("generic.messages.associatefdm")%>' >										
								</a>
							</td>
<%			if(role.equals(UserData.GROUP_ASSESSMENT)) {
%>					      	<td align="left" class="texts">
								<a href='<%="layout.jsp?refer=/user/changeGroup.jsp?user="+user%>'>
									<input type="button" class="button" value='<%=messages.getText("generic.messages.changegroup")%>' >										
								</a>
							</td>
<%			}
%>					      	<td align="left" class="texts">
         						<a href='<%="layout.jsp?refer=/user/list.jsp"%>'> 
           							<input type="button" class="button" value='<%=messages.getText("generic.messages.cancel")%>' > 
           						</a> 
							</td>
						</tr>
					</table>
				</td>