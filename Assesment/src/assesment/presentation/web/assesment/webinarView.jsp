<%@page language="java"
	import="assesment.business.*"
	import="assesment.communication.language.*"
	import="assesment.communication.security.*"
	import="assesment.communication.user.*"
	import="assesment.communication.administration.corporation.tables.*"
	import="assesment.communication.corporation.*"
	import="assesment.presentation.translator.web.util.*"
	import="assesment.persistence.administration.tables.*"
	
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
		String type=(!Util.empty(request.getParameter("type")))?(String)request.getParameter("type"):"";
		Collection participants = sys.getUserReportFacade().findWebinarParticipants(code, assesmentId, sys.getUserSessionData());
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
									<%=messages.getText("generic.data.assesmenttype")+": "%>
								</td>
				    			<td align="right">
									<%=messages.getText(type)%>
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
						</table>
					<jsp:include page="../component/utilityboxbottom.jsp" />
				</td>
			</tr>
			
			<tr>
				<td colspan="2">
					<div >
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.messages.participants")%>' />
					    	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
					      		<tr>
							    	<td class="guide2" width="5%">&nbsp;</td>
							        <td class="guide2" width="50%" align="left"><%=messages.getText("report.users.name") %></td>
							        <td class="guide2" width="20%" align="left"><%=messages.getText("user.data.lastname") %></td>
							        <td class="guide2" width="20%" align="left"><%=messages.getText("generic.results.advance") %></td>
						        </tr>
<%			if(participants.size() == 0) {
%>				      			<tr class="linetwo">
					            	<td colspan="4"><%=messages.getText("generic.messages.notresult")%></td>
            					</tr>
<%			}else {
		    	Iterator it = participants.iterator();
				boolean linetwo = false;
				while(it.hasNext()){
					AssessmentUserData user = (AssessmentUserData)it.next();
					String[] advance= sys.getAssesmentReportFacade().getWebinarAdvance(code, assesmentId, user.getLoginname(),sys.getUserSessionData());
					String link = "layout.jsp?refer=/assesment/webinarUserView.jsp?loginname="+user.getLoginname()+"&code="+code+"&assesment="+assesmentId;
%>	            				<tr class='<%=(linetwo)?"linetwo":"lineone"%>'>
<%					linetwo = !linetwo;	
%>          	    				<td width="5%" align="left">
									</td>
				              		<td width="50%" align="left">
					              		<a href='<%=link%>'>
								        	<%=user.getFirstname() %>
								        </a>
									</td>
		            				<td width="20%" align="left">	
					              		<a href='<%=link%>'>
			            					<%=user.getLastname() %>
								        </a>
		            				</td>
		        	    	      	<td width="20%" align="left">
		        	    	      		<%=advance[0]%>
		        	    	      	</td>
				    	    	</tr>
<%				}
 			}
%>							</table>
						<jsp:include page="../component/utilityboxbottom.jsp" />
					</div>
				</td>
			</tr>
   			<%	
 %>

		<jsp:include  page='../component/titleend.jsp' />
	</body>
<%		}%>  			

</html:html>

