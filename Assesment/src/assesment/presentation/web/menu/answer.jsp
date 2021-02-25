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
	    String module = request.getParameter("module");
	    ModuleData moduleData = null;
	    if(Integer.parseInt(module) == 0) {
	        moduleData = sys.getModuleReportFacade().getPsicoModule(sys.getUserSessionData());
	    }else {
	    	moduleData = sys.getModuleReportFacade().findModule(new Integer(module),sys.getUserSessionData());
	    }
%>

<html xmlns="http://www.w3.org/1999/xhtml">
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
	</head>
	<body scroll="auto">
 		<table width="100%" height="90" border="0" cellpadding="0" cellspacing="0" class="default">
 	 		<form name="logout" action="./logout.jsp" method="post"></form>
			<tr id="top">
    			<td height="90" colspan="2" valign="top" >
    				<table width="100%" height="90" border="0" cellpadding="0" cellspacing="0">
  						<tr>
				        	<td background="./imgs/top_bg2.gif" width="100%" colspan="3">
				        		&nbsp;
				        	</td>
  						</tr>
  						<tr>
						    <td width="4%" class="bgTop">
						    	<div id="menu_parent" align="center"><span class="style2"><%=messages.getText("generic.messages.preferences")%></span></div></td>
								<div id="menu_child" style="position: absolute; background: #e2e1e1; visibility: hidden;">
									<a href="javascript:openChangePassword(500,400)" style="display: block; width: 150px; height:25; text-align:center; vertical-align:middle;"  class="clA0"><%=messages.getText("generic.user.changepassword")%></a>
									<a style="display: block; width: 150px; height:25; text-align:center; vertical-align:middle;"  class="clA0" href="javascript:openChangeLanguage()"><%=messages.getText("generic.user.changelanguage")%></a>
								</div>
								<script type="text/javascript">
									at_attach("menu_parent", "menu_child", "hover", "y", "pointer");
								</script>
		    				<td width="35%" align="right" class="bgTop"><a href="#"   onClick="javascript:document.forms[0].submit()"><span class="style2" ><%=messages.getText("generic.messages.logout")%></span></a></td>
		    				<td width="2%" align="right" class="bgTop"><span class="style2" >&nbsp;</span></td>
					  	</tr>
					</table>
			  	</td>
			</tr>
	      	<tr>
				<jsp:include  page="/component/spaceline.jsp" />
	  		</tr>
			<tr id="top">
    			<td height="90" colspan="2" valign="top" >
					<html:form action="/Resultado">
						<html:hidden property="module" value='<%=module%>' />
						<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
							<tr class="guide1">
								<jsp:include  page='<%="/component/titlecomponent.jsp?title=Módulo"%>' />
		  					</tr>
					      	<tr>
								<jsp:include  page="/component/spaceline.jsp" />
					  		</tr>
			  				<tr>
					    		<td valign="top">
									<jsp:include  page='<%="/component/utilityboxtop.jsp?title=Resultado"%>' />
									<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<%		Iterator it = moduleData.getQuestionIterator();
		while(it.hasNext()) {
		    QuestionData question = (QuestionData)it.next();
%>								      	<tr  class="linetwo">
<%			if(question.getType().intValue() == QuestionData.TEXT || question.getType().intValue() == QuestionData.EMAIL) {
%>								      		<td>	
												<%=messages.getText(question.getKey()) %>
											</td>
											<td align="right" width="60%">
								      			<input type="text" name='<%="question"+String.valueOf(question.getId())%>' width="150" class="input">
								      		</td>	
<%			}else if(question.getType().intValue() == QuestionData.COUNTRY) {
%>								      		<td>	
												<%=messages.getText(question.getKey()) %>
											</td>
											<td class="linetwo" align="right">
												<select name='<%="question"+String.valueOf(question.getId())%>' class="input">
<%				Iterator options = new CountryConstants(messages).getCountryIterator();
				while(options.hasNext()) {
					CountryData country = (CountryData)options.next();
%>													<option value='<%=String.valueOf(country.getId())%>'>
														<%=country.getName()%>
													</option>
<%				}
%>												</select>
											</td>
<%			}else if(question.getType().intValue() == QuestionData.DATE || question.getType().intValue() == QuestionData.OPTIONAL_DATE) {
%>								      		<td>	
												<%=messages.getText(question.getKey()) %>
											</td>
											<td align="right" width="60%">
												<input type="text" name='<%="questionDay"+String.valueOf(question.getId())%>' style="width:20;" class="input" />/
			            						<input type="text" name='<%="questionMonth"+String.valueOf(question.getId())%>' style="width:20;" class="input" />/
			            						<input type="text" name='<%="questionYear"+String.valueOf(question.getId())%>' style="width:40;" class="input" />
			            					</td>
<%			}else if(question.getType().intValue() == QuestionData.EXCLUDED_OPTIONS) {
%>    										<td colspan="2" width="100%">
												<jsp:include  page='<%="/component/utilitybox2top.jsp?title="+messages.getText(question.getKey())%>' />
													<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<%				Iterator options = question.getAnswerIterator();
				while(options.hasNext()) {
				    AnswerData answer = (AnswerData)options.next();
%>													
														<tr>
															<td class="lineone">
																<input type="radio" name='<%="question"+String.valueOf(question.getId())%>' value='<%=String.valueOf(answer.getId())%>'/>
																<%=messages.getText(answer.getKey()) %>
															</td>
														</tr>
<%				}
%>													</table>
												<jsp:include  page="/component/utilitybox2bottom.jsp" />
											</td>
<%			}else if(question.getType().intValue() == QuestionData.LIST) {
%>								      		<td>	
												<%=messages.getText(question.getKey()) %>
											</td>
											<td class="linetwo" align="right">
												<select name='<%="question"+String.valueOf(question.getId())%>' class="input">
<%				Iterator options = question.getAnswerIterator();
				while(options.hasNext()) {
				    AnswerData answer = (AnswerData)options.next();
%>													<option value='<%=String.valueOf(answer.getId())%>'>
														<%=messages.getText(answer.getKey()) %>
													</option>
<%				}
%>												</select>
											</td>
<%			}else if(question.getType().intValue() == QuestionData.KILOMETERS) {
%>								      		<td>	
												<%=messages.getText(question.getKey()) %>
											</td>
											<td class="linetwo" align="right">
												<input type="text" name='<%="question"+String.valueOf(question.getId())%>' class="input" />
												<select name='<%="unit"+String.valueOf(question.getId())%>' class="input">
													<option value="0"><%=messages.getText("answer.units.kilometers") %></option>
													<option value="1"><%=messages.getText("answer.units.miles") %></option>
												</select>
											</td>
<%			}else if(question.getType().intValue() == QuestionData.INCLUDED_OPTIONS) {
%>    										<td colspan="2" width="100%">
												<jsp:include  page='<%="/component/utilitybox2top.jsp?title="+messages.getText(question.getKey())%>' />
												<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<%				Iterator options = question.getAnswerIterator();
				while(options.hasNext()) {
					AnswerData answer = (AnswerData)options.next();
%>													<tr>
														<td class="lineone">
															<input type="checkbox" name='<%="answer"+String.valueOf(answer.getId())%>'/>
															<%=messages.getText(answer.getKey()) %>
														</td>
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
								      		<td colspan="2" align="right">
									            <html:submit styleClass="input">
													<%=messages.getText("generic.messages.save")%>
												</html:submit>
									            <html:cancel styleClass="input">
													<%=messages.getText("generic.messages.cancel")%>
												</html:cancel>
			          						</td>
			      						</tr>
			      					</table>
									<jsp:include  page="/component/utilityboxbottom.jsp" />
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
