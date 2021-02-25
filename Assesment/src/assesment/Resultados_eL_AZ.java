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

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import assesment.communication.question.AnswerData;
import assesment.communication.question.QuestionData;
import assesment.communication.util.CountryConstants;

public class Resultados_eL_AZ {
	

    public static void main(String[] args) {

    	try {
    		
            Class.forName("org.postgresql.Driver");
            
            File fout = new File("C:/ResultadosAZ_eLAZLatinoamérica_2013_EN.xls");
            WritableWorkbook w = Workbook.createWorkbook(fout);
            WritableSheet s = w.createSheet("Resultados", 0);

            //Connection conn1 = DriverManager.getConnection("jdbc:postgresql://173.247.255.40:5432/assesment","postgres","pr0v1s0r1A");
                       
            Connection conn1 = DriverManager.getConnection("jdbc:postgresql://173.247.255.40:5432/elearning","postgres","pr0v1s0r1A");
            
            
            Statement st = conn1.createStatement();
            Statement st2 = conn1.createStatement();
            Statement st3 = conn1.createStatement();
            
            Integer courseId = 1334;
            ResultSet set1 = st.executeQuery("select m.name as module,q.text,q.idquestion,l.name as lesson" +
            		" from modules m " +
            		"join lessonsmodules lm on lm.idmodule = m.idmodule " +
            		"join questionsbylessons ql on lm.idlesson = ql.idlesson " +
            		"join lessons l on l.idlesson = ql.idlesson " +
            		"join questions q on q.idquestion = ql.idquestion " +
            		"where course  = "+courseId+" order by m.order,l.idlesson,q.idquestion");
            int column = 3;
            Hashtable columnIds = new Hashtable();
            while(set1.next()) {
            	String module = set1.getString("module");
            	String question = set1.getString("text");
            	String questionId = set1.getString("idquestion");
            	String lesson = set1.getString("lesson");
            	columnIds.put(questionId, column);
                s.addCell(new Label(column,0,module)); 
                s.addCell(new Label(column,1,lesson)); 
                s.addCell(new Label(column,2,question)); 
            	column++;
            }
            
            ResultSet set2 = st.executeQuery("select distinct u.iduser,u.firstname,u.lastname, u.loginname, u.lastlogin, ua.idquestion, a.correct "+
			            "from useranswers ua  "+
			            "join users u on u.iduser = ua.iduser "+
			            "join usercourses uc on uc.iduser = u.iduser "+
			            "join questions q on q.idquestion = ua.idquestion "+
			            "join answers a on a.idanswer = ua.idanswer "+
			            "where uc.idcourse = "+courseId+
			            " order by firstname,lastname,iduser ");
            int row = 2;
            int idUser = 0;
            while(set2.next()) {
            	int userId = set2.getInt("iduser");
            	String name = set2.getString("firstname")+" "+set2.getString("lastname");
            	String login = set2.getString("loginname");
            	String lastData = set2.getString("lastlogin");
            	if(idUser != userId) {
            		row++;
            		idUser = userId; 
                	s.addCell(new Label(0,row,name));
                	s.addCell(new Label(1,row,login));
                	s.addCell(new Label(2,row,lastData));
            	}
            	String questionId = set2.getString("idquestion");
            	String result = (set2.getInt("correct") == 1) ? "CORRECTA" : "INCORRECTA";
            	if(columnIds.containsKey(questionId)) {
            		s.addCell(new Label((Integer)columnIds.get(questionId),row,result));
            	}
            }
/*            
            String moduleDatosPersonales = "";
            String modules = "0";
            ResultSet setModules = st.executeQuery("select id,type from modules where assesment = "+assesmentId);
            while(setModules.next()) {
            	if(setModules.getInt("type") == 1) {
            		moduleDatosPersonales = setModules.getString("id");
            	}else {
            		modules += ","+setModules.getString("id");
            	}
            }
            
            File fout = new File("C:/Resultados"+assessmentName+"_DatosPersonales.csv");
            System.out.println("C:/Resultados"+assessmentName+"_DatosPersonales.csv");
	        FileOutputStream fos = new FileOutputStream(fout);

	        Hashtable<String,String> names = new Hashtable<String,String>();
            Collection<String> doneUsers = new LinkedList<String>();
            ResultSet set = st.executeQuery("SELECT DISTINCT ua.loginname,u.firstname,u.lastname,u.email,u.brithdate, " +
            		" u.startdate,u.vehicle, u.extradata, u.extradata2" +
            		" FROM useranswers ua " +
            		" JOIN userassesments uas ON (ua.loginname = uas.loginname AND ua.assesment = uas.assesment) " +
            		" JOIN answerdata ad ON ua.answer = ad.id " +
            		" JOIN questions q ON ad.question = q.id " +
            		" JOIN users u ON u.loginname = ua.loginname " +
            		" WHERE ua.assesment = " + assesmentId + " AND uas.enddate IS NOT NULL "); // cantidad total de preguntas del assesment
            
            while(set.next()) {
            	String loginname = set.getString("loginname"); 
            	doneUsers.add(loginname);
            	String data = set.getString("firstname")+";"+set.getString("lastname")+";"+set.getString("email")+";";
            	data += set.getString("brithdate")+";";
            	data += set.getString("startdate")+";";
            	data += set.getString("vehicle")+";";
            	data += set.getString("extradata")+";";
            	data += set.getString("extradata2")+";";
            	names.put(loginname,data);
            }
            
            set = st.executeQuery("SELECT count(*) AS c FROM questions q, generalmessages gm WHERE q.key = gm.labelkey AND " +
	        		"module = "+moduleDatosPersonales+" AND gm.language = 'es'");
            set.next();
	        int[] answers = new int[set.getInt("c")]; // cantidad de preguntas personales
	        set = st.executeQuery("SELECT q.id,gm.text,q.type FROM questions q, generalmessages gm WHERE q.key = gm.labelkey AND " +
	        		"module = "+moduleDatosPersonales+" AND gm.language = 'es' ORDER BY questionorder");
	        int i = 0;
	        Hashtable<Integer,Integer> questionType = new Hashtable<Integer,Integer>();
        	fos.write(new String("NOMBRE;APELLIDO;EMAIL;FECHA NACIMIENTO;FECHA DE INGRESO;FUERZA;RESIDENCIA;# EMPLEADO;").getBytes());

        	CountryConstants cc = new CountryConstants();
        	
        	while(set.next()) {
	        	String id = set.getString("id");
	        	String type = set.getString("type");
	        	fos.write(new String(set.getString("text")+";").getBytes());
	        	answers[i] = Integer.parseInt(id);
	        	questionType.put(new Integer(id), new Integer(type));
	        	i++;
	        }
        	fos.write(new String("\n").getBytes());
	        
	        Iterator<String> it = doneUsers.iterator();
            while(it.hasNext()) {
            	String loginname = (String)it.next();
	        	fos.write(String.valueOf(names.get(loginname)).getBytes());
            	for(i = 0; i < answers.length; i++) {
            		switch(questionType.get(new Integer(answers[i])).intValue()) {
            			
            			case QuestionData.TEXT: case QuestionData.EMAIL:// Texto Simple            				
            				set = st.executeQuery("SELECT ad.text FROM useranswers ua " +
                			"JOIN answerdata ad ON ua.answer = ad.id " +
                			"WHERE ua.assesment = " + assesmentId + " " +
                			"AND ua.loginname = '"+loginname+"' " +
                			"AND ad.question = "+answers[i]);
            				if(set.next()) {
            					fos.write(new String(set.getString("text")+";").getBytes());
            				}
            				break;
            			
            			case QuestionData.KILOMETERS:
            				set = st.executeQuery("SELECT ad.text,ad.distance FROM useranswers ua " +
                			"JOIN answerdata ad ON ua.answer = ad.id " +
                			"WHERE ua.assesment = " + assesmentId + " " +
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
            				
            			case QuestionData.DATE: case QuestionData.BIRTHDATE: case QuestionData.OPTIONAL_DATE:
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
            			
            				
            			case QuestionData.EXCLUDED_OPTIONS: case QuestionData.LIST:
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
            			
            			case QuestionData.INCLUDED_OPTIONS:
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
            			case QuestionData.COUNTRY:
            				set = st.executeQuery("SELECT ad.country FROM useranswers ua " +
    						"JOIN answerdata ad ON ua.answer = ad.id " +
    						"WHERE ua.assesment = " + assesmentId + " "+
                			"AND ua.loginname = '"+loginname+"' " +
                			"AND ad.question = "+answers[i]);
            				if(set.next()) {
            					//cc.find(set.getString("country"));
            					ResultSet set2 = st.executeQuery("SELECT text FROM generalmessages WHERE language = 'es' AND labelkey = '" + cc.getCc().get(set.getString("country")) +"'");
                    			if(set2.next()) {
                    				fos.write(new String(set2.getString("text")+";").getBytes());
                    			}
            				}
            				break;
            			default:
            				System.out.println("ERROR "+answers[i]);
            		}
            	}
				fos.write(new String("\n").getBytes());
            }
            fos.close();
            
            
            // Modulo Psicologico
            // Transmeta no lleva modulo psicologico
            moduloPsicologico(assessmentName,doneUsers,names,st);
            

            
            
            // Modulos:
            set = st.executeQuery("SELECT m.id,gm.text FROM modules m " +
            		"JOIN generalmessages gm ON gm.labelkey = m.key " +
            		// "WHERE id IN (288,289,291) AND gm.language = 'es'");
            		"WHERE id IN ("+modules+") AND gm.language = 'es'");
            
            while(set.next()) {
            	String module = set.getString("id");
                fout = new File("C:/Resultados"+assessmentName+"_"+set.getString("text")+".csv");
                System.out.println("C:/Resultados"+assessmentName+"_"+set.getString("text")+".csv");
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
            }*/
    		w.write();
    		w.close();
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	System.out.println("FIN");
	}
}
