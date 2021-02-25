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
		session.setAttribute("url","module/genericcreate.jsp");

		RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
		dispatcher.include(request,response);
	
		Text messages=sys.getText();
		String type = request.getParameter("type");
		String value = "";

		String id = request.getParameter("module");

		AssesmentAttributes assesment = null;
		ModuleAttribute module = null;
		String[] texts = {"","",""};
		String moduleType = "";
		if(!type.equals("create")) {
		    module = sys.getModuleReportFacade().findGenericModuleAttributes(new Integer(request.getParameter("module")),sys.getUserSessionData());
			moduleType = String.valueOf(module.getType());
		    texts = sys.getLanguageReportFacade().findTexts(module.getKey(),sys.getUserSessionData());
		}
		
		String formName = (type.equals("create")) ? "/GenericModuleCreate" : "/GenericModuleUpdate";

		Collection typeList = new LinkedList();
		typeList.add(new OptionItem(messages.getText("generic.messages.select"),""));
		typeList.add(new OptionItem(messages.getText("module.data.type"+ModuleData.PERSONAL_DATA),String.valueOf(ModuleData.PERSONAL_DATA)));
		typeList.add(new OptionItem(messages.getText("module.data.type"+ModuleData.GENERAL_CULTURE),String.valueOf(ModuleData.GENERAL_CULTURE)));
		typeList.add(new OptionItem(messages.getText("module.data.type"+ModuleData.SECURITY_POLICIES),String.valueOf(ModuleData.SECURITY_POLICIES)));
		typeList.add(new OptionItem(messages.getText("module.data.type"+ModuleData.VEHICLE_SECURITY),String.valueOf(ModuleData.VEHICLE_SECURITY)));
		typeList.add(new OptionItem(messages.getText("module.data.type"+ModuleData.OTHER),String.valueOf(ModuleData.OTHER)));
		typeList.add(new OptionItem(messages.getText("module.data.type"+ModuleData.FINAL_MODULE),String.valueOf(ModuleData.FINAL_MODULE)));
		session.setAttribute("typeList",typeList);
%>
	<head/>
	<body>
		<html:form action='<%=formName%>' >
<%		if(type.equals("edit")) { 
%>			<html:hidden property="module" value='<%=String.valueOf(module.getId())%>'/>
<%		}
%>			<table width="600" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr class="guide1">
					<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("generic.module")%>' />
				</tr>
		      	<tr>
					<jsp:include  page="../component/spaceline.jsp" />
		  		</tr>
		      	<tr>
		      		<td>
		    			<table width='100%' align='center' cellpadding="0" cellspacing="0">
					  		<tr>
								<td>
									<jsp:include  page='<%="../component/utilityboxtop.jsp?title="+messages.getText("generic.module.data")%>' />
									<table align='center' width='100%' cellpadding="0" cellspacing="0">
			      						<tr  class="space">
			      							<td colspan="2">
												<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("module.data.name")%>' />
												<table align='center' width='100%' cellpadding="0" cellspacing="0">
											  		<tr class="lineone">
														<th align="left"><%=messages.getText("module.data.name.es")%></th>
														<td align="right" width="80%">
									   						<html:text property="es_name" value='<%=texts[0]%>' styleClass="input" style="width:100%;"/>              
														</td>
													</tr>
						      						<tr  class="lineone"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
											  		<tr class="lineone">
														<th align="left"><%=messages.getText("module.data.name.en")%></th>
														<td align="right" width="80%">
									   						<html:text property="en_name" value='<%=texts[1]%>' styleClass="input" style="width:100%;"/>      
														</td>
													</tr>
						      						<tr class="lineone"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
											  		<tr class="lineone">
														<th align="left"><%=messages.getText("module.data.name.pt")%></th>
														<td align="right" width="80%">
									   						<html:text property="pt_name" value='<%=texts[2]%>' styleClass="input" style="width:100%;"/>     
														</td>
													</tr>
												</table>
												<jsp:include  page="../component/utilitybox2bottom.jsp" />
											</td>
			      						</tr>
								      	<tr  class="space"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
      									<tr  class="linetwo">
        									<th align="left" > <%=messages.getText("module.data.type")%><span class="required">*</span></th>
			        						<td align="right">
												<html:select property="type" styleClass="input" style="width:160;"  value='<%=moduleType%>'>
													<html:options collection="typeList" property="value" labelProperty="label"/>
												</html:select>
      										</td>
										</tr>
									</table>
									<jsp:include  page="../component/utilityboxbottom.jsp" />
								</td>
							</tr>
					      	<tr>
								<jsp:include  page="../component/spaceline.jsp" />
					  		</tr>
						</table>	
					</td>
				</tr>
		      	<tr>
					<jsp:include  page="../component/spaceline.jsp" />
		  		</tr>
		      	<tr>
		      		<td align="right" colspan="3">
			            <html:submit styleClass="input"><%=messages.getText("generic.messages.save")%></html:submit>
			            <html:cancel styleClass="input"><%=messages.getText("generic.messages.cancel")%></html:cancel>
		      		</td>
		      	</tr>
			</table>
		</html:form>
	</body>
<%	}	
%>
</html:html>
