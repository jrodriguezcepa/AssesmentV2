<%@ page language="java"
	import="assesment.business.*"
	import="assesment.communication.language.*"
%>
<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>
<%
	String entity = request.getParameter("entity");
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	Text messages = sys.getText();
//	Boolean view = (Boolean) (session.getAttribute("view"));
	Boolean view = (Boolean) (session.getAttribute("activityview"));
%>

<html>
	<head>
		<LINK REL=StyleSheet HREF="./util/css/estilo.css" TYPE="text/css">
	</head>
 
	<body>
		<center>
			<table width="600" border="0" cellpadding="0" cellspacing="0" class="guideSearch">
		  		<tr>
		    		<td width="19" height="30"><img src="./util/imgs/left_top_search.jpg" width="19" height="30" /></td>
		    		<td width="562" height="30">&nbsp;</td>
		    		<td width="19" height="30"><img src="./util/imgs/right_top_search.jpg" width="19" height="30" /></td>
		  		</tr>
		  		<tr>
		    		<td background="./util/imgs/left_line_search.jpg">&nbsp;</td>
	    		  <td width="562" align="center" class="lineone">
		    			<span class="success">
						<%=messages.getText(entity+".message")%>
			    		</span>
						<br>
		    			<br>
		    			<br>
						<img src="/assesment/util/imgs/icon1.jpg" height="12" width="12">&nbsp;&nbsp;<a href='<%=messages.getText(entity+".link")%>'class="linksuccess"><%=messages.getText(entity+".next")%></a><br>
						<br>
						<br>
						<%
						if (view!=null) {
							String activityId = (String) session.getAttribute("id");
							session.removeAttribute("id");
							session.removeAttribute("activityview");							
						%>
							<img src="/assesment/util/imgs/icon1.jpg" height="12" width="12">&nbsp;&nbsp;<a href='<%=messages.getText(entity+".linkview")%>?id=<%= activityId%>' class="linksuccess"><%=messages.getText(entity+".nextview")%></a>
						<%
	
						}%>
					</td>
		    		<td background="./util/imgs/right_line_search.jpg">&nbsp;</td>
		  		</tr>
		  		<tr>
		    		<td width="19" height="11"><img src="./util/imgs/left_base_search.jpg" width="19" height="11" /></td>
		    		<td width="562" height="11" background="./util/imgs/center_base_search.jpg"><img src="x" width="1" height="1" /></td>
		    		<td width="19" height="11"><img src="./util/imgs/right_base_search.jpg" width="19" height="11" /></td>
			  	</tr>
			</table>
		</center>
	</body>
</html>
