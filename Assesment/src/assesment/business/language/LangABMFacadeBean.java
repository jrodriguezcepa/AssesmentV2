/*
 * Created on 31-oct-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.business.language;

import javax.ejb.SessionBean;

import org.hibernate.classic.Session;

import assesment.business.util.ExceptionHandler;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.exception.DeslogedException;
import assesment.persistence.hibernate.HibernateAccess;
import assesment.persistence.language.LangABM;
import assesment.persistence.language.LangABMHome;
import assesment.persistence.language.LangABMUtil;
import assesment.persistence.language.tables.GeneralMessage;

/**
 * @ejb.bean name="LangABMFacade"
 *           display-name="Name for LangABMFacade"
 *           description="Description for LangABMFacade"
 *           jndi-name="ejb/LangABMFacade"
 *           type="Stateless"
 *           view-type="remote"
 * 
 * @ejb.ejb-ref
 * 			    ejb-name = "LangABM"
 *  			ref-name = "ejb/LangABM" 
 * 				view-type = "remote"
 * 
 * @jboss.ejb-ref-jndi 
 * 
 * 				jndi-name = "ejb/LangABM"
 * 				ref-name ="LangABM"
 * 
 * @ejb.util generate="physical"
 */
public abstract class LangABMFacadeBean implements SessionBean {
	
    ExceptionHandler handler = new ExceptionHandler(LangABMFacadeBean.class);

    /**
	 * Create method
	 * 
	 * @ejb.create-method
	 * @ejb.permission role-name="administrator" 
	 */
	public void ejbCreate() throws javax.ejb.CreateException {
	}

	/**
	 * @ejb.interface-method view-type = "remote"
     * @ejb.permission role-name="administrator" 
	 */
	public void updateTexts(String[] texts,String key,UserSessionData userSessionData) throws Exception {
		if(userSessionData==null){
			throw new DeslogedException("updateTexts","session = null");
		}
		try{
			LangABM languageABM = LangABMUtil.getHome().create();
            languageABM.updateText(key,GeneralMessage.SPANISH,texts[0],userSessionData);
            languageABM.updateText(key,GeneralMessage.ENGLISH,texts[1],userSessionData);
            languageABM.updateText(key,GeneralMessage.PORTUGUESE,texts[2],userSessionData);
		}catch(Exception e) {
			handler.handleException("updateTexts",e);
		}
	}
	
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     */
    public void saveText(String key,String language, String text, UserSessionData userSessionData) throws Exception {
        if (userSessionData == null) {
            throw new DeslogedException("saveText","session = null");
        }
        try {
			LangABM languageABM = LangABMUtil.getHome().create();
            languageABM.saveText(key,language,text,userSessionData);
        } catch (Exception e) {
			handler.handleException("saveText",e);
        }
    }

}