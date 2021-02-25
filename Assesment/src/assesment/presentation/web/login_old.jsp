<%@ page language="java"
	import="assesment.presentation.translator.web.util.*"	
	import="assesment.presentation.translator.web.administration.user.*"
	import="assesment.communication.util.MD5"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>


<html:html>
<%! RequestDispatcher dispatcher; String context; String pathMsg; String pathGarbagecolector;
	String userName; String password; String sessionExpired; String accesscode;
%>

<%	userName="";
	password = "";
	accesscode = "";
	sessionExpired="";
	String body = "";
	context=request.getContextPath();
	pathMsg="/util/jsp/message.jsp";
	pathGarbagecolector="/util/jsp/garbagecolector.jsp";
	System.setProperty("java.security.manager", "assesment"); 
	dispatcher=request.getRequestDispatcher(pathMsg);
	dispatcher.include(request,response);
	
	dispatcher=request.getRequestDispatcher(pathGarbagecolector);
	dispatcher.include(request,response);
	if(!Util.empty(request.getParameter("session"))) {
		sessionExpired=request.getParameter("session");
	}
    if(!Util.empty(request.getParameter("user"))) {
	    userName = request.getParameter("user");
	} 
	if(!Util.empty(request.getParameter("password"))) {
	    password = request.getParameter("password");
	}
	if(!Util.empty(request.getParameter("emea"))) {
		userName = "accesscode";
		password = "accesscode";
		accesscode = request.getParameter("emea");
	}else if(!Util.empty(request.getParameter("login"))) {
		MD5 md5 = new MD5();
		userName = md5.encriptar(request.getParameter("login"));
	    password = md5.encriptar(userName);
	}
	if(!Util.empty(request.getParameter("access"))) {
		userName = "accesscode";
		password = "accesscode";
		accesscode = request.getParameter("access");
	}
	if(!Util.empty(userName) && !Util.empty(password)) {
		body = "onload=enter('"+userName+"','"+password+"')"; 
	}
%>
<script language='javascript'>
	function vopenw() {	
		tbar='location=no,status=yes,resizable=yes,scrollbars=yes,width=560,height=535';
		sw =  window.open('https://www.certisign.com.br/seal/splashcerti.htm','CRSN_Splash',tbar);	
		sw.focus();
	}
</script>

