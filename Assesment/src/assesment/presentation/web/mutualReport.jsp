<!doctype html>
<%@page import="assesment.communication.security.SecurityConstants"%>
<%@page import="assesment.presentation.translator.web.administration.user.LogoutAction"%>
<%@page import="assesment.presentation.translator.web.util.Util"%>
<%@page import="assesment.business.AssesmentAccess"%>
<%@page import="assesment.communication.administration.user.UserSessionData"%>
<%@page import="assesment.communication.corporation.CediAttributes"%>
<%@page import="assesment.communication.report.UserMutualReportData"%>
<%@page import="assesment.communication.user.UserData"%>
<%@page import="assesment.communication.language.Text"%>
<%@page import="java.util.*"%>

	

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html" 
%>

<%@ taglib uri="/WEB-INF/struts-bean.tld"
        prefix="bean" 
%>

<%
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	String language = (sys != null) ? sys.getUserSessionData().getLenguage() : System.getProperty("user.language");
    String next = (sys.getUserSessionData().getRole().equals(SecurityConstants.ADMINISTRATOR)) ? "index.jsp" : "logout.jsp";

%>

<%@page import="java.util.HashMap"%>
<html lang='<%=language%>'>
	<head>
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<link rel="apple-touch-icon-precomposed" sizes="57x57" href="images/apple-touch-icon-57x57-precomposed.png" />
		<link rel="apple-touch-icon-precomposed" sizes="72x72" href="images/apple-touch-icon-72x72-precomposed.png" />
		<link rel="apple-touch-icon-precomposed" sizes="114x114" href="images/apple-touch-icon-114x114-precomposed.png" />
		<link rel="apple-touch-icon-precomposed" sizes="144x144" href="images/apple-touch-icon-144x144-precomposed.png" />
		<meta name="viewport" content="user-scalable=no, width=980" />
		<meta name="description" content="">
		<meta name="keywords" content="">
		<title>CEPA Driver Assessment</title>

		<!--[if lt IE 9]>
			<script type="text/javascript" src="scripts/html5shiv.min.js"></script>
		<![endif]-->
		<style type="text/css">	
			.col-1 {width: 15%;}
			.col-2 {width: 15%;}
			.col-3 {width: 15%;}
			.col-4 {width: 15%;}
			.col-5 {width: 15%;}
			.col-6 {width: 2%;}
			.col-7 {width: 3%;}
			.col-8 {width: 4.5%;}
			.col-9 {width:	1%;}
			.col-10 {width: 4.5%;}
			
			[class*="col-"] {
				float: left;
				padding: 1px;
			
			  }
			
			  .row {
				clear: both;
				position: relative;
				display: flex;
				justify-content: space-between;
				align-items: center;
			  }
			  .row::after {
				content: "";
				clear: both;
				display: table;
			  }
			  .row::before {
				content: "";
				clear: both;
				display: table;
			  }
			.row img{
				width: 60%;
			}
			  .table, .table2 {
				width: 100%;
				border-collapse: collapse;
				border-bottom: 1px solid #d6d6d6;
				margin-top: 5px;
			
			}
			
			.table th, .table2 th,  .table td, .table2 td {
				
				font-family: 'Roboto', sans-serif;
				font-weight: 500;
				font-size: 1vw;
				white-space: normal;
				color: #434343;
				background-color: #f7f7f7;
				border-top:1px solid #d8cccc;
				border-left:1px solid #a39999;
				border-right:1px solid #dcdcdc;
			}
			
			.table th, .table2 th {
				font-size: 0.8vw;
				font-weight: 600;
				letter-spacing: 0.01em;
				color: rgb(14, 13, 13);
				background-color: #ffffff;
				text-align:left;
				padding: 0.5px 10px 10px 2.5px;
				border-bottom: 1px solid #d6d6d6;
				white-space:normal;

			}
	
			.table2 a{
				color: inherit;  
			}
			
			.table td, .table2 td {
				padding:12px 5px 12px 5px;
			   white-space: pre-wrap;      /* CSS3 */   
			   white-space: -moz-pre-wrap; /* Firefox */    
			   white-space: -pre-wrap;     /* Opera <7 */   
			   white-space: -o-pre-wrap;   /* Opera 7 */    
			   word-wrap: break-word;      /* IE */
				}
				
			.table .odd td, .table2 .odd td{
				background-color: #ededed;}
			
			.table img {
				float:left;
				width: 13px;
				height: auto;
			}
			.table2 img {
				float:left;
				width: 20px;
				height: auto;
			}	
			.thText{
				padding: 9.5px 0px 0px 7.5px;

		    }
			a.button {
				width: -moz-fit-content;
				width: fit-content;
				font-family: Arial, Helvetica, sans-serif;
				font-size: 1vw;
				font-weight: 500;
				text-decoration: none;
				text-align:center;
				letter-spacing: 0.10em;
				color:rgb(7, 6, 6);
				padding: 10px;
				border-radius: 10px;
				background-color:#eceeed;
				border-top: 1px solid #121312;
				border-right:1px solid #121312;
				border-bottom:1px solid #121312;
				border-left: 1px solid #121312;
				margin:3px;
				display:flex;
				align-items: center;
				white-space: nowrap;
			}
			
			a.button:hover {
				width: -moz-fit-content;
				width: fit-content;
				font-size: 1vw;
				font-weight: 500;
				display:flex;
				background-color:rgb(255, 255, 255);
				border-top: 1px solid #121312;
				border-right:1px solid #121312;
				border-bottom:1px solid #121312;
				border-left: 1px solid #121312;
				}
			
			
			  .header {
				margin: auto;
			  }
			  #left {
				float:left;
				width:40%;
				text-align: left;
				
			}
			
			#right {
			    position:relative;
				float:right;
				width:60%;
				text-align: right;
			
			}
			#cepa {
				left:0;
				float:left;
				padding:10px;
			
			}
			#logout_user{
				width:50%;
				display:flex;
				white-space: nowrap;
				align-items: center;
				float:right;
				font-size:2vw;
				font-family: Arial, Helvetica, sans-serif;
				text-align:right;
			}
			
			.title {
				clear: both;
				margin-right:auto;
				font-family: Arial, Helvetica, sans-serif;
				color:rgb(36, 32, 33);
				padding: 20px;
				font-size:3vw;
				text-align: center;
			}	
			
			.button img {
				width: -moz-fit-content;
				width: fit-content;
				width: 12%;
			
			  }
			
			.slidershow{
			
				width: 100%;
				overflow:hidden;
			  }
			.middle{
				position: relative;
				/*left: 50%;*/
				left: 50%;
				transform: translate(-50%);
			  }
			
			#r1:checked ~ .table{
				margin-left: 0;
			  }
			  #r2:checked ~ .table{
				margin-left: -50%;
			  }
			
			  input[name="r"]{
				  position: absolute;
				  visibility: hidden;
			  }
			  
			  .slides{
			
				width: 200%;
				display:flex;
			  }
			  
			  .slide{
				position: static;
				width: 50%;
				transition: 0.6s;
			  }
			  .slide table{
				width: 100%;
			  }
			
			  #right_btn, #left_btn{
				  cursor: pointer;
			  }
			  #logout_icon{
			  	width:30px;
			  	height:auto;
			  	margin-top:10px;
			  	margin-left:20px;
			  }
		</style>
	
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
		RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
		dispatcher.include(request,response);
		String login=userSessionData.getFilter().getLoginName();
		UserData user = sys.getUserReportFacade().findUserByPrimaryKey(login, userSessionData);
		CediAttributes cedi=null;
		String cediName=messages.getText("generic.data.mutualSeguridad");
		Integer cediId=null;
		if(sys.getCorporationReportFacade().findCediUser(login, userSessionData).length!=0){
			cediId=sys.getCorporationReportFacade().findCediUser(login, userSessionData)[0];
		}
		if (cediId!=null){
			cedi=sys.getCorporationReportFacade().findCediAttributes(cediId, userSessionData);
		}
		if(cedi!=null){
			cediName=cedi.getName();
		}
		
		boolean odd=true;
		Collection r = sys.getAssesmentReportFacade().findMutualAssesmentResults(cediId, sys.getUserSessionData());
		ArrayList<UserMutualReportData> results = new ArrayList(r);
		if(request.getParameter("sort")!=null){
			String criteria=(String)request.getParameter("sort");
			UserMutualReportData u=new UserMutualReportData();
			u.sortCollection(results, criteria);
		}
		
