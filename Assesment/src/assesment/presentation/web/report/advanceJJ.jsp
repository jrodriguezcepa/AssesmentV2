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
		session.setAttribute("format",format);

		Collection type = new LinkedList();
		type.add(new OptionItem(messages.getText("report.advance.jj.all"),"all"));	    
		type.add(new OptionItem("report.advance.jj.champion","champion"));	    
		session.setAttribute("types",type);
%>
	<LINK REL=StyleSheet HREF="../util/css/estilo.css" TYPE="text/css">
		
<head/>

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
				<html:form action="/AdvanceJJ">
		    		<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<th align="left">
								<span class="input"><%=messages.getText("report.advance.jj.type")%></span>
							</th>
							<td align="right">
      							<html:select property="type" styleClass="input">
	      							<html:options collection="types" property="value" labelProperty="label" styleClass="input"/>
		  						</html:select>   
							</td>
						</tr>
   						<tr  class="space"><th align="right">&nbsp;</th><td align="right">&nbsp;</td></tr>
						<tr>
							<td colspan="2">
								<jsp:include  page='<%="../component/utilityboxtop.jsp?title="+messages.getText("report.advancejj.champions")%>' />
					    		<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr class="linetwo">
										<td align="left">
											<html:radio property="champion" value="SRomero@its.jnj.com">Santiago Romero</html:radio>											
										</td>
									</tr>
									<tr class="linetwo">
										<td align="left">
											<html:radio property="champion" value="Jtota@its.jnj.com">Jackson Tota</html:radio>
										</td>
									</tr>
									<tr class="linetwo">
										<td align="left">
											<html:radio property="champion" value="SRolon@its.jnj.com">Steven  Rolon</html:radio>
										</td>
									</tr>
									<tr class="linetwo">
										<td align="left">
											<html:radio property="champion" value="jaltaful@its.jnj.com">Julio Altafulla</html:radio>
										</td>
									</tr>
									<tr class="linetwo">
										<td align="left">
											<html:radio property="champion" value="JCRUZ81@its.jnj.com">Jaime Cruz</html:radio>
										</td>
									</tr>
									<tr class="linetwo">
										<td align="left">
											<html:radio property="champion" value="AOWEN2@its.jnj.com">Arthur Owen</html:radio>
										</td>
									</tr>
									<tr class="linetwo">
										<td align="left">
											<html:radio property="champion" value="LCardo@its.jnj.com">Luis Cardo</html:radio>
										</td>
									</tr>
									<tr class="linetwo">
										<td align="left">
											<html:radio property="champion" value="MLozano2@JNJVE.JNJ.com">Mario Lozano</html:radio>
										</td>
									</tr>
									<tr class="linetwo">
										<td align="left">
											<html:radio property="champion" value="gmarrese@its.jnj.com">Guillermo Marrese</html:radio>
										</td>
									</tr>
									<tr class="linetwo">
										<td align="left">
											<html:radio property="champion" value="SCALELLO@its.jnj.com">Silvia  Calello</html:radio>
										</td>
									</tr>
									<tr class="linetwo">
										<td align="left">
											<html:radio property="champion" value="AMANZANO@its.jnj.com">Antonio Manzano</html:radio>
										</td>
									</tr>
									<tr class="linetwo">
										<td align="left">
											<html:radio property="champion" value="GAGUILER@its.jnj.com">Greicy Aguilera</html:radio>
										</td>
									</tr>
									<tr class="linetwo">
										<td align="left">
											<html:radio property="champion" value="LCISNER2@its.jnj.com">Luis Cisneros</html:radio>
										</td>
									</tr>
									<tr class="linetwo">
										<td align="left">
											<html:radio property="champion" value="lafrabot@its.jnj.com">Luisa Ana Frabotta</html:radio>
										</td>
									</tr>
									<tr class="linetwo">
										<td align="left">
											<html:radio property="champion" value="ACASTRO@its.jnj.com">Alberto Castro</html:radio>
										</td>
									</tr>
									<tr class="linetwo">
										<td align="left">
											<html:radio property="champion" value="jyung1@its.jnj.com">Jose Yung</html:radio>
										</td>
									</tr>
									<tr class="linetwo">
										<td align="left">
											<html:radio property="champion" value="MBENITE1@its.jnj.com">Maria  Benitez</html:radio>
										</td>
									</tr>
									<tr class="linetwo">
										<td align="left">
											<html:radio property="champion" value="famartin@its.jnj.com">Francisco Martinez</html:radio>
										</td>
									</tr>
									<tr class="linetwo">
										<td align="left">
											<html:radio property="champion" value="JFERNAN7@its.jnj.com">Eduardo Fernandes</html:radio>
										</td>
									</tr>
									<tr class="linetwo">
										<td align="left">
											<html:radio property="champion" value="dsanto18@its.jnj.com">Daniele Santos</html:radio>
										</td>
									</tr>
								</table>	
								<jsp:include  page="../component/utilityboxbottom.jsp" />
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
