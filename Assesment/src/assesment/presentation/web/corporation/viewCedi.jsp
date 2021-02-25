<%@page language="java"
	import="assesment.business.*"
	import="assesment.communication.language.*"
	import="assesment.communication.security.*"
	import="assesment.communication.administration.corporation.tables.*"
	import="assesment.communication.corporation.*"
	import="assesment.communication.assesment.*"
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


<%!	CediData data;
   	AssesmentAccess sys; 
   	Text messages;
%>
<%
	sys = (AssesmentAccess)session.getAttribute("AssesmentAccess"); 
	String check = Util.checkPermission(sys,SecurityConstants.ADMINISTRATOR);
	if(check!=null) {
		response.sendRedirect(request.getContextPath()+check);
	}else {
		session.setAttribute("url","corporation/viewCedi.jsp");
		messages=sys.getText();
	
		Integer id = null;
		if(!Util.empty(request.getParameter("cedi"))) {
			id = new Integer(request.getParameter("cedi"));
		}else {
			id = new Integer((String)session.getAttribute("cedi"));
		}
		
		if(id != null){
			data = sys.getCorporationReportFacade().findCedi(id,sys.getUserSessionData());
			Integer company = data.getCompany();
			String compName = (new Integer(company).equals(CediData.GRUPO_MODELO)) ? "cedi" : "mutualCompany";
%>

<head/>

	<body>

		<form action='<%="./layout.jsp?refer=/corporation/"+compName+"List.jsp"%>'  name='back' method="post">
		</form>	
		<form action="./layout.jsp?refer=/corporation/createCedi.jsp" name='edit' method="post">
			<input type="hidden" name="cedi"  value='<%=id%>' />
			<input type="hidden" name="company"  value='<%=String.valueOf(company)%>' />
			<input type="hidden" name="type" value="edit" />
		</form>	
		<html:form action="/CediDelete" >
			<input type="hidden" name="cedi" value='<%=id%>' />
	  	</html:form>
		<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("generic."+compName.toLowerCase()+".data")%>' />
	  		<tr>			  		
				<jsp:include page='<%="viewCediOptions.jsp?data="+compName.toLowerCase()%>' />
			</tr>
	  		<tr>
	  			<td>
			  		<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic."+compName.toLowerCase()+".data")%>' />
				    	<table width="100%" border="0" align="center" cellpadding="2" cellspacing="2">
					  		<tr class="line">
				    			<td align="left" valign="top">
									<%=messages.getText("generic."+compName.toLowerCase()+".cedidata.name")+": "%>
								</td>
				    			<td align="left">
									<%=data.getName()%>
								</td>
							</tr>
					  		<tr class="line">
				    			<td align="left" valign="top">
									<%=messages.getText("generic.cedi.cedidata.accesscode")+": "%>
								</td>
				    			<td align="left">
									<%=data.getAccessCode()%>
								</td>
							</tr>	
<%				if(company.equals(CediData.GRUPO_MODELO)) {
%>					  		<tr class="line">
				    			<td align="left" valign="top">
									<%=messages.getText("generic.cedi.cedidata.uen")+": "%>
								</td>
				    			<td align="left">
									<%=data.getUen()%>
								</td>
							</tr>	
					  		<tr class="line">
				    			<td align="left" valign="top">
									<%=messages.getText("generic.cedi.cedidata.drv")+": "%>
								</td>
				    			<td align="left">
									<%=data.getDrv()%>
								</td>
							</tr>							
					  		<tr class="line">
				    			<td align="left" valign="top">
									<%=messages.getText("generic.cedi.cedidata.manager")+": "%>
								</td>
				    			<td align="left">
									<%=data.getManager()%>
								</td>
							</tr>
					  		<tr class="line">
				    			<td align="left" valign="top">
									<%=messages.getText("generic.cedi.cedidata.managermail")+": "%>
								</td>
				    			<td align="left">
									<%=data.getManagerMail()%>
								</td>
							</tr>
<%				}
%>							<tr class="line">
				    			<td align="left" valign="top">
									<%=messages.getText("generic.cedi.cedidata.loginname")+": "%>
								</td>
				    			<td align="left">
									<%=data.getLoginName()%>
								</td>
							</tr>							
						</table>
					<jsp:include page="../component/utilityboxbottom.jsp" />
				</td>
			</tr>
		<jsp:include  page='../component/titleend.jsp' />
	</body>
<%		}
	}
%>  			
</html:html>