%>   
  <body>
	<header class="header">
		<div id="left">
			<div id="cepa">
				<img  width=100% src="images/main_logo.png" alt="cepa">
			</div>
		</div>
		<div id="right">
			<div  id="logout_user">
				<span > <%=user.getFirstName() + " "+ user.getLastName() %></span>
				<a href="<%=next%>">
					<img id="logout_icon"  src="images/mutual_logout.png" alt="logout">
				</a>
			</div>
		</div>

	</br></br></br></br></br>
	</header>
	<main>
	
	
	<div class="title">
		<%=cediName %>
	</div>
	<div class="row">
		<div class="col-1"><a href="mutualReport.jsp" class="button"><%=messages.getText("generic.data.refresh")%>  <img  src="images/mutual_refresh.png" alt="left"></a></div>
		<div class="col-2"><html:form action="/DownloadMutualReport" method="post"><a href="" class="button"><html:submit style="all:unset;"><%=messages.getText("generic.data.downloadxls")%> </html:submit><img  src="images/mutual_download.png" alt="left"></a></html:form></div>
		<div class="col-3"><a href="/assesment/reportMutual.jsp?action=2" class="button"><%=messages.getText("generic.data.generalresults")%></a></div>
		<div class="col-4"><a href="/assesment/reportMutual.jsp?action=3" class="button"><%=messages.getText("generic.data.questionsranking")%></a></div>
		<div class="col-5"><a href="/assesment/reportMutual.jsp?action=4" class="button"><%=messages.getText("generic.data.behavtest")%></a></div>
		<div class="col-6"></div>
		<div class="col-7"></div>
		<div class="col-8"><label for="r1"><img  id="left_btn" src="images/mutual_left.png" alt="left"></label></div>
		<div class="col-9"></div>
		<div class="col-10"><label for="r2"><img id="right_btn" src="images/mutual_right.png" alt="right"></label></div>

	</div>
	<div class="slidershow middle">

		<div class="slides">
			<input type="radio" name="r" id="r1" checked>
			<input type="radio" name="r" id="r2">
	<table class=" slide table">
		<tr>
			<th><div style="display:flex;align-items: center;"><a href="/assesment/mutualReport.jsp?sort=firstname" ><img onclick="sortBy('firstName')" src="images/mutual_filter.png" alt="filter"></a><span class="thText"><%=messages.getText("report.users.name")%></span></div></th>
			<th><div style="display:flex;align-items: center;"><a href="/assesment/mutualReport.jsp?sort=lastname" ><img onclick="sortBy('lastName')" src="images/mutual_filter.png" alt="filter"></a><span class="thText"><%=messages.getText("user.data.lastname")%></span></div></th>
			<th><span class="thText"><%=messages.getText("user.data.mail")%></span></th>
			<th><span class="thText"><%=messages.getText("generic.data.username")%></span></th>
