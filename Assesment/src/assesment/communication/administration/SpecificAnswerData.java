package assesment.communication.administration;

public class SpecificAnswerData {

	private Integer id;
	private Integer question;
	private Integer answer;
	
	public SpecificAnswerData() {
	}

	public SpecificAnswerData(Integer id, Integer question, Integer answer) {
		this.id = id;
		this.question = question;
		this.answer = answer;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQuestion() {
		return question;
	}

	public void setQuestion(Integer question) {
		this.question = question;
	}

	public Integer getAnswer() {
		return answer;
	}

	public void setAnswer(Integer answer) {
		this.answer = answer;
	}
	
	
}
