/*
 * Created on 14-oct-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.persistence.language;


import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import assesment.communication.administration.user.UserSessionData;
import assesment.communication.exception.CommunicationProblemException;
import assesment.communication.exception.DeslogedException;
import assesment.communication.exception.InvalidDataException;
import assesment.persistence.hibernate.HibernateAccess;
import assesment.persistence.language.tables.CorporationMessage;
import assesment.persistence.language.tables.GeneralMessage;
import assesment.persistence.language.tables.GeneralMessagePK;
import assesment.persistence.language.tables.Language;
import assesment.persistence.util.ExceptionHandler;

/**
 * @ejb.bean name="LangReport"
 *           display-name="Name for LangeReport"
 *           description="Description for LangReport"
 *           jndi-name="ejb/LangReport"
 *           type="Stateless"
 *           view-type="both"
 */
public abstract class LangReportBean implements SessionBean,Serializable {

    private ExceptionHandler handler = new ExceptionHandler(LangReportBean.class);

    /**
     * @ejb.create-method
     * @ejb.permission role-name = "systemaccess,administrator,resetpassword,accesscode,pepsico_candidatos,basf_assessment,clientreporter,cepareporter,webinar"
     * @throws CreateException
     */
    public void ejbCreate()throws CreateException{
         
    }
    
	/**
	 * @ejb.interface-method  
	 * @ejb.permission role-name = "systemaccess,administrator,resetpassword,accesscode,pepsico_candidatos,basf_assessment,clientreporter,cepareporter,webinar"
	 * @param locale
	 * @return Object[][]
	 * @throws InvalidDataException
	 * @throws CommunicationProblemException
	 */
	public Object[][] findMessages(Locale locale) throws Exception {
		if(locale==null){
			throw new InvalidDataException("findMessages","language = null");
		}
		Object[][] messages=null;
		try{
		    Session session=HibernateAccess.currentSession();
            Query query=session.createSQLQuery("SELECT labelkey,text FROM generalmessages WHERE language = '"+locale.getLanguage()+"'").addScalar("labelkey",Hibernate.STRING).addScalar("text",Hibernate.STRING);
            Collection list1 = query.list();
            
            messages=new Object[list1.size()][2];
            int i = 0;
            
            Iterator it = list1.iterator();
            while(it.hasNext()){
                messages[i] = (Object[])it.next();
                i++;
            }
            
            return messages;
		} catch(Exception e){
			handler.getException(e,"findMessages","user");
		}
		return messages;

	}
	
	/**
	 * @ejb.interface-method  
	 * @ejb.permission role-name = "systemaccess,administrator,resetpassword,accesscode,pepsico_candidatos,basf_assessment,clientreporter,cepareporter"
	 * @return Collection of LanguageData
	 * @throws CommunicationProblemException
	 */
	public Collection findAllLanguage() throws Exception {
		Collection languages=new LinkedList();
		try{
            Session session=HibernateAccess.currentSession();			
			Query query=session.createQuery("SELECT l FROM Language AS l");
			List result=query.list();
			Iterator iter=result.iterator();
			while(iter.hasNext()){
				Language lng=(Language)iter.next();
				languages.add(lng.getData());
			}
		} catch(Exception e){
			handler.getException(e,"findAllLanguages","login");
		}
		return languages;
	}
	
	/**
	 * @ejb.interface-method  
	 * @ejb.permission role-name = "systemaccess,administrator,resetpassword,accesscode,pepsico_candidatos,basf_assessment,clientreporter,cepareporter" 
	 * @return Collection of LanguageData
	 * @throws CommunicationProblemException
	 */
	public HashMap findAllNativeMessages() throws Exception{
		HashMap mapAux=new HashMap();	
		try{
		    Session session=HibernateAccess.currentSession();
			Query query=session.createQuery("SELECT gm FROM GeneralMessage AS gm WHERE gm.primaryKey.language.name = :language");
			String language="en";
			query.setParameter("language",language);
			List result=query.list();
			Iterator iter=result.iterator();
			while(iter.hasNext()){
				GeneralMessage msg=(GeneralMessage)iter.next();
				mapAux.put(msg.getPrimaryKey().getLabelKey(),msg.getText());
			}
		} catch(Exception e){
			handler.getException(e,"findAllNativeMessages","login");
		}
		return mapAux;
	}

    /**
     * @ejb.permission role-name="administrator,accesscode" 
     * @ejb.interface-method view-type = "remote"
     */
    public String findText(String key,String language,UserSessionData userSessionData) throws Exception {
        if(userSessionData == null) {
            throw new DeslogedException("findText","session = null");
        }
        try{
            Session session=HibernateAccess.currentSession();
            GeneralMessage gm = (GeneralMessage)session.load(GeneralMessage.class,new GeneralMessagePK(key,language));
            return gm.getText();
        }catch (Exception e) {
            handler.getException(e,"findText",userSessionData.getFilter().getLoginName());
        }
        return null;
    }


