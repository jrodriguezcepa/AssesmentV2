package assesment.persistence.assesment;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.ejb.SessionBean;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import assesment.communication.administration.user.UserSessionData;
import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.assesment.CategoryData;
import assesment.communication.assesment.GroupData;
import assesment.communication.exception.DeslogedException;
import assesment.communication.exception.InvalidDataException;
import assesment.communication.question.QuestionData;
import assesment.persistence.administration.tables.AccessCode;
import assesment.persistence.administration.tables.UserAnswer;
import assesment.persistence.administration.tables.UserAssesment;
import assesment.persistence.administration.tables.UserAssesmentResult;
import assesment.persistence.assesment.tables.Assesment;
import assesment.persistence.assesment.tables.AssesmentCategory;
import assesment.persistence.assesment.tables.AssesmentCategoryPK;
import assesment.persistence.assesment.tables.Category;
import assesment.persistence.assesment.tables.Feedback;
import assesment.persistence.assesment.tables.Group;
import assesment.persistence.hibernate.HibernateAccess;
import assesment.persistence.user.tables.User;
import assesment.persistence.util.ExceptionHandler;
import assesment.persistence.util.PersistenceUtil;

/**
 * @ejb.bean name="AssesmentABM" display-name="Name for AssesmentABM"
 *           description="Description for AssesmentABM"
 *           jndi-name="ejb/AssesmentABM" type="Stateless" view-type="remote"
 */
public abstract class AssesmentABMBean implements SessionBean {

    private ExceptionHandler handler = new ExceptionHandler(AssesmentABMBean.class);

