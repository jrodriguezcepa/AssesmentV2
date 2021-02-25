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

public class UsuariosTransmeta {
	
    protected static final String SMTP = "smtp.cepasafedrive.com";
    protected static final String FROM_NAME = "CEPA - Driver Assessment";
    protected static final String FROM_DIR = "da@cepasafedrive.com";
    protected static final String PASSWORD = "dacepa09";

    
    public static void main(String[] args) {

        try {
            Collection doneUsers = new LinkedList();
	        int EOF = 65535;
	
            File fin = new File("C:/transmeta.csv");
	        FileInputStream fis = new FileInputStream(fin);
	
	        String[][] values = new String[113][4];
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

	        File f = new File("C:/transmeta.sql");
	        FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(f);
			} catch (Exception e) {
				e.printStackTrace();
			}
	            
	        MD5 md5 = new MD5();
	    	for(i = 1; i < Math.min(1000,values.length); i++) {
	    		String ci = values[i][0].trim();
	    		String fullName = values[i][1].trim();
	    		StringTokenizer tokenizer = new StringTokenizer(values[i][2].trim(),"/");
	    		String day = tokenizer.nextToken();
	    		String month = tokenizer.nextToken();
	    		String year = tokenizer.nextToken();
	    		String birth = "'"+year+"-"+month+"-"+day+" 00:00:00.000'";
	    		int count = Integer.parseInt(values[i][3].trim());
	    		tokenizer = new StringTokenizer(fullName);
	    		String firstName = "";
	    		String lastName = "";
	    		String login = "";
		        int index = 0;
	    		while(tokenizer.hasMoreTokens()) {
	    			if(index < count){
	    				firstName += tokenizer.nextToken()+" ";
	    				if(index == 0) {
	    					login = firstName.toLowerCase().substring(0, 1);
	    				}
	    			}else {
	    				lastName += tokenizer.nextToken()+" ";
	    				if(index == count) {
	    					login += lastName.substring(0, 1)+ lastName.substring(1,lastName.length()).toLowerCase();
	    				}
	    			}
	    			index++;
	    		}
		        try {
			        		
			        String password = md5.encriptar(ci);
				    String insert = "INSERT INTO users (loginname,firstname,lastname,brithdate,email,language,password,role,extradata) VALUES ('"+login.trim()+"','"+firstName.trim()+"','"+lastName.trim()+"',"+birth+",'federico.millan@gamil.com','es','"+password+"','systemaccess','"+ci+"');\n";
			        fos.write(insert.getBytes());
			        insert = "INSERT INTO userassesments(assesment,loginname) VALUES (62,'"+login.trim()+"');\n";
			        fos.write(insert.getBytes());
			   }catch (Exception e) {
			        e.printStackTrace();
			   }
	    	}

    	}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
