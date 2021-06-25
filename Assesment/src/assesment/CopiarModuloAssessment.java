package assesment;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CopiarModuloAssessment {

	public static final String DA_SERVER = "177.71.248.87";
	public static final String BORRAR_FILE = "C:/Users/Usuario/Desktop/BORRAR";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        try {
        	        	
            File f = new File(BORRAR_FILE+"/CopiarModulo.sql");
            FileOutputStream fos = new FileOutputStream(f);
            
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://"+DA_SERVER+":5432/assesment","postgres","pr0v1s0r1A");
            Statement st1 = conn.createStatement();
            Statement st2 = conn.createStatement();
            Statement st3 = conn.createStatement();
            Statement st4 = conn.createStatement();

            Statement st6 = conn.createStatement();

            int[] assessmentId = {1845};
            /*ResultSet set = st1.executeQuery("SELECT MAX(moduleorder) FROM modules WHERE assesment = "+assessmentId);
            set.next();*/
            int moduleOrder = 0;//set.getInt(1)+1;
            
            int moduleId = -1; 
            ResultSet set = st1.executeQuery("SELECT MAX(id) FROM modules");
            set.next();
            moduleId = set.getInt(1)+2;
            
            int questionId = -1; 
            set = st1.executeQuery("SELECT MAX(id) FROM questions");
            set.next();
            questionId = set.getInt(1)+2;

            int answerId = -1; 
            set = st1.executeQuery("SELECT MAX(id) FROM answers");
            set.next();
            answerId = set.getInt(1)+2;
            
            String sql = "";

            for(int i = 0; i < assessmentId.length; i++) {
	            set = st1.executeQuery("SELECT * FROM modules WHERE id IN (3946,3939,3948,3944,3950,3952) order by assesment");
	        	while(set.next()) {
	        		moduleOrder++;
	        		String oldModuleId = set.getString("id");
	        		String newKey = "assesment"+assessmentId[i]+".module"+moduleId+".name"; 
	        		sql = "INSERT INTO modules VALUES ("+moduleId+","+assessmentId[i]+",'"+newKey+"',"+moduleOrder+","+set.getString(5)+");\n"; 
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
	            		sql = "INSERT INTO questions VALUES ("+questionId+", "+moduleId+", '"+newKey+"', "+set3.getString(4)+", "+set3.getString(5)+", "+image+", "+set3.getString(7)+", "+set3.getString(8)+",'f');\n";
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
        	}
        	
        	sql = "SELECT pg_catalog.setval('module_sequence', "+moduleId+", true);\n";
            fos.write(sql.getBytes());

        	sql = "SELECT pg_catalog.setval('question_sequence', "+questionId+", true);\n";
            fos.write(sql.getBytes());
    
            sql = "SELECT pg_catalog.setval('answer_sequence', "+answerId+", true);\n";
            fos.write(sql.getBytes());
        }catch(Exception e) {
        	e.printStackTrace();
        }
	}
}
