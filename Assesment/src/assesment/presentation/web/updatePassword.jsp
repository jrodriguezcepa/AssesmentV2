<%@ page language="java"
	import="assesment.business.*"
	import="assesment.business.administration.user.*"
	import="assesment.communication.administration.user.tables.*"
	import="assesment.communication.language.*"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>

<html>
<%
	AssesmentAccess sys=(AssesmentAccess)session.getAttribute("AssesmentAccess");
	String passwd = request.getParameter("password");
	String confirmation = request.getParameter("rePassword");
	String valid = null;
	if(!passwd.equals(confirmation)) {
        session.setAttribute("Msg",Text.getMessage("generic user.userdata.passconfirm"));
    	response.sendRedirect("./firstaccess.jsp");
	}else {
	    if(valid != null) {
	        session.setAttribute("Msg", Text.getMessage(valid));
	    	response.sendRedirect("./firstaccess.jsp");
	    }else {
			UsABMFacade userABM = null;
			
			userABM = sys.getUserABMFacade();
			userABM.userResetOwnPassword(passwd,sys.getUserSessionData());
			session.setAttribute("AssesmentAccess",new AssesmentAccess(sys.getUserSessionData().getFilter().getLoginName()));
			
			response.sendRedirect("./home.jsp");
	    }
	}
%>

</html>
