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
		session.setAttribute("url","report/createfromdc.jsp");
	
		if(session.getAttribute("Msg")!=null){
			session.removeAttribute("Msg");
		}
		messages=sys.getText();
	
		ListResult assesmentList = sys.getAssesmentReportFacade().findAssesments("","","0",sys.getUserSessionData());
		Iterator it = assesmentList.getElements().iterator();
		Collection assesments = new LinkedList();
		assesments.add(new OptionItem(messages.getText("generic.messages.select"),""));
		while(it.hasNext()) {
	        Object[] data = (Object[])it.next();
		    assesments.add(new OptionItem((String)data[1],String.valueOf(data[0])));
		}
		session.setAttribute("assesments",assesments);
		
		Collection passwords = new LinkedList();
		passwords.add(new OptionItem(messages.getText("user.password.generate"),"1"));
		passwords.add(new OptionItem(messages.getText("user.password.insert"),"2"));
		session.setAttribute("passwords",passwords);

		Collection result = sys.getUserReportFacade().getCorporationsDC(sys.getUserSessionData());
%>
	<LINK REL=StyleSheet HREF="../util/css/estilo.css" TYPE="text/css">
		
<head/>

<script type="text/javascript">
	function showPassword() {
		var value = document.forms['UserCreateDCForm'].elements['password'].selectedIndex;
		if(value == 0) {
			document.getElementById("passwordbox").style.display = 'none';
		}else {
			document.getElementById("passwordbox").style.display = 'block';
		}
	}
</script>
<body>
	<html:form action="/CreateUsersDC">
	<table width="500" border="0" align="center" cellpadding="0" cellspacing="0">
		<tr class="guide1">
			<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("generic.data.results")%>' />
  		</tr>
      	<tr>
			<jsp:include  page="../component/spaceline.jsp" />
  		</tr>
  		<tr>
    		<td width="500" valign="top" colspan="2">
				<jsp:include  page='<%="../component/utilityboxtop.jsp?title="+messages.getText("user.corporations")%>' />
				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
					<tr>
						<td class="guide2" width="5%">&nbsp;</td>
		                <td class="guide2" width="25%" align="left"><%=messages.getText("user.corporation.name")%></td>
               			<td class="guide2" width="25%" align="left"><%=messages.getText("user.corporation.drivers")%></td>
					</tr>
<%		Iterator corporations = result.iterator();
		while(corporations.hasNext()){
		    String[] data = (String[])corporations.next();
%>					<tr align=center class="lineone">
	              		<td width="5%" align="center">
							<html:radio property="corporation" value='<%=String.valueOf(data[0])%>' ></html:radio>                  
						</td>
						<td width="25%" align="left"><%=String.valueOf(data[1])%></td>
  			            <td width="25%" align="left"><%=String.valueOf(data[2])%></td>
	                </tr>
<%			}		
%>					<tr class="space"><td>&nbsp;</td></tr>
				</table>
				<jsp:include  page="../component/utilityboxbottom.jsp" />
			</td>
		</tr>
   		<tr  class="space"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
		<tr>
			<td align="right" colspan="2">
				<jsp:include  page='<%="../component/utilityboxtop.jsp?title="+messages.getText("user.corporation.otherdata")%>' />
   					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="default">
						<tr>
							<th align="left">
								<span class="input"><%=messages.getText("generic.assesment")%></span>
							</th>
							<td align="right">
								<html:select property="assesment" styleClass="input">
									<html:options collection="assesments" property="value" labelProperty="label" styleClass="input"/>
								</html:select>   
							</td>
						</tr>
				   		<tr  class="space"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
						<tr>
							<th align="left">
								<span class="input"><%=messages.getText("user.data.pass")%></span>
							</th>
							<td align="right">
								<html:select property="password" styleClass="input" onchange="javascript:showPassword()">
									<html:options collection="passwords" property="value" labelProperty="label" styleClass="input"/>
								</html:select>   
							</td>
						</tr>
				   		<tr  class="space"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
						<tr>
							<td align="right" colspan="2">
								<div id="passwordbox" style="display: none;">
									<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("user.corporation.password")%>' />
				   					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="default">
					                 	<tr class="lineone">
				  								<th align="left"><%=messages.getText("user.data.pass")%></th>
				  			  	        		<td align="right">
				    					     		<html:password property="passwordtext"	size="25" styleClass="input"/>
				  		 		        		</td>
				    					</tr>
								      	<tr  class="lineone">
								        	<th align="right">&nbsp;</th>
								        	<td align="right">&nbsp;</td>
								      	</tr>
				    					<tr class="lineone">
				 				       		<th align="left"><%=messages.getText("user.data.confirmpassword")%></th>
								        	<td align="right">
								             	<html:password property="rePasswordtext" size="25" styleClass="input"/>
							            	</td>
				    					</tr>
				    				</table>
									<jsp:include  page="../component/utilitybox2bottom.jsp" />
								</div>
							</td>
						</tr>
				   		<tr  class="space"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
						<tr>
							<td align="left" colspan="2">
								<html:checkbox property="generatefile" /><%=messages.getText("user.corporation.generatefile")%>
							</td>
						</tr>
					</table>
				<jsp:include  page="../component/utilityboxbottom.jsp" />
			</td>
		</tr>
   		<tr  class="space"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
		<tr>
			<td align="right" colspan="2">
				<html:submit value='<%=messages.getText("generic.messages.save")%>' styleClass="input" />
				<html:cancel value='<%=messages.getText("generic.messages.cancel")%>' styleClass="input" />
			</td>
		</tr>
    </table>
	</html:form>
</body>
<%
	}
%>
</html>
