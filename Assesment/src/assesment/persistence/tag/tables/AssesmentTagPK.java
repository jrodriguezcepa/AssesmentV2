/**
 * Created on 08-ene-2009
 * CEPA
 * DataCenter 5
 */
package assesment.persistence.tag.tables;

import java.io.Serializable;

import org.hibernate.Session;

import assesment.persistence.assesment.tables.Assesment;

public class AssesmentTagPK implements Serializable {

    private Assesment assesment;
    private Tag tag;
    
    public AssesmentTagPK() {
    }

    public AssesmentTagPK(Integer assesmentId,Integer tagId,Session session) {
        assesment = (Assesment)session.load(Assesment.class,assesmentId);
        tag = (Tag)session.load(Tag.class,tagId);
    }

    public Assesment getAssesment() {
        return assesment;
    }

    public Tag getTag() {
        return tag;
    }

    public void setAssesment(Assesment assesment) {
        this.assesment = assesment;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

}
