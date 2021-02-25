<%@page language="java"
	import="assesment.business.*"
	import="assesment.business.administration.user.*"
	import="assesment.communication.assesment.*"
	import="assesment.communication.language.*"
	import="assesment.communication.security.*"
	import="assesment.communication.administration.user.*"
	import="assesment.communication.module.*"
	import="java.util.*"
%>
<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>

<%
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess"); 
 	UserSessionData userSession=sys.getUserSessionData(); 
 	Text messages=sys.getText();
 	
 	if(userSession.getFilter().getAssesment() == null) {
 	    response.sendRedirect("noassesment.jsp");
 	}else {
	 	AssesmentData assesment = sys.getAssesmentReportFacade().findAssesment(userSession.getFilter().getAssesment(),userSession);
 		Collection userModule = sys.getUserReportFacade().getUserModules(userSession.getFilter().getLoginName(),userSession.getFilter().getAssesment(),userSession,assesment.isInstantFeedback());

	    boolean done1 = sys.getUserReportFacade().isAssessmentDone(userSession.getFilter().getLoginName(),assesment.getId(),userSession,assesment.isPsitest());
		if(userModule.size() == 0) {
	 	    response.sendRedirect("answerMoviles.jsp?module=522");
		}else {
%>


<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<link rel="STYLESHEET" type="text/css" href="./css/menu.css"> 
		<script type="text/javascript" src="./menu/js/dropdown.js"></script>
		<script language="JavaScript" src="./menu/js/refactor.js" type="text/javascript"></script>
 		<title>Assesment</title>
 		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<META http-equiv="Cache-Control" content="no-cache">
		<META http-equiv="Pragma" content="no-cache">
		<style type="text/css">
		<!--
			body {
				margin-left: 0px;
				margin-top: 0px;
				margin-right: 0px;
				margin-bottom: 0px;
			}
			.contain {
				width: 100%; 
				height: 100%;
			}
			.style14 {font-family: Arial, Helvetica, sans-serif; font-weight: bold; font-size: 12px; }
			a.style14, a.style14, a.style14, a.style14 {
				font-family: Arial, Helvetica, sans-serif; 
				font-weight: bold; 
				font-size: 12px;
				color: #000000;
				
			}
			.style17 {
			   font-family: Arial, Helvetica, sans-serif;
			   font-size: 12;
			   font-weight: bold;
			}
			.style25 {font-size: 12px; font-family: Arial, Helvetica, sans-serif;}
			.style26 {font-family: Arial, Helvetica, sans-serif}
			.style27 {color: #FFFFFF; font-weight: bold; font-family: Arial, Helvetica, sans-serif; font-size: 14px; }
			.style28 {
			   font-family: Arial, Helvetica, sans-serif;
			   font-size: 16px;
			   font-weight: bold;
			}
			.style29 {
			   font-size: 14px;
			   font-family: Arial, Helvetica, sans-serif;
			   font-weight: bold;
			}
			.style31 {font-size: 13px; font-family: Arial, Helvetica, sans-serif; color: #000000; }
			.style34 {font-size: 14px}
			.style35 {font-weight: bold; font-family: Arial, Helvetica, sans-serif;}
			.style36 {
			   font-size: 11px;
			   font-family: Arial, Helvetica, sans-serif;
			   font-weight: bold;
			}
			body {
			   background-image: url(fondo.gif);
			   background-repeat: repeat;
			}
			.style37 {color: #F7F7F7}
			.style38 {font-size: 18px}
			.style39 {font-family: "Times New Roman", Times, serif}
			.style40 {
				font-family: "Times New Roman", Times, serif;
				font-size: 18px;
			}
			-->
			</style>
	</head>
	<body scroll="auto">
 		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="default">
 	 		<form name="logout" action="./logout.jsp" method="post"></form>
			<tr id="top">
    			<td height="40" colspan="3" valign="top" >
			  	</td>
			</tr>
			<tr>
    			<td valign="middle" align="center" width="20%">&nbsp;</td>
		        <td height="60%" align="center" valign="bottom" bgcolor="#FFFFFF">
		        	<table width="100%">
		        		<tr>
		        			<td align="left" valign="bottom">
					        	<p class="style28">
					        		<span class="style38">Demo Dispositivo m&oacute;vil</span>
					        	</p>
					        </td>
							<td align="right">
			        			<img src="./imgs/logocepa.jpg" valign="bottom">
			        		</td>
			        	</tr>
			        </table>
			  	</td>
    			<td valign="middle" align="center" width="20%">&nbsp;</td>
			</tr>
	      	<tr>
				<jsp:include  page="/component/spaceline.jsp" />
	  		</tr>
	  		<tr>
				<jsp:include  page="/component/spaceline.jsp" />
	  		</tr>
  			<tr>
    			<td valign="middle" align="center" width="20%">&nbsp;</td>
    			<td valign="middle" align="center" width="60%" >
    				<table width="100%" border="1" cellspacing="0" cellpadding="0">
						<tr>
        					<td height="40" colspan="2" align="left" bgcolor="#004C38">
        						<p class="style27">&nbsp;&nbsp;<%=messages.getText("assesment.modules")%></p>
        					</td>
      					</tr>
<%	Iterator it = assesment.getModuleIterator();
	while(it.hasNext()) {
	    ModuleData module = (ModuleData)it.next();
	    boolean done = false;
	    String text = "Pendiente";
	    Iterator itM = userModule.iterator();
	    while(itM.hasNext()) {
	    	int[] data = (int[]) itM.next();
	    	if(data[0] == module.getId().intValue()) {
	    		done = true;
	    		if(data[2] == 0) {
	    			text = "Realizado";
	    		}else {
	    			text = data[1] + " correctas de "+data[2];  
	    		}
	    	}
	    }
	    if(!done) {
%> 						<tr>
       						<td width="75%" height="32" align="left" bgcolor="#FFFFFF">
      							&nbsp;&nbsp;
      							<strong>
    								<a href='<%="./answerMoviles.jsp?module="+String.valueOf(module.getId())%>' class="style14">
										<%=messages.getText(module.getKey())%>
									</a>  					
								</strong>
       						</td>
       						<td width="75%" height="32" align="left" bgcolor="#FFFFFF">
       							<span class="style14">&nbsp;&nbsp;
       								<strong>
       									<%=text%>
									</strong>
       							</span>
       						</td>
						</tr>
<%		}else {
%> 						<tr>
       						<td width="75%" height="32" align="left" bgcolor="#E2E2E2">
       							<span class="style14">&nbsp;&nbsp;
       								<strong>
										<%=messages.getText(module.getKey())%>
									</strong>
       							</span>
       						</td>
       						<td width="75%" height="32" align="left" bgcolor="#E2E2E2">
       							<span class="style14">&nbsp;&nbsp;
       								<strong>
       									<%=text%>
									</strong>
       							</span>
       						</td>
						</tr>
<%		}
%>							
<%	}
	if(assesment.isPsitest()) {
%>						<tr>
							<td align="left" class="menu"> 
<%	    if(!sys.getUserReportFacade().isPsicologicDone(userSession.getFilter().getLoginName(),userSession.getFilter().getAssesment(),userSession)) {
%>	  						<tr>
        						<td width="75%" height="32" align="left" bgcolor="#FFFFFF">
        							&nbsp;&nbsp;
       								<strong>
     										<a href='<%="./answerAZ.jsp?module="+String.valueOf(ModuleData.PSICO)%>' class="style14">
											<%=messages.getText("assesment.module.psicologic")%>
										</a>  					
									</strong>
        						</td>
        						<td width="75%" height="32" align="left" bgcolor="#FFFFFF">
        							<span class="style14">&nbsp;&nbsp;
        								<strong>
        									Pendiente
										</strong>
        							</span>
        						</td>
							</tr>
<%		}else {
%>	  						<tr>
        						<td width="75%" height="32" align="left" bgcolor="#E2E2E2">
        							<span class="style14">&nbsp;&nbsp;
        								<strong>
											<%=messages.getText("assesment.module.psicologic")%>
										</strong>
        							</span>
        						</td>
        						<td width="75%" height="32" align="left" bgcolor="#E2E2E2">
        							<span class="style14">&nbsp;&nbsp;
        								<strong>
        									Realizado
										</strong>
        							</span>
        						</td>
							</tr>
<%		}
	}
%>   				</table>
    			</td>
    			<td valign="middle" align="center" width="20%">&nbsp;</td>
  			</tr>
	      	<tr>
				<jsp:include  page="/component/spaceline.jsp" />
	  		</tr>
<%			if(done1) {
%>	  		<tr>
		        <td height="100%" colspan="3" align="center" valign="bottom" bgcolor="#FFFFFF">
					<span class="style39"><b><%=messages.getText("assesment.report.footermessage1") %></b></span>
		        </td>
			</tr>
	  		<tr>
		        <td height="100%" colspan="3" align="center" valign="bottom" bgcolor="#FFFFFF">
					<span class="style39">A continuaci&oacute;n se enviar&aacute; a su correo el reporte de resultados.</span>
		        </td>
			</tr>
<%			}
%>  		<tr>
	      		<td align="right" colspan="2">
		            <input type="submit" value='<%=messages.getText("generic.messages.logout")%>' onclick="javascript:document.forms['logout'].submit();" />
		        </td>
		    </tr>
		</table>
	</body>
</html>
<%		}
	}
%>