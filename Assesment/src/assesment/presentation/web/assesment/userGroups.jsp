<%@page import="assesment.communication.user.UserData"%>
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
		var form = document.forms['delete'];

		var elements = document.forms[formName].elements;
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
</script>

<script language="JavaScript" src="./css/create_functions.js" type="text/javascript">
</script>
<%!	GroupData data;
   	AssesmentAccess sys; 
   	Text messages;
%>
<%
	sys = (AssesmentAccess)session.getAttribute("AssesmentAccess"); 
	String check = Util.checkPermission(sys,SecurityConstants.ADMINISTRATOR);
	if(check!=null) {
		response.sendRedirect(request.getContextPath()+check);
	}else {

	    session.setAttribute("url","assesment/userGroups.jsp");

	    RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
		dispatcher.include(request,response);
		
		messages=sys.getText();
	
		Integer id = null;
		if(!Util.empty(request.getParameter("group"))) {
			id = new Integer(request.getParameter("group"));
		}else {
			id = new Integer((String)session.getAttribute("group"));
		}
		
		if(id != null){
			data = sys.getAssesmentReportFacade().findGroup(new Integer(id),sys.getUserSessionData());
	        HashMap<String, Collection<UserData>> users = sys.getUserReportFacade().getGroupUsers(new Integer(id),sys.getUserSessionData());
%>

	<head/>
	<body>
		<form action="./layout.jsp?refer=/assesment/deleteGroupUsers.jsp" name='delete' method="post">
			<input type="hidden" name="group" 		value="<%=String.valueOf(id)%>" />
			<input type="hidden" name="users" />
		</form>	
		<form action="./layout.jsp?refer=/assesment/viewGroup.jsp" name='back' method="post">
			<input type="hidden" name="id" value="<%=String.valueOf(id)%>" />
		</form>	
		<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("generic.group")+" "+data.getName()%>' />
<%			Iterator<String> roles = users.keySet().iterator();
			while(roles.hasNext()) {
				String role = roles.next();
%>	  		<tr>
	   			<td width="100%">
					<form name='<%="users_"+role%>'>
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("role."+role+".name")%>' />
		   				<table width="100%" border="0" align="center" cellpadding="2" cellspacing="2">
		   					<tr>
		   						<td align="center" class="guide2" width="2%"></td>
		   						<td align="center" class="guide2" width="23%"><%=messages.getText("user.data.nickname") %></td>
		   						<td align="center" class="guide2" width="35%"><%=messages.getText("report.users.name") %></td>
		   						<td align="center" class="guide2" width="10%"><%=messages.getText("user.data.email")%></td>
	    					</tr>			    					
<%				Iterator<UserData> it = users.get(role).iterator();
				boolean color = false;
				while(it.hasNext()) {
					UserData user = it.next();
	            	color = !color;
%>							
		   					<tr class='<%=(color) ? "lineone" : "linetwo"%>'>
		   						<td align="center"><input type="checkbox" name="<%=user.getLoginName()%>" value="<%=user.getLoginName()%>"/></td>
		   						<td align="left"><%=user.getLoginName()%></td>
		   						<td align="left"><%=user.getFirstName()+" "+user.getLastName()%></td>
		   						<td align="center"><%=user.getEmail()%></td>
	    					</tr>			    					
<%				}
%>			    					
							<tr class="line">
								<td align="right" colspan="4">
									<input type="button" class="button" value='<%=messages.getText("generic.messages.delete")%>' onclick="doAction('<%="users_"+role%>');" />
								</td>
							</tr>
	      				</table>
						<jsp:include page="../component/utilitybox2bottom.jsp" />
					</form>
				</td>
			</tr>
<%			}
%>			    					
			<tr class="line">
				<td align="right" colspan="4">
					<input type="button" class="button" value='<%=messages.getText("generic.messages.cancel")%>' onclick="document.forms['back'].submit();" />
				</td>
			</tr>
		<jsp:include  page='../component/titleend.jsp' />
	</body>
<%		}
	}
%>  			
</html:html>

