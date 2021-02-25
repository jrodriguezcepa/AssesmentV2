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
	if(sys == null) {
	    System.out.println("null");
	}
	String check = Util.checkPermission(sys,SecurityConstants.ADMINISTRATOR);
	
	RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
	dispatcher.include(request,response);

	if(check!=null) {
		response.sendRedirect(request.getContextPath()+check);
	}else {
		session.setAttribute("url","corporation/cediList.jsp");
	
		if(session.getAttribute("Msg")!=null){
			session.removeAttribute("Msg");
		}
		messages=sys.getText();
	
		String value = "";
		if(request.getParameter("value")!=null){
			value = request.getParameter("value");
		}		
		ListResult result = sys.getCorporationReportFacade().findCediName(value,CediData.GRUPO_MODELO,sys.getUserSessionData());
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
	form.cedi.value=valueCheckboxParamList;
	if(valueCheckboxParamList.length>0){
		if(confirm(msg)){
			form.submit();
		}
	}
}
</script>
	<LINK REL=StyleSheet HREF="../util/css/estilo.css" TYPE="text/css">
		
<head/>

	<html:form action="/CediDelete">
		<html:hidden property="cedi"/>
		<html:hidden property="target" value="list"/>
	</html:form>
	<body>
		<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("system.data.cedis")%>' />
	  		<tr>
	    		<td width="100%" valign="top">
					<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.messages.search")%>' />
		    		<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td align="left" class="line">
								<form action="layout.jsp?refer=/corporation/cediList.jsp" method="post">
									<%=messages.getText("generic.cedi")%>
									<input type="text" name="value" style="width: 300px;"  class="input" value='<%=value%>'/>
									<input name="button" type="submit" value='<%=messages.getText("generic.messages.search")%>' class="input"/>
								</form>
							</td>
						</tr>
					</table>
					<jsp:include  page="../component/utilitybox2bottom.jsp" />
				</td>
    		</tr>
	  		<tr>
	    		<td width="100%" valign="top">
       				<a href='<%="layout.jsp?refer=/corporation/createCedi.jsp?type=create&company="+CediData.MUTUAL%>'> 
       					<input type="button" class="button" value='<%=messages.getText("corporation.message.new")%>'> 
       				</a>
				</td>
		  	</tr>
	  		<tr>
	    		<td colspan="3">
					<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("system.data.cedis")%>' />
	    			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	      				<tr>
			                <td class="guide2" width="3%"></td>
			                <td class="guide2" width="70%" align="left"><%=messages.getText("corporation.data.name")%></td>
	              		</tr>
<%		if(elements.size() == 0) { 	

%>			      		<tr>
    	          			<td colspan="5"><%=messages.getText("generic.messages.notresult")%></td>
        	      		</tr>
<%		}else { 
			Iterator it = elements.iterator();
			boolean linetwo = false;
			while(it.hasNext()){
	    		Object[] cedi = (Object[])it.next();

%>	       		     	<tr align=center class='<%=(linetwo)?"linetwo":"lineone"%>'>
<%				linetwo = !linetwo;	
%>              			<td width="3%" align="center">
								<input type='checkbox' name='<%=String.valueOf(cedi[0])%>' value='<%=String.valueOf(cedi[0])%>' >                  
							</td>
	            	  		<td width="70%" align="left">
		            			<a href='layout.jsp?refer=/corporation/viewCedi.jsp?cedi=<%=String.valueOf(cedi[0])%>' >
	                  				<%=String.valueOf(cedi[1])%>
	                  			</a>
							</td>
        	        	</tr>
<%			}
%>  			    	
<% 		}
%>          		</table>
        			<jsp:include page="../component/utilitybox2bottom.jsp" />
				</td>
	 		</tr>
			<tr>
				<td colspan="3" align="right">
                	<input name="button2" type="button" onClick="javascript:deleteIFConfirm(document.forms['CediDeleteForm'],'<%=messages.getText("corporation.cedi.delete.confirm")%>');" value='<%=messages.getText("generic.messages.delete")%>' class="button"/>
				</td>
           	</tr>
		<jsp:include  page='../component/titleend.jsp' />
	</body>
<%
	}
%>
</html>
