<!doctype html>
<%@page import="assesment.business.assesment.AssesmentReportFacade"%>
<%@page import="assesment.communication.assesment.CategoryData"%>
<%@page import="assesment.communication.assesment.GroupData"%>
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
	AssesmentReportFacade assessmentReport = sys.getAssesmentReportFacade();
	String groupId = "123";
	String role = userSessionData.getRole(); 
	GroupData group = (role.equals(SecurityConstants.ADMINISTRATOR)) ? assessmentReport.findGroup(new Integer(groupId),sys.getUserSessionData()) : assessmentReport.getUserGroup(userSessionData.getFilter().getLoginName(),userSessionData);
	Iterator it=group.getCategories().iterator();
	while(it.hasNext()){
		CategoryData cat= (CategoryData)it.next();
		Iterator it2=cat.getAssesments().iterator();
		while(it2.hasNext()){
			AssesmentAttributes as= (AssesmentAttributes)it2.next();
			System.out.println(as.getName());
			System.out.println("*******");
		}
	}
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
     
        .col-1 {width: 10.66%;
        }
            .col-2 {width: 22.66%; 
                text-align: center;
                  border-right: 1px rgb(218, 215, 215);
                  border-top: 0px;
                  border-left: 0px;
                  border-bottom: 0px;
                 border-style: solid;
                 padding-top: 7px;
                 padding-bottom: 7px;}
			.col-3 {width: 16.66%; border-right: 1px rgb(218, 215, 215);
                  border-top: 0px black;                text-align: center;
                  border-left: 0px black;
                  border-bottom: 0px black;
                 border-style: solid;}
			.col-4 {width: 16.66%;border-right: 1px rgb(218, 215, 215);
                  border-top: 0px black;                text-align: center;
                  border-left: 0px black;
                  border-bottom: 0px black;
                 border-style: solid;}
			.col-5 {width: 16.66%;border-right: 1px rgb(218, 215, 215);
                  border-top: 0px black;                text-align: center;
                  border-left: 0px black;
                  border-bottom: 0px black;
                 border-style: solid;}
			.col-6 {width: 16.66%;border-right: 1px rgb(218, 215, 215);
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
        <span style="float:left;margin-left: 60px;margin-top: 50px;font-weight: bold; font-size: 1.5vw;">Total de Participantes (licencias de uso) Disponibles</span>
        <span style="float:right; margin-right: 130px; margin-top: 50px;font-weight: bold; font-size: 1.5vw;">Detalle de actividades por pa&iacute;s, divisi&oacute;n</span>

    </div>
    <table  style="clear:both;float:left;" class="table">
        <tr>
            <th style="background-color: #ffffff;"></th>
            <th>Argentina</th>
            <th>Brasil</th>
            <th>Chile</th>
            <th>Uruguay</th>
            <th style="background-color: #342997;">Total</th>
        </tr>
        
        <tr>
            <td style="color:#444496;font-weight: bold;">Divisi&oacute;n 1</td>
            <td >15</td>
            <td >25</td>
            <td >9</td>
            <td >10</td>
            <td >59</td>
        </tr>
       
        <tr>
            <td style="color:#444496;font-weight: bold;">Divisi&oacute;n 2</td>
            <td>5</td>
            <td>12</td>
            <td>0</td>
            <td>10</td>
            <td>27</td>
        </tr>
        
        <tr>
            <td style="color:#444496;font-weight: bold;">Divisi&oacute;n 3</td>
            <td>5</td>
            <td>13</td>
            <td>1</td>
            <td>10</td>
            <td>29</td>
        </tr>
        <tr >
            <td style="color:#444496;font-weight: bold;">Todas las divisiones</td>
            <td>25</td>
            <td>50</td>
            <td>10</td>
            <td>30</td>
            <td>115</td>
        </tr>
        
    </table>
    <!--ACTIVIDADES POR PAIS-->

    <table id="report" style="float:right;" class="table" >
        <thead>
            <th colspan="7" style="display: none;"></th>
            
        </thead>
        <tr>
            <td style="background-color: #342997; color:#f4f4f5;font-weight: bold;">Argentina</td>
            <td>Divisi&oacute;n</td>
            <td>Intersecciones</td>
            <td>Velocidad</td>
            <td>Distancia</td>
            <td >ebtw+</td>
            <td><div class="arrow"></div></td>
        </tr>
        <tr>
            <td colspan="7">
                <div >
                   <div class="row">
                    <div class=col-1></div>

                       <div class=col-2 style="color:#444496;font-weight: bold;">Divisi&oacute;n 1</div>

                       <div class=col-3 > <span style="color:#3dad39;font-weight: bold;">15</span></div>

                       <div class=col-4>
                        <span style="color:#3dad39;font-weight: bold;">10</span> |
                        <span style="color:#d31111;font-weight: bold;">5</span>                       
                      
                     </div>

                       <div class=col-5>
                        <span style="color:#3dad39;font-weight: bold;">5</span> |
                        <span style="color:#d31111;font-weight: bold;">10</span>    
                       </div>

                       <div class=col-6>
                        <span style="color:#3dad39;font-weight: bold;">13</span> |
                        <span style="color:#d31111;font-weight: bold;">2</span>    
                       </div>


                   </div>  
                   <div class="row odd">
                    <div class=col-1></div>

                    <div class=col-2 style="color:#444496;font-weight: bold;">Divisi&oacute;n 2 </div>

                    <div class=col-3>                        
                        <span style="color:#3dad39;font-weight: bold;"></span> 
                        <span style="color:#d31111;font-weight: bold;">5</span>  
                    </div>

                    <div class=col-4>
                        <span style="color:#3dad39;font-weight: bold;">4</span> |
                        <span style="color:#d31111;font-weight: bold;">1</span>  
                    </div>

                    <div class=col-5>
                        <span style="color:#3dad39;font-weight: bold;">5</span> 
                        <span style="color:#d31111;font-weight: bold;"></span>  
                    </div>

                    <div class=col-6>
                        <span style="color:#3dad39;font-weight: bold;">5</span> 
                        <span style="color:#d31111;font-weight: bold;"></span>  
                    </div>

                </div>  
                <div class="row">
                    <div class=col-1></div>
                    <div class=col-2 style="color:#444496;font-weight: bold;">Divisi&oacute;n 3</div>

                    <div class=col-3>
                        <span style="color:#3dad39;font-weight: bold;">5</span> 
                        <span style="color:#d31111;font-weight: bold;"></span>  
                    </div>

                    <div class=col-4>
                        <span style="color:#3dad39;font-weight: bold;">4</span> |
                        <span style="color:#d31111;font-weight: bold;">1</span> 
                    </div>

                    <div class=col-5>
                        <span style="color:#3dad39;font-weight: bold;">2</span> |
                        <span style="color:#d31111;font-weight: bold;">3</span> 
                    </div>

                    <div class=col-6>
                        <span style="color:#3dad39;font-weight: bold;">3</span> |
                        <span style="color:#d31111;font-weight: bold;">2</span> 
                    </div>


                </div> 
                </div> 
            </td>
        </tr>
        <tr>
            <td style="background-color: #342997; color:#f4f4f5;font-weight: bold;">Brasil</td>
            <td>Divisi&oacute;n</td>
            <td>Intersecciones</td>
            <td>Velocidad</td>
            <td>Distancia</td>
            <td >ebtw+</td>
            <td><div class="arrow"></div></td>
        </tr>
        <tr>
            <td colspan="7">
                <div >
                   <div class="row">
                    <div class=col-1></div>

                       <div class=col-2 style="color:#444496;font-weight: bold;">Divisi&oacute;n 1</div>

                       <div class=col-3 > <span style="color:#3dad39;font-weight: bold;">15</span></div>

                       <div class=col-4>
                        <span style="color:#3dad39;font-weight: bold;">10</span> |
                        <span style="color:#d31111;font-weight: bold;">5</span>                       
                      
                     </div>

                       <div class=col-5>
                        <span style="color:#3dad39;font-weight: bold;">5</span> |
                        <span style="color:#d31111;font-weight: bold;">10</span>    
                       </div>

                       <div class=col-6>
                        <span style="color:#3dad39;font-weight: bold;">13</span> |
                        <span style="color:#d31111;font-weight: bold;">2</span>    
                       </div>


                   </div>  
                   <div class="row odd">
                    <div class=col-1></div>

                    <div class=col-2 style="color:#444496;font-weight: bold;">Divisi&oacute;n 2 </div>

                    <div class=col-3>                        
                        <span style="color:#3dad39;font-weight: bold;">4</span> | 
                        <span style="color:#d31111;font-weight: bold;">2</span>  
                    </div>

                    <div class=col-4>
                        <span style="color:#3dad39;font-weight: bold;">8</span> |
                        <span style="color:#d31111;font-weight: bold;">2</span>  
                    </div>

                    <div class=col-5>
                        <span style="color:#3dad39;font-weight: bold;">13</span> |
                        <span style="color:#d31111;font-weight: bold;">1</span>  
                    </div>

                    <div class=col-6>
                        <span style="color:#3dad39;font-weight: bold;">5</span> 
                        <span style="color:#d31111;font-weight: bold;"></span>  
                    </div>

                </div>  
                 
                </div> 
            </td>
        </tr>
        <tr>
            <td style="background-color: #342997; color:#f4f4f5;font-weight: bold;">Chile</td>
            <td>Divisi&oacute;n</td>
            <td>Intersecciones</td>
            <td>Velocidad</td>
            <td>Distancia</td>
            <td >ebtw+</td>
            <td><div class="arrow"></div></td>
        </tr>
        <tr>
            <td colspan="7">
                <div >
                   <div class="row">
                    <div class=col-1></div>

                       <div class=col-2 style="color:#444496;font-weight: bold;">Divisi&oacute;n 1</div>

                       <div class=col-3 > <span style="color:#3dad39;font-weight: bold;">15</span></div>

                       <div class=col-4>
                        <span style="color:#3dad39;font-weight: bold;">10</span> |
                        <span style="color:#d31111;font-weight: bold;">5</span>                       
                      
                     </div>

                       <div class=col-5>
                        <span style="color:#3dad39;font-weight: bold;">5</span> |
                        <span style="color:#d31111;font-weight: bold;">10</span>    
                       </div>

                       <div class=col-6>
                        <span style="color:#3dad39;font-weight: bold;">13</span> |
                        <span style="color:#d31111;font-weight: bold;">2</span>    
                       </div>


                   </div>  
                   <div class="row odd">
                    <div class=col-1></div>

                    <div class=col-2 style="color:#444496;font-weight: bold;">Divisi&oacute;n 2 </div>

                    <div class=col-3>                        
                        <span style="color:#3dad39;font-weight: bold;"></span> 
                        <span style="color:#d31111;font-weight: bold;">5</span>  
                    </div>

                    <div class=col-4>
                        <span style="color:#3dad39;font-weight: bold;">4</span> |
                        <span style="color:#d31111;font-weight: bold;">1</span>  
                    </div>

                    <div class=col-5>
                        <span style="color:#3dad39;font-weight: bold;">5</span> 
                        <span style="color:#d31111;font-weight: bold;"></span>  
                    </div>

                    <div class=col-6>
                        <span style="color:#3dad39;font-weight: bold;">5</span> 
                        <span style="color:#d31111;font-weight: bold;"></span>  
                    </div>

                </div>  
                <div class="row">
                    <div class=col-1></div>
                    <div class=col-2 style="color:#444496;font-weight: bold;">Divisi&oacute;n 3</div>

                    <div class=col-3>
                        <span style="color:#3dad39;font-weight: bold;">5</span> 
                        <span style="color:#d31111;font-weight: bold;"></span>  
                    </div>

                    <div class=col-4>
                        <span style="color:#3dad39;font-weight: bold;">4</span> |
                        <span style="color:#d31111;font-weight: bold;">1</span> 
                    </div>

                    <div class=col-5>
                        <span style="color:#3dad39;font-weight: bold;">2</span> |
                        <span style="color:#d31111;font-weight: bold;">3</span> 
                    </div>

                    <div class=col-6>
                        <span style="color:#3dad39;font-weight: bold;">3</span> |
                        <span style="color:#d31111;font-weight: bold;">2</span> 
                    </div>


                </div> 
                <div class="row">
                    <div class=col-1></div>
                    <div class=col-2 style="color:#444496;font-weight: bold;">Divisi&oacute;n 4</div>

                    <div class=col-3>
                        <span style="color:#3dad39;font-weight: bold;">5</span> 
                        <span style="color:#d31111;font-weight: bold;"></span>  
                    </div>

                    <div class=col-4>
                        <span style="color:#3dad39;font-weight: bold;">4</span> |
                        <span style="color:#d31111;font-weight: bold;">1</span> 
                    </div>

                    <div class=col-5>
                        <span style="color:#3dad39;font-weight: bold;">2</span> |
                        <span style="color:#d31111;font-weight: bold;">3</span> 
                    </div>

                    <div class=col-6>
                        <span style="color:#3dad39;font-weight: bold;">3</span> |
                        <span style="color:#d31111;font-weight: bold;">2</span> 
                    </div>


                </div> 
                </div> 
            </td>
        </tr>
        <tr>
            <td style="background-color: #342997; color:#f4f4f5;font-weight: bold;">Uruguay</td>
            <td>Divisi&oacute;n</td>
            <td>Intersecciones</td>
            <td>Velocidad</td>
            <td>Distancia</td>
            <td >ebtw+</td>
            <td><div class="arrow"></div></td>
        </tr>
        <tr>
            <td colspan="7">
                <div>
                    <div class="row">
                        <div class=col-1></div>
                        <div class=col-2 style="color:#444496;font-weight: bold;">Divisi&oacute;n 3</div>
    
                        <div class=col-3>
                            <span style="color:#3dad39;font-weight: bold;">5</span> 
                            <span style="color:#d31111;font-weight: bold;"></span>  
                        </div>
    
                        <div class=col-4>
                            <span style="color:#3dad39;font-weight: bold;">4</span> |
                            <span style="color:#d31111;font-weight: bold;">1</span> 
                        </div>
    
                        <div class=col-5>
                            <span style="color:#3dad39;font-weight: bold;">2</span> |
                            <span style="color:#d31111;font-weight: bold;">3</span> 
                        </div>
    
                        <div class=col-6>
                            <span style="color:#3dad39;font-weight: bold;">3</span> |
                            <span style="color:#d31111;font-weight: bold;">2</span> 
                        </div>
    
    
                    </div>                   
                </div> 
            </td>
        </tr>
    </table>
<div style="clear:both;">  </div>
<div style="width: 100%; font-weight: bold;text-align: center; padding-top: 30px; font-size: 1.5vw;">
    Usuarios Activos - Porcentaje
    <br>
</div>
<table id="charts">
    <tr>
        <td colspan="4"><canvas id="chart1"></canvas></td>
        <td colspan="4"><canvas id="chart2"></canvas></td>
        <td colspan="4"><canvas id="chart3"></canvas></td>
        <td colspan="4"><canvas id="chart4"></canvas></td>
        <td colspan="4"><canvas id="chart5"></canvas></td>
        <td colspan="4"><canvas id="chart6"></canvas></td>
    </tr>
    <tr>
        <td colspan="4" style="font-weight: bolder; color:black;text-align: center;">Resultados</td>
        <td colspan="4" style="font-weight: bolder; color:black;text-align: center;">Resultados</td>
        <td colspan="4" style="font-weight: bolder; color:black;text-align: center;">Resultados</td>
        <td colspan="4" style="font-weight: bolder; color:black;text-align: center;">Resultados</td>
        <td colspan="4" style="font-weight: bolder; color:black;text-align: center;">Resultados</td>
        <td colspan="4" style="font-weight: bolder; color:black;text-align: center;">Resultados</td>


    </tr>
    <tr>
        <td colspan="2"  style="font-weight: bolder; color:black;text-align: center; padding: 3px;">No aprobado</td><td  colspan="2" style="font-weight: bolder; color:black;text-align: center; padding-top: 3px;  padding-bottom: 3px;">Aprobado</td>
        <td colspan="2"  style="font-weight: bolder; color:black;text-align: center; padding: 3px;">No aprobado</td><td  colspan="2" style="font-weight: bolder; color:black;text-align: center; padding-top: 3px;  padding-bottom: 3px;">Aprobado</td>
        <td colspan="2"  style="font-weight: bolder; color:black;text-align: center; padding: 3px;">No aprobado</td><td  colspan="2" style="font-weight: bolder; color:black;text-align: center; padding-top: 3px;  padding-bottom: 3px;">Aprobado</td>
        <td colspan="2"  style="font-weight: bolder; color:black;text-align: center; padding: 3px;">No aprobado</td><td  colspan="2" style="font-weight: bolder; color:black;text-align: center; padding-top: 3px;  padding-bottom: 3px;">Aprobado</td>
        <td colspan="2"  style="font-weight: bolder; color:black;text-align: center; padding: 3px;">No aprobado</td><td  colspan="2" style="font-weight: bolder; color:black;text-align: center; padding-top: 3px;  padding-bottom: 3px;">Aprobado</td>
        <td colspan="2"  style="font-weight: bolder; color:black;text-align: center; padding: 3px;">No aprobado</td><td  colspan="2" style="font-weight: bolder; color:black;text-align: center; padding-top: 3px;  padding-bottom: 3px;">Aprobado</td>

    </tr>
    <tr>
        <td style="background-color:#ffffff;"></td> <td style="background-color:#f03232;font-weight: bolder;color:white; text-align: center;">15%</td> <td style="background-color:#29c05b;font-weight: bolder;color:white; text-align: center;">85%</td><td style="background-color:#ffffff;"></td>
        <td style="background-color:#ffffff;"></td> <td style="background-color:#f03232;font-weight: bolder;color:white; text-align: center;">50%</td> <td style="background-color:#29c05b;font-weight: bolder;color:white; text-align: center;">50%</td><td style="background-color:#ffffff;"></td>
        <td style="background-color:#ffffff;"></td> <td style="background-color:#f03232;font-weight: bolder;color:white; text-align: center;">80%</td> <td style="background-color:#29c05b;font-weight: bolder;color:white; text-align: center;">20%</td><td style="background-color:#ffffff;"></td>
        <td style="background-color:#ffffff;"></td> <td style="background-color:#f03232;font-weight: bolder;color:white; text-align: center;">55%</td> <td style="background-color:#29c05b;font-weight: bolder;color:white; text-align: center;">45%</td><td style="background-color:#ffffff;"></td>
        <td style="background-color:#ffffff;"></td> <td style="background-color:#f03232;font-weight: bolder;color:white; text-align: center;">68%</td> <td style="background-color:#29c05b;font-weight: bolder;color:white; text-align: center;">32%</td><td style="background-color:#ffffff;"></td>
        <td style="background-color:#ffffff;"></td> <td style="background-color:#f03232;font-weight: bolder;color:white; text-align: center;">70%</td> <td style="background-color:#29c05b;font-weight: bolder;color:white; text-align: center;">30%</td><td style="background-color:#ffffff;"></td>


      
    </tr>

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
        <th>Pa&iacute;s</th>
        <th>Divisi&oacute;n</th>
        <th>Intersecciones</th>
        <th>Velocidad</th>
        <th>eBtw+</th>
    </tr>
    <tr>
        <th></th>
        <th></th>
        <th></th>
        <th style="font-weight: 500; padding:5px"><div style="display:flex;align-items: center;"><a href="" ><img src="images/abbott_filter.png" alt="filter"></a><span class="thText">Ordenar</span></div></th>
        <th style="font-weight: 500; padding:5px"><div style="display:flex;align-items: center;"><a href="" ><img src="images/abbott_filter.png" alt="filter"></a><span class="thText">Ordenar</span></div></th>
        <th style="font-weight: 500; padding:5px"><div style="display:flex;align-items: center;"><a href="" ><img src="images/abbott_filter.png" alt="filter"></a><span class="thText">Ordenar</span></div></th>
        <th style="font-weight: 500; padding:5px"><div style="display:flex;align-items: center;"><a href="" ><img src="images/abbott_filter.png" alt="filter"></a><span class="thText">Ordenar</span></div></th>

    </tr>
    <tr>
        <td>Carla </td>
        <td>carla@demo.com</td>
        <td style="font-weight: 600;">Argentina</td>
        <td style="font-weight: 600;">Divisi&oacute;n 1</td>
        <td>Pendiente</td>
        <td style="background-color:#29c05b;color:white"><div style="display:flex;align-items: center;"><span class="thText">Aprobado</span><a href="" style="width:100%;padding-left: 10px; padding-right: 0;"></a><a href="images/abbott_pdf2.pdf"><img src="images/abbott_star.png" alt="filter"></a><a href="images/abbott_pdf.pdf"><img src="images/abbott_file.png" alt="filter"></a></div></td>
        <td>Pendiente</td>
    </tr>
    <tr>
        <td>Carolina </td>
        <td>carolina@demo.com</td>
        <td style="font-weight: 600;">Brasil</td>
        <td style="font-weight: 600;">Divisi&oacute;n 2</td>
        <td style="background-color:#29c05b;color:white"><div style="display:flex;align-items: center;"><span class="thText">Aprobado</span><a href="" style="width:100%;padding-left: 10px; padding-right: 0;"></a><a href="images/abbott_pdf2.pdf"><img src="images/abbott_star.png" alt="filter"></a><a href="images/abbott_pdf.pdf"><img src="images/abbott_file.png" alt="filter"></a></div></td>
        <td style="background-color:#f03232;color:white">No aprobado</td>
        <td style="background-color:#29c05b;color:white"><div style="display:flex;align-items: center;"><span class="thText">Aprobado</span><a href="" style="width:100%;padding-left: 10px; padding-right: 0;"></a><a href="images/abbott_pdf2.pdf"><img src="images/abbott_star.png" alt="filter"></a><a href="images/abbott_pdf.pdf"><img src="images/abbott_file.png" alt="filter"></a></div></td>
    </tr>
    <tr>
        <td>Federico </td>
        <td>federico@demo.com</td>
        <td style="font-weight: 600;">Uruguay</td>
        <td style="font-weight: 600;">Divisi&oacute;n 2</td>
        <td style="background-color:#29c05b;color:white"><div style="display:flex;align-items: center;"><span class="thText">Aprobado</span><a href="" style="width:100%;padding-left: 10px; padding-right: 0;"></a><a href="images/abbott_pdf2.pdf"><img src="images/abbott_star.png" alt="filter"></a><a href="images/abbott_pdf.pdf"><img src="images/abbott_file.png" alt="filter"></a></div></td>
        <td style="background-color:#f03232;color:white">No aprobado</td>
        <td style="background-color:#29c05b;color:white"><div style="display:flex;align-items: center;"><span class="thText">Aprobado</span><a href="" style="width:100%;padding-left: 10px; padding-right: 0;"></a><a href="images/abbott_pdf2.pdf"><img src="images/abbott_star.png" alt="filter"></a><a href="images/abbott_pdf.pdf"><img src="images/abbott_file.png" alt="filter"></a></div></td>
    </tr>
    <tr>
        <td>Fernando </td>
        <td>fernando@demo.com</td>
        <td style="font-weight: 600;">Argentina</td>
        <td style="font-weight: 600;">Divisi&oacute;n 1</td>
        <td style="background-color:#29c05b;color:white"><div style="display:flex;align-items: center;"><span class="thText">Aprobado</span><a href="" style="width:100%;padding-left: 10px; padding-right: 0;"></a><a href="images/abbott_pdf2.pdf"><img src="images/abbott_star.png" alt="filter"></a><a href="images/abbott_pdf.pdf"><img src="images/abbott_file.png" alt="filter"></a></div></td>
        <td style="background-color:#f03232;color:white">No aprobado</td>
        <td style="background-color:#29c05b;color:white"><div style="display:flex;align-items: center;"><span class="thText">Aprobado</span><a href="" style="width:100%;padding-left: 10px; padding-right: 0;"></a><a href="images/abbott_pdf2.pdf"><img src="images/abbott_star.png" alt="filter"></a><a href="images/abbott_pdf.pdf"><img src="images/abbott_file.png" alt="filter"></a></div></td>
    </tr>
    <tr>
        <td>Francisco </td>
        <td>francisco@demo.com</td>
        <td style="font-weight: 600;">Brasil</td>
        <td style="font-weight: 600;">Divisi&oacute;n 3</td>
        <td style="background-color:#29c05b;color:white"><div style="display:flex;align-items: center;"><span class="thText">Aprobado</span><a href="" style="width:100%;padding-left: 10px; padding-right: 0;"></a><a href="images/abbott_pdf2.pdf"><img src="images/abbott_star.png" alt="filter"></a><a href="images/abbott_pdf.pdf"><img src="images/abbott_file.png" alt="filter"></a></div></td>
        <td style="background-color:#29c05b;color:white"><div style="display:flex;align-items: center;"><span class="thText">Aprobado</span><a href="" style="width:100%;padding-left: 10px; padding-right: 0;"></a><a href="images/abbott_pdf2.pdf"><img src="images/abbott_star.png" alt="filter"></a><a href="images/abbott_pdf.pdf"><img src="images/abbott_file.png" alt="filter"></a></div></td>
        <td style="background-color:#29c05b;color:white"><div style="display:flex;align-items: center;"><span class="thText">Aprobado</span><a href="" style="width:100%;padding-left: 10px; padding-right: 0;"></a><a href="images/abbott_pdf2.pdf"><img src="images/abbott_star.png" alt="filter"></a><a href="images/abbott_pdf.pdf"><img src="images/abbott_file.png" alt="filter"></a></div></td>

    </tr>
    <tr>
        <td>Juan </td>
        <td>juan@demo.com</td>
        <td style="font-weight: 600;">Chile</td>
        <td style="font-weight: 600;">Divisi&oacute;n 1</td>
        <td style="background-color:#29c05b;color:white"><div style="display:flex;align-items: center;"><span class="thText">Aprobado</span><a href="" style="width:100%;padding-left: 10px; padding-right: 0;"></a><a href="images/abbott_pdf2.pdf"><img src="images/abbott_star.png" alt="filter"></a><a href="images/abbott_pdf.pdf"><img src="images/abbott_file.png" alt="filter"></a></div></td>
        <td>Pendiente</td>
        <td style="background-color:#f03232;color:white">No aprobado</td>
    </tr>
    <tr>
        <td>Luc&iacute;a </td>
        <td>lucia@demo.com</td>
        <td style="font-weight: 600;">Chile</td>
        <td style="font-weight: 600;">Divisi&oacute;n 1</td>
        <td style="background-color:#29c05b;color:white"><div style="display:flex;align-items: center;"><span class="thText">Aprobado</span><a href="" style="width:100%;padding-left: 10px; padding-right: 0;"></a><a href="images/abbott_pdf2.pdf"><img src="images/abbott_star.png" alt="filter"></a><a href="images/abbott_pdf.pdf"><img src="images/abbott_file.png" alt="filter"></a></div></td>
        <td>Pendiente</td>
        <td style="background-color:#f03232;color:white">No aprobado</td>
    </tr>
</table>
<div>
    <a href="images/abbott_xls.xls" class="button">Descargar .xls</a>
</div>
    
<script src="chart.js"></script>
<script>
var ctx = document.getElementById('chart1').getContext('2d');
var chart = new Chart(ctx, {
    type: 'doughnut',
    data:{
	datasets: [{
        data: [85,15],
		backgroundColor: ['green', 'red'],
		label: ''}],
		labels: ['Finalizado','No finalizado']},
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
                                text:"Programa Avance",
                                fontSize: 20
                                 }}
});
var ctx2 = document.getElementById('chart2').getContext('2d');
var chart2 = new Chart(ctx2, {
    type: 'doughnut',
    data:{
	datasets: [{
        data: [50,50],
		backgroundColor: ['green', 'red'],
		label: ''}],
		labels: ['Finalizado','No finalizado']},
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
                                    boxWidth:100,
                                    fontSize: 20,
                                } }
                ,title: { 
                                display: true,
                                position: 'top',
                                align: 'center',
                                color:'black',
                                text:"Interacciones",
                                fontSize: 20
                                 }}
});
var ctx3 = document.getElementById('chart3').getContext('2d');
var chart3 = new Chart(ctx3, {
    type: 'doughnut',
    data:{
	datasets: [{
        data: [20,80],
		backgroundColor: ['green', 'red'],
		label: ''}],
		labels: ['Finalizado','No finalizado']},
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
                                    boxWidth:100,
                                    fontSize: 80,
                                } }
                ,title: { 
                                display: true,
                                position: 'top',
                                align: 'center',
                                color:'black',
                                text:"Velocidad",
                                fontSize: 20
                                 }}
});
var ctx4 = document.getElementById('chart4').getContext('2d');
var chart4 = new Chart(ctx4, {
    type: 'doughnut',
    data:{
	datasets: [{
        data: [47,53],
		backgroundColor: ['green', 'red'],
		label: ''}],
		labels: ['Finalizado','No finalizado']},
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
                                    boxWidth:100,
                                    fontSize: 80,
                                } }
                ,title: { 
                                display: true,
                                position: 'top',
                                align: 'center',
                                color:'black',
                                text:"Distancia",
                                fontSize: 20
                                 }}
});
var ctx5 = document.getElementById('chart5').getContext('2d');
var chart5 = new Chart(ctx5, {
    type: 'doughnut',
    data:{
	datasets: [{
        data: [45,55],
		backgroundColor: ['green', 'red'],
		label: ''}],
		labels: ['Finalizado','No finalizado']},
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
                                    boxWidth:100,
                                    fontSize: 80,
                                } }
                ,title: { 
                                display: true,
                                position: 'top',
                                align: 'center',
                                color:'black',
                                text:"eBTW+",
                                fontSize: 20
                                 }}
});
var ctx6 = document.getElementById('chart6').getContext('2d');
var chart6 = new Chart(ctx6, {
    type: 'doughnut',
    data:{
	datasets: [{
        data: [30,70],
		backgroundColor: ['green', 'red'],
		label: ''}],
		labels: ['Finalizado','No finalizado']},
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
                                    boxWidth:100,
                                    fontSize: 80,
                                } }
                ,title: { 
                                display: true,
                                position: 'top',
                                align: 'center',
                                color:'black',
                                text:"Pack",
                                fontSize: 20
                                 }}
});
</script>
    </body>

</html>