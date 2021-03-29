/*
 * Created on 14-oct-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.persistence.language.tables;

import java.io.Serializable;

import org.hibernate.Session;

import assesment.persistence.hibernate.HibernateAccess;

/**
 * @author fcarriquiry
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GeneralMessageBKPPK implements Serializable {

    private static final long serialVersionUID = 1L;

    private String labelKey;
	private Language language;

	
	/**
	 * 
	 */
	public GeneralMessageBKPPK() {
		
	}
	public GeneralMessageBKPPK(String key, String languageKey) {
        labelKey = key;
        Session session = HibernateAccess.currentSession();
        language = (Language)session.load(Language.class,languageKey);
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
