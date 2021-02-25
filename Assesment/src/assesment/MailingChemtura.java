package assesment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.LinkedList;

import assesment.communication.assesment.AssesmentData;
import assesment.communication.util.MailSender;

public class MailingChemtura {
	
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
            Connection conn1 = DriverManager.getConnection("jdbc:postgresql://173.247.255.40:5432/assesment","postgres","pr0v1s0r1A");
          //  Connection conn2 = DriverManager.getConnection("jdbc:postgresql://67.192.60.149:5432/elearning","postgres","pr0v1s0r1A");
            Statement st = conn1.createStatement();
            Statement st2 = conn1.createStatement();
              /*          
            File fin = new File("C:/Users/Juan Rodriguez/Desktop/mailingchemtura.csv");
	        FileInputStream fis = new FileInputStream(fin);

	        String[][] values = new String[93][4];
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
*/
    		int index = 0;
	/*    	for(i = 1; i < Math.min(1000,values.length); i++) {
	    		String email = values[i][0].trim();
	    		String login = values[i][1].trim();
	    		String password = values[i][2].trim();
	    		String lang = values[i][3].trim();

	    		ResultSet set = st.executeQuery("select emailsended,enddate from users u join userassesments ua on u.loginname = ua.loginname where u.loginname = '"+login+"'");
	    		set.next();
	    		if(set.getString(2) != null && (set.getString(1) == null || !set.getString(1).equals("2012-10-23"))) {
*/	    			String subject;
String email = "jrodriguez@cepasafedrive.com";
String login = "jrodriguez";
String password = "123456";
		    		MailSender sender;
		    		if(email != null && email.length() > 0) {
		                try {
			        		email = "jrodriguez@cepasafedrive.com";
		        			subject = "Invitación a participar del curso CEPA e-BTW";
	
		        			String  body = getMail(login,password);
	
		        			sender = new MailSender();
	        				String[] ccAddress = new String[0];
	        				//ccAddress[0] = "sabrina.silva@chemtura.com";
/*	        				if(emailCopy != null && emailCopy.length() > 0 && !emailCopy.equals("---")) {
		        					ccAddress = new String[1];
		        					ccAddress[0] = email;
		        				}
*/		        				
	        				// System.out.println("Aca mandaria el email...");
	        				
	        				try {
	        					sender.sendImage(email, "CEPA Driver Assessment", "indesa@cepasafedrive.com","indesa01", 
	        							subject, new LinkedList(), body, AssesmentData.FLASH_PATH+"/images/Mailing_CLARO.jpg",ccAddress);
	        					//Collection bccAddress = new LinkedList();
	        					//sender.send("jrodriguez@cepasafedrive.com","jrodriguez@cepasafedrive.com","$Dc&28a$",
	        			        //		"REPORTED ERROR",body,bccAddress);
	        					System.out.println("'"+login+"', ");
	        					
	        					//st2.executeUpdate("UPDATE users SET emailsended = CURRENT_DATE where loginname='"+login+"'");
	        					
	        				}catch (Exception e) {
	        					e.printStackTrace();
	        					System.out.println("EXCEPCION "+login);	
	        				}
	    		            
			        		Thread.sleep(50000);	        			
	        				
			            }catch (Exception e) {
		        			System.out.println("ERROR '"+email+"',");
		        			e.printStackTrace();	
		        			
		        			
			            }
		    		}
	    	/*	}
	    		index++;
		    	System.out.println(index);*/
	    //	}
    	}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getMail(String usuario, String password) {
		String mail = "<html>";
		
			mail += "	<head>" +
			"	</head>" +
			"	<body bgcolor=\"#ffffff\" text=\"#000066\">" +
			"		<table width='800'>" +
			"			<tr>" +
			"				<td width='100'></td>" +
			"				<td>" +
			"					<img src=\"cid:figura1\" alt=\"\">" +
			"					<br>" +
			"				</td>" +
			"			</tr>" +
			"			<tr>" +
			"				<td width='100'></td>" +
			"				<td>"+
			"<div style=\"margin:0cm 0cm 0.0001pt;font-size:11pt;font-family:Calibri,sans-serif\">" +
			"<b>&nbsp;&nbsp;Link:&nbsp;&nbsp;</b>" +
			"<a href=\"http://ebtw.cepasafedrive.com\" target=\"_blank\">" +
			"<span style=\"font-family:Helvetica,sans-serif;color:purple\">http://ebtw.cepasafedrive.com</span>" +
			"</a>" +
			"</div>" +
			"<div style=\"margin:0cm 0cm 0.0001pt;font-size:11pt;font-family:Calibri,sans-serif\">" +
			"<b>&nbsp;&nbsp;Usuario:&nbsp;&nbsp;</b> " +usuario+
			"</div>" +
			"</div>" +
			"<div style=\"margin:0cm 0cm 0.0001pt;font-size:11pt;font-family:Calibri,sans-serif\">" +
			"<b>&nbsp;&nbsp;Clave:&nbsp;&nbsp;</b> "+password+
			"</div>" +
			"<p class=\"MsoNormal\">" +
			"<div style=\"margin:0cm 0cm 0.0001pt;font-size:11pt;font-family:Calibri,sans-serif\">" +
			"<b>&nbsp;&nbsp;Tutorial:&nbsp;&nbsp;	</b>" +
			"<a href=\"https://vimeo.com/68076404\" target=\"_blank\">" +
			"https://vimeo.com/68076404" +
			"</a>" +
			"</div>" +
			"</p>" +
			"<p class=\"MsoNormal\" style=\"margin-bottom:17pt\">" +
			"<div style=\"margin:0cm 0cm 0.0001pt;font-size:11pt;font-family:Calibri,sans-serif\">" +
			"<b>&nbsp;&nbsp;Para consultas técnicas:&nbsp;&nbsp;</b>" +
			"<a href=\"mailto:indesa@cepasafedrive.com\" target=\"_blank\">" +
			"<span lang=\"PT-BR\" style=\"font-family:Calibri;color:#0c1aca;text-decoration:none\">indesa@cepasafedrive.com</span>" +
			"</a>" +
			"</div>				" +
			"</td>" +
			"			</tr>" +
			"		</table>" +
			"	</body>" +
			"</html>";
		return mail;
	}
}
