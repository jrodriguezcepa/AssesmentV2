package assesment;

import java.io.File;
import java.io.FileOutputStream;
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

public class UsuariosAllergan {
	
    protected static final String SMTP = "smtp.cepasafedrive.com";
    protected static final String FROM_NAME = "CEPA - Driver Assessment";
    protected static final String FROM_DIR = "da@cepasafedrive.com";
    protected static final String PASSWORD = "dacepa09";

    public static void main(String[] args) {
        
		try {
            Collection<String> doneUsers = new LinkedList<String>();

            String[][] users = {
            		
            		{"ROZANY","IPAVES CRUZ","GRU","Controladoria","EXECUTIVO","Própria","Cruz_Rozany@allergan.com"},
            		{"NEWTON","MACEDO DINIZ","GRU","Diretoria Industrial","EXECUTIVO","Própria","Diniz_Newton@Allergan.com"},
            		{"SANDRO","DUARTE CHANG","GRU","Engenharia","EXECUTIVO","Própria","Chang_Sandro@Allergan.com"},
            		{"ELIZABETH","MESQUITA","GRU","Gerência de Produção","EXECUTIVO","Própria","Mesquita_Elizabeth@Allergan.com"},
            		{"VLADIMIR","APARECIDO CHIARI ","GRU","Gerência de RH","EXECUTIVO","Própria","Chiari_Vladimir@Allergan.com"},
            		{"JOAO","EDO NETO","GRU","Materiais","EXECUTIVO","Própria","Edo_Joao@Allergan.com"},
            		{"FLAVIA","REGINA PEGORER","GRU","Qualidade","EXECUTIVO","Própria","Pegorer_Flavia@Allergan.com"},
            		{"ADRIANA CRISTINA","C DA COSTA MANEIRA","HERTZ","Botox Cosmética","BR22202T","Terceira","Maneira_Adriana@Allergan.com"},
            		{"ADRIANO","TANAJURA MAGALHAES","HERTZ","Botox Cosmética","BR22307T","Terceira","Magalhaes_Adriano@Allergan.com"},
            		{"ALAN VAGNER","MACHADO DE QUEIROZ","HERTZ","Botox Cosmética","BR21208T","Terceira","Queiroz_Alan@Allergan.com"},
            		{"ALESSANDRA ","AGUIAR DE JESUS OLIVEIRA","HERTZ","Botox Cosmética","COORD1","Terceira","Oliveira_Alessandra@Allergan.com"},
            		{"CARLA ANDREA","BACELAR RAMOS","HERTZ","Botox Cosmética","BR22306T","Terceira","Ramos_Carla@Allergan.com"},
            		{"CARLOS HENRIQUE","PIRES DA SILVA","HERTZ","Botox Cosmética","BR22204T","Terceira","Pires_Carlos@Allergan.com"},
            		{"CAROLINE","CARDINALI DE ASSIS RIBEIRO","HERTZ","Botox Cosmética","BR22301T","Terceira","Ribeiro_Caroline@Allergan.com"},
            		{"DANIEL","ALBUQUERQUE TABATINGA","HERTZ","Botox Cosmética","BR22302T","Terceira","Tabatinga_Daniel@Allergan.com"},
            		{"GRAZIELA","MELO LOPES","HERTZ","Botox Cosmética","BR22304T","Terceira","Lopes_Graziela@Allergan.com"},
            		{"JULIANA","PAGANOTE DIAS FERNANDES","HERTZ","Botox Cosmética","BR22203T","Terceira","Fernandes_Juliana@Allergan.com"},
            		{"LEANDRA","SOARES RODRIGUES","HERTZ","Botox Cosmética","BR22200","Terceira","Rodrigues_Leandra@Allergan.com"},
            		{"LEANDRO","DE MACEDO MARTINS","HERTZ","Botox Cosmética","BR22100","Terceira","Martins_Leandro@Allergan.com"},
            		{"LINA CELIA","LOTT PEIXOTO DOMENICI","HERTZ","Botox Cosmética","BR22300","Terceira","Domenici_Lina@Allergan.com"},
            		{"LUCIANA","SPOLAOR VERRO","HERTZ","Botox Cosmética","BR22105T","Terceira","Verro_Luciana@Allergan.com"},
            		{"MARCIO","CONTRICIANI","HERTZ","Botox Cosmética","BR22102T","Terceira","Contriciani_Marcio@Allergan.com"},
            		{"PAULO RENATO","FERREIRA BALBY","HERTZ","Botox Cosmética","BR22403T","Terceira","Ferreira_Paulo@Allergan.com"},
            		{"PRISCILA","SALINAS P GIANOTTI","HERTZ","Botox Cosmética","MKT1","Terceira","Gianotti_Priscila@Allergan.com"},
            		{"VALESCA","DANIELE","HERTZ","Botox Cosmética","COORD2","Terceira","Daniele_Valesca@Allergan.com"},
            		{"ADERITO","SARZEDO NETO","HERTZ","Botox Terapêutica","BR21304T","Terceira","Neto_Aderito@Allergan.com"},
            		{"AGENOR","RODRIGUES JUNIOR","HERTZ","Botox Terapêutica","BR21200T","Terceira","Rodrigues_Agenor@Allergan.com"},
            		{"ANGELA","REGINA PRIOR A GONCALVES","HERTZ","Botox Terapêutica","BR21103T","Terceira","Goncalves_Angela@Allergan.com"},
            		{"ANTONIO","SCROK JUNIOR","HERTZ","Botox Terapêutica","BR21300T","Terceira","Scrok_Antonio@Allergan.com"},
            		{"EDUARDO","WOLGA","HERTZ","Botox Terapêutica","BR21204T","Terceira","Wolga_Eduardo@Allergan.com"},
            		{"FERNANDO","MAGNO AMANTEA","HERTZ","Botox Terapêutica","BR21104T","Terceira","Amantea_Fernando@Allergan.com"},
            		{"FLAVIO","BOLINSENHA JUNIOR","HERTZ","Botox Terapêutica","BR21100T","Terceira","Bolinsenha_Flavio@Allergan.com"},
            		{"MARCELO","DE ANDRADE GONCALVES","HERTZ","Botox Terapêutica","BR21101T","Terceira","Andrade_Marcelo@Allergan.com"},
            		{"RENATA","FRANCO DE ALMEIDA MELO","HERTZ","Botox Terapêutica","BR21109T","Terceira","Almeida_Renata@Allergan.com"},
            		{"SIDNEI","RIBEIRO TELES","HERTZ","Botox Terapêutica","BR21303T","Terceira","Teles_Sidnei@Allergan.com"},
            		{"DIEGO","COLICCHIO LOPES","HERTZ","Breast","Ger. Produtos","Terceira","Lopes_Diego@Allergan.com"},
            		{"EDUARDO","ANGEL HAGIPANTELLI","HERTZ","Breast","Ger. Vendas","Terceira","Hagipantelli_Eduardo@Allergan.com"},
            		{"AILTON","CARLOS FERREIRA PAIVA","HERTZ","Farma","110000","Terceira","Paiva_Ailton@Allergan.com"},
            		{"ANA LUISA","ELISEI ALMADA","HERTZ","Farma","110602","Terceira","Almada_Ana@Allergan.com"},
            		{"ANDERSON","FERREIRA MORAES","HERTZ","Farma","110401","Terceira","Moraes_Anderson@Allergan.com"},
            		{"ANDERSON","LUIS DE SOUZA","HERTZ","Farma","110201","Terceira","Souza_Anderson@Allergan.com"},
            		{"ANDRE","LUIZ RAMOS","HERTZ","Farma","110207","Terceira","Ramos_Andre@Allergan.com"},
            		{"ANDREA","VALERIA GOMBERG","HERTZ","Farma","110407","Terceira","Gomberg_Andrea@Allergan.com"},
            		{"ANDRESA","RIBEIRO FREITAS DE PAULA","HERTZ","Farma","110205","Terceira","Freitas_Andresa@Allergan.com"},
            		{"ANTONIO CARLOS","BARBOSA","HERTZ","Farma","300100","Terceira","Barbosa_Antonio@Allergan.com"},
            		{"ANTONIO CARLOS","NUNES","HERTZ","Farma","110107","Terceira","Nunes_Antonio@Allergan.com"},
            		{"BENEDITO","JOSE DE SOUSA JUNIOR","HERTZ","Farma","110505","Terceira","Sousa_Benedito@Allergan.com"},
            		{"BRUNO","CESAR R F DO NASCIMENTO","HERTZ","Farma","110702","Terceira","Nascimento_Bruno@Allergan.com"},
            		{"BRUNO","FERNANDES MACHADO","HERTZ","Farma","110200","Terceira","Machado_Bruno@Allergan.com"},
            		{"CARLA","FERREIRA TERRA","HERTZ","Farma","110600","Terceira","Terra_Carla@Allergan.com"},
            		{"CARLA ROBERTA","BORBA MACHADO","HERTZ","Farma","110102","Terceira","Machado_Carla@Allergan.com"},
            		{"CARLOS","EUCLIDES LIMA MEDEIROS DA PAZ","HERTZ","Farma","110701","Terceira","Paz_Carlos@Allergan.com"},
            		{"CARLOS ANDRE","CORDEIRO FERREIRA","HERTZ","Farma","110704","Terceira","Ferreira_Carlos@Allergan.com"},
            		{"CARLOS AUGUSTO","AFONSO DOS SANTOS","HERTZ","Farma","110408","Terceira","Santos_Carlos@Allergan.com"},
            		{"CLAYTON","TEIXEIRA PINTO JUNIOR","HERTZ","Farma","300200","Terceira","Junior_Clayton@Allergan.com"},
            		{"EDGARD","PINTO NETO","HERTZ","Farma","110500","Terceira","Neto_Edgard@Allergan.com"},
            		{"EDUARDO","BILOBROVEC","HERTZ","Farma","110106","Terceira","Bilobrovec_Eduardo@Allergan.com"},
            		{"EDUARDO","NUNES MATIAS","HERTZ","Farma","110502","Terceira","Matias_Eduardo@Allergan.com"},
            		{"EDVONEID","ANIBOLETE BARROSO","HERTZ","Farma","119000","Terceira","Barroso_Edvoneid@Allergan.com"},
            		{"ELIAS","CRUCIOL JUNIOR","HERTZ","Farma","110301","Terceira","Junior_Elias@Allergan.com"},
            		{"FABIANE","XAVIER BRANDAO DE OLIVEIRA","HERTZ","Farma","110604","Terceira","Brandao_Fabiane@Allergan.com"},
            		{"FABIO","LUIZ GONCALVES RAMOS","HERTZ","Farma","300000","Terceira","Ramos_Fabio@Allergan.com"},
            		{"GILBERTO","SOUZA MENDES PAES","HERTZ","Farma","110208","Terceira","Paes_Gilberto@Allergan.com"},
            		{"GLADSTON","SANCHEZ CAETANO","HERTZ","Farma","110303","Terceira","Caetano_Gladston@Allergan.com"},
            		{"HELIO","PAULO M DE CARVALHO JUNIOR","HERTZ","Farma","110406","Terceira","Carvalho_Helio@Allergan.com"},
            		{"HILTON","FELIX CHAVES LARRONDA","HERTZ","Farma","110104","Terceira","Larronda_Hilton@Allergan.com"},
            		{"IGUATEMI","MACEDO CARNEIRO","HERTZ","Farma","110100","Terceira","Carneiro_Iguatemi@Allergan.com"},
            		{"JESIANE","RODRIGUES DIAS","HERTZ","Farma","110405","Terceira","Dias_Jesiane@Allergan.com"},
            		{"JOAO CESAR"," GAGLIARDI","HERTZ","Farma","110302","Terceira","Gagliardi_Joao@Allergan.com"},
            		{"JORGE","LUIZ FONSECA DE ALMEIDA","HERTZ","Farma","110601","Terceira","Almeida_Jorge@Allergan.com"},
            		{"JORGE ","ANDRE DE CASTRO","HERTZ","Farma","110501","Terceira","castro_jorge@allergan.com"},
            		{"JOSE INACIO","DA SILVA BELMONTE","HERTZ","Farma","110101","Terceira","Belmonte_Jose@Allergan.com"},
            		{"KATHIA","MARIA DOS SANTOS KRAMER","HERTZ","Farma","110705","Terceira","Kramer_Kathia@Allergan.com"},
            		{"KLEBER","FREITAS DE LIMA","HERTZ","Farma","110206","Terceira","Lima_Kleber@Allergan.com"},
            		{"LUCIMARA","SALOMAO FERREIRA","HERTZ","Farma","300300","Terceira","Salomao_Lucimara@Allergan.com"},
            		{"LUIS CLAUDIO","FERREIRA MONTEIRO","HERTZ","Farma","110400","Terceira","Monteiro_Luis@Allergan.com"},
            		{"MARCELO","ROSA E SILVA","HERTZ","Farma","110503","Terceira","Rosa_Marcelo@Allergan.com"},
            		{"MARCELO","SILVA GUIMARAES","HERTZ","Farma","110605","Terceira","Guimaraes_Marcelo@Allergan.com"},
            		{"MARCOS","JOSE SANTOS CARREIRO","HERTZ","Farma","110402","Terceira","Carreiro_Marcos@Allergan.com"},
            		{"MARCOS","TADEU NEVES LIA","HERTZ","Farma","110404","Terceira","Lia_Marcos@Allergan.com"},
            		{"MARCUS VINICIUS","PEREIRA CAMPOS","HERTZ","Farma","110703","Terceira","Campos_Marcus@Allergan.com"},
            		{"MARIO CELESTINO","OLIVEIRA FILHO","HERTZ","Farma","110700","Terceira","Oliveira_Mario@Allergan.com"},
            		{"NADJA","FERNANDA BORGES LEITE","HERTZ","Farma","110300","Terceira","Borges_Nadja@Allergan.com"},
            		{"NERILSON","DYBAS BARROS","HERTZ","Farma","110105","Terceira","Barros_Nerilson@Allergan.com"},
            		{"NILTON","BATISTA MARÇAL","HERTZ","Farma","110504","Terceira","Marcal_Nilton@Allergan.com"},
            		{"NOEMI","ANA DE BARROS","HERTZ","Farma","110203","Terceira","Barros_Noemi@Allergan.com"},
            		{"PAULO","GUILHERME DELVAN","HERTZ","Farma","110103","Terceira","Delvan_Paulo@Allergan.com"},
            		{"PAULO","ODECIO FERNANDES JUNIOR","HERTZ","Farma","110305","Terceira","Fernandes_Paulo@Allergan.com"},
            		{"RAFAEL","HERMAN MAURO","HERTZ","Farma","110307","Terceira","Mauro_Rafael@Allergan.com"},
            		{"RAFAEL","MAGALHAES DE OLIVEIRA","HERTZ","Farma","110607","Terceira","Oliveira_Rafael@Allergan.com"},
            		{"RICARDO","SA DE SOUZA LIMA","HERTZ","Farma","110608","Terceira","Lima_Ricardo@allergan.com"},
            		{"RICARDO","SOUTO VIANNA","HERTZ","Farma","110306","Terceira","Vianna_Ricardo@Allergan.com"},
            		{"ROBERTO","LUIZ SOARES","HERTZ","Farma","110603","Terceira","Soares_Roberto@Allergan.com"},
            		{"SHEILA","MARIA BROENN DA S DIAS","HERTZ","Farma","110403","Terceira","Dias_Sheila@Allergan.com"},
            		{"SONIA","CRISTINA OLIVEIRA","HERTZ","Farma","110304","Terceira","Oliveira_Sonia@Allergan.com"},
            		{"TAIS","AYRES DE ALMEIDA CHAVES","HERTZ","Farma","110606","Terceira","Chaves_Tais@Allergan.com"},
            		{"THIAGO","ALBUQUERQUE DA SILVA","HERTZ","Farma","110506","Terceira","Silva_Thiago@Allergan.com"},
            		{"WAGNER","BRANCO DE ANDRADE","HERTZ","Farma","110204","Terceira","Andrade_Wagner@Allergan.com"},
            		{"ANTONIO","SABRE NASSER FILHO","VO","Botox Terapêutica","EXECUTIVO","Própria","Nasser_Antonio@Allergan.com"},
            		{"CARLOS ALBERTO","JOSE","VO","Botox Terapêutica","EXECUTIVO","Própria","Jose_Carlos@Allergan.com"},
            		{"EDUARDO","MARTINELLI SCHAFER","VO","Botox Terapêutica","EXECUTIVO","Própria","Schafer_Eduardo@Allergan.com"},
            		{"MARIO","EGIDIO ZANOTI SILVA","VO","Botox Terapêutica","BR21203T","Terceira","Silva_Mario@Allergan.com"},
            		{"SIMONE","ANDREA RODRIGUES BIO ALBERTINI","VO","Botox Terapêutica","EXECUTIVO","Própria","Albertini_Simone@Allergan.com"},
            		{"PAULO HENRIQUE","GOMES DE O. DUARTE","VO","Breast","EXECUTIVO","Própria","Duarte_Paulo@Allergan.com"},
            		{"CRISTIANE REGINA","DE O RUBINIAK","VO","Cosmetics","EXECUTIVO","Própria","Rubiniak_Cristiane@Allergan.com"},
            		{"GISELE","CADAVAL","VO","Cosmetics","EXECUTIVO","Própria","Cadaval_Gisele@Allergan.com"},
            		{"ODAIR","KIYOSHI HOMMA","VO","Cosmetics","EXECUTIVO","Própria","Homma_Odair@Allergan.com"},
            		{"SIMONE","PETRONI AGRA","VO","Cosmetics","EXECUTIVO","Própria","Agra_Simone@Allergan.com"},
            		{"GENI","LIKA MATSUMURA","VO","Customer Services","EXECUTIVO","Própria","Matsumura_Lika@Allergan.com"},
            		{"ANDRE","LORENZ DE AZEVEDO","VO","Farma","EXECUTIVO","Própria","Azevedo_Andre@Allergan.com"},
            		{"CARLA","DOCAMPO FERRARI","VO","Farma","EXECUTIVO","Própria","Ferrari_Carla@Allergan.com"},
            		{"CARLOS ALBERTO","DE CARVALHO PUGLIA","VO","Farma","EXECUTIVO","Própria","Puglia_Carlos@Allergan.com"},
            		{"CLEOMAR","FERRAZ ALIANE","VO","Farma","EXECUTIVO","Própria","Aliane_Cleomar@Allergan.com"},
            		{"MARCELO","CARLETTI","VO","Farma","EXECUTIVO","Própria","Carletti_Marcelo@Allergan.com"},
            		{"ARNO","WILFRIED HABICHT","VO","Finance","EXECUTIVO","Própria","Habicht_Arno@Allergan.com"},
            		{"EDUARDO","HENRIQUE C DE SOUZA","VO","Finance","EXECUTIVO","Própria","Souza_Eduardo@Allergan.com"},
            		{"JOYCE","MUHR","VO","Finance","EXECUTIVO","Própria","Muhr_Joyce@Allergan.com"},
            		{"MARCELO","HOUARA LORDELLO","VO","Finance","EXECUTIVO","Própria","Lordello_Marcelo@Allergan.com"},
            		{"PAULO ROGERIO","ANTUNES CORREA","VO","GADS","EXECUTIVO","Própria","Correa_Paulo_BR@Allergan.com"},
            		{"NICO","ANDRIESSE","VO","Health","EXECUTIVO","Própria","Andriesse_Nico@Allergan.com"},
            		{"SELMA","CAETANO DA SILVA PEREIRA","VO","Health","EXECUTIVO","Própria","Pereira_Selma@Allergan.com"},
            		{"MARA CRISTINA","GALLINARO DE SA","VO","Human Resources","EXECUTIVO","Própria","Gallinaro_Cristina@Allergan.com"},
            		{"SILVIA","F B EDLER VON SCHMADEL","VO","Human Resources","EXECUTIVO","Própria","Schmadel_Silvia@Allergan.com"},
            		{"PAULO ANTONIO","RIBEIRO MURAD","VO","IS","EXECUTIVO","Própria","Murad_Paulo@Allergan.com"},
            		{"PAULO","KENGI NAKANO","VO","Logistics","EXECUTIVO","Própria","Nakano_Paulo@Allergan.com"},
            		{"NELSON","ROBERTO DE ALMEIDA MARQUES","VO","Presidence","EXECUTIVO","Própria","Marques_Nelson@Allergan.com"},
            		{"CARLA","VIOTTO BELLI","VO","Reg. Affairs","EXECUTIVO","Própria","Belli_Carla@Allergan.com"},
            		{"MARIA PAULA","MARQUES","VO","Reg. Affairs","EXECUTIVO","Própria","Marques_Paula@Allergan.com"}
            		
       			};

	        File f = new File("C://usersAllergan1.sql");
	        FileOutputStream fos = null;

	        Class.forName("org.postgresql.Driver");
	        Connection conn1 = DriverManager.getConnection("jdbc:postgresql://67.192.60.149:5432/assesment","postgres","pr0v1s0r1A");
	        Statement st = conn1.createStatement();
			fos = new FileOutputStream(f);
            
			
			String idAssessment = "40"; // Allergan Ref 1
            ResultSet setDone = st.executeQuery("SELECT ua.loginname " +
            		"FROM useranswers ua " +
            		"JOIN userassesments uas ON (ua.loginname = uas.loginname AND ua.assesment = uas.assesment) " +
            		"JOIN answerdata ad ON ua.answer = ad.id " +
            		"JOIN questions q ON ad.question = q.id " +
            		"WHERE ua.assesment = " + idAssessment + " " + // ID Assessment
            		"GROUP BY ua.loginname " +
            		"HAVING count(*) = 128"); // Cantidad de usuarios en el assessment
            while(setDone.next()) {
            	doneUsers.add(setDone.getString("loginname"));
            }

            MD5 md5 = new MD5();
	    	for(int i = 0; i < Math.min(1000,users.length); i++) {
	    		String firstName = users[i][0];
	    		String lastName = users[i][1];
	    		String extraData = users[i][2]+","+users[i][3]+","+users[i][4]+","+users[i][5];
	    		String email = users[i][6];
	    		int in = 1;
	            boolean done = false;
	        	while(!done) {
	                try {
	
	                	String href = md5.encriptar(email);
		        		String login = md5.encriptar(href);
		        		String password = md5.encriptar(login);		        		
		        		password = md5.encriptar(password);
		        		/*
	                	ResultSet set = st.executeQuery("SELECT COUNT(*) AS c FROM users WHERE loginname = '" +login+"'");
	        			String insert = "INSERT INTO users (loginname,firstname,lastname,language,email,password,role,extradata) " +
	        					"VALUES ('"+login+"','"+firstName+"','"+lastName+"','pt','"+email+"','"+password+"','systemaccess','"+extraData+"');\n";
	                	set.next();
	                	if(set.getInt("c") == 0) {
	                		fos.write(insert.getBytes());
	                	}
		        		insert = "INSERT INTO userassesments(assesment,loginname) VALUES ("+idAssessment+",'"+login+"');\n";
		        		fos.write(insert.getBytes());
		        		*/
//	        			email = "federico.millan@cepasafedrive.com"; 
	        			email = "gbenaderet@cepasafedrive.com";
	        			
	        			
		        		if(!doneUsers.contains(login)) {
		        	        Properties properties = new Properties();
		        	        properties.put("mail.smtp.host",SMTP);
		        	        properties.put("mail.smtp.auth", "true");
		        	        properties.put("mail.from",FROM_NAME);
		        	        Session session = Session.getInstance(properties,null);
		        	    
		        	        InternetAddress[] address = {new InternetAddress(email,users[i][0]+" "+users[i][1])
       							 						,new InternetAddress("cepadriverassessment@gmail.com","INDESA")
       							};		        	              	        		        	        
       
		        			Message mensaje = new MimeMessage(session);
		        			// Rellenar los atributos y el contenido
		        			// Asunto
		        			mensaje.setSubject("Invitación a Participar del Driver Assessment");
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
		        			imagen.attachFile(AssesmentData.FLASH_PATH+"/images/invitacion-allergan.jpg");
		        			imagen.setHeader("Content-ID","<figura1>");
		        			multipart.addBodyPart(imagen);
		        			mensaje.setContent(multipart);
		        			// Enviar el mensaje
		
		        	        Transport transport = session.getTransport(address[0]);
		        	        transport.connect(SMTP, FROM_DIR, PASSWORD);
		        	        transport.sendMessage(mensaje,address);

		        	        System.out.println("ENVIADO "+in+" "+firstName+" "+lastName+" "+email);
		        	        // System.out.println("http://da.cepasafedrive.com/assesment/index.jsp?login="+href);
		        	        in++;
		        		}else {
		        	        System.out.println("FINALIZADO "+email);        	        
		        		}
		        		
	        	        done = true;
		            }catch (Exception e) {
		            	e.printStackTrace();
		    	        System.out.println("EXCEPCION "+email);
		            	// 	TODO: handle exception
		            }
	    		}
	    	}
		} catch (Exception e) {
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
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;; color: rgb(102, 102, 102);\">" +
			"						Em linha com o comunicado enviado ontem pela Allergan, disponibilizamos a seguir seu acesso para a ferramenta &ldquo;Driver Assessment&rdquo;." +
			"						<br/><br/>"+
			"						O t&eacute;rmino desta atividade est&aacute; previsto para 05/abril/2010. Por favor, responda todos os question&aacute;rios at&eacute; esta data para receber o relat&oacute;rio de desempenho."+
			"						<br/><br/>"+
			"						O link abaixo &eacute; privado e servir&aacute; para voc&ecirc; acessar a ferramenta sempre que desejar.  Se voc&ecirc; iniciar o preenchimento do question&aacute;rio, por&eacute;m, por alguma raz&atilde;o n&atilde;o puder concluir os m&oacute;dulos, poder&aacute; usar o mesmo link para depois reiniciar o sistema e finalizar as atividades."+
			"						<br/><br/>"+
			"						Uma vez finalizados todos os m&oacute;dulos, voc&ecirc; receber&aacute; o relat&oacute;rio de desempenho em formato PDF neste mesmo endere&ccedil;o de correio eletr&ocirc;nico."+
			"						<br/><br/>"+
			"					</span>"+
			
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;;\">" +
			"						" +
			"						<a class=\"moz-txt-link-freetext\" href=\""+href+"\">" + href + "</a>" +
			"						" +
			"					<span>" +
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;; color: rgb(102, 102, 102);\">" +
			"						<br/><br/>"+			
			"						Agradecemos sua participa&ccedil;&atilde;o."+
			"						Sua contribui&ccedil;&atilde;o ser&aacute; muito valiosa e contamos com ela!"+
			"						<br/><br/>"+
			"						Em caso de d&uacute;vidas ou consultas, favor entrar em contato com:  indesa@cepasafedrive.com"+
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
