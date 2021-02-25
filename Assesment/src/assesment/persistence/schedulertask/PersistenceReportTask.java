package assesment.persistence.schedulertask;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.LinkedList;

import assesment.communication.administration.property.PropertyData;
import assesment.communication.exception.InvalidDataException;
import assesment.communication.user.UserData;

/**
 * Consultas a la BD sin pasar por el login de los EJB de la aplicacion.
 * Se utiliza unicamente para las "Alertas" que no se loguean.
 * 
 * @author gbenaderet
 *
 */
public class PersistenceReportTask implements Serializable{

	/**
	 * 
	 */
	public PersistenceReportTask() {
		super();
	}
	
	
	
	/**
	 * Devuelve una lista de Usuarios de Abitab que no pagaron aún.
	 * Esto se indica como extradata != UserData.ELEARNING_VALID_USER para aquellos usuarios
	 * creados for el sistema de Abitab: abitab_<ceduladeidentidad>_numero
	 * 
	 * @return
	 * @throws Exception
	 */
	public LinkedList<UserData> notPayUsers() throws Exception {
		LinkedList data = new LinkedList<UserData>();
		try{
			
			// UserData.ELEARNING_VALID_USER = 2
			int assesmentAbitab = 30;
			

            ConnectionTask conn = new ConnectionTask();
			conn.conect();
            
            ResultSet result = conn.executeQuery(
            		"SELECT "+
            	    " u.loginName as loginName, password, firstName, lastName, brithDate, sex, "+
            	    " email, country, nationality, startDate, licenseExpiry, "+
            	    " vehicle, location, language, role, expiry, extradata  " +            	    
            		" FROM users u " +
            		" INNER JOIN userassesments ua on ua.loginname = u.loginname " +
            		" WHERE  "+
            		// "  u.extradata != '2' " +
            		"  ua.assesment = " + assesmentAbitab + 
            		"  and ua.elearningInstance is not null " +
            		"  and ua.elearningenabled = false " +
            		"  and lower(u.loginName) LIKE lower('abitab_%_%') " 
            		);
            if (result != null){
	            while(result.next()){
	            	UserData user = new UserData(result.getString("loginName"),
	            			result.getString("password"), 
	            			result.getString("firstName"), 
	            			result.getString("lastName"), 
	            			result.getString("language"), 
	            			result.getString("email"), 
	            			result.getDate("brithDate"), 
	            			result.getInt("sex"), 
	            			result.getInt("country"), 
	            			result.getInt("nationality"), 
	            			result.getDate("startDate"), 
	            			result.getDate("licenseExpiry"), 
	            			result.getString("vehicle"), 
	            			result.getInt("location"), 
	            			result.getString("role"), 
	            			result.getDate("expiry")
	            			); 
	            	user.setExtraData(result.getString("extradata"));
	            	data.add(user);
	            }
            }
            conn.disconnect();		    
		    
		}catch(Exception e){
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * @param propertyPK
	 * @param userRequest
	 * @return
	 * @throws Exception
	 */
	public PropertyData findPropertyByPrimaryKey(String propertyPK) throws Exception {
		
		PropertyData data=null;
		try {
			ConnectionTask conn = new ConnectionTask();
			conn.conect();
			
			ResultSet result = conn.executeQuery("SELECT name, value FROM properties " +
		    		"WHERE lower(name) = lower('"+propertyPK+"')"
            		);
            if(result != null && result.next()){
            	data = new PropertyData(result.getString("name"),
            				result.getString("value"));            	
            }
            conn.disconnect();
		    
		} catch (Exception e) {
            e.printStackTrace();
        }
		return data;
	}

	
}
