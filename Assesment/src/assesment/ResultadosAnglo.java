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

public class ResultadosAnglo {
	

    public static void main(String[] args) {

    	try {
    		
            Class.forName("org.postgresql.Driver");
            Connection conn1 = DriverManager.getConnection("jdbc:postgresql://67.192.60.149:5432/assesment","postgres","pr0v1s0r1A");
            Statement st = conn1.createStatement();
            Statement st2 = conn1.createStatement();
            Statement st3 = conn1.createStatement();
            
            File fout = new File("C:/ResultadosAnglo_DatosPersonales.csv");
            System.out.println("C:/ResultadosAnglo_DatosPersonales.csv");
	        FileOutputStream fos = new FileOutputStream(fout);

	        Hashtable names = new Hashtable();
            Collection doneUsers = new LinkedList();
            ResultSet set = st.executeQuery("SELECT ua.loginname,u.firstname,u.lastname,u.email,u.sex AS state," +
            		" u.country AS operation,u.nationality AS division,u.licenseexpiry AS expiry,u.vehicle AS phone,u.location AS subdivision" +
            		" FROM useranswers ua " +
            		" JOIN userassesments uas ON (ua.loginname = uas.loginname AND ua.assesment = uas.assesment) " +
            		" JOIN answerdata ad ON ua.answer = ad.id JOIN questions q ON ad.question = q.id " +
            		" JOIN users u ON u.loginname = ua.loginname " +
            		" WHERE ua.assesment = 60 " +
            		" AND uas.psiid IS NOT NULL" +
            		" GROUP BY ua.loginname,u.firstname,u.lastname,u.email,u.sex,u.country,u.nationality,u.licenseexpiry,u.vehicle,u.location " +
            		" HAVING count(*) = 77");
            while(set.next()) {
            	String loginname = set.getString("loginname"); 
            	doneUsers.add(loginname);
            	String data = set.getString("firstname")+";"+set.getString("lastname")+";"+set.getString("email")+";"+set.getString("phone")+";"+set.getDate("expiry")+";"+getState(set.getInt("state"));
            	data += ";"+getOperation(set.getInt("operation"))+";"+getDivision(set.getInt("division"))+";"+getSubdivision(set.getInt("subdivision"))+";";
            	names.put(loginname,data);
            }
            
	        int[] answers = new int[17];
	        set = st.executeQuery("select id,gm.text from questions q, generalmessages gm where q.key = gm.labelkey and module = 287 and gm.language = 'pt' order by questionorder");
	        int i = 0; 
        	fos.write(new String("NOMBRE;APELLIDO;EMAIL;TELEFONO;VENCIMIENTO DE CNH;ESTADO;OPRACION;DIVISION;SUBDIVISION;").getBytes());
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
            			case 5259:
            				set = st.executeQuery("SELECT ad.text FROM useranswers ua " +
                			"JOIN answerdata ad ON ua.answer = ad.id " +
                			"WHERE ua.assesment = 60 " +
                			"AND ua.loginname = '"+loginname+"' " +
                			"AND ad.question = "+answers[i]);
            				if(set.next()) {
            					fos.write(new String(set.getString("text")+";").getBytes());
            				}
            				break;
            			case 5265:
            				set = st.executeQuery("SELECT ad.text,ad.distance FROM useranswers ua " +
                			"JOIN answerdata ad ON ua.answer = ad.id " +
                			"WHERE ua.assesment = 60 " +
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
            			case 5260:
            				set = st.executeQuery("SELECT ad.date FROM useranswers ua " +
                			"JOIN answerdata ad ON ua.answer = ad.id " +
                			"WHERE ua.assesment = 60 " +
                			"AND ua.loginname = '"+loginname+"' " +
                			"AND ad.question = "+answers[i]);
            				if(set.next()) {
            					fos.write(new String(set.getString("date")+";").getBytes());
            				}
            				break;
            			case 5317:
            			case 5318:
            			case 5320:
            			case 5321:
            			case 5322:
            			case 5266:
            			case 5268:
            			case 5269:
            			case 5316:
            			case 5270:
            			case 5261:
            			case 5271:
            			case 5264:
            				set = st.executeQuery("SELECT gm.text FROM useranswers ua " +
    						"JOIN answerdata ad ON ua.answer = ad.id " +
    						"JOIN answers a ON a.id = ad.answer " +
    						"JOIN generalmessages gm ON gm.labelkey = a.key " +
    						"WHERE ua.assesment = 60 " +
    						"AND gm.language = 'pt' " +
                			"AND ua.loginname = '"+loginname+"' " +
                			"AND ad.question = "+answers[i]);
            				if(set.next()) {
            					fos.write(new String(set.getString("text")+";").getBytes());
            				}
            				break;
            			case 5319:
            				set = st.executeQuery("SELECT gm.text FROM useranswers ua " +
    						"JOIN answerdata ad ON ua.answer = ad.id " +
    						"JOIN multioptions m ON ad.id = m.id "+
    						"JOIN answers a ON a.id = m.answer "+
    						"JOIN generalmessages gm ON gm.labelkey = a.key " +
    						"WHERE ua.assesment = 60 " +
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
            
            fout = new File("C:/ResultadosAnglo_PSI.csv");
            System.out.println("C:/ResultadosAnglo_PSI.csv");
	        fos = new FileOutputStream(fout);
        	fos.write(new String("NOMBRE;APELLIDO;EMAIL;TELEFONO;VENCIMIENTO DE CNH;ESTADO;OPRACION;DIVISION;SUBDIVISION;ACTITUD;STRESS;\n").getBytes());
	        
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
            		"WHERE id IN (288,289,291) AND gm.language = 'es'");
            while(set.next()) {
            	String module = set.getString("id");
                fout = new File("C:/ResultadosAnglo_"+set.getString("text")+".csv");
                System.out.println("C:/ResultadosAnglo_"+set.getString("text")+".csv");
    	        fos = new FileOutputStream(fout);

    	        ResultSet set1 = st2.executeQuery("SELECT gm.text FROM questions q " +
    	        		"JOIN generalmessages gm ON gm.labelkey = q.key " +
    	        		"WHERE q.module = " +module+" "+
    	        		"AND gm.language = 'es' " +
    	        		"ORDER BY questionorder");
            	fos.write(new String("NOMBRE;APELLIDO;EMAIL;TELEFONO;VENCIMIENTO DE CNH;ESTADO;OPRACION;DIVISION;SUBDIVISION;").getBytes());
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
    	        			"WHERE ua.assesment = 60 " +
    	        			"AND ua.loginname = '"+loginname+"'" +
    	        			"AND q.module = " +module+" " +
    	        			"ORDER BY q.questionorder");
    	        	fos.write(String.valueOf(names.get(loginname)).getBytes());
    	        	while(set2.next()) {
    	        		int question = set2.getInt("id"); 
    	        		if(question == 5329 || question == 5333) {
            				ResultSet set3 = st3.executeQuery("SELECT gm.text FROM useranswers ua " +
            						"JOIN answerdata ad ON ua.answer = ad.id " +
            						"JOIN answers a ON a.id = ad.answer " +
            						"JOIN generalmessages gm ON gm.labelkey = a.key " +
            						"WHERE ua.assesment = 60 " +
            						"AND gm.language = 'pt' " +
                        			"AND ua.loginname = '"+loginname+"' " +
                        			"AND ad.question = "+question);
                    		if(set3.next()) {
                    			fos.write(new String(set3.getString("text")+";").getBytes());
                    		}

    	        		}else {
	    	        		if(set2.getInt("type") == AnswerData.CORRECT) {
	    	        			fos.write(new String("CORRECTA;").getBytes());
	    	        		}else {
	    	        			fos.write(new String("INCORRECTA;").getBytes());
	    	        		}
    	        		}
    	        	}
    	        	fos.write(new String("\n").getBytes());
    	        }
            }
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	System.out.println("FIN");
	}

