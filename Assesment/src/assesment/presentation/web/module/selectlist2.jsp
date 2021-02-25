<%@page language="java" 
	errorPage="../exception.jsp"
	import="java.util.*"
	import="assesment.business.*"
	import="assesment.communication.language.*"
	import="assesment.communication.security.*"
	import="assesment.communication.module.*"
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
	    String moduleId = request.getParameter("module");
	    String target = request.getParameter("target");
	    
		session.setAttribute("url","module/selectlist2.jsp");
	
		ListResult result = sys.getModuleReportFacade().findGenericModules("",sys.getUserSessionData());

		Collection elements = result.getElements();
%>
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
				<jsp:include  page='<%="../component/utilityboxtop.jsp?title="+messages.getText("system.data.modules")%>' />
		    	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		      		<tr>
				        <td class="guide2" width="64%" align="left"><%=messages.getText("module.data.name")%></td>
				        <td class="guide2" width="35%" align="center"><%=messages.getText("module.data.question")%></td>
			        </tr>
<%		if(result.getElements().size() == 0) {
%>	     			<tr class="linetwo">
		            	<td colspan="3"><%=messages.getText("generic.messages.notresult")%></td>
           			</tr>
<%		}else {
		   	Iterator it = result.getElements().iterator();
			boolean linetwo = false;
			while(it.hasNext()){
	    		Object[] module = (Object[])it.next();
%>	            	<tr class='<%=(linetwo)?"linetwo":"lineone"%>'>
<%				linetwo = !linetwo;	
%>		            	<td width="35%" align="rigth">
					      	<a href='<%="./layout.jsp?refer=/question/selectlist.jsp?module="+moduleId+"&genericmodule="+String.valueOf(module[0])+"&target="+target%>' >
	        		        	<%=messages.getText((String)module[1])%>
	                		</a>
						</td>
        	    	   	<td width="20%" align="center"><%=module[2]%></td>
		   	    	</tr>
<%			}
%>		      		<tr class="linetwo">
			           	<td colspan="5">&nbsp;</td>
            		</tr>
<% 		}
%>				</table>
				<jsp:include page="../component/utilityboxbottom.jsp" />
			</td>
		</tr>
      	<tr>
			<jsp:include  page="../component/spaceline.jsp" />
  		</tr>
<%		if(target.equals("assesment")) {
    		ModuleData module = sys.getModuleReportFacade().findModule(new Integer(moduleId),sys.getUserSessionData());
%>  	<tr>
	    	<td colspan="3" class="linetwo" align="right">
	    		<a href='<%="./layout.jsp?refer=/assesment/view.jsp?assesment="+String.valueOf(module.getAssesment())%>'>
					<input name="button2" type="button" value=<%=messages.getText("generic.messages.cancel")%> class="input"/>
				</a>
			</td>
        </tr>
<%		}else {
%>  	<tr>
	    	<td colspan="3" class="linetwo" align="right">
	    		<a href='<%="./layout.jsp?refer=/module/view.jsp?module="+moduleId%>'>
					<input name="button2" type="button" value=<%=messages.getText("generic.messages.cancel")%> class="input"/>
				</a>
			</td>
        </tr>
<%		}
%>	</table>
</body>
<%	}
%>
</html>
