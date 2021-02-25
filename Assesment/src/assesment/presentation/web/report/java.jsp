<%@page import="assesment.communication.assesment.AssesmentListData"%>
<%@page language="java" 
	errorPage="../exception.jsp"
	import="java.util.*"
	import="assesment.business.*"
	import="assesment.communication.language.*"
	import="assesment.communication.security.*"
	import="assesment.communication.corporation.*"
	import="assesment.communication.util.*"
	import="assesment.presentation.translator.web.util.*"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>


<%@page import="assesment.communication.assesment.AssesmentData"%><html xmlns="http://www.w3.org/1999/xhtml">
<%!
	Text messages;	AssesmentAccess sys;
%>
<%
	sys=(AssesmentAccess)session.getAttribute("AssesmentAccess");
	String check = Util.checkPermission(sys,SecurityConstants.ADMINISTRATOR);
	
	RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
	dispatcher.include(request,response);

	if(check!=null) {
		response.sendRedirect(request.getContextPath()+check);
	}else {
		session.setAttribute("url","report/java.jsp");
	
		if(session.getAttribute("Msg")!=null){
			session.removeAttribute("Msg");
		}
		messages=sys.getText();
	
		ListResult result = sys.getAssesmentReportFacade().findAssesments("","",sys.getUserSessionData());
%>
	<LINK REL=StyleSheet HREF="../util/css/estilo.css" TYPE="text/css">
		
	<head/>
	<script type="text/javascript">
	function onSelectFrom(calendar, date) {
		var date2 = calendar.date;
		var time = date2.getTime();
  		var input_day = document.getElementById("dayFrom");
  		var input_month = document.getElementById("monthFrom");
  		var input_year = document.getElementById("yearFrom");
  		var date3 = new Date(time);
  		input_day.value = date3.print("%d");
  		input_month.value = date3.print("%m");
  		input_year.value= parseInt(date3.print("%Y"),10);
  	}
	function onSelectTo(calendar, date) {
		var date2 = calendar.date;
		var time = date2.getTime();
  		var input_day = document.getElementById("dayTo");
  		var input_month = document.getElementById("monthTo");
  		var input_year = document.getElementById("yearTo");
  		var date3 = new Date(time);
  		input_day.value = date3.print("%d");
  		input_month.value = date3.print("%m");
  		input_year.value= parseInt(date3.print("%Y"),10);
  	}
	function openReport(type) {
		var form = document.forms['JavaReportForm'];
		form.type.value = type;
		form.submit();
  	}
	</script>
	<body>
		<html:form action="/JavaReport">
			<html:hidden property="type" />
				<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("generic.report.java")%>' />
				<tr>
		    		<td valign="top">
						<jsp:include  page='../component/utilitybox2top.jsp?title=Charla UPM' />
							<table width="100%" border="0" cellpadding="5" cellspacing="5">
								<tr class="line">
									<td align="right">
										<input type="button" class="button" value='<%=messages.getText("generic.report.open") %>' onclick="javascript:openReport(10);">
									</td>
								</tr>
						</table>
						<jsp:include  page="../component/utilitybox2bottom.jsp" />
					</td>
				</tr>
				<tr>
		    		<td valign="top">
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("report.java.timac")%>' />
							<table width="100%" border="0" cellpadding="5" cellspacing="5">
								<tr class="line">
									<td align="right">
										<input type="button" class="button" value='<%=messages.getText("generic.report.open") %>' onclick="javascript:openReport(11);">
									</td>
								</tr>
						</table>
						<jsp:include  page="../component/utilitybox2bottom.jsp" />
					</td>
				</tr>				
		  		<tr>
		    		<td valign="top">
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("report.java.totalanswers")%>' />
			    		<table width="100%" border="0" cellpadding="5" cellspacing="5">
							<tr class="line">
								<td align="left">
									<%=messages.getText("generic.assesment")+":"%>&nbsp;
								</td>
								<td align="right">
									<html:select property="assessment3" styleClass="input">
<%		Iterator<AssesmentListData> it = Util.getAssessmentIterator(result.getElements(), messages);
		while(it.hasNext()) {
			AssesmentListData assesment = it.next();
%>									 	<html:option value='<%=String.valueOf(assesment.getId())%>'><%=String.valueOf(assesment.getName())%></html:option>
<%		}
%>									</html:select>
								</td>
							</tr>
							<tr class="line">
								<td align="left">
									<%=messages.getText("question.data.type")+":"%>&nbsp;
								</td>
								<td align="right">
									<html:select property="typeResult" styleClass="input">
									 	<html:option value='0'><%=messages.getText("javareport.answers.type0") %></html:option>
									 	<html:option value='1'><%=messages.getText("javareport.answers.type1") %></html:option>
									</html:select>
								</td>
							</tr>
							<tr>
								<td align="right" colspan="2">
									<input type="button" class="button" value='<%=messages.getText("generic.report.open") %>' onclick="javascript:openReport(8);">
								</td>
							</tr>
						</table>
						<jsp:include  page="../component/utilitybox2bottom.jsp" />
					</td>
				</tr>
		  		<tr>
		    		<td valign="top">
						<jsp:include  page='../component/utilitybox2top.jsp?title=ANTP México - PNSV 2' />
			    		<table width="100%" border="0" cellpadding="5" cellspacing="5">
							<tr class="line">
								<td align="right">
									<input type="button" class="button" value='<%=messages.getText("generic.report.open") %>' onclick="javascript:openReport(9);">
								</td>
							</tr>
						</table>
						<jsp:include  page="../component/utilitybox2bottom.jsp" />
					</td>
				</tr>
		  		<tr>
		    		<td valign="top">
						<jsp:include  page='../component/utilitybox2top.jsp?title=ANTP México - PNSV' />
			    		<table width="100%" border="0" cellpadding="5" cellspacing="5">
							<tr class="line">
								<td align="right">
									<input type="button"  class="button" value='<%=messages.getText("generic.report.open") %>' onclick="javascript:openReport(7);">
								</td>
							</tr>
						</table>
						<jsp:include  page="../component/utilitybox2bottom.jsp" />
					</td>
				</tr>
		  		<tr>
		    		<td valign="top">
						<jsp:include  page='../component/utilitybox2top.jsp?title=CEPA México - RSMM' />
			    		<table width="100%" border="0" cellpadding="5" cellspacing="5">
							<tr class="line">
								<td align="right">
									<input type="button"  class="button" value='<%=messages.getText("generic.report.open") %>' onclick="javascript:openReport(6);">
								</td>
							</tr>
						</table>
						<jsp:include  page="../component/utilitybox2bottom.jsp" />
					</td>
				</tr>
		  		<tr>
		    		<td valign="top">
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("report.java.pepsico")%>' />
			    		<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="line">
								<td align="right">
									<input type="button"  class="button" value='<%=messages.getText("generic.report.open") %>' onclick="javascript:openReport(1);">
								</td>
							</tr>
						</table>
						<jsp:include  page="../component/utilitybox2bottom.jsp" />
					</td>
				</tr>
		  		<tr>
		    		<td valign="top">
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("report.java.jj")%>' />
			    		<table width="100%" border="0" cellpadding="5" cellspacing="5">
							<tr class="line">
								<td align="left">
									<%=messages.getText("generic.assesment")+":"%>&nbsp;
									<html:select property="assessment1" styleClass="input">
									 	<html:option value='<%=String.valueOf(AssesmentData.JJ) %>'>J&J Lat y Caribe 1</html:option>
									 	<html:option value='<%=String.valueOf(AssesmentData.JJ_2) %>'>J&J Lat y Caribe 2</html:option>
									 	<html:option value='<%=String.valueOf(AssesmentData.JJ_3) %>'>J&J Lat y Caribe 3</html:option>
									 	<html:option value='<%=String.valueOf(AssesmentData.JJ_4) %>'>J&J Lat y Caribe 4</html:option>
									</html:select>
								</td>
								<td align="right">
									<input type="button"  class="button" value='<%=messages.getText("generic.report.open") %>' onclick="javascript:openReport(2);">
								</td>
							</tr>
						</table>
						<jsp:include  page="../component/utilitybox2bottom.jsp" />
					</td>
				</tr>
		  		<tr>
		    		<td valign="top">
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("report.java.assessmentquestions")%>' />
			    		<table width="100%" border="0" cellpadding="5" cellspacing="5">
							<tr class="line">
								<td align="left">
									<%=messages.getText("generic.assesment")+":"%>&nbsp;
								</td>
								<td align="right">
									<html:select property="assessment2" styleClass="input">
<%		Iterator<AssesmentListData> it = Util.getAssessmentIterator(result.getElements(), messages);
		while(it.hasNext()) {
			AssesmentListData assesment = it.next();
%>									 	<html:option value='<%=String.valueOf(assesment.getId())%>'><%=String.valueOf(assesment.getName())%></html:option>
<%		}
%>									</html:select>
								</td>
							</tr>
							<tr>
								<td align="right" colspan="2">
									<input type="button" class="button" value='<%=messages.getText("generic.report.open") %>' onclick="javascript:openReport(3);">
								</td>
							</tr>
						</table>
						<jsp:include  page="../component/utilitybox2bottom.jsp" />
					</td>
				</tr>
		  		<tr>
		    		<td valign="top">
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("report.java.basf")%>' />
			    		<table width="100%" border="0" cellpadding="5" cellspacing="5">
							<tr class="line">
								<td align="left" width="10%">
									<%=messages.getText("generic.messages.from")+":"%>&nbsp;
								</td>
     							<td align="left" width="40%">
									<html:text property="fromDay" style="width:50px;" styleClass="input" styleId="dayFrom" />/
            						<html:text property="fromMonth" style="width:50px;" styleClass="input" styleId="monthFrom" />/
            						<html:text property="fromYear" style="width:100px;" styleClass="input" styleId="yearFrom"/>
            						<html:img page="/component/jscalendar-1.0/img.gif" styleId="calendar_buttonFrom" style="cursor: pointer; border: 1px solid red;" onmouseover="this.style.background='red';" style="width:20;" align="middle" onmouseout="this.style.background=''"/>
            						<script>
										Calendar.setup({
								        	inputArea     :    "yearFrom",
								        	ifFormat       :    "%d/%m/%Y",
								        	showsTime      :    true,
								        	timeFormat     :    "24",
								        	button         :    "calendar_buttonFrom",
								        	step           :    1,
								        	onSelect       :    onSelectFrom,
								        	singleClick    :    true
								    	});
								    </script>
          						</td>
								<td align="right" width="20%">
									<%=messages.getText("generic.messages.to")+":"%>&nbsp;
								</td>
     							<td align="left" width="30%">
									<html:text property="toDay" style="width:50px;" styleClass="input" styleId="dayTo" />/
            						<html:text property="toMonth" style="width:50px;" styleClass="input" styleId="monthTo" />/
            						<html:text property="toYear" style="width:100px;" styleClass="input" styleId="yearTo"/>
            						<html:img page="/component/jscalendar-1.0/img.gif" styleId="calendar_buttonTo" style="cursor: pointer; border: 1px solid red;" onmouseover="this.style.background='red';" style="width:20;" align="middle" onmouseout="this.style.background=''"/>
            						<script>
										Calendar.setup({
								        	inputArea     :    "yearTo",
								        	ifFormat       :    "%d/%m/%Y",
								        	showsTime      :    true,
								        	timeFormat     :    "24",
								        	button         :    "calendar_buttonTo",
								        	step           :    1,
								        	onSelect       :    onSelectTo,
								        	singleClick    :    true
								    	});
								    </script>
          						</td>
							</tr>
							<tr class="line">
								<td align="left" colspan="2">
									<%=messages.getText("question.data.type")+":"%>&nbsp;
     								<html:select property="typeBasf" styleClass="input">
     									<html:option value="1"><%=messages.getText("generic.messages.resumed") %></html:option>
     									<html:option value="2"><%=messages.getText("generic.messages.detailed") %></html:option>
     								</html:select>
								</td>
								<td align="right" colspan="2">
									<input type="button" class="button" value='<%=messages.getText("generic.report.open") %>' onclick="javascript:openReport(4);">
								</td>
							</tr>
						</table>
						<jsp:include  page="../component/utilitybox2bottom.jsp" />
					</td>
				</tr>
		  		<!-- tr>
		    		<td valign="top">
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("report.java.mdp")%>' />
			    		<table width="100%" border="0" cellpadding="5" cellspacing="5">
							<tr class="line">
								<td align="right">
									<input type="button" class="button" value='<%=messages.getText("generic.report.open") %>' onclick="javascript:openReport(5);">
								</td>
							</tr>
						</table>
						<jsp:include  page="../component/utilitybox2bottom.jsp" />
					</td>
				</tr -->
		    </table>
		</html:form>
	</body>
<%
	}
%>
</html>
