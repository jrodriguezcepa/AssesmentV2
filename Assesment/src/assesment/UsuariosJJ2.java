

package assesment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.Hashtable;
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

public class UsuariosJJ2 {
	
    protected static final String SMTP = "smtp.cepasafedrive.com";
    protected static final String FROM_NAME = "CEPA - Driver Assessment";
    protected static final String FROM_DIR = "da@cepasafedrive.com";
    protected static final String PASSWORD = "dacepa09";

    
    public static void main(String[] args) {

    	int i = 0;
        try {
            Collection doneUsers = new LinkedList();
	        int EOF = 65535;
	
        	Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://67.192.60.149:5432/datacenter5","postgres","pr0v1s0r1A");       
            Statement st = conn.createStatement();
            
        	Class.forName("org.postgresql.Driver");
            Connection conn2 = DriverManager.getConnection("jdbc:postgresql://67.192.60.149:5432/assesment","postgres","pr0v1s0r1A");       
            Statement st2 = conn2.createStatement();

            File fin = new File("C:/usuariosJJ7.csv");
	        FileInputStream fis = new FileInputStream(fin);
	
	        String[][] values = new String[2][5];
	        StringBuffer word = new StringBuffer();
	        char car = 0;
	        i = 0;
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

	        File f = new File("C:/UsuariosJJ7.sql");
	        FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(f);
			} catch (Exception e) {
				e.printStackTrace();
			}
	            
	        File f2 = new File("C:/UsuariosJJ7_links.csv");
	        FileOutputStream fos2 = null;
			try {
				fos2 = new FileOutputStream(f2);
			} catch (Exception e) {
				e.printStackTrace();
			}
	            
            Hashtable<String,String> changed = new Hashtable<String,String>();

            MD5 md5 = new MD5();
	        int index = 1;
    		int code = 2530;
    		Collection emails = new LinkedList();
	    	for(i = 0; i < Math.min(1000,values.length); i++) {

	    		String firstName = values[i][0].trim();
	    		String lastName = values[i][1].trim();
	    		String wwid = values[i][2].trim();
	    		String fullName = firstName + " " + lastName;
	    		String email = values[i][3].trim();
	    		if(emails.contains(email)) {
	    			System.out.println("ERROR "+email+" "+i);
	    		}
    			emails.add(email);
	    		String language = "pt";
	    		String supervisor = ""; // values[i][4].trim();
	    		if(email != null && email.length() > 0) {
	    			
		    		boolean done = false;
		        	while(!done) {
		                try {
			        		String href = (changed.containsKey(email)) ? md5.encriptar(changed.get(email)) : md5.encriptar(email);
			        		String login = md5.encriptar(href);
			        		
			        		String password = md5.encriptar(login);
			        		password = md5.encriptar(password);
			        		
			        		String[] driver = getDriver(firstName,lastName,wwid, st);
				    		String sex = (driver[2].equals("datatype.sex.female")) ? "1" : "2";
			        		if(!doneUsers.contains(login)) {
				        		fos2.write(new String(fullName+";"+email+";"+"http://da.cepasafedrive.com/assesment/index.jsp?login="+href+"\n").getBytes());
				        		
				        		String insert = "";
				        		ResultSet setA = st2.executeQuery("select count(*) from users where loginname = '"+login+"'");
				        		setA.next();
				        		boolean exists = setA.getInt(1) > 0; 
				        		if(exists) {
//				        			insert = "UPDATE users SET firstname = '"+firstName.trim()+"', lastname = '"+lastName.trim()+"', language = '"+language+"', password = '"+password+"', extradata = '"+extradata+"', vehicle = '"+supervisor+"', datacenter = "+driver+" WHERE loginname = '"+login+"';\n"; 
				        			insert = "UPDATE users SET email = '"+email.trim()+"' WHERE loginname = '"+login+"';\n";
//				        			st2.execute("UPDATE users SET email = '"+email.trim()+"' WHERE loginname = '"+login+"'");
				        		}else {
				        			insert = "INSERT INTO users (loginname,firstname,lastname,sex,country,email,language,password,role,vehicle,datacenter) VALUES ('"+login+"','"+firstName.trim()+"','"+lastName.trim()+"',"+sex+","+driver[1]+",'"+email+"','"+language+"','"+password+"','systemaccess','"+supervisor+"',"+driver[0]+");\n";
				        			//System.out.println(insert);
//				        			st2.execute("INSERT INTO users (loginname,firstname,lastname,sex,country,email,language,password,role,vehicle,datacenter) VALUES ('"+login+"','"+firstName.trim()+"','"+lastName.trim()+"',"+sex+","+driver[1]+",'"+email+"','"+language+"','"+password+"','systemaccess','"+supervisor+"',"+driver[0]+")");
				        		}
			        			fos.write(insert.getBytes());
			        			insert = "INSERT INTO userassesments(assesment,loginname) VALUES (70,'"+login+"');\n";
//			        			st2.execute("INSERT INTO userassesments(assesment,loginname) VALUES (70,'"+login+"')");
			        			fos.write(insert.getBytes());
				        		setA = st2.executeQuery("select count(*) from usercodes where loginname = '"+login+"'");
				        		setA.next();
				        		exists = setA.getInt(1) > 0; 
				        		if(!exists) {
				        			insert = "INSERT INTO usercodes VALUES ("+code+",'"+login+"');\n";
//				        			st2.execute("INSERT INTO usercodes VALUES ("+code+",'"+login+"')");
				        			fos.write(insert.getBytes());
				        		}

			        	        System.out.println("ENVIADO "+index+" "+fullName);
			        	        code++;
			        	        index++;
			        		}			        	        
		        	        done = true;
			            }catch (Exception e) {
		        	        done = true;
		        	        System.out.println("ERROR en "+i);
			            	e.printStackTrace();
//			    	        System.out.println("EXCEPCION "+email);
			            	// 	TODO: handle exception
			            }
		    		}
	    		}
	    	}

    	}catch (Exception e) {    		
	        System.out.println("ERROR en "+i);
			e.printStackTrace();
		}
	}

    private static String[] getDriver(String first, String last, String corporationId, Statement st) throws Exception {
    	String[] s = new String[3];
    	ResultSet set = st.executeQuery("SELECT id,country,sex FROM drivers WHERE corporation = 4 AND lower(corporationid) = '"+corporationId.toLowerCase()+"' and exclusiondate is null");
    	if(set.next()) {
    		s[0] = set.getString(1);
    		s[1] = set.getString(2);
    		s[2] = set.getString(3);
    		if(set.next()) {
    		//	System.out.println("REPETIDO "+" "+first+" "+last+" "+corporationId);
    			return getDriver(first, last, st);
    		}
    		return s;
    	}else {
			//System.out.println("NO ENCONTRADO "+" SELECT id FROM drivers WHERE corporation = 4 AND lower(corporationid) = '"+corporationId.toLowerCase()+"'");
			return getDriver(first, last, st);
    	}
    }

    private static String[] getDriver(String firstName,String lastName,Statement st) throws Exception {
    	String[] s = new String[3];
    	ResultSet set = st.executeQuery("SELECT id,country,sex FROM drivers WHERE corporation = 4 AND exclusiondate is null AND lower(firstname) like  '%"+firstName.toLowerCase()+"%' AND lower(lastname) like  '"+lastName.toLowerCase()+"%'");
    	if(set.next()) {
    		s[0] = set.getString(1);
    		s[1] = set.getString(2);
    		s[2] = set.getString(3);
    		if(set.next()) {
    			System.out.println("REPETIDO "+firstName+" "+lastName);
    		}
    		return s;
    	}else {
			System.out.println("NO ENCONTRADO "+" "+firstName+" "+lastName);
    	}
    	return null;
    }
}