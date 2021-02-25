/**
 * Created on 25-sep-2007
 * CEPA
 * DataCenter 5
 */
package assesment.persistence.administration.tables;

import org.hibernate.classic.Session;

import assesment.communication.administration.SpecificAnswerData;
import assesment.persistence.hibernate.HibernateAccess;
import assesment.persistence.question.tables.Answer;
import assesment.persistence.question.tables.Question;

public class UserSpecificAnswer {

    private Integer id;
    
    private UserMultiAssessment multianswer;
    
    private Question question;
    private Answer answer;
    
    public UserSpecificAnswer() {
    }

    public UserSpecificAnswer(Integer questionId, Integer answerId, Integer multiId) {
        Session session = HibernateAccess.currentSession();
        multianswer = (UserMultiAssessment) session.load(UserMultiAssessment.class, multiId);
        question = (Question) session.load(Question.class,questionId);
        answer = (Answer) session.load(Answer.class,answerId);
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserMultiAssessment getMultianswer() {
		return multianswer;
	}

	public void setMultianswer(UserMultiAssessment multianswer) {
		this.multianswer = multianswer;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

	public SpecificAnswerData getData() {
		return new SpecificAnswerData(id, question.getId(), answer.getId());
	}


}
