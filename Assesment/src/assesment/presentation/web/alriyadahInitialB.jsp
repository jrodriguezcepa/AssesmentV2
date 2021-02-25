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
		  float: right;
		  width: 20%;
		  padding: 5px;
		}
		
		/* Clear floats after image containers */
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
				<label><h4>اختبار المعرفة (B)</h4></label>
			</div>
			<html:form action="/AlRiyadahSaveAnswers">
				<html:hidden property="assesment" value='<%=String.valueOf(AssesmentData.ALRIYADAH_INITIALB)%>'/>
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
					<br><label> يزعجك ابهار أنوار سيارة خلفك في الليل. ماذا عليك ان تفعل؟</label>
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51286)) && answers.get(new Integer(51286)).equals("162750")){
%>					<label class="radio">
						ضع المرآة في وضع عدم الانبهار<input type="radio" name='51286'  value="162750" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						ضع المرآة في وضع عدم الانبهار<input type="radio" name='51286'  value="162750"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51286)) && answers.get(new Integer(51286)).equals("162749")){
%>					<label class="radio">
						ضع مرآتك لإبهار السائق الآخر<input type="radio" name='51286'  value="162749" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						ضع مرآتك لإبهار السائق الآخر<input type="radio" name='51286'  value="162749"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51286)) && answers.get(new Integer(51286)).equals("162748")){
%>					<label class="radio">
						اضغط الفرامل بحدة حتى التوقف<input type="radio" name='51286'  value="162748" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						اضغط الفرامل بحدة حتى التوقف<input type="radio" name='51286'  value="162748"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51286)) && answers.get(new Integer(51286)).equals("162751")){
%>					<label class="radio">
						قم بتشغيل وإطفاء المصابيح الخلفية<input type="radio" name='51286'  value="162751" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						قم بتشغيل وإطفاء المصابيح الخلفية<input type="radio" name='51286'  value="162751"/>
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
					<br><label> ما هو نمط القيادة الذي يسبب زيادة المخاطر على الجميع؟	</label>
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51287)) && answers.get(new Integer(51287)).equals("162754")){
%>					<label class="radio">
						الدفاعي<input type="radio" name='51287'  value="162754" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						الدفاعي<input type="radio" name='51287'  value="162754"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51287)) && answers.get(new Integer(51287)).equals("162755")){
%>					<label class="radio">
						المسؤول<input type="radio" name='51287'  value="162755" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						المسؤول<input type="radio" name='51287'  value="162755"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51287)) && answers.get(new Integer(51287)).equals("162752")){
%>					<label class="radio">
						المنافس<input type="radio" name='51287'  value="162752" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						المنافس<input type="radio" name='51287'  value="162752"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51287)) && answers.get(new Integer(51287)).equals("162753")){
%>					<label class="radio">
						المراعي<input type="radio" name='51287'  value="162753" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						المراعي<input type="radio" name='51287'  value="162753"/>
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
					<br><label> كيف يمكنك تقليل الضرر الذي تسببه مركبتك على البيئة؟</label>
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51288)) && answers.get(new Integer(51288)).equals("162758")){
%>					<label class="radio">
						ضغط الفرامل بشدة<input type="radio" name='51288'  value="162758" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						ضغط الفرامل بشدة<input type="radio" name='51288'  value="162758"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51288)) && answers.get(new Integer(51288)).equals("162756")){
%>					<label class="radio">
						استخدام الشوارع الجانبية الضيقة<input type="radio" name='51288'  value="162756" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						استخدام الشوارع الجانبية الضيقة<input type="radio" name='51288'  value="162756"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51288)) && answers.get(new Integer(51288)).equals("162757")){
%>					<label class="radio">
						استخدام الطرق المزدحمة<input type="radio" name='51288'  value="162757" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						استخدام الطرق المزدحمة<input type="radio" name='51288'  value="162757"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51288)) && answers.get(new Integer(51288)).equals("162759")){
%>					<label class="radio">
						التوقع الجيدًا لما يمكن ان يحدث تاليا<input type="radio" name='51288'  value="162759" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						التوقع الجيدًا لما يمكن ان يحدث تاليا<input type="radio" name='51288'  value="162759"/>
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
					<br><label>أ ت تسافر تحت أمطار غزيرة للغاية. كيف من المحتمل أن يؤثر ذلك على مسافة التوقف الإجمالية؟ </label>
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51289)) && answers.get(new Integer(51289)).equals("162762")){
%>					<label class="radio">
						لن يكون مختلفا<input type="radio" name='51289'  value="162762" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						لن يكون مختلفا<input type="radio" name='51289'  value="162762"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51289)) && answers.get(new Integer(51289)).equals("162761")){
%>					<label class="radio">
						سيكون النصف<input type="radio" name='51289'  value="162761" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						سيكون النصف<input type="radio" name='51289'  value="162761"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51289)) && answers.get(new Integer(51289)).equals("162763")){
%>					<label class="radio">
						سيكون أكبر بعشر مرات<input type="radio" name='51289'  value="162763" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						سيكون أكبر بعشر مرات<input type="radio" name='51289'  value="162763"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51289)) && answers.get(new Integer(51289)).equals("162760")){
%>					<label class="radio">
						سوف يتضاعف<input type="radio" name='51289'  value="162760" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						سوف يتضاعف<input type="radio" name='51289'  value="162760"/>
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
					<br><label style="float:right";> ماذا تعني هذه العلامة؟</label><br>
					<img style="float:left;margin-left:300px;width:20%;" src="images/1635-5.png" alt="cepa">
				
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51290)) && answers.get(new Integer(51290)).equals("162765")){
%>					<label class="radio">
						جسر محدب<input type="radio" name='51290'  value="162765" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						جسر محدب<input type="radio" name='51290'  value="162765"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51290)) && answers.get(new Integer(51290)).equals("162764")){
%>					<label class="radio">
						مطب على الطريق<input type="radio" name='51290'  value="162764" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						مطب على الطريق<input type="radio" name='51290'  value="162764"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51290)) && answers.get(new Integer(51290)).equals("162766")){
%>					<label class="radio">
						مدخل النفق<input type="radio" name='51290'  value="162766" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						مدخل النفق<input type="radio" name='51290'  value="162766"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51290)) && answers.get(new Integer(51290)).equals("162767")){
%>					<label class="radio">
						حواف ناعمة<input type="radio" name='51290'  value="162767" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						حواف ناعمة<input type="radio" name='51290'  value="162767"/>
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
					<br><label> علامات التحذير المرورية بشكل عام أي شكل؟</label>
				</div>
				<!-- Option 1 -->
				<div>

				<div class="row">
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51291)) && answers.get(new Integer(51291)).equals("162768")){
%>					<div class="column">
						<label class="radio">
							<img style="width:50%;" src="images/1634-3-4.png" alt="cepa">
							<input type="radio" name='51291'  value="162768" checked/>
						</label>
					</div>
<%				}else{
%>					<div class="column">
						<label class="radio">
							<img  style="width:50%;" src="images/1634-3-4.png" alt="cepa">
							<input type="radio" name='51291'  value="162768"/>
						</label>
					</div>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51291)) && answers.get(new Integer(51291)).equals("162769")){
%>					<div class="column">
						<label class="radio">
							<img style="width:50%;" src="images/1634-3-3.png" alt="cepa">
							<input type="radio" name='51291'  value="162769" checked/>
						</label>
					</div>
<%				}else{
%>					<div class="column">
						<label class="radio">
							<img  style="width:50%;" src="images/1634-3-3.png" alt="cepa">
							<input type="radio" name='51291'  value="162769"/>
						</label>
					</div>
<%				}  
%>		
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51291)) && answers.get(new Integer(51291)).equals("162770")){
%>					<div class="column">
						<label class="radio">
							<img style="width:50%;" src="images/1634-3-2.png" alt="cepa">
							<input type="radio" name='51291'  value="162770" checked/>
						</label>
					</div>
<%				}else{
%>					<div class="column">
						<label class="radio">
							<img  style="width:50%;" src="images/1634-3-2.png" alt="cepa">
							<input type="radio" name='51291'  value="162770"/>
						</label>
					</div>
<%				}  
%>				
			<!-- Option 1 -->
