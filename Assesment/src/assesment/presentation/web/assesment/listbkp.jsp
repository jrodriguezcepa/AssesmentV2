<%@page import="assesment.communication.assesment.AssesmentListData"%>
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
		session.setAttribute("url","assesment/listbkp.jsp");
	
		if(session.getAttribute("Msg")!=null){
			session.removeAttribute("Msg");
		}
		messages=sys.getText();
	
		String name = "";
		if(request.getParameter("name")!=null){
		    name = request.getParameter("name");
		}		
		String corporation = "";
		if(request.getParameter("corporation")!=null){
		    corporation = request.getParameter("corporation");
		}		
		String archived = "0";
		if(request.getParameter("archived") != null) {
			archived = request.getParameter("archived");
		}
		//ListResult result = sys.getAssesmentReportFacade().findAssesments(name,corporation,archived,sys.getUserSessionData());
		ListResult result = sys.getAssesmentReportFacade().findAssesmentsbkp(name,corporation,archived,sys.getUserSessionData());

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
		<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("system.data.assesments")%>' />
		<tr>
    		<td width="100%" valign="top">
				<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.messages.search")%>' />
				<form action="layout.jsp?refer=/assesment/listbkp.jsp" method="post">
		    		<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td align="left" class="line"><%=messages.getText("generic.assesment")%></td>
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
							<td align="left" class="line"><%=messages.getText("assesment.data.archived")%></td>
							<td align="right">
								<select name="archived" style="width: 300px;" class="input">
		        					<option value="0"><%=messages.getText("generic.messages.no")%></option>
		        					<option value="1"><%=messages.getText("generic.messages.yes")%></option>
		        					<option value="2"><%=messages.getText("generic.messages.all.pl")%></option>
								</select>
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
			<td colspan="3">
				<html:form action="/AssesmentDelete" >
					<html:hidden property="assesment" />
					<html:hidden property="target" value="list"/>
					<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("corporation.data.assesments")%>' />
			    	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			      		<tr>
					    	<td class="guide2" width="5%">&nbsp;</td>
					        <td class="guide2" width="35%" align="left"><%=messages.getText("assesment.data.name")%></td>
					        <td class="guide2" width="25%" align="left"><%=messages.getText("assesment.data.corporation")%></td>
					        <td class="guide2" width="20%" align="center"><%=messages.getText("assesment.data.start")%></td>
					        <td class="guide2" width="20%" align="center"><%=messages.getText("assesment.data.end")%></td>
				        </tr>
<%			if(result.getElements().size() == 0) {
%>		      			<tr class="linetwo">
			            	<td colspan="5"><%=messages.getText("generic.messages.notresult")%></td>
            			</tr>
<%			}else {
		    	Iterator<AssesmentListData> it = Util.getAssessmentIterator(result.getElements(), messages);
				boolean linetwo = false;
				while(it.hasNext()){
					AssesmentListData assesment = it.next();
					HashMap<String, String> messagesbkp = sys.getLanguageReportFacade().findAssessmentBKPTexts(assesment.getId(), sys.getUserSessionData());

%>	            		<tr class='<%=(linetwo)?"linetwo":"lineone"%>'>
<%					linetwo = !linetwo;	
%>          	    		<td width="5%" align="left">
								<input type='checkbox' name='<%=String.valueOf(assesment.getId())%>' value='<%=String.valueOf(assesment.getId())%>' >                  
							</td>
		              		<td width="35%" align="left">
					        	<a href='layout.jsp?refer=/assesment/viewbkp.jsp?assesment=<%=String.valueOf(assesment.getId())%>' >
	        			        	<%=messagesbkp.get((String)assesment.getName())%>
	                			</a>
							</td>
            				<td width="25%" align="left"><%=assesment.getCorporation()%></td>
            				<td width="20%" align="center"><%=Util.formatDate((Date)assesment.getStart())%></td>
        	    	      	<td width="20%" align="center"><%=Util.formatDate((Date)assesment.getEnd())%></td>
		    	    	</tr>
<%				}
%>		      			<tr class="linetwo">
			            	<td colspan="5">&nbsp;</td>
            			</tr>
<% 			}
%>					</table>
					<jsp:include page="../component/utilitybox2bottom.jsp" />
				</html:form>
			</td>
		</tr>
 
		<jsp:include  page='../component/titleend.jsp' />
	</body>
<%
	}
%>
</html>
