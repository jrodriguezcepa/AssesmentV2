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

		session.setAttribute("url","corporation/create.jsp");

	
		RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
		dispatcher.include(request,response);
	
		Text messages=sys.getText();
		String type = request.getParameter("type");
		String value = "";
		String logo = "*.png";
		String id = request.getParameter("corporation");
		if(type.equals("edit")) { 
    		CorporationAttributes corporation	 = sys.getCorporationReportFacade().findCorporationAttributes(new Integer(id),sys.getUserSessionData());
    		value = corporation.getName();
    		logo = (corporation.getLogo() != null) ? corporation.getLogo() : "*.png";
		}
		
		String formName = (type.equals("create")) ? "/CorporationCreate" : "/CorporationUpdate";
%>
	<head/>
	<body>
		<html:form action='<%=formName%>' enctype="multipart/form-data">
<%		if(type.equals("edit")) { 
%>			<html:hidden property="id" value='<%=id%>'/>
<%		}
%>			<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("generic.corporation")%>' />
		      	<tr>
		      		<td>
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.corporation.data")%>' />
						<table align='center' width='100%' cellpadding="2" cellspacing="2">
					  		<tr class="line">
								<td align="left"><%=messages.getText("generic.corporation.corporationdata.name")%></td>
				    			<td align="right">
			   						<html:text property="name" size="50" styleClass="input" value='<%=value%>'/>              
								</td>
							</tr>
					  		<tr class="line">
								<td align="left">
									<%=messages.getText("generic.corporation.corporationdata.logo")+" ("+logo+")"%>
								</td>
				    			<td align="right">
			   						<html:file property="logo" size="15" styleClass="line"/>              
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
