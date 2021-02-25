/**
 * 
 */
package assesment.presentation.translator.web.util;

import org.apache.struts.action.ActionForm;

/**
 * @author gbenaderet
 *
 */
public class LongItem extends ActionForm implements Comparable {

	private static final long serialVersionUID = 1L;

    private Long label;
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
	public Long getLabel() {
		return label;
	}

	/**
	 * @param label
	 *            The label to set.
	 */
	public void setLabel(Long label) {
		this.label = label;
	}

	/**
	 *  
	 */
	public LongItem(Long label, String value) {
		this.label = label;
		this.value = value;
	}

    public int compareTo(Object arg0) {
        if(value.length() == 0) {
            return -1;
        }
        return label.compareTo(((LongItem)arg0).label);
    }

}