    /**
     * @ejb.permission role-name="systemaccess,administrator,accesscode" 
     * @ejb.interface-method view-type = "remote"
     */
    public String[] findTexts(String key,UserSessionData userSessionData) throws Exception {
        String[] texts = {key, key, key};
    	if(userSessionData == null) {
            throw new DeslogedException("findText","session = null");
        }
        try{
            Session session=HibernateAccess.currentSession();
            Query q = session.createSQLQuery("SELECT language, text FROM generalmessages WHERE labelkey = '"+key+"' AND language IN ('es','en','pt')").addScalar("language", Hibernate.STRING).addScalar("text", Hibernate.STRING);
            Iterator it = q.list().iterator();
            while(it.hasNext()) {
            	Object[] data = (Object[])it.next();
            	if(data[0].equals("es")) {
            		texts[0] = (String)data[1];
            	} else if(data[0].equals("en")) {
            		texts[1] = (String)data[1];
            	} else if(data[0].equals("pt")) {
            		texts[2] = (String)data[1];
            	}
            }
        }catch (Exception e) {
            handler.getException(e,"findText",userSessionData.getFilter().getLoginName());
        }
        return texts;
    }

    
    /**
     * @ejb.permission role-name="administrator" 
     * @ejb.interface-method view-type = "remote"
     */
    public String[] getCompleteText(String key, UserSessionData userSessionData) throws Exception {
        String[] texts = new String[3];
    	if(userSessionData == null) {
            throw new DeslogedException("getCompleteText","session = null");
        }
        try{
            Session session=HibernateAccess.currentSession();
            Query q = session.createSQLQuery("SELECT text,language FROM generalmessages WHERE labelkey = '"+key+"'").addScalar("text", Hibernate.STRING).addScalar("language", Hibernate.STRING);
            Iterator it = q.list().iterator();
            while(it.hasNext()) {
            	Object[] data = (Object[])it.next();
            	if(data[1].equals(GeneralMessage.SPANISH)) {
            		texts[0] = (String)data[0];
            	} else if(data[1].equals(GeneralMessage.ENGLISH)) {
            		texts[1] = (String)data[0];
            	}else if(data[1].equals(GeneralMessage.PORTUGUESE)) {
            		texts[2] = (String)data[0];
            	}
            }
        }catch (Exception e) {
            handler.getException(e,"getCompleteText",userSessionData.getFilter().getLoginName());
        }
        return texts;
    }
    
	/**
	 * @ejb.interface-method  
	 * @ejb.permission role-name = "systemaccess,administrator,resetpassword,accesscode,pepsico_candidatos,basf_assessment,clientreporter,cepareporter,webinar"
	 * @param locale
	 * @return Object[][]
	 * @throws InvalidDataException
	 * @throws CommunicationProblemException
	 */
	public Object[][] findMessagesbkp(Locale locale) throws Exception {
		if(locale==null){
			throw new InvalidDataException("findMessagesbkp","language = null");
		}
		Object[][] messages=null;
		try{
		    Session session=HibernateAccess.currentSession();
            Query query=session.createSQLQuery("SELECT labelkey,text FROM generalmessagesbkp WHERE language = '"+locale.getLanguage()+"'").addScalar("labelkey",Hibernate.STRING).addScalar("text",Hibernate.STRING);
            Collection list1 = query.list();
            
            messages=new Object[list1.size()][2];
            int i = 0;
            
            Iterator it = list1.iterator();
            while(it.hasNext()){
                messages[i] = (Object[])it.next();
                i++;
            }
            
            return messages;
		} catch(Exception e){
			handler.getException(e,"findMessagesbkp","user");
		}
		return messages;

	}
	
    /**
     * @ejb.permission role-name="systemaccess,administrator,accesscode" 
     * @ejb.interface-method view-type = "remote"
     */
    public String[] findTextsbkp(String key,UserSessionData userSessionData) throws Exception {
        String[] texts = {key, key, key};
    	if(userSessionData == null) {
            throw new DeslogedException("findTextsbkp","session = null");
        }
        try{
            Session session=HibernateAccess.currentSession();
            Query q = session.createSQLQuery("SELECT language, text FROM generalmessagesbkp WHERE labelkey = '"+key+"' AND language IN ('es','en','pt')").addScalar("language", Hibernate.STRING).addScalar("text", Hibernate.STRING);
            Iterator it = q.list().iterator();
            while(it.hasNext()) {
            	Object[] data = (Object[])it.next();
            	if(data[0].equals("es")) {
            		texts[0] = (String)data[1];
            	} else if(data[0].equals("en")) {
            		texts[1] = (String)data[1];
            	} else if(data[0].equals("pt")) {
            		texts[2] = (String)data[1];
            	}
            }
        }catch (Exception e) {
            handler.getException(e,"findTextsbkp",userSessionData.getFilter().getLoginName());
        }
        return texts;
    }
}
