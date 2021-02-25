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

public class SucursalesPepsicoCepaSystem {
	
    
    public static void main(String[] args) {

        try {
            Collection doneUsers = new LinkedList();
	        int EOF = 65535;
	
	        Connection conn1 = DriverManager.getConnection("jdbc:postgresql://67.192.60.149:5432/assesment","postgres","pr0v1s0r1A");
	        Statement st = conn1.createStatement();
            
            File fin = new File("C:/Users/Juan Rodriguez/Desktop/sucursal.csv");
	        FileInputStream fis = new FileInputStream(fin);
	
	        String[][] values = new String[1559][3];
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

	        File f = new File("C:/Users/Juan Rodriguez/Desktop/pepsico.sql");
	        FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(f);
			} catch (Exception e) {
				e.printStackTrace();
			}
	            
	    	for(i = 1; i < Math.min(2000,values.length); i++) {
	    		String firstName = values[i][0].trim();
	    		String lastName = values[i][1].trim();
	    		String sucursal = values[i][2].trim();
				String query = "select u.loginname from users u join userassesments ua on ua.loginname = u.loginname where trim(u.firstname) =  '"+firstName+"' and trim(u.lastname) =  '"+lastName+"' AND ua.assesment = 82";
	    		ResultSet set = st.executeQuery(query);
				if(set.next()) {
					String s = "update users set extradata = '"+sucursal+"' where loginname = '"+set.getString("loginname")+"';\n";
					fos.write(s.getBytes());	
					if(set.next()) {
						System.out.println("REPETIDO "+query);
					}
				}else {
					System.out.println("ERROR "+query);
				}

	    	}

    	}catch (Exception e) {
			e.printStackTrace();
		}
	}


	private static String replaceChars(String text) {
		String value = "";
		for(int i = 0; i < text.length(); i++) {
			switch(text.charAt(i)) {
				case 'á':
					value += "a";
					break;
				case 'é':
					value += "e";
					break;
				case 'í':
					value += "i";
					break;
				case 'ó':
					value += "o";
					break;
				case 'ú':
					value += "u";
					break;
				case 'ñ':
					value += "n";
					break;
				default:
					value += String.valueOf(text.charAt(i));
			}
		}
		return value;
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
