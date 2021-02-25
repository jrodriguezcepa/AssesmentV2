package assesment;

import java.io.File;
import java.io.FileOutputStream;

import assesment.communication.util.MD5;

public class LinkUsuario {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			String firstName = "Demo";
			String lastName = "Basf";
			String language = "pt";
			String key = "demobasf";
			String assessment = "97";
			String country = "32";
			int count = 10;
			
			FileOutputStream fos = new FileOutputStream(new File("C:/Users/Juan Rodriguez/Desktop/"+key+".sql"));
			
			MD5 md5 = new MD5();
			
			for(int i = 1; i <= count; i++) {
				String login = md5.encriptar(key+i);
				String password = md5.encriptar(md5.encriptar(login));
				String insert = "INSERT INTO users VALUES ('"+login+"','"+firstName+"','"+lastName+"','"+language+"',NULL,'"+password+"','systemaccess','1970-01-01 16:25:47.608',2,"+country+","+country+",NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL);\n";
				fos.write(insert.getBytes());
				insert = "INSERT INTO userassesments VALUES ("+assessment+",'"+login+"');\n";
				fos.write(insert.getBytes());
				
				System.out.println("http://www.cepada.com/assesment/index.jsp?login="+key+i);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
