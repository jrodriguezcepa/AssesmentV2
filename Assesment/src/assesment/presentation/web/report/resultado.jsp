<%@page language="java"
	errorPage="../exception.jsp"
	import="java.util.*"
	import="assesment.business.*"
	import="assesment.communication.language.*"
	import="assesment.communication.security.*"
	import="assesment.communication.language.tables.*"
	import="assesment.communication.administration.user.tables.*"
	import="assesment.communication.assesment.*"
	import="assesment.presentation.translator.web.util.*"
	import="assesment.presentation.actions.user.*"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html" 
%>

<%@ taglib uri="/WEB-INF/struts-bean.tld"
        prefix="bean" 
%>

<html:html>

<%
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	String check = Util.checkPermission(sys,SecurityConstants.ADMINISTRATOR);
	if(check!=null) {
		response.sendRedirect(request.getContextPath()+check);
	}else {
%>

<LINK REL=StyleSheet HREF="../util/css/estilo.css" TYPE="text/css">

<head/>
	<body bgcolor="#FFFFFF">
		<html:form action="/Resultado">
			<table width="450" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr class="guide1">
					<jsp:include  page='<%="../component/titlecomponent.jsp?title=Resultado"%>' />
		  		</tr>
		      	<tr>
					<jsp:include  page="../component/spaceline.jsp" />
		  		</tr>
			  	<tr>
		    		<td valign="top">
						<jsp:include  page='<%="../component/utilityboxtop.jsp?title=Resultado"%>' />
						<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
					      	<tr  class="linetwo">
					        	<th width="224" align="left"> Usuario<span class="required">*</span></th>
        						<td align="right">
				            		<html:text property="user" size="25" styleClass="input"/>
					          	</td>
					      	</tr>
					      	<tr  class="linetwo">
					        	<th width="224" align="left"> Assesment<span class="required">*</span></th>
        						<td align="right">
				            		<html:text property="assesment" size="25" styleClass="input"/>
					          	</td>
					      	</tr>
					      	<tr  class="linetwo">
					        	<th width="224" align="left"> Resultado<span class="required">*</span></th>
        						<td align="right">
				            		<html:text property="resultado" size="25" styleClass="input"/>
					          	</td>
					      	</tr>
					      	<tr  class="linetwo">
					      		<td colspan="2">
						            <html:submit styleClass="input">
										Aceptar
									</html:submit>
						            <html:cancel styleClass="input">
										Cancelar
									</html:cancel>
          						</td>
      						</tr>
      					</table>
						<jsp:include  page="../component/utilityboxbottom.jsp" />
    				</td>
  				</tr>
			</table>
		</html:form>
	</body>
<%	}	%>
</html:html>
