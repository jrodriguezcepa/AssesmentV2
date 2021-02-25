package assesment.persistence.corporation;

import java.util.Iterator;

import javax.ejb.SessionBean;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import assesment.communication.administration.user.UserSessionData;
import assesment.communication.corporation.CediAttributes;
import assesment.communication.corporation.CorporationAttributes;
import assesment.communication.exception.AlreadyExistsException;
import assesment.communication.exception.DeslogedException;
import assesment.communication.exception.InvalidDataException;
import assesment.persistence.assesment.tables.Cedi;
import assesment.persistence.corporation.tables.Corporation;
import assesment.persistence.hibernate.HibernateAccess;
import assesment.persistence.util.ExceptionHandler;
import assesment.persistence.util.PersistenceUtil;

/**
 * @ejb.bean name="CorpABM" display-name="Name for CorpABM"
 *           description="Description for CorpABM"
 *           jndi-name="ejb/CorpABM" type="Stateless" view-type="remote"
 */
public abstract class CorpABMBean implements SessionBean {

    private ExceptionHandler handler = new ExceptionHandler(CorpABMBean.class);

    /**
     * Init transaction
     * @ejb.create-method
     * @ejb.permission role-name = "administrator"
     */
    public void ejbCreate() throws javax.ejb.CreateException {  }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     */
    public Integer create(CorporationAttributes attributes, UserSessionData userRequest) throws Exception {
        if (userRequest == null) {
            throw new DeslogedException("create","session = null");
        }
        if (attributes == null) {
            throw new InvalidDataException("create","data = null");
        }
        if (PersistenceUtil.empty(attributes.getName())) {
            throw new InvalidDataException("create","corporation.name.invalid");
        }
        if (exists(attributes)) {
            throw new AlreadyExistsException("create","corporation.error.alreadyexist");
        }
        try {
            Session session = HibernateAccess.currentSession();
            Corporation corporation = new Corporation(attributes);
            return (Integer)session.save(corporation);
        } catch (Exception e) {
            handler.getException(e,"create",userRequest.getFilter().getLoginName());
        }
        return null;
    }
    
	/**
	 * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
	 */
	public void update(CorporationAttributes attributes,UserSessionData userSessionData) throws Exception {
        if (userSessionData == null) {
            throw new DeslogedException("update","session = null");
        }
		if (attributes == null) {
			throw new InvalidDataException("update","generic.error.emptydata");
		}
        if (PersistenceUtil.empty(attributes.getName())) {
            throw new InvalidDataException("update","corporation.name.invalid");
        }
        if (exists(attributes)) {
            throw new AlreadyExistsException("update","corporation.error.alreadyexist");
        }
        try {
            Session session = HibernateAccess.currentSession();
            Corporation corporation = (Corporation)session.load(Corporation.class,attributes.getId());
            corporation.setName(attributes.getName());
            if(attributes.getLogo() != null) {
                corporation.setLogo(attributes.getLogo());
            }
            session.update(corporation);
        } catch (Exception e) {
            handler.getException(e,"update",userSessionData.getFilter().getLoginName());
        }
    }

	
    /**
     * @ejb.permission role-name = "administrator"
     * @ejb.interface-method view-type = "both"
     */
    public void delete(Integer corporationId, UserSessionData userSessionData) throws Exception {
    	try{       
    		Session session = HibernateAccess.currentSession();
            Corporation corporation = (Corporation) session.load(Corporation.class, corporationId);
            session.delete(corporation);
        } catch (InvalidDataException e) {
            throw e;
    	} catch (Exception e) {
    		handler.getException(e,"delete",userSessionData.getFilter().getLoginName());
    	}
    }
    
    /**
     * Verifies if a corporation exist
     */
    private boolean exists(CorporationAttributes attributes)throws Exception{
        Session session = HibernateAccess.currentSession();

        Query query = session.createQuery("SELECT c FROM Corporation c " +
                "WHERE lower(name) = lower(:name) ");
        query.setParameter("name", attributes.getName());
 
        Iterator iter = query.list().iterator();
        if(iter.hasNext()) {
            if(attributes.getId() == null) {
                return true;
            }else {
                return !attributes.getId().equals(((Corporation)iter.next()).getId());
            }
        }
        return false;
    }

