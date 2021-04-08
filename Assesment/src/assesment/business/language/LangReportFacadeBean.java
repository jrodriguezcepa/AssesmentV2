/*
 * Created on 31-oct-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.business.language;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;

import javax.ejb.SessionBean;

import assesment.business.util.ExceptionHandler;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.exception.CommunicationProblemException;
import assesment.communication.exception.DeslogedException;
import assesment.persistence.language.LangReport;
import assesment.persistence.language.LangReportHome;
import assesment.persistence.language.LangReportUtil;
import assesment.persistence.language.tables.GeneralMessage;

/**
 * @ejb.bean name="LangReportFacade"
 *           display-name="Name for LangReportFacade"
 *           description="Description for LangReportFacade"
 *           jndi-name="ejb/LangReportFacade"
 *           type="Stateless"
 *           view-type="remote"
 * 
 * @ejb.ejb-ref
 * 			    ejb-name = "LangReport"
 *  			ref-name = "ejb/LangReport" 
 * 				view-type = "remote"
 * 
 * @jboss.ejb-ref-jndi 
 * 
 * 				jndi-name = "ejb/LangReport"
 * 				ref-name ="LangReport"
 * 
 * @ejb.util generate="physical"
 */
public abstract class LangReportFacadeBean implements SessionBean {
	
    ExceptionHandler handler = new ExceptionHandler(LangReportFacadeBean.class);

    /**
	 * Create method
	 * 
	 * @ejb.create-method
	 * @ejb.permission role-name="systemaccess,administrator,accesscode,pepsico_candidatos,basf_assessment,clientreporter,cepareporter,webinar" 
	 */
	public void ejbCreate() throws javax.ejb.CreateException {
	}

	/**
	 * @ejb.interface-method view-type = "remote"
	 * 
	 * @return Collection of LanguageData
	 * @throws CommunicationProblemException
	 * @throws DeslogedException
     * @ejb.permission role-name="systemaccess,resetpassword,administrator,accesscode,pepsico_candidatos,basf_assessment,clientreporter,cepareporter" 
	 */
	public Collection findAllLanguage(UserSessionData userSessionData) throws Exception{
		if(userSessionData==null){
			throw new DeslogedException("Has ocurred error in class business.language.LanguageReportFacede(findAllLanguage), userSessionData is empty","generic.error.deslogued");
		}
		Collection languages=null;
		try{
			LangReportHome lngReportHome=LangReportUtil.getHome();
			LangReport languageReport=lngReportHome.create();
			languages=languageReport.findAllLanguage();
		} catch(Exception e){
			handler.handleException("findAllLanguage",e);
		}
		return languages;
	}
	
	/**
	 * @ejb.interface-method view-type = "remote"
     * @ejb.permission role-name="systemaccess,resetpassword,administrator,accesscode,pepsico_candidatos,basf_assessment,clientreporter,cepareporter,webinar" 
	 */
	public Object[][] findAllMessages(Locale locale,UserSessionData userSessionData) throws Exception {
		if(userSessionData==null){
			throw new DeslogedException("findAllMessages","session = null");
		}
		try{
			LangReportHome lngReportHome=LangReportUtil.getHome();
            LangReport languageReport=lngReportHome.create();
			return languageReport.findMessages(locale);
		} catch(Exception e){
            handler.handleException("findAllMessages",e);
		}
        return null;
	}

    /**
     * @ejb.permission role-name="administrator,accesscode" 
     * @ejb.interface-method view-type = "remote"
     */
    public String[] findTexts(String key,UserSessionData userSessionData) throws Exception {
        if(userSessionData==null){
            throw new DeslogedException("findTexts","session = null");
        }
        String values[] = new String[3]; 
        try{
            LangReport languageReport=LangReportUtil.getHome().create();
            return languageReport.findTexts(key,userSessionData);
        }catch (Exception e) {
            handler.handleException("findTexts",e);
        }
        return values;
    }
  
    /**
     * @ejb.permission role-name="systemaccess,administrator,accesscode" 
     * @ejb.interface-method view-type = "remote"
     */
    public HashMap<String, String> findAssessmentBKPTexts(Integer assessment, UserSessionData userSessionData) throws Exception {
        try{
            LangReport languageReport=LangReportUtil.getHome().create();
            return languageReport.findAssessmentBKPTexts(assessment, userSessionData);
        }catch (Exception e) {
            handler.handleException("findAssessmentBKPTexts",e);
        }
        return new HashMap<String, String>();
    }

}