package assesment.communication.user;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

public class UsuarioCharlaUPM implements Comparable<UsuarioCharlaUPM> {
	
	private String firstName;
	private String lastName;
	private String ci;
	private String contractor;
	
	private Calendar register;
	private Calendar endDate;
	private Integer correct1;
	private Integer incorrect1;
	private Integer correct2;
	private Integer incorrect2;
	
	
	public UsuarioCharlaUPM(String firstName, String lastName, String ci, String contractor, Timestamp registerD,
			Timestamp endDateD, Integer correct1, Integer incorrect1, Integer correct2, Integer incorrect2) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.ci = ci;
		this.contractor = contractor;
		if(registerD != null) {
			register = Calendar.getInstance();
			register.setTime(registerD);
		}
		if(endDateD != null) {
			endDate = Calendar.getInstance();
			endDate.setTime(endDateD);
		}
		this.correct1 = correct1;
		this.incorrect1 = incorrect1;
		this.correct2 = correct2;
		this.incorrect2 = incorrect2;
	}

	public UsuarioCharlaUPM(String firstName, String lastName, String ci, String contractor) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.ci = ci;
		this.contractor = contractor;
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

	public String getContractor() {
		return contractor;
	}

	public void setContractor(String contractor) {
		this.contractor = contractor;
	}

	public String getCi() {
		return ci;
	}

	public void setCi(String ci) {
		this.ci = ci;
	}

	public Calendar getRegister() {
		return register;
	}

	public void setRegister(Timestamp timestamp) {
		if(timestamp != null) {
			register = Calendar.getInstance();
			register.setTime(timestamp);
		}
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDateD) {
		if(endDateD != null) {
			endDate = Calendar.getInstance();
			endDate.setTime(endDateD);
		}
	}

	public Integer getCorrect1() {
		return correct1;
	}

	public void setCorrect1(Integer correct1) {
		this.correct1 = correct1;
	}

	public Integer getIncorrect1() {
		return incorrect1;
	}

	public void setIncorrect1(Integer incorrect1) {
		this.incorrect1 = incorrect1;
	}

	public Integer getCorrect2() {
		return correct2;
	}

	public void setCorrect2(Integer correct2) {
		this.correct2 = correct2;
	}

	public Integer getIncorrect2() {
		return incorrect2;
	}

	public void setIncorrect2(Integer incorrect2) {
		this.incorrect2 = incorrect2;
	}

	public ArrayList<Object> getLine() {
        ArrayList<Object> row = new ArrayList<Object>();
 		row.add(ci);
 		row.add(firstName);
 		row.add(lastName);
 		row.add(contractor);
 		row.add(formatDate(register));
 		row.add(formatDate(endDate));
 		if(correct1 != null)
 			row.add(correct1);
 		if(incorrect1 != null)
 			row.add(incorrect1);
 		if(correct2 != null)
 			row.add(correct2);
 		if(incorrect2 != null)
 			row.add(incorrect2);
		return row;
	}

	private String formatDate(Calendar date) {
		if(date == null)
			return null;
		return date.get(Calendar.DATE)+"/"+String.valueOf(date.get(Calendar.MONTH)+1)+"/"+date.get(Calendar.YEAR)+" "+formatTime(date.get(Calendar.HOUR_OF_DAY))+":"+formatTime(date.get(Calendar.MINUTE)) ;
	}

	private String formatTime(int time) {
		if(time < 10)
			return "0"+time;
		return String.valueOf(time);
	}

	@Override
	public int compareTo(UsuarioCharlaUPM o) {
		int value = compareDates(endDate, o.endDate);
		if(value == 0)
			value = compareDates(register, o.register);
		if(value == 0)
			value = firstName.compareTo(o.firstName);
		return value;
	}

	private int compareDates(Calendar date1, Calendar date2) {
		if(date1 == null && date2 == null)
			return 0;
		if(date1 != null && date2 == null)
			return -1;
		if(date1 == null && date2 != null)
			return 1;
		return date1.compareTo(date2);
	}
	
	
}
