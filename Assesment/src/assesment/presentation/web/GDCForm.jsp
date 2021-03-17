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
			text-align:left;
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
				<label><h4>ИТОГОВЫЙ КОНТРОЛЬ ДЛЯ ТРЕНЕРОВ Опросный бланк</h4></label>
			</div>
			<html:form action="/AlRiyadahSaveAnswers">
				<html:hidden property="assesment" value='<%=String.valueOf(AssesmentData.ALRIYADAH_INITIALA)%>'/>
				<html:hidden property="user" value='<%=userData.getLoginName()%>'/>

				<section>
				<div class="center2"> 
					</br>
			
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
					
					<br><label>1. Какими качествами должен обладать тренер? </label>
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51636)) && answers.get(new Integer(51636)).equals("163773")){
%>					<label class="radio">
						<input type="radio" name='51636'  value="163773" checked/>Иметь водительское удостоверение профессионального водителя, навык
						управлять автомобилями.
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51636'  value="163773"/>Иметь водительское удостоверение профессионального водителя, навык
						управлять автомобилями.
					</label>
					</br>
<%				}  
%>				
				<!-- Option 2 -->
<%				if(answers.containsKey(new Integer(51636)) && answers.get(new Integer(51636)).equals("163774")){
%>					<label class="radio">
						<input type="radio" name='51636'  value="163774" checked/>Иметь опыт управления автомобилями, навыки планирования, исполнения и
						оценки, говорить ясно.
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51636'  value="163774"/>Иметь опыт управления автомобилями, навыки планирования, исполнения и
						оценки, говорить ясно.
					</label>
					</br>
<%				}  
%>
				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51636)) && answers.get(new Integer(51636)).equals("163772")){
%>					<label class="radio">
						<input type="radio" name='51636'  value="163772" checked/>Быть способным к постоянному
						самосовершенствованию.
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51636'  value="163772"/>Быть способным к постоянному
						самосовершенствованию.
					</label>
					</br>
<%				}  
%>
				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51636)) && answers.get(new Integer(51636)).equals("163775")){
%>					<label class="radio">
						<input type="radio" name='51636'  value="163775" checked/>Уметь активно слушать, быть способным вести
						диалог.
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51636'  value="163775"/>Уметь активно слушать, быть способным вести
						диалог.
					</label>
					</br>
<%				}  
%>
				<!-- Option 5 -->
<%				if(answers.containsKey(new Integer(51636)) && answers.get(new Integer(51636)).equals("163771")){
%>					<label class="radio">
						<input type="radio" name='51636'  value="163771" checked/>Иметь знание всех технических элементов в автомобиле и навык обращения с ними.
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51636'  value="163771"/>Иметь знание всех технических элементов в автомобиле и навык обращения с ними.
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
					<br><label> 2. Какой из следующих вариантов лучше всего описывает составляющие,
						позволяющие Тренеру эффективнее влиять на водителя?</label>
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51637)) && answers.get(new Integer(51637)).equals("163777")){
%>					<label class="radio">
						<input type="radio" name='51637'  value="163777" checked/>Его внешний вид, позитивный настрой, ответственность, способность понимать
						потребности участников, самодисциплина, навыки работы, твердое знание
						программы.
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51637'  value="163777"/>Его внешний вид, позитивный настрой, ответственность, способность понимать
						потребности участников, самодисциплина, навыки работы, твердое знание
						программы.
					</label>
					</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51637)) && answers.get(new Integer(51637)).equals("163776")){
%>					<label class="radio">
						<input type="radio" name='51637'  value="163776" checked/>Только знания, передаваемые в установленном порядке.
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51637'  value="163776"/>Только знания, передаваемые в установленном порядке.
					</label>
					</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51637)) && answers.get(new Integer(51637)).equals("163778")){
%>					<label class="radio">
						<input type="radio" name='51637'  value="163778" checked/>Технический уровень и совершенные знания того, как управлять автомобилем
						в различных обстоятельствах.
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51637'  value="163778"/>Технический уровень и совершенные знания того, как управлять автомобилем
						в различных обстоятельствах.
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
			<br><label>3. Эффективная коммуникация во время обучения это:</label>
		</div>
		<!-- Option 1 -->
		<div>
<%				if(answers.containsKey(new Integer(51638)) && answers.get(new Integer(51638)).equals("163781")){
%>			<label class="radio">
					<input type="radio" name='51638'  value="163781" checked/>Процесс взаимодействия, через который Тренер передает идеи, чувства,
					знания, навыки и нормы поведения и получает соответствующую ответную
					реакцию на эти стимулы.
			</label>
			</br>	
<%				}else{
%>			<label class="radio">
					<input type="radio" name='51638'  value="163781"/>Процесс взаимодействия, через который Тренер передает идеи, чувства,
					знания, навыки и нормы поведения и получает соответствующую ответную
					реакцию на эти стимулы.
			</label>
			</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51638)) && answers.get(new Integer(51638)).equals("163780")){
%>			<label class="radio">
				<input type="radio" name='51638'  value="163780" checked/>Процесс, посредством которого Тренер передает практические, теоретические
				знания и в свою очередь, получает ответные практические действия.
			</label>
			</br>	
<%				}else{
%>			<label class="radio">
				<input type="radio" name='51638'  value="163780"/>Процесс, посредством которого Тренер передает практические, теоретические
				знания и в свою очередь, получает ответные практические действия.
			</label>
			</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51638)) && answers.get(new Integer(51638)).equals("163779")){
%>			<label class="radio">
				<input type="radio" name='51638'  value="163779" checked/>Процесс, посредством которого Тренер передает навыки, практические
				теоретические знания и навыки, получая, в свою очередь, быстрый ответ на
				вопрос.
			</label>
			</br>	
<%				}else{
%>			<label class="radio">
				<input type="radio" name='51638'  value="163779"/>Процесс, посредством которого Тренер передает навыки, практические
				теоретические знания и навыки, получая, в свою очередь, быстрый ответ на
				вопрос.
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
			<br><label>Два фактора, часто вызывающие смерть на дорогах, это:</label>
		</div>
		<!-- Option 1 -->
		<div>
<%				if(answers.containsKey(new Integer(1111)) && answers.get(new Integer(1111)).equals("1111")){
%>			<label class="radio">
				<input type="radio" name='11111'  value="1111" checked/>Плохие погодные условия
			</label>
			</br>	
<%				}else{
%>			<label class="radio">
				<input type="radio" name='11111'  value="11111"/>Плохие погодные условия
			</label>
			</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(11111)) && answers.get(new Integer(11111)).equals("11111")){
%>			<label class="radio">
				<input type="radio" name='11111'  value="11111" checked/>Плохая дорога
			</label>
			</br>	
<%				}else{
%>			<label class="radio">
				<input type="radio" name='11111'  value="11111"/>Плохая дорога
			</label>
			</br>
<%				}  
%>		<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(11111)) && answers.get(new Integer(11111)).equals("11111")){
%>			<label class="radio">
				<input type="radio" name='11111'  value="11111" checked/>Высокая скорость
			</label>
			</br>	
<%				}else{
%>			<label class="radio">
				<input type="radio" name='11111'  value="11111"/>Высокая скорость
			</label>
			</br>
<%				}

