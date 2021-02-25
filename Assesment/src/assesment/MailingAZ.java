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

public class MailingAZ {
	
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
            Connection conn2 = DriverManager.getConnection("jdbc:postgresql://67.192.60.149:5432/elearning","postgres","pr0v1s0r1A");
            Statement st = conn1.createStatement();
            Statement st2 = conn2.createStatement();
                        
            File fin = new File("C:/Users/Juan Rodriguez/Desktop/mailingAZ_2.csv");
	        FileInputStream fis = new FileInputStream(fin);

	        String[][] values = new String[5][3];
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

    		int index = 0;
	    	for(i = 0; i < Math.min(1,values.length); i++) {
	    		String login = values[i][0].trim();
	    		String password = values[i][1].trim();
	    		String email = values[i][2].trim();

	    		boolean send = true;
	    		ResultSet set = st.executeQuery("select emailsended,enddate,elearningredirect,firstname,lastname from users u join userassesments ua on u.loginname = ua.loginname where u.loginname = '"+login+"'");
	    		set.next();
	    		String firstname = set.getString(4);
	    		String lastname = set.getString(5);
	    		if(set.getString(1) == "2012-07-23") {
	    			send = false;
	    		}else if(set.getDate(2) != null) {
	    			if(set.getString(3) == null) {
	    				send = false;
	    			}else {
	    	    		ResultSet set2 = st2.executeQuery("select count(*) from usertests where iduser in (select iduser from users where email = '"+email+"') and (approveddate is null or result < 60)");
	    	    		set2.next();
	    	    		if(set2.getInt(1) == 0) {
	    	    			send = false;
	    	    		}
	    			}
	    		}
		    	
	    		if(send) {
	    			
	    			index++;
/*	    			
	    			String subject;
		    		MailSender sender;
		    		if(email != null && email.length() > 0) {
		                try {
			        		//email = "j@cepasafedrive.com";
		        			subject = "Acceso a entrenamiento electrónico Drive Success - CEPA";
	
		        			String  body = getMail(login,password);
	
		        			sender = new MailSender();
		        			String[] address = sender.getAvailableMailAddress();
		        			if(address[0] != null) {
		        				String[] ccAddress = new String[0];
	/*	        				if(emailCopy != null && emailCopy.length() > 0 && !emailCopy.equals("---")) {
		        					ccAddress = new String[1];
		        					ccAddress[0] = email;
		        				}
		        				
		        				// System.out.println("Aca mandaria el email...");
		        				
		        				try {
		        					sender.sendImage(email, "CEPA Driver Assessment", address[0], address[1], 
		        							subject, new LinkedList(), body, AssesmentData.FLASH_PATH+"/images/recordatorio_az_mx_2012.jpg",ccAddress);
		        					
		        					System.out.println("'"+login+"', ");
		        					
		        					//st2.executeUpdate("UPDATE users SET emailsended = CURRENT_DATE where loginname='"+login+"'");
		        					
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
*/
	    		}
		    	System.out.println(index);
	    	}
    	}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getMail(String usuario, String password) {
		String mail = "<html>" +
			"	<head>" +
			"	</head>" +
			"	<body bgcolor=\"#ffffff\" text=\"#000066\">" +
			"		<table>" +
			"			<tr>" +
			"				<td>"+
			"					<p class=\"MsoNormal\"><u></u>&nbsp;<u></u></p>"+
			"					<p class=\"MsoNormal\"><span lang=\"ES-UY\" style=\"font-size:10.0pt;font-family:&quot;Verdana&quot;,&quot;sans-serif&quot;;color:#666666\"><a href=\"http://da.cepasafedrive.com/\" target=\"_blank\"><b>http://da.cepasafedrive.com/</b></a></span><u></u><u></u></p>"+
			"					<p class=\"MsoNormal\"><b><span lang=\"ES-UY\" style=\"font-size:10.0pt;font-family:&quot;Verdana&quot;,&quot;sans-serif&quot;;color:#666666\">Usuario:</span></b><span lang=\"ES-UY\" style=\"font-size:10.0pt;font-family:&quot;Verdana&quot;,&quot;sans-serif&quot;;color:#666666\">&nbsp;"+usuario+"</span><u></u><u></u></p>"+
			"					<p style=\"margin-bottom:12.0pt\"><b><span lang=\"ES-UY\" style=\"font-size:10.0pt;font-family:&quot;Verdana&quot;,&quot;sans-serif&quot;;color:#666666\">Clave:</span></b><span lang=\"ES-UY\" style=\"font-size:10.0pt;font-family:&quot;Verdana&quot;,&quot;sans-serif&quot;;color:#666666\">&nbsp;"+password+"<u></u><u></u></span></p>"+
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
