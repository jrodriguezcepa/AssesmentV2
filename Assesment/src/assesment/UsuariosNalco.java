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
import java.util.StringTokenizer;

import assesment.communication.assesment.AssesmentData;
import assesment.communication.util.MD5;
import assesment.communication.util.MailSender;

public class UsuariosNalco {
	
    
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
            		"WHERE ua.assesment = 56 " +
            		"GROUP BY ua.loginname " +
            		"HAVING count(*) = 88");
            while(setDone.next()) {
            	doneUsers.add(setDone.getString("loginname"));
            }
            
            
            File fin = new File("C:/nalco2.csv");
	        FileInputStream fis = new FileInputStream(fin);
	
	        String[][] values = new String[2][3];
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

	        File f = new File("C:/UsuariosNalco2.sql");
	        FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(f);
			} catch (Exception e) {
				e.printStackTrace();
			}
	            
	        File f2 = new File("C:/NalcoLinks2.csv");
	        FileOutputStream fos2 = null;
			try {
				fos2 = new FileOutputStream(f2);
			} catch (Exception e) {
				e.printStackTrace();
			}
	            
	        MD5 md5 = new MD5();
	        int index = 1;
	        
	    	for(i = 0; i < Math.min(1000,values.length); i++) {
	        //for(i = 0; i < values.length; i++) {
	    		String fullName = values[i][0].trim();
	    		String email = values[i][1].trim();
	    		int length = Integer.parseInt(values[i][2].trim());
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
					
					System.out.println(firstName + " " + lastName);
		    		boolean done = false;
		    		String image = AssesmentData.FLASH_PATH+"/images/invitacionNalco.jpg";
		        	while(!done) {
		                try {
			        		String href = md5.encriptar(email);
		                	String link = "http://da.cepasafedrive.com/assesment/index.jsp?login="+href;
			        		String login = md5.encriptar(href);
			        		String password = md5.encriptar(login);
			        		password = md5.encriptar(password);
			        		
			        		if(!doneUsers.contains(login)) {
				        		fos2.write(new String(fullName+";"+email+";"+"http://da.cepasafedrive.com/assesment/index.jsp?login="+href+"\n").getBytes());
			        			String insert = "INSERT INTO users (loginname,firstname,lastname,language,email,password,role) VALUES ('"+login+"','"+firstName.trim()+"','"+lastName.trim()+"','es','"+email+"','"+password+"','systemaccess');\n";
			        			fos.write(insert.getBytes());
			        			insert = "INSERT INTO userassesments(assesment,loginname) VALUES (56,'"+login+"');\n";
			        			fos.write(insert.getBytes());
/*					        	email = "federico.millan@cepasafedrive.com";  
					        	email = "jrodriguez@cepasafedrive.com"; */
			        			
			        			// email="gbenaderet@cepasafedrive.com";

			        			/*
			        			MailSender sender = new MailSender();
			        			String[] address = sender.getAvailableMailAddress();
			        			
			        			if(address[0] != null) {
			        				//sender.sendImage(email, "CEPA", address[0], address[1], "Invitación a participar del 'Driver Assessment' online (2)", new LinkedList(), getMail(link), image);
			        			}*/
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
		"					<p class=MsoNormal align=center style='margin-bottom:12.0pt;text-align:center'>" +
		"						<b>" +
		"							<span style='font-size:14.0pt;color:#666666'>"+
		"								Invitaci&oacute;n a participar del 'Driver Assessment' online"+
		"							</span>"+
		"						</b>"+
		"					</p>"+		
		"				</td>" +
		"			</tr>" +
		"			<tr>" +				
		"				<td>" +
		"					<br>" +
		"					<font size=\"-1\"><font face=\"Verdana\">" +
		"					<br>" +
		"					</font></font>" +
		"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;; color: rgb(102, 102, 102);\">" +
		"						Estimados, con el objetivo de optimizar los proceso de capacitaci&oacute;n lo invitamos a participar y completar la herramienta web CEPA Driver Assessment.<br><br>"+
		"						El link a continuaci&oacute;n es privado y &uacute;nico, el mismo le servir&aacute; para ingresar a la herramienta cada vez que lo desee. Una vez termine todos los m&oacute;dulos, recibir&aacute; los reportes en esta misma cuenta de correo en formato PDF.<br><br>"+
		"					</span>" +
		"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;;\">" +
		"						<br>" +
		"						<a class=\"moz-txt-link-freetext\" href=\""+href+"\">" + href + "</a>" +
		"						<br>" +
		"					<span>" +
		"					<span style=\"font-size: 8pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;; color: rgb(102, 102, 102);\">"+
		"						<b>Importante:</b> le pedimos por favor <b><u>no reenviar el link</u></b> a otros participantes, el mismo fue creado espacialmente para usted."+
		"						<br>" +
		"						<br>" +
		"					<span>" +
		"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;; color: rgb(102, 102, 102);\">" +
		"						<i>Le agradecemos su participaci&oacute;n la misma es sumamente valiosa.</i>" +
		"					</span>" +
		"					<br><br>" +
		"					<span style=\"font-size: 10pt; font-family: &quot;Verdana&quot;,&quot;sans-serif&quot;;\">" +
		"							<font color=\"#666666\">" +
		"								<i><u>Importante:</u></i> en caso de no poder acceder a trav&eacute;s del link por favor copie y pegue el link en un navegador.<br>"+
		"								Para aclarar dudas y/o consultas por favor comunicarse con <b><a href=\"mailto:indesa@cepasafedrive.com\">indesa@cepasafedrive.com</a></b>" +
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

}
