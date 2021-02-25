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

public class ResultadosJJ {
	

    public static void main(String[] args) {

    	try {
    		
            Class.forName("org.postgresql.Driver");
            Connection conn1 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/assesment","postgres","pr0v1s0r1A");
            Statement st = conn1.createStatement();
            Statement st2 = conn1.createStatement();
            
            File fout = new File("C:/ResultadosJJ_DatosPersonales.csv");
	        FileOutputStream fos = new FileOutputStream(fout);

	        Hashtable names = new Hashtable();
            Collection doneUsers = new LinkedList();
            ResultSet set = st.executeQuery("SELECT ua.loginname,u.firstname,u.lastname,u.email,u.extradata,u.vehicle " +
            		"FROM useranswers ua " +
            		"JOIN userassesments uas ON (ua.loginname = uas.loginname AND ua.assesment = uas.assesment) " +
            		"JOIN answerdata ad ON ua.answer = ad.id " +
            		"JOIN questions q ON ad.question = q.id " +
            		"JOIN users u ON u.loginname = ua.loginname "+
            		"WHERE ua.assesment = 57 " +
            		"AND uas.psiid IS NOT NULL " +
            		"GROUP BY ua.loginname,u.firstname,u.lastname,u.email,u.extradata,u.vehicle " +
            		"HAVING count(*) = 62");
            while(set.next()) {
            	String loginname = set.getString("loginname"); 
            	doneUsers.add(loginname);
            	String data = set.getString("firstname")+";"+set.getString("lastname")+";"+set.getString("email")+";"+set.getString("extradata")+";"+set.getString("vehicle");
            	names.put(loginname,data);
            }
            
	        int[] answers = new int[22];
	        set = st.executeQuery("select id,gm.text from questions q, generalmessages gm where q.key = gm.labelkey and module = 63 and gm.language = 'es' order by questionorder");
	        int i = 0; 
        	fos.write(new String("NOMBRE;APELLIDO;FECHA DE NACIMIENTO;EMAIL;OP_GROUP;SUPERVISOR;").getBytes());
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
            			case 1121: case 1054:
            				set = st.executeQuery("SELECT ad.text FROM useranswers ua " +
                			"JOIN answerdata ad ON ua.answer = ad.id " +
                			"WHERE ua.assesment = 11 " +
                			"AND ua.loginname = '"+loginname+"' " +
                			"AND ad.question = "+answers[i]);
            				if(set.next()) {
            					fos.write(new String(set.getString("text")+";").getBytes());
            				}
            				break;
            			case 1070:
            				set = st.executeQuery("SELECT ad.text,ad.distance FROM useranswers ua " +
                			"JOIN answerdata ad ON ua.answer = ad.id " +
                			"WHERE ua.assesment = 11 " +
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
            			case 1062: case 1063:
            				set = st.executeQuery("SELECT ad.date FROM useranswers ua " +
                			"JOIN answerdata ad ON ua.answer = ad.id " +
                			"WHERE ua.assesment = 11 " +
                			"AND ua.loginname = '"+loginname+"' " +
                			"AND ad.question = "+answers[i]);
            				if(set.next()) {
            					fos.write(new String(set.getString("date")+";").getBytes());
            				}
            				break;
            			case 1090: case 1084: case 1069: case 1068: case 1082: case 1077: case 1076: case 1071:
            			case 1064: case 1061: case 1119: case 1120: case 1087: case 1086: case 1081: case 1079:
            				set = st.executeQuery("SELECT gm.text FROM useranswers ua " +
    						"JOIN answerdata ad ON ua.answer = ad.id " +
    						"JOIN answers a ON a.id = ad.answer " +
    						"JOIN generalmessages gm ON gm.labelkey = a.key " +
    						"WHERE ua.assesment = 11 " +
    						"AND gm.language = 'pt' " +
                			"AND ua.loginname = '"+loginname+"' " +
                			"AND ad.question = "+answers[i]);
            				if(set.next()) {
            					fos.write(new String(set.getString("text")+";").getBytes());
            				}
            				break;
            			case 1085:
            				set = st.executeQuery("SELECT gm.text FROM useranswers ua " +
    						"JOIN answerdata ad ON ua.answer = ad.id " +
    						"JOIN multioptions m ON ad.id = m.id "+
    						"JOIN answers a ON a.id = m.answer "+
    						"JOIN generalmessages gm ON gm.labelkey = a.key " +
    						"WHERE ua.assesment = 11 " +
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
				fos.write(new String("\n").getBytes());
            }
            fos.close();
            
            fout = new File("C:/ResultadosJJ_PSI.csv");
	        fos = new FileOutputStream(fout);
        	fos.write(new String("NOMBRE;APELLIDO;FECHA DE NACIMIENTO;EMAIL;OP_GROUP;PAIS;SUPERVISOR;ACTITUD;STRESS;\n").getBytes());
	        
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
            		"WHERE id IN (64,65,67) AND gm.language = 'es'");
            while(set.next()) {
            	String module = set.getString("id");
                fout = new File("C:/ResultadosJJ_"+set.getString("text")+".csv");
    	        fos = new FileOutputStream(fout);

    	        ResultSet set1 = st2.executeQuery("SELECT gm.text FROM questions q " +
    	        		"JOIN generalmessages gm ON gm.labelkey = q.key " +
    	        		"WHERE q.module = " +module+" "+
    	        		"AND gm.language = 'es' " +
    	        		"ORDER BY questionorder");
            	fos.write(new String("NOMBRE;APELLIDO;FECHA DE NACIMIENTO;EMAIL;OP_GROUP;PAIS;SUPERVISOR;").getBytes());
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
    	        			"WHERE ua.assesment = 11 " +
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

    private static String getCountry(int extraData,int country) {
    	String op_group = "";
    	String countryName = "";
    	String supervisor = "";
		switch(extraData) {
        case 4460:
    		op_group = "Vistakon";
            supervisor = "Daniele Santos";
            switch (country) {
            	case 31:
            		countryName = "Uruguay";
            		break;
            	case 33:
            		countryName = "Argentina";
            		break;
            	case 34:
            		countryName = "Paraguay";
            		break;
            	case 54:
            		countryName = "Chile";
            		break;
                case 32:
            		countryName = "Brasil";
            		break;
                case 57:
                	countryName = "Rep. Dominicana";
            		break;
                case 66:
                	countryName = "Puerto Rico";
            		break;
                case 69:
                	countryName = "Trinidad & Tobago";
            		break;
                case 56:
                	countryName = "Costa Rica";
            		break;
                case 67:
                	countryName = "El Salvador";
            		break;
                case 59:
                	countryName = "Guatemala";
            		break;
                case 61:
                	countryName = "Honduras";
            		break;
                case 63:
                	countryName = "Panamá";
            		break;
                case 37:
                	countryName = "Ecuador";
            		break;
                case 55:
            		countryName = "Colombia";
            		break;
                case 42:
            		countryName = "México";
            		break;
                case 64:
            		countryName = "Perú";
            		break;
                case 39:
            		countryName = "Venezuela";
            		break;
                case 85:
                	countryName = "Jamaica";
                	break;
                case 62:
                	countryName = "Nicaragua";
                	break;
            }
            break;
        case 4461:
    		op_group = "Consumo";
            switch (country) {
            	case 31:
            		countryName = "Uruguay";
            		supervisor = "Santiago Romero";
            		break;
            	case 33:
            		countryName = "Argentina";
            		supervisor = "Santiago Romero";
            		break;
            	case 34:
            		supervisor = "Santiago Romero";
            		countryName = "Paraguay";
            		break;
            	case 54:
            		countryName = "Chile";
            		supervisor = "Santiago Romero";
            		break;
                case 32:
            		countryName = "Brasil";
                	supervisor = "Jackson Tota";
            		break;
                case 57:
                	countryName = "Rep. Dominicana";
                	supervisor = "Steven Rolon";
            		break;
                case 66:
                	countryName = "Puerto Rico";
                	supervisor = "Steven Rolon";
            		break;
                case 69:
                	countryName = "Trinidad & Tobago";
                	supervisor = "Steven Rolon";
            		break;
                case 56:
                	countryName = "Costa Rica";
                	supervisor = "Julio Altafulla";
            		break;
                case 67:
                	countryName = "El Salvador";
                	supervisor = "Julio Altafulla";
            		break;
                case 59:
                	countryName = "Guatemala";
                	supervisor = "Julio Altafulla";
            		break;
                case 61:
                	countryName = "Honduras";
                	supervisor = "Julio Altafulla";
            		break;
                case 63:
                	countryName = "Panamá";
                	supervisor = "Julio Altafulla";
            		break;
                case 37:
                	countryName = "Ecuador";
                	supervisor = "Jaime Cruz";
            		break;
                case 55:
            		countryName = "Colombia";
                	supervisor = "Jaime Cruz";
            		break;
                case 42:
            		countryName = "México";
                	supervisor = "Arthur Owen";
            		break;
                case 64:
            		countryName = "Perú";
                	supervisor = "Luis Cardo";
            		break;
                case 39:
            		countryName = "Venezuela";
                	supervisor = "Mario Lozano";
            		break;
            }
    		break;
        case 4462:
    		op_group = "MD&D";
            switch (country) {
                case 32:
            		countryName = "Brasil";
                	supervisor = "Eduardo Fernandes";
            		break;
                case 57:
                	countryName = "Rep. Dominicana";
                	supervisor = "Francisco Martinez";
            		break;
                case 66:
                	countryName = "Puerto Rico";
                	supervisor = "Francisco Martinez";
            		break;
                case 85:
                	countryName = "Jamaica";
                	supervisor = "Francisco Martinez";
            		break;
                case 42:
            		countryName = "México";
                	supervisor = "Maria Benitez";
            		break;
                case 37:
                	countryName = "Ecuador";
                	supervisor = "Jose Yung";
            		break;
                case 39:
            		countryName = "Venezuela";
                	supervisor = "Jose Yung";
            		break;
                case 55:
            		countryName = "Colombia";
                	supervisor = "Jose Yung";
            		break;
                case 64:
            		countryName = "Perú";
                	supervisor = "Jose Yung";
            		break;
                case 31:
            		countryName = "Uruguay";
                	supervisor = "Alberto Castro";
            		break;
                case 33:
            		countryName = "Argentina";
                	supervisor = "Alberto Castro";
            		break;
                case 54:
            		countryName = "Chile";
                	supervisor = "Alberto Castro";
            		break;
                case 63:
                	countryName = "Panamá";
                	supervisor = "---";
            		break;
                case 59:
                	countryName = "Guatemala";
                	supervisor = "---";
            		break;
            }
    		break;
        case 4463:
    		op_group = "Janssen-Cilag";
            switch (country) {
            	case 31:
            		countryName = "Uruguay";
            		supervisor = "Guillermo Marrese";
            		break;
            	case 54:
            		countryName = "Chile";
            		supervisor = "Guillermo Marrese";
            		break;
            	case 64:
            		countryName = "Perú";
            		supervisor = "Guillermo Marrese";
            		break;
                case 33:
            		countryName = "Argentina";
                	supervisor = "Silvia Calello";
            		break;
                case 32:
            		countryName = "Brasil";
                	supervisor = "Antonio Manzano";
            		break;
                case 55:
            		countryName = "Colombia";
                	supervisor = "Greicy Aguilera";
            		break;
                case 39:
            		countryName = "Venezuela";
                	supervisor = "Luisa Ana Frabotta";
            		break;
                case 42:
            		countryName = "México";
                	supervisor = "Luis Cisneros";
            		break;
                case 57:
                	countryName = "Rep. Dominicana";
                	supervisor = "Luis Cisneros";
            		break;
                case 66:
                	countryName = "Puerto Rico";
                	supervisor = "Luis Cisneros";
            		break;
                case 69:
                	countryName = "Trinidad & Tobago";
                	supervisor = "Luis Cisneros";
            		break;
                case 56:
                	countryName = "Costa Rica";
                	supervisor = "Luis Cisneros";
            		break;
                case 67:
                	countryName = "El Salvador";
                	supervisor = "Luis Cisneros";
            		break;
                case 59:
                	countryName = "Guatemala";
                	supervisor = "Luis Cisneros";
            		break;
                case 61:
                	countryName = "Honduras";
                	supervisor = "Luis Cisneros";
            		break;
                case 62:
                	countryName = "Nicaragua";
                	supervisor = "Luis Cisneros";
            		break;
                case 63:
                	countryName = "Panamá";
                	supervisor = "Luis Cisneros";
            		break;
                case 85:
                	countryName = "Jamaica";
                	supervisor = "Luis Cisneros";
            		break;
                case 170:
                	countryName = "Belice";
                	supervisor = "Luis Cisneros";
            		break;
            }
            break;
		}
		return	op_group+";"+countryName+";"+supervisor+";";

    }
}
