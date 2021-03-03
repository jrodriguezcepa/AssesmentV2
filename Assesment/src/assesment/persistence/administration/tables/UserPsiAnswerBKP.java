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
import assesment.persistence.question.tables.AnswerBKP;
import assesment.persistence.question.tables.PsiAnswerBKP;
import assesment.persistence.question.tables.PsiQuestionBKP;
import assesment.persistence.question.tables.QuestionBKP;

public class UserPsiAnswerBKP {

    private Integer id;
    private PsiQuestionBKP psiquestion;
    private PsiAnswerBKP psianswer;
    
    public UserPsiAnswerBKP() {
    }


    public Integer getId() {
        return id;
    }

    public PsiQuestionBKP getPsiquestion() {
        return psiquestion;
    }

    public void setPsianswer(PsiAnswerBKP psianswer) {
        this.psianswer = psianswer;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPsiquestion(PsiQuestionBKP psiquestion) {
        this.psiquestion = psiquestion;
    }

    public PsiAnswerBKP getPsianswer() {
        return psianswer;
    }


}
