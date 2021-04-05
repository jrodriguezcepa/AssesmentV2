<%@page language="java"
	import="assesment.business.*"
	import="assesment.business.assesment.*"
	import="java.util.*"
	import="assesment.communication.language.*"
	import="assesment.communication.security.*"
	import="assesment.communication.util.*"
	import="assesment.presentation.actions.assesment.*"
	import="assesment.presentation.translator.web.util.*"
	import="assesment.communication.assesment.*"
	errorPage="../exception.jsp"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>
<html:html>

<LINK REL=StyleSheet HREF="../util/css/estilo.css" TYPE="text/css">
<%
	AssesmentAccess sys=(AssesmentAccess)session.getAttribute("AssesmentAccess");
	String check = Util.checkPermission(sys,SecurityConstants.ADMINISTRATOR);
	Text messages=sys.getText();

	if(check!=null) {
		response.sendRedirect(request.getContextPath()+check);
	}else {
	String id=request.getParameter("id");
        AssesmentABMFacade assessmentABM = sys.getAssesmentABMFacade();
        assessmentABM.backUpAssessment(Integer.parseInt(id),sys.getUserSessionData());
%>
	<head/>
	<script type="text/javascript">
	
	</script>
	<body>
		
	</body>
<%	}	
%>
</html:html>
