<%@page language="java"
	import="assesment.business.*"
	import="assesment.communication.language.*"
	import="assesment.communication.security.*"
	import="assesment.communication.administration.corporation.tables.*"
	import="assesment.communication.corporation.*"
	import="assesment.communication.assesment.*"
	import="assesment.communication.module.*"
	import="assesment.communication.question.*"
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

<%!	ModuleData data;
   	AssesmentAccess sys; 
   	Text messages;
%>
<%
	sys = (AssesmentAccess)session.getAttribute("AssesmentAccess"); 
	String check = Util.checkPermission(sys,SecurityConstants.ADMINISTRATOR);
	if(check!=null) {
		response.sendRedirect(request.getContextPath()+check);
	}else {

	    session.setAttribute("url","assesment/view.jsp");
		messages=sys.getText();
	
		Integer id = null;
		if(!Util.empty(request.getParameter("module"))) {
			id = new Integer(request.getParameter("module"));
		}else {
			id = new Integer((String)session.getAttribute("module"));
		}
		
		if(id != null){
			data = sys.getModuleReportFacade().findModule(id,sys.getUserSessionData());
%>

<head/>
<script type="text/javascript">
	function move(questionDown,questionUp,mode) {
		form = document.forms['QuestionChangeOrderForm'];
		form.questionDown.value = questionDown;
		form.questionUp.value = questionUp;
		form.submit();
	}	
</script>
	<body>
	<html:form action="/QuestionChangeOrder" >
		<input type="hidden" name="questionDown" 	/>
		<input type="hidden" name="questionUp" 	/>
		<input type="hidden" name="module"	value='<%=String.valueOf(id)%>' 	/>
  	</html:form>
		<table width="600" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr class="guide1">
				<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("generic.module")+" "+messages.getText(data.getKey())%>' />
	  		</tr>
    		<tr>
				<jsp:include  page="../component/spaceline.jsp" />
  			</tr>
			<tr>
				<td colspan="3">
					<jsp:include  page='<%="../component/utilityboxtop.jsp?title="+messages.getText("assesment.data.modules")%>' />
						<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<%			if(data.getQuestionSize() == 0) { 
%>			      			<tr>
					    		<td class="linetwo"><%=messages.getText("generic.messages.notresult")%></td>
							</tr>
<%			}else {
    			Iterator questionIt = data.getQuestionIterator();
    			Integer before = null;
    			QuestionData question = null;
    			boolean cont = true;
    			while(cont) {
    			    if(question == null) {
    			    	question = (QuestionData)questionIt.next();
    			    }
%>			      			<tr>
					    		<td class="linetwo">
									<jsp:include page='<%="../component/utilitybox2top.jsp?title="+messages.getText(question.getKey())%>' />
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr class="lineone">
		    								<td align="left" width="50%">
<%					if(before != null) {
%>												<input type=button class="lineone" value='<%=messages.getText("module.action.moveup")%>' onclick="javascript:move(<%=String.valueOf(before)%>,<%=String.valueOf(question.getId())%>);" />
<%					}else {
%>    											&nbsp;
<%					}
					before = question.getId();
%>
    										</td>
		    								<td align="right" width="50%">
<% 					if(questionIt.hasNext()) {    										
					    question = (QuestionData)questionIt.next();
%>		    									<input type=button class="lineone" value='<%=messages.getText("module.action.movedown")%>' onclick="javascript:move(<%=String.valueOf(before)%>,<%=String.valueOf(question.getId())%>);"/>
<%					}else {
    					cont = false;
%>												&nbsp;
<%					}
%>			    							</td>
    									</tr>
									</table>
									<jsp:include page="../component/utilitybox2bottom.jsp" />
								</td>
							</tr>
	   						<tr  class="space"><td align="right">&nbsp;</td></tr>
<%    			}
			}
%>
						</table>
					<jsp:include page="../component/utilityboxbottom.jsp" />
				</td>
			</tr>
    		<tr>
				<jsp:include  page="../component/spaceline.jsp" />
  			</tr>
    		<tr >
				<td colspan="3" align="right">
					<a href='<%="./layout.jsp?refer=/module/view.jsp?module="+String.valueOf(id)%>'>
						<input type="button" value='<%=messages.getText("generic.messages.end")%>' class="input">
					</a>
				</td>
  			</tr>
  		</table>
</body>
<%		}
	}
%>  			
</html:html>

