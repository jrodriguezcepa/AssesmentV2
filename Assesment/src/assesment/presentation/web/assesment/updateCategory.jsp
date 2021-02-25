<%@page import="assesment.presentation.actions.assesment.CategoryForm"%>
<%@page import="assesment.communication.util.ListResult"%>
<%@page language="java"
	import="assesment.business.*"
	import="java.util.*"
	import="assesment.communication.language.*"
	import="assesment.communication.security.*"
	import="assesment.communication.assesment.*"
	import="assesment.communication.module.*"
	import="assesment.presentation.translator.web.util.*"
	import="assesment.communication.corporation.*"
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
	if(check!=null) {
		response.sendRedirect(request.getContextPath()+check);
	}else {
		String type = request.getParameter("type");
		session.setAttribute("url","assesment/createCategory.jsp?type="+type);

		RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
		dispatcher.include(request,response);
	
		Text messages=sys.getText();

		String id = request.getParameter("id");
		if(id == null) {
			id = (String) session.getAttribute("category");
		}
				
		CategoryData category = sys.getAssesmentReportFacade().findCategory(new Integer(id), sys.getUserSessionData());
		CategoryForm form = new CategoryForm(category);
		session.setAttribute("CategoryUpdateForm", form);

		Collection typeData = new LinkedList();
		typeData.add(new OptionItem("Video","1"));
		typeData.add(new OptionItem("Icono","2"));
		typeData.add(new OptionItem("Volante","3"));
		session.setAttribute("types",typeData);


		Collection colorData = new LinkedList();
		colorData.add(new OptionItem("Oscuro","1"));
		colorData.add(new OptionItem("Claro","2"));
		session.setAttribute("colors", colorData);

		Collection orderData = new LinkedList();
		for(int i = 1; i <= 20; i++) {
			orderData.add(new OptionItem(String.valueOf(i),String.valueOf(i)));
		}
		session.setAttribute("orders", orderData);
%>
	<head/>
	<script>
		function assessments(form){
			var elements = form.elements;
			var list="";
		
			for(var i=0 ; i < elements.length; i++){
				var element=elements[i];
				if(element.type.toLowerCase()=="checkbox"){
					if(element.checked){
						if(list==""){
							list = element.value;
						}else{
							list=element.value+"<"+list;
						}	
					}	
				} 
			}
			if(list.length>0){
				form.list.value=list;
				form.submit();
			}
		}
		function deleteAssessments(form, msg){
			var elements = form.elements;
			var list="";
		
			for(var i=0 ; i < elements.length; i++){
				var element=elements[i];
				if(element.type.toLowerCase()=="checkbox"){
					if(element.checked){
						if(list==""){
							list = element.value;
						}else{
							list=element.value+"<"+list;
						}	
					}	
				} 
			}
			if(list.length>0){
				if(confirm(msg)){
					form.list.value=list;
					form.submit();
				}
			}
		}
	</script>
	<form name='back' action='./layout.jsp'>
		<input type='hidden' name='refer' value='/assesment/viewGroup.jsp' />
		<input type='hidden' name='id' value='<%=String.valueOf(category.getGroupId())%>' />
	</form>
	<body>
		<jsp:include  page='<%="../component/titlecomponent.jsp?title="+messages.getText("group.category")%>' />
	    	<tr>
	    		<td>
					<html:form action='/CategoryUpdate' enctype="multipart/form-data">
						<html:hidden property="id" value='<%=id%>'/>
						<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("generic.category.data")%>' />
							<table align='center' width='100%' cellpadding="0" cellspacing="0">
								<tr class="line">
					        		<td align="left">
					        		 	<%=messages.getText("group.category.type")%>
					        		 </td>
				        			<td align="right">
					      				<html:select property="type" styleClass="input" style="width: 300px;">
						      				<html:options collection="types" property="value" labelProperty="label" styleClass="input"/>
							  			</html:select>   
					          		</td>
					          	</tr>
								<tr class="line">
					        		<td align="left">
					        		 	<%=messages.getText("group.category.order")%>
					        		 </td>
				        			<td align="right">
					      				<html:select property="ord" styleClass="input" style="width: 300px;">
						      				<html:options collection="orders" property="value" labelProperty="label" styleClass="input"/>
							  			</html:select>   
					          		</td>
					          	</tr>
								<tr class="line">
									<td align="left"><%=messages.getText("group.category.name")+" ("+messages.getText("module.data.name.es")+")"%><span class="required">*</span></td>
									<td align="right" width="80%">
				   						<html:text property="es_Text" styleClass="input" style="width:300px;"/>              
									</td>
								</tr>
								<tr class="line">
									<td align="left"><%=messages.getText("group.category.name")+" ("+messages.getText("module.data.name.en")+")"%><span class="required">*</span></td>
									<td align="right" width="80%">
				   						<html:text property="en_Text" styleClass="input" style="width:300px;" />              
									</td>
								</tr>
								<tr class="line">
									<td align="left"><%=messages.getText("group.category.name")+" ("+messages.getText("module.data.name.pt")+")"%><span class="required">*</span></td>
									<td align="right" width="80%">
				   						<html:text property="pt_Text" styleClass="input" style="width:300px;" />
									</td>
								</tr>
								<tr class="line">
					        		<td align="left">
					        		 	<%=messages.getText("group.category.titleColor")%>
					        		 </td>
				        			<td align="right">
					      				<html:select property="titleColor" styleClass="input" style="width: 300px;">
						      				<html:options collection="colors" property="value" labelProperty="label" styleClass="input"/>
							  			</html:select>   
					          		</td>
					          	</tr>
								<tr class="line">
					        		<td align="left">
					        		 	<%=messages.getText("group.category.itemColor")%>
					        		 </td>
				        			<td align="right">
					      				<html:select property="itemColor" styleClass="input" style="width: 300px;">
						      				<html:options collection="colors" property="value" labelProperty="label" styleClass="input"/>
							  			</html:select>   
					          		</td>
					          	</tr>
								<tr class="line" style="height: 40px;">
					        		<td align="left">
										<%=messages.getText("generic.group.background")%>
									</td>
				        			<td align="right">
				   						<html:file property="logo" style="width:400px;" styleClass="line"/>              
									</td>
								</tr>
			     				<tr>
									<td align="right" colspan="3">
									    <html:submit styleClass="input"><%=messages.getText("generic.messages.save")%></html:submit>
									</td>
								</tr>
							</table>
						<jsp:include  page="../component/utilitybox2bottom.jsp" />
					</html:form>	
				</td>
			</tr>
<%		Iterator assIt = category.getOrderedAssesments();
%>			<tr>
				<td>
					<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("group.category.assesments")%>' />
						<html:form action='/CategoryAssessmentDelete'>
							<html:hidden property="id" value='<%=id%>'/>
							<html:hidden property="list" />
					   		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				      			<tr>
						    		<td class="guide2" width="5%">&nbsp;</td>
							        <td class="guide2" width="95%" align="left"><%=messages.getText("assesment.data.name")%></td>
						        </tr>
<%			if(!assIt.hasNext()) {
%>			      				<tr class="linetwo">
				            		<td colspan="2"><%=messages.getText("generic.messages.notresult")%></td>
        	    				</tr>
<%			}else {
				boolean linetwo = false;
				while(assIt.hasNext()){
	    			AssesmentAttributes assesment = (AssesmentAttributes)assIt.next();
	    			linetwo = !linetwo;
%>	            				<tr class='<%=(linetwo)?"linetwo":"lineone"%>'>
		          	    			<td align="left">
										<input type='checkbox' name='<%=String.valueOf(assesment.getId())%>' value='<%=assesment.getId()%>' >                  
									</td>
		        	      			<td align="left">
	        				        	<%=messages.getText(assesment.getName())%>
									</td>
		    	    			</tr>
<%				}
 			}
%>		      					<tr>
									<td align="right" colspan="3">
						            	<input type="button" class="input" value='<%=messages.getText("generic.messages.delete")%>' onclick="deleteAssessments(document.forms['CategoryAssessmentDeleteForm'], '<%=messages.getText("assessmentcategory.delete.confirm")%>')" />
									</td>
								</tr>
							</table>
						</html:form>
					<jsp:include page="../component/utilitybox2bottom.jsp" />
				</td>
			</tr>
<%		Collection assesments = sys.getAssesmentReportFacade().findAssessmentForGroup(category.getGroupId(), sys.getUserSessionData());
%>			<tr>
				<td colspan="3">
					<jsp:include  page='<%="../component/utilitybox2top.jsp?title="+messages.getText("group.category.availableassesments")%>' />
					<html:form action='/CategoryAssessmentAdd'>
						<html:hidden property="id" value='<%=id%>'/>
						<html:hidden property="list" />
				   		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			      			<tr>
					    		<td class="guide2" width="5%">&nbsp;</td>
						        <td class="guide2" width="95%" align="left"><%=messages.getText("assesment.data.name")%></td>
					        </tr>
<%			if(assesments.size() == 0) {
%>		      				<tr class="linetwo">
			            		<td colspan="5"><%=messages.getText("generic.messages.notresult")%></td>
            				</tr>
<%			}else {
				assIt = assesments.iterator();
				boolean linetwo = false;
				while(assIt.hasNext()){
	    			AssesmentAttributes assesment = (AssesmentAttributes)assIt.next();
%>	            			<tr class='<%=(linetwo)?"linetwo":"lineone"%>'>
<%					linetwo = !linetwo;	
%>          	    			<td align="left">
									<input type='checkbox' name='<%=String.valueOf(assesment.getId())%>' value='<%=assesment.getId()%>' >                  
								</td>
		              			<td align="left">
	        			        	<%=messages.getText(assesment.getName())%>
								</td>
		    	    		</tr>
<%				}
 			}
%>		      				<tr>
					      		<td align="right" colspan="3">
						            <input type="button" class="input" value='<%=messages.getText("generic.messages.add")%>' onclick="assessments(document.forms['CategoryAssessmentAddForm'])" />
					      		</td>
					      	</tr>
						</table>
					</html:form>
					<jsp:include page="../component/utilitybox2bottom.jsp" />
				</td>
			</tr>
	      	<tr>
	      		<td align="right" colspan="3">
		            <input type="button" class="button" value='<%=messages.getText("generic.messages.back")%>' onclick="document.forms['back'].submit()" />
	      		</td>
	      	</tr>
		</table>
	</body>
<%	}	
%>
</html:html>
