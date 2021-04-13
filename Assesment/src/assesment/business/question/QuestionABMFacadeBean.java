
/*
 * Created on 22-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.business.question;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.SessionBean;

import assesment.business.util.ExceptionHandler;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.exception.CommunicationProblemException;
import assesment.communication.exception.DeslogedException;
import assesment.communication.exception.InvalidDataException;
import assesment.communication.module.ModuleData;
import assesment.communication.question.AnswerData;
import assesment.communication.question.QuestionData;
import assesment.persistence.language.LangABM;
import assesment.persistence.language.LangABMHome;
import assesment.persistence.language.LangABMUtil;
import assesment.persistence.language.LangReport;
import assesment.persistence.language.LangReportUtil;
import assesment.persistence.language.tables.GeneralMessage;
import assesment.persistence.module.ModuleReport;
import assesment.persistence.module.ModuleReportUtil;
import assesment.persistence.question.QuestionABM;
import assesment.persistence.question.QuestionABMHome;
import assesment.persistence.question.QuestionABMUtil;
import assesment.persistence.question.QuestionReport;
import assesment.persistence.question.QuestionReportUtil;

/**
 * @ejb.bean name="QuestionABMFacade"
 *           display-name="Name for Question"
 *           description="Description for Question"
 *           jndi-name="ejb/QuestionABMFacade"
 *           type="Stateless"
 *           view-type="remote"
 * 
 * @ejb.ejb-ref 
 *          ejb-name ="QuestionABM"
 *          ref-name = "ejb/QuestionABM"
 *          view-type ="remote"
 * 
 * @jboss.ejb-ref-jndi
 *          jndi-name = "ejb/QuestionABM" 
 *          ref-name = "QuestionABM"
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
 * @ejb.ejb-ref 
 *          ejb-name ="LangABM"
 *          ref-name = "ejb/LangABM"
 *          view-type ="remote"
 * 
 * @jboss.ejb-ref-jndi
 *          jndi-name = "ejb/LangABM" 
 *          ref-name = "LangABM"
 * 
 * @ejb.ejb-ref 
 *          ejb-name ="LangReport"
 *          ref-name = "ejb/LangReport"
 *          view-type ="remote"
 * 
 * @jboss.ejb-ref-jndi
 *          jndi-name = "ejb/LangReport" 
 *          ref-name = "LangReport"
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
 * @ejb.util generate="physical"
*/
public abstract class QuestionABMFacadeBean implements SessionBean {
	
    ExceptionHandler handler = new ExceptionHandler(QuestionABMFacadeBean.class);
    
	/**
	 * @ejb.create-method
     * @ejb.permission role-name = "administrator,systemaccess"
     * Create method
	 */
	public void ejbCreate()	throws javax.ejb.CreateException { }

	/**
	 * @ejb.interface-method	view-type = "remote"
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "administrator"
	 */
	public void create(QuestionData question,String[] questionTexts,String[][] answerTexts,UserSessionData userSessionData,int type) throws Exception{
		if (userSessionData == null) {
			throw new DeslogedException("create","session = null");
		}
		if (question == null) {
			throw new InvalidDataException("create","question = null");
		}
		try {
            
            QuestionABMHome home = QuestionABMUtil.getHome();
            QuestionABM questionABM = home.create();
            Integer id = questionABM.create(question, userSessionData,type);
            
            String questionType = (type == QuestionData.NORMAL) ? "" : "generic"; 
            question.setId(id);
            question.setKey(questionType+"module"+String.valueOf(question.getModule())+"."+questionType+"question"+String.valueOf(id)+".text");
            questionABM.updateAttributes(question,userSessionData,type);

            LangABMHome languageHome = LangABMUtil.getHome();
            LangABM languageABM = languageHome.create();
            languageABM.saveText(question.getKey(),GeneralMessage.SPANISH,questionTexts[0], userSessionData);
            languageABM.saveText(question.getKey(),GeneralMessage.ENGLISH,questionTexts[1], userSessionData);
            languageABM.saveText(question.getKey(),GeneralMessage.PORTUGUESE,questionTexts[2], userSessionData);

            QuestionReport questionReport = QuestionReportUtil.getHome().create();
            question = questionReport.findQuestion(id,userSessionData,type);
            
            if(question.getAnswers() != null) {
                Iterator it = question.getAnswerIterator();
                int index = 0;
                while(it.hasNext()) {
                    AnswerData answer = (AnswerData)it.next();
                    answer.setKey(questionType+"question"+String.valueOf(id)+"."+questionType+"answer"+String.valueOf(answer.getId())+".text");                    
                    languageABM.saveText(answer.getKey(),GeneralMessage.SPANISH,answerTexts[index][0], userSessionData);
                    languageABM.saveText(answer.getKey(),GeneralMessage.ENGLISH,answerTexts[index][1], userSessionData);
                    languageABM.saveText(answer.getKey(),GeneralMessage.PORTUGUESE,answerTexts[index][2], userSessionData);
                    questionABM.updateAnswerAttributes(answer,userSessionData,type);
                    index++;
                }
            }
            
		} catch (Exception e) {
            handler.handleException("create",e);
		}
	}