    /**
     * Init transaction
     * @ejb.create-method
     * @ejb.permission role-name = "systemaccess,administrator,accesscode"
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
    public Integer create(AssesmentAttributes attributes, UserSessionData userRequest) throws Exception {
        if (userRequest == null) {
            throw new DeslogedException("create","session = null");
        }
        if (attributes == null) {
            throw new InvalidDataException("create","data = null");
        }
        if (PersistenceUtil.empty(attributes.getName())) {
            throw new InvalidDataException("create","corporation.name.invalid");
        }
        try {
            Session session = HibernateAccess.currentSession();
            Assesment assesment = new Assesment(attributes);
            return (Integer)session.save(assesment);
        } catch (Exception e) {
            handler.getException(e,"create",userRequest.getFilter().getLoginName());
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
	public void update(AssesmentAttributes attributes,UserSessionData userSessionData) throws Exception {
        if (userSessionData == null) {
            throw new DeslogedException("update","session = null");
        }
		if (attributes == null) {
			throw new InvalidDataException("update","generic.error.emptydata");
		}
        if (PersistenceUtil.empty(attributes.getName())) {
            throw new InvalidDataException("update","corporation.name.invalid");
        }
        try {
            Session session = HibernateAccess.currentSession();
            Assesment assesment = (Assesment)session.load(Assesment.class,attributes.getId());
            assesment.setData(attributes);
            session.update(assesment);
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
    public void delete(Integer id, UserSessionData userSessionData) throws Exception {
    	try{       
    		Session session = HibernateAccess.currentSession();
            Assesment assesment = (Assesment) session.load(Assesment.class, id);
            session.delete(assesment);
    	} catch (Exception e) {
    		handler.getException(e,"delete",userSessionData.getFilter().getLoginName());
    	}
    }
    
    /**
     * @ejb.permission role-name = "administrator"
     * @ejb.interface-method view-type = "both"
     * @return
     */
    public String validateDelete(Integer assesment) {
        Session session = HibernateAccess.currentSession();

        String queryStr = "SELECT count(*) as count FROM modules m WHERE m.assesment = (:assesment)";
        Query query = session.createSQLQuery(queryStr).addScalar("count",Hibernate.INTEGER);
        query.setParameter("assesment", assesment);
        if(((Integer)query.uniqueResult()).intValue() > 0) {
            return "module.delete.questionasociated";
        }
       
        return null;
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
        Session session = HibernateAccess.currentSession();
        try {
            AccessCode accessCode = new AccessCode(code,assesment,number,extension,language);
            session.save(accessCode);
        }catch (Exception e) {
            handler.getException(e,"createAccessCode",userSessionData.getFilter().getLoginName());
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     */
    public void deleteAccessCode(String code,UserSessionData userSessionData) throws Exception {
        if(code == null || code.trim().length() == 0) {
            throw new InvalidDataException("deleteAccessCode","code = null");
        }
        if(userSessionData == null) {
            throw new DeslogedException("deleteAccessCode","session = null");
        }
        Session session = HibernateAccess.currentSession();
        try {
            AccessCode accessCode = (AccessCode)session.load(AccessCode.class,code);
            session.delete(accessCode);
        }catch (Exception e) {
            handler.getException(e,"deleteAccessCode",userSessionData.getFilter().getLoginName());
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
            Session session = HibernateAccess.currentSession();
            AccessCode accessCode = (AccessCode)session.load(AccessCode.class,code);
            accessCode.setNumber(number);
            accessCode.setExtension(extension);
            accessCode.setLanguage(language);
            session.update(accessCode);
        } catch (Exception e) {
            handler.getException(e,"updateAccessCodeNumber",userSessionData.getFilter().getLoginName());
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,accesscode,systemaccess"
     */
    public void decreaseAccessCode(String code,UserSessionData userSessionData) throws Exception {
        if(code == null || code.trim().length() == 0) {
            throw new InvalidDataException("decreaseAccessCode","code = null");
        }
        if(userSessionData == null) {
            throw new DeslogedException("decreaseAccessCode","session = null");
        }
        Session session = HibernateAccess.currentSession();
        try {
            AccessCode accessCode = (AccessCode)session.load(AccessCode.class,code);
            accessCode.setUsed(new Integer(accessCode.getUsed().intValue()+1));
            session.update(accessCode);
        }catch (Exception e) {
            handler.getException(e,"decreaseAccessCode",userSessionData.getFilter().getLoginName());
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     */
    public void deleteFeedback(Integer assesment, String email, UserSessionData userSessionData) throws Exception {
        try {
            Session session = HibernateAccess.currentSession();
            Query q = session.createQuery("DELETE FROM Feedback f WHERE f.pk.assesment = "+String.valueOf(assesment)+" AND email = '"+email+"'");
            q.executeUpdate();
        }catch (Exception e) {
            handler.getException(e,"deleteFeedback",userSessionData.getFilter().getLoginName());
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     */
    public void createFeedback(Integer assesment, String email, Integer report, UserSessionData userSessionData) throws Exception {
        try {
            Session session = HibernateAccess.currentSession();
            Feedback feedback = new Feedback(assesment,email,report,session);
            session.save(feedback);
        }catch (Exception e) {
            handler.getException(e,"createFeedback",userSessionData.getFilter().getLoginName());
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator"
     */
    public void deleteResults(Integer assessment, String user, int type, boolean onlyTestType, UserSessionData userSessionData) throws Exception {
        try {
            Session session = HibernateAccess.currentSession();
            
        	Query sqlUpdate = session.createQuery("SELECT ua from UserAssesment ua where ua.pk.user.loginName = '"+user+"' AND ua.pk.assesment.id = "+assessment+"");
        	Iterator it = sqlUpdate.list().iterator();
        	if(it.hasNext()) {
        		UserAssesment ua = (UserAssesment)it.next();
        		User u = ua.getPk().getUser();
        		
        		Iterator it2 = ua.getAnswers().iterator();
        		while(it2.hasNext()) {
        			UserAnswer answer = (UserAnswer)it2.next();
        			if(onlyTestType) {
	        			if(answer.getQuestion().getTestType().intValue() == QuestionData.TEST_QUESTION) {
	        				session.delete(answer);
	            			it2.remove();
	        			}
        			}else {
        				session.delete(answer);
            			it2.remove();
            		        			}
/*	        		Iterator it3 = answer.getMultioptions().iterator();
	        		while(it3.hasNext()) {
	        			session.delete(it3.next());
	        			it3.remove();
	        		}
*/        		}

        		if(type == -1) {
        			ua.setEndDate(null);
        			session.update(ua);
        		} else {
	        		it2 = ua.getPsianswers().iterator();
	        		while(it2.hasNext()) {
	        			session.delete(it2.next());
	        			it2.remove();
	        		}
	        		if(type > 1) {
	            		session.delete(ua);
	            		if(type > 2) 
	                		session.delete(u);
	        		}else {
	        			ua.setEndDate(null);
	        			session.update(ua);
	        		}
        		}
        	}
        	
        	sqlUpdate = session.createQuery("SELECT ua from UserAssesmentResult ua where ua.login = '"+user+"' AND ua.assesment = "+assessment);
        	it = sqlUpdate.list().iterator();
        	while(it.hasNext()) {
        		UserAssesmentResult ua = (UserAssesmentResult)it.next();
        		session.delete(ua);
        	}
        }catch (Exception e) {
            handler.getException(e,"createFeedback",userSessionData.getFilter().getLoginName());
        }
    }
    

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator"
     */
    public void deleteResults(String user, UserSessionData userSessionData) throws Exception {
        try {
            Session session = HibernateAccess.currentSession();
            
        	Query sqlUpdate = session.createQuery("SELECT ua from UserAssesment ua where ua.pk.user.loginName = '"+user+"'");
        	Iterator it = sqlUpdate.list().iterator();
        	while(it.hasNext()) {
        		UserAssesment ua = (UserAssesment)it.next();
        		User u = ua.getPk().getUser();
        		
        		Iterator it2 = ua.getAnswers().iterator();
        		while(it2.hasNext()) {
        			UserAnswer answer = (UserAnswer)it2.next();
    				session.delete(answer);
        			it2.remove();
        		}

        		it2 = ua.getPsianswers().iterator();
        		while(it2.hasNext()) {
        			session.delete(it2.next());
        			it2.remove();
        		}
        		
        		session.delete(ua);
            	if(!it.hasNext()) {
                	session.delete(u);
        		}
        	}
        	
        	
        	sqlUpdate = session.createQuery("SELECT ua from UserAssesmentResult ua where ua.login = '"+user+"'");
        	it = sqlUpdate.list().iterator();
        	while(it.hasNext()) {
        		UserAssesmentResult ua = (UserAssesmentResult)it.next();
        		session.delete(ua);
        	}

        }catch (Exception e) {
            handler.getException(e,"deleteResults",userSessionData.getFilter().getLoginName());
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator"
     */
   public void createLink(Integer assessmentId, String link, String language, UserSessionData userSessionData) throws Exception {
       try {
           
    	   Session session = HibernateAccess.currentSession();
           Assesment assessment = (Assesment)session.load(Assesment.class, assessmentId);
           if(language.equals("es"))
        	   assessment.setLink_es(link);
           if(language.equals("en"))
        	   assessment.setLink_en(link);
           if(language.equals("pt"))
        	   assessment.setLink_pt(link);
           session.update(assessment);
           
       }catch (Exception e) {
    	   handler.getException(e,"createLink",userSessionData.getFilter().getLoginName());
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
           Session session = HibernateAccess.currentSession();
           Group group = new Group(groupData, session);
           return (Integer)session.save(group);
       } catch (Exception e) {
           handler.getException(e,"createGroup",userSessionData.getFilter().getLoginName());
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
           Session session = HibernateAccess.currentSession();
           Group group = (Group) session.load(Group.class, groupData.getId());
           group.setData(groupData, session);
           session.update(group);
       } catch (Exception e) {
           handler.getException(e,"updateGroup",userSessionData.getFilter().getLoginName());
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
	   Integer id = null;
       if (userSessionData == null) {
           throw new DeslogedException("create","session = null");
       }
       if (categoryData == null) {
           throw new InvalidDataException("create","data = null");
       }
       try {
           Session session = HibernateAccess.currentSession();
           Category category = new Category(categoryData, session);
           id = (Integer)session.save(category);
           
           Iterator it = assesments.iterator();
           while(it.hasNext()) {
        	   Integer assId = new Integer((String)it.next());
               Assesment obj = (Assesment) session.load(Assesment.class, assId);
        	   AssesmentCategory link = new AssesmentCategory(assId, id);
        	   link.setStart(obj.getStart());
        	   link.setEnd(obj.getEnd());
        	   session.save(link);
           }
       } catch (Exception e) {
           handler.getException(e,"createCategory",userSessionData.getFilter().getLoginName());
       }
       return id;
   }

   /**
    * @ejb.interface-method
    * @ejb.permission role-name = "administrator"
    * Create a corporation  
    * @param data - Contains the data of the new corporation
    * @param userRequest - Logged user
    * @throws Exception
    */
   public void createAssesmentCategory(Integer categoryId, Integer assesment, UserSessionData userSessionData) throws Exception {
       if (userSessionData == null) {
           throw new DeslogedException("create","session = null");
       }
       try {
           Session session = HibernateAccess.currentSession();
           Assesment obj = (Assesment) session.load(Assesment.class, assesment);
    	   AssesmentCategory link = new AssesmentCategory(assesment, categoryId);
    	   link.setStart(obj.getStart());
    	   link.setEnd(obj.getEnd());
    	   session.save(link);
       } catch (Exception e) {
           handler.getException(e,"createAssesmentCategory",userSessionData.getFilter().getLoginName());
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
   public void deleteAssesmentCategory(Integer categoryId, Integer assesment, UserSessionData userSessionData) throws Exception {
       if (userSessionData == null) {
           throw new DeslogedException("create","session = null");
       }
       try {
           Session session = HibernateAccess.currentSession();
    	   AssesmentCategory ac = (AssesmentCategory)session.load(AssesmentCategory.class, new AssesmentCategoryPK(assesment, categoryId));
    	   session.delete(ac);
       } catch (Exception e) {
           handler.getException(e,"deleteAssesmentCategory",userSessionData.getFilter().getLoginName());
       }
   }

   
   /**
	 * @ejb.interface-method	view-type = "remote"
    * @ejb.transaction type="Required" 
    * @ejb.permission role-name = "administrator"
	 */
   public void deleteCategory(Integer categoryId, UserSessionData userSessionData) throws Exception {
	   try {
           Session session = HibernateAccess.currentSession();
    	   Category c = (Category)session.load(Category.class, categoryId);
    	   session.delete(c);
       }catch (Exception e) {
           handler.getException(e,"deleteCategory",userSessionData.getFilter().getLoginName());
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
   public void updateCategory(CategoryData categoryData, UserSessionData userSessionData) throws Exception {
       if (userSessionData == null) {
           throw new DeslogedException("create","session = null");
       }
       if (categoryData == null) {
           throw new InvalidDataException("create","data = null");
       }
       try {
           Session session = HibernateAccess.currentSession();
           Category category = (Category) session.load(Category.class, categoryData.getId());
           category.setData(categoryData, session);
           session.update(category);
       } catch (Exception e) {
           handler.getException(e,"updateGroup",userSessionData.getFilter().getLoginName());
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
           Session session = HibernateAccess.currentSession();
    	   AssesmentCategory ac = (AssesmentCategory)session.load(AssesmentCategory.class, new AssesmentCategoryPK(assessmentId, categoryId));
    	   if(start != null)
    		   ac.setStart(start);
    	   if(end != null)
    		   ac.setEnd(end);
    	   session.update(ac);
		} catch (Exception e) {
	           handler.getException(e,"changeAssesmentGroupDate",userSessionData.getFilter().getLoginName());
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
   public void changeAssesmentReportView(Integer assessmentId, Integer categoryId, Integer show, UserSessionData userSessionData) throws Exception {
		try {
			Session session = HibernateAccess.currentSession();
    	   	AssesmentCategory ac = (AssesmentCategory)session.load(AssesmentCategory.class, new AssesmentCategoryPK(assessmentId, categoryId));
    	   	ac.setShowReport(show.intValue() == 1);
    	   	session.update(ac);
		} catch (Exception e) {
			handler.getException(e,"changeAssesmentReportView",userSessionData.getFilter().getLoginName());
		}
   }
}