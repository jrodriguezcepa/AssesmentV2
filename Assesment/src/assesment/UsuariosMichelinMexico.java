package assesment;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;
import java.util.StringTokenizer;

import assesment.communication.util.MD5;

public class UsuariosMichelinMexico {
	
    protected static final String SMTP = "smtp.cepasafedrive.com";
    protected static final String FROM_NAME = "CEPA - Driver Assessment";
    protected static final String FROM_DIR = "da@cepasafedrive.com";
    protected static final String PASSWORD = "dacepa09";

    public static void main(String[] args) {
        
		try {
            Collection doneUsers = new LinkedList();

            String[][] users = {{"Durán Pizarro","Erika  Patricia","15/09/1979","blummen@hotmail.com"},
            		{"Jiménez Jiménez","Juan Carlos","13/08/1980","jimenez_juancarlitos@hotmail.com"},
            		{"León Serrano","Israel","13/06/1977","isleo@hotmail.com"},
            		{"Masse Zepeda","Antonio de Jesus","23/07/1979","massezepeda01@hotmail.com"},
            		{"Moreno Chavez","Miguel Ángel","14/03/1977","miguelangel42@hotmail.com"},
            		{"Rodriguez Mendoza","Christian Hector","26/06/1983","krogen_@hotmail.com"},
            		{"Estrada Estrada","Irene","10/08/1971","federico.millan@cepasafedrive.com"},
            		{"Juárez Rivas","Jorge Lucio","28/10/1972","jorgejuarezrivas@hotmail.com"},
            		{"Arias Hinojosa","Roberto","26/12/1968","federico.millan@cepasafedrive.com"}};

	        File f = new File("C://usersMichelinMX6.sql");
	        FileOutputStream fos = null;
			fos = new FileOutputStream(f);

	        File f2 = new File("C://linksMichelin.csv");
	        FileOutputStream fos2 = null;
			fos2 = new FileOutputStream(f2);

	        Class.forName("org.postgresql.Driver");
	        Connection conn1 = DriverManager.getConnection("jdbc:postgresql://67.192.60.149:5432/assesment","postgres","pr0v1s0r1A");
	        Statement st = conn1.createStatement();
            
			
			String idAssessment = "59";
/*            ResultSet setDone = st.executeQuery("SELECT ua.loginname " +
            		"FROM useranswers ua " +
            		"JOIN userassesments uas ON (ua.loginname = uas.loginname AND ua.assesment = uas.assesment) " +
            		"JOIN answerdata ad ON ua.answer = ad.id " +
            		"JOIN questions q ON ad.question = q.id " +
            		"WHERE ua.assesment = " + idAssessment + " " + // ID Assessment
            		"GROUP BY ua.loginname " +
            		"HAVING count(*) = 20"); // Cantidad de usuarios en el assessment
            while(setDone.next()) {
            	doneUsers.add(setDone.getString("loginname"));
            }
*/
            MD5 md5 = new MD5();
	    	for(int i = 0; i < Math.min(1000,users.length); i++) {
	    		String firstName = users[i][0];
	    		String lastName = users[i][1];
	    		StringTokenizer birthdateTokenizer = new StringTokenizer(users[i][2],"/");
	    		String day = birthdateTokenizer.nextToken();
	    		String month = birthdateTokenizer.nextToken();
	    		String year = birthdateTokenizer.nextToken();
	    		String birthdate = year+"-"+month+"-"+day+" 10:00:00.000";
	    		String email = users[i][3];
	    		String full = "MICHELIN_6"+firstName+lastName;
	    		
	            boolean done = false;
	        	while(!done) {
	                try {
	
	                	String href = md5.encriptar(full);// 
	                	String link = firstName+";"+lastName+";http://da.cepasafedrive.com/assesment/index.jsp?login="+href;
	                	
		        		String login = md5.encriptar(href); // a186a378da54d5079878b12d4fd1e2f3
		        		String password = md5.encriptar(login);	// a69f483217d88c26603ede4adee949cc	        		
		        		password = md5.encriptar(password); // 3675e90bb362c733b2835a56cb49ba13

		        		String insert = "INSERT INTO users (loginname,firstname,lastname,brithdate,language,email,password,role) VALUES ('"+login+"','"+firstName+"','"+lastName+"','"+birthdate+"','es','"+email+"','"+password+"','systemaccess');\n";
		        		fos.write(insert.getBytes());
		        		insert = "INSERT INTO userassesments(assesment,loginname) VALUES ("+idAssessment+",'"+login+"');\n";
		        		fos.write(insert.getBytes());
		        		
		        		fos2.write(link.getBytes());

		        		done = true;
		            }catch (Exception e) {
		            	e.printStackTrace();
		    	        System.out.println("EXCEPCION ");
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
			"					<br>" +
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;; color: rgb(102, 102, 102);\">" +
			"						Continuando con el proceso de mejoramiento continuo, lo invitamos nuevamente a utilizar la herramienta web Driver Assessment.<br>"+  
			"						El link a continuaci&oacute;n es privado y el mismo le servir&aacute; para ingresar a la herramienta cada vez que lo desee.<br>" +
			"						Una vez termine todos los m&oacute;dulos, recibir&aacute; los reportes en esta misma cuenta de correo en formato PDF.<br>" +
			"					</span>" +
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;;\">" +
			"						<br>" +
			"						<a class=\"moz-txt-link-freetext\" href=\""+href+"\">" + href + "</a>" +
			"						<br>" +
			"					<span>" +
			"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;;\">" +
			"						<small>" +
			"							<br>" +
			"							<font color=\"#666666\">" +
			"								<i><u>Importante:</u>  En caso de no poder hacer click en el link por favor copie y pegue el link en un navegador.</i>" +
			"								<br/><br/>" +			
			" 								<i>Gracias por su participaci&oacute;n la misma es sumamente valiosa.</i><br/><br/>"+			
			"								<i>Para aclarar dudas y/o consultas por favor comunicarse con <b><a href=\"mailto:indesa@cepasafedrive.com\">indesa@cepasafedrive.com</a></b></i>" +
			"							</font>" +
			"						</small>" +
			"					</span>" +
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
