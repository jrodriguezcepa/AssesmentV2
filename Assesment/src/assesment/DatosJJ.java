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

public class DatosJJ {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        try {
            File f = new File("C://assessmentJJ.csv");
            FileOutputStream fos = new FileOutputStream(f);

            Class.forName("org.postgresql.Driver");
            Connection conn1 = DriverManager.getConnection("jdbc:postgresql://173.247.255.40:5432/assesment","postgres","pr0v1s0r1A");
            Statement st = conn1.createStatement();


            String sql = "SELECT u.loginname,u.firstname,u.lastname,u.vehicle,u.extradata," +
				"ua.psiresult1,ua.psiresult2,ua.psiresult3,ua.psiresult4,ua.psiresult5,ua.psiresult6," +
				"a.type,COUNT(*) AS count " +
				"FROM users u " +
				"JOIN userassesments ua ON ua.loginname = u.loginname " +
				"JOIN useranswers uan ON uan.loginname = u.loginname " +
				"JOIN answerdata ad ON ad.id = uan.answer " +
				"JOIN questions q ON q.id = ad.question " +
				"JOIN answers a ON a.id = ad.answer " +
				"WHERE ua.assesment = " + AssesmentData.JJ_6 + " " +
				"AND uan.assesment = " + AssesmentData.JJ_6 + " "+
				"AND q.testtype = " + QuestionData.TEST_QUESTION + " " +
				"GROUP BY u.loginname,u.firstname,u.lastname,u.vehicle," +
				"u.extradata,ua.psiresult1,ua.psiresult2,ua.psiresult3," +
				"ua.psiresult4,ua.psiresult5,ua.psiresult6,a.type " +
				"UNION ("+
				"SELECT DISTINCT u.loginname,u.firstname,u.lastname,u.vehicle,u.extradata,"+
				"ua.psiresult1,ua.psiresult2,ua.psiresult3,ua.psiresult4,ua.psiresult5,ua.psiresult6,0,0 "+
				"FROM users u "+ 
				"JOIN userassesments ua ON ua.loginname = u.loginname "+ 
				"JOIN useranswers uans ON uans.loginname = ua.loginname "+
				"WHERE ua.assesment = " + AssesmentData.JJ_6 +" "+
				"AND uans.assesment = " + AssesmentData.JJ_6 + " "+
				"AND u.loginname NOT IN (SELECT DISTINCT uan.loginname "+ 
				"FROM useranswers uan  "+
				"JOIN answerdata ad ON uan.answer = ad.id "+ 
				"JOIN questions q ON q.id = ad.question  "+
				"WHERE uan.assesment = " + AssesmentData.JJ_6 + " AND q.testtype = " + QuestionData.TEST_QUESTION + ")) ORDER BY loginname ";
        	ResultSet set = st.executeQuery(sql);
        	
        	Hashtable values = new Hashtable();
        	while(set.next()) {
        		String login = set.getString("loginname");
        		if(values.containsKey(login)) {
        			((LineaJJ)values.get(login)).addQuestions(set);
        		}else {
        			values.put(login,new LineaJJ(set));
        		}
        	}
        	
        	sql = "SELECT DISTINCT u.loginname,ant.tag FROM answerdata ad " +
        			"JOIN useranswers ua ON ad.id = ua.answer " +
        			"JOIN answertags ant ON ad.answer = ant.answer " +
        			"JOIN assesmenttags ast ON ant.tag = ast.tag " +
        			"JOIN users u ON u.loginname = ua.loginname " +
        			"WHERE ua.assesment = " + AssesmentData.JJ_6+" "+
        			"AND ast.assesment = " + AssesmentData.JJ_6+" "+
        			"GROUP BY u.loginname,ant.tag " +
        			"HAVING sum(ant.value) > 0 " +
        			"ORDER BY loginname,tag";
        	set = st.executeQuery(sql);
        	while(set.next()) {
        		String login = set.getString("loginname");
        		if(values.containsKey(login)) {
        			((LineaJJ)values.get(login)).addLesson(set.getString("tag"));
        		}
        	}
        	
        	Enumeration<LineaJJ> en = values.elements();
        	Collection<LineaJJ> list = new LinkedList<LineaJJ>();
        	while (en.hasMoreElements()) {
				LineaJJ lineaJJ = (LineaJJ) en.nextElement();
				list.add(lineaJJ);
			}
        	Collections.sort((List) list);
        	Iterator<LineaJJ> it = list.iterator();
        	while (it.hasNext()) {
				LineaJJ lineaJJ = (LineaJJ) it.next();
				fos.write(lineaJJ.getString().getBytes());
			}
        }catch(Exception e) {
        	e.printStackTrace();
        }
	}
	
}
