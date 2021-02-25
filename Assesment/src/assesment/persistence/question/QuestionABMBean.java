package assesment.persistence.question;

import java.util.Iterator;

import javax.ejb.SessionBean;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import assesment.communication.administration.user.UserSessionData;
import assesment.communication.exception.DeslogedException;
import assesment.communication.exception.InvalidDataException;
import assesment.communication.question.AnswerData;
import assesment.communication.question.QuestionData;
import assesment.persistence.hibernate.HibernateAccess;
import assesment.persistence.question.tables.Answer;
import assesment.persistence.question.tables.GenericAnswer;
import assesment.persistence.question.tables.GenericQuestion;
import assesment.persistence.question.tables.Question;
import assesment.persistence.question.tables.Video;
import assesment.persistence.util.ExceptionHandler;

/**
 * @ejb.bean name="QuestionABM" display-name="Name for QuestionABM"
 *           description="Description for QuestionABM"
 *           jndi-name="ejb/QuestionABM" type="Stateless" view-type="remote"
 */
public abstract class QuestionABMBean implements SessionBean {

    private ExceptionHandler handler = new ExceptionHandler(QuestionABMBean.class);

    /**
     * Init transaction
     * @ejb.create-method
     * @ejb.permission role-name = "administrator"
     */
    public void ejbCreate() throws javax.ejb.CreateException {  }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     * Create a corporation  
     * @param data - Contains the data of the new corporation
     * @param userRequest - Logged user
     * @throws Exception
     */
    public Integer create(QuestionData questionData, UserSessionData userRequest,int type) throws Exception {
        if (userRequest == null) {
            throw new DeslogedException("create","session = null");
        }
        if (questionData == null) {
            throw new InvalidDataException("create","data = null");
        }
        try {
            Session session = HibernateAccess.currentSession();
            Integer id = null;
            if(type == QuestionData.NORMAL) {
                Question question = new Question(questionData);
                id = (Integer)session.save(question);
            }else {
                GenericQuestion question = new GenericQuestion(questionData);
                id = (Integer)session.save(question);
            }
            return id;
        } catch (Exception e) {
            handler.getException(e,"create",userRequest.getFilter().getLoginName());
        }
        return null;
    }
    
