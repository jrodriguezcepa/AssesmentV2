/**
 * CEPA
 * Assesment
 */
package assesment.communication.util;

/**
 * @author jrodriguez
 */
public interface LogActions {

    public static final int CREATE = 0;
    public static final int UPDATE = 1;
    public static final int DELETE = 2;
    public static final int EXCLUDE = 3;
    public static final int INCLUDE = 4;
    
    // Log Actions for Fleets:
    public static final int ADDDIVISION = 5;
    public static final int ADDVEHICLE = 6;
    public static final int DELETEVEHICLE = 7;
    public static final int DELETEDIVISION = 8;
    public static final int EXCLUDEDIVISION = 9;
    public static final int EXCLUDEVEHICLE = 10;
    public static final int INCLUDEDIVISION = 11;
    public static final int INCLUDEVEHICLE = 12;
    public static final int BLOCKDATAENTRY = 13; // Block Data Entry for a Fleet
    
    // Log Actions for Activity Commentary Drive
    public static final int ADDCOMMENTARYDRIVE = 14;
    public static final int UPDATECOMMENTARYDRIVE = 15;
    public static final int DELETECOMMENTARYDRIVE = 16;
}
