package assesment.persistence.tag;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import javax.ejb.SessionBean;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import assesment.communication.administration.user.UserSessionData;
import assesment.communication.exception.DeslogedException;
import assesment.communication.language.Text;
import assesment.communication.tag.TagData;
import assesment.persistence.hibernate.HibernateAccess;
import assesment.persistence.tag.tables.AnswerTag;
import assesment.persistence.tag.tables.AssesmentTag;
import assesment.persistence.tag.tables.Tag;
import assesment.persistence.util.ExceptionHandler;

/**
 * @ejb.bean name="TagReport" display-name="Name for TagReport"
 *           description="Description for TagReport"
 *           jndi-name="ejb/TagReport" type="Stateless" view-type="remote"
 */
public abstract class TagReportBean implements SessionBean {

    private ExceptionHandler handler = new ExceptionHandler(TagReportBean.class);

    /**
     * Init transaction
     * @ejb.create-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public void ejbCreate() throws javax.ejb.CreateException {  }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     * @throws Exception
     */
    public Collection getListNotAssociated(Integer assesment,Text messages,UserSessionData userSessionData) throws Exception {
        Collection list = new LinkedList();
        if (userSessionData == null) {
            throw new DeslogedException("getListNotAssociated","session = null");
        }
        try {
            Session session = HibernateAccess.currentSession();
            Query q = session.createQuery("SELECT t FROM Tag t WHERE t NOT IN (SELECT at.pk.tag FROM AssesmentTag at WHERE at.pk.assesment = "+String.valueOf(assesment)+")");
            Iterator it = q.list().iterator();
            while(it.hasNext()) {
            	TagData tagData = ((Tag)it.next()).getData();
            	tagData.setText(messages);
                list.add(tagData);
            }
        } catch (Exception e) {
            handler.getException(e,"getListNotAssociated",userSessionData.getFilter().getLoginName());
        }
        return list;
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     * @throws Exception
     */
    public Collection getListAssociated(Integer assesment,UserSessionData userSessionData) throws Exception {
        Collection list = new LinkedList();
        if (userSessionData == null) {
            throw new DeslogedException("getListAssociated","session = null");
        }
        try {
            Session session = HibernateAccess.currentSession();
            Query q = session.createQuery("SELECT at FROM AssesmentTag at WHERE at.pk.assesment = "+String.valueOf(assesment));
            Iterator it = q.list().iterator();
            while(it.hasNext()) {
                list.add(((AssesmentTag)it.next()).getData());
            }
        } catch (Exception e) {
            handler.getException(e,"getListAssociated",userSessionData.getFilter().getLoginName());
        }
        return list;
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     * @throws Exception
     */
    public Collection getListNotAssociatedQuestion(Integer assesment,Integer question,UserSessionData userSessionData) throws Exception {
        Collection list = new LinkedList();
        if (userSessionData == null) {
            throw new DeslogedException("getListNotAssociatedQuestion","session = null");
        }
        try {
            Session session = HibernateAccess.currentSession();
            Query q = session.createQuery("SELECT t FROM Tag t WHERE t IN (SELECT at.pk.tag FROM AssesmentTag at WHERE at.pk.assesment = "+String.valueOf(assesment)+") AND t NOT IN (SELECT at.pk.tag FROM AnswerTag at WHERE at.pk.answer IN (SELECT a.id FROM Answer a WHERE a.question = "+String.valueOf(question)+"))");
            Iterator it = q.list().iterator();
            while(it.hasNext()) {
                list.add(((Tag)it.next()).getData());
            }
        } catch (Exception e) {
            handler.getException(e,"getListNotAssociatedQuestion",userSessionData.getFilter().getLoginName());
        }
        return list;
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     * @throws Exception
     */
    public Collection getListAssociatedQuestion(Integer question,UserSessionData userSessionData) throws Exception {
        Collection list = new LinkedList();
        if (userSessionData == null) {
            throw new DeslogedException("getListAssociatedQuestion","session = null");
        }
        try {
            Session session = HibernateAccess.currentSession();
            Query q = session.createQuery("SELECT at FROM AnswerTag at WHERE at.pk.answer IN (SELECT a.id FROM Answer a WHERE a.question = "+String.valueOf(question)+")");
            Iterator it = q.list().iterator();
            while(it.hasNext()) {
                list.add(((AnswerTag)it.next()).getData());
            }
        } catch (Exception e) {
            handler.getException(e,"getListAssociatedQuestion",userSessionData.getFilter().getLoginName());
        }
        return list;
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     * @throws Exception
     */
    public String[] getAnswerTag(Integer tag,Integer answer,UserSessionData userSessionData) throws Exception {
        String[] s = {"","","",""};
        try {
            Session session = HibernateAccess.currentSession();
            Query q = session.createQuery("SELECT at FROM AnswerTag at WHERE at.pk.tag = "+String.valueOf(tag)+" AND at.pk.answer = "+String.valueOf(answer));
            AnswerTag at = (AnswerTag) q.uniqueResult();
            s[0] = at.getPk().getAnswer().getQuestion().getKey();
            s[1] = at.getPk().getAnswer().getKey();
            s[2] = "assesment.elearning.lesson"+String.valueOf(at.getPk().getTag().getId());
            s[3] = String.valueOf(at.getValue());
        } catch (Exception e) {
            handler.getException(e,"getListAssociatedQuestion",userSessionData.getFilter().getLoginName());
        }
        return s;
    }

    /**
     * @ejb.interface-method    view-type = "remote"
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public Collection getAssociatedLessons(String user,Integer assesment,UserSessionData userSessionData) throws Exception {
        Collection lessons = new LinkedList();
        try {
            Session session = HibernateAccess.currentSession();
            String sql = 
            "SELECT CASE WHEN u.language='es' THEN t.lessones " +
            			"WHEN u.language='pt' THEN t.lessonpt " +
            			"WHEN u.language='en' THEN t.lessonen " +
            			"END as lessons "+
            "FROM ( SELECT ant.tag as tag FROM answerdata ad " +
            		"JOIN useranswers ua ON ad.id = ua.answer " +
            		"JOIN answertags ant ON ad.answer = ant.answer " +
            		"JOIN assesmenttags ast ON ant.tag = ast.tag " +
            		"WHERE loginname = '"+user+"' " +
            		"AND ua.assesment =  " + String.valueOf(assesment) + " " +
            		"AND ast.assesment = " + String.valueOf(assesment) + " " +
            		"GROUP BY ant.tag,ast.min " +
            		"HAVING sum(ant.value) >= ast.min " +
            ")p, users u, tags t "+
            "WHERE u.loginname = '"+user+"' and t.id = p.tag "; 
            Query query = session.createSQLQuery(sql).addScalar("lessons",Hibernate.INTEGER);
            lessons.addAll(query.list());
        }catch(Exception e) {
            e.printStackTrace();
        }
        return lessons;
    }

    /**
     * @ejb.interface-method    view-type = "remote"
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public Collection getTextAssociatedLessons(String user,Integer assesment,UserSessionData userSessionData) throws Exception {
        Collection lessons = new LinkedList();
        try {
            Session session = HibernateAccess.currentSession();
            String sql = 
            "SELECT ant.tag as tag FROM answerdata ad " +
            		"JOIN useranswers ua ON ad.id = ua.answer " +
            		"JOIN answertags ant ON ad.answer = ant.answer " +
            		"JOIN assesmenttags ast ON ant.tag = ast.tag " +
            		"WHERE loginname = '"+user+"' " +
            		"AND ua.assesment =  " + String.valueOf(assesment) + " " +
            		"AND ast.assesment = " + String.valueOf(assesment) + " " +
            		"GROUP BY ant.tag,ast.min " +
            		"HAVING sum(ant.value) >= ast.min "; 
            Query query = session.createSQLQuery(sql).addScalar("tag",Hibernate.INTEGER);
            lessons.addAll(query.list());
        }catch(Exception e) {
            e.printStackTrace();
        }
        return lessons;
    }
}