%>
		<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(11111)) && answers.get(new Integer(11111)).equals("11111")){
	%>			<label class="radio">
					<input type="radio" name='11111'  value="11111" checked/>Пренебрежение средствами безопасности
				</label>
				</br>	
	<%				}else{
	%>			<label class="radio">
					<input type="radio" name='11111'  value="11111"/>Пренебрежение средствами безопасности
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
					<br><label> 5. Эмпатия означает:</label>
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51640)) && answers.get(new Integer(51640)).equals("163788")){
%>					<label class="radio">
						<input type="radio" name='51640'  value="163788" checked/>способность достичь сострадания и дружеского внимания от водителя.
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51640'  value="163788"/>способность достичь сострадания и дружеского внимания от водителя.
					</label>
					</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51640)) && answers.get(new Integer(51640)).equals("163787")){
%>					<label class="radio">
						<input type="radio" name='51640'  value="163787" checked/>способность или процесс получить и использовать знания водителя для
						достижения целей обучения.
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51640'  value="163787"/>способность или процесс получить и использовать знания водителя для
						достижения целей обучения.
					</label>
					</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51640)) && answers.get(new Integer(51640)).equals("163789")){
%>					<label class="radio">
						<input type="radio" name='51640'  value="163789" checked/>способность или процесс распознать и проникнуться чувствами, требованиями и
						эмоциями водителя и использовать их для того, чтобы создать идеальную для
						обучения обстановку.
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51640'  value="163789"/>способность или процесс распознать и проникнуться чувствами, требованиями и
						эмоциями водителя и использовать их для того, чтобы создать идеальную для
						обучения обстановку.
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
					<br><label>6. Тренер постоянно коммуницирует с водителем</label>
					
				</div>
				<!-- Option 1 -->
				<div>
<%				if(answers.containsKey(new Integer(51641)) && answers.get(new Integer(51641)).equals("163793")){
%>					<label class="radio">
						<input type="radio" name='51641'  value="163793" checked/>Через слова и язык жестов
					</label>
					</br>	
<%				}else{
%>					<label class="radio" >
						<input type="radio" name='51641'  value="163793"/>Через слова и язык жестов
					</label>
					</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51641)) && answers.get(new Integer(51641)).equals("163791")){
%>					<label class="radio" >
						<input type="radio" name='51641'  value="163791" checked/>Преимущественно посредством слов
					</label>
					</br>	
<%				}else{
%>					<label class="radio" >
						<input type="radio" name='51641'  value="163791"/>Преимущественно посредством слов
					</label>
					</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51641)) && answers.get(new Integer(51641)).equals("163790")){
%>					<label class="radio" >
						<input type="radio" name='51641'  value="163790" checked/>Главным образом через язык жестов
					</label>
					</br>	
<%				}else{
%>					<label class="radio" >
						<input type="radio" name='51641'  value="163790"/>Главным образом через язык жестов
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
			<br><label>7. Из всех каналов, по которым идут процессы восприятия и обучения, какие являются
				главными?</label><br>	
		</div>
		<!-- Option 1 -->
		<div>
<%				if(answers.containsKey(new Integer(51642)) && answers.get(new Integer(51642)).equals("163795")){
%>					<label class="radio">
						<input type="radio" name='51642'  value="163795" checked/>Визуальный, слуховой, кинестетический и обонятельный
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51642'  value="163795"/>Визуальный, слуховой, кинестетический и обонятельный
					</label>
					</br>
<%				}  
%>	<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51642)) && answers.get(new Integer(51642)).equals("163796")){
%>					<label class="radio">
						<input type="radio" name='51642'  value="163796" checked/>Визуальный, слуховой и кинестетический
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51642'  value="163796"/>Визуальный, слуховой и кинестетический
					</label>
					</br>
<%				}  
%>	<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51642)) && answers.get(new Integer(51642)).equals("163794")){
%>					<label class="radio">
						<input type="radio" name='51642'  value="163794" checked/>Визуальный, слуховой, кинестетический, обонятельный и канал чувства вкуса.
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51642'  value="163794"/>Визуальный, слуховой, кинестетический, обонятельный и канал чувства вкуса.
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
		
			<br><label>8. Люди запоминают лучше:</label>
		</div>
		<!-- Option 1 -->
		<div>
<%				if(answers.containsKey(new Integer(51643)) && answers.get(new Integer(51643)).equals("163797")){
%>					<label class="radio">
						<input type="radio" name='51643'  value="163797" checked/>То, что они слышат и видят
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51643'  value="163797"/>То, что они слышат и видят
					</label>
					</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51643)) && answers.get(new Integer(51643)).equals("163799")){
%>					<label class="radio">
						<input type="radio" name='51643'  value="163799" checked/>То, что они видят и делают
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51643'  value="163799"/>То, что они видят и делают
					</label>
					</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51643)) && answers.get(new Integer(51643)).equals("163798")){
%>					<label class="radio">
						<input type="radio" name='51643'  value="163798" checked/>То, что они слышат, видят и делают.
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51643'  value="163798"/>То, что они слышат, видят и делают.
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
			<br><label> 9.Что такое «вождение с комментариями»?</label>
		</div>
		<!-- Option 1 -->
		<div>
<%				if(answers.containsKey(new Integer(51644)) && answers.get(new Integer(51644)).equals("163802")){
%>					<label class="radio">
						<input type="radio" name='51644'  value="163802" checked/>Это механизм в процессе научения-обучения, который позволяет водителю
						быть оцененным, и используется, когда Тренер останавливает автомобиль и
						побуждает водителя комментировать / объяснять, над чем идет работа во
						время занятия.
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51644'  value="163802"/>Это механизм в процессе научения-обучения, который позволяет водителю
						быть оцененным, и используется, когда Тренер останавливает автомобиль и
						побуждает водителя комментировать / объяснять, над чем идет работа во
						время занятия.
					</label>
					</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51644)) && answers.get(new Integer(51644)).equals("163801")){
%>					<label class="radio">
						<input type="radio" name='51644'  value="163801" checked/>Это учебный инструмент, который Тренер может использовать на любом этапе,
						побуждая водителя представить рассказ / дать объяснение, что позволяет
						Тренеру проследить ход мыслей обучающегося и оценить знания и / или
						действия, которые тот будет выполнять.
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51644'  value="163801"/>Это учебный инструмент, который Тренер может использовать на любом этапе,
						побуждая водителя представить рассказ / дать объяснение, что позволяет
						Тренеру проследить ход мыслей обучающегося и оценить знания и / или
						действия, которые тот будет выполнять.
					</label>
					</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51644)) && answers.get(new Integer(51644)).equals("163800")){
%>					<label class="radio">
						<input type="radio" name='51644'  value="163800" checked/>Это инструмент обучения и оценки, который используется в начале и в конце
						каждого дня, когда Тренер побуждает водителя комментировать / объяснять
						выполняемые действия.
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51644'  value="163800"/>Это инструмент обучения и оценки, который используется в начале и в конце
						каждого дня, когда Тренер побуждает водителя комментировать / объяснять
						выполняемые действия.
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
			<br><label>10. Почему важно знать краткие биографические сведения о тех людях, которые будут
				обучаться? </label>
		</div>
		<!-- Option 1 -->
		<div>
<%				if(answers.containsKey(new Integer(51645)) && answers.get(new Integer(51645)).equals("163803")){
%>					<label class="radio">
						<input type="radio" name='51645'  value="163803" checked/>Чтобы с самого начала Тренер знал уровень иерархии водителей.
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51645'  value="163803"/>Чтобы с самого начала Тренер знал уровень иерархии водителей.
					</label>
					</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51645)) && answers.get(new Integer(51645)).equals("163805")){
%>					<label class="radio">
						<input type="radio" name='51645'  value="163805" checked/>Чтобы адаптировать содержание и разработать стратегию обучения в
						соответствии с потребностями каждого слушателя.
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51645'  value="163805"/>Чтобы адаптировать содержание и разработать стратегию обучения в
						соответствии с потребностями каждого слушателя.
					</label>
					</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51645)) && answers.get(new Integer(51645)).equals("163804")){
%>					<label class="radio">
						<input type="radio" name='51645'  value="163804" checked/>Чтобы знать, нужно ли будет водителю работать на протяжении всего процесса
						обучения, указанного программой СЕРА.
					</label>
					</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51645'  value="163804"/>Чтобы знать, нужно ли будет водителю работать на протяжении всего процесса
						обучения, указанного программой СЕРА.
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
				<br><label>11. Тренер не только должен в совершенстве владеть программой СЕРА, но и
					должен правильно понимать трудности водителя, чтобы найти адекватное решение.</label>
			</div>
			<!-- Option 1 -->
			<div>
	<%				if(answers.containsKey(new Integer(51646)) && answers.get(new Integer(51646)).equals("163807")){
	%>					<label class="radio">
							<input type="radio" name='51646'  value="163807" checked/>Верно
						</label>
						</br>	
	<%				}else{
	%>					<label class="radio">
							<input type="radio" name='51646'  value="163807"/>Верно
						</label>
						</br>
	<%				}  
	%>				<!-- Option 2 -->				
	<%				if(answers.containsKey(new Integer(51646)) && answers.get(new Integer(51646)).equals("163806")){
	%>					<label class="radio">
							<input type="radio" name='51646'  value="163806" checked/>Неверно
						</label>
						</br>	
	<%				}else{
	%>					<label class="radio">
							<input type="radio" name='51646'  value="163806"/>Неверно
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
		<br><label>12. Применяя систему, предлагаемую программой СЕРА, водители могут повысить
			экономичность (уменьшить расход топлива и износа ТС и пр.) и лучше контролировать
			уровень загрязнения окружающей среды.</label>
	</div>
	<!-- Option 1 -->
	<div>
<%				if(answers.containsKey(new Integer(51647)) && answers.get(new Integer(51647)).equals("163809")){
%>					<label class="radio">
						<input type="radio" name='51647'  value="163809" checked/>Верно
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51647'  value="163809"/>Верно
					</label>
				</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51647)) && answers.get(new Integer(51647)).equals("163808")){
%>					<label class="radio">
						<input type="radio" name='51647'  value="163808" checked/>Неверно
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51647'  value="163808"/>Неверно
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
		<br><label>13. Чтобы добиться большей эффективности в процессе обучения по программе СЕРА,
			удобно дать водителям возможность комментировать и выражать свои собственные
			оценки, чувства и эмоции. </label>
	</div>
	<!-- Option 1 -->
	<div>
<%				if(answers.containsKey(new Integer(51648)) && answers.get(new Integer(51648)).equals("163810")){
%>					<label class="radio">
						<input type="radio" name='51648'  value="163810" checked/>Верно
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51648'  value="163810"/>Верно
					</label>
				</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51648)) && answers.get(new Integer(51648)).equals("163811")){
%>					<label class="radio">
						<input type="radio" name='51648'  value="163811" checked/>Неверно
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51648'  value="163811"/>Неверно
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
		<br><label>14. Согласно статистики кто из водителей более опасно управляет своим транспортным
			средством на скорости 90 км/ч? </label>
	</div>
	<!-- Option 1 -->
	<div>
<%				if(answers.containsKey(new Integer(51649)) && answers.get(new Integer(51649)).equals("163813")){
%>					<label class="radio">
						<input type="radio" name='51649'  value="163813" checked/>Водитель легкового автомобиля
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51649'  value="163813"/>Водитель легкового автомобиля
					</label>
				</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51649)) && answers.get(new Integer(51649)).equals("163812")){
%>					<label class="radio">
						<input type="radio" name='51649'  value="163812" checked/>Водитель мотоцикла
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51649'  value="163812"/>Водитель мотоцикла
					</label>
				</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51649)) && answers.get(new Integer(51649)).equals("163814")){
%>					<label class="radio">
						<input type="radio" name='51649'  value="163814" checked/>Водитель грузового автомобиля
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51649'  value="163814"/>Водитель грузового автомобиля
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
		<br><label>15. Езда на скорости, соответствующей правилам, гарантирует безопасность?</label>
	</div>
	<!-- Option 1 -->
	<div>
<%				if(answers.containsKey(new Integer(51650)) && answers.get(new Integer(51650)).equals("163816")){
%>					<label class="radio">
						<input type="radio" name='51650'  value="163816" checked/>Да, потому что указанная правилами скорость предусматривает все варианты
						переменных.
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51650'  value="163816"/>Да, потому что указанная правилами скорость предусматривает все варианты
						переменных.
					</label>
				</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51650)) && answers.get(new Integer(51650)).equals("163817")){
%>					<label class="radio">
						<input type="radio" name='51650'  value="163817" checked/>Нет, потому что состояние автомобиля, опыт и психофизиологическое
						состояние водителя и все переменные окружающей среды также необходимо
						принимать во внимание.
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51650'  value="163817"/>Нет, потому что состояние автомобиля, опыт и психофизиологическое
						состояние водителя и все переменные окружающей среды также необходимо
						принимать во внимание.
					</label>
				</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51650)) && answers.get(new Integer(51650)).equals("163815")){
%>					<label class="radio">
						<input type="radio" name='51650'  value="163815" checked/>Да, всегда. Нет других переменных, которые нужно рассматривать.
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51650'  value="163815"/>Да, всегда. Нет других переменных, которые нужно рассматривать.
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
		<br><label>16. Если загорелся индикатор аккумуляторной батареи на приборной панели, что
			необходимо делать?</label>
	</div>
	<!-- Option 1 -->
	<div>
<%				if(answers.containsKey(new Integer(51651)) && answers.get(new Integer(51651)).equals("163820")){
%>					<label class="radio">
						<input type="radio" name='51651'  value="163820" checked/>Продолжить поездку, а потом проверить, есть ли необходимость в замене
						аккумулятора из-за низкого заряда.
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51651'  value="163820"/>Продолжить поездку, а потом проверить, есть ли необходимость в замене
						аккумулятора из-за низкого заряда.
					</label>
				</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51651)) && answers.get(new Integer(51651)).equals("163819")){
%>					<label class="radio">
						<input type="radio" name='51651'  value="163819" checked/>Сделать остановку и проверить целостность ремня генератора, что так же
						может повлиять на работоспособность водяного насоса.
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51651'  value="163819"/>Сделать остановку и проверить целостность ремня генератора, что так же
						может повлиять на работоспособность водяного насоса.
					</label>
				</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51651)) && answers.get(new Integer(51651)).equals("163818")){
%>					<label class="radio">
						<input type="radio" name='51651'  value="163818" checked/>Продолжить поездку, а потом произвести ремонт или замену преобразователя
						тока.
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51651'  value="163818"/>Продолжить поездку, а потом произвести ремонт или замену преобразователя
						тока.
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
		<br><label>17. Какой эффект на человека оказывает усталость?</label>
	</div>
	<!-- Option 1 -->
	<div>
<%				if(answers.containsKey(new Integer(51652)) && answers.get(new Integer(51652)).equals("163821")){
%>					<label class="radio">
						<input type="radio" name='51652'  value="163821" checked/>Увеличивает время реакции
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51652'  value="163821"/>Увеличивает время реакции
					</label>
				</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51652)) && answers.get(new Integer(51652)).equals("163823")){
%>					<label class="radio">
						<input type="radio" name='51652'  value="163823" checked/>Уменьшает время реакции
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51652'  value="163823"/>Уменьшает время реакции
					</label>
				</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51652)) && answers.get(new Integer(51652)).equals("163822")){
%>					<label class="radio">
						<input type="radio" name='51652'  value="163822" checked/>Практически никакого, если водитель опытный.
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51652'  value="163822"/>Практически никакого, если водитель опытный.
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
		<br><label>18. Каковы факторы, которые нужно принимать во внимание во время прохождения
			поворота?</label>
	</div>
	<!-- Option 1 -->
	<div>
<%				if(answers.containsKey(new Integer(51653)) && answers.get(new Integer(51653)).equals("163825")){
%>					<label class="radio">
						<input type="radio" name='51653'  value="163825" checked/>Скорость, конструкция ТС, радиус поворота и состояние дороги.
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51653'  value="163825"/>Скорость, конструкция ТС, радиус поворота и состояние дороги.
					</label>
				</br>
<%				}  
%>	<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51653)) && answers.get(new Integer(51653)).equals("163826")){
%>					<label class="radio">
						<input type="radio" name='51653'  value="163826" checked/>Скорость, габариты и груз, радиус поворота, состояние шин и тип тормозов.
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51653'  value="163826"/>Скорость, габариты и груз, радиус поворота, состояние шин и тип тормозов.
					</label>
				</br>
<%				}  
%>	<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51653)) && answers.get(new Integer(51653)).equals("163824")){
%>					<label class="radio">
						<input type="radio" name='51653'  value="163824" checked/>Скорость, габариты и груз, радиус поворота, состояние шин и состояние
						дороги.
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51653'  value="163824"/>Скорость, габариты и груз, радиус поворота, состояние шин и состояние
						дороги.
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
		<br><label>19. Тормозной путь зависит от:</label>
	</div>
	<!-- Option 1 -->
	<div>
<%				if(answers.containsKey(new Integer(51654)) && answers.get(new Integer(51654)).equals("163829")){
%>					<label class="radio">
						<input type="radio" name='51654'  value="163829" checked/>Реакции водителя, скорости и техники торможения.
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51654'  value="163829"/>Реакции водителя, скорости и техники торможения.
					</label>
				</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51654)) && answers.get(new Integer(51654)).equals("163827")){
%>					<label class="radio">
						<input type="radio" name='51654'  value="163827" checked/>Скорости, состояния дороги, состояния автомобиля и техники торможения.
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51654'  value="163827"/>Скорости, состояния дороги, состояния автомобиля и техники торможения.
					</label>
				</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51654)) && answers.get(new Integer(51654)).equals("163828")){
%>					<label class="radio">
						<input type="radio" name='51654'  value="163828" checked/>Реакции водителя, скорости, состояния дороги, состояния автомобиля и
						техники торможения.
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51654'  value="163828"/>Реакции водителя, скорости, состояния дороги, состояния автомобиля и
						техники торможения.
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
		<br><label>20. На скорости 50 км/ч перед вами возникает препятствие. Сколько метров проедет
			ваш автомобиль до того, как вы нажмете на тормоз?</label>
	</div>
	<!-- Option 1 -->
	<div>
<%				if(answers.containsKey(new Integer(51655)) && answers.get(new Integer(51655)).equals("163832")){
%>					<label class="radio">
						<input type="radio" name='51655'  value="163832" checked/>1 м
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51655'  value="163832"/>1 м
					</label>
				</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51655)) && answers.get(new Integer(51655)).equals("163833")){
%>					<label class="radio">
						<input type="radio" name='51655'  value="163833" checked/>5 м
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51655'  value="163833"/>5 м
					</label>
				</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51655)) && answers.get(new Integer(51655)).equals("163831")){
%>					<label class="radio">
						<input type="radio" name='51655'  value="163831" checked/>10 м
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51655'  value="163831"/>10 м
					</label>
				</br>
<%				}

