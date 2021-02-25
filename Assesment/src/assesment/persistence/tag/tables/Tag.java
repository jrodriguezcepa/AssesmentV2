/**
 * Created on 08-ene-2009
 * CEPA
 * DataCenter 5
 */
package assesment.persistence.tag.tables;

import assesment.communication.tag.TagData;

public class Tag {

    private Integer id; 
    private Integer lessonEs;
    private Integer lessonEn;
    private Integer lessonPt;
    
    public Tag() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    
    public Integer getLessonEn() {
        return lessonEn;
    }

    public Integer getLessonEs() {
        return lessonEs;
    }

    public Integer getLessonPt() {
        return lessonPt;
    }

    public void setLessonEn(Integer lessonEn) {
        this.lessonEn = lessonEn;
    }

    public void setLessonEs(Integer lessonEs) {
        this.lessonEs = lessonEs;
    }

    public void setLessonPt(Integer lessonPt) {
        this.lessonPt = lessonPt;
    }

    public TagData getData() {
        return new TagData(id,lessonEs,lessonEn,lessonPt);
    }

    
}
