package assesment;

import java.io.File;
import java.io.FileInputStream;
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

public class MailingCiemsa {
	
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
	public static final String MAILSERVER = "smtp.gmail.com";
	public static final String MAILSERVER2 = "smtp.cepainternational.com";
	public static final String MAILSERVER_INMOTION = "mail.cepalogistica.com";

	protected static final String FROM_NAME = "CEPA - Driver Assessment";
    protected static final String FROM_DIR = "da@cepainternational.com";
    // protected static final String PASSWORD = "dacepa09";
	

    public static void main(String[] args) {

        try {
	        int EOF = 65535;

	        Class.forName("org.postgresql.Driver");
            //Connection conn1 = DriverManager.getConnection("jdbc:postgresql://173.247.255.40:5432/assesment","postgres","pr0v1s0r1A");
            Connection conn1 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/assesment","postgres","pr0v1s0r1A");
            Statement st = conn1.createStatement();
            Statement st2 = conn1.createStatement();
                        
            File fin = new File("C:/Users/Juan Rodriguez/Desktop/mailingciemsa.csv");
	        FileInputStream fis = new FileInputStream(fin);

	        String[][] values = new String[125][4];
	        StringBuffer word = new StringBuffer();
	        char car = 0;
	        int i = 0;
	        while(car != EOF) {
	            car = 0;
	            int j = 0;
	            while(car != '\n' && car != EOF) {
	                car = (char)fis.read();
	                if(car == ';' || car == '\n') {
	                	if(word.toString().trim().length() > 0) {
	                		values[i][j] = word.toString().trim();
	                		word = new StringBuffer();
	                		j++;
	                	}
	                }else {
	                    word.append(car);
	                }
	            }
	            i++;
	        }

    		int index = 0;
	    	for(i = 0; i < Math.min(1000,values.length); i++) {
	    		String email = values[i][0].trim();
	    		String nombre = values[i][1].trim();
	    		String login = values[i][2].trim();
	    		String password = values[i][3].trim();

	    		ResultSet set = st.executeQuery("select emailsended,enddate from users u join userassesments ua on u.loginname = ua.loginname where u.loginname = '"+login+"'");
	    		set.next();
	    		if(set.getString(2) == null && (set.getString(1) == null || !set.getString(1).equals("2012-11-14"))) {
	    			String subject;
		    		MailSender sender;
		    		if(email != null && email.length() > 0) {
		                try {
			        		//email = "jrodriguez@cepasafedrive.com";
			        		//email = "ecodina@ciemsa.com.uy";
		                	//email = "federico.millan@cepasafedrive.com";
		        			subject = "Ciemsa - Programa de Conducción Segura – Invitación para Driver Assessment On Line";
	
		        			String  body = getMail(login,password);
	
		        			sender = new MailSender();
		        			String[] address = sender.getAvailableMailAddress();
		        			if(address[0] != null) {
		        				String[] ccAddress = new String[0];
		        				/*	        				String[] ccAddress = new String[2];
		        				ccAddress[0] = "federico.millan@cepasafedrive.com";
		        				ccAddress[1] = "triva@cepasafedrive.com";
/*		        				if(emailCopy != null && emailCopy.length() > 0 && !emailCopy.equals("---")) {
		        					ccAddress = new String[1];
		        					ccAddress[0] = email;
		        				}
*/		        				
		        				// System.out.println("Aca mandaria el email...");
		        				
		        				try {
		        					sender.sendImage(email, "CEPA Driver Assessment", address[0], address[1], 
		        							subject, new LinkedList(), body, AssesmentData.FLASH_PATH+"/images/template_ciemsa.jpg",ccAddress);
		        					
		        					System.out.println("'"+login+"', ");
		        		    		index++;
		        					
		        					st2.executeUpdate("UPDATE users SET emailsended = CURRENT_DATE where loginname='"+login+"'");
		        					
		        				}catch (Exception e) {
		        					e.printStackTrace();
		        					System.out.println("EXCEPCION "+login);	
		        				}
		    		            
				        		//Thread.sleep(50000);	        			
		        				
		        					
		        			}
	
			            }catch (Exception e) {
		        			System.out.println("ERROR '"+email+"',");
		        			e.printStackTrace();	
		        			
		        			
			            }
		    		}
	    		}
		    	System.out.println(index);
	    	}
    	}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getMail(String usuario, String password) {
		String mail = "<html>";
		
			mail += "	<head>" +
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
			"				<td>"+
			"				<div style=\"margin:0cm 0cm 0.0001pt;font-family:Calibri,sans-serif\">" +
			"<p class=\"MsoNormal\">" +
			"	<span lang=\"PT-BR\" style=\"font-family:Calibri;font-size:18px\">" +
			"		CIEMSA y CSI, como parte del proceso de profesionalizaci&oacute;n en la gesti&oacute;n de la flota de veh&iacute;culos,<br>" +
			" 		la seguridad de nuestros conductores y del entorno donde trabajamos, ha realizado <br>" +
			"		 un convenio de capacitaci&oacute;n con CEPA.<br>" +
			"<br>" +
			"		Como primer paso estaremos realizando una evaluaci&oacute;n de riesgos de nuestros conductores.<br>" +
			"		Para ello cada uno de ustedes deber&aacute; completar el cuestionario de evaluaci&oacute;n Driver Assessment. <br>" +
			" 		Este es un cuestionario autoadministrado que completar&aacute;n desde su PC.<br>" +
			"<br>" +
			"		Tanto el Usuario como la clave son de exclusivo uso personal e intransferible y los<br>" +
			"	 	resultados de la evaluaci&oacute;n ser&aacute;n de car&aacute;cter confidencial.<br>" +
			"<br>" +
			"		Estimamos que completar todo el cuestionario insume entre 20 y 30 minutos.<br>" +
			"<br>" +
			"		<b>IMPORTANTE:</b> Una vez iniciado el cuestionario deber&aacute; completarse en la misma<br>" +
			"		instancia (pues de lo contrario, se perder&aacute; la clave de acceso y no se dispondr&aacute;<br>" +
			"		del resultado).<br>" +
			"<br>" +
			"		<b>Plazo: hasta el 20/11/2012</b><br>" +
			"<br>" +
			"		Por consultas, estoy a disposici&oacute;n de ustedes. Gracias desde ya por su valiosa participaci&oacute;n.<br>" +
			"		Saludos,<br>"+
			"		Ernesto Codina"+
			"<br>" +
			"	</span>" +
			"</p>" +
			"<p class=\"MsoNormal\">" +
			"<span style=\"font-size:18px\"><b><span lang=\"PT-BR\" style=\"font-family:Calibri\">" +
			"<br>" +
			"	</span>" +
			"</p>" +
			"<div>" +
			"<div style=\"font-family:Helvetica\">" +
			"<div style=\"margin:0cm 0cm 0.0001pt;font-size:11pt;font-family:Calibri,sans-serif\">" +
			"<p class=\"MsoNormal\">" +
			"<span lang=\"PT-BR\" style=\"font-family:Calibri;font-size:4px\"></span>" +
			"</p>" +
			"<p class=\"MsoNormal\" >" +
			"	<span lang=\"PT-BR\" style=\"font-family:Calibri;font-size:18px\">" +
			"		<b>Utilice el link que aparece abajo para la realizaci&oacute;n de su Driver Assessment,<br> con su usuario y clave. </b>"+
			"<br>" +
			"		<a href=\"http://da.cepasafedrive.com/\" target=\"_blank\">" +
			"			<b><span style=\"font-family:Helvetica,sans-serif;color:purple\">http://da.cepasafedrive.com</span></b>" +
			"		</a>" +
			"<br>" +
			"		<b>Usuario: " +usuario+"</b>" +
			"<br>" +
			"		<b>Clave: "+password+"</b>" +
			"	</span>" +
			"</p>" +
			"</div>" +
			"<p class=\"MsoNormal\">" +
			"<span style=\"font-size:18px\"><b><span lang=\"PT-BR\" style=\"font-family:Calibri\">" +
			"Para leer y consultar la Pol&iacute;tica de Seguridad:&nbsp;</span></b><br>" +
			"<a href=\"https://portal.csi.com.uy/documentacion/GestionRecursosMateriales/Maquinaria/Veh%C3%ADculos/Publico/Pol%C3%ADtica%20de%20Asignación%20y%20Uso%20de%20Veh%C3%ADculos%20-%20Versión%20para%20Usuarios%20No%20autorizantes.doc\" target=\"_blank\">" +
			"<span lang=\"PT-BR\" style=\"font-family:Calibri\">Pol&iacute;tica de Asignaci&oacute;n y Uso de Veh&iacute;culos- Versi&oacute;n para Usuarios No autorizantes</span>" +
			"</a>" +
			"</span>" +
			"<span lang=\"PT-BR\" style=\"font-size:11pt;font-family:Calibri\">" +
			"<u></u><u></u>" +
			"</span>" +
			"</p>" +
			"<p class=\"MsoNormal\">" +
			"	<span lang=\"PT-BR\" style=\"font-family:Calibri;font-size:4px\"></span>" +
			"</p>" +
			"<p class=\"MsoNormal\" >" +
			"<span lang=\"PT-BR\" style=\"font-family:Calibri;font-size:18px\">" +
			"<b>Nuevamente, agradecemos su valiosa participaci&oacute;n.</b>" + 
			"<u></u><u></u>" +
			"</span>" +
			"</p>" +
			"</div>				" +
			"</td>" +
			"			</tr>" +
			"		</table>" +
			"	</body>" +
			"</html>";
		return mail;
	}
}