<%	if(!Util.empty(request.getParameter("assessment")) && request.getParameter("assessment").equals("anglo")) {
%>	<head>
		<title> CEPA </title>
		<script>
  			function funcaoBotaoEnter(parametro){
    			javascript:document.forms['loginForm'].submit();      
  			}
		</script>
		<link href='<%=context+"/util/css/estilo.css"%>' rel="stylesheet" type="text/css">
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">
		
		<!--
		body {
			background-color: #FFFFFF;
		}
		-->
		</style>
	</head>
	<body <%=body%> >
		<form name="loginForm" method="post" action="j_security_check" onSubmit="return enc()" onKeyPress=>
			<input type="hidden" name="assessment" value='<%=request.getParameter("assessment")%>'/>
			<table width="990" height="594" border="0" align="center" cellpadding="0" cellspacing="0"  class="tablereloadAnglo">
			  	<tr>
    				<td height="180" colspan="3">&nbsp;</td>
	  			</tr>
  				<tr height="50">
    				<td width="350">&nbsp;</td>
    				<th align="left" valign="top" width="140" height="50">
    					Usu&aacute;rio:
    				</th>
    				<td align="right" valign="top" width="150">
    					<input type="text" name="j_username" class="input" onKeyPress="if ((window.event ? event.keyCode : event.which) == 13) { funcaoBotaoEnter(this.value); }" style="height: 20px; width: 127px; vertical-align: middle; font-size: 12; " />
    				</td>
    				<td width="350">&nbsp;</td>
  				</tr>
  				<tr height="50">
    				<td width="350">&nbsp;</td>
    				<th align="left" valign="top" width="50">
    					Senha:
    				</th>
    				<td align="right" valign="top" width="150">
    					<input type="password" name="j_password" class="input" onKeyPress="if ((window.event ? event.keyCode : event.which) == 13) { funcaoBotaoEnter(this.value); }" style="height: 20px; width: 127px;  vertical-align: middle; font-size: 12;"  />
    				</td>
    				<td width="350">&nbsp;</td>
  				</tr>
   				<tr height="34">
    				<td colspan="2">
    					&nbsp;
    				</td>
    				<td height="28" align="right" valign="top" width="150">
						<input type="submit" value="Login" align="middle" class="cursor">
    				</td>
    				<td>&nbsp;</td>
  				</tr>
			  	<tr>
    				<td height="180" colspan="3">&nbsp;</td>
	  			</tr>
  			</table>
		</form>
	</body>		
<%	
	}else {
%>	
	<head>
		<title> CEPA </title>

		<script>
      		function funcaoBotaoEnter(parametro){
        		javascript:document.forms['loginForm'].submit();      
      		}
      		function sub(){
	      		document.forms['loginForm'].j_username.style.display = 'none';
	      		document.forms['loginForm'].j_password.style.display = 'none';
	      		document.forms['loginForm'].username2.style.display = 'block';
	      		document.forms['loginForm'].password2.style.display = 'block';
	      		document.forms['loginForm'].j_username.value = 'accesscode';
	      		document.forms['loginForm'].j_password.value = 'accesscode';
        		javascript:document.forms['loginForm'].submit();      
      		}
      		function enter(user,password){
	      		document.forms['loginForm'].j_username.value = user;
	      		document.forms['loginForm'].j_password.value = password;
        		javascript:document.forms['loginForm'].submit();      
      		}
		</script>
		<script>
			if (parent.frames.length)
				parent.location.href= self.location;
		</script>
		
		<link href='<%=context+"/util/css/estilo.css"%>' rel="stylesheet" type="text/css">
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">
		
		<!--
		body {
			background-color: #000000;
		}
		-->
		</style>
	</head>
	<body <%=body%> >
		<form name="loginForm" method="post" action="j_security_check" onSubmit="return enc()" onKeyPress=>
			<input type="hidden" name="accesscode" value='<%=accesscode%>'/>		
<%		if(Util.empty(userName) && Util.empty(password)) {
%>			<table width="990" height="594" border="0" align="center" cellpadding="0" cellspacing="0"  class="tablelogin2">
			  	<tr>
    				<td height="100" colspan="3">&nbsp;</td>
	  			</tr>
  				<tr>
    				<td height="183" width="608">
    					&nbsp;
    				</td>
    				<td height="183" valign="bottom">
    					<input type="text" name="j_username" class="input" onKeyPress="if ((window.event ? event.keyCode : event.which) == 13) { funcaoBotaoEnter(this.value); }" style="border: none; height: 20px; width: 127px; vertical-align: middle; font-size: 12; " />
    					<input type="text" name="username2" class="input" style="border: none; height: 21px; width: 127px;  vertical-align: middle; display:none;"/>
    				</td>
  				</tr>
  				<tr>
    				<td height="25" width="608">
    					&nbsp;
    				</td>
    				<td valign="bottom">
    					<input type="password" name="j_password" class="input" onKeyPress="if ((window.event ? event.keyCode : event.which) == 13) { funcaoBotaoEnter(this.value); }" style="border: none; height: 20px; width: 127px;  vertical-align: middle; font-size: 12;"  />
    					<input type="password" name="password2" class="input" style="border: none; height: 21px; width: 127px;  vertical-align: middle; display:none;">
    				</td>
  				</tr>
   				<tr>
    				<td height="28" width="600">
    					&nbsp;
    				</td>
    				<td height="28" align="left" valign="bottom">
						<img src='/assesment/util/imgs/botlogin.jpg' border=0 align=center onclick="javascript:document.forms[0].submit()" class="cursor">
    				</td>
  				</tr>
   				<tr>
    				<td height="26" width="593"></td>
    			</tr>
   				<tr>
    				<td height="41" colspan="2" align="center" valign="bottom">
    					<table width="100%" border="0">
			  				<tr height="100%">
			    				<td width="382">
			    					&nbsp;
			    				</td>
			    				<td valign="bottom" align="left">
			    					<img src='/assesment/util/imgs/botaccesscode.jpg' border=0 align=center onclick="javascript:sub()" class="cursor" >
			    				</td>	
			    			</tr>
    					</table>
    				</td>
  				</tr>
   				<tr>
    				<td height="20" width="593"></td>
    			</tr>
   				<tr>
    				<td height="26" width="593">
    					<table border="0">
    						<tr>
    							<td width="238" >&nbsp;</td>
    							<td valign="top">
			   						<img src='/assesment/util/imgs/secure.jpg' border=0 align=center onclick="javascript:vopenw()" class="cursor" onclick="javascript:vopenw()" >
								</td>
							</tr>
    					</table>
    				</td>
    				<td height="26" align="left" valign="bottom">
    					&nbsp;
    				</td>
  				</tr>
  				<!-- tr>
    				<td height="25" colspan="3" align="center" class="msglogin">
    					<div align="center">
    					<table width="62%">
    						<tr>
    							<td valign="top" align="left" class="msglogin" width="31%">
    						Se voce esqueceu sua password ou deseja ativa-la usando a ferramenta de Primeiro Acesso, <a href="javascript:sub()" ><strong><u>clique aqui</u></strong></a><u>.</u>
    							</td>
    							<td  width="2%"></td>
    							<td valign="top" align="left" class="msglogin" width="29%">
    						If you forgot your password or have received instructions to activate your password using First Access Tool <a href="javascript:sub()" ><strong><u>click here</u></strong></a><u>.</u>
    							</td>
    						</tr>
    					</table>
          				</div>
          			</td>
  				</tr-->
  				
<%			if (sessionExpired.equals("1")){
%>				<tr>
					<td height="25" colspan="3" align="center" class="msglogin">
    					<div align="center">
    						<table width="75%">
    							<tr><td colspan="3" height="5"></td></tr>
    							<tr>
  									<td colspan="3" align="center" class="msglogin"><FONT COLOR="red"><strong>Su Sesi�n ah Caducado. Por Favor Ingrese Nuevamente su Nombre de Usuario y Contrase�a si Desea Ingresar al Sisitema.</strong></FONT></td>
  								</tr>
  								<tr>
  									<td colspan="3" align="center" class="msglogin"><FONT COLOR="red"><strong>Your Session has Expired. Please Input Your Username and Password Again if you Would Like to Enter the System.</strong></FONT></td>
  								</tr>	
  							</table>
  						</div>
  					</td>
  				</tr>
<% 			} 
		}else {
%>			<table width="990" height="594" border="0" align="center" cellpadding="0" cellspacing="0" >
			  	<tr>
    				<td height="100" colspan="3">&nbsp;</td>
	  			</tr>
  				<tr>
    				<td width="352"height="24" align="right" class="txtlogin">Loading</td>
    				<td width="5">
		    			<input type="hidden" name="j_username"/>
		  				<input type="hidden" name="j_password"/>
    				</td>
    				<td width="352">&nbsp;</td>
  				</tr>
  				<tr>
    				<td height="25" colspan="3" align="center" class="msglogin">
    				</td>
    			</tr>
<%		}
%>  				
  				<tr>
    				<td height="100" colspan="3"></td>
  				</tr>
  				<tr>
    				<td colspan="3" align="center" class="txtloginBig">
    					&nbsp;
   					</td>
  				</tr>
			</table>
		</form>
	</body>
<%	}
%>	
</html:html>


