/**
 * CEPA
 * Assesment
 */
package assesment.persistence.util;

import org.hibernate.HibernateException;
import org.jboss.logging.Logger;

import assesment.communication.exception.AlreadyExistsException;
import assesment.communication.exception.DCException;
import assesment.communication.exception.DatabaseException;
import assesment.communication.exception.InvalidDataException;

public class ExceptionHandler {

    private Logger logger;
    private LoggerFormatting logFormatting=new LoggerFormatting();

    public ExceptionHandler(Class logClass) {
        logger=Logger.getLogger(logClass);
    }

    public void getException(Exception e,String function,String user) throws Exception {
        if(e instanceof HibernateException) {
            logger.error(logFormatting.formattingText(e,"driverCreate(DriverData, UserSessionData)","HibernateException",user), e);
            throw new DatabaseException("exception."+function,e.getMessage());
        }else if(e instanceof InvalidDataException) {
            throw (InvalidDataException)e;
        }else if(e instanceof AlreadyExistsException) {
            throw (AlreadyExistsException)e;
        }else {
            e.printStackTrace();
            logger.error(logFormatting.formattingText(e,"driverCreate(DriverData, UserSessionData)","Exception",user),e);
            throw new DCException(e);
        }
    }
}
