package assesment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.LinkedList;

import assesment.communication.assesment.AssesmentData;
import assesment.communication.util.MD5;
import assesment.communication.util.MailSender;

public class UsuariosDOW {
	
    protected static final String SMTP = "smtp.cepasafedrive.com";
    protected static final String FROM_NAME = "CEPA - Driver Assessment";
    protected static final String FROM_DIR = "da@cepasafedrive.com";
    protected static final String PASSWORD = "dacepa09";

    
    public static void main(String[] args) {

        try {
            Collection doneUsers = new LinkedList();
	        int EOF = 65535;
	
            
            
            File fin = new File("C:/dow.csv");
	        FileInputStream fis = new FileInputStream(fin);
	
	        String[][] values = new String[51][3];
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

	        File f = new File("C:/dow.sql");
	        FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(f);
			} catch (Exception e) {
				e.printStackTrace();
			}
	            
	        File f2 = new File("C:/dow_links.csv");
	        FileOutputStream fos2 = null;
			try {
				fos2 = new FileOutputStream(f2);
			} catch (Exception e) {
				e.printStackTrace();
			}
	            
	        MD5 md5 = new MD5();
	        int index = 1;
	    	for(i = 0; i < Math.min(1000,values.length); i++) {
	    		String firstName = values[i][0].trim();
	    		String lastName = values[i][1].trim();
	    		String fullName = firstName + " " + lastName;
	    		String email = values[i][2].trim();
	    		boolean first = true;
	    		if(email != null && email.length() > 0) {
	    			
		    		boolean done = false;
		    		MailSender sender = new MailSender();
		        	while(!done) {
		                try {
			        		String href = md5.encriptar(email);
			        		String login = md5.encriptar(href);
			        		String password = md5.encriptar(login);
			        		password = md5.encriptar(password);
			        		
			        		if(!doneUsers.contains(login)) {
				        		fos2.write(new String(fullName+";"+email+";http://da.cepasafedrive.com/assesment/index.jsp?login="+href+"\n").getBytes());
			        			String insert = "INSERT INTO users (loginname,firstname,lastname,language,password,role) VALUES ('"+login+"','"+firstName.trim()+"','"+lastName.trim()+"','es','"+password+"','systemaccess');\n";
			        			fos.write(insert.getBytes());
			        			insert = "INSERT INTO userassesments(assesment,loginname) VALUES (65,'"+login+"');\n";
			        			fos.write(insert.getBytes());

			        			//if(first) {
//			        				sendEmail("http://da.cepasafedrive.com/assesment/index.jsp?login="+href,"rodriguez.jme@gmail.com",sender);
			        				sendEmail("http://da.cepasafedrive.com/assesment/index.jsp?login="+href,email,sender);
//			        				first = false;
//			        			}
			        	        System.out.println("ENVIADO "+index+" "+fullName+" "+email);
			        	        index++;
			        		}			        	        
		        	        done = true;
			            }catch (Exception e) {
			            	e.printStackTrace();
			    	        System.out.println("EXCEPCION "+email);
			            }
		    		}
	    		}
	    	}

    	}catch (Exception e) {
			e.printStackTrace();
		}
	}


	private static void sendEmail(String href, String email, MailSender sender) throws Exception {
		String body = "<html>" +
		"	<head>" +
		"	</head>" +
		"	<body bgcolor=\"#ffffff\" text=\"#000066\">" +
		"		<table>" +
		"			<tr>" +
		"				<td>"+
		" 					<p class=MsoNormal><span lang=ES-UY style='font-size:11.0pt;mso-bidi-font-size: "+
		" 					11.0pt;line-height:115%;font-family:\"sans-serif\"'>Continuando con el proceso de mejoramiento continuo, lo invitamos a utilizar la herramienta web Driver Assessment.<o:p></o:p></span></p><br> "+
		" 					<p class=MsoNormal><span lang=ES-UY style='font-size:11.0pt;mso-bidi-font-size:"+
		" 					11.0pt;line-height:115%;font-family:\"sans-serif\"'>El link a continuaci&oacute;n es privado y el mismo le servir&aacute; para ingresar a la herramienta cada vez que lo desee."+
		" 					<o:p></o:p></span></p>"+
		" 					<p class=MsoNormal><span lang=ES-UY style='font-size:11.0pt;mso-bidi-font-size:"+
		" 					11.0pt;line-height:115%;font-family:\"sans-serif\"'>Una vez termine todos los módulos, recibir&aacute; los reportes en esta misma cuenta de correo en formato PDF.<o:p></o:p></span></p><br>"+
		" 					<p class=MsoNormal><span lang=EN-US style='font-size:11.0pt;"+
		" 					mso-bidi-font-size:11.0pt;line-height:115%;font-family:\"sans-serif\";"+
		" 					mso-fareast-font-family:\"Times New Roman\";mso-bidi-font-family:Calibri;"+
		" 					color:#1F497D;mso-ansi-language:EN-US;mso-fareast-language:ES-UY'><a href='"+href+"'>"+href+"</a><o:p></o:p></span></p><br>"+
		" 					<p class=MsoNormal style='margin-bottom:12.0pt'><span"+
		" 					lang=ES-UY style='font-size:9.0pt;mso-bidi-font-size:12.0pt;line-height:115%;"+
		" 					font-family:\"sans-serif\";color:black'><i>Nuevamente le agradecemos su participaci&oacute;n la misma es sumamente valiosa.</i><o:p></o:p></span></p><br>"+
		" 					<p class=MsoNormal><span lang=ES-UY style='font-size:9.0pt;mso-bidi-font-size:"+
		" 					9.0pt;line-height:115%;font-family:\"sans-serif\"'><b><span lang=ES-UY"+
		" 					style='font-size:9.0pt;mso-bidi-font-size:9.0pt;line-height:115%;font-family:"+
		" 					\"sans-serif\";color:black'>Importante: </span></b> en caso de no poder acceder a través del link por favor copie y pegue el link en un navegador. No comparta su link con otros participante, el mismo es privado y exclusivo para usted. Para aclarar dudas y/o consultas por favor comunicarse con </span><span lang=ES-UY><a"+
		" 					href=\"mailto:indesa@cepasafedrive.com\" target=\"_blank\"><b><span"+
		" 					style='font-size:9.0pt;mso-bidi-font-size:9.0pt;line-height:115%;font-family:"+
		" 					\"sans-serif\"'>indesa@cepasafedrive.com</span></b></a></span><span"+
		" 					lang=ES-UY style='font-size:9.0pt;mso-bidi-font-size:9.0pt;line-height:115%;"+
		" 					font-family:\"sans-serif\"'><o:p></o:p></span></p>"+
		"				</td>" +
		"			</tr>" +
		"					<br>" +
		"					<br>" +
		"					<br>" +
		"			<tr>" +
		"				<td>" +
		"					<img src=\"cid:figura1\" alt=\"\">" +
		"					<br>" +
		"				</td>" +
		"			</tr>" +
		"		</table>" +
		"	</body>" +
		"</html>";
		Collection files = new LinkedList();
		String[] address = sender.getAvailableMailAddress();
		if(address[0] != null) {
			sender.sendImage(email, "CEPA Driver Assessment", address[0], address[1], "USUARIO DOW", files, body, AssesmentData.FLASH_PATH+"/images/logos_cepa_dow_email.jpg");
		}
	}
}
