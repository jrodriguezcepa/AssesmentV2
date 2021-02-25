/*
 * Created on 18-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.translator.web.util;

import org.apache.struts.action.ActionForm;

/**
 * @author fcarriquiry
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class OptionItem extends ActionForm implements Comparable {

    private static final long serialVersionUID = 1L;

    private String label;
	private String value;

	/**
	 * @return Returns the value.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            The value to set.
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return Returns the label.
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label
	 *            The label to set.
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 *  
	 */
	public OptionItem(String label, String value) {

		// TODO Auto-generated constructor stub

		this.label = label;
		this.value = value;
	}

    public int compareTo(Object arg0) {
        if(value.length() == 0) {
            return -1;
        }
        if(value.equals("0")) {
            return -1;
        }
        return label.compareTo(((OptionItem)arg0).label);
    }

}