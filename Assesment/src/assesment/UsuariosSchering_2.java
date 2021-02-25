package assesment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Properties;

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

public class UsuariosSchering_2 {
	
    protected static final String SMTP = "smtp.cepasafedrive.com";
    protected static final String FROM_NAME = "CEPA - Driver Assessment";
    protected static final String FROM_DIR = "da@cepasafedrive.com";
    protected static final String PASSWORD = "dacepa09";

    
    public static void main(String[] args) {

        try {
            Collection doneUsers = new LinkedList();
	        int EOF = 65535;
	
            Class.forName("org.postgresql.Driver");
            Connection conn1 = DriverManager.getConnection("jdbc:postgresql://67.192.60.149:5432/assesment","postgres","pr0v1s0r1A");
            Statement st = conn1.createStatement();
            
            MD5 md5 = new MD5();
            ResultSet set = st.executeQuery("select firstname,lastname,email from users where email in ('anacarolina.malvezzi@spcorp.com','anderson.nunes@spcorp.com','andrea.ciolette@spcorp.com','barbara.machado@spcorp.com','cristiano_gomes@merck.com','daniel.caetano@spcorp.com','fabio.rosito@spcorp.com','fernando.pereira@spcorp.com','flavia.lima@spcorp.com','gil.linhares@spcorp.com','joao.mantovanini@spcorp.com','jorge.oliveira@spcorp.com','jose.farah@spcorp.com','juliana.albuquerque@spcorp.com','lia.amaral@spcorp.com','lilian.lustre@spcorp.com','liliane.nagoshi@spcorp.com','lilian.harder@spcorp.com','lindomar.moura2@spcorp.com','luizcarlos.rodrigues@spcorp.com','luiz.eduardo.lima@spcorp.com','marcela.trindade@spcorp.com','marcia.ferrara@spcorp.com','milton.futami@spcorp.com','nelson.faria@spcorp.com','pedro.peralta@spcorp.com','peter.gallagher@spcorp.com','rogerio.ruiz@spcorp.com','rosemeire.castro@spcorp.com','simone.lopes@spcorp.com','vivian.lee@spcorp.com')");
            int index = 1;
            while(set.next()) {
	    		String firstName = set.getString("firstname");
	    		String lastName = set.getString("lastname");
	    		String fullName = firstName + " " + lastName;
	    		String email = set.getString("email");;
	    		if(email != null && email.length() > 0) {
		
		    		boolean done = false;
		        	while(!done) {
		                try {
			        		String href = md5.encriptar(email);
			        		String login = md5.encriptar(href);
			        		String password = md5.encriptar(login);
			        		password = md5.encriptar(password);
		        		
//		        			email = "jrodriguez@cepasafedrive.com";
		        	        Properties properties = new Properties();
		        	        properties.put("mail.smtp.host",SMTP);
		        	        properties.put("mail.smtp.auth", "true");
		        	        properties.put("mail.from",FROM_NAME);
		        	        Session session = Session.getInstance(properties,null);
		        	    
//			        	        InternetAddress[] address = {new InternetAddress(email,fullName)};
		        			Message mensaje = new MimeMessage(session);
		        			// Rellenar los atributos y el contenido
		        			// Asunto
		        			mensaje.setSubject("CONVITE 'Driver Assessment'");
		        			// Emisor del mensaje
		        			mensaje.setFrom(new InternetAddress(FROM_DIR,FROM_NAME));
		        			// Receptor del mensaje
		        			InternetAddress[] addrs = {new InternetAddress(email,fullName),new InternetAddress("daniel.lima@spcorp.com","Daniel Lima")};
		        			mensaje.addRecipients(Message.RecipientType.TO,addrs);

		        			// Crear un Multipart de tipo multipart/related
		        			Multipart multipart = new MimeMultipart ("related");
		        			// Leer el fichero HTML
		
		        			// Rellenar el MimeBodyPart con el fichero e indicar que es un fichero HTML
		        			BodyPart texto = new MimeBodyPart ();
//			        		texto.setContent(getMail1(),"text/html");
		        			texto.setContent(getMail2("http://da.cepasafedrive.com/assesment/index.jsp?login="+href),"text/html");
		        			multipart.addBodyPart(texto);
		
		        			// Procesar la imagen
		        			MimeBodyPart imagen = new MimeBodyPart();
//		        			imagen.attachFile(AssesmentData.FLASH_PATH+"/images/MSDbrasil-convite-Unidade Santo Amaro.jpg");
		        			imagen.attachFile(AssesmentData.FLASH_PATH+"/images/invitacion3-Schering.jpg");
		        			imagen.setHeader("Content-ID","<figura1>");
		        			multipart.addBodyPart(imagen);

//		        			MimeBodyPart fileAtt = new MimeBodyPart();
//		        			multipart.addBodyPart(fileAtt);

		        			mensaje.setContent(multipart);
		        			// Enviar el mensaje
		
		        	        Transport transport = session.getTransport(addrs[0]);
		        	        transport.connect(SMTP, FROM_DIR, PASSWORD);
		        	        transport.sendMessage(mensaje,addrs);
		        	        
		        	        System.out.println("ENVIADO "+index+" "+email);
		        	        index++;
		        	        
		        	        done = true;
			            }catch (Exception e) {
			            	e.printStackTrace();
			    	        System.out.println("EXCEPCION "+email);
			            	// 	TODO: handle exception
			            }
		    		}
	    		}
	    	}

    	}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getMail1() {
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
    private static String getMail2(String href) {
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
			"				<td>" +
			"					<br>" +
			"					<font size=\"-1\"><font face=\"Verdana\">" +
			"					<br>" +
			"					</font></font>" +
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;; color: rgb(102, 102, 102);\">" +
			"					Prezados.<br><br><br>"+
			"					O link abaixo &eacute; privado e servir&aacute; para acessar a ferramenta sempre que desejar.<br>"+
			"					Se voc&ecirc; iniciou parcialmente as atividades, poder&aacute; usar o mesmo link para reiniciar e finalizar as atividades.<br><br>"+
			"					Uma vez finalizado todos os módulos, voc&ecirc; receber&aacute; os relat&oacute;rios de desempenho em formato PDF neste mesmo endere&ccedil;o de correio eletr&ocirc;nico.<br>"+
			"					Data de in&iacute;cio e t&eacute;rmino: 05/02 &agrave; 19/02 de 2010."+
			"					</span>" +
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;;\">" +
			"						<br><br>" +
			"						<a class=\"moz-txt-link-freetext\" href=\""+href+"\">" + href + "</a>" +
			"						<br><br>" +
			"					<span>" +
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;; color: rgb(102, 102, 102);\">" +
			"						Agradecemos sua participa&ccedil;&atilde;o que &eacute; sumamente valiosa." +
			"					</span>" +
			"					<br><br>" +
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;;\">" +
			"						<small>" +
			"							<br>" +
			"							<font color=\"#666666\">" +
			"								Em caso de dúvidas ou consultas, favor entrar em contato por e-mail  <b><a href=\"mailto:indesa@cepasafedrive.com\">indesa@cepasafedrive.com</a></b> ou por telefone 012-2139-1952." +
			"							</font>" +
			"						</small>" +
			"					</span>" +
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
}
