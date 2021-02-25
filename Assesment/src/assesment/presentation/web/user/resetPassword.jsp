<%@page language="java" 
	errorPage="../exception.jsp"
	import="assesment.business.*"
	import="assesment.business.administration.user.*"
	import="assesment.communication.language.*"
	import="assesment.communication.administration.user.tables.*"
	import="assesment.communication.security.*"
	import="assesment.presentation.translator.web.user.*"
	import="assesment.presentation.translator.web.util.*"
	import="java.util.*"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>

<html:html>
<%! Text messages; 
	String user;
	AssesmentAccess sys;
%>
<%
	sys=(AssesmentAccess)session.getAttribute("AssesmentAccess");
	String check = Util.checkPermission(sys,SecurityConstants.ADMINISTRATOR);
	if(check!=null) {
		response.sendRedirect(request.getContextPath()+check);
	}else {
	    user = request.getParameter("user");
		if(user == null){
		    user = (String)session.getAttribute("user");
		}
		session.setAttribute("url","user/resetPassword.jsp?user="+user);

		RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
		dispatcher.include(request,response);
	
		messages=sys.getText();
		
		if(user != null){
%>

	<LINK REL=StyleSheet HREF="../util/css/estilo.css" TYPE="text/css">

	<head/>
	<body>
	    <html:form  action="/UserResetPassword" >
	    	<html:hidden property="loginname" value='<%=user%>' />
			<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("user.resetpassword.foruser")+" "+user%>' />
	  			<tr>
	    			<td width="100%" valign="top">
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("user.resetpassword.newdata")%>' />
    					<table width="100%" border="0" align="center" cellpadding="2" cellspacing="2">
		                 	<tr class="line">
   								<td align="right">
   									<div align="left"><%=messages.getText("user.data.newpass")%> </div>
   								</td>
   			  	        		<td align="right">
     					     		<html:password property="password" style="width:300;" styleClass="input"/>
   		 		        		</td>
	    					</tr>
	    					<tr  class="line">
	 				       		<td align="right">
	 				       			<div align="left">
	 				       				<%=messages.getText("user.data.confirmnewpassword")%>
	 				       			</div>
	 				       		</td>
					        	<td align="right" >
					             	<html:password property="rePassword" style="width:300;" styleClass="input"/>
				            	</td>
	    					</tr>
	    				</table>
						<jsp:include  page="../component/utilitybox2bottom.jsp" />
			 		</td>
                </tr>
		     	<tr>
		       		<td class="line"> 
		       			<div align="right">
					     	<html:submit styleClass="input" >
						         <%=messages.getText("generic.messages.save")%>
				         	</html:submit>
			     			<html:cancel styleClass="input" >
					        	<%=messages.getText("generic.messages.cancel")%>
		         			</html:cancel>
				 		</div>
					</td>
          		</tr>
			<jsp:include  page='../component/titleend.jsp' />
		</html:form>
	</body>
<%
		}
	}
%>
</html:html>
