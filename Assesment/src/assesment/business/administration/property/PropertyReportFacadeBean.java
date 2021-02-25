package assesment.business.administration.property;


import javax.ejb.CreateException;
import javax.ejb.SessionBean;

import assesment.business.util.ExceptionHandler;
import assesment.communication.administration.property.PropertyData;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.exception.CommunicationProblemException;
import assesment.communication.exception.DeslogedException;
import assesment.communication.exception.InvalidDataException;
import assesment.persistence.administration.property.PropertyReport;
import assesment.persistence.administration.property.PropertyReportHome;
import assesment.persistence.administration.property.PropertyReportUtil;

/**
 * @ejb.bean name="PropertyReportFacade" display-name="Name for PropertyReportFacade"
 *           description="Description for PropertyReportFacade"
 *           jndi-name="ejb/PropertyReportFacade" type="Stateless" view-type="remote"
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
public abstract class PropertyReportFacadeBean implements SessionBean {	
    private ExceptionHandler handler = new ExceptionHandler(PropertyReportFacadeBean.class);
    
	/**
	 * Default create method
	 * 
	 * @throws CreateException
	 * @ejb.create-method
     * @ejb.permission role-name = "systemaccess,administrator,resetpassword,accesscode"
	 */
	public void ejbCreate() throws CreateException {
		// TODO Auto-generated method stub

	}
	
	/**
	 * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator,resetpassword,accesscode"
	 * 
	 * @param propertyPK
	 * @return PropertyData
 	 * @throws InvalidDataException
	 * @throws CommunicationProblemException
	 * @throws DeslogedException
	 */
	public PropertyData findPropertyByPrimaryKey(String propertyPK, UserSessionData userSessionData) throws Exception {
		
		PropertyData data=null;
		PropertyReport propertyReport=null;
		try {
			if (propertyPK == null) {
				throw new InvalidDataException("Class business.user.UserReportFacade(findPropertyByPrimaryKey), primary Key is empty","generic.user.");
			}			

			PropertyReportHome propertyHome = PropertyReportUtil.getHome();
			propertyReport = propertyHome.create();
			data = propertyReport.findPropertyByPrimaryKey(propertyPK,userSessionData);
			
			return data;			
		} catch (Exception exception) {
            handler.handleException("findPropertyByPrimaryKey", exception);
        } 
		return null;		
	}
	
}
    