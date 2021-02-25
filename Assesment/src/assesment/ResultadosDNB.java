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

public class ResultadosDNB {
	

    public static void main(String[] args) {

    	try {
    		
            Class.forName("org.postgresql.Driver");
            Connection conn1 = DriverManager.getConnection("jdbc:postgresql://67.192.60.149:5432/assesment","postgres","pr0v1s0r1A");
            Statement st = conn1.createStatement();
            Statement st2 = conn1.createStatement();
            
            File fout = new File("C:/ResultadosDNB_DatosPersonales.csv");
	        FileOutputStream fos = new FileOutputStream(fout);

	        Hashtable<String,String> names = new Hashtable<String,String>();
            Collection<String> doneUsers = new LinkedList<String>();
            ResultSet set = st.executeQuery("SELECT ua.loginname,firstname,lastname,email,brithdate,nationality,licenseexpiry,vehicle,location,extradata,sex,startdate " +
            		"FROM useranswers ua " +
            		"JOIN userassesments uas ON (ua.loginname = uas.loginname AND ua.assesment = uas.assesment) " +
            		"JOIN answerdata ad ON ua.answer = ad.id " +
            		"JOIN questions q ON ad.question = q.id " +
            		"JOIN users u ON u.loginname = ua.loginname "+
            		"WHERE ua.assesment = 58 " +
            		"GROUP BY ua.loginname,firstname,lastname,email,brithdate,nationality,licenseexpiry,vehicle,location,extradata,sex,startdate " +
            		"HAVING count(*) = 59");
            while(set.next()) {
            	String loginname = set.getString("loginname"); 
            	doneUsers.add(loginname);
            	String data = set.getString("firstname")+";"+set.getString("lastname")+";"+set.getString("brithdate")+";"+set.getString("email")+";";
            	data += set.getString("extradata")+";";
            	data += set.getDate("startdate")+";";
            	data += set.getDate("licenseexpiry")+";";
            	switch(set.getInt("location")) {
	            	case 1: 
	            		data += "Menos de 3 meses;";
	            		break;
	            	case 2: 
	            		data += "Menos de 6 meses;";
	            		break;
	            	case 3: 
	            		data += "1 año;";
	            		break;
	            	case 4: 
	            		data += "2 años;";
	            		break;
	            	case 5: 
	            		data += "3 años;";
	            		break;
	            	case 6: 
	            		data += "4 años;";
	            		break;
	            	case 7: 
	            		data += "5 años;";
	            		break;
	            	case 8: 
	            		data += "6 años o más;";
	            		break;
            		default: 
            			data += "---;";
            	}
            	switch(set.getInt("nationality")) {
	            	case 1: 
	            		data += "Artigas;";
	            		break;
	            	case 2: 
	            		data += "Canelones;";
	            		break;
	            	case 3: 
	            		data += "Cerro Largo;";
	            		break;
	            	case 4: 
	            		data += "Colonia;";
	            		break;
	            	case 5: 
	            		data += "Durazno;";
	            		break;
	            	case 6: 
	            		data += "Flores;";
	            		break;
	            	case 7: 
	            		data += "Florida;";
	            		break;
	            	case 8: 
	            		data += "Lavalleja;";
	            		break;
	            	case 9: 
	            		data += "Maldonado;";
	            		break;
	            	case 10: 
	            		data += "Montevideo;";
	            		break;
	            	case 11: 
	            		data += "Paysandú;";
	            		break;
	            	case 12: 
	            		data += "Río Negro;";
	            		break;
	            	case 13: 
	            		data += "Rivera;";
	            		break;
	            	case 14: 
	            		data += "Rocha;";
	            		break;
	            	case 15: 
	            		data += "Salto;";
	            		break;
	            	case 16: 
	            		data += "San José;";
	            		break;
	            	case 17: 
	            		data += "Soriano;";
	            		break;
	            	case 18: 
	            		data += "Tacuarembó;";
	            		break;
	        		case 19: 
	        			data += "Treinta y Tres;";
	            		break;
	        		default: 
	        			data += "---;";
	        	}
            	switch(set.getInt("sex")) {
	            	case 1: 
	            		data += "ZyD;";
	            		break;
	            	case 2: 
	            		data += "CCB;";
	            		break;
	            	case 3: 
	            		data += "Subdirección;";
	            		break;
	        		default: 
	        			data += "---;";
	        	}
            	data += set.getString("vehicle")+";";
            	names.put(loginname,data);
            }
            
	        int[] answers = new int[13];
	        set = st.executeQuery("select id,gm.text from questions q, generalmessages gm where q.key = gm.labelkey and module = 276 and gm.language = 'es' order by questionorder");
	        int i = 0; 
        	fos.write(new String("NOMBRE;APELLIDO;FECHA DE NACIMIENTO;EMAIL;TELEFONOS;EXPEDICIÓN DE LICENCIA;VENCIMIENTO DE LICENCIA;ANTIGÜEDAD;DEPARTAMENTO;COMANDO;DESTACAMENTO;").getBytes());
	        while(set.next()) {
	        	fos.write(new String(set.getString("text")+";").getBytes());
	        	answers[i] = set.getInt("id");
	        	i++;
	        }
        	fos.write(new String("\n").getBytes());
	        
	        Iterator<String> it = doneUsers.iterator();
            while(it.hasNext()) {
            	String loginname = it.next();
	        	fos.write(String.valueOf(names.get(loginname)).getBytes());
            	for(i = 0; i < answers.length; i++) {
            		switch(answers[i]) {
            			case 5081:
            				set = st.executeQuery("SELECT ad.text FROM useranswers ua " +
                			"JOIN answerdata ad ON ua.answer = ad.id " +
                			"WHERE ua.assesment = 58 " +
                			"AND ua.loginname = '"+loginname+"' " +
                			"AND ad.question = "+answers[i]);
            				if(set.next()) {
            					fos.write(new String(set.getString("text")+";").getBytes());
            				}
            				break;
            			case 5097:
            				set = st.executeQuery("SELECT ad.text,ad.distance FROM useranswers ua " +
                			"JOIN answerdata ad ON ua.answer = ad.id " +
                			"WHERE ua.assesment = 58 " +
                			"AND ua.loginname = '"+loginname+"' " +
                			"AND ad.question = "+answers[i]);
            				if(set.next()) {
            					String s = set.getString("text");
            					if(set.getInt("distance") == 0) {
            						s += " kms;";
            					}else {
            						s += " millas;";
            					}
            					fos.write(s.getBytes());
            				}
            				break;
            			case 5109: case 5098: case 5106: case 5107: case 5108: 
            			case 5113: case 5114: case 5088: case 5104: case 5091: case 5086:
            				set = st.executeQuery("SELECT gm.text FROM useranswers ua " +
    						"JOIN answerdata ad ON ua.answer = ad.id " +
    						"JOIN answers a ON a.id = ad.answer " +
    						"JOIN generalmessages gm ON gm.labelkey = a.key " +
    						"WHERE ua.assesment = 58 " +
    						"AND gm.language = 'es' " +
                			"AND ua.loginname = '"+loginname+"' " +
                			"AND ad.question = "+answers[i]);
            				if(set.next()) {
            					fos.write(new String(set.getString("text")+";").getBytes());
            				}
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
            		"WHERE id IN (273,274) AND gm.language = 'es'");
            while(set.next()) {
            	String module = set.getString("id");
                fout = new File("C:/ResultadosDNB_"+set.getString("text")+".csv");
    	        fos = new FileOutputStream(fout);

    	        ResultSet set1 = st2.executeQuery("SELECT gm.text FROM questions q " +
    	        		"JOIN generalmessages gm ON gm.labelkey = q.key " +
    	        		"WHERE q.module = " +module+" "+
    	        		"AND gm.language = 'es' " +
    	        		"ORDER BY questionorder");
            	fos.write(new String("NOMBRE;APELLIDO;FECHA DE NACIMIENTO;EMAIL;TELEFONOS;EXPEDICIÓN DE LICENCIA;VENCIMIENTO DE LICENCIA;ANTIGÜEDAD;DEPARTAMENTO;COMANDO;DESTACAMENTO;").getBytes());
    	        while(set1.next()) {
    	        	fos.write(new String(set1.getString("text")+";").getBytes());
    	        }
	        	fos.write(new String("\n").getBytes());
    	        
    	        it = doneUsers.iterator();
    	        while(it.hasNext()) {
    	        	String loginname = (String)it.next();
    	        	ResultSet set2 = st2.executeQuery("SELECT a.type FROM useranswers ua " +
    	        			"JOIN answerdata ad ON ua.answer = ad.id " +
    	        			"JOIN questions q ON q.id = ad.question " +
    	        			"JOIN answers a ON a.id = ad.answer " +
    	        			"WHERE ua.assesment = 58 " +
    	        			"AND ua.loginname = '"+loginname+"'" +
    	        			"AND q.module = " +module+" " +
    	        			"AND q.testtype = 1 "+
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

}
