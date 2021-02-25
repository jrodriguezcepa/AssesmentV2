package assesment;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;

import assesment.communication.assesment.AssesmentData;
import assesment.communication.util.MD5;
import assesment.communication.util.MailSender;

public class MailingJJ {
	
	//GMAIL
/*    private static final String FROM_NAME = "CEPA - Driver Assessment";
    private static final String FROM_DIR = "info@cepada.com";
    private static final String PASSWORD = "info01";
    private static final String SMTP = "smtp.gmail.com";
*/    
    //LOCAWEB
	/*
    protected static final String SMTP = "smtp.cepasafedrive.com";
    protected static final String FROM_NAME = "CEPA - Driver Assessment";
    protected static final String FROM_DIR = "da@cepasafedrive.com";
    protected static final String PASSWORD = "dacepa09";
    */
	
	// CepaLogistica
	protected static final String SMTP = "mail.cepalogistica.com";
    protected static final String FROM_NAME = "CEPA - Driver Assessment";
    protected static final String FROM_DIR = "da@cepainternational.com";
    // protected static final String PASSWORD = "dacepa09";
	

    public static void main(String[] args) {

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn1 = DriverManager.getConnection("jdbc:postgresql://173.247.255.40:5432/assesment","postgres","pr0v1s0r1A");
            Statement st = conn1.createStatement();
            Statement st2 = conn1.createStatement();
                        
            ResultSet set = st.executeQuery(
            		" select u.extradata2, u.extradata3, u.email, u.firstname, u.lastname, u.language, u.loginname "+            		
            		" from users u join userassesments ua on ua.loginname = u.loginname " +
            		" where ua.assesment = 115 " +
            		//" AND enddate is null "+
            		" and u.vehicle not in ('FERNANDO BUENO','GONZALO REDONDO','PEDRO PITELLA') " +
            		" order by vehicle");
            		
//            		" and u.email in ('smendes@its.jnj.com','RFONSEC9@its.jnj.com') order by vehicle");
//            ResultSet set = st.executeQuery("select u.email, u.firstname, u.lastname, u.language from users u join userassesments ua on ua.loginname = u.loginname where u.email in ('scarden1@conpa.jnj.com','tolivei3@medbr.jnj.com')");
//            ResultSet set = st.executeQuery("SELECT u.email, u.firstname, u.lastname, u.language FROM users u join userassesments ua on ua.loginname = u.loginname WHERE ua.assesment = 57 AND email IN ('desirose@cwjamaica.com')"); 
//            ResultSet set = st.executeQuery("SELECT u.email, u.firstname, u.lastname, u.language FROM users u JOIN userassesments uas ON u.loginname = uas.loginname WHERE uas.assesment = 57 AND enddate IS NULL AND u.email IN ('yvillesc@its.jnj.com')");

            MD5 md5 = new MD5();
         //   int i = 1;

            while(set.next()){
	    		String emailSend = set.getString(1);
	    		String emailCopy = set.getString(2);
	    		String email = set.getString(3);
	    		String firstName = set.getString(4);
	    		String lastName = set.getString(5);
	    		String language = set.getString(6);
	    		
	    		String loginname = set.getString(7);

	    		String subject;
	    		MailSender sender;
	    		if(email != null && email.length() > 0) {
	                try {
		        		String href = md5.encriptar(email);
		        		 //emailSend = "juanyfla@adinet.com.uy";
		        		 //emailSend = "rodriguez.jme@gmail.com";
		        		
		        		// email = "gbenaderet@cepasafedrive.com";
		        		
	        			subject = "";
	        			if(language.equals("es")) {
	        				subject = "Invitación a participar en el 'Driver Assessment' online";
	        			} else if(language.equals("en")) {
	        				subject = "Invitation to participate in the Online Driver Assessment";
	        			}else {
	        				subject = "Convite para participar do 'Driver Assessment' on-line";
	        			}
	        			System.out.println(firstName+","+lastName+",http://da.cepasafedrive.com/assesment/index.jsp?login="+href);
/*
	        			String  body = getMail("http://da.cepasafedrive.com/assesment/index.jsp?login="+href,language);

	        			sender = new MailSender();
	        			int i = 0;
	        			boolean send = false;
	        			while (!send && i < 10) {
		        			String[] address = sender.getAvailableMailAddress();
		        			if(address[0] != null) {
		        				String[] ccAddress = new String[0];
		        				/*if(emailCopy != null && emailCopy.length() > 0 && !emailCopy.equals("---")) {
		        					ccAddress = new String[1];
		        					ccAddress[0] = emailCopy;
		        				}*/
		        				
		        				
/*		        				// System.out.println("Aca mandaria el email...");
		        				
		        				try {
		        					sender.sendImage(emailSend, "CEPA Driver Assessment", address[0], address[1], 
		        							subject, new LinkedList(), body, AssesmentData.FLASH_PATH+"/images/cabezal_jj_latyc.jpg",ccAddress);
		        					send = true;
		        					System.out.println("'"+loginname+"', ");
		        					
		        					//st2.executeUpdate("UPDATE users SET emailsended = CURRENT_DATE where loginname='"+loginname+"'");
		        					
		        				}catch (Exception e) {
		    	        			System.out.println("ERROR '"+email+"',");
		        					e.printStackTrace();
		    		            }
				        		//Thread.sleep(50000);	        			
		        			}
		        			i++;
	        			}*/
		            }catch (Exception e) {
	        			System.out.println("ERROR '"+email+"',");
	        			e.printStackTrace();	
	        			
	        			
		            }
	    		}
	    	}

    	}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getMail(String href, String language) {
		String mail = "<html>" +
			"	<head>" +
			"	</head>" +
			"	<body bgcolor=\"#ffffff\" text=\"#000066\">" +
			"		<table>" +
			"			<tr>" +
			"				<td>" +
			"					<img src=\"cid:figura1\" alt=\"\">" +
			"					<br>" +
			"				</td>" +
			"			</tr>" +
			"			<tr>" +
			"				<td>";
		if(language.equals("es")) {
			mail += " 					<h1 align=center style='text-align:center'><span lang=ES-UY style='font-size: 13.0pt;mso-bidi-font-size:16.0pt;line-height:115%;font-family:\"Trebuchet MS\"'>INVITACI&Oacute;N a participar en el 'Driver Assessment' online<o:p></o:p></span></h1> "+
			" 					<p class=MsoNormal><span lang=ES-UY style='font-size:11.0pt;mso-bidi-font-size: "+
			" 					11.0pt;line-height:115%;font-family:\"Trebuchet MS\"'>Como parte de "+
			" 					un proceso de mejoramiento continuo, SAFE Fleet Latinoam&eacute;rica ha decidido nuevamente "+
			" 					utilizar la herramienta <b style='mso-bidi-font-weight:normal'>'Driver Assessment'</b> "+
			" 					para los conductores que ya han completado m&aacute;s de dos cursos <b "+
			" 					style='mso-bidi-font-weight:normal'>BTW</b> (Behind the Wheel o Detr&aacute;s del "+
			" 					Volante) en la regi&oacute;n, y as&iacute; poder detectar y asesorar oportunidades "+
			" 					adicionales de aprendizaje. <o:p></o:p></span></p><br> "+
			" 					<p class=MsoNormal><span lang=ES-UY style='font-size:11.0pt;mso-bidi-font-size:"+
			" 					11.0pt;line-height:115%;font-family:\"Trebuchet MS\"'>A trav&eacute;s del"+
			" 					link, a continuaci&oacute;n podr&aacute; acceder al 'Driver Assessment'. Este contiene una"+
			" 					serie de breves cuestionarios que est&aacute;n divididos en 4 m&oacute;dulos (ver abajo) ."+
			" 					Una vez complete estos cuestionarios, recibir&aacute; un reporte autom&aacute;tico de su"+
			" 					desempe&ntilde;o en esta misma cuenta de email. <o:p></o:p></span></p><br>"+
			" 					<p class=MsoNormal><span lang=ES-UY style='font-size:11.0pt;mso-bidi-font-size:"+
			" 					11.0pt;line-height:115%;font-family:\"Trebuchet MS\"'>Por favor"+
			" 					tenga en cuenta que deber&aacute; revisar cada uno de los m&oacute;dulos en su totalidad; de"+
			" 					lo contrario, el sistema no lo toma como completado.<o:p></o:p></span></p><br>"+
			" 					<p class=MsoNormal><span lang=EN-US style='font-size:11.0pt;mso-bidi-font-size:"+
			" 					11.0pt;line-height:115%;font-family:\"Trebuchet MS\";color:#1F497D;"+
			" 					mso-ansi-language:EN-US'>Link: </span><span lang=EN-US style='font-size:11.0pt;"+
			" 					mso-bidi-font-size:11.0pt;line-height:115%;font-family:\"Trebuchet MS\";"+
			" 					mso-fareast-font-family:\"Times New Roman\";mso-bidi-font-family:Calibri;"+
			" 					color:#1F497D;mso-ansi-language:EN-US;mso-fareast-language:ES-UY'><a href='"+href+"'>"+href+"</a><o:p></o:p></span></p><br>"+
			" 					<p class=MsoNormal style='margin-bottom:12.0pt'><b><span lang=ES-UY"+
			" 					style='font-size:9.0pt;mso-bidi-font-size:10.0pt;line-height:115%;font-family:"+
			" 					\"Trebuchet MS\";color:black'>Importante: </span></b><span"+
			" 					lang=ES-UY style='font-size:9.0pt;mso-bidi-font-size:12.0pt;line-height:115%;"+
			" 					font-family:\"Trebuchet MS\";color:black'>le pedimos por favor <u>no"+
			" 					reenviar el link</u> a otros participantes, el mismo fue creado espacialmente"+
			" 					para usted. En caso de no poder acceder a trav&eacute;s del link por favor copie y"+
			" 					pegue el mismo en un navegador.<o:p></o:p></span></p><br>"+
			" 					<p class=MsoNormal><span lang=ES-UY style='font-size:11.0pt;mso-bidi-font-size:"+
			" 					12.0pt;line-height:115%;font-family:\"Trebuchet MS\"'>Para aclarar"+
			" 					dudas y/o consultas por favor comunicarse con </span><span lang=ES-UY><a"+
			" 					href=\"mailto:indesa@cepasafedrive.com\" target=\"_blank\"><b><span"+
			" 					style='font-size:11.0pt;mso-bidi-font-size:12.0pt;line-height:115%;font-family:"+
			" 					\"Trebuchet MS\"'>indesa@cepasafedrive.com</span></b></a></span><span"+
			" 					lang=ES-UY style='font-size:12.0pt;mso-bidi-font-size:13.0pt;line-height:115%;"+
			" 					font-family:\"Trebuchet MS\"'><o:p></o:p></span></p><br>"+
			" 					<p class=MsoNormal><span lang=ES-UY style='font-size:11.0pt;mso-bidi-font-size:"+
			" 					13.0pt;line-height:115%;font-family:\"Trebuchet MS\"'>Lo invitamos a"+
			" 					utilizar esta herramienta con el fin de seguir mejorando d&iacute;a a d&iacute;a la seguridad"+
			" 					de nuestra flota en Latinoam&eacute;rica, e influenciar con nuestra cultura de"+
			" 					seguridad vehicular a nuestros seres queridos y aquellas comunidades donde"+
			" 					trabajamos y vivimos.<o:p></o:p></span></p><br>"+
			" 					<h2><span lang=ES-UY style='font-size:13.0pt;mso-bidi-font-size:14.0pt;"+
			" 					line-height:115%;font-family:\"Trebuchet MS\"'>Estructura y"+
			" 					Contenido<o:p></o:p></span></h2>"+
			" 					<p class=MsoNormal><span lang=ES-UY style='font-size:11.0pt;mso-bidi-font-size:"+
			" 					13.0pt;line-height:115%;font-family:\"Trebuchet MS\"'>Los 4 m&oacute;dulos"+
			" 					de la herramienta son:<o:p></o:p></span></p><br>"+
			" 					<p class=MsoNormal style='margin-left:18.0pt;text-indent:-18.0pt;mso-list:l0 level1 lfo2;"+
			" 					tab-stops:list 18.0pt'><![if !supportLists]><span lang=ES-UY style='font-size:"+
			" 					11.0pt;mso-bidi-font-size:13.0pt;line-height:115%;font-family:Symbol;mso-fareast-font-family:"+
			" 					Symbol;mso-bidi-font-family:Symbol'><span style='mso-list:Ignore'>·<span"+
			" 					style='font:7.0pt \"Times New Roman\"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
			" 					</span></span></span><![endif]><span lang=ES-UY style='font-size:11.0pt;"+
			" 					mso-bidi-font-size:13.0pt;line-height:115%;font-family:\"Trebuchet MS\"'>M&oacute;dulo"+
			" 					1: Cultura General de Seguridad, cuestionario de la situaci&oacute;n mundial y"+
			" 					generalidades acerca del tr&aacute;nsito.<o:p></o:p></span></p><br>"+
			" 					<p class=MsoNormal style='margin-left:18.0pt;text-indent:-18.0pt;mso-list:l0 level1 lfo2;"+
			" 					tab-stops:list 18.0pt'><![if !supportLists]><span lang=ES-UY style='font-size:"+
			" 					11.0pt;mso-bidi-font-size:13.0pt;line-height:115%;font-family:Symbol;mso-fareast-font-family:"+
			" 					Symbol;mso-bidi-font-family:Symbol'><span style='mso-list:Ignore'>·<span"+
			" 					style='font:7.0pt \"Times New Roman\"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
			" 					</span></span></span><![endif]><span lang=ES-UY style='font-size:11.0pt;"+
			" 					mso-bidi-font-size:13.0pt;line-height:115%;font-family:\"Trebuchet MS\"'>M&oacute;dulo"+
			" 					2: Conocimientos Acerca del Programa SAFE Fleet.<o:p></o:p></span></p><br>"+
			" 					<p class=MsoNormal style='margin-left:18.0pt;text-indent:-18.0pt;mso-list:l0 level1 lfo2;"+
			" 					tab-stops:list 18.0pt'><![if !supportLists]><span lang=ES-UY style='font-size:"+
			" 					11.0pt;mso-bidi-font-size:13.0pt;line-height:115%;font-family:Symbol;mso-fareast-font-family:"+
			" 					Symbol;mso-bidi-font-family:Symbol'><span style='mso-list:Ignore'>·<span"+
			" 					style='font:7.0pt \"Times New Roman\"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
			" 					</span></span></span><![endif]><span lang=ES-UY style='font-size:11.0pt;"+
			" 					mso-bidi-font-size:13.0pt;line-height:115%;font-family:\"Trebuchet MS\"'>M&oacute;dulo"+
			" 					3: Conocimientos de Seguridad Vehicular; cuestionario espec&iacute;fico sobre"+
			" 					conducci&oacute;n y sus t&eacute;cnicas.<o:p></o:p></span></p><br>"+
			" 					<p class=MsoNormal style='margin-left:18.0pt;text-indent:-18.0pt;mso-list:l0 level1 lfo2;"+
			" 					tab-stops:list 18.0pt'><![if !supportLists]><span lang=ES-UY style='font-size:"+
			" 					11.0pt;mso-bidi-font-size:13.0pt;line-height:115%;font-family:Symbol;mso-fareast-font-family:"+
			" 					Symbol;mso-bidi-font-family:Symbol'><span style='mso-list:Ignore'>·<span"+
			" 					style='font:7.0pt \"Times New Roman\"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
			" 					</span></span></span><![endif]><span lang=ES-UY style='font-size:11.0pt;"+
			" 					mso-bidi-font-size:13.0pt;line-height:115%;font-family:\"Trebuchet MS\"'>M&oacute;dulo"+
			" 					4: Perfil Individual; eval&uacute;a y detecta los factores que afectan la conducta al"+
			" 					dirigir.<o:p></o:p></span></p><br>"+
			" 					<h1><span lang=ES-UY style='font-size:13.0pt;mso-bidi-font-size:14.0pt;"+
			" 					line-height:115%;font-family:\"Trebuchet MS\"'>Informaci&oacute;n para el"+
			" 					Usuario</span><span lang=ES-UY style='font-size:12.0pt;mso-bidi-font-size:14.0pt;"+
			" 					line-height:115%;font-family:\"Trebuchet MS\"'><o:p></o:p></span></h1>"+
			" 					<p class=MsoListParagraphCxSpFirst style='margin-left:18.0pt;mso-add-space:"+
			" 					auto;text-indent:-18.0pt;mso-list:l1 level1 lfo1'><![if !supportLists]><span"+
			" 					lang=ES-UY style='font-size:11.0pt;mso-bidi-font-size:13.0pt;line-height:115%;"+
			" 					font-family:Symbol;mso-fareast-font-family:Symbol;mso-bidi-font-family:Symbol'><span"+
			" 					style='mso-list:Ignore'>·<span style='font:7.0pt \"Times New Roman\"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
			" 					</span></span></span><![endif]><span lang=ES-UY style='font-size:11.0pt;"+
			" 					mso-bidi-font-size:13.0pt;line-height:115%;font-family:\"Trebuchet MS\"'>Con"+
			" 					el link que le presentamos anteriormente usted podr&aacute; retomar su sesi&oacute;n cuantas"+
			" 					veces lo desee hasta completar esta primera actividad.<o:p></o:p></span></p>"+
			" 					<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:"+
			" 					auto;text-indent:-18.0pt;mso-list:l1 level1 lfo1'><![if !supportLists]><span"+
			" 					lang=ES-UY style='font-size:11.0pt;mso-bidi-font-size:13.0pt;line-height:115%;"+
			" 					font-family:Symbol;mso-fareast-font-family:Symbol;mso-bidi-font-family:Symbol'><span"+
			" 					style='mso-list:Ignore'>·<span style='font:7.0pt \"Times New Roman\"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
			" 					</span></span></span><![endif]><span lang=ES-UY style='font-size:11.0pt;"+
			" 					mso-bidi-font-size:13.0pt;line-height:115%;font-family:\"Trebuchet MS\"'>Tiempo"+
			" 					de Uso: aproximadamente 40 minutos.<o:p></o:p></span></p>"+
			" 					<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:"+
			" 					auto;text-indent:-18.0pt;mso-list:l1 level1 lfo1'><![if !supportLists]><span"+
			" 					lang=ES-UY style='font-size:11.0pt;mso-bidi-font-size:13.0pt;line-height:115%;"+
			" 					font-family:Symbol;mso-fareast-font-family:Symbol;mso-bidi-font-family:Symbol'><span"+
			" 					style='mso-list:Ignore'>·<span style='font:7.0pt \"Times New Roman\"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
			" 					</span></span></span><![endif]><span lang=ES-UY style='font-size:11.0pt;"+
			" 					mso-bidi-font-size:13.0pt;line-height:115%;font-family:\"Trebuchet MS\"'>Inicio"+
			" 					y Fin de la Actividad: estar&aacute; disponible desde el momento en que recibe este"+
			" 					mail hasta el <b>15 de Noviembre de 2013</b>. Su participaci&oacute;n es requerida de acuerdo"+
			" 					con los Est&aacute;ndares Mundiales de SAFE Fleet, pues esta determina si necesita un"+
			" 					entrenamiento online, practico o no. <o:p></o:p></span></p>"+
			" 					<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:"+
			" 					auto;text-indent:-18.0pt;mso-list:l1 level1 lfo1'><![if !supportLists]><span"+
			" 					lang=ES-UY style='font-size:11.0pt;mso-bidi-font-size:13.0pt;line-height:115%;"+
			" 					font-family:Symbol;mso-fareast-font-family:Symbol;mso-bidi-font-family:Symbol'><span"+
			" 					style='mso-list:Ignore'>·<span style='font:7.0pt \"Times New Roman\"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
			" 					</span></span></span><![endif]><span lang=ES-UY style='font-size:11.0pt;"+
			" 					mso-bidi-font-size:13.0pt;line-height:115%;font-family:\"Trebuchet MS\"'>Requerimientos:"+
			" 					se puede hacer desde cualquier computador con conexi&oacute;n a internet.<o:p></o:p></span></p>"+
			" 					<p class=MsoListParagraphCxSpLast style='margin-left:18.0pt;mso-add-space:auto;"+
			" 					text-indent:-18.0pt;mso-list:l1 level1 lfo1'><![if !supportLists]><span"+
			" 					lang=ES-UY style='font-size:11.0pt;mso-bidi-font-size:13.0pt;line-height:115%;"+
			" 					font-family:Symbol;mso-fareast-font-family:Symbol;mso-bidi-font-family:Symbol'><span"+
			" 					style='mso-list:Ignore'>·<span style='font:7.0pt \"Times New Roman\"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
			" 					</span></span></span><![endif]><span lang=ES-UY style='font-size:11.0pt;"+
			" 					mso-bidi-font-size:13.0pt;line-height:115%;font-family:\"Trebuchet MS\"'>Reportes:"+
			" 					una vez finalizados todos los m&oacute;dulos, autom&aacute;ticamente le ser&aacute; enviado un"+
			" 					reporte a su e-mail.<o:p></o:p></span></p>"+
			" 					<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span lang=ES-UY"+
			" 					style='font-size:12.0pt;mso-bidi-font-size:14.0pt;line-height:115%;font-family:"+
			" 					\"Trebuchet MS\"'>¡Gracias por su participaci&oacute;n! ¡Su aporte es"+
			" 					sumamente valioso, contamos con &eacute;l!<o:p></o:p></span></b></p>";
		}else if(language.equals("pt")) {
			 mail += "				<h1 align=center style='text-align:center;line-height:116%'><span lang=PT-BR"+
				"				style='font-size:13.0pt;line-height:116%;font-family:\"Trebuchet MS\"'>CONVITE"+
				"				para participar do 'Driver Assessment' on-line</span></h1>"+
				"				<p class=MsoNormal style='line-height:116%'><span lang=PT-BR style='font-size:"+
				"				11.0pt;line-height:116%;font-family:\"Trebuchet MS\"'>Como parte de"+
				"				um processo de aprimoramento cont&iacute;nuo, o SAFE Fleet Am&eacute;rica Latina decidiu"+
				"				aplicar novamente a ferramenta <b>'Driver Assessment'</b> para os condutores da"+
				"				regi&atilde;o que tenham conclu&iacute;do mais de dois cursos <b>BTW</b> (Behind the Wheel)"+
				"				para poder detectar e avaliar oportunidades adicionais de aprendizagem.</span></p><br>"+
				"				<p class=MsoNormal style='line-height:116%'><span lang=PT-BR style='font-size:"+
				"				11.0pt;line-height:116%;font-family:\"Trebuchet MS\"'>Atrav&eacute;s do link"+
				"				abaixo, ser&aacute; poss&iacute;vel acessar a ferramenta 'Driver Assessment' que cont&eacute;m uma"+
				"				s&eacute;rie de breves question&aacute;rios divididos em quatro<a name=\"_GoBack\"></a> m&oacute;dulos"+
				"				(veja abaixo). Assim que os question&aacute;rios forem conclu&iacute;dos, um relat&oacute;rio"+
				"				autom&aacute;tico do seu desempenho ser&aacute; emitido e enviado para esta conta de e-mail.</span></p><br>"+
				"				<p class=MsoNormal style='line-height:116%'><span lang=PT-BR style='font-size:"+
				"				11.0pt;line-height:116%;font-family:\"Trebuchet MS\"'>Lembre-se de"+
				"				que voc&ecirc; dever&aacute; realizar cada um dos m&oacute;dulos em sua totalidade; do contr&aacute;rio, o"+
				"				sistema n&atilde;o considerar&aacute; a tarefa como conclu&iacute;da.</span></p><br>"+
				"				<p class=MsoNormal style='line-height:116%'><span lang=PT-BR style='font-size:"+
				"				11.0pt;line-height:116%;font-family:\"Trebuchet MS\";color:#1F497D'>Link:"+
				"				</span><span lang=PT-BR style='font-size:11.0pt;line-height:116%;font-family:"+
				"				\"Trebuchet MS\";color:#1F497D'><a href='"+href+"'>"+href+"</a><o:p></o:p></span></p><br>"+
				"				<p class=MsoNormal style='margin-bottom:14.0pt;line-height:116%'><b><span"+
				"				lang=PT-BR style='font-size:9.0pt;line-height:116%;font-family:\"Trebuchet MS\";"+
				"				color:black'>Importante: F</span></b><span lang=PT-BR style='font-size:9.0pt;"+
				"				line-height:116%;font-family:\"Trebuchet MS\";color:black'>avor <u>n&atilde;o"+
				"				reenviar o link</u> a outros participantes, pois o mesmo foi criado"+
				"				especialmente para voc&ecirc;. Caso n&atilde;o consiga acessar o site atrav&eacute;s link acima,"+
				"				copie e cole o endere&ccedil;o na barra de endere&ccedil;os do seu navegador.</span></p><br>"+
				"				<p class=MsoNormal style='line-height:116%'><span lang=PT-BR style='font-size:"+
				"				11.0pt;line-height:116%;font-family:\"Trebuchet MS\"'>Para tirar"+
				"				d&uacute;vidas e/ou realizar consultas, entre em contato com </span><span lang=ES-UY><a"+
				"				href=\"mailto:indesa@cepasafedrive.com\" target=\"_blank\"><b><span lang=PT-BR"+
				"				style='font-size:11.0pt;line-height:116%;font-family:\"Trebuchet MS\"'>indesa@cepasafedrive.com</span></b></a></span></p><br>"+
				"				<p class=MsoNormal style='line-height:116%'><span lang=PT-BR style='font-size:"+
				"				11.0pt;line-height:116%;font-family:\"Trebuchet MS\"'>Convidamos voc&ecirc;"+
				"				a utilizar essa ferramenta para que possamos continuar melhorando diariamente a"+
				"				seguran&ccedil;a de nossa frota latino-americana e, com base em nossa cultura de"+
				"				seguran&ccedil;a veicular, influenciarmos os entes queridos e as comunidades onde"+
				"				trabalhamos e vivemos.</span></p><br>"+
				"				<h2 style='line-height:116%'><span lang=PT-BR style='font-size:13.0pt;"+
				"				line-height:116%;font-family:\"Trebuchet MS\"'>Estrutura e Conte&uacute;do</span></h2>"+
				"				<p class=MsoNormal style='line-height:116%'><span lang=PT-BR style='font-size:"+
				"				11.0pt;line-height:116%;font-family:\"Trebuchet MS\"'>Os quatro"+
				"				m&oacute;dulos dessa ferramenta s&atilde;o:</span></p><br>"+
				"				<p class=MsoNormal style='margin-left:17.85pt;text-indent:-17.85pt;line-height:"+
				"				normal'><span lang=PT-BR style='font-size:11.0pt;font-family:Symbol'>·<span"+
				"				style='font:9.0pt \"Times New Roman\"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
				"				</span></span><span lang=PT-BR style='font-size:11.0pt;font-family:\"Trebuchet MS\"'>M&oacute;dulo"+
				"				1: Cultura geral de seguran&ccedil;a, Question&aacute;rio sobre a situa&ccedil;&atilde;o mundial atual e"+
				"				aspectos gerais do tr&acirc;nsito.</span></p><br>"+
				"				<p class=MsoNormal style='margin-left:17.85pt;text-indent:-17.85pt;line-height:"+
				"				normal'><span lang=PT-BR style='font-size:11.0pt;font-family:Symbol'>·<span"+
				"				style='font:9.0pt \"Times New Roman\"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
				"				</span></span><span lang=PT-BR style='font-size:11.0pt;font-family:\"Trebuchet MS\"'>M&oacute;dulo"+
				"				2: Conhecimentos sobre o Programa SAFE Fleet.</span></p><br>"+
				"				<p class=MsoNormal style='margin-left:17.85pt;text-indent:-17.85pt;line-height:"+
				"				normal'><span lang=PT-BR style='font-size:11.0pt;font-family:Symbol'>·<span"+
				"				style='font:9.0pt \"Times New Roman\"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
				"				</span></span><span lang=PT-BR style='font-size:11.0pt;font-family:\"Trebuchet MS\"'>M&oacute;dulo"+
				"				3: Conhecimentos sobre seguran&ccedil;a veicular, Question&aacute;rio espec&iacute;fico sobre"+
				"				dire&ccedil;&atilde;o segura e t&eacute;cnicas preventivas ao volante.</span></p><br>"+
				"				<p class=MsoNormal style='margin-left:18.0pt;text-indent:-18.0pt;line-height:"+
				"				116%'><span lang=PT-BR style='font-size:11.0pt;line-height:116%;font-family:"+
				"				Symbol'>·<span style='font:9.0pt \"Times New Roman\"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
				"				</span></span><span lang=PT-BR style='font-size:11.0pt;line-height:116%;"+
				"				font-family:\"Trebuchet MS\"'>M&oacute;dulo 4: Perfil individual, Avalia e"+
				"				identifica os fatores que afetam a conduta ao volante.</span></p><br>"+
				"				<h1 style='line-height:116%'><span lang=PT-BR style='font-size:13.0pt;"+
				"				line-height:116%;font-family:\"Trebuchet MS\"'>Informa&ccedil;&otilde;es para o"+
				"				Usu&aacute;rio</span></h1>"+
				"				<p class=MsoListParagraphCxSpFirst style='margin-left:18.0pt;text-indent:-18.0pt;"+
				"				line-height:116%'><span lang=PT-BR style='font-size:11.0pt;line-height:116%;"+
				"				font-family:Symbol'>·<span style='font:9.0pt \"Times New Roman\"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
				"				</span></span><span lang=PT-BR style='font-size:11.0pt;line-height:116%;"+
				"				font-family:\"Trebuchet MS\"'>Com o link fornecido acima, voc&ecirc;"+
				"				poder&aacute; retomar sua sess&atilde;o quantas vezes quiser at&eacute; concluir a atividade.</span></p>"+
				"				<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;text-indent:-18.0pt;"+
				"				line-height:116%'><span lang=PT-BR style='font-size:11.0pt;line-height:116%;"+
				"				font-family:Symbol'>·<span style='font:9.0pt \"Times New Roman\"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
				"				</span></span><span lang=PT-BR style='font-size:11.0pt;line-height:116%;"+
				"				font-family:\"Trebuchet MS\"'>Dura&ccedil;&atilde;o total: aproximadamente 40"+
				"				minutos.</span></p>"+
				"				<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;text-indent:-18.0pt;"+
				"				line-height:116%'><span lang=PT-BR style='font-size:11.0pt;line-height:116%;"+
				"				font-family:Symbol'>·<span style='font:9.0pt \"Times New Roman\"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
				"				</span></span><span lang=PT-BR style='font-size:11.0pt;line-height:116%;"+
				"				font-family:\"Trebuchet MS\"'>In&iacute;cio e fim da atividade: ela estar&aacute;"+
				"				dispon&iacute;vel desde o momento em que voc&ecirc; recebeu este e-mail at&eacute; <b>15 de Novembro"+
				"				de 2013</b>. De acordo com as Normas Mundiais do SAFE Fleet, sua participa&ccedil;&atilde;o &eacute;"+
				"				necess&aacute;ria, pois &eacute; o que determinar&aacute; se voc&ecirc; precisa de um treinamento on-line,"+
				"				seja pr&aacute;tico ou n&atilde;o.</span></p>"+
				"				<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;text-indent:-18.0pt;"+
				"				line-height:116%'><span lang=PT-BR style='font-size:11.0pt;line-height:116%;"+
				"				font-family:Symbol'>·<span style='font:9.0pt \"Times New Roman\"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
				"				</span></span><span lang=PT-BR style='font-size:11.0pt;line-height:116%;"+
				"				font-family:\"Trebuchet MS\"'>Requisitos:a avalia&ccedil;&atilde;o pode ser"+
				"				realizada a partir de qualquer computador com acesso &agrave; Internet.</span></p>"+
				"				<p class=MsoListParagraphCxSpLast style='margin-left:18.0pt;text-indent:-18.0pt;"+
				"				line-height:116%'><span lang=PT-BR style='font-size:11.0pt;line-height:116%;"+
				"				font-family:Symbol'>·<span style='font:9.0pt \"Times New Roman\"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
				"				</span></span><span lang=PT-BR style='font-size:11.0pt;line-height:116%;"+
				"				font-family:\"Trebuchet MS\"'>Relat&oacute;rios: uma vez finalizados todos"+
				"				os m&oacute;dulos, um relat&oacute;rio ser&aacute; enviado automaticamente ao seu e-mail.</span></p>"+
				"				<p class=MsoNormal style='line-height:116%'><b><span lang=PT-BR"+
				"				style='font-size:12.0pt;line-height:116%;font-family:\"Trebuchet MS\"'>Agradecemos"+
				"				sua participa&ccedil;&atilde;o. Seu apoio &eacute; extremamente valioso. Contamos com voc&ecirc;!</span></b></p>";
		}else {
			mail += "				<h1 align=center style='text-align:center;line-height:116%'><span lang=EN-US"+
				"				style='font-size:13.0pt;line-height:116%;font-family:\"Trebuchet MS\"'>INVITATION"+
				"				to participate in the Online Driver Assessment</span></h1>"+
				"				<p class=MsoNormal style='line-height:116%'><span lang=EN-US style='font-size:"+
				"				11.0pt;line-height:116%;font-family:\"Trebuchet MS\"'>As part of our"+
				"				continuous improvement process, SAFE Fleet Latin America has decided once"+
				"				again to use the <b>Driver Assessment </b>tool with those Latin American"+
				"				drivers who have already attended more than two <b>BTW</b> (Behind the Wheel) training"+
				"				sessions. Thereby enabling us to identify and assess the need for additional"+
				"				learning opportunities.</span></p><br>"+
				"				<p class=MsoNormal style='line-height:116%'><span lang=EN-US style='font-size:"+
				"				11.0pt;line-height:116%;font-family:\"Trebuchet MS\"'>You can access"+
				"				the Driver Assessment tool using the link provided below. This tool comprises a"+
				"				series of short questionnaires divided into four modules (see below). Once the"+
				"				questionnaires have been completed, an automatic performance report will be"+
				"				forwarded to your e-mail account.</span></p><br>"+
				"				<p class=MsoNormal style='line-height:116%'><span lang=EN-US style='font-size:"+
				"				11.0pt;line-height:116%;font-family:\"Trebuchet MS\"'>Every module must"+
				"				be fully completed; otherwise the system will not consider the activity as finished.</span></p><br>"+
				"				<p class=MsoNormal style='line-height:116%'><span lang=EN-US style='font-size:"+
				"				11.0pt;line-height:116%;font-family:\"Trebuchet MS\";color:#1F497D'>Link:</span><span"+
				"				lang=EN-US style='font-size:11.0pt;line-height:116%;font-family:\"Trebuchet MS\";"+
				"				color:#1F497D'><a href='"+href+"'>"+href+"</a><o:p></o:p></span></p><br>"+
				"				<p class=MsoNormal style='margin-bottom:14.0pt;line-height:116%'><b><span"+
				"				lang=EN-US style='font-size:9.0pt;line-height:116%;font-family:\"Trebuchet MS\";"+
				"				color:black'>Important: </span></b><span lang=EN-US style='font-size:9.0pt;"+
				"				line-height:116%;font-family:\"Trebuchet MS\";color:black'>Please <u>do"+
				"				not send this link</u> to any other participant. It was created exclusively for"+
				"				you. If you are unable to access the link, please copy and paste it to your web browser´s"+
				"				Address Bar.</span></p><br>"+				
				"				<p class=MsoNormal style='line-height:116%'><span lang=EN-US style='font-size:"+
				"				11.0pt;line-height:116%;font-family:\"Trebuchet MS\"'>If you have any"+
				"				doubts or comments, please contact </span><span lang=ES-UY><a"+
				"				href=\"mailto:indesa@cepasafedrive.com\" target=\"_blank\"><b><span lang=EN-US"+
				"				style='font-size:11.0pt;line-height:116%;font-family:\"Trebuchet MS\"'>indesa@cepasafedrive.com</span></b></a></span></p><br>"+
				"				<p class=MsoNormal style='line-height:116%'><span lang=EN-US style='font-size:"+
				"				11.0pt;line-height:116%;font-family:\"Trebuchet MS\"'>We invite you"+
				"				to use this resource to continue improving the safety of our Latin American fleet"+
				"				and share our traffic safety culture with our loved ones and the community where"+
				"				we work and live.</span></p><br>"+
				"				<h2 style='line-height:116%'><span lang=EN-US style='font-size:13.0pt;"+
				"				line-height:116%;font-family:\"Trebuchet MS\"'>Structure and Content</span></h2>"+
				"				<p class=MsoNormal style='line-height:116%'><span lang=EN-US style='font-size:"+
				"				11.0pt;line-height:116%;font-family:\"Trebuchet MS\"'>The four modules"+
				"				are as follows:</span></p><br>"+
				"				<p class=MsoNormal style='margin-left:17.85pt;text-indent:-17.85pt;line-height:"+
				"				normal'><span lang=EN-US style='font-size:11.0pt;font-family:Symbol'>·<span"+
				"				style='font:9.0pt \"Times New Roman\"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
				"				</span></span><span lang=EN-US style='font-size:11.0pt;font-family:\"Trebuchet MS\"'>Module"+
				"				1: General Road Safety Culture, Questions on worldwide traffic-related events"+
				"				and issues.</span></p><br>"+
				"				<p class=MsoNormal style='margin-left:17.85pt;text-indent:-17.85pt;line-height:"+
				"				normal'><span lang=EN-US style='font-size:11.0pt;font-family:Symbol'>·<span"+
				"				style='font:9.0pt \"Times New Roman\"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
				"				</span></span><span lang=EN-US style='font-size:11.0pt;font-family:\"Trebuchet MS\"'>Module"+
				"				2: SAFE Fleet Program Knowledge.</span></p><br>"+
				"				<p class=MsoNormal style='margin-left:17.85pt;text-indent:-17.85pt;line-height:"+
				"				normal'><span lang=EN-US style='font-size:11.0pt;font-family:Symbol'>·<span"+
				"				style='font:9.0pt \"Times New Roman\"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
				"				</span></span><span lang=EN-US style='font-size:11.0pt;font-family:\"Trebuchet MS\"'>Module"+
				"				3: Road Traffic Safety Knowledge, Specific questions on safe driving.</span></p><br>"+
				"				<p class=MsoNormal style='margin-left:17.85pt;text-indent:-17.85pt;line-height:"+
				"				normal'><span lang=EN-US style='font-size:11.0pt;font-family:Symbol'>·<span"+
				"				style='font:9.0pt \"Times New Roman\"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
				"				</span></span><span lang=EN-US style='font-size:11.0pt;font-family:\"Trebuchet MS\"'>Module"+
				"				4: Individual profile, Used to evaluate and detect aspects that influence safe"+
				"				driving behavior.</span></p><br>"+
				"				<h1 style='line-height:116%'><span lang=EN-US style='font-size:13.0pt;"+
				"				line-height:116%;font-family:\"Trebuchet MS\"'>Additional Information"+
				"				for Users</span></h1>"+
				"				<p class=MsoListParagraphCxSpFirst style='margin-left:18.0pt;text-indent:-18.0pt;"+
				"				line-height:116%'><span lang=EN-US style='font-size:11.0pt;line-height:116%;"+
				"				font-family:Symbol'>·<span style='font:9.0pt \"Times New Roman\"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
				"				</span></span><span lang=EN-US style='font-size:11.0pt;line-height:116%;"+
				"				font-family:\"Trebuchet MS\"'>Using the link provided above, you can"+
				"				resume your session as many times as you want until you have completed the"+
				"				activity.</span></p>"+
				"				<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;text-indent:-18.0pt;"+
				"				line-height:116%'><span lang=EN-US style='font-size:11.0pt;line-height:116%;"+
				"				font-family:Symbol'>·<span style='font:9.0pt \"Times New Roman\"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
				"				</span></span><span lang=EN-US style='font-size:11.0pt;line-height:116%;"+
				"				font-family:\"Trebuchet MS\"'>Total duration: approximately 40 minutes.</span></p>"+
				"				<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;text-indent:-18.0pt;"+
				"				line-height:116%'><span lang=EN-US style='font-size:11.0pt;line-height:116%;"+
				"				font-family:Symbol'>·<span style='font:9.0pt \"Times New Roman\"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
				"				</span></span><span lang=EN-US style='font-size:11.0pt;line-height:116%;"+
				"				font-family:\"Trebuchet MS\"'>Start and end date: The resource will remain"+
				"				available from the moment you receive this email until <b>November 15, 2013</b>. According"+
				"				to International SAFE Fleet Standards, your participation is required. This"+
				"				resource provides information regarding the need for web-based training, hands-on,"+
				"				or classroom training. </span></p>"+
				"				<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;text-indent:-18.0pt;"+
				"				line-height:116%'><span lang=EN-US style='font-size:11.0pt;line-height:116%;"+
				"				font-family:Symbol'>·<span style='font:9.0pt \"Times New Roman\"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
				"				</span></span><span lang=EN-US style='font-size:11.0pt;line-height:116%;"+
				"				font-family:\"Trebuchet MS\"'>Requirements: the assessment can be carried"+
				"				out using any computer with Internet access.</span></p>"+
				"				<p class=MsoListParagraphCxSpLast style='margin-left:18.0pt;text-indent:-18.0pt;"+
				"				line-height:116%'><span lang=EN-US style='font-size:11.0pt;line-height:116%;"+
				"				font-family:Symbol'>·<span style='font:9.0pt \"Times New Roman\"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
				"				</span></span><span lang=EN-US style='font-size:11.0pt;line-height:116%;"+
				"				font-family:\"Trebuchet MS\"'>Reports: Upon completion of all"+
				"				modules, a performance report will automatically be forwarded to your e-mail"+
				"				account.</span></p>"+
				"				<p class=MsoNormal style='line-height:116%'><b><span lang=EN-US"+
				"				style='font-size:12.0pt;line-height:116%;font-family:\"Trebuchet MS\"'>Thank"+
				"				you for your participation! Your support is invaluable and we are counting on"+
				"				it!</span></b></p>";
		}
		mail += "				</td>" +
			"			</tr>" +
			"		</table>" +
			"	</body>" +
			"</html>";
		return mail;
	}
}
