/*
 * Created on 21-abr-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.communication.exception;

import javax.ejb.EJBException;

/**
 * 
 * 
 * @author fcarriquiry
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class GenericException extends EJBException implements I18NException{

    private static final long serialVersionUID = 1L;

    private String msg;
	private String labelKey;

    /**
	 * @deprecated
	 * @param msg
	 */
	public GenericException(String msg) {
		this.msg = msg;
	}
	
	public GenericException(String msg,String labelKey){
		this.labelKey=labelKey;
		this.msg=msg; 
	}

	/**
	 * 
	 * 
	 *  
	 */
	public GenericException() {
		// your code here
	}

	/**
	 * 
	 * 
	 * @deprecated
	 * 
	 * @return String
	 */
	public String getMessage() {
		return msg;
	}
	public String getLabelKey() {
		return labelKey;
	}
	public void setLabelKey(String labelKey) {
		this.labelKey = labelKey;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}