/*
 * Created on 17-oct-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.communication.exception;

/**
 * @author fcarriquiry
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class InInitializerException extends ExceptionInInitializerError implements I18NException {

    private static final long serialVersionUID = 1L;

    private String msg;
	private String labelKey;

    /**
	 * @deprecated
	 * @param msg
	 */
	public InInitializerException(String msg) {
		this.msg = msg;
	}
	
	public InInitializerException(String msg,String labelKey){
		this.labelKey=labelKey;
		this.msg=msg; 
	}

	public InInitializerException() {

		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 * 
	 * @deprecated
	 * 
	 * @return String
	 * 
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
