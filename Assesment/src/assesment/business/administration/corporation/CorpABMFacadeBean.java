
/*
 * Created on 22-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.business.administration.corporation;

import java.util.Collection;
import java.util.Iterator;

import javax.ejb.SessionBean;

import assesment.business.util.ExceptionHandler;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.corporation.CediAttributes;
import assesment.communication.corporation.CorporationAttributes;
import assesment.communication.exception.CommunicationProblemException;
import assesment.communication.exception.DeslogedException;
import assesment.communication.exception.InvalidDataException;
import assesment.persistence.corporation.CorpABM;
import assesment.persistence.corporation.CorpABMHome;
import assesment.persistence.corporation.CorpABMUtil;
import assesment.persistence.util.PersistenceUtil;

/**
 * @ejb.bean name="CorpABMFacade"
 *           display-name="Name for Corp"
 *           description="Description for Corp"
 *           jndi-name="ejb/CorpABMFacade"
 *           type="Stateless"
 *           view-type="remote"
 * 
 * @ejb.ejb-ref 
 * 			ejb-name ="CorpABM"
 * 			ref-name = "ejb/CorpABM"
 * 			view-type ="remote"
 * 
 * @jboss.ejb-ref-jndi
 * 			jndi-name = "ejb/CorpABM" 
 * 			ref-name = "CorpABM"
 * 
  * @ejb.ejb-ref 
 * 			ejb-name ="CorpReport"
 * 			ref-name = "ejb/CorpReport"
 * 			view-type ="remote"
 * 
 * @jboss.ejb-ref-jndi
 * 			jndi-name = "ejb/CorpReport" 
 * 			ref-name = "CorpReport"
 * 
 * @ejb.util generate="physical"
*/
public abstract class CorpABMFacadeBean implements SessionBean {
	
    ExceptionHandler handler = new ExceptionHandler(CorpABMFacadeBean.class);
    
	/**
	 * @ejb.create-method
     * @ejb.permission role-name = "administrator,corporationmodify,corporationcreate,corporationdelete"
     * Create method
	 */
	public void ejbCreate()	throws javax.ejb.CreateException { }

	/**
	 * @ejb.interface-method	view-type = "remote"
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "administrator"
	 */
	public Integer create(CorporationAttributes data,UserSessionData userSessionData) throws Exception{
		if (userSessionData == null) {
			throw new DeslogedException("create","session = null");
		}
		if (data == null) {
			throw new InvalidDataException("create","data = null");
		}
		if (!validate(data)) {
			throw new InvalidDataException("create","corporation.name.invalid");
		}
		try {
			CorpABMHome home = CorpABMUtil.getHome();
            CorpABM corporationABM = home.create();
            return corporationABM.create(data, userSessionData);
		} catch (Exception e) {
            handler.handleException("create",e);
		}
        return null;
	}

	/**
	 * @ejb.interface-method 
	 * 		view-type = "remote"
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "administrator,corporationmodify"
	 * Modifies a corporation
	 * @param data Data of the corporation modified
	 * @throws InvalidDataException
	 * @throws CommunicationProblemException
	 * @throws DeslogedException
	 */
	public void update(CorporationAttributes data,UserSessionData userSessionData) throws Exception{
		if (userSessionData == null) {
			throw new DeslogedException("update","session = null");
		}
		if (data == null) {
			throw new InvalidDataException("update","data = null");
		}
		if (!validate(data)) {
			throw new InvalidDataException("update","corporation.name.invalid");
		}
		try {
			CorpABMHome home = CorpABMUtil.getHome();
            CorpABM corporationABM = home.create();
			corporationABM.update(data,userSessionData);
        } catch (Exception e) {
            handler.handleException("update",e);
        }
	}

    /**
     * Validate if the name of the corporation data is not empty
     * @param data - Data of the corporation
     * @return if is valid or not
     */
	private boolean validate(CorporationAttributes data){
		return !PersistenceUtil.empty(data.getName());
	}

    /**
     * @ejb.interface-method view-type = "remote"
     * @ejb.transaction type="Required"
     * @ejb.permission role-name = "administrator"
     * @param data
     * @throws InvalidDataException
     * @throws DeslogedException
     */
    public void delete(Collection list, UserSessionData userSessionData) throws Exception {
    	try{   
    		if (userSessionData == null) {
    			throw new DeslogedException("delete","session = null");
    		}
    		if (list == null) {
    			throw new InvalidDataException("delete","data = null");
    		}
    		CorpABM corporationABM = CorpABMUtil.getHome().create();
            Iterator it = list.iterator();
            while(it.hasNext()) {
                Integer id = new Integer((String)it.next());
                String msg = corporationABM.validateDelete(id); 
                if(msg != null) {
                    throw new InvalidDataException("delete",msg);
                }else {
                    corporationABM.delete(id,userSessionData);
                }
            }
    	}catch (Exception e) {
    		handler.handleException("delete",e);
    	}
    }
	
	/**
	 * @ejb.interface-method	view-type = "remote"
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "administrator"
	 */
	public Integer createCedi(CediAttributes data,UserSessionData userSessionData) throws Exception{
		if (userSessionData == null) {
			throw new DeslogedException("create","session = null");
		}
		if (data == null) {
			throw new InvalidDataException("create","data = null");
		}
		/*if (!validate(data)) {
			throw new InvalidDataException("create","corporation.name.invalid");
		}*/
		try {
			CorpABMHome home = CorpABMUtil.getHome();
            CorpABM corporationABM = home.create();
            return corporationABM.createCedi(data, userSessionData);
		} catch (Exception e) {
            handler.handleException("create",e);
		}
        return null;
	}
	

	/**
	 * @ejb.interface-method 
	 * 		view-type = "remote"
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "administrator,corporationmodify"
	 * Modifies a corporation
	 * @param data Data of the corporation modified
	 * @throws InvalidDataException
	 * @throws CommunicationProblemException
	 * @throws DeslogedException
	 */
	public void updateCedi(CediAttributes data,UserSessionData userSessionData) throws Exception{
		if (userSessionData == null) {
			throw new DeslogedException("update","session = null");
		}
		if (data == null) {
			throw new InvalidDataException("update","data = null");
		}
		if (!validateCedi(data)) {
			throw new InvalidDataException("update","corporation.name.invalid");
		}
		try {
			CorpABMHome home = CorpABMUtil.getHome();
            CorpABM corporationABM = home.create();
			corporationABM.updateCedi(data,userSessionData);
        } catch (Exception e) {
            handler.handleException("update",e);
        }
	}

	/**
     * Validate if the name of the corporation data is not empty
     * @param data - Data of the corporation
     * @return if is valid or not
     */
	private boolean validateCedi(CediAttributes data){
		return !PersistenceUtil.empty(data.getName());
	}

    
    /**
     * @ejb.interface-method view-type = "remote"
     * @ejb.transaction type="Required"
     * @ejb.permission role-name = "administrator"
     * @param data
     * @throws InvalidDataException
     * @throws DeslogedException
     */
    public void deleteCedi(Collection list, UserSessionData userSessionData) throws Exception {
    	try{   
    		if (userSessionData == null) {
    			throw new DeslogedException("delete","session = null");
    		}
    		if (list == null) {
    			throw new InvalidDataException("delete","data = null");
    		}
    		CorpABM corporationABM = CorpABMUtil.getHome().create();
            Iterator it = list.iterator();
            while(it.hasNext()) {
                Integer id = new Integer((String)it.next());
                corporationABM.deleteCedi(id,userSessionData);
            }
    	}catch (Exception e) {
    		handler.handleException("delete",e);
    	}
    }
}