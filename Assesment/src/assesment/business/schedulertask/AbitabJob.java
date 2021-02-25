package assesment.business.schedulertask;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.search.FromStringTerm;
import javax.mail.search.SearchTerm;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import assesment.business.administration.property.PropertyABMFacade;
import assesment.business.administration.property.PropertyReportFacade;
import assesment.communication.administration.AbitabRegistry;
import assesment.communication.administration.property.PropertyData;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.user.UserData;
import assesment.communication.util.MD5;
import assesment.communication.util.MailSender;
import assesment.persistence.schedulertask.PersistenceABMTask;
import assesment.persistence.schedulertask.PersistenceReportTask;

import com.sun.mail.pop3.POP3SSLStore;
 
/**
 * Tareas Programadas:
 * 
 * Esta clase ejecuta una tarea programada que revisa periodicamente
 * un emial "receptor" de Abitab, y procesa la información de 
 * los pagos para habilitar el acceso al elearning después de haber
 * sido pagado.
 * 
 * Bibliografia:
 * http://www.quartz-scheduler.org/  
 * 		-> Pagina Oficial de Quartz
 * http://www.javisjava.com/blog/quartz 
 * 		-> Idea General mejor explicada para version anterior de quartz
 * http://www.docjar.com/docs/api/org/quartz/ee/servlet/QuartzInitializerListener.html 
 * 		-> Configuracion para web.xml
 * http://forums.sun.com/thread.jspa?threadID=5267916
 * 		-> Mail con Authetificacion SSL, por ejemplo GMAIL
 * 
 * @author gbenaderet
 *
 */
public class AbitabJob implements Job {
 
	/**
	 * Ejecuta la Tarea Programada
	 */
    public void execute(JobExecutionContext context) throws JobExecutionException {
        
    	// Simpatico saludo a la tarea programada utilizando parametros del xml.
    	System.out.println("AbitabJob.java: Hola "
            + context.getJobDetail().getJobDataMap().getString("nombre"));	

        /*
         * Procesar los emails:
         * Recibo las personas que pagaron y les seteo en el extra data del usuario el valor
         * UserData.ELEARNING_VALID_USER
         */
        fectchingMessages();
        
        /*
         * Procesar las personas que no pagaron.
         */
        notPayUsers();
    }
    
    
    
    
    /**
     * Configuraciones del Servidor de Correos de CEPA Locaweb
     */
    // private static final String POP3 = "pop.cepasafedrive.com";
    // private static final String FROM_DIR = "abitab@cepasafedrive.com"; // Email de CEPA que recibe los emails de Abitab:
    // private static final String PASSWORD = "abitab2010";

    /**
     * Configuraciones del Servidor de Correos de CEPA GMAIL
     */
     private static final String FROM_DIR = "abitab@cepaebtw.com";
     private static final String PASSWORD = "E99688";
     private static final String POP3 = "pop.gmail.com";
    
    /**
     * Email de Abitab desde donde se envian los emails que recibiremos:
     */
	private static final String FROM_ABITAB = "gestion.automatica@abitab.com.uy"; // gbenaderet@cepasafedrive.com
 
	/**
	 * Otras constantes
	 */
	private static final int EOF = 65535;
	// private static final String ASSESSMENT_ACCESS_USER = "jrodriguez";
   
