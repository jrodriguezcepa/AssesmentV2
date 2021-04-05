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
    private Integer psiquestion;
    private Integer psianswer;
    
    public UserPsiAnswerBKP() {
    }

    public UserPsiAnswerBKP(Object[] d) {
    	this.id=(Integer)d[0];
    	this.psiquestion=(Integer)d[1];
    	this.psianswer=(Integer)d[2];
    }

    public Integer getId() {
        return id;
    }

    public Integer getPsiquestion() {
        return psiquestion;
    }

    public void setPsianswer(Integer psianswer) {
        this.psianswer = psianswer;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPsiquestion(Integer psiquestion) {
        this.psiquestion = psiquestion;
    }

    public Integer getPsianswer() {
        return psianswer;
    }


}
