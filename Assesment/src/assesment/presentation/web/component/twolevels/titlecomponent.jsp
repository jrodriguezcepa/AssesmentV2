<%@page language="java"
%>
<%
	String colspan="3";
	if(request.getParameter("colspan") != null) {
		colspan=request.getParameter("colspan");
	}
	String title=request.getParameter("title");
%>
			<td height="30" colspan="<%=colspan%>" >
				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                	<tr class="title2">
                   		<td width="9"><img src="../../util/imgs/titulo_left.jpg" width="9" height="40" /></td>
                   		<td class="title2" align="center"><%=title%></td>
                   		<td width="9" align="right"><img src="../../util/imgs/titulo_right.jpg" width="9" height="40" /></td>
                 	</tr>
				</table>
			</td>