	/**
	 * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
	 */
	public void updateAttributes(QuestionData questionData,UserSessionData userSessionData,int type) throws Exception {
        try{       
            Session session = HibernateAccess.currentSession();
            if(type == QuestionData.NORMAL) {
                Question question = (Question) session.load(Question.class, questionData.getId());
                question.setData(questionData);
                session.update(question);
            }else {
                GenericQuestion question = (GenericQuestion) session.load(GenericQuestion.class, questionData.getId());
                question.setData(questionData);
                session.update(question);
            }
        } catch (Exception e) {
            handler.getException(e,"updateAttributes",userSessionData.getFilter().getLoginName());
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     */
    public void update(QuestionData questionData,UserSessionData userSessionData,int type) throws Exception {
        try{       
            Session session = HibernateAccess.currentSession();
            if(type == QuestionData.NORMAL) {
                Question question = (Question) session.load(Question.class, questionData.getId());
                question.setData(questionData);
                Iterator<Answer> it = question.getAnswerSet().iterator();
                while(it.hasNext()) {
                    if(it.next().getOrder() > questionData.getAnswerSize()) {
                        it.remove();
                    }
                }
                session.update(question);
            }else {
                GenericQuestion question = (GenericQuestion) session.load(GenericQuestion.class, questionData.getId());
                question.setData(questionData);
                Iterator<GenericAnswer> it = question.getAnswerSet().iterator();
                while(it.hasNext()) {
                    if(it.next().getOrder() > questionData.getAnswerSize()) {
                        it.remove();
                    }
                }
                session.update(question);
            }
        } catch (Exception e) {
            handler.getException(e,"update",userSessionData.getFilter().getLoginName());
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     */
    public void updateAnswerAttributes(AnswerData answerData,UserSessionData userSessionData,int type) throws Exception {
        try{       
            Session session = HibernateAccess.currentSession();
            if(type == QuestionData.NORMAL) {
                Answer answer = (Answer) session.load(Answer.class, answerData.getId());
                answer.setData(answerData);
                session.update(answer);
            }else {
                GenericAnswer answer = (GenericAnswer) session.load(GenericAnswer.class, answerData.getId());
                answer.setData(answerData);
                session.update(answer);
            }
        } catch (Exception e) {
            handler.getException(e,"updateAttributes",userSessionData.getFilter().getLoginName());
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     */
    public Integer createAnswer(AnswerData answerData,Integer questionId,UserSessionData userSessionData,int type) throws Exception {
        try{       
            Session session = HibernateAccess.currentSession();
            if(type == QuestionData.NORMAL) {
                Question question = (Question) session.load(Question.class, questionId);
                Answer answer = new Answer(answerData,question);
                return (Integer)session.save(answer);
            }else {
                GenericQuestion question = (GenericQuestion) session.load(GenericQuestion.class, questionId);
                GenericAnswer answer = new GenericAnswer(answerData,question);
                return (Integer)session.save(answer);
            }
        } catch (Exception e) {
            handler.getException(e,"createAnser",userSessionData.getFilter().getLoginName());
        }
        return null;
    }
    
    /**
     * @ejb.permission role-name = "administrator"
     * @ejb.interface-method view-type = "both"
     * @param data
     * @param userRequest
     */
    public void delete(Integer questionId, UserSessionData userSessionData, int type) throws Exception {
    	try{       
    		Session session = HibernateAccess.currentSession();
            if(type == QuestionData.NORMAL) {
                Question question = (Question) session.load(Question.class, questionId);
                session.delete(question);
            }else {
                GenericQuestion question = (GenericQuestion) session.load(GenericQuestion.class, questionId);
                session.delete(question);
            }
        } catch (Exception e) {
    		handler.getException(e,"delete",userSessionData.getFilter().getLoginName());
    	}
    }
    
    
    /**
     * @ejb.permission role-name = "administrator"
     * @ejb.interface-method view-type = "both"
     */
    public String validateDelete(Integer corporation) {
        Session session = HibernateAccess.currentSession();

        String queryStr = "SELECT count(*) FROM Assesment a WHERE a.corporation = (:corporation)";
        Query query = session.createQuery(queryStr);
        query.setParameter("corporation", corporation);
        if(((Integer)query.uniqueResult()).intValue() > 0) {
            return "corporation.delete.assesmentasociated";
        }
       
        return null;
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     */
    public void moveQuestion(Integer questionId, int action, UserSessionData userSessionData, int type) throws Exception {
        try{   
            Session session = HibernateAccess.currentSession();
            if(type == QuestionData.NORMAL) {
                Question question = (Question) session.load(Question.class, questionId);
                question.setOrder(new Integer(question.getOrder().intValue()+action));
                session.update(question);
            }else {
                GenericQuestion question = (GenericQuestion) session.load(GenericQuestion.class, questionId);
                question.setOrder(new Integer(question.getOrder().intValue()+action));
                session.update(question);
            }
        }catch (Exception e) {
            handler.getException(e,"moveQuestion",userSessionData.getFilter().getLoginName());
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     */
    public void updateGroup(Integer questionId, Integer group, UserSessionData userSessionData, int type) throws Exception {
        try{   
            Session session = HibernateAccess.currentSession();
            if(type == QuestionData.NORMAL) {
                Question question = (Question) session.load(Question.class, questionId);
                question.setGroupId(group);
                session.update(question);
            }else {
                GenericQuestion question = (GenericQuestion) session.load(GenericQuestion.class, questionId);
                question.setGroupId(group);
                session.update(question);
            }
        }catch (Exception e) {
            handler.getException(e,"updateGroup",userSessionData.getFilter().getLoginName());
        }
    }

    /**
     * @ejb.interface-method view-type = "remote"
     * @ejb.transaction type="Required"
     * @ejb.permission role-name = "administrator"
     */
    public Integer createVideo(String key, String language, UserSessionData userSessionData) throws Exception {
        try{   
            Session session = HibernateAccess.currentSession();
            Video video = new Video();
            video.setKey(key);
            video.setLanguage(language);
            return (Integer)session.save(video);
        }catch (Exception e) {
            handler.getException(e,"createVideo",userSessionData.getFilter().getLoginName());
        }
        return null;
    }
}