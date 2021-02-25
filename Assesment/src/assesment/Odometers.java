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

public class Odometers {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        try {
            File f = new File("C://odometers.csv");
            FileOutputStream fos = new FileOutputStream(f);

            Class.forName("org.postgresql.Driver");
            Connection conn1 = DriverManager.getConnection("jdbc:postgresql://67.192.60.149:5432/datacenter5","postgres","pr0v1s0r1A");
            Statement st = conn1.createStatement();
            Statement st2 = conn1.createStatement();


            String sql = "SELECT DISTINCT o.vehicle,v.matricula,div2.resourcekey AS division,div1.resourcekey AS subdivision,div3.resourcekey AS unidad " +
            		"FROM odometers o,vehicles v,divorgitemlevel1s div1,divorgitemlevel2s div2,divorgitemlevel1s div3 " +
            		"WHERE o.fleet = 9398 " +
            		"AND ((o.year = 2008 AND o.period > 6) OR (o.year = 2009 AND o.period < 13)) " +
            		"AND o.vehicle = v.id " +
            		"AND v.divorg2 = div1.id " +
            		"AND v.divorg3 = div3.id " +
            		"AND div2.id = div1.divorgitemlevel2 " +
            		"ORDER by division,subdivision,unidad";
        	ResultSet set = st.executeQuery(sql);
        	
        	while(set.next()) {
        		String vehicle = set.getString("vehicle");
        		String matricula = set.getString("matricula");
        		String division = set.getString("division");
        		String subdivision = set.getString("subdivision");
        		String unidad = set.getString("unidad");
        		
        		int min = 10000000; 
        		int max = 0;

        		String s = matricula+";";
        		
        		for(int i = 7; i < 13; i++) {
            		sql = "SELECT odometer FROM odometers WHERE vehicle = "+vehicle+" AND period = "+i+" AND year = 2008";
            		ResultSet set3 = st2.executeQuery(sql);
                	if(set3.next()) {
                		int odometer = set3.getInt("odometer"); 
                		s += odometer+";";
                		min = Math.min(min, odometer);
                		max = Math.max(max, odometer);
                	}else {
                		s += "--;";
                	}
        		}
        		
        		for(int i = 1; i < 13; i++) {
            		sql = "SELECT odometer FROM odometers WHERE vehicle = "+vehicle+" AND period = "+i+" AND year = 2009";
            		ResultSet set3 = st2.executeQuery(sql);
                	if(set3.next()) {
                		int odometer = set3.getInt("odometer"); 
                		s += odometer+";";
                		min = Math.min(min, odometer);
                		max = Math.max(max, odometer);
                	}else {
                		s += "--;";
                	}
        		}

//            		s += min+";";
//            		s += max+";";
//            		s += (max-min)+";";
        		
        		s += "\n"; 
				fos.write(s.getBytes());
        		
			}
        }catch(Exception e) {
        	e.printStackTrace();
        }
	}
	
}
