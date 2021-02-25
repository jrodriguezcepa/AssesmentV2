/**
 * CEPA
 * Assesment
 */
package assesment.communication.exception;


public class InvalidDeleteException extends AssesmentException {

    public InvalidDeleteException(String assesmentMessage, String systemMessage) {
        super(assesmentMessage, systemMessage);
    }

    private static final long serialVersionUID = 1L;

    public String getName() {
        return "InvalidDeleteException";
    }

}
