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
	String groupId = "123";
	String role = userSessionData.getRole(); 
	GroupData group = assessmentReport.findGroup(new Integer(groupId),sys.getUserSessionData());
	GroupUsersData groupResults= assessmentReport.getGroupUsersResults(new Integer(groupId),sys.getUserSessionData());
	groupResults.setUsers(sys.getUserReportFacade().findGroupUsers(groupResults.getId(), sys.getUserSessionData()));
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
     
        .col-1 {width: 15%;
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
			.col-3 {width: 15%; border-right: 1px rgb(218, 215, 215);
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
                margin-left: 10px;
                margin-right: 10px;
			
            }  
            .table{
                width:25%;
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
                width:90%;
				margin-bottom:1.5%;
                margin-left: 5%;
                margin-right: 5%;
            }
            canvas{
                clear:both;
                width: 200px;}

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
    <!--TOTAL DE PARTICIPANTES-->
    <div>
        <span style="float:left;margin-left: 60px;margin-top: 50px;font-weight: bold; font-size: 1.5vw;">Total de Participantes <br>(licencias de uso) Disponibles</span>
        <span style="float:right; margin-right: 300px; margin-top: 50px;font-weight: bold; font-size: 1.5vw;">Detalle de actividades<br> por país, división</span>

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
            <td style="color:#444496;font-weight: bold;">Todas las divisiones</td>
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
            <td>División</td>
            <!-- for con las actividades del grupo -->
<%			Iterator it=groupResults.getAssesments().iterator();
				while(it.hasNext()){
					AssesmentAttributes as= (AssesmentAttributes)it.next();
					cont++;
%>					<td><%=as.getId()%></td>
<%			}
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
 %>                     <div class=col-3>
	                        <span style="color:#3dad39;font-weight: bold;"><%=groupResults.getResultsByDivision(country, division, as.getId())[0] %></span> |
	                        <span style="color:#d31111;font-weight: bold;"><%=groupResults.getResultsByDivision(country, division,  as.getId())[1] %></span>                       
                     	</div>							
<%						}
%>						
      			 </div> 
<%		  		}
%>               
 
                </div> 
            </td>
        </tr>
<% 		}
%>  
  
    </table>
<div style="clear:both;">  </div>
<div style="width: 100%; font-weight: bold;text-align: center; padding-top: 30px; font-size: 1.5vw;">
    Usuarios Activos - Porcentaje
    <br>
</div>
<table id="charts">
<%	
	int assesments=cont-3;
	Iterator as=groupResults.getAssesments().iterator();
	int td=1;
	int chartId=1;
	while (as.hasNext()){
		AssesmentAttributes assesment=(AssesmentAttributes)as.next();
		Integer[] chartValues=groupResults.getChartValues(sys.getUserReportFacade(), assesment.getId(), userSessionData);

		if(td==1){
%>			<tr>
<%		}
		if(td<7){
			String id="chart"+chartId;
%>				<td colspan="5"><canvas id='<%=id%>'></canvas></td>
<%		}if(td==6 || chartId==assesments){
%>			</tr>
			<tr>
<%			for(int i=1; i<=td; i++){
%>				<td colspan="5" style="font-weight: bolder; color:black;text-align: center;">Resultados</td>
<% 			}
%>			</tr>
 			<tr>
<%			for(int i=1; i<=td; i++){
%>				<td colspan="2"  style="font-weight: bolder; color:black;text-align: center; padding: 3px;">Reprobado</td>
				<td  colspan="2" style="font-weight: bolder; color:black;text-align: center; padding-top: 3px;  padding-bottom: 3px;">Aprobado</td>
			    <td  colspan="1" style="font-weight: bolder; color:black;text-align: center; padding-top: 3px;  padding-bottom: 3px;">Pendiente</td>
				
<% 			}
%>			</tr>  			 
 			<tr>
<%			for(int i=1; i<=td; i++){
				int div=chartValues[0]+chartValues[1]+chartValues[2];
				
%>				<td style="background-color:#ffffff;"></td> 
				<td style="background-color:#f03232;font-weight: bolder;color:white; text-align: center;"><%= (div>0?(chartValues[1]/div):0)*100%>%</td> 
				<td style="background-color:#29c05b;font-weight: bolder;color:white; text-align: center;"><%= (div>0?(chartValues[0]/div):0)*100%>%</td>
				<td style="background-color:#DCDCDC;font-weight: bolder;color:white; text-align: center;"><%= (div>0?(chartValues[2]/div):0)*100%>%</td>
				<td style="background-color:#ffffff;"></td>
				
<% 			}
%>			</tr> 				        
<%			td=0;

		}
		chartId++;
		td++;
	}
