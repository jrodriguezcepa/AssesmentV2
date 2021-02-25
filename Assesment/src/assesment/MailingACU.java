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

public class MailingACU {
	
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
            Connection conn1 = DriverManager.getConnection("jdbc:postgresql://67.192.60.149:5432/assesment","postgres","pr0v1s0r1A");
            Statement st = conn1.createStatement();
            Statement st2 = conn1.createStatement();
                        
            File fin = new File("C:/Users/Juan Rodriguez/Desktop/acu_users_4.csv");
	        FileInputStream fis = new FileInputStream(fin);

	        String[][] values = new String[4][6];
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

	    	for(i = 0; i < Math.min(2000,values.length); i++) {
	    		String name = values[i][0].trim();
	    		String login = values[i][2].trim();
	    		String password = values[i][3].trim();
	    		String email = values[i][4].trim();
	    		String number = values[i][5].trim();

	    		ResultSet set = st.executeQuery("select count(*) from users where loginname = '"+login+"' and emailsended = CURRENT_DATE");
	    		set.next();
	    		if(set.getInt(1) == 0) {
		    		String subject;
		    		MailSender sender;
		    		if(email != null && email.length() > 0) {
		                try {
			        		//email = "jrodriguez@cepasafedrive.com";
		        			subject = "PROGRAMA INTERNO SEGURIDAD VIAL ACU 2012 - Test de Usuario y Conductor Seguro";
	
		        			String  body = getMail(login,password,name,number);
	
		        			sender = new MailSender();
		        			String[] address = sender.getAvailableMailAddress();
		        			if(address[0] != null) {
		        				String[] ccAddress = new String[0];
	/*	        				if(emailCopy != null && emailCopy.length() > 0 && !emailCopy.equals("---")) {
		        					ccAddress = new String[1];
		        					ccAddress[0] = email;
		        				}
		        				
	*/	        				
		        				// System.out.println("Aca mandaria el email...");
		        				
		        				try {
		        					sender.sendImage(email, "CEPA Driver Assessment", address[0], address[1], 
		        							subject, new LinkedList(), body, AssesmentData.FLASH_PATH+"/images/pantalla_acu2.jpg",ccAddress);
		        					
		        					System.out.println("'"+login+"', ");
		        					
		        					st2.executeUpdate("UPDATE users SET emailsended = CURRENT_DATE where loginname='"+login+"'");
		        					
		        				}catch (Exception e) {
		        					e.printStackTrace();
		        					System.out.println("EXCEPCION "+login);	
		        				}
		    		            
				        		Thread.sleep(50000);	        			
		        				
		        					
		        			}
	
			            }catch (Exception e) {
		        			System.out.println("ERROR '"+email+"',");
		        			e.printStackTrace();	
		        			
		        			
			            }
		    		}
		    	}
	    	}
    	}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getMail(String usuario, String password, String name, String number) {
		String mail = "<html>" +
			"	<head>" +
			"	</head>" +
			"	<body bgcolor=\"#ffffff\" text=\"#000066\">" +
			"		<table>" +
			"			<tr>" +
			"				<td>"+
			"<p class=\"MsoNormal\"><u></u>&nbsp;<u></u></p>"+
			"<p class=\"MsoNormal\"><b><span lang=\"ES-UY\" style=\"font-size:10.0pt;font-family:&quot;Verdana&quot;,&quot;sans-serif&quot;;color:#666666\">"+name+" ("+number+")</span></b><u></u></p>"+
			"<p class=\"MsoNormal\"><u></u>&nbsp;<u></u></p>"+
			"<p class=\"MsoNormal\"><span lang=\"ES-UY\" style=\"font-size:10.0pt;font-family:&quot;Verdana&quot;,&quot;sans-serif&quot;;color:#666666\">Link: <a href=\"http://da.cepasafedrive.com/\" target=\"_blank\"><b>http://da.cepasafedrive.com/</b></a></span><u></u><u></u></p>"+
			"<p class=\"MsoNormal\"><u></u>&nbsp;<u></u></p>"+
			"<p class=\"MsoNormal\"><b><span lang=\"ES-UY\" style=\"font-size:10.0pt;font-family:&quot;Verdana&quot;,&quot;sans-serif&quot;;color:#666666\">Usuario:</span></b><span lang=\"ES-UY\" style=\"font-size:10.0pt;font-family:&quot;Verdana&quot;,&quot;sans-serif&quot;;color:#666666\">&nbsp;"+usuario+"</span><u></u><u></u></p>"+
			"<p style=\"margin-bottom:12.0pt\"><b><span lang=\"ES-UY\" style=\"font-size:10.0pt;font-family:&quot;Verdana&quot;,&quot;sans-serif&quot;;color:#666666\">Clave:</span></b><span lang=\"ES-UY\" style=\"font-size:10.0pt;font-family:&quot;Verdana&quot;,&quot;sans-serif&quot;;color:#666666\">&nbsp;"+password+"<u></u><u></u></span></p>"+
			"<p class=\"MsoNormal\"><span lang=\"ES-UY\" style=\"font-size:10.0pt;font-family:&quot;Verdana&quot;,&quot;sans-serif&quot;;color:#666666\">Por consultas técnicas: <a href=\"mailto:indesa.cepasafedrive.com/\" target=\"_blank\"><b>indesa@cepasafedrive.com</b></a></span><u></u><u></u></p>"+
			"<p class=\"MsoNormal\"><u></u>&nbsp;<u></u></p>"+
			"				</td>" +
			"			</tr>" +
			"			<tr>" +
			"				<td>" +
			"					<img src=\"cid:figura1\" alt=\"\">" +
			"					<br>" +
			"				</td>" +
			"			</tr>" +
			"		</table>" +
			"	</body>" +
			"</html>";
		return mail;
	}
}
