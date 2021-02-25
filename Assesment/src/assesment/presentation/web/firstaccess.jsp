<%@page language="java" 
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

<html>
<%! RequestDispatcher dispatcher; String pathMsg;
	String pathLanguage;
	String uid;
	AssesmentAccess sys;
%>
<%
	sys=(AssesmentAccess)session.getAttribute("AssesmentAccess");
	String check = Util.checkPermission(sys,"firstaccess");
	if(check!=null) {
		response.sendRedirect(request.getContextPath()+check);
	}else {
		pathLanguage="/util/jsp/language.jsp";
	
		uid=sys.getUserSessionData().getFilter().getLoginName();

		dispatcher=request.getRequestDispatcher(pathLanguage);
		dispatcher.include(request,response);
		pathMsg="/util/jsp/message.jsp";
	
		dispatcher=request.getRequestDispatcher(pathMsg);
		dispatcher.include(request,response);
	
		if(request.getParameter("target")!=null){
			session.setAttribute("target",request.getParameter("target"));
		}
		
		if(session.getAttribute("target")==null){
			session.setAttribute("target",request.getParameter("target"));
		}
				
		if(uid!=null){
			session.setAttribute("uid",uid); 
%>

	<LINK REL=StyleSheet HREF="./util/css/estilo.css" TYPE="text/css">

	<head/>
	<body>
	    <form  action="./updatePassword.jsp" >
			<table width="450" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr class="guide1">
					<jsp:include  page="./component/titlecomponent.jsp?title=First Access, Insert New Password" />
	  			</tr>
	      		<tr>
					<jsp:include  page="./component/spaceline.jsp" />
	  			</tr>
    			<tr>
					<td colspan="4">
						<table width="100%" border="1" bordercolor="black" >
							<tr>
								<td>
									<table width="100%" align='center' cellpadding="0" cellspacing="0" bgcolor="#F7F7F7">
										<tr>
											<th colspan="2" align="left" class="input"><%="Following recommendations resulting from a J&J Risk Assessment, password policies have been aligned with J&J's Worldwide Policies on Information Asset Protection (WWIAPs)."%></th>
										</tr>
						    			<tr>
											<th colspan="2" align="left" class="input">&nbsp;</th>
										</tr>
						    			<tr>
											<th colspan="2" align="left" class="input"><%="This policies state that passwords should:"%></th>
										</tr>
						    			<tr>
											<th width="3%" class="lineone">-</th>
											<td align="left" class="input"><%="have 3 levels of complexity (English Upper Case Letters, English Lower Case Letters, Westernized Arabic Numerals, and/or Non-alphanumeric (@#$%^&+=))"%></td>
										</tr>
						    			<tr>
											<th width="3%" class="lineone">-</th>
											<td align="left" class="input"><%="have a minimum of 6 characters"%></td>
										</tr>
						    			<tr>
											<th width="3%" class="lineone">-</th>
											<td align="left" class="input"><%="expire every 90 days at a minimum"%></td>
										</tr>
						    			<tr>
											<th width="3%" class="lineone">-</th>
											<td align="left" class="input"><%="expire upon first logon (Initial passwords)"%></td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
	      		<tr>
					<jsp:include  page="./component/spaceline.jsp" />
	  			</tr>
	  			<tr>
	    			<td width="100%" valign="top">
						<jsp:include  page="./component/utilitybox2top.jsp?title=Reset Password" />
    					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="default">
		                 	<tr class="lineone">
   								<th align="right">
   									<div align="left">New Password </div>
   								</th>
   			  	        		<td align="left" class="lineone">
     					     		<input type="password" name="password"	size="25" class="input"/>
   		 		        		</td>
	    					</tr>
					      	<tr  class="lineone">
					        	<th align="right">&nbsp;</th>
					        	<td align="right">&nbsp;</td>
					      	</tr>
	    					<tr>
	 				       		<th align="right" class="lineone"><div align="left">Confirm New Password</div></th>
					        	<td align="left" class="lineone">
					             	<input type="password" name="rePassword" size="25" class="input"/>
				            	</td>
	    					</tr>
					      	<tr  class="lineone">
					        	<th align="right"></th>
					        	<td align="right"></td>
					      	</tr>
					     	<tr>
					       		<td colspan="2" class="lineone"> 
					       			<div align="right">
								     	<input type="submit" class="input" value="Save" />
							 		</div>
								</td>
			          		</tr>
	    				</table>
						<jsp:include  page="./component/utilitybox2bottom.jsp" />
			 		</td>
                </tr>
			</table>
		</form>
	</body>
<%
		}
	}
%>
</html>
