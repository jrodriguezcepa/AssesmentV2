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

	Integer assesment = (Integer)session.getAttribute("assesment");
	AssesmentAttributes assesmentAttr = sys.getAssesmentReportFacade().findAssesmentAttributes(assesment,sys.getUserSessionData());
	int assesmentId = assesmentAttr.getId().intValue();
	session.removeAttribute("assesment");
	
	String accesscode = (String)session.getAttribute("accesscode");
	session.removeAttribute("accesscode");

	Collection sexList = new LinkedList();
	sexList.add(new OptionItem(messages.getText("user.sex.female"),String.valueOf(UserData.FEMALE)));
	sexList.add(new OptionItem(messages.getText("user.sex.male"),String.valueOf(UserData.MALE)));
	session.setAttribute("sexList",sexList);

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
	
	Collection languageList=new LinkedList();
	languageList.add(new OptionItem(messages.getText("es"),"es"));
	languageList.add(new OptionItem(messages.getText("en"),"en"));
	languageList.add(new OptionItem(messages.getText("pt"),"pt"));
	session.setAttribute("languageOptionList",languageList);
	
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
				<nav class="sections" data-min-rel-top="0" data-min-rel-bottom="0">
					<div class="score">
						<h2 class="title">CEPA Driver Assessment</h2>
						<img class="picture" src="images/sample-images/home_da_foto.jpg" width="345">
					</div>
					<p class="copyright">
						CEPA Safe Drive &copy; 2020
					</p>
				</nav>
				<html:form action="/UserData">
					<html:hidden property="assesment" value='<%=String.valueOf(assesment)%>'/>
					<html:hidden property="role" value='<%=accesscode%>'/>
					<fieldset id="username_block" class="active">
						<div>
							<label for="accesscode"><%=messages.getText("user.data.nickname")%><span class="required">*</span></label>
		            		<html:text property="loginname" />
						</div>
						<div>
							<label for="accesscode"><%=messages.getText("user.data.pass")%><span class="required">*</span></label>
		            		<html:password property="password" />
						</div>
						<div>
							<label for="accesscode"><%=messages.getText("user.data.confirmpassword")%><span class="required">*</span></label>
		            		<html:password property="rePassword" />
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
							<label><%=messages.getText("user.data.birthdate")%><span class="required">*</label></label>
           					<html:text property="birthDay" style="width: 60px;"/>&nbsp;/&nbsp;
           					<html:text property="birthMonth" style="width: 60px;"/>&nbsp;/&nbsp;
           					<html:text property="birthYear" style="width: 80px;"/>        
           				</div>
						<div>
							<label><%=messages.getText("user.data.sex")%><span class="required">*</span></label>
           					<html:select property="sex" style="width:160; ">
								<html:options collection="sexList" property="value" labelProperty="label"/>
							</html:select>
						</div>
						<div>
							<label><%=messages.getText("user.data.mail")%></label>
           					<html:text property="email" />
						</div>
						<div>
							<label><%=messages.getText("user.data.country")%><span class="required">*</label></label>
           					<html:select property="country" >
								<html:options collection="countryList" property="value" labelProperty="label"/>
							</html:select>
						</div>
        				<html:hidden property="startDay" />
         				<html:hidden property="startMonth" />
         				<html:hidden property="startYear" />        
       					<html:hidden property="expiryDay" />
           				<html:hidden property="expiryMonth" />
           				<html:hidden property="expiryYear" />        
	   					<html:hidden property="vehicle" />              
						<html:hidden property="location" />
						<div>
							<label><%=messages.getText("user.data.language")%><span class="required">*</span></label>
							<html:select property="language">
								<html:options collection="languageOptionList" property="value" labelProperty="label"/>
							</html:select>
						</div>
						<div>
							<label><%=messages.getText("user.data.fdmid")%></label>
           					<html:text property="fdm" />
						</div>
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
