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
		session.setAttribute("url","module/create.jsp");

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
		String greenV = "";
		String yellowV = "";
		if(type.equals("create")) {
		    assesment = sys.getAssesmentReportFacade().findAssesmentAttributes(new Integer(request.getParameter("assesment")),sys.getUserSessionData());
		}else {
		    module = sys.getModuleReportFacade().findModuleAttributes(new Integer(request.getParameter("module")),sys.getUserSessionData());
		    moduleType = String.valueOf(module.getType());
		    if(module.getGreen() != null)
		    	greenV =  String.valueOf(module.getGreen());
		    if(module.getYellow() != null)
		    	yellowV =  String.valueOf(module.getYellow());
		    texts = sys.getLanguageReportFacade().findTexts(module.getKey(),sys.getUserSessionData());
		    assesment = sys.getAssesmentReportFacade().findAssesmentAttributes(module.getAssesment(),sys.getUserSessionData());
		}
		CorporationAttributes corporation = sys.getCorporationReportFacade().findCorporationAttributes(assesment.getCorporationId(),sys.getUserSessionData());
		
		String formName = (type.equals("create")) ? "/ModuleCreate" : "/ModuleUpdate";

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
<%		}else {
%>			<html:hidden property="assesment" value='<%=String.valueOf(assesment.getId())%>'/>
<%		}
%>
			<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("generic.module")%>' />
		      	<tr>
		      		<td>
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.module.data")%>' />
						<table align='center' width='100%' cellpadding="5" cellspacing="5">
							<tr class="line">
								<td align="left">
									<%=messages.getText("module.data.corporation")%>
								</td>
								<td align="right">
			   						<%=corporation.getName()%>              
								</td>
				          	</tr>
							<tr class="line">
								<td align="left">
									<%=messages.getText("module.data.assesment")%>
								</td>
								<td align="right">
			   						<%=messages.getText(assesment.getName())%>              
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("module.data.name")%>' />
									<table width="100%" cellpadding="0" cellspacing="0">	
										<tr class="line">
											<td align="left">
												<%=messages.getText("module.data.name.es")%>
											</td>
											<td align="right" width="80%">
									   			<html:text property="es_name" value='<%=texts[0]%>' styleClass="input" style="width:100%;"/>              
											</td>
										</tr>
										<tr class="line">
											<td align="left">
												<%=messages.getText("module.data.name.en")%>
											</td>
											<td align="right" width="80%">
						   						<html:text property="en_name" value='<%=texts[1]%>' styleClass="input" style="width:100%;" />              
											</td>
										</tr>
										<tr class="line">
											<td align="left">
												<%=messages.getText("module.data.name.pt")%>
											</td>
											<td align="right" width="80%">
									   			<html:text property="pt_name" value='<%=texts[2]%>' styleClass="input" style="width:100%;" />
											</td>
										</tr>
									</table>
									<jsp:include  page="../component/utilitybox2bottom.jsp" />
								</td>
			      			</tr>
							<tr class="line">
								<td align="left">
        							<%=messages.getText("module.data.type")%><span class="required">*</span>
        						</td>
        						<td align="right">
									<html:select property="type" styleClass="input" style="width:160;"  value='<%=moduleType%>'>
										<html:options collection="typeList" property="value" labelProperty="label"/>
									</html:select>
   								</td>
							</tr>
							<tr class="line">
								<td align="left">
				    				<%=messages.getText("report.generalresult.minhighlevel")%><span class="required">*</span>
				    			</td>
		   						<td align="right">
			   						<html:text property="green" size="10" styleClass="input" value='<%=greenV%>'/>              
			  					</td>
	      					</tr>
							<tr class="line">
								<td align="left">
	    							<%=messages.getText("report.generalresult.minmeddiumlevel")%><span class="required">*</span>
	    						</td>
		   						<td align="right">
			   						<html:text property="yellow" size="10" styleClass="input" value='<%=yellowV%>'/>              
			  					</td>
	      					</tr>
						</table>
						<jsp:include  page="../component/utilityboxbottom.jsp" />
					</td>
				</tr>
		      	<tr>
		      		<td align="right" colspan="3">
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
