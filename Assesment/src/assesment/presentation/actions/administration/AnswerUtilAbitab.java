package assesment.presentation.actions.administration;

import java.io.File;
import java.io.FileOutputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;

import javax.xml.ws.BindingProvider;

import ws.elearning.assessment.ElearningWSAssessment;
import ws.elearning.assessment.ElearningWSAssessmentService;
import assesment.business.AssesmentAccess;
import assesment.business.administration.property.PropertyABMFacade;
import assesment.business.administration.property.PropertyReportFacade;
import assesment.business.administration.user.UsABMFacade;
import assesment.business.administration.user.UsReportFacade;
import assesment.communication.administration.AbitabRegistry;
import assesment.communication.administration.property.PropertyData;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.language.Text;
import assesment.communication.user.UserData;
import assesment.communication.util.MD5;
import assesment.communication.util.MailSender;
import assesment.communication.ws.Identification;
import assesment.communication.ws.UserElearning;
import assesment.communication.ws.UserToRegister;
import assesment.presentation.translator.web.util.Util;

public class AnswerUtilAbitab extends AnswerUtil {

	//private final String WEBSERVICE = "http://localhost:8080/WebServices2/ElearningWSAssessment?wsdl";
	//private final String URL_REDIRECT = "http://localhost/loadAssesment.php";
	
	
    public AnswerUtilAbitab() {
        super();
    }

