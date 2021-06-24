/**
 * Assesment
 */
package assesment.communication.corporation;

import java.util.Collection;

import assesment.communication.assesment.AssesmentData;

public class CediData extends CediAttributes {

    public static final int GRUPO_MODELO = 1;
    public static final int MUTUAL = 2;

    public static final int COVEPA = 514;
    public static final int AUSTRAL = 526;

    public CediData() {
        super();
    }

   public CediData(Integer id, String name, String accessCode,String uen,String drv, String manager,  String managerMail, String loginName, Integer company) {
        super(id, name, accessCode, uen, drv, manager, managerMail, loginName, company);
       
    }

   	public static int getMutualMax(int cedi) {
   		switch(cedi) {
			case COVEPA:
   				return 55;
   			case AUSTRAL:
   				return 100;
   		}
   		return 50;
   	}



}