%>
<!-- Option 4 -->				
<%				if(answers.containsKey(new Integer(51655)) && answers.get(new Integer(51655)).equals("163830")){
	%>					<label class="radio">
							<input type="radio" name='51655'  value="163830" checked/>больше
						</label>
					</br>	
	<%				}else{
	%>					<label class="radio">
							<input type="radio" name='51655'  value="163830"/>больше
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
		<br><label>21. Среднее время реакции человека</label>
	</div>
	<!-- Option 1 -->
	<div>
<%				if(answers.containsKey(new Integer(51656)) && answers.get(new Integer(51656)).equals("163836")){
%>					<label class="radio">
						<input type="radio" name='51656'  value="163836" checked/>0,5 сек
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51656'  value="163836"/>0,5 сек
					</label>
				</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51656)) && answers.get(new Integer(51656)).equals("163837")){
%>					<label class="radio">
						<input type="radio" name='51656'  value="163837" checked/>0,25 сек
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51656'  value="163837"/>0,25 сек
					</label>
				</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51656)) && answers.get(new Integer(51656)).equals("163834")){
%>					<label class="radio">
						<input type="radio" name='51656'  value="163834" checked/>0,01 сек
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51656'  value="163834"/>0,01 сек
					</label>
				</br>
<%				}

%>
<!-- Option 4 -->				
<%				if(answers.containsKey(new Integer(51656)) && answers.get(new Integer(51656)).equals("163835")){
	%>					<label class="radio">
							<input type="radio" name='51656'  value="163835" checked/>1 сек
						</label>
					</br>	
	<%				}else{
	%>					<label class="radio">
							<input type="radio" name='51656'  value="163835"/>	1 сек
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
		<br><label>22. В отношении давления в шинах, как бы вы поступили, если температура воздуха и
			температура асфальта высокая?</label>
	</div>
	<!-- Option 1 -->
	<div>
<%				if(answers.containsKey(new Integer(51657)) && answers.get(new Integer(51657)).equals("163840")){
%>					<label class="radio">
						<input type="radio" name='51657'  value="163840" checked/>Никогда не понижать давление
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51657'  value="163840"/>Никогда не понижать давление
					</label>
				</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51657)) && answers.get(new Integer(51657)).equals("163839")){
%>					<label class="radio">
						<input type="radio" name='51657'  value="163839" checked/>Всегда понижать давление
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51657'  value="163839"/> Всегда понижать давление
					</label>
				</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51657)) && answers.get(new Integer(51657)).equals("163838")){
%>					<label class="radio">
						<input type="radio" name='51657'  value="163838" checked/>Неважно
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51657'  value="163838"/>Неважно
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
		<br><label>23. Какова допустимая минимальная остаточная высота рисунка протектора летней
			шины в соответствии с правилами?</label>
	</div>
	<!-- Option 1 -->
	<div>
<%				if(answers.containsKey(new Integer(51658)) && answers.get(new Integer(51658)).equals("163842")){
%>					<label class="radio">
						<input type="radio" name='51658'  value="163842" checked/>3,6 мм
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51658'  value="163842"/>3,6 мм
					</label>
				</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51658)) && answers.get(new Integer(51658)).equals("163841")){
%>					<label class="radio">
						<input type="radio" name='51658'  value="163841" checked/>2,6 мм
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51658'  value="163841"/>2,6 мм
					</label>
				</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51658)) && answers.get(new Integer(51658)).equals("163843")){
%>					<label class="radio">
						<input type="radio" name='51658'  value="163843" checked/>1,6 мм
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51658'  value="163843"/>1,6 мм
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
		<br><label>24. Пассивная безопасность автомобиля состоит из элементов, систем или
			компонентов, которые</label>
	</div>
	<!-- Option 1 -->
	<div>
<%				if(answers.containsKey(new Integer(51659)) && answers.get(new Integer(51659)).equals("163846")){
%>					<label class="radio">
						<input type="radio" name='51659'  value="163846" checked/>предназначены предотвратить или уменьшить возможные травмы
						пассажиров автомобиля
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51659'  value="163846"/>предназначены предотвратить или уменьшить возможные травмы
						пассажиров автомобиля
					</label>
				</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51659)) && answers.get(new Integer(51659)).equals("163845")){
%>					<label class="radio">
						<input type="radio" name='51659'  value="163845" checked/>предназначены предотвратить или уменьшить потерю контроля или аварии
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51659'  value="163845"/>предназначены предотвратить или уменьшить потерю контроля или аварии
					</label>
				</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51659)) && answers.get(new Integer(51659)).equals("163844")){
%>					<label class="radio">
						<input type="radio" name='51659'  value="163844" checked/>оба ответа правильные
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51659'  value="163844"/>оба ответа правильные
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
		<br><label>25. Использование мобильного телефона влияет на</label>
	</div>
	<!-- Option 1 -->
	<div>
<%				if(answers.containsKey(new Integer(51660)) && answers.get(new Integer(51660)).equals("163847")){
%>					<label class="radio">
						<input type="radio" name='51660'  value="163847" checked/>Зрение
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51660'  value="163847"/>Зрение
					</label>
				</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51660)) && answers.get(new Integer(51660)).equals("163849")){
%>					<label class="radio">
						<input type="radio" name='51660'  value="163849" checked/>Скорость
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51660'  value="163849"/>Скорость
					</label>
				</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51660)) && answers.get(new Integer(51660)).equals("163848")){
%>					<label class="radio">
						<input type="radio" name='51660'  value="163848" checked/>Других
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51660'  value="163848"/>Других
					</label>
				</br>
<%				}

