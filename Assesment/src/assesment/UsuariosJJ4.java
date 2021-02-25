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

import assesment.business.administration.user.UsReportFacade;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.user.UserData;
import assesment.communication.util.MD5;

public class UsuariosJJ4 {
	
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
            Connection conn = DriverManager.getConnection("jdbc:postgresql://74.124.197.17:5432/datacenter5","postgres","pr0v1s0r1A");       
            Statement st = conn.createStatement();
            
        	Class.forName("org.postgresql.Driver");
            Connection conn2 = DriverManager.getConnection("jdbc:postgresql://173.247.255.40:5432/assesment","postgres","pr0v1s0r1A");       
            Statement st2 = conn2.createStatement();
            Statement st3 = conn2.createStatement();

            File fin = new File("C:/Users/Juan Rodriguez/Desktop/da2013.csv");
	        FileInputStream fis = new FileInputStream(fin);
	
	        String[][] values = new String[191][6];
	        StringBuffer word = new StringBuffer();
	        char car = 0;
	        i = 0;
	        while(car != EOF) {
	            car = 0;
	            int j = 0;
	            while(car != '\n' && car != EOF) {
	                car = (char)fis.read();
	                if(car == '\t' || car == '\n') {
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

	        File f = new File("C:/Users/Juan Rodriguez/Desktop/assessmentjj51.sql");
	        FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(f);
			} catch (Exception e) {
				e.printStackTrace();
			}
	            
	        File f2 = new File("C:/Users/Juan Rodriguez/Desktop/assessmentjj51_links.csv");
	        FileOutputStream fos2 = null;
			try {
				fos2 = new FileOutputStream(f2);
			} catch (Exception e) {
				e.printStackTrace();
			}
	            

            MD5 md5 = new MD5();
	        int index = 1;
    		int code = 2706;
    		Collection<String> emails = new LinkedList<String>();
	    	for(i = 1; i < Math.min(1000,values.length); i++) {

	    		String supervisor = values[i][0].trim();
	    		String wwid = values[i][1].trim();
	    		String language = getLanguage(values[i][2].trim());
	    		String op_group = getReference(values[i][3].trim());
	    		String co = values[i][4].trim();
	    		String copyEmail = values[i][5].trim();
	    		String email = values[i][5].trim();
	    		if(email != null && email.length() > 0) {
	    			
		    		boolean done = false;
		        	while(!done) {
		                try {
		                	if(!email.equals("---")) {
				        		String href = md5.encriptar(email);
				        		String login = md5.encriptar(href);
				        		
					    		if(emails.contains(login)) {
					    			System.out.println("ERROR "+login+" "+i);
					    		}else {
					    			emails.add(login);
					    		}
	
					    		String password = md5.encriptar(login);
				        		password = md5.encriptar(password);
				        		
				        		String[] driver = getDriver(wwid, st);
				        		String driverId = driver[0];
					    		String firstName = driver[1];
					    		String lastName = driver[2];
				        		String country = driver[3];
				        		String sex = (driver[4].equals("datatype.sex.female")) ? "1" : "2";
					    		String fullName = firstName + " " + lastName;
				        		if(!doneUsers.contains(login)) {
					        		
					        		String insert = "";
					        		ResultSet setA = st2.executeQuery("select loginname,email from users where datacenter = "+driverId+" AND loginname IN (select loginname FROM userassesments where assesment in (11,57,70,85,115))");
					        		if(setA.next()) {
					        			login = setA.getString(1);
					        			String email2 = setA.getString(2);
					        			insert = "UPDATE users SET " +
			        					"extradata = '"+op_group+"'," +
			        					"WHERE loginname = '"+login+"';\n";
					        			String href2 = md5.encriptar(email2);
						        		fos2.write(new String(fullName+";"+email+";"+"http://da.cepasafedrive.com/assesment/index.jsp?login="+href2+"\n").getBytes());
	//				        			st2.execute("UPDATE users SET email = '"+email.trim()+"' WHERE loginname = '"+login+"'");
					        		}else {
					        			
					        			ResultSet setUser = st3.executeQuery("SELECT loginname,email FROM users WHERE loginname = '"+login+"'");
					        			if(setUser.next()) {
						        			login = setUser.getString(1);
						        			String email2 = setUser.getString(2);
						        			insert = "UPDATE users SET " +
						        					"firstname = '"+firstName.trim()+"', " +
						        					"lastname = '"+lastName.trim()+"', " +
						        					"sex = "+sex+"," +
						        					"country = "+country+"," +
						        					"datacenter = "+driverId+", " +
						        					"language = '"+language+"'," +
						        					"vehicle = '"+supervisor+"'," +
						        					"extradata = '"+op_group+"'," +
						        					"extradata2 = '"+email.trim()+"' " +
						        					"WHERE loginname = '"+login+"';\n";
						        			String href2 = md5.encriptar(email2);
							        		fos2.write(new String(fullName+";"+email+";"+"http://da.cepasafedrive.com/assesment/index.jsp?login="+href2+"\n").getBytes());
					        			}else {
					        				insert = "INSERT INTO users (loginname,firstname,lastname,sex,country,email,language,password,role,vehicle,datacenter,extradata,extradata2,extradata3) VALUES ('"+login+"','"+firstName.trim()+"','"+lastName.trim()+"',"+sex+","+country+",'"+email+"','"+language+"','"+password+"','systemaccess','"+supervisor+"',"+driverId+",'"+op_group+"','"+email+"','"+copyEmail+"');\n";
					        			}
					        			//System.out.println(insert);
	//				        			st2.execute("INSERT INTO users (loginname,firstname,lastname,sex,country,email,language,password,role,vehicle,datacenter) VALUES ('"+login+"','"+firstName.trim()+"','"+lastName.trim()+"',"+sex+","+driver[1]+",'"+email+"','"+language+"','"+password+"','systemaccess','"+supervisor+"',"+driver[0]+")");
						        		fos2.write(new String(fullName+";"+email+";"+"http://da.cepasafedrive.com/assesment/index.jsp?login="+href+"\n").getBytes());
					        		}
				        			fos.write(insert.getBytes());
				        		/*	insert = "INSERT INTO userassesments(assesment,loginname) VALUES (115,'"+login+"');\n";
				        			fos.write(insert.getBytes());
					        		setA = st2.executeQuery("select count(*) from usercodes where loginname = '"+login+"'");
					        		setA.next();
					        		boolean exists = setA.getInt(1) > 0; 
					        		if(!exists) {
					        			insert = "INSERT INTO usercodes VALUES ("+code+",'"+login+"');\n";
	//				        			st2.execute("INSERT INTO usercodes VALUES ("+code+",'"+login+"')");
					        			fos.write(insert.getBytes());
					        		}
	*/
				        	      //  System.out.println("ENVIADO "+index+" "+fullName);
				        	        code++;
				        	        index++;
				        		}			        	        
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

    private static String getLanguage(String language) {
		if(language.equals("Español")) {
			return "es";
		} else if(language.equals("Portugués")) {
			return "pt";
		} else if(language.equals("Inglés")) {
			return "en";
		}  
		return null;
	}

	private static String[] getDriver(String first, String last, String corporationId, Statement st) throws Exception {
    	String[] s = new String[3];
    	ResultSet set = st.executeQuery("SELECT id,country,sex FROM drivers WHERE corporation = 4 AND lower(trim(corporationid)) = '"+corporationId.toLowerCase()+"'");
    	if(set.next()) {
    		s[0] = set.getString(1);
    		s[1] = set.getString(2);
    		s[2] = set.getString(3);
    		if(set.next()) {
    		//	System.out.println("REPETIDO "+" "+first+" "+last+" "+corporationId);
    			//return getDriver(first, last, st);
    		}
    		return s;
    	}else {
			//System.out.println("NO ENCONTRADO "+" SELECT id FROM drivers WHERE corporation = 4 AND lower(corporationid) = '"+corporationId.toLowerCase()+"'");
			//return getDriver(first, last, st);
    	}
    	return null;
    }

    private static String[] getDriver(String wwid,Statement st) throws Exception {
    	String[] s = new String[5];
    	ResultSet set = st.executeQuery("SELECT id,firstname,lastname,country,sex FROM drivers WHERE id NOT IN (206013) AND corporation = 4 AND lower(trim(corporationid)) = '"+wwid.toLowerCase()+"'");
    	if(set.next()) {
    		s[0] = set.getString(1);
    		s[1] = set.getString(2);
    		s[2] = set.getString(3);
    		s[3] = set.getString(4);
    		s[4] = set.getString(5);
        	if(set.next()) {
            	ResultSet set2 = st.executeQuery("SELECT id,firstname,lastname,country,sex FROM drivers WHERE corporation = 4 AND exclusiondate IS NULL AND lower(corporationid) = '"+wwid.toLowerCase()+"'");
            	if(set2.next()) {
            		s[0] = set2.getString(1);
            		s[1] = set2.getString(2);
            		s[2] = set2.getString(3);
            		s[3] = set2.getString(4);
            		s[4] = set2.getString(5);
	        		if(set2.next()) {
	        			System.out.println("REPETIDO "+wwid);
	        		}
            	}
    		}
    		return s;
    	}else {
			System.out.println("NO ENCONTRADO "+" "+wwid);
    	}
    	return null;
    }

    private static String getReference(String opgroup) throws Exception {
    	if(opgroup.equalsIgnoreCase("CONSUMER")) {
    		return "4461";
    	} else if(opgroup.equalsIgnoreCase("MD&D")) {
    		return "4462";
    	} else if(opgroup.equalsIgnoreCase("PHARMA")) {
    		return "4463";
    	} else if(opgroup.equalsIgnoreCase("Vision Care")) {
    		return "4460";
    	} else {
    		throw new Exception("Error en Op group "+opgroup);
    	}
    }


}
