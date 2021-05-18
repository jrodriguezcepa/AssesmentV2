<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
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
				    	<table width="100%" border="0" align="center" cellpadding="2" cellspacing="2">
					  		<tr class="line">
				    			<th align="left">
									<%=messages.getText("report.users.name")+": "%>
								</th>
				    			<td align="right">
									<%=user.getFirstName()%>
								</td>
							</tr>
					  		<tr class="line">
				    			<th align="left" valign="top">
									<%=messages.getText("user.data.lastname")+": "%>
								</th>
				    			<td align="right">
										<%=user.getLastName()%>
								</td>
							</tr>
					  		<tr class="line">
				    			<th align="left" valign="top">
									<%=messages.getText("generic.messages.webinarcode")+": "%>
								</th>
				    			<td align="right">
										<%=code%>
								</td>
							</tr>
					  		<tr class="line">
				    			<th align="left" valign="top">
									<%=messages.getText("generic.results.advance")+": "%>
								</th>
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
				    	<table width="100%" border="0" align="center" cellpadding="2" cellspacing="2">
<%			Iterator it = personalData.iterator();
			while(it.hasNext()) {
				Object[] data = (Object[])it.next();
%>					  		<tr class="line">
			    				<th align="left">
									<%=messages.getText((String)data[1])+":"%>
								</th>
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
			    				<td align="right">
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
<%			if(advance[5] != null) {
%>			<tr>
				<td colspan="2">
					<div>
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.webinar.fdmsync")%>' />
<%				Connection connDC = (SecurityConstants.isProductionServer()) ? DriverManager.getConnection("jdbc:postgresql://18.229.182.37:5432/datacenter5","postgres","pr0v1s0r1A") : DriverManager.getConnection("jdbc:postgresql://localhost:5432/datacenter5","postgres","pr0v1s0r1A");
				Statement stDC = connDC.createStatement();
				
				ResultSet set = stDC.executeQuery("SELECT ca.date, gm2.text, ca.code, c.name, d.firstname, d.lastname, d.corporationid, d.email, gm1.text, ca.activityid, d.id "+
							"FROM activityregistry ar JOIN cepaactivity ca on ca.activityid = ar.activity "+
							"JOIN drivers d ON d.id = ar.driver "+
							"JOIN corporations c ON c.id = ca.corporation "+
							"JOIN generalmessages gm1 ON gm1.labelkey = ar.note "+
							"JOIN generalmessages gm2 ON gm2.labelkey = ca.type "+
							"WHERE activityregistryid = " + advance[5] +
							" AND gm1.language = 'es' "+
							"AND gm2.language = 'es'");
				if(set.next()) {
%>				    	<table width="100%" border="0" align="center" cellpadding="2" cellspacing="2">
				  			<tr class="line">
			    				<th align="left">
									<%=messages.getText("generic.activity")+": "%>
								</th>
			    				<td align="right">
				    				<a href='<%="https://fdmpro.cepasafedrive.com/datacenter/home.jsp?refer=/activity/schedule/CEPAActivityScheduleView.jsp?id="+set.getInt(10)%>'  target="_blank">
										<%=Util.formatDate(set.getDate(1))+" - "+set.getString(2)%>
									</a>
								</td>
							</tr>
					  		<tr class="line">
			    				<th align="left">
									<%=messages.getText("generic.corporation")+": "%>
								</th>
			    				<td align="right">
									<%=set.getString(4)%>
								</td>
							</tr>
					  		<tr class="line">
			    				<th align="left">
									<%=messages.getText("role.systemacces.name")+": "%>
								</th>
			    				<td align="right">
				    				<a href='<%="https://fdmpro.cepasafedrive.com/datacenter/home.jsp?refer=/driver/DriverView.jsp?id="+set.getInt(11)%>'  target="_blank">
										<%=set.getString(5)+" "+set.getString(6)%>
									</a>
								</td>
							</tr>
					  		<tr class="line">
			    				<th align="left">
									<%=messages.getText("user.data.mail")+": "%>
								</th>
			    				<td align="right">
									<%=set.getString(8)%>
								</td>
							</tr>
					  		<tr class="line">
			    				<th align="left">
									<%=messages.getText("module3856.question46964.text")+": "%>
								</th>
			    				<td align="right">
									<%=set.getString(7)%>
								</td>
							</tr>

<%				}
				stDC.close();
				connDC.close();
%>
						<jsp:include page="../component/utilityboxbottom.jsp" />
					</div>
				</td>
			</tr>
<%			}
		}
%>   

		<jsp:include  page='../component/titleend.jsp' />
	</body>
<%		}%>  			

</html:html>

