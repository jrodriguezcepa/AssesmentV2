package assesment.communication.assesment;

import java.sql.ResultSet;
import java.sql.SQLException;

import assesment.communication.question.AnswerData;

public class LineaJJ implements Comparable{

	private String firstName;
	private String lastName;
	private String country;
	private String op_group;
	private String supervisor;
	private String psi;
	private String test;
	private int total;
	private String lessons;

	public LineaJJ(ResultSet set) throws SQLException {
		firstName = set.getString("firstname");
		lastName = set.getString("lastname");
		supervisor = set.getString("vehicle");
		switch(set.getInt("extradata")) {
	        case 4460:
	    		op_group = "Vistakon";
	    		break;
	        case 4461:
	    		op_group = "Consumo";
        		break;
	        case 4462:
	    		op_group = "MD&D";
        		break;
	        case 4463:
	    		op_group = "Janssen-Cilag";
	            break;
	    }
		if(set.getString("psiresult1") == null) {
			psi = "No realizó";
		}else {
			double value = set.getDouble("psiresult1")+set.getDouble("psiresult2")+set.getDouble("psiresult3")+set.getDouble("psiresult4")+set.getDouble("psiresult5")+set.getDouble("psiresult6");
			value = value * 100.0 / 30.0;
			psi = String.valueOf(Math.round(value));
		}
		if(set.getInt("type") == AnswerData.CORRECT) {
			test = set.getString("count");
		}
		total = set.getInt("count");
	}

	public void addQuestions(ResultSet set) throws SQLException {
		if(set.getInt("type") == AnswerData.CORRECT) {
			test = set.getString("count");
		}
		total += set.getInt("count");
	}

	public void addLesson(String string) {
		if(lessons == null || lessons.length() == 0) {
			lessons = string;
		}else {
			lessons += "," + string;
		}
		
	}

	public String getString() {
		if(total < 38) {
			if(total == 0) {
				test = "Solo Datos Personales";
			}else {
				test = "Incompleto";
			}
		}else {
			double value = new Double(test).doubleValue();
			value = value * 100.0 / 38.0;
			test = String.valueOf(Math.round(value));
		}
		if(lessons == null) {
			lessons = "---";
		}
		return supervisor+";"+firstName+";"+lastName+";"+op_group+";"+psi+";"+test+";"+lessons+"\n";
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getCountry() {
		return country;
	}

	public String getOp_group() {
		return op_group;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public String getPsi() {
		return psi;
	}

	public String getTest() {
		if(total < 38) {
			if(total == 0) {
				test = "Solo Datos Personales";
			}else {
				test = "Incompleto";
			}
		}else {
			double value = new Double(test).doubleValue();
			value = value * 100.0 / 38.0;
			test = String.valueOf(Math.round(value));
		}
		return test;
	}

	public String getLessons() {
		if(lessons == null) {
			lessons = "---";
		}
		return lessons;
	}

	public int compareTo(Object o) {
		try {
			LineaJJ l = (LineaJJ)o;
			if(supervisor != null && l.supervisor != null && !l.supervisor.equals(supervisor)) {
				return supervisor.compareTo(l.supervisor);
			}
			if(op_group != null && l.op_group != null && !l.op_group.equals(op_group)) {
				return op_group.compareTo(l.op_group);
			}
			if(country != null && l.country != null && !l.country.equals(country)) {
				return country.compareTo(l.country);
			}
			if(firstName != null && l.firstName != null && !l.firstName.equals(firstName)) {
				return firstName.compareTo(l.firstName);
			}
			if(lastName != null && l.lastName != null && !l.lastName.equals(lastName)) {
				return lastName.compareTo(l.lastName);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
