package assesment;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;

import assesment.communication.question.AnswerData;
import assesment.communication.question.QuestionData;
import assesment.communication.util.CountryConstants;

public class ResultadosPepsicoCS {
	

    public static void main(String[] args) {

    	try {
    		
            Class.forName("org.postgresql.Driver");
            
            
            Connection conn1 = DriverManager.getConnection("jdbc:postgresql://67.192.60.149:5432/assesment","postgres","pr0v1s0r1A");
                       
            //Connection conn1 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/assesment","postgres","pr0v1s0r1A");
            
            
            Statement st = conn1.createStatement();
            Statement st2 = conn1.createStatement();
            Statement st3 = conn1.createStatement();
            
            Integer assesmentId = 82;
            String assessmentName = "";
            ResultSet setName = st.executeQuery("SELECT name FROM assesments WHERE id = "+assesmentId);
            if(setName.next()) {
            	assessmentName = setName.getString("name");
            }
            
            File fout = new File("C:/Resultados_PepsicoCepaSystem.csv");
	        FileOutputStream fos = new FileOutputStream(fout);
            
            String header = "NOMBRE;APELLIDO;SUCURSAL;STATUS;ULTIMO ACCESO;";
            ResultSet setHeader = st.executeQuery("SELECT gm.text " +
            		"FROM modules m " +
            		"JOIN questions q ON q.module = m.id " +
            		"JOIN generalmessages gm ON gm.labelkey = q.key " +
            		"WHERE assesment = "+assesmentId+
            		" AND gm.language = 'es'" +
            		"ORDER BY moduleorder,questionorder");
            while(setHeader.next()) {
            	header += setHeader.getString("text")+";";
            }
            header += "\n";

            fos.write(header.getBytes());

            int[][] modules = new int[4][2];
            ResultSet setModules = st.executeQuery("select m.id,count(*) " +
            		"from modules m " +
            		"join questions q on q.module = m.id " +
            		"WHERE assesment = "+assesmentId+
            		" GROUP BY m.id,m.moduleorder " +
            		"ORDER BY moduleorder");
            int index = 0;
            int total = 0;
            while(setModules.next()) {
            	modules[index][0] = setModules.getInt(1);
            	modules[index][1] = setModules.getInt(2);
            	total += modules[index][1];
            	index++;
            }
            
            int[][] personal = new int[modules[0][1]][2];
            ResultSet set = st.executeQuery("SELECT q.id,q.type FROM questions q, generalmessages gm WHERE q.key = gm.labelkey AND " +
	        		"module = "+modules[0][0]+" AND gm.language = 'es' ORDER BY questionorder");
            index = 0;
        	while(set.next()) {
	        	personal[index][0] = set.getInt("id");
	        	personal[index][1] = set.getInt("type");
	        	index++;
	        }

	        
            ResultSet setUsers = st.executeQuery("SELECT DISTINCT u.loginname,u.firstname,u.lastname,u.extradata,ua.enddate FROM users u" +
            		" JOIN userassesments ua ON u.loginname = ua.loginname " +
            		" WHERE ua.assesment = " + assesmentId+" ORDER BY u.firstname,u.lastname"); // cantidad total de usuarios
            
        	CountryConstants cc = new CountryConstants();

        	while(setUsers.next()) {
            	String loginname = setUsers.getString("loginname"); 
            	Date enddate = setUsers.getDate("enddate");

            	String line = "";
            	line += setUsers.getString("firstname")+";"+setUsers.getString("lastname")+";"+setUsers.getString("extradata")+";";
            
            	ResultSet setStatus = st2.executeQuery("select count(*) from useranswers where assesment = "+assesmentId+" and loginname = '"+loginname+"'");
            	setStatus.next();
            	int answers = setStatus.getInt(1);
            	
            	if(answers == 0) {
            		line += "No comenzó;----;";
            		for(int i = 0; i < total; i++) {
            			line += "----;";
            		}
            		line += "\n";
            	}else {
            		Calendar c = Calendar.getInstance();
            		if(enddate == null) {
            			ResultSet setAccess = st2.executeQuery("select max(logindate) from userslogins where loginname = '"+loginname+"'");
            			setAccess.next();
            			c.setTimeInMillis(setAccess.getLong(1));
            		}else {
            			c.setTime(enddate);            			
            		}
            		if(answers < total) {
            			line += "Incompleto;";
            		}else {
            			line += "Completo;";
            		}
            		line += c.get(Calendar.DATE)+"/"+String.valueOf(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.YEAR)+";";
            		for(int i = 0; i < personal.length; i++) {
            			switch(personal[i][1]) {
            			
            				case QuestionData.TEXT: case QuestionData.EMAIL:// Texto Simple            				
	            				set = st2.executeQuery("SELECT ad.text FROM useranswers ua " +
	                			"JOIN answerdata ad ON ua.answer = ad.id " +
	                			"WHERE ua.assesment = " + assesmentId + " " +
	                			"AND ua.loginname = '"+loginname+"' " +
	                			"AND ad.question = "+personal[i][0]);
	            				if(set.next()) {
	            					line += new String(set.getString("text")+";");
	            				}
	            				break;
            			
            				case QuestionData.KILOMETERS:
	            				set = st2.executeQuery("SELECT ad.text,ad.distance FROM useranswers ua " +
	                			"JOIN answerdata ad ON ua.answer = ad.id " +
	                			"WHERE ua.assesment = " + assesmentId + " " +
	                			"AND ua.loginname = '"+loginname+"' " +
	                			"AND ad.question = "+personal[i][0]);
	            				if(set.next()) {
	            					line += set.getString("text");
	            					if(set.getInt("distance") == 0) {
	            						line += " kms;";
	            					}else {
	            						line += " millas;";
	            					}
	            				}
	            				break;
            				
            				case QuestionData.DATE: case QuestionData.BIRTHDATE: case QuestionData.OPTIONAL_DATE:
            				// Fechas
            					set = st2.executeQuery("SELECT ad.date FROM useranswers ua " +
            						"JOIN answerdata ad ON ua.answer = ad.id " +
            						"WHERE ua.assesment = " + assesmentId + " "+
            						"AND ua.loginname = '"+loginname+"' " +
            						"AND ad.question = "+personal[i][0]);
            				
            					if(set.next()) {
            						String fecha = (set.getString("date") == null)?"":set.getString("date");
            						line += fecha+";";
            					}
            					break;
            				
            				case QuestionData.EXCLUDED_OPTIONS: case QuestionData.LIST:
	            				// Texto con traducciones
	            				set = st2.executeQuery("SELECT gm.text FROM useranswers ua " +
	    						"JOIN answerdata ad ON ua.answer = ad.id " +
	    						"JOIN answers a ON a.id = ad.answer " +
	    						"JOIN generalmessages gm ON gm.labelkey = a.key " +
	    						"WHERE ua.assesment = " + assesmentId + " "+
	    						"AND gm.language = 'es' " +
	                			"AND ua.loginname = '"+loginname+"' " +
	                			"AND ad.question = "+personal[i][0]);
	            				if(set.next()) {
	            					line += set.getString("text")+";";
	            				}
	            				break;
            			
	            			case QuestionData.INCLUDED_OPTIONS:
	            				// Texto con traducciones y multipleopcion
	            				set = st2.executeQuery("SELECT gm.text FROM useranswers ua " +
	    						"JOIN answerdata ad ON ua.answer = ad.id " +
	    						"JOIN multioptions m ON ad.id = m.id "+
	    						"JOIN answers a ON a.id = m.answer "+
	    						"JOIN generalmessages gm ON gm.labelkey = a.key " +
	    						"WHERE ua.assesment = " + assesmentId + " "+
	    						"AND gm.language = 'es' " +
	                			"AND ua.loginname = '"+loginname+"' " +
	                			"AND ad.question = "+personal[i][0]);
	            				String s = "";
	            				while(set.next()) {
	            					s += set.getString("text")+"-";
	            				}
	            				if(s.length() > 2) {
	            					s = s.substring(0, s.length()-2)+";";
	            				}else {
	            					s = ";";
	            				}
	        					line += s;
	            				break;
	            			case QuestionData.COUNTRY:
	            				set = st2.executeQuery("SELECT ad.country FROM useranswers ua " +
	    						"JOIN answerdata ad ON ua.answer = ad.id " +
	    						"WHERE ua.assesment = " + assesmentId + " "+
	                			"AND ua.loginname = '"+loginname+"' " +
	                			"AND ad.question = "+personal[i][0]);
	            				if(set.next()) {
	            					ResultSet set2 = st2.executeQuery("SELECT text FROM generalmessages WHERE language = 'es' AND labelkey = '" + cc.getCc().get(set.getString("country")) +"'");
	                    			if(set2.next()) {
	                    				line += set2.getString("text")+";";
	                    			}
	            				}
	            				break;
	            			default:
	            				System.out.println("ERROR "+personal[i][0]);
	            		}
	            	}
            

            		for (int i = 1; i < modules.length; i++) {		

            			ResultSet set2 = st2.executeQuery("SELECT q.id,a.type FROM useranswers ua " +
    	        			"JOIN answerdata ad ON ua.answer = ad.id " +
    	        			"JOIN questions q ON q.id = ad.question " +
    	        			"JOIN answers a ON a.id = ad.answer " +
    	        			"WHERE ua.assesment = " + assesmentId + " "+
    	        			"AND ua.loginname = '"+loginname+"'" +
    	        			"AND q.module = " +modules[i][0]+" " +
    	        			"ORDER BY q.questionorder");
    	        	
            			boolean done = false;
            			while(set2.next()) {
            				done = true;
	    	        		if(set2.getInt("type") == AnswerData.CORRECT) {
	    	        			line += "CORRECTA;";
	    	        		}else {
	    	        			line += "INCORRECTA;";
	    	        		}
	    	        	}
            		
            			if(!done) {
                    		for(int j = 0; j < modules[i][1]; j++) {
                    			line += "----;";
                    		}
            			}
            		}
            		line += "\n";
            	}
            	fos.write(line.getBytes());
            }
        	//moduloPsicologico(name, doneUsers, names, st3)
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	System.out.println("FIN");
	}

    
    public static void moduloPsicologico(String name, Collection doneUsers, Hashtable names, Statement st) throws Exception {
      File fout = new File("C:/Resultados"+name+"_PSI.csv");
      System.out.println("C:/Resultados"+name+"_PSI.csv");
      FileOutputStream fos = new FileOutputStream(fout);
      fos.write(new String("NOMBRE;APELLIDO;EMAIL;FECHA DE NACIMIENTO;ACTITUD;STRESS;\n").getBytes());
      
      Iterator it = doneUsers.iterator();
      while(it.hasNext()) {
      	String loginname = (String)it.next();
      	fos.write(String.valueOf(names.get(loginname)).getBytes());
      	ResultSet set = st.executeQuery("SELECT psiresult1+psiresult2+psiresult3 AS actitud, " +
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
    }
}
