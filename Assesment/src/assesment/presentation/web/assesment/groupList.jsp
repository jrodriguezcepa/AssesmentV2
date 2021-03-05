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
		session.setAttribute("url","assesment/groupList.jsp");
	
		if(session.getAttribute("Msg")!=null){
			session.removeAttribute("Msg");
		}
		messages=sys.getText();
	
		String name = "";
		if(request.getParameter("name")!=null){
		    name = request.getParameter("name");
		}		
		Integer corporation = null;
		if(request.getParameter("corporation")!=null){
		    corporation = new Integer(request.getParameter("corporation"));
		}		
		Collection groups = sys.getAssesmentReportFacade().findGroups(name, corporation, sys.getUserSessionData());
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
	form.assesment.value=valueCheckboxParamList;
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
		<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("system.data.groups")%>' />
  		<tr>
    		<td width="100%" valign="top">
				<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.messages.search")%>' />
				<form action="layout.jsp?refer=/assesment/groupList.jsp" method="post">
		    		<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td align="left" class="line"><%=messages.getText("generic.group")%></td>
							<td align="right">
								<input type="text" name="name" style="width: 300px;" class="input" value='<%=name%>'/>
							</td>
						</tr>
			      		<tr  class="space"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
						<tr>
							<td align="left" class="line"><%=messages.getText("generic.corporation")%></td>
							<td align="right">
								<select name="corporation" style="width: 300px;" class="input">
		        					<option value=""><%=messages.getText("generic.messages.select")%></option>
<%		ListResult corporations = sys.getCorporationReportFacade().findCorporationName("",sys.getUserSessionData());
		if(corporations != null && corporations.getElements() != null) {
		    Iterator it = corporations.getElements().iterator();
		    while(it.hasNext()) {
		        Object[] data = (Object[])it.next();
%>		        					<option value='<%=String.valueOf(data[0])%>'><%=String.valueOf(data[1])%></option>
<%		    }
		}
%>								</select>
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
				<jsp:include  page="../component/utilitybox2bottom.jsp" />
			</td>
  		</tr>
  		<tr>
    		<td width="100%" valign="top">
       			<a href="layout.jsp?refer=/assesment/createGroup.jsp?type=create&target=list"> 
       				<input type="button" class="button" value='<%=messages.getText("corporation.message.new")%>'> 
				</a>
			</td>
	  	</tr>
		<tr>
			<td colspan="3">
					<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("corporation.data.groups")%>' />
			    	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			      		<tr>
					        <td class="guide2" width="1%" align="left"></td>
					        <td class="guide2" width="49%" align="left"><%=messages.getText("group.data.name")%></td>
					        <td class="guide2" width="50%" align="left"><%=messages.getText("group.data.corporation")%></td>
				        </tr>
<%			if(groups.size() == 0) {
%>		      			<tr class="linetwo">
			            	<td colspan="5"><%=messages.getText("generic.messages.notresult")%></td>
            			</tr>
<%			}else {
		    	Iterator it = groups.iterator();
				boolean linetwo = false;
				while(it.hasNext()){
	    			Object[] assesment = (Object[])it.next();
%>	            		<tr class='<%=(linetwo)?"linetwo":"lineone"%>'>
<%					linetwo = !linetwo;	
%>
					        <td width="1%" align="left"></td>
	          	    		<td width="49%" align="left">
					        	<a href='layout.jsp?refer=/assesment/viewGroup.jsp?id=<%=String.valueOf(assesment[0])%>' >
	        			        	<%=assesment[1]%>
	                			</a>
							</td>
            				<td width="50%" align="left"><%=assesment[2]%></td>
		    	    	</tr>
<%				}
%>		      			<tr class="linetwo">
			            	<td colspan="5">&nbsp;</td>
            			</tr>
<% 			}
%>					</table>
					<jsp:include page="../component/utilitybox2bottom.jsp" />
				</td>
			</tr>
		<jsp:include  page='../component/titleend.jsp' />
	</body>
<%
	}
%>
</html>
