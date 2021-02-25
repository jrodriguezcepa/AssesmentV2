<!doctype html>
<%@page import="assesment.communication.assesment.GroupData"%>
<%@page import="assesment.presentation.translator.web.administration.user.LogoutAction"%>
<%@page import="assesment.presentation.translator.web.util.Util"%>
<%@page import="assesment.communication.assesment.AssesmentAttributes"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.ArrayList"%>
<%@page import="assesment.communication.question.QuestionData"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="assesment.persistence.user.tables.UserAssesmentData"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.LinkedList"%>
<%@page import="assesment.business.AssesmentAccess"%>
<%@page import="assesment.communication.assesment.AssesmentData"%>
<%@page import="assesment.communication.util.CountryConstants"%>
<%@page import="assesment.communication.util.CountryData"%>
<%@page import="assesment.communication.question.AnswerData"%>
<%@page import="java.util.Iterator"%>
<%@page import="assesment.communication.module.ModuleData"%>
<%@page import="assesment.communication.administration.UserAnswerData"%>
<%@page import="assesment.communication.administration.user.UserSessionData"%>
<%@page import="assesment.communication.language.Text"%>
<%@page import="assesment.communication.user.UserData"%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html" 
%>

<%@ taglib uri="/WEB-INF/struts-bean.tld"
        prefix="bean" 
%>
<%@ page pageEncoding="UTF-8" %>


<%
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	String estilo = "styles/base.css";
	String language = (sys != null) ? sys.getUserSessionData().getLenguage() : System.getProperty("user.language");
	if(sys != null && sys.isTelematics()) {
		estilo = "styles/base_telematics.css";
	}
	ArrayList<UserAnswerData> answerDataList=new ArrayList<UserAnswerData>();

%>


<%@page import="java.util.HashMap"%>
<html lang='<%=language%>'>
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=9; IE=EDGE" />		<meta name="apple-mobile-web-app-capable" content="yes" />
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
		<link rel="stylesheet" href='<%=estilo%>'>
		<link rel="stylesheet" href="styles/jquery-ui-1.10.3.custom.min.css">
		<!--[if lt IE 9]>
			<script type="text/javascript" src="scripts/html5shiv.min.js"></script>
		<![endif]-->
		<style type="text/css">
		.body{
			background-image: url("images/background.png");
			background-repeat: repeat;

		}
		.center{
		 	background-color: #ECECEC;
 			box-shadow: 0px 0px 10px #C8C8C8;		
  			margin: auto;
			width: 80%;
			max-width: 800px;
			padding: 10px;
			
		}
		.center2{
  			margin: auto;
			width: 80%;
			padding: 10px;
		}
		.header {
    		display: block;
    		padding: 15 px;
   			margin-left: auto;
    		margin-right: auto
    	}
		.column {
		 	float: left;
		 	text-align: center;
			width: 20%;
			padding: 5px;
		}
		.row::after { 
			content: "";
			clear: both;
			display: table;
		} 
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
		</style>
		<script>
		function enterDA(assessmentId) {
			var form = document.forms['SelectAssessmentForm'];
			form.assessment.value = assessmentId;
			form.submit();
		}
		
		</script>
	</head>
