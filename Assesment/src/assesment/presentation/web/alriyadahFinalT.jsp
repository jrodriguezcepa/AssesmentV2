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
	UserSessionData userSessionData = sys.getUserSessionData();
	Text messages = sys.getText(); 
	
	RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
	dispatcher.include(request,response);
	UserData userData = sys.getUserReportFacade().findUserByPrimaryKey(userSessionData.getFilter().getLoginName(),userSessionData);
	String webinar="";
	if((String)session.getAttribute("webinarcode")!=null){
		webinar=(String)session.getAttribute("webinarcode");	
	}
	HashMap <Integer, String> answers=new HashMap<Integer, String>();

%>


<%@page import="java.util.HashMap"%>
<html>
	<head>
		<meta charset="UTF-8">
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
			text-align: right;
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
   	<body>
   		<header id="header">
      		<br><br>
	  	</header>
		<form name="logout" action="./logout.jsp" method="post"></form>
		
		<br><br><br>
		
		<section class="center">
			<br>
			<img  class="header" src="images/main_logo.png" alt="cepa">
			<br>
			<div class=center2> 
				<label><h4>ااختبار المعرفة النهائي</h4></label>
			</div>
			<html:form action="/AlRiyadahSaveAnswers">
				<html:hidden property="assesment" value='<%=String.valueOf(AssesmentData.ALRIYADAH_FINAL)%>'/>
				<html:hidden property="user" value='<%=userData.getLoginName()%>'/>
				<section>
				<div class="center2"> 
					</br>
					<label>
						<h5>هذا اختبار متعدد الخيارات. لكل سؤال إجابة واحدة صحيحة. علامة النجاح.٪ 70</h5>
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
					<br><label> .1</label>
				
					<br><label> من الضروري فحص ضغط الإطارات بانتظام. متى يجب القيام بذلك؟</label>
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51296)) && answers.get(new Integer(51296)).equals("162788")){
%>					<label class="radio">
						بعد أي رحلة طويلة<input type="radio" name='51296'  value="162788" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						بعد أي رحلة طويلة<input type="radio" name='51296'  value="162788"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51296)) && answers.get(new Integer(51296)).equals("162789")){
%>					<label class="radio">
						بعد السفر بسرعة عالية<input type="radio" name='51296'  value="162789" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						بعد السفر بسرعة عالية<input type="radio" name='51296'  value="162789"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51296)) && answers.get(new Integer(51296)).equals("162791")){
%>					<label class="radio">
						عندما تكون الإطارات ساخنة<input type="radio" name='51296'  value="162791" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						عندما تكون الإطارات ساخنة<input type="radio" name='51296'  value="162791"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51296)) && answers.get(new Integer(51296)).equals("162790")){
%>					<label class="radio">
						عندما تكون الإطارات باردة<input type="radio" name='51296'  value="162790" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						عندما تكون الإطارات باردة<input type="radio" name='51296'  value="162790"/>
					</label>
					</br>
<%				}  
%>
				</div>
	</div>	
	<div>
		<img src="images/lineWebinar.png" style="width: 100%">
	</div>
	<!-- Question 1 ends -->
	<!-- Question 2  -->
			<div class="center2">

				<div>
					<br><label> .2</label>
				
					<br><label> من هو أكثر مستخدمي الطريق عرضة للخطر عند تقاطعات الطرق؟</label>
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51297)) && answers.get(new Integer(51297)).equals("162794")){
%>					<label class="radio">
						سائق السياره<input type="radio" name='51297'  value="162794" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						سائق السياره<input type="radio" name='51297'  value="162794"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51297)) && answers.get(new Integer(51297)).equals("162792")){
%>					<label class="radio">
						سائق الدراجة الهوائية<input type="radio" name='51297'  value="162792" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						سائق الدراجة الهوائية<input type="radio" name='51297'  value="162792"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51297)) && answers.get(new Integer(51297)).equals("162795")){
%>					<label class="radio">
						المشاة<input type="radio" name='51297'  value="162795" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						المشاة<input type="radio" name='51297'  value="162795"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51297)) && answers.get(new Integer(51297)).equals("162793")){
%>					<label class="radio">
						سائق الدراجة النارية<input type="radio" name='51297'  value="162793" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						سائق الدراجة النارية<input type="radio" name='51297'  value="162793"/>
					</label>
					</br>
<%				}  
%>
				</div>
	</div>	
	<div>
		<img src="images/lineWebinar.png" style="width: 100%">
	</div>
	<!-- Question 2 ends -->
	<!-- Question 3  -->
			<div class="center2">

				<div>
					<br><label> .3</label>
					<br><label style="float:right";> ماذا تعني هذه الإشارة؟</label><br>
					<img style="float:left;margin-left:300px;width:15%;" src="images/1636-3.png" alt="cepa">
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51298)) && answers.get(new Integer(51298)).equals("162799")){
%>					<label class="radio">
						منطقة خدمة بعد 30 ميلا<input type="radio" name='51298'  value="162799" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						منطقة خدمة بعد 30 ميلا<input type="radio" name='51298'  value="162799"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51298)) && answers.get(new Integer(51298)).equals("162798")){
%>					<label class="radio">
						السرعة القصوى 30 كم / ساعة<input type="radio" name='51298'  value="162798" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						السرعة القصوى 30 كم / ساعة<input type="radio" name='51298'  value="162798"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51298)) && answers.get(new Integer(51298)).equals("162797")){
%>					<label class="radio">
						الحد الأدنى للسرعة 30 كم / ساعة<input type="radio" name='51298'  value="162797" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						الحد الأدنى للسرعة 30 كم / ساعة<input type="radio" name='51298'  value="162797"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51298)) && answers.get(new Integer(51298)).equals("162796")){
%>					<label class="radio">
						استراحة بعد 30 كم<input type="radio" name='51298'  value="162796" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						استراحة بعد 30 كم<input type="radio" name='51298'  value="162796"/>
					</label>
					</br>
<%				}  
%>
				</div>
	</div>	
	<div>
		<img src="images/lineWebinar.png" style="width: 100%">
	</div>
	<!-- Question 3 ends -->
	<!-- Question 4  -->
			<div class="center2">

				<div>
					<br><label> .4</label>
					<br><label> ماذا تعنى هذه الاشارة</label><br>
					<img style="float:left;margin-left:300px;width:15%;" src="images/1636-4.png" alt="cepa">
					
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51299)) && answers.get(new Integer(51299)).equals("162802")){
%>					<label class="radio">
						لا توجد علامات طريق<input type="radio" name='51299'  value="162802" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						لا توجد علامات طريق<input type="radio" name='51299'  value="162802"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51299)) && answers.get(new Integer(51299)).equals("162801")){
%>					<label class="radio">
						ممنوع الدخول<input type="radio" name='51299'  value="162801" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						ممنوع الدخول<input type="radio" name='51299'  value="162801"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51299)) && answers.get(new Integer(51299)).equals("162803")){
%>					<label class="radio">
						لا طريق<input type="radio" name='51299'  value="162803" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						لا طريق<input type="radio" name='51299'  value="162803"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51299)) && answers.get(new Integer(51299)).equals("162800")){
%>					<label class="radio">
						ممنوع الوقوف<input type="radio" name='51299'  value="162800" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						ممنوع الوقوف<input type="radio" name='51299'  value="162800"/>
					</label>
					</br>
<%				}  
%>
				</div>
	</div>	
	<div>
		<img src="images/lineWebinar.png" style="width: 100%">
	</div>
	<!-- Question 4 ends -->
	<!-- Question 5  -->
			<div class="center2">

				<div>
					<br><label> .5</label>
					<br><label> ما هو السبب الوحيد الذي يجعلك تومض المصابيح الأمامية لمستخدمي الطريق الآخرين؟</label>
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51300)) && answers.get(new Integer(51300)).equals("162807")){
%>					<label class="radio">
						لتظهر أنك تفسح المجال<input type="radio" name='51300'  value="162807" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						لتظهر أنك تفسح المجال<input type="radio" name='51300'  value="162807"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51300)) && answers.get(new Integer(51300)).equals("162806")){
%>					<label class="radio">
						لتظهر أنك على وشك الاستدارة<input type="radio" name='51300'  value="162806" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						لتظهر أنك على وشك الاستدارة<input type="radio" name='51300'  value="162806"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51300)) && answers.get(new Integer(51300)).equals("162804")){
%>					<label class="radio">
						لإخبارهم أن لديك حق المرور<input type="radio" name='51300'  value="162804" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						لإخبارهم أن لديك حق المرور<input type="radio" name='51300'  value="162804"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51300)) && answers.get(new Integer(51300)).equals("162805")){
%>					<label class="radio">
						لإعلامهم أنك متواجد<input type="radio" name='51300'  value="162805" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						لإعلامهم أنك متواجد<input type="radio" name='51300'  value="162805"/>
					</label>
					</br>
<%				}  
%>
				</div>
	</div>	
	<div>
		<img src="images/lineWebinar.png" style="width: 100%">
	</div>
	<!-- Question 5 ends -->
	<!-- Question 6  -->
			<div class="center2">

				<div>
					<br><label> .6</label>
					<br><label> عند الخروج من التقاطعات ، ما الذي من المرجح أن يعيق رؤيتك؟</label>
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51301)) && answers.get(new Integer(51301)).equals("162809")){
%>					<label class="radio">
						ركائز الزجاج الأمامي<input type="radio" name='51301'  value="162809" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						ركائز الزجاج الأمامي<input type="radio" name='51301'  value="162809"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51301)) && answers.get(new Integer(51301)).equals("162811")){
%>					<label class="radio">
						المقود<input type="radio" name='51301'  value="162811" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						المقود<input type="radio" name='51301'  value="162811"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51301)) && answers.get(new Integer(51301)).equals("162808")){
%>					<label class="radio">
						المرآة الداخلية<input type="radio" name='51301'  value="162808" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						المرآة الداخلية<input type="radio" name='51301'  value="162808"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51301)) && answers.get(new Integer(51301)).equals("162810")){
%>					<label class="radio">
						مساحات الزجاج الأمامي<input type="radio" name='51301'  value="162810" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						مساحات الزجاج الأمامي<input type="radio" name='51301'  value="162810"/>
					</label>
					</br>
<%				}  
%>
				</div>
	</div>	
	<div>
		<img src="images/lineWebinar.png" style="width: 100%">
	</div>
	<!-- Question 6 ends -->
	<!-- Question 7  -->
			<div class="center2">

				<div>
					<br><label> .7</label>	
					<br><label> يجب عليك تشغيل مصابيح الضباب الخلفية عندما تنخفض الرؤية إلى ما دون أي مسافة؟</label>
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51302)) && answers.get(new Integer(51302)).equals("162815")){
%>					<label class="radio">
						مسافة التوقف الإجمالية<input type="radio" name='51302'  value="162815" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						مسافة التوقف الإجمالية<input type="radio" name='51302'  value="162815"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51302)) && answers.get(new Integer(51302)).equals("162814")){
%>					<label class="radio">
						عشرة أطوال سيارات<input type="radio" name='51302'  value="162814" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						عشرة أطوال سيارات<input type="radio" name='51302'  value="162814"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51302)) && answers.get(new Integer(51302)).equals("162812")){
%>					<label class="radio">
						200 متر<input type="radio" name='51302'  value="162812" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						200 متر<input type="radio" name='51302'  value="162812"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51302)) && answers.get(new Integer(51302)).equals("162813")){
%>					<label class="radio">
						100 متر<input type="radio" name='51302'  value="162813" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						100 متر<input type="radio" name='51302'  value="162813"/>
					</label>
					</br>
<%				}  
%>
				</div>
	</div>	
	<div>
		<img src="images/lineWebinar.png" style="width: 100%">
	</div>
	<!-- Question 7 ends -->
	<!-- Question 8  -->
			<div class="center2">

				<div>
					<br><label> .8</label>
					<br><label> أنت تقترب من تقاطع طرق مزدحم. هناك عدة مسارات عليها علامات الطريق. في اللحظة  الأخيرة أدركت أنك في المسار الخطأ. ماذا عليك ان تفعل؟</label>
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51303)) && answers.get(new Integer(51303)).equals("162816")){
%>					<label class="radio">
						تواصل في هذا الممر<input type="radio" name='51303'  value="162816" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						تواصل في هذا الممر<input type="radio" name='51303'  value="162816"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51303)) && answers.get(new Integer(51303)).equals("162819")){
%>					<label class="radio">
						شق طريقك عبره<input type="radio" name='51303'  value="162819" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						شق طريقك عبره<input type="radio" name='51303'  value="162819"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51303)) && answers.get(new Integer(51303)).equals("162818")){
%>					<label class="radio">
						توقف حتى تصبح المنطقة خالية<input type="radio" name='51303'  value="162818" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						توقف حتى تصبح المنطقة خالية<input type="radio" name='51303'  value="162818"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51303)) && answers.get(new Integer(51303)).equals("162817")){
%>					<label class="radio">
						استخدم إشارات ذراعك لتقطع الطريق<input type="radio" name='51303'  value="162817" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						استخدم إشارات ذراعك لتقطع الطريق<input type="radio" name='51303'  value="162817"/>
					</label>
					</br>
<%				}  
%>
				</div>
	</div>	
	<div>
		<img src="images/lineWebinar.png" style="width: 100%">
	</div>
	<!-- Question 8 ends -->
	<!-- Question 9  -->
			<div class="center2">

				<div>
					<br><label> .9</label>
					<br><label style="float:right";> ماذا تعنى هذه الاشارة</label><br>
					<img style="float:left;margin-left:250px;width:15%;" src="images/1636-9.png" alt="cepa">
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51304)) && answers.get(new Integer(51304)).equals("162823")){
%>					<label class="radio">
						ضوضاء على الطريق<input type="radio" name='51304'  value="162823" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						ضوضاء على الطريق<input type="radio" name='51304'  value="162823"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51304)) && answers.get(new Integer(51304)).equals("162822")){
%>					<label class="radio">
						المطار أمامك<input type="radio" name='51304'  value="162822" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						المطار أمامك<input type="radio" name='51304'  value="162822"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51304)) && answers.get(new Integer(51304)).equals("162820")){
%>					<label class="radio">
						رياح متقاطعة<input type="radio" name='51304'  value="162820" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						رياح متقاطعة<input type="radio" name='51304'  value="162820"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51304)) && answers.get(new Integer(51304)).equals("162821")){
%>					<label class="radio">
						طريق محدب<input type="radio" name='51304'  value="162821" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						طريق محدب<input type="radio" name='51304'  value="162821"/>
					</label>
					</br>
<%				}  
%>
				</div>
	</div>	
	<div>
		<img src="images/lineWebinar.png" style="width: 100%">
	</div>
	<!-- Question 9 ends -->
	<!-- Question 10  -->
			<div class="center2">

				<div>
					<br><label> .10</label>
					<br><label>  يجب أن تتوقف عندما يُطلب منك ذلك من قبل من؟</label>
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51305)) && answers.get(new Integer(51305)).equals("162824")){
%>					<label class="radio">
						سائق دراجة نارية<input type="radio" name='51305'  value="162824" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						سائق دراجة نارية<input type="radio" name='51305'  value="162824"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51305)) && answers.get(new Integer(51305)).equals("162826")){
%>					<label class="radio">
						مشاة<input type="radio" name='51305'  value="162826" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						مشاة<input type="radio" name='51305'  value="162826"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51305)) && answers.get(new Integer(51305)).equals("162827")){
%>					<label class="radio">
						ضابط شرطة<input type="radio" name='51305'  value="162827" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						ضابط شرطة<input type="radio" name='51305'  value="162827"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51305)) && answers.get(new Integer(51305)).equals("162825")){
%>					<label class="radio">
						سائق الحافلة<input type="radio" name='51305'  value="162825" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						سائق الحافلة<input type="radio" name='51305'  value="162825"/>
					</label>
					</br>
<%				}  
%>
				</div>
	</div>	
	<div>
		<img src="images/lineWebinar.png" style="width: 100%">
	</div>
	<!-- Question 10 ends -->
	<!-- Question 11  -->
			<div class="center2">

				<div>
				<br><label> .11</label>
					<br><label> تعتقد أن سائق السيارة التي أمامك نسي إلغاء المؤشر الأيمن. ماذا  عليك ان تفعل؟</label>
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51306)) && answers.get(new Integer(51306)).equals("162828")){
%>					<label class="radio">
						استعمل وميض الأضواء لتنبيه السائق<input type="radio" name='51306'  value="162828" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						استعمل وميض الأضواء لتنبيه السائق<input type="radio" name='51306'  value="162828"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51306)) && answers.get(new Integer(51306)).equals("162830")){
%>					<label class="radio">
						أطلق البوق قبل تجاوزه<input type="radio" name='51306'  value="162830" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						أطلق البوق قبل تجاوزه<input type="radio" name='51306'  value="162830"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51306)) && answers.get(new Integer(51306)).equals("162829")){
%>					<label class="radio">
						تجاوز على اليسار إذا كان هناك متسع<input type="radio" name='51306'  value="162829" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						تجاوز على اليسار إذا كان هناك متسع<input type="radio" name='51306'  value="162829"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51306)) && answers.get(new Integer(51306)).equals("162831")){
%>					<label class="radio">
						ابق في الخلف ولا تتخطى<input type="radio" name='51306'  value="162831" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						ابق في الخلف ولا تتخطى<input type="radio" name='51306'  value="162831"/>
					</label>
					</br>
<%				}  
%>
				</div>
	</div>	
	<div>
		<img src="images/lineWebinar.png" style="width: 100%">
	</div>
	<!-- Question 11 ends -->
	<!-- Question 12  -->
			<div class="center2">

				<div>
					<br><label> .12</label>
					<br><label>  أنت تقود خلف حافلة فتتوقف عند محطة للحافلات. ماذا عليك ان تفعل؟</label>
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51307)) && answers.get(new Integer(51307)).equals("162833")){
%>					<label class="radio">
						تسارع لتتجاوز الحافلة و تطلق البوق<input type="radio" name='51307'  value="162833" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						تسارع لتتجاوز الحافلة و تطلق البوق<input type="radio" name='51307'  value="162833"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51307)) && answers.get(new Integer(51307)).equals("162835")){
%>					<label class="radio">
						إذا سمحت المساحة ، تجاوز الحافلة بعناية و أنت تراقب المشاة<input type="radio" name='51307'  value="162835" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						إذا سمحت المساحة ، تجاوز الحافلة بعناية و أنت تراقب المشاة<input type="radio" name='51307'  value="162835"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51307)) && answers.get(new Integer(51307)).equals("162834")){
%>					<label class="radio">
						توقف وكن مستعدًا لإفساح المجال للحافلة<input type="radio" name='51307'  value="162834" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						توقف وكن مستعدًا لإفساح المجال للحافلة<input type="radio" name='51307'  value="162834"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51307)) && answers.get(new Integer(51307)).equals("162832")){
%>					<label class="radio">
						توقف على مسافة قريبة خلف الحافلة وانتظر حتى تنطلق<input type="radio" name='51307'  value="162832" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						توقف على مسافة قريبة خلف الحافلة وانتظر حتى تنطلق<input type="radio" name='51307'  value="162832"/>
					</label>
					</br>
<%				}  
%>
				</div>
	</div>	
	<div>
		<img src="images/lineWebinar.png" style="width: 100%">
	</div>
	<!-- Question 12 ends -->
	<!-- Question 13  -->
			<div class="center2">

				<div>
					<br><label> .13</label>
					<br><label>  ماذا يجب أن تفعل عندما تقترب من حافلة تشير إلى بدء التحرك من  محطة الحافلات؟</label>
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51308)) && answers.get(new Integer(51308)).equals("162839")){
%>					<label class="radio">
						تجاوز قبل أن يتحرك<input type="radio" name='51308'  value="162839" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						تجاوز قبل أن يتحرك<input type="radio" name='51308'  value="162839"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51308)) && answers.get(new Integer(51308)).equals("162837")){
%>					<label class="radio">
						اسمح لها بالانسحاب إذا كان ذلك آمنًا<input type="radio" name='51308'  value="162837" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						اسمح لها بالانسحاب إذا كان ذلك آمنًا<input type="radio" name='51308'  value="162837"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51308)) && answers.get(new Integer(51308)).equals("162838")){
%>					<label class="radio">
						استعمل وميض المصابيح الأمامية عند اقترابك<input type="radio" name='51308'  value="162838" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						استعمل وميض المصابيح الأمامية عند اقترابك<input type="radio" name='51308'  value="162838"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51308)) && answers.get(new Integer(51308)).equals("162836")){
%>					<label class="radio">
						قم باعطاء إشارة لليمين ولوح بالحافلة<input type="radio" name='51308'  value="162836" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						قم باعطاء إشارة لليمين ولوح بالحافلة<input type="radio" name='51308'  value="162836"/>
					</label>
					</br>
<%				}  
%>
				</div>
	</div>	
	<div>
		<img src="images/lineWebinar.png" style="width: 100%">
	</div>
	<!-- Question 13 ends -->
	<!-- Question 14  -->
			<div class="center2">

				<div>
					<br><label> .14</label>
					<br><label style="float:right";> ماذا تعنى هذه الاشارة؟</label><br>
					<img style="float:left;margin-left:250px;width:15%;" src="images/1636-14.png" alt="cepa">
				
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51309)) && answers.get(new Integer(51309)).equals("162842")){
%>					<label class="radio">
						تايق في صف واحد<input type="radio" name='51309'  value="162842" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						ايق في صف واحد<input type="radio" name='51309'  value="162842"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51309)) && answers.get(new Integer(51309)).equals("162843")){
%>					<label class="radio">
						صف انتظار على الأرجح<input type="radio" name='51309'  value="162843" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						صف انتظار على الأرجح<input type="radio" name='51309'  value="162843"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51309)) && answers.get(new Integer(51309)).equals("162841")){
%>					<label class="radio">
						حافظ على المسافة الخاصة بك<input type="radio" name='51309'  value="162841" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						حافظ على المسافة الخاصة بك<input type="radio" name='51309'  value="162841"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51309)) && answers.get(new Integer(51309)).equals("162840")){
%>					<label class="radio">
						موقف السيارات<input type="radio" name='51309'  value="162840" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						موقف السيارات<input type="radio" name='51309'  value="162840"/>
					</label>
					</br>
<%				}  
%>
				</div>
	</div>	
	<div>
		<img src="images/lineWebinar.png" style="width: 100%">
	</div>
	<!-- Question 14 ends -->
	<!-- Question 15  -->
			<div class="center2">

				<div>
					<br><label> .15</label>
					<br><label> توقفت للمشاة في انتظار عبورهم عند معبر المشاه. لم يبدأوا في العبور. ماذا عليك ان تفعل؟</label>
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51310)) && answers.get(new Integer(51310)).equals("162847")){
%>					<label class="radio">
						تحلى بالصبر وانتظر<input type="radio" name='51310'  value="162847" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						تحلى بالصبر وانتظر<input type="radio" name='51310'  value="162847"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51310)) && answers.get(new Integer(51310)).equals("162846")){
%>					<label class="radio">
						أطلق البوق<input type="radio" name='51310'  value="162846" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						أطلق البوق<input type="radio" name='51310'  value="162846"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51310)) && answers.get(new Integer(51310)).equals("162845")){
%>					<label class="radio">
						استمرفى السير<input type="radio" name='51310'  value="162845" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						استمرفى السير<input type="radio" name='51310'  value="162845"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51310)) && answers.get(new Integer(51310)).equals("162844")){
%>					<label class="radio">
						لوح لهم للعبور<input type="radio" name='51310'  value="162844" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						لوح لهم للعبور<input type="radio" name='51310'  value="162844"/>
					</label>
					</br>
<%				}  
%>
				</div>
	</div>	
	<div>
		<img src="images/lineWebinar.png" style="width: 100%">
	</div>
	<!-- Question 15 ends -->
	<!-- Question 16  -->
			<div class="center2">

				<div>
					<br><label> .16</label>
					<br><label>  أنت تقود في المدينة. توجد حافلة عند محطة الحافلات على الجانب الآخر من الطريق. لماذا يجب أن تكون حذرا؟</label>
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51311)) && answers.get(new Integer(51311)).equals("162851")){
%>					<label class="radio">
						ربما تعطلت الحافلة<input type="radio" name='51311'  value="162851" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						ربما تعطلت الحافلة<input type="radio" name='51311'  value="162851"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51311)) && answers.get(new Integer(51311)).equals("162848")){
%>					<label class="radio">
						قد يأتي المشاة من خلف الحافلة<input type="radio" name='51311'  value="162848" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						قد يأتي المشاة من خلف الحافلة<input type="radio" name='51311'  value="162848"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51311)) && answers.get(new Integer(51311)).equals("162849")){
%>					<label class="radio">
						قد تتحرك الحافلة فجأة<input type="radio" name='51311'  value="162849" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						قد تتحرك الحافلة فجأة<input type="radio" name='51311'  value="162849"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51311)) && answers.get(new Integer(51311)).equals("162850")){
%>					<label class="radio">
						قد تظل الحافلة ثابتة<input type="radio" name='51311'  value="162850" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						قد تظل الحافلة ثابتة<input type="radio" name='51311'  value="162850"/>
					</label>
					</br>
<%				}  
%>
				</div>
	</div>	
	<div>
		<img src="images/lineWebinar.png" style="width: 100%">
	</div>
	<!-- Question 16 ends -->
	<!-- Question 17  -->
			<div class="center2">

				<div>
					<br><label> .17</label>
					<br><label> يجب أن يكون لديك تأمين صالح قبل أن تتمكن من فعل ماذا؟</label>
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51312)) && answers.get(new Integer(51312)).equals("162855")){
%>					<label class="radio">
						تسجيل سيارتك<input type="radio" name='51312'  value="162855" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						تسجيل سيارتك<input type="radio" name='51312'  value="162855"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51312)) && answers.get(new Integer(51312)).equals("162854")){
%>					<label class="radio">
						شراء أو بيع سيارة<input type="radio" name='51312'  value="162854" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						شراء أو بيع سيارة<input type="radio" name='51312'  value="162854"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51312)) && answers.get(new Integer(51312)).equals("162852")){
%>					<label class="radio">
						التقدم بطلب للحصول على رخصة قيادة<input type="radio" name='51312'  value="162852" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						التقدم بطلب للحصول على رخصة قيادة<input type="radio" name='51312'  value="162852"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51312)) && answers.get(new Integer(51312)).equals("162853")){
%>					<label class="radio">
						استخدام راديو السيارة<input type="radio" name='51312'  value="162853" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						استخدام راديو السيارة<input type="radio" name='51312'  value="162853"/>
					</label>
					</br>
<%				}  
%>
				</div>
	</div>	
	<div>
		<img src="images/lineWebinar.png" style="width: 100%">
	</div>
	<!-- Question 17 ends -->
	<!-- Question 18  -->
			<div class="center2">

				<div>
					<br><label> .18</label>
					<br><label> في الحالة الجيدة ، ما هي مسافة التوقف النموذجية عند 60 كم / ساعة؟</label>
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51313)) && answers.get(new Integer(51313)).equals("162857")){
%>					<label class="radio">
						10.8 أمتار<input type="radio" name='51313'  value="162857" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						10.8 أمتار<input type="radio" name='51313'  value="162857"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51313)) && answers.get(new Integer(51313)).equals("162858")){
%>					<label class="radio">
						32.4 مترا<input type="radio" name='51313'  value="162858" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						32.4 مترا<input type="radio" name='51313'  value="162858"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51313)) && answers.get(new Integer(51313)).equals("162856")){
%>					<label class="radio">
						77.7 مترا<input type="radio" name='51313'  value="162856" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						77.7 مترا<input type="radio" name='51313'  value="162856"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51313)) && answers.get(new Integer(51313)).equals("162859")){
%>					<label class="radio">
						107.5 مترا<input type="radio" name='51313'  value="162859" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						107.5 مترا<input type="radio" name='51313'  value="162859"/>
					</label>
					</br>
<%				}  
%>
				</div>
	</div>	
	<div>
		<img src="images/lineWebinar.png" style="width: 100%">
	</div>
	<!-- Question 18 ends -->
	<!-- Question 19  -->
			<div class="center2">

				<div>
					<br><label> .19</label>
					<br><label>  تجد أنك بحاجة إلى نظارات لقراءة لوحات أرقام المركبات على المسافة المطلوبة. متى يجب عليك ارتدائها؟</label>
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51314)) && answers.get(new Integer(51314)).equals("162862")){
%>					<label class="radio">
						فقط في الظروف الجوية السيئة<input type="radio" name='51314'  value="162862" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						فقط في الظروف الجوية السيئة<input type="radio" name='51314'  value="162862"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51314)) && answers.get(new Integer(51314)).equals("162861")){
%>					<label class="radio">
						في جميع الأوقات عند القيادة<input type="radio" name='51314'  value="162861" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						في جميع الأوقات عند القيادة<input type="radio" name='51314'  value="162861"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51314)) && answers.get(new Integer(51314)).equals("162860")){
%>					<label class="radio">
						فقط عندما تعتقد أنه ضروري<input type="radio" name='51314'  value="162860" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						فقط عندما تعتقد أنه ضروري<input type="radio" name='51314'  value="162860"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51314)) && answers.get(new Integer(51314)).equals("162863")){
%>					<label class="radio">
						فقط في الإضاءة السيئة أو في الليل<input type="radio" name='51314'  value="162863" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						فقط في الإضاءة السيئة أو في الليل<input type="radio" name='51314'  value="162863"/>
					</label>
					</br>
<%				}  
%>
				</div>
	</div>	
	<div>
		<img src="images/lineWebinar.png" style="width: 100%">
	</div>
	<!-- Question 19 ends -->
	<!-- Question 20  -->
			<div class="center2">

				<div>
					<br><label> .20</label>
					<br><label>  لماذا المرايا غالبًا ما تكون منحنية قليلاً (محدبة)؟</label>
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51315)) && answers.get(new Integer(51315)).equals("162864")){
%>					<label class="radio">
						تعطي مجال رؤية أوسع<input type="radio" name='51315'  value="162864" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						تعطي مجال رؤية أوسع<input type="radio" name='51315'  value="162864"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51315)) && answers.get(new Integer(51315)).equals("162867")){
%>					<label class="radio">
						تغطي تماما النقاط العمياء<input type="radio" name='51315'  value="162867" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						تغطي تماما النقاط العمياء<input type="radio" name='51315'  value="162867"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51315)) && answers.get(new Integer(51315)).equals("162865")){
%>					<label class="radio">
						تجعل من السهل الحكم على سرعة حركة المرور التالية <input type="radio" name='51315'  value="162865" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						تجعل من السهل الحكم على سرعة حركة المرور التالية <input type="radio" name='51315'  value="162865"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51315)) && answers.get(new Integer(51315)).equals("162866")){
%>					<label class="radio">
						تجعل حركة المرور التالية تبدو أكبر<input type="radio" name='51315'  value="162866" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						تجعل حركة المرور التالية تبدو أكبر<input type="radio" name='51315'  value="162866"/>
					</label>
					</br>
<%				}  
%>
				</div>
	</div>	
	<div>
		<img src="images/lineWebinar.png" style="width: 100%">
	</div>
	<!-- Question 20 ends -->
	<!-- Question 21  -->
			<div class="center2">

				<div>
					<br><label> .21</label>
					<br><label style="float:right";>  ماذا تعنى هذه الاشارة؟</label><br>
					<img style="float:left;margin-left:250px;width:15%;" src="images/1636-21.png" alt="cepa">
				
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51316)) && answers.get(new Integer(51316)).equals("162871")){
%>					<label class="radio">
						 جسر محدب<input type="radio" name='51316'  value="162871" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						 جسر محدب<input type="radio" name='51316'  value="162871"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51316)) && answers.get(new Integer(51316)).equals("162870")){
%>					<label class="radio">
						مطب صناعى على الطريق<input type="radio" name='51316'  value="162870" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						مطب صناعى على الطريق<input type="radio" name='51316'  value="162870"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51316)) && answers.get(new Integer(51316)).equals("162868")){
%>					<label class="radio">
						مدخل نفق <input type="radio" name='51316'  value="162868" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						مدخل نفق <input type="radio" name='51316'  value="162868"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51316)) && answers.get(new Integer(51316)).equals("162869")){
%>					<label class="radio">
						حواف ناعمة<input type="radio" name='51316'  value="162869" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						حواف ناعمة<input type="radio" name='51316'  value="162869"/>
					</label>
					</br>
<%				}  
%>
				</div>
	</div>	
	<div>
		<img src="images/lineWebinar.png" style="width: 100%">
	</div>
	<!-- Question 21 ends -->
	<!-- Question 22  -->
			<div class="center2">

				<div>
					<br><label> .22</label>
					<br><label> لقد توقفت في مكان الحادث الذي وقع فيه ضحايا. ما الذي يجب عليك فعله للمساعدة أثناء انتظار وصول خدمات الطوارئ؟</label>
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51317)) && answers.get(new Integer(51317)).equals("162874")){
%>					<label class="radio">
						اجعل المصاب دافئًا ومريحًا<input type="radio" name='51317'  value="162874" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						اجعل المصاب دافئًا ومريحًا<input type="radio" name='51317'  value="162874"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51317)) && answers.get(new Integer(51317)).equals("162872")){
%>					<label class="radio">
						إزالة الحطام من الطريق<input type="radio" name='51317'  value="162872" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						إزالة الحطام من الطريق<input type="radio" name='51317'  value="162872"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51317)) && answers.get(new Integer(51317)).equals("162873")){
%>					<label class="radio">
						حافظ على حركة الجرحى من خلال مصاحبتهم فى تجولهم <input type="radio" name='51317'  value="162873" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						حافظ على حركة الجرحى من خلال مصاحبتهم فى تجولهم <input type="radio" name='51317'  value="162873"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51317)) && answers.get(new Integer(51317)).equals("162875")){
%>					<label class="radio">
						أعط المصاب شرابًا دافئًا<input type="radio" name='51317'  value="162875" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						أعط المصاب شرابًا دافئًا<input type="radio" name='51317'  value="162875"/>
					</label>
					</br>
<%				}  
%>
				</div>
	</div>	
	<div>
		<img src="images/lineWebinar.png" style="width: 100%">
	</div>
	<!-- Question 22 ends -->
	<!-- Question 23  -->
			<div class="center2">

				<div>
					<br><label> .23</label>
					<br><label> عندما تتبعك سيارة إسعاف تظهر ضوء أزرق وامض ، يجب عليك أن</label>
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51318)) && answers.get(new Integer(51318)).equals("162877")){
%>					<label class="radio">
						تتوقف في أسرع وقت ممكن بأمان للسماح لها بالمرور<input type="radio" name='51318'  value="162877" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						تتوقف في أسرع وقت ممكن بأمان للسماح لها بالمرورا<input type="radio" name='51318'  value="162877"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51318)) && answers.get(new Integer(51318)).equals("162879")){
%>					<label class="radio">
						الاسراع بأقصى ما يمكن للابتعاد عنها<input type="radio" name='51318'  value="162879" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						الاسراع بأقصى ما يمكن للابتعاد عنها<input type="radio" name='51318'  value="162879"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51318)) && answers.get(new Integer(51318)).equals("162876")){
%>					<label class="radio">
						حافظ على سرعتك ومسارك <input type="radio" name='51318'  value="162876" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						حافظ على سرعتك ومسارك <input type="radio" name='51318'  value="162876"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51318)) && answers.get(new Integer(51318)).equals("162878")){
%>					<label class="radio">
						اضغط الفرامل بشدة و توقف على الطريق فورا<input type="radio" name='51318'  value="162878" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						اضغط الفرامل بشدة و توقف على الطريق فورا<input type="radio" name='51318'  value="162878"/>
					</label>
					</br>
<%				}  
%>
				</div>
	</div>	
	<div>
		<img src="images/lineWebinar.png" style="width: 100%">
	</div>
	<!-- Question 23 ends -->
	<!-- Question 24  -->
			<div class="center2">

				<div>
					<br><label> .24</label>
					<br><label> سيارتك مزودة بمكابح مانعة للانغلاق. تحتاج إلى التوقف في حالة طوارئ. ماذا عليك ان تفعل؟</label>
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51319)) && answers.get(new Integer(51319)).equals("162881")){
%>					<label class="radio">
						الفرامل بشكل طبيعي وتجنب قلب عجلة القيادة<input type="radio" name='51319'  value="162881" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						الفرامل بشكل طبيعي وتجنب قلب عجلة القيادة<input type="radio" name='51319'  value="162881"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51319)) && answers.get(new Integer(51319)).equals("162883")){
%>					<label class="radio">
						اضغط على دواسة الفرامل بسرعة وبقوة حتى تتوقف<input type="radio" name='51319'  value="162883" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						اضغط على دواسة الفرامل بسرعة وبقوة حتى تتوقف<input type="radio" name='51319'  value="162883"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51319)) && answers.get(new Integer(51319)).equals("162880")){
%>					<label class="radio">
						استمر في دفع مكابح القدم وتحريرها بسرعة لمنع الانزلاق <input type="radio" name='51319'  value="162880" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						استمر في دفع مكابح القدم وتحريرها بسرعة لمنع الانزلاق <input type="radio" name='51319'  value="162880"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51319)) && answers.get(new Integer(51319)).equals("162882")){
%>					<label class="radio">
						استخدم فرملة اليد لتقليل مسافة التوقف<input type="radio" name='51319'  value="162882" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						استخدم فرملة اليد لتقليل مسافة التوقف<input type="radio" name='51319'  value="162882"/>
					</label>
					</br>
<%				}  
%>
				</div>
	</div>	
	<div>
		<img src="images/lineWebinar.png" style="width: 100%">
	</div>
	<!-- Question 24 ends -->
	<!-- Question 25  -->
			<div class="center2">

				<div>
					<br><label> .25</label>
					<br><label style="float:right";> ماذا تعنى هذه الاشارة ؟</label><br>
					<img style="float:left;margin-left:250px;width:15%;" src="images/1636-25.png" alt="cepa">
				
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51320)) && answers.get(new Integer(51320)).equals("162887")){
%>					<label class="radio">
						ممنوع الوقوف<input type="radio" name='51320'  value="162887" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						ممنوع الوقوف<input type="radio" name='51320'  value="162887"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51320)) && answers.get(new Integer(51320)).equals("162884")){
%>					<label class="radio">
						ممنوع الدخول<input type="radio" name='51320'  value="162884" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						ممنوع الدخول<input type="radio" name='51320'  value="162884"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51320)) && answers.get(new Integer(51320)).equals("162886")){
%>					<label class="radio">
						ممنوع الانتظار<input type="radio" name='51320'  value="162886" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						ممنوع الانتظار <input type="radio" name='51320'  value="162886"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51320)) && answers.get(new Integer(51320)).equals("162885")){
%>					<label class="radio">
						ممنوع السيارات<input type="radio" name='51320'  value="162885" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						ممنوع السيارات<input type="radio" name='51320'  value="162885"/>
					</label>
					</br>
<%				}  
%>
				</div>
	</div>	
	<div>
		<img src="images/lineWebinar.png" style="width: 100%">
	</div>
	<!-- Question 25 ends -->
	<!-- Question 26  -->
			<div class="center2">

				<div>
					<br><label> .26</label>
					<br><label> أنت تسافر تحت أمطار غزيرة للغاية. ما هي مسافة التوقف الإجمالية التي يحتمل أن تقارن بالقيادة على طريق جاف؟</label>
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51321)) && answers.get(new Integer(51321)).equals("162890")){
%>					<label class="radio">
						الضعف<input type="radio" name='51321'  value="162890" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						الضعف<input type="radio" name='51321'  value="162890"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51321)) && answers.get(new Integer(51321)).equals("162891")){
%>					<label class="radio">
						النصف<input type="radio" name='51321'  value="162891" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						النصف<input type="radio" name='51321'  value="162891"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51321)) && answers.get(new Integer(51321)).equals("162888")){
%>					<label class="radio">
						تصل إلى عشر مرات أكبر<input type="radio" name='51321'  value="162888" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						تصل إلى عشر مرات أكبر <input type="radio" name='51321'  value="162888"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51321)) && answers.get(new Integer(51321)).equals("162889")){
%>					<label class="radio">
						لا فرق<input type="radio" name='51321'  value="162889" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						لا فرق<input type="radio" name='51321'  value="162889"/>
					</label>
					</br>
<%				}  
%>
				</div>
	</div>	
	<div>
		<img src="images/lineWebinar.png" style="width: 100%">
	</div>
	<!-- Question 26 ends -->
	<!-- Question 27  -->
			<div class="center2">

				<div>
					<br><label> .27</label>
					<br><label> أنت تقود أسفل تل طويل شديد الانحدار. لاحظت فجأة أن الفرامل لا تعمل بشكل طبيعي. ماذا تفعل؟</label>
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51322)) && answers.get(new Integer(51322)).equals("162895")){
%>					<label class="radio">
						الفرامل بقوة أكبر<input type="radio" name='51322'  value="162895" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						الفرامل بقوة أكبر<input type="radio" name='51322'  value="162895"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51322)) && answers.get(new Integer(51322)).equals("162892")){
%>					<label class="radio">
						قم بالتغيير إلى سرعة أقل<input type="radio" name='51322'  value="162892" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						قم بالتغيير إلى سرعة أقل<input type="radio" name='51322'  value="162892"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51322)) && answers.get(new Integer(51322)).equals("162894")){
%>					<label class="radio">
						استخدم فرامل الانتظار<input type="radio" name='51322'  value="162894" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						استخدم فرامل الانتظار<input type="radio" name='51322'  value="162894"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51322)) && answers.get(new Integer(51322)).equals("162893")){
%>					<label class="radio">
						ضع أضواء الخطر وأطلق البوق<input type="radio" name='51322'  value="162893" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						ضع أضواء الخطر وأطلق البوق<input type="radio" name='51322'  value="162893"/>
					</label>
					</br>
<%				}  
%>
				</div>
	</div>	
	<div>
		<img src="images/lineWebinar.png" style="width: 100%">
	</div>
	<!-- Question 27 ends -->
	<!-- Question 28  -->
			<div class="center2">

				<div>
					<br><label> .28</label>
					<br><label> أنت تقترب من معبر للمشاة حيث ينتظر المشاة العبور؟</label>
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51323)) && answers.get(new Integer(51323)).equals("162899")){
%>					<label class="radio">
						تجاهلهم<input type="radio" name='51323'  value="162899" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						تجاهلهم<input type="radio" name='51323'  value="162899"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51323)) && answers.get(new Integer(51323)).equals("162896")){
%>					<label class="radio">
						أطلق البوق وتجاهلهم<input type="radio" name='51323'  value="162896" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						أطلق البوق وتجاهلهم<input type="radio" name='51323'  value="162896"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51323)) && answers.get(new Integer(51323)).equals("162898")){
%>					<label class="radio">
						توقف ودعهم يعبرون<input type="radio" name='51323'  value="162898" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						توقف ودعهم يعبرون<input type="radio" name='51323'  value="162898"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51323)) && answers.get(new Integer(51323)).equals("162897")){
%>					<label class="radio">
						تحرك ببطء ، قد يعبرون<input type="radio" name='51323'  value="162897" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						تحرك ببطء ، قد يعبرون<input type="radio" name='51323'  value="162897"/>
					</label>
					</br>
<%				}  
%>
				</div>
	</div>	
	<div>
		<img src="images/lineWebinar.png" style="width: 100%">
	</div>
	<!-- Question 28 ends -->
	<!-- Question 29  -->
			<div class="center2">

				<div>
					<br><label> .29</label>
					<br><label> أنت تقود على طول طريق مزدحم وتعتزم الانعطاف إلى اليسار ، فماذا يجب أن تفعل أولاً؟</label>
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51324)) && answers.get(new Integer(51324)).equals("162900")){
%>					<label class="radio">
						الفرامل<input type="radio" name='51324'  value="162900" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						الفرامل<input type="radio" name='51324'  value="162900"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51324)) && answers.get(new Integer(51324)).equals("162902")){
%>					<label class="radio">
						تغيير المسار<input type="radio" name='51324'  value="162902" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						تغيير المسار<input type="radio" name='51324'  value="162902"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51324)) && answers.get(new Integer(51324)).equals("162901")){
%>					<label class="radio">
						افحص المرآة<input type="radio" name='51324'  value="162901" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						افحص المرآة<input type="radio" name='51324'  value="162901"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51324)) && answers.get(new Integer(51324)).equals("162903")){
%>					<label class="radio">
						الإشارة<input type="radio" name='51324'  value="162903" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						الإشارة<input type="radio" name='51324'  value="162903"/>
					</label>
					</br>
<%				}  
%>
				</div>
	</div>	
	<div>
		<img src="images/lineWebinar.png" style="width: 100%">
	</div>
	<!-- Question 29 ends -->
	<!-- Question 30  -->
			<div class="center2">

				<div>
					<br><label > .30</label>
					<br><label style="float:right";>  ماذا تعني هذه الإشارة؟</label><br>
					<img style="float:left;margin-left:250px;width:15%;" src="images/1636-30.png" alt="cepa">
				
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51325)) && answers.get(new Integer(51325)).equals("162905")){
%>					<label class="radio">
						حصى سائب<input type="radio" name='51325'  value="162905" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						حصى سائب<input type="radio" name='51325'  value="162905"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51325)) && answers.get(new Integer(51325)).equals("162907")){
%>					<label class="radio">
						موقع أثري<input type="radio" name='51325'  value="162907" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						موقع أثري<input type="radio" name='51325'  value="162907"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51325)) && answers.get(new Integer(51325)).equals("162904")){
%>					<label class="radio">
						الطريق مغلق<input type="radio" name='51325'  value="162904" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						الطريق مغلق<input type="radio" name='51325'  value="162904"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51325)) && answers.get(new Integer(51325)).equals("162906")){
%>					<label class="radio">
						حجر متساقط<input type="radio" name='51325'  value="162906" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						حجر متساقط<input type="radio" name='51325'  value="162906"/>
					</label>
					</br>
<%				}  
%>
				</div>
	</div>	
	<div>
		<img src="images/lineWebinar.png" style="width: 100%">
	</div>
	<!-- Question 30 ends -->
				<div align="center">
			       	<html:submit value='أدخل' styleClass="button"/>
			       	<input type="button" value='تسجيل خروج' class="buttonRed" onclick="javascript:document.forms['logout'].submit();"/>
				</div>
			</html:form>
		</section>
   </body> 
</html>