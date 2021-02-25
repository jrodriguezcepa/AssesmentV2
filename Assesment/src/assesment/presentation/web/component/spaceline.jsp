<%@page language="java"
%>
<%
	String colspan="3";
	if(request.getParameter("colspan") != null) {
		colspan=request.getParameter("colspan");
	}
%>
        	<td height="10" colspan="<%=colspan%>"><img src="x" width="1" height="1"></td>
