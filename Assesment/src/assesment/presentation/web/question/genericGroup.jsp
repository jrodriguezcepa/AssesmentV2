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

		RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
		dispatcher.include(request,response);

		session.setAttribute("url","module/genericGroup.jsp");
		messages=sys.getText();
	
		Integer id = null;
		if(!Util.empty(request.getParameter("module"))) {
			id = new Integer(request.getParameter("module"));
		}else {
			id = new Integer((String)session.getAttribute("module"));
		}
		
		if(id != null){
			data = sys.getModuleReportFacade().findGenericModule(id,sys.getUserSessionData());
%>

<head/>
<script type="text/javascript">
	function move(questionDown,questionUp,mode) {
		form = document.forms['GenericQuestionChangeOrderForm'];
		form.questionDown.value = questionDown;
		form.questionUp.value = questionUp;
		form.submit();
	}	
</script>
	<body>
	<html:form action="/GenericQuestionGroup" >
		<html:hidden property="module"	value='<%=String.valueOf(id)%>' 	/>
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
    			while(questionIt.hasNext()) {
    			    QuestionData question = (QuestionData)questionIt.next();
%>			      			<tr>
					    		<td class="linetwo">
									<jsp:include page='<%="../component/utilitybox2top.jsp?title="+messages.getText(question.getKey())%>' />
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr class="lineone">
		    								<td align="left" width="50%">
		    									<%=messages.getText("module.question.group")%>&nbsp;
		    									<select name='<%="group"+String.valueOf(question.getId())%>' class="input">
													<option value="0"><%=messages.getText("module.question.nogroup")%></option>    
<% 					for(int i = 1; i <= data.getQuestionSize()/2; i++){
    					String selected = (question.getGroup().intValue() == i) ? "selected" : "";
%>													<option value='<%=i%>' <%=selected%>><%=i%></option>    
<%					}
%>		    									</select>
		    								</td>
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
					<html:submit value='<%=messages.getText("generic.messages.accept")%>'  styleClass="input"/>
					<html:cancel value='<%=messages.getText("generic.messages.cancel")%>'  styleClass="input"/>
				</td>
  			</tr>
  		</table>
  	</html:form>  		
</body>
<%		}
	}
%>  			
</html:html>

