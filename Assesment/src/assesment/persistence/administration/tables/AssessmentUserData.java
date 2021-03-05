package assesment.persistence.administration.tables;

import java.util.Date;
import java.util.HashMap;

import assesment.communication.language.Text;
import assesment.communication.question.QuestionData;
import assesment.communication.util.CountryConstants;
import assesment.persistence.util.Util;
import jxl.write.Label;


public class AssessmentUserData implements Comparable<AssessmentUserData> {

	private String firstname;
	private String lastname;
	private String loginname;
	private String email;
	private Integer assesment;
	private boolean finished;
	private boolean psi;
	private Integer id;
	private boolean sended;
	private boolean certificate;

	private Integer answers;
	private Integer correct;
	private Date ended;
	
	private String extraData3;
	
	private HashMap<Integer, Object> questions = new HashMap<Integer, Object>();
	
	public AssessmentUserData(Object[] data) {
        firstname = (String) data[0];
        lastname = (String) data[1];
        loginname = (String) data[2];
        email = (String) data[3];
        if(data.length>10)
        	assesment = (Integer) data[10];

        if(data[4] != null)
        	extraData3 = ((String) data[4]).toUpperCase();
        answers = 0;
        correct = 0;
        if(data.length == 6) {
	        ended = (Date) data[5];
        }else {
	        finished = ((Boolean) data[5]).booleanValue();
	        psi = ((Boolean) data[6]).booleanValue();
	        id = (Integer) data[7];
	        sended = (data[8] == null) ? false : ((Boolean) data[8]).booleanValue();
	        certificate = (data[9] == null) ? false : ((Boolean) data[9]).booleanValue();
	        correct = (data[11] == null) ? 0 : ((Integer) data[11]).intValue();
	        answers = (data[12] == null) ? correct : ((Integer) data[12]).intValue() + correct;
        }
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getLoginname() {
		return loginname;
	}

	public String getEmail() {
		return email;
	}

	public boolean isFinished() {
		return finished;
	}

	public boolean isPsi() {
		return psi;
	}

	public Integer getId() {
		return id;
	}
	
	public Integer getCorrect() {
		return correct;
	}
	public Integer getAssesment() {
		return assesment;
	}
	
	public void setAssesment(Integer assesment) {
		this.assesment= assesment;
	}

	public boolean isSended() {
		return sended;
	}

	public boolean isCertificate() {
		return certificate;
	}

	public void setAnswers(Integer answers) {
		this.answers = answers;
	}

	public void setCorrect(Integer correct) {
		this.correct = correct;
	}
	
	public Integer getQuestionCount() {
		return questions.size();
	}


	@Override
	public int compareTo(AssessmentUserData o) {
		if(finished && !o.finished) {
			return 1;
		}
		if(!finished && o.finished) {
			return -1;
		}
		if(answers == 0 && o.answers > 0) {
			return -1;
		}
		if(answers > 0 && o.answers == 0) {
			return 1;
		}
		if(extraData3 != null && o.extraData3 != null) {
			if(!extraData3.equals(o.extraData3)) {
				return extraData3.compareTo(o.extraData3);
			}
		}
		return new String(firstname+" "+lastname).compareTo(new String(o.firstname+" "+o.lastname));
	}
	
	public String getStatus(int questions, boolean psiAssessment, Text messages) {
		if(finished) {
			return messages.getText("assesment.status.ended");
		}
		if(answers == 0) {
			return messages.getText("report.users.total.notstarted");
		}
		String v = String.valueOf(answers * 100 / questions)+"%";
		if(psiAssessment)
			v += (psi) ? " (PSI compelto)" : "(PSI pendiente)";
		return v;
	}
	
	public String getWebinarStatus(Text messages) {
		if(!finished) {
			return messages.getText("assesment.status.pending");
		}
		if(answers == 0) {
			return messages.getText("report.users.total.notstarted");
		}
		return String.valueOf(correct * 100 / answers)+"%";
	}

	public boolean isNotStarted() {
		return answers == 0;
	}
	
	public int getResult(int testQuestions) {
		return (correct == null || testQuestions == 0) ? 0 : correct * 100 / testQuestions;
	}

	public void addQuestionValues(Object[] data, CountryConstants cc, Text messages) {
		Integer question = (Integer)data[1];
		if(!questions.containsKey(question)) {
			Object obj = null;
			if(((Boolean)data[7]).booleanValue()) {
				obj = messages.getText("generic.messages.never");
			}else {
				if(data[2] != null) {
					obj = data[2];
				} else if(data[3] != null) {
					obj = data[3];
				} else if(data[4] != null) {
					if(((Integer)data[5]) == 0) {
						obj = data[4]+" kms";
					}else {
						obj = data[4]+" millas";
					}
				} else if(data[6] != null) {
					obj = cc.getCc().get(String.valueOf(data[6]));
				}  else if(data[8] != null) {
					obj = new Object[] {messages.getText((String)data[8]), data[9]};
				}
			}
			questions.put(question, obj);
		}else {
			if(data[8] != null) {
				Object[] v = (Object[])questions.get(question);
				questions.remove(question);
				if(v == null)
					v = new Object[] {messages.getText((String)data[8]), data[9]};
				else
					v[0] = v[0] + ", "+messages.getText((String)data[8]);
				questions.put(question, v);
			}
		}
		
	}

	public String getQuestionAnswer(QuestionData question, Integer reportType, Text messages) {
		String s = "---";
		if(questions.containsKey(question.getId())) {
			Object obj = questions.get(question.getId());
			if(obj != null) {
	        	if(question.getType().intValue() == QuestionData.YOU_TUBE_VIDEO || question.getType().intValue() == QuestionData.VIDEO) {
					s = (Boolean.parseBoolean((String)obj)) ? messages.getText("generic.messages.yes") : messages.getText("generic.messages.no");
	        	} else {
	        		if(obj instanceof Date) {
						s = Util.formatDate((Date)obj);
					}else if(obj instanceof Object[]) {
						Object[] data = (Object[]) obj;
						if(question.getTestType().intValue() == QuestionData.NOT_TEST_QUESTION || reportType.intValue() == 1) {
							s = String.valueOf(data[0]);
						} else {
							s = messages.getText("question.result"+data[1]);
						}
					}else {
						s = String.valueOf(obj);
					}
	        	}
			}
		}
		return s;
	}

	public Date getEndDate() {
		return ended;
	}

	public String getExtraData3() {
		return extraData3;
	}

}