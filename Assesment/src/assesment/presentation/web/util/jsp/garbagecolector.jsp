<%@page language="java" import="java.util.*" %>
<html>
	<head>
<%! 	Enumeration enu; Iterator iter;%>
<%
		enu=session.getAttributeNames();
		while(enu.hasMoreElements()){
			session.removeAttribute((String)enu.nextElement());
		
		}
	

%>
	</head>
	<body/>
</html>
