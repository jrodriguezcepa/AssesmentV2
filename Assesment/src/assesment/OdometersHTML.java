package assesment;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import assesment.communication.assesment.AssesmentData;
import assesment.communication.question.QuestionData;

public class OdometersHTML {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        try {
            File f = new File("C://odometers.html");
            FileOutputStream fos = new FileOutputStream(f);

            fos.write("<html>\n".getBytes());
            fos.write("<body>\n".getBytes());
            fos.write("<table border=1>\n".getBytes());
            Class.forName("org.postgresql.Driver");
            Connection conn1 = DriverManager.getConnection("jdbc:postgresql://67.192.60.149:5432/datacenter5","postgres","pr0v1s0r1A");
            Statement st = conn1.createStatement();
            Statement st1 = conn1.createStatement();
            Statement st2 = conn1.createStatement();


            String sql = "SELECT DISTINCT vehicle FROM odometers WHERE fleet = 9398 AND year IN (2008,2009)";
        	ResultSet set = st.executeQuery(sql);
        	
        	while(set.next()) {
        		String vehicle = set.getString("vehicle");
        		
        		sql = "SELECT matricula FROM vehicles WHERE id = "+vehicle;
        		ResultSet set2 = st1.executeQuery(sql);
        		
            	if(set2.next()) {
                    fos.write("<tr>\n".getBytes());

                    int min = 10000000; 
            		int max = 0;

            		String vehicleName = set2.getString("matricula");

                    fos.write(new String("<td>"+vehicleName+"</td>\n").getBytes());
            		
            		for(int i = 1; i < 13; i++) {
                		sql = "SELECT odometer FROM odometers WHERE vehicle = "+vehicle+" AND period = "+i+" AND year = 2008";
                		ResultSet set3 = st2.executeQuery(sql);
                    	if(set3.next()) {
                    		int odometer = set3.getInt("odometer"); 
                            fos.write(new String("<td>"+odometer+"</td>\n").getBytes());
                    		min = Math.min(min, odometer);
                    		max = Math.max(max, odometer);
                    	}else {
                            fos.write(new String("<td>---</td>\n").getBytes());
                    	}
            		}
            		
            		for(int i = 1; i < 13; i++) {
                		sql = "SELECT odometer FROM odometers WHERE vehicle = "+vehicle+" AND period = "+i+" AND year = 2009";
                		ResultSet set3 = st2.executeQuery(sql);
                    	if(set3.next()) {
                    		int odometer = set3.getInt("odometer"); 
                            fos.write(new String("<td>"+odometer+"</td>\n").getBytes());
                    		min = Math.min(min, odometer);
                    		max = Math.max(max, odometer);
                    	}else {
                            fos.write(new String("<td>---</td>\n").getBytes());
                    	}
            		}

                    fos.write(new String("<td>"+min+"</td>\n").getBytes());
                    fos.write(new String("<td>"+max+"</td>\n").getBytes());
                    fos.write(new String("<td>"+(max-min)+"</td>\n").getBytes());
                    fos.write("</tr>\n".getBytes());
            	}
        		
			}
            fos.write("</table>\n".getBytes());
            fos.write("</body>\n".getBytes());
            fos.write("</html>\n".getBytes());
        }catch(Exception e) {
        	e.printStackTrace();
        }
	}
	
}
