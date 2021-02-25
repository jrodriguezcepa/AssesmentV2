/**
 * Created on 08-ene-2009
 * CEPA
 * DataCenter 5
 */
package assesment.communication.tag;

import assesment.communication.language.Text;

public class TagData implements Comparable {

    private Integer id;
    private Integer lessonEs;
    private Integer lessonEn;
    private Integer lessonPt;
    private Integer min;
    
    private String text;
    
    public static final Integer DEFAULT_MIN = 10;

    public TagData() {
    }

    /**
     * @param id
     * @param lesson
     */
    public TagData(Integer id, Integer lessonEs, Integer lessonEn, Integer lessonPt) {
        this.id = id;
        this.lessonEs = lessonEs;
        this.lessonEn = lessonEn;
        this.lessonPt = lessonPt;
    }


    public TagData(Integer id, Integer lessonEs, Integer lessonEn, Integer lessonPt, Integer min) {
        this.id = id;
        this.lessonEs = lessonEs;
        this.lessonEn = lessonEn;
        this.lessonPt = lessonPt;
        this.min = min;
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

    public void setLessonEn(Integer lessonEn) {
        this.lessonEn = lessonEn;
    }

    public Integer getLessonEs() {
        return lessonEs;
    }

    public void setLessonEs(Integer lessonEs) {
        this.lessonEs = lessonEs;
    }

    public Integer getLessonPt() {
        return lessonPt;
    }

    public void setLessonPt(Integer lessonPt) {
        this.lessonPt = lessonPt;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

	public int compareTo(Object arg0) {
		if(text == null) {
			return -1;
		}
		TagData tag = (TagData)arg0;
		if(tag.text == null) {
			return 1;
		}
		return text.compareTo(tag.text);
	}

	public void setText(Text messages) {
		text = messages.getText("assesment.elearning.lesson"+String.valueOf(id));
	}

	public String getText() {
		return text;
	}

}
