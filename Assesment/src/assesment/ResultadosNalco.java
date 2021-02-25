package assesment;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

import assesment.communication.question.AnswerData;

public class ResultadosNalco {
	

    public static void main(String[] args) {

    	try {
    		
            Class.forName("org.postgresql.Driver");
            
            
            Connection conn1 = DriverManager.getConnection("jdbc:postgresql://67.192.60.149:5432/assesment","postgres","pr0v1s0r1A");
                       
            // Connection conn1 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/assesment","postgres","pr0v1s0r1A");
            
            
            Statement st = conn1.createStatement();
            Statement st2 = conn1.createStatement();
            Statement st3 = conn1.createStatement();
            
            Integer assesmentId = 56;
            Integer moduleDatosPersonales = 265;
            
            File fout = new File("C:/ResultadosNalco_DatosPersonales.csv");
            System.out.println("C:/ResultadosNalco_DatosPersonales.csv");
	        FileOutputStream fos = new FileOutputStream(fout);

	        Hashtable<String,String> names = new Hashtable<String,String>();
            Collection<String> doneUsers = new LinkedList<String>();
            ResultSet set = st.executeQuery("SELECT ua.loginname,u.firstname,u.lastname,u.email," +
            		" u.sex AS state," +
            		" u.country AS operation," +
            		" u.nationality AS division," +
            		" u.licenseexpiry AS expiry," +
            		" u.vehicle AS phone," +
            		" u.location AS subdivision" +
            		" FROM useranswers ua " +
            		" JOIN userassesments uas ON (ua.loginname = uas.loginname AND ua.assesment = uas.assesment) " +
            		" JOIN answerdata ad ON ua.answer = ad.id " +
            		" JOIN questions q ON ad.question = q.id " +
            		" JOIN users u ON u.loginname = ua.loginname " +
            		" WHERE ua.assesment = " + assesmentId + " " +
            		// " AND uas.psiid IS NOT NULL" + // sin sicologico
            		" GROUP BY ua.loginname,u.firstname,u.lastname,u.email,u.sex,u.country," +
            		" u.nationality,u.licenseexpiry,u.vehicle,u.location " +
            		" HAVING count(*) = 88"); // cantidad total de preguntas del assesment
            
            while(set.next()) {
            	String loginname = set.getString("loginname"); 
            	doneUsers.add(loginname);
            	String data = set.getString("firstname")+";"+set.getString("lastname")+";"+set.getString("email")+";"; //+set.getString("phone")+";"+set.getDate("expiry")+";";
            	//data += getState(set.getInt("state"))+";"+getOperation(set.getInt("operation"))+";"+getDivision(set.getInt("division"))+";"+getSubdivision(set.getInt("subdivision"))+";";
            	names.put(loginname,data);
            }
            
            
            
	        int[] answers = new int[28]; // cantidad de preguntas personales
	        set = st.executeQuery("select id,gm.text from questions q, generalmessages gm where q.key = gm.labelkey and " +
	        		"module = "+moduleDatosPersonales+" and gm.language = 'es' order by questionorder");
	        int i = 0; 
        	// fos.write(new String("NOMBRE;APELLIDO;EMAIL;TELEFONO;VENCIMIENTO DE CNH;ESTADO;OPRACION;DIVISION;SUBDIVISION;").getBytes());
//	        fos.write(new String("País;Ciudad;Nombre,Apellido,Segundo Apellido;"+
//	        		"Fecha de Nacimiento;Sexo;Tipo de Vehículo Conduce; Expedición de la Licencia;Vencimiento de la Licecnia").getBytes());
	        while(set.next()) {
	        	fos.write(new String(set.getString("text")+";").getBytes());
	        	answers[i] = set.getInt("id");
	        	i++;
	        }
        	fos.write(new String("\n").getBytes());
	        
	        Iterator<String> it = doneUsers.iterator();
            while(it.hasNext()) {
            	String loginname = (String)it.next();
	        	// fos.write(String.valueOf(names.get(loginname)).getBytes());
            	for(i = 0; i < answers.length; i++) {
            		switch(answers[i]) {
            		
            			case 4889:
            				set = st.executeQuery("SELECT ad.text FROM useranswers ua " +
                        			"JOIN answerdata ad ON ua.answer = ad.id " +
                        			"WHERE ua.assesment = " + assesmentId + " " +
                        			"AND ua.loginname = '"+loginname+"' " +
                        			"AND ad.question = "+answers[i]);
                    		if(set.next()) {
                    			String pais = set.getString("text");
                    			if (pais == null || pais=="null") pais ="Colombia";
                    			fos.write( (pais+";").getBytes());
                    		}else{
                    			String pais = "Colombia";
                    			fos.write( (pais+";").getBytes());
                    		}
                    		break;
                    				
            			case 4890:case 4891:case 4892:
            			case 4893:case 4917:case 4918:
            			case 4902:
            			
            				// Texto Simple
            				set = st.executeQuery("SELECT ad.text FROM useranswers ua " +
                			"JOIN answerdata ad ON ua.answer = ad.id " +
                			"WHERE ua.assesment = " + assesmentId + " " +
                			"AND ua.loginname = '"+loginname+"' " +
                			"AND ad.question = "+answers[i]);
            				if(set.next()) {
            					fos.write(new String(set.getString("text")+";").getBytes());
            				}
            				break;
            			
//            			case 5265:
//            				set = st.executeQuery("SELECT ad.text,ad.distance FROM useranswers ua " +
//                			"JOIN answerdata ad ON ua.answer = ad.id " +
//                			"WHERE ua.assesment = " + assesmentId + " " +
//                			"AND ua.loginname = '"+loginname+"' " +
//                			"AND ad.question = "+answers[i]);
//            				if(set.next()) {
//            					String s = set.getString("text");
//            					if(set.getInt("distance") == 0) {
//            						s += " kms;";
//            					}else {
//            						s += " millas;";
//            					}
//            					fos.write(s.getBytes());
//            				}
//            				break;
            				
            			case 4894:case 4898: case 4899:
            				// Fechas
            				set = st.executeQuery("SELECT ad.date FROM useranswers ua " +
                			"JOIN answerdata ad ON ua.answer = ad.id " +
                			"WHERE ua.assesment = " + assesmentId + " "+
                			"AND ua.loginname = '"+loginname+"' " +
                			"AND ad.question = "+answers[i]);
            				
            				if(set.next()) {
            					String fecha = (set.getString("date") == null)?"":set.getString("date");
            					fos.write(   ( fecha+";").getBytes());
            				}
            				break;
            			
            				
            			case 4897:case 4895:case 4900:case 4901:case 4903:
            			case 4904:case 4905:case 4906:case 4907:
            			case 4908:case 4909:case 4910:case 4911:
            			case 4912:case 4916:case 4896:
            				// Texto con traducciones
            				set = st.executeQuery("SELECT gm.text FROM useranswers ua " +
    						"JOIN answerdata ad ON ua.answer = ad.id " +
    						"JOIN answers a ON a.id = ad.answer " +
    						"JOIN generalmessages gm ON gm.labelkey = a.key " +
    						"WHERE ua.assesment = " + assesmentId + " "+
    						"AND gm.language = 'es' " +
                			"AND ua.loginname = '"+loginname+"' " +
                			"AND ad.question = "+answers[i]);
            				if(set.next()) {
            					fos.write(new String(set.getString("text")+";").getBytes());
            				}
            				break;
            			
            			case 4913:
            				// Texto con traducciones y multipleopcion
            				set = st.executeQuery("SELECT gm.text FROM useranswers ua " +
    						"JOIN answerdata ad ON ua.answer = ad.id " +
    						"JOIN multioptions m ON ad.id = m.id "+
    						"JOIN answers a ON a.id = m.answer "+
    						"JOIN generalmessages gm ON gm.labelkey = a.key " +
    						"WHERE ua.assesment = " + assesmentId + " "+
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
            
            
            // Modulo Psicologico
            // Nalco no lleva modulo psicologico
            moduloPsicologico();
            

            
            
            // Modulos:
            set = st.executeQuery("SELECT m.id,gm.text FROM modules m " +
            		"JOIN generalmessages gm ON gm.labelkey = m.key " +
            		// "WHERE id IN (288,289,291) AND gm.language = 'es'");
            		"WHERE id IN (263,262,261) AND gm.language = 'es'");
            
            while(set.next()) {
            	String module = set.getString("id");
                fout = new File("C:/ResultadosNalco_"+set.getString("text")+".csv");
                System.out.println("C:/ResultadosNalco_"+set.getString("text")+".csv");
    	        fos = new FileOutputStream(fout);

    	        ResultSet set1 = st2.executeQuery("SELECT gm.text FROM questions q " +
    	        		"JOIN generalmessages gm ON gm.labelkey = q.key " +
    	        		"WHERE q.module = " +module+" "+
    	        		"AND gm.language = 'es' " +
    	        		"ORDER BY questionorder");
            	
    	        // fos.write(new String("NOMBRE;APELLIDO;EMAIL;TELEFONO;VENCIMIENTO DE CNH;ESTADO;OPRACION;DIVISION;SUBDIVISION;").getBytes());
    	        fos.write(new String("NOMBRE;APELLIDO;EMAIL;").getBytes());
    	        // preguntas:
    	        while(set1.next()) {
    	        	fos.write(new String(set1.getString("text")+";").getBytes());
    	        }
	        	fos.write(new String("\n").getBytes());
    	        
    	        it = doneUsers.iterator();
    	        while(it.hasNext()) {
    	        	String loginname = (String)it.next();
    	        	fos.write(String.valueOf(names.get(loginname)).getBytes());
    	        	
    	        	ResultSet set2 = st2.executeQuery("SELECT q.id,a.type FROM useranswers ua " +
    	        			"JOIN answerdata ad ON ua.answer = ad.id " +
    	        			"JOIN questions q ON q.id = ad.question " +
    	        			"JOIN answers a ON a.id = ad.answer " +
    	        			"WHERE ua.assesment = " + assesmentId + " "+
    	        			"AND ua.loginname = '"+loginname+"'" +
    	        			"AND q.module = " +module+" " +
    	        			"ORDER BY q.questionorder");
    	        	
    	        	
    	        	while(set2.next()) {
    	        		int question = set2.getInt("id"); 
    	        		
    	        		if(question == 4839 || 
    	        			question == 4834
    	        			) {
            				ResultSet set3 = st3.executeQuery("SELECT gm.text FROM useranswers ua " +
            						"JOIN answerdata ad ON ua.answer = ad.id " +
            						"JOIN answers a ON a.id = ad.answer " +
            						"JOIN generalmessages gm ON gm.labelkey = a.key " +
            						"WHERE ua.assesment = " + assesmentId + " "+
            						"AND gm.language = 'es' " +
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

    
    public static void moduloPsicologico(){
//      fout = new File("C:/ResultadosNalco_PSI.csv");
//      System.out.println("C:/ResultadosNalco_PSI.csv");
//      fos = new FileOutputStream(fout);
//  	// fos.write(new String("NOMBRE;APELLIDO;EMAIL;TELEFONO;VENCIMIENTO DE CNH;ESTADO;OPRACION;DIVISION;SUBDIVISION;ACTITUD;STRESS;\n").getBytes());
//      fos.write(new String("NOMBRE;APELLIDO;EMAIL;TELEFONO;VENCIMIENTO DE CNH;ESTADO;OPRACION;DIVISION;SUBDIVISION;ACTITUD;STRESS;\n").getBytes());
//      
//      it = doneUsers.iterator();
//      while(it.hasNext()) {
//      	String loginname = (String)it.next();
//      	fos.write(String.valueOf(names.get(loginname)).getBytes());
//      	set = st.executeQuery("SELECT psiresult1+psiresult2+psiresult3 AS actitud, " +
//      			"psiresult4+psiresult5+psiresult6 AS stress " +
//      			"FROM userassesments " +
//          		"WHERE loginname = '"+loginname+"' ");
//      	if(set.next()) {
//      		double actitud = set.getDouble("actitud") / 3.0;
//      		if(actitud < 3) {
//      			fos.write(new String("Verde;").getBytes());
//      		}else if(actitud < 4) {
//      			fos.write(new String("Amarillo;").getBytes());
//      		}else {
//      			fos.write(new String("Rojo;").getBytes());
//      		}
//      		double stress = set.getDouble("stress") / 3.0;
//      		if(stress < 3) {
//      			fos.write(new String("Verde;\n").getBytes());
//      		}else if(stress < 4) {
//      			fos.write(new String("Amarillo;\n").getBytes());
//      		}else {
//      			fos.write(new String("Rojo;\n").getBytes());
//      		}
//      	}
//      }
    }
}
