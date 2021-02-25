<%@page language="java" 
	errorPage="../exception.jsp"
	import="java.util.*"
	import="assesment.business.*"
	import="assesment.communication.language.*"
	import="assesment.communication.security.*"
	import="assesment.communication.corporation.*"
	import="assesment.communication.util.*"
	import="assesment.presentation.translator.web.util.*"
	import="assesment.communication.assesment.*"
	import="assesment.communication.module.*"
	import="assesment.communication.question.*"
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
		session.setAttribute("url","report/questionresults.jsp");
	
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
		
		String assesment = "";
		if(request.getParameter("assesment")!=null){
		    assesment = request.getParameter("assesment");
		}		
		
		AssesmentData assesmentData = null;
		if(!Util.empty(assesment)) {
		    assesmentData = sys.getAssesmentReportFacade().findAssesment(new Integer(assesment),sys.getUserSessionData());
		}

%>
	<LINK REL=StyleSheet HREF="../util/css/estilo.css" TYPE="text/css">
		
<head/>

<script type="text/javascript">
	function searchQuestion(form1,form2) {
		form2.elements['assesment'].value = form1.elements['assesment'].value;
		if(form2.elements['assesment'] != '') {
			form2.submit();
		}else{
			alert("Debe seleccionar un assesment");
		}
	}
</script>
<form name="search" action="layout.jsp?refer=/report/questionresults.jsp" method="post">
	<input type="hidden" name="assesment">
</form>

<body>
	<table width="600" border="0" align="center" cellpadding="0" cellspacing="0">
		<tr class="guide1">
			<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("generic.data.questionresults")%>' />
  		</tr>
      	<tr>
			<jsp:include  page="../component/spaceline.jsp" />
  		</tr>
  		<tr>
    		<td width="700" valign="top">
				<jsp:include  page='<%="../component/utilityboxtop.jsp?title="+messages.getText("generic.report.parameters")%>' />
				<html:form action="/QuestionResults">
		    		<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<th align="left">
								<span class="input"><%=messages.getText("generic.assesment")%></span>
							</th>
							<td align="right">
      							<html:select property="assesment" value='<%=assesment%>' styleClass="input">
	      							<html:options collection="assesments" property="value" labelProperty="label" styleClass="input"/>
		  						</html:select>   
								<input name="button" type="button" onClick="javascript:searchQuestion(document.forms['QuestionResultReportForm'],document.forms['search']);" value='<%=messages.getText("generic.messages.search")%>' class="input"/>
							</td>
						</tr>
   						<tr  class="space"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
						<tr>
							<td colspan="2">
								<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
							  		<tr>
							    		<td valign="top">
											<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.questions.search")%>' />
					    						<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
					    			  				<tr>
										                <td class="guide2" width="5%">&nbsp;</td>
							                			<td class="guide2" width="25%" align="left"><%=messages.getText("question.data.text")%></td>
					              					</tr>
<%		if(assesmentData != null) {
			Iterator moduleIt = assesmentData.getModuleIterator();
			boolean lineone = true;
			while(moduleIt.hasNext()){
			    ModuleData module = (ModuleData)moduleIt.next();
		    	Iterator questionIt = module.getQuestionIterator();
			    while(questionIt.hasNext()) {
			        QuestionData question = (QuestionData)questionIt.next();
			        if(question.getType().intValue() == QuestionData.EXCLUDED_OPTIONS || question.getType().intValue() == QuestionData.INCLUDED_OPTIONS || question.getType().intValue() == QuestionData.LIST) {
%>					    			        		<tr align=center class='<%=(lineone) ? "lineone" : "linetwo"%>'>
       													<td width="5%" align="center">
															<html:radio property="question" value='<%=String.valueOf(question.getId())%>' ></html:radio>                  
														</td>
					              						<td width="95%" align="left"><%=messages.getText(question.getKey())%></td>
							                		</tr>
<%						lineone = !lineone;
					}
				}
			}			 
		}else {		
%>					    			        		<tr align=center class="lineone">
       													<td colspan="3">
															<%=messages.getText("generic.messages.notresult")%>                  
														</td>
							                		</tr>
<%		}
%>									            	<tr class="space"><td>&nbsp;</td></tr>
												</table>
											<jsp:include  page="../component/utilitybox2bottom.jsp" />
										</td>
									</tr>
								</table>
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
