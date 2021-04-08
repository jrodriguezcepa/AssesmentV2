/*
 * Created on 30-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.business.module;

import javax.ejb.SessionBean;

import assesment.business.util.ExceptionHandler;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.exception.DeslogedException;
import assesment.communication.exception.InvalidDataException;
import assesment.communication.module.ModuleAttribute;
import assesment.communication.module.ModuleData;
import assesment.communication.util.ListResult;
import assesment.persistence.module.ModuleReport;
import assesment.persistence.module.ModuleReportHome;
import assesment.persistence.module.ModuleReportUtil;
import assesment.persistence.question.QuestionReport;
import assesment.persistence.question.QuestionReportUtil;

/**
 * @ejb.bean name="ModuleReportFacade"
 *           display-name="Name for ModuleReportFacade"
 *           description="Description for ModuleReportFacade"
 *           jndi-name="ejb/ModuleReportFacade"
 *           type="Stateless"
 *           view-type="both"
 * 
 * @ejb.ejb-ref 
 *          ejb-name ="ModuleReport"
 *          ref-name = "ejb/ModuleReport"
 *          view-type ="remote"
 * 
 * @jboss.ejb-ref-jndi
 *          jndi-name = "ejb/ModuleReport" 
 *          ref-name = "ModuleReport"
 *
 * @ejb.ejb-ref 
 *          ejb-name ="QuestionReport"
 *          ref-name = "ejb/QuestionReport"
 *          view-type ="remote"
 * 
 * @jboss.ejb-ref-jndi
 *          jndi-name = "ejb/QuestionReport" 
 *          ref-name = "QuestionReport"
 *
 * @ejb.util generate="physical"
 *
 */
public abstract class ModuleReportFacadeBean implements SessionBean {

    ExceptionHandler handler = new ExceptionHandler(ModuleReportFacadeBean.class);

    /**
	 * Create method
	 * 
	 * @ejb.create-method
	 * @ejb.permission role-name = "systemaccess,administrator"
	 *  
	 */
	public void ejbCreate() throws javax.ejb.CreateException {
	}
	
	/**
	 * @ejb.interface-method 
     * @ejb.permission role-name = "administrator"
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ModuleAttribute findModuleAttributes(Integer id,UserSessionData userSessionData) throws Exception {
		if (userSessionData == null) {
			throw new DeslogedException("findModuleAttributes","userSessionData = null");
		}
		if (id == null) {
			throw new InvalidDataException("findModuleAttributes","id = null");
		}
		try {
            ModuleReportHome home = ModuleReportUtil.getHome();
            ModuleReport moduleReport = home.create();
			return moduleReport.findModuleAttributes(id, userSessionData);
		}catch (Exception e) {
            handler.handleException("findModuleAttributes",e);
		}
		return null;
	}
	
    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public ModuleData findModule(Integer id,UserSessionData userSessionData) throws Exception {
        if (userSessionData == null) {
            throw new DeslogedException("findModule","userSessionData = null");
        }
        if (id == null) {
            throw new InvalidDataException("findModule","id = null");
        }
        try {
            ModuleReportHome home = ModuleReportUtil.getHome();
            ModuleReport moduleReport = home.create();
            return moduleReport.findModule(id, userSessionData);
        }catch (Exception e) {
            handler.handleException("findModule",e);
        }
        return null;
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator"
     */
    public ListResult findGenericModules(String name,UserSessionData userSessionData) throws Exception {
        if (userSessionData == null) {
            throw new DeslogedException("findGenericModules","userSessionData = null");
        }
        try {
            ModuleReportHome home = ModuleReportUtil.getHome();
            ModuleReport moduleReport = home.create();
            return moduleReport.findGenericModules(name,userSessionData);
        }catch (Exception e) {
            handler.handleException("findGenericModules",e);
        }
        return null;
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator"
     * @param id
     * @return
     * @throws Exception
     */
    public ModuleAttribute findGenericModuleAttributes(Integer id,UserSessionData userSessionData) throws Exception {
        if (userSessionData == null) {
            throw new DeslogedException("findGenericModuleAttributes","userSessionData = null");
        }
        if (id == null) {
            throw new InvalidDataException("findGenericModuleAttributes","id = null");
        }
        try {
            ModuleReportHome home = ModuleReportUtil.getHome();
            ModuleReport moduleReport = home.create();
            return moduleReport.findGenericModuleAttributes(id, userSessionData);
        }catch (Exception e) {
            handler.handleException("findGenericModuleAttributes",e);
        }
        return null;
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator"
     * @param id
     * @return
     * @throws Exception
     */
    public ModuleData findGenericModule(Integer id,UserSessionData userSessionData) throws Exception {
        if (userSessionData == null) {
            throw new DeslogedException("findGenericModule","userSessionData = null");
        }
        if (id == null) {
            throw new InvalidDataException("findGenericModule","id = null");
        }
        try {
            ModuleReportHome home = ModuleReportUtil.getHome();
            ModuleReport moduleReport = home.create();
            return moduleReport.findGenericModule(id, userSessionData);
        }catch (Exception e) {
            handler.handleException("findGenericModule",e);
        }
        return null;
    }
    
    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public ModuleData getPsicoModule(UserSessionData userSessionData) throws Exception {
        ModuleData module = new ModuleData();
        module.setId(new Integer(ModuleData.PSICO));
        module.setKey("assesment.module.psicologic");
        if (userSessionData == null) {
            throw new DeslogedException("findGenericModule","userSessionData = null");
        }
        try {
            QuestionReport questionReport = QuestionReportUtil.getHome().create();
            module.setQuestions(questionReport.getPsicoQuestions(userSessionData));
        }catch (Exception e) {
            handler.handleException("findGenericModule",e);
        }
        return module;
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public ModuleData getPersonalDataModule(Integer assesment, UserSessionData userSessionData) throws Exception {
        if (userSessionData == null) {
            throw new DeslogedException("getPersonalDataModule","userSessionData = null");
        }
        if (assesment == null) {
            throw new InvalidDataException("getPersonalDataModule","assesment = null");
        }
        try {
            ModuleReportHome home = ModuleReportUtil.getHome();
            ModuleReport moduleReport = home.create();
            return moduleReport.getPersonalDataModule(assesment, userSessionData);
        }catch (Exception e) {
            handler.handleException("getPersonalDataModule",e);
        }
        return null;
    }
    
	/**
	 * @ejb.interface-method 
     * @ejb.permission role-name = "administrator"
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ModuleAttribute findModuleAttributesbkp(Integer id,UserSessionData userSessionData) throws Exception {
		if (userSessionData == null) {
			throw new DeslogedException("findModuleAttributesbkp","userSessionData = null");
		}
		if (id == null) {
			throw new InvalidDataException("findModuleAttributesbkp","id = null");
		}
		try {
            ModuleReportHome home = ModuleReportUtil.getHome();
            ModuleReport moduleReport = home.create();
			return moduleReport.findModuleAttributesbkp(id, userSessionData);
		}catch (Exception e) {
            handler.handleException("findModuleAttributesbkp",e);
		}
		return null;
	}
	
}