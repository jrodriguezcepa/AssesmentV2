package assesment.communication.exception;

public class DCSecurityException extends CreateBeanException implements
		I18NException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * 
	 * 
	 * 
	 * @param msg
	 */
	public DCSecurityException(String msg) {
		super(msg,null);
	}
	
	public DCSecurityException(String msg, String labelKey){
		super(msg,labelKey);
	}

}
