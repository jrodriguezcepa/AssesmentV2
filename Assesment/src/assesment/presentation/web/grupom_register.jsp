<%@page import="assesment.communication.corporation.CediData"%>
<%@page import="assesment.presentation.translator.web.util.Util"%>
<%@ page language="java"
	import="assesment.business.*"	
	import="assesment.communication.language.*"
	import="assesment.communication.assesment.AssesmentAttributes"
	import="java.util.Collection"
	import="java.util.LinkedList"
	import="assesment.presentation.translator.web.util.OptionItem"
	import="assesment.communication.user.UserData"
	import="assesment.communication.util.CountryConstants"
	import="java.util.Iterator"
	import="assesment.communication.util.CountryData"
	import="assesment.communication.language.tables.LanguageData"
	import="assesment.presentation.actions.user.UserCreateForm"
	import="assesment.communication.corporation.CediAttributes"
	
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

	Integer cedi = (Integer)session.getAttribute("cedi");
	if(cedi == null) {
		String c = request.getParameter("cedi");
		if(Util.isNumber(c))
			cedi = new Integer(c); 
	}
	String cediName="";
	if(cedi!=null){
		CediAttributes cediAttributes = sys.getCorporationReportFacade().findCediAttributes(new Integer(cedi),sys.getUserSessionData());
		cediName = cediAttributes.getName().toUpperCase();
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
		<!--[if lt IE 9]>
			<script type="text/javascript" src="scripts/html5shiv.min.js"></script>
		<![endif]-->
		<style type="text/css">
			.fondo{
				background-color: #EBEBEB;
			}
			.contenedor{
				width:100%;
			}
			.box{
				background-color: #F1F4F9;
				margin: 0 auto;
				margin-top:20px;
				width:600px;
	        	border-radius: 1.2em;
				-moz-border-radius: 1.2em;
				-webkit-border-radius: 1.2em;
				border: 0px solid #C9C9C9;
				box-shadow: 2px 2px #CCCCCC;'
			}
			.text{
				background-color: #DDE4EA;
				padding-left:10px;
				font-family: 'Roboto Thin',"Helvetica Neue", Helvetica, Arial, "Pontano Sans", Verdana, sans-serif;
				font-size:1.0em;
				width:520px;
				height:50px;
	        	border-radius: 0.8em;
				-moz-border-radius: 0.8em;
				-webkit-border-radius: 0.8em;
				border: 0px solid #C9C9C9;
				margin-left: 20px;
    			margin-top: 10px;
			}
			.cedi{
				padding-left:10px;
				color: #8E8E8E;
				font-family: 'Roboto Thin',"Helvetica Neue", Helvetica, Arial, "Pontano Sans", Verdana, sans-serif;
				font-size:1.0em;
				margin-left: 20px;
    			margin-top: 20px;
			}
			.button{
				background-color: #0F172A;
				color: #FFFFFF;
				padding-left:10px;
				font-family: 'Roboto Thin',"Helvetica Neue", Helvetica, Arial, "Pontano Sans", Verdana, sans-serif;
				font-size:0.8em;
				width:250px;
				height:50px;
	        	border-radius: 0.8em;
				-moz-border-radius: 0.8em;
				-webkit-border-radius: 0.8em;
				border: 0px solid #C9C9C9;
				margin-top:20px;
				margin-left:20px;
				margin-bottom:10px;
			}
			.logo {
				margin-left:30px;
				margin-rigth:30px;
				margin-top:20px;
				width:75px;
			}
		</style>
	</head>
	<body class="fondo">
		<header style="height: 50px;">
		</header>		
		<html:form action="/CediRegister">
			<html:hidden property="cedi" value='<%=String.valueOf(cedi)%>'/>
			<html:hidden property="company" value='<%=String.valueOf(CediData.GRUPO_MODELO)%>'/>
			<div class="contenedor">
				<div class="box">
					<div>
						<div style="float: left; width: 400px;">
							<img src="styles/grupomodelo/UI_Logo_CEPA.png" class="logo"/>
						</div>
						<div>
							<img src="styles/grupomodelo/UI_Logo_GMM.png" class="logo"/>
						</div>
					</div>
					<div class="cedi">
						CEDI: <b><%= cediName %></b>
					</div>
					<div>
						<input name="loginname" class="text" placeholder='<%=messages.getText("user.data.nickname").toUpperCase()+"*"%>'/>
					</div>
					<div>
						<input type="password" name="password" class="text" placeholder='<%=messages.getText("user.data.pass").toUpperCase()+"*"%>'/>
					</div>
					<div>
						<input type="password" name="rePassword" class="text" placeholder='<%=messages.getText("user.data.confirmpassword").toUpperCase()+"*"%>'/>
					</div>
					<div>
						<input name="firstName" class="text" placeholder="NOMBRE TAL COMO APARECE EN LA LICENCIA *"/>
					</div>
					<div>
						<input name="lastName" class="text" placeholder="APELLIDO TAL COMO APARECE EN LA LICENCIA *"/>
					</div>
					<div>
						<input name="idnumber" class="text" placeholder="NÚMERO SAP (SI APLICA, SI NO DEJAR EN BLANCO)"/>
					</div>
					<div>
						<input name="email" class="text" placeholder='<%=messages.getText("user.data.mail").toUpperCase()%>'/>
					</div>
					<div class="cedi">
						Driver Assessment
					</div>
					<div>
						<select name="type" class="text">
       						<option value="1">Si - Livianos</option>
       						<option value="2">Si - Pesados</option>
       						<option value="3">No</option>
       					</select>
					</div>
					<div>
						<html:submit styleClass="button" value='<%=messages.getText("generic.messages.save")%>' />
						<html:cancel styleClass="button" value='<%=messages.getText("generic.messages.cancel")%>' />
					</div>
				</div>
			</div>
		</html:form>
	</body>
</html:html>
