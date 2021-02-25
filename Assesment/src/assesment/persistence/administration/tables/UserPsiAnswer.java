/**
 * Created on 25-sep-2007
 * CEPA
 * DataCenter 5
 */
package assesment.persistence.administration.tables;

import java.util.Date;

import org.hibernate.classic.Session;

import assesment.communication.administration.UserAnswerData;
import assesment.persistence.hibernate.HibernateAccess;
import assesment.persistence.question.tables.Answer;
import assesment.persistence.question.tables.PsiAnswer;
import assesment.persistence.question.tables.PsiQuestion;
import assesment.persistence.question.tables.Question;

public class UserPsiAnswer {

    private Integer id;
    private PsiQuestion psiquestion;
    private PsiAnswer psianswer;
    
    public UserPsiAnswer() {
    }

    public UserPsiAnswer(UserAnswerData userAnswer) {
        Session session = HibernateAccess.currentSession();
        id = userAnswer.getId();
        psiquestion = (PsiQuestion) session.load(PsiQuestion.class,userAnswer.getQuestion());
        if(userAnswer.getAnswer() != null) {
            psianswer = (PsiAnswer) session.load(PsiAnswer.class,userAnswer.getAnswer());
        }
    }

    public Integer getId() {
        return id;
    }

    public PsiQuestion getPsiquestion() {
        return psiquestion;
    }

    public void setPsianswer(PsiAnswer psianswer) {
        this.psianswer = psianswer;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPsiquestion(PsiQuestion psiquestion) {
        this.psiquestion = psiquestion;
    }

    public PsiAnswer getPsianswer() {
        return psianswer;
    }

	public void updatePsiData(UserAnswerData answer) {
		Session session = HibernateAccess.currentSession();
        psianswer = (PsiAnswer) session.load(PsiAnswer.class,answer.getAnswer());
	}

}
