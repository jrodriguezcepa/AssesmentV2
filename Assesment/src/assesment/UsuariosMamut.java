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

public class UsuariosMamut {
	
    protected static final String SMTP = "smtp.cepasafedrive.com";
    protected static final String FROM_NAME = "CEPA - Driver Assessment";
    protected static final String FROM_DIR = "da@cepasafedrive.com";
    protected static final String PASSWORD = "dacepa09";

    
    public static void main(String[] args) {

        try {
            Collection doneUsers = new LinkedList();
	        int EOF = 65535;
	
            
            
            File fin = new File("C:/mamut.csv");
	        FileInputStream fis = new FileInputStream(fin);
	
	        String[][] values = new String[10][3];
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

	        File f = new File("C:/Mamut.sql");
	        FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(f);
			} catch (Exception e) {
				e.printStackTrace();
			}
	            
	        File f2 = new File("C:/Mamut_links.csv");
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
	    		String division = values[i][2].trim();
	    		String email = "mamut_"+division;
	    		if(email != null && email.length() > 0) {
	    			
		    		boolean done = false;
		        	while(!done) {
		                try {
			        		String href = md5.encriptar(email);
			        		String login = md5.encriptar(href);
			        		String password = md5.encriptar(login);
			        		password = md5.encriptar(password);
			        		
			        		if(!doneUsers.contains(login)) {
				        		fos2.write(new String(fullName+";"+division+";"+"http://da.cepasafedrive.com/assesment/index.jsp?login="+href+"\n").getBytes());
			        			String insert = "INSERT INTO users (loginname,firstname,lastname,language,password,role,extradata) VALUES ('"+login+"','"+firstName.trim()+"','"+lastName.trim()+"','es','"+password+"','systemaccess','"+division+"');\n";
			        			fos.write(insert.getBytes());
			        			insert = "INSERT INTO userassesments(assesment,loginname) VALUES (52,'"+login+"');\n";
			        			fos.write(insert.getBytes());

			        	        System.out.println("ENVIADO "+index+" "+fullName);
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
}
