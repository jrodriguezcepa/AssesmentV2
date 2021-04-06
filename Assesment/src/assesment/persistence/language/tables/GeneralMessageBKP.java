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
	
	private GeneralMessageBKPPK primaryKey;
	
	public GeneralMessageBKP(){
		
	}

	public GeneralMessageBKP(GeneralMessage gm){
		this.text=gm.getText();
		this.assessment=gm.getAssessment();
		GeneralMessageBKPPK pk=new GeneralMessageBKPPK();
		pk.setLabelKey(gm.getPrimaryKey().getLabelKey());
		pk.setLanguage(gm.getPrimaryKey().getLanguage());
		this.primaryKey= pk;
		
	}

    public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}


	public Integer getAssessment() {
		return assessment;
	}

	public void setAssessment(Integer assessment) {
		this.assessment = assessment;
	}

	public GeneralMessageBKPPK getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(GeneralMessageBKPPK primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String toString() {
		return primaryKey.getLabelKey()+", "+primaryKey.getLanguage().getName()+", "+text;
	}
}
