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
	

		String assesment = request.getParameter("assesment");
		AssesmentForm form = null;
		if(Util.empty(assesment)) {
			String corporation = request.getParameter("corporation");
			form = new AssesmentForm(corporation);
			session.setAttribute("AssesmentCreateForm",form);
		}else {
		    AssesmentAttributes data = sys.getAssesmentReportFacade().findAssesmentAttributes(new Integer(assesment),sys.getUserSessionData());
			form = new AssesmentForm(data,sys);
			session.setAttribute("AssesmentUpdateForm",form);
		}

		String target = request.getParameter("target");
		String actionName = (type.equals("create")) ? "/AssesmentCreate" : "/AssesmentUpdate";
		String formName = (type.equals("create")) ? "AssesmentCreateForm" : "AssesmentUpdateForm";
		
		Collection corporationData = new LinkedList();
		corporationData.add(new OptionItem(messages.getText("generic.messages.select"),""));
		ListResult corporations = sys.getCorporationReportFacade().findCorporationName("",sys.getUserSessionData());
		if(corporations != null && corporations.getElements() != null) {
		    Iterator it = corporations.getElements().iterator();
		    while(it.hasNext()) {
		        Object[] data = (Object[])it.next();
		        corporationData.add(new OptionItem((String)data[1],String.valueOf(data[0])));
		    }
		}
		session.setAttribute("corporations",corporationData);
		
		Collection statusData = new LinkedList();
		statusData.add(new OptionItem(messages.getText("assesment.status.editable"),String.valueOf(AssesmentAttributes.EDITABLE)));
		statusData.add(new OptionItem(messages.getText("assesment.status.noeditable"),String.valueOf(AssesmentAttributes.NO_EDITABLE)));
		statusData.add(new OptionItem(messages.getText("assesment.status.ended"),String.valueOf(AssesmentAttributes.ENDED)));
		session.setAttribute("statusData",statusData);

		Collection optionsData = new LinkedList();
		optionsData.add(new OptionItem(messages.getText("generic.messages.no"),"0"));
		optionsData.add(new OptionItem(messages.getText("generic.messages.yes"),"1"));
		session.setAttribute("options",optionsData);

		Collection optionsCertificate = new LinkedList();
		optionsCertificate.add(new OptionItem(messages.getText("generic.messages.no"),"0"));
		optionsCertificate.add(new OptionItem(messages.getText("assessment.certificate.default"),"1"));
		optionsCertificate.add(new OptionItem(messages.getText("assessment.certificate.images"),"2"));
		session.setAttribute("optionsCertificate",optionsCertificate);

		Iterator itAssesments = sys.getUserReportFacade().findActiveAssesments("",sys.getUserSessionData()).iterator();
		Collection assesmentList = new LinkedList();
		assesmentList.add(new OptionItem(messages.getText("generic.messages.no"),"0"));
		while(itAssesments.hasNext()){
	        Object[] data = (Object[])itAssesments.next();
			assesmentList.add(new OptionItem(messages.getText(String.valueOf(data[1])).trim(), String.valueOf(data[0])));
		}
		Collections.sort((List)assesmentList);
		session.setAttribute("assesments",assesmentList);

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
  	function showCertificates() {
  	  	var certificatesDiv = document.getElementById('certificates');
  	  	if(document.forms['<%=formName%>'].certificate.selectedIndex == 2) {
  	  		certificatesDiv.style.display = 'block';
  	  	}else {
  	  		certificatesDiv.style.display = 'none';
  	  	}
  	}
	</script>
	<body>
		<html:form action="<%=actionName%>"  enctype="multipart/form-data">
			<html:hidden property="target" value="<%=target%>" />
			<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("generic.assesment.new")%>' />
		      	<tr>
		      		<td>
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.assesment.data")%>' />
						<table align='center' width='100%' cellpadding="2" cellspacing="2">
							<tr class="line">
				        		<td align="left">
				        		 	<%=messages.getText("assesment.data.corporation")%><span class="required">*</span>
				        		 </td>
			        			<td align="right">
				      				<html:select property="corporation" styleClass="input" style="width: 300px;">
					      				<html:options collection="corporations" property="value" labelProperty="label" styleClass="input"/>
						  			</html:select>   
				          		</td>
				          	</tr>
							<tr class="line">
								<td align="left">
									<%=messages.getText("assesment.data.name")+" ("+messages.getText("es")+")"%><span class="required">*</span>
								</td>
								<td align="right">
			   						<html:text property="name_es" style="width: 300px;" styleClass="input"/>              
								</td>
							</tr>
							<tr class="line">
								<td align="left">
									<%=messages.getText("assesment.data.name")+" ("+messages.getText("en")+")"%><span class="required">*</span>
								</td>
								<td align="right">
			   						<html:text property="name_en" style="width: 300px;" styleClass="input"/>              
								</td>
							</tr>
							<tr class="line">
								<td align="left">
									<%=messages.getText("assesment.data.name")+" ("+messages.getText("pt")+")"%><span class="required">*</span>
								</td>
								<td align="right">
			   						<html:text property="name_pt" style="width: 300px;" styleClass="input"/>              
								</td>
							</tr>
							<tr class="line" style="height: 40px;">
								<td align="left"><%=messages.getText("assesment.data.icon")%><span class="required">*</span></td>
								<td align="right">
			   						<html:file property="icon" styleClass="line"/>              
								</td>
							</tr>
							<tr class="line">
								<td align="left" colspan="2">
									<%=messages.getText("assesment.data.description")%>
								</td>
							</tr>
							<tr class="line">
								<td align="left" colspan="2">
			   						<html:textarea property="description" cols="150" rows="4" styleClass="input"/>              
								</td>
							</tr>
							<tr class="line">
								<td align="left">
									<%=messages.getText("assesment.data.start")%><span class="required">*</span>
								</td>
								<td align="right">
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
							</tr>
							<tr class="line">
								<td align="left">
									<%=messages.getText("assesment.data.end")%><span class="required">*</span>
								</td>
								<td align="right">
									<html:text property="endDay" style="width:50px" styleClass="input" styleId="dayEnd" />/
            						<html:text property="endMonth" style="width:50px" styleClass="input"  styleId="monthEnd"/>/
            						<html:text property="endYear" style="width:100px" styleClass="input"  styleId="yearEnd"/>
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
<%		if(type.equals("edit")) {
%>							<tr class="line">
								<td align="left">
									<%=messages.getText("assesment.data.status")%>
								</td>
								<td align="right">
	      							<html:select property="status" styleClass="input">
		      							<html:options collection="statusData" property="value" labelProperty="label" styleClass="input"/>
			  						</html:select>   
								</td>
							</tr>
							<tr>
								<td align="left">
									<%=messages.getText("assesment.data.archived")%>
								</td>
								<td align="right">
	      							<html:select property="archived" styleClass="input">
		      							<html:option value="0"><%=messages.getText("generic.messages.no").toUpperCase()%></html:option>
		      							<html:option value="1"><%=messages.getText("generic.messages.yes").toUpperCase()%></html:option>
			  						</html:select>   
								</td>
							</tr>	
<%		}else {
%>	    					<html:hidden property="status"/>      				
<%		}
%>	
							<tr class="line">
	    						<td align="left"> 
	    							<%=messages.getText("report.generalresult.minhighlevel")%><span class="required">*</span>
	    						</td>
		   						<td align="right">
			   						<html:text property="green" size="10" styleClass="input"/>              
			  					</td>
	      					</tr>
							<tr class="line">
	    						<td align="left"> 
	    							<%=messages.getText("report.generalresult.minmeddiumlevel")%><span class="required">*</span>
	    						</td>
		   						<td align="right">
			   						<html:text property="yellow" size="10" styleClass="input"/>              
			  					</td>
	      					</tr>
							<tr class="line">
	    						<td align="left"> 
	    							<%=messages.getText("assesment.wrt.showemail")%>
								</td>
								<td align="right">
	      							<html:select property="emailWRT" styleClass="input" >
		      							<html:options collection="options" property="value" labelProperty="label" styleClass="input"/>
			  						</html:select>   
			  					</td>
	      					</tr>
							<tr class="line">
	        					<td align="left">
									<%=messages.getText("assesment.module.psicologic")%><span class="required">*</span>
								</td>
								<td align="right">
	      							<html:select property="psitest" styleClass="input" >
		      							<html:options collection="options" property="value" labelProperty="label" styleClass="input"/>
			  						</html:select>   
	          					</td>
	      					</tr>
							<tr class="line">
    							<td align="left"> 
    								<%=messages.getText("assesment.module.repeatable")%>
								</td>
	       						<td align="right">
	      							<html:select property="repeatable" styleClass="input" >
		      							<html:options collection="options" property="value" labelProperty="label" styleClass="input"/>
			  						</html:select>   
		  						</td>
      						</tr>
							<tr class="line">
	        					<td align="left"> 
	        						<%=messages.getText("assesment.data.reportfeedback")%><span class="required">*</span>
	        					</td>
        						<td align="right">
	      							<html:select property="reportFeedback" styleClass="input" >
		      							<html:options collection="options" property="value" labelProperty="label" styleClass="input"/>
			  						</html:select>   
	          					</td>
	          				</tr>
						</table>
						<jsp:include  page="../component/utilitybox2bottom.jsp" />
					</td>
				</tr>	
				<tr>
					<td>
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("assesment.data.attach")%>' />
						<table width="100%" cellpadding="0" cellspacing="0">	
							<tr class="line" style="height: 40px;">
								<td align="left"><%=messages.getText("es")%></td>
								<td align="right">
			   						<html:file property="attachesPDF" styleClass="line"/>              
								</td>
							</tr>
							<tr class="line" style="height: 40px;">
								<td align="left"><%=messages.getText("en")%></td>
								<td align="right">
			   						<html:file property="attachenPDF" styleClass="line"/>              
								</td>
							</tr>
							<tr class="line" style="height: 40px;">
								<td align="left"><%=messages.getText("pt")%></th>
								<td align="right">
			   						<html:file property="attachptPDF" styleClass="line"/>              
								</td>
							</tr>
						</table>	
						<jsp:include  page="../component/utilitybox2bottom.jsp" />
					</td>
				</tr>				
				<tr>
					<td>
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.certificate")%>' />
						<table width="100%" cellpadding="0" cellspacing="0">	
							<tr class="line">
								<td align="left">
									<%=messages.getText("assesment.data.certificate")%>
								</td>
     							<td align="right">
	      							<html:select property="certificate" styleClass="input" onchange="showCertificates()">
		      							<html:options collection="optionsCertificate" property="value" labelProperty="label" styleClass="input" />
			  						</html:select>   
								</td>
							</tr>	
