package assesment.business.administration.property;

import assesment.business.util.ExceptionHandler;
import assesment.communication.administration.property.PropertyData;
import assesment.communication.administration.user.UserSessionData;
import assesment.persistence.administration.property.PropertyABM;
import assesment.persistence.administration.property.PropertyABMUtil;

/**
 * @ejb.bean name = "PropertyABMFacade" jndi-name = "ejb/PropertyABMFacade" 
 * 			type="Stateless"  transaction-type = "Container"
 * 
 * @ejb.ejb-ref ejb-name = "PropertyABM" ref-name = "ejb/PropertyABM" view-type =
 *              "remote"
 * 
 * @jboss.ejb-ref-jndi jndi-name = "ejb/PropertyABM" ref-name = "PropertyABM"
 *  
 * @ejb.ejb-ref ejb-name = "PropertyReport" ref-name = "ejb/PropertyReport" view-type =
 *              "remote"
 * 
 * @jboss.ejb-ref-jndi jndi-name = "ejb/PropertyReport" ref-name = "PropertyReport"
 *  
 *  
 * @ejb.util generate="physical"
 * 
 */
public abstract class PropertyABMFacadeBean implements javax.ejb.SessionBean {
   
	private ExceptionHandler handler = new ExceptionHandler(PropertyABMFacadeBean.class);

	/**
	 * Create method
	 * 
	 * @ejb.create-method
     * @ejb.permission role-name = "administrator,systemaccess,accesscode"	 
     */
	public void ejbCreate() {

	}
	
	/**
	 * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess,userupdate,userupdateaccess"
	 * 
	 * @param data
	 * @throws Exception
	 */
	public void propertyUpdate(PropertyData data,UserSessionData userSessionData) throws Exception {
		try{
			PropertyABM propertyABM = PropertyABMUtil.getHome().create();
			propertyABM.propertyUpdate(data,userSessionData);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}