	/**
	 * @ejb.interface-method 
	 * 		view-type = "remote"
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "administrator"
	 * Modifies a corporation
	 * @param data Data of the corporation modified
	 * @throws InvalidDataException
	 * @throws CommunicationProblemException
	 * @throws DeslogedException
	 */
	public void update(QuestionData data,UserSessionData userSessionData) throws Exception{
	}

    /**
     * @ejb.interface-method view-type = "remote"
     * @ejb.transaction type="Required"
     * @ejb.permission role-name = "administrator"
     */
    public void delete(Integer id, UserSessionData userSessionData, int type) throws Exception {
    	try{   
    		if (userSessionData == null) {
    			throw new DeslogedException("delete","session = null");
    		}
    		if (id == null) {
    			throw new InvalidDataException("delete","id = null");
    		}
            QuestionABM questionABM = QuestionABMUtil.getHome().create();
           /* String msg = questionABM.validateDelete(id); 
            if(!Util.empty(msg)) {
               throw new InvalidDataException("delete",msg); 
            }*/
            questionABM.delete(id,userSessionData,type);
    	}catch (Exception e) {
    		handler.handleException("delete",e);
    	}
    }

    /**
     * @ejb.interface-method view-type = "remote"
     * @ejb.transaction type="Required"
     * @ejb.permission role-name = "administrator"
     */
    public void deleteList(Collection list, UserSessionData userSessionData, int type) throws Exception {
        try{   
            if (userSessionData == null) {
                throw new DeslogedException("deleteList","session = null");
            }
            if (list == null) {
                throw new InvalidDataException("deleteList","list = null");
            }
            QuestionABM questionABM = QuestionABMUtil.getHome().create();

            Iterator it = list.iterator();
            while(it.hasNext()) {
                Integer id = new Integer((String)it.next());
               /* String msg = questionABM.validateDelete(id); 
                if(!Util.empty(msg)) {
                   throw new InvalidDataException("delete",msg); 
                }*/
                questionABM.delete(id,userSessionData,type);
            }
        }catch (Exception e) {
            handler.handleException("deleteList",e);
        }
    }

    /**
     * @ejb.interface-method view-type = "remote"
     * @ejb.transaction type="Required"
     * @ejb.permission role-name = "administrator"
     */
    public void updateOrder(ModuleData module, UserSessionData userSessionData,int type) throws Exception {
        int index = 1;
        Iterator it = module.getQuestionIterator();
        QuestionABM questionABM = null;
        while(it.hasNext()) {
            QuestionData question = (QuestionData)it.next();
            if(question.getOrder().intValue() != index) {
                question.setOrder(new Integer(index));
                if(questionABM == null) {
                    questionABM = QuestionABMUtil.getHome().create();
                }
                questionABM.updateAttributes(question,userSessionData,type);
            }
            index++;
        }
    }
    
    /**
     * @ejb.interface-method view-type = "remote"
     * @ejb.transaction type="Required"
     * @ejb.permission role-name = "administrator"
     */
    public void update(QuestionData questionData,String[] questionTexts,String[][] answerTexts, UserSessionData userSessionData, int type) throws Exception {
        LangABM languageABM = LangABMUtil.getHome().create();
        
        languageABM.updateText(questionData.getKey(),GeneralMessage.SPANISH,questionTexts[0],userSessionData);
        languageABM.updateText(questionData.getKey(),GeneralMessage.ENGLISH,questionTexts[1],userSessionData);
        languageABM.updateText(questionData.getKey(),GeneralMessage.PORTUGUESE,questionTexts[2],userSessionData);
        
        QuestionABM questionABM = QuestionABMUtil.getHome().create();
        
        Iterator<AnswerData> it = questionData.getAnswerIterator();
        int index = 0;
        while(it.hasNext()) {
            AnswerData answer = it.next();
            if(answer.getId() != null) {
                languageABM.updateText(answer.getKey(),GeneralMessage.SPANISH,answerTexts[index][0],userSessionData);
                languageABM.updateText(answer.getKey(),GeneralMessage.ENGLISH,answerTexts[index][1],userSessionData);
                languageABM.updateText(answer.getKey(),GeneralMessage.PORTUGUESE,answerTexts[index][2],userSessionData);
                questionABM.updateAnswerAttributes(answer,userSessionData,type);
            }else {
                Integer answerId = questionABM.createAnswer(answer,questionData.getId(),userSessionData,type);
                answer.setId(answerId);
                String questionType = (type == QuestionData.GENERIC) ? "generic" : "";
                answer.setKey(questionType+"question"+String.valueOf(questionData.getId())+"."+questionType+"answer"+String.valueOf(answerId)+".text");                    
                languageABM.saveText(answer.getKey(),GeneralMessage.SPANISH,answerTexts[index][0], userSessionData);
                languageABM.saveText(answer.getKey(),GeneralMessage.ENGLISH,answerTexts[index][1], userSessionData);
                languageABM.saveText(answer.getKey(),GeneralMessage.PORTUGUESE,answerTexts[index][2], userSessionData);
                questionABM.updateAnswerAttributes(answer,userSessionData,type);
            }
            index++;
        }
        questionABM.update(questionData,userSessionData,type);
    }
    
