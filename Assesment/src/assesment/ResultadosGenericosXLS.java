package assesment;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import assesment.communication.question.AnswerData;
import assesment.communication.question.QuestionData;
import assesment.communication.user.UserData;
import assesment.communication.util.CountryConstants;

public class ResultadosGenericosXLS {
	

    public static void main(String[] args) {

    	try {
    		
            Class.forName("org.postgresql.Driver");
            
            
            Connection conn1 = DriverManager.getConnection("jdbc:postgresql://177.71.248.87:5432/assesment","postgres","pr0v1s0r1A");
                       
            //Connection conn1 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/assesment","postgres","pr0v1s0r1A");
            
            
            Statement st = conn1.createStatement();
            Statement st2 = conn1.createStatement();
            Statement st3 = conn1.createStatement();
            

            Integer assesmentId = 519;
            String assessmentName = "";
            ResultSet setName = st.executeQuery("SELECT name FROM assesments WHERE id = "+assesmentId);
            if(setName.next()) {
            	assessmentName = setName.getString("name");
            }
            //assessmentName = "Anglo Gold Ashanti Survey 2018";
            
            String language = "es";
            
            WritableWorkbook w = Workbook.createWorkbook(new File("C:/Users/Juan Rodriguez/Desktop/CEPA/Assessment/"+assessmentName+".xls"));
            
            String moduleDatosPersonales = null;
            String modules = "0";
            ResultSet setModules = st.executeQuery("select id,type from modules where assesment = "+assesmentId);
            while(setModules.next()) {
            	if(setModules.getInt("type") == 1) {
            		moduleDatosPersonales = setModules.getString("id");
            	}else {
            		modules += ","+setModules.getString("id");
            	}
            }
            
	        Hashtable<String,UserData> names = new Hashtable<String,UserData>();
            Collection<String> doneUsers = new LinkedList<String>();
            ResultSet set = st.executeQuery("SELECT DISTINCT ua.loginname,u.firstname,u.lastname,u.email,u.brithdate" +
            		",u.vehicle,u.extradata,u.extradata2,u.extradata3 " +
            		" from useranswers ua " +
            		" JOIN users u ON u.loginname = ua.loginname " +
            		" WHERE ua.assesment = " + assesmentId); // cantidad total de preguntas del assesment
            
            while(set.next()) {
            	String loginname = set.getString("loginname"); 
            	doneUsers.add(loginname);
            	UserData user = new UserData();
            	user.setLoginName(loginname);
            	user.setFirstName(set.getString("firstname"));
            	user.setLastName(set.getString("lastname"));
            	user.setBirthDate(set.getDate("brithdate"));
            	user.setEmail(set.getString("email"));
            	user.setVehicle(set.getString("vehicle"));
            	user.setExtraData(set.getString("extradata"));
            	user.setExtraData2(set.getString("extradata2"));
            	user.setExtraData3(set.getString("extradata3"));
            	names.put(loginname,user);
            }
	
            if(moduleDatosPersonales != null && moduleDatosPersonales.length() > 0) {
	            
                WritableSheet s = w.createSheet("Datos Personales", 0);
	
	            set = st.executeQuery("SELECT count(*) AS c FROM questions q, generalmessages gm WHERE q.key = gm.labelkey AND " +
		        		"module = "+moduleDatosPersonales+" AND gm.language = '"+language+"'");
	            set.next();
		        int[] answers = new int[set.getInt("c")]; // cantidad de preguntas personales
		        set = st.executeQuery("SELECT q.id,gm.text,q.type FROM questions q, generalmessages gm WHERE q.key = gm.labelkey AND " +
		        		"module = "+moduleDatosPersonales+" AND gm.language = '"+language+"' ORDER BY questionorder");
		        int i = 0;
		        Hashtable<Integer,Integer> questionType = new Hashtable<Integer,Integer>();
		        int column = writeUserHeader(s);
	
	        	CountryConstants cc = new CountryConstants();
	        	while(set.next()) {
		        	String id = set.getString("id");
		        	String type = set.getString("type");
		        	s.addCell(new Label(column,0,set.getString("text"))); 
		    		column++;		
		        	answers[i] = Integer.parseInt(id);
		        	questionType.put(new Integer(id), new Integer(type));
		        	i++;
		        }

	        	int row = 1;
	        	Iterator<String> it = doneUsers.iterator();
	            while(it.hasNext()) {
		        	int lineas = 1;
	            	String loginname = (String)it.next();
		        	column = writeUserData(names.get(loginname),row,s);
	            	for(i = 0; i < answers.length; i++) {
	            		switch(questionType.get(new Integer(answers[i])).intValue()) {
	            			
	            		case QuestionData.TEXTAREA: case QuestionData.TEXT: case QuestionData.EMAIL:// Texto Simple            				
	            				set = st.executeQuery("SELECT ad.text FROM useranswers ua " +
	                			"JOIN answerdata ad ON ua.answer = ad.id " +
	                			"WHERE ua.assesment = " + assesmentId + " " +
	                			"AND ua.loginname = '"+loginname+"' " +
	                			"AND ad.question = "+answers[i]);
	            				if(set.next()) {
	            					s.addCell(new Label(column, row,  set.getString("text"))); 
	            				}
	            				break;
	            			
	            			case QuestionData.KILOMETERS:
	            				set = st.executeQuery("SELECT ad.text,ad.distance FROM useranswers ua " +
	                			"JOIN answerdata ad ON ua.answer = ad.id " +
	                			"WHERE ua.assesment = " + assesmentId + " " +
	                			"AND ua.loginname = '"+loginname+"' " +
	                			"AND ad.question = "+answers[i]);
	            				if(set.next()) {
	            					String str = set.getString("text");
	            					if(set.getInt("distance") == 0) {
	            						str += " kms";
	            					}else {
	            						str += " milhas";
	            					}
	            					s.addCell(new Label(column, row,  str)); 
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
	            					s.addCell(new Label(column, row,  fecha)); 
	            				}
	            				break;
	            			
	            				
	            			case QuestionData.EXCLUDED_OPTIONS: case QuestionData.LIST:
	            				// Texto con traducciones
	            				set = st.executeQuery("SELECT gm.text FROM useranswers ua " +
	    						"JOIN answerdata ad ON ua.answer = ad.id " +
	    						"JOIN answers a ON a.id = ad.answer " +
	    						"JOIN generalmessages gm ON gm.labelkey = a.key " +
	    						"WHERE ua.assesment = " + assesmentId + " "+
	    						"AND gm.language = '"+language+"' " +
	                			"AND ua.loginname = '"+loginname+"' " +
	                			"AND ad.question = "+answers[i]);
	            				if(set.next()) {
	            					s.addCell(new Label(column, row,  set.getString("text"))); 
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
	    						"AND gm.language = '"+language+"' " +
	                			"AND ua.loginname = '"+loginname+"' " +
	                			"AND ad.question = "+answers[i]);
	            				int c = 0;
	            				while(set.next()) {
	            					s.addCell(new Label(column, row + c,  set.getString("text")));
	            					c++;
	            				}
	            				if(lineas < c) {
	            					lineas = c;
	            				}
	            				break;
	            			case QuestionData.COUNTRY:
	            				set = st.executeQuery("SELECT ad.country FROM useranswers ua " +
	    						"JOIN answerdata ad ON ua.answer = ad.id " +
	    						"WHERE ua.assesment = " + assesmentId + " "+
	                			"AND ua.loginname = '"+loginname+"' " +
	                			"AND ad.question = "+answers[i]);
	            				if(set.next()) {
	            					//cc.find(set.getString("country"));
	            					ResultSet set2 = st.executeQuery("SELECT text FROM generalmessages WHERE language = '"+language+"' AND labelkey = '" + cc.getCc().get(set.getString("country")) +"'");
	                    			if(set2.next()) {
		            					s.addCell(new Label(column, row,  set.getString("text"))); 
	                    			}
	            				}
	            				break;
	            			default:
	            				System.out.println("ERROR "+answers[i]);
	            		}
    					column++;
	            	}
					row += lineas;
	            }
	
	            // Modulo Psicologico
	            // Transmeta no lleva modulo psicologico
            }

            // Modulos:
            set = st.executeQuery("SELECT m.id,gm.text FROM modules m " +
            		"JOIN generalmessages gm ON gm.labelkey = m.key " +
            		"WHERE id IN ("+modules+") AND gm.language = '"+language+"'");
            int tab = 0;
            while(set.next()) {
            	String module = set.getString("id");
            	tab++;
                WritableSheet s = w.createSheet(set.getString("text").replaceAll("Conocimientos de ", ""), tab);
		        int column = writeUserHeader(s);

    	        ResultSet set1 = st2.executeQuery("SELECT gm.text, q.id FROM questions q " +
    	        		"JOIN generalmessages gm ON gm.labelkey = q.key " +
    	        		"WHERE q.module = " +module+" "+
    	        		"AND gm.language = '"+language+"' " +
    	        		"ORDER BY questionorder");
            	
    	        // preguntas:
	        	HashMap<String,Integer> map = new HashMap<String,Integer>(); 
    	        while(set1.next()) {
		        	s.addCell(new Label(column,0,set1.getString("text"))); 
		    		map.put(set1.getString(2), new Integer(column));
		        	column++;
    	        }
    	        
    	        Iterator it = doneUsers.iterator();
    	        int row = 0;
    	        while(it.hasNext()) {
    	        	row++;
    	        	String loginname = (String)it.next();
    	        	column = writeUserData(names.get(loginname),row,s);
    	        	
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
    	        		if(assesmentId.intValue() == 312) {
            				ResultSet set3 = st3.executeQuery("SELECT gm.text FROM useranswers ua " +
            						"JOIN answerdata ad ON ua.answer = ad.id " +
            						"JOIN answers a ON a.id = ad.answer " +
            						"JOIN generalmessages gm ON gm.labelkey = a.key " +
            						"WHERE ua.assesment = " + assesmentId + " "+
            						"AND gm.language = '"+language+"' " +
                        			"AND ua.loginname = '"+loginname+"' " +
                        			"AND ad.question = "+question);
                    		if(set3.next()) {
                    			s.addCell(new Label(map.get(String.valueOf(question)).intValue(), row,  set3.getString("text")));
                    			column++;
                    		}

    	        		}else {
	    	        		if(set2.getInt("type") == AnswerData.CORRECT) {
                    			s.addCell(new Label(map.get(String.valueOf(question)).intValue(), row,  "CORRECTA"));
	    	        		}else {
                    			s.addCell(new Label(map.get(String.valueOf(question)).intValue(), row,  "INCORRECTA"));
	    	        		}
    	        		}
    	        	}

    	        	ResultSet set3 = st2.executeQuery("SELECT q.id, ad.text " +
    	        			"FROM useranswers ua " +
    	        			"JOIN answerdata ad ON ad.id = ua.answer " +
    	        			"JOIN questions q ON q.id = ad.question " +
    						"WHERE ua.assesment = " + assesmentId + " "+
    	        			"AND ua.loginname = '"+loginname+"'" +
    	        			"AND text IS NOT NULL " +
    	        			"AND q.module =" +module+" " +
    	        			"ORDER BY q.questionorder");
    	        	while(set3.next()) {
    	        		int question = set3.getInt("id"); 
            			s.addCell(new Label(map.get(String.valueOf(question)).intValue(), row,  set3.getString("text")));
    	        	}
    	        }
            }
	        
            moduloPsicologico(assessmentName,doneUsers,names,st,w,tab+1);

	        w.write();
            w.close();

    	}catch (Exception e) {
			e.printStackTrace();
		}
    	System.out.println("FIN");
	}


	private static int writeUserData(UserData userData, int row, WritableSheet s) throws WriteException, RowsExceededException {
		int column = 0;
		s.addCell(new Label(column, row,  userData.getFirstName())); 
		column++;
		s.addCell(new Label(column, row,  userData.getLastName())); 
		column++;
		s.addCell(new Label(column, row,  userData.getEmail())); 
		column++;
		//s.addCell(new Label(column, row,  formatDate(userData.getBirthDate()))); 
		//column++;
		//s.addCell(new Label(column, row,  userData.getVehicle())); 
		//column++;
		//s.addCell(new Label(column, row,  userData.getExtraData())); 
		//column++;
		//s.addCell(new Label(column, row,  userData.getExtraData2())); 
		//column++;
		return column;
		
	}


	private static int writeUserHeader(WritableSheet s) throws WriteException, RowsExceededException {
		int column = 0;
		s.addCell(new Label(column,0,  "Nombre")); 
		column++;
		s.addCell(new Label(column,0,  "Apellido")); 
		column++;
		s.addCell(new Label(column,0,  "Email")); 
		column++;
		//s.addCell(new Label(column,0,  "Fecha de Nacimiento")); 
		//column++;
		//s.addCell(new Label(column,0,  "Extra 1")); 
		//column++;
		//s.addCell(new Label(column,0,  "Extra")); 
		//column++;
		//s.addCell(new Label(column,0,  "Extra 3")); 
		//column++;
		return column;
	}

    
    private static String formateDate(java.sql.Date date) {
    	if(date == null) {
    		return "--";
    	}
    	Calendar c = Calendar.getInstance();
    	c.setTime(date);
    	return c.get(Calendar.DATE)+"/"+String.valueOf(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.YEAR);
	}


	private static String getCountry(int country) {
    	switch(country) {
    	case 32:
    		return "Brasil";
    	case 33:
    		return "Argentina";
    	case 37:
    		return "Ecuador";
    	case 42:
    		return "Mexico";
    	case 55:
    		return "Colombia";
    	}
    	return "---";
	}


	public static void moduloPsicologico(String name, Collection doneUsers, Hashtable names, Statement st, WritableWorkbook w, int tab) throws Exception {
        WritableSheet s = w.createSheet("Comportamiento", tab);
        int column = writeUserHeader(s);
        s.addCell(new Label(column,0,"ACTITUD"));
        s.addCell(new Label(column+1,0,"STRESS"));
        

        Iterator it = doneUsers.iterator();
        int row = 0;
        while(it.hasNext()) {
        	row++;
        	String loginname = (String)it.next();
        	column = writeUserData((UserData)names.get(loginname),row,s);
        	ResultSet set = st.executeQuery("SELECT psiresult1+psiresult2+psiresult3 AS actitud, " +
      			"psiresult4+psiresult5+psiresult6 AS stress,psiid " +
      			"FROM userassesments " +
          		"WHERE loginname = '"+loginname+"' ");
        	if(set.next()) {
        		if(set.getString("psiid") != null) {
        			double actitud = set.getDouble("actitud") / 3.0;
        			if(actitud < 3) {
        				s.addCell(new Label(column,row,"Verde"));
        			}else if(actitud < 4) {
        				s.addCell(new Label(column,row,"Amarillo"));
        			}else {
        				s.addCell(new Label(column,row,"Rojo"));
        			}
        			double stress = set.getDouble("stress") / 3.0;
		      		if(stress < 3) {
        				s.addCell(new Label(column+1,row,"Verde"));
		      		}else if(stress < 4) {
        				s.addCell(new Label(column+1,row,"Amarillo"));
		      		}else {
        				s.addCell(new Label(column+1,row,"Rojo"));
		      		}
        		}
        	}
        }
    }

	private static String formatDate(Date d) {
		if(d == null) {
			return "---";
		}
		Calendar date = Calendar.getInstance();
		date.setTime(d);
		return date.get(Calendar.DATE)+"/"+String.valueOf(date.get(Calendar.MONTH)+1)+"/"+date.get(Calendar.YEAR);
	}
}
