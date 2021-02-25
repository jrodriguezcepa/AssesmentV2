<%@page language="java"
	import="assesment.business.*"
	import="java.util.*"
	import="assesment.communication.language.*"
	import="assesment.communication.security.*"
	import="assesment.communication.util.*"
	import="assesment.presentation.actions.assesment.*"
	import="assesment.presentation.translator.web.util.*"
	import="assesment.communication.assesment.*"
	errorPage="../exception.jsp"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>
<html:html>

<LINK REL=StyleSheet HREF="../util/css/estilo.css" TYPE="text/css">
<%
	AssesmentAccess sys=(AssesmentAccess)session.getAttribute("AssesmentAccess");
	String check = Util.checkPermission(sys,SecurityConstants.ADMINISTRATOR);
	Text messages=sys.getText();

	if(check!=null) {
		response.sendRedirect(request.getContextPath()+check);
	}else {

		String type = request.getParameter("type");
		session.setAttribute("url","assesment/create.jsp?type="+type);

	
		RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
		dispatcher.include(request,response);
	
		String group = request.getParameter("group");
		String assesment = request.getParameter("assesment");
		AssesmentForm form = null;
		if(!Util.empty(assesment)) {
		    GroupData data = sys.getAssesmentReportFacade().findGroup(new Integer(group),sys.getUserSessionData());
		    String groupName = data.getName();
		    CategoryData categoryData = null;
		    AssesmentAttributes assessmentData = null;
		    Iterator<CategoryData> itC = data.getCategories().iterator();
		    boolean found = false;
		    while(itC.hasNext() && !found) {
		    	categoryData = itC.next();
		    	Iterator<AssesmentAttributes> itA = categoryData.getAssesments().iterator();
		    	while(itA.hasNext() && !found) {
		    		assessmentData = itA.next();
		    		found = (assessmentData.getId().intValue() == Integer.parseInt(assesment));
		    	}
		    }
			form = new AssesmentForm(assessmentData,sys);
			form.setCorporation(categoryData.getId().toString());
			form.setTarget(group);
			session.setAttribute("AssesmentChangeDatesForm",form);

%>
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
		<html:form action="/ChangeDates">
			<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("generic.assesment.changedates")%>' />
		      	<tr>
		      		<td>
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.assesment.data")%>' />
						<table width="100%" cellpadding="2" cellspacing="2">	
							<tr class="line">
								<td align="left">
									<%=messages.getText("generic.group")%>
								</td>
								<td align="right">
			   						<%=data.getName()%>              
								</td>
							</tr>
							<tr class="line">
								<td align="left">
									<%=messages.getText("group.category")%>
								</td>
								<td align="right">
			   						<%=messages.getText(categoryData.getKey())%>              
								</td>
							</tr>
							<tr class="line">
								<td align="left">
									<%=messages.getText("generic.assesment")%>
								</td>
								<td align="right">
			   						<%=assessmentData.getName()%>              
								</td>
							</tr>
							<tr class="line">
								<td align="left">
									<%=messages.getText("assesment.data.start")%><span class="required">*</span>&nbsp;
									<html:text property="startDay" style="width:50px;" styleClass="input" styleId="dayStart" />/
            						<html:text property="startMonth" style="width:50px;" styleClass="input" styleId="monthStart" />/
            						<html:text property="startYear" style="width:100px;" styleClass="input" styleId="yearStart"/>
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
								<td align="right">
									<%=messages.getText("assesment.data.end")%><span class="required">*</span>&nbsp;
									<html:text property="endDay" style="width:50px;" styleClass="input" styleId="dayEnd" />/
            						<html:text property="endMonth" style="width:50px;" styleClass="input"  styleId="monthEnd"/>/
            						<html:text property="endYear" style="width:100px;" styleClass="input"  styleId="yearEnd"/>
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
	          			</table>
						<jsp:include  page='../component/utilitybox2bottom.jsp' />
					</td>
			   	</tr>
		      	<tr>
		      		<td align="right" colspan="3">
			            <html:submit styleClass="input"><%=messages.getText("generic.messages.save")%></html:submit>
			            <html:cancel styleClass="input"><%=messages.getText("generic.messages.cancel")%></html:cancel>
		      		</td>
		      	</tr>
			<jsp:include  page='../component/titleend.jsp' />
		</html:form>
	</body>
<%		}
	}
%>
</html:html>
