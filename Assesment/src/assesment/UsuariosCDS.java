package assesment;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import assesment.communication.assesment.AssesmentData;
import assesment.communication.util.MD5;

public class UsuariosCDS {
	
    protected static final String SMTP = "smtp.cepasafedrive.com";
    protected static final String FROM_NAME = "CEPA - Driver Assessment";
    protected static final String FROM_DIR = "da@cepasafedrive.com";
    protected static final String PASSWORD = "dacepa09";

    public static void main(String[] args) {
       	String[][] users = {{"Noé","Gómez Hernandez","noe.gomez@cimsacv.com.mx"},
       			{"Jesús Gabriel","Buenrostro Flores","darkgo_s@hotmail.com"},
       			{"Pedro","Olivares Estrada","pedro.olivares@cimsacv.com.mx"}};

        File f = new File("C://usersCDS.sql");
        FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
            
        MD5 md5 = new MD5();
    	for(int i = 0; i < users.length; i++) {
    		String firstName = users[i][0];
    		String lastName = users[i][1];
    		String email = users[i][2];

            boolean done = false;
        	while(!done) {
                try {
	        		String href = md5.encriptar(email);
	        		String login = md5.encriptar(href);
	        		String password = md5.encriptar(login);
	        		password = md5.encriptar(password);
        			String insert = "INSERT INTO users (loginname,firstname,lastname,language,email,password,role) VALUES ('"+login+"','"+firstName+"','"+lastName+"','es','"+email+"','"+password+"','systemaccess');\n";
	        		fos.write(insert.getBytes());
	        		insert = "INSERT INTO userassesments(assesment,loginname) VALUES ("+AssesmentData.CEPA_DRIVING_SYSTEM+",'"+login+"');\n";
	        		fos.write(insert.getBytes());
/*        			email = "federico.millan@cepasafedrive.com";*/ 
        			email = "jrodriguez@cepasafedrive.com";
        				
        	        Properties properties = new Properties();
        	        properties.put("mail.smtp.host",SMTP);
        	        properties.put("mail.smtp.auth", "true");
        	        properties.put("mail.from",FROM_NAME);
        	        Session session = Session.getInstance(properties,null);
        	    
        	        InternetAddress[] address = {new InternetAddress(email,users[i][0]+" "+users[i][1])};
        			Message mensaje = new MimeMessage(session);
        			// Rellenar los atributos y el contenido
        			// Asunto
        			mensaje.setSubject("Invitación - Driver Assessment");
        			// Emisor del mensaje
        			mensaje.setFrom(new InternetAddress(FROM_DIR,FROM_NAME));
        			// Receptor del mensaje
        			mensaje.addRecipient(Message.RecipientType.TO,new InternetAddress(email,users[i][0]+" "+users[i][1]));
        			// Crear un Multipart de tipo multipart/related
        			Multipart multipart = new MimeMultipart ("related");
        			// Leer el fichero HTML

        			// Rellenar el MimeBodyPart con el fichero e indicar que es un fichero HTML
        			BodyPart texto = new MimeBodyPart ();
        			texto.setContent(getMail("http://da.cepasafedrive.com/assesment/index.jsp?login="+href),"text/html");
        			multipart.addBodyPart(texto);

        			// Procesar la imagen
        			MimeBodyPart imagen = new MimeBodyPart();
        			imagen.attachFile(AssesmentData.FLASH_PATH+"/images/invitacion-michelin.jpg");
        			imagen.setHeader("Content-ID","<figura1>");
        			multipart.addBodyPart(imagen);
        			mensaje.setContent(multipart);
        			// Enviar el mensaje

        	        Transport transport = session.getTransport(address[0]);
        	        transport.connect(SMTP, FROM_DIR, PASSWORD);
        	        transport.sendMessage(mensaje,address);
        	        
        	        done = true;
        	        System.out.println("ENVIADO "+email);        	        
	            }catch (Exception e) {
	            	e.printStackTrace();
	    	        System.out.println("EXCEPCION "+email);
	            	// 	TODO: handle exception
	            }
    		}
    	}
	}
	
	private static String mail2() {
		String mail = "<html>" +
			"	<head>" +
			"	</head>" +
			"	<body bgcolor=\"#ffffff\" text=\"#000066\">" +
			"		<table>" +
			"			<tr>" +
			"				<td>" +
			"					<img src=\"cid:figura1\" alt=\"\">" +
			"				</td>" +
			"			</tr>" +
			"		</table>" +
			"	</body>" +
			"</html>";
		return mail;
	}
	
