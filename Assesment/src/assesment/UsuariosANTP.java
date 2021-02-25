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

public class UsuariosANTP {
	
    protected static final String SMTP = "smtp.cepasafedrive.com";
    protected static final String FROM_NAME = "CEPA - Driver Assessment";
    protected static final String FROM_DIR = "da@cepasafedrive.com";
    protected static final String PASSWORD = "dacepa09";

    
    public static void main(String[] args) {

        try {
            Collection doneUsers = new LinkedList();
	        int EOF = 65535;
	
            
            
            File fin = new File("C:/antp.csv");
	        FileInputStream fis = new FileInputStream(fin);
	
	        String[][] values = new String[428][25];
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

	        File f = new File("C:/antp_3.sql");
	        FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(f);
			} catch (Exception e) {
				e.printStackTrace();
			}
	            
	        File f2 = new File("C:/usuariosANTP3.csv");
	        FileOutputStream fos2 = null;
			try {
				fos2 = new FileOutputStream(f2);
			} catch (Exception e) {
				e.printStackTrace();
			}
	            
	        MD5 md5 = new MD5();
	        int index = 1;
	        Collection valueList = new LinkedList();
	    	for(i = 3; i < values.length; i++) {
	    		String company = values[i][0].trim();
	    		
	    		String firstName = values[i][1].trim();
	    		
	    		String lastName = values[i][2].trim();
	    		String lastName2 = values[i][3].trim();
	    		String fullLastName = lastName + " " +lastName2; 
	    			
	    		String camion = values[i][4].trim();
	    		String sex = (isChecked(values[i][5].trim())) ? "2" : "1";
	    		
	    		boolean first = isChecked(values[i][6].trim()) || isChecked(values[i][7].trim()) || isChecked(values[i][8].trim()) || isChecked(values[i][9].trim());
	    		boolean second = isChecked(values[i][10].trim()) || isChecked(values[i][11].trim()) || isChecked(values[i][12].trim()) || isChecked(values[i][13].trim());
	    		
	    		boolean carga = isChecked(values[i][14].trim());
	    		boolean materiales = isChecked(values[i][15].trim());
	    		
	    		int accidentes = 0;
	    		for(int j = 16; j < 21; j++) {
	    			if(!isNotChecked(values[i][j].trim())) {
	    				try {
	    					accidentes = Integer.parseInt(new StringTokenizer(values[i][j].trim(),",").nextToken());
	    				}catch (Exception e) {
	    		    		System.out.println("i "+firstName+" "+fullLastName);
//							e.printStackTrace();
							accidentes = 0;
						}
	    			}
	    		}
	    		/*
	    		String sede = "";
	    		if(isChecked(values[i][21].trim())) {
	    			sede = "GUADALAJARA";
	    		}
	    		if(isChecked(values[i][22].trim())) {
	    			sede = "MEXICO";
	    		}
	    		if(isChecked(values[i][23].trim())) {
	    			sede = "MONTERREY";
	    		}
	    		if(values[i][24] != null && isChecked(values[i][24].trim())) {
	    			sede = "COATZACOALCOS";
	    		}
	    		*/
	    		if(first && second) {
	    			throw new Exception("error 1 "+i);
	    		}
	    		if(carga && materiales) {
	    			throw new Exception("error 2 "+i);
	    		}
	    		
	    		String email = "resultadospnsv2011@antp.org.mx";

			    String login = firstName.substring(0,1).toLowerCase()+lastName.substring(0,1).toUpperCase()+lastName.substring(1, lastName.length()).toLowerCase()+lastName2.substring(0,1).toUpperCase();
			    login = replacement(login);
			    
			    if(valueList.contains(login)) {
			    	login += "1";
			    	valueList.add(login);
			    }else {
			    	valueList.add(login);
			    }
			    String pass = getNewPass();
			    String password = md5.encriptar(pass);
			    			        		
        		fos2.write(new String(company+";"+firstName+";"+fullLastName+";"+login+";"+pass+"\n").getBytes());
        		
    			//String insert = "INSERT INTO users (loginname,firstname,lastname,language,password,email,sex,role,vehicle,location,extradata) VALUES ('"+login+"','"+firstName.trim()+"','"+fullLastName.trim()+"','es','"+password+"','"+email+"',"+sex+",'systemaccess','"+company+"',"+accidentes+",'"+sede+"');\n";
    			//fos.write(insert.getBytes());
        		int nationality = -1;
        		if(camion.equals("Trailer sencillo")) {
        			nationality = 1;
        		}else if(camion.equals("Camion < a 8 ton")) {
        			nationality = 2;
        		}else if(camion.equals("Full")) {
        			nationality = 3;
        		}else if(camion.equals("Camion > 8 ton")) {
        			nationality = 4;
        		}else {
        			System.out.println("ERROR "+i);
        		}

    			String insert = "UPDATE users SET nationality = "+nationality+" WHERE loginname'"+login+"';\n";
    			fos.write(insert.getBytes());

        		int assessment = 0;
    			if(carga) {
    				if(first) {
    					assessment = 72;
    				}else if(second) {
    					assessment = 75;    					
    				}
    			}else if(materiales) {
    				if(first) {
    					assessment = 73;
    				}else if(second) {
    					assessment = 74;    					
    				}
    			}
/*    			 
    			if(assessment == 0) {
	    			throw new Exception("error 3 "+i);
    			}
    			if(assessment > 73) {
    				String insert = "INSERT INTO userassesments(assesment,loginname) VALUES ("+assessment+",'"+login+"');\n";
    				fos.write(insert.getBytes());
    			}
*/
	    	}

    	}catch (Exception e) {
			e.printStackTrace();
		}
	}


	private static boolean isChecked(String value) {
		return value.equals("X");
	}

	private static boolean isNotChecked(String value) {
		return value.equals("0");
	}

    private static String getNewPass(){
    	String result = "";
    	
        String randomCharacterSet = "123456789";
        int x = (int)Math.round(Math.random()*(randomCharacterSet.length()-1));
        result += randomCharacterSet.charAt(x);

        String randomCharacterSet2 = "0123456789";
        for (int i=1; i<=3; i++) {  
            x = (int)Math.round(Math.random()*(randomCharacterSet.length()-1));
            result+=randomCharacterSet2.charAt(x);
        }
        return(result);
        
    }
    
    private static String replacement(String value) {
		  value = value.replace("á", "a");
		  value = value.replace("é", "e");
		  value = value.replace("í", "i");
		  value = value.replace("ó", "o");
		  value = value.replace("ú", "u");
		  value = value.replace("Á", "A");
		  value = value.replace("É", "E");
		  value = value.replace("Í", "I");
		  value = value.replace("Ó", "O");
		  value = value.replace("Ú", "U");
		return value;
	}

}
