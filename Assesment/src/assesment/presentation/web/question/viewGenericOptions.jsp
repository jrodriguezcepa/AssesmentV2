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
			<td colspan="3">
				<jsp:include  page='<%="../component/utilitybox3top.jsp?title="+messages.getText("generic.messages.options")%>' />
				<table width="100%" class="texts1" border="0" cellpadding="0" cellspacing="0">
					<tr valign="bottom">
						<td width="50%">
							<img align="ABSMIDDLE" border="0" src="./util/imgs/icon2.gif" width="12">&nbsp;
							<a href='<%="javascript:document.forms['edit'].submit()"%>' class="texts">
			         			<%=messages.getText("generic.messages.edit")%>
			         		</a>
						</td>
						<td width="50%">
							<img align="ABSMIDDLE" border="0" src="./util/imgs/icon2.gif" width="12">&nbsp;
							<a href="javascript:document.forms['back'].submit()" class="texts">
								<%=messages.getText("generic.messages.cancel")%>
							</a>
						</td>
					</tr>
					<tr valign="bottom">
						<td width="50%">
							<img align="ABSMIDDLE" border="0" src="./util/imgs/icon2.gif" width="12">&nbsp;
							<a href='<%="javascript:confirmDelete(document.forms['GenericQuestionDeleteForm'],'"+messages.getText("question.delete.confirm")+"')"%>' class="texts">
								<%=messages.getText("generic.messages.delete")%>
							</a>
						</td>
						<td width="50%">&nbsp;</td>
					</tr>
				</table>
				<jsp:include page="../component/utilitybox3bottom.jsp"/>
			</td>
			