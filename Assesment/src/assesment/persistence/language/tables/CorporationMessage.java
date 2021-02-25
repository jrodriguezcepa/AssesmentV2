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
public class CorporationMessage {
	
	private String text;
	
	private CorporationMessagePK primaryKey;
	/**
	 * 
	 */
	public CorporationMessage() {
	 	
		// TODO Auto-generated constructor stub
	}
	
	public CorporationMessagePK getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(CorporationMessagePK primaryKey) {
		this.primaryKey = primaryKey;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
