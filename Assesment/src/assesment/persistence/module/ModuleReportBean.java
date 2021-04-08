/*
 * Created on 30-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.persistence.module;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import assesment.communication.administration.user.UserSessionData;
import assesment.communication.exception.DeslogedException;
import assesment.communication.exception.InvalidDataException;
import assesment.communication.module.ModuleAttribute;
import assesment.communication.module.ModuleData;
import assesment.communication.util.ListResult;
import assesment.persistence.assesment.AssesmentReportBean;
import assesment.persistence.hibernate.HibernateAccess;
import assesment.persistence.module.tables.GenericModule;
import assesment.persistence.module.tables.Module;
import assesment.persistence.module.tables.ModuleBKP;
import assesment.persistence.util.ExceptionHandler;

/**
 * @ejb.bean name="ModuleReport"
 *           display-name="Name for ModuleReport"
 *           description="Description for ModuleReport"
 *           jndi-name="ejb/ModuleReport"
 *           type="Stateless"
 *           view-type="both"
 */
public abstract class ModuleReportBean implements SessionBean {

    private ExceptionHandler handler = new ExceptionHandler(AssesmentReportBean.class);

    /**
     * @ejb.create-method
     * @ejb.permission role-name = "systemaccess,administrator"
     * @throws CreateException
     */
    public void ejbCreate() throws CreateException{
    }
    
    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator"
     */
    public ModuleAttribute findModuleAttributes(Integer id, UserSessionData userSessionData) throws Exception {
        if (id == null) {
            throw new InvalidDataException("findModuleAttributes","id = null");
        }
        if (userSessionData == null) {
            throw new DeslogedException("findModuleAttributes","session = null");
        }

        Session session = null;
        try {
            session = HibernateAccess.currentSession();
            Module module = (Module)session.load(Module.class,id);
            return module.getAttributes();
        } catch (Exception e) {
            handler.getException(e,"findModuleAttributes",userSessionData.getFilter().getLoginName());
        }
        return null;
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public ModuleData findModule(Integer id, UserSessionData userSessionData) throws Exception {
        if (id == null) {
            throw new InvalidDataException("findModule","id = null");
        }
        if (userSessionData == null) {
            throw new DeslogedException("findModule","session = null");
        }

        Session session = null;
        try {
            session = HibernateAccess.currentSession();
            Module module = (Module)session.load(Module.class,id);
            return module.getData();
        } catch (Exception e) {
            handler.getException(e,"findModule",userSessionData.getFilter().getLoginName());
        }
        return null;
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public ListResult findGenericModules(String name,UserSessionData userSessionData) throws Exception {
        Session session = null;
        try {
 
            session = HibernateAccess.currentSession();
            String queryCountStr = "SELECT COUNT(*) AS count FROM genericmodules AS gm JOIN generalmessages t ON t.labelkey = gm.key " +
                    "WHERE lower(t.text) like lower(:name) AND t.language = (:language)";
            
            Query queryCount = session.createSQLQuery(queryCountStr).addScalar("count",Hibernate.INTEGER);
            queryCount.setParameter("name", "%"+name+"%");
            queryCount.setParameter("language", userSessionData.getLenguage());
            Integer total = (Integer)queryCount.uniqueResult();
            if(total == null) {
                total = 0;
            }

            String queryStr = "SELECT gm.id,gm.key as module,count(gq.id) as count FROM genericmodules AS gm " +
                    "JOIN generalmessages t ON t.labelkey = gm.key " +
                    "LEFT JOIN genericquestions gq on gq.module = gm.id "+
                    "WHERE lower(t.text) LIKE lower(:name)  AND t.language = (:language)";
            queryStr += "GROUP BY gm.id,gm.key ";
            Query query = session.createSQLQuery(queryStr).addScalar("id",Hibernate.INTEGER).addScalar("module",Hibernate.STRING).addScalar("count",Hibernate.INTEGER);
            query.setParameter("name", "%"+name+"%");
            query.setParameter("language", userSessionData.getLenguage());

            Iterator iter = query.list().iterator();
            Collection<Object[]> list = new LinkedList<Object[]>();
            while (iter.hasNext()) {
                list.add((Object[])iter.next());
            }
            
            return new ListResult(list,total);
        }catch (Exception e) {
            handler.getException(e,"findModules",userSessionData.getFilter().getLoginName());
        }
        return null;
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator"
     */
    public ModuleAttribute findGenericModuleAttributes(Integer id, UserSessionData userSessionData) throws Exception {
        if (id == null) {
            throw new InvalidDataException("findGenericModuleAttributes","id = null");
        }
        if (userSessionData == null) {
            throw new DeslogedException("findGenericModuleAttributes","session = null");
        }

        Session session = null;
        try {
            session = HibernateAccess.currentSession();
            GenericModule module = (GenericModule)session.load(GenericModule.class,id);
            return module.getAttributes();
        } catch (Exception e) {
            handler.getException(e,"findModuleAttributes",userSessionData.getFilter().getLoginName());
        }
        return null;
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator"
     */
    public ModuleData findGenericModule(Integer id, UserSessionData userSessionData) throws Exception {
        if (id == null) {
            throw new InvalidDataException("findGenericModule","id = null");
        }
        if (userSessionData == null) {
            throw new DeslogedException("findGenericModule","session = null");
        }

        Session session = null;
        try {
            session = HibernateAccess.currentSession();
            GenericModule module = (GenericModule)session.load(GenericModule.class,id);
            return module.getData();
        } catch (Exception e) {
            handler.getException(e,"findModule",userSessionData.getFilter().getLoginName());
        }
        return null;
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public ModuleData getPersonalDataModule(Integer assesment, UserSessionData userSessionData) throws Exception {
        if (assesment == null) {
            throw new InvalidDataException("getPersonalDataModule","assesment = null");
        }
        if (userSessionData == null) {
            throw new DeslogedException("getPersonalDataModule","session = null");
        }

        Session session = null;
        try {
            session = HibernateAccess.currentSession();
            Query q = session.createQuery("SELECT m FROM Module m WHERE m.assesment = (:assesment) AND m.type = (:type)");
            q.setParameter("assesment",assesment);
            q.setParameter("type",ModuleData.PERSONAL_DATA);
            return ((Module)q.uniqueResult()).getData();
        } catch (Exception e) {
            handler.getException(e,"findModule",userSessionData.getFilter().getLoginName());
        }
        return null;
    }
    
    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator"
     */
    public ModuleAttribute findModuleAttributesbkp(Integer id, UserSessionData userSessionData) throws Exception {
        if (id == null) {
            throw new InvalidDataException("findModuleAttributesbkp","id = null");
        }
        if (userSessionData == null) {
            throw new DeslogedException("findModuleAttributesbkp","session = null");
        }

        Session session = null;
        try {
            session = HibernateAccess.currentSession();
            ModuleBKP module = (ModuleBKP)session.load(ModuleBKP.class,id);
            return module.getAttributes();
        } catch (Exception e) {
            handler.getException(e,"findModuleAttributesbkp",userSessionData.getFilter().getLoginName());
        }
        return null;
    }
}