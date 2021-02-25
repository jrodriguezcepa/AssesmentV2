package assesment.persistence.administration.property;


import javax.ejb.SessionBean;

import org.hibernate.classic.Session;

import assesment.communication.administration.property.PropertyData;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.exception.DeslogedException;
import assesment.communication.exception.InvalidDataException;
import assesment.persistence.administration.property.tables.Property;
import assesment.persistence.hibernate.HibernateAccess;
import assesment.persistence.util.ExceptionHandler;

/**
 * @ejb.bean name="PropertyABM" jndi-name="ejb/PropertyABM" type="Stateless"
 *           description="Description for PropertyABM" view-type="remote"
 *           display-name="Name for PropertyABM"
 *           
 *  
 */
public abstract class PropertyABMBean implements SessionBean {

    private ExceptionHandler handler = new ExceptionHandler(PropertyABMBean.class);
    
    /**
     * Init transaction
     * @ejb.create-method
     * @ejb.permission role-name = "administrator,systemaccess,accesscode"
     */
    public void ejbCreate() throws javax.ejb.CreateException { }

	
	/**
	 * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess,resetpassword,accesscode"
	 */
	public void propertyUpdate(PropertyData data, UserSessionData userSessionData) throws Exception {
		try {
			if (userSessionData == null) {
				throw new DeslogedException("userUpdate","session = null");
			}
			if (data == null) {
				throw new InvalidDataException("userUpdate","data = null");
			}			
			Session session = HibernateAccess.currentSession();
            
			Property property = (Property)session.load(Property.class,data.getName());
			property.setValue(data.getValue());

            session.update(property);
		} catch (Exception e) {
			e.printStackTrace();
            handler.getException(e, "propertyUpdate", userSessionData.getFilter().getLoginName());
        }
	}
}
