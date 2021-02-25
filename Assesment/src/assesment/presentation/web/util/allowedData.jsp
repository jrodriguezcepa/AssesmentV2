<%@page language="java" 
	import="assesment.business.*"
	import="assesment.communication.language.*"
%>
<%	
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	Text messages = sys.getText();
%>	
	<table width="600" border="0" align="center" cellpadding="0" cellspacing="0">
		<tr class="guideSearch">
			<td width="19" align="left" valign="top" ><img src="/assesment/util/imgs/left_top_especial.jpg" width="19" height="15" align="bottom" /></td>
            <td width="562" align="left" class="guideSearch"><img src="x" height="1px" width="1px"></td>
            <td width="20" align="right" valign="top" ><img src="/assesment/util/imgs/right_top_especial.jpg" width="19" height="15" align="bottom" /></td>
        </tr>
        <tr>
        	<td width="19" align="left" background="/assesment/util/imgs/left_line_search.jpg" class="leftLineSearch"></td>
            <td width="562" class="linethree">
           		<%=messages.getText(request.getParameter("message")) %>
    		</td>
			<td width="19" align="right" background="/assesment/util/imgs/right_line_search.jpg" class="rightLineSearch"></td>
		</tr>
        <tr>
        	<td width="19" height="11" align="left" valign="top"><img src="/assesment/util/imgs/left_base_search.jpg" width="19" height="11" /></td>
            <td width="562" class="centerBaseSearch"></td>
            <td width="19" height="11" align="right" valign="top"><div align="right"><img src="/assesment/util/imgs/right_base_search.jpg" width="19" height="11" /></div></td>
		</tr>
	</table>	  