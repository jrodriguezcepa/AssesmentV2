<%@page language="java" 
	errorPage="../exception.jsp"
	import="java.util.*"
	import="assesment.business.*"
	import="assesment.communication.language.*"
	import="assesment.communication.security.*"
	import="assesment.communication.administration.*"
	import="assesment.communication.util.*"
	import="assesment.presentation.translator.web.util.*"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>

<%!
	Text messages;	AssesmentAccess sys;
%>
<%
	sys=(AssesmentAccess)session.getAttribute("AssesmentAccess");
	String check = Util.checkPermission(sys,SecurityConstants.ADMINISTRATOR);
	
	RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
	dispatcher.include(request,response);

	if(check!=null) {
		response.sendRedirect(request.getContextPath()+check);
	}else {
		session.setAttribute("url","report/accesscodelist.jsp");
		messages=sys.getText();
		Collection list = sys.getAssesmentReportFacade().getAccessCodes(sys.getUserSessionData(), messages);
		Collections.sort((List)list);
%>
	<head>
		<LINK REL=StyleSheet HREF="../util/css/estilo.css" TYPE="text/css">
	<head/>
	<body>
		<html:form action="/DeleteAccessCode">
			<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("user.accesscode.list")%>' />
		  		<tr>
		    		<td width="100%" valign="top">
		       			<a href="layout.jsp?refer=/user/createaccesscode.jsp?type=create"> 
		       				<input type="button" class="button" value='<%=messages.getText("generic.messages.createaccesscode")%>'> 
						</a>
					</td>
			  	</tr>
		  		<tr>
    				<td width="100%" valign="top">
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("user.accesscodes")%>' />
							<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
								<tr>
									<td class="guide2" width="5%">&nbsp;</td>
				                	<td class="guide2" width="25%" align="left"><%=messages.getText("user.accesscode.code")%></td>
               						<td class="guide2" width="25%" align="left"><%=messages.getText("generic.assesment")%></td>
               						<td class="guide2" width="25%" align="center"><%=messages.getText("user.accesscode.number")%></td>
               						<td class="guide2" width="25%" align="center"><%=messages.getText("user.accesscode.used")%></td>
								</tr>
<%		boolean linetwo = true;
		if(list.size() == 0) {
%>								<tr align="left" class="linetwo">
									<td colspan="5"><%=messages.getText("generic.messages.notresult") %></td>
								</tr>
							</table>
						<jsp:include  page="../component/utilityboxbottom.jsp" />
					</td>
				</tr>
<%		}else {
			Iterator codes = list.iterator();
			while(codes.hasNext()){
			    AccessCodeData data = (AccessCodeData)codes.next();
				linetwo = !linetwo;	
%>	 		            		<tr align=center class='<%=(linetwo)?"linetwo":"lineone"%>'>
				              		<td width="5%" align="center">
										<input type="checkbox" name='<%=data.getCode()%>' />                  
									</td>
									<td width="25%" align="left">
										<a href='<%="layout.jsp?refer=/user/createaccesscode.jsp?type=edit&code="+data.getCode()%>' >
											<%=data.getCode()%>
										</a>
									</td>
			  			            <td width="25%" align="left"><%=data.getAssesmentName()%></td>
  						            <td width="25%" align="center"><%=String.valueOf(data.getNumber())%></td>
  				    		        <td width="25%" align="center"><%=String.valueOf(data.getUsed())%></td>
				                </tr>
<%			}
%>					
							</table>
						<jsp:include  page="../component/utilityboxbottom.jsp" />
					</td>
				</tr>
				<tr align="right" class="linetwo">
					<td colspan="5">
						<html:submit value='<%=messages.getText("generic.messages.delete")%>' styleClass="input" />
					</td>
				</tr>
<%		}		
%>	
			<jsp:include  page='../component/titleend.jsp' />
	    </html:form>
	</body>
<%
	}
%>
