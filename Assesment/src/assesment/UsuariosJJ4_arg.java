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

public class UsuariosJJ4_arg {
	
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
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/datacenter5","postgres","pr0v1s0r1A");       
            Statement st = conn.createStatement();
            
        	Class.forName("org.postgresql.Driver");
            Connection conn2 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/assesment","postgres","pr0v1s0r1A");       
            Statement st2 = conn2.createStatement();
            Statement st3 = conn2.createStatement();

            File fin = new File("C:/Users/Juan Rodriguez/Desktop/assessmentjj_2.csv");
	        FileInputStream fis = new FileInputStream(fin);
	
	        String[][] values = new String[4][8];
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

	        File f = new File("C:/Users/Juan Rodriguez/Desktop/assessmentjj_2.sql");
	        FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(f);
			} catch (Exception e) {
				e.printStackTrace();
			}
	            
	        File f2 = new File("C:/Users/Juan Rodriguez/Desktop/assessmentjj_2_links.csv");
	        FileOutputStream fos2 = null;
			try {
				fos2 = new FileOutputStream(f2);
			} catch (Exception e) {
				e.printStackTrace();
			}
	            

            MD5 md5 = new MD5();
	        int index = 1;
    		int code = 2537;
    		Collection<String> emails = new LinkedList<String>();
	    	for(i = 1; i < Math.min(1000,values.length); i++) {

	    		String supervisor = values[i][0].trim();
	    		String wwid = values[i][1].trim();
	    		String op_group = getReference(values[i][3].trim());
	    		String language = getLanguage(values[i][4].trim());
	    		String copyEmail = values[i][6].trim();
	    		String email = values[i][7].trim();
	    		if(email != null && email.length() > 0) {
	    			
		    		boolean done = false;
		        	while(!done) {
		                try {
			        		String href = md5.encriptar(email+"_"+i);
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
				    		String firstName = "DEMO";
				    		String lastName = driver[1] + " " + driver[2];
			        		String country = driver[3];
			        		String sex = (driver[4].equals("datatype.sex.female")) ? "1" : "2";
				    		String fullName = firstName + " " + lastName;
			        		if(!doneUsers.contains(login)) {
				        		
				        		String insert = "";
			        			ResultSet setUser = st3.executeQuery("SELECT * FROM users WHERE loginname = '"+login+"'");
			        			if(setUser.next()) {
			        				System.out.println("YA EXISTE "+fullName+" "+email+" "+login);
			        			}
			        			
			        			insert = "INSERT INTO users (loginname,firstname,lastname,sex,country,email,language,password,role,vehicle,datacenter,extradata,extradata2,extradata3) VALUES ('"+login+"','"+firstName.trim()+"','"+lastName.trim()+"',"+sex+","+country+",'"+email+"','"+language+"','"+password+"','systemaccess','"+supervisor+"',"+driverId+",'"+op_group+"','"+email+"','"+copyEmail+"');\n";
			        			//System.out.println(insert);
//				        			st2.execute("INSERT INTO users (loginname,firstname,lastname,sex,country,email,language,password,role,vehicle,datacenter) VALUES ('"+login+"','"+firstName.trim()+"','"+lastName.trim()+"',"+sex+","+driver[1]+",'"+email+"','"+language+"','"+password+"','systemaccess','"+supervisor+"',"+driver[0]+")");
				        		fos2.write(new String(fullName+";"+email+";"+"http://da.cepasafedrive.com/assesment/index.jsp?login="+href+"\n").getBytes());

				        		fos.write(insert.getBytes());
			        			insert = "INSERT INTO userassesments(assesment,loginname) VALUES (85,'"+login+"');\n";
//			        			st2.execute("INSERT INTO userassesments(assesment,loginname) VALUES (70,'"+login+"')");
			        			fos.write(insert.getBytes());
				        		ResultSet setA = st2.executeQuery("select count(*) from usercodes where loginname = '"+login+"'");
				        		setA.next();
				        		boolean exists = setA.getInt(1) > 0; 
				        		if(!exists) {
				        			insert = "INSERT INTO usercodes VALUES ("+code+",'"+login+"');\n";
//				        			st2.execute("INSERT INTO usercodes VALUES ("+code+",'"+login+"')");
				        			fos.write(insert.getBytes());
				        		}

			        	      //  System.out.println("ENVIADO "+index+" "+fullName);
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
    	ResultSet set = st.executeQuery("SELECT id,firstname,lastname,country,sex FROM drivers WHERE id NOT IN (3120,160105,141085,165006,142466,142462,125522,2102,172299) AND corporation = 4 AND lower(corporationid) = '"+wwid.toLowerCase()+"'");
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
    	if(opgroup.equals("Consumo")) {
    		return "4461";
    	} else if(opgroup.equals("Medical")) {
    		return "4462";
    	} else if(opgroup.equals("Pharma")) {
    		return "4463";
    	} else if(opgroup.equals("Vision Care")) {
    		return "4460";
    	} else {
    		throw new Exception("Error en Op group "+opgroup);
    	}
    }


}
