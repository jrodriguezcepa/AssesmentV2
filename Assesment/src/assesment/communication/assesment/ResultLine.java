package assesment.communication.assesment;

import java.sql.ResultSet;
import java.sql.SQLException;

import assesment.communication.question.AnswerData;

public class ResultLine implements Comparable{

	protected String login;
	protected String firstName;
	protected String lastName;
	protected Integer country;
	protected Integer extraData;
	protected Integer psi;
	protected Integer test;
	protected Integer total;
	protected String lessons;

	public ResultLine(Object[] data) throws SQLException {
		login = (String)data[0];
		firstName = (String)data[1];
		lastName = (String)data[2];
		country = (Integer)data[3];
		extraData = (Integer)data[4];
		if(data[5] == null) {
			psi = new Integer(-1);
		}else {
			double value = 0.0; 
			for(int i = 5; i < 11; i++) {
				value += ((Integer)data[i]).doubleValue();
			}
			value = value * 100.0 / 30.0;
			psi = Integer.valueOf(String.valueOf(Math.round(value)));
		}
		addQuestions(data);
	}

	public void addQuestions(Object[] data) throws SQLException {
		if(((Integer)data[11]).intValue() == AnswerData.CORRECT) {
			test = (Integer)data[11];
		}
		total = (Integer)data[11];
	}

	public void addLesson(String string) {
		if(lessons == null || lessons.length() == 0) {
			lessons = string;
		}else {
			lessons += "," + string;
		}
		
	}

	public int compareTo(Object o) {
		try {
			ResultLine l = (ResultLine)o;
			if(extraData != null && l.extraData != null && !l.extraData.equals(extraData)) {
				return extraData.compareTo(l.extraData);
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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getCountry() {
		return country;
	}

	public void setCountry(Integer country) {
		this.country = country;
	}

	public Integer getExtraData() {
		return extraData;
	}

	public void setExtraData(Integer extraData) {
		this.extraData = extraData;
	}

	public Integer getPsi() {
		return psi;
	}

	public void setPsi(Integer psi) {
		this.psi = psi;
	}

	public Integer getTest() {
		return test;
	}

	public void setTest(Integer test) {
		this.test = test;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getLessons() {
		return lessons;
	}

	public void setLessons(String lessons) {
		this.lessons = lessons;
	}

}
