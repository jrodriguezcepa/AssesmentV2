/**
 * CEPA
 * Assesment
 */
package assesment.communication.language;

import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Locale;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import assesment.business.AssesmentAccess;
import assesment.business.AssesmentAccessRemote;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.exception.CommunicationProblemException;
import assesment.communication.exception.DeslogedException;
import assesment.communication.exception.InvalidDataException;
import assesment.persistence.language.LangReport;

public class TextContainer {

    private static TextContainer textContainer;
    private Hashtable texts = new Hashtable();
    
    public static TextContainer getInstance() {
        if(textContainer == null) {
            textContainer = new TextContainer();
        }
        return textContainer;
    }
    
    public static TextContainer getNewInstance() {
        textContainer = new TextContainer();
        return textContainer;
    }    

    public Object[][] getTexts(String language,AssesmentAccess bean,UserSessionData userSessionData) 
    throws NamingException, CreateException, DeslogedException, InvalidDataException, CommunicationProblemException, RemoteException {
        LangReport languageReport = null;
            try {
                String key = language;
                if(texts.containsKey(key)) {
                    return (Object[][])texts.get(key);
                }else {
                	Locale locale=new Locale(language,"");
                	Object[][] contents = bean.getLanguageReportFacade().findAllMessages(locale,userSessionData);
                	synchronized (texts) {
                		texts.put(key,contents);
                	}	
                	return contents;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            return null;
    }
    
    public Object[][] getTexts(String language,AssesmentAccessRemote bean,UserSessionData userSessionData) throws Exception {
        try {
            String key = language;
            if(texts.containsKey(key)) {
                return (Object[][])texts.get(key);
            }else {
            	Locale locale=new Locale(language,"");
                Object[][] contents = bean.getLanguageReportFacade().findAllMessages(locale,userSessionData);
                synchronized (texts) {
                    texts.put(key,contents);
                }	
                return contents;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Object[][] reloadTexts(String language,AssesmentAccess bean,UserSessionData userSessionData) throws NamingException, CreateException, DeslogedException, InvalidDataException, CommunicationProblemException, RemoteException {
        LangReport languageReport = null;
        try {
            String key = language;
            Locale locale=new Locale(language,"");
            Object[][] contents = bean.getLanguageReportFacade().findAllMessages(locale,userSessionData);
            synchronized (texts) {
                texts.put(key,contents);
            }	
            return contents;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Object[][] reloadTexts(String language,AssesmentAccessRemote bean,UserSessionData userSessionData) throws NamingException, CreateException, DeslogedException, InvalidDataException, CommunicationProblemException, RemoteException {
        LangReport languageReport = null;
        try {
            String key = language;
            Locale locale=new Locale(language,"");
            Object[][] contents = bean.getLanguageReportFacade().findAllMessages(locale,userSessionData);
            synchronized (texts) {
                texts.put(key,contents);
            }	
            return contents;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
}
