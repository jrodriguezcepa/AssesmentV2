/**
 * CEPA
 * Assesment
 */
package assesment.business.util;

import assesment.communication.exception.AssesmentException;
import assesment.communication.exception.DCException;

public class ExceptionHandler {

    private String className;
    
    public ExceptionHandler(Class bussinessClass) {
        className = bussinessClass.getName();
    }

    public void handleException(String message,Exception e) throws Exception {
        Throwable thw = e;
        while (thw.getCause() != null) {
            thw = thw.getCause();
        }
        if(thw instanceof AssesmentException) {
            throw new Exception(thw);
        }else {
            throw new DCException(message,thw.getMessage());
        }
    }

}
