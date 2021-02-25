<%@ page language="java" isErrorPage="true"
	import="java.util.*"
	import="assesment.business.*"
	import="assesment.communication.module.*"
	import="assesment.presentation.translator.web.util.*"
%>
<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>
<%!
	AssesmentAccess sys;
%>
<%
	sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	String xml = "";
	String moduleId = request.getParameter("module");
	if(!Util.empty(moduleId) && sys.getUserSessionData().getFilter().getAssesment() != null) {
        ModuleData moduleData = null;
        if(Integer.parseInt(moduleId) == ModuleData.PSICO) {
            moduleData = sys.getModuleReportFacade().getPsicoModule(sys.getUserSessionData());
        }else {
            moduleData = sys.getModuleReportFacade().findModule(new Integer(moduleId),sys.getUserSessionData());
        }
        ModuleXML moduleXml = new ModuleXML(sys);
        xml = moduleXml.generateXML(moduleData);
	}
	response.setContentType("text/xml");
	response.getWriter().write(xml);
%>