%>
<!-- Option 4 -->				
<%				if(answers.containsKey(new Integer(51660)) && answers.get(new Integer(51660)).equals("163850")){
	%>					<label class="radio">
							<input type="radio" name='51660'  value="163850" checked/>Концентрацию внимания
						</label>
					</br>	
	<%				}else{
	%>					<label class="radio">
							<input type="radio" name='51660'  value="163850"/>Концентрацию внимания
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
		<br><label>26. Безопасная скорость – это скорость…</label>
	</div>
	<!-- Option 1 -->
	<div>
<%				if(answers.containsKey(new Integer(51661)) && answers.get(new Integer(51661)).equals("163853")){
%>					<label class="radio">
						<input type="radio" name='51661'  value="163853" checked/>которая соответствует ограничениям, установленным знаками и правилами.
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51661'  value="163853"/>которая соответствует ограничениям, установленным знаками и правилами.
					</label>
				</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51661)) && answers.get(new Integer(51661)).equals("163852")){
%>					<label class="radio">
						<input type="radio" name='51661'  value="163852" checked/>которая соответствует особенностям водителя, автомобиля и окружающей
						среды.
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51661'  value="163852"/>которая соответствует особенностям водителя, автомобиля и окружающей
						среды.
					</label>
				</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51661)) && answers.get(new Integer(51661)).equals("163851")){
%>					<label class="radio">
						<input type="radio" name='51661'  value="163851" checked/>которая соответствует опыту водителя, правилам движения, условиям
						окружающей среды и состоянию водителя.
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51661'  value="163851"/>которая соответствует опыту водителя, правилам движения, условиям
						окружающей среды и состоянию водителя.
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
		<br><label>27. Безопасная дистанция следования между двумя автомобилями должна быть не менее</label>
	</div>
	<!-- Option 1 -->
	<div>
<%				if(answers.containsKey(new Integer(51662)) && answers.get(new Integer(51662)).equals("163855")){
%>					<label class="radio">
						<input type="radio" name='51662'  value="163855" checked/>1,5 сек
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51662'  value="163855"/>1,5 сек
					</label>
				</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51662)) && answers.get(new Integer(51662)).equals("163854")){
%>					<label class="radio">
						<input type="radio" name='51662'  value="163854" checked/>2 сек
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51662'  value="163854"/>2 сек
					</label>
				</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51662)) && answers.get(new Integer(51662)).equals("163856")){
%>					<label class="radio">
						<input type="radio" name='51662'  value="163856" checked/>3 сек
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51662'  value="163856"/>3 сек
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
		<br><label>28. При остановке за впередистоящим автомобилем, я должен видеть</label>
	</div>
	<!-- Option 1 -->
	<div>
<%				if(answers.containsKey(new Integer(51663)) && answers.get(new Integer(51663)).equals("163859")){
%>					<label class="radio">
						<input type="radio" name='51663'  value="163859" checked/>Его задние колеса
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51663'  value="163859"/>Его задние колеса
					</label>
				</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51663)) && answers.get(new Integer(51663)).equals("163858")){
%>					<label class="radio">
						<input type="radio" name='51663'  value="163858" checked/>Его регистрационный номерной знак
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51663'  value="163858"/>Его регистрационный номерной знак
					</label>
				</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51663)) && answers.get(new Integer(51663)).equals("163857")){
%>					<label class="radio">
						<input type="radio" name='51663'  value="163857" checked/>Его задние колеса в зоне контакта с поверхностью дороги
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51663'  value="163857"/>Его задние колеса в зоне контакта с поверхностью дороги
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
		<br><label>29. Слепая зона автомобиля это</label>
	</div>
	<!-- Option 1 -->
	<div>
<%				if(answers.containsKey(new Integer(51664)) && answers.get(new Integer(51664)).equals("163862")){
%>					<label class="radio">
						<input type="radio" name='51664'  value="163862" checked/>Зона, расположенная в задней части автомобиля, которая
						просматривается с помощью сферического зеркала заднего вида.
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51664'  value="163862"/>Зона, расположенная в задней части автомобиля, которая
						просматривается с помощью сферического зеркала заднего вида.
					</label>
				</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51664)) && answers.get(new Integer(51664)).equals("163861")){
%>					<label class="radio">
						<input type="radio" name='51664'  value="163861" checked/>Зона за пределами автомобиля, которая просматривается с помощью
						боковых зеркал заднего вида.
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51664'  value="163861"/>Зона за пределами автомобиля, которая просматривается с помощью
						боковых зеркал заднего вида.
					</label>
				</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51664)) && answers.get(new Integer(51664)).equals("163860")){
%>					<label class="radio">
						<input type="radio" name='51664'  value="163860" checked/>Зона за пределами автомобиля, которую водитель не может
						просматривать в зеркала заднего вида со своего рабочего места.
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51664'  value="163860"/>Зона за пределами автомобиля, которую водитель не может
						просматривать в зеркала заднего вида со своего рабочего места.
					</label>
				</br>
<%				}

