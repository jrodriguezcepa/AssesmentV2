
/*
 * Created on 22-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.business.assesment;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import javax.ejb.SessionBean;

import assesment.business.module.ModuleABMFacade;
import assesment.business.module.ModuleABMFacadeUtil;
import assesment.business.question.QuestionABMFacade;
import assesment.business.question.QuestionABMFacadeUtil;
import assesment.business.util.ExceptionHandler;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.assesment.CategoryData;
import assesment.communication.assesment.GroupData;
import assesment.communication.exception.CommunicationProblemException;
import assesment.communication.exception.DeslogedException;
import assesment.communication.exception.InvalidDataException;
import assesment.communication.module.ModuleData;
import assesment.communication.question.AnswerData;
import assesment.communication.question.QuestionData;
import assesment.communication.user.UserData;
import assesment.communication.util.MD5;
import assesment.persistence.assesment.AssesmentABM;
import assesment.persistence.assesment.AssesmentABMHome;
import assesment.persistence.assesment.AssesmentABMUtil;
import assesment.persistence.assesment.AssesmentReport;
import assesment.persistence.assesment.AssesmentReportHome;
import assesment.persistence.assesment.AssesmentReportUtil;
import assesment.persistence.language.LangReport;
import assesment.persistence.language.LangReportUtil;
import assesment.persistence.module.ModuleReport;
import assesment.persistence.module.ModuleReportUtil;
import assesment.persistence.user.UsABM;
import assesment.persistence.user.UsABMUtil;
import assesment.persistence.user.UsReport;
import assesment.persistence.user.UsReportUtil;
import assesment.persistence.util.PersistenceUtil;

/**
 * @ejb.bean name="AssesmentABMFacade"
 *           display-name="Name for Assesment"
 *           description="Description for Assesment"
 *           jndi-name="ejb/AssesmentABMFacade"
 *           type="Stateless"
 *           view-type="remote"
 * 
 * @ejb.ejb-ref 
 * 			ejb-name ="AssesmentABM"
 * 			ref-name = "ejb/AssesmentABM"
 * 			view-type ="remote"
 * 
 * @jboss.ejb-ref-jndi
 * 			jndi-name = "ejb/AssesmentABM" 
 * 			ref-name = "AssesmentABM"
 * 
 * @ejb.ejb-ref ejb-name = "UsABM" ref-name = "ejb/UsABM" view-type =
 *              "remote"
 * 
 * @jboss.ejb-ref-jndi jndi-name = "ejb/UsABM" ref-name = "UsABM"
 *  
 * @ejb.ejb-ref ejb-name = "ModuleABMFacade" ref-name = "ejb/ModuleABMFacade" view-type =
 *              "remote"
 * 
 * @jboss.ejb-ref-jndi jndi-name = "ejb/ModuleABMFacade" ref-name = "ModuleABMFacade"
 *
 * @ejb.ejb-ref ejb-name = "ModuleReport" ref-name = "ejb/ModuleReport" view-type =
 *              "remote"
 * 
 * @jboss.ejb-ref-jndi jndi-name = "ejb/ModuleReport" ref-name = "ModuleReport"
 *
 *
 * @ejb.ejb-ref ejb-name = "LangReport" ref-name = "ejb/LangReport" view-type =
 *              "remote"
 * 
 * @jboss.ejb-ref-jndi jndi-name = "ejb/LangReport" ref-name = "LangReport"
 *
 * 
 * @ejb.ejb-ref 
 * 			ejb-name ="AssesmentReport"
 * 			ref-name = "ejb/AssesmentReport"
 * 			view-type ="remote"
 * 
 * @jboss.ejb-ref-jndi
 * 			jndi-name = "ejb/AssesmentReport" 
 * 			ref-name = "AssesmentReport"
 * 
 * 
 * @ejb.ejb-ref ejb-name = "UsABM" ref-name = "ejb/UsABM" view-type =
 *              "remote"
 * 
 * @jboss.ejb-ref-jndi jndi-name = "ejb/UsABM" ref-name = "UsABM"
 *  
 * @ejb.ejb-ref ejb-name = "UsReport" ref-name = "ejb/UsReport" view-type =
 *              "remote"
 * 
 * @jboss.ejb-ref-jndi jndi-name = "ejb/UsReport" ref-name = "UsReport"
 *  
 * @ejb.ejb-ref ejb-name = "AssesmentABM" ref-name = "ejb/AssesmentABM" view-type =
 *              "remote"
 *              
 * @ejb.util generate="physical"
*/
public abstract class AssesmentABMFacadeBean implements SessionBean {
	
