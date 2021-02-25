<%@page import="assesment.communication.util.ListResult"%>
<%@page import="assesment.communication.corporation.CorporationData"%>
<%@page import="assesment.communication.assesment.GroupData"%>
<%@page language="java"
	import="assesment.business.*"
	import="assesment.business.administration.user.*"
	import="assesment.communication.language.*"
	import="assesment.communication.administration.user.tables.*"
	import="assesment.presentation.translator.web.user.*"
	import="assesment.communication.language.tables.*"
	import="assesment.presentation.translator.web.util.Util"
	import="assesment.communication.administration.corporation.tables.*"
	import="java.util.*"
 	import="assesment.communication.administration.user.*" 		
 	import="assesment.presentation.translator.web.util.*"
 	import="assesment.communication.security.*"
	import="assesment.communication.assesment.AssesmentData"
	import="assesment.communication.user.UserData"
	import="assesment.communication.administration.AccessCodeData"
	errorPage="../exception.jsp"
%>
<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>

<%
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
 	Text messages = sys.getText();  
 	
 	Collection<CorporationData> list = sys.getCorporationReportFacade().getPrincipalResume(sys.getUserSessionData());
%>
	<div style="max-width:800px;text-align:center;margin-left:20px;font-family:Avenir;font-size:1.55em;">
		<%=messages.getText("generic.maindata")%>
	</div>
	<br>
 	<div style="max-width:800px;text-align:center;margin-left:20px;font-family:Avenir;font-size:.95em;">
 		<table width="100%" height="90" border="0" cellpadding="0" cellspacing="0" class="default">
			<tr style="background-color:#CDCDCD;font-family:Avenir;font-size:1.15em;height:25px;">
				<th align="left" style="padding-left:10px;">
					<%=messages.getText("generic.corporation")%>
				</th>
				<th>
						<%=messages.getText("corporation.data.assesments") %>
				</th>
				<th>
					<%=messages.getText("corporation.data.groups")%>
				</th>
			</tr>
<%	Collections.sort((List)list);
	Iterator<CorporationData> it = list.iterator();
	boolean white = true;
	while(it.hasNext()) {
		CorporationData corp = it.next();
		String color = (white) ? "#FFFFFF" : "#EDEDED";
		white = !white;
%> 	
			<tr style="background-color:<%=color%>;font-family:Avenir;font-size:1.15em;height:25px;">
				<td align="left" style="padding-left:10px;">
					<a href='layout.jsp?refer=/corporation/view.jsp?corporation=<%=String.valueOf(corp.getId())%>' >
						<span style="text-decoration: none; color: #000000;"><%=corp.getName()%></span>
					</a>
				</td>
				<td align="center">
<% 		if(corp.getAssessmentCount() == null) {
%>					---
<%		} else {
			if(corp.getAssessmentCount().intValue() == 1) {
				ListResult result = sys.getAssesmentReportFacade().findAssesments("",corp.getId().toString(),"0",sys.getUserSessionData());
				if(result.getTotal() == 0) {
%>					---
<%				} else {
					Object[] assesment = (Object[])result.getElements().iterator().next();				
%>					<a href='layout.jsp?refer=/assesment/view.jsp?assesment=<%=String.valueOf(assesment[0])%>' >
						<span style="text-decoration: none; color: #000000;"><%=String.valueOf(corp.getAssessmentCount())%></span>
					</a>
<%				}
			} else {
%>					<a href='layout.jsp?refer=/assesment/list.jsp?corporation=<%=String.valueOf(corp.getId())%>' >
						<span style="text-decoration: none; color: #000000;"><%=String.valueOf(corp.getAssessmentCount())%></span>
					</a>
<%			}
		}
%>				</td>
				<td align="center">
<% 		if(corp.getGroupCount() == null) {
%>					---
<%		} else {
			if(corp.getGroupCount().intValue() == 1) {
				Collection groups = sys.getAssesmentReportFacade().findGroups(null, corp.getId(), sys.getUserSessionData());
				Object[] group = (Object[])groups.iterator().next();
%>					<a href='layout.jsp?refer=/assesment/viewGroup.jsp?id=<%=String.valueOf(group[0])%>' >
						<span style="text-decoration: none; color: #000000;"><%=String.valueOf(corp.getGroupCount())%></span>
					</a>
<%			} else {
%>					<a href='layout.jsp?refer=/assesment/groupList.jsp?corporation=<%=String.valueOf(corp.getId())%>' >
						<span style="text-decoration: none; color: #000000;"><%=String.valueOf(corp.getGroupCount())%></span>
					</a>
<%			}
		}
%>				</td>
			</tr>
<%	}
%> 	
		</table>
