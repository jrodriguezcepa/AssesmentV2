package assesment.communication.exception;

public class DCException extends AssesmentException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DCException(Exception e){
        super("",e.getMessage());
    }
	
	public DCException(String assesmentMessage,String systemMessage){
		super(assesmentMessage,systemMessage);
	}

    public String getName() {
        return "DCException";
    }
}
