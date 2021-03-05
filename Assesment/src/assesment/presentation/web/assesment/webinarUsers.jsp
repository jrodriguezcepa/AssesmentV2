<%@page language="java" 
	errorPage="../exception.jsp"
	import="java.util.*"
	import="assesment.business.*"
	import="assesment.communication.language.*"
	import="assesment.communication.security.*"
	import="assesment.communication.corporation.*"
	import="assesment.communication.util.*"
	import="assesment.presentation.translator.web.util.*"
	import="assesment.persistence.administration.tables.*"
	
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<%!
	Text messages;	AssesmentAccess sys;
%>
<%
	sys=(AssesmentAccess)session.getAttribute("AssesmentAccess");
	String check = Util.checkPermission(sys,SecurityConstants.ADMINISTRATOR);
	
	RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
	dispatcher.include(request,response);

	if(check!=null) {
		response.sendRedirect(request.getContextPath()+check);
	}else {
		session.setAttribute("url","assesment/groupList.jsp");
	
		if(session.getAttribute("Msg")!=null){
			session.removeAttribute("Msg");
		}
		messages=sys.getText();
	
	
	  	String wbCode = (Util.empty(request.getParameter("wbCode"))) ? "" : request.getParameter("wbCode");
	  	String firstName = (Util.empty(request.getParameter("firstName"))) ? "" : request.getParameter("firstName");
	  	String lastName = (Util.empty(request.getParameter("lastName"))) ? "" : request.getParameter("lastName");
	  
	  	Collection users = null;
	  	if(!Util.empty(wbCode) || !Util.empty(firstName) || !Util.empty(lastName))
	  		users = sys.getUserReportFacade().findAllWebinarParticipants(wbCode, firstName, lastName, sys.getUserSessionData());
%>
<script language="javascript" src='../util/js/Prepared_Parameters.js' type='text/javascript' ></script>

<script>
function deleteIFConfirm(form,msg){
	var elements=form.elements;
	var length=elements.length;
	var i;
	var valueCheckboxParamList="";
	var separator="<";
		
	for(i=0;i<length;i++){
		var element=elements[i];
		if(element.type.toLowerCase()=="checkbox"){
			if(element.checked){
				if(valueCheckboxParamList==""){
					valueCheckboxParamList=element.value;
				}else{
					valueCheckboxParamList=element.value+"<"+valueCheckboxParamList;
				}	
			}	
		} 
	}
	form.assesment.value=valueCheckboxParamList;
	if(valueCheckboxParamList.length>0){
		if(confirm(msg)){
			form.submit();
		}
	}
}
</script>
	<LINK REL=StyleSheet HREF="../util/css/estilo.css" TYPE="text/css">
		
<head/>

	<body>
		<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("generic.data.users")%>' />
  		<tr>
    		<td width="100%" valign="top">
				<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.messages.search")%>' />
				<form action="layout.jsp?refer=/assesment/webinarUsers.jsp" method="post">
		    		<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td align="left" class="line"><%=messages.getText("report.users.name")%></td>
							<td align="right">
								<input type="text" name="firstName" style="width: 500px;" class="input" value='<%=firstName%>'/>
							</td>
						</tr>
						<tr>
							<td align="left" class="line"><%=messages.getText("user.data.lastname")%></td>
							<td align="right">
								<input type="text" name="lastName" style="width: 500px;" class="input" value='<%=lastName%>'/>
							</td>
						</tr>
						<tr>
							<td align="left" class="line"><%=messages.getText("generic.messages.webinarcode")%></td>
							<td align="right">
								<input type="text" name="wbCode" style="width: 500px;" class="input" value='<%=wbCode%>'/>
							</td>
						</tr>
						<tr>
							<td colspan="2" align="right">
								<input name="button" type="submit" value='<%=messages.getText("generic.messages.search")%>' class="input"/>
							</td>
						</tr>
					</table>
				</form>
				<jsp:include  page="../component/utilitybox2bottom.jsp" />
			</td>
  		</tr>
		<tr>
			<td colspan="3">
					<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.data.users")%>' />
			    	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			      		<tr>
					        <td class="guide2" width="5%" align="left"></td>
					        <td class="guide2" width="25%" align="left"><%=messages.getText("report.users.name")%></td>
					        <td class="guide2" width="25%" align="left"><%=messages.getText("user.data.lastname")%></td>
					        <td class="guide2" width="20%" align="left"><%=messages.getText("generic.messages.webinarcode")%></td>
				        	<td class="guide2" width="20%" align="left"><%=messages.getText("generic.results.advance")%></td>
				        	<td class="guide2" width="10%" align="left"></td>
				        
				        </tr>
<%			if(users == null || users.size() == 0) {
%>		      			<tr class="linetwo">
			            	<td colspan="5"><%=messages.getText("generic.messages.notresult")%></td>
            			</tr>
<%			}else {
		    	Iterator it = users.iterator();
				boolean linetwo = false;
				while(it.hasNext()){
					linetwo = !linetwo;	
					AssessmentUserData user = (AssessmentUserData)it.next();
					String [] progress=sys.getAssesmentReportFacade().getWebinarAdvance(user.getExtraData3(), String.valueOf(user.getAssesment()), user.getLoginname(), sys.getUserSessionData());

%>	            		<tr class='<%=(linetwo)?"linetwo":"lineone"%>'>
					        <td width="5%" align="left"></td>
	          	    		<td width="25%" align="left">
	          	    			<a href='layout.jsp?refer=/assesment/webinarUserView.jsp?loginname=<%=user.getLoginname()%>&code=<%=user.getExtraData3()%>&assesment=<%= String.valueOf(user.getAssesment())%>' >
	        			        	<%=user.getFirstname()+" "+user.getLastname()%>
	                			</a>
							</td>
							<td width="25%" align="left">
								<a href='<%="layout.jsp?refer=/assesment/webinarView.jsp?code="+user.getExtraData3()+"&type=assessment"+user.getAssesment()+".name&assesment="+user.getAssesment()%>' >
	        			        	<%=user.getExtraData3()%>
	                			</a>
							</td>            				
							<td width="20%" align="left"><%=messages.getText("assessment"+user.getAssesment()+".name")%></td>
            				<td width="20%" align="left"><%=user.getWebinarStatus(messages)%></td>
            				<td width="5%" align="left"></td>
            				 
            				
		    	    	</tr>
<%				}
%>		      			<tr class="linetwo">
			            	<td colspan="5">&nbsp;</td>
            			</tr>
<% 			}
%>					</table>
					<jsp:include page="../component/utilitybox2bottom.jsp" />
				</td>
			</tr>
		<jsp:include  page='../component/titleend.jsp' />
	</body>
<%
	}
%>
</html>