/*
 * Created on 30-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.persistence.corporation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import assesment.communication.administration.user.UserSessionData;
import assesment.communication.corporation.CediAttributes;
import assesment.communication.corporation.CediData;
import assesment.communication.corporation.CorporationAttributes;
import assesment.communication.corporation.CorporationData;
import assesment.communication.exception.DeslogedException;
import assesment.communication.exception.InvalidDataException;
import assesment.communication.util.ListResult;
import assesment.persistence.assesment.tables.Cedi;
import assesment.persistence.corporation.tables.Corporation;
import assesment.persistence.hibernate.HibernateAccess;
import assesment.persistence.util.ExceptionHandler;

/**
 * @ejb.bean name="CorpReport"
 *           display-name="Name for CorpReport"
 *           description="Description for CorpReport"
 *           jndi-name="ejb/CorpReport"
 *           type="Stateless"
 *           view-type="both"
 */
public abstract class CorpReportBean implements SessionBean {

    private ExceptionHandler handler = new ExceptionHandler(CorpReportBean.class);

    /**
     * @ejb.create-method
     * @ejb.permission role-name = "systemaccess,administrator, clientreporter"
     * @throws CreateException
     */
    public void ejbCreate() throws CreateException{
    }
    
	/**
	 * @ejb.interface-method 
     * @ejb.permission role-name = "administrator"
	 * @param name
	 * @param userRequest
	 * @return
	 * @throws Exception
	 */
	public ListResult findCorporationName(String name, UserSessionData userSessionData) throws Exception {
		if (name == null) {
			throw new InvalidDataException("findCorporationName","name = null");
		}
		if (userSessionData == null) {
			throw new DeslogedException("findCorporationName","session = null");
		}

		Session session = null;
		try {
 
			session = HibernateAccess.currentSession();
            Query queryCount = session.createSQLQuery("SELECT COUNT(*) AS count FROM corporations AS c WHERE lower(c.name) like lower(:name)").addScalar("count",Hibernate.INTEGER);
            queryCount.setParameter("name", "%"+name+"%");
            Integer total = (Integer)queryCount.uniqueResult();
            if(total == null) {
                total = 0;
            }

            String queryStr = "SELECT c.id AS id,c.name AS name,COUNT(a.id) AS count FROM corporations AS c " +
            "LEFT JOIN assesments AS a ON a.corporation = c.id WHERE lower(c.name) like lower(:name) GROUP BY c.id,c.name ORDER BY c.name ";
            Query query = session.createSQLQuery(queryStr).addScalar("id",Hibernate.INTEGER).addScalar("name",Hibernate.STRING).addScalar("count",Hibernate.INTEGER);
			query.setParameter("name", "%"+name+"%");

            Iterator iter = query.list().iterator();
            Collection<Object[]> list = new LinkedList<Object[]>();
			while (iter.hasNext()) {
				list.add((Object[])iter.next());
			}
            
            return new ListResult(list,total);
		} 
		catch (Exception e) {
            handler.getException(e,"findCorporationName",userSessionData.getFilter().getLoginName());
		}
		return new ListResult(new LinkedList(),0);
	}

	
	/**
	 * @ejb.interface-method 
     * @ejb.permission role-name = "systemaccess,administrator"
	 * @throws Exception
	 */
	public CorporationData findCorporation(Integer id, UserSessionData userSessionData) throws Exception {
		if (id == null) {
			throw new InvalidDataException("findCorporation","id = null");
		}
		if (userSessionData == null) {
			throw new DeslogedException("findCorporation","session = null");
		}
		try {
			Session session = HibernateAccess.currentSession();
			Corporation corporation = (Corporation)session.load(Corporation.class,id);
			return corporation.getData();
            
		}catch (Exception e) {
            handler.getException(e,"findCorporation",userSessionData.getFilter().getLoginName());
		}
		return null;
	}

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "systemaccess,administrator"
     * @throws Exception
     */
    public CorporationAttributes findCorporationAttributes(Integer id, UserSessionData userSessionData) throws Exception {
        if (id == null) {
            throw new InvalidDataException("findCorporationAttributes","id = null");
        }
        if (userSessionData == null) {
            throw new DeslogedException("findCorporationAttributes","session = null");
        }
        try {
            Session session = HibernateAccess.currentSession();
            Corporation corporation = (Corporation)session.load(Corporation.class,id);
            return corporation.getAttributes();
            
        }catch (Exception e) {
            handler.getException(e,"findCorporationAttributes",userSessionData.getFilter().getLoginName());
        }
        return null;
    }
    
    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator"
     * @throws Exception
     */
    public Collection<CorporationData> getPrincipalResume(UserSessionData userSessionData) throws Exception {
    	Collection<CorporationData> list = new LinkedList<CorporationData>();
    	try {
            Session session = HibernateAccess.currentSession();
            Query q = session.createSQLQuery("SELECT c.id, c.name, a.id FROM corporations c "
            		+ "JOIN assesments a ON c.id = a.corporation WHERE a.archived = 'f' ORDER BY a.id DESC").addScalar("id", Hibernate.INTEGER).addScalar("name", Hibernate.STRING);
            Iterator it = q.list().iterator();
            HashMap<Integer, CorporationData> map = new HashMap<Integer, CorporationData>();
            String ids = "0";
            while(it.hasNext() && map.size() < 20) {
            	Object[] data = (Object[]) it.next();
            	if(!map.containsKey(data[0])) {
            		ids += ", "+data[0];
            		CorporationData corp = new CorporationData((Integer)data[0], (String)data[1], null, new LinkedList(), null);
            		map.put(corp.getId(), corp);
            	}
            }
            
            q = session.createSQLQuery("SELECT corporation, COUNT(*) AS c FROM assesments WHERE archived = 'f' AND corporation IN ("+ids+") GROUP BY corporation").addScalar("corporation", Hibernate.INTEGER).addScalar("c", Hibernate.INTEGER);
            it = q.list().iterator();
            while(it.hasNext()) {
            	Object[] data = (Object[]) it.next();
        		map.get(data[0]).setAssessmentCount((Integer)data[1]);
            }
            
            q = session.createSQLQuery("SELECT corporation, COUNT(*) AS c FROM groups WHERE corporation IN ("+ids+") GROUP BY corporation").addScalar("corporation", Hibernate.INTEGER).addScalar("c", Hibernate.INTEGER);
            it = q.list().iterator();
            while(it.hasNext()) {
            	Object[] data = (Object[]) it.next();
        		map.get(data[0]).setGroupCount((Integer)data[1]);
            }
            
            list.addAll(map.values());
        }catch (Exception e) {
            handler.getException(e,"getPrincipalResume",userSessionData.getFilter().getLoginName());
        }
    	return list;
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     */
    public Integer findCediCode(String cedi, Integer company, UserSessionData userSessionData) throws Exception {
    	Integer id = null;
    	try {
            Session session = HibernateAccess.currentSession();
            
    		Query q = session.createSQLQuery("SELECT id FROM cedi WHERE LOWER(accesscode) = '"+cedi.toLowerCase()+"' AND company = "+company).addScalar("id", Hibernate.INTEGER);
            Iterator it = q.list().iterator();
            if(it.hasNext()) {
            	id = (Integer) it.next();
            }
        } catch (Exception e) {
            handler.getException(e,"findCediCode",userSessionData.getFilter().getLoginName());
        }
    	return id;
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator, clientreporter"
     */
    public Integer[] findCediUser(String user, UserSessionData userSessionData) throws Exception {
    	Collection<Integer> ids = new LinkedList<Integer>();
    	try {
            Session session = HibernateAccess.currentSession();
            
    		Query q = session.createSQLQuery("SELECT id, loginname FROM cedi WHERE LOWER(loginname) LIKE '%"+user.toLowerCase()+"%'").addScalar("id", Hibernate.INTEGER).addScalar("loginname", Hibernate.STRING);
            Iterator it = q.list().iterator();
            while(it.hasNext()) {
            	Object[] data = (Object[])it.next();
            	StringTokenizer st = new StringTokenizer((String)data[1], ",");
            	boolean found = false;
            	while(st.hasMoreTokens() && !found) {
                	found = st.nextToken().equalsIgnoreCase(user);
                	if(found)
                		ids.add((Integer)data[0]);
            	}
            }
        } catch (Exception e) {
            handler.getException(e,"findCediUser",userSessionData.getFilter().getLoginName());
        }
    	return (Integer[]) ids.toArray(new Integer[0]);
    }
    
    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "systemaccess,administrator, clientreporter"
     * @throws Exception
     */
    public CediAttributes findCediAttributes(Integer id, UserSessionData userSessionData) throws Exception {
        if (id == null) {
            throw new InvalidDataException("findCorporationAttributes","id = null");
        }
        if (userSessionData == null) {
            throw new DeslogedException("findCorporationAttributes","session = null");
        }
        try {
            Session session = HibernateAccess.currentSession();
            Cedi cedi = (Cedi)session.load(Cedi.class,id);
            return cedi.getAttributes();
            
        }catch (Exception e) {
            handler.getException(e,"findCorporationAttributes",userSessionData.getFilter().getLoginName());
        }
        return null;
    }

	
	/**
	 * @ejb.interface-method 
     * @ejb.permission role-name = "administrator, clientreporter"
	 * @param name
	 * @param userRequest
	 * @return
	 * @throws Exception
	 */
	public ListResult findCediName(String name, Integer company, UserSessionData userSessionData) throws Exception {
		if (name == null) {
			throw new InvalidDataException("findCorporationName","name = null");
		}
		if (userSessionData == null) {
			throw new DeslogedException("findCorporationName","session = null");
		}

		Session session = null;
		try {
 
			session = HibernateAccess.currentSession();
            Query queryCount = session.createSQLQuery("SELECT COUNT(*) AS count FROM cedi AS c WHERE lower(c.name) like lower(:name)").addScalar("count",Hibernate.INTEGER);
            queryCount.setParameter("name", "%"+name+"%");
            Integer total = (Integer)queryCount.uniqueResult();
            if(total == null) {
                total = 0;
            }

            String queryStr = "SELECT c.id AS id,c.name AS name FROM cedi AS c " +
            "WHERE company = "+company+" AND lower(c.name) like lower(:name) GROUP BY c.id,c.name ORDER BY c.name ";
            Query query = session.createSQLQuery(queryStr).addScalar("id",Hibernate.INTEGER).addScalar("name",Hibernate.STRING);
			query.setParameter("name", "%"+name+"%");

            Iterator iter = query.list().iterator();
            Collection<Object[]> list = new LinkedList<Object[]>();
			while (iter.hasNext()) {
				list.add((Object[])iter.next());
			}
            
            return new ListResult(list,total);
		} 
		catch (Exception e) {
            handler.getException(e,"findCorporationName",userSessionData.getFilter().getLoginName());
		}
		return new ListResult(new LinkedList(),0);
	}

	
	/**
	 * @ejb.interface-method 
     * @ejb.permission role-name = "systemaccess,administrator, clientreporter"
	 * @throws Exception
	 */
	public CediData findCedi(Integer id, UserSessionData userSessionData) throws Exception {
		if (id == null) {
			throw new InvalidDataException("findCorporation","id = null");
		}
		if (userSessionData == null) {
			throw new DeslogedException("findCorporation","session = null");
		}
		try {
			Session session = HibernateAccess.currentSession();
			Cedi cedi = (Cedi)session.load(Cedi.class,id);
			return cedi.getData();
            
		}catch (Exception e) {
            handler.getException(e,"findCorporation",userSessionData.getFilter().getLoginName());
		}
		return null;
	}

}