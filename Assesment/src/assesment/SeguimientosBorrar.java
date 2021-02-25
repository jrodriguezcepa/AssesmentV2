package assesment;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SeguimientosBorrar {
	
	public static void main(String[] args) {

		try {
			File f = new File("C://SeguimientosOctubre.csv");
	        FileOutputStream fos = new FileOutputStream(f);
	
	        Class.forName("org.postgresql.Driver");
	        Connection conn1 = DriverManager.getConnection("jdbc:postgresql://67.192.60.149:5432/datacenter5","postgres","pr0v1s0r1A");
	        Statement st = conn1.createStatement();
	        
	        ResultSet set = st.executeQuery("select v.matricula,vp.date,vp.obs,vpi.* from vehiclepursuitroute vp " +
	        		"join vehiclepursuitrouteitemsvalues vpi on vpi.vehiclepursuitroute = vp.pursuitrouteid " +
	        		"join vehicles v on v.id = vp.vehicle " +
	        		"where v.corporation = 9 and vp.date >= '2009-10-01 00:01'" +
	        		"order by vp.pursuitrouteid,vpi.item");
	        int id = -1;
	        String s = "";
	        while(set.next()) {	        	
	        	if(set.getInt("vehiclepursuitroute") != id) {
	        		if(id != -1) {
	        			s += "\n";
	        			fos.write(s.getBytes());
	        		}
	        		s = set.getString("matricula")+";"+set.getString("date")+";"+set.getString("obs")+";";
	        		id = set.getInt("vehiclepursuitroute");
	        	}
	        	if(set.getInt("itemvalue") == 1) {
	        		s += "O;";
	        	}else {
	        		s += "X;";
	        	}
	        }
			s += "\n";
			fos.write(s.getBytes());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
