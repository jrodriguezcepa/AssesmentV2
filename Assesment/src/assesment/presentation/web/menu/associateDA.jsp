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
			body{
                background-color: rgb(241, 238, 238);
            }
            #rcorners1 {
                margin:auto;
                margin-bottom:1.5%;
                border-radius: 10px;
                background: #f8f8f8;
                padding: 20px;
                padding-bottom: 40px;
                width: 95%;
                height: 15px;
                box-shadow: 0 4px 8px 0 rgba(129, 128, 128, 0.2), 0 6px 10px 0 rgba(121, 120, 120, 0.19);
            }
              #rcorners2 {
                margin:auto;
                border-radius: 10px;
                background: #f8f8f8;
                width: 95%;
                padding:20px;
                padding-top: 20px;
                padding-bottom: 20px;
                box-shadow: 0 4px 8px 0 rgba(129, 128, 128, 0.2), 0 6px 10px 0 rgba(121, 120, 120, 0.19);
            }
            .textInput{
                margin-bottom:20px;
                margin-right:5px;

                width: 25%;
                height: 30px;
                border-radius: 5px;
                background-color: rgb(227, 230, 235);
                border-width: 0px;
                color: red;
                padding:5px;
            }
            .button{
                font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
                font-weight: 500;
                margin-left:30px;
                margin-bottom:20px;
                color: rgb(255, 255, 255);
                background-color: black;
                border-radius: 5px;
                width: 10%;
                height: 38px;
                padding:5px;
                border-width: 0px;
                cursor:pointer;

            }
            .button:hover{
                background-color: rgb(58, 55, 55);

            }
			.title {
				width:100%;
				margin:auto;
				color: #1D272D;
				font-family: Roboto, Helvetica, Arial, sans-serif;
				font-size: 2.5em;
				text-align:center;
			}
			.subtitle {
				margin-left: 10px;
				margin-top: 20px;
				margin-bottom: 20px;
				color: #1D272D;
				font-family: Roboto, Helvetica, Arial, sans-serif;
				font-size: 1.5em;
			}
            .table{
                border-collapse: collapse;
                font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
				font-size: 11;
				table-layout: fixed;
                width: 100%;
                margin: auto;

            }
            .table th{
                background: #f8f8f8;
 				word-wrap: break-word;   
            }
            .table td:first-child {
                border-top-left-radius: 10px;
                border-bottom-left-radius: 10px;

            }
            .table td:last-child {
                border-top-right-radius: 10px;
                border-bottom-right-radius: 10px;

            }
            .table  tr:nth-child(even) {

                background: #CCC
            }
            .table tr:nth-child(even):hover {

                background: rgb(243, 237, 237)
            }
            .table tr:nth-child(odd) {

                background:rgb(227, 230, 235);
             }
            .table tr:nth-child(odd):hover {

                background: rgb(243, 237, 237)
             }
            .table td{

                border-style:none;
                border-width: 0.5px;
                border-collapse:collapse;
                border-left-color: darkkhaki;
                padding-top:15px;
                padding-bottom:15px;
                padding-left:1px;
                padding-right:1px;
                text-align: center;
                white-space: nowrap;
    			height: 30px;
    			overflow:hidden;

             }
			
			div.wrap {
			    width:90%;
			    height:50px;
			    position: relative;
			}
			
			.wrap img {
			     position: absolute;
			}
			
			.wrap img:nth-of-type(1) {
			    left: 0;
			}
			
			.wrap img:nth-of-type(2) {
			    right: 0;
			}		
		
			
		</style>
	</head>
	<body>
		<form name="back" action="reportgrupomodelo.jsp">
		</form>

		<div class="wrap">
			<img src="./images/main_logo_large.png">
			<img width="5%" src="./images/grupo-modelo.png">
		</div>
			<br>
			<br>
			<br>
			
			<div>
		<div class="title">
			Grupo Modelo - Asociar <%=da%>
		</div>
		</br>
		 <div id="rcorners1">
		 	<form action="<%=link%>" method="post">
	            <input name="firstName" class="textInput" type="text" placeholder="Nombre" value='<%=firstName%>'>
	            <input name="lastName" class="textInput" type="text" placeholder="Apellido" value='<%=lastName%>'>
	            <input name="value" class="textInput" type="text" placeholder="CEDI" value='<%=value%>'>
	            <input name="button" class="button"  type="submit" value="BUSCAR">
	         </form> 
        </div>
    		<html:form action="/CediAsociateAssessment">
			<html:hidden property="type" />
			<html:hidden property="list" value="<%=String.valueOf(type)%>" />
			<div id="rcorners2">
				<div>
<%				if(type == 1) {
%>					<input type="button" class="button" value="Asociar Livianos" onclick="javascript:doAsociate(1,'Confirma que desea asociar estos usuarios al Driver Assessment de Livianos?');" />
					<input type="button" class="button" value="Asociar Pesados" onclick="javascript:doAsociate(2,'Confirma que desea asociar estos usuarios al Driver Assessment de Pesados?');" />
<%				}else {
%>					<input type="button" class="button" value="Asociar eBTW" onclick="javascript:doAsociate(3,'Confirma que desea asociar estos usuarios al eBTW?');" />
<%				}
%>					<input type="button" class="button" value="Cancelar" onclick="javascript:document.forms['back'].submit();" />
				</div>
				<div>	
					<table class="table">
						<tr>
							<th style="width:2%"></th>
							<th><%=messages.getText("user.data.nickname").toUpperCase()%></th>
							<th><%=messages.getText("user.data.firstname").toUpperCase()%></th>
							<th><%=messages.getText("user.data.lastname").toUpperCase()%></th>
							<th><%=messages.getText("user.data.mail").toUpperCase()%></th>
							<th>CEDI</th>
						</tr>
	<%			Iterator<UserData> it = users.iterator();
				while(it.hasNext()) {
					UserData user = it.next();
	%>					<tr>
							<td  style="width:2%"><input type="checkbox" value="<%=user.getLoginName() %>"></td>
							<td><%=user.getLoginName() %></td>
							<td><%=user.getFirstName() %></td>
							<td><%=user.getLastName() %></td>
							<td><%=(user.getEmail() == null) ? "---" : user.getEmail() %></td>
							<td><%=(user.getExtraData2() == null) ? "---" : user.getExtraData2() %></td>
						</tr>						
	<%			}
	%>				</table>
			</div></br>
		<div>
<%			if(type == 1) {
%>				<input type="button" class="button" value="Asociar Livianos" onclick="javascript:doAsociate(1,'Confirma que desea asociar estos usuarios al Driver Assessment de Livianos?');" />
				<input type="button" class="button" value="Asociar Pesados" onclick="javascript:doAsociate(2,'Confirma que desea asociar estos usuarios al Driver Assessment de Pesados?');" />
<%			}else {
%>				<input type="button" class="button" value="Asociar eBTW" onclick="javascript:doAsociate(3,'Confirma que desea asociar estos usuarios al eBTW?');" />
<%			}
%>				<input type="button" class="button" value="Cancelar" onclick="javascript:document.forms['back'].submit();" />
		</div>
		</div>
			
		</html:form>
	</body>
<%	}
%>
	
</html:html>