<%
	if(sys == null) {
%>
	<body>
		<form action="logout.jsp">
			<br>
			<br>
			<div>
            	<label for="accesscode"><%=Text.getMessage("generic.messages.session.expired")%></label>
			</div>
			<br>
			<br>
			<input type="submit" class="button" value='<%=Text.getMessage("generic.messages.accept")%>' />
			<br>
		</form>   	 		
   	 </body>		
<%	}else {
		UserSessionData userSessionData = sys.getUserSessionData();
		Text messages = sys.getText(); 
		//Integer assesmentId = new Integer(request.getParameter("assesment"));
		//session.setAttribute("assesment", assesmentId);
		
		RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
		dispatcher.include(request,response);
		//AssesmentData assesment =  sys.getAssesmentReportFacade().findAssesment(assesmentId, userSessionData);
		//AssesmentData assesment = sys.getUserSessionData().getFilter().getAssessmentData();
		UserData userData = sys.getUserReportFacade().findUserByPrimaryKey(userSessionData.getFilter().getLoginName(),userSessionData);
		//int assessmentId = assesment.getId().intValue();
	   // String logoName = "../flash/images/logo"+assesment.getCorporationId()+".png";
		String webinar="";
		if((String)session.getAttribute("webinarcode")!=null){
			webinar=(String)session.getAttribute("webinarcode");	
		}
		HashMap <Integer, String> answers=new HashMap<Integer, String>();
		//estoy haciendo assesment 1634
%>   
   	<body>
   		<header id="header">
      		<br><br>
	  	</header>
		<form name="logout" action="./logout.jsp" method="post"></form>
		<form name="back" action="./webinarList.jsp" method="post"></form>
		
		<br><br><br>
		
		<section class="center">
			<br>
			<img  class="header" src="images/main_logo.png" alt="cepa">
			<br>
			<div class=center2> 
				<label><h4>اختبار المعرفة (A) </h4></label>
			</div>
			<html:form action="/AssesmentSaveAnswers">
				<html:hidden property="assesment" value='1634'/>

				<section>
				<div class="center2"> 
					</br>
					<label>
						<h5>هذا اختبار متعدد الخيارات. لكل سؤال إجابة واحدة صحيحة. علامة النجاح 70٪.</h5>
					</label>
				</div>
					
<%		
		if (session.getAttribute("answersHash")!=null){
			answers=(HashMap<Integer, String>) session.getAttribute("answersHash");
		}
%>
		<section>

<!-- Question 1  -->
			<div class="center2">

				<div>
					<br><label>1. ترغب في الانعطاف يمينا إلى الأمام. لماذا يجب أن تأخذ الوضع الصحيح في الوقت المناسب؟</label>
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51276)) && answers.get(new Integer(51276)).equals("162709")){
%>					<label class="radio">
						للسماح للسائقين الآخرين بالانسحاب أمامك<input type="radio" name='51276'  value="162709" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						للسماح للسائقين الآخرين بالانسحاب أمامك<input type="radio" name='51276'  value="162709"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51276)) && answers.get(new Integer(51276)).equals("162711")){
%>					<label class="radio">
						لإعطاء رؤية أفضل للطريق الذي تنضم إليه<input type="radio" name='51276'  value="162711" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						لإعطاء رؤية أفضل للطريق الذي تنضم إليه <input type="radio" name='51276'  value="162711"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51276)) && answers.get(new Integer(51276)).equals("162710")){
%>					<label class="radio">
						لمساعدة مستخدمي الطريق الآخرين على معرفة ما تنوي القيام به<input type="radio" name='51276'  value="162710" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						لمساعدة مستخدمي الطريق الآخرين على معرفة ما تنوي القيام به<input type="radio" name='51276'  value="162710"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51276)) && answers.get(new Integer(51276)).equals("162708")){
%>					<label class="radio">
						للسماح للسائقين بتمريرك على اليمين<input type="radio" name='51276'  value="162708" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						للسماح للسائقين بتمريرك على اليمين<input type="radio" name='51276'  value="162708"/>
					</label>
					</br>
<%				}  
%>
				</div>
	</div>	
	<!-- Question 1 ends -->
	<!-- Question 2  -->
			<div class="center2">

				<div>
					<br><label>1. ما الذي يشترط القانون أن تبقيه في حالة جيدة؟</label>
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51277)) && answers.get(new Integer(51277)).equals("162715")){
%>					<label class="radio">
						المقاعد<input type="radio" name='51277'  value="162715" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						المقاعد<input type="radio" name='51277'  value="162715"/>
					</label>
					</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51277)) && answers.get(new Integer(51277)).equals("162712")){
%>					<label class="radio">
						الانتقال<input type="radio" name='51277'  value="162712" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						الانتقال<input type="radio" name='51277'  value="162712"/>
					</label>
					</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51277)) && answers.get(new Integer(51277)).equals("162713")){
%>					<label class="radio">
						أقفال الابواب<input type="radio" name='51277'  value="162713" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						أقفال الابواب<input type="radio" name='51277'  value="162713"/>
					</label>
					</br>
<%				}