    ExceptionHandler handler = new ExceptionHandler(AssesmentABMFacadeBean.class);
    
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
	public Integer create(AssesmentAttributes data,UserSessionData userSessionData) throws Exception{
		if (userSessionData == null) {
			throw new DeslogedException("create","session = null");
		}
		if (data == null) {
			throw new InvalidDataException("create","data = null");
		}
		if (!validate(data)) {
			throw new InvalidDataException("create","corporation.name.invalid");
		}
		try {
            AssesmentABMHome home = AssesmentABMUtil.getHome();
            AssesmentABM assesmentABM = home.create();
            return assesmentABM.create(data, userSessionData);
		} catch (Exception e) {
            handler.handleException("create",e);
		}
		return null;
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
	public void update(AssesmentAttributes data,UserSessionData userSessionData) throws Exception{
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
            AssesmentABMHome home = AssesmentABMUtil.getHome();
            AssesmentABM assesmentABM = home.create();
            assesmentABM.update(data,userSessionData);
        } catch (Exception e) {
            handler.handleException("update",e);
        }
	}

    /**
     * Validate if the name of the corporation data is not empty
     * @param data - Data of the corporation
     * @return if is valid or not
     */
	private boolean validate(AssesmentAttributes data){
		return !PersistenceUtil.empty(data.getName());
	}

