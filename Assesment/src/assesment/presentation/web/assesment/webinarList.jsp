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
	  	String wbCode = (Util.empty(request.getParameter("wbCode"))) ? "" : request.getParameter("wbCode");
	  	String wb1 = (Util.empty(request.getParameter("wb1"))) ? "" : request.getParameter("wb1");
	  	String wb2 = (Util.empty(request.getParameter("wb2"))) ? "" : request.getParameter("wb2");
	  	String wb3 = (Util.empty(request.getParameter("wb3"))) ? "" : request.getParameter("wb3");
	  	String wb4 = (Util.empty(request.getParameter("wb4"))) ? "" : request.getParameter("wb4");
	  	String wb5 = (Util.empty(request.getParameter("wb5"))) ? "" : request.getParameter("wb5");

		Collection courses = sys.getAssesmentReportFacade().findWebinars(wbCode, wb1, wb2, wb3, wb4, wb5, sys.getUserSessionData());
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
		<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("generic.messages.course")%>' />
  		<tr>
    		<td width="100%" valign="top">
				<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.messages.search")%>' />
				<form action="layout.jsp?refer=/assesment/webinarList.jsp" method="post">
		    		
		    		<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr>
							<td align="left" class="line"><%=messages.getText("generic.messages.webinarcode")%></td>
							<td align="right">
								<input type="text" name="wbCode" style="width: 500px;" class="input" value='<%=wbCode%>'/>
							</td>
						</tr>
						<tr  class="space"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
						<tr>
							<td align="left" class="line"><%=messages.getText("generic.data.assesmenttype")%></td>
							<td align="right">
														</td>
						</tr>
						<tr  class="space"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
						
						<tr>	
						<td align="left" class="line">
						
							<label>
								<%if (wb1.equals("")) {%>
								<input type="checkbox" name="wb1" value="1149"><%=messages.getText("assessment1149.name")%></input>
							<%} 
							else{ %>
								<input type="checkbox" name="wb1" value="1149" checked><%=messages.getText("assessment1149.name")%></input>							
							<%} %>							
							</label>
						</td></tr>
						<tr>	<td align="left" class="line">
												
											
						
							<label>
								<%if (wb2.equals("")) {%>
								<input type="checkbox" name="wb2" value="1270"><%=messages.getText("assessment1270.name")%></input>
							<%} 
							else{ %>
								<input type="checkbox" name="wb2" value="1270" checked><%=messages.getText("assessment1270.name")%></input>							
							<%} %>							
							</label>
						</td></tr>	
						<tr>						<td align="left" class="line">
						
							<label>
								<%if (wb3.equals("")) {%>
								<input type="checkbox" name="wb3" value="1324"><%=messages.getText("assessment1324.name")%></input>
							<%} 
							else{ %>
								<input type="checkbox" name="wb3" value="1324" checked><%=messages.getText("assessment1324.name")%></input>							
							<%} %>							
							</label>
						</td></tr>
						<tr><td align="left" class="line">
						
							<label>
								<%if (wb4.equals("")) {%>
								<input type="checkbox" name="wb4" value="1327"><%=messages.getText("assessment1327.name")%></input>
							<%} 
							else{ %>
								<input type="checkbox" name="wb4" value="1327" checked><%=messages.getText("assessment1327.name")%></input>							
							<%} %>							
							</label>
						</td></tr>
							<tr><td align="left" class="line">
						
							<label>
								<%if (wb5.equals("")) {%>
								<input type="checkbox" name="wb5" value="1471"><%=messages.getText("assessment1471.name")%></input>
							<%} 
							else{ %>
								<input type="checkbox" name="wb5" value="1471" checked><%=messages.getText("assessment1471.name")%></input>							
							<%} %>							
							</label>
								      				
	
	           		</td></tr>
						</tr>

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
					<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.messages.course")%>' />
			    	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			      		<tr>
					        <td class="guide2" width="5%" align="left"></td>
					        <td class="guide2" width="30%" align="left"><%=messages.getText("TIPO")%></td>
					        <td class="guide2" width="20%" align="left"><%=messages.getText("CODIGO WEBINAR")%></td>
					        <td class="guide2" width="40%" align="left"><%=messages.getText("CANTIDAD ")%></td>
				        	<td class="guide2" width="5%" align="left"></td>
				        
				        </tr>
<%			if(courses.size() == 0) {
%>		      			<tr class="linetwo">
			            	<td colspan="5"><%=messages.getText("generic.messages.notresult")%></td>
            			</tr>
<%			}else {
		    	Iterator it = courses.iterator();
				boolean linetwo = false;
				while(it.hasNext()){
	    			String[] webinar = (String[])it.next();
%>	            		<tr class='<%=(linetwo)?"linetwo":"lineone"%>'>
<%					linetwo = !linetwo;	
%>
					        <td width="1%" align="left"></td>
	          	    		<td width="49%" align="left">
					        	<a href='layout.jsp?refer=/assesment/webinarView.jsp?code=<%=messages.getText(String.valueOf(webinar[1]))%>&type=<%=String.valueOf(webinar[0])%>&assesment=<%=String.valueOf(webinar[2])%>' >
	        			        	<%=messages.getText(String.valueOf(webinar[0]))%>
	                			</a>
							</td>
            				<td width="50%" align="left"><%=webinar[1]%></td>
            				<td width="50%" align="left"><%=webinar[3]%></td>
            				
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
