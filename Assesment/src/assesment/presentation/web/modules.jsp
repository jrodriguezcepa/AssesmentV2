<%@page language="java"
	import="assesment.business.*"
	import="assesment.business.administration.user.*"
	import="assesment.communication.assesment.*"
	import="assesment.communication.language.*"
	import="assesment.communication.security.*"
	import="assesment.communication.administration.user.*"
	import="assesment.communication.module.*"
	import="java.util.*"
%>
<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>

<%
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess"); 
 	UserSessionData userSession=sys.getUserSessionData(); 
 	Text messages=sys.getText();
 	
 	if(userSession.getFilter().getAssesment() == null) {
 	    response.sendRedirect("noassesment.jsp");
 	}else {
	 	AssesmentData assesment = sys.getAssesmentReportFacade().findAssesment(userSession.getFilter().getAssesment(),userSession);
		Iterator it = assesment.getModuleIterator();
		while(it.hasNext()) {
	    	ModuleData module = (ModuleData)it.next();
	    	boolean done = false;
	    	String text = "Pendiente";
%>     							<div id="module" class='<%=text%>'>
    								<a href='<%="module_da.jsp?module="+String.valueOf(module.getId())%>' class="style14">
										<%=messages.getText(module.getKey())%>
									</a>  					
								</div>
<%		}
		if(assesment.isPsitest()) {
			String text = "Pendiente";
%>     							<div id="module" class='<%=text%>'>
    								<a href='<%="module_da.jsp?module="+String.valueOf(ModuleData.PSICO)%>' class="style14">
										<%=messages.getText("assesment.module.psicologic")%>
									</a>  					
								</div>
<%		}
 	}
%>