    /**
     * @ejb.interface-method view-type = "remote"
     * @ejb.transaction type="Required"
     * @ejb.permission role-name = "administrator"
     * @param data
     * @throws InvalidDataException
     * @throws DeslogedException
     */
    public void delete(Collection list, UserSessionData userSessionData) throws Exception {
    	try{   
    		if (userSessionData == null) {
    			throw new DeslogedException("delete","session = null");
    		}
    		if (list == null) {
    			throw new InvalidDataException("delete","data = null");
    		}
            AssesmentABM assesmentABM = AssesmentABMUtil.getHome().create();
            Iterator it = list.iterator();
            while(it.hasNext()) {
                Integer id = new Integer((String)it.next());
                String msg = assesmentABM.validateDelete(id); 
                if(!PersistenceUtil.empty(msg)) {
                   throw new InvalidDataException("delete",msg); 
                }
                assesmentABM.delete(id,userSessionData);
            }
    	}catch (Exception e) {
    		handler.handleException("delete",e);
    	}
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     */
    public void createAccessCode(Integer assesment,String code,Integer number,Integer extension,String language,UserSessionData userSessionData) throws Exception {
        if(assesment == null) {
            throw new InvalidDataException("createAccessCode","assesment = null");
        }
        if(code == null || code.trim().length() == 0) {
            throw new InvalidDataException("createAccessCode","code = null");
        }
        if(number == null || number.intValue() <= 0) {
            throw new InvalidDataException("createAccessCode","number = null");
        }
        if(userSessionData == null) {
            throw new DeslogedException("createAccessCode","session = null");
        }
        try {
            AssesmentABM assesmentABM = AssesmentABMUtil.getHome().create();
            assesmentABM.createAccessCode(assesment,code,number,extension,language,userSessionData);
        } catch (Exception e) {
            handler.handleException("createAccessCode",e);
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     */
    public void deleteAccessCode(Collection list, UserSessionData userSessionData) throws Exception {
        if(list == null) {
            throw new InvalidDataException("deleteAccessCode","list = null");
        }
        if(userSessionData == null) {
            throw new DeslogedException("deleteAccessCode","session = null");
        }
        try {
            AssesmentABM assesmentABM = AssesmentABMUtil.getHome().create();
            Iterator it = list.iterator();
            while(it.hasNext()) {
                String code = (String)it.next();
                assesmentABM.deleteAccessCode(code,userSessionData);
            }
        } catch (Exception e) {
            handler.handleException("deleteAccessCode",e);
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     */
    public void updateAccessCodeNumber(String code, Integer number, Integer extension, String language, UserSessionData userSessionData) throws Exception {
        if(code == null || code.trim().length() == 0) {
            throw new InvalidDataException("updateAccessCodeNumber","code = null");
        }
        if(number == null || number.intValue() <= 0) {
            throw new InvalidDataException("updateAccessCodeNumber","number = null");
        }
        if(userSessionData == null) {
            throw new DeslogedException("updateAccessCodeNumber","session = null");
        }
        try {
            AssesmentABM assesmentABM = AssesmentABMUtil.getHome().create();
            assesmentABM.updateAccessCodeNumber(code,number,extension,language,userSessionData);
        } catch (Exception e) {
            handler.handleException("updateAccessCodeNumber",e);
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     */
    public void updateFeedback(Integer assesment, String email, Collection reports, UserSessionData userSessionData) throws Exception {
        try {
            AssesmentABM assesmentABM = AssesmentABMUtil.getHome().create();
            assesmentABM.deleteFeedback(assesment,email,userSessionData);
            Iterator it = reports.iterator();
            while(it.hasNext()) {
                assesmentABM.createFeedback(assesment,email,(Integer)it.next(),userSessionData);
            }
        } catch (Exception e) {
            handler.handleException("updateFeedback",e);
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator"
     */
    public void deleteResults(Integer assessment, Collection users, Integer type, UserSessionData userSessionData) throws Exception {
        try {
            AssesmentABM assesmentABM = AssesmentABMUtil.getHome().create();
            
            Iterator it = users.iterator();
            while(it.hasNext()) {
            	String user = (String) it.next();
	            assesmentABM.deleteResults(assessment,user,type,false,userSessionData);            
            }
        }catch (Exception e) {
            handler.handleException("deleteResults",e);
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator"
     */
    public void deleteQuestionTestResults(Integer assessment, Collection users, Integer type, UserSessionData userSessionData) throws Exception {
        try {
            AssesmentABM assesmentABM = AssesmentABMUtil.getHome().create();
            
            Iterator it = users.iterator();
            while(it.hasNext()) {
            	String user = (String) it.next();
	            assesmentABM.deleteResults(assessment,user,type,true,userSessionData);            
            }
        }catch (Exception e) {
            handler.handleException("deleteQuestionTestResults",e);
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     */
    public void addExistingModule(Integer assesment,Integer module,UserSessionData userSessionData) throws Exception {
        QuestionABMFacade questionABMFacade = null;
        
        AssesmentReportHome assesmentHome = AssesmentReportUtil.getHome();
        AssesmentReport assesmentReport = assesmentHome.create();
        int size = assesmentReport.findAssesment(assesment,userSessionData).getModules().size();

        ModuleReport moduleReport = ModuleReportUtil.getHome().create();
        ModuleData moduleData = moduleReport.findModule(module,userSessionData);
            
        LangReport languageReport = LangReportUtil.getHome().create();
        String[] texts = languageReport.getCompleteText(moduleData.getKey(),userSessionData);

        ModuleABMFacade moduleABM = ModuleABMFacadeUtil.getHome().create();
        Integer newModuleId = moduleABM.create(texts,assesment,moduleData.getType(),size,userSessionData);
            
        Iterator questions = moduleData.getQuestionIterator();
        int index = 1;
        while(questions.hasNext()) {
        	QuestionData questionData = (QuestionData)questions.next();                
            String[] questionTexts = languageReport.getCompleteText(questionData.getKey(),userSessionData);
                
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
                   answerTexts[answerIndex] = languageReport.getCompleteText(answerData.getKey(),userSessionData);
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
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator"
     */
   public void createLink(Integer assessmentId, String language, String firstName, String lastName, UserSessionData userSessionData) throws Exception {
	   try {
		   AssesmentABM assesmentABM = AssesmentABMUtil.getHome().create();
		   assesmentABM.createAccessCode(assessmentId, "generate_"+language+"_"+assessmentId, 10000, 0, language, userSessionData);
		   
		   String login = "generate_"+assessmentId;

		   UsReport usReport = UsReportUtil.getHome().create();
		   UserData exist = usReport.findUserByPrimaryKey(login, null, userSessionData);
		   
		   String password = new MD5().encriptar(login);
		   if(exist == null) {
			   UsABM usABM = UsABMUtil.getHome().create();
			   UserData user = new UserData(login,password,firstName,lastName,language,UserData.DEFAULT_EMAIL,"accesscode",null);
			   usABM.userCreate(user, (Integer)null, userSessionData);
		   }
		   
		   String link = "https://www.cepada.com//assesment/index.jsp?g="+assessmentId+"&lng="+language+"&p="+password;
		   assesmentABM.createLink(assessmentId, link, language, userSessionData);
		   
       }catch (Exception e) {
           handler.handleException("createLink",e);
       }
    }

   /**
    * @ejb.interface-method
    * @ejb.permission role-name = "administrator"
    * Create a corporation  
    * @param data - Contains the data of the new corporation
    * @param userRequest - Logged user
    * @throws Exception
    */
   public Integer createGroup(GroupData groupData, UserSessionData userSessionData) throws Exception {
		if (userSessionData == null) {
			throw new DeslogedException("create","session = null");
		}
		if (groupData == null) {
			throw new InvalidDataException("create","data = null");
		}
		try {
            AssesmentABM assesmentABM = AssesmentABMUtil.getHome().create();
            return assesmentABM.createGroup(groupData, userSessionData);
		} catch (Exception e) {
            handler.handleException("AssesmentABMUtil.getHome()",e);
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
   public void updateGroup(GroupData groupData, UserSessionData userSessionData) throws Exception {
		if (userSessionData == null) {
			throw new DeslogedException("create","session = null");
		}
		if (groupData == null) {
			throw new InvalidDataException("create","data = null");
		}
		try {
            AssesmentABM assesmentABM = AssesmentABMUtil.getHome().create();
            assesmentABM.updateGroup(groupData, userSessionData);
		} catch (Exception e) {
            handler.handleException("AssesmentABMUtil.getHome()",e);
		}
   }

   /**
    * @ejb.interface-method
    * @ejb.permission role-name = "administrator"
    * Create a corporation  
    * @param data - Contains the data of the new corporation
    * @param userRequest - Logged user
    * @throws Exception
    */
   public Integer createCategory(CategoryData categoryData, Collection assesments, UserSessionData userSessionData) throws Exception {
		if (userSessionData == null) {
			throw new DeslogedException("create","session = null");
		}
		if (categoryData == null) {
			throw new InvalidDataException("create","data = null");
		}
		try {
            AssesmentABM assesmentABM = AssesmentABMUtil.getHome().create();
            return assesmentABM.createCategory(categoryData, assesments, userSessionData);
		} catch (Exception e) {
            handler.handleException("AssesmentABMUtil.getHome()",e);
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
   public void updateCategory(CategoryData categoryData, UserSessionData userSessionData) throws Exception {
		if (userSessionData == null) {
			throw new DeslogedException("create","session = null");
		}
		if (categoryData == null) {
			throw new InvalidDataException("create","data = null");
		}
		try {
            AssesmentABM assesmentABM = AssesmentABMUtil.getHome().create();
            assesmentABM.updateCategory(categoryData, userSessionData);
		} catch (Exception e) {
            handler.handleException("AssesmentABMUtil.getHome()",e);
		}
   }

   /**
	 * @ejb.interface-method	view-type = "remote"
    * @ejb.transaction type="Required" 
    * @ejb.permission role-name = "administrator"
	 */
   public void deleteCategory(Integer categoryId,  UserSessionData userSessionData) throws Exception {
       try {
           AssesmentABM assesmentABM = AssesmentABMUtil.getHome().create();
           assesmentABM.deleteCategory(categoryId,userSessionData);
       } catch (Exception e) {
           handler.handleException("deleteCategory",e);
       }
   }

   /**
    * @ejb.interface-method
    * @ejb.permission role-name = "administrator"
    * Create a corporation  
    * @param data - Contains the data of the new corporation
    * @param userRequest - Logged user
    * @throws Exception
    */
   public void asociateAssesmentCategory(Integer category, Collection assessments, UserSessionData userSessionData) throws Exception {
		if (userSessionData == null) {
			throw new DeslogedException("create","session = null");
		}
		if (category == null || assessments == null) {
			throw new InvalidDataException("create","data = null");
		}
		try {
            AssesmentABM assesmentABM = AssesmentABMUtil.getHome().create();
            Iterator it = assessments.iterator();
            while(it.hasNext()) {
            	assesmentABM.createAssesmentCategory(category, new Integer((String)it.next()), userSessionData);
            }
		} catch (Exception e) {
            handler.handleException("AssesmentABMUtil.getHome()",e);
		}
   }

   /**
    * @ejb.interface-method
    * @ejb.permission role-name = "administrator"
    * Create a corporation  
    * @param data - Contains the data of the new corporation
    * @param userRequest - Logged user
    * @throws Exception
    */
   public void desasociateAssesmentCategory(Integer category, Collection assessments, UserSessionData userSessionData) throws Exception {
		if (userSessionData == null) {
			throw new DeslogedException("create","session = null");
		}
		if (category == null || assessments == null) {
			throw new InvalidDataException("create","data = null");
		}
		try {
            AssesmentABM assesmentABM = AssesmentABMUtil.getHome().create();
            Iterator it = assessments.iterator();
            while(it.hasNext()) {
            	assesmentABM.deleteAssesmentCategory(category, new Integer((String)it.next()), userSessionData);
            }
		} catch (Exception e) {
            handler.handleException("AssesmentABMUtil.getHome()",e);
		}
   }


   /**
    * @ejb.interface-method
    * @ejb.permission role-name = "administrator"
    * Create a corporation  
    * @param data - Contains the data of the new corporation
    * @param userRequest - Logged user
    * @throws Exception
    */
   public void changeAssesmentGroupDate(Integer assessmentId, Integer categoryId, Date start, Date end, UserSessionData userSessionData) throws Exception {
		try {
            AssesmentABMUtil.getHome().create().changeAssesmentGroupDate(assessmentId, categoryId, start, end, userSessionData);
		} catch (Exception e) {
            handler.handleException("changeAssesmentGroupDate",e);
		}
   }
}