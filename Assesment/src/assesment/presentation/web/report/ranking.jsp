<%@page language="java" 
	errorPage="../exception.jsp"
	import="java.util.*"
	import="assesment.business.*"
	import="assesment.communication.language.*"
	import="assesment.communication.security.*"
	import="assesment.communication.corporation.*"
	import="assesment.communication.util.*"
	import="assesment.presentation.translator.web.util.*"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>

<html xmlns="http://www.w3.org/1999/xhtml">
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
		session.setAttribute("url","report/users.jsp");
	
		if(session.getAttribute("Msg")!=null){
			session.removeAttribute("Msg");
		}
		messages=sys.getText();
	
		ListResult result = sys.getAssesmentReportFacade().findAssesments("","",sys.getUserSessionData());
		Iterator it = result.getElements().iterator();
		Collection assesments = new LinkedList();
		assesments.add(new OptionItem(messages.getText("generic.messages.select"),""));
		while(it.hasNext()) {
	        Object[] data = (Object[])it.next();
		    assesments.add(new OptionItem((String)data[1],String.valueOf(data[0])));
		}
		session.setAttribute("assesments",assesments);

		Collection format = new LinkedList();
		format.add(new OptionItem("HTML","HTML"));	    
		format.add(new OptionItem("PDF","PDF"));	    
		session.setAttribute("format",format);
%>
	<LINK REL=StyleSheet HREF="../util/css/estilo.css" TYPE="text/css">
		
<head/>

<body>
	<table width="500" border="0" align="center" cellpadding="0" cellspacing="0">
		<tr class="guide1">
			<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("generic.data.results")%>' />
  		</tr>
      	<tr>
			<jsp:include  page="../component/spaceline.jsp" />
  		</tr>
  		<tr>
    		<td width="500" valign="top">
				<jsp:include  page='<%="../component/utilityboxtop.jsp?title="+messages.getText("generic.report.parameters")%>' />
				<html:form action="/QuestionRanking">
		    		<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<th align="left">
								<span class="input"><%=messages.getText("generic.assesment")%></span>
							</th>
							<td align="right">
      							<html:select property="assesment" styleClass="input">
	      							<html:options collection="assesments" property="value" labelProperty="label" styleClass="input"/>
		  						</html:select>   
							</td>
						</tr>
   						<tr  class="space"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
						<tr>
							<th align="left">
								<span class="input"><%=messages.getText("generic.report.output")%></span>
							</th>
							<td align="right">
								<html:select property="output" styleClass="input">
									<html:options collection="format" property="value" labelProperty="label"/>
								</html:select>					
							</td>
						</tr>
   						<tr  class="space"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
			    		<tr>
	                		<td colspan="5" class="linetwo" align="right">
			                	<html:submit value='<%=messages.getText("generic.report.open")%>' styleClass="input"/>
							</td>
              			</tr>
					</table>
				</html:form>
				<jsp:include  page="../component/utilityboxbottom.jsp" />
			</td>
    </table>
</body>
<%
	}
%>
</html>