<% 		if(cedi==null){
%>			<th><span class="thText"><%=messages.getText("generic.data.company")%></span></th>
<% 		}
%>			<th><div style="display:flex;align-items: center;"><a href="/assesment/mutualReport.jsp?sort=module1" ><img src="images/mutual_filter.png" alt="filter"></a><span class="thText"><%=messages.getText("assesment1613.module4354.name")%></span></div></th>
			<th><div style="display:flex;align-items: center;"><a href="/assesment/mutualReport.jsp?sort=module2" ><img src="images/mutual_filter.png" alt="filter"></a><span class="thText"><%=messages.getText("assesment1613.module4356.name")%></span></div></th>
			<th><div style="display:flex;align-items: center;"><a href="/assesment/mutualReport.jsp?sort=module3" ><img src="images/mutual_filter.png" alt="filter"></a><span class="thText"><%=messages.getText("assesment1613.module4355.name")%></span></div></th>
			<th><div style="display:flex;align-items: center;"><a href="/assesment/mutualReport.jsp?sort=module4" ><img src="images/mutual_filter.png" alt="filter"></a><span class="thText"><%=messages.getText("assesment1613.module4357.name")%></span></div></th>
			<th><div style="display:flex;align-items: center;"><a href="/assesment/mutualReport.jsp?sort=behaviour"><img src="images/mutual_filter.png" alt="filter"></a><span class="thText"><%=messages.getText("assessment.psi")%></div></th>
			<th><div style="display:flex;align-items: center;"><a href="/assesment/mutualReport.jsp?sort=ranking" ><img src="images/mutual_filter.png" alt="filter"></a><span class="thText"><%=messages.getText("generic.data.ranking")%>(%)</span></div></th>
		</tr>