<%		String display = "none";
		if(type.equals("edit")) {
			if(form.getCertificate().equals("2")) {
				display = "block";
			}
		}
%>							<tr class="line">
						      	<td colspan="2">
						      		<div id="certificates" style='display: <%=display%>;'>
			      						<table width="100%" cellpadding="2" cellspacing="2">
											<tr class="line" style="height: 40px;">
												<td align="left">
													<%=messages.getText("assesment.data.certificate.es")+" (*.png)"%>
												</td>
												<td align="right">
							   						<html:file property="certificateES" size="35" styleClass="line"/>              
												</td>
											</tr>
											<tr class="line" style="height: 40px;">
												<td align="left">
													<%=messages.getText("assesment.data.certificate.en")+" (*.png)"%>
												</td>
												<td align="right">
							   						<html:file property="certificateEN" size="30" styleClass="line" />              
												</td>
											</tr>
											<tr class="line" style="height: 40px;">
												<td align="left">
													<%=messages.getText("assesment.data.certificate.pt")+" (*.png)"%>
												</td>
												<td align="right">
							   						<html:file property="certificatePT" size="25" styleClass="line" />              
												</td>
											</tr>
			      						</table>
			      					</div>
			      				</td>
			      			</tr>
						</table>
						<jsp:include  page="../component/utilitybox2bottom.jsp" />
					</td>
				</tr>
<%		if(!type.equals("edit")) {
%>				<tr>
					<td colspan="2">
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("Contenido")%>' />
							<table width="100%" cellpadding="0" cellspacing="0">	
								<tr  class="line">
									<td align="left">
										<%=messages.getText("assesment.data.copy")%>
									</td>
									<td align="right">
		      							<html:select property="copy" styleClass="input" >
			      							<html:options collection="assesments" property="value" labelProperty="label" styleClass="input"/>
				  						</html:select>   
									</td>
								</tr>	
							</table>
						<jsp:include  page="../component/utilitybox2bottom.jsp" />
					</td>
				</tr>
<%		}
%>
				<html:hidden property="certificateImageES"/>      				
				<html:hidden property="certificateImageEN"/>      				
				<html:hidden property="certificateImagePT"/>      				
		      	<tr>
		      		<td align="right" colspan="3">
			            <html:submit styleClass="input"><%=messages.getText("generic.messages.save")%></html:submit>
			            <html:cancel styleClass="input"><%=messages.getText("generic.messages.cancel")%></html:cancel>
		      		</td>
		      	</tr>
			<jsp:include  page='../component/titleend.jsp' />
		</html:form>
	</body>
<%	}	
%>
</html:html>
