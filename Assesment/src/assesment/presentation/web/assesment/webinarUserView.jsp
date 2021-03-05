<%@page import="assesment.communication.question.QuestionData"%>
<%@page language="java"
	import="assesment.business.*"
	import="assesment.communication.language.*"
	import="assesment.communication.security.*"
	import="assesment.communication.user.*"
	import="assesment.communication.administration.corporation.tables.*"
	import="assesment.communication.corporation.*"
	import="assesment.communication.assesment.*"
	import="assesment.presentation.translator.web.util.*"
	import="assesment.persistence.user.tables.*"
	
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
	function confirmDelete(form,message) {
		if(confirm(message)) {
			form.submit();
		}
	}

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

<script language="JavaScript" src="./css/create_functions.js" type="text/javascript">
</script>


<%!	CorporationData data;
   	AssesmentAccess sys; 
   	Text messages;
%>
<%
	sys = (AssesmentAccess)session.getAttribute("AssesmentAccess"); 
	String check = Util.checkPermission(sys,SecurityConstants.ADMINISTRATOR);
	if(check!=null) {
		response.sendRedirect(request.getContextPath()+check);
	}else {
		session.setAttribute("url","corporation/view.jsp");
		messages=sys.getText();
		String assesmentId=(!Util.empty(request.getParameter("assesment")))?(String)request.getParameter("assesment"):"";
		String code=(!Util.empty(request.getParameter("code")))?(String)request.getParameter("code"):"";
		String loginName=(!Util.empty(request.getParameter("loginname")))?(String)request.getParameter("loginname"):"";
		UserData user= sys.getUserReportFacade().findUserByPrimaryKey(loginName, sys.getUserSessionData());
		String [] advance=sys.getAssesmentReportFacade().getWebinarAdvance(code, assesmentId, loginName, sys.getUserSessionData());
%>

<head/>

	<body>

		<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("generic.data.webinar")%>' />
	  		
	  		<tr>
	  			<td>
			  		<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.data.webinar")%>' />
				    	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
					  		<tr class="line">
				    			<td align="left">
									<%=messages.getText("report.users.name")+": "%>
								</td>
				    			<td align="right">
									<%=user.getFirstName()%>
								</td>
							</tr>
					  		<tr class="line">
				    			<td align="left" valign="top">
									<%=messages.getText("user.data.lastname")+": "%>
								</td>
				    			<td align="right">
										<%=user.getLastName()%>
								</td>
							</tr>
					  		<tr class="line">
				    			<td align="left" valign="top">
									<%=messages.getText("generic.messages.webinarcode")+": "%>
								</td>
				    			<td align="right">
										<%=code%>
								</td>
							</tr>
					  		<tr class="line">
				    			<td align="left" valign="top">
									<%=messages.getText("generic.results.advance")+": "%>
								</td>
				    			<td align="right">
										<%=advance[0]%>
								</td>
							</tr>
						</table>
					<jsp:include page="../component/utilityboxbottom.jsp" />
				</td>
			</tr>
<%		Collection personalData = sys.getAssesmentReportFacade().getWebinarPersonalData(loginName, sys.getUserSessionData());
		if(personalData != null && personalData.size() > 0) {
%>	  		<tr>
	  			<td>
			  		<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.data.personaldata")%>' />
				    	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<%			Iterator it = personalData.iterator();
			while(it.hasNext()) {
				Object[] data = (Object[])it.next();
%>					  		<tr class="line">
			    				<td align="right">
									<%=messages.getText((String)data[1])%>
								</td>
<%				String value = "---";
				switch(((Integer)data[2]).intValue()) {
				case QuestionData.EXCLUDED_OPTIONS:
					value = messages.getText("question"+data[0]+".answer"+data[4]+".text");
					break;
				case QuestionData.DATE:
					value = Util.formatDate((Date)data[6]);
					break;
				case QuestionData.EMAIL: case QuestionData.TEXT:
					value = (String)data[5];
					break;
				}
%>								
			    				<td align="left">
									<%=value%>
								</td>
							</tr>
<%			}
		}
		if(advance[0].equals("Completo")) {
%>	
			<tr>
				<td colspan="2">
					<div >
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("javareport.answers.type1")%>' />
					    	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
					      		<tr>
							    	<td class="guide2" width="5%">&nbsp;</td>
							        <td class="guide2" width="30%" align="left"><%=messages.getText("report.userresult.right") %></td>
							        <td class="guide2" width="30%" align="left"><%=messages.getText("report.userresult.wrong") %></td>
							        <td class="guide2" width="30%" align="left"><%=messages.getText("report.userresult.percent") %></td>
						       		<td class="guide2" width="5%">&nbsp;</td>
						       
						        </tr>
<%					boolean linetwo = false; %>
	            				<tr class='<%=(linetwo)?"linetwo":"lineone"%>'>
<%					linetwo = !linetwo;	
%>          	    				<td width="5%" align="left">
									</td>
				              		<td width="30%" align="left">
							        	<%=advance[2] %>
									</td>
		            				<td width="30%" align="left">	<%=advance[3] %></td>
		        	    	      	<td width="30%" align="left"><%=advance[4]%></td>
				    	    	     <td width="5%" align="left">
									</td>
				    	    	</tr>
							</table>
						<jsp:include page="../component/utilityboxbottom.jsp" />
					</div>
				</td>
			</tr>
<%			}
%>   

		<jsp:include  page='../component/titleend.jsp' />
	</body>
<%		}%>  			

</html:html>

