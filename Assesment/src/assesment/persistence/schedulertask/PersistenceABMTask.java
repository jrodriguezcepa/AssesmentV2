package assesment.persistence.schedulertask;

import java.io.Serializable;

import org.hibernate.classic.Session;

import assesment.communication.administration.property.PropertyData;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.exception.DeslogedException;
import assesment.communication.exception.InvalidDataException;
import assesment.persistence.administration.property.tables.Property;
import assesment.persistence.hibernate.HibernateAccess;

/**
 * ABMs a la BD sin pasar por el login de los EJB de la aplicacion.
 * Se utiliza unicamente para las "Alertas" que no se loguean.
 * 
 * @author gbenaderet
 *
 */
public class PersistenceABMTask implements Serializable{

	public PersistenceABMTask() {
		super();
	}
	
	/**
	 * 
	 * @param data
	 * @param corporationId
	 * @param userSessionData
	 * @param validate
	 * @throws Exception
	 * 
	 */
//	public boolean userUpdateExtraData(String userLogin, String extraData) throws Exception {
//		
//		boolean updated = false;
//		try {
//			
//			ConnectionTask conn = new ConnectionTask();
//			conn.conect();
//			
//			// Solo actualizo el extra data
//			int update = conn.executeUpdate("UPDATE users " +
//					"SET extradata = '"+extraData+"' where loginname='"+userLogin+"' ");						
//           
//			if (update == 1){
//				updated = true;
//			}else{
//            	System.out.println("PersistenceABMTask.java: No se actualizo el extra data de: "+
//            			userLogin+"\n"+"No existe ese usuario en el sistema.");
//            	updated = false;
//            }
//			conn.disconnect();
//			
//		} catch (Exception e) {
//            e.printStackTrace();
//        }
//		return updated;
//	}
	
	/**
	 * 
	 * @param data
	 * @param corporationId
	 * @param userSessionData
	 * @param validate
	 * @throws Exception
	 * 
	 */
	public boolean userUpdateElearningEnabled(String cedula, boolean enabled) throws Exception {
		
		boolean updated = false;
		try {
			
			ConnectionTask conn = new ConnectionTask();
			conn.conect();
			
			// Solo actualizo el extra data
			int update = conn.executeUpdate("UPDATE userassesments " +
					"SET elearningenabled = "+enabled+" where loginname like 'abitab_"+cedula+"_%' ");						
           
			if (update == 1){
				updated = true;
			}else{
            	System.out.println("PersistenceABMTask.java: No se actualizo el elearning enabled de la persona" +
            			"con cedula: "+	cedula+"\n"+"No existe ese usuario en el sistema.");
            	updated = false;
            }
			conn.disconnect();
			
		} catch (Exception e) {
            e.printStackTrace();
        }
		return updated;
	}
	
	
	/**
	 * Actualiza una propiedad, en particular el lote de abitab. 
	 * Este metodo solo se debe llamar desde las alertas porque no utilizan EJB
	 * @param data
	 * @param userSessionData
	 * @throws Exception
	 * @return Devuelve true si actualizo correctamente y false en caso contrario.
	 */
	public boolean propertyUpdate(PropertyData data) throws Exception {
		boolean updated = false;
		try {

			ConnectionTask conn = new ConnectionTask();
			conn.conect();
			
			// Solo actualizo el value
			int update = conn.executeUpdate("UPDATE properties " +
					"SET value = '"+data.getValue()+"' where name = '"+data.getName()+"' ");						
           
			if (update == 1){
				updated = true;
			}else{
            	System.out.println("PersistenceABMTask.java, propertyUpdate: No se pudo actualizar la propiedad "+data.getName());
            	updated = false;
            }
			conn.disconnect();

            
		} catch (Exception e) {
			e.printStackTrace();           
        }
		return updated;
	}
	

	
}
