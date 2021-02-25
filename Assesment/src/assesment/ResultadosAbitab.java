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

public class ResultadosAbitab {
	

    public static void main(String[] args) {

    	try {
    		
            Class.forName("org.postgresql.Driver");
            Connection conn1 = DriverManager.getConnection("jdbc:postgresql://67.192.60.149:5432/assesment","postgres","pr0v1s0r1A");
            Statement st = conn1.createStatement();
            Statement st2 = conn1.createStatement();
            Statement st3 = conn1.createStatement();
            
            File fout = new File("C:/ResultadosAbitab_DatosPersonales.csv");
	        FileOutputStream fos = new FileOutputStream(fout);

	        Hashtable names = new Hashtable();
            Collection doneUsers = new LinkedList();
/*            ResultSet set = st.executeQuery("SELECT ua.loginname,u.firstname,u.lastname,u.email,u.brithdate," +
            		" u.extradata AS phone,u.vehicle AS mobile" +
            		" FROM useranswers ua " +
            		" JOIN userassesments uas ON (ua.loginname = uas.loginname AND ua.assesment = uas.assesment) " +
            		" JOIN answerdata ad ON ua.answer = ad.id JOIN questions q ON ad.question = q.id " +
            		" JOIN users u ON u.loginname = ua.loginname " +
            		" WHERE ua.assesment = 30 " +
            		" GROUP BY ua.loginname,u.firstname,u.lastname,u.email,u.brithdate,u.extradata,u.vehicle,uas.enddate " +
            		" HAVING count(*) = 54 AND enddate >= '2011-01-01 00:00:00.000'  AND enddate < '2011-02-01 00:00:00.000'");
*/
            ResultSet set = st.executeQuery("SELECT ua.loginname,u.firstname,u.lastname,u.email,u.brithdate," +
    		" u.extradata AS phone,u.vehicle AS mobile" +
    		" FROM useranswers ua " +
    		" JOIN userassesments uas ON (ua.loginname = uas.loginname AND ua.assesment = uas.assesment) " +
    		" JOIN answerdata ad ON ua.answer = ad.id JOIN questions q ON ad.question = q.id " +
    		" JOIN users u ON u.loginname = ua.loginname " +
    		" WHERE ua.assesment = 30 " +
    		" GROUP BY ua.loginname,u.firstname,u.lastname,u.email,u.brithdate,u.extradata,u.vehicle ");
            while(set.next()) {
            	String loginname = set.getString("loginname"); 
            	doneUsers.add(loginname);
            	String data = getCI(set.getString("loginname"))+";"+set.getString("firstname")+";"+set.getString("lastname")+";"+set.getDate("brithdate")+";"+set.getString("email")+";"+set.getString("phone")+";"+set.getString("mobile")+";";
            	names.put(loginname,data);
            }
            
	        int[] answers = new int[8];
	        set = st.executeQuery("select id,gm.text from questions q, generalmessages gm where q.key = gm.labelkey and module = 136 and gm.language = 'es' order by questionorder");
	        int i = 0; 
        	fos.write(new String("CI;NOMBRE;APELLIDO;EMAIL;FECHA DE NACIMIENTO;TELEFONO;CELULAR;").getBytes());
	        while(set.next()) {
	        	fos.write(new String(set.getString("text")+";").getBytes());
	        	answers[i] = set.getInt("id");
	        	i++;
	        }
        	fos.write(new String("\n").getBytes());
	        
	        Iterator it = doneUsers.iterator();
            while(it.hasNext()) {
            	String loginname = (String)it.next();
	        	fos.write(String.valueOf(names.get(loginname)).getBytes());
            	for(i = 0; i < answers.length; i++) {
            		switch(answers[i]) {
            			case 2567:
            			case 2568:
            			case 2569:
            			case 2570:
            			case 5192:
            			case 5191:
            			case 5190:
            				set = st.executeQuery("SELECT gm.text FROM useranswers ua " +
    						"JOIN answerdata ad ON ua.answer = ad.id " +
    						"JOIN answers a ON a.id = ad.answer " +
    						"JOIN generalmessages gm ON gm.labelkey = a.key " +
    						"WHERE ua.assesment = 30 " +
    						"AND gm.language = 'es' " +
                			"AND ua.loginname = '"+loginname+"' " +
                			"AND ad.question = "+answers[i]);
            				if(set.next()) {
            					fos.write(new String(set.getString("text")+";").getBytes());
            				}
            				break;
            			case 2571:
            				set = st.executeQuery("SELECT gm.text FROM useranswers ua " +
    						"JOIN answerdata ad ON ua.answer = ad.id " +
    						"JOIN multioptions m ON ad.id = m.id "+
    						"JOIN answers a ON a.id = m.answer "+
    						"JOIN generalmessages gm ON gm.labelkey = a.key " +
    						"WHERE ua.assesment = 30 " +
    						"AND gm.language = 'es' " +
                			"AND ua.loginname = '"+loginname+"' " +
                			"AND ad.question = "+answers[i]);
            				String s = "";
            				while(set.next()) {
            					s += set.getString("text")+"-";
            				}
            				if(s.length() > 2) {
            					s = s.substring(0, s.length()-2)+";";
            				}else {
            					s = ";";
            				}
        					fos.write(s.getBytes());
            				break;
            			default:
            				System.out.println("ERROR "+answers[i]);
            		}
            	}
				fos.write(new String("\n").getBytes());
            }
            fos.close();
            
            set = st.executeQuery("SELECT m.id,gm.text FROM modules m " +
            		"JOIN generalmessages gm ON gm.labelkey = m.key " +
            		"WHERE id IN (135,137) AND gm.language = 'es'");
            while(set.next()) {
            	String module = set.getString("id");
                fout = new File("C:/ResultadosAbitab_"+set.getString("text")+".csv");
    	        fos = new FileOutputStream(fout);

    	        ResultSet set1 = st2.executeQuery("SELECT gm.text FROM questions q " +
    	        		"JOIN generalmessages gm ON gm.labelkey = q.key " +
    	        		"WHERE q.module = " +module+" "+
    	        		"AND gm.language = 'es' " +
    	        		"ORDER BY questionorder");
            	fos.write(new String("CI;NOMBRE;APELLIDO;EMAIL;FECHA DE NACIMIENTO;TELEFONO;CELULAR;").getBytes());
    	        while(set1.next()) {
    	        	fos.write(new String(set1.getString("text")+";").getBytes());
    	        }
	        	fos.write(new String("\n").getBytes());
    	        
    	        it = doneUsers.iterator();
    	        while(it.hasNext()) {
    	        	String loginname = (String)it.next();
    	        	ResultSet set2 = st2.executeQuery("SELECT q.id,a.type FROM useranswers ua " +
    	        			"JOIN answerdata ad ON ua.answer = ad.id " +
    	        			"JOIN questions q ON q.id = ad.question " +
    	        			"JOIN answers a ON a.id = ad.answer " +
    	        			"WHERE ua.assesment = 30 " +
    	        			"AND ua.loginname = '"+loginname+"'" +
    	        			"AND q.module = " +module+" " +
    	        			"ORDER BY q.questionorder");
    	        	fos.write(String.valueOf(names.get(loginname)).getBytes());
    	        	while(set2.next()) {
    	        		if(set2.getInt("type") == AnswerData.CORRECT) {
    	        			fos.write(new String("CORRECTA;").getBytes());
    	        		}else {
    	        			fos.write(new String("INCORRECTA;").getBytes());
    	        		}
    	        	}
    	        	fos.write(new String("\n").getBytes());
    	        }
            }
    	}catch (Exception e) {
			e.printStackTrace();
		}
	}

    private static String getCI(String string) {
		StringTokenizer tokenizer = new StringTokenizer(string,"_");
		if(tokenizer.countTokens() == 3) {
			tokenizer.nextToken();
			return tokenizer.nextToken();
		}
		return "---";
	}
}