%>				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51277)) && answers.get(new Integer(51277)).equals("162714")){
%>					<label class="radio">
						أحزمة المقاعد<input type="radio" name='51277'  value="162714" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						أحزمة المقاعد<input type="radio" name='51277'  value="162714"/>
					</label>
					</br>
<%				}  
%>
				</div>
	</div>	
	<!-- Question 2 ends -->
	<!-- Question 3  -->
			<div class="center2">

				<div>
					<br><label>1. إشارات المرور التي تعطي الأوامر هي بشكل عام أي شكل</label>
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51278)) && answers.get(new Integer(51278)).equals("162717")){
%>					<label class="radio">
						<img  width=15% src="images/1634-3-1.png" alt="cepa">
						<input type="radio" name='51278'  value="162717" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						<img  width=15% src="images/1634-3-1.png" alt="cepa">
						<input type="radio" name='51278'  value="162717"/>
					</label>
					</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51278)) && answers.get(new Integer(51278)).equals("162716")){
%>					<label class="radio">
						<img  width=15% src="images/1634-3-2.png" alt="cepa">
						<input type="radio" name='51278'  value="162716" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						<img  width=15% src="images/1634-3-2.png" alt="cepa">
						<input type="radio" name='51278'  value="162716"/>
					</label>
					</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51278)) && answers.get(new Integer(51278)).equals("162719")){
%>					<label class="radio">
						<img  width=15% src="images/1634-3-3.png" alt="cepa">
						<input type="radio" name='51278'  value="162719" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						<img  width=15% src="images/1634-3-3.png" alt="cepa">
						<input type="radio" name='51278'  value="162719"/>
					</label>
					</br>
<%				}

%>				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51278)) && answers.get(new Integer(51278)).equals("162718")){
%>					<label class="radio">
						<img  width=15% src="images/1634-3-4.png" alt="cepa">
						<input type="radio" name='51278'  value="162718" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						<img  width=15% src="images/1634-3-4.png" alt="cepa">
						<input type="radio" name='51278'  value="162718"/>
					</label>
					</br>
<%				}  
%>
				</div>
	</div>	
	<!-- Question 3 ends -->
	<!-- Question 4  -->
			<div class="center2">

				<div>
					<br><label>1. ما هو ضوء التحذير الموجود في لوحة العدادات الذي يُظهر أن المصابيح   الأمامية في شعاع كامل؟</label>
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51279)) && answers.get(new Integer(51279)).equals("162722")){
%>					<label class="radio">
						<img  width=15% src="images/1634-4-1.png" alt="cepa">
						<input type="radio" name='51279'  value="162722" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						<img  width=15% src="images/1634-4-1.png" alt="cepa">
						<input type="radio" name='51279'  value="162722"/>
					</label>
					</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51279)) && answers.get(new Integer(51279)).equals("162721")){
%>					<label class="radio">
						<img  width=15% src="images/1634-4-2.png" alt="cepa">
						<input type="radio" name='51279'  value="162721" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						<img  width=15% src="images/1634-4-2.png" alt="cepa">
						<input type="radio" name='51279'  value="162716"/>
					</label>
					</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51279)) && answers.get(new Integer(51279)).equals("162720")){
%>					<label class="radio">
						<img  width=15% src="images/1634-4-3.png" alt="cepa">
						<input type="radio" name='51279'  value="162720" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						<img  width=15% src="images/1634-4-3.png" alt="cepa">
						<input type="radio" name='51279'  value="162720"/>
					</label>
					</br>
<%				}

%>				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51279)) && answers.get(new Integer(51279)).equals("162723")){
%>					<label class="radio">
						<img  width=15% src="images/1634-4-4.png" alt="cepa">
						<input type="radio" name='51279'  value="162723" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						<img  width=15% src="images/1634-4-4.png" alt="cepa">
						<input type="radio" name='51279'  value="162723"/>
					</label>
					</br>
<%				}  
%>
				</div>
	</div>	
	<!-- Question 4 ends -->	
	<!-- Question 5  -->
			<div class="center2">

				<div>
					<br><label>1. ما هي الأضواء التي يجب أن تستخدمها أثناء القيادة في نفق؟</label>
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51280)) && answers.get(new Integer(51280)).equals("162727")){
%>					<label class="radio">
						مصابيح ضباب خلفية<input type="radio" name='51280'  value="162727" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						مصابيح ضباب خلفية<input type="radio" name='51280'  value="162727"/>
					</label>
					</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51280)) && answers.get(new Integer(51280)).equals("162726")){
%>					<label class="radio">
						الأضواء الجانبية<input type="radio" name='51280'  value="162726" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						الأضواء الجانبية<input type="radio" name='51280'  value="162726"/>
					</label>
					</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51280)) && answers.get(new Integer(51280)).equals("162725")){
%>					<label class="radio">
						الأضواء الأمامية<input type="radio" name='51280'  value="162725" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						الأضواء الأمامية<input type="radio" name='51280'  value="162725"/>
					</label>
					</br>
<%				}

