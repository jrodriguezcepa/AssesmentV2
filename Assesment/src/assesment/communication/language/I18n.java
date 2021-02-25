/*
 * Created on 10-nov-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.communication.language;

import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * @author jrodriguez
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class I18n extends ResourceBundle {

	/**
	 * 
	 */
	public I18n() {
		super();
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.util.ResourceBundle#handleGetObject(java.lang.String)
	 */
	protected Object handleGetObject(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see java.util.ResourceBundle#getKeys()
	 */
	public Enumeration getKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String args[]) {
		I18n messages = new I18n();
		messages.getString("ss");
	}
}
