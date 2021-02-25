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

<html xmlns="http://www.w3.org/1999/xhtml">
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
		session.setAttribute("url","report/assesment.jsp");
	
		if(session.getAttribute("Msg")!=null){
			session.removeAttribute("Msg");
		}
		messages=sys.getText();
	
		ListResult result = sys.getAssesmentReportFacade().findAssesments("","",sys.getUserSessionData());
		Iterator it = result.getElements().iterator();
		Collection assesments = new LinkedList();
		assesments.add(new OptionItem(messages.getText("generic.messages.select"),""));
		while(it.hasNext()) {
	        Object[] data = (Object[])it.next();
		    assesments.add(new OptionItem((String)data[1],String.valueOf(data[0])));
		}
		session.setAttribute("assesments",assesments);

		Collection format = new LinkedList();
		format.add(new OptionItem("HTML","HTML"));	    
		format.add(new OptionItem("PDF","PDF"));	    
		format.add(new OptionItem("XLS","XLS"));	    
		session.setAttribute("format",format);
%>
	<LINK REL=StyleSheet HREF="../util/css/estilo.css" TYPE="text/css">
		
<head/>

	<script type="text/javascript">
	function onSelectStart(calendar, date) {
		var date2 = calendar.date;
		var time = date2.getTime();
  		var input_day = document.getElementById("dayStart");
  		var input_month = document.getElementById("monthStart");
  		var input_year = document.getElementById("yearStart");
  		var date3 = new Date(time);
  		input_day.value = date3.print("%d");
  		input_month.value = date3.print("%m");
  		input_year.value= parseInt(date3.print("%Y"),10);
  	}
	function onSelectEnd(calendar, date) {
		var date2 = calendar.date;
		var time = date2.getTime();
  		var input_day = document.getElementById("dayEnd");
  		var input_month = document.getElementById("monthEnd");
  		var input_year = document.getElementById("yearEnd");
  		var date3 = new Date(time);
  		input_day.value = date3.print("%d");
  		input_month.value = date3.print("%m");
  		input_year.value= parseInt(date3.print("%Y"),10);
  	}
	</script>
<body>
	<table width="500" border="0" align="center" cellpadding="0" cellspacing="0">
		<tr class="guide1">
			<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("system.report.assesments")%>' />
  		</tr>
      	<tr>
			<jsp:include  page="../component/spaceline.jsp" />
  		</tr>
  		<tr>
    		<td width="500" valign="top">
				<jsp:include  page='<%="../component/utilityboxtop.jsp?title="+messages.getText("generic.report.parameters")%>' />
				<html:form action="/CompleteUser">
		    		<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<th align="left">
								<span class="input"><%=messages.getText("generic.assesment")%></span>
							</th>
							<td align="right">
      							<html:select property="assesment" styleClass="input">
	      							<html:options collection="assesments" property="value" labelProperty="label" styleClass="input"/>
		  						</html:select>   
							</td>
						</tr>
			      		<tr  class="space"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
						<tr>
							<th align="left" class="linetwo"><%=messages.getText("generic.messages.from")%></th>
	       					<td align="right" class="linetwo">
								<html:text property="startDate" style="width:20;" styleClass="input" styleId="dayStart" />/
			            		<html:text property="startMonth" style="width:20;" styleClass="input" styleId="monthStart" />/
			            		<html:text property="startYear" style="width:40;" styleClass="input" styleId="yearStart"/>
			            		<html:img page="/component/jscalendar-1.0/img.gif" styleId="calendar_buttonStart" style="cursor: pointer; border: 1px solid red;" onmouseover="this.style.background='red';" style="width:20;" align="middle" onmouseout="this.style.background=''"/>
			            		<script>
										Calendar.setup({
								        	inputArea     :    "yearStart",
								        	ifFormat       :    "%d/%m/%Y",
								        	showsTime      :    true,
								        	timeFormat     :    "24",
								        	button         :    "calendar_buttonStart",
								        	step           :    1,
								        	onSelect       :    onSelectStart,
								        	singleClick    :    true
								    	});
					    		</script>
			          		</td>
			          	</tr>
			      		<tr  class="space"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
						<tr>
							<th align="left" class="linetwo"><%=messages.getText("generic.messages.to")%></th>
	       					<td align="right" class="linetwo">
								<html:text property="endDate" style="width:20;" styleClass="input" styleId="dayEnd" />/
			            		<html:text property="endMonth" style="width:20;" styleClass="input"  styleId="monthEnd"/>/
			            		<html:text property="endYear" style="width:40;" styleClass="input"  styleId="yearEnd"/>
			            		<html:img page="/component/jscalendar-1.0/img.gif" styleId="calendar_buttonEnd"  style="cursor: pointer; border: 1px solid red;" onmouseover="this.style.background='red';" style="width:20;" align="middle" onmouseout="this.style.background=''"/>
			            		<script>
									Calendar.setup({
							        	inputArea     :    "yearEnd",
							        	ifFormat       :    "%d/%m/%Y",
							        	showsTime      :    true,
							        	timeFormat     :    "24",
							        	button         :    "calendar_buttonEnd",
							        	step           :    1,
							        	onSelect       :    onSelectEnd,
							        	singleClick    :    true
							    	});
			    				</script>
				          	</td>
				       	</tr>
   						<tr  class="space"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
						<tr>
							<th align="left">
								<span class="input"><%=messages.getText("generic.report.output")%></span>
							</th>
							<td align="right">
								<html:select property="output" styleClass="input">
									<html:options collection="format" property="value" labelProperty="label"/>
								</html:select>					
							</td>
						</tr>
   						<tr  class="space"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
			    		<tr>
	                		<td colspan="5" class="linetwo" align="right">
			                	<html:submit value='<%=messages.getText("generic.report.open")%>' styleClass="input"/>
							</td>
              			</tr>
					</table>
				</html:form>
				<jsp:include  page="../component/utilityboxbottom.jsp" />
			</td>
    </table>
</body>
<%
	}
%>
</html>
