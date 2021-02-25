/*
 * Created on 19-oct-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.communication.exception;

import javax.ejb.CreateException;

/**
 * @author fcarriquiry
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CreateBeanException extends CreateException implements
		I18NException {
	
    private static final long serialVersionUID = 1L;

    private String labelKey;
	private String msg;

	/**
	 * 
	 */
	public CreateBeanException(String msg, String labelKey) {
		// TODO Auto-generated constructor stub
		this.msg=msg;
		this.labelKey=labelKey;
	}
	
	/* (non-Javadoc)
	 * @see communication.exception.I18NException#getLabelKey()
	 */
	public String getLabelKey() {
		// TODO Auto-generated method stub
		return this.labelKey;
	}

	/* (non-Javadoc)
	 * @see communication.exception.I18NException#setLabelKey(java.lang.String)
	 */
	public void setLabelKey(String labelKey) {
		// TODO Auto-generated method stub
		this.labelKey=labelKey;
	}

	/* (non-Javadoc)
	 * @see communication.exception.I18NException#getMsg()
	 */
	public String getMsg() {
		// TODO Auto-generated method stub
		return this.msg;
	}

	/* (non-Javadoc)
	 * @see communication.exception.I18NException#setMsg(java.lang.String)
	 */
	public void setMsg(String msg) {
		// TODO Auto-generated method stub
		this.msg=msg;
	}

	
}
