<%@page language="java"
	import="assesment.business.*"
	import="assesment.communication.language.*"
	import="assesment.communication.security.*"
	import="assesment.communication.administration.corporation.tables.*"
	import="assesment.communication.corporation.*"
	import="assesment.communication.assesment.*"
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

<script language="JavaScript">
	function confirmDelete(form,message) {
		if(confirm(message)) {
			form.submit();
		}
	}

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

<script language="JavaScript" src="./css/create_functions.js" type="text/javascript">
</script>


<%!	CorporationData data;
   	AssesmentAccess sys; 
   	Text messages;
%>
<%
	sys = (AssesmentAccess)session.getAttribute("AssesmentAccess"); 
	String check = Util.checkPermission(sys,SecurityConstants.ADMINISTRATOR);
	if(check!=null) {
		response.sendRedirect(request.getContextPath()+check);
	}else {
		session.setAttribute("url","corporation/view.jsp");
		messages=sys.getText();
	
		Integer id = null;
		if(!Util.empty(request.getParameter("corporation"))) {
			id = new Integer(request.getParameter("corporation"));
		}else {
			id = new Integer((String)session.getAttribute("corporation"));
		}
		
		if(id != null){
			data = sys.getCorporationReportFacade().findCorporation(id,sys.getUserSessionData());
%>

<head/>

	<body>

		<form action="./layout.jsp?refer=/corporation/list.jsp" name='back' method="post">
		</form>	
		<form action="./layout.jsp?refer=/corporation/create.jsp" name='edit' method="post">
			<input type="hidden" name="corporation" 		value='<%=id%>' />
			<input type="hidden" name="type" 	value="edit" />
		</form>	
		<form action="./layout.jsp?refer=/assesment/create.jsp" name='assesment' method="post">
			<input type="hidden" name="corporation" 		value='<%=id%>' />
			<input type="hidden" name="type" 			value="create" />
			<input type="hidden" name="target" 			value="corporation" />
		</form>	
		<html:form action="/CorporationDelete" >
			<input type="hidden" name="corporation" value='<%=id%>' />
	  	</html:form>
		<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("generic.corporation.data")%>' />
	  		<tr>			  		
				<jsp:include page="viewOptions.jsp" />
			</tr>
	  		<tr>
	  			<td>
			  		<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.corporation.data")%>' />
				    	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
					  		<tr class="line">
				    			<td align="left">
									<%=messages.getText("generic.corporation.corporationdata.name")+": "%>
								</td>
				    			<td align="right">
									<%=data.getName()%>
								</td>
							</tr>
					  		<tr class="line">
				    			<td align="left" valign="top">
									<%=messages.getText("generic.corporation.corporationdata.logo")+": "%>
								</td>
				    			<td align="right">
									<img src='<%="../flash/images/logo"+id+".png"%>' width="150">
								</td>
							</tr>
						</table>
					<jsp:include page="../component/utilityboxbottom.jsp" />
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<html:form action="/AssesmentDelete" >
						<html:hidden property="assesment" />
						<html:hidden property="corporation" value='<%=String.valueOf(id)%>'/>
						<html:hidden property="target" value="corporation"/>
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("corporation.data.assesments")%>' />
					    	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
					      		<tr>
							    	<td class="guide2" width="5%">&nbsp;</td>
							        <td class="guide2" width="50%" align="left"><%=messages.getText("assesment.data.name")%></td>
							        <td class="guide2" width="20%" align="center"><%=messages.getText("assesment.data.start")%></td>
							        <td class="guide2" width="20%" align="center"><%=messages.getText("assesment.data.end")%></td>
						        </tr>
<%			if(data.getAssesments().size() == 0) {
%>				      			<tr class="linetwo">
					            	<td colspan="4"><%=messages.getText("generic.messages.notresult")%></td>
            					</tr>
<%			}else {
		    	Iterator<AssesmentListData> it = Util.getAssessmentAttrIterator(data.getAssesments(), messages);
				boolean linetwo = false;
				while(it.hasNext()){
					AssesmentListData assesment = it.next();
%>	            				<tr class='<%=(linetwo)?"linetwo":"lineone"%>'>
<%					linetwo = !linetwo;	
%>          	    				<td width="5%" align="center">
										<input type='checkbox' name='<%=String.valueOf(assesment.getId())%>' value='<%=String.valueOf(assesment.getId())%>' >                  
									</td>
				              		<td width="50%" align="rigth">
							        	<a href='layout.jsp?refer=/assesment/view.jsp?assesment=<%=String.valueOf(assesment.getId())%>' >
			        			        	<%=messages.getText(assesment.getName())%>
			                			</a>
									</td>
		            				<td width="20%" align="center"><%=Util.formatDate(assesment.getStart())%></td>
		        	    	      	<td width="20%" align="center"><%=Util.formatDate(assesment.getEnd())%></td>
				    	    	</tr>
<%				}
 			}
%>							</table>
						<jsp:include page="../component/utilityboxbottom.jsp" />
					</html:form>
				</td>
			</tr>
    		<tr class="line">
           		<td colspan="2" align="right">
			    	<input name="button2" type="button" onClick="javascript:deleteIFConfirm(document.forms['AssesmentDeleteForm'],'<%=messages.getText("assesment.delete.confirm")%>');" value=<%=messages.getText("generic.messages.delete")%> class="input"/>
			    </td>
  			</tr>
	  		<tr>			  		
				<jsp:include page="viewOptions.jsp" />
			</tr>
		<jsp:include  page='../component/titleend.jsp' />
	</body>
<%		}
	}
%>  			
</html:html>

