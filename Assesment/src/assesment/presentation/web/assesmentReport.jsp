<!doctype html>
<%@page import="assesment.communication.security.SecurityConstants"%>
<%@page import="assesment.presentation.translator.web.administration.user.LogoutAction"%>
<%@page import="assesment.presentation.translator.web.util.Util"%>
<%@page import="assesment.business.AssesmentAccess"%>
<%@page import="assesment.communication.administration.user.UserSessionData"%>
<%@page import="assesment.communication.corporation.CediAttributes"%>
<%@page import="assesment.communication.corporation.CediData"%>
<%@page import="assesment.communication.report.UserMutualReportData"%>
<%@page import="assesment.communication.user.UserData"%>
<%@page import=" assesment.communication.assesment.AssesmentData"%>
<%@page import=" assesment.communication.util.CountryConstants"%>
<%@page import="assesment.communication.module.ModuleData"%>

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
	String link="assesmentReport.jsp";

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
			.col-1 {width: 12%;}
			.col-2 {width: 12%;}
			.col-3 {width: 15%;margin-left:10px;}
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
				table-layout: fixed;
			
			}
			
			.table th, .table2 th,  .table td, .table2 td {
				
				font-family: 'Roboto', sans-serif;
				font-weight: 300;
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
			.searchText {
				color: #1D272D;
				font-family: Roboto, Helvetica, Arial, sans-serif;
				font-size: 13;
				text-align: left;
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
		String assesmentId=String.valueOf(AssesmentData.MUTUAL_DA);
		String columns="";
		String cediName="";
		if (request.getParameter("id")!=null){
			assesmentId=(String)request.getParameter("id");

		}else if(session.getAttribute("assesmentId")!=null){
			assesmentId=(String)session.getAttribute("assesmentId");
		}
		AssesmentData assesment =  sys.getAssesmentReportFacade().findAssesment(new Integer(assesmentId), userSessionData);
		if(Integer.parseInt(assesmentId)==AssesmentData.MUTUAL_DA){
			 cediName=messages.getText("generic.data.mutualSeguridad");
			 columns="11";
		}else{
			cediName=messages.getText(assesment.getName());
			 int colspan=assesment.getModules().size()+7;
			 columns=String.valueOf(colspan);
		}
		session.setAttribute("assesmentId", assesmentId);
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
		if(cedi!=null) session.setAttribute("cedi", cedi.getId());
		
		String refresh="assesmentReport.jsp?id="+assesmentId;
		String report="reportAssesment.jsp?id="+assesmentId;
		String rec0="util/mutual/recommendation0_"+language+"/index.html";
		String rec1="util/mutual/recommendation1_"+language+"/index.html";
		String rec2="util/mutual/recommendation2_"+language+"/index.html";
		String rec3="util/mutual/recommendation3_"+language+"/index.html";
		String rec4="util/mutual/recommendation4_"+language+"/index.html";
		String rec5="util/mutual/recommendation5_"+language+"/index.html";

		link+="?id="+assesmentId;
		String since_day = "";
		String since_month = "";
		String since_year = "";

		String until_day = "";
		String until_month = "";
		String until_year = "";

		if(request.getParameter("since_day")!=null){
			since_day = request.getParameter("since_day");
		}	
		if(request.getParameter("since_month")!=null){
			since_month = request.getParameter("since_month");
		}	
		if(request.getParameter("since_year")!=null){
			since_year = request.getParameter("since_year");
		}	
		if(request.getParameter("until_day")!=null){
			until_day = request.getParameter("until_day");
		}	
		if(request.getParameter("until_month")!=null){
			until_month = request.getParameter("until_month");
		}	
		if(request.getParameter("until_year")!=null){
			until_year = request.getParameter("until_year");
		}	
		
		String date_from=since_year+"-"+since_month+"-"+since_day;
		String date_to=until_year+"-"+until_month+"-"+until_day;

		boolean odd=true;
		CountryConstants countries= new CountryConstants();
		countries.setLAData(messages);
		Collection r = sys.getAssesmentReportFacade().findMutualAssesmentResults(Integer.parseInt(assesmentId),cediId, date_from, date_to, sys.getUserSessionData());
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
		<div class="col-1"><a href='<%=refresh%>' class="button"><%=messages.getText("generic.data.refresh")%>  <img  src="images/mutual_refresh.png" alt="left"></a></div>
		<div class="col-2"><html:form action="/DownloadMutualReport" method="post"><a href="" class="button"><html:submit style="all:unset;"><%=messages.getText("generic.data.downloadxls")%> </html:submit><img  src="images/mutual_download.png" alt="left"></a></html:form></div>
		<div class="col-3"><a href='<%=report+"&action=2"%>' class="button"><%=messages.getText("generic.data.generalresults")%></a></div>
		<div class="col-4"><a href='<%=report+"&action=3"%>' class="button"><%=messages.getText("generic.data.questionsranking")%></a></div>
		<div class="col-5"><a href='<%=report+"&action=4"%>' class="button"><%=messages.getText("generic.data.behavtest")%></a></div>
		<div class="col-6"></div>
		<div class="col-7"></div>
<% 
	if(Integer.parseInt(assesmentId)!=AssesmentData.ABBOTT_NEWDRIVERS){
%>	
		<div class="col-8"><label for="r1"><img  id="left_btn" src="images/mutual_left.png" alt="left"></label></div>
		<div class="col-9"></div>
		<div class="col-10"><label for="r2"><img id="right_btn" src="images/mutual_right.png" alt="right"></label></div>
<%	} %>
	</div>
	</br>
	<table width="100%" border="0" cellpadding="1" cellspacing="1">

		<tr class="searchText">
			<form action="<%=link%>" method="post">
				<span style="width:30px;padding-right:10px;"><%=messages.getText("generic.messages.from") %></span>
				<input type="text" name="since_day" style="width: 45px;"  class="input" value='<%=since_day%>' placeholder="dd"/>/
				<input type="text" name="since_month" style="width: 45px;"  class="input" value='<%=since_month%>' placeholder="mm" />/
				<input type="text" name="since_year" style="width: 60px;"  class="input" value='<%=since_year%>' placeholder="yyyy"/>
			    <span style="width:30px;padding-left:10px;padding-right:10px;"><%=messages.getText("generic.messages.to") %></span>
				<input type="text" name="until_day" style="width: 45px;"  class="input" value='<%=until_day%>' placeholder="dd"/>/
				<input type="text" name="until_month" style="width: 45px;"  class="input" value='<%=until_month%>' placeholder="mm"/>/
				<input type="text" name="until_year" style="width: 60px;padding-right:10px;"  class="input" value='<%=until_year%>' placeholder="yyyy"/>
				<input name="button"   style="padding-left:10px;" type="submit" value='<%=messages.getText("generic.messages.search")%>' class="input"/>
			</form>
			</td>
		</tr>
	</table>
	<div class="slidershow middle">

		<div class="slides">
			<input type="radio" name="r" id="r1" checked>
			<input type="radio" name="r" id="r2">
	<table class=" slide table">
		<tr>
			<th><div style="display:flex;align-items: center;"><a href='<%=refresh+"&sort=firstname"%>' ><img onclick="sortBy('firstName')" src="images/mutual_filter.png" alt="filter"></a><span class="thText"><%=messages.getText("report.users.name")%></span></div></th>
			<th><div style="display:flex;align-items: center;"><a href='<%=refresh+"&sort=lastname"%>' ><img onclick="sortBy('lastName')" src="images/mutual_filter.png" alt="filter"></a><span class="thText"><%=messages.getText("user.data.lastname")%></span></div></th>
			<th><span class="thText" ><%=messages.getText("user.data.mail")%></span></th>
			<th><span class="thText"><%=messages.getText("generic.data.username")%></span></th>
<% 		if(cedi==null&&Integer.parseInt(assesmentId)==AssesmentData.MUTUAL_DA){
%>			<th><span class="thText"><%=messages.getText("generic.data.company")%></span></th>
<% 		}
		if (Integer.parseInt(assesmentId)==AssesmentData.MUTUAL_DA){
%>			<th><div style="display:flex;align-items: center;"><a href="/assesment/assesmentReport.jsp?sort=module1" ><img src="images/mutual_filter.png" alt="filter"></a><span class="thText"><%=messages.getText("assesment1613.module4354.name")%></span></div></th>
			<th><div style="display:flex;align-items: center;"><a href="/assesment/assesmentReport.jsp?sort=module2" ><img src="images/mutual_filter.png" alt="filter"></a><span class="thText"><%=messages.getText("assesment1613.module4356.name")%></span></div></th>
			<th><div style="display:flex;align-items: center;"><a href="/assesment/assesmentReport.jsp?sort=module3" ><img src="images/mutual_filter.png" alt="filter"></a><span class="thText"><%=messages.getText("assesment1613.module4355.name")%></span></div></th>
			<th><div style="display:flex;align-items: center;"><a href="/assesment/assesmentReport.jsp?sort=module4" ><img src="images/mutual_filter.png" alt="filter"></a><span class="thText"><%=messages.getText("assesment1613.module4357.name")%></span></div></th>
<%		}else if(Integer.parseInt(assesmentId)!=AssesmentData.MUTUAL_DA){
%>		<th><span class="thText"><%=messages.getText("user.data.country")%></span></th>	
<%  		Iterator iter=assesment.getModuleIterator();
			int cont=1;
			while (iter.hasNext()){
				ModuleData mod=(ModuleData)iter.next();
%>				<th><div style="display:flex;align-items: center;"><a href='<%=refresh+"&sort=module"+cont%>' ><img src="images/mutual_filter.png" alt="filter"></a><span class="thText"><%=messages.getText("assesment"+assesmentId+".module"+mod.getId()+".name")%></span></div></th>

<% 				cont++;
}%>			

<%		} %>

			<th><div style="display:flex;align-items: center;"><a href='<%=refresh+"&sort=behaviour"%>'><img src="images/mutual_filter.png" alt="filter"></a><span class="thText"><%=messages.getText("assessment.psi")%></div></th>
			<th><div style="display:flex;align-items: center;"><span class="thText"><%=messages.getText("question.type.date")%></span></div></th>
			<th><div style="display:flex;align-items: center;"><a href='<%=refresh+"&sort=ranking"%>' ><img src="images/mutual_filter.png" alt="filter"></a><span class="thText"><%=messages.getText("generic.data.ranking")%>(%)</span></div></th>
			
		</tr>
<%		if(results.size() == 0) {
%>		<tr>
			<td colspan='<%=columns%>'><%=messages.getText("generic.messages.notresult")%></td>
        </tr>
<%   }else {
	  	Iterator it = results.iterator();
		while(it.hasNext()){
			UserMutualReportData result = (UserMutualReportData)it.next();
			odd = !odd;

		
%>	    <tr class='<%=(odd)?"odd":""%>'>
			<td><%=result.getFirstName().toUpperCase()%></td>
			<td><%=result.getLastName().toUpperCase()%></td>
			<td style="word-wrap: break-word;"><%=result.getEmail().toLowerCase()%></td>
			<td><%=result.getLogin().toLowerCase()%></td>
<% 		if(cedi==null&&Integer.parseInt(assesmentId)==AssesmentData.MUTUAL_DA){
			String loc=result.getLocation()==null?"--":sys.getCorporationReportFacade().findCedi(Integer.parseInt(result.getLocation()), userSessionData).getName();
%>			<td><%=loc%></td>
<% 		}
%>	
<% 		if(Integer.parseInt(assesmentId)!=AssesmentData.MUTUAL_DA){ 
%>			<td><%=messages.getText(countries.find(result.getCountry()))%></td>
<% 		}
%>		
<%		if(result.getModule1Completed()){ 
%>			<td style="<%=result.getColorM1()%>;text-align: center;"><%=String.valueOf(result.getModule1())%>%</td>
<%		}else{ 
%>			<td style="text-align: center;"><%=messages.getText("generic.uncompleted")%></td>
<%		} if(result.getModule2Completed()){ 	
%>			<td  style="<%=result.getColorM2()%>;text-align: center;"><%=String.valueOf(result.getModule2())%>%</td>
<%		}else{ 
%>			<td style="text-align: center;"><%=messages.getText("generic.uncompleted")%></td>
<%		} if(result.getModule3Completed()){ 	
%>			<td  style="<%=result.getColorM3()%>;text-align: center;"><%=String.valueOf(result.getModule3())%>%</td>
<%		}else{ 
%>			<td style="text-align: center;"><%=messages.getText("generic.uncompleted")%></td>
<%		}
		if(assesment.getId()==AssesmentData.MUTUAL_DA||assesment.getId()==AssesmentData.ABBOTT_NEWDRIVERS||assesment.getId()==AssesmentData.SUMITOMO){
		if(result.getModule4Completed()){ 	
%>			<td style="<%=result.getColorM4()%>;text-align: center;"><%=String.valueOf(result.getModule4())%>%</td>
<%		}else{ 
%>			<td style="text-align: center;"><%=messages.getText("generic.uncompleted")%></td>
<%		}
		} if (Integer.parseInt(assesmentId)==AssesmentData.ABBOTT_NEWDRIVERS){
			if(result.getModule5Completed()){ 	
%>				<td style="<%=result.getColorM5()%>;text-align: center;"><%=String.valueOf(result.getModule5())%>%</td>
<%			}else{ 
%>				<td style="text-align: center;"><%=messages.getText("generic.uncompleted")%></td>
<%			}
			if(result.getModule6Completed()){ 	
%>				<td style="<%=result.getColorM6()%>;text-align: center;"><%=String.valueOf(result.getModule6())%>%</td>
<%			}else{ 
%>				<td style="text-align: center;"><%=messages.getText("generic.uncompleted")%></td>
<%			} 			
%>		
<% 		}
		if(result.getBehaviourCompleted()){ 	
%>			<td  style="<%=result.getPsiColor()%>;text-align: center;"><%= messages.getText(result.getPsiText())%></td>
<%		}else{ 
%>			<td style="text-align: center;"><%=messages.getText("generic.uncompleted")%></td>
<%		} 
		if(result.getEndDate()!=null){ 
%>			<td  style="text-align: center;"><%= result.getEndDate()%></td>
<%		}else{ 
%>			<td style="text-align: center;">--</td>
<%		}
%>
<%	if (Integer.parseInt(assesmentId)==AssesmentData.ABBOTT_NEWDRIVERS){
		
		if(!result.getBehaviourCompleted()|| !result.getModule1Completed() || !result.getModule2Completed()||!result.getModule3Completed()||
		!result.getModule4Completed()||!result.getModule6Completed()||!result.getModule6Completed()){ 	
%>			<td style="text-align: center;"><%=messages.getText("generic.uncompleted")%></td>
<%      }else{ 
%>			<td style="text-align: center;"><%= String.valueOf(result.getRanking())%></td>
<%		}
	}else if (Integer.parseInt(assesmentId)==AssesmentData.MUTUAL_DA || Integer.parseInt(assesmentId)==AssesmentData.SUMITOMO){
		if(!result.getBehaviourCompleted()|| !result.getModule1Completed() || !result.getModule2Completed()||!result.getModule3Completed()||
		!result.getModule4Completed()){ 	
%>			<td style="text-align: center;"><%=messages.getText("generic.uncompleted")%></td>
<%      }else{ 
%>			<td  style="<%=result.getTotalColor()%>; text-align:center;"><%= String.valueOf(result.getRanking())%></td>
<%		}
	}else if(Integer.parseInt(assesmentId)==AssesmentData.ABBEVIE_LATAM){
		if(!result.getBehaviourCompleted()|| !result.getModule1Completed() || !result.getModule2Completed()||!result.getModule3Completed()){ 	
		%>			<td style="text-align: center;"><%=messages.getText("generic.uncompleted")%></td>
		<%      }else{ 
		%>			<td style="text-align: center;"><%= String.valueOf(result.getRanking())%></td>
		<%		}
	}
%>	</tr>
<%		
}
%>

	</table>
	<div class="slide">
		<table class="table2">
			<tr>
				<th><span class="thText"><%=messages.getText("report.users.name")%></span></th>
				<th><span class="thText"><%=messages.getText("user.data.lastname")%></span></th>
				<th><span class="thText"><%=messages.getText("generic.data.username")%></span></th>
<% 		if(cedi==null && assesment.getId()!=AssesmentData.ABBEVIE_LATAM&&  assesment.getId()!=AssesmentData.SUMITOMO){
%>				<th><span class="thText"><%=messages.getText("generic.data.company")%></span></th>
<% 		}
%>
<%  		Iterator iter=assesment.getModuleIterator();
			while (iter.hasNext()){
				ModuleData mod=(ModuleData)iter.next();
%>				<th><div style="display:flex;align-items: center;"><a target="_blank"  href='<%=rec0%>'  ><img src="images/mutual_info.png" alt="filter"></a><span class="thText"><%=messages.getText("assesment"+assesmentId+".module"+mod.getId()+".name")%> <%=messages.getText("generic.data.recommendation")%></span></div></th>
<% 			}
%>			

<%		 %><th><div style="display:flex;align-items: center;"><a  target="_blank" href='<%=rec0 %>' ><img src="images/mutual_info.png" alt="info"></a><span class="thText"><%=messages.getText("assessment.psi")%> <%=messages.getText("generic.data.recommendation")%></span></div></th>
				
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
<% 		if(cedi==null  && assesment.getId()!=AssesmentData.ABBEVIE_LATAM && assesment.getId()!=AssesmentData.SUMITOMO){
			String loc=result.getLocation()==null?"--":sys.getCorporationReportFacade().findCedi(Integer.parseInt(result.getLocation()), userSessionData).getName();
%>			<td><%=loc%></td>
<% 		}
%>
<%		if(!result.getModule1Completed()){
%>			<td><%=messages.getText("generic.uncompleted")%></td>
<%		}else if(result.getMod1Recommendation().equals("question25099.answer83751.text")){
%>			<td style="color: #228B22;"><%=messages.getText(result.getMod1Recommendation())%></td>
<%		}else{
%>			<td style="color: #da2a2a;"><a target="_blank" href='<%=rec1%>'><%=messages.getText(result.getMod1Recommendation())%></td>
			
<%		}

	if(assesment.getId()==AssesmentData.ABBEVIE_LATAM||assesment.getId()==AssesmentData.SUMITOMO){
		rec3=rec2;
		rec2="util/abbevie/recommendation2_"+language+"/index.html";
	}
%>	
<%		if(!result.getModule2Completed()){
%>			<td><%=messages.getText("generic.uncompleted")%></td>
<%		}else if(result.getMod2Recommendation().equals("question25099.answer83751.text")){
%>			<td style="color: #228B22;"><%=messages.getText(result.getMod2Recommendation())%></td>
<%		}else{
%>			<td style="color: #da2a2a;"><a target="_blank" href='<%=rec2%>'><%=messages.getText(result.getMod2Recommendation())%></td>
			
<%		}%>
<%		if(!result.getModule3Completed()){
%>			<td><%=messages.getText("generic.uncompleted")%></td>
<%		}else if(result.getMod3Recommendation().equals("question25099.answer83751.text")){
%>			<td style="color: #228B22;"><%=messages.getText(result.getMod3Recommendation())%></td>
<%		}else{
%>			<td style="color: #da2a2a;" ><a target="_blank" href='<%=rec3%>'><%=messages.getText(result.getMod3Recommendation())%></td>
			
<%		}
	if(assesment.getId()==AssesmentData.MUTUAL_DA ||assesment.getId()==AssesmentData.SUMITOMO){
%>
<%		if(!result.getModule4Completed()){
%>			<td><%=messages.getText("generic.uncompleted")%></td>
<%		}else if(result.getMod4Recommendation().equals("question25099.answer83751.text")){
%>			<td style="color: #228B22;"><%=messages.getText(result.getMod4Recommendation())%></td>
<%		}else{
%>			<td style="color: #da2a2a;"><a target="_blank" href='<%=rec4%>'><%=messages.getText(result.getMod4Recommendation())%></td>
			
<%		}
	}%>
<%		if(!result.getBehaviourCompleted()){
%>			<td><%=messages.getText("generic.uncompleted")%></td>
<%		}else if(result.getPsiColor().equals("background-color: rgb(30, 209, 105);")){
%>			<td style="color: #228B22;"><%=messages.getText("question25099.answer83751.text")%></td>
<%		}else{
%>			<td style="color: #da2a2a;"><a target="_blank" href='<%=rec5%>'><%=messages.getText("generic.data.apply")%></td>
			
<%		}%>
			
        </tr>
<%	
}
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