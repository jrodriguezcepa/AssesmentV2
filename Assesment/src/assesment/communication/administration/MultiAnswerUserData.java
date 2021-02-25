package assesment.communication.administration;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import assesment.communication.question.AnswerData;

public class MultiAnswerUserData implements Comparable<MultiAnswerUserData> {
	
	private String loginName;
	
	private String firstName;
	private String lastName;
	
	private Date end;
	private String result;
	
	private Collection<UserMultiAnswerData> answers;
	
	public MultiAnswerUserData(Object[] data) {
		loginName = (String)data[1];
		firstName = (String)data[2];
		lastName = (String)data[3];
		end = (Date)data[4];
		result = "approuved";
		
		answers = new LinkedList<UserMultiAnswerData>();
		addAnswer(data);
	}

	public void addAnswer(Object[] data) {
		UserMultiAnswerData answer = new UserMultiAnswerData(data);
		if(answer.getType().intValue() == AnswerData.INCORRECT)
			result = "notapprouved";
		answers.add(answer);
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
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

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Collection<UserMultiAnswerData> getAnswers() {
		return answers;
	}

	public void setAnswers(Collection<UserMultiAnswerData> answers) {
		this.answers = answers;
	}

	@Override
	public int compareTo(MultiAnswerUserData o) {
		return o.end.compareTo(end);
	}

	
}
