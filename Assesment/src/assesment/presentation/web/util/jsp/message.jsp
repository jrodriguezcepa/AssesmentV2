<%@page language="java" %>

<%!		String msg;%>
<%
			msg=(String)session.getAttribute("Msg");
			
			if(msg!=null){
				session.removeAttribute("Msg");
	
%>
	
				<script language="javascript">
	
					alert("<%=msg%>");

				</script>
<%
			}
%>
