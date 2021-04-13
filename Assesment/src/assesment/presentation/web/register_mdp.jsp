<%@page import="assesment.communication.assesment.AssesmentData"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="assesment.communication.security.SecurityConstants"%>
<%@page import="assesment.presentation.translator.web.util.Util"%>
<%@ page language="java"
	import="assesment.business.*"	
	import="assesment.communication.language.*"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html" 
%>

<%@ taglib uri="/WEB-INF/struts-bean.tld"
        prefix="bean" 
%>

<%	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	Text messages = sys.getText();
	
	RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
	dispatcher.include(request,response);

	AssesmentData data = sys.getAssesmentReportFacade().findAssesment(AssesmentData.UPM_CHARLA, sys.getUserSessionData());

	int action = 0;
	if(Util.isNumber(request.getParameter("action"))) {
		action = Integer.parseInt(request.getParameter("action"));
	}
%>
<html:html>
	<head>
		<meta charset="iso-8859-1">
		<meta http-equiv="X-UA-Compatible" content="IE=9; IE=EDGE" />
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<link rel="apple-touch-icon-precomposed" sizes="57x57" href="images/apple-touch-icon-57x57-precomposed.png" />
		<link rel="apple-touch-icon-precomposed" sizes="72x72" href="images/apple-touch-icon-72x72-precomposed.png" />
		<link rel="apple-touch-icon-precomposed" sizes="114x114" href="images/apple-touch-icon-114x114-precomposed.png" />
		<link rel="apple-touch-icon-precomposed" sizes="144x144" href="images/apple-touch-icon-144x144-precomposed.png" />
		<meta name="viewport" content="user-scalable=no, width=980" />
		<meta name="description" content="">
		<meta name="keywords" content="">
		<title>CEPA Driver Assessment</title>
		<link rel="shortcut icon" type="image/ico" href="images/favicon.ico">
		<link rel="stylesheet" href="styles/fonts/pontano_sans.css">
		<link rel="stylesheet" href="styles/base.css">
		<link rel="stylesheet" href="styles/jquery-ui-1.10.3.custom.min.css">
		<!--[if lt IE 9]>
			<script type="text/javascript" src="scripts/html5shiv.min.js"></script>
		<![endif]-->
		<style type="text/css">
		.buttonRed{
			background-color: red;
			color: white;
			font-family: 'Roboto Thin',"Helvetica Neue", Helvetica, Arial, "Pontano Sans", Verdana, sans-serif;
			font-size: 18px;
			margin-top: .3em;
	        border-radius: 0.2em;
			-moz-border-radius: 0.2em;
			-webkit-border-radius: 0.2em;
			border: 1px solid #999;
			text-align: center;
			width:90px;
		    height:35px;
		}
		.red{
			color: red;
			font-family: 'Roboto Thin',"Helvetica Neue", Helvetica, Arial, "Pontano Sans", Verdana, sans-serif;
			font-size: 24px;
		}
		</style>
	</head>
	<body class="login">
		<form name="logout" action="./logout.jsp" method="post"></form>
		<header style="height: 50px;">
		</header>		
		<section id="content" style="padding-top: 50px;">
			<section class="grid_container">
				<html:form action="/RegisterMDP" style="padding-right: 0px;">
					<html:hidden property="action" value="<%=String.valueOf(action) %>"/>
					<fieldset id="username_block" class="active">
						<div>
							<label for="logo">
								<img src='images/main_logo_large.png'>
							</label>
						</div>
						<div>
		                  	<label for="accesscode"><%=messages.getText("charlas.mdp.register")%></label>
						</div>
<%		if(Util.isRegistrable()) {
			if(action == 0) {
%>						<div>
		                  	<label for="accesscode"><%=messages.getText("generic.messages.insertci")%></label>
							<html:text property="code" />
						</div>
						<div>
							<div style="float: left; width: 250px;">
								<input type="button" class="button" style="width: 200px;" value="<%=messages.getText("generic.messages.register")%>" onclick="javascript:document.forms['CharlaMDPForm'].submit();"/>
							</div>
							<div>
				  				<input class="buttonRed" value='<%=messages.getText("generic.messages.logout")%>' onClick="javascript:document.forms['logout'].submit()" />
							</div>
						</div>
<%			} else {
%>						<div>
		                  	<label for="accesscode">
		                  		<span class="red">
		                  			<%=messages.getText("mdp.charla.cinotfounded")%>
		                  		</span>
		                  	</label>
						</div>
						<div>
		                  	<label for="accesscode"><%=messages.getText("generic.messages.insertci")%></label>
							<html:text property="code" />
						</div>
						<div>
							<label><%=messages.getText("user.data.firstname")%><span class="required">*</span></label>
           					<html:text property="firstName"/>
           				</div>
						<div>
							<label><%=messages.getText("user.data.lastname")%><span class="required">*</span></label>
           					<html:text property="lastName"/>
						</div>
						<div>
							<label><%=messages.getText("Contratista")%><span class="required">*</span></label>
							<html:select property="company">
								<html:option value=''></html:option>
<%      	  	Connection connDC = (SecurityConstants.isProductionServer()) ? DriverManager.getConnection("jdbc:postgresql://18.229.182.37:5432/datacenter5","postgres","pr0v1s0r1A") : DriverManager.getConnection("jdbc:postgresql://localhost:5432/datacenter5","postgres","pr0v1s0r1A");
        		Statement stDC = connDC.createStatement();
	        	ResultSet set = stDC.executeQuery("SELECT resourcekey FROM divorgitemlevel1s WHERE active AND divorg = 456435 ORDER BY resourcekey");
	        	while(set.next()) {
	        		String s = set.getString(1);
%>           					<html:option value='<%=s%>'><%=s%></html:option>
<%	        	}
%>           				</html:select>
						</div>
						<div>
							<div style="float: left; width: 250px;">
								<input type="button" class="button" style="width: 200px;" value="<%=messages.getText("generic.messages.register")%>" onclick="javascript:document.forms['CharlaMDPForm'].submit();"/>
							</div>
							<div>
				  				<input class="buttonRed" value='<%=messages.getText("generic.messages.logout")%>' onClick="javascript:document.forms['logout'].submit()" />
							</div>
						</div>
<%			}
		}else {
%>						<div>
		                  	<label for="accesscode">
		                  		<span class="red">
		                  			No est&aacute; habilitado el registro.
		                  		</span>
		                  	</label>
						</div>
						<div>
			  				<input class="buttonRed" value='<%=messages.getText("generic.messages.logout")%>' onClick="javascript:document.forms['logout'].submit()" />
						</div>
<%		}
%>						
					</fieldset>
				</html:form>
			</section>
		</section>
		<script type="text/javascript" src="scripts/jquery-2.0.2.min.js"></script>
		<script type="text/javascript" src="scripts/assessment.js"></script>
	</body>
</html:html>
