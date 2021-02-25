<%@page language="java"
    errorPage="../exception.jsp"
	import="java.io.FileInputStream"
	import="java.util.HashMap"
	import="java.util.Calendar"
	import="net.sf.jasperreports.engine.JasperReport"
	import="net.sf.jasperreports.engine.util.JRLoader"
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
    UserData userData = sys.getUserReportFacade().findUserByPrimaryKey(user,sys.getUserSessionData());
	
    UserResultReportDataSource moduleDS = sys.getUserReportFacade().findUsersResult(new Integer(assesment),user,sys.getUserSessionData(),messages);
    FileInputStream input = new FileInputStream(QuestionResultAction.class.getResource("jasper/userResultModuleHTML.jasper").getFile());
    JasperReport subreport = (JasperReport)JRLoader.loadObject(input);
    String[] reportData = sys.getAssesmentReportFacade().findReportData(new Integer(assesment),sys.getUserSessionData());
    AssesmentAttributes attributes = sys.getAssesmentReportFacade().findAssesmentAttributes(new Integer(assesment),sys.getUserSessionData());

    TotalUserResultReportDataSource dataSource = new TotalUserResultReportDataSource(moduleDS.getRed(),moduleDS.getGreen(),messages);
    
    HashMap parameters = new HashMap();
    parameters.put("moduleDS",moduleDS);
    parameters.put("subreport",subreport);
    parameters.put("user",user);
    parameters.put("Title",messages.getText("report.userresult.title"));
    parameters.put("Module",messages.getText("report.userresult.module"));
    parameters.put("ModuleTitle",messages.getText("report.userresult.moduletitle"));
    parameters.put("RightText"," "+messages.getText("report.userresult.right"));
    parameters.put("WrongText"," "+messages.getText("report.userresult.wrong"));
    parameters.put("RightC",dataSource.getRightAnswers());
    parameters.put("WrongC",dataSource.getWrongAnswers());
    parameters.put("RightP",dataSource.getRightPercent());
    parameters.put("WrongP",dataSource.getWrongPercent());
    parameters.put("Right",messages.getText("report.userresult.right"));
    parameters.put("Wrong",messages.getText("report.userresult.wrong"));
    parameters.put("Percent",messages.getText("report.userresult.percent"));
    parameters.put("Count",messages.getText("question.resultreport.count"));
    parameters.put("UserText",messages.getText("report.userresult.user")+": "+userData.getFirstName()+" "+userData.getLastName());
    parameters.put("AssessmentText",messages.getText("generic.assesment")+": "+reportData[0]);
    parameters.put("CorporationText",messages.getText("generic.data.corporation")+": "+reportData[1]);
    parameters.put("Logo",AssesmentData.FLASH_PATH+"/images/logo"+String.valueOf(attributes.getCorporationId())+".jpg");
    parameters.put("LogoCEPA",AssesmentData.FLASH_PATH+"/images/logo-cepa.jpg");
    parameters.put("Date",Util.dateToString(messages,Calendar.getInstance(),sys.getUserSessionData().getLenguage()));
    if(sys.getUserReportFacade().isPsicologicDone(user,new Integer(assesment),sys.getUserSessionData())) {
	    parameters.put("PsiResultText",messages.getText("report.userresult.viewpsiresult"));
	    parameters.put("PsiResultLink","layout.jsp?refer=report/userpsiresultreport.jsp?user="+user+"&assesment="+String.valueOf(assesment));
    }
    if(sys.getUserReportFacade().isPersonalDataDone(user,new Integer(assesment),sys.getUserSessionData())) {
	    parameters.put("PersonalDataText",messages.getText("report.userresult.viewpersonaldata"));
	    parameters.put("PersonalDataLink","layout.jsp?refer=report/userpersonaldataresultreport.jsp?user="+user+"&assesment="+String.valueOf(assesment));
    }
    
    ReportUtil util = new ReportUtil();
    String value = util.executeReportToString("jasper/userResultHTML.jasper","HTML",parameters,dataSource,session,response,"usersresult");
	
	response.getWriter().write(value);
%>	

