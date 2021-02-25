package assesment.communication.exception;


public class DatabaseException extends AssesmentException {

    private static final long serialVersionUID = 1L;
    
    
	public DatabaseException(String assesmentMessage, String systemMessage) {
        super(assesmentMessage,systemMessage);
    }

    public String getName() {
        return "DatabaseException";
    }
    
    
}
