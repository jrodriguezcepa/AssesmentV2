/**
 * Created on 08-ene-2009
 * CEPA
 * DataCenter 5
 */
package assesment.persistence.tag.tables;

import org.hibernate.Session;

import assesment.communication.tag.AnswerTagData;

public class AnswerTag {

    private AnswerTagPK pk;
    private Integer value;
    
    public AnswerTag() {
    }

    public AnswerTag(Integer answerId,Integer tagId,Integer value,Session session) {
        pk = new AnswerTagPK(answerId,tagId,session);
        this.value = value;
    }

    public AnswerTagPK getPk() {
        return pk;
    }

    public Integer getValue() {
        return value;
    }

    public void setPk(AnswerTagPK pk) {
        this.pk = pk;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public AnswerTagData getData() {
        return new AnswerTagData(pk.getAnswer().getId(),pk.getAnswer().getQuestion().getId(),pk.getTag().getData(),value);
    }

}
