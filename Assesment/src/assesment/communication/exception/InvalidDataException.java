/**
 * Created on 21-abr-2005
 */
package assesment.communication.exception;


/**
 * @author fcarriquiry
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class InvalidDataException extends AssesmentException {

    private static final long serialVersionUID = 1L;

	public InvalidDataException(String msg, String labelKey){
		super(msg,labelKey);
	}

    public String getName() {
        return "InvalidDataException";
    }
}