<%				if(answers.containsKey(new Integer(51291)) && answers.get(new Integer(51291)).equals("162771")){
%>					<div class="column">
						<label class="radio">
							<img style="width:72%;margin-top:20px;" src="images/1634-3-1.png" alt="cepa">
							<input type="radio" name='51291'  value="162771" checked/>
						</label>
					</div>
<%				}else{
%>					<div class="column">
						<label class="radio">
							<img  style="width:72%;margin-top:20px;" src="images/1634-3-1.png" alt="cepa">
							<input type="radio" name='51291'  value="162771"/>
						</label>
					</div>
<%				}  
%>			

				</div>
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
					<br><label> أنت تقود بالسرعة المحددة للطريق. هناك سائق خلفك يحاول التجاوز ، ماذا عليك فعله ؟</label>
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51292)) && answers.get(new Integer(51292)).equals("162772")){
%>					<label class="radio">
						اقترب أكثر من السيارة التي أمامك ، حتى لا يكون للسائق خلفك مجال للتجاوز<input type="radio" name='51292'  value="162772" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						اقترب أكثر من السيارة التي أمامك ، حتى لا يكون للسائق خلفك مجال للتجاوز<input type="radio" name='51292'  value="162772"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51292)) && answers.get(new Integer(51292)).equals("162775")){
%>					<label class="radio">
						لوح بيدك للسائق بالخلف للتجاوز عندما يكون الوضع آمنًا<input type="radio" name='51292'  value="162775" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						لوح بيدك للسائق بالخلف للتجاوز عندما يكون الوضع آمنًا<input type="radio" name='51292'  value="162775"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51292)) && answers.get(new Integer(51292)).equals("162773")){
%>					<label class="radio">
						حافظ على مسار ثابت واسمح للسائق بالخلف بالتجاوز<input type="radio" name='51292'  value="162773" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						حافظ على مسار ثابت واسمح للسائق بالخلف بالتجاوز<input type="radio" name='51292'  value="162773"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51292)) && answers.get(new Integer(51292)).equals("162774")){
%>					<label class="radio">
						ازد السرعة للابتعاد عن السائق خلفك<input type="radio" name='51292'  value="162774" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						ازد السرعة للابتعاد عن السائق خلفك<input type="radio" name='51292'  value="162774"/>
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
					<br><label> أنت تقود على طريق مبلل. عليك إيقاف مركبتك في حالة الطوارئ. ماذا عليك ان تفعل؟</label>
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51293)) && answers.get(new Integer(51293)).equals("162776")){
%>					<label class="radio">
						ضع فرملة اليد وفرملة القدم معًا<input type="radio" name='51293'  value="162776" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						ضع فرملة اليد وفرملة القدم معًا<input type="radio" name='51293'  value="162776"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51293)) && answers.get(new Integer(51293)).equals("162778")){
%>					<label class="radio">
						ضع كلتا يديك على عجلة القيادة<input type="radio" name='51293'  value="162778" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						ضع كلتا يديك على عجلة القيادة<input type="radio" name='51293'  value="162778"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51293)) && answers.get(new Integer(51293)).equals("162777")){
%>					<label class="radio">
						حدد السرعة العكسية<input type="radio" name='51293'  value="162777" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						حدد السرعة العكسية<input type="radio" name='51293'  value="162777"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51293)) && answers.get(new Integer(51293)).equals("162779")){
%>					<label class="radio">
						أعط إشارة ذراع<input type="radio" name='51293'  value="162779" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						أعط إشارة ذراع<input type="radio" name='51293'  value="162779"/>
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
					<br><label style="float:right";> ترى هذه العلامة في امامك. ماذا تتوقع؟</label><br>
					<img style="float:left;margin-left:80px;width:35%;" src="images/1635-9.png" alt="cepa">
									
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51294)) && answers.get(new Integer(51294)).equals("162782")){
%>					<label class="radio">
						سيذهب الطريق صعودًا بشكل حاد<input type="radio" name='51294'  value="162782" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						سيذهب الطريق صعودًا بشكل حاد<input type="radio" name='51294'  value="162782"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51294)) && answers.get(new Integer(51294)).equals("162780")){
%>					<label class="radio">
						الطريق سوف ينحدر بشكل حاد<input type="radio" name='51294'  value="162780" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						الطريق سوف ينحدر بشكل حاد<input type="radio" name='51294'  value="162780"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51294)) && answers.get(new Integer(51294)).equals("162781")){
%>					<label class="radio">
						سوف ينحني الطريق بحدة إلى اليسار<input type="radio" name='51294'  value="162781" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						سوف ينحني الطريق بحدة إلى اليسار<input type="radio" name='51294'  value="162781"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51294)) && answers.get(new Integer(51294)).equals("162783")){
%>					<label class="radio">
						سوف ينحني الطريق بحدة إلى اليمين<input type="radio" name='51294'  value="162783" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						سوف ينحني الطريق بحدة إلى اليمين<input type="radio" name='51294'  value="162783"/>
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
					<br><label> متى ستشعر بآثار فرملة المحرك؟</label>
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51295)) && answers.get(new Integer(51295)).equals("162784")){
%>					<label class="radio">
						عند استخدام فرملة اليد فقط<input type="radio" name='51295'  value="162784" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						عند استخدام فرملة اليد فقط<input type="radio" name='51295'  value="162784"/>
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51295)) && answers.get(new Integer(51295)).equals("162785")){
%>					<label class="radio">
						عندما تكون في موقف محايد<input type="radio" name='51295'  value="162785" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						عندما تكون في موقف محايد<input type="radio" name='51295'  value="162785"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51295)) && answers.get(new Integer(51295)).equals("162786")){
%>					<label class="radio">
						عند التغيير إلى ترس أقل<input type="radio" name='51295'  value="162786" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						عند التغيير إلى ترس أقل<input type="radio" name='51295'  value="162786"/>
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51295)) && answers.get(new Integer(51295)).equals("162787")){
%>					<label class="radio">
						عند التغيير إلى ترس أعلى<input type="radio" name='51295'  value="162787" checked/>
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						عند التغيير إلى ترس أعلى<input type="radio" name='51295'  value="162787"/>
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
		</section>
			
				<br>
				<div align="center">
			       	<html:submit value='أدخل' styleClass="button"/>
			       	<input type="button" value='تسجيل خروج' class="buttonRed" onclick="javascript:document.forms['logout'].submit();"/>
				</div>
			</html:form>
		</section>
   </body> 
</html>