
/*
 * Created on 22-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.business.module;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import javax.ejb.SessionBean; 

import assesment.business.question.QuestionABMFacade;
import assesment.business.question.QuestionABMFacadeUtil;
import assesment.business.util.ExceptionHandler;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.exception.CommunicationProblemException;
import assesment.communication.exception.DeslogedException;
import assesment.communication.exception.InvalidDataException;
import assesment.communication.module.ModuleAttribute;
import assesment.communication.module.ModuleData;
import assesment.communication.question.AnswerData;
import assesment.communication.question.QuestionData;
import assesment.persistence.assesment.AssesmentReport;
import assesment.persistence.assesment.AssesmentReportHome;
import assesment.persistence.assesment.AssesmentReportUtil;
import assesment.persistence.language.LangABM;
import assesment.persistence.language.LangABMHome;
import assesment.persistence.language.LangABMUtil;
import assesment.persistence.language.LangReport;
import assesment.persistence.language.LangReportUtil;
import assesment.persistence.language.tables.GeneralMessage;
import assesment.persistence.module.ModuleABM;
import assesment.persistence.module.ModuleABMHome;
import assesment.persistence.module.ModuleABMUtil;
import assesment.persistence.module.ModuleReport;
import assesment.persistence.module.ModuleReportUtil;
import assesment.persistence.util.PersistenceUtil;

/**
 * @ejb.bean name="ModuleABMFacade"
 *           display-name="Name for Module"
 *           description="Description for Module"
 *           jndi-name="ejb/ModuleABMFacade"
 *           type="Stateless"
 *           view-type="remote"
 * 
 * @ejb.ejb-ref 
 * 			ejb-name ="ModuleABM"
 * 			ref-name = "ejb/ModuleABM"
 * 			view-type ="remote"
 * 
 * @jboss.ejb-ref-jndi
 * 			jndi-name = "ejb/ModuleABM" 
 * 			ref-name = "ModuleABM"
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
 * @ejb.ejb-ref 
 *          ejb-name ="QuestionABMFacade"
 *          ref-name = "ejb/QuestionABMFacade"
 *          view-type ="remote"
 * 
 * @jboss.ejb-ref-jndi
 *          jndi-name = "ejb/QuestionABMFacade" 
 *          ref-name = "QuestionABMFacade"
 * 
 * @ejb.ejb-ref 
 *          ejb-name ="AssesmentReport"
 *          ref-name = "ejb/AssesmentReport"
 *          view-type ="remote"
 * 
 * @jboss.ejb-ref-jndi
 *          jndi-name = "ejb/AssesmentReport" 
 *          ref-name = "AssesmentReport"
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
 * @ejb.util generate="physical"
*/
public abstract class ModuleABMFacadeBean implements SessionBean {
	
    ExceptionHandler handler = new ExceptionHandler(ModuleABMFacadeBean.class);
    
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
	public Integer create(String[] texts,Integer assesment, Integer type, Integer green, Integer yellow, UserSessionData userSessionData) throws Exception{
		if (userSessionData == null) {
			throw new DeslogedException("create","session = null");
		}
		if (assesment == null) {
			throw new InvalidDataException("create","assesment = null");
		}
		try {
            
            AssesmentReportHome assesmentHome = AssesmentReportUtil.getHome();
            AssesmentReport assesmentReport = assesmentHome.create();
            int size = assesmentReport.findAssesment(assesment,userSessionData).getModules().size();

            ModuleABMHome home = ModuleABMUtil.getHome();
            ModuleABM moduleABM = home.create();
            ModuleAttribute data = new ModuleAttribute(null,"",new Integer(size+1), assesment, type, green, yellow);
            Integer id = moduleABM.create(data, userSessionData);

            data.setId(id);
            String key =  "assesment"+String.valueOf(assesment)+".module"+String.valueOf(id)+".name";
            data.setKey(key);
            moduleABM.update(data, userSessionData, ModuleData.NORMAL);
            
            LangABMHome languageHome = LangABMUtil.getHome();
            LangABM languageABM = languageHome.create();
            languageABM.saveText(key,"es",texts[0], userSessionData);
            languageABM.saveText(key,"en",texts[1], userSessionData);
            languageABM.saveText(key,"pt",texts[2], userSessionData);

            return id;
		} catch (Exception e) {
            handler.handleException("create",e);
		}
        return null;
	}