%>				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51280)) && answers.get(new Integer(51280)).equals("162724")){
%>					<label class="radio">
						المصابيح الأمامية المنخفضة	<input type="radio" name='51280'  value="162724" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						المصابيح الأمامية المنخفضة<input type="radio" name='51280'  value="162724"/>
					</label>
					</br>
<%				}  
%>
				</div>
	</div>	
	<!-- Question 5 ends -->
		<!-- Question 6  -->
			<div class="center2">

				<div>
					<br><label>1. أنت تتبع مركبة تتحرك ببطء على طريق ريفي ضيق. هناك   تقاطع أمامك على اليمين. ماذا عليك ان تفعل؟</label>
					<img  width=15% src="images/1634-6.png" alt="cepa">
					
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51281)) && answers.get(new Integer(51281)).equals("162729")){
%>					<label class="radio">
						تجاوز بعد فحص المرايا الخاصة بك والإشارة<input type="radio" name='51281'  value="162729" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						تجاوز بعد فحص المرايا الخاصة بك والإشارة<input type="radio" name='51281'  value="162729"/>
					</label>
					</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51281)) && answers.get(new Integer(51281)).equals("162728")){
%>					<label class="radio">
						ضع في اعتبارك التجاوز فقط عندما تتجاوز التقاطع<input type="radio" name='51281'  value="162728" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						ضع في اعتبارك التجاوز فقط عندما تتجاوز التقاطع<input type="radio" name='51281'  value="162728"/>
					</label>
					</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51281)) && answers.get(new Integer(51281)).equals("162730")){
%>					<label class="radio">
						قم بالتسريع بالمرور قبل التقاطع<input type="radio" name='51281'  value="162730" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						قم بالتسريع بالمرور قبل التقاطع<input type="radio" name='51281'  value="162730"/>
					</label>
					</br>
<%				}

%>				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51281)) && answers.get(new Integer(51281)).equals("162731")){
%>					<label class="radio">
						تمهل واستعد للتجاوز على اليسار<input type="radio" name='51281'  value="162731" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						تمهل واستعد للتجاوز على اليسار<input type="radio" name='51281'  value="162731"/>
					</label>
					</br>
<%				}  
%>
				</div>
	</div>	
	<!-- Question 6 ends -->
	<!-- Question 7  -->
		<div class="center2">

		<div>
			<br><label>1. أنت تقترب من إشارة مرور حمراء. ماذا ستظهر الإشارة بعد ذلك؟</label>
		</div>
		<!-- Option 1 -->
		<div>
<%				if(answers.containsKey(new Integer(51282)) && answers.get(new Integer(51282)).equals("162735")){
%>					<label class="radio">
						أحمر واصفر<input type="radio" name='51282'  value="162735" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
					أحمر واصفر<input type="radio" name='51282'  value="162735"/>
					</label>
					</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51282)) && answers.get(new Integer(51282)).equals("162733")){
%>					<label class="radio">
					أخضر وحده<input type="radio" name='51282'  value="162733" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
					أخضر وحده<input type="radio" name='51282'  value="162733"/>
					</label>
					</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51282)) && answers.get(new Integer(51282)).equals("162732")){
%>					<label class="radio">
					اصفر وحده<input type="radio" name='51282'  value="162732" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						اصفر وحده<input type="radio" name='51282'  value="162732"/>
					</label>
					</br>
<%				}

%>				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51282)) && answers.get(new Integer(51282)).equals("162734")){
%>					<label class="radio">
						أخضر واصفر<input type="radio" name='51282'  value="162734" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						أخضر واصفر<input type="radio" name='51282'  value="162734"/>
					</label>
					</br>
<%				}  
%>
		</div>
	</div>	
	<!-- Question 7 ends -->	
	<!-- Question 8  -->
		<div class="center2">

		<div>
			<br><label>1. كيف يمكنك استخدام محرك مركبتك للتحكم في سرعتك؟</label>
		</div>
		<!-- Option 1 -->
		<div>
<%				if(answers.containsKey(new Integer(51283)) && answers.get(new Integer(51283)).equals("162738")){
%>					<label class="radio">
						بالتغيير إلى ترس أقل<input type="radio" name='51283'  value="162738" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						بالتغيير إلى ترس أقل<input type="radio" name='51283'  value="162738"/>
					</label>
					</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51283)) && answers.get(new Integer(51283)).equals("162739")){
%>					<label class="radio">
						عن طريق اختيار الترس العكسي<input type="radio" name='51283'  value="162739" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						عن طريق اختيار الترس العكسي<input type="radio" name='51283'  value="162739"/>
					</label>
					</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51283)) && answers.get(new Integer(51283)).equals("162736")){
%>					<label class="radio">
						بالتغيير إلى ترس أعلى<input type="radio" name='51283'  value="162736" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						بالتغيير إلى ترس أعلى<input type="radio" name='51283'  value="162736"/>
					</label>
					</br>
<%				}

