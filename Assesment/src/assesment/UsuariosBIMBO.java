package assesment;

import java.io.File;
import java.io.FileOutputStream;
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

public class UsuariosBIMBO {
	
    protected static final String SMTP = "smtp.cepasafedrive.com";
    protected static final String FROM_NAME = "CEPA - Driver Assessment";
    protected static final String FROM_DIR = "da@cepasafedrive.com";
    protected static final String PASSWORD = "dacepa09";

    /**
	 * @param args
	 */
	public static void main(String[] args) {
/*       	String[][] users = {{"marcelofavioechegoyen","19375521","Marcelo Favio","Echegoyen Corrente"}, 
       			{"fernandomartinmenyou","39590721","Fernando Martin","Menyou Schoning"},
       			{"mariosebastiancelle","38316657","Mario Sebastian","Celle Rosa"},
       			{"rogersclovisgoncalves","42387751","Rogers Clovis","Goncalves de Borba"}, 
       			{"waltertrinidad","40801472","Walter","Trinidad Fagundez"},
       			{"hectormatiasfarias","43574096","Hector Matias","Farias Blundell"},
       			{"carlosomarromero","49222651","Carlos Omar","Romero Almeda"},
       			{"sebastiansantosantunez","38681086","Sebastian Santos","Antunez Leiva"},
       			{"deiviedmarcelcastro","36845224","Deivied Marcel","Castro Mendez"}, 
       			{"gerardopassade","15763922","Gerardo","Passade Spinelli"},
       			{"carlosmauriciotejera","39765958","Carlos Mauricio","Tejera Gutierrez"}, 
       			{"pablosebastiansureda","39677725","Pablo Sebastián","Sureda Zabala"},
       			{"pabloandressuarez","38511322","Pablo Andrés","Suarez Dematteis"},
       			{"pablofernandomarquez","29308360","Pablo Fernando","Marquez Padilla"},
       			{"carlosvicentelaino","19391048","Carlos Vicente","Laino Pedrozo"},
       			{"jorgepedrojuncadella","12648498","Jorge Pedro","Juncadella Sarda"},
       			{"damianalejandrogerman","36834534","Damian Alejandro","German Machado"},
       			{"leonardofabricio","18715097","Leonardo Fabricio","Gallo"},
       			{"carlosmariacaceres","13290488","Carlos María","Cáceres Casales"}, 
       			{"carlosrobertomanzuetti","19450788","Carlos Roberto","Manzuetti Nusspaumer"},
       			{"juliocesardacosta","18583999","Julio Cesar","Dacosta  Leao"},
       			{"giordanogreco","27269067","Giordano","Greco Chelle"}, 
       			{"alvarojaviertomas","31442053","Álvaro Javier","Tomas Oliera"},
       			{"santiagobentacourt","33601843","Santiago","Bentacourt Ordóñez"},
       			{"pablodanielrodriguez","28057687","Pablo Daniel","Rodriguez Morales"},
       			{"juanmanuelrodriguez","26264492","Juan Manuel","Rodriguez Escande"},
       			{"joseenriquebertiz","16689915","José Enrique","Bertiz  Velázquez"},
       			{"sergioivanohefabra","16261129","Sergio Ivanohe","Fabra San Martín"}};*/

       	String[][] users = {{"marcelosoriaroggero","37688316","Marcelo Alejandro","Soria Roggero"},
       			{"diegogomezaguiar","36663933","Diego Valentín","Gomez Aguiar"},    
       			{"martinfernandezcastro","33287558","Martin Rogelio","Fernandez Castro"},
       			{"reynaldoferraoetcheverria","46143781","Reynaldo Martín","Ferrao Etcheverria"},  
       			{"pablocasalborgen","36014087","Pablo Augusto","Casal Borgen"}}; 
       	
       	try {

        	File f = new File("C://usersBIMBO_2.sql");
        	FileOutputStream fos = new FileOutputStream(f);
            
        	MD5 md5 = new MD5();
        	for(int i = 0; i < users.length; i++) {
        		String login = users[i][0];
    			String password = md5.encriptar(users[i][1]);
    			String firstName = users[i][2];
    			String lastName = users[i][3];
       			String insert = "INSERT INTO users (loginname,firstname,lastname,language,password,role) VALUES ('"+login+"','"+firstName+"','"+lastName+"','es','"+password+"','systemaccess');\n";
       			fos.write(insert.getBytes());
        		insert = "INSERT INTO userassesments(assesment,loginname) VALUES (24,'"+login+"');\n";
        		fos.write(insert.getBytes());
            }
    	}catch (Exception e) {
        	e.printStackTrace();
        }
	}
}
