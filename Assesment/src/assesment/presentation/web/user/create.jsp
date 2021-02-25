<%@page language="java"
	errorPage="../exception.jsp"
	import="java.util.*"
	import="assesment.business.*"
	import="assesment.communication.language.*"
	import="assesment.communication.security.*"
	import="assesment.communication.language.tables.*"
	import="assesment.communication.administration.user.tables.*"
	import="assesment.communication.assesment.*"
	import="assesment.presentation.translator.web.util.*"
	import="assesment.presentation.actions.user.*"
	import="assesment.communication.user.*"
	import="assesment.communication.util.*"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html" 
%>

<%@ taglib uri="/WEB-INF/struts-bean.tld"
        prefix="bean" 
%>

<html:html>

<%! 
	Text messages; 
	String user;
%>
<%
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	String check = Util.checkPermission(sys,SecurityConstants.ADMINISTRATOR);
	if(check!=null) {
		response.sendRedirect(request.getContextPath()+check);
	}else {
		Text messages = sys.getText();
		session.setAttribute("url","user/create.jsp");

		String type = request.getParameter("type");
		
		RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
		dispatcher.include(request,response);
		
		Collection roleList = new LinkedList();
		roleList.add(new OptionItem(messages.getText("role."+SecurityConstants.ACCESS_TO_SYSTEM+".name"),SecurityConstants.ACCESS_TO_SYSTEM));
		roleList.add(new OptionItem(messages.getText("role."+SecurityConstants.MULTI_ASSESSMENT+".name"),SecurityConstants.MULTI_ASSESSMENT));
		roleList.add(new OptionItem(messages.getText("role."+SecurityConstants.GROUP_ASSESSMENT+".name"),SecurityConstants.GROUP_ASSESSMENT));
		roleList.add(new OptionItem(messages.getText("role."+SecurityConstants.CLIENT_REPORTER+".name"),SecurityConstants.CLIENT_REPORTER));
		roleList.add(new OptionItem(messages.getText("role."+SecurityConstants.CLIENTGROUP_REPORTER+".name"),SecurityConstants.CLIENTGROUP_REPORTER));
		roleList.add(new OptionItem(messages.getText("role."+SecurityConstants.CEPA_REPORTER+".name"),SecurityConstants.CEPA_REPORTER));
		roleList.add(new OptionItem(messages.getText("role."+SecurityConstants.ADMINISTRATOR+".name"),SecurityConstants.ADMINISTRATOR));
		session.setAttribute("roleList",roleList);

		Collection languageList=new LinkedList();
		languageList.add(new OptionItem(messages.getText("es"),"es"));
		languageList.add(new OptionItem(messages.getText("en"),"en"));
		languageList.add(new OptionItem(messages.getText("pt"),"pt"));
		session.setAttribute("languageOptionList",languageList);
		
		String action = "/UserCreate";
		UserCreateForm form = null;
		if(type.equals("edit")) {
		    user = request.getParameter("user");
		    if(session.getAttribute("UserUpdateForm") == null || (user != null && !((UserCreateForm)session.getAttribute("UserUpdateForm")).getLoginname().equals(user))) {
			    form = new UserCreateForm(sys.getUserReportFacade().findUserByPrimaryKey(user,sys.getUserSessionData()));
			    session.setAttribute("UserUpdateForm",form);
		    }
		    action = "/UserUpdate";
		}else {
		    if(session.getAttribute("UserCreateForm") != null) {
		        form = (UserCreateForm)session.getAttribute("UserCreateForm");
		    }
			Iterator itAssesments = sys.getUserReportFacade().findActiveAssesments("",sys.getUserSessionData()).iterator();
			Collection assesmentList = new LinkedList();
			assesmentList.add(new OptionItem(messages.getText("user.assesment.notassociated"),""));
			while(itAssesments.hasNext()){
		        Object[] data = (Object[])itAssesments.next();
				assesmentList.add(new OptionItem(messages.getText(String.valueOf(data[1])).trim(), String.valueOf(data[0])));
			}
			Collections.sort((List)assesmentList);
			session.setAttribute("assesmentList",assesmentList);

			Iterator itGroups = sys.getUserReportFacade().findGroups(sys.getUserSessionData()).iterator();
			Collection groupList = new LinkedList();
			groupList.add(new OptionItem(messages.getText("user.assesment.notassociated"),""));
			while(itGroups.hasNext()){
		        Object[] data = (Object[])itGroups.next();
		        groupList.add(new OptionItem(String.valueOf(data[1]),String.valueOf(data[0])));
			}
			session.setAttribute("groupList",groupList);
		}
		
		Collection sexList = new LinkedList();
		sexList.add(new OptionItem(messages.getText("user.sex.female"),String.valueOf(UserData.FEMALE)));
		sexList.add(new OptionItem(messages.getText("user.sex.male"),String.valueOf(UserData.MALE)));
		session.setAttribute("sexList",sexList);

		Collection userExpiryList = new LinkedList();
		userExpiryList.add(new OptionItem(messages.getText("user.expirytype.permanent"),String.valueOf(UserData.PERMANENT)));
		userExpiryList.add(new OptionItem(messages.getText("user.expirytype.withexpiry"),String.valueOf(UserData.WITH_EXPIRY)));
		session.setAttribute("userExpiryList",userExpiryList);

		Collection locationList = new LinkedList();
		locationList.add(new OptionItem(messages.getText("user.location.acomp"),String.valueOf(UserData.ACOM)));
		locationList.add(new OptionItem(messages.getText("user.location.general"),String.valueOf(UserData.GENERAL)));
		locationList.add(new OptionItem(messages.getText("user.location.nonsales"),String.valueOf(UserData.NON_SALES)));
		locationList.add(new OptionItem(messages.getText("user.location.plant"),String.valueOf(UserData.PLANT)));
		locationList.add(new OptionItem(messages.getText("user.location.sales"),String.valueOf(UserData.SALES)));
		session.setAttribute("locationList",locationList);

		CountryConstants cc = new CountryConstants(messages);
        Collection list = new LinkedList();
        list.add(new OptionItem(messages.getText("generic.messages.select"),""));
        Iterator it = cc.getCountryIterator();
        while(it.hasNext()) {
            CountryData data = (CountryData)it.next();
            list.add(new OptionItem(data.getName(),data.getId()));
        }
		session.setAttribute("countryList",list);
		
		Calendar today = Calendar.getInstance();

%>

<LINK REL=StyleSheet HREF="../util/css/estilo.css" TYPE="text/css">

<head/>

	<script language="javascript">
		function createWithPassword() {
			document.forms['UserCreateForm'].elements['type'].value = 'password';
			document.forms['UserCreateForm'].submit();
		}
		function showUserExpiry() {
			if(document.forms['UserCreateForm'].elements['expiryType'].selectedIndex == 0) {
				document.getElementById('userExpiry').style.display = 'none';
				document.getElementById('userExpiryText').style.display = 'none';
			}else {
				document.getElementById('userExpiry').style.display = 'block';
				document.getElementById('userExpiryText').style.display = 'block';
			}
		}
		function showUserExpiry2() {
			if(document.forms['UserUpdateForm'].elements['expiryType'].selectedIndex == 0) {
				document.getElementById('userExpiry').style.display = 'none';
				document.getElementById('userExpiryText').style.display = 'none';
			}else {
				document.getElementById('userExpiry').style.display = 'block';
				document.getElementById('userExpiryText').style.display = 'block';
			}
		}
		function onSelectBirth(calendar, date) {
			var date2 = calendar.date;
			var time = date2.getTime();
	  		var input_day = document.getElementById("dayBirth");
	  		var input_month = document.getElementById("monthBirth");
	  		var input_year = document.getElementById("yearBirth");
	  		var date3 = new Date(time);
	  		input_day.value = date3.print("%d");
	  		input_month.value = date3.print("%m");
	  		input_year.value= parseInt(date3.print("%Y"),10);
	  	}
		function onSelectStart(calendar, date) {
			var date2 = calendar.date;
			var time = date2.getTime();
	  		var input_day = document.getElementById("dayStart");
	  		var input_month = document.getElementById("monthStart");
	  		var input_year = document.getElementById("yearStart");
	  		var date3 = new Date(time);
	  		input_day.value = date3.print("%d");
	  		input_month.value = date3.print("%m");
	  		input_year.value= parseInt(date3.print("%Y"),10);
	  	}
		function onSelectExpiry(calendar, date) {
			var date2 = calendar.date;
			var time = date2.getTime();
	  		var input_day = document.getElementById("dayExpiry");
	  		var input_month = document.getElementById("monthExpiry");
	  		var input_year = document.getElementById("yearExpiry");
	  		var date3 = new Date(time);
	  		input_day.value = date3.print("%d");
	  		input_month.value = date3.print("%m");
	  		input_year.value= parseInt(date3.print("%Y"),10);
	  	}
		function onSelectUserExpiry(calendar, date) {
			var date2 = calendar.date;
			var time = date2.getTime();
	  		var input_day = document.getElementById("dayUserExpiry");
	  		var input_month = document.getElementById("monthUserExpiry");
	  		var input_year = document.getElementById("yearUserExpiry");
	  		var date3 = new Date(time);
	  		input_day.value = date3.print("%d");
	  		input_month.value = date3.print("%m");
	  		input_year.value= parseInt(date3.print("%Y"),10);
	  	}
		function changeList() {
			var form = document.forms['UserCreateForm'];
			var index = form.elements['role'].selectedIndex;
			if(index == 0 || index == 3) {
				document.getElementById('assessmentSelect').style.display = '';
				document.getElementById('assessmentListSelect').style.display = 'none';
				document.getElementById('groupSelect').style.display = 'none';
				document.getElementById('naSelect').style.display = 'none';
			} else if(index == 1) {
				document.getElementById('assessmentSelect').style.display = 'none';
				document.getElementById('assessmentListSelect').style.display = '';
				document.getElementById('groupSelect').style.display = 'none';
				document.getElementById('naSelect').style.display = 'none';
			} else if(index == 2 || index == 4) {
				document.getElementById('assessmentSelect').style.display = 'none';
				document.getElementById('assessmentListSelect').style.display = 'none';
				document.getElementById('groupSelect').style.display = '';
				document.getElementById('naSelect').style.display = 'none';
			} else {
				document.getElementById('assessmentSelect').style.display = 'none';
				document.getElementById('assessmentListSelect').style.display = 'none';
				document.getElementById('groupSelect').style.display = 'none';
				document.getElementById('naSelect').style.display = '';
			}
		}
	</script>

	<body>
		<html:form action='<%=action%>' focus="loginname">
			<html:hidden property="type" value="normal" />
			<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("generic.user.new")%>' />
			  	<tr>
		    		<td valign="top">
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.user.data")%>' />
						<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<%		if(type.equals("edit")) {
%>							<html:hidden property="loginname" value='<%=user%>'/>
					      	<tr  class="line">
					        	<td align="left"> <%=messages.getText("user.data.nickname")%></td>
        						<td align="right">
				            		<%=user%>
					          	</td>
					      	</tr>
<%		}else {
%>					      	<tr  class="line">
					        	<td align="left"> <%=messages.getText("user.data.nickname")%><span class="required">*</span></td>
        						<td align="right">
				            		<html:text property="loginname" style="width:300px;" styleClass="input"/>
					          	</td>
					      	</tr>
      						<tr  class="line">
        						<td align="left"><%=messages.getText("user.data.pass")%><span class="required">*</span></td>
        						<td align="right">
           							<html:password property="password" style="width:300px;" styleClass="input"/>
          						</td>
      						</tr>
					      	<tr  class="space"><th align="right"></th><td align="right"></td></tr>
      						<tr  class="line">
        						<td align="left"> <%=messages.getText("user.data.confirmpassword")%><span class="required">*</span></td>
        						<td align="right">
           							<html:password property="rePassword" style="width:300px;" styleClass="input"/>
          						</td>
      						</tr>
<%		}
%>      					<tr  class="line">
        						<td align="left" > <%=messages.getText("user.data.firstname")%><span class="required">*</span></t>
        						<td align="right">
           							<html:text property="firstName" style="width:300px;" styleClass="input"/>
          						</td>
      						</tr>
      						<tr  class="line">
        						<td align="left"> <%=messages.getText("user.data.lastname")%><span class="required">*</span></td>
        						<td align="right">
           							<html:text property="lastName" style="width:300px;"  styleClass="input"/>
          						</td>
      						</tr>
							<html:hidden property="birthDay" value="<%=String.valueOf(today.get(Calendar.DATE))%>" />
							<html:hidden property="birthMonth" value="<%=String.valueOf(today.get(Calendar.MONTH)+1)%>" />
							<html:hidden property="birthYear" value="<%=String.valueOf(today.get(Calendar.YEAR)-30)%>" />
							<html:hidden property="sex" value="<%=String.valueOf(UserData.MALE)%>" />
      						<tr  class="line">
        						<td align="left"> <%=messages.getText("user.data.mail")%></td>
        						<td align="right">
           							<html:text property="email" style="width:300;" styleClass="input"/>
          						</td>
      						</tr>
      						<tr  class="line">
        						<td align="left" > <%=messages.getText("user.data.country")%><span class="required">*</span></td>
        						<td align="right">
									<html:select property="country" styleClass="input" style="width:300;">
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
							<html:hidden property="startDay" value="<%=String.valueOf(today.get(Calendar.DATE))%>" />
							<html:hidden property="startMonth" value="<%=String.valueOf(today.get(Calendar.MONTH)-1)%>" />
							<html:hidden property="startYear" value="<%=String.valueOf(today.get(Calendar.YEAR)-30)%>" />
							<html:hidden property="expiryDay"/>
          					<html:hidden property="expiryMonth"/>
          					<html:hidden property="expiryYear"/>
			   				<html:hidden property="vehicle" />              
							<html:hidden property="locationList" value="<%=String.valueOf(UserData.GENERAL)%>"/>
							<html:hidden property="expiryType" value="<%=String.valueOf(UserData.PERMANENT)%>"/>
			      			<tr  class="line">
        						<tD align="left" >
				            		<%=messages.getText("user.data.fdmid")%>
				            	</tD>
        						<td align="right">
									<html:text property="fdm" styleClass="input" style="width:300px;"/>		                		
								</td>
			  				</tr>
			      			<tr  class="line">
								<td align="left"><%=messages.getText("user.data.extradata")%></td>
        						<td align="right">
									<html:text property="extraData" styleClass="input" style="width:300px;"/>		                		
								</td>
			  				</tr>
			      			<tr  class="line">
								<td align="left"><%=messages.getText("user.data.extradata")+" 2"%></td>
        						<td align="right">
									<html:text property="extraData2" styleClass="input" style="width:300px;"/>		                		
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
									<html:select property="role" styleClass="input" style="width:300;" onchange="changeList()">
										<html:options collection="roleList" property="value" labelProperty="label"/>
									</html:select>
			      				</td>
							</tr>
<%			if(!type.equals("edit")) {
%>							<tr class="line">
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
	        						<div id="naSelect" style="display:none;">
									</div>
								</td>
							</tr>
<%			}
%>						</table>
						<jsp:include  page="../component/utilitybox2bottom.jsp" />
	   				</td>
				</tr>
				<tr class="line">
					<td align="right" colspan="2">
<%		if(!type.equals("edit")) {
%>     					<input type="button" class="button" onclick="createWithPassword()" value='<%=messages.getText("user.create.with.password")%>' />
<%		}
%>						<html:submit styleClass="button">
							<%=messages.getText("generic.messages.save")%>
						</html:submit>
			            <html:cancel styleClass="button">
							<%=messages.getText("generic.messages.cancel")%>
						</html:cancel>
					</td>
   				</tr>
			<jsp:include  page='../component/titleend.jsp' />
		</html:form>
	</body>
<%	}	%>
</html:html>
