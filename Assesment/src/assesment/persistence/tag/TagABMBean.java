package assesment.persistence.tag;

import javax.ejb.SessionBean;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import assesment.communication.administration.user.UserSessionData;
import assesment.communication.exception.DeslogedException;
import assesment.communication.exception.InvalidDataException;
import assesment.persistence.hibernate.HibernateAccess;
import assesment.persistence.tag.tables.AnswerTag;
import assesment.persistence.tag.tables.AssesmentTag;
import assesment.persistence.util.ExceptionHandler;

/**
 * @ejb.bean name="TagABM" display-name="Name for TagABM"
 *           description="Description for TagABM"
 *           jndi-name="ejb/TagABM" type="Stateless" view-type="remote"
 */
public abstract class TagABMBean implements SessionBean {

    private ExceptionHandler handler = new ExceptionHandler(TagABMBean.class);

    /**
     * Init transaction
     * @ejb.create-method
     * @ejb.permission role-name = "administrator"
     */
    public void ejbCreate() throws javax.ejb.CreateException {  }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     * @throws Exception
     */
    public Integer associateAssesment(Integer tag, Integer assesment, Integer min, UserSessionData userSessionData) throws Exception {
        if (userSessionData == null) {
            throw new DeslogedException("associateAssesment","session = null");
        }
        if (assesment == null) {
            throw new InvalidDataException("associateAssesment","assesment = null");
        }
        if (tag == null) {
            throw new InvalidDataException("associateAssesment","tag = null");
        }
        try {
            Session session = HibernateAccess.currentSession();
            AssesmentTag assesmentTag = new AssesmentTag(assesment,tag,min,session);
            session.save(assesmentTag);
        } catch (Exception e) {
            handler.getException(e,"associateAssesment",userSessionData.getFilter().getLoginName());
        }
        return null;
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     * @throws Exception
     */
    public Integer associateAnswer(Integer tag, Integer answer, Integer value, UserSessionData userSessionData) throws Exception {
        if (userSessionData == null) {
            throw new DeslogedException("associateAnswer","session = null");
        }
        if (answer == null) {
            throw new InvalidDataException("associateAnswer","answer = null");
        }
        if (tag == null) {
            throw new InvalidDataException("associateAnswer","tag = null");
        }
        try {
            Session session = HibernateAccess.currentSession();
            AnswerTag answerTag = new AnswerTag(answer,tag,value,session);
            session.save(answerTag);
        } catch (Exception e) {
            handler.getException(e,"associateAnswer",userSessionData.getFilter().getLoginName());
        }
        return null;
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     * @throws Exception
     */
    public void desassociateAssesment(Integer tag, Integer assesment, UserSessionData userSessionData) throws Exception {
        if (userSessionData == null) {
            throw new DeslogedException("desassociateAssesment","session = null");
        }
        if (assesment == null) {
            throw new InvalidDataException("desassociateAssesment","assesment = null");
        }
        if (tag == null) {
            throw new InvalidDataException("desassociateAssesment","tag = null");
        }
        try {
            Session session = HibernateAccess.currentSession();
            Query q = session.createQuery("DELETE FROM AssesmentTag at WHERE at.pk.assesment = "+String.valueOf(assesment)+" AND at.pk.tag = "+String.valueOf(tag));
            q.executeUpdate();
        } catch (Exception e) {
            handler.getException(e,"desassociateAssesment",userSessionData.getFilter().getLoginName());
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     * @throws Exception
     */
    public void desassociateAssesmentQuestion(Integer tag, Integer assesment, UserSessionData userSessionData) throws Exception {
        if (userSessionData == null) {
            throw new DeslogedException("desassociateAssesmentQuestion","session = null");
        }
        if (assesment == null) {
            throw new InvalidDataException("desassociateAssesmentQuestion","assesment = null");
        }
        if (tag == null) {
            throw new InvalidDataException("associateAssesment","tag = null");
        }
        try {
            Session session = HibernateAccess.currentSession();
            Query q = session.createQuery("DELETE FROM AnswerTag at WHERE at.pk.tag = "+String.valueOf(tag)+" AND at.pk.answer IN (SELECT a.id FROM Answer a WHERE a.question IN (SELECT q.id FROM Question q WHERE q.module IN (SELECT m.id FROM Module m WHERE m.assesment = "+String.valueOf(assesment)+")))");
            q.executeUpdate();
        } catch (Exception e) {
            handler.getException(e,"desassociateAssesmentQuestion",userSessionData.getFilter().getLoginName());
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     * @throws Exception
     */
    public void desassociateQuestion(Integer tag, Integer question, UserSessionData userSessionData) throws Exception {
        if (userSessionData == null) {
            throw new DeslogedException("desassociateQuestion","session = null");
        }
        if (question == null) {
            throw new InvalidDataException("desassociateQuestion","question = null");
        }
        if (tag == null) {
            throw new InvalidDataException("desassociateQuestion","tag = null");
        }
        try {
            Session session = HibernateAccess.currentSession();
            Query q = session.createQuery("DELETE FROM AnswerTag at WHERE at.pk.tag = "+String.valueOf(tag)+" AND at.pk.answer IN (SELECT a.id FROM Answer a WHERE a.question = "+String.valueOf(question)+")");
            q.executeUpdate();
        } catch (Exception e) {
            handler.getException(e,"desassociateQuestion",userSessionData.getFilter().getLoginName());
        }
    }

    /**
     * @ejb.interface-method    view-type = "remote"
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "administrator"
     */
    public void updateAnswerTag(Integer tag, Integer answer, Integer value, UserSessionData userSessionData) throws Exception {
        if (userSessionData == null) {
            throw new DeslogedException("updateAnswerTag","session = null");
        }
        if (answer == null) {
            throw new InvalidDataException("updateAnswerTag","answer = null");
        }
        if (value == null) {
            throw new InvalidDataException("updateAnswerTag","tag = null");
        }
        if (tag == null) {
            throw new InvalidDataException("updateAnswerTag","tag = null");
        }
        try {
            Session session = HibernateAccess.currentSession();
            Query q = session.createQuery("SELECT at FROM AnswerTag at WHERE at.pk.tag = "+String.valueOf(tag)+" AND at.pk.answer = "+String.valueOf(answer));
            AnswerTag at = (AnswerTag) q.uniqueResult();
            at.setValue(value);
            session.save(at);
        } catch (Exception e) {
            handler.getException(e,"updateAnswerTag",userSessionData.getFilter().getLoginName());
        }
    }

}