    private static String getState(int state) {
        switch(state) {
        	case 1: return "Sao Paulo";
        	case 2: return "Minas Gerais";
        	case 3: return "Rio Grande do Sul";
        	case 4: return "Parana";
        	case 5: return "Rio de Janeiro";
        	case 6: return "Goiás";
        	case 7: return "Santa Catarina";
        	case 8: return "Bahia";
        	case 9: return "Pará";
        	case 10: return "Distrito Federal";
        	case 11: return "Mato Grosso";
        	case 12: return "Maranhao";
        	case 13: return "Pernambuco";
        	case 14: return "Ceará";
        	case 15: return "Mato Grosso do Sul";
        	case 16: return "Espírito Santo";
        	case 17: return "Amazonas";
        	case 18: return "Paraíba";
        	case 19: return "Piauí";
        	case 20: return "Rio Grande do Norte";
        	case 21: return "Rondonia";
        	case 22: return "Alagoas";
        	case 23: return "Sergipe";
        	case 24: return "Tocantins";
        	case 25: return "Amapá";
        	case 26: return "Acre";
        	case 27: return "Roraima";
        	default: return "";
        }
    }

    private static String getOperation(int operation) {
        switch(operation) {
        	case 1: return "AFB MINAS RIO - IMPLANTACAO";
        	case 2: return "AFB MINAS RIO - OPERACAO";
        	default: return "";
        }
    }

    private static String getDivision(int division) {
        switch(division) {
        	case 1: return "BENEFICIAMENTO";
        	case 2: return "MINERODUTO";
        	case 3: return "ADM";
        	case 4: return "GEOLOGIA";
        	case 5: return "GESTAO FUNDIARIA";
        	case 6: return "GPO";
        	case 7: return "MEIO AMBIENTE";
        	case 8: return "RCC";
        	default: return "";
        }
    }

    private static String getSubdivision(int subdivision) {
        switch(subdivision) {
        	case 1: return "ARG";
        	case 2: return "CRA";
        	case 3: return "K-WAY";
        	case 4: return "STA. BARBARA";
        	case 5: return "CCCC";
        	case 6: return "INTEGRAL";
        	case 7: return "LOGOS";
        	case 8: return "ADMINISTRACAO";
        	case 9: return "COMUNICACAO";
        	case 10: return "EXPANSAO";
        	case 11: return "JURIDICO";
        	case 12: return "RH";
        	case 13: return "SEG EMPRESARIAL";
        	case 14: return "SSO";
        	case 15: return "SUPRIMENTOS";
        	case 16: return "TI";
        	case 17: return "SERVITEC";
        	case 18: return "AVALICON";
        	case 19: return "CARVALHO PEREIRA";
        	case 20: return "BENEFICIAMENTO";
        	case 21: return "CONFIABILIDADE";
        	case 22: return "MANUTENCAO";
        	case 23: return "MINA";
        	case 24: return "MINERODUTO";
        	case 25: return "EPA";
        	case 26: return "ERG";
        	case 27: return "GEO NATURA";
        	case 28: return "NOVA LUZ";
        	case 29: return "SCIENTIA";
        	case 30: return "URB TOPO";
        	case 31: return "RCC";
        	default: return "";
        }
    }
}
