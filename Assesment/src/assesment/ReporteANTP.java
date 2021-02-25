 
package assesment;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import assesment.communication.question.AnswerData;
import assesment.communication.question.QuestionData;


public class ReporteANTP {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        try {
            File f = new File("C://ReporteANTP.csv");
            FileOutputStream fos = new FileOutputStream(f);
            

            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://67.192.60.149:5432/assesment","postgres","pr0v1s0r1A");
            Statement st = conn.createStatement();
            Statement sst = conn.createStatement();
            
            String query = "select u.loginname,u.firstname,u.lastname,u.sex,u.vehicle,u.location,u.extradata,ua.assesment,u.nationality " +
				"from userassesments ua " +
				"join users u on ua.loginname = u.loginname " +
				"where assesment in (72,73,74,75) " +
				"and enddate is not null " +
				"and email in ('resultadospnsv2011@antp.org.mx','resultadospmsv2011@antp.org.mx')";
            
            ResultSet set = st.executeQuery(query);
            while(set.next()) {
            	String usuario = set.getString("loginname");
            	String firstName = set.getString("firstname");
            	String lastName = set.getString("lastname");
            	String sex = (set.getInt("sex") == 1) ? "Femenino" : "Masculino";
            	String empresa = (set.getString("extradata") == null) ? "--" : set.getString("vehicle");
            	String accidentes = (set.getString("extradata") == null) ? "--" : set.getString("location");
            	String sede = (set.getString("extradata") == null) ? "--" : set.getString("extradata");

            	String vehiculo = "";
            	switch(set.getInt("nationality")) {
            		case 1:
            			vehiculo = "Trailer sencillo";
            			break;
            		case 2:
            			vehiculo = "Camion < a 8 ton";
            			break;
            		case 3:
            			vehiculo = "Full";
            			break;
            		case 4:
            			vehiculo = "Camion > 8 ton";
            			break;
            		default:
            			vehiculo = "---";
            			break;
            	}

            	String assesment = "";
            	switch(set.getInt("assesment")) {
            		case 72:
            			assesment = "Carga General";
            			break;
            		case 73:
            			assesment = "Materiales Peligrosos";
            			break;
            		case 74:
            			assesment = "Distribución Secundaria Materiales Peligrosos (Ciudad)";
            			break;
            		case 75:
            			assesment = "Distribución Secundaria Cargas Generales (Ciudad)";
            			break;
            	}
            	
            	int modulo1 = 0;
            	int modulo2 = 0;
            	int modulo3 = 0;
            	int modulo4 = 0;
            	
            	String query2 = "select moduleorder,count(*) as count " +
            			"from answerdata ad " +
            			"JOIN useranswers ua on ua.answer = ad.id " +
            			"join questions q on q.id = ad.question " +
            			"join answers a on a.id = ad.answer " +
            			"join modules m on q.module = m.id " +
            			"where ua.loginname = '"+usuario+"' " +
            			"and a.type = " +AnswerData.CORRECT+" "+
            			"and q.testtype = " +QuestionData.TEST_QUESTION+" "+
            			"group by moduleorder " +
            			"order by moduleorder";
                ResultSet set2 = sst.executeQuery(query2);
                while(set2.next()) {
                	switch(set2.getInt("moduleorder")) {
                		case 2:
                			modulo1 = set2.getInt("count");
                			break;
                		case 3:
                			modulo2 = set2.getInt("count");
                			break;
                		case 4:
                			modulo3 = set2.getInt("count");
                			break;
                		case 5:
                			modulo4 = set2.getInt("count");
                			break;
                		default:
                			System.out.println("ERROR "+set2.getInt("moduleorder"));
                	}
                }
                
                String w = firstName+";"+lastName+";"+sex+";"+empresa+";"+sede+";"+accidentes+";"+vehiculo+";"+assesment+";"+modulo1+";"+modulo2+";"+modulo3+";"+modulo4+";"+String.valueOf(modulo1+modulo2+modulo3+modulo4)+";\n";
                fos.write(w.getBytes());
            }
            
        }catch (Exception e) {
			e.printStackTrace();
		}
	}

}
