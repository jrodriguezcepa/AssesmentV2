/**
 * Created on 08-ene-2009
 * CEPA
 * DataCenter 5
 */
package assesment.persistence.tag.tables;

import org.hibernate.Session;

import assesment.communication.tag.TagData;

public class AssesmentTag {

    private AssesmentTagPK pk;
    private Integer min;
    
    public AssesmentTag() {
    }

    public AssesmentTag(Integer assesmentId,Integer tagId,Integer min,Session session) {
        pk = new AssesmentTagPK(assesmentId,tagId,session);
        this.min = min;
    }

    public Integer getMin() {
        return min;
    }

    public AssesmentTagPK getPk() {
        return pk;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public void setPk(AssesmentTagPK pk) {
        this.pk = pk;
    }

    public TagData getData() {
        return new TagData(pk.getTag().getId(),pk.getTag().getLessonEs(),pk.getTag().getLessonEn(),pk.getTag().getLessonPt(),min);
    }
    
    
}
