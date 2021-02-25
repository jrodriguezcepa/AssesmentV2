<%@page import="assesment.presentation.actions.user.UserCreateFileForm"%>
<%@page import="assesment.communication.language.tables.LanguageData"%>
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
	} else {
		session.setAttribute("url","user/createfromfile.jsp");
	
		if(session.getAttribute("Msg")!=null){
			session.removeAttribute("Msg");
		}
		messages=sys.getText();
	
		UserCreateFileForm form = new UserCreateFileForm();
		session.setAttribute("UserCreateFileForm", form);
		
		Iterator itAssesments = sys.getUserReportFacade().findActiveAssesments("",sys.getUserSessionData()).iterator();
		Collection assesmentList = new LinkedList();
		while(itAssesments.hasNext()){
	        Object[] data = (Object[])itAssesments.next();
			assesmentList.add(new OptionItem(messages.getText(String.valueOf(data[1])).trim(), String.valueOf(data[0])));
		}
		Collections.sort((List)assesmentList);
		session.setAttribute("assesmentList",assesmentList);

		Iterator itGroups = sys.getUserReportFacade().findGroups(sys.getUserSessionData()).iterator();
		Collection groupList = new LinkedList();
		while(itGroups.hasNext()){
	        Object[] data = (Object[])itGroups.next();
	        groupList.add(new OptionItem(String.valueOf(data[1]),String.valueOf(data[0])));
		}
		session.setAttribute("groupList",groupList);

		CountryConstants cc = new CountryConstants(messages);
        Collection list = new LinkedList();
        list.add(new OptionItem(messages.getText("generic.messages.select"),"0"));
        Iterator it = cc.getCountryIterator();
        while(it.hasNext()) {
            CountryData data = (CountryData)it.next();
            list.add(new OptionItem(data.getName(),data.getId()));
        }
		session.setAttribute("countryList",list);

		Collection languageList=new LinkedList();
		languageList.add(new OptionItem(messages.getText("es"),"es"));
		languageList.add(new OptionItem(messages.getText("en"),"en"));
		languageList.add(new OptionItem(messages.getText("pt"),"pt"));
		session.setAttribute("languageOptionList",languageList);

		Collection roleList = new LinkedList();
		roleList.add(new OptionItem(messages.getText("role."+SecurityConstants.ACCESS_TO_SYSTEM+".name"),SecurityConstants.ACCESS_TO_SYSTEM));
		roleList.add(new OptionItem(messages.getText("role."+SecurityConstants.MULTI_ASSESSMENT+".name"),SecurityConstants.MULTI_ASSESSMENT));
		roleList.add(new OptionItem(messages.getText("role."+SecurityConstants.GROUP_ASSESSMENT+".name"),SecurityConstants.GROUP_ASSESSMENT));
		session.setAttribute("roleList",roleList);

%>
<script type="text/javascript">
	function showDefinitions() {
		var form = document.forms['UserCreateFileForm'];
		var value = form.columnCount.selectedIndex+4;
		for(i = 4; i <= 10; i++) {
			if(i < value) {
				document.getElementById('columnDefinition'+i).style.display = '';
			}else {
				document.getElementById('columnDefinition'+i).style.display = 'none';
			}
		}
	}
	function changeList() {
		var form = document.forms['UserCreateFileForm'];
		var index = form.elements['role'].selectedIndex;
		if(index == 0) {
			document.getElementById('assessmentSelect').style.display = '';
			document.getElementById('assessmentListSelect').style.display = 'none';
			document.getElementById('groupSelect').style.display = 'none';
		} else if(index == 1) {
			document.getElementById('assessmentSelect').style.display = 'none';
			document.getElementById('assessmentListSelect').style.display = '';
			document.getElementById('groupSelect').style.display = 'none';
		} else {
			document.getElementById('assessmentSelect').style.display = 'none';
			document.getElementById('assessmentListSelect').style.display = 'none';
			document.getElementById('groupSelect').style.display = '';
		}
	}
</script>
<body>
	<html:form action="/CreateUsersFile" enctype="multipart/form-data">
		<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("generic.data.results")%>' />
			<tr>
				<td align="right" colspan="2">
					<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("user.createfromfile.users")%>' />
	  				<table width="100%" border="0" align="center" cellpadding="5" cellspacing="5">
						<tr class="line">
							<td align="left">
								<%=messages.getText("user.selectfile")%>
							</td>
							<td align="right">
								<html:file property="file" styleClass="line"/>
							</td>
						</tr>
     					<tr  class="line">
       						<td align="left" > <%=messages.getText("user.data.country")%><span class="required">*</span></td>
       						<td align="right">
								<html:select property="country" styleClass="input" style="width:300px;">
									<html:options collection="countryList" property="value" labelProperty="label"/>
								</html:select>
     						</td>
						</tr>
   						<tr  class="line">
       						<td align="left" > <%=messages.getText("user.data.language")%><span class="required">*</span></td>
       						<td align="right">
								<html:select property="language" styleClass="input" style="width:300px; ">
									<html:options collection="languageOptionList" property="value" labelProperty="label"/>
								</html:select>
   							</td>
						</tr>
   						<tr  class="line">
							<td align="left">
								<%=messages.getText("user.createfromfile.userend")%>
							</td>
							<td align="right">
								<html:text property="extra" styleClass="input" style="width:300px;"/>
							</td>
						</tr>
   						<tr  class="line">
							<td align="left">
								<%=messages.getText("user.createfromfile.userformat")%>
							</td>
							<td align="right">
								<html:select property="userFormat" styleClass="input" style="width:300px;">
									<html:option value="1"><%=messages.getText("user.createfromfile.useremails")%></html:option>
									<html:option value="2"><%=messages.getText("user.createfromfile.username1")%></html:option>
									<html:option value="3"><%=messages.getText("user.createfromfile.username2")%></html:option>
								</html:select>
							</td>
						</tr>
					</table>
					<jsp:include  page="../component/utilitybox2bottom.jsp" />
				</td>
			</tr>
			<tr>
				<td valign="top">
					<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.user.activity")%>' />
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		      			<tr  class="line">
		        			<td align="left" > <%=messages.getText("user.data.role")%><span class="required">*</span></td>
		        			<td align="right">
								<html:select property="role" styleClass="input" style="width:300px;" onchange="changeList()">
									<html:options collection="roleList" property="value" labelProperty="label"/>
								</html:select>
		      				</td>
						</tr>
						<tr class="line">
							<td width="100%" colspan="2">
				        		<div id="assessmentSelect">
									<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						      			<tr  class="line">
					        				<td align="left" > <%=messages.getText("user.data.assesement")%></td>
					        				<td align="right">
												<html:select property="assesment" styleClass="input" style="width:300px;" >
													<html:options collection="assesmentList" property="value" labelProperty="label"/>
												</html:select>
											</td>
										</tr>
									</table>
								</div>
        						<div id="assessmentListSelect" style="display:none;">
									<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						      			<tr  class="line" valign="top">
					        				<td align="left"  valign="top"> <%=messages.getText("user.data.assesement")%></td>
					        				<td align="right">
												<html:select property="assesments" styleClass="input" style="width:500px;" size="10" multiple="true" value="0">
													<html:options collection="assesmentList" property="value" labelProperty="label"/>
												</html:select>
											</td>
										</tr>
									</table>
								</div>
        						<div id="groupSelect" style="display:none;">
									<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						      			<tr  class="line">
					        				<td align="left" > <%=messages.getText("generic.group")%></td>
					        				<td align="right">
												<html:select property="group" styleClass="input" style="width:300px" >
													<html:options collection="groupList" property="value" labelProperty="label"/>
												</html:select>
											</td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
					</table>
					<jsp:include  page="../component/utilitybox2bottom.jsp" />
   				</td>
			</tr>
			<tr>
				<td>
					<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("user.createfromfile.fileformat")%>' />
	  				<table width="100%" border="0" align="center" cellpadding="5" cellspacing="5">
						<tr class="line">
							<td align="left">
								<%=messages.getText("user.createfromfile.columncount")%>
							</td>
							<td align="right">
								<html:select property="columnCount" styleClass="input" onchange="showDefinitions()">
<%				for(int i = 3; i <= 9; i++) {
%>									<html:option value="<%=String.valueOf(i)%>"><%=String.valueOf(i)%></html:option>
<%				}
%>								</html:select>   
							</td>
						</tr>
						<tr class="line">
							<td align="left">
								<%=messages.getText("user.createfromfile.column")+" 1:"%>
							</td>
							<td align="right">
								<%=messages.getText("user.data.firstname")%>
							</td>
						</tr>
						<tr class="line">
							<td align="left">
								<%=messages.getText("user.createfromfile.column")+" 2:"%>
							</td>
							<td align="right">
								<%=messages.getText("user.data.lastname")%>
							</td>
						</tr>
						<tr class="line">
							<td align="left">
								<%=messages.getText("user.createfromfile.column")+" 3:"%>
							</td>
							<td align="right">
								<%=messages.getText("user.data.mail")%>
							</td>
						</tr>
<%		for(int i = 4; i <= 9; i++) { %>						
						<tr id='<%="columnDefinition"+i%>' style="display: none;" class="line">
							<td align="left">
								<%=messages.getText("user.createfromfile.column")+" "+i+":"%>
							</td>
							<td align="right">
								<html:select property='<%="column"+i%>' styleClass="input">
									<html:option value="1"><%=messages.getText("user.data.birthdate")%></html:option>
									<html:option value="2"><%=messages.getText("user.data.sex")%></html:option>
									<html:option value="3"><%=messages.getText("user.data.extradata")+" 1"%></html:option>
									<html:option value="4"><%=messages.getText("user.data.extradata")+" 2"%></html:option>
									<html:option value="5"><%=messages.getText("user.data.extradata")+" 3"%></html:option>
									<html:option value="6">ID FDM</html:option>
								</html:select>
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
		<jsp:include  page='../component/titleend.jsp' />
	</html:form>
</body>
<%
	}
%>
</html>
