/**
 * CEPA
 * Assesment
 */
package assesment.persistence.util;


/**
 * @author jrodriguez
 */
public class PersistenceUtil {

    /**
     * 
     */
    public PersistenceUtil() {
        super();
    }

    public static boolean empty(String string) {
        return (string == null || string.trim().length() == 0);
    }
}