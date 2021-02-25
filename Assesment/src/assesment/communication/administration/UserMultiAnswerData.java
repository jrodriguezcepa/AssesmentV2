package assesment.communication.administration;

public class UserMultiAnswerData implements Comparable<UserMultiAnswerData> {

	private String question;
	private String answer;
	private Integer type;
	
	private Integer order;

	public UserMultiAnswerData(Object[] data) {
		question = (String)data[5];
		order = (Integer)data[6];
		answer = (String)data[7];
		type = (Integer)data[8];
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	@Override
	public int compareTo(UserMultiAnswerData o) {
		return order.compareTo(o.order);
	}

	
}
