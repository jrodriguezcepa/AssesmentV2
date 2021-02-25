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

<%!	AssesmentData data;
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
		if(!Util.empty(request.getParameter("assesment"))) {
			id = new Integer(request.getParameter("assesment"));
		}else {
			id = new Integer((String)session.getAttribute("assesment"));
		}
		
		if(id != null){
			data = sys.getAssesmentReportFacade().findAssesment(id,sys.getUserSessionData());
%>

<head/>
<script type="text/javascript">
	function move(moduleDown,moduleUp,mode) {
		form = document.forms['ModuleChangeOrderForm'];
		form.moduleDown.value = moduleDown;
		form.moduleUp.value = moduleUp;
		form.submit();
	}	
</script>
	<body>
		<html:form action="/ModuleChangeOrder" >
			<input type="hidden" name="moduleDown" 	/>
			<input type="hidden" name="moduleUp" 	/>
			<input type="hidden" name="assesment"	value='<%=String.valueOf(id)%>' 	/>
	  	</html:form>
		<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("generic.assesment")+" "+messages.getText(data.getName())%>' />
			<tr>
				<td>
					<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("assesment.data.modules")%>' />
						<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<%			if(data.getModules().size() == 0) { 
%>			      			<tr>
					    		<td class="linetwo"><%=messages.getText("generic.messages.notresult")%></td>
							</tr>
<%			}else {
    			Iterator moduleIt = data.getModuleIterator();
    			Integer before = null;
    			ModuleData module = null;
    			boolean cont = true;
    			while(cont) {
    			    if(module == null) {
    			    	module = (ModuleData)moduleIt.next();
    			    }
%>			      			<tr class="line">
		    					<td align="center" width="15%">
<%					if(before != null) {
%>									<input type=button class="input" value='<%=messages.getText("module.action.moveup")%>' onclick="javascript:move(<%=String.valueOf(before)%>,<%=String.valueOf(module.getId())%>);" />
<%					}else {
%>    								&nbsp;
<%					}
					before = module.getId();
%>								</td>
	   							<td align="left" width="70%" class="line">
									<%=messages.getText(module.getKey())%>
								</td>
	   							<td align="center" width="15%">
<% 					if(moduleIt.hasNext()) {    										
				    	module = (ModuleData)moduleIt.next();
%>	   								<input type=button class="input" value='<%=messages.getText("module.action.movedown")%>' onclick="javascript:move(<%=String.valueOf(before)%>,<%=String.valueOf(module.getId())%>);"/>
<%					}else {
    					cont = false;
%>									&nbsp;
<%					}
%>	    						</td>
    						</tr>
							<tr class="line" style="height: 10px;">
    							<td align="center" colspan="3">
    								<hr>
    							</td>
  							</tr>
<%    			}
			}
%>
						</table>
					<jsp:include page="../component/utilitybox2bottom.jsp" />
				</td>
			</tr>
    		<tr>
				<td colspan="3" align="right">
					<a href='<%="./layout.jsp?refer=/assesment/view.jsp?assesment="+String.valueOf(id)%>'>
						<input type="button" value='<%=messages.getText("generic.messages.end")%>' class="button">
					</a>
				</td>
  			</tr>
		<jsp:include  page='../component/titleend.jsp' />
	</body>
<%		}
	}
%>  			
</html:html>

