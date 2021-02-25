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
import java.util.Iterator;
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
import assesment.communication.question.AnswerData;
import assesment.communication.util.MD5;

public class ListaJJ {
	

    public static void main(String[] args) {

    	try {
    		
            Class.forName("org.postgresql.Driver");
            Connection conn1 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/assesment","postgres","pr0v1s0r1A");
            Statement st = conn1.createStatement();

            Connection conn2 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/datacenter5","postgres","pr0v1s0r1A");
            Statement st2 = conn2.createStatement();
            
            File fout = new File("C:/ListaJJ.csv");
	        FileOutputStream fos = new FileOutputStream(fout);

	        Hashtable names = new Hashtable();
            Collection doneUsers = new LinkedList();
            ResultSet set = st.executeQuery("SELECT ua.loginname,u.firstname,u.lastname " +
            		"FROM useranswers ua " +
            		"JOIN userassesments uas ON (ua.loginname = uas.loginname AND ua.assesment = uas.assesment) " +
            		"JOIN answerdata ad ON ua.answer = ad.id " +
            		"JOIN questions q ON ad.question = q.id " +
            		"JOIN users u ON u.loginname = ua.loginname "+
            		"WHERE ua.assesment = 11 " +
            		"AND uas.psiid IS NOT NULL " +
            		"GROUP BY ua.loginname,u.firstname,u.lastname " +
            		"HAVING count(*) = 60");
            while(set.next()) {
            	String loginname = set.getString("loginname"); 
            	doneUsers.add(loginname);
            	String data = set.getString("loginname")+";"+set.getString("firstname")+";"+set.getString("lastname")+";";
            	names.put(loginname,data);
            }
            
        	fos.write(new String("LOGIN;NOMBRE;APELLIDO;WWID;ID;NOMBRE DC5;APELLIDO DC5;WWID DC5\n").getBytes());
	        
	        Iterator it = doneUsers.iterator();
	        int no = 0;
            while(it.hasNext()) {
            	String loginname = (String)it.next();
	        	fos.write(String.valueOf(names.get(loginname)).getBytes());

	        	set = st.executeQuery("SELECT ad.text FROM useranswers ua " +
            			"JOIN answerdata ad ON ua.answer = ad.id " +
            			"WHERE ua.assesment = 11 " +
            			"AND ua.loginname = '"+loginname+"' " +
            			"AND ad.question = 1121");
        		if(set.next()) {
        			String WWID = set.getString("text");
        			fos.write(new String(WWID+";").getBytes());
        			ResultSet set2 = st2.executeQuery("SELECT id,firstname,lastname,corporationid FROM drivers WHERE corporation = 4 AND corporationid = '"+WWID+"'");
        			boolean found = false;
        			while(set2.next()) {
        				String s = set2.getString("id")+";"+set2.getString("firstname")+";"+set2.getString("lastname")+";"+set2.getString("corporationid")+";";
            			fos.write(s.getBytes());
            			found = true;
        			}
        			
        			if(!found) {
        				StringTokenizer tokenizer = new StringTokenizer((String)names.get(loginname),";");
        				tokenizer.nextToken();
        				String f = tokenizer.nextToken().toLowerCase();
        				String l = tokenizer.nextToken().toLowerCase();
            			set2 = st2.executeQuery("SELECT id,firstname,lastname,corporationid FROM drivers WHERE corporation = 4 AND lower(firstname) LIKE '%"+f+"%' AND lower(lastname) LIKE '%"+l+"%'");
            			while(set2.next()) {
            				String s = set2.getString("id")+";"+set2.getString("firstname")+";"+set2.getString("lastname")+";"+set2.getString("corporationid")+";";
                			fos.write(s.getBytes());
                			found = true;
            			}
        			}
        			
        			if(!found) {
        				no++;
        			}
        		}
	        	fos.write(new String("\n").getBytes());
            }
            System.out.println("NO "+no);
		}catch (Exception e) {
			e.printStackTrace();
		}

    }
}
