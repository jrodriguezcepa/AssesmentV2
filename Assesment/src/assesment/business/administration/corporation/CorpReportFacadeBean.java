/*
 * Created on 30-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.business.administration.corporation;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.SessionBean;

import assesment.business.util.ExceptionHandler;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.corporation.CediAttributes;
import assesment.communication.corporation.CediData;
import assesment.communication.corporation.CorporationAttributes;
import assesment.communication.corporation.CorporationData;
import assesment.communication.exception.CommunicationProblemException;
import assesment.communication.exception.DeslogedException;
import assesment.communication.exception.InvalidDataException;
import assesment.communication.util.ListResult;
import assesment.persistence.corporation.CorpReport;
import assesment.persistence.corporation.CorpReportHome;
import assesment.persistence.corporation.CorpReportUtil;

/**
 * @ejb.bean name="CorpReportFacade"   display-name="Name for CorpReportFacade"
 *           description="Description for CorpReportFacade"
 *           jndi-name="ejb/CorpReportFacade"     type="Stateless"       view-type="both"
 * 
 * @ejb.ejb-ref ejb-name ="CorpReport" ref-name = "ejb/CorpReport"
 * 			view-type ="remote"
 * 
 * @jboss.ejb-ref-jndi jndi-name = "ejb/CorpReport" ref-name = "CorpReport"
 *
 * @ejb.util generate="physical"
 *
 */
public abstract class CorpReportFacadeBean implements SessionBean {

    ExceptionHandler handler = new ExceptionHandler(CorpReportFacadeBean.class);

    /**
	 * Create method
	 * 
	 * @ejb.create-method
	 * @ejb.permission role-name = "systemaccess,administrator, clientreporter"
	 *  
	 */
	public void ejbCreate() throws javax.ejb.CreateException {
	}
	
	/**
	 * @ejb.interface-method 
     * @ejb.permission role-name = "administrator"
	 * @param name
	 * @return
	 * @throws InvalidDataException
	 * @throws CommunicationProblemException
	 * @throws DeslogedException
	 */
	public ListResult findCorporationName(String name,UserSessionData userSessionData) throws Exception {
		if (userSessionData == null) {
			throw new DeslogedException("findCorporationName","userSessionData = null");
		}
		if (name == null) {
			throw new InvalidDataException("findCorporationName","name = null");
		}
		try {
            CorpReportHome home = CorpReportUtil.getHome();
            CorpReport crpReport = home.create();
			return crpReport.findCorporationName(name, userSessionData);
		}catch (Exception e) {
            handler.handleException("findCorporationName",e);
		}
		return null;
	}
	
	/**
	 * @ejb.interface-method 
     * @ejb.permission role-name = "systemaccess,administrator"
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public CorporationData findCorporation(Integer id,UserSessionData userSessionData)	throws Exception {
		if (userSessionData == null) {
			throw new DeslogedException("findCorporation","userSessionData = null");
		}
		if (id == null) {
			throw new InvalidDataException("findCorporation","id = null");
		}
		try {
			CorpReportHome home = CorpReportUtil.getHome();
            CorpReport corporationReport = home.create();
			return corporationReport.findCorporation(id, userSessionData);
		}catch (Exception e) {
			handler.handleException("findCorporation",e);
		}
		return null;
	}

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "systemaccess,administrator"
     * @param name
     * @return
     * @throws Exception
     */
    public CorporationAttributes findCorporationAttributes(Integer id,UserSessionData userSessionData)  throws Exception {
        if (userSessionData == null) {
            throw new DeslogedException("findCorporationAttributes","userSessionData = null");
        }
        if (id == null) {
            throw new InvalidDataException("findCorporationAttributes","id = null");
        }
        try {
            CorpReportHome home = CorpReportUtil.getHome();
            CorpReport corporationReport = home.create();
            return corporationReport.findCorporation(id, userSessionData);
        }catch (Exception e) {
            handler.handleException("findCorporationAttributes",e);
        }
        return null;
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator"
     * @throws Exception
     */
    public Collection<CorporationData> getPrincipalResume(UserSessionData userSessionData) throws Exception {
        try {
            return CorpReportUtil.getHome().create().getPrincipalResume(userSessionData);
        }catch (Exception e) {
            handler.handleException("getPrincipalResume",e);
        }
        return new LinkedList<CorporationData>();
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator, clientreporter"
     */
    public Integer findCediCode(String cedi, Integer company, UserSessionData userSessionData) throws Exception {
    	try {
            return CorpReportUtil.getHome().create().findCediCode(cedi,company,userSessionData);
        }catch (Exception e) {
            handler.handleException("findCediCode",e);
        }
    	return null;
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator, clientreporter"
     */
    public Integer[] findCediUser(String user, UserSessionData userSessionData) throws Exception {
    	try {
            return CorpReportUtil.getHome().create().findCediUser(user,userSessionData);
        }catch (Exception e) {
            handler.handleException("findCediUser",e);
        }
    	return null;
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "systemaccess,administrator, clientreporter"
     * @param name
     * @return
     * @throws Exception
     */
    public CediAttributes findCediAttributes(Integer id,UserSessionData userSessionData)  throws Exception {
        if (userSessionData == null) {
            throw new DeslogedException("findCorporationAttributes","userSessionData = null");
        }
        if (id == null) {
            throw new InvalidDataException("findCorporationAttributes","id = null");
        }
        try {
            CorpReportHome home = CorpReportUtil.getHome();
            CorpReport corporationReport = home.create();
            return corporationReport.findCedi(id, userSessionData);
        }catch (Exception e) {
            handler.handleException("findCorporationAttributes",e);
        }
        return null;
    }

	
	/**
	 * @ejb.interface-method 
     * @ejb.permission role-name = "administrator, clientreporter"
	 * @param name
	 * @return
	 * @throws InvalidDataException
	 * @throws CommunicationProblemException
	 * @throws DeslogedException
	 */
	public ListResult findCediName(String name,Integer company,UserSessionData userSessionData) throws Exception {
		if (userSessionData == null) {
			throw new DeslogedException("findCorporationName","userSessionData = null");
		}
		if (name == null) {
			throw new InvalidDataException("findCorporationName","name = null");
		}
		try {
            CorpReportHome home = CorpReportUtil.getHome();
            CorpReport crpReport = home.create();
			return crpReport.findCediName(name, company, userSessionData);
		}catch (Exception e) {
            handler.handleException("findCorporationName",e);
		}
		return null;
	}
	
	/**
	 * @ejb.interface-method 
     * @ejb.permission role-name = "systemaccess,administrator, clientreporter"
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public CediData findCedi(Integer id,UserSessionData userSessionData)	throws Exception {
		if (userSessionData == null) {
			throw new DeslogedException("findCorporation","userSessionData = null");
		}
		if (id == null) {
			throw new InvalidDataException("findCorporation","id = null");
		}
		try {
			CorpReportHome home = CorpReportUtil.getHome();
            CorpReport corporationReport = home.create();
			return corporationReport.findCedi(id, userSessionData);
		}catch (Exception e) {
			handler.handleException("findCorporation",e);
		}
		return null;
	}
	

	/**
	 * @ejb.interface-method 
     * @ejb.permission role-name = "systemaccess,administrator, clientreporter"
	 * @throws Exception
	 */
	public Integer getCompletedCediUsers(Integer cedi, UserSessionData userSessionData) throws Exception {
  		try {
  			return CorpReportUtil.getHome().create().getCompletedCediUsers(cedi, userSessionData);
  		} catch (Exception e) {
  			e.printStackTrace();
			handler.handleException("getCompletedCediUsers",e);
		}
  		return null;
	}

}