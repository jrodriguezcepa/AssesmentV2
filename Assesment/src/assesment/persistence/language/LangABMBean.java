package assesment.persistence.language;

import javax.ejb.SessionBean;

import org.hibernate.classic.Session;

import assesment.communication.administration.user.UserSessionData;
import assesment.communication.exception.DeslogedException;
import assesment.persistence.hibernate.HibernateAccess;
import assesment.persistence.language.tables.GeneralMessage;
import assesment.persistence.language.tables.GeneralMessagePK;
import assesment.persistence.util.ExceptionHandler;

/**
 * @ejb.bean name="LangABM" display-name="Name for LangABM"
 *           description="Description for LangABM"
 *           jndi-name="ejb/LangABM" type="Stateless" view-type="remote"
 */
public abstract class LangABMBean implements SessionBean {

    private ExceptionHandler handler = new ExceptionHandler(LangABMBean.class);

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
    public void saveText(String key,String language, String text, UserSessionData userSessionData) throws Exception {
        if (userSessionData == null) {
            throw new DeslogedException("saveText","session = null");
        }
        try {
            Session session = HibernateAccess.currentSession();
            GeneralMessage message = new GeneralMessage(key,language,text);
            session.saveOrUpdate(message);
        } catch (Exception e) {
            handler.getException(e,"saveText",userSessionData.getFilter().getLoginName());
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     */
    public void updateText(String key,String language, String text, UserSessionData userSessionData) throws Exception {
        if (userSessionData == null) {
            throw new DeslogedException("updateText","session = null");
        }
        try {
            Session session = HibernateAccess.currentSession();
            GeneralMessage message = (GeneralMessage)session.load(GeneralMessage.class, new GeneralMessagePK(key,language));
            message.setText(text);
            session.update(message);
        } catch (Exception e) {
            handler.getException(e,"updateText",userSessionData.getFilter().getLoginName());
        }
    }
}