	private static String getMail(String href) {
		String mail = "<html>" +
			"	<head>" +
			"	</head>" +
			"	<body bgcolor=\"#ffffff\" text=\"#000066\">" +
			"		<table>" +
			"			<tr>" +
			"				<th align=\"center\">" +
			"					<span style='font-size:13.5pt;color:#1F497D'>Junta Nacional de Ventas 2009</span>" +
			"				</th>" +
			"			</tr>"+
			"			<tr>" +
			"				<th align=\"center\">" +
			"					<span style='font-size:13.5pt;color:#1F497D'>Material previo para curso de conducci&oacute;n segura</span>" +
			"				</th>" +
			"			</tr>"+
			"			<tr>" +				
			"				<td>" +
			"					<br>" +
			"					<font size=\"-1\"><font face=\"Verdana\">" +
			"					<br>" +
			"					</font></font>" +
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;; color: rgb(102, 102, 102);\">" +
			"						Dentro del programa de actividades de la junta nacional de ventas 2009 esta incluido un curso teorico-pr&aacute;ctico " +
			"						de conducci&oacute;n segura. Es necesario que usted complete un cuestionario como material de trabajo previo a este curso." +
			" 						Esta información ser&aacute; utilizada por la compa&ntilde;&iacute;a que impartir&aacute; el curso para conocer el perfil " +
			"						de los participantes y para adecuar el contenido del curso a las necesidades del grupo." +
			"					</span>" +
			"					<br><br>" +
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;; color: rgb(102, 102, 102);\">" +
			"					El link a continuaci&oacute;n es privado y el mismo le servir&aacute; para ingresar a la herramienta cada vez que lo desee.<br>" +
			"					Una vez que termine todos los m&oacute;dulos, recibir&aacute; los reportes en esta misma cuenta de correo en formato PDF."+ 
			"					</span>" +
			"					<br>" +
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;;\">" +
			"						<br><br>" +
			"						<a class=\"moz-txt-link-freetext\" href=\""+href+"\">" + href + "</a>" +
			"						<br><br>" +
			"					<span>" +
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;; color: rgb(102, 102, 102);\">" +
			"						<i>Su participaci&oacute;n es sumamente valiosa. </i>" +
			"					</span>" +
			"					<br><br>" +
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;;\">" +
			"						<small>" +
			"							<br>" +
			"							<font color=\"#666666\">" +
			"								<i><u>Importante:</u> En caso de no poder hacer click en el link por favor copie y pegue el link en un navegador.</i>" +
			"								<br>" +
			"								<i>Para dudas y/o consultas por favor comunicarse con <b><a href=\"mailto:indesa@cepasafedrive.com\">indesa@cepasafedrive.com</a></b></i>" +
			"							</font>" +
			"						</small>" +
			"					</span>" +
			"					<br>" +
			"					<br>" +
			"					<br>" +
			"				</td>" +
			"			</tr>" +
			"			<tr>" +
			"				<td>" +
			"					<img src=\"cid:figura1\" alt=\"\">" +
			"					<br>" +
			"					<br>" +
			"					<br>" +
			"				</td>" +
			"			</tr>" +
			"		</table>" +
			"	</body>" +
			"</html>";
		return mail;
	}


	private static String getMailRe(String href) {
		String mail = "<html>"+
				"<head>"+
				"</head>"+
				"<body bgcolor=\"#ffffff\" text=\"#000066\">"+
				"	<table>"+
				"		<tr>"+
				"			<td>"+
				"				<img src=\"cid:figura1\" alt=\"\">"+
				"			</td>"+
				"		</tr>"+
				"		<tr>"+
				"			<td>"+
				"				<font size=\"-1\"><font face=\"Verdana\">"+
				"				<br>"+
				"				</font></font>"+
				"				<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;; color: rgb(102, 102, 102);\">"+
				"Estimados les recordamos la invitación a utilizar (o completar) la herramienta web Driver Assessment."+
				"<br>"+
				"<br>"+
				"El link a continuación está personalizado, contiene los módulos que debe completar, y el mismo le servirá para ingresar a la herramienta cada vez que lo desee. Una vez termine los módulos, recibirá un email con los reportes y/o certificado de la actividad."+
				"<br>"+
				"<br>"+
				"Gracias por su participación."+
				"				</span>"+
				"				<i>"+
				"					<span  style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;; color: rgb(102, 102, 102);\">"+
				"					<br>"+
				"					</span>"+
				"				</i>"+
				"				<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;;\">"+
				"					<br>"+
				"							<a class=\"moz-txt-link-freetext\" href=\""+href+"\">" + href + "</a>" +
				"					<br>"+
				"				</span>"+
				"				<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;;\">"+
				"					<br>"+
				"				</span>"+
				"				<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;; color: rgb(102, 102, 102);\">"+
				"					<b>Importante:</b> algunos administradores de correos no permiten abrir link adjuntos, si es su caso, por favor copiar y pegar la dirección en su navegador."+
				"					<br>"+
				"					<br>"+
				"					Por dudas y consultas favor comunicarse con"+
				"				</span>"+
				"				<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;; color: rgb(102, 102, 102);\">"+
				"					<b><a href=\"mailto:indesa@cepasafedrive.com\">indesa@cepasafedrive.com</a></b>"+
				"				</span>"+
				"				<br>"+
				"				<br>"+
				"				<br>"+
				"				<br>"+
				"			</td>"+
				"		</tr>"+
				"	</table>"+
				"</body>"+
				"</html>";
		return mail;
	}
}
