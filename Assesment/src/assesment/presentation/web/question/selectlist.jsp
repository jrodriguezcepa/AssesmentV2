<%@page language="java" 
	errorPage="../exception.jsp"
	import="java.util.*"
	import="assesment.business.*"
	import="assesment.communication.language.*"
	import="assesment.communication.security.*"
	import="assesment.communication.module.*"
	import="assesment.communication.question.*"
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
	messages = sys.getText();
	String check = Util.checkPermission(sys,SecurityConstants.ADMINISTRATOR);
	
	RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
	dispatcher.include(request,response);

	if(check!=null) {
		response.sendRedirect(request.getContextPath()+check);
	}else {
	    String module = request.getParameter("module");
	    String genericModule = request.getParameter("genericmodule");
	    String target = request.getParameter("target");
		session.setAttribute("url","question/genericlist.jsp");
	
		ModuleData moduleData = sys.getModuleReportFacade().findGenericModule(new Integer(genericModule),sys.getUserSessionData());
%>
<script language="javascript" src='../util/js/Prepared_Parameters.js' type='text/javascript' ></script>

<script>
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
	form.questions.value=valueCheckboxParamList;
	if(valueCheckboxParamList.length>0){
		if(confirm(msg)){
			form.submit();
		}
	}
}
</script>
	<LINK REL=StyleSheet HREF="../util/css/estilo.css" TYPE="text/css">
		
<head/>

<body>
	<table width="600" border="0" align="center" cellpadding="0" cellspacing="0">
		<tr class="guide1">
			<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("system.add.genericquestions")%>' />
  		</tr>
      	<tr>
			<jsp:include  page="../component/spaceline.jsp" />
  		</tr>
		<tr>
			<td colspan="3">
				<html:form action="/QuestionCopy" >
					<html:hidden property="module" value='<%=module%>'/>
					<html:hidden property="target" value='<%=target%>'/>
					<html:hidden property="questions"/>
					<jsp:include  page='<%="../component/utilityboxtop.jsp?title="+messages.getText("system.data.questions")%>' />
			    	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			      		<tr>
					    	<td class="guide2" width="5%">&nbsp;</td>
					        <td class="guide2" width="60%" align="left"><%=messages.getText("module.data.text")%></td>
					        <td class="guide2" width="30%" align="center"><%=messages.getText("module.data.type")%></td>
				        </tr>
<%			if(moduleData.getQuestions().size() == 0) {
%>		      			<tr class="linetwo">
			            	<td colspan="3"><%=messages.getText("generic.messages.notresult")%></td>
            			</tr>
<%			}else {
		    	Iterator it = moduleData.getQuestionIterator();
				boolean linetwo = false;
				while(it.hasNext()){
	    			QuestionData question = (QuestionData)it.next();
%>	            		<tr class='<%=(linetwo)?"linetwo":"lineone"%>'>
<%					linetwo = !linetwo;	
%>          	    		<td width="5%" align="left">
								<input type='checkbox' name='<%=String.valueOf(question.getId())%>' value='<%=String.valueOf(question.getId())%>' >                  
							</td>
		              		<td width="35%" align="rigth">
					        	<a href='layout.jsp?refer=/module/genericview.jsp?module=<%=String.valueOf(question.getId())%>' >
	        			        	<%=messages.getText(question.getKey())%>
	                			</a>
							</td>
<%					switch(question.getType().intValue()) {
				    	case 1:
%>        	    	      	<td width="20%" align="center"><%=messages.getText("question.type.text")%></td>
<%							break;
				    	case 2:
%>        	    	      	<td width="20%" align="center"><%=messages.getText("question.type.date")%></td>
<%							break;
				    	case 3:
%>        	    	      	<td width="20%" align="center"><%=messages.getText("question.type.options")%></td>
<%							break;
					}
%>						</tr>
<%				}
%>		      			<tr class="linetwo">
			            	<td colspan="5">&nbsp;</td>
            			</tr>
			    		<tr>
	                		<td colspan="5" class="linetwo" align="right">
			                	<input name="button2" type="button" onClick="javascript:deleteIFConfirm(document.forms['QuestionCopyForm'],'<%=messages.getText("question.add.confirm")%>');" value=<%=messages.getText("generic.messages.add")%> class="input"/>
			                	<html:cancel value='<%=messages.getText("generic.messages.cancel	")%>' styleClass="input"/>
							</td>
              			</tr>
<% 			}
%>					</table>
					<jsp:include page="../component/utilityboxbottom.jsp" />
					</html:form>
				</td>
			</tr>
    </table>
</body>
<%
	}
%>
</html>















