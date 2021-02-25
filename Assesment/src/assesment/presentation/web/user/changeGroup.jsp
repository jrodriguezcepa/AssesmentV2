<%@page language="java"
	errorPage="../exception.jsp"
	import="assesment.business.*"
	import="assesment.business.administration.user.*" 
	import="assesment.business.administration.corporation.*" 
	import="assesment.communication.language.*"
	import="assesment.communication.security.*"
	import="assesment.communication.assesment.*"
	import="assesment.communication.administration.group.tables.*"
	import="assesment.presentation.translator.web.user.*"
	import="assesment.presentation.translator.web.util.*"
	import="assesment.communication.user.*"
	import="assesment.communication.util.*"
	import="java.util.*"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>

<html:html>
<%! Text messages;
	AssesmentAccess sys;
%>
<%
	sys=(AssesmentAccess)session.getAttribute("AssesmentAccess");
	String check = Util.checkPermission(sys,SecurityConstants.ADMINISTRATOR);
	if(check!=null) {
		response.sendRedirect(request.getContextPath()+check);
	}else {

		RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
		dispatcher.include(request,response);

	    String user = request.getParameter("user");
		if(Util.empty(user)){
			user = (String)session.getAttribute("user");
			session.removeAttribute("user");
		}
		UserData data = sys.getUserReportFacade().findUserByPrimaryKey(user,sys.getUserSessionData());
		GroupData group = sys.getUserReportFacade().findUserGroup(user,sys.getUserSessionData());
		session.setAttribute("url","user/changeGroup.jsp?user="+user);

		if(!Util.empty(user) && group != null){
			messages=sys.getText();

			Iterator itGroups = sys.getUserReportFacade().findGroups(sys.getUserSessionData()).iterator();
			Collection groupList = new LinkedList();
			groupList.add(new OptionItem(messages.getText("user.assesment.notassociated"),""));
			while(itGroups.hasNext()){
		        Object[] d = (Object[])itGroups.next();
		        Integer idG = (Integer)d[0];
		        if(idG.intValue() != group.getId().intValue()) {
			        groupList.add(new OptionItem(String.valueOf(d[1]),String.valueOf(idG)));
		        }
			}
			session.setAttribute("groupList",groupList);
%>
<head>
	<LINK REL=StyleSheet HREF="./util/css/estilo.css" TYPE="text/css">

	<style type="text/css">
	</style>
<head/>

<body bgcolor="#FFFFFF">
	<html:form action="/UserChangeGroup" >
		<html:hidden property="loginname"  value='<%=user%>'/>		
		<html:hidden property="extraData"  value='<%=String.valueOf(group.getId())%>'/>		
		<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("generic.user.data")+" "+user%>' />
  			<tr>
    			<td width="100%" valign="top" colspan="3">
					<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.user.data")%>' />
						<table width='100%' border="0" align='left' cellpadding="5" cellspacing="5">
							<tr class="line">
			   					<td align="right">
									<div align="left"><%=messages.getText("user.data.nickname")%></div>
								</td>
			    			  	<td align="left">
			     					<div align="right"><%=data.getLoginName()%></div>
			    		 		</td>
			  	         	</tr>
			 				<tr class="line">
								<td align="right">
									<div align="left"><%=messages.getText("user.data.firstname")%></div>
								</td>
			    			  	<td align="left">
			     					<div align="right"><%=data.getFirstName()+" "+data.getLastName()%></div>
			    		 		</td>
							</tr>
			 				<tr class="line">
								<td align="right">
									<div align="left"><%=messages.getText("user.data.currentgroup")%></div>
								</td>
			    			  	<td align="left">
			     					<div align="right"><%=group.getName()%></div>
			    		 		</td>
							</tr>
			      			<tr  class="line">
		        				<td align="left"> 
		        					<%=messages.getText("generic.group")%>
		        				</td>
		        				<td align="right">
									<html:select property="group" styleClass="input" style="width:300px" >
										<html:options collection="groupList" property="value" labelProperty="label"/>
									</html:select>
								</td>
							</tr>
						</table>
					<jsp:include  page="../component/utilitybox2bottom.jsp" />
				</td>
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
<%		}else{
			dispatcher=request.getRequestDispatcher("layout.jsp?refer=/user/list.jsp");
			dispatcher.forward(request,response);
		}	
	}
%>
</html:html>