    /**
     * @ejb.interface-method view-type = "remote"
     * @ejb.transaction type="Required"
     * @ejb.permission role-name = "administrator"
     */
    public void copyQuestion(Integer module,Collection list, UserSessionData userSessionData) throws Exception {
        ModuleReport moduleReport = ModuleReportUtil.getHome().create();
        ModuleData moduleData = moduleReport.findModule(module,userSessionData);
        int index = moduleData.getQuestionSize() + 1;
        
        QuestionReport questionReport = QuestionReportUtil.getHome().create();
        Iterator questions = list.iterator();
        Collection<QuestionData> queryList = new LinkedList<QuestionData>();
        while(questions.hasNext()) {
            queryList.add(questionReport.findQuestion(new Integer((String)questions.next()),userSessionData, QuestionData.GENERIC));
        }

        LangReport languageReport = LangReportUtil.getHome().create();
        Collections.sort((List)queryList);
        questions = queryList.iterator();
        while(questions.hasNext()) {
            QuestionData questionData = (QuestionData)questions.next();
            String[] questionTexts = getTexts(questionData.getKey(),languageReport,userSessionData);
            
            Collection<AnswerData> newAnswers = new LinkedList<AnswerData>();
            String[][] answerTexts = null;
            if(questionData.getType().intValue() == QuestionData.EXCLUDED_OPTIONS
                    || questionData.getType().intValue() == QuestionData.INCLUDED_OPTIONS  
                    || questionData.getType().intValue() == QuestionData.LIST) {  
                answerTexts = new String[questionData.getAnswers().size()][3];
                int answerIndex = 0;
                Iterator answers = questionData.getAnswerIterator();
                while(answers.hasNext()) {
                    AnswerData answerData = (AnswerData)answers.next();
                    answerTexts[answerIndex] = getTexts(answerData.getKey(),languageReport,userSessionData);
                    newAnswers.add(new AnswerData(null,"",new Integer(answerIndex+1),answerData.getResultType(), answerData.getPoints()));
                    answerIndex++;
                }
            }else {
                answerTexts = new String[0][0];
            }
            
            QuestionData newQuestion = new QuestionData(null,"",module,index,questionData.getType(),questionData.getImage(),questionData.getTestType(),questionData.getGroup(),newAnswers);
            create(newQuestion,questionTexts,answerTexts,userSessionData,QuestionData.NORMAL);
            index++;
        }
    }

    private String[] getTexts(String key, LangReport languageReport, UserSessionData userSessionData) throws Exception{
        String[] s = new String[3];
        s[0] = languageReport.findText(key,GeneralMessage.SPANISH,userSessionData);
        s[1] = languageReport.findText(key,GeneralMessage.ENGLISH,userSessionData);
        s[2] = languageReport.findText(key,GeneralMessage.PORTUGUESE,userSessionData);
        return s;
    }

    /**
     * @ejb.interface-method view-type = "remote"
     * @ejb.transaction type="Required"
     * @ejb.permission role-name = "administrator"
     */
    public void moveQuestion(Integer id, int action, UserSessionData userSessionData,int type) throws Exception {
        try{   
            if (userSessionData == null) {
                throw new DeslogedException("moveQuestion","session = null");
            }
            if (id == null) {
                throw new InvalidDataException("moveQuestion","id = null");
            }
            QuestionABM questionABM = QuestionABMUtil.getHome().create();
            questionABM.moveQuestion(id,action,userSessionData,type);
        }catch (Exception e) {
            handler.handleException("moveQuestion",e);
        }
    }

    /**
     * @ejb.interface-method view-type = "remote"
     * @ejb.transaction type="Required"
     * @ejb.permission role-name = "administrator"
     */
    public void updateGroups(Collection changes, UserSessionData userSessionData, int type) throws Exception {
        try{   
            if (userSessionData == null) {
                throw new DeslogedException("updateGroups","session = null");
            }
            if (changes == null) {
                throw new InvalidDataException("updateGroups","changes = null");
            }
            QuestionABM questionABM = QuestionABMUtil.getHome().create();
            Iterator it = changes.iterator();
            while(it.hasNext()) {
                Integer[] elements = (Integer[])it.next();
                questionABM.updateGroup(elements[0],elements[1],userSessionData,type);
            }
        }catch (Exception e) {
            handler.handleException("updateGroups",e);
        }
    }

    /**
     * @ejb.interface-method view-type = "remote"
     * @ejb.transaction type="Required"
     * @ejb.permission role-name = "administrator"
     */
    public Integer createVideo(String key, String language, UserSessionData userSessionData) throws Exception {
        try{   
            if (userSessionData == null) {
                throw new DeslogedException("updateGroups","session = null");
            }
            return QuestionABMUtil.getHome().create().createVideo(key, language, userSessionData);
        }catch (Exception e) {
            handler.handleException("createVideo",e);
        }
        return null;
    }
}