%>				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51283)) && answers.get(new Integer(51283)).equals("162737")){
%>					<label class="radio">
						عن طريق اختيار محايد<input type="radio" name='51283'  value="162737" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						عن طريق اختيار محايد<input type="radio" name='51283'  value="162737"/>
					</label>
					</br>
<%				}  
%>
		</div>
	</div>	
	<!-- Question 8 ends -->
	<!-- Question 9  -->
		<div class="center2">

		<div>
			<br><label> لماذا يجب أن تترك مساحة إضافية عند تجاوز راكب دراجة نارية في يوم  عاصف؟   </label>
		</div>
		<!-- Option 1 -->
		<div>
<%				if(answers.containsKey(new Integer(51284)) && answers.get(new Integer(51284)).equals("162742")){
%>					<label class="radio">
						قد يطفئ الراكب الدراجة فجأة للخروج من الريح<input type="radio" name='51284'  value="162742" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						قد يطفئ الراكب الدراجة فجأة للخروج من الريح<input type="radio" name='51284'  value="162742"/>
					</label>
					</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51284)) && answers.get(new Integer(51284)).equals("162740")){
%>					<label class="radio">
						قد ينجرف أمامك بشدة راكب الدراجة<input type="radio" name='51284'  value="162740" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						قد ينجرف أمامك بشدة راكب الدراجة<input type="radio" name='51284'  value="162740"/>
					</label>
					</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51284)) && answers.get(new Integer(51284)).equals("162741")){
%>					<label class="radio">
						قد يتوقف راكب الدراجة فجأة<input type="radio" name='51284'  value="162741" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						قد يتوقف راكب الدراجة فجأة<input type="radio" name='51284'  value="162741"/>
					</label>
					</br>
<%				}

%>				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51284)) && answers.get(new Integer(51284)).equals("162743")){
%>					<label class="radio">
						قد يتحرك راكب الدراجة أسرع من المعتاد<input type="radio" name='51284'  value="162743" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						قد يتحرك راكب الدراجة أسرع من المعتاد<input type="radio" name='51284'  value="162743"/>
					</label>
					</br>
<%				}  
%>
		</div>
	</div>	
	<!-- Question 9 ends -->
		<!-- Question 10  -->
		<div class="center2">

		<div>
			<br><label> أنت على طريق مضاء جيدًا في الليل ، في منطقة سكنية. كيف سيساعد  استخدام المصابيح الأمامية الخافتة؟  </label>
		</div>
		<!-- Option 1 -->
		<div>
<%				if(answers.containsKey(new Integer(51285)) && answers.get(new Integer(51285)).equals("162746")){
%>					<label class="radio">
						يمكنك أن ترى على طول الطريق<input type="radio" name='51285'  value="162746" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						يمكنك أن ترى على طول الطريق<input type="radio" name='51285'  value="162746"/>
					</label>
					</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51285)) && answers.get(new Integer(51285)).equals("162745")){
%>					<label class="radio">
						يمكنك الذهاب بسرعة أكبر بكثير<input type="radio" name='51285'  value="162745" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						يمكنك الذهاب بسرعة أكبر بكثير<input type="radio" name='51285'  value="162745"/>
					</label>
					</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51285)) && answers.get(new Integer(51285)).equals("162744")){
%>					<label class="radio">
						يمكنك التبديل إلى المصباح الرئيسي بسرعة<input type="radio" name='51285'  value="162744" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						يمكنك التبديل إلى المصباح الرئيسي بسرعة<input type="radio" name='51285'  value="162744"/>
					</label>
					</br>
<%				}

%>				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51285)) && answers.get(new Integer(51285)).equals("162747")){
%>					<label class="radio">
						يمكن أن يراك الآخرون بسهولة<input type="radio" name='51285'  value="162747" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						يمكن أن يراك الآخرون بسهولة<input type="radio" name='51285'  value="162747"/>
					</label>
					</br>
<%				}  
%>
		</div>
	</div>	
	<!-- Question 10 ends -->	
		</section>
			
				<br>
		<div align="center">
	       	<html:submit value='<%=messages.getText("generic.messages.accept")%>' styleClass="button"/>
	       	<input type="button" value='<%=messages.getText("generic.messages.change")%>' class="buttonRed" onclick="document.forms['back'].submit();"/>
		</div>
			</html:form>
		</section>
<% 		}
%>	
   </body> 
</html>