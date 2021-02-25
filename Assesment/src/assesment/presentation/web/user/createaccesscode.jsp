<%@page language="java" 
	errorPage="../exception.jsp"
	import="java.util.*"
	import="assesment.business.*"
	import="assesment.communication.language.*"
	import="assesment.communication.language.tables.*"
	import="assesment.communication.security.*"
	import="assesment.communication.corporation.*"
	import="assesment.communication.util.*"
	import="assesment.communication.user.*"
	import="assesment.presentation.translator.web.util.*"
	import="assesment.communication.administration.*"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>

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
		session.setAttribute("url","report/createaccesscode.jsp");
	
		if(session.getAttribute("Msg")!=null){
			session.removeAttribute("Msg");
		}
		messages=sys.getText();
	
		boolean edit = request.getParameter("type").equals("edit");
		
		AccessCodeData acData = null;
		String action = null;
		String number = "";
		String extension = "";
		int extensionType = UserData.PERMANENT;
		if(edit) {
		    acData = sys.getAssesmentReportFacade().getAccessCode(request.getParameter("code"),sys.getUserSessionData());
		    number = String.valueOf(acData.getNumber());
		    extension = String.valueOf(acData.getExtension());
		    extensionType = (acData.getExtension().intValue() > 0) ? UserData.WITH_EXPIRY : UserData.PERMANENT;
		    action = "/EditAccessCode";
		}else {
			ListResult assesmentList = sys.getAssesmentReportFacade().findAssesments("","","0",sys.getUserSessionData());
			Iterator it = assesmentList.getElements().iterator();
			Collection assesments = new LinkedList();
			assesments.add(new OptionItem(messages.getText("generic.messages.select"),""));
			while(it.hasNext()) {
		        Object[] data = (Object[])it.next();
			    assesments.add(new OptionItem(messages.getText((String)data[1]).trim(),String.valueOf(data[0])));
			}
			Collections.sort((List)assesments);
			session.setAttribute("assesments",assesments);
			if(session.getAttribute("AccessCodeCreateForm") != null) {
			    extensionType = Integer.parseInt(((org.apache.struts.action.DynaActionForm)session.getAttribute("AccessCodeCreateForm")).getString("type"));
			}
		    action = "/CreateAccessCode";
		}
		
		Collection userExpiryList = new LinkedList();
		userExpiryList.add(new OptionItem(messages.getText("user.expirytype.permanent"),String.valueOf(UserData.PERMANENT)));
		userExpiryList.add(new OptionItem(messages.getText("user.expirytype.withexpiry"),String.valueOf(UserData.WITH_EXPIRY)));
		session.setAttribute("userExpiryList",userExpiryList);

		Collection languageList=new LinkedList();
		languageList.add(new OptionItem(messages.getText("es"),"es"));
		languageList.add(new OptionItem(messages.getText("en"),"en"));
		languageList.add(new OptionItem(messages.getText("pt"),"pt"));
		session.setAttribute("languageOptionList",languageList);
%>
	<LINK REL=StyleSheet HREF="../util/css/estilo.css" TYPE="text/css">

	<script language="javascript">
		function showUserExpiry() {
			if(document.forms['UserAccessCodeForm'].elements['type'].selectedIndex == 0) {
				document.getElementById('userExpiry').style.display = 'none';
				document.getElementById('userExpiryText').style.display = 'none';
			}else {
				document.getElementById('userExpiry').style.display = 'block';
				document.getElementById('userExpiryText').style.display = 'block';
			}
		}
		function showUserExpiry2() {
			if(document.forms['UserUpdateAccessCodeForm'].elements['type'].selectedIndex == 0) {
				document.getElementById('userExpiry').style.display = 'none';
				document.getElementById('userExpiryText').style.display = 'none';
			}else {
				document.getElementById('userExpiry').style.display = 'block';
				document.getElementById('userExpiryText').style.display = 'block';
			}
		}
	</script>
		
<head/>
	<body>
		<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("user.accesscode.title")%>' />
		<html:form action='<%=action%>'>
  			<tr>
    			<td width="100%" valign="top" colspan="2">
					<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("user.accesscode.data")%>' />
   					<table width="100%" border="0" align="center" cellpadding="2" cellspacing="2">
						<tr class="line">
							<td align="left">
								<%=messages.getText("generic.assesment")%>
							</td>
							<td align="right">
<%		if(edit) {					 
%>								<%=acData.getAssesmentName()%>		
<%		}else {
%>								<html:select property="assesment" styleClass="input" style="width:300px;">
									<html:options collection="assesments" property="value" labelProperty="label" styleClass="input"/>
								</html:select>   
<%		}
%>							</td>
						</tr>
						<tr class="line">
							<td align="left">
								<%=messages.getText("user.accesscode.code")%>
							</td>
							<td align="right">
<%		if(edit) {					 
%>								<%=acData.getCode()%>		
								<html:hidden property="accesscode" value='<%=acData.getCode()%>' />
<%		}else {
%>								<html:text property="accesscode" style="width:300px;" styleClass="input"/>
<%		}
%>							</td>
						</tr>
      					<tr  class="line">
        					<td align="left" > <%=messages.getText("user.data.language")%><span class="required">*</span></td>
        					<td align="right">
								<html:select property="language" styleClass="input"  style="width:300px;" value='<%=(acData == null) ? "" : acData.getLanguage()%>'>
									<html:options collection="languageOptionList" property="value" labelProperty="label"/>
								</html:select>
      						</td>
						</tr>
						<tr class="line">
							<td align="left">
								<%=messages.getText("user.accesscode.number")%>
							</td>
							<td align="right">
								<html:text property="number"  style="width:300px;" styleClass="input" value='<%=number%>'/>
							</td>
						</tr>
   						<tr  class="line">
       						<td align="left" > <%=messages.getText("accesscode.data.type")%></td>
       						<td align="right">
								<html:select property="type" styleClass="input"  style="width:300px;" onchange='<%=(!edit) ? "showUserExpiry()" : "showUserExpiry2()" %>' value='<%=String.valueOf(extensionType)%>'>
									<html:options collection="userExpiryList" property="value" labelProperty="label"/>
								</html:select>
   							</td>
						</tr>
   						<tr  class="line">
       						<td align="left"> 
								<div id="userExpiryText" style='<%=(extensionType == UserData.WITH_EXPIRY) ? "display: block;" : "display: none;"%>'>
        							<%=messages.getText("accesscode.data.extension")%>
        						</div>
       						</td>
       						<td align="right" >
								<div id="userExpiry" style='<%=(extensionType == UserData.WITH_EXPIRY) ? "display: block;" : "display: none;"%>'>
									<html:text property="extension"  style="width:300px;" styleClass="input" value='<%=extension%>'/>
								</div>
   							</td>
						</tr>
<%		if(edit) {					 
%>				   		<tr class="line">
							<td align="left">
								<%=messages.getText("user.accesscode.used")%>
							</td>
							<td align="right">
								<html:hidden property="used" value='<%=String.valueOf(acData.getUsed())%>' />
								<%=String.valueOf(acData.getUsed())%>
							</td>
						</tr>
<%		}
%>					</table>
					<jsp:include  page="../component/utilitybox2bottom.jsp" />
				</td>
			</tr>
			<tr>
				<td align="right" colspan="2">
					<html:submit value='<%=messages.getText("generic.messages.save")%>' styleClass="button" />
						<html:cancel value='<%=messages.getText("generic.messages.cancel")%>' styleClass="button" />
					</td>
				</tr>
    		</table>
		</html:form>
		<jsp:include  page='../component/titleend.jsp' />
	</body>
<%
	}
%>
