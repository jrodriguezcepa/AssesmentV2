<%@page import="assesment.communication.util.MD5"%>
<%@page import="assesment.communication.assesment.GroupData"%>
<%@page language="java"
	import="assesment.business.*"
	import="assesment.business.administration.user.*"
	import="assesment.communication.language.*"
	import="assesment.communication.administration.user.tables.*"
	import="assesment.presentation.translator.web.user.*"
	import="assesment.communication.language.tables.*"
	import="assesment.presentation.translator.web.util.Util"
	import="assesment.communication.administration.corporation.tables.*"
	import="java.util.*"
 	import="assesment.communication.administration.user.*" 		
 	import="assesment.presentation.translator.web.util.*"
 	import="assesment.communication.security.*"
	import="assesment.communication.assesment.AssesmentData"
	import="assesment.communication.user.UserData"
	import="assesment.communication.administration.AccessCodeData"
	errorPage="../exception.jsp"
%>
<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>

<%!
	RequestDispatcher dispatcher; String pathMsg;
	Text messages; String pathLanguage; 
	AssesmentAccess sys;
%>
<%
	String area = "resume.jsp";
	Enumeration names = request.getHeaderNames();
	while(names.hasMoreElements()) {
		String name = (String)names.nextElement();
		System.out.print(name+" "+request.getHeader(name));		
	}
	String ua = request.getHeader("User-Agent");
	boolean isMSIE = false;//(ua != null && (ua.indexOf("MSIE") != -1 || ua.indexOf("Trident") != -1));
	
	String user = request.getUserPrincipal().getName();
	String accessCode = null;
	AssesmentAccess sys = null;
	if(session.getAttribute("AssesmentAccess") != null) {
		sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	}else {
		sys = (Util.empty(request.getParameter("assessment"))) ? new AssesmentAccess(user) : new AssesmentAccess(user, new Integer(request.getParameter("assessment")));
	}
	UsReportFacade userReport = sys.getUserReportFacade();
 	UserSessionData userSession=sys.getUserSessionData(); 
	String login = sys.getUserSessionData().getFilter().getLoginName();
	
	if(request.getUserPrincipal().getName().equals("accesscode") && request.getParameter("assessment") != null && request.getParameter("telematics") != null) {
		Collection codes = userReport.getUserList("telematics_%_"+request.getParameter("telematics"),userSession);
		int max = 0;
		Iterator it = codes.iterator();
		while(it.hasNext()) {
			String userLogin = (String)it.next();
			if(userLogin.startsWith("telematics_") && userLogin.endsWith("_"+request.getParameter("telematics"))) {
				int v = Integer.parseInt(userLogin.replace("telematics_", "").replace("_"+request.getParameter("telematics"), ""));
				if(v > max)
					max = v;
			}
		}
		String userName = "telematics_"+String.valueOf(max+1)+"_"+request.getParameter("telematics");
		UserData userData = new UserData(userName,userName,"Telematics","Coach","es","telematics@cepasafedrive.com","systemaccess",null);
		userData.setTelematics(new Integer(request.getParameter("telematics")));
		sys.getUserABMFacade().userCreateFromAC(userData,new Integer(request.getParameter("assessment")),"telematics_"+request.getParameter("assessment"),userSession);
		new LogoutAction().logout(request);
		response.sendRedirect("index.jsp?user="+userName+"&password="+userName);
	} else if(request.getUserPrincipal().getName().equals("generate") && request.getParameter("generate") != null) {
		String accesscode = "generate_"+request.getParameter("generate");
		AccessCodeData accessCodeData = sys.getAssesmentReportFacade().getAccessCode(accesscode, userSession);
		Integer code = userReport.getNextCode(accesscode,userSession);
		code = (code == null) ? 1 : code + 1;
		String userName = "generate_"+code+"_"+request.getParameter("generate");
		UserData userData = new UserData(userName,userName,"Survey","CEPA",accessCodeData.getLanguage(),"cepasafedrive@gmail.com","systemaccess",null);
		sys.getUserABMFacade().userCreateFromAC(userData,new Integer(request.getParameter("generate")),accesscode,userSession);
		new LogoutAction().logout(request);
		response.sendRedirect("index.jsp?user="+userName+"&password="+userName);
	} else if(request.getUserPrincipal().getName().startsWith("ggenerate_") && request.getParameter("groupac") != null) {
		String lng = request.getParameter("lng");
		String accesscode = "ggenerate_"+lng+"_"+request.getParameter("groupac");
		AccessCodeData accessCodeData = sys.getAssesmentReportFacade().getAccessCode(accesscode, userSession);
		Integer code = userReport.getNextCode(accesscode,userSession);
		code = (code == null) ? 1 : code + 1;
		String userName = "ggenerate_"+code+"_"+lng+"_"+request.getParameter("groupac");
		UserData userData = new UserData(userName,userName,"User","group",lng,"cepasafedrive@gmail.com",SecurityConstants.GROUP_ASSESSMENT,null);
		sys.getUserABMFacade().userGroupCreate(userData,new Integer(request.getParameter("groupac")),accesscode,userSession);
		new LogoutAction().logout(request);
		response.sendRedirect("index.jsp?user="+userName+"&password="+userName);
	} else if(request.getUserPrincipal().getName().startsWith("generate_") && request.getParameter("g") != null) {
		UserData userAC = sys.getUserReportFacade().findUserByPrimaryKey(login,sys.getUserSessionData());
		String accesscode = (request.getParameter("lng") != null) ? "generate_"+request.getParameter("lng")+"_"+request.getParameter("g") : "generate_"+request.getParameter("g");
		UserData userData = null;
		AccessCodeData accessCodeData = sys.getAssesmentReportFacade().getAccessCode(accesscode, userSession);
		Integer code = userReport.getNextCode(accesscode,userSession);
		code = (code == null) ? 1 : code + 1;
		String userName = (request.getParameter("lng") != null) ? "generate_"+request.getParameter("lng")+"_"+code+"_"+request.getParameter("g") : "generate_"+code+"_"+request.getParameter("g");
		userData = new UserData(userName,userName,userAC.getFirstName(),userAC.getLastName(),accessCodeData.getLanguage(),"cepasafedrive@gmail.com","systemaccess",null);
		if(request.getParameter("c") != null) {
			userData.setExtraData3(request.getParameter("c").trim());
		}
		sys.getUserABMFacade().userCreateFromAC(userData,new Integer(request.getParameter("g")),accesscode,userSession);
		new LogoutAction().logout(request);
		response.sendRedirect("index.jsp?user="+userName+"&password="+userName);
	} else if(request.getUserPrincipal().getName().startsWith("epdemo") && request.getParameter("ep") != null) {
		String accesscode = "epdemo_"+request.getParameter("ep")+request.getParameter("l");
		System.out.println(accesscode);
		AccessCodeData accessCodeData = sys.getAssesmentReportFacade().getAccessCode(accesscode, userSession);
		Integer code = userReport.getNextCode(accesscode,userSession);
		code = (code == null) ? 1 : code + 1;
		String userName = "demoep"+code+"_"+request.getParameter("ep")+request.getParameter("l");
		UserData userData = new UserData(userName,userName,"Demo","CEPA",accessCodeData.getLanguage(),"cepasafedrive@gmail.com","systemaccess",null);
		sys.getUserABMFacade().userCreateFromAC(userData,new Integer(request.getParameter("ep")),accesscode,userSession);
		new LogoutAction().logout(request);
		response.sendRedirect("index.jsp?user="+userName+"&password="+userName);
	} else if(request.getUserPrincipal().getName().startsWith("ab959f91d46e0cc688c0034e28916602") && request.getParameter("marsh") != null) {
		String accesscode = "marsh2019";
		AccessCodeData accessCodeData = sys.getAssesmentReportFacade().getAccessCode(accesscode, userSession);
		Integer code = userReport.getNextCode(accesscode,userSession);
		code = (code == null) ? 1 : code + 1;
		String userName = "marsh2019_"+code;
		UserData userData = new UserData(userName,userName,"Usuario","Marsh",accessCodeData.getLanguage(),"cepasafedrive@gmail.com","multiassessment",null);
		sys.getUserABMFacade().userCreateFromAC(userData,new int[]{502,503,504},accesscode,userSession);
		new LogoutAction().logout(request);
		response.sendRedirect("index.jsp?user="+userName+"&password="+userName);
	}else {
		session.setAttribute("AssesmentAccess", sys);
		long date = System.currentTimeMillis();
		String role = userSession.getRole();
		if(role.equals(SecurityConstants.ACCESSCODE)) {
			if(!Util.empty(request.getParameter("emea"))) {
				response.sendRedirect("./emeaLanguage.jsp?accesscode="+request.getParameter("emea"));
			} else if(!Util.empty(request.getParameter("ache"))) {
				String email = request.getParameter("ache");
				String hash = request.getParameter("hash");
				String firstName = request.getParameter("primnome");
				String lastName = request.getParameter("sobrenome");
				String extraData = request.getParameter("login");
				MD5 md5 = new MD5();
				switch(Util.validateAcheHash(email, hash, sys)) {
					case 1:
			            session.setAttribute("user",email.trim().toLowerCase());
			            session.setAttribute("password", md5.encriptar(hash));
						response.sendRedirect("./relogin.jsp");
						break;
					case 2:
						UserData data = new UserData(email.trim().toLowerCase(),md5.encriptar(hash),firstName,lastName,"pt",email.trim().toLowerCase(),SecurityConstants.GROUP_ASSESSMENT,null);
			            data.setBirthDate(Calendar.getInstance().getTime());
			            data.setSex(new Integer(2));
			            data.setCountry(new Integer(32));
			            data.setExtraData(extraData);
			            sys.getUserABMFacade().userGroupCreate(data,new Integer(GroupData.ACHE),sys.getUserSessionData());
			            session.setAttribute("user",email.trim().toLowerCase());
			            session.setAttribute("password",md5.encriptar(hash));
						response.sendRedirect("./relogin.jsp");
						break;
					default:
						response.sendRedirect("./errorAche.jsp");
						break;
				}
			} else if(!Util.empty(request.getParameter("group"))) {
				int groupId = Integer.parseInt(request.getParameter("group"));
				if(groupId == GroupData.MERCADOLIBRE) {	
					response.sendRedirect("./mlb.jsp?group="+groupId);
				} else if(groupId == GroupData.MERCADOLIVRE) {	
					response.sendRedirect("./mlv.jsp?group="+groupId);
				} else if(groupId == GroupData.BAT) {	
					response.sendRedirect("./bat.jsp");
				}
			} else if(!Util.empty(request.getParameter("access"))) {
				AccessCodeData accessCodeData = sys.getAssesmentReportFacade().getAccessCode(request.getParameter("access"),userSession);
				switch(accessCodeData.getAssesment().intValue()) {
					case AssesmentData.ABITAB:
						response.sendRedirect("./userDataAbitab.jsp?accesscode="+request.getParameter("access"));
						break;
					case AssesmentData.ASTRAZENECA:
						response.sendRedirect("./userDataAZ.jsp?accesscode="+request.getParameter("access"));
						break;
					case AssesmentData.ANGLO:case AssesmentData.ANGLO_3:
						response.sendRedirect("./userDataAnglo.jsp?accesscode="+request.getParameter("access"));
						break;
					case AssesmentData.IMESEVI:
						response.sendRedirect("./userDataImesevi.jsp?accesscode="+request.getParameter("access"));
						break;
				}
			}else {
				response.sendRedirect("./ac.jsp");
			}
		}else if(role.equals(SecurityConstants.WEBINAR)) {
			response.sendRedirect("./webinar.jsp");
		}else if(login.equals(SecurityConstants.ALRIYADAH_INITIALA)) {
			response.sendRedirect("./alriyadahFinal.jsp?type=1");
		}else if(login.equals(SecurityConstants.ALRIYADAH_INITIALB)) {
			response.sendRedirect("./alriyadahFinal.jsp?type=2");
		}else if(login.equals(SecurityConstants.ALRIYADAH_FINAL)) {
			response.sendRedirect("./alriyadahFinal.jsp?type=3");
		}else if(login.equals(SecurityConstants.WEBINAR_FORM)) {
			response.sendRedirect("./webinarLang.jsp");
		}else if(login.equals(UserData.VEIBRAS)) {
			response.sendRedirect("./veibras.jsp");
		}else if(login.equals(UserData.TIMAC)) {
			response.sendRedirect("./timac.jsp");
		}else if(login.equals(UserData.DAIMLER)) {
			response.sendRedirect("./daimler.jsp");
		}else if(login.equals(UserData.GRUPO_MODELO)) {
			response.sendRedirect("./grupom_code.jsp");
		}else if(login.equals(UserData.MUTUAL)) {
			response.sendRedirect("./mutual.jsp");
		}else if(login.equals(UserData.HABILITARUPM)) {
			response.sendRedirect("./habilitar_upm.jsp");
		}else if(login.equals(UserData.REGISTROUPM)) {
			response.sendRedirect("./register_upm.jsp");
		}else if(login.equals(UserData.CHARLAUPM)) {
			response.sendRedirect("./charla_upm.jsp");
		}else if(login.equals(UserData.REGISTROMDP)) {
			response.sendRedirect("./register_mdp.jsp");
		}else if(login.equals(UserData.CHARLAMDP)) {
			response.sendRedirect("./charla_mdp.jsp");
		}else if(login.equals(UserData.REGISTROLUMIN)) {
			response.sendRedirect("./register_lumin.jsp");
		}else if(login.equals(UserData.CHARLALUMIN)) {
			response.sendRedirect("./charla_lumin.jsp");
		}else if(role.equals(SecurityConstants.CLIENTGROUP_REPORTER)) {
			GroupData group = sys.getAssesmentReportFacade().getUserGroup(login,userSession);
			if(group.getId().equals(GroupData.GRUPO_MODELO)) {
				Integer[] cedis = sys.getCorporationReportFacade().findCediUser(login, sys.getUserSessionData());
				if(cedis.length == 1) {
					response.sendRedirect("./reportcedi.jsp?cedi="+cedis[0]);
				} else {
					response.sendRedirect("./reportgrupomodelo.jsp");
				}
			}else if(group.getId().equals(GroupData.GUINEZ_ADMINISTRACION) || group.getId().equals(GroupData.GUINEZ_FAENA)){
				response.sendRedirect("./report.jsp?id="+AssesmentData.GUINEZ_INGENIERIA+"&group="+group.getId());
			}else {
				response.sendRedirect("./reportgroup.jsp");
			}
		}else if(role.equals(SecurityConstants.CLIENT_REPORTER)) {
			Collection assessments = userReport.findUserReportAssesments(user,userSession);
			switch(assessments.size()) {
				case 0:
					response.sendRedirect("./noassessment.jsp");
					break;
				case 1:
					Integer aId = ((AssesmentAttributes)assessments.iterator().next()).getId();
					String link = (aId.intValue() == AssesmentData.FRAYLOG_FATIGA) ? "reportmultianswer.jsp?id="+aId : "report.jsp?id="+aId;
					response.sendRedirect("./"+link);
					break;
				default:
					response.sendRedirect("./report_assessment.jsp");
					break;
			}
		}else if(role.equals(SecurityConstants.CEPA_REPORTER)) {
			response.sendRedirect("./report_assessment.jsp");
		}else if(role.equals(SecurityConstants.GROUP_ASSESSMENT)) {
			GroupData group = sys.getAssesmentReportFacade().getUserGroup(login,userSession);
			sys.getUserSessionData().getFilter().setGroup(group.getId());
			int terms = userReport.terms(login, sys.getUserSessionData()).intValue();
			System.out.println("terms "+terms);
			if(terms > 0) {
				if(group.getLayout().intValue() == GroupData.GROUP) {
					response.sendRedirect("termsgroup_"+terms+".jsp");
				}else {
					response.sendRedirect("terms_"+terms+".jsp");
				}
			}else {
				if(group.getLayout().intValue() == GroupData.GROUP) {
					int groupId = group.getId().intValue();
					if(groupId == GroupData.MERCADOLIBRE) {
						if(userReport.isAssessmentDone(login,new Integer(AssesmentData.MERCADO_LIBRE_START),sys.getUserSessionData(),false)) {
							response.sendRedirect("./mercadolibre.jsp");
						} else {
							response.sendRedirect("./newmodule.jsp?id="+AssesmentData.MERCADO_LIBRE_START);
						}
					} else if(groupId == GroupData.MERCADOLIVRE) {
						if(userReport.isAssessmentDone(login,new Integer(AssesmentData.MERCADO_LIVRE_START),sys.getUserSessionData(),false)) {
							response.sendRedirect("./mercadolibre.jsp");
						} else {
							response.sendRedirect("./newmodule.jsp?id="+AssesmentData.MERCADO_LIVRE_START);
						}
					} else if(groupId == GroupData.DAIMLER) {
						UserData userData = sys.getUserReportFacade().findUserByPrimaryKey(login, sys.getUserSessionData());
						if(userData.getFirstName().equals("Usuario") && userData.getLastName().equals("Daimler")) {
							response.sendRedirect("./registroDaimler.jsp");
						}else {
							response.sendRedirect("./group.jsp");
						}
					} else {
						response.sendRedirect("./group.jsp");
					}
				}else {
					response.sendRedirect("./select_da.jsp");
				}
			}
		}else if(role.equals(SecurityConstants.PEPSICO_CANDIDATOS)) {
			response.sendRedirect("./candidatos.jsp");
		}else if(role.equals(SecurityConstants.BASF)) {
			response.sendRedirect("./basf.jsp");
		}else if(role.equals(SecurityConstants.MULTI_ASSESSMENT) && sys.getUserSessionData().getFilter().getAssesment() == null) {
			sys.getUserABMFacade().saveLogin(date,userSession);
			int terms = userReport.terms(login, sys.getUserSessionData()).intValue();
			System.out.println("terms "+terms);
			if(terms > 0) {
				response.sendRedirect("terms_"+terms+".jsp");
			}else {
				if(sys.getLoginUser().startsWith("marsh2019_")) {
			    	response.sendRedirect("./marsh.jsp");				
				}else {
					Collection assessments = userReport.findPendingUserAssesments(login,userSession);
					switch(assessments.size()) {
						case 0:
					    	response.sendRedirect("./noassessment.jsp");
					    	break;
						case 1:
							AssesmentAttributes assesmentA = (AssesmentAttributes)assessments.iterator().next();
							AssesmentData assesment = sys.getAssesmentReportFacade().findAssesment(assesmentA.getId(), userSession);
							userSession.getFilter().setAssessmentData(assesment);
							Iterator it = assesment.getModuleIterator();
							while(it.hasNext()) {
								ModuleData module = (ModuleData)it.next();
								module.setAnswered(userReport.getQuestionCount(user,module.getId(),userSession.getFilter().getAssesment(),userSession));
							}
							if(assesment.isPsitest()) {
								assesment.setPsiCount(userReport.getQuestionCount(user,new Integer(0),userSession.getFilter().getAssesment(),userSession));
							}
					    	response.sendRedirect("./module_da.jsp");
					    	//response.sendRedirect("./newmodule.jsp?id="+assesmentA.getId());
					    	break;
						default:
					    	response.sendRedirect("./select_da.jsp");
					    	break;
					}
				}
			}
		}else if(role.equals(SecurityConstants.ACCESS_TO_SYSTEM) || (role.equals(SecurityConstants.MULTI_ASSESSMENT) && sys.getUserSessionData().getFilter().getAssesment() != null)) {
			sys.getUserABMFacade().saveLogin(date,userSession);
			if(sys.getUserSessionData().getFilter().getAssesment() != null) {
				int assId = sys.getUserSessionData().getFilter().getAssesment().intValue();
				boolean blockMutual = false;
				if(assId == AssesmentData.MUTUAL_DA) {
					UserData userData = sys.getUserReportFacade().findUserByPrimaryKey(login, sys.getUserSessionData());
			        Integer count = sys.getCorporationReportFacade().getCompletedCediUsers(userData.getLocation(), sys.getUserSessionData());
			        blockMutual = (userData.getLocation() == 514) ? count >= 55 : count >= 50;
				}
				if(blockMutual) {
					response.sendRedirect("mutualExcedUser.jsp");
				}else {
					int terms = userReport.terms(login, sys.getUserSessionData()).intValue();
					System.out.println("terms "+terms);
					if(terms > 0 && assId != AssesmentData.UPM_CHARLA && assId != AssesmentData.UPM_CHARLA_V2 && assId != AssesmentData.ALRIYADAH_INITIALA
							 && assId != AssesmentData.ALRIYADAH_INITIALB  && assId != AssesmentData.ALRIYADAH_FINAL&& assId != AssesmentData.GDC) {
						response.sendRedirect("terms_"+terms+".jsp");
					}else {
						switch(assId) {
							case AssesmentData.ALRIYADAH_INITIALA:
						    	response.sendRedirect("alriyadahInitialA.jsp");
						    	break;
							case AssesmentData.ALRIYADAH_INITIALB:
						    	response.sendRedirect("alriyadahInitialB.jsp");
						    	break;
							case AssesmentData.ALRIYADAH_FINAL:
						    	response.sendRedirect("alriyadahFinalT.jsp");
						    	break;
							case AssesmentData.GDC:
						    	response.sendRedirect("GDCForm.jsp");
						    	break;
							case AssesmentData.ASTRAZENECA_2:
							case AssesmentData.ASTRAZENECA_2013:
						    	response.sendRedirect("az.jsp");
						    	break;
							case AssesmentData.DEMO_MOVILES:
						    	response.sendRedirect("moviles.jsp");
						    	break;
							case AssesmentData.MONSANTO_EMEA:
						    	response.sendRedirect("emea.jsp");
						    	break;
							case AssesmentData.FRAYLOG_FATIGA:
						    	response.sendRedirect("repeat_da.jsp");
						    	break;
							case AssesmentData.JJ_5:		
					    		String redirect = userReport.getElearningURL(user,userSession.getFilter().getAssesment(),userSession);
					    		if(redirect != null && redirect.length() > 0) {
						    		response.sendRedirect(redirect);
					    		}else {
						    		response.sendRedirect("../flash/assessment/?lang="+userSession.getLenguage());
					    		}
					    		break;
							default:
					    		redirect = userReport.getElearningURL(user,userSession.getFilter().getAssesment(),userSession);
					    		if(redirect != null && redirect.length() > 0) {
						    		response.sendRedirect(redirect);
					    		}else {
									AssesmentData assesment = sys.getAssesmentReportFacade().findAssesment(userSession.getFilter().getAssesment(),userSession);
									if(assesment.getStatus().intValue() != AssesmentData.NO_EDITABLE) {
								    	response.sendRedirect("./noassessment.jsp");
									}else {
						    			if(isMSIE) {
								    		response.sendRedirect("../flash/assessment/?lang="+userSession.getLenguage());
						    			}else {
											userSession.getFilter().setAssessmentData(assesment);
											Iterator it = assesment.getModuleIterator();
											while(it.hasNext()) {
												ModuleData module = (ModuleData)it.next();
												module.setAnswered(userReport.getQuestionCount(user,module.getId(),userSession.getFilter().getAssesment(),userSession));
											}
											if(assesment.isPsitest()) {
												assesment.setPsiCount(userReport.getQuestionCount(user,new Integer(0),userSession.getFilter().getAssesment(),userSession));
											}
											String lng = sys.getUserSessionData().getLenguage();
											if(lng.equals("vt") || lng.equals("in") || lng.equals("id") || lng.equals("ph") || lng.equals("pk")) {
										    	response.sendRedirect("./module_vt.jsp");
											}else {
										    	response.sendRedirect("./module_da.jsp");
	//									    	response.sendRedirect("./newmodule.jsp?id="+assesment.getId());
											}
						    			}
									}
					    		}
						    	break;
					    }
					}
				}
			}else {
		    	response.sendRedirect("./noassessment.jsp");
			}
		}else {
			sys.getUserABMFacade().saveLogin(date,sys.getUserSessionData());
		 	String check = Util.checkPermission(sys,SecurityConstants.ADMINISTRATOR);
		 	if(request.getUserPrincipal().getName().equals("resetpassword")) {
			    response.sendRedirect("./resetPassword.jsp");
			}else if(userSession.getRole().equals("firstAccess")) {
			    response.sendRedirect("./firstaccess.jsp");
			}else if(check!=null) {
				response.sendRedirect(request.getContextPath()+check);
			}else { 		
				if(request.getParameter("refer") != null) {
					area = request.getParameter("refer");
				}
				try {
					String password = request.getParameter("password"); 
					if(!Util.empty(password)) {
				        if(password.length() > 13) {
				            date = Long.parseLong(password.substring(password.length()-13));
				        }
					}
				}catch(NumberFormatException e) {
				}
				
				session.setAttribute("url","./home.jsp");
	
				if(sys==null){
					response.sendRedirect("/login.jsp");
				}
				
				messages=sys.getText();
%>




<%@page import="assesment.communication.module.ModuleData"%>
<%@page import="assesment.communication.assesment.AssesmentAttributes"%>
<%@page import="assesment.presentation.translator.web.administration.user.LogoutAction"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<LINK REL=StyleSheet HREF="util/css/estilo.css" TYPE="text/css">
	<link rel="STYLESHEET" type="text/css" href="./css/menu.css"> 
	<script type="text/javascript" src="./menu/js/dropdown.js"></script>
	<script language="JavaScript" src="./menu/js/refactor.js" type="text/javascript">
 	</script>
 	<title> CEPA International</title>
	<META http-equiv="Cache-Control" content="no-cache">
	<META http-equiv="Pragma" content="no-cache">

<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

.contain {
width: 100%; 
height: 100%;
}
 -->
</style>
	<script type="text/javascript" src="./menu/js/dropdown.js"></script>
	<script type="text/javascript" src="./menu/js/functionPreferences.js"></script>

</head>

<body scroll="auto" onLoad="setTimeout('refactor.loading()',4000);return true">
 	<table width="100%" height="90" border="0" cellpadding="0" cellspacing="0" class="default">
		<form name="logout" action="./logout.jsp" method="post"></form>
		<form name="home" action="./layout.jsp" method="post"></form>
		<form name="texts" action="./layout.jsp" method="post">
			<input type="hidden" name="refer" value="/user/reloadText.jsp" />
		</form>
		<tr id="top">
  			<td height="90" colspan="2" valign="top">
   				<table width="100%" height="70" border="0" cellpadding="0" cellspacing="0" id="topo">
					<tr>
			        	<td background="./imgs/fondo.jpg" align="left">
			        		<a href="index.jsp">
				        		<img src="./imgs/main_logo.png" height="50" valign="bottom" style="vaº">
				        	</a>
			        	</td>
			        	<td background="./imgs/fondo.jpg" align="left">
			        		<a href="index.jsp">
				        		<img src="./imgs/logo.jpg" height="69" valign="bottom">
							</a>
			        	</td>
			        	<td background="./imgs/fondo.jpg" align="left" width="30%">
			        	</td>
					</tr>
				</table>
   				<table width="100%" height="20" border="0" cellpadding="0" cellspacing="0" id="topo">
					<tr>
						<td width="90%" style="background-color: #868686;">
							<div id="header">
								<ul class="nav">
<%		if(userSession.checkPermission(SecurityConstants.ADMINISTRATOR)) {	
%>									<li><a href="layout.jsp?refer=/corporation/list.jsp"><%=messages.getText("generic.data.corporation")%></a></li>
									<li><a href=""><%=messages.getText("generic.data.company")%></a>
										<ul>
											<li><a href="layout.jsp?refer=/corporation/cediList.jsp"><%=messages.getText("generic.data.cedi")%></a></li>
											<li><a href="layout.jsp?refer=/corporation/mutualCompanyList.jsp"><%=messages.getText("generic.data.mutualcompany")%></a></li>
										</ul>
									</li>
									<li><a href="layout.jsp?refer=/assesment/groupList.jsp"><%=messages.getText("system.data.groups")%></a></li>
									<li><a href=""><%=messages.getText("generic.data.assesment")%></a>
										<ul>
											<li><a href="layout.jsp?refer=/assesment/list.jsp"><%=messages.getText("generic.actives")%></a></li>
											<li><a href="layout.jsp?refer=/assesment/listbkp.jsp"><%=messages.getText("generic.backedup")%></a></li>
										</ul>
									</li>
									<li><a href=""><%=messages.getText("generic.data.webinar")%></a>
										<ul>
											<li><a href="layout.jsp?refer=/assesment/webinarList.jsp"><%=messages.getText("generic.messages.course")%></a></li>
											<li><a href="layout.jsp?refer=/assesment/webinarUsers.jsp"><%=messages.getText("generic.data.users")%></a></li>
											<li><a href="layout.jsp?refer=/assesment/webinarErrors.jsp"><%=messages.getText("generic.messages.errors")%></a></li>
										</ul>
									</li>
									<li><a href=""><%=messages.getText("generic.data.users")%></a>
										<ul>
											<li><a href="layout.jsp?refer=/user/list.jsp"><%=messages.getText("generic.messages.management")%></a></li>
											<li><a href="layout.jsp?refer=/user/accesscodelist.jsp"><%=messages.getText("user.accesscodes")%></a></li>
											<li><a href="layout.jsp?refer=/user/createfromfile.jsp"><%=messages.getText("user.fromfile")%></a></li>
											<li><a href="layout.jsp?refer=/user/createtimacusers.jsp"><%=messages.getText("user.timcafile")%></a></li>
										</ul>
									</li>
									<li><a href=""><%=messages.getText("generic.data.reports")%></a>
										<ul>
											<li><a href="layout.jsp?refer=/report/assesment.jsp"><%=messages.getText("generic.data.assesment")%></a></li>
											<li><a href="layout.jsp?refer=/report/users.jsp"><%=messages.getText("generic.data.users")%></a></li>
											<li><a href="layout.jsp?refer=/report/java.jsp">JAVA</a></li>
											<li><a href=""><%=messages.getText("Generales")%></a>
												<ul>
													<li style="left: 60px;"><a href="layout.jsp?refer=/report/results.jsp"><%=messages.getText("generic.results.all")%></a></li>
													<li style="left: 60px;"><a href="layout.jsp?refer=/report/advance.jsp"><%=messages.getText("generic.results.advance")%></a></li>
													<li style="left: 60px;"><a href="layout.jsp?refer=/report/moduleresults.jsp"><%=messages.getText("generic.question.module.results")%></a></li>
													<li style="left: 60px;"><a href="layout.jsp?refer=/report/ranking.jsp"><%=messages.getText("generic.question.ranking")%></a></li>
													<li style="left: 60px;"><a href="layout.jsp?refer=/report/questionresults.jsp"><%=messages.getText("generic.question.results")%></a></li>
												</ul>
											</li>
											<li><a href=""><%=messages.getText("Usuario")%></a>
												<ul>
													<li style="left: 60px;"><a href="layout.jsp?refer=/report/userresults.jsp"><%=messages.getText("generic.results.all")%></a></li>
													<li style="left: 60px;"><a href="layout.jsp?refer=/report/usererrors.jsp"><%=messages.getText("generic.results.error")%></a></li>
													<li style="left: 60px;"><a href="layout.jsp?refer=/report/userpsiresults.jsp"><%=messages.getText("driver.result.psi")%></a></li>
													<li style="left: 60px;"><a href="layout.jsp?refer=/report/userpersonaldataresults.jsp"><%=messages.getText("driver.result.personaldata")%></a></li>
													<li style="left: 60px;"><a href="layout.jsp?refer=/report/newhire.jsp"><%=messages.getText("generic.results.newhire")%></a></li>
												</ul>
											</li>
										</ul>
									</li>
<%			}
%>
									<li><a href=""><%=messages.getText("generic.messages.preferences")%></a>
										<ul>
											<li><a href="javascript:openChangePassword(500,400)"><%=messages.getText("generic.user.changepassword")%></a></li>
											<li><a href="javascript:openChangeLanguage()"><%=messages.getText("generic.user.changelanguage")%></a></li>
											<li><a href="javascript:document.forms['texts'].submit();"><%=messages.getText("generic.messages.updatetexts")%></a></li>
										</ul>
									</li>							
								</ul>
							</div>
						</td>
						<td width="10%" style="background-color: #868686; padding-right: 10px;" >
							<a href="javascript:document.forms['logout'].submit()">
								<span style="color:#b92a2a; text-decoration:none;font-family:Roboto,Verdana,Arial, Helvetica, sans-serif;"><b><%=messages.getText("generic.messages.logout")%></b></span>
							</a>
						</td>
				  	</tr>
				</table>
			</td>
		</tr>
		<tr style="height: 25px;">
			<td>&nbsp;</td>
		</tr>
		<tr>
   			<td valign="top" align="center" >
<%					if(!Util.empty(area)) {
%>     						<jsp:include page='<%= area %>'/>
<%					}
%>
   			</td>
  		</tr>
	</table>
</body>
<%			}
		}
	}
%>
</html>
