/*
 * Created on 21-abr-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.communication.exception;

/**
 * 
 * 
 * @author fcarriquiry
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CommunicationProblemException extends GenericException {

    private static final long serialVersionUID = 1L;

    /**
	 * @param msg
	 */
	public CommunicationProblemException(String msg) {
		super(msg,null);
	}
	
	public CommunicationProblemException(String msg,String labelKey){
		super(msg,labelKey);
	}
	
}