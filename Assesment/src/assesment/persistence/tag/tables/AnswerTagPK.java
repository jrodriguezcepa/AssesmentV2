/**
 * Created on 08-ene-2009
 * CEPA
 * DataCenter 5
 */
package assesment.persistence.tag.tables;

import java.io.Serializable;

import org.hibernate.Session;

import assesment.persistence.question.tables.Answer;
import assesment.persistence.question.tables.Question;

public class AnswerTagPK implements Serializable {

    private Answer answer;
    private Tag tag;
    
    public AnswerTagPK() {
    }

    public AnswerTagPK(Integer answerId,Integer tagId,Session session) {
        answer = (Answer)session.load(Answer.class,answerId);
        tag = (Tag)session.load(Tag.class,tagId);
    }

    public Answer getAnswer() {
        return answer;
    }

    public Tag getTag() {
        return tag;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

}
