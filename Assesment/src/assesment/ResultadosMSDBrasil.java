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

public class ResultadosMSDBrasil {
	

    public static void main(String[] args) {

    	try {
    		
            Class.forName("org.postgresql.Driver");
            Connection conn1 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/assesment","postgres","pr0v1s0r1A");
            Statement st = conn1.createStatement();
            Statement st2 = conn1.createStatement();
            
            Hashtable names = new Hashtable();
            Collection doneUsers = new LinkedList();
            ResultSet set = st.executeQuery("SELECT ua.loginname " +
            		"FROM useranswers ua " +
            		"JOIN userassesments uas ON (ua.loginname = uas.loginname AND ua.assesment = uas.assesment) " +
            		"JOIN answerdata ad ON ua.answer = ad.id " +
            		"JOIN questions q ON ad.question = q.id " +
            		"WHERE ua.assesment = 35 " +
            		"AND uas.psiid IS NOT NULL " +
            		"GROUP BY ua.loginname " +
            		"HAVING count(*) = 75");
            while(set.next()) {
            	doneUsers.add(set.getString("loginname"));
            }
            
            File fout = new File("C:/ResultadosMSDBrasil_DatosPersonales.csv");
	        FileOutputStream fos = new FileOutputStream(fout);

	        int[] answers = new int[23];
	        set = st.executeQuery("select id,gm.text from questions q, generalmessages gm where q.key = gm.labelkey and module = 154 and gm.language = 'es' order by questionorder");
	        int i = 0; 
	        while(set.next()) {
	        	fos.write(new String(set.getString("text")+";").getBytes());
	        	answers[i] = set.getInt("id");
	        	i++;
	        }
        	fos.write(new String("\n").getBytes());
	        
	        Iterator it = doneUsers.iterator();
            while(it.hasNext()) {
            	String loginname = (String)it.next();
            	String name = "";
            	for(i = 0; i < answers.length; i++) {
            		switch(answers[i]) {
            			case 2935: case 2936: case 2937: case 2972: case 2973:
            				set = st.executeQuery("SELECT ad.text FROM useranswers ua " +
                			"JOIN answerdata ad ON ua.answer = ad.id " +
                			"WHERE ua.assesment = 35 " +
                			"AND ua.loginname = '"+loginname+"' " +
                			"AND ad.question = "+answers[i]);
            				if(set.next()) {
            					fos.write(new String(set.getString("text")+";").getBytes());
            				}
            				if(answers[i] == 2936) {
            					name = set.getString("text")+";";
            				}else if(answers[i] == 2937) {
            					name += set.getString("text")+";";
            				}
            				break;
            			case 2951:
            				set = st.executeQuery("SELECT ad.text,ad.distance FROM useranswers ua " +
                			"JOIN answerdata ad ON ua.answer = ad.id " +
                			"WHERE ua.assesment = 35 " +
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
            			case 2939: case 2943: case 2944:
            				set = st.executeQuery("SELECT ad.date FROM useranswers ua " +
                			"JOIN answerdata ad ON ua.answer = ad.id " +
                			"WHERE ua.assesment = 35 " +
                			"AND ua.loginname = '"+loginname+"' " +
                			"AND ad.question = "+answers[i]);
            				if(set.next()) {
            					fos.write(new String(set.getString("date")+";").getBytes());
            					if(answers[i] == 2939) {
            						name += set.getString("date")+";";
            					}
            				}
            				break;
            			case 2963: case 2965: case 2971: case 2940: case 2942: case 2945: case 2952: case 2957: case 2958: case 2960: case 2962: case 2967: case 2968:
            				set = st.executeQuery("SELECT gm.text FROM useranswers ua " +
    						"JOIN answerdata ad ON ua.answer = ad.id " +
    						"JOIN answers a ON a.id = ad.answer " +
    						"JOIN generalmessages gm ON gm.labelkey = a.key " +
    						"WHERE ua.assesment = 35 " +
    						"AND gm.language = 'pt' " +
                			"AND ua.loginname = '"+loginname+"' " +
                			"AND ad.question = "+answers[i]);
            				if(set.next()) {
            					fos.write(new String(set.getString("text")+";").getBytes());
            					if(answers[i] == 2968 || answers[i] == 2960 ) {
                					name += set.getString("text")+";";
            					}
            				}
            				break;
            			case 2966:
            				set = st.executeQuery("SELECT gm.text FROM useranswers ua " +
    						"JOIN answerdata ad ON ua.answer = ad.id " +
    						"JOIN multioptions m ON ad.id = m.id "+
    						"JOIN answers a ON a.id = m.answer "+
    						"JOIN generalmessages gm ON gm.labelkey = a.key " +
    						"WHERE ua.assesment = 35 " +
    						"AND gm.language = 'pt' " +
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
            	names.put(loginname,name);
				fos.write(new String("\n").getBytes());
            }
            fos.close();
            
            fout = new File("C:/ResultadosMSDBrasil_PSI.csv");
	        fos = new FileOutputStream(fout);
        	fos.write(new String("NOMBRE;APELLIDO;FECHA DE NACIMIENTO;ACCIDENTES;ULTIMO ENTRENAMIENTO;Actitud Rumbo a la conquista de objetivos;Nivel de stress;\n").getBytes());
	        
	        it = doneUsers.iterator();
            while(it.hasNext()) {
            	String loginname = (String)it.next();
	        	fos.write(String.valueOf(names.get(loginname)).getBytes());
            	set = st.executeQuery("SELECT psiresult1+psiresult2+psiresult3 AS actitud, " +
            			"psiresult4+psiresult5+psiresult6 AS stress " +
            			"FROM userassesments " +
                		"WHERE loginname = '"+loginname+"' ");
            	if(set.next()) {
            		double actitud = set.getDouble("actitud") / 3.0;
            		if(actitud < 3) {
            			fos.write(new String("Verde;").getBytes());
            		}else if(actitud < 4) {
            			fos.write(new String("Amarillo;").getBytes());
            		}else {
            			fos.write(new String("Rojo;").getBytes());
            		}
            		double stress = set.getDouble("stress") / 3.0;
            		if(stress < 3) {
            			fos.write(new String("Verde;\n").getBytes());
            		}else if(stress < 4) {
            			fos.write(new String("Amarillo;\n").getBytes());
            		}else {
            			fos.write(new String("Rojo;\n").getBytes());
            		}
            	}
            }
            
            set = st.executeQuery("SELECT m.id,gm.text FROM modules m " +
            		"JOIN generalmessages gm ON gm.labelkey = m.key " +
            		"WHERE id IN (156,158,157) AND gm.language = 'pt'");
            while(set.next()) {
            	String module = set.getString("id");
                fout = new File("C:/ResultadosMSDBrasil_"+set.getString("text")+".csv");
    	        fos = new FileOutputStream(fout);

    	        ResultSet set1 = st2.executeQuery("SELECT gm.text FROM questions q " +
    	        		"JOIN generalmessages gm ON gm.labelkey = q.key " +
    	        		"WHERE q.module = " +module+" "+
    	        		"AND gm.language = 'pt' " +
    	        		"ORDER BY questionorder");
	        	fos.write(new String("NOMBRE;APELLIDO;FECHA DE NACIMIENTO;ACCIDENTES;ULTIMO ENTRENAMIENTO;").getBytes());
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
    	        			"WHERE ua.assesment = 35 " +
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