%></div>
</div>	
<div>
	<img src="images/lineWebinar.png" style="width: 100%">
</div>
<!-- Question 29 ends -->	
<!-- Question 30  -->
<div class="center2">

	<div>			
		<br><label>30. Неправильное положение водителя за рулем может в основном повлиять на...</label>
	</div>
	<!-- Option 1 -->
	<div>
<%				if(answers.containsKey(new Integer(51665)) && answers.get(new Integer(51665)).equals("163865")){
%>					<label class="radio">
						<input type="radio" name='51665'  value="163865" checked/>Способность управлять автомобилем и вести наблюдение в нужный момент
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51665'  value="163865"/>Способность управлять автомобилем и вести наблюдение в нужный момент
					</label>
				</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51665)) && answers.get(new Integer(51665)).equals("163864")){
%>					<label class="radio">
						<input type="radio" name='51665'  value="163864" checked/>Состояние здоровье
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51665'  value="163864"/>Состояние здоровье
					</label>
				</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51665)) && answers.get(new Integer(51665)).equals("163863")){
%>					<label class="radio">
						<input type="radio" name='51665'  value="163863" checked/>Обзор с места водителя
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51665'  value="163863"/>Обзор с места водителя
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
<!-- Question 31  -->
<div class="center2">

	<div>			
		<br><label>31. Использование коробки передач и контроль уровня давления в шинах влияют на
			экологию и экономичность?</label>
	</div>
	<!-- Option 1 -->
	<div>
<%				if(answers.containsKey(new Integer(51666)) && answers.get(new Integer(51666)).equals("163868")){
%>					<label class="radio">
						<input type="radio" name='51666'  value="163868" checked/>Нет. Экономически и экологически эффективное вождение зависит от стиля
						езды водителя и в особенности от того, как он пользуется педалями
						акселератора и тормоза.
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51666'  value="163868"/>Нет. Экономически и экологически эффективное вождение зависит от стиля
						езды водителя и в особенности от того, как он пользуется педалями
						акселератора и тормоза.
					</label>
				</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51666)) && answers.get(new Integer(51666)).equals("163867")){
%>					<label class="radio">
						<input type="radio" name='51666'  value="163867" checked/>Да. Это те вопросы, о которых водитель (среди прочих) должен задумываться
						и стараться с большим вниманием относиться к заботе об окружающей среде
						и должен контролировать расход топлива.
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51666'  value="163867"/>Да. Это те вопросы, о которых водитель (среди прочих) должен задумываться
						и стараться с большим вниманием относиться к заботе об окружающей среде
						и должен контролировать расход топлива.
					</label>
				</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51666)) && answers.get(new Integer(51666)).equals("163866")){
%>					<label class="radio">
						<input type="radio" name='51666'  value="163866" checked/>Нет. Забота об окружающей среде и контроль за расходом топлива
						достигается посредством применения особого превентивного стиля
						вождения.
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51666'  value="163866"/>Нет. Забота об окружающей среде и контроль за расходом топлива
						достигается посредством применения особого превентивного стиля
						вождения.
					</label>
				</br>
<%				}

%>
	</div>
</div>	
<div>
	<img src="images/lineWebinar.png" style="width: 100%">
</div>
<!-- Question 31 ends -->	
<!-- Question 32  -->
<div class="center2">

	<div>			
		<br><label>32. Важно, чтобы водитель помнил и действовал таким образом, чтобы снизить
			нагрузку на двигатель от</label>
	</div>
	<!-- Option 1 -->
	<div>
<%				if(answers.containsKey(new Integer(51667)) && answers.get(new Integer(51667)).equals("163872")){
%>					<label class="radio">
						<input type="radio" name='51667'  value="163872" checked/>Сопротивления качения шины и сопротивления при прохождении подъема
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51667'  value="163872"/>Сопротивления качения шины и сопротивления при прохождении подъема
					</label>
				</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51667)) && answers.get(new Integer(51667)).equals("163871")){
%>					<label class="radio">
						<input type="radio" name='51667'  value="163871" checked/>Сопротивления при разгоне и аэродинамического сопротивления
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51667'  value="163871"/>Сопротивления при разгоне и аэродинамического сопротивления
					</label>
				</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51667)) && answers.get(new Integer(51667)).equals("163869")){
%>					<label class="radio">
						<input type="radio" name='51667'  value="163869" checked/>Верны оба варианта
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51667'  value="163869"/>Верны оба варианта
					</label>
				</br>
<%				}

%>
	</div>
</div>	
<div>
	<img src="images/lineWebinar.png" style="width: 100%">
</div>
<!-- Question 32 ends -->
<!-- Question 33  -->
<div class="center2">

	<div>			
		<br><label>33. Время отклика тормозного механизма легкового автомобиля в среднем равно</label>
	</div>
	<!-- Option 1 -->
	<div>
<%				if(answers.containsKey(new Integer(51668)) && answers.get(new Integer(51668)).equals("163875")){
%>					<label class="radio">
						<input type="radio" name='51668'  value="163875" checked/>0,5 сек
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51668'  value="163875"/>0,5 сек
					</label>
				</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51668)) && answers.get(new Integer(51668)).equals("163874")){
%>					<label class="radio">
						<input type="radio" name='51668'  value="163874" checked/>0,25 сек
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51668'  value="163874"/>0,25 сек
					</label>
				</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51668)) && answers.get(new Integer(51668)).equals("163873")){
%>					<label class="radio">
						<input type="radio" name='51668'  value="163873" checked/>0,01 сек
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51668'  value="163873"/>0,01 сек
					</label>
				</br>
<%				}

%>				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51668)) && answers.get(new Integer(51668)).equals("163879")){
%>					<label class="radio">
						<input type="radio" name='51668'  value="163879" checked/>1 сек
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51668'  value="163879"/>1 сек
					</label>
				</br>
<%				}

%>
	</div>
</div>	
<div>
	<img src="images/lineWebinar.png" style="width: 100%">
</div>
<!-- Question 33 ends -->
<!-- Question 34  -->
<div class="center2">

	<div>			
		<br><label>34. Периферическое (боковое) зрение дает вам понятие о</label>
	</div>
	<!-- Option 1 -->
	<div>
<%				if(answers.containsKey(new Integer(51669)) && answers.get(new Integer(51669)).equals("163877")){
%>					<label class="radio">
						<input type="radio" name='51669'  value="163877" checked/>Скорости движения других объектов
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51669'  value="163877"/>Скорости движения других объектов
					</label>
				</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51669)) && answers.get(new Integer(51669)).equals("163876")){
%>					<label class="radio">
						<input type="radio" name='51669'  value="163876" checked/>Объектах, находящихся за пределами центрального зрения
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51669'  value="163876"/>Объектах, находящихся за пределами центрального зрения
					</label>
				</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51669)) && answers.get(new Integer(51669)).equals("163878")){
%>					<label class="radio">
						<input type="radio" name='51669'  value="163878" checked/>Расстоянии до объектов
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51669'  value="163878"/>Расстоянии до объектов
					</label>
				</br>
<%				}

%>
	</div>
</div>	
<div>
	<img src="images/lineWebinar.png" style="width: 100%">
</div>
<!-- Question 34 ends -->
<!-- Question 35  -->
<div class="center2">

	<div>			
		<br><label>35. Какое количество граммов спирта на литр крови, уже серьезно влияет на
			рефлексы водителя?</label>
	</div>
	<!-- Option 1 -->
	<div>
<%				if(answers.containsKey(new Integer(51670)) && answers.get(new Integer(51670)).equals("163882")){
%>					<label class="radio">
						<input type="radio" name='51670'  value="163882" checked/>0,2 г/л
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51670'  value="163882"/>0,2 г/л
					</label>
				</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51670)) && answers.get(new Integer(51670)).equals("163880")){
%>					<label class="radio">
						<input type="radio" name='51670'  value="163880" checked/>0,4 г/л
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51670'  value="163880"/>0,4 г/л
					</label>
				</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51670)) && answers.get(new Integer(51670)).equals("163881")){
%>					<label class="radio">
						<input type="radio" name='51670'  value="163881" checked/>0,5 г/л
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51670'  value="163881"/>0,5 г/л
					</label>
				</br>
<%				}

