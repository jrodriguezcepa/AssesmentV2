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

public class UsuariosPlatero {
	
    
    public static void main(String[] args) {

        try {
            Collection doneUsers = new LinkedList();
	        int EOF = 65535;
	
	        File f = new File("C:/Users/Juan Rodriguez/Desktop/platero.sql");
	        FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(f);
			} catch (Exception e) {
				e.printStackTrace();
			}
	            
	        File f2 = new File("C:/Users/Juan Rodriguez/Desktop/platero_users.csv");
	        FileOutputStream fos2 = null;
			try {
				fos2 = new FileOutputStream(f2);
			} catch (Exception e) {
				e.printStackTrace();
			}
	            
			String[][] values = {{"Alejandra","Stirling","51","17445936","alestirling@hotmail.com","1962"},{"Andrés","González","27","48915631","andrés.producción@platero.com.uy","1986"},{"Atilio","Gutiérrez","43","40480468","cepaplatero@gmail.com","1970"},{"Carlos","De Ávila","34","45375383","cepaplatero@gmail.com","1979"},{"Carlos","Fioritti","41","32297099","cfioriti.produccion@platero.com.uy","1972"},{"Eliana","Castaño","25","43868318","ecastano.comercial@platero.com.uy","1988"},{"Federico","Platero","25","43394894","fedeplatero@hotmail.com","1988"},{"Fernando","Platero","54","16454417","pimpo@platero.com.uy","1959"},{"Francisco","Navas","63","9433406","cepaplatero@gmail.com","1950"},{"Francisco","Platero","59","13219769","zika@platero.com.uy","1954"},{"Gerardo","De Ávila","49","41181677","garu7810@hotmail.com","1964"},{"Gonzalo","Cal","23","46447482","cepaplatero@gmail.com","1990"},{"Gustavo","Barrios","53","17433872","cepaplatero@gmail.com","1960"},{"Ignacio","Platero","23","43394907 ","nachoplatero@hotmail.com","1990"},{"José","Vidal","52","36401494","unsiglodefierro@hotmail.com","1961"},{"Joselina","López","60","12894708","joselinalopez@hotmail.com","1953"},{"Juan","de Araujo","28","46229991","juanchis..a.1000@hotmail.com","1985"},{"Julio","Bagnasco","58","15038242","cepaplatero@gmail.com","1955"},{"Marcelo","Perdomo","34","39970400","cepaplatero@gmail.com","1979"},{"Marcelo","Silva","28","39970403","cepaplatero@gmail.com","1985"},{"Martín","Platero","21","45461308","mplatero@adinet.com.uy","1992"},{"Nicolás","Platero","26","37807607","nicoplatero@hotmail.com","1987"},{"Liborio","Bonora","52","18996500","cepaplatero@gmail.com","1961"},{"Richard","Vidal","28","44509375","cepaplatero@gmail.com","1985"},{"Rivera","Astigarraga","51","15505489","rastigarraca.comercial@platero.com.uy","1962"},{"Santiago","Platero","29","37807726","splatero.produccin@platero.com.uy","1984"},{"Sergio","Farias","31","42549983","sfarias.cadeteria@platero.com.uy","1982"},{"Sofía","Bayce","23","45362037","sofia.bayce@gmail.com","1990"}};
	        MD5 md5 = new MD5();
	        Collection users = new LinkedList();
	    	for(int i = 0; i < values.length; i++) {
	    		String firstName = values[i][0].trim();
	    		String lastName = values[i][1].trim();
	    		String fullName = firstName+""+lastName;
	    		String extradata = values[i][3].trim();
	    		String email = values[i][4].trim().toLowerCase();
	    		String anio = values[i][5].trim();
	    		
	    		String login = firstName.toLowerCase().substring(0,1)+replaceChars(lastName.toLowerCase())+"platero";

				if(users.contains(login)) {
					login += "1";
					if(users.contains(login)) {
						System.out.println("| --> "+login);
					}else {
						users.add(login);
					}
				}else {
					users.add(login);
				}

				System.out.println(i);
        		fos2.write(new String(fullName+";"+login+";"+extradata+"\n").getBytes());
    			String insert = "INSERT INTO users (loginname, password, firstname, lastname, brithdate, email, language, role, country, nationality, extradata) "+
    					"VALUES ('"+login+"','"+md5.encriptar(extradata)+"','"+firstName+"','"+lastName+"','"+anio+"-01-01','"+email+"','es','systemaccess',31,31,'"+extradata+"');\n";
    			fos.write(insert.getBytes());
    			insert = "INSERT INTO userassesments(assesment,loginname) VALUES (123,'"+login+"');\n";
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
	        birth = tokDate.nextToken() + "-"+ birth;
	        birth = "'"+tokDate.nextToken() + "-"+ birth+"'";
		}
		return birth;
	}


	private static String replaceChars(String text) {
		String value = "";
		for(int i = 0; i < text.length(); i++) {
			switch(text.charAt(i)) {
				case 'á':
					value += "a";
					break;
				case 'é':
					value += "e";
					break;
				case 'í':
					value += "i";
					break;
				case 'ó':
					value += "o";
					break;
				case 'ú':
					value += "u";
					break;
				case 'ñ':
					value += "n";
					break;
				case ' ':
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