    /**
     * @ejb.interface-method    view-type = "remote"
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "administrator"
     */
    public Integer create(String[] texts,Integer assesment,Integer type,Integer green, Integer yellow, int size,UserSessionData userSessionData) throws Exception{
        if (userSessionData == null) {
            throw new DeslogedException("create","session = null");
        }
        if (assesment == null) {
            throw new InvalidDataException("create","assesment = null");
        }
        try {
            
            ModuleABMHome home = ModuleABMUtil.getHome();
            ModuleABM moduleABM = home.create();
            ModuleAttribute data = new ModuleAttribute(null, "", new Integer(size+1), assesment, type, green, yellow);
            Integer id = moduleABM.create(data, userSessionData);

            data.setId(id);
            String key =  "assesment"+String.valueOf(assesment)+".module"+String.valueOf(id)+".name";
            data.setKey(key);
            moduleABM.update(data, userSessionData, ModuleData.NORMAL);
            
            LangABMHome languageHome = LangABMUtil.getHome();
            LangABM languageABM = languageHome.create();
            languageABM.saveText(key,"es",texts[0], userSessionData);
            languageABM.saveText(key,"en",texts[1], userSessionData);
            languageABM.saveText(key,"pt",texts[2], userSessionData);

            return id;
        } catch (Exception e) {
            handler.handleException("create",e);
        }
        return null;
    }

    /**
     * @ejb.interface-method    view-type = "remote"
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "administrator"
     */
    public void createGeneric(String[] texts,Integer type,UserSessionData userSessionData) throws Exception{
        if (userSessionData == null) {
            throw new DeslogedException("createGeneric","session = null");
        }
        try {
            
            ModuleABMHome home = ModuleABMUtil.getHome();
            ModuleABM moduleABM = home.create();
            ModuleAttribute data = new ModuleAttribute(null,"",null,null,type,null,null);
            Integer id = moduleABM.createGeneric(data, userSessionData);

            data.setId(id);
            String key =  "generic.module"+String.valueOf(id)+".name";
            data.setKey(key);
            moduleABM.update(data, userSessionData, ModuleData.GENERIC);
            
            LangABMHome languageHome = LangABMUtil.getHome();
            LangABM languageABM = languageHome.create();
            languageABM.saveText(key,"es",texts[0], userSessionData);
            languageABM.saveText(key,"en",texts[1], userSessionData);
            languageABM.saveText(key,"pt",texts[2], userSessionData);

        } catch (Exception e) {
            handler.handleException("createGeneric",e);
        }
    }

	/**
	 * @ejb.interface-method 
	 * 		view-type = "remote"
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "administrator,corporationmodify"
	 * Modifies a corporation
	 * @param data Data of the corporation modified
	 * @throws InvalidDataException
	 * @throws CommunicationProblemException
	 * @throws DeslogedException
	 */
	public void update(ModuleAttribute data,UserSessionData userSessionData,int generic) throws Exception{
		if (userSessionData == null) {
			throw new DeslogedException("update","session = null");
		}
		if (data == null) {
			throw new InvalidDataException("update","data = null");
		}
		if (!validate(data)) {
			throw new InvalidDataException("update","corporation.name.invalid");
		}
		try {
            ModuleABMHome home = ModuleABMUtil.getHome();
            ModuleABM moduleABM = home.create();
            moduleABM.update(data,userSessionData,generic);
        } catch (Exception e) {
            handler.handleException("update",e);
        }
	}

    /**
     * Validate if the name of the corporation data is not empty
     * @param data - Data of the corporation
     * @return if is valid or not
     */
	private boolean validate(ModuleAttribute data){
		return !PersistenceUtil.empty(data.getKey());
	}

    /**
     * @ejb.interface-method view-type = "remote"
     * @ejb.transaction type="Required"
     * @ejb.permission role-name = "administrator"
     */
    public void delete(Integer id, UserSessionData userSessionData) throws Exception {
    	try{   
    		if (userSessionData == null) {
    			throw new DeslogedException("delete","session = null");
    		}
    		if (id == null) {
    			throw new InvalidDataException("delete","id = null");
    		}
            ModuleABM moduleABM = ModuleABMUtil.getHome().create();
            String msg = moduleABM.validateDelete(id,QuestionData.NORMAL); 
            if(!PersistenceUtil.empty(msg)) {
               throw new InvalidDataException("delete",msg); 
            }
            moduleABM.deleteAll(id,userSessionData,QuestionData.NORMAL);
    	}catch (Exception e) {
    		handler.handleException("delete",e);
    	}
    }
    
