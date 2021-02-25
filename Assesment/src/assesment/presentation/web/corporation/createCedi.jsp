<%@page language="java"
	import="assesment.business.*"
	import="java.util.*"
	import="assesment.communication.language.*"
	import="assesment.communication.security.*"
	import="assesment.presentation.translator.web.util.*"
	import="assesment.communication.corporation.*"
	errorPage="../exception.jsp"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>
<html:html>

<LINK REL=StyleSheet HREF="../util/css/estilo.css" TYPE="text/css">
<%
	AssesmentAccess sys=(AssesmentAccess)session.getAttribute("AssesmentAccess");
	String check = Util.checkPermission(sys,SecurityConstants.ADMINISTRATOR);
	if(check!=null) {
		response.sendRedirect(request.getContextPath()+check);
	}else {

		session.setAttribute("url","corporation/createCedi.jsp");

	
		RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
		dispatcher.include(request,response);
	
		Text messages=sys.getText();
		String type = request.getParameter("type");
		String company = request.getParameter("company");
		if(company == null)
			company = (String) session.getAttribute("company");
		String value = "";
		String accessCode="";
		String uen="";
		String drv="";
		String manager="";
		String managerMail="";
		String loginName="";

		String id = request.getParameter("cedi");
		if(type.equals("edit")) { 
			CediAttributes cedi = sys.getCorporationReportFacade().findCediAttributes(new Integer(id),sys.getUserSessionData());
			value=cedi.getName();
			accessCode=cedi.getAccessCode();
			uen=cedi.getUen();
			drv=cedi.getDrv();
			manager=cedi.getManager();
			managerMail=cedi.getManagerMail();
			loginName=cedi.getLoginName();
			company=cedi.getCompany().toString();
		}
		
		String compName = (new Integer(company).equals(CediData.GRUPO_MODELO)) ? "cedi" : "mutualcompany";
		String formName = (type.equals("create")) ? "/CediCreate" : "/CediUpdate";
%>
	<head/>
	<body>
		<html:form action='<%=formName%>' enctype="multipart/form-data">
			<html:hidden property="company" value='<%=company%>'/>
<%		if(type.equals("edit")) { 
%>			<html:hidden property="id" value='<%=id%>'/>
<%		}
%>			<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("generic."+compName)%>' />
		      	<tr>
		      		<td>
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic."+compName+".data")%>' />
						<table align='center' width='100%' cellpadding="2" cellspacing="2">
					  		<tr class="line">
								<td align="left"><%=messages.getText("generic."+compName+".cedidata.name")%></td>
				    			<td align="right">
			   						<html:text property="name" size="50" styleClass="input" value='<%=value%>'/>              
								</td>
							</tr>
					  		<tr class="line">
								<td align="left"><%=messages.getText("generic.cedi.cedidata.accesscode")%></td>
				    			<td align="right">
			   						<html:text property="accessCode" size="50" styleClass="input" value='<%=accessCode%>'/>              
								</td>
							</tr>
<%				if(new Integer(company).equals(CediData.GRUPO_MODELO)) {
%>					  		<tr class="line">
								<td align="left"><%=messages.getText("generic.cedi.cedidata.uen")%></td>
				    			<td align="right">
			   						<html:text property="uen" size="50" styleClass="input" value='<%=uen%>'/>              
								</td>
							</tr>
					  		<tr class="line">
								<td align="left"><%=messages.getText("generic.cedi.cedidata.drv")%></td>
				    			<td align="right">
			   						<html:text property="drv" size="50" styleClass="input" value='<%=drv%>'/>              
								</td>
							</tr>
							<tr class="line">
								<td align="left"><%=messages.getText("generic.cedi.cedidata.manager")%></td>
				    			<td align="right">
			   						<html:text property="manager" size="50" styleClass="input" value='<%=manager%>'/>              
								</td>
							</tr>
							<tr class="line">
								<td align="left"><%=messages.getText("generic.cedi.cedidata.managermail")%></td>
				    			<td align="right">
			   						<html:text property="managerMail" size="50" styleClass="input" value='<%=managerMail%>'/>              
								</td>
							</tr>
<%				}
%>					<tr class="line">
								<td align="left"><%=messages.getText("generic.cedi.cedidata.loginname")%></td>
				    			<td align="right">
			   						<html:text property="loginName" size="50" styleClass="input" value='<%=loginName%>'/>              
								</td>
							</tr>
						</table>
						<jsp:include  page="../component/utilitybox2bottom.jsp" />
					</td>
				</tr>
		      	<tr>
		      		<td align="right">
			            <html:submit styleClass="button"><%=messages.getText("generic.messages.save")%></html:submit>
			            <html:cancel styleClass="button"><%=messages.getText("generic.messages.cancel")%></html:cancel>
		      		</td>
		      	</tr>
			<jsp:include  page='../component/titleend.jsp' />
		</html:form>
	</body>
<%	}	
%>
</html:html>
