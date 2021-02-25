<%@page language="java"
	import="assesment.business.*"
	import="assesment.communication.language.*"
	import="assesment.communication.tag.*"
	import="assesment.communication.security.*"
	import="assesment.communication.administration.corporation.tables.*"
	import="assesment.communication.corporation.*"
	import="assesment.communication.assesment.*"
	import="assesment.communication.module.*"
	import="assesment.communication.question.*"
	import="assesment.business.assesment.AssesmentReportFacade"
	import="assesment.communication.report.UsersReportDataSource"
	import="assesment.presentation.translator.web.util.*"
	import="java.util.*"
	import="java.lang.*"
	errorPage="../exception.jsp"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>



<%@page import="assesment.communication.user.UserData"%><html:html>

<LINK REL=StyleSheet HREF="../util/css/estilo.css" TYPE="text/css">


<script language="JavaScript" src="./css/create_functions.js" type="text/javascript">
</script>


<%!	AssesmentData data;
   	AssesmentAccess sys; 
   	Text messages;
%>
<%
	sys = (AssesmentAccess)session.getAttribute("AssesmentAccess"); 
	String check = Util.checkPermission(sys,SecurityConstants.ADMINISTRATOR);
	if(check!=null) {
		response.sendRedirect(request.getContextPath()+check);
	}else {

	    session.setAttribute("url","assesment/view.jsp");

	    RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
		dispatcher.include(request,response);
		
		messages=sys.getText();
	
	 	String strAssesment = request.getParameter("assesment");
		Integer id = new Integer(strAssesment);
		data = sys.getAssesmentReportFacade().findAssesment(id,sys.getUserSessionData());

		String strUser = request.getParameter("users");	 
		Collection users = Util.stringToCollection(strUser);
		Iterator it = users.iterator();
%>

	<head/>
	<body>
		<html:form action="DeleteUsersAssesment">
			<html:hidden property="user" value="<%=strUser%>"/>
			<html:hidden property="assesment" value="<%=strAssesment%>"/>
			<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("generic.assesment")+" "+data.getName()%>' />
		  		<tr>
	    			<td width="100%">
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.data.users")%>' />
			   				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			   					<tr>
			   						<td width="1%" class="guide2"></td>
			   						<td class="guide2" width="49%" align="left"><%=messages.getText("user.data.nickname")%></td>
					                <td class="guide2" width="50%" align="left"><%=messages.getText("user.data.firstname")%></td>
			   					<tr>
		   				
<%		boolean linetwo = true;
		while(it.hasNext()){
			String user = (String)it.next();
			UserData userData = sys.getUserReportFacade().findUserByPrimaryKey(user,sys.getUserSessionData());
			linetwo = !linetwo;	
%> 			            		<tr align=center class='<%=(linetwo)?"linetwo":"lineone"%>'>
			   						<td width="1%"></td>
			   						<td align="left"><%=userData.getLoginName()%></td>
			   						<td align="left"><%=userData.getFirstName()+" "+userData.getLastName()%></td>
	    						</tr>			    					
<%		}
%>			    			</table>					
						<jsp:include page="../component/utilitybox2bottom.jsp" />
					</td>
				</tr>
				<tr>
					<td align="left" class="line">
						<%=messages.getText("userassessment.delete.type")%>:&nbsp;
						<html:select property="type" styleClass="input">
							<html:option value="1"><%=messages.getText("userassessment.delete.onlyresult")%></html:option>
							<html:option value="2"><%=messages.getText("userassessment.delete.result.user")%></html:option>
							<html:option value="3"><%=messages.getText("userassessment.delete.all")%></html:option>
						</html:select>
					</td>
				</tr>
				<tr>
					<td align="right" class="line" >
						<html:submit styleClass="button" value='<%=messages.getText("generic.messages.delete")%>' />
						<html:cancel styleClass="button" value='<%=messages.getText("generic.messages.cancel")%>' />
					</td>
				</tr>
			<jsp:include  page='../component/titleend.jsp' />
	  	</html:form>
	</body>
<%	}
%>  			
</html:html>

