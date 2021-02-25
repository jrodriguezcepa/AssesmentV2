package assesment.persistence.user.tables;

import java.util.Date;

import assesment.communication.assesment.AssesmentData;
import assesment.communication.user.UserData;

public class UserAssesmentData {

	private UserData user;
	private AssesmentData assessment;
	private Date end;
	private int answers;
	
	public UserAssesmentData() {
	}
	
	public UserAssesmentData(UserData user, AssesmentData assessment, Date end) {
		this(user, assessment,0 ,end);
	}

	public UserAssesmentData(UserData user, AssesmentData assessment, int answers, Date end) {
		this.user = user;
		this.assessment = assessment;
		this.end = end;
		this.answers = answers;
	}

	public UserData getUser() {
		return user;
	}
	
	public void setUser(UserData user) {
		this.user = user;
	}
	
	public AssesmentData getAssessment() {
		return assessment;
	}
	
	public void setAssessment(AssesmentData assessment) {
		this.assessment = assessment;
	}
	
	public Date getEnd() {
		return end;
	}
	
	public void setEnd(Date end) {
		this.end = end;
	}

	public int getAnswers() {
		return answers;
	}

	public void setAnswers(int answers) {
		this.answers = answers;
	}
	
	
}
