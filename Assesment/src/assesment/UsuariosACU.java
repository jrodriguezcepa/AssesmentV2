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
import java.util.StringTokenizer;

import assesment.communication.assesment.AssesmentData;
import assesment.communication.user.UserData;
import assesment.communication.util.MD5;
import assesment.communication.util.MailSender;

public class UsuariosACU {
	
    
    public static void main(String[] args) {

        try {
            Collection doneUsers = new LinkedList();
	        int EOF = 65535;
	
	        Connection conn1 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/assesment","postgres","pr0v1s0r1A");
	        Statement st = conn1.createStatement();
            
            File fin = new File("C:/Users/Juan Rodriguez/Desktop/usuariosAUC2.csv");
	        FileInputStream fis = new FileInputStream(fin);
	
	        String[][] values = new String[4][8];
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

	        File f = new File("C:/Users/Juan Rodriguez/Desktop/acu2.sql");
	        FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(f);
			} catch (Exception e) {
				e.printStackTrace();
			}
	            
	        File f2 = new File("C:/Users/Juan Rodriguez/Desktop/acu_users_3.csv");
	        FileOutputStream fos2 = null;
			try {
				fos2 = new FileOutputStream(f2);
			} catch (Exception e) {
				e.printStackTrace();
			}
	            
	        MD5 md5 = new MD5();
	        Collection users = new LinkedList();
	    	for(i = 0; i < Math.min(1000,values.length); i++) {
	    		String firstName = values[i][3].trim();
	    		String lastName = values[i][4].trim();
	    		String fullName = firstName+" "+lastName;
	    		String email = values[i][7].trim();
	    		
	    		StringTokenizer tokenizer = new StringTokenizer(email,"@");
	    		String login = firstName.toLowerCase().substring(0,1)+lastName.toLowerCase()+".acu";

				if(users.contains(login)) {
					login = firstName.toLowerCase()+"."+lastName.toLowerCase()+".acu";
					if(users.contains(login)) {
						login += ".2";
						if(users.contains(login)) {
							System.out.println("| --> "+login);
						}else {
							users.add(login);
						}
					}else {
						users.add(login);
					}
				}else {
					users.add(login);
				}

				ResultSet set = st.executeQuery("select * from users where loginname = '"+login+"'");
				if(set.next()) {
					login += ".1";
					set = st.executeQuery("select * from users where loginname = '"+login+"'");
					if(set.next()) {
						System.out.println("--> "+login);
					}
				}
	    		String password = getPassword();

	    		String number = values[i][0].trim();
	    		String extradata = values[i][1].trim()+"-"+values[i][2].trim();
	    		String extradata2 = values[i][5].trim();
	    		String extradata3 = values[i][6].trim();

				//System.out.println(i);
        		fos2.write(new String(fullName+";"+extradata+";"+login+";"+password+";"+email+";"+number+"\n").getBytes());
    			String insert = "INSERT INTO users (loginname, password, firstname, lastname, email, language, role, vehicle, extradata, extradata2, extradata3) " +
    					"VALUES ('"+login+"','"+md5.encriptar(password)+"','"+firstName+"','"+lastName+"','"+email+"','es','systemacces','"+number+"','"+extradata+"','"+extradata2+"','"+extradata3+"');\n";
    			fos.write(insert.getBytes());
    			insert = "INSERT INTO userassesments(assesment,loginname) VALUES (66,'"+login+"');\n";
    			fos.write(insert.getBytes());

	    	}

    	}catch (Exception e) {
			e.printStackTrace();
		}
	}


	private static String formatDate(String value) {
		StringTokenizer tokDate = new StringTokenizer(value,"/");
        String birth = "'1970-01-01'";
		if(tokDate.countTokens() == 3) {
	        birth = tokDate.nextToken();
	        birth = tokDate.nextToken() + "-" + birth;
	        birth = "'"+tokDate.nextToken() + "-" + birth+"'";
		}
		return birth;
	}


	private static String replaceChars(String text) {
		String value = "";
		for(int i = 0; i < text.length(); i++) {
			switch(text.charAt(i)) {
				case '�':
					value += "a";
					break;
				case '�':
					value += "e";
					break;
				case '�':
					value += "i";
					break;
				case '�':
					value += "o";
					break;
				case '�':
					value += "u";
					break;
				case '�':
					value += "n";
					break;
				default:
					value += String.valueOf(text.charAt(i));
			}
		}
		return value;
	}

   public static String getPassword(){
       String randomCharacterSet = "123456789";
       int x = (int)Math.round(Math.random()*(randomCharacterSet.length()-1));
       String result = String.valueOf(randomCharacterSet.charAt(x));
       randomCharacterSet = "0123456789";
       for (int i=1; i<=3; i++) {  
    	   x = (int)Math.round(Math.random()*(randomCharacterSet.length()-1));
           result+=randomCharacterSet.charAt(x);
       }
       return result;
    }
}