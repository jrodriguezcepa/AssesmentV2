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
		<link rel="stylesheet" href="styles/fonts/pontano_sans.css">
		<link rel="stylesheet" href="styles/base.css">
		<link rel="stylesheet" href="styles/jquery-ui-1.10.3.custom.min.css">
		<!--[if lt IE 9]>
			<script type="text/javascript" src="scripts/html5shiv.min.js"></script>
		<![endif]-->
	</head>
	<body>
		<header id="header">
			<section class="grid_container">
				<h1 class="customer_logo" style="background-image: url('images/main_logo_large.png');">CEPA Driver Assessment</h1>
			</section>
		</header>

		<section id="content">

			<section class="grid_container">
				<html:form action="/CediRegister">
					<html:hidden property="cedi" value='<%=String.valueOf(cedi)%>'/>
					<html:hidden property="company" value='<%=String.valueOf(CediData.GRUPO_MODELO)%>'/>
					<fieldset id="username_block" class="active">
						<div><label> CEDI: <b><%= cediName %></b></label>
						</div>
						<br>
						<div>
							<label for="accesscode"><%=messages.getText("user.data.nickname").toUpperCase()%><span class="required">*</span></label>
		            		<html:text property="loginname" />
						</div>
						<div>
							<label for="accesscode"><%=messages.getText("user.data.pass").toUpperCase()%><span class="required">*</span></label>
		            		<html:password property="password" />
						</div>
						<div>
							<label for="accesscode"><%=messages.getText("user.data.confirmpassword").toUpperCase()%><span class="required">*</span></label>
		            		<html:password property="rePassword" />
						</div>
						<div>
							<label>NOMBRE TAL COMO APARECE EN LA LICENCIA<span class="required">*</span></label>
           					<html:text property="firstName"/>
           				</div>
						<div>
							<label>APELLIDO TAL COMO APARECE EN LA LICENCIA<span class="required">*</span></label>
           					<html:text property="lastName"/>
						</div>
						<div>
							<label>NÚMERO SAP (SI APLICA, SI NO DEJAR EN BLANCO)</label>
           					<html:text property="idnumber" />
						</div>
						<div>
							<label><%=messages.getText("user.data.mail").toUpperCase()%></label>
           					<html:text property="email" />
						</div>
						<div>
							<label><%=messages.getText("user.data.mail").toUpperCase()%></label>
           					<html:text property="email" />
						</div>
						<div>
							<label>DRIVER ASSESSMENT</label>
           					<html:select property="type">
           						<html:option value="1">Si - Livianos</html:option>
           						<html:option value="2">Si - Pesados</html:option>
           						<html:option value="3">No</html:option>
           					</html:select>
						</div>
						<!--
						<div>
							<label>eBTW</label>
           					<html:select property="ebtw">
           						<html:option value="1">Si</html:option>
           						<html:option value="2">No</html:option>
           					</html:select>           					
						</div>
						 -->
						<html:submit styleClass="button" value='<%=messages.getText("generic.messages.save")%>' />
						<html:cancel styleClass="button" value='<%=messages.getText("generic.messages.cancel")%>' />
					</fieldset>
				</html:form>
			</section>
		</section>
		<script type="text/javascript" src="scripts/json2.min.js"></script>
		<!--[if lt IE 9]>
			<script type="text/javascript" src="scripts/jquery-1.11.1.min.js"></script>
		<![endif]-->
		<!--[if gte IE 9]><!-->
			<script type="text/javascript" src="scripts/jquery-2.0.2.min.js"></script>
		<!--<![endif]-->
		<script type="text/javascript" src="scripts/jquery-ui-1.10.3.custom.min.js"></script>
		<script type="text/javascript" src="scripts/jquery.ui.datepicker-pt.js"></script>
		<script type="text/javascript" src="scripts/jquery.ui.datepicker-es.js"></script>
		<script type="text/javascript" src="scripts/jquery.tadeouy.loggable.js"></script>
		<script type="text/javascript" src="scripts/jquery.tadeouy.assessmentController.js"></script>
		<script type="text/javascript" src="scripts/jquery.tadeouy.question.js"></script>
		<script type="text/javascript" src="scripts/jquery.tadeouy.card.js"></script>
		<script type="text/javascript" src="scripts/jquery.tadeouy.assessment.js"></script>
		<script type="text/javascript" src="scripts/assessment.js"></script>
	</body>
</html:html>