	/**
	 * Directorio deonde se guardaran los archivos de los emails.
	 * A partir de ahora se guardan fijos los archivos !!
	 */
	private static String DIRECTORIO_ARCHIVOS = AssesmentData.FLASH_PATH + AbitabRegistry.DIR_ABITAB_RECEIVED;; 
		
	
	/**
	 * Leer los emails de la casilla de correos:
	 */
	private void fectchingMessages(){
		
		try{
			String host = POP3;
			String username = FROM_DIR;
			String password = PASSWORD;
		
			// Create empty properties
			Properties props = new Properties();
			// Get session
			Session session;
			// Get the store
			Store store;
			
			if (host.contains("gmail.com")){
				String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory"; 	        
		        
		        props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
		        props.setProperty("mail.pop3.socketFactory.fallback", "false");
		        props.setProperty("mail.pop3.port",  "995");
		        props.setProperty("mail.pop3.socketFactory.port", "995");
		        
		        URLName url = new URLName("pop3", POP3, 995, "", username, password);
		        
		        session = Session.getInstance(props, null);
		        store = new POP3SSLStore(session, url);
		        store.connect();

			}else{
				session = Session.getDefaultInstance(props, null);
				// Get the store
				store = session.getStore("pop3");
				store.connect(host, username, password);
			}		
			
		
			// Get folder
			Folder folder = store.getFolder("INBOX");
			// folder.open(Folder.READ_ONLY);
			folder.open(Folder.READ_WRITE); // para poder borrar el email
		
			// Get directory
			SearchTerm st = new FromStringTerm(FROM_ABITAB);
			Message[] message = folder.search(st); // Aca ya puedo filtrar los emails que traigo
			// Message[] message = folder.getMessages(); 
		
		
			if (message.length > 0){
				System.out.println("AbitabJob.java: Mails en: "+username);
			
				for (int j = 0, n = message.length; j < n; j++) {
					
					InternetAddress from = (InternetAddress) message[j].getFrom()[0];
					String subject = message[j].getSubject();
					
					System.out.println("AbitabJob.java: "+j + ": " + from  + "\t" + subject);
				
					// Procesar Attachment si el email es de abitab:
					if (from.equals(new InternetAddress(FROM_ABITAB)) &&
						Multipart.class.isInstance(message[j].getContent()) ){
						
						Multipart mp = (Multipart)message[j].getContent();
		
						for (int i = 0, m = mp.getCount(); i < m; i++) {
						  
							Part part = mp.getBodyPart(i);
							
		
							String disposition = part.getDisposition();
		
							if ( (disposition != null) && 
						         (disposition.equals(Part.ATTACHMENT) || disposition.equals(Part.INLINE) )
					
						     ){
						    	  
								String filePath = saveFile(part.getFileName(), part.getInputStream());
								recibirArchivoCobranzaDesdeAbitab(filePath);
						    	 
								// Borro el email.
								message[j].setFlag(Flags.Flag.DELETED, true);
								
							}else if (disposition == null) {
							  
								// Check if plain
								MimeBodyPart mbp = (MimeBodyPart)part;
								if (mbp.isMimeType("text/plain")) {
									// Handle plain
								} else {
									// Special non-attachment cases here of 
									// image/gif, text/html, ...
								}
							// ...
							}
							
						} // end for
						
					} // if from abitab
					
					// Fin procesar atacchment si es email de abitab
					
				}// end for i = 0
				
				System.out.println("AbitabJob.java: Fin de los emails...\n\n");
			}else{
				System.out.println("AbitabJob.java: No hay mensajes nuevos.");
			}		


			// Close connection 
			folder.close(true); // true para guardar el cambio del borrado del email.
			store.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Este metodo lee el archivo XXX_ddmmaa.TXT
	 * y crea un objeto AbitabPayment que es utilizado luego para
	 * registrar en la Base de Datos el pago realizado.
	 * Asi mismo habilita el elearning al participante.
	 */
	private void recibirArchivoCobranzaDesdeAbitab(String filePath){
		
		try{

//			/* Variables para conectarse a la BD */
//			AssesmentAccess sys = new AssesmentAccess(ASSESSMENT_ACCESS_USER);			
//        	UsReportFacade userReport = sys.getUserReportFacade();
//        	UsABMFacade userABM = sys.getUserABMFacade();
//        	UserSessionData userSessionData = sys.getUserSessionData();
        	
			
			
			/* Variables para conectarse a la BD */
			PersistenceABMTask abmTask = new PersistenceABMTask();			
			PersistenceReportTask reportTask = new PersistenceReportTask();
			
			
			/* Variables para el manejo de los archivos */
			File f = new File(filePath);
			FileInputStream fis = new FileInputStream(f);
			StringBuffer word = new StringBuffer();
	        LinkedList<AbitabPayment> cobranzas = new LinkedList<AbitabPayment>();
	        
	        char car;
	        car = 0;
	        
	        while(car != EOF) {
	        	String campo = "";
	            int index = 0;
	            car = 0;
	            
	            AbitabPayment cobranza = new AbitabPayment();
	            
	            while(car != '\n' && car != EOF) {
	            	
	            	car = (char)fis.read();
	                if(car == '|' || car == '\n') {
	                	campo = word.toString().trim();
	                	if(campo != null && campo.length() > 0) {
	                		
	                		switch(index) {
	                    		case 0:
	                    			cobranza.setCodigoEmpresa(campo);
	                    			break;
	                    		case 1:
	                    			cobranza.setLote(Integer.valueOf(campo).intValue());
	                    			break;
	                    		case 2:
	                    			cobranza.setIdentificacion(campo);
	                    			break;
	                    		case 3:
	                    			cobranza.setNroDocumento(campo);
	                    			break;
	                    		case 4:
	                    			cobranza.setMoneda(Integer.valueOf(campo).intValue());
	                    			break;
	                    		case 5:
	                    			cobranza.setImportePago(Double.valueOf(campo).doubleValue());
	                    			break;
	                    		case 6:
	                    			cobranza.setCuota(Integer.valueOf(campo).intValue());
	                    			break;
	                    		case 7:
	                    			cobranza.setFechaDePago(campo);
	                    			break;
	                    		case 8:
	                    			cobranza.setAgencia(Integer.valueOf(campo));
	                    			break;
	                    		case 9:
	                    			cobranza.setSubAgencia(Integer.valueOf(campo));
	                    			
	                		}
	                	}
	                	word = new StringBuffer();	                        
	                    index++;
	                }else {
	                    word.append(car);
	                }
	            }
	            if (cobranza != null && cobranza.getCodigoEmpresa() != null){
	            	cobranzas.add(cobranza);
	            }
	        }			 
	        
	        // Log, muestro en pantalla el resultado
	        System.out.println("AbitabJob.java: FileName: "+filePath);
	        System.out.println("AbitabJob.java: Cantidad de Cobranzas: "+cobranzas.size());
	        Iterator<AbitabPayment> it = cobranzas.iterator();
	        while (it.hasNext()){
	        	AbitabPayment a = it.next();
	        	System.out.println("AbitabJob.java: "+ a.toLine());

	        	boolean updated = abmTask.userUpdateElearningEnabled(a.getIdentificacion(), true);
	   
	        	if (updated){
	        		System.out.println("AbitabJob.java: El participante: "+
	            		" (CI: "+a.getIdentificacion()+") pagó el eLearning. \n"+
	            		"Se le habilito el acceso.");
	        	}else{
	        		System.out.println("AbitabJob.java: No se pudo actualizar el pago del participante: "+
		            		" (CI: "+a.getIdentificacion()+").  \n");
	        	}
	        	
	        	
	        }
		}catch (Exception e){
			e.printStackTrace();
		}
	}
		
	/**
	 * Guarda el attachment en el disco duro y devuelve el path del archivo
	 * @param filename
	 * @param inputStream
	 * @return
	 */
	private String saveFile(String filename, InputStream inputStream){
		
		try{
			// from saveFile()
			File file = new File(DIRECTORIO_ARCHIVOS + filename);
			for (int i=0; file.exists(); i++) {
			  file = new File(DIRECTORIO_ARCHIVOS + filename+i);
			}
			System.out.println("AbitabJob.java: Filename: "+file.getName());
			
			// Recorro el attachment y lo coloco en un StringBuffer:
			char c = 0;
			StringBuffer buf = new StringBuffer();
			while (c != EOF){
				 c = (char)inputStream.read();
				 if (c != EOF) {
					buf.append(c);
				 }
			}
			
			// Guardo el contenido del attachment en el archivo filename:
			FileOutputStream fos = new FileOutputStream(file);
			 
			fos.write(buf.toString().getBytes());
			fos.flush();
	        fos.close();
			
	        return file.getPath();
	        
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * Procesa los usuarios que no pagaron (luego de recibido el pago del dia actual)
	 * y vuelve a enviar a Abitab el listado de usuarios pendientes de pago.
	 */
	private void notPayUsers(){
		try{
   		    
			PersistenceReportTask reportTask = new PersistenceReportTask();
			PersistenceABMTask abmTask = new PersistenceABMTask();
			LinkedList<UserData> users = reportTask.notPayUsers();
			// Traemos de la BD el numero de lote de email a enviar:
            PropertyData property = reportTask.findPropertyByPrimaryKey(PropertyData.LOTE_ABITAB);
			String lote = (property != null)?property.getValue():"1";			
			
			LinkedList<AbitabRegistry> registros = null;
			if (users != null && users.size() > 0){
				Iterator<UserData> it = users.iterator();
				registros = new LinkedList<AbitabRegistry>();
				
				while (it.hasNext()){
					
					UserData user = it.next();
					AbitabRegistry registro = getUserRegistry(user,lote);
					registros.add(registro);
					
				}
				
				if (registros.size() > 0){
					/**
					 * Crea el attachment con el archivo de los usuarios que no pagaron
					 * y lo envia a abitab.
					 */
					createAbitabAttachment(registros, lote);
				}
								  
                // Actualizamos el numero de lote de abitab (incrementandolo en 1):
                property.setValue( String.valueOf( (Integer.valueOf(lote).intValue() + 1) ) );
                abmTask.propertyUpdate(property);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Genera un AbitabRegistry del usuario que aún no pago a partir del UserData
	 * @param user
	 * @param lote
	 * @return
	 */
	private AbitabRegistry getUserRegistry(UserData user,String lote){
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
		return registro;
	}

	/**
	 * Este método genera el archivo CEP_ddmmaa_x.ONL a enviar como adjunto en el email a Abitab.
	 * 
	 */
	private void createAbitabAttachment(LinkedList<AbitabRegistry> registros, String lote){
		
		try {
			
			
			MailSender sender = new MailSender();
			/**
			 * Armo el archivo con la informacion:
			 */
			String primeraLinea = "13|16|1\n";
			StringBuffer segundasLineas = new StringBuffer();
			Iterator<AbitabRegistry> it = registros.iterator();
			Double importeTotal = new Double(0);
			while (it.hasNext()){
				AbitabRegistry registro = it.next();
				segundasLineas.append(registro.toLine()+"\n");
				importeTotal += registro.getImporte();
			}
				
			
			int cantidadRegistros = registros.size();//1;
			// String sumatoriaImportesMonedaPesos = AbitabRegistry.convertNumberToString(registro.getImporte(),2, false);
			String sumatoriaImportesMonedaPesos = AbitabRegistry.convertNumberToString(importeTotal,2, false);
			String terceraLinea = "#|" + AbitabRegistry.TIPO_MONEDA_PESOS + "|" + 
				cantidadRegistros + "|" + sumatoriaImportesMonedaPesos+"\n";
			
			StringBuffer fileStream = new StringBuffer();
			fileStream.append(primeraLinea);
			fileStream.append(segundasLineas);
			fileStream.append(terceraLinea);
			
			/**
			 * Guardo el Archivo en el disco duro:
			 */
			
			Calendar today = Calendar.getInstance();
			String fecha = AbitabRegistry.convertDateToString(today.getTime()).replace("/","");
			
			String fileName = AbitabRegistry.EMPRESA+"_"+fecha+"_"+lote+".ONL";
	    	
			File f = new File( AssesmentData.FLASH_PATH + AbitabRegistry.DIR_ABITAB_SENDED + fileName);
	        
			FileOutputStream fos = new FileOutputStream(f);
			 
			fos.write(fileStream.toString().getBytes());
			fos.flush();
	        fos.close();
	        
	        // Log, muestro en pantalla el resultado
	        System.out.println("Usuarios que NO pagaron en Abitab: ");
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
	        		"CEPA Import Data",body,
	        		files);
            
	        
	        sender.send("jrodriguez@cepasafedrive.com",
	        		AbitabRegistry.FROM_NAME,
	        		AbitabRegistry.EMAIL_CEPA_PARA_ENVIAR_A_ABITAB,
            		AbitabRegistry.EMAIL_CEPA_PARA_ENVIAR_A_ABITAB_PASSWORD,            		
	        		"CEPA Import Data",body,
	        		files);
	        
	        sender.send("gbenaderet@cepasafedrive.com",
	        		AbitabRegistry.FROM_NAME,
	        		AbitabRegistry.EMAIL_CEPA_PARA_ENVIAR_A_ABITAB,
            		AbitabRegistry.EMAIL_CEPA_PARA_ENVIAR_A_ABITAB_PASSWORD,            		
	        		"CEPA Import Data",body,
	        		files);
	        
	            
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
