/*
 * Created on 14-oct-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.persistence.language.tables;

import java.io.Serializable;

import assesment.persistence.corporation.tables.Corporation;

/**
 * @author fcarriquiry
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CorporationMessagePK implements Serializable {
	
    private static final long serialVersionUID = 1L;

    private String labelKey;
	private Corporation corporation;
	private Language language;
	
	/**
	 * 
	 */
	public CorporationMessagePK() {

		// TODO Auto-generated constructor stub
	}	
	
	public Corporation getCorporation() {
		return corporation;
	}
	public void setCorporation(Corporation corporation) {
		this.corporation = corporation;
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
}
