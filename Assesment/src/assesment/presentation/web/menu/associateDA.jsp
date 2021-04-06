<%@page import="assesment.business.assesment.AssesmentReportFacade"%>
<%@page import="assesment.business.administration.user.UsReportFacade"%>
<%@page import="java.util.Date"%>
<%@page import="assesment.presentation.translator.web.util.Util"%>
<%@page import="java.util.HashMap"%>
<%@page import="assesment.communication.assesment.CategoryData"%>
<%@page import="assesment.communication.assesment.GroupData"%>
<%@page language="java"
	import="assesment.business.AssesmentAccess"
%>


<%@page import="assesment.communication.report.AssessmentReportData"%>


<%@page import="assesment.communication.assesment.AssesmentData"%>
<%@page import="assesment.communication.language.Text"%>


<%@page import="assesment.communication.user.UserData"%>
<%@page import="assesment.communication.administration.user.UserSessionData"%>
<%@page import="java.util.Iterator"%>
<%@page import="assesment.communication.report.UserReportData"%>
<%@page import="assesment.communication.module.ModuleData"%>
<%@page import="assesment.communication.report.ModuleReportData"%>
<%@page import="assesment.communication.report.QuestionReportData"%>
<%@page import="assesment.communication.security.SecurityConstants"%>
<%@page import="assesment.communication.assesment.AssesmentAttributes"%>
<%@page import="assesment.communication.question.QuestionData"%>

<%@page import="java.util.Collection"%>

<%@page import="java.util.LinkedList"%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>


<html:html>

