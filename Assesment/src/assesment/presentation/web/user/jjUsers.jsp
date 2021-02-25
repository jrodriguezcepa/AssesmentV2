<%@page language="java"
	errorPage="../exception.jsp"
	import="assesment.business.*"
	import="assesment.business.administration.user.*" 
	import="assesment.business.administration.corporation.*" 
	import="assesment.communication.language.*"
	import="assesment.communication.security.*"
	import="assesment.communication.assesment.*"
	import="assesment.communication.administration.group.tables.*"
	import="assesment.presentation.translator.web.user.*"
	import="assesment.presentation.translator.web.util.*"
	import="assesment.communication.user.*"
	import="assesment.communication.util.*"
	import="assesment.communication.administration.user.UserSessionData"
	import="java.util.*"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>



<%@page import="java.io.*"%>
<html>
<%! Text messages;
	AssesmentAccess sys;
%>
<%
	sys=(AssesmentAccess)session.getAttribute("AssesmentAccess");
	String check = Util.checkPermission(sys,SecurityConstants.ADMINISTRATOR);
	UserSessionData userSessionData = sys.getUserSessionData();
    File f = new File("C://elearningJJ.csv");
    FileOutputStream fos = new FileOutputStream(f);

    if(check!=null) {
		response.sendRedirect(request.getContextPath()+check);
	}else {
		UsReportFacade usReport = sys.getUserReportFacade();
		
		Collection users = sys.getUserReportFacade().getAssessmentUsers(new Integer(AssesmentData.JJ),sys.getUserSessionData());
        Iterator it = users.iterator();
        while(it.hasNext()) {
        	UserData userData = (UserData)it.next();
        	//String user = userData.getLoginName();
        	String user = "CAROLMORALES";
            if(usReport.isAssessmentDone(user,new Integer(AssesmentData.JJ),userSessionData,true)) {
                if(!usReport.isResultGreen(user,new Integer(AssesmentData.JJ),userSessionData) || !usReport.isPsiGreen(user,new Integer(AssesmentData.JJ),userSessionData)) {
                	Collection lessons = sys.getTagReportFacade().getAssociatedLessons(user,new Integer(AssesmentData.JJ),userSessionData);
                    if(lessons.size() > 0){
                    	String s = userData.getLoginName()+";"+userData.getFirstName()+";"+userData.getLastName()+";"+userData.getEmail()+";"+userData.getSex()+";"+userData.getBirthDate()+";"+userData.getCountry()+";";
						Iterator it2 = lessons.iterator();
						while(it2.hasNext()) {
							s += it2.next();
							if(it2.hasNext()) {
								s += ",";								
							}
						}
						s += ";\n";
						fos.write(s.getBytes());
                    }
                }
            }
        }
	}
%>
</html>

