<%@page language="java"
	errorPage="../exception.jsp"
	import="java.util.*"
	import="assesment.business.*"
	import="assesment.communication.language.*"
	import="assesment.communication.security.*"
	import="assesment.communication.language.tables.*"
	import="assesment.communication.assesment.*"
	import="assesment.communication.user.*"
	import="assesment.presentation.translator.web.util.*"
	import="assesment.presentation.actions.user.*"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html" 
%>

<%@ taglib uri="/WEB-INF/struts-bean.tld"
        prefix="bean" 
%>

<html:html>

<%! 
	Text messages; 
	String user;
%>
<%
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	String check = Util.checkPermission(sys,SecurityConstants.ADMINISTRATOR);
	if(check!=null) {
		response.sendRedirect(request.getContextPath()+check);
	}else {
		Text messages = sys.getText();
		session.setAttribute("url","user/assesment.jsp");

		RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
		dispatcher.include(request,response);
		
	    user = request.getParameter("user");
	    UserData data = sys.getUserReportFacade().findUserByPrimaryKey(user,sys.getUserSessionData());

		Iterator itAssesments = sys.getUserReportFacade().findActiveAssesments(user,sys.getUserSessionData()).iterator();
		Collection assesmentList = new LinkedList();
		assesmentList.add(new OptionItem(messages.getText("user.assesment.notassociated"),""));
		while(itAssesments.hasNext()){
	        Object[] objData = (Object[])itAssesments.next();
			assesmentList.add(new OptionItem(messages.getText(String.valueOf(objData[1])).trim(),String.valueOf(objData[0])));
		}
		Collections.sort((List)assesmentList);
		session.setAttribute("assesmentList",assesmentList);
%>

	<LINK REL=StyleSheet HREF="../util/css/estilo.css" TYPE="text/css">
	<head/>
	<body>
		<html:form action="/UserAssesment" >
			<html:hidden property="loginname" value='<%=user%>' />
			<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("generic.user.new")%>' />
			  	<tr>
		    		<td valign="top">
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.user.data")%>' />
						<table width="100%" border="0" align="center" cellpadding="2" cellspacing="2">
							<tr class="line">
			   					<td align="right">
									<div align="left"><%=messages.getText("user.data.nickname")%></div>
								</td>
			    			  	<td align="left">
			     					<div align="right"><%=data.getLoginName()%></div>
			    		 		</td>
			  	         	</tr>
			 				<tr class="line">
								<td align="right">
									<div align="left"><%=messages.getText("user.data.firstname")%></div>
								</td>
			    			  	<td align="left">
			     					<div align="right"><%=data.getFirstName()%></div>
			    		 		</td>
							</tr>
							<tr class="line">
								<td align="right">
									<div align="left"><%=messages.getText("user.data.lastname")%></div>
								</td>
			    			  	<td align="left">
			     					<div align="right"><%=data.getLastName()%></div>
			    		 		</td>
							</tr>
							<tr class="line">
							    <td align="right">
									<div align="left"><%=messages.getText("user.data.mail")%></div>
								</td>
								<td align="left">
			     					<div align="right"><%=data.getEmail()%></div>
			    		 		</td>
			 				</tr>
						    <tr class="line">
							    <td align="right">
									<div align="left"><%=messages.getText("user.data.language")%></div>
								</td>
			    			  	<td align="left">
			     					<div align="right"><%=messages.getText(data.getLanguage())%></div>
			    		 		</td>
						  	</tr>
						  	<tr class="line">
								<td align="right">
									<div align="left"><%=messages.getText("user.data.role")%></div>
								</td>
			    			  	<td align="left">
			     					<div align="right"><%=messages.getText("role."+data.getRole()+".name")%></div>
			    		 		</td>
						  	</tr>
							<tr  class="line">
        						<td align="left" valign="top"> <%=messages.getText("user.data.assesement")%></td>
        						<td align="right">
									<html:select property="assesments" styleClass="input" style="width:500px;" size="10" multiple="true" value="0">
										<html:options collection="assesmentList" property="value" labelProperty="label"/>
									</html:select>
      							</td>
							</tr>
      					</table>
						<jsp:include  page="../component/utilitybox2bottom.jsp" />
    				</td>
  				</tr>
				<tr class="line">
 					<td align="right">
           				<html:submit styleClass="button">
							<%=messages.getText("generic.messages.save")%>
						</html:submit>
           				<html:cancel styleClass="button">
							<%=messages.getText("generic.messages.cancel")%>
						</html:cancel>
   					</td>
				</tr>
			<jsp:include  page='../component/titleend.jsp' />
		</html:form>
	</body>
<%	}	%>
</html:html>