<%		if(results.size() == 0) {
%>		<tr>
			<td colspan="11"><%=messages.getText("generic.messages.notresult")%></td>
        </tr>
<%   }else {
    	Iterator it = results.iterator();
		while(it.hasNext()){
			UserMutualReportData result = (UserMutualReportData)it.next();
			odd = !odd;

		
%>	    <tr class='<%=(odd)?"odd":""%>'>
			<td><%=result.getFirstName().toUpperCase()%></td>
			<td><%=result.getLastName().toUpperCase()%></td>
			<td><%=result.getEmail().toLowerCase()%></td>
			<td><%=result.getLogin().toLowerCase()%></td>
<% 		if(cedi==null){
%>			<td><%=result.getLocation()%></td>
<% 		}
%>			
<%		if(result.getModule1Completed()){ 
%>			<td style="<%=result.getColorM1()%>"><%=String.valueOf(result.getModule1())%>%</td>
<%		}else{ 
%>			<td><%=messages.getText("generic.uncompleted")%></td>
<%		} if(result.getModule2Completed()){ 	
%>			<td  style="<%=result.getColorM2()%>"><%=String.valueOf(result.getModule2())%>%</td>
<%		}else{ 
%>			<td><%=messages.getText("generic.uncompleted")%></td>
<%		} if(result.getModule3Completed()){ 	
%>			<td  style="<%=result.getColorM3()%>"><%=String.valueOf(result.getModule3())%>%</td>
<%		}else{ 
%>			<td><%=messages.getText("generic.uncompleted")%></td>
<%		} if(result.getModule4Completed()){ 	
%>			<td style="<%=result.getColorM4()%>"><%=String.valueOf(result.getModule4())%>%</td>
<%		}else{ 
%>			<td><%=messages.getText("generic.uncompleted")%></td>
<%		} if(result.getBehaviourCompleted()){ 	
%>			<td  style="<%=result.getPsiColor()%>"><%= messages.getText(result.getPsiText())%></td>
<%		}else{ 
%>			<td><%=messages.getText("generic.uncompleted")%></td>
<%		} 
%>
<%		if(!result.getBehaviourCompleted()|| !result.getModule1Completed() || !result.getModule2Completed()||!result.getModule3Completed()||
		!result.getModule4Completed()){ 	
%>			<td><%=messages.getText("generic.uncompleted")%></td>
<%      }else{ 
%>			<td  style="<%=result.getTotalColor()%>"><%= String.valueOf(result.getRanking())%></td>
<%		}
%></tr>

<% 		}
%>	

	</table>
	<div class="slide">
		<table class="table2">
			<tr>
				<th><span class="thText"><%=messages.getText("report.users.name")%></span></th>
				<th><span class="thText"><%=messages.getText("user.data.lastname")%></span></th>
				<th><span class="thText"><%=messages.getText("generic.data.username")%></span></th>
<% 		if(cedi==null){
%>				<th><span class="thText"><%=messages.getText("generic.data.company")%></span></th>
<% 		}
%>				<th><div style="display:flex;align-items: center;"><a href="util/mutual/recommendation0/index.html" ><img src="images/mutual_info.png" alt="info"></a><span class="thText"><%=messages.getText("assesment1613.module4354.name")%> <%=messages.getText("generic.data.recommendation")%></span></div></th>
				<th><div style="display:flex;align-items: center;"><a href="util/mutual/recommendation0/index.html" ><img src="images/mutual_info.png" alt="info"></a><span class="thText"><%=messages.getText("assesment1613.module4356.name")%> <%=messages.getText("generic.data.recommendation")%></span></div></th>
				<th><div style="display:flex;align-items: center;"><a href="util/mutual/recommendation0/index.html" ><img src="images/mutual_info.png" alt="info"></a><span class="thText"><%=messages.getText("assesment1613.module4355.name")%> <%=messages.getText("generic.data.recommendation")%></span></div></th>
				<th><div style="display:flex;align-items: center;"><a href="util/mutual/recommendation0/index.html" ><img src="images/mutual_info.png" alt="info"></a><span class="thText"><%=messages.getText("assesment1613.module4357.name")%> <%=messages.getText("generic.data.recommendation")%></span></div></th>
				<th><div style="display:flex;align-items: center;"><a href="util/mutual/recommendation0/index.html" ><img src="images/mutual_info.png" alt="info"></a><span class="thText"><%=messages.getText("assessment.psi")%> <%=messages.getText("generic.data.recommendation")%></span></div></th>
				
		</tr>	
<%    	it = results.iterator();
		while(it.hasNext()){
			UserMutualReportData result = (UserMutualReportData)it.next();
			odd = !odd;

%>
		<tr class='<%=(odd)?"odd":""%>'>
			<td><%=result.getFirstName().toUpperCase()%></td>
			<td><%=result.getLastName().toUpperCase()%></td>
			<td><%=result.getLogin().toLowerCase()%></td>
<% 		if(cedi==null){
%>			<td><%=result.getLocation()%></td>
<% 		}
%>
<%		if(!result.getModule1Completed()){
%>			<td><%=messages.getText("generic.uncompleted")%></td>
<%		}else if(result.getMod1Recommendation().equals("question25099.answer83751.text")){
%>			<td style="color: #228B22;"><%=messages.getText(result.getMod1Recommendation())%></td>
<%		}else{
%>			<td style="color: #da2a2a;"><a href="util/mutual/recommendation1/index.html"><%=messages.getText(result.getMod1Recommendation())%></td>
			
<%		}%>
<%		if(!result.getModule2Completed()){
%>			<td><%=messages.getText("generic.uncompleted")%></td>
<%		}else if(result.getMod2Recommendation().equals("question25099.answer83751.text")){
%>			<td style="color: #228B22;"><%=messages.getText(result.getMod2Recommendation())%></td>
<%		}else{
%>			<td style="color: #da2a2a;"><a href="util/mutual/recommendation2/index.html"><%=messages.getText(result.getMod2Recommendation())%></td>
			
<%		}%>
<%		if(!result.getModule3Completed()){
%>			<td><%=messages.getText("generic.uncompleted")%></td>
<%		}else if(result.getMod3Recommendation().equals("question25099.answer83751.text")){
%>			<td style="color: #228B22;"><%=messages.getText(result.getMod3Recommendation())%></td>
<%		}else{
%>			<td style="color: #da2a2a;" ><a href="util/mutual/recommendation3/index.html"><%=messages.getText(result.getMod3Recommendation())%></td>
			
<%		}%>
<%		if(!result.getModule4Completed()){
%>			<td><%=messages.getText("generic.uncompleted")%></td>
<%		}else if(result.getMod4Recommendation().equals("question25099.answer83751.text")){
%>			<td style="color: #228B22;"><%=messages.getText(result.getMod4Recommendation())%></td>
<%		}else{
%>			<td style="color: #da2a2a;"><a href="util/mutual/recommendation4/index.html"><%=messages.getText(result.getMod4Recommendation())%></td>
			
<%		}%>
<%		if(!result.getBehaviourCompleted()){
%>			<td><%=messages.getText("generic.uncompleted")%></td>
<%		}else if(result.getPsiColor().equals("background-color: rgb(30, 209, 105);")){
%>			<td style="color: #228B22;"><%=messages.getText("question25099.answer83751.text")%></td>
<%		}else{
%>			<td style="color: #da2a2a;"><a href="util/mutual/recommendation5/index.html"><%=messages.getText("generic.data.apply")%></td>
			
<%		}%>
			
        </tr>
<%}
%>
	
	
		</table>
<%}
%>
	</div>
  </div>
</div>
</main>
</body>
<%} %>
</html>