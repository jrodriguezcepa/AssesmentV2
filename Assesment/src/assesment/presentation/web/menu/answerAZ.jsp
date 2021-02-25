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
<%@page import="assesment.communication.util.CountryData"%><html:html>

<%
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	Text messages = sys.getText();
	String check = Util.checkPermission(sys,SecurityConstants.ACCESS_TO_SYSTEM);
	if(check!=null) {
		response.sendRedirect(request.getContextPath()+check);
	}else {
		
		RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
		dispatcher.include(request,response);
		if(session.getAttribute("Msg")!=null){
			session.removeAttribute("Msg");
		}
		
	    String module = request.getParameter("module");
	    ModuleData moduleData = null;
	    if(Integer.parseInt(module) == 0) {
	        moduleData = sys.getModuleReportFacade().getPsicoModule(sys.getUserSessionData());
	    }else {
	    	moduleData = sys.getModuleReportFacade().findModule(new Integer(module),sys.getUserSessionData());
	    }
%>

<html>
	<head>
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
			.style14 {font-family: Arial, Helvetica, sans-serif; font-weight: bold; font-size: 12px; }
			a.style14, a.style14, a.style14, a.style14 {
				font-family: Arial, Helvetica, sans-serif; 
				font-weight: bold; 
				font-size: 12px;
				color: #000000;
				text-decoration:none;
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
			-->
		</style>
	</head>
	<body scroll="auto">
		<html:form action="/Resultado">
			<html:hidden property="module" value='<%=module%>' />
	 		<table width="100%" height="90" border="0" cellpadding="0" cellspacing="0" class="default">
		      	<tr>
					<jsp:include  page="/component/spaceline.jsp" />
		  		</tr>
				<tr>
	    			<td valign="middle" align="center" width="10%">&nbsp;</td>
			        <td height="60%" align="center" valign="bottom" bgcolor="#FFFFFF">
			        	<table width="100%">
			        		<tr>
			        			<td align="left" valign="bottom">
						        	<p class="style28">
						        		<span class="style38"><%=messages.getText(moduleData.getKey())%></span>
						        	</p>
						        </td>
								<td align="right">
				        			<img src="./imgs/logo_az_1.png" height="50" valign="bottom">
				        			<img src="./imgs/logo_az_2.png" height="50" valign="bottom">
				        		</td>
				        	</tr>
				        </table>
				  	</td>
	    			<td valign="middle" align="center" width="10%">&nbsp;</td>
				</tr>
		      	<tr>
					<jsp:include  page="/component/spaceline.jsp" />
		  		</tr>
				<tr id="top">
					<td valign="middle" align="center" width="10%">&nbsp;</td>
	    			<td valign="middle" align="center" width="80%" >
	    				<table width="100%" border="1" cellspacing="0" cellpadding="0">
							<tr height="40">
	        					<td height="40" colspan="2" align="left" bgcolor="#004C38">
	        						<p class="style27">&nbsp;&nbsp;Preguntas</p>
	        					</td>
	      					</tr>
<%		Iterator it = moduleData.getQuestionIterator();
		while(it.hasNext()) {
		    QuestionData question = (QuestionData)it.next();
%>					      	<tr height="30">
								<td width="40%" height="32" align="left" bgcolor="#E2E2E2">
									<table>
										<tr>
											<td width="10">
				        					</td>
				      					</tr>
										<tr>
											<td>
												<span class="style14">
				       								<strong>
														<%=messages.getText(question.getKey()) %>
													</strong>
												</span>
				        					</td>
				      					</tr>
				      				</table>
								</td>
<%			if(question.getType().intValue() == QuestionData.TEXT || question.getType().intValue() == QuestionData.EMAIL) {
%>					      		<td width="60%" align="left" bgcolor="#FFFFFF">
									<span class="style14">
										&nbsp;<input type="text" name='<%="question"+String.valueOf(question.getId())%>' >
									</span>
								</td>	
<%			}else if(question.getType().intValue() == QuestionData.COUNTRY) {
%>								<td width="60%" align="left" bgcolor="#FFFFFF">
									<span class="style14">
										&nbsp;<select name='<%="question"+String.valueOf(question.getId())%>'>
<%				Iterator options = new CountryConstants(messages).getCountryIterator();
				while(options.hasNext()) {
					CountryData country = (CountryData)options.next();
%>											<option value='<%=String.valueOf(country.getId())%>'>
												<%=country.getName()%>
											</option>
<%				}
%>										</select>
									</span>		
								</td>
<%			}else if(question.getType().intValue() == QuestionData.OPTIONAL_DATE) {
%>								<td width="60%" align="left" bgcolor="#FFFFFF">
									<span class="style14">
										&nbsp;<input type="text" name='<%="questionDay"+String.valueOf(question.getId())%>' style="width:40;"/>&nbsp;/&nbsp;
	            						<input type="text" name='<%="questionMonth"+String.valueOf(question.getId())%>' style="width:40;"/>&nbsp;/&nbsp;
	            						<input type="text" name='<%="questionYear"+String.valueOf(question.getId())%>' style="width:80;" />
	            					<span class="style14">
	            					&nbsp;
	            					<input type="checkbox" name='<%="permanent"+String.valueOf(question.getId())%>'>Permanente</input>
            					</td>
<%			}else if(question.getType().intValue() == QuestionData.DATE || question.getType().intValue() == QuestionData.OPTIONAL_DATE
		 || question.getType().intValue() == QuestionData.BIRTHDATE) {
%>								<td width="60%" align="left" bgcolor="#FFFFFF">
									<span class="style14">
										&nbsp;<input type="text" name='<%="questionDay"+String.valueOf(question.getId())%>' style="width:40;"/>&nbsp;/&nbsp;
	            						<input type="text" name='<%="questionMonth"+String.valueOf(question.getId())%>' style="width:40;"/>&nbsp;/&nbsp;
	            						<input type="text" name='<%="questionYear"+String.valueOf(question.getId())%>' style="width:80;" />
	            					<span class="style14">
            					</td>
<%			}else if(question.getType().intValue() == QuestionData.EXCLUDED_OPTIONS) {
%>    							<td width="60%" align="left" bgcolor="#FFFFFF">
									<table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
<%				Iterator options = question.getAnswerIterator();
				while(options.hasNext()) {
				    AnswerData answer = (AnswerData)options.next();
%>										<tr height="25">
											<td width="5%" align="center" bgcolor="#FFFFFF">
												<input type="radio" name='<%="question"+String.valueOf(question.getId())%>' value='<%=String.valueOf(answer.getId())%>'/>
											</td>
											<td width="95%" align="left" bgcolor="#FFFFFF">
												<span class="style14">
													<%=messages.getText(answer.getKey()) %>
												</span>
											</td>
										</tr>
<%				}
%>									</table>
								</td>
<%			}else if(question.getType().intValue() == QuestionData.LIST) {
%>								<td width="60%" align="left" bgcolor="#FFFFFF">
									<span class="style14">
										&nbsp;<select name='<%="question"+String.valueOf(question.getId())%>'>
<%				Iterator options = question.getAnswerIterator();
				while(options.hasNext()) {
				    AnswerData answer = (AnswerData)options.next();
%>											<option value='<%=String.valueOf(answer.getId())%>'>
												<%=messages.getText(answer.getKey()) %>
											</option>
<%				}
%>										</select>
									</span>
								</td>
<%			}else if(question.getType().intValue() == QuestionData.KILOMETERS) {
%>								<td width="60%" align="left" bgcolor="#FFFFFF">
									<span class="style14">
										&nbsp;<input type="text" name='<%="question"+String.valueOf(question.getId())%>' class="input" />&nbsp;
										<select name='<%="unit"+String.valueOf(question.getId())%>'>
											<option value="0"><%=messages.getText("answer.units.kilometers") %></option>
											<option value="1"><%=messages.getText("answer.units.miles") %></option>
										</select>
									</span>
								</td>
<%			}else if(question.getType().intValue() == QuestionData.INCLUDED_OPTIONS) {
%> 								<td width="60%" align="left" bgcolor="#FFFFFF">
									<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<%				Iterator options = question.getAnswerIterator();
				while(options.hasNext()) {
					AnswerData answer = (AnswerData)options.next();
%>										<tr>
											<td width="5%" align="center" bgcolor="#FFFFFF">
												<input type="checkbox" name='<%="answer"+String.valueOf(answer.getId())%>'/>
											</td>
											<td width="95%" align="left" bgcolor="#FFFFFF">
												<span class="style14">
													<%=messages.getText(answer.getKey()) %>
												</span>
											</td>
										</tr>
<%				}
%>									</table>
								</td>
<%    		}
%>					      	</tr>
<%		}
%>									
      					</table>
	   				</td>
					<td valign="middle" align="center" width="10%">&nbsp;</td>
				</tr>
		      	<tr>
					<jsp:include  page="/component/spaceline.jsp" />
		  		</tr>
				<tr id="top">
					<td valign="middle" align="center" width="10%">&nbsp;</td>
					<td align="right">
	    				<table width="850" border="0" cellspacing="0" cellpadding="0">
					      	<tr>
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
					<td valign="middle" align="center" width="10%">&nbsp;</td>
				</tr>
			</table>
		</html:form>
	</body>
<%	}	%>
</html:html>
