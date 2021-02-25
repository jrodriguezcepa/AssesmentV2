<%@page language="java"
    errorPage="../exception.jsp"
	import="java.io.*"
	import="java.util.*"
	import="net.sf.jasperreports.engine.*"
	import="net.sf.jasperreports.engine.util.*"
	import="assesment.presentation.actions.report.*"
	import="assesment.communication.report.*"
	import="assesment.communication.language.*"
	import="assesment.communication.user.*"
	import="assesment.communication.assesment.*"
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
	String user = request.getParameter("user");

	String[] reportData = sys.getAssesmentReportFacade().findReportData(new Integer(assesment),sys.getUserSessionData());
    AssesmentAttributes attributes = sys.getAssesmentReportFacade().findAssesmentAttributes(new Integer(assesment),sys.getUserSessionData());
    
    UserData userData = sys.getUserReportFacade().findUserByPrimaryKey(user,sys.getUserSessionData());
    UserPsiReportDataSource dataSource = new UserPsiReportDataSource(sys.getUserReportFacade().getUserPsicoResult(userData.getLoginName(),new Integer(assesment),sys.getUserSessionData()),messages);
    
    HashMap parameters = new HashMap();
    parameters.put("Title",messages.getText("report.user.psiresults"));
    parameters.put("UserText",messages.getText("report.userresult.user")+": "+userData.getFirstName()+" "+userData.getLastName());
    parameters.put("AssessmentText",messages.getText("generic.assesment")+": "+reportData[0]);
    parameters.put("CorporationText",messages.getText("generic.data.corporation")+": "+reportData[1]);
    parameters.put("Average1Text",messages.getText("generic.assessment.complacency"));
    parameters.put("Average2Text",messages.getText("generic.assessment.nervous"));
    parameters.put("Logo",AssesmentData.FLASH_PATH+"/images/logo"+String.valueOf(attributes.getCorporationId())+".jpg");
    parameters.put("Date",Util.dateToString(messages,Calendar.getInstance(),sys.getUserSessionData().getLenguage()));
    
    ReportUtil util = new ReportUtil();
    String value = util.executeReportToString("jasper/userPsiHTML.jasper","HTML",parameters,dataSource,session,response,"usersresult");
	
	response.getWriter().write(value);
%>	