<%
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	Text messages = sys.getText();
	UserSessionData userSessionData = sys.getUserSessionData();
	String role = userSessionData.getRole();	
	String value = "";
	String firstName = "";
	String lastName = "";

	if(request.getParameter("value")!=null){
		value = request.getParameter("value");
	}	
	if(request.getParameter("firstName")!=null){
		firstName = request.getParameter("firstName");
	}	
	if(request.getParameter("lastName")!=null){
		lastName = request.getParameter("lastName");
	}	
	int type = Integer.parseInt(request.getParameter("type"));
	String link="associateDA.jsp?type="+type;
	if(role.equals(SecurityConstants.ADMINISTRATOR) || role.equals(SecurityConstants.CLIENTGROUP_REPORTER)) {
		UsReportFacade usReport = sys.getUserReportFacade();
		UserData userData = usReport.findUserByPrimaryKey(userSessionData.getFilter().getLoginName(),userSessionData);
		Integer[] cedis = sys.getCorporationReportFacade().findCediUser(userData.getLoginName(), userSessionData);
		//Collection<UserData> users = usReport.findCediMissingUsers(cedis, type, sys.getUserSessionData());
		Collection<UserData> users = usReport.findCediMissingUsers(value, firstName, lastName, cedis, type, sys.getUserSessionData());

		String da = (type == 1) ? "Driver Assessment" : "eBTW";

%>
		<head>
		<meta charset="iso-8859-1">
		<meta http-equiv="X-UA-Compatible" content="IE=9; IE=EDGE" />
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<meta name="viewport" content="user-scalable=no, width=980" />
		<meta name="description" content="">
		<meta name="keywords" content="">
		<title>CEPA Driver Assessment</title>
		<link rel="shortcut icon" type="image/ico" href="images/favicon.ico">
		<!--[if lt IE 9]>
			<script type="text/javascript" src="scripts/html5shiv.min.js"></script>
		<![endif]-->
	    <!-- script type="text/javascript" src="https://www.google.com/jsapi"></script -->
		<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
      	<script type="text/javascript">
      	function doAsociate(type,msg) {
      		var form = document.forms['CediAsociateAssessmentForm'];
      		var elements=form.elements;
      		var list="";
      		for(i=0;i<elements.length;i++){
      			var element=elements[i];
      			if(element.type.toLowerCase()=="checkbox"){
      				if(element.checked){
      					if(list==""){
      						list=element.value;
      					} else {
      						list=element.value+"<"+list;
      					}	
      				}	
      			} 
      		}
      		if(list.length>0){
          		form.type.value=type;
          		form.list.value=list;
      			if(confirm(msg)){
      				form.submit();
      			}
      		}
      		
      	}
		</script>
		<style type="text/css">
			body {
				background-color: #FFFFFF;
				margin-left: 10px;
				margin-top: 10px;
				margin-right: 10px;
				margin-bottom: 0px;
			}
			.title {
				margin-left: 10px;
				margin-top: 20px;
				margin-bottom: 20px;
				color: #1D272D;
				font-family: Roboto, Helvetica, Arial, sans-serif;
				font-size: 2.5em;
			}
			.subtitle {
				margin-left: 10px;
				margin-top: 20px;
				margin-bottom: 20px;
				color: #1D272D;
				font-family: Roboto, Helvetica, Arial, sans-serif;
				font-size: 1.5em;
			}
			.tabla {
				margin-left: 10px;
				color: #1D272D;
				font-family: Roboto, Helvetica, Arial, sans-serif;
				font-size: 11;
				text-align: center;
				width:98%;
				padding: 0;
				border-spacing: 0;
			}
			.cell {
				min-width: 130px;
				border-color: #1D272D;
				border-width: 1px;
				border-style: solid;
				text-align: center;
				height: 30px;
			}
			.cellText {
				color: #1D272D;
				font-family: Roboto, Helvetica, Arial, sans-serif;
				font-size: 11;
				text-align: center;
			}
			.searchText {
				color: #1D272D;
				font-family: Roboto, Helvetica, Arial, sans-serif;
				font-size: 13;
				text-align: left;
			}
			.cell1 {
				background-color: white;
				border-color: #1D272D;
				border-width: 1px;
				border-style: solid;
				text-align: center;
				height: 30px;
			}
			.cellData {
				border-color: #1D272D;
				border-width: 1px;
				border-style: solid;
				text-align: left;
				padding: 5px;
				height: 30px;
			}
			.cellData1 {
				background-color: #DCDCDC;
				border-color: black;
				border-width: 1px;
				border-style: solid;
				text-align: left;
				padding: 5px;
				height: 30px;
			}
			.cellRed {
				background-color: red;
				color:white;
				border-color: #1D272D;
				border-width: 1px;
				border-style: solid;
				text-align: center;
				height: 30px;
				vertical-align: middle;
			}
			.cellYellow {
				background-color: yellow;
				border-color: #1D272D;
				border-width: 1px;
				border-style: solid;
				text-align: center;
				height: 30px;
			}
			.cellGreen {
				background-color: green;
				color:white;
				border-color: #1D272D;
				border-width: 1px;
				border-style: solid;
				text-align: center;
				height: 30px;
			}
		</style>
	</head>
	<body>
		<form name="back" action="reportgrupomodelo.jsp">
		</form>

			<div>
				<img src="./imgs/logocepa.jpg">
			</div>
			<br>
			<br>
			<div>
				<table width="100%">
					<tr>
						<td class="title" width="50%" align="left">
							Grupo Modelo - Asociar <%=da%>
						</td>
					</tr>
				</table>
			</div>
			<br>
			<div>
				 <tr>
	    		<td width="100%" valign="top">
		    		<table width="100%" border="0" cellpadding="1" cellspacing="1">
		    			<tr>
		    				<div class="searchText" style="font-weight:600;"><%= messages.getText("generic.messages.search")%></div>
		    			
		    			</tr>
		    			
						<tr>
							<td align="left" class="searchText">
								<form action="<%=link%>" method="post">
									<span style="width:30px">Nombre</span>
									<input type="text" name="firstName" style="width: 300px;"  class="input" value='<%=firstName%>'/>
									<span style="width:30px">Apellido</span>
									<input type="text" name="lastName" style="width: 300px;"  class="input" value='<%=lastName%>'/>
									<span style="width:30px"><%=messages.getText("generic.cedi")%></span>
									<input type="text" name="value" style="width: 300px;"  class="input" value='<%=value%>'/>
									<input name="button" type="submit" value='<%=messages.getText("generic.messages.search")%>' class="input"/>
								</form>
							</td>
						</tr>
					</table>
					<jsp:include  page="component/utilitybox2bottom.jsp" />
				</td>
    		</tr>
    		<html:form action="/CediAsociateAssessment">
			<html:hidden property="type" />
			<html:hidden property="list" value="<%=String.valueOf(type)%>" />
				<table class="tabla">
					<tr>
						<th></th>
						<th><%=messages.getText("user.data.nickname").toUpperCase()%></th>
						<th><%=messages.getText("user.data.firstname").toUpperCase()%></th>
						<th><%=messages.getText("user.data.lastname").toUpperCase()%></th>
						<th><%=messages.getText("user.data.mail").toUpperCase()%></th>
						<th>CEDI</th>
					</tr>
<%			Iterator<UserData> it = users.iterator();
			boolean line = true;
			while(it.hasNext()) {
				UserData user = it.next();
				String cellName = (line) ? "cellData1" : "cellData";
				line = !line;
%>					<tr>
						<td class="<%=cellName%>"><input type="checkbox" value="<%=user.getLoginName() %>"></td>
						<td class="<%=cellName%>"><%=user.getLoginName() %></td>
						<td class="<%=cellName%>"><%=user.getFirstName() %></td>
						<td class="<%=cellName%>"><%=user.getLastName() %></td>
						<td class="<%=cellName%>"><%=(user.getEmail() == null) ? "---" : user.getEmail() %></td>
						<td class="<%=cellName%>"><%=(user.getExtraData2() == null) ? "---" : user.getExtraData2() %></td>
					</tr>						
<%			}
%>				</table>
			</div>
			<br>
			<div>
<%			if(type == 1) {
%>				<input type="button" class="input" value="Asociar Livianos" onclick="javascript:doAsociate(1,'Confirma que desea asociar estos usuarios al Driver Assessment de Livianos?');" />
				<input type="button" class="input" value="Asociar Pesados" onclick="javascript:doAsociate(2,'Confirma que desea asociar estos usuarios al Driver Assessment de Pesados?');" />
<%			}else {
%>				<input type="button" class="input" value="Asociar eBTW" onclick="javascript:doAsociate(3,'Confirma que desea asociar estos usuarios al eBTW?');" />
<%			}
%>				<input type="button" class="input" value="Cancelar" onclick="javascript:document.forms['back'].submit();" />
			</div>
		</html:form>
	</body>
<%	}
%>
	
</html:html>