<%@page language="java"
    errorPage="../exception.jsp"
	import="java.io.*"
	import="java.util.*"
	import="net.sf.jasperreports.engine.*"
	import="assesment.presentation.actions.report.*"
	import="assesment.communication.assesment.*"
	import="assesment.communication.report.*"
	import="assesment.communication.language.*"
	import="assesment.business.*"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html" 
%>

<%@ taglib uri="/WEB-INF/struts-bean.tld"
        prefix="bean" 
%>
<%	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	Text messages = sys.getText();
	String assesment = request.getParameter("assesment");
	String module = request.getParameter("module");

	AssesmentAttributes attributes = sys.getAssesmentReportFacade().findAssesmentAttributes(new Integer(assesment),sys.getUserSessionData());
	
    JRDataSource[] dataSources = sys.getQuestionReportFacade().findQuestionReportByModule(new Integer(module),new Integer(assesment),sys.getUserSessionData(),messages);
    String[] reportData = sys.getAssesmentReportFacade().findReportData(new Integer(assesment),sys.getUserSessionData());
    
    FileInputStream input = new FileInputStream(QuestionResultAction.class.getResource("jasper/questionModuleHTML.jasper").getFile());
    JasperReport subReport = (JasperReport)net.sf.jasperreports.engine.util.JRLoader.loadObject(input);
    
    HashMap parameters = new HashMap();
    parameters.put("questionDS",dataSources[1]);
    parameters.put("subreport",subReport);
    parameters.put("Title",messages.getText("module.resultreport.title"));
    parameters.put("AssesmentText",messages.getText("module.resultreport.assesment")+": "+((ModuleResultReportDataSource)dataSources[0]).getAssesmentName());
    parameters.put("Percent",messages.getText("report.generalresult2.average"));
    parameters.put("ModuleText",messages.getText("module.resultreport.module")+": "+((ModuleResultReportDataSource)dataSources[0]).getModuleName());
    parameters.put("Question",messages.getText("module.resultreport.question"));
    parameters.put("Wrong",messages.getText("module.resultreport.wrong"));
    parameters.put("Count",messages.getText("module.resultreport.right"));
    parameters.put("Logo",AssesmentData.FLASH_PATH+"/images/logo"+String.valueOf(attributes.getCorporationId())+".jpg");
    parameters.put("LogoCEPA",AssesmentData.FLASH_PATH+"/images/logo-cepa.jpg");
    parameters.put("AssessmentText",messages.getText("generic.assesment")+": "+reportData[0]);
    parameters.put("CorporationText",messages.getText("generic.data.corporation")+": "+reportData[1]);
    parameters.put("Date",Util.dateToString(messages,Calendar.getInstance(),sys.getUserSessionData().getLenguage()));
    
    ReportUtil util = new ReportUtil();
    String value = util.executeReportToString("jasper/moduleResultHTML.jasper","HTML",parameters,dataSources[0],session,response,"usersresult");
	
	response.getWriter().write(value);
%>	

