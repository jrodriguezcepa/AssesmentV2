package assesment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.StringTokenizer;

import assesment.communication.assesment.AssesmentData;
import assesment.communication.util.MD5;
import assesment.communication.util.MailSender;

public class UsuariosNycomed {
	
    protected static final String SMTP = "smtp.cepasafedrive.com";
    protected static final String FROM_NAME = "CEPA - Driver Assessment";
    protected static final String FROM_DIR = "da@cepasafedrive.com";
    protected static final String PASSWORD = "dacepa09";

    
    public static void main(String[] args) {

        try {
            Collection doneUsers = new LinkedList();
	        int EOF = 65535;
	
            
            
            File fin = new File("C:/nycomed.csv");
	        FileInputStream fis = new FileInputStream(fin);
	
	        String[][] values = new String[13][4];
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

	        File f = new File("C:/usuarios_nycomed.sql");
	        FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(f);
			} catch (Exception e) {
				e.printStackTrace();
			}
	            
	        File f2 = new File("C:/usuarios_password_nycomed.csv");
	        FileOutputStream fos2 = null;
			try {
				fos2 = new FileOutputStream(f2);
			} catch (Exception e) {
				e.printStackTrace();
			}

			int index = 1;
			MD5 md5 = new MD5();
	    	for(i = 0; i < Math.min(1000,values.length); i++) {
	    		String firstName = values[i][0].trim();
	    		String lastName = values[i][1].trim();
	    		String fullName = firstName + " " + lastName;
	    		String email = values[i][2].trim();
	    		String birthDate = values[i][3].trim();
	    		if(email != null && email.length() > 0) {
	    			
		    		boolean done = false;
		    		MailSender sender = new MailSender();
		        	while(!done) {
		                try {
		                	StringTokenizer token = new StringTokenizer(email,"@");
			        		String login = token.nextToken();
			        		String password1 = getPassword();
			        		String password = md5.encriptar(password1);
			        		
			        		if(!doneUsers.contains(login)) {
				        		fos2.write(new String(fullName+";"+login+";"+password1+"\n").getBytes());
			        			String insert = "INSERT INTO users (loginname,firstname,lastname,email,brithdate,language,password,role) VALUES ('"+login+"','"+firstName.trim()+"','"+lastName.trim()+"','"+email.trim()+"','"+birthDate.trim()+"','pt','"+password+"','systemaccess');\n";
			        			fos.write(insert.getBytes());
			        			insert = "INSERT INTO userassesments(assesment,loginname) VALUES (80,'"+login+"');\n";
			        			fos.write(insert.getBytes());

			        	        System.out.println("ENVIADO "+index+" "+fullName+" "+password1);
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

    private static String getPassword(){
        String randomCharacterSet = "0123456789";
        String result = "";
        for (int i=1; i<=5; i++) {  
            int x = (int)Math.round(Math.random()*(randomCharacterSet.length()-1));
            result+=randomCharacterSet.charAt(x);
        }
        return(result);
        
    }

}
