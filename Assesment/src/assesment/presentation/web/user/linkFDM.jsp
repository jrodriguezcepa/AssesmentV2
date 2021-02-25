<%@page language="java"
	errorPage="../exception.jsp"
	import="assesment.business.*"
	import="assesment.business.administration.user.*" 
	import="assesment.business.administration.corporation.*" 
	import="assesment.communication.language.*"
	import="assesment.communication.security.*"
	import="assesment.communication.assesment.*"
	import="assesment.communication.administration.group.tables.*"
	import="assesment.presentation.translator.web.user.*"
	import="assesment.presentation.translator.web.util.*"
	import="assesment.communication.user.*"
	import="assesment.communication.util.*"
	import="java.util.*"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>

<html:html>
<%! Text messages;
	AssesmentAccess sys;
%>
<%
	sys=(AssesmentAccess)session.getAttribute("AssesmentAccess");
	messages = sys.getText();
	String check = Util.checkPermission(sys,SecurityConstants.ADMINISTRATOR);
	if(check!=null) {
		response.sendRedirect(request.getContextPath()+check);
	}else {

		RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
		dispatcher.include(request,response);

	    String user = request.getParameter("user");
		if(Util.empty(user)){
			user = (String)session.getAttribute("user");
			session.removeAttribute("user");
		}
		UserData data = sys.getUserReportFacade().findUserByPrimaryKey(user,sys.getUserSessionData());
	
		session.setAttribute("url","user/linkFDM.jsp?user="+user);
		if(data != null && (data.getRole().equals(UserData.SYSTEMACCESS) || data.getRole().equals(UserData.MULTIASSESSMENT))) {
			String fdm = (data.getDatacenter() == null) ? "" : String.valueOf(data.getDatacenter());

%>
<head>
	<LINK REL=StyleSheet HREF="./util/css/estilo.css" TYPE="text/css">
	<script>
		function doSubmitConfirm(form,id,name,msg) {
			form.elements[name].value=id;
			if(confirm(msg)){
				form.submit();
			}
		}
		function confirmElearning(form,msg) {
			if(confirm(msg)){
				form.submit();
			}
		}
		function doSubmit(form,id,name) {
			form.elements[name].value=id;
			form.submit();
		}
		function addAllCountry(form) {
			form.submit();
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
			form.assesments.value=valueCheckboxParamList;
			if(valueCheckboxParamList.length>0){
				if(confirm(msg)){
					form.submit();
				}
			}
		}
	</script>

	<style type="text/css">
	</style>
<head/>

	<body>
		<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("generic.messages.associatefdm")+" "+user%>' />
  			<tr>
    			<td width="100%" valign="top" colspan="3">
					<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.user.data")%>' />
					<table width="100%">
						<tr>
            				<td valign="middle">
								<table width='100%' border="0" align='left' cellpadding="0" cellspacing="0">
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
										<td align="left"><%=messages.getText("user.data.lastname")%></td>
					    			  	<td align="right"><%=data.getLastName()%></td>
									</tr>
									<tr class="line">
									    <td align="left"><%=messages.getText("user.data.mail")%></td>
										<td align="right"><%=data.getEmail()%></td>
					 				</tr>
								</table>
							</td> 
						</tr>
					</table>
					<jsp:include  page="../component/utilitybox2bottom.jsp" />
				</td>
          	</tr>
  			<tr>
    			<td width="100%" valign="top" colspan="3">
					<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("user.data.fdm")%>' />
					<html:form action="/UserAssesmentFDM">
						<html:hidden property="user" value='<%=user%>'/>
						<table width="100%">
			      			<tr class="line" align="left">
				            	<td>
				            		<%=messages.getText("user.data.fdmid")%>
				            	</td>
        						<td align="right">
									<html:text property="fdm" styleClass="input" value="<%=fdm%>" style="width:100px;"/>		                		
								</td>
			  				</tr>
			      			<tr class="line" align="right">
				            	<td colspan="2" align="right">
						            <html:submit styleClass="input">
										<%=messages.getText("generic.messages.save")%>
									</html:submit>
						            <html:cancel styleClass="input">
										<%=messages.getText("generic.messages.cancel")%>
									</html:cancel>
								</td>
							</tr>
	            		</table>
					</html:form>
					<jsp:include  page="../component/utilitybox2bottom.jsp" />
				</td>
          	</tr>
        </table>
	</body>
<%		}
	}
%>
</html:html>

