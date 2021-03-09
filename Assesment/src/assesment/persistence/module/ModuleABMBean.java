package assesment.persistence.module;

import java.util.Iterator;

import javax.ejb.SessionBean;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import assesment.communication.administration.user.UserSessionData;
import assesment.communication.exception.DeslogedException;
import assesment.communication.exception.InvalidDataException;
import assesment.communication.module.ModuleAttribute;
import assesment.communication.module.ModuleData;
import assesment.communication.question.QuestionData;
import assesment.persistence.hibernate.HibernateAccess;
import assesment.persistence.module.tables.GenericModule;
import assesment.persistence.module.tables.Module;
import assesment.persistence.question.tables.Answer;
import assesment.persistence.question.tables.GenericAnswer;
import assesment.persistence.question.tables.GenericQuestion;
import assesment.persistence.question.tables.Question;
import assesment.persistence.util.ExceptionHandler;
import assesment.persistence.util.PersistenceUtil;

/**
 * @ejb.bean name="ModuleABM" display-name="Name for ModuleABM"
 *           description="Description for ModuleABM"
 *           jndi-name="ejb/ModuleABM" type="Stateless" view-type="remote"
 */
public abstract class ModuleABMBean implements SessionBean {

    private ExceptionHandler handler = new ExceptionHandler(ModuleABMBean.class);

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
    public Integer create(ModuleAttribute attributes, UserSessionData userRequest) throws Exception {
        if (userRequest == null) {
            throw new DeslogedException("create","session = null");
        }
        if (attributes == null) {
            throw new InvalidDataException("create","data = null");
        }
        if (attributes.getOrder() == null) {
            throw new InvalidDataException("create","module.order.invalid");
        }
        try {
            Session session = HibernateAccess.currentSession();
            Module module = new Module(attributes);
            Integer id = (Integer)session.save(module);
            return id;
        } catch (Exception e) {
            handler.getException(e,"create",userRequest.getFilter().getLoginName());
        }
        return null;
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     * Create a corporation  
     * @param data - Contains the data of the new corporation
     * @param userRequest - Logged user
     * @throws Exception
     */
    public Integer createGeneric(ModuleAttribute attributes, UserSessionData userRequest) throws Exception {
        if (userRequest == null) {
            throw new DeslogedException("createGeneric","session = null");
        }
        if (attributes == null) {
            throw new InvalidDataException("createGeneric","data = null");
        }
        try {
            Session session = HibernateAccess.currentSession();
            GenericModule module = new GenericModule(attributes);
            Integer id = (Integer)session.save(module);
            return id;
        } catch (Exception e) {
            handler.getException(e,"createGeneric",userRequest.getFilter().getLoginName());
        }
        return null;
    }
    
	/**
	 * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
	 * Modify a corporation
	 * @param data - Data with the change of the corporation
	 * @param userRequest - Logged user
     * @throws Exception
	 */
	public void update(ModuleAttribute attributes,UserSessionData userSessionData,int generic) throws Exception {
        if (userSessionData == null) {
            throw new DeslogedException("update","session = null");
        }
		if (attributes == null) {
			throw new InvalidDataException("update","generic.error.emptydata");
		}
        if (PersistenceUtil.empty(attributes.getKey())) {
            throw new InvalidDataException("update","assesment.name.invalid");
        }
        try {
            Session session = HibernateAccess.currentSession();
            if(generic == ModuleData.NORMAL) {
                if (attributes.getOrder() == null) {
                    throw new InvalidDataException("update","assesment.name.invalid");
                }
                Module module = (Module)session.load(Module.class,attributes.getId());
                module.setKey(attributes.getKey());
                module.setOrder(attributes.getOrder());
                module.setType(attributes.getType());
                module.setGreen(attributes.getGreen());
                module.setYellow(attributes.getYellow());
                session.update(module);
            }else {
                GenericModule module = (GenericModule)session.load(GenericModule.class,attributes.getId());
                module.setKey(attributes.getKey());
                module.setType(attributes.getType());
                session.update(module);
            }
        } catch (Exception e) {
            handler.getException(e,"update",userSessionData.getFilter().getLoginName());
        }
    }

    /**
     * @ejb.permission role-name = "administrator"
     * @ejb.interface-method view-type = "both"
     * @param data
     * @param userRequest
     */
    public void delete(Integer moduleId, UserSessionData userSessionData) throws Exception {
    	try{       
    		Session session = HibernateAccess.currentSession();
            Module module = (Module) session.load(Module.class, moduleId);            
            session.delete(module);
        } catch (Exception e) {
    		handler.getException(e,"delete",userSessionData.getFilter().getLoginName());
    	}
    }
    
    /**
     * @ejb.permission role-name = "administrator"
     * @ejb.interface-method view-type = "both"
     * @param data
     * @param userRequest
     */
    public void deleteAll(Integer moduleId, UserSessionData userSessionData, int type) throws Exception {
        try{       
            Session session = HibernateAccess.currentSession();
            if(type == QuestionData.NORMAL) {
                Module module = (Module) session.load(Module.class, moduleId);
                Iterator<Question> itQuestion = module.getQuestionSet().iterator();
                while(itQuestion.hasNext()) {
                    Question question = itQuestion.next();
                    Iterator<Answer> itAnswer = question.getAnswerSet().iterator();
                    while(itAnswer.hasNext()) {
                        session.delete(itAnswer.next());
                    }
                    session.delete(question);
                }
                session.delete(module);
            }else {
                GenericModule module = (GenericModule) session.load(GenericModule.class, moduleId);
                Iterator<GenericQuestion> itQuestion = module.getQuestionSet().iterator();
                while(itQuestion.hasNext()) {
                    GenericQuestion question = itQuestion.next();
                    Iterator<GenericAnswer> itAnswer = question.getAnswerSet().iterator();
                    while(itAnswer.hasNext()) {
                        session.delete(itAnswer.next());
                    }
                    session.delete(question);
                }
                session.delete(module);
            }
        } catch (Exception e) {
            handler.getException(e,"deleteAll",userSessionData.getFilter().getLoginName());
        }
    }
    
    /**
     * @ejb.permission role-name = "administrator"
     * @ejb.interface-method view-type = "both"
     */
    public String validateDelete(Integer module,int type) {
        Session session = HibernateAccess.currentSession();

        String queryStr = "SELECT COUNT(*) AS count FROM answerdata ad JOIN questions AS q ON ad.question = q.id WHERE q.module = "+String.valueOf(module);
        Query query = session.createSQLQuery(queryStr).addScalar("count",Hibernate.INTEGER);
        if(((Integer)query.uniqueResult()).intValue() > 0) {
            return "module.delete.questionanswered";
        }
       
        return null;
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     */
    public void moveModule(Integer moduleId, int action, UserSessionData userSessionData) throws Exception {
        try{   
            Session session = HibernateAccess.currentSession();
            Module module = (Module) session.load(Module.class, moduleId);
            module.setOrder(new Integer(module.getOrder().intValue()+action));
            session.update(module);
        }catch (Exception e) {
            handler.getException(e,"moveModule",userSessionData.getFilter().getLoginName());
        }
    }
}