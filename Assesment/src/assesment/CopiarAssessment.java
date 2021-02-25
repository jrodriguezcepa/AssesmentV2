package assesment;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import assesment.communication.assesment.AssesmentData;
import assesment.communication.question.QuestionData;

public class CopiarAssessment {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        try {
        	
        	String newName = "MDP";
        	String comment = "J&J Lat y C.";
        	String corporation = "5";
        	
        	Calendar c = Calendar.getInstance();
        	String start = c.get(Calendar.YEAR)+"-"+String.valueOf(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+" 10:00:00.000";
        	
        	c.add(Calendar.YEAR,1);
        	String end = c.get(Calendar.YEAR)+"-"+String.valueOf(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+" 10:00:00.000";
        	
            File f = new File("C:/Users/Juan Rodriguez/Desktop/BORRAR/"+newName+".sql");
            FileOutputStream fos = new FileOutputStream(f);
            
            String copyId = "314";
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://177.71.248.87:5432/assesment","postgres","pr0v1s0r1A");
            Statement st1 = conn.createStatement();
            Statement st2 = conn.createStatement();
            Statement st3 = conn.createStatement();
            Statement st4 = conn.createStatement();

            Statement st6 = conn.createStatement();

            ResultSet set = st1.executeQuery("SELECT MAX(id) FROM assesments");
            set.next();
            int assessmentId = 314;//set.getInt(1)+1;
            
            int moduleId = -1; 
            set = st1.executeQuery("SELECT MAX(id) FROM modules");
            set.next();
            moduleId = set.getInt(1)+20;
            
            int questionId = -1; 
            set = st1.executeQuery("SELECT MAX(id) FROM questions");
            set.next();
            questionId = set.getInt(1)+100;

            int answerId = -1; 
            set = st1.executeQuery("SELECT MAX(id) FROM answers");
            set.next();
            answerId = set.getInt(1)+100;
            
            String sql = "";

            ResultSet setAssessment = st2.executeQuery("SELECT psitest,instantfeedback,reportfeedback,psifeedback,green,yellow,errorfeedback,elearning,certificate,certificateimagees,certificateimageen,certificateimagept FROM assesments WHERE id = "+copyId);
            if(setAssessment.next()) {
            	sql = "INSERT INTO assesments "+
            		"VALUES ("+assessmentId+","+corporation+",'"+newName+"','"+comment+"','"+start+"','"+end+"',1,'"+setAssessment.getString("psitest")+"','" +
            		setAssessment.getString("instantfeedback")+"','"+setAssessment.getString("reportfeedback")+"','" +
            		setAssessment.getString("psifeedback")+"',"+setAssessment.getString("green")+","+setAssessment.getString("yellow")+",'"+
            		setAssessment.getString("errorfeedback")+"','"+setAssessment.getString("elearning")+"','"+setAssessment.getString("certificate")+"','"+setAssessment.getString("certificateimagees")+"','"+setAssessment.getString("certificateimageen")+"','"+setAssessment.getString("certificateimagept")+"');\n";
            	//fos.write(sql.getBytes());
	/*
	            set = st1.executeQuery("SELECT * FROM assesmenttags WHERE assesment IN ("+copyId+")");
	        	while(set.next()) {
	        		sql = "INSERT INTO assesmenttags VALUES ("+assessmentId+","+set.getString("tag")+","+set.getString("min")+");\n"; 
	                fos.write(sql.getBytes());
	        	}
	*/
	            set = st1.executeQuery("SELECT * FROM modules WHERE id IN (1465)");
	        	while(set.next()) {
	        		String oldModuleId = set.getString("id");
	        		String newKey = "assesment"+assessmentId+".module"+moduleId+".name"; 
	        		sql = "INSERT INTO modules VALUES ("+moduleId+","+assessmentId+",'"+newKey+"',"+set.getString(4)+","+set.getString(5)+");\n"; 
	                fos.write(sql.getBytes());
	                
	                ResultSet set2 = st2.executeQuery("SELECT * FROM generalmessages WHERE labelkey = '"+set.getString("key")+"'  ");
	                while(set2.next()) {
	                //	sql = "DELETE FROM generalmessages WHERE labelkey = '"+newKey+"' AND language = '"+set2.getString(2)+"';\n";
	                //    fos.write(sql.getBytes());
	                	sql = "INSERT INTO generalmessages VALUES ('"+newKey+"','"+set2.getString(2)+"','"+set2.getString(3)+"');\n";
	                	
	                	ResultSet setGM = st6.executeQuery("SELECT * FROM generalmessages where labelkey = '"+newKey+"'");
	                	if(setGM.next()) {
	                		System.out.println("--> "+newKey);
	                	}
	                    fos.write(sql.getBytes());
	                }
	                
	                ResultSet set3 = st3.executeQuery("SELECT * FROM questions WHERE module = "+oldModuleId);
	            	while(set3.next()) {
	            		String oldQuestionId = set3.getString("id");
	            		newKey = "module"+moduleId+".question"+questionId+".text"; 
	            		String image = (set3.getString(6) != null) ? "'"+set3.getString(6)+"'" : "NULL";
	            		sql = "INSERT INTO questions VALUES ("+questionId+", "+moduleId+", '"+newKey+"', "+set3.getString(4)+", "+set3.getString(5)+", "+image+", "+set3.getString(7)+", "+set3.getString(8)+");\n";
	                    fos.write(sql.getBytes());
	
	                    set2 = st2.executeQuery("SELECT * FROM generalmessages WHERE labelkey = '"+set3.getString("key")+"' ");
	                    while(set2.next()) {
	                    	//sql = "DELETE FROM generalmessages WHERE labelkey = '"+newKey+"' AND language = '"+set2.getString(2)+"';\n";
	                        //fos.write(sql.getBytes());
	                    	sql = "INSERT INTO generalmessages VALUES ('"+newKey+"','"+set2.getString(2)+"','"+set2.getString(3)+"');\n";
	                    	//System.out.println("- "+set2.getString(3));
		                	ResultSet setGM = st6.executeQuery("SELECT * FROM generalmessages where labelkey = '"+newKey+"'");
		                	if(setGM.next()) {
		                		System.out.println("--> "+newKey);
		                	}

		                	fos.write(sql.getBytes());
	                    }
	
	                    ResultSet set4 = st4.executeQuery("SELECT * FROM answers WHERE question = "+oldQuestionId);
	                	while(set4.next()) {
	                		String oldAnswerId = set4.getString("id");
	                		newKey = "question"+questionId+".answer"+answerId+".text"; 
	                		sql = "INSERT INTO answers VALUES ("+answerId+", "+questionId+", '"+newKey+"', "+set4.getString(4)+", "+set4.getString(5)+");\n";
	                        fos.write(sql.getBytes());
	
	                        set2 = st2.executeQuery("SELECT * FROM generalmessages WHERE labelkey = '"+set4.getString("key")+"' ");
	                        while(set2.next()) {
	                        	//sql = "DELETE FROM generalmessages WHERE labelkey = '"+newKey+"' AND language = '"+set2.getString(2)+"';\n";
	                            //fos.write(sql.getBytes());
	                            sql = "INSERT INTO generalmessages VALUES ('"+newKey+"','"+set2.getString(2)+"','"+set2.getString(3)+"');\n";
	                        	//System.out.println("     - "+set2.getString(3));
	    	                	ResultSet setGM = st6.executeQuery("SELECT * FROM generalmessages where labelkey = '"+newKey+"'");
	    	                	if(setGM.next()) {
	    	                		System.out.println("--> "+newKey);
	    	                	}

	    	                	fos.write(sql.getBytes());
	                        }
	
	                    	/*set2 = st2.executeQuery("SELECT * FROM answertags WHERE answer = "+oldAnswerId);
	                        while(set2.next()) {
		                		sql = "INSERT INTO answertags VALUES ("+answerId+", "+set2.getString("tag")+","+set2.getString("value")+");\n";
		                        fos.write(sql.getBytes());
	                        }*/
	
	                    	answerId++;
	                	}
	                	
	                    
	                	questionId++;
	            	}
	                
	
	                moduleId++;
	        	}
	        	
	        	sql = "SELECT pg_catalog.setval('assesment_sequence', "+assessmentId+", true);\n";
	         //   fos.write(sql.getBytes());
	            
	        	sql = "SELECT pg_catalog.setval('module_sequence', "+moduleId+", true);\n";
	            fos.write(sql.getBytes());
	
	        	sql = "SELECT pg_catalog.setval('question_sequence', "+questionId+", true);\n";
	            fos.write(sql.getBytes());
        
	            sql = "SELECT pg_catalog.setval('answer_sequence', "+answerId+", true);\n";
	            fos.write(sql.getBytes());
            }
        }catch(Exception e) {
        	e.printStackTrace();
        }
	}
}
