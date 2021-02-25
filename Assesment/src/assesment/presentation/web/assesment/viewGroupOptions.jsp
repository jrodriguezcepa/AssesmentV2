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
%>
				<td align="left">
					<table>
						<tr>
							<td align="left"  class="texts">
								<a href="javascript:document.forms['edit'].submit();">
									<input type="button" class="button" value='<%=messages.getText("generic.messages.edit")%>' >
								</a>
							</td>
							<td align="left"  class="texts">
								<a href="javascript:document.forms['addcategory'].submit()" class="texts">
									<input type="button" class="button" value='<%=messages.getText("generic.group.addcategory")%>' >
								</a>
							</td>
							<td align="left"  class="texts">
								<a href="#" class="texts">
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
			