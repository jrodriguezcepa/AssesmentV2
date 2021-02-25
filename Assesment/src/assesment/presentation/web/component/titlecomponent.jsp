<%@page language="java"
%>
<%
	String colspan="3";
	if(request.getParameter("colspan") != null) {
		colspan=request.getParameter("colspan");
	}
	String title=request.getParameter("title");
%>			<table width="90%" style="padding-left: 20px;">
				<tr height="30px;">
					<td colspan="<%=colspan%>" class="title2" align="center">
						<%=title%>
					</td>
				</tr>
				<tr style="border:1px solid;">
					<td>
						<table width="100%" style="background-color: #ECECEC;" cellpadding="5" cellspacing="5">
					