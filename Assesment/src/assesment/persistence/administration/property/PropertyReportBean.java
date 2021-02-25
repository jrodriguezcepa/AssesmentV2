package assesment.persistence.administration.property;


import javax.ejb.CreateException;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import assesment.communication.administration.property.PropertyData;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.exception.DeslogedException;
import assesment.communication.exception.InvalidDataException;
import assesment.persistence.administration.property.tables.Property;
import assesment.persistence.hibernate.HibernateAccess;
import assesment.persistence.util.ExceptionHandler;

/**
 * 
 * @ejb.bean name="PropertyReport" jndi-name="ejb/PropertyReport" type="Stateless"
 *           transaction-type="Container"
 * 
 * @generated
 */
public abstract class PropertyReportBean implements javax.ejb.SessionBean {
	
    private ExceptionHandler handler = new ExceptionHandler(PropertyReportBean.class);
    
    /**
     * @ejb.create-method 
     * @ejb.permission role-name = "systemaccess,administrator,resetpassword,accesscode"
     * @throws CreateException
     */
    public void ejbCreate() throws CreateException { }
    
    /**
	 * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator,resetpassword,accesscode"
	 * @param propertyPK
	 * @param userRequest
	 * @return
	 * @throws Exception
	 */
	public PropertyData findPropertyByPrimaryKey(String propertyPK, UserSessionData userSessionData) throws Exception {
		
		PropertyData data=null;
		try {
			if(propertyPK==null){
				throw new InvalidDataException("findPropertyByPrimaryKey","propertyPK = null");
			}
			if (userSessionData == null) {
				throw new DeslogedException("findPropertyByPrimaryKey","userRequest = null");
			}
		    
            Session session = HibernateAccess.currentSession();
		    Query query = session.createQuery("SELECT property FROM Property property WHERE lower(property.name) = lower(:nick)");
		    query.setParameter("nick", propertyPK);
		    Property user = (Property) query.uniqueResult();
		    if(user != null) {
		        data=user.getPropertyData();
		    }
		} catch (Exception e) {
            handler.getException(e, "findPropertyByPrimaryKey", userSessionData.getFilter().getLoginName());
        }
		return data;
	}

}