<%@page language="java"
	import="assesment.business.*"
	import="java.util.*"
	import="assesment.communication.tag.*"
	import="assesment.communication.language.*"
	import="assesment.communication.security.*"
	import="assesment.communication.util.*"
	import="assesment.presentation.actions.assesment.*"
	import="assesment.presentation.translator.web.util.*"
	import="assesment.communication.assesment.*"
	errorPage="../exception.jsp"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>
<html:html>

	<LINK REL=StyleSheet HREF="../util/css/estilo.css" TYPE="text/css">
	<script>
		function confirmAssociation(form,msg){
			var elements=document.forms['tags'].elements;
			var length=elements.length;
			var list="";
			for(i=0;i<length;i++){
				var element=elements[i];
				if(element.type.toLowerCase()=="checkbox"){
					if(element.checked){
						if(list==""){
							list=element.value;
						}else{
							list=element.value+"<"+list;
						}	
					}	
				} 
			}
			if(list.length>0){
				if(confirm(msg)){
					form.tags.value=list;
					form.submit();
				}
			}
		}
	</script>
<%
	AssesmentAccess sys=(AssesmentAccess)session.getAttribute("AssesmentAccess");
	String check = Util.checkPermission(sys,SecurityConstants.ADMINISTRATOR);
	Text messages=sys.getText();

	if(check!=null) {
		response.sendRedirect(request.getContextPath()+check);
	}else {

		String assesment = request.getParameter("assesment");
		session.setAttribute("url","assesment/tags.jsp?assesment="+assesment);

	
		RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
		dispatcher.include(request,response);

		Collection list = sys.getTagReportFacade().getListNotAssociated(new Integer(assesment),messages,sys.getUserSessionData());
		Collections.sort((List)list);
%>
	<head/>
	<html:form action="/AssociateAssesmentTag">
		<html:hidden property="assesment" value='<%=assesment%>' />
		<html:hidden property="tags" />
	</html:form>
	<body>
		<form name="tags">
			<table width="600" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr class="guide1">
					<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("assessment.action.associatetag")%>' />
				</tr>
		      	<tr>
					<jsp:include  page="../component/spaceline.jsp" />
		  		</tr>
		      	<tr>
		      		<td>
						<jsp:include  page='<%="../component/utilityboxtop.jsp?title="+messages.getText("generic.assesment.tags")%>' />
						<table align='center' width='100%' cellpadding="0" cellspacing="0">
				      		<tr>
						    	<td class="guide2" width="5%">&nbsp;</td>
						        <td class="guide2" width="95%" align="left"><%=messages.getText("assesment.tags.lesson")%></td>
						  	</tr>      
<%		Iterator it = list.iterator();
		boolean lineone = false;
		while(it.hasNext()) {
		    TagData tag = (TagData)it.next();
%>							<tr class='<%=(lineone) ? "lineone" : "linetwo" %>'>
								<td width="5%"><input type="checkbox" name='<%=String.valueOf(tag.getId())%>' value='<%=String.valueOf(tag.getId())%>'></td>
								<th width="95%" align="left">
									<%=tag.getText()%>
								</th>
<%			lineone = !lineone;
		}
%>							</tr>
					      	<tr>
					      		<td align="right" colspan="3">
						            <input type="button" class="input" value='<%=messages.getText("assessment.action.associate")%>' onClick="javascript:confirmAssociation(document.forms['AssociateAssesmentTagForm'],'<%=messages.getText("assesment.associatetag.confirm")%>');" />
						            <input type="button" class="input" value='<%=messages.getText("generic.messages.cancel")%>' />
					      		</td>
					      	</tr>
						</table>
						<jsp:include  page="../component/utilityboxbottom.jsp" />
					</td>
				</tr>
		      	<tr>
					<jsp:include  page="../component/spaceline.jsp" />
		  		</tr>
			</table>
		</form>
	</body>
<%	}	
%>
</html:html>
