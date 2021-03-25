<!doctype html>
<%@page import="assesment.business.assesment.AssesmentReportFacade"%>
<%@page import="assesment.communication.assesment.CategoryData"%>
<%@page import="assesment.communication.assesment.GroupData"%>
<%@page import="assesment.communication.assesment.GroupUsersData"%>
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
<%@page import="assesment.communication.assesment.AssesmentAttributes"%>
<%@page import=" assesment.communication.util.CountryConstants"%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html" 
%>

<%@ taglib uri="/WEB-INF/struts-bean.tld"
        prefix="bean" 
%>

<%
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	String language = (sys != null) ? sys.getUserSessionData().getLenguage() : System.getProperty("user.language");
	UserSessionData userSessionData = sys.getUserSessionData();
	Text messages = sys.getText(); 

	AssesmentReportFacade assessmentReport = sys.getAssesmentReportFacade();
	String groupId =request.getParameter("id");
	String role = userSessionData.getRole(); 
	GroupData group = assessmentReport.findGroup(new Integer(groupId),sys.getUserSessionData());
	 Collection<AssesmentAttributes> a= new LinkedList<AssesmentAttributes>();
	 Iterator itCategories= group.getCategories().iterator();
	 while(itCategories.hasNext()){
		 CategoryData c=(CategoryData)itCategories.next();
		 Iterator as= c.getAssesments().iterator();
		 while(as.hasNext()){
			 AssesmentAttributes assesm = (AssesmentAttributes)as.next();
			 a.add(assesm);
		 }
	 }
	GroupUsersData groupResults= assessmentReport.getGroupUsersResults(new Integer(groupId),sys.getUserSessionData());
	groupResults.setUsers(sys.getUserReportFacade().findGroupUsers(groupResults.getId(), sys.getUserSessionData()));
	groupResults.setAssesments(a);
	CountryConstants countries= new CountryConstants();
	countries.setLAData(messages);
	int cont=3;	

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
		   <script src="./js/Chart.min.js"></script>
		
       <style type="text/css">
        body { font-family:Arial, Helvetica, Sans-Serif; font-size:0.8em;}
        #charts { max-width: 100%;
            margin:auto;  }

        #charts td { 
            padding:10px;
            background:#ffffff;
            border-right: 1px rgb(145, 148, 155);
            border-top: 0px rgb(189, 206, 243);
            border-left: 0px rgb(189, 206, 243);
            border-bottom: 0px black; 
         }
        #report { border-collapse:collapse;}
        #report h4 { margin:0px; padding:0px;}
        #report img { float:right;}
        #report td { background:#ffffff;padding:15px;  border: 1px solid black;border-color:#12354e;  }
        #report tr.odd td { background-color: #dee8f0;padding:10px; color:black; cursor:pointer; border: 1px;border-color:#12354e; padding:10px;font-family: 'Roboto', sans-serif;
				font-weight: 500;
				font-size: 1vw;
				white-space: normal;}
        #report div.arrow { background:transparent url(images/arrows.png) no-repeat scroll 0px -16px; width:16px; height:16px; display:block;}
        #report div.up { background-position:0px 0px;}
     
        .col-1 {width: 10%;
        }
        .col-2 {width: 20%; 
                text-align: center;
                  border-right: 1px rgb(218, 215, 215);
                  border-top: 0px;
                  border-left: 0px;
                  border-bottom: 0px;

                 border-style: solid;
                 padding-top: 7px;
                 padding-bottom: 7px;}
			.col-3 {width: 20%; border-right: 1px rgb(218, 215, 215);
                  border-top: 0px black;                text-align: center;

                  border-left: 0px black;
                  border-bottom: 0px black;

                 border-style: solid;}
			.col-4 {width: 12.66%;border-right: 1px rgb(218, 215, 215);
                  border-top: 0px black;                text-align: center;

                  border-left: 0px black;
                  border-bottom: 0px black;

                 border-style: solid;}
			.col-5 {width: 12.66%;border-right: 1px rgb(218, 215, 215);
                  border-top: 0px black;                text-align: center;

                  border-left: 0px black;
                  border-bottom: 0px black;

                 border-style: solid;}
			.col-6 {width: 12.66%;border-right: 1px rgb(218, 215, 215);
                  border-top: 0px black;                text-align: center;

                  border-left: 0px black;
                  border-bottom: 0px black;

                 border-style: solid;}
			.col-7 {width: 12.66%;border-right: 1px rgb(218, 215, 215);
                  border-top: 0px black;                text-align: center;

                  border-left: 0px black;
                  border-bottom: 0px black;

                 border-style: solid;}
			.col-7 {width: 12.66%;border-right: 1px rgb(218, 215, 215);
                  border-top: 0px black;                text-align: center;

                  border-left: 0px black;
                  border-bottom: 0px black;

                 border-style: solid;}

			
			[class*="col-"] {
				float: left;
				padding: 1px;
			
			  }
			
			  .row {
                  background-color: rgb(236, 236, 235);
                  border-right: 1px rgb(189, 206, 243);
                  border-top: 0.5px rgb(189, 206, 243);
                  border-left: 1px rgb(189, 206, 243);
                  border-bottom: 0px black;
                 border-style: solid;
                 padding-top: 7px;
                 padding-bottom: 7px;
				clear: both;
				position: relative;
				display: flex;
				justify-content: space-between;
				align-items: center;
              }
              .odd{
                  background-color: rgb(255, 255, 255);
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
              .table, .table2, .table3{
				border-collapse: collapse;
				border-bottom: 1px solid #d6d6d6;
                margin-top: 20px;
                margin-left: 20px;
                margin-right: 10px;
			
            }  
            .table{
                width:45%;
            }
                        
            .table3 th, td, .table3 th,td{
                padding: 10px;
				font-family: 'Roboto', sans-serif;
				font-weight: 500;
				font-size: 1vw;
				white-space: normal;
				color: #434343;
				background-color: #ffffff;
				border-top:0.5px solid #918d8d;
				border-left:0.5px solid #a39999;
				border-right:0.5px solid #9c9c9c;

			
            }

            .table3 img {
				float:left;
                width: 20px;
                padding-right: 10px;
				height: auto;
			}
            .table th, .table2 th {
				
				font-weight:600;
                padding:10px;
                font-size: 1vw;

				color: rgb(255, 255, 255);
				background-color: #7682c9;
				text-align:center;
				
				border-bottom: 1px solid #d6d6d6;
				white-space:normal;

            }
            .table3 th {
				
				font-weight:600;
				
				color: rgb(255, 255, 255);
				background-color: #342997;
				text-align:left;
				border-bottom: 0.5px solid #7e7b7b;
				white-space:normal;

            }
            .table3{
                max-width:100%;
				margin-bottom:1.5%;
                margin-left: auto;
                margin-right: auto;
                table-layout: fixed;
            }
            canvas{
                clear:both;
                width: 200 px;}

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
			
    </style>
    <!--     <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js" type="text/javascript"></script>
     -->
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript">
	        $(document).ready(function(){
            $("#report tr:odd").addClass("odd");
            $("#report tr:not(.odd)").hide();
            $("#report tr:first-child").show();
            
            $("#report tr.odd").click(function(){
                $(this).next("tr").toggle();
                $(this).find(".arrow").toggleClass("up");
            });
            //$("#report").jExpand();
        });
    </script>   
	</head>
<body>
    <header>
        <img src="images/main_logo_large.png">
    </header>
   	<html:form action="/DownloadResult" target="_blank">
		<html:hidden property="assessment"/>
		<html:hidden property="login"/>
	    <html:hidden property="reportType"/>
		
	</html:form>
	<html:form action="/DownloadGroup">
		<html:hidden property="group" value="<%=groupId%>" />
	</html:form>	
    <!--TOTAL DE PARTICIPANTES-->
    <div>
        <span style="float:left;margin-left: 60px;margin-top: 50px;font-weight: bold; font-size: 1.5vw;"><%=messages.getText("assesment.report.totalparticipants") %></span>
        <span style="float:right; margin-right: 170px; margin-top: 50px;font-weight: bold; font-size: 1.5vw;"><%=messages.getText("assesment.report.activitybycountry") %></span>

    </div>
    <table  style="clear:both;float:left;" class="table">
        <tr>
            <th style="background-color: #ffffff;"></th>
<%			Iterator itCountries= groupResults.getCountries().iterator();
			while (itCountries.hasNext()){
				Integer c= (Integer)itCountries.next();
%>				<th><%=countries.find(String.valueOf(c)) %></th>
<%			}
%>				
<%			
%>
           <th style="background-color: #342997;"><%=messages.getText("report.generalresult.total") %></th>
        </tr>
<% 		Iterator itDivisions=groupResults.getDivisions().iterator();
			while (itDivisions.hasNext()){
				String division=(String)itDivisions.next();
%>  	<tr>
            <td style="color:#444496;font-weight: bold;"><%=division%></td>
<% 				itCountries= groupResults.getCountries().iterator();
				while (itCountries.hasNext()){
					Integer country= (Integer)itCountries.next();
%>			<td ><%=groupResults.getParticipants(division, country) %></td>
<%				}
%>			 <td ><%=groupResults.getTotalByDivision(division) %></td>
         </tr>
<%			}
%>
 	<tr>
            <td style="color:#444496;font-weight: bold;"><%=messages.getText("assesment.report.totaldivision") %></td>
<% 				itCountries= groupResults.getCountries().iterator();
				while (itCountries.hasNext()){
					Integer country= (Integer)itCountries.next();
%>			<td ><%=groupResults.getTotalByCountry(country) %></td>
<%				}
%>			 <td ><%=groupResults.getTotal() %></td>
         </tr>
    </table>
    <!--ACTIVIDADES POR PAIS-->

    <table id="report" style="float:right;" class="table" >
        <thead>
            <th colspan="7" style="display: none;"></th>
            
        </thead>
<%      itCountries=groupResults.getCountries().iterator();
		while (itCountries.hasNext()){
			Integer country=(Integer)itCountries.next();
%>		   <tr>
            <td style="background-color: #342997; color:#f4f4f5;font-weight: bold;"><%=countries.find(String.valueOf(country)) %></td>
            <td><%=messages.getText("assesment.report.division") %></td>
            <!-- for con las actividades del grupo -->
<%			Iterator it=groupResults.getAssesments().iterator();
				while(it.hasNext()){
					AssesmentAttributes as= (AssesmentAttributes)it.next();
					if(as.getShowReport()){
						cont++;
%>					<td><%=messages.getText(as.getName())%></td>
<%					}
				}
%>
 			 <!--termina for -->
            <td><div class="arrow"></div></td>
        </tr>	

				
        <tr>
            <td colspan='<%=cont%>'>
                <div >
<%			    itDivisions=groupResults.getDivisions().iterator();
		  		while(itDivisions.hasNext()){
		  			String division= (String)itDivisions.next();
		  			boolean odd=false;
		  			String row=(odd)?"row":"row odd";
		  			odd=!odd;
%>		  		<div class='<%=row%>'>
                    <div class=col-1></div>

                       <div class=col-2 style="color:#444496;font-weight: bold;"><%=division %></div>
<%						Iterator itAssesment=groupResults.getAssesments().iterator();
						while(itAssesment.hasNext()){
							AssesmentAttributes as=(AssesmentAttributes)itAssesment.next();
							if(as.getShowReport()){
 %>                     <div class=col-3>
	                        <span style="color:#3dad39;font-weight: bold;"><%=groupResults.getResultsByDivision(country, division, as.getId())[0] %></span> |
	                        <span style="color:#d31111;font-weight: bold;"><%=groupResults.getResultsByDivision(country, division,  as.getId())[1] %></span>                       
                     	</div>							
<%						
							}}
%>					
      			 </div> 
<%		  }		
%>               
 
                </div> 
            </td>
        </tr>
<% 		}
%>  
  
    </table>
<div style="clear:both;">  </div>
<div style="width: 100%; font-weight: bold;text-align: center; padding-top: 30px; font-size: 1.5vw;">
    <%=messages.getText("assesment.report.activeusers") %>
    <br>
</div>
<table id="charts">
<%	
	int assesments=cont-3;
	Iterator as=groupResults.getAssesments().iterator();
	int td=1;
	int chartId=1;
	ArrayList<Integer[]> res=new ArrayList<Integer[]>();
	while (as.hasNext()){
		AssesmentAttributes assesment=(AssesmentAttributes)as.next();
		if(assesment.getShowReport()){
		Integer[] chartValues=groupResults.getChartValues(sys.getUserReportFacade(), assesment.getId(), userSessionData);
		res.add(chartValues);
		if(td==1){
%>			<tr>
<%		}
		if(td<5){
			String id="chart"+chartId;
%>				<td colspan="5"><canvas id='<%=id%>'></canvas></td>
<%		}if(td==4 || !as.hasNext()){
%>			</tr>
			<tr>
<%			for(int i=1; i<=td; i++){
%>				<td colspan="5" style="font-weight: bolder; color:black;text-align: center;"><%=messages.getText("generic.results") %></td>
<% 			}
%>			</tr>
 			<tr>
<%			for(int i=1; i<=td; i++){
%>				<td colspan="2"  style="font-weight: bolder; color:black;text-align: center; padding: 3px;"><%=messages.getText("assesment.report.notapprouved") %></td>
				<td  colspan="2" style="font-weight: bolder; color:black;text-align: center; padding-top: 3px;  padding-bottom: 3px;"><%=messages.getText("result.approuved") %></td>
			    <td  colspan="1" style="font-weight: bolder; color:black;text-align: center; padding-top: 3px;  padding-bottom: 3px;"><%=messages.getText("generic.report.pending") %></td>
				
<% 			}
%>			</tr>  			 
 			<tr>
<%			
			for(int i=1; i<=td; i++){
				int div=res.get(i-1)[0]+res.get(i-1)[1]+res.get(i-1)[2];

%>				<td style="background-color:#ffffff;"></td> 
				<td style="background-color:#f03232;font-weight: bolder;color:white; text-align: center;"><%= (res.get(i-1)[1]*100/div)%>%</td> 
				<td colspan="2" style="background-color:#29c05b;font-weight: bolder;color:white; text-align: center;"><%= (res.get(i-1)[0]*100/div)%>%</td>
				<td  style="background-color:#808080;font-weight: bolder;color:white; text-align: center;"><%= res.get(i-1)[2]*100/div%>%</td>
				
				
<% 			}
%>			</tr> 				        
<%			td=0;

		}
		chartId++;
		td++;
		}
		
	}
%>




</table>
<div style="clear:both;">  </div>
<div style="width: 100%; font-weight: bold;text-align: center; padding-top: 5%; font-size: 1.5vw;">
   <%=messages.getText("assesment.report.activitydashboard") %>
    <br>
</div>

<table class="table3" id="myTable2">
    <tr>
        <th><%=messages.getText("report.users.name") %></th>
        <th><%=messages.getText("user.data.mail") %></th>
        <th><%=messages.getText("user.data.country") %></th>
        <th><%=messages.getText("assesment.report.division") %></th>
<%		 as=groupResults.getAssesments().iterator();
		while (as.hasNext()){
			AssesmentAttributes assesment=(AssesmentAttributes)as.next();	
			if(assesment.getShowReport()){
%>
        	<th><%=messages.getText(assesment.getName()) %></th>
<%		}}
%>
    </tr>
    <tr>
        <th></th>
        <th></th>
        <th></th>
        <th style="font-weight: 500; padding:5px"><div style="display:flex;align-items: center;"><a href="javascript:sortTable(3);"><img src="images/abbott_filter.png" alt="filter"></a><span class="thText"><%=messages.getText("assesment.report.sort") %></span></div></th>
<%  	int columna=4;
		as=groupResults.getAssesments().iterator();
		while (as.hasNext()){
			AssesmentAttributes assesment=(AssesmentAttributes)as.next();	
			if(assesment.getShowReport()){
%>
        <th style="font-weight: 500; padding:5px"><div style="display:flex;align-items: center;"><a href="javascript:sortTable(<%=columna%>);"><img src="images/abbott_filter.png" alt="filter"></a><span class="thText"><%=messages.getText("assesment.report.sort") %></span></div></th>
<%			columna++;}
		}
%>
    </tr>
<%		Iterator users=groupResults.getUsers().iterator();
		while (users.hasNext()){
			UserData user=(UserData)users.next();
			as=groupResults.getAssesments().iterator();
%>		<tr>
	        <td><%=user.getFirstName()+" "+user.getLastName() %> </td>
	        <td style="word-wrap: break-word;"><%=(user.getEmail()==null)?"--":user.getEmail() %></td>
	        <td style="font-weight: 600;"><%=countries.find(String.valueOf(user.getCountry())) %></td>
	        <td style="font-weight: 600;"><%=user.getExtraData2() %></td>
<%  	while (as.hasNext()){
			AssesmentAttributes assesment=(AssesmentAttributes)as.next();
			if(assesment.getShowReport()){
			String[] results=groupResults.getAssesmentResult(sys.getUserReportFacade(), assesment.getId(), user.getLoginName(), sys.getUserSessionData());
%>
 			<td style='<%=results[0]%>'>
<%			if(results[1].equals("")){
%>				--
<% 			} 
%>
<%			if(results[1].equals("result.approuved")){
%>				<div style="display:flex;align-items: center;"><span class="thText"><%=messages.getText("result.approuved") %></span><a href="" style="width:100%;padding-left: 10px; padding-right: 0;"></a><a href="javascript:openReport('<%=user.getLoginName() %>', '<%=String.valueOf(assesment.getId())%>', 2)"><img src="images/abbott_star.png" alt="filter"></a><a href="javascript:openReport('<%=user.getLoginName() %>', '<%=String.valueOf(assesment.getId())%>', 1)"><img src="images/abbott_file.png" alt="filter"></a></div>
<% 			} 
%>
<%			if(results[1].equals("assesment.report.notapprouved")){
%>				<%=messages.getText("assesment.report.notapprouved") %>
<% 			} 
%>
<%			if(results[1].equals("generic.report.pending")){
%>				<%=messages.getText("generic.report.pending") %>
<% 			} 
%>
 			</td>
<%		}}
%>
	    </tr>
<%		}
%>
    
</table>
<div>
    <a href="javascript:document.forms['DownloadGroupReportForm'].submit()" class="button"><%=messages.getText("generic.data.downloadxls") %></a>
</div>
    
<script src="chart.js"></script>
<script>
	function sortTable(n) {
    	  var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
    	  table = document.getElementById("myTable2");
    	  switching = true;
    	  dir = "asc";
    	  while (switching) {
    	    switching = false;
    	    rows = table.rows;
    	   
    	    for (i = 2; i < (rows.length - 1); i++) {
    	      shouldSwitch = false;
    	    
    	      x = rows[i].getElementsByTagName("TD")[n];
    	      y = rows[i + 1].getElementsByTagName("TD")[n];
    	   
    	      if (dir == "asc") {
    	        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
    	          shouldSwitch = true;
    	          break;
    	        }
    	      } else if (dir == "desc") {
    	        if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
    	          shouldSwitch = true;
    	          break;
    	        }
    	      }
    	    }
    	    if (shouldSwitch) {
    	   
    	      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
    	      switching = true;
    	      switchcount ++;
    	    } else {
    	    
    	      if (switchcount == 0 && dir == "asc") {
    	        dir = "desc";
    	        switching = true;
    	      }
    	    }
    	  }
    	}
	function openReport(login, assesment, type) {
		var form = document.forms['DownloadResultReportForm'];
		form.login.value = login;
		form.assessment.value = assesment;
		form.reportType.value = type;
		form.submit();
  	}
