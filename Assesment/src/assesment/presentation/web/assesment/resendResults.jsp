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
	import="assesment.business.administration.user.UsReportFacade"
	import="assesment.communication.report.UsersReportDataSource"
	import="assesment.presentation.translator.web.util.*"
	import="assesment.communication.user.UserData"
	import="java.util.*"
	import="java.lang.*"
	errorPage="../exception.jsp"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>


<html:html>

<LINK REL=StyleSheet HREF="../util/css/estilo.css" TYPE="text/css">


<script language="JavaScript" src="./css/create_functions.js" type="text/javascript">
</script>
<script language="JavaScript">
	function showEmailText(){
		var element = document.forms['UserAssesmentForm'].type;
		if(element.selectedIndex == 0){
			document.getElementById('showEmail').style.display = "none";
		}else {
			document.getElementById('showEmail').style.display = "block";
		}
	}
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
		
		UsReportFacade userReport = sys.getUserReportFacade(); 
%>

	<head/>
	<body>
		<html:form action="/ResendUserAssesmentResults">
			<html:hidden property="assesment" value="<%=strAssesment%>"/>
			<table width="600" border="0" align="center" cellpadding="2" cellspacing="2">
				<tr>
					<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("generic.assesment")+" "+data.getName()%>' />
		  		</tr>
	    		<tr>
					<jsp:include  page="../component/spaceline.jsp" />
	  			</tr>
		  		<tr>
	    			<td width="100%">
						<jsp:include  page='<%="../component/utilityboxtop.jsp?title="+messages.getText("generic.data.users")%>' />
		   				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" height="100">
<%			String doneUsers = "";	
			while(it.hasNext()){
				String user = (String)it.next();
				UserData userData = userReport.findUserByPrimaryKey(user,sys.getUserSessionData());
				if(userReport.isAssessmentDone(user,data.getId(),sys.getUserSessionData(),data.isPsitest())) {
					doneUsers += user+"<";
%>
	 						<tr  class="space" height="2"><th align="right" colspan="4" bgcolor="#CCCCCC"></th></tr>
		   					<tr >
		   						<td align="left" class="linetwo"><%=userData.getLoginName()%></td>
		   						<td align="left" class="linetwo"><%=userData.getFirstName()+" "+userData.getLastName()%></td>
	    					</tr>			    					
<%				}
			}
%>			    					
	 						<tr  class="space" height="15" ><th align="right" colspan="4" bgcolor="#FFFFFF">&nbsp;</th></tr>
		   					<tr>
		   						<td align="left" class="linetwo">
		   							<%=messages.getText("userassessment.resend.type")%>:&nbsp;
		   							<html:select property="type" styleClass="input" onchange="showEmailText();">
		   								<html:option value="1"><%=messages.getText("userassessment.resend.touser")%></html:option>
		   								<html:option value="2"><%=messages.getText("userassessment.resend.toaddress")%></html:option>
		   							</html:select>
		   						</td>
		   						<td align="right" class="linetwo">
		   							<div id="showEmail" style="display: none;">
		   								<html:text property="email" styleClass="input" size="50"/>
		   							</div>
		   						</td>
		   					</tr>
	 						<tr  class="space" height="15" ><th align="right" colspan="4" bgcolor="#FFFFFF">&nbsp;</th></tr>
	 						<tr>
	    						<td align="right" class="linetwo" colspan="4">
	    							<html:submit styleClass="input" value='<%=messages.getText("assessment.users.resend")%>' />
	    							<html:cancel styleClass="input" value='<%=messages.getText("generic.messages.cancel")%>' />
	    						</td>
	 						</tr>
	      				</table>
						<jsp:include page="../component/utilityboxbottom.jsp" />
					</td>
				</tr>
				<tr>
					<jsp:include  page="../component/spaceline.jsp" />
			  	</tr>
	  		</table>
			<html:hidden property="user" value="<%=doneUsers%>"/>
	  	</html:form>
	</body>
<%	}
%>  			
</html:html>

