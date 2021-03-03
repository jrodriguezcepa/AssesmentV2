/*
 * Created on 13-oct-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.persistence.language.tables;

/**
 * @author fcarriquiry
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GeneralMessageBKP {
	
    public static final String SPANISH = "es";
    public static final String ENGLISH = "en";
    public static final String PORTUGUESE = "pt";

    private String text;
	private Integer assessment; 
	
    private String labelKey;
	private Language language;	
	public GeneralMessageBKP(){
		
	}


    public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

    public String getLabelKey() {
		return labelKey;
	}
	public void setLabelKey(String labelKey) {
		this.labelKey = labelKey;
	}

	public Language getLanguage() {
		return language;
	}
	public void setLanguage(Language language) {
		this.language = language;
	}
		

	public Integer getAssessment() {
		return assessment;
	}

	public void setAssessment(Integer assessment) {
		this.assessment = assessment;
	}
}
