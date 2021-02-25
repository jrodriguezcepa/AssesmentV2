<%@page import="assesment.presentation.actions.assesment.GroupForm"%>
<%@page import="assesment.communication.util.ListResult"%>
<%@page language="java"
	import="assesment.business.*"
	import="java.util.*"
	import="assesment.communication.language.*"
	import="assesment.communication.security.*"
	import="assesment.communication.assesment.*"
	import="assesment.communication.module.*"
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
		String type = request.getParameter("type");
		session.setAttribute("url","assesment/createGroup.jsp?type="+type);

		RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
		dispatcher.include(request,response);
	
		Text messages=sys.getText();

		String id = request.getParameter("id");

		Collection corporationData = new LinkedList();
		corporationData.add(new OptionItem(messages.getText("generic.messages.select"),""));
		ListResult corporations = sys.getCorporationReportFacade().findCorporationName("",sys.getUserSessionData());
		if(corporations != null && corporations.getElements() != null) {
		    Iterator it = corporations.getElements().iterator();
		    while(it.hasNext()) {
		        Object[] data = (Object[])it.next();
		        corporationData.add(new OptionItem((String)data[1],String.valueOf(data[0])));
		    }
		}
		session.setAttribute("corporations",corporationData);

		String formName = "/GroupCreate";
		if(type.equals("edit")) {
			GroupData group = sys.getAssesmentReportFacade().findGroup(new Integer(id), sys.getUserSessionData());
			GroupForm form = new GroupForm(group);
			session.setAttribute("GroupUpdateForm", form);
			formName = "/GroupUpdate";
		}
		

%>
	<head/>
	<body>
		<html:form action='<%=formName%>'  enctype="multipart/form-data">
<%		if(type.equals("edit")) { 
%>			<html:hidden property="id" value='<%=id%>'/>
<%		}
%>			<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("generic.group")%>' />
		  		<tr>
					<td colspan="2">
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.module.data")%>' />
						<table align='left' width='100%' cellpadding="2" cellspacing="2">
							<tr class="line">
								<td align="left" ><%=messages.getText("assesment.data.name")%><span class="required">*</span></td>
									<td align="right"  width="50%">
				   						<html:text property="name"  style="width:300;" styleClass="input"/>              
									</td>
								</tr>
								<tr class="line">
					        		<td align="left" >
					        		 	<%=messages.getText("assesment.data.corporation")%><span class="required">*</span>
					        		 </td>
				        			<td align="right" >
					      				<html:select property="corporation" styleClass="input" style="width: 300">
						      				<html:options collection="corporations" property="value" labelProperty="label" styleClass="input"/>
							  			</html:select>   
					          		</td>
					          	</tr>
								<tr class="line">
					        		<td align="left" >
					        		 	<%=messages.getText("group.data.layout")%>
					        		 </td>
				        			<td align="right" >
					      				<html:select property="layout" styleClass="input" style="width: 300">
						      				<html:option value="<%=String.valueOf(GroupData.GROUP)%>"><%=messages.getText("group.layout.group")%></html:option>
						      				<html:option value="<%=String.valueOf(GroupData.CLASIC)%>"><%=messages.getText("group.layout.clasic")%></html:option>
							  			</html:select>   
					          		</td>
					          	</tr>
								<tr class="line">
					        		<td align="left" >
					        		 	<%=messages.getText("group.data.repeatable")%>
					        		 </td>
				        			<td align="right" >
					      				<html:select property="repeatable" styleClass="input" style="width: 300">
						      				<html:option value="0"><%=messages.getText("generic.messages.no")%></html:option>
						      				<html:option value="1"><%=messages.getText("generic.messages.yes")%></html:option>
							  			</html:select>   
					          		</td>
					          	</tr>
					     	</table>
						<jsp:include  page="../component/utilityboxbottom.jsp" />
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("group.data.initialtext")%>' />
						<table align='left' width='100%' cellpadding="2" cellspacing="2">
					  		<tr class="line">
								<td align="left"><%=messages.getText("module.data.name.es")%></td>
								<td align="right">
			   						<html:text property="es_initialText" styleClass="input" style="width:300;"/>              
								</td>
							</tr>
					  		<tr class="line">
								<td align="left"><%=messages.getText("module.data.name.en")%></td>
								<td align="right">
			   						<html:text property="en_initialText" styleClass="input" style="width:300;" />              
								</td>
							</tr>
					  		<tr class="line">
								<td align="left"><%=messages.getText("module.data.name.pt")%></td>
								<td align="right">
			   						<html:text property="pt_initialText" styleClass="input" style="width:300;" />
								</td>
							</tr>
						</table>
						<jsp:include  page="../component/utilitybox2bottom.jsp" />
					</td>
				</tr>
		  		<tr class="line">
	        		<td align="left" >
						<%=messages.getText("generic.group.background")%>
					</td>
					<td align="right">
   						<html:file property="logo" size="15" styleClass="line"/>              
					</td>
				</tr>
		      	<tr class="line">
		      		<td align="right" colspan="2">
			            <html:submit styleClass="input"><%=messages.getText("generic.messages.save")%></html:submit>
			            <html:cancel styleClass="input"><%=messages.getText("generic.messages.cancel")%></html:cancel>
		      		</td>
		      	</tr>
			<jsp:include  page='../component/titleend.jsp' />
		</html:form>
	</body>
<%	}	
%>
</html:html>
