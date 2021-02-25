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
	
		session.setAttribute("url","user/view.jsp?user="+user);

		if(!Util.empty(user)){
			messages=sys.getText();
			CountryConstants cc = new CountryConstants(messages);
			Collection assesments = sys.getUserReportFacade().findUserAssesments(user,sys.getUserSessionData());
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

<body bgcolor="#FFFFFF">
	
	<html:form action="/UserDelete" >
		<html:hidden property="users"  value='<%=user%>'/>		
	</html:form>
	<html:form action="/CreateELearning" >
		<html:hidden property="user"  value='<%=user%>'/>		
	</html:form>
	<form action="layout.jsp?refer=/user/updateusercode.jsp" name='edit' method="post">
		<input type="hidden" name="user" value='<%=user%>' />
		<input type="hidden" name="type" value="edit" />
	</form>	
	<body>
		<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("generic.user.data")+" "+user%>' />
	  		<tr>			  		
				<td colspan="3" align="left">
					<table >
						<tr>
							<td align="left"  class="texts">
								<a href="javascript:document.forms['edit'].submit();">
									<input type="button" class="button" value='<%=messages.getText("generic.messages.edit")%>' >
								</a>
							</td>
							  <td align="left" class="texts">
         						<a href='<%="layout.jsp?refer=/user/codeuser.jsp"%>'> 
           							<input type="button" class="button" value='<%=messages.getText("generic.messages.cancel")%>' > 
           						</a> 
							</td>
						</tr>
					</table>
				</td>			</tr>
  			<tr>
    			<td width="100%" valign="top" colspan="3">
					<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.user.data")%>' />
						<table width='100%' border="0" align='left' cellpadding="0" cellspacing="0">
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
			 				<tr class="line">
								<td align="left"><%=messages.getText("user.data.country")%></td>
<%							if(data.getCountry() == null) {
%>								<td align="right">&nbsp;</td>
<%							}else {
%>			    			  	<td align="right"><%=cc.find(String.valueOf(data.getCountry()))%></td>
<%							}
%>			    			 </tr>


							<tr class="line">
								<td align="left">Codigo WEBINAR</td>
			    			  	<td align="right"><%=(data.getExtraData3() == null) ? "" : data.getExtraData3()%></td>
			    			 </tr>
					</table>
					<jsp:include  page="../component/utilitybox2bottom.jsp" />
				</td>

   		<tr>			  		
				<td colspan="3" align="left">
					<table >
						<tr>
							<td align="left"  class="texts">
								<a href="javascript:document.forms['edit'].submit();">
									<input type="button" class="button" value='<%=messages.getText("generic.messages.edit")%>' >
								</a>
							</td>
							  <td align="left" class="texts">
         						<a href='<%="layout.jsp?refer=/user/codeuser.jsp"%>'> 
           							<input type="button" class="button" value='<%=messages.getText("generic.messages.cancel")%>' > 
           						</a> 
							</td>
						</tr>
					</table>
				</td>			</tr>			</tr>
		<jsp:include  page='../component/titleend.jsp' />
	</body>
<% 	}else{
			dispatcher=request.getRequestDispatcher("layout.jsp?refer=/user/viewusercode.jsp");
			dispatcher.forward(request,response);
		}	
	}
%>
</html:html>

