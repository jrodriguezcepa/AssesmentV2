package assesment.communication.report;

import java.util.HashMap;
import java.util.Iterator;

import assesment.communication.language.Text;
import assesment.communication.question.AnswerData;

public class ModuleReportData {

	private Integer id;
	private String key;
	private Integer order;
	private HashMap<Integer,QuestionReportData> questions;
	
	private int correct;
	private int incorrect;
	
	private int red;
	private int green;
	private int yellow;
	
	private HashMap<String,Integer[]> values = new HashMap<String,Integer[]>();

	public ModuleReportData(Integer id) {
		this.id = id;
		correct = 0;
		incorrect = 0;
		red = 0;
		green = 0;
		yellow = 0;
		questions = new HashMap<Integer,QuestionReportData>();
	}

	public void addUserResult(String user,int type,Integer value) {
		if(type == AnswerData.CORRECT) {
			correct += value;
		}else {
			incorrect += value;
		}
		if(values.containsKey(user)) {
			values.get(user)[type] = value;
		}else {
			Integer[] results = {0,0};
			results[type] = value;
			values.put(user, results);
		}
	}

	public Integer getId() {
		return id;
	}

	public int getCorrect() {
		double percent = new Integer(correct).doubleValue();
		percent = percent * 100;
		percent = percent / getTotal();
		return Integer.parseInt(String.valueOf(Math.round(percent)));
	}

	public int getIncorrect() {
		return 100 - getCorrect();
	}
	
	public double getTotal() {
		return new Integer(correct + incorrect).doubleValue();
	}

	public int getRed() {
		return red;
	}

	public int getGreen() {
		return green;
	}

	public int getYellow() {
		return yellow;
	}

	public void loadColors(double greenLine, double yellowLine) {
		Iterator<Integer[]> valueIt = values.values().iterator();
		while(valueIt.hasNext()) {
			Integer[] userValues = valueIt.next();
			double total = userValues[AnswerData.CORRECT].doubleValue() + userValues[AnswerData.INCORRECT].doubleValue();
			double value = userValues[AnswerData.CORRECT].doubleValue() / total*100;
	        if(value < yellowLine) {
	            red++;
	        }else if(value < greenLine) {
	            yellow++;
	        }else {
	            green++;
	        }
		}
	}

	public void addQuestion(Object[] data) {
		if(questions.size() == 0) {
			key = (String)data[1];
			order = (Integer)data[2];
		}
		if(questions.containsKey(data[3])) {
			questions.get(data[3]).addValue((Integer)data[6],(Integer)data[7]);
		}else {
			QuestionReportData questionData = new QuestionReportData((Integer)data[3],(String)data[4],(Integer)data[5]);
			questionData.addValue((Integer)data[6],(Integer)data[7]);
			questions.put((Integer)data[3],questionData);
		}
	}

	public Iterator<QuestionReportData> getQuestionIterator() {
		return questions.values().iterator();
	}
	
	public int getQuestionSize() {
		return questions.size();
	}

	public String getLine(int index, Text messages) {
		return "data.setCell("+index+", 0, '"+messages.getText(key)+"');";
	}

	public String getKey() {
		return key;
	}

	public void addWrongQuestion(QuestionReportData questionData) {
		questions.put(questionData.getId(), questionData);
	}

	public void addResult(int type) {
		if(type == AnswerData.CORRECT) {
			correct++;
		}else {
			incorrect++;
		}
	}

	public int getCorrectValue() {
		return correct;
	}

	public int getIncorrectValue() {
		return incorrect;
	}
}
