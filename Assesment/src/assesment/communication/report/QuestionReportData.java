package assesment.communication.report;

import java.util.HashMap;

import assesment.communication.language.Text;
import assesment.communication.question.AnswerData;

public class QuestionReportData {

	private Integer id;
	private String key;
	private Integer order;
	private int correct;
	private int incorrect;
	private Integer answer;

	public QuestionReportData(Integer id, String key, Integer order) {
		this.id = id;
		this.key = key;
		this.order = order;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public int getCorrect() {
		return correct;
	}

	public void setCorrect(int correct) {
		this.correct = correct;
	}

	public int getIncorrect() {
		return incorrect;
	}

	public void setIncorrect(int incorrect) {
		this.incorrect = incorrect;
	}

	public void addValue(Integer type, Integer value) {
		if(type == AnswerData.CORRECT) {
			correct = value;
		}else {
			incorrect = value;
		}		
	}
	
	public String getLine(int index, Text messages) {
		String line = "data.setCell("+index+", 0, '"+messages.getText(key)+"');";
		line += "data.setCell("+index+", 1, "+correct+");";
		line += "data.setCell("+index+", 2, "+incorrect+");";
		return line;
	}

	public String getLineBKP(int index, HashMap messages) {
		String line = "data.setCell("+index+", 0, '"+(String)messages.get(key)+"');";
		line += "data.setCell("+index+", 1, "+correct+");";
		line += "data.setCell("+index+", 2, "+incorrect+");";
		return line;
	}
	
	public void setAnswer(Integer answer) {
		this.answer = answer;
	}

	public Integer getAnswer() {
		return answer;
	}
	
}
