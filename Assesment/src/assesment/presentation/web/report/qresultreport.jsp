<%@page language="java"
    errorPage="../exception.jsp"
	import="java.io.File"
	import="java.util.*"
	import="assesment.communication.assesment.*"
	import="assesment.presentation.translator.web.util.Util"
	import="assesment.presentation.actions.report.ReportUtil"
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

	String question = request.getParameter("question");
    QuestionResultReportDataSource dataSource = sys.getQuestionReportFacade().findQuestionResult(new Integer(question),sys.getUserSessionData(),messages);
    Integer assesment = sys.getQuestionReportFacade().findAssesmentId(new Integer(question),sys.getUserSessionData());
    AssesmentAttributes attributes = sys.getAssesmentReportFacade().findAssesmentAttributes(assesment,sys.getUserSessionData());
    String[] reportData = sys.getAssesmentReportFacade().findReportData(assesment,sys.getUserSessionData());
        
	HashMap parameters = new HashMap();
    parameters.put("Title",messages.getText("question.resultreport.title"));
    parameters.put("Question",messages.getText(dataSource.getQuestion()));
    parameters.put("Answer",messages.getText("question.resultreport.answer"));
    parameters.put("Count",messages.getText("question.resultreport.count"));
    parameters.put("Logo",AssesmentData.FLASH_PATH+"/images/logo"+String.valueOf(attributes.getCorporationId())+".jpg");
    parameters.put("LogoCEPA",AssesmentData.FLASH_PATH+"/images/logo-cepa.jpg");
    parameters.put("AssessmentText",messages.getText("generic.assesment")+": "+reportData[0]);
    parameters.put("CorporationText",messages.getText("generic.data.corporation")+": "+reportData[1]);
    parameters.put("Date",Util.dateToString(messages,Calendar.getInstance(),sys.getUserSessionData().getLenguage()));
        
    String fileName = null; 
    if(dataSource.getQuestionSize() > 10) {
        fileName = "jasper/questionResultMultiOptionsHTML.jasper";
    }else {
        fileName = "jasper/questionResultOptionsHTML.jasper";
    }
    ReportUtil util = new ReportUtil();
    String report = util.executeReportToString(fileName,"HTML",parameters,dataSource,session,response,"usersresult");
    response.getWriter().write(report);
%>
