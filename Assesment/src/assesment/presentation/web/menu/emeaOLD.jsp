<%@page language="java"
	errorPage="../exception.jsp"
	import="java.util.*"
	import="assesment.business.*"
	import="assesment.communication.language.*"
	import="assesment.communication.security.*"
	import="assesment.communication.language.tables.*"
	import="assesment.communication.administration.user.tables.*"
	import="assesment.communication.question.*"
	import="assesment.communication.module.*"
	import="assesment.presentation.translator.web.util.*"
	import="assesment.presentation.actions.user.*"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html" 
%>

<%@ taglib uri="/WEB-INF/struts-bean.tld"
        prefix="bean" 
%>


<%@page import="assesment.communication.util.CountryConstants"%>
<%@page import="assesment.communication.util.CountryData"%>

<html:html>

<%
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	Text messages = sys.getText();
	
	RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
	dispatcher.include(request,response);

	String check = Util.checkPermission(sys,SecurityConstants.ACCESS_TO_SYSTEM);
	if(check!=null) {
		response.sendRedirect(request.getContextPath()+check);
	}else {
	    ModuleData moduleData = sys.getModuleReportFacade().findModule(new Integer(81),sys.getUserSessionData());
	    
%>

	<head>
		<LINK REL=StyleSheet HREF="util/css/estilo.css" TYPE="text/css">
		<link rel="STYLESHEET" type="text/css" href="./css/menu.css"> 
		<script type="text/javascript" src="./menu/js/dropdown.js"></script>
		<script language="JavaScript" src="./menu/js/refactor.js" type="text/javascript"></script>
 		<title>Assesment</title>
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
		 -->
		</style>
		<script type="text/javascript">
			function loadCities(form) {
				var comboCountry = form.elements['question1492'];
				var value = comboCountry.options[comboCountry.options.selectedIndex].value;
				var comboCity = form.elements['question1555'];
				document.getElementById('othercity').style.visibility = 'hidden';
				if(value == -1) {
					comboCity.options.length = 0;
				}else if(value == 6185) {
					comboCity.options.length = 3;
					comboCity.options[0].value = "-1";
					comboCity.options[0].text = "";
					comboCity.options[1].value = "6178";
					comboCity.options[1].text = '<%=messages.getText("question1555.answer6178.text")%>';
					comboCity.options[2].value = "6227";
					comboCity.options[2].text = '<%=messages.getText("question1555.answer6227.text")%>';					
					comboCity.options.selectedIndex = 0;					
				}else if(value == 6186) {
					comboCity.options.length = 11;
					comboCity.options[0].value = "-1";
					comboCity.options[0].text = "";
					comboCity.options[1].value = "6170";
					comboCity.options[1].text = '<%=messages.getText("question1555.answer6170.text")%>';
					comboCity.options[2].value = "6183";
					comboCity.options[2].text = '<%=messages.getText("question1555.answer6183.text")%>';
					comboCity.options[3].value = "6176";
					comboCity.options[3].text = '<%=messages.getText("question1555.answer6176.text")%>';
					comboCity.options[4].value = "6169";
					comboCity.options[4].text = '<%=messages.getText("question1555.answer6169.text")%>';
					comboCity.options[5].value = "6164";
					comboCity.options[5].text = '<%=messages.getText("question1555.answer6164.text")%>';
					comboCity.options[6].value = "6179";
					comboCity.options[6].text = '<%=messages.getText("question1555.answer6179.text")%>';
					comboCity.options[7].value = "6168";
					comboCity.options[7].text = '<%=messages.getText("question1555.answer6168.text")%>';
					comboCity.options[8].value = "6173";
					comboCity.options[8].text = '<%=messages.getText("question1555.answer6173.text")%>';
					comboCity.options[9].value = "6225";
					comboCity.options[9].text = '<%=messages.getText("question1555.answer6225.text")%>';
					comboCity.options[10].value = "6227";
					comboCity.options[10].text = '<%=messages.getText("question1555.answer6227.text")%>';
					comboCity.options.selectedIndex = 0;					
				}else if(value == 6187) {
					comboCity.options.length = 3;
					comboCity.options[0].value = "-1";
					comboCity.options[0].text = "";
					comboCity.options[1].value = "6172";
					comboCity.options[1].text = '<%=messages.getText("question1555.answer6172.text")%>';
					comboCity.options[2].value = "6227";
					comboCity.options[2].text = '<%=messages.getText("question1555.answer6227.text")%>';					
					comboCity.options.selectedIndex = 0;					
				}else if(value == 6188) {
					comboCity.options.length = 3;
					comboCity.options[0].value = "-1";
					comboCity.options[0].text = "";
					comboCity.options[1].value = "6167";
					comboCity.options[1].text = '<%=messages.getText("question1555.answer6167.text")%>';
					comboCity.options[2].value = "6227";
					comboCity.options[2].text = '<%=messages.getText("question1555.answer6227.text")%>';					
					comboCity.options.selectedIndex = 0;					
				}else if(value == 6189) {
					comboCity.options.length = 4;
					comboCity.options[0].value = "-1";
					comboCity.options[0].text = "";
					comboCity.options[1].value = "6184";
					comboCity.options[1].text = '<%=messages.getText("question1555.answer6184.text")%>';
					comboCity.options[2].value = "6174";
					comboCity.options[2].text = '<%=messages.getText("question1555.answer6174.text")%>';
					comboCity.options[3].value = "6227";
					comboCity.options[3].text = '<%=messages.getText("question1555.answer6227.text")%>';					
					comboCity.options.selectedIndex = 0;					
				}else if(value == 6190) {
					comboCity.options.length = 3;
					comboCity.options[0].value = "-1";
					comboCity.options[0].text = "";
					comboCity.options[1].value = "6165";
					comboCity.options[1].text = '<%=messages.getText("question1555.answer6165.text")%>';
					comboCity.options[2].value = "6227";
					comboCity.options[2].text = '<%=messages.getText("question1555.answer6227.text")%>';					
					comboCity.options.selectedIndex = 0;					
				}else if(value == 6191) {
					comboCity.options.length = 3;
					comboCity.options[0].value = "-1";
					comboCity.options[0].text = "";
					comboCity.options[1].value = "6226";
					comboCity.options[1].text = '<%=messages.getText("question1555.answer6226.text")%>';
					comboCity.options[2].value = "6227";
					comboCity.options[2].text = '<%=messages.getText("question1555.answer6227.text")%>';					
					comboCity.options.selectedIndex = 0;					
				}else if(value == 6192) {
					comboCity.options.length = 3;
					comboCity.options[0].value = "-1";
					comboCity.options[0].text = "";
					comboCity.options[1].value = "6162";
					comboCity.options[1].text = '<%=messages.getText("question1555.answer6162.text")%>';
					comboCity.options[2].value = "6227";
					comboCity.options[2].text = '<%=messages.getText("question1555.answer6227.text")%>';					
					comboCity.options.selectedIndex = 0;					
				}else if(value == 6193) {
					comboCity.options.length = 3;
					comboCity.options[0].value = "-1";
					comboCity.options[0].text = "";
					comboCity.options[1].value = "6177";
					comboCity.options[1].text = '<%=messages.getText("question1555.answer6177.text")%>';
					comboCity.options[2].value = "6227";
					comboCity.options[2].text = '<%=messages.getText("question1555.answer6227.text")%>';					
					comboCity.options.selectedIndex = 0;					
				}else if(value == 6194) {
					comboCity.options.length = 4;
					comboCity.options[0].value = "-1";
					comboCity.options[0].text = "";
					comboCity.options[1].value = "6182";
					comboCity.options[1].text = '<%=messages.getText("question1555.answer6182.text")%>';
					comboCity.options[2].value = "6175";
					comboCity.options[2].text = '<%=messages.getText("question1555.answer6175.text")%>';
					comboCity.options[3].value = "6227";
					comboCity.options[3].text = '<%=messages.getText("question1555.answer6227.text")%>';					
					comboCity.options.selectedIndex = 0;					
				}else if(value == 6195) {
					comboCity.options.length = 4;
					comboCity.options[0].value = "-1";
					comboCity.options[0].text = "";
					comboCity.options[1].value = "6163";
					comboCity.options[1].text = '<%=messages.getText("question1555.answer6163.text")%>';
					comboCity.options[2].value = "6181";
					comboCity.options[2].text = '<%=messages.getText("question1555.answer6181.text")%>';
					comboCity.options[3].value = "6227";
					comboCity.options[3].text = '<%=messages.getText("question1555.answer6227.text")%>';					
					comboCity.options.selectedIndex = 0;					
				}else if(value == 6196) {
					comboCity.options.length = 3;
					comboCity.options[0].value = "-1";
					comboCity.options[0].text = "";
					comboCity.options[1].value = "6166";
					comboCity.options[1].text = '<%=messages.getText("question1555.answer6166.text")%>';
					comboCity.options[2].value = "6227";
					comboCity.options[2].text = '<%=messages.getText("question1555.answer6227.text")%>';					
					comboCity.options.selectedIndex = 0;					
				}else if(value == 6197) {
					comboCity.options.length = 3;
					comboCity.options[0].value = "-1";
					comboCity.options[0].text = "";
					comboCity.options[1].value = "6171";
					comboCity.options[1].text = '<%=messages.getText("question1555.answer6171.text")%>';
					comboCity.options[2].value = "6227";
					comboCity.options[2].text = '<%=messages.getText("question1555.answer6227.text")%>';					
					comboCity.options.selectedIndex = 0;					
				}
			}
			function loadModels(form) {
				var comboTrade = form.elements['question1531'];
				var value = comboTrade.options[comboTrade.options.selectedIndex].value;
				var comboModel = form.elements['question1532'];
				document.getElementById('othermodel').style.visibility = 'hidden';
				if(value == 6208) {
					comboModel.options.length = 7;
					comboModel.options[0].value = "-1";
					comboModel.options[0].text = "";
					comboModel.options[1].value = "6250";
					comboModel.options[1].text = '<%=messages.getText("question1532.answer6250.text")%>';
					comboModel.options[2].value = "6251";
					comboModel.options[2].text = '<%=messages.getText("question1532.answer6251.text")%>';					
					comboModel.options[3].value = "6252";
					comboModel.options[3].text = '<%=messages.getText("question1532.answer6252.text")%>';					
					comboModel.options[4].value = "6253";
					comboModel.options[4].text = '<%=messages.getText("question1532.answer6253.text")%>';					
					comboModel.options[5].value = "6254";
					comboModel.options[5].text = '<%=messages.getText("question1532.answer6254.text")%>';					
					comboModel.options[6].value = "6269";
					comboModel.options[6].text = '<%=messages.getText("question1532.answer6269.text")%>';
					comboModel.options.selectedIndex = 0;
				}else if(value == 6207) {	
					comboModel.options.length = 7;
					comboModel.options[0].value = "-1";
					comboModel.options[0].text = "";
					comboModel.options[1].value = "6255";
					comboModel.options[1].text = '<%=messages.getText("question1532.answer6255.text")%>';
					comboModel.options[2].value = "6256";
					comboModel.options[2].text = '<%=messages.getText("question1532.answer6256.text")%>';					
					comboModel.options[3].value = "6257";
					comboModel.options[3].text = '<%=messages.getText("question1532.answer6257.text")%>';					
					comboModel.options[4].value = "6258";
					comboModel.options[4].text = '<%=messages.getText("question1532.answer6258.text")%>';					
					comboModel.options[5].value = "6259";
					comboModel.options[5].text = '<%=messages.getText("question1532.answer6259.text")%>';					
					comboModel.options[6].value = "6269";
					comboModel.options[6].text = '<%=messages.getText("question1532.answer6269.text")%>';
					comboModel.options.selectedIndex = 0;
				}else if(value == 6212) {	
					comboModel.options.length = 3;
					comboModel.options[0].value = "-1";
					comboModel.options[0].text = "";
					comboModel.options[1].value = "6260";
					comboModel.options[1].text = '<%=messages.getText("question1532.answer6260.text")%>';
					comboModel.options[2].value = "6269";
					comboModel.options[2].text = '<%=messages.getText("question1532.answer6269.text")%>';
				}else if(value == 6221) {	
					comboModel.options.length = 10;
					comboModel.options[0].value = "-1";
					comboModel.options[0].text = "";
					comboModel.options[1].value = "6261";
					comboModel.options[1].text = '<%=messages.getText("question1532.answer6261.text")%>';
					comboModel.options[2].value = "6262";
					comboModel.options[2].text = '<%=messages.getText("question1532.answer6262.text")%>';
					comboModel.options[3].value = "6263";
					comboModel.options[3].text = '<%=messages.getText("question1532.answer6263.text")%>';
					comboModel.options[4].value = "6264";
					comboModel.options[4].text = '<%=messages.getText("question1532.answer6264.text")%>';
					comboModel.options[5].value = "6265";
					comboModel.options[5].text = '<%=messages.getText("question1532.answer6265.text")%>';
					comboModel.options[6].value = "6266";
					comboModel.options[6].text = '<%=messages.getText("question1532.answer6266.text")%>';
					comboModel.options[7].value = "6267";
					comboModel.options[7].text = '<%=messages.getText("question1532.answer6267.text")%>';
					comboModel.options[8].value = "6268";
					comboModel.options[8].text = '<%=messages.getText("question1532.answer6268.text")%>';
					comboModel.options[9].value = "6269";
					comboModel.options[9].text = '<%=messages.getText("question1532.answer6269.text")%>';
				}else {					
					comboModel.options.length = 1;
					comboModel.options[0].value = "6269";
					comboModel.options[0].text = '<%=messages.getText("question1532.answer6269.text")%>';
					document.getElementById('othermodel').style.visibility = 'visible';
				}
			}
			function loadOtherCity(form) {
				var comboCity = form.elements['question1555'];
				var value = comboCity.options[comboCity.options.selectedIndex].value;
				if(value == 6227) {
					document.getElementById('othercity').style.visibility = 'visible';
				}else {
					document.getElementById('othercity').style.visibility = 'hidden';
				}
			}
			function loadCity(form,value) {
				var comboCity = form.elements['question1555'];
				for(i = 0; i < comboCity.options.length; i++) {
					if(comboCity.options[i].value == value) {
						comboCity.options.selectedIndex = i;
					}
				}
				if(value == 6227) {
					document.getElementById('othercity').style.visibility = 'visible';
				}
			}
			function loadOtherModel(form) {
				var comboModel = form.elements['question1532'];
				var value = comboModel.options[comboModel.options.selectedIndex].value;
				if(value == 6269) {
					document.getElementById('othermodel').style.visibility = 'visible';
				}else {
					document.getElementById('othermodel').style.visibility = 'hidden';
				}
			}
		</script>
	</head>
	<body scroll="auto" onload="loadCities(document.forms['EmeaForm']);loadCity(document.forms['EmeaForm'],'<%=String.valueOf(request.getParameter("question1555"))%>')">
 		<table width="100%" height="90" border="0" cellpadding="0" cellspacing="0" class="default">
 	 		<form name="logout" action="./logout.jsp" method="post"></form>
			<tr id="top">
    			<td height="90" valign="top" >
    				<table width="100%" height="90" border="0" cellpadding="0" cellspacing="0">
  						<tr>
				        	<td bgcolor="#000000" width="100%" colspan="3" align="right">
				        		<img src="./imgs/emea.jpg">
				        	</td>
  						</tr>
  						<tr>
						    <td width="4%" class="bgTop"></td>
		    				<td width="35%" align="right" class="bgTop">
		    					<a href="#"   onClick="javascript:document.forms[0].submit()">
		    						<span class="style3" ><%=messages.getText("generic.messages.logout")%></span>
		    					</a>
		    				</td>
		    				<td width="2%" align="right" class="bgTop"><span class="style2" >&nbsp;</span></td>
					  	</tr>
					</table>
			  	</td>
			</tr>
	      	<tr>
				<jsp:include  page="/component/spaceline.jsp" />
	  		</tr>
			<tr id="top">
    			<td valign="top" >
					<html:form action="/Emea">
						<table width="600" border="0" align="center" cellpadding="0" cellspacing="0">
					      	<tr>
								<jsp:include  page="/component/spaceline.jsp" />
					  		</tr>
			  				<tr>
					    		<td valign="top">
									<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<%		Iterator it = moduleData.getQuestionIterator();
		while(it.hasNext()) {
		    QuestionData question = (QuestionData)it.next();
		    String value = (request.getParameter("question"+String.valueOf(question.getId())) == null) ? "" : request.getParameter("question"+String.valueOf(question.getId()));
			if(question.getType().intValue() == QuestionData.TEXT || question.getType().intValue() == QuestionData.EMAIL) {
				if(question.getId().intValue() != 1556 && question.getId().intValue() != 1557) {
					if(question.getId().intValue() == 1552) {
%>											<td width="100%">	
												<jsp:include  page='<%="/component/utilitybox2top.jsp?title="+messages.getText(question.getKey())%>' />
												<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
													<tr>
														<td align="left" class="lineone">
	      													<textarea name='<%="question"+String.valueOf(question.getId())%>' rows="5" cols="200" class="inputBig"><%=value%></textarea>
	      												</td>
	      											</tr>
	      										</table>
												<jsp:include  page="/component/utilitybox2bottom.jsp" />
											</td>					
<%					}else {
%>											<td width="100%">	
												<jsp:include  page="/component/utilitybox4top.jsp" />
												<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
													<tr>
						      							<td class="answerText" align="left">	
															<%=messages.getText(question.getKey())%>
														</td>
														<td align="right" class="lineone">
								      						<input type="text" name='<%="question"+String.valueOf(question.getId())%>' size="200" class="input" value='<%=value%>'>
								      					</td>
								      				</tr>
								      			</table>
												<jsp:include  page="/component/utilitybox4bottom.jsp" />
											</td>								      					
<%					}
				}
			}else if(question.getType().intValue() == QuestionData.COUNTRY) {
%>											<td width="100%">	
												<jsp:include  page="/component/utilitybox4top.jsp" />
												<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
													<tr>
											      		<td class="answerText" align="left">	
															<%=messages.getText(question.getKey()) %>
														</td>
														<td class="lineone" align="right">
															<select name='<%="question"+String.valueOf(question.getId())%>' class="input" >
																<option value="-1" <%=(value.equals("-1")) ? "selected" : "" %>>&nbsp;</option>
<%				Iterator options = new CountryConstants(messages).getCountryIterator();
				while(options.hasNext()) {
					CountryData country = (CountryData)options.next();
%>																<option value='<%=String.valueOf(country.getId())%>' <%=(value.equals(String.valueOf(country.getId()))) ? "selected" : "" %>>
																	<%=country.getName()%>
																</option>
<%				}
%>															</select>
														</td>
							      					</tr>
							      				</table>
												<jsp:include  page="/component/utilitybox4bottom.jsp" />
											</td>														
<%			}else if(question.getType().intValue() == QuestionData.DATE || question.getType().intValue() == QuestionData.OPTIONAL_DATE) {
    			String day = (request.getParameter("questionDay"+String.valueOf(question.getId())) == null) ? "" : request.getParameter("questionDay"+String.valueOf(question.getId()));
			    String month = (request.getParameter("questionMonth"+String.valueOf(question.getId())) == null) ? "" : request.getParameter("questionMonth"+String.valueOf(question.getId()));
			    String year = (request.getParameter("questionYear"+String.valueOf(question.getId())) == null) ? "" : request.getParameter("questionYear"+String.valueOf(question.getId()));
%>											<td width="100%">	
												<jsp:include  page="/component/utilitybox4top.jsp" />
												<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
													<tr>
											      		<td class="answerText" align="left">	
															<%=messages.getText(question.getKey()) %>
														</td>
														<td align="right" width="60%" class="lineone">
															<input type="text" name='<%="questionDay"+String.valueOf(question.getId())%>' style="width:20;" class="input" value='<%=day%>'/>/
			            									<input type="text" name='<%="questionMonth"+String.valueOf(question.getId())%>' style="width:20;" class="input"  value='<%=month%>'/>/
			            									<input type="text" name='<%="questionYear"+String.valueOf(question.getId())%>' style="width:40;" class="input"  value='<%=year%>'/>
			            								</td>
								      				</tr>
								      			</table>
												<jsp:include  page="/component/utilitybox4bottom.jsp" />
											</td>
<%			}else if(question.getType().intValue() == QuestionData.EXCLUDED_OPTIONS) {
%>    										<td width="100%">
												<jsp:include  page='<%="/component/utilitybox2top.jsp?title="+messages.getText(question.getKey())%>' />
												<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<%				Iterator options = question.getAnswerIterator();
				while(options.hasNext()) {
				    AnswerData answer = (AnswerData)options.next();
%>													<tr>
														<td class="lineone">
															<input type="radio" name='<%="question"+String.valueOf(question.getId())%>' value='<%=String.valueOf(answer.getId())%>' <%=value.equals(String.valueOf(answer.getId())) ? "checked" : "" %> />
															<%=messages.getText(answer.getKey()) %>
														</td>
													</tr>
<%				}
%>												</table>
												<jsp:include  page="/component/utilitybox2bottom.jsp" />
											</td>
<%			}else if(question.getType().intValue() == QuestionData.LIST) {
				String change = "";
				switch(question.getId().intValue()){
					case 1492:
						change = "onchange=\"loadCities(document.forms['EmeaForm']);\"" ;
						break;
					case 1555:
						change = "onchange=\"loadOtherCity(document.forms['EmeaForm']);\"" ;
						break;
					case 1531:
						change = "onchange=\"loadModels(document.forms['EmeaForm']);\"" ;
						break;
					case 1532:
						change = "onchange=\"loadOtherModel(document.forms['EmeaForm']);\"" ;
				}
%>											<td width="100%">	
												<jsp:include  page="/component/utilitybox4top.jsp" />
												<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
													<tr>
											      		<td class="answerText" align="left">	
															<%=messages.getText(question.getKey()) %>
														</td>
														<td align="right" class="lineone">
															<select name='<%="question"+String.valueOf(question.getId())%>' class="input" <%=change%> style="width: 150px;">
<%				if(question.getId().intValue() != 1555 && question.getId().intValue() != 1532) {
%>																<option value="-1" <%=(value.equals("-1")) ? "selected" : "" %>>&nbsp;</option>
<%					Iterator options = question.getAnswerIterator();
					while(options.hasNext()) {
					    AnswerData answer = (AnswerData)options.next();
%>																<option value='<%=String.valueOf(answer.getId())%>' <%=(value.equals(String.valueOf(answer.getId()))) ? "selected" : "" %>>
																	<%=messages.getText(answer.getKey()) %>
																</option>
<%					}
				}
%>															</select>
														</td>
							      					</tr>
<%				if(question.getId().intValue() == 1555) {
		    		String valueCity = (request.getParameter("question1556") == null) ? "" : request.getParameter("question1556");
		    		String visibility = (value.equals("6227")) ? "visible" : "hidden";
%>													<tr id="othercity" style='<%="visibility:"+visibility+";" %>'>
														<td class="answerText" colspan="2" align="right">
															<%=messages.getText("generic.message.othercity")+": "%>&nbsp;															
															<input type="text" name="question1556" style="width:150;" class="input" value='<%=(valueCity)%>'/>
														</td>										
							      					</tr>
<%				}		
				if(question.getId().intValue() == 1532) {
		    		String valueTrade = (request.getParameter("question1557") == null) ? "" : request.getParameter("question1557");
		    		String visibility = (value.equals("6269")) ? "visible" : "hidden";
%>													<tr id="othermodel" style='<%="visibility:"+visibility+";" %>'>
														<td class="answerText" colspan="2" align="right">
															<%=messages.getText("generic.message.othermodel")+": "%>&nbsp;															
															<input type="text" name="question1557" style="width:150;" class="input" value='<%=(valueTrade)%>'/>
														</td>										
							      					</tr>
<%				}		
%>							      				</table>
												<jsp:include  page="/component/utilitybox4bottom.jsp" />
											</td>
<%			}else if(question.getType().intValue() == QuestionData.KILOMETERS) {
%>											<td width="100%">	
												<jsp:include  page="/component/utilitybox4top.jsp" />
												<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
													<tr>
											      		<td class="answerText" align="left">	
															<%=messages.getText(question.getKey()) %>
														</td>
														<td align="right" class="lineone">
															<input type="text" name='<%="question"+String.valueOf(question.getId())%>' class="input" value='<%=value%>'/>
															<select name='<%="unit"+String.valueOf(question.getId())%>' class="input">
																<option value="0"><%=messages.getText("answer.units.kilometers") %></option>
																<option value="1"><%=messages.getText("answer.units.miles") %></option>
															</select>
														</td>
							      					</tr>
							      				</table>
												<jsp:include  page="/component/utilitybox4bottom.jsp" />
											</td>
<%			}else if(question.getType().intValue() == QuestionData.INCLUDED_OPTIONS) {
%>    										<td width="100%">
												<jsp:include  page='<%="/component/utilitybox2top.jsp?title="+messages.getText(question.getKey())%>' />
												<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<%				Iterator options = question.getAnswerIterator();
				boolean lot = question.getAnswerSize() > 10;
				while(options.hasNext()) {
					AnswerData answer = (AnswerData)options.next();
				    String valueA = (request.getParameter("answer"+String.valueOf(answer.getId())) == null) ? "" : request.getParameter("answer"+String.valueOf(answer.getId()));
%>													<tr>
														<td class="lineone">
															<input type="checkbox" name='<%="answer"+String.valueOf(answer.getId())%>' <%=valueA.equals("on") ? "checked" : "" %>/>
															<%=messages.getText(answer.getKey()) %>
														</td>
<%					if(lot) {
						if(options.hasNext()) {
							answer = (AnswerData)options.next();
				    		valueA = (request.getParameter("answer"+String.valueOf(answer.getId())) == null) ? "" : request.getParameter("answer"+String.valueOf(answer.getId()));
%>														<td class="lineone">&nbsp;</td>							
														<td class="lineone">
															<input type="checkbox" name='<%="answer"+String.valueOf(answer.getId())%>' <%=valueA.equals("on") ? "checked" : "" %>/>
															<%=messages.getText(answer.getKey()) %>
														</td>
<%						}else {
%>														<td class="lineone" colspan="2">&nbsp;</td>							
<%	
						}
					}
%>														
													</tr>
<%				}
%>												</table>
												<jsp:include  page="/component/utilitybox2bottom.jsp" />
											</td>
<%    		}
%>								      	</tr>
								      	<tr  class="linetwo"><td>&nbsp;</td></tr>
<%		}
%>									
								      	<tr  class="linetwo">
								      		<td align="right">
									            <html:submit styleClass="input">
													<%=messages.getText("generic.messages.save")%>
												</html:submit>
									            <html:cancel styleClass="input">
													<%=messages.getText("generic.messages.cancel")%>
												</html:cancel>
			          						</td>
			      						</tr>
			      					</table>
			    				</td>
  							</tr>
						</table>
					</html:form>
				</td>
			</tr>
		</table>
	</body>
<%	}	%>
</html:html>
