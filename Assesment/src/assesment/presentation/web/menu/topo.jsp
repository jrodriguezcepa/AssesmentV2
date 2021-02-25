<%@ page language="java"
	import="assesment.business.*"
	import="java.util.*"
	import="assesment.communication.language.Text"
%>
<%!
	AssesmentAccess sys;
	Text messages;
%>
<%
	sys=(AssesmentAccess)session.getAttribute("AssesmentAccess");
	messages=sys.getText();
	
%>
	<table width="100%" height="90" border="0" cellpadding="0" cellspacing="0" id="topo">
		<tr id="top">
  			<td height="90" colspan="2" valign="top">
   				<table width="100%" height="90" border="0" cellpadding="0" cellspacing="0" id="topo">
					<tr>
			        	<td background="./imgs/fondo.jpg" align="left">
			        		<a href="index.jsp">
				        		<img src="./imgs/top_0.gif" height="69" valign="bottom" style="height: 100%">
				        	</a>
			        	</td>
			        	<td background="./imgs/fondo.jpg" align="left">
			        		<a href="index.jsp">
				        		<img src="./imgs/logo.jpg" height="69" valign="bottom">
							</a>
			        	</td>
					</tr>
					<tr>
						<td width="16%" class="bgTop">
							<img src="./imgs/top_1.gif" width="149" height="21">
						</td>
						<td width="100%">
   							<table width="100%" border="0" cellpadding="0" cellspacing="0">
   								<tr>
								    <td width="4%" class="bgTop" valign="middle">
								    	<div id="menu_parent" align="center">
								    		<span class="style3"><%=messages.getText("generic.messages.preferences")%></span>
								    	</div>
								    </td>
								    <td width="25%" class="bgTop" valign="middle">
										<div id="menu_child" style="position: absolute; background: #e2e1e1; visibility: hidden;">
											<a href="javascript:openChangePassword(500,400)" style="display: block; width: 150px; height:25; text-align:center; vertical-align:middle;"  class="clA0">
												<%=messages.getText("generic.user.changepassword")%>
											</a>
											<a style="display: block; width: 150px; height:25; text-align:center; vertical-align:middle;"  class="clA0" href="javascript:openChangeLanguage()">
												<%=messages.getText("generic.user.changelanguage")%>
											</a>
										</div>
										<script type="text/javascript">
											at_attach("menu_parent", "menu_child", "hover", "y", "pointer");
										</script>
									</td>
								    <td width="20%" class="bgTop" valign="middle">
								    	<div id="menu_parent" align="center">
							    			<a href="javascript:document.forms['texts'].submit();">
									    		<span class="style3">
								    				<%=messages.getText("generic.messages.updatetexts")%>
									    		</span>
							    			</a>
								    	</div>
								    </td>
				    				<td width="35%" align="right" class="bgTop"><a href="#"   onClick="javascript:document.forms[0].submit()"><span class="style3" ><%=messages.getText("generic.messages.logout")%></span></a></td>
				    				<td width="5%" align="right" class="bgTop"><span class="style3" >&nbsp;</span></td>
			    				</tr>
			    			</table>
			    		</td>
				  	</tr>
				</table>
			</td>
		</tr>
	</table>