    /**
     * @ejb.permission role-name = "administrator"
     * @ejb.interface-method view-type = "both"
     */
    public String validateDelete(Integer corporation) {
        Session session = HibernateAccess.currentSession();

        String queryStr = "SELECT count(*) FROM Assesment a WHERE a.corporation.id = (:corporation)";
        Query query = session.createQuery(queryStr);
        query.setParameter("corporation", corporation);
        if(((Long)query.uniqueResult()).intValue() > 0) {
            return "corporation.delete.assesmentasociated";
        }
       
        return null;
    }

    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     */
    public Integer createCedi(CediAttributes attributes, UserSessionData userRequest) throws Exception {
        if (userRequest == null) {
            throw new DeslogedException("create","session = null");
        }
        if (attributes == null) {
            throw new InvalidDataException("create","data = null");
        }
        if (PersistenceUtil.empty(attributes.getName())) {
            throw new InvalidDataException("create","corporation.name.invalid");
        }
        if (existsCedi(attributes)) {
            throw new AlreadyExistsException("create","corporation.error.alreadyexist");
        }
        try {
            Session session = HibernateAccess.currentSession();
            Cedi cedi = new Cedi(attributes);
            return (Integer)session.save(cedi);
        } catch (Exception e) {
            handler.getException(e,"create",userRequest.getFilter().getLoginName());
        }
        return null;
    }
   
    /**
     * Verifies if a cedi exists
     */
    private boolean existsCedi(CediAttributes attributes)throws Exception{
        Session session = HibernateAccess.currentSession();

        Query query = session.createQuery("SELECT c FROM Cedi c " +
                "WHERE lower(name) = lower(:name) ");
        query.setParameter("name", attributes.getName());
 
        Iterator iter = query.list().iterator();
        if(iter.hasNext()) {
            if(attributes.getId() == null) {
                return true;
            }else {
                return !attributes.getId().equals(((Corporation)iter.next()).getId());
            }
        }
        return false;
    }

	/**
	 * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
	 */
	public void updateCedi(CediAttributes attributes,UserSessionData userSessionData) throws Exception {
        if (userSessionData == null) {
            throw new DeslogedException("update","session = null");
        }
		if (attributes == null) {
			throw new InvalidDataException("update","generic.error.emptydata");
		}
        if (PersistenceUtil.empty(attributes.getName())) {
            throw new InvalidDataException("update","corporation.name.invalid");
        }
      /*  if (existsCedi(attributes)) {
            throw new AlreadyExistsException("update","corporation.error.alreadyexist");
        }*/
        try {
            Session session = HibernateAccess.currentSession();
            Cedi cedi = (Cedi)session.load(Cedi.class,attributes.getId());
            cedi.setName(attributes.getName());
            cedi.setAccessCode(attributes.getAccessCode());
            cedi.setUen(attributes.getUen());
            cedi.setDrv(attributes.getDrv());
            cedi.setManager(attributes.getManager());
            cedi.setManagerMail(attributes.getManagerMail());
            cedi.setLoginName(attributes.getLoginName());
            session.update(cedi);
        } catch (Exception e) {
            handler.getException(e,"update",userSessionData.getFilter().getLoginName());
        }
    }

    /**
     * @ejb.permission role-name = "administrator"
     * @ejb.interface-method view-type = "both"
     */
    public void deleteCedi(Integer cediId, UserSessionData userSessionData) throws Exception {
    	try{
    		Session session = HibernateAccess.currentSession();
            Cedi cedi = (Cedi) session.load(Cedi.class, cediId);
            session.delete(cedi);
        } catch (InvalidDataException e) {
            throw e;
    	} catch (Exception e) {
    		handler.getException(e,"delete",userSessionData.getFilter().getLoginName());
    	}
    }
}