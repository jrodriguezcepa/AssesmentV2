package assesment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;
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

public class UsuariosMonsantoLAN {
	
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
            

            ResultSet setDone = st.executeQuery("SELECT ua.loginname " +
            		"FROM useranswers ua " +
            		"JOIN userassesments uas ON (ua.loginname = uas.loginname AND ua.assesment = uas.assesment) " +
            		"JOIN answerdata ad ON ua.answer = ad.id " +
            		"JOIN questions q ON ad.question = q.id " +
            		"WHERE ua.assesment = 49 " +
            		"GROUP BY ua.loginname " +
            		"HAVING count(*) = 79");
            while(setDone.next()) {
            	doneUsers.add(setDone.getString("loginname"));
            }
            
            
            File fin = new File("C:/MonsantoLan.csv");
	        FileInputStream fis = new FileInputStream(fin);
	
	        String[][] values = new String[426][4];
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

	        File f = new File("C:/MonsantoLan.sql");
	        FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(f);
			} catch (Exception e) {
				e.printStackTrace();
			}
	            
	        File f2 = new File("C:/MonsantoLanLinks.csv");
	        FileOutputStream fos2 = null;
			try {
				fos2 = new FileOutputStream(f2);
			} catch (Exception e) {
				e.printStackTrace();
			}
	            
	        MD5 md5 = new MD5();
	        int index = 1;
	    	for(i = 0; i < Math.min(1000,values.length); i++) {
	    		String division = values[i][0].trim();
	    		String fullName = values[i][1].trim();
	    		String email = values[i][2].trim();
	    		int length = Integer.parseInt(values[i][3].trim());
	    		if(email != null && email.length() > 0) {
	    			
	    			String firstName = "";
	    			String lastName = "";
	    			StringTokenizer tokenizer = new StringTokenizer(fullName);
	    			int count = tokenizer.countTokens();
	    			for(int j = 0; j < count; j++) {
	    				if(j < length) {
	    					firstName += tokenizer.nextToken()+" ";
	    				}else {
	    					lastName += tokenizer.nextToken()+" ";
	    				}
	    			}
					firstName = firstName.trim();
					lastName = lastName.trim();
		    		boolean done = false;
		        	while(!done) {
		                try {
			        		String href = md5.encriptar(email);
			        		String login = md5.encriptar(href);
			        		String password = md5.encriptar(login);
			        		password = md5.encriptar(password);
			        		
			        		if(!doneUsers.contains(login)) {
				        		fos2.write(new String(fullName+";"+email+";"+"http://da.cepasafedrive.com/assesment/index.jsp?login="+href+"\n").getBytes());
			        			String insert = "INSERT INTO users (loginname,firstname,lastname,language,email,password,role,extradata) VALUES ('"+login+"','"+firstName.trim()+"','"+lastName.trim()+"','pt','"+email+"','"+password+"','systemaccess','"+division+"');\n";
			        			fos.write(insert.getBytes());
			        			insert = "INSERT INTO userassesments(assesment,loginname) VALUES (49,'"+login+"');\n";
			        			fos.write(insert.getBytes());
/*					        	email = "federico.millan@cepasafedrive.com";  
					        	email = "jrodriguez@cepasafedrive.com"; 

			        	        Properties properties = new Properties();
			        	        properties.put("mail.smtp.host",SMTP);
			        	        properties.put("mail.smtp.auth", "true");
			        	        properties.put("mail.from",FROM_NAME);
			        	        Session session = Session.getInstance(properties,null);
				        	    
			        			Message mensaje = new MimeMessage(session);
			        			// Rellenar los atributos y el contenido
			        			// Asunto
			        			mensaje.setSubject("CONVITE 'Driver Assessment'");
			        			// Emisor del mensaje
			        			mensaje.setFrom(new InternetAddress(FROM_DIR,FROM_NAME));
			        			// Receptor del mensaje
			        			InternetAddress[] addrs = {new InternetAddress(email,fullName),new InternetAddress("cepadriverassessment@gmail.com","Respaldo")};
			        			mensaje.addRecipients(Message.RecipientType.TO,addrs);
	
			        			// Crear un Multipart de tipo multipart/related
			        			Multipart multipart = new MimeMultipart ("related");
			        			// Leer el fichero HTML
				
			        			// Rellenar el MimeBodyPart con el fichero e indicar que es un fichero HTML
			        			BodyPart texto = new MimeBodyPart ();
				        		texto.setContent(getMail("http://da.cepasafedrive.com/assesment/index.jsp?login="+href),"text/html");
			        			multipart.addBodyPart(texto);
				
			        			//System.out.println("http://da.cepasafedrive.com/assesment/index.jsp?login="+href);

			        			// Procesar la imagen
			        			MimeBodyPart imagen = new MimeBodyPart();
			        			imagen.attachFile(AssesmentData.FLASH_PATH+"/images/invitacion-janssen2.jpg");
			        			imagen.setHeader("Content-ID","<figura1>");
			        			multipart.addBodyPart(imagen);
	
			        			mensaje.setContent(multipart);
	
			        			// Enviar el mensaje
				       	        Transport transport = session.getTransport(addrs[0]);
				      	        transport.connect(SMTP, FROM_DIR, PASSWORD);
			        	        transport.sendMessage(mensaje,addrs);
*/				        	        
			        	        System.out.println("ENVIADO "+index+" "+email);
			        	        index++;
			        		}			        	        
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
			"						Convite para utilizar a ferramenta web Driver Assessment. O fim desta atividade está previsto para <b>26/05/2010</b>.<br><br>" +
			"						O link abaixo &eacute; privado e servir&aacute; para acessar a ferramenta sempre que desejar.<br>"+
			"						Se voc&ecirc; iniciou parcialmente as atividades, poder&aacute; usar o mesmo link para reiniciar e finalizar as atividades.<br><br>"+					
			"						Uma vez finalizado todos os m&oacute;dulos, voc&ecirc; receber&aacute; os relat&oacute;rios de desempenho em formato PDF neste mesmo endere&ccedil;o de correio eletr&oacute;nico.<br>"+
			"					</span>" +
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;;\">" +
			"						<br>" +
			"						<a class=\"moz-txt-link-freetext\" href=\""+href+"\">" + href + "</a>" +
			"						<br><br>" +
			"					<span>" +
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;; color: rgb(102, 102, 102);\">" +
			"						<i>Agradecemos sua participa&ccedil;&atilde;o que &eacute; sumamente valiosa.</i>" +
			"					</span>" +
			"					<br><br>" +
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;;\">" +
			"							<font color=\"#666666\">" +
			"								Em caso de d&uacute;vidas e/ou consultas favor entrar em contato com <b><a href=\"mailto:indesa@cepasafedrive.com\">indesa@cepasafedrive.com</a></b>" +
			"							</font>" +
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
			"				<th align=\"left\">" +
			"					<span style='font-size:18.0pt;font-family:\"Tahoma\",\"sans-serif\"; color:#15B7AF'>SEGUNDO Convite para Participar do Driver Assessment</span>" +
			"				</th>" +
			"			</tr>"+
			"			<tr>" +				
			"				<td>" +
			"					<br>" +
			"					<font size=\"-1\"><font face=\"Verdana\">" +
			"					<br>" +
			"					</font></font>" +
			"					<span style='font-size:10.0pt;font-family:\"Verdana\",\"sans-serif\";color:#666666'>" +
			"						Reiteramos o convite para utilizar a ferramenta web Driver Assessment. O fim desta atividade est&aacute; previsto para o pr&oacute;ximo dia 20.<br>"+
			"					</span>" +
			"					<span style='font-size:10.0pt;font-family:\"Verdana\",\"sans-serif\";color:#666666'>" +
			"						O link abaixo &eacute; privado e servir&aacute; para acessar a ferramenta sempre que desejar.<br>"+
			"					</span>" +
			"					<span style='font-size:10.0pt;font-family:\"Verdana\",\"sans-serif\";color:#666666'>" +
			"						Uma vez finalizado todos os m&oacute;dulos, voc&ecirc; receber&aacute; os relat&oacute;rios de desempenho em formato PDF neste mesmo endere&ccedil;o de correio eletr&oacute;nico.<br>"+
			"					</span>" +
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;;\">" +
			"						<br><br>" +
			"						<a class=\"moz-txt-link-freetext\" href=\""+href+"\">" + href + "</a>" +
			"						<br><br>" +
			"					<span>" +
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;; color: rgb(102, 102, 102);\">" +
			"						<i>Agradecemos sua participa&ccedil;&atilde;o que &eacute; muito valiosa.</i>" +
			"					</span>" +
			"					<br><br>" +
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;;\">" +
			"						<small>" +
			"							<br>" +
			"							<font color=\"#666666\">" +
			"								<i><u>Importante:</u>  Caso n&atilde;o consiga acessar o website atrav&eacute;s do link fornecido, favor copiar e colar o endere&ccedil;o URL fornecido acima na barra de endere&ccedil;os do seu navegador.</i>" +
			"								<br>" +
			"								<i>Em caso de d&uacute;vidas e/ou consultas favor entrar em contato com <b><a href=\"mailto:indesa@cepasafedrive.com\">indesa@cepasafedrive.com</a></b></i>" +
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

	private static String getMail3(String href) {
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
			"						Hoje &eacute; o último dia para o t&eacute;rmino do treinamento. &Eacute; de suma import&acircncia que todos participem. Para possibilitar a sua participa&ccedil;&atilde;o estamos prorrogando as atividades para 05/02/2010. Participe!<br>"+
			"						O link abaixo &eacute; privado e servir&aacute; para acessar a ferramenta sempre que desejar.<br>"+
			"						Se voc&ecirc; iniciou parcialmente as atividades, poder&aacute; usar o mesmo link para reiniciar e finalizar as atividades.<br><br>"+					
			"						Uma vez finalizado todos os m&oacute;dulos, voc&ecirc; receber&aacute; os relat&oacute;rios de desempenho em formato PDF neste mesmo endere&ccedil;o de correio eletr&oacute;nico.<br>"+
			"					</span>" +
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;;\">" +
			"						<br><br>" +
			"						<a class=\"moz-txt-link-freetext\" href=\""+href+"\">" + href + "</a>" +
			"						<br><br>" +
			"					<span>" +
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;; color: rgb(102, 102, 102);\">" +
			"						<i>Agradecemos sua participa&ccedil;&atilde;o que &eacute; sumamente valiosa.</i>" +
			"					</span>" +
			"					<br><br>" +
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;;\">" +
			"						<small>" +
			"							<br>" +
			"							<font color=\"#666666\">" +
			"								<i><u>Importante:</u>  Caso n&atilde;o consiga acessar o website atrav&eacute;s do link fornecido, favor copiar e colar o endere&ccedil;o URL fornecido acima na barra de endere&ccedil;os do seu navegador.</i>" +
			"								<br>" +
			"								<i>Em caso de dúvidas ou consultas, favor entrar em contato por e-mail  <b><a href=\"mailto:indesa@cepasafedrive.com\">indesa@cepasafedrive.com</a></b> ou por telefone 012-2139-1952." +
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
