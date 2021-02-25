<%@page import="assesment.persistence.administration.tables.AssessmentUserData"%>
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


<html:html>

<LINK REL=StyleSheet HREF="../util/css/estilo.css" TYPE="text/css">

<script language="JavaScript">
	function doAction(formName){
		var form = document.forms[formName];

		var elements = document.forms['users'].elements;
		var length=elements.length;
		var list="";
		for(i=0;i<length;i++){
			var element=elements[i];
			if(element.type.toLowerCase()=="checkbox" && element.name != 'all'){
				if(element.checked){
					if(list==""){
						list=element.value;
					}else{
						list=element.value+"<"+list;
					}	
				}	
			} 
		}
		form.users.value=list;
		form.submit();
	}
	function doAll(){
		var elementAll = document.forms['users'].all;
		var elements = document.forms['users'].elements;
		for(i=0;i<elements.length;i++){
			element=elements[i];
			if(element.type.toLowerCase()=="checkbox" && element.name != 'all'){
				element.checked = elementAll.checked;
			} 
		}
	}
	function generateReport(valueL, report) {
		var form = document.forms['DownloadResultReportForm'];
		form.login.value = valueL;			
		form.reportType.value = report;
		form.submit();
	}
</script>

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
	
		Integer id = null;
		if(!Util.empty(request.getParameter("assesment"))) {
			id = new Integer(request.getParameter("assesment"));
		}else {
			id = new Integer((String)session.getAttribute("assesment"));
		}
		
		AssesmentReportFacade report = sys.getAssesmentReportFacade();
		if(id != null){
			data = report.findAssesment(id,sys.getUserSessionData());
	        boolean webinar = data.isWebinar();
			Collection<AssessmentUserData> list = sys.getAssesmentReportFacade().getAssessmentUsers(new Integer(id),sys.getUserSessionData());
	        Collections.sort((List<AssessmentUserData>)list);
%>

	<head/>
	<body>
		<html:form action="/DownloadResult" target="_blank">
			<html:hidden property="assessment" value="<%=String.valueOf(id)%>" />
			<html:hidden property="login" />
			<html:hidden property="reportType" />
		</html:form>	
		<form action="./layout.jsp?refer=/assesment/deleteUsers.jsp" name='delete' method="post">
			<input type="hidden" name="assesment" 		value="<%=String.valueOf(id)%>" />
			<input type="hidden" name="users" />
		</form>	
		<form action="./layout.jsp?refer=/assesment/resendResults.jsp" name='resend' method="post">
			<input type="hidden" name="assesment" 		value="<%=String.valueOf(id)%>" />
			<input type="hidden" name="users" />
		</form>	
		<form action="./layout.jsp?refer=/assesment/view.jsp" name='back' method="post">
			<input type="hidden" name="assesment" 		value="<%=String.valueOf(id)%>" />
		</form>	
		<form name='users'>
			<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("generic.assesment")+" "+messages.getText(data.getName())%>' />
		  		<tr>
	    			<td width="100%">
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.data.users")%>' />
		   				<table width="100%" border="0" align="center" cellpadding="2" cellspacing="2" height="100">
		   					<tr>
		   						<td align="center" class="guide2" width="5%"><input type="checkbox" name="all" id="all" onchange="doAll()"></td>
		   						<td align="center" class="guide2" width="20%"><%=messages.getText("user.data.nickname") %></td>
<%			if(data.isWebinar()) {
%>								<td align="center" class="guide2" width="25%"><%=messages.getText("report.users.name") %></td>
								<td align="center" class="guide2" width="10%"><%=messages.getText("report.users.webinarcode") %></td>
<%			}else {
%>								<td align="center" class="guide2" width="35%"><%=messages.getText("report.users.name") %></td>
<%			}
%>		   						<td align="center" class="guide2" width="10%"><%=messages.getText("assesment.data.status")%></td>
		   						<td align="center" class="guide2" width="10%"><%=messages.getText("generic.result")%></td>
		   						<td align="center" class="guide2" width="10%"><%=messages.getText("generic.report")%></td>
<%			if(data.isCertificate()) {
%>		   						<td align="center" class="guide2" width="10%"><%=messages.getText("generic.certificate")%></td>
<%			}
%>	    					</tr>			    					
<%			Iterator<AssessmentUserData> it = list.iterator();
			boolean color = false;
			int questionCount = data.getQuestionCount();
			int testQuestionCount = data.getTestQuestionCount();
			while(it.hasNext()) {
				AssessmentUserData user = it.next();
				if(!webinar || !user.isNotStarted()) {
		            color = !color;
%>
		   					<tr class='<%=(color) ? "lineone" : "linetwo"%>'>
		   						<td align="center"><input type="checkbox" name="<%=user.getLoginname()%>" value="<%=user.getLoginname()%>"/></td>
		   						<td align="left"><%=user.getLoginname()%></td>
		   						<td align="left"><%=user.getFirstname()+" "+user.getLastname()%></td>
<%					if(data.isWebinar()) {
%>								<td align="center"><%=user.getExtraData3()%></td>
<%					}
%>		   						<td align="center"><%=user.getStatus(questionCount,data.isPsitest(),messages)%></td>
<%					if(user.isFinished()) {
						int percentResult = user.getResult(testQuestionCount);
%>		   						<td align="center"><%=percentResult+" %"%></td>
								<td align="center">
									<a href='<%="javascript:generateReport(\""+user.getLoginname()+"\",1);"%>'>
										<img src="./imgs/pdf.jpg" width="20px;">
									</a>
								</td>
<%						if(data.isCertificate()) {
							boolean certSanofi = true;
							if(id.intValue() == AssesmentData.SANOFI_BRASIL_DA_2019) {
								certSanofi = sys.getUserReportFacade().isResultGreen(user.getLoginname(), new Integer(AssesmentData.SANOFI_BRASIL_EBTW_2019), sys.getUserSessionData());
							}
							if(id.intValue() == AssesmentData.TIMAC_BRASIL_DA_2020) {
								certSanofi = sys.getUserReportFacade().isResultGreen(user.getLoginname(), new Integer(AssesmentData.TIMAC_BRASIL_EBTW_2020), sys.getUserSessionData());
							}
							if(certSanofi && percentResult >= data.getGreen().intValue()) {
%>								<td align="center">
									<a href='<%="javascript:generateReport(\""+user.getLoginname()+"\",2);"%>'>
										<img src="./imgs/pdf.jpg" width="20px;">
									</a>
								</td>
<%							}else {
%>		   						<td align="center">---</td>
<%							}
						}
					}else {
%>		   						<td align="center">---</td>
		   						<td align="center">---</td>
<%						if(data.isCertificate()) {
%>		   						<td align="center">---</td>
<%						}
					}
%>	    					</tr>			    					
<%				}
			}
%>			    					
	      				</table>
						<jsp:include page="../component/utilitybox2bottom.jsp" />
					</td>
				</tr>
				<tr class="line">
					<td align="right" colspan="4">
						<input type="button" class="button" value='<%=messages.getText("assessment.users.resend")%>' onclick="doAction('resend');" />
						<input type="button" class="button" value='<%=messages.getText("generic.messages.delete")%>' onclick="doAction('delete');" />
						<input type="button" class="button" value='<%=messages.getText("generic.messages.cancel")%>' onclick="document.forms['back'].submit();" />
					</td>
				</tr>
			<jsp:include  page='../component/titleend.jsp' />
	  	</form>
	</body>
<%		}
	}
%>  			
</html:html>