%>
		<!-- Option 4 -->
		<%				if(answers.containsKey(new Integer(51670)) && answers.get(new Integer(51670)).equals("163883")){
			%>					<label class="radio">
									<input type="radio" name='51670'  value="163883" checked/>0,8 г/л
								</label>
							</br>	
			<%				}else{
			%>					<label class="radio">
									<input type="radio" name='51670'  value="163883"/>0,8 г/л
								</label>
							</br>
			<%				}
			
			%>
	</div>
</div>	
<div>
	<img src="images/lineWebinar.png" style="width: 100%">
</div>
<!-- Question 35 ends -->
<!-- Question 36  -->
<div class="center2">

	<div>			
		<br><label>36. Укажите техники защитного вождения</label>
	</div>
	<!-- Option 1 -->
	<div>
<%				if(answers.containsKey(new Integer(1111)) && answers.get(new Integer(1111)).equals("1111")){
%>					<label class="radio">
						<input type="radio" name='1111'  value="1111" checked/>Сканировать дорогу вертикально, смотреть далеко
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='1111'  value="1111"/>Сканировать дорогу вертикально, смотреть далеко
					</label>
				</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(1111)) && answers.get(new Integer(1111)).equals("1111")){
%>					<label class="radio">
						<input type="radio" name='1111'  value="1111" checked/>Следить за дорожными знаками и светофорами
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='1111'  value="1111"/>Следить за дорожными знаками и светофорами
					</label>
				</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(1111)) && answers.get(new Integer(1111)).equals("1111")){
%>					<label class="radio">
						<input type="radio" name='1111'  value="1111" checked/>Прогнозировать и оставлять себе запасной путь для выхода из ситуации
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='1111'  value="1111"/>Прогнозировать и оставлять себе запасной путь для выхода из ситуации
					</label>
				</br>
<%				}

%>
				<!-- Option 4 -->
				<%				if(answers.containsKey(new Integer(1111)) && answers.get(new Integer(1111)).equals("1111")){
					%>					<label class="radio">
											<input type="radio" name='1111'  value="1111" checked/>Сканировать дорогу взглядом горизонтально, часто смотреть в зеркала
										</label>
									</br>	
					<%				}else{
					%>					<label class="radio">
											<input type="radio" name='1111'  value="1111"/>Сканировать дорогу взглядом горизонтально, часто смотреть в зеркала
										</label>
									</br>
					<%				}
					
					%>
	</div>
</div>	
<div>
	<img src="images/lineWebinar.png" style="width: 100%">
</div>
<!-- Question 36 ends -->
<!-- Question 37  -->
<div class="center2">

	<div>			
		<br><label>37. Въезжая в зону, где есть дети/животные, я должен</label>
	</div>
	<!-- Option 1 -->
	<div>
<%				if(answers.containsKey(new Integer(51672)) && answers.get(new Integer(51672)).equals("163889")){
%>					<label class="radio">
						<input type="radio" name='51672'  value="163889" checked/>Смотреть в зеркала, снизить скорость, не сигналить звуковым сигналом, быть уверенным,
						что меня видят и понимают, иметь запасной план действий
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51672'  value="163889"/>Смотреть в зеркала, снизить скорость, не сигналить звуковым сигналом, быть уверенным,
						что меня видят и понимают, иметь запасной план действий
					</label>
				</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51672)) && answers.get(new Integer(51672)).equals("163890")){
%>					<label class="radio">
						<input type="radio" name='51672'  value="163890" checked/>Смотреть в зеркала, отрегулировать скорость, при необходимости сигналить звуковым
						сигналом, быть уверенным, что меня видят и понимают, иметь запасной план действий
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51672'  value="163890"/>Смотреть в зеркала, отрегулировать скорость, при необходимости сигналить звуковым
						сигналом, быть уверенным, что меня видят и понимают, иметь запасной план действий
					</label>
				</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51672)) && answers.get(new Integer(51672)).equals("163891")){
%>					<label class="radio">
						<input type="radio" name='51672'  value="163891" checked/>Снизить скорость, смотреть в зеркала, быть уверенным, что меня видят и понимают,
						иметь запасной план действий
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51672'  value="163891"/>Снизить скорость, смотреть в зеркала, быть уверенным, что меня видят и понимают,
						иметь запасной план действий
					</label>
				</br>
<%				}

%>				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51672)) && answers.get(new Integer(51672)).equals("163888")){
%>					<label class="radio">
						<input type="radio" name='51672'  value="163888" checked/>Смотреть в зеркала, отрегулировать скорость, сигналить звуковым сигналом,
						иметь запасной план действий
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51672'  value="163888"/>Смотреть в зеркала, отрегулировать скорость, сигналить звуковым сигналом,
						иметь запасной план действий
					</label>
				</br>
<%				}

%>
	</div>
</div>	
<div>
	<img src="images/lineWebinar.png" style="width: 100%">
</div>
<!-- Question 37 ends -->
<!-- Question 38  -->
<div class="center2">

	<div>			
		<br><label>38. При подъезде к перекрестку со встречным транспортом я должен</label>
	</div>
	<!-- Option 1 -->
	<div>
<%				if(answers.containsKey(new Integer(51673)) && answers.get(new Integer(51673)).equals("163893")){
%>					<label class="radio">
						<input type="radio" name='51673'  value="163893" checked/>Смотреть в зеркала, снизить скорость, не сигналить звуковым сигналом, быть уверенным,
						что меня видят и понимают, иметь запасной план действий
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51673'  value="163893"/>Смотреть в зеркала, снизить скорость, не сигналить звуковым сигналом, быть уверенным,
						что меня видят и понимают, иметь запасной план действий
					</label>
				</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51673)) && answers.get(new Integer(51673)).equals("163894")){
%>					<label class="radio">
						<input type="radio" name='51673'  value="163894" checked/>Смотреть в зеркала, отрегулировать скорость, при необходимости сигналить звуковым
						сигналом, быть уверенным, что меня видят и понимают, иметь запасной план действий
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51673'  value="163894"/>Смотреть в зеркала, отрегулировать скорость, при необходимости сигналить звуковым
						сигналом, быть уверенным, что меня видят и понимают, иметь запасной план действий
					</label>
				</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51673)) && answers.get(new Integer(51673)).equals("163892")){
%>					<label class="radio">
						<input type="radio" name='51673'  value="163892" checked/>Снизить скорость, смотреть в зеркала, быть уверенным, что меня видят и понимают,
						иметь запасной план действий
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51673'  value="163892"/>Снизить скорость, смотреть в зеркала, быть уверенным, что меня видят и понимают,
						иметь запасной план действий
					</label>
				</br>
<%				}

%>				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51673)) && answers.get(new Integer(51673)).equals("163895")){
%>					<label class="radio">
						<input type="radio" name='51673'  value="163895" checked/>Смотреть в зеркала, отрегулировать скорость, сигналить звуковым сигналом,
						иметь запасной план действий
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51673'  value="163895"/>Смотреть в зеркала, отрегулировать скорость, сигналить звуковым сигналом,
						иметь запасной план действий
					</label>
				</br>
<%				}

%>
	</div>
</div>	
<div>
	<img src="images/lineWebinar.png" style="width: 100%">
</div>
<!-- Question 38 ends -->
<!-- Question 39  -->
<div class="center2">

	<div>			
		<br><label>39. При въезде в пешеходную (дворовую) зону</label>
	</div>
	<!-- Option 1 -->
	<div>
<%				if(answers.containsKey(new Integer(51674)) && answers.get(new Integer(51674)).equals("163896")){
%>					<label class="radio">
						<input type="radio" name='51674'  value="163896" checked/>Смотреть в зеркала, снизить скорость, не сигналить звуковым сигналом, быть уверенным,
						что меня видят и понимают, иметь запасной план действий
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51674'  value="163896"/>Смотреть в зеркала, снизить скорость, не сигналить звуковым сигналом, быть уверенным,
						что меня видят и понимают, иметь запасной план действий
					</label>
				</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51674)) && answers.get(new Integer(51674)).equals("163899")){
%>					<label class="radio">
						<input type="radio" name='51674'  value="163899" checked/>Смотреть в зеркала, отрегулировать скорость, при необходимости сигналить звуковым
						сигналом, быть уверенным, что меня видят и понимают, иметь запасной план действий
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51674'  value="163899"/>Смотреть в зеркала, отрегулировать скорость, при необходимости сигналить звуковым
						сигналом, быть уверенным, что меня видят и понимают, иметь запасной план действий
					</label>
				</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51674)) && answers.get(new Integer(51674)).equals("163898")){
%>					<label class="radio">
						<input type="radio" name='51674'  value="163898" checked/>Снизить скорость, смотреть в зеркала, быть уверенным, что меня видят и понимают,
						иметь запасной план действий
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51674'  value="163898"/>Снизить скорость, смотреть в зеркала, быть уверенным, что меня видят и понимают,
						иметь запасной план действий
					</label>
				</br>
<%				}