    public void feedback(AssesmentAccess sys,AssesmentAttributes assesment) throws Exception {
        UsReportFacade userReport = sys.getUserReportFacade();
        Text messages = sys.getText();
        UserSessionData userSessionData = sys.getUserSessionData();
        String login = userSessionData.getFilter().getLoginName();
        assesment.presentation.actions.report.Util util = new assesment.presentation.actions.report.Util();
        if(userReport.isAssessmentDone(login,assesment.getId(),userSessionData,assesment.isPsitest())) {
            UserData user = userReport.findUserByPrimaryKey(login,userSessionData);
            String name = user.getFirstName()+" "+user.getLastName();
            
            MailSender sender = new MailSender();
            Collection files = new LinkedList();
            
            String fileName = AssesmentData.FLASH_PATH+"/reports/resultado_"+name+".pdf";
            util.createUserReport(sys,fileName,assesment,user.getLoginName());
            files.add(fileName);

            fileName = AssesmentData.FLASH_PATH+"/reports/errores_"+name+".pdf";
            util.createUserErrorReport(sys,fileName,assesment,user.getLoginName());
            files.add(fileName);

            String redirect = null;

        	Collection lessons = sys.getTagReportFacade().getAssociatedLessons(login,assesment.getId(),userSessionData);
            //Collection lessons = new LinkedList();
            if(!lessons.contains(new Integer(400))){
            	lessons.add(new Integer(400));
            }
            if(!lessons.contains(new Integer(401))){
          		lessons.add(new Integer(401));
            }
            if(!lessons.contains(new Integer(505))){
          		lessons.add(new Integer(505));
            }
            if(!lessons.contains(new Integer(402))){
          		lessons.add(new Integer(402));
            }
            if(!lessons.contains(new Integer(507))){
          		lessons.add(new Integer(507));
          	}
        	int lessonsCount = lessons.size();
        
        	UserToRegister userToRegister = new UserToRegister();
        	String sex = "datatype.sex.male";

        	Format dateFormat;	            	
        	dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        	
        	// Dc5 Corporation Id del usuario, por defecto DEMO = 2
        	Integer dc5corporationId = new Integer(2105);
        	
            UserElearning userElearning = new UserElearning(
            		dateFormat.format(user.getBirthDate()),31,
        			user.getEmail(), user.getFirstName(), user.getLanguage(),
        			user.getLastName(),sex, dc5corporationId);
            userToRegister.setUser(userElearning);
            
            // Lecciones que tomará el usuario:
            LinkedList<Integer> idLessons = new LinkedList<Integer>();	       
            idLessons.addAll(lessons);
            userToRegister.setIdLessons(idLessons);
            
            /* Invocacion al WebService */ 
            ElearningWSAssessmentService service = new ElearningWSAssessmentService();
			ElearningWSAssessment port = service.getElearningWSAssessmentPort();	    			
			String endpointURL = WEBSERVICE;	    			
			BindingProvider bp = (BindingProvider)port;
			bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointURL);
			String idString = null;
			
			try{
				idString = port.addUserToCourse(userToRegister.toString());
			}catch(Exception e){
				e.printStackTrace();
			}
			
			/* fin Invocación al WebService*/ 
			
			UsABMFacade userABMFacade = sys.getUserABMFacade();
			if (idString != null){
    			Identification identification = new Identification(idString);
    			if (identification != null){
    				redirect = URL_REDIRECT+"?key="+identification.getKey()+"&id="+identification.getId();
    				// No se habilita el Elearning hasta que pague en Abitab.
    				Boolean eLearningEnabled = new Boolean(false);
    				userABMFacade.setRedirect(user.getLoginName(),redirect,
    						new Integer(identification.getId()),
    						eLearningEnabled,
    						assesment.getId(), userSessionData);
    				userABMFacade.userUpdate(user, userSessionData);
    			}
			}else{
				System.out.println("Error al agregar usuario al Elearning:"+user.getLoginName());
			}
            	
			if(!Util.empty(redirect)) {
/*	            switch (lessonsCount) {
	            	case 0:
    					userABMFacade.setElearningEnabled(user.getLoginName(), assesment.getId(), true, userSessionData);
	            		String body = "<html>" +
	            		"	<head>" +
	            		"	</head>" +
	            		"	<body bgcolor=\"#ffffff\" text=\"#000066\">";
	            		body += "<table>";
		                body += "<tr><td><table width='100%'>";
		            	body += "<tr><td><span style='font-family:Verdana; font-size:10;'>"+user.getFirstName()+" "+user.getLastName()+", felicitaciones por el alto rendimiento obtenido en el test de 'Auto Ayuda' Abitab - CEPA. A continuaci&oacute;n y a modo de cortes&iacute;a lo invitamos a participar de los 5 m&oacute;dulos b&aacute;sicos de la 'Capacitaci&oacute;n Virtual Personalizada'</span></td></tr>";
		            	body += "<tr><td>&nbsp;</td></tr>";
			            body += "<tr><td><a href='"+redirect+"'><span style='font-family:Verdana; font-size:10;'>"+redirect+"</span></a></td></tr>";
		            	body += "<tr><td>&nbsp;</td></tr>";
		            	body += "<tr><td><span style='font-family:Verdana; font-size:10;'>Hasta la pr&oacute;xima.</span></td></tr>";
		            	body += "<tr><td>&nbsp;</td></tr>";
		            	body += "<tr><td><img src=\"cid:figura1\" alt=\"\"></td></tr>";
		            	body += "</table></td></tr>";
		            	body += "</table>";
		            	body += "	</body>";
		            	body += "</html>";
		                sender.sendImage(user.getEmail(),FROM_NAME,"'Test de Conducci�n Segura' - Reportes",files,body,AssesmentData.FLASH_PATH+"/images/bottom_abitab.jpg");
		                sender.sendImage("federico.millan@cepasafedrive.com",FROM_NAME,"'Test de Conducci�n Segura' - Reportes",files,body,AssesmentData.FLASH_PATH+"/images/bottom_abitab.jpg");
		                sender.sendImage("jrodriguez@cepasafedrive.com",FROM_NAME,"'Test de Conducci�n Segura' - Reportes",files,body,AssesmentData.FLASH_PATH+"/images/bottom_abitab.jpg");
		                break;
	            	case 1: case 2: case 3:
    					userABMFacade.setElearningEnabled(user.getLoginName(), assesment.getId(), true, userSessionData);
	            		body = "<html>" +
	            		"	<head>" +
	            		"	</head>" +
	            		"	<body bgcolor=\"#ffffff\" text=\"#000066\">";
	            		body += "<table>";
		                body += "<tr><td><table width='100%'>";
		            	body += "<tr><td><span style='font-family:Verdana; font-size:10;'>"+user.getFirstName()+" "+user.getLastName()+", felicitaciones por el alto rendimiento obtenido en el test de 'Auto Ayuda' Abitab - CEPA. A continuaci&oacute;n le ofrecemos un link para ingresar a 'Capacitaci&oacute;n Virtual Personalizada' con las lecciones que le fueron asignadas, el mismo fue creado especialmente para usted con  los resultados del test de 'Auto Ayuda'.</span></td></tr>";
		            	body += "<tr><td>&nbsp;</td></tr>";
			            body += "<tr><td><a href='"+redirect+"'><span style='font-family:Verdana; font-size:10;'>"+redirect+"</span></a></td></tr>";
		            	body += "<tr><td>&nbsp;</td></tr>";
		            	body += "<tr><td><span style='font-family:Verdana; font-size:10;'>Hasta la pr&oacute;xima.</span></td></tr>";
		            	body += "<tr><td>&nbsp;</td></tr>";
		            	body += "<tr><td><img src=\"cid:figura1\" alt=\"\"></td></tr>";
		            	body += "</table></td></tr>";
		            	body += "</table>";
		            	body += "	</body>";
		            	body += "</html>";
		                sender.sendImage(user.getEmail(),FROM_NAME,"'Test de Conducci�n Segura' - Reportes",files,body,AssesmentData.FLASH_PATH+"/images/bottom_abitab.jpg");
		                sender.sendImage("federico.millan@cepasafedrive.com",FROM_NAME,"'Test de Conducci�n Segura' - Reportes",files,body,AssesmentData.FLASH_PATH+"/images/bottom_abitab.jpg");
		                sender.sendImage("jrodriguez@cepasafedrive.com",FROM_NAME,"'Test de Conducci�n Segura' - Reportes",files,body,AssesmentData.FLASH_PATH+"/images/bottom_abitab.jpg");
		                break;
	            	default:*/
    					userABMFacade.setElearningEnabled(user.getLoginName(), assesment.getId(), false, userSessionData);
	            		String body = "<html>" +
	            		"	<head>" +
	            		"	</head>" +
	            		"	<body bgcolor=\"#ffffff\" text=\"#000066\">" +
	            		"		<table>" +
	            		"			<tr>" +
	            		"				<td>" +
	            		"					<span style='font-family:Verdana; font-size:10;'>Estimado/a " +Util.formatHTML(user.getFirstName())+" "+Util.formatHTML(user.getLastName())+",</span>"+
	            		"				</td>" +
	            		"			</tr>" +
	            		"			<tr>" +
	            		"				<td>" +
	            		"					<img src=\"cid:figura1\" alt=\"\">" +
	            		"				</td>" +
	            		"			</tr>" +
	            		"		</table>" +
	            		"	</body>" +
	            		"</html>";
	            	
		                // Traemos de la BD el numero de lote de email a enviar:
		                PropertyReportFacade propertyReportFacade = sys.getPropertyReportFacade();
		                PropertyData property = propertyReportFacade.findPropertyByPrimaryKey(PropertyData.LOTE_ABITAB, userSessionData);
		                String lote = property.getValue();
		                
		                // Mandamos email a Abitab con la información del participante:
		                importDataToAbitab(sender, user, lote);
		                 
		                // Actualizamos el numero de lote de abitab (incrementandolo en 1):
		                property.setValue( String.valueOf( (Integer.valueOf(lote).intValue() + 1) ) );
		                PropertyABMFacade propertyABMFacade = sys.getPropertyABMFacade();
		                propertyABMFacade.propertyUpdate(property, userSessionData);
		                
		                // Fin email a abitab
		
		                // Envaimos los emails al usuario y una copia a Juan y Fede
		                String[] senderAddress = sender.getAvailableMailAddress();
		                if(!Util.empty(senderAddress[0])) {
		                	sender.sendImage(user.getEmail(),FROM_NAME,senderAddress[0],senderAddress[1],"'Test de Conducci�n Segura' - Reportes",files,body,AssesmentData.FLASH_PATH+"/images/mail_abitab.jpg");
		                }
/*		                break;
	            }*/
			}
        	
        }
    }
    
    public void resend(AssesmentAccess sys,AssesmentAttributes assesment, String login, String email) throws Exception {
    	
    }

    /**
	 * Este metodo es el que carga los datos del participante y genera el archivo
	 * adjunto para enviar a abitab.
	 */
	private void importDataToAbitab(MailSender sender, UserData user , String lote){
		/*
		 * La idea es generar los datos obligatorios desde
		 * la Base de Datos del assessment y los datos
		 * constantes se obtienen de las constantes static
		 * de AbitabRegistry.java.
		 * 
		 * El numero de email a mandar (o lote) de deberá guardar en un archivo
		 * de texto o en la base de datos y se ira incrementando en 1 con cada envio.
		 */
		
		AbitabRegistry registro = new AbitabRegistry();
		
		/*
		 * Cargo los datos fijos
		 */
		registro.setTipoDeGestion(AbitabRegistry.TIPO_DE_GESTION);
		registro.setEmpresa(AbitabRegistry.EMPRESA);
		registro.setNroEmpresa(AbitabRegistry.NRO_EMPRESA);
		registro.setSubEmpresa(AbitabRegistry.SUB_EMPRESA);
		registro.setTipoDeDocumento(AbitabRegistry.TIPO_DE_DOCUMENTO_FACUTRA);
		registro.setImporte(AbitabRegistry.IMPORTE);
		registro.setImporteTotal(0);
		registro.setImporteMinimo(0);
		registro.setTipoMoneda(AbitabRegistry.TIPO_MONEDA_PESOS);
		registro.setBonificacion(0);
		registro.setDescuento(0);
		registro.setDiasGracia(0);
		registro.setMora(0);
		registro.setCotizacion(0);
		registro.setCuota(0);
		registro.setRemitente("");
		registro.setRuc("");
		registro.setRazonSocial("");
		registro.setDireccion("");
		registro.setCodigoPostal("");
		registro.setConcepto1(AbitabRegistry.CONCEPTO1);
		registro.setConcepto2(AbitabRegistry.CONCEPTO2);
		registro.setConcepto3(AbitabRegistry.CONCEPTO3);
		registro.setConcepto4(AbitabRegistry.CONCEPTO4);
		registro.setObservacion1("");
		registro.setObservacion2("");
		registro.setControlCuotas(AbitabRegistry.CONTROL_CUOTAS_NO_CONTROLA);
		
		
		/*
		 * Cargo los datos del participante 
		 */
		
		registro.setLote(Integer.valueOf(lote).intValue()); // Numero de email enviado
		
		// En identificacion ponesmos sola la cedula
		// que la obtenemos del loginname. El loginname de los usuarios e abitab tiene la forma
		// abitab_cedula_numero
		int posFirst = 6; // positcion del primer underscore
		int posLast = user.getLoginName().lastIndexOf("_"); // posicion del ultimo underscore
		
		String cedula = "";
		if (user.getLoginName().contains("_")){
			cedula = user.getLoginName().substring(posFirst+1,posLast);
		}else{
			cedula = user.getLoginName();
		}
		registro.setIdentificacion(cedula); // ID del participante, o cedula
		
		registro.setNombre(user.getFirstName()); // Nombre del participante
		registro.setApellido(user.getLastName()); // Apellido del participante
		
		MD5 md5 = new MD5();
		/**
		 * Mandamos en nro de documento los primeros 6 caracteres del MD5
		 * de la cedula de identidad del particpante o del loginname.
		 */
		String codigoWeb = md5.encriptar(user.getLoginName()).substring(0, 6); // 16 caracteres maximo
		registro.setNroDocumento( codigoWeb ); // ID del participante, o cedula
		
		// Fecha de Vencimiento de la Factura (Campo Obligatorio, 1 año?)
		Calendar fechaDeVencimiento = Calendar.getInstance();
		fechaDeVencimiento.add(Calendar.YEAR, 1);
		registro.setFechaDeVencimiento(fechaDeVencimiento.getTime());
		
		// Fecha de Corte del servicio: No aplica o 1 año?
		Calendar fechaCorte = Calendar.getInstance();
		fechaCorte.add(Calendar.YEAR, 1);		
		registro.setFechaCorte(null);
		
		// Fecha de Emisión de la Factura, cuando termina el assessment? Hoy
		Calendar fechaDeEmision = Calendar.getInstance();
		registro.setFechaDeEmision(fechaDeEmision.getTime());
		
		/*
		 * Fin Carga de datos del participante 
		 */
		
		
		createAbitabAttachment(registro,sender);
	
	}
	
	/**
	 * Este método genera el archivo CEP_ddmmaa_x.ONL a enviar como adjunto en el email a Abitab.
	 * 
	 */
	private void createAbitabAttachment(AbitabRegistry registro, MailSender sender){
		
		try {
			
			/**
			 * Armo el archivo con la informacion:
			 */
			String primeraLinea = "13|16|1\n";
			String segundaLinea = registro.toLine()+"\n";
			int cantidadRegistros = 1;
			String sumatoriaImportesMonedaPesos = registro.convertNumberToString(registro.getImporte(),2, false);
			String terceraLinea = "#|" + AbitabRegistry.TIPO_MONEDA_PESOS + "|" + 
				cantidadRegistros + "|" + sumatoriaImportesMonedaPesos+"\n";
			
			StringBuffer fileStream = new StringBuffer();
			fileStream.append(primeraLinea);
			fileStream.append(segundaLinea);
			fileStream.append(terceraLinea);
			
			/**
			 * Guardo el Archivo en el disco duro:
			 */
			
			Calendar today = Calendar.getInstance();
			String fecha = AbitabRegistry.convertDateToString(today.getTime()).replace("/","");
			
			String fileName = AbitabRegistry.EMPRESA+"_"+fecha+"_"+registro.getLote()+".ONL";
	    	
			File f = new File( AssesmentData.FLASH_PATH + AbitabRegistry.DIR_ABITAB_SENDED + fileName);
	        
			FileOutputStream fos = new FileOutputStream(f);
			 
			fos.write(fileStream.toString().getBytes());
			fos.flush();
	        fos.close();
	        
	        // Log, muestro en pantalla el resultado
	        System.out.println("FileName: "+fileName);
	        System.out.println("Contenido: \n"+fileStream.toString());
	       
	        /**
	         * Armo el email:
	         */
	        String body ="CODEMPRESA="+AbitabRegistry.EMPRESA;
	        
	        LinkedList<String> files = new LinkedList();
	        //  fileName = AssesmentData.FLASH_PATH+"/abitab/errores_"+name+".pdf";
	        files.add(AssesmentData.FLASH_PATH + AbitabRegistry.DIR_ABITAB_SENDED + fileName);
	        
	        
	        sender.send(AbitabRegistry.EMAIL_ABITAB_PARA_ENVIO,
	        		AbitabRegistry.FROM_NAME,
	        		AbitabRegistry.EMAIL_CEPA_PARA_ENVIAR_A_ABITAB,
            		AbitabRegistry.EMAIL_CEPA_PARA_ENVIAR_A_ABITAB_PASSWORD,            		
	        		//messages.getText("assessment.reports")+" - Abitab - "+ name
	        		"CEPA Import Data",body,
	        		files);
            
	        
	        sender.send("jrodriguez@cepasafedrive.com",
	        		AbitabRegistry.FROM_NAME,
	        		AbitabRegistry.EMAIL_CEPA_PARA_ENVIAR_A_ABITAB,
            		AbitabRegistry.EMAIL_CEPA_PARA_ENVIAR_A_ABITAB_PASSWORD,            		
	        		//messages.getText("assessment.reports")+" - Abitab - "+ name
	        		"CEPA Import Data",body,
	        		files);
	        
	        sender.send("gbenaderet@cepasafedrive.com",
	        		AbitabRegistry.FROM_NAME,
	        		AbitabRegistry.EMAIL_CEPA_PARA_ENVIAR_A_ABITAB,
            		AbitabRegistry.EMAIL_CEPA_PARA_ENVIAR_A_ABITAB_PASSWORD,            		
	        		//messages.getText("assessment.reports")+" - Abitab - "+ name
	        		"CEPA Import Data",body,
	        		files);
	        
	            
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
