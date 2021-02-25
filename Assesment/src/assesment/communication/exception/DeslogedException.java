/*
 * Created on 22-abr-2005
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
public class DeslogedException extends AssesmentException {

    private static final long serialVersionUID = 1L;

    /**
	 * @param msg
	 */
	public DeslogedException(String msg) {
		super(msg,null);
	}
	
	public DeslogedException(String msg,String labelKey){
		super(msg,labelKey);
	}

    public String getName() {
        return "DeslogedException";
    }
}