%><!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51674)) && answers.get(new Integer(51674)).equals("163897")){
	%>					<label class="radio">
							<input type="radio" name='51674'  value="163897" checked/>Смотреть в зеркала, отрегулировать скорость, сигналить звуковым сигналом,
							иметь запасной план действий
						</label>
					</br>	
	<%				}else{
	%>					<label class="radio">
							<input type="radio" name='51674'  value="163897"/>Смотреть в зеркала, отрегулировать скорость, сигналить звуковым сигналом,
							иметь запасной план действий
						</label>
					</br>
	<%				}
	
	%>
	</div>
</div>	
<div>
	<img src="images/lineWebinar.png" style="width: 100%">
</div>
<!-- Question 39 ends -->
<!-- Question 40  -->
<div class="center2">

	<div>			
		<br><label>40. О чём сообщают водителю сигнальные лампы красного цвета на приборной панели
			автомобиля?</label>
	</div>
	<!-- Option 1 -->
	<div>
<%				if(answers.containsKey(new Integer(51675)) && answers.get(new Integer(51675)).equals("163903")){
%>					<label class="radio">
						<input type="radio" name='51675'  value="163903" checked/>Разрешено дальнейшие движение с соблюдением мер безопасности
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51675'  value="163903"/>Разрешено дальнейшие движение с соблюдением мер безопасности
					</label>
				</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51675)) && answers.get(new Integer(51675)).equals("163901")){
%>					<label class="radio">
						<input type="radio" name='51675'  value="163901" checked/>Запрещается дальнейшее движение автомобиля, т.к. опасность очень серьезная
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51675'  value="163901"/>Запрещается дальнейшее движение автомобиля, т.к. опасность очень серьезная
					</label>
				</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51675)) && answers.get(new Integer(51675)).equals("163902")){
%>					<label class="radio">
						<input type="radio" name='51675'  value="163902" checked/>Ничего страшного, но надо обратиться в автосервис
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51675'  value="163902"/>Ничего страшного, но надо обратиться в автосервис
					</label>
				</br>
<%				}

%>				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51675)) && answers.get(new Integer(51675)).equals("163900")){
%>					<label class="radio">
						<input type="radio" name='51675'  value="163900" checked/>О том, что надо выключить зажигание и заново включить
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51675'  value="163900"/>О том, что надо выключить зажигание и заново включить
					</label>
				</br>
<%				}

%>
	</div>
</div>	
<div>
	<img src="images/lineWebinar.png" style="width: 100%">
</div>
<!-- Question 40 ends -->
<!-- Question 41  -->
<div class="center2">

	<div>			
		<br><label>41. Какую опасность представляют не закрепленные предметы в салоне автомобиля?</label>
	</div>
	<!-- Option 1 -->
	<div>
<%				if(answers.containsKey(new Integer(51676)) && answers.get(new Integer(51676)).equals("163905")){
%>					<label class="radio">
						<input type="radio" name='51676'  value="163905" checked/>Никакой опасности нет
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51676'  value="163905"/>Никакой опасности нет
					</label>
				</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51676)) && answers.get(new Integer(51676)).equals("163907")){
%>					<label class="radio">
						<input type="radio" name='51676'  value="163907" checked/>Тяжелые предметы, настолько тяжелы, что никуда не смогут переместиться в любом
						случае
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51676'  value="163907"/>Тяжелые предметы, настолько тяжелы, что никуда не смогут переместиться в любом
						случае
					</label>
				</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51676)) && answers.get(new Integer(51676)).equals("163904")){
%>					<label class="radio">
						<input type="radio" name='51676'  value="163904" checked/>Могут перемещаться с большой скоростью внутри салона автомобиля, нанося увечия
						пассажирам и водителю
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51676'  value="163904"/>Могут перемещаться с большой скоростью внутри салона автомобиля, нанося увечия
						пассажирам и водителю
					</label>
				</br>
<%				}

%>				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51676)) && answers.get(new Integer(51676)).equals("163906")){
%>					<label class="radio">
						<input type="radio" name='51676'  value="163906" checked/>Нет опасности от этих предметов, если они уложены аккуратно, плотно друг к другу
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51676'  value="163906"/>Нет опасности от этих предметов, если они уложены аккуратно, плотно друг к другу
					</label>
				</br>
<%				}

%>
	</div>
</div>	
<div>
	<img src="images/lineWebinar.png" style="width: 100%">
</div>
<!-- Question 41 ends -->
<!-- Question 42  -->
<div class="center2">

	<div>			
		<br><label>42. Что такое «прогнозирование»?</label>
	</div>
	<!-- Option 1 -->
	<div>
<%				if(answers.containsKey(new Integer(51677)) && answers.get(new Integer(51677)).equals("163909")){
%>					<label class="radio">
						<input type="radio" name='51677'  value="163909" checked/>Это возможность наперед предсказать ход развития событий
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51677'  value="163909"/>Это возможность наперед предсказать ход развития событий
					</label>
				</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51677)) && answers.get(new Integer(51677)).equals("163911")){
%>					<label class="radio">
						<input type="radio" name='51677'  value="163911" checked/>Это возможность предположить вероятное развитие событий
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51677'  value="163911"/>Это возможность предположить вероятное развитие событий
					</label>
				</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51677)) && answers.get(new Integer(51677)).equals("163908")){
%>					<label class="radio">
						<input type="radio" name='51677'  value="163908" checked/>Это возможность быть уверенным в дальнейшем развитии событий
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51677'  value="163908"/>Это возможность быть уверенным в дальнейшем развитии событий
					</label>
				</br>
<%				}

%>				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51677)) && answers.get(new Integer(51677)).equals("163910")){
%>					<label class="radio">
						<input type="radio" name='51677'  value="163910" checked/>Верны все варианты
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51677'  value="163910"/>Верны все варианты
					</label>
				</br>
<%				}

%>
	</div>
</div>	
<div>
	<img src="images/lineWebinar.png" style="width: 100%">
</div>
<!-- Question 42 ends -->
<!-- Question 43  -->
<div class="center2">

	<div>			
		<br><label>43. От чего зависит возможность водителя «прогнозировать»?</label>
	</div>
	<!-- Option 1 -->
	<div>
<%				if(answers.containsKey(new Integer(51678)) && answers.get(new Integer(51678)).equals("163913")){
%>					<label class="radio">
						<input type="radio" name='51678'  value="163913" checked/>Только от скорости движения
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51678'  value="163913"/>Только от скорости движения
					</label>
				</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51678)) && answers.get(new Integer(51678)).equals("163912")){
%>					<label class="radio">
						<input type="radio" name='51678'  value="163912" checked/>От возраста
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51678'  value="163912"/>От возраста
					</label>
				</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51678)) && answers.get(new Integer(51678)).equals("163915")){
%>					<label class="radio">
						<input type="radio" name='51678'  value="163915" checked/>От концентрации внимания
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51678'  value="163915"/>От концентрации внимания
					</label>
				</br>
<%				}

%>				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51678)) && answers.get(new Integer(51678)).equals("163914")){
%>					<label class="radio">
						<input type="radio" name='51678'  value="163914" checked/>Верны все варианты
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51678'  value="163914"/>Верны все варианты
					</label>
				</br>
<%				}

%>
	</div>
</div>	
<div>
	<img src="images/lineWebinar.png" style="width: 100%">
</div>
<!-- Question 43 ends -->
<!-- Question 44  -->
<div class="center2">

	<div>			
		<br><label>44. Что мешает водителю определить важность возникающей перед ним дорожной ситуации?</label>
	</div>
	<!-- Option 1 -->
	<div>
<%				if(answers.containsKey(new Integer(51679)) && answers.get(new Integer(51679)).equals("163917")){
%>					<label class="radio">
						<input type="radio" name='51679'  value="163917" checked/>Усталость
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51679'  value="163917"/>Усталость
					</label>
				</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51679)) && answers.get(new Integer(51679)).equals("163918")){
%>					<label class="radio">
						<input type="radio" name='51679'  value="163918" checked/>Выполнение нескольких задач одновременно
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51679'  value="163918"/>Выполнение нескольких задач одновременно
					</label>
				</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51679)) && answers.get(new Integer(51679)).equals("163916")){
%>					<label class="radio">
						<input type="radio" name='51679'  value="163916" checked/>Недостаток времени
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51679'  value="163916"/>Недостаток времени
					</label>
				</br>
<%				}

%>				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51679)) && answers.get(new Integer(51679)).equals("163919")){
%>					<label class="radio">
						<input type="radio" name='51679'  value="163919" checked/>Верны все варианты ответов.
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51679'  value="163919"/>Верны все варианты ответов.
					</label>
				</br>
<%				}

%>
	</div>
</div>	
<div>
	<img src="images/lineWebinar.png" style="width: 100%">
</div>
<!-- Question 44 ends -->
<!-- Question 45  -->
<div class="center2">

	<div>			
		<br><label>45.Что такое «Зона неопределенности»</label>
	</div>
	<!-- Option 1 -->
	<div>