%>




</table>
<div style="clear:both;">  </div>
<div style="width: 100%; font-weight: bold;text-align: center; padding-top: 5%; font-size: 1.5vw;">
    Dashboard de Actividades y Resultados
    <br>
</div>

<table class="table3">
    <tr>
        <th>Nombre</th>
        <th>Email</th>
        <th>País</th>
        <th>División</th>
<%		 as=groupResults.getAssesments().iterator();
		while (as.hasNext()){
			AssesmentAttributes assesment=(AssesmentAttributes)as.next();			
%>
        	<th><%=messages.getText(assesment.getName()) %></th>
<%		}
%>
    </tr>
    <tr>
        <th></th>
        <th></th>
        <th></th>
        <th style="font-weight: 500; padding:5px"><div style="display:flex;align-items: center;"><a href="" ><img src="images/abbott_filter.png" alt="filter"></a><span class="thText">Ordenar</span></div></th>
<%  	as=groupResults.getAssesments().iterator();
		while (as.hasNext()){
			AssesmentAttributes assesment=(AssesmentAttributes)as.next();			
%>
        <th style="font-weight: 500; padding:5px"><div style="display:flex;align-items: center;"><a href="" ><img src="images/abbott_filter.png" alt="filter"></a><span class="thText">Ordenar</span></div></th>
<%		}
%>
    </tr>
<%		Iterator users=groupResults.getUsers().iterator();
		while (users.hasNext()){
			UserData user=(UserData)users.next();
%>		<tr>
	        <td><%=user.getFirstName()+" "+user.getLastName() %> </td>
	        <td><%=(user.getEmail()==null)?"--":user.getEmail() %></td>
	        <td style="font-weight: 600;"><%=countries.find(String.valueOf(user.getCountry())) %></td>
	        <td style="font-weight: 600;"><%=user.getExtraData2() %></td>
<%  	as=groupResults.getAssesments().iterator();
		while (as.hasNext()){
			AssesmentAttributes assesment=(AssesmentAttributes)as.next();
			String[] results=groupResults.getAssesmentResult(sys.getUserReportFacade(), assesment.getId(), user.getLoginName(), sys.getUserSessionData());
%>
 			<td style='<%=results[0]%>'>
<%			if(results[1].equals("")){
%>				--
<% 			} 
%>
<%			if(results[1].equals("Aprobado")){
%>				<div style="display:flex;align-items: center;"><span class="thText"><%=results[1] %></span><a href="" style="width:100%;padding-left: 10px; padding-right: 0;"></a><a href="images/abbott_pdf2.pdf"><img src="images/abbott_star.png" alt="filter"></a><a href="images/abbott_pdf.pdf"><img src="images/abbott_file.png" alt="filter"></a></div>
<% 			} 
%>
<%			if(results[1].equals("No aprobado")){
%>				No aprobado
<% 			} 
%>
<%			if(results[1].equals("generic.report.pending")){
%>				<%=messages.getText("generic.report.pending") %>
<% 			} 
%>
 			</td>
<%		}
%>
	    </tr>
<%		}
%>
    
</table>
<div>
    <a href="images/abbott_xls.xls" class="button">Descargar .xls</a>
</div>
    
<script src="chart.js"></script>
<script>
<%
	as=groupResults.getAssesments().iterator();
	chartId=1;
	String ctx="ctx";
	while(as.hasNext()){
		AssesmentAttributes assesment=(AssesmentAttributes)as.next();
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
<%		chartId++;

	}
%>

</script>
    </body>

</html>