    /**
     * @ejb.interface-method view-type = "remote"
     * @ejb.transaction type="Required"
     * @ejb.permission role-name = "administrator"
     */
    public void deleteGeneric(Collection list, UserSessionData userSessionData) throws Exception {
        try{   
            if (userSessionData == null) {
                throw new DeslogedException("deleteGeneric","session = null");
            }
            if (list == null) {
                throw new InvalidDataException("deleteGeneric","list = null");
            }
            ModuleABM moduleABM = ModuleABMUtil.getHome().create();
            Iterator it = list.iterator();
            while(it.hasNext()) {
                Integer id = new Integer((String)it.next());
                moduleABM.deleteAll(id,userSessionData,QuestionData.GENERIC);
            }
        }catch (Exception e) {
            handler.handleException("deleteGeneric",e);
        }
    }
    
    /**
     * @ejb.interface-method view-type = "remote"
     * @ejb.transaction type="Required"
     * @ejb.permission role-name = "administrator"
     */
    public void updateOrder(AssesmentData assesment, UserSessionData userSessionData) throws Exception {
        int index = 1;
        Iterator it = assesment.getModuleIterator();
        ModuleABM moduleABM = null;
        while(it.hasNext()) {
            ModuleData module = (ModuleData)it.next();
            if(module.getOrder().intValue() != index) {
                module.setOrder(new Integer(index));
                if(moduleABM == null) {
                    moduleABM = ModuleABMUtil.getHome().create();
                }
                moduleABM.update(module,userSessionData,ModuleData.NORMAL);
            }
            index++;
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     */
    public void copyModule(Integer assesment,Collection genericModules,UserSessionData userSessionData) throws Exception {
        ModuleReport moduleReport = null;
        LangReport languageReport = null;
        QuestionABMFacade questionABMFacade = null;
        
        AssesmentReportHome assesmentHome = AssesmentReportUtil.getHome();
        AssesmentReport assesmentReport = assesmentHome.create();
        int size = assesmentReport.findAssesment(assesment,userSessionData).getModules().size();

        Iterator it = genericModules.iterator();
        while(it.hasNext()) {
            Integer moduleId = new Integer((String)it.next());
            if(moduleReport == null) {
                moduleReport = ModuleReportUtil.getHome().create();
            }
            ModuleData moduleData = moduleReport.findGenericModule(moduleId,userSessionData);
            
            if(languageReport == null) {
                languageReport = LangReportUtil.getHome().create();
            }
            String[] texts = getTexts(moduleData.getKey(),languageReport,userSessionData);
            Integer newModuleId = create(texts,assesment,moduleData.getType(),moduleData.getGreen(),moduleData.getYellow(),size,userSessionData);
            
            Iterator questions = moduleData.getQuestionIterator();
            int index = 1;
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
                        newAnswers.add(new AnswerData(null,"",new Integer(answerIndex+1),answerData.getResultType()));
                        answerIndex++;
                    }
                }else {
                    answerTexts = new String[0][0];
                }
                
                QuestionData newQuestion = new QuestionData(null,"",newModuleId,index,questionData.getType(),questionData.getImage(),questionData.getTestType(),questionData.getGroup(),newAnswers);
                if(questionABMFacade == null) {
                    questionABMFacade = QuestionABMFacadeUtil.getHome().create();
                }
                questionABMFacade.create(newQuestion,questionTexts,answerTexts,userSessionData,QuestionData.NORMAL);
                index++;
            }
            size++;
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
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     */
    public void moveModule(Integer module, int action, UserSessionData userSessionData) throws Exception {
        try{   
            if (userSessionData == null) {
                throw new DeslogedException("moveModule","session = null");
            }
            ModuleABM moduleABM = ModuleABMUtil.getHome().create();
            moduleABM.moveModule(module,action,userSessionData);
        }catch (Exception e) {
            handler.handleException("moveModule",e);
        }
    }
}