<%				if(answers.containsKey(new Integer(51680)) && answers.get(new Integer(51680)).equals("163903")){
%>					<label class="radio">
						<input type="radio" name='51680'  value="163903" checked/>
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51680'  value="163903"/>
					</label>
				</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51680)) && answers.get(new Integer(51680)).equals("163901")){
%>					<label class="radio">
						<input type="radio" name='51680'  value="163901" checked/>
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51680'  value="163901"/>
					</label>
				</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51680)) && answers.get(new Integer(51680)).equals("163902")){
%>					<label class="radio">
						<input type="radio" name='51680'  value="163902" checked/>
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51680'  value="163902"/>
					</label>
				</br>
<%				}

%>
	</div>
</div>	
<div>
	<img src="images/lineWebinar.png" style="width: 100%">
</div>
<!-- Question 45 ends -->
<!-- Question 46  -->
<div class="center2">

	<div>			
		<br><label>46. Что такое полный остановочный путь?</label>
	</div>
	<!-- Option 1 -->
	<div>
<%				if(answers.containsKey(new Integer(11111)) && answers.get(new Integer(11111)).equals("11111")){
%>					<label class="radio">
						<input type="radio" name='11111'  value="11111" checked/>Это путь, пройденный за время реакции водителя и тормозной путь
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='11111'  value="11111"/>Это путь, пройденный за время реакции водителя и тормозной путь
					</label>
				</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(11111)) && answers.get(new Integer(11111)).equals("11111")){
%>					<label class="radio">
						<input type="radio" name='11111'  value="11111" checked/>Это путь, пройденный за время торможения
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='11111'  value="11111"/>Это путь, пройденный за время торможения
					</label>
				</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(11111)) && answers.get(new Integer(11111)).equals("11111")){
%>					<label class="radio">
						<input type="radio" name='11111'  value="11111" checked/>Это путь, пройденный за время реакции водителя, за время срабатывания тормозного
						механизма и тормозной путь
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='11111'  value="11111"/>Это путь, пройденный за время реакции водителя, за время срабатывания тормозного
						механизма и тормозной путь
					</label>
				</br>
<%				}

%>				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(11111)) && answers.get(new Integer(11111)).equals("11111")){
%>					<label class="radio">
						<input type="radio" name='11111'  value="11111" checked/>Это путь, пройденный за время срабатывания тормозного механизма и тормозной путь
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='11111'  value="11111"/>Это путь, пройденный за время срабатывания тормозного механизма и тормозной путь
					</label>
				</br>
<%				}

%>
	</div>
</div>	
<div>
	<img src="images/lineWebinar.png" style="width: 100%">
</div>
<!-- Question 46 ends -->
<!-- Question 47  -->
<div class="center2">

	<div>			
		<br><label>47. Во сколько раз возрастает тормозной путь автомобиля при увеличении скорости в 2 раза</label>
	</div>
	<!-- Option 1 -->
	<div>
<%				if(answers.containsKey(new Integer(51682)) && answers.get(new Integer(51682)).equals("163929")){
%>					<label class="radio">
						<input type="radio" name='51682'  value="163929" checked/>В 1,5 раза
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51682'  value="163929"/>В 1,5 раза
					</label>
				</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51682)) && answers.get(new Integer(51682)).equals("163926")){
%>					<label class="radio">
						<input type="radio" name='51682'  value="163926" checked/>В 2 раза
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51682'  value="163926"/>В 2 раза
					</label>
				</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51682)) && answers.get(new Integer(51682)).equals("163928")){
%>					<label class="radio">
						<input type="radio" name='51682'  value="163928" checked/>В 3 раза
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51682'  value="163928"/>В 3 раза
					</label>
				</br>
<%				}

%>				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51682)) && answers.get(new Integer(51682)).equals("163900")){
%>					<label class="radio">
						<input type="radio" name='51682'  value="163927" checked/>В 4 раза
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51682'  value="163927"/>В 4 раза
					</label>
				</br>
<%				}

%>
	</div>
</div>	
<div>
	<img src="images/lineWebinar.png" style="width: 100%">
</div>
<!-- Question 47 ends -->
<!-- Question 48  -->
<div class="center2">

	<div>			
		<br><label>48.Что влияет на вероятность опрокидывания автомобиля в повороте?</label>
	</div>
	<!-- Option 1 -->
	<div>
<%				if(answers.containsKey(new Integer(51683)) && answers.get(new Integer(51683)).equals("163932")){
%>					<label class="radio">
						<input type="radio" name='51683'  value="163932" checked/>Давление в шинах автомобиля
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51683'  value="163932"/>Давление в шинах автомобиля
					</label>
				</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51683)) && answers.get(new Integer(51683)).equals("163933")){
%>					<label class="radio">
						<input type="radio" name='51683'  value="163933" checked/>Скорость движения
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51683'  value="163933"/>Скорость движения
					</label>
				</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51683)) && answers.get(new Integer(51683)).equals("163931")){
%>					<label class="radio">
						<input type="radio" name='51683'  value="163931" checked/>Высокий центр тяжести (его смещение)
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51683'  value="163931"/>Высокий центр тяжести (его смещение)
					</label>
				</br>
<%				}

%>				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51683)) && answers.get(new Integer(51683)).equals("163930")){
%>					<label class="radio">
						<input type="radio" name='51683'  value="163930" checked/>Радиус поворота
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51683'  value="163930"/>Радиус поворота
					</label>
				</br>
<%				}

%>
	</div>
</div>	
<div>
	<img src="images/lineWebinar.png" style="width: 100%">
</div>
<!-- Question 48 ends -->
<!-- Question 49  -->
<div class="center2">

	<div>			
		<br><label>49. Что такое первичное средство пассивной безопасности?</label>
	</div>
	<!-- Option 1 -->
	<div>
<%				if(answers.containsKey(new Integer(51684)) && answers.get(new Integer(51684)).equals("163934")){
%>					<label class="radio">
						<input type="radio" name='51684'  value="163934" checked/>Подушка безопасности
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51684'  value="163934"/>Подушка безопасности
					</label>
				</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51684)) && answers.get(new Integer(51684)).equals("163936")){
%>					<label class="radio">
						<input type="radio" name='51684'  value="163936" checked/>Бампер
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51684'  value="163936"/>Бампер
					</label>
				</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51684)) && answers.get(new Integer(51684)).equals("163937")){
%>					<label class="radio">
						<input type="radio" name='51684'  value="163937" checked/>Ремень безопасности
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51684'  value="163937"/>Ремень безопасности
					</label>
				</br>
<%				}

%>				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51684)) && answers.get(new Integer(51684)).equals("163935")){
%>					<label class="radio">
						<input type="radio" name='51684'  value="163935" checked/>Программируемые зоны смятия кузова автомобиля при ДТП
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51684'  value="163935"/>Программируемые зоны смятия кузова автомобиля при ДТП
					</label>
				</br>
<%				}

%>
	</div>
</div>	
<div>
	<img src="images/lineWebinar.png" style="width: 100%">
</div>
<!-- Question 49 ends -->
<!-- Question 50  -->
<div class="center2">

	<div>			
		<br><label>50. Ремни безопасности предотвращают водителя и пассажиров от …</label>
	</div>
	<!-- Option 1 -->
	<div>
<%				if(answers.containsKey(new Integer(51685)) && answers.get(new Integer(51685)).equals("163940")){
%>					<label class="radio">
						<input type="radio" name='51685'  value="163940" checked/>Первого удара в ДТП
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51685'  value="163940"/>Первого удара в ДТП
					</label>
				</br>
<%				}  
%>				<!-- Option 2 -->				
<%				if(answers.containsKey(new Integer(51685)) && answers.get(new Integer(51685)).equals("163941")){
%>					<label class="radio">
						<input type="radio" name='51685'  value="163941" checked/>Второго удара в ДТП
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51685'  value="163941"/>Второго удара в ДТП
					</label>
				</br>
<%				}  
%>				<!-- Option 3 -->
<%				if(answers.containsKey(new Integer(51685)) && answers.get(new Integer(51685)).equals("163938")){
%>					<label class="radio">
						<input type="radio" name='51685'  value="163938" checked/>Третьего удара в ДТП
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51685'  value="163938"/>Третьего удара в ДТП
					</label>
				</br>
<%				}

%>				<!-- Option 4 -->
<%				if(answers.containsKey(new Integer(51685)) && answers.get(new Integer(51685)).equals("163939")){
%>					<label class="radio">
						<input type="radio" name='51685'  value="163939" checked/>От всех трех ударов в ДТП
					</label>
				</br>	
<%				}else{
%>					<label class="radio">
						<input type="radio" name='51685'  value="163939"/>От всех трех ударов в ДТП
					</label>
				</br>
<%				}

%>
	</div>
</div>	
<div>
	<img src="images/lineWebinar.png" style="width: 100%">
</div>
<!-- Question 50 ends -->
		</section>
			
				<br>
				<div align="center">
			       	<html:submit value='Save' styleClass="button"/>
			       	<input type="button" value='Cancel' class="buttonRed" onclick="javascript:document.forms['logout'].submit();"/>
				</div>
			</html:form>
		</section>
   </body> 
</html>