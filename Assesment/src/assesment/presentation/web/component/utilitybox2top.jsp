<%@page language="java"
%>
			<table width="100%" border="0" cellpadding="5" cellspacing="5">
				<tr>
					<td align="left" class="guideSearch"><%=(assesment.presentation.translator.web.util.Util.empty(request.getParameter("title"))) ? "" : request.getParameter("title")%></td>
				</tr>
   				<tr>
    				<td>
