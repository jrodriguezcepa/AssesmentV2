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
		UpdateUserCodeForm form = null;
		if(type.equals("edit")) {
		    user = request.getParameter("user");
		    if(user==null){
		    	user=(String) session.getAttribute("user");
		    }

		    //if(session.getAttribute("UpdateUserCodeForm") == null || (user != null && !((UpdateUserCodeForm)session.getAttribute("UpdateUserCodeForm")).getLoginname().equals(user))) {
		    	form = new UpdateUserCodeForm(sys.getUserReportFacade().findUserByPrimaryKey(user,sys.getUserSessionData()));
			    session.setAttribute("UpdateUserCodeForm",form);

		    //}

		    action = "/UserUpdate";
		}else {

		    if(session.getAttribute("UpdateUserCodeForm") != null) {
		        form = (UpdateUserCodeForm)session.getAttribute("UpdateUserCodeForm");
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


	</script>

	<body>
		<html:form action="/UpdateUserCode" focus="loginname">
			<html:hidden property="type" value="normal" />
			<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("generic.user.new")%>' />
			  	<tr>
		    		<td valign="top">
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.user.data")%>' />
						<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						<html:hidden property="loginname" value='<%=user%>'/>
  					<tr  class="line">
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
							<tr  class="line">
        						<td align="left"> <%=messages.getText("user.data.mail")%></td>
        						<td align="right">
           							<html:text property="email" style="width:300;" styleClass="input"/>
          						</td>
      						</tr>
>
			      			<tr  class="line">
								<td align="left"><%=messages.getText("user.data.extradata")+" 3"%></td>
        						<td align="right">
									<html:text property="extraData3" styleClass="input" style="width:300px;"/>		                		
								</td>
			  				</tr>
			  			</table>
						<jsp:include  page="../component/utilitybox2bottom.jsp" />
				    </td>
				</tr>
				<tr>

				</tr>
				<tr class="line">
					<td align="right" colspan="2">
					<html:submit styleClass="button">
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