<%
	as=groupResults.getAssesments().iterator();
	chartId=1;
	String ctx="ctx";
	while(as.hasNext()){
		AssesmentAttributes assesment=(AssesmentAttributes)as.next();
		if(assesment.getShowReport()){

		Integer[] chartValues=groupResults.getChartValues(sys.getUserReportFacade(), assesment.getId(), userSessionData);
		String id="chart"+chartId;
%>      var <%=ctx+chartId%> = document.getElementById('<%="chart"+chartId%>').getContext('2d');
		var chart<%=chartId%> = new Chart(<%=ctx+chartId%>, {
		    type: 'doughnut',
		    data:{
			datasets: [{
		        data: [<%=chartValues[0]%>,<%=chartValues[1]%>,<%=chartValues[2]%>],
		
				backgroundColor: ['green', 'red', 'grey'],
				label: ''}],
				labels: ['Aprobado','No aprobado', 'Pendiente']},
		    options: {cutoutPercentage: 80, layout: {
		            padding: {
	                left: 10,
	                right: 10,
	                top: 0,
	                bottom: 10
		            }},tooltip:{enabled:false},
		             legend: { 
                         display: false,
                         position: 'bottom',
                         align: 'left',
                         labels: {
                         fontSize: 10,
		                                } }
		                ,title: { 
                                display: true,
                                position: 'top',
                                align: 'center',
                                color:'black',
                                text:'<%= messages.getText(assesment.getName())%>',
                                fontSize: 20
		                                 }}
		});

<%	chartId++;}
		}
	
%>

</script>
    </body>

</html>