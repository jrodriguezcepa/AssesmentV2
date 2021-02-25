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
	messages = sys.getText();
	String check = Util.checkPermission(sys,SecurityConstants.ADMINISTRATOR);
	
	RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
	dispatcher.include(request,response);

	if(check!=null) {
		response.sendRedirect(request.getContextPath()+check);
	}else {
	    String assesment = request.getParameter("assesment");
		session.setAttribute("url","module/genericlist.jsp");
	
		ListResult result = sys.getModuleReportFacade().findGenericModules("",sys.getUserSessionData());

		Collection elements = result.getElements();
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
	form.modules.value=valueCheckboxParamList;
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
			<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("system.add.genericmodules")%>' />
  		</tr>
      	<tr>
			<jsp:include  page="../component/spaceline.jsp" />
  		</tr>
		<tr>
			<td colspan="3">
				<html:form action="/ModuleCopy" >
					<html:hidden property="assesment" value='<%=assesment%>'/>
					<html:hidden property="modules"/>
					<jsp:include  page='<%="../component/utilityboxtop.jsp?title="+messages.getText("system.data.modules")%>' />
			    	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			      		<tr>
					    	<td class="guide2" width="5%">&nbsp;</td>
					        <td class="guide2" width="60%" align="left"><%=messages.getText("module.data.name")%></td>
					        <td class="guide2" width="30%" align="center"><%=messages.getText("module.data.question")%></td>
				        </tr>
<%			if(result.getElements().size() == 0) {
%>		      			<tr class="linetwo">
			            	<td colspan="3"><%=messages.getText("generic.messages.notresult")%></td>
            			</tr>
<%			}else {
		    	Iterator it = result.getElements().iterator();
				boolean linetwo = false;
				while(it.hasNext()){
	    			Object[] module = (Object[])it.next();
%>	            		<tr class='<%=(linetwo)?"linetwo":"lineone"%>'>
<%					linetwo = !linetwo;	
%>          	    		<td width="5%" align="left">
								<input type='checkbox' name='<%=String.valueOf(module[0])%>' value='<%=String.valueOf(module[0])%>' >                  
							</td>
		              		<td width="35%" align="rigth">
					        	<a href='layout.jsp?refer=/module/genericview.jsp?module=<%=String.valueOf(module[0])%>' >
	        			        	<%=messages.getText((String)module[1])%>
	                			</a>
							</td>
        	    	      	<td width="20%" align="center"><%=module[2]%></td>
		    	    	</tr>
<%				}
%>		      			<tr class="linetwo">
			            	<td colspan="5">&nbsp;</td>
            			</tr>
			    		<tr>
	                		<td colspan="5" class="linetwo" align="right">
			                	<input name="button2" type="button" onClick="javascript:deleteIFConfirm(document.forms['ModuleCopyForm'],'<%=messages.getText("module.add.confirm")%>');" value=<%=messages.getText("generic.messages.add")%> class="input"/>
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















