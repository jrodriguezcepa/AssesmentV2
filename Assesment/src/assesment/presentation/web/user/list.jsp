<%@page import="assesment.communication.assesment.AssesmentListData"%>
<%@page language="java" 
	errorPage="../exception.jsp"
	import="java.util.*"
	import="assesment.business.*"
	import="assesment.business.administration.user.*"
	import="assesment.communication.language.*"
	import="assesment.communication.security.*"
	import="assesment.communication.util.*"
	import="assesment.communication.user.*"
	import="assesment.presentation.translator.web.util.*"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>


<%@page import="assesment.communication.assesment.AssesmentAttributes"%><html xmlns="http://www.w3.org/1999/xhtml">
<%!
	RequestDispatcher dispatcher;
	Integer pageNum; int pageSize; boolean all; 
	Text messages;
	AssesmentAccess sys; Collection col; 
	String attribute; String value;
%>
<%
	sys=(AssesmentAccess)session.getAttribute("AssesmentAccess");
	String check = Util.checkPermission(sys,SecurityConstants.ADMINISTRATOR);
	if(check!=null) {
		response.sendRedirect(request.getContextPath()+check);
	}else {
		session.setAttribute("url","user/list.jsp");
	
		dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
		dispatcher.include(request,response);
		messages=sys.getText();

		HashMap<String,String> parameters = new HashMap<String,String>();
		String login = "";
		if(request.getParameter("login")!=null){
			login = request.getParameter("login");
			parameters.put("login",login);
		}		
		String firstName = "";
		if(request.getParameter("firstName")!=null && request.getParameter("firstName").trim().length()>0){
			firstName = request.getParameter("firstName");
			parameters.put("firstName",firstName);
		}		
		String lastName = "";
		if(request.getParameter("lastName")!=null && request.getParameter("lastName").trim().length()>0){
			lastName = request.getParameter("lastName");
			parameters.put("lastName",lastName);
		}		
		String email = "";
		if(request.getParameter("email")!=null && request.getParameter("email").trim().length()>0){
			email = request.getParameter("email");
			parameters.put("email",email);
		}		
		String role = "";
		if(request.getParameter("role")!=null){
			role = request.getParameter("role");
		}		
		parameters.put("role",role);
		String archived = "0";
		if(request.getParameter("archived")!=null){
			archived = request.getParameter("archived");
		}		
		parameters.put("archived",archived);
		String assessment = "";
		if(request.getParameter("assessment")!=null && request.getParameter("assessment").trim().length()>0){
			assessment = request.getParameter("assessment");
			parameters.put("assessment",assessment);
		}		
		
		Collection[] assessmentList = sys.getAssesmentReportFacade().getAssessments(sys.getUserSessionData());
		Collection elements = sys.getUserReportFacade().findList(parameters,sys.getUserSessionData());

		String link = "/user/list.jsp";

%>

	<script language="javascript" src='../util/js/Prepared_Parameters.js' type='text/javascript' ></script>
	
	<script>
		
	function doSubmit() {
		document.forms['search'].submit();
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
		form.users.value=valueCheckboxParamList;
		if(valueCheckboxParamList.length>0){
			if(confirm(msg)){
				form.submit();
			}
		}
	}
	function cambio(){
		var parentCombo = document.forms['search'].elements['archived'+document.forms['search'].archived.selectedIndex];
		var childCombo = document.forms['search'].elements['assessment'];
		childCombo.options.length=parentCombo.options.length;
		for(i=0; i < parentCombo.length; i++) {
			childCombo.options[i].value = parentCombo.options[i].value;
			childCombo.options[i].text = parentCombo.options[i].text;
		}
	}
	</script>

	<LINK REL=StyleSheet HREF="../util/css/estilo.css" TYPE="text/css">
		
	<head/>

	<body>
		<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("generic.systemusers")%>' />
			<form name="search" action="layout.jsp?refer=/user/list.jsp" method="post">
  				<tr>
    				<td width="100%" valign="top">
<%		for(int i = 0; i < 2; i++) {
%>						<select name='<%="archived"+String.valueOf(i)%>' style="display:none; ">
<%								Iterator it = assessmentList[i].iterator();
								while(it.hasNext()) {
									AssesmentAttributes assessmentAtr = (AssesmentAttributes)it.next();
%>							<option value='<%=String.valueOf(assessmentAtr.getId())%>'><%=assessmentAtr.getName()%></option>
<%								}
%>						</select>
<%		}
%>		
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.user.data")%>' />
				    		<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td  class="line" align="left" valign="top">
										<%=messages.getText("user.data.nickname")%>
									</td>
									<td align="right" class="line">
										<input type="text" name="login" style="width: 300px;" class="input" value='<%=login%>'/>
									</td>
								</tr>
								<tr>
									<td  class="line" align="left" valign="top">
										<%=messages.getText("user.data.firstname")%>
									</td>
									<td align="right" class="line">
										<input type="text" name="firstName" style="width: 300px;" class="input" value='<%=firstName%>'/>
									</td>
								</tr>
								<tr>
									<td  class="line" align="left" valign="top">
										<%=messages.getText("user.data.lastname")%>
									</td>
									<td align="right" class="line">
										<input type="text" name="lastName" style="width: 300px;" class="input" value='<%=lastName%>'/>
									</td>
								</tr>
								<tr>
									<td  class="line" align="left" valign="top">
										<%=messages.getText("user.data.mail")%>
									</td>
									<td align="right" class="line">
										<input type="text" name="email" style="width: 300px;" class="input" value='<%=email%>'/>
									</td>
								</tr>
								<tr>
									<td  class="line" align="left" valign="top">
										<%=messages.getText("user.data.role")%>
									</td>
									<td align="right" class="line">
										<select name="role" style="width: 300px;" class="input">
											<option value='' <%=(role.length()==0)?"selected":""%>><%=messages.getText("generic.messages.all")%></option>
											<option value='<%=SecurityConstants.ACCESS_TO_SYSTEM%>' <%=(role.equals(SecurityConstants.ACCESS_TO_SYSTEM))?"selected":""%>><%=messages.getText("role."+SecurityConstants.ACCESS_TO_SYSTEM+".name")%></option>
											<option value='<%=SecurityConstants.MULTI_ASSESSMENT%>' <%=(role.equals(SecurityConstants.MULTI_ASSESSMENT))?"selected":""%>><%=messages.getText("role."+SecurityConstants.MULTI_ASSESSMENT+".name")%></option>
											<option value='<%=SecurityConstants.GROUP_ASSESSMENT%>' <%=(role.equals(SecurityConstants.GROUP_ASSESSMENT))?"selected":""%>><%=messages.getText("role."+SecurityConstants.GROUP_ASSESSMENT+".name")%></option>
											<option value='<%=SecurityConstants.CLIENT_REPORTER%>' <%=(role.equals(SecurityConstants.CLIENT_REPORTER))?"selected":""%>><%=messages.getText("role."+SecurityConstants.CLIENT_REPORTER+".name")%></option>
											<option value='<%=SecurityConstants.CEPA_REPORTER%>' <%=(role.equals(SecurityConstants.CEPA_REPORTER))?"selected":""%>><%=messages.getText("role."+SecurityConstants.CEPA_REPORTER+".name")%></option>
											<option value='<%=SecurityConstants.CLIENTGROUP_REPORTER%>' <%=(role.equals(SecurityConstants.CLIENTGROUP_REPORTER))?"selected":""%>><%=messages.getText("role."+SecurityConstants.CLIENTGROUP_REPORTER+".name")%></option>
											<option value='<%=SecurityConstants.ADMINISTRATOR%>' <%=(role.equals(SecurityConstants.ADMINISTRATOR))?"selected":""%>><%=messages.getText("role."+SecurityConstants.ADMINISTRATOR+".name")%></option>
										</select>
									</td>
								</tr>
							</table>
						<jsp:include  page="../component/utilitybox2bottom.jsp" />
					</td>
				</tr>
				<tr>	
					<td>	
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.assesment.data")%>' />
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td  class="line" align="left" valign="top">
										<%=messages.getText("assesment.data.archived")%>
									</td>
									<td align="right" class="line">
										<select name="archived" style="width: 300px;" class="input" onchange="cambio();">
											<option value="0" <%=(archived.equals("0"))?"selected":""%>><%=messages.getText("generic.messages.no") %></option>
											<option value="1" <%=(archived.equals("1"))?"selected":""%>><%=messages.getText("generic.messages.yes") %></option>
										</select>
									</td>
								</tr>
								<tr>
									<td  class="line" align="left" valign="top">
										<%=messages.getText("generic.assesment")%>
									</td>
									<td align="right" class="line">
										<select name="assessment" style="width: 300px;" class="input">
											<option value=""></option>
<%								Iterator<AssesmentListData> it = Util.getAssessmentAttrIterator(assessmentList[Integer.parseInt(archived)], messages);
								while(it.hasNext()) {
									AssesmentListData assessmentAtr = it.next();
%>											<option value='<%=String.valueOf(assessmentAtr.getId())%>'  <%=(assessment.equals(String.valueOf(assessmentAtr.getId())))?"selected":""%>><%=assessmentAtr.getName()%></option>
<%								}
%>										</select>
									</td>
								</tr>
							</table>
						<jsp:include  page="../component/utilitybox2bottom.jsp" />
					</td>
				</tr>
				<tr>
					<td colspan="3" align="right">
						<input name="button" type="submit" value='<%=messages.getText("generic.messages.search")%>' class="input"/>
					</td>
				</tr>
		  		<tr>
		    		<td width="100%" valign="top">
		       			<a href="layout.jsp?refer=/user/create.jsp?type=create"> 
		       				<input type="button" class="button" value='<%=messages.getText("generic.user.new")%>'> 
						</a>
					</td>
			  	</tr>
			</form>
       		<html:form action="/UserDelete"  >
       			<html:hidden property="users" />
		  		<tr>
		    		<td colspan="3">
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.systemusers")%>' />
		    			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		      				<tr>
				                <td class="guide2" width="2%">&nbsp;</td>
				                <td class="guide2" width="12%" align="left"><%=messages.getText("user.data.nickname")%></td>
				                <td class="guide2" width="20%" align="left"><%=messages.getText("user.data.firstname")%></td>
				                <td class="guide2" width="20%" align="left"><%=messages.getText("user.data.lastname")%></td>
				                <td class="guide2" width="20%" align="left"><%=messages.getText("user.data.mail")%></td>
				                <td class="guide2" width="12%" align="center"><%=messages.getText("user.data.language")%></td>
				                <td class="guide2" width="16%" align="center"><%=messages.getText("user.data.role")%></td>
		              		</tr>
<%		boolean linetwo = true;
		if(elements.size() == 0){
%>					  		<tr>
        		      			<td colspan="7" class="linetwo"><%=messages.getText("generic.messages.notresult")%></td>
              				</tr>
<%		}else {
			Iterator userIt = elements.iterator();
			while(userIt.hasNext()){
			    UserData user = (UserData)userIt.next();
				linetwo = !linetwo;	
%> 		            		<tr align=center class='<%=(linetwo)?"linetwo":"lineone"%>'>
				      			<td align="center">
									<input type='checkbox' name='<%=user.getLoginName()%>' value='<%=user.getLoginName()%>' >                  
								</td>
	              				<td width="25%" align="left">
		              				<a href='layout.jsp?refer=/user/view.jsp?user=<%=user.getLoginName()%>' >
	                  					<%=(user.getLoginName().length() > 20) ? user.getLoginName().substring(0,20)+"..." : user.getLoginName()%>
	                  				</a>
								</td>
    	              			<td align="left"><%=user.getFirstName()%></td>
        	          			<td align="left"><%=user.getLastName()%></td>
            	      			<td align="left"><%=user.getEmail()%></td>
                	  			<td align="center"><%=messages.getText(user.getLanguage())%></td>
                  				<td align="center"><%=messages.getText("role."+user.getRole()+".name")%></td>
                			</tr>
<%			}
		}
%>						</table>
						<jsp:include page="../component/utilitybox2bottom.jsp" />
					</td>
				</tr>	
<%		if(elements.size() > 0){
%>			  	<tr>
            		<td class="line"align="right" colspan="7">
                      	<input name="button2" type="button" onClick="javascript:deleteIFConfirm(document.forms['UserDeleteForm'],'<%=messages.getText("user.delete.confirm")%>');" value=<%=messages.getText("generic.messages.delete")%> class="input"/>
					</td>
              	</tr>
<%		}
%>         	</html:form>
		<jsp:include  page='../component/titleend.jsp' />
	</body>
<%
	}
%>
