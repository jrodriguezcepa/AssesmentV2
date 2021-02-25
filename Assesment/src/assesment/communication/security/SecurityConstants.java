/**
 * CEPA
 * Assesment
 */
package assesment.communication.security;

import java.io.File;
import java.io.Serializable;


/**
 * @author jrodriguez
 */
public class SecurityConstants implements Serializable {

    public static final String ACCESS_TO_SYSTEM = "systemaccess";

    public static final String MULTI_ASSESSMENT = "multiassessment";

    public static final String GROUP_ASSESSMENT = "groupassessment";

    public static final String ADMINISTRATOR = "administrator";
    
    public static final String ACCESSCODE = "accesscode";

    public static final String PEPSICO_CANDIDATOS = "pepsico_candidatos";

    public static final String BASF = "basf_assessment";

    public static final String CLIENT_REPORTER = "clientreporter";

    public static final String CLIENTGROUP_REPORTER = "clientgroupreporter";

    public static final String CEPA_REPORTER = "cepareporter";

    public static final String WEBINAR = "webinar";
    public static final String WEBINAR_FORM = "webinarform";
    
    public static final String ALRIYADAH_INITIALA = "alriyadahi";
    public static final String ALRIYADAH_INITIALB = "alriyadahb";
    public static final String ALRIYADAH_FINAL = "alriyadahf";
    
    public static final String SERVER_PRODUCTION_IP = "172.31.23.142";

    public static final String LINUX = "Linux";
	public static final String WINDOWS = "Windows";

    public static final String FILES_WAR_DIRECTORY = (SecurityConstants.isWindowsOS()) ?
        	"C:/flash/flash.war/userfiles/"://"C:/jboss51/datacenter/images.war/":
        	"/home/flash/flash.war/userfiles/";

    public static boolean isProductionServer() {
		try{
			/*InetAddress inetAddress = InetAddress.getLocalHost();
			String ip = inetAddress.getHostAddress();
			System.out.println("Server IP: " + ip);
			if (ip.equalsIgnoreCase(SERVER_PRODUCTION_IP)){
				return true;
			}else{
				return false;
			}*/
			return new File("/home/ubuntu/").exists() && !new File("/home/cepadc5/testing").exists();
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
    

	/**
	 * Verifica que sistema operativo se esta utilizando.
	 * Devuelve true si es Windows y false en caso contrario (Linux).
	 * @return
	 */
	public static boolean isWindowsOS(){
		String sSistemaOperativo = System.getProperty("os.name");
		if (sSistemaOperativo.contains(WINDOWS)){
			return true;
		}
		return false;
	}
}
