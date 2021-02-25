/**
 * CEPA
 * Assesment
 */
package assesment.communication.exception;

import javax.ejb.EJBException;

public abstract class AssesmentException extends EJBException {

    private static final long serialVersionUID = 1L;
    
    private String codeMessage;
    private String systemMessage;

    public AssesmentException(String codeMessage, String systemMessage) {
        this.codeMessage = codeMessage;
        this.systemMessage = systemMessage;
    }
    
    public abstract String getName();
    
    public String getKey() {
        return codeMessage;
    }
    
    public String getSystemMessage() {
        return systemMessage;
    }
}
