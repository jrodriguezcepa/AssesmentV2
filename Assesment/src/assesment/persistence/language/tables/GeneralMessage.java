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
public class GeneralMessage {
	
    public static final String SPANISH = "es";
    public static final String ENGLISH = "en";
    public static final String PORTUGUESE = "pt";

    private String text;
	private Integer assessment; 
	
	private GeneralMessagePK primaryKey;
	
	public GeneralMessage(){
		
	}
	
	public GeneralMessage(String key, String language, String text) {
        primaryKey = new GeneralMessagePK(key,language);
        this.text = text;
    }

    public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public GeneralMessagePK getPrimaryKey() {
		return primaryKey;
	}
	
	public void setPrimaryKey(GeneralMessagePK primaryKey) {
		this.primaryKey = primaryKey;
	}

	public Integer getAssessment() {
		return assessment;
	}

	public void setAssessment(Integer assessment) {
		this.assessment = assessment;
	}
}
