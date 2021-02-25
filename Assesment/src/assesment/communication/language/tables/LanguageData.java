/*
 * Created on 31-oct-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.communication.language.tables;

import java.io.Serializable;

/**
 * @author fcarriquiry
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LanguageData implements Serializable{

    private static final long serialVersionUID = 1L;

    private String name;
	
	/**
	 * Constructor method 
	 */
	public LanguageData(String name) {
		this.name=name;
	}
	
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	
}
