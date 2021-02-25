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
		Collection elements = sys.getUserReportFacade().findListWrongCode(parameters,sys.getUserSessionData());

		String link = "/user/list.jsp";

%>

	<script language="javascript" src='../util/js/Prepared_Parameters.js' type='text/javascript' ></script>
	
	<script>

	</script>

	<LINK REL=StyleSheet HREF="../util/css/estilo.css" TYPE="text/css">
		
	<head/>

	<body>
		  <form action="updateusercode.jsp">
		  	<table  width="100%" align="center">	
		  		<tr>
		    		<td colspan="3">
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.systemusers")%>' />
		    			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		      				<tr>
				               <!--  <td class="guide2" width="2%">&nbsp;</td>-->
				                <td class="guide2" width="20%" align="left"><%=messages.getText("user.data.firstname")%></td>
				                <td class="guide2" width="20%" align="left"><%=messages.getText("user.data.lastname")%></td>
				                <td class="guide2" width="20%" align="left"><%=messages.getText("user.data.mail")%></td>
				                <td class="guide2" width="12%" align="center">codigo webinar</td>
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
				      		 <!--	<td align="center">
									<input type='checkbox' name='<%=user.getLoginName()%>' value='<%=user.getLoginName()%>' >                  
								</td>-->
    	              			<td align="left"><a href='layout.jsp?refer=/user/viewusercode.jsp?user=<%=user.getLoginName()%>' ><%=user.getFirstName()%></a></td>
        	          			<td align="left"><a href='layout.jsp?refer=/user/viewusercode.jsp?user=<%=user.getLoginName()%>'><%=user.getLastName()%></a></td>
            	      			<td align="left"><%=user.getEmail()%></td>
            	      			<td align="left"><%=user.getExtraData3()%></td>
								
                			</tr>
<%			}
		}
%>						</table>
						<jsp:include page="../component/utilitybox2bottom.jsp" />
					</td>
				</tr>	
<%		if(elements.size() > 0){
%>			  <!-- 	<tr>
            		<td class="line"align="right" colspan="7">
                      	<input name="button2" type="submit" value=<%=messages.getText("generic.messages.edit")%> class="input"/>
					</td>
              	</tr> -->
             </table>
          </form>
<%		}
%>      
		<jsp:include  page='../component/titleend.jsp' />
	</body>
<%
	}
%>
