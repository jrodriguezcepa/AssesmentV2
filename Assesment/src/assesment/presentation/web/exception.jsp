<%@ page language="java" isErrorPage="true"
	import="assesment.business.*"
	import="assesment.communication.language.*"
	import="assesment.communication.exception.*"
	import=" presentation.translator.web.util.*"
%>
<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>
<%!
	Text messages;
	AssesmentAccess sys;
	String msg;	String key;
	Exception systemException;
%>
<%
	sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	if(sys != null) {
		messages = sys.getText();

		systemException = (request.getAttribute("exception")!=null) ? (Exception)request.getAttribute("exception") : null;
		if(systemException!=null) {
			if((systemException.getCause()!=null && systemException.getCause().toString().indexOf("SecurityException") > 0) ||
 	           (systemException.getClass()!=null && systemException.getClass().getName().toString().indexOf("SecurityException") > 0)) {
				response.sendRedirect(request.getContextPath()+"/insufficientpermissions.jsp");
			}
		}else {
		    if(exception!=null) {
				if((exception.getCause()!=null && exception.getCause().toString().indexOf("SecurityException") > 0) ||
				   (exception.getClass()!=null && exception.getClass().getName().toString().indexOf("SecurityException")>0)) {
					response.sendRedirect(request.getContextPath()+"/insufficientpermissions.jsp");
				}
			}
		}
		Throwable thw = exception;
		if(exception != null) {
		    while(thw.getCause() != null) {
			    thw = exception.getCause();
		    }
		}
		if(systemException == null && thw instanceof Exception) {
		    systemException = (Exception)thw;
		}    
%>

<html>
	<head>
		<title> CEPA </title>
		<link href="./util/css/estilo.css" rel="stylesheet" type="text/css">
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css"/>
	</head>

	<script type="text/javascript" src='<%="./util/js/uiframework.js"%>'>
	</script>

	<body onload="ui.loading();">
		<div id="loading">
			<%=messages.getText("generic.messages.loading")%>... 
		</div>
		<div id="source">
				<table width="500"  border="0" align="center" cellpadding="0" cellspacing="0">
  					<tr>
    					<td width="100%" class="guide1"><%=messages.getText("generic.messages.exception")%></td>
    				</tr>
    				<tr height="70px">
    					<td width="100%" class="lineone">
    						<%=messages.getText("generic.messages.senderror")%>
    					</td>
    				</tr>
    				<tr>	
    					<td width="100%" class="lineone">&nbsp;</td>
    				</tr>
    				<tr>
    					<td height="10px" width="100%" class="lineone" align="left">
    						<%=messages.getText("generic.exception.description")%>
    					</td> 
    				</tr>
    				<tr>	
    					<td width="100%" align="left" class="lineone">
    					</td>
    				</tr>
    				<tr>	
    					<td width="100%" align="right" class="lineone">
    					</td>
    				</tr>
	    			<tr>	
    				    <td height="10" width="100%" class="lineone">&nbsp;</td>
    				</tr>
    				<tr>	
    				 	<td width="100%" align="left" class="lineone" align="left">																					
    			 			<a id="open" href="#" onClick="document.getElementById('more').style.display='block';this.style.display='none';document.getElementById('close').style.display='block';ui.reziseIFrame();"> 
								<%=messages.getText("generic.exception.showdetail")%>
	    			 		</a>	
    				 		<a id="close" style="display:none" href="#" onClick="document.getElementById('tmore').border='0';document.getElementById('more').style.display='none';this.style.display='none';document.getElementById('open').style.display='block';ui.reziseIFrame();"> 
								<%=messages.getText("generic.exception.hidedetail")%>
    				 		</a>
    				 	</td>
    				 </tr>
    				<tr>	
    				 	<td width="100%" align="left" class="lineone" align="left">																					
   							<div id="more" style="align:left;display:none">
	    						<table id="tmore" width="100%">
<%		
		if(systemException instanceof AssesmentException) {
%>  	  							<tr>
    									<td class="guide1" align="left">
											<%=((AssesmentException)systemException).getName()%>
										</td>
									</tr>
	  	  							<tr>
    									<td class="guide2" align="left">
											<%=messages.getText(((AssesmentException)systemException).getKey())%>
										</td>
									</tr>
  	  								<tr>
    									<td class="guide2" align="left">
											<%=messages.getText("generic.exception.systemmessage")+": "+((AssesmentException)systemException).getSystemMessage()%>
										</td>
									</tr>
<%		}
%>		  							<tr>
    									<td class="lineone" >&nbsp;</td>
									</tr>
		  							<tr>
    									<td class="lineone" >
											<%=messages.getText("generic.exception.stacktrace")%>
										</td>
									</tr>
			 						<tr>
										<td class="lineone">   					
											<TEXTAREA name="exception" name="body" cols="90" rows="8" readonly="readonly" class="input">
<%	  	if(systemException != null){
			if(systemException.getStackTrace() != null) {
				for(int i = 0; i < systemException.getStackTrace().length; i++){
%>                          	                <%=systemException.getStackTrace()[i].getClassName() + " " +
													systemException.getStackTrace()[i].getMethodName() + " " +
                                    	        	systemException.getStackTrace()[i].getLineNumber() %>
<%		  		}
		  	}
		}
%>		    								</TEXTAREA>	
		    							</td>	
		    						</tr>	
	    						</table>
	    					</div>
    					</td>
	    			</tr>
  				</table>
		</div>		
	</body>
</html>
<%
	}
%>