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
		session.setAttribute("url","assesment/genericlist.jsp");
	
		if(session.getAttribute("Msg")!=null){
			session.removeAttribute("Msg");
		}
		messages=sys.getText();
	
		String name = "";
		if(request.getParameter("name")!=null){
		    name = request.getParameter("name");
		}		
		ListResult result = sys.getModuleReportFacade().findGenericModules(name,sys.getUserSessionData());

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
	form.modules.value = valueCheckboxParamList;
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
			<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("system.data.genericmodules")%>' />
  		</tr>
      	<tr>
			<jsp:include  page="../component/spaceline.jsp" />
  		</tr>
  		<tr>
    		<td width="292" valign="top">
				<jsp:include  page='<%="../component/utilityboxtop.jsp?title="+messages.getText("generic.messages.search")%>' />
				<form action="layout.jsp?refer=/module/genericlist.jsp" method="post">
		    		<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<th align="left">
								<span class="input"><%=messages.getText("generic.module")%></span>
							</th>
							<td align="right">
								<input type="text" name="name" size="16" class="input" value='<%=name%>'/>
							</td>
						</tr>
			      		<tr  class="space"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
						<tr>
							<td colspan="2" align="right">
								<input name="button" type="submit" value='<%=messages.getText("generic.messages.search")%>' class="input"/>
							</td>
						</tr>
					</table>
				</form>
				<jsp:include  page="../component/utilityboxbottom.jsp" />
			</td>
    		<td width="16">&nbsp;</td>
    		<td width="292" valign="top">
				<jsp:include  page='<%="../component/utilityboxtop.jsp?title="+messages.getText("generic.messages.options")%>' />
				<table>
					<tr>
						<td class="linetwo">
            				<img align="ABSMIDDLE" border="0" height="12" src="/assesment/util/imgs/icon1.jpg" width="12">&nbsp; <a href="layout.jsp?refer=/module/genericcreate.jsp?type=create"> <%=messages.getText("module.message.new")%> </a>
            			</td>
            		</tr>
        		</table>
				<jsp:include  page="../component/utilityboxbottom.jsp" />
			</td>
	  	</tr>
  		<tr>
			<jsp:include  page="../component/spaceline.jsp" />
  		</tr>
		<tr>
			<td colspan="3">
				<html:form action="/GenericModuleDelete" >
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
			                	<input name="button2" type="button" onClick="javascript:deleteIFConfirm(document.forms['GenericModuleDeleteForm'],'<%=messages.getText("module.delete.confirm")%>');" value=<%=messages.getText("generic.messages.delete")%> class="input"/>
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















