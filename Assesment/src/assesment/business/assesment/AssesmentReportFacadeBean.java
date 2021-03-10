/*
 * Created on 30-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.business.assesment;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

import javax.ejb.SessionBean;

import assesment.business.util.ExceptionHandler;
import assesment.communication.administration.AccessCodeData;
import assesment.communication.administration.UserMultiAssessmentData;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.assesment.CategoryData;
import assesment.communication.assesment.FeedbackData;
import assesment.communication.assesment.GroupData;
import assesment.communication.exception.DeslogedException;
import assesment.communication.exception.InvalidDataException;
import assesment.communication.language.Text;
import assesment.communication.report.AssessmentReportData;
import assesment.communication.report.ModuleReportData;
import assesment.communication.report.UserReportData;
import assesment.communication.util.ListResult;
import assesment.persistence.administration.tables.AssessmentUserData;
import assesment.persistence.assesment.AssesmentReport;
import assesment.persistence.assesment.AssesmentReportHome;
import assesment.persistence.assesment.AssesmentReportUtil;
import assesment.persistence.question.QuestionReport;
import assesment.persistence.question.QuestionReportUtil;
import assesment.persistence.user.UsReport;
import assesment.persistence.user.UsReportUtil;

/**
 * @ejb.bean name="AssesmentReportFacade"
 *           display-name="Name for AssesmentReportFacade"
 *           description="Description for AssesmentReportFacade"
 *           jndi-name="ejb/AssesmentReportFacade"
 *           type="Stateless"
 *           view-type="both"
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
 * @ejb.ejb-ref 
 * 			ejb-name ="QuestionReport"
 * 			ref-name = "ejb/QuestionReport"
 * 			view-type ="remote"
 * 
 * @jboss.ejb-ref-jndi
 * 			jndi-name = "ejb/QuestionReport" 
 * 			ref-name = "QuestionReport"
 *
 * 
 * @ejb.ejb-ref 
 * 			ejb-name ="UsReport"
 * 			ref-name = "ejb/UsReport"
 * 			view-type ="remote"
 * 
 * @jboss.ejb-ref-jndi
 * 			jndi-name = "ejb/UsReport" 
 * 			ref-name = "UsReport"
 *
 * @ejb.util generate="physical"
 *
 */
public abstract class AssesmentReportFacadeBean implements SessionBean {

    ExceptionHandler handler = new ExceptionHandler(AssesmentReportFacadeBean.class);

    /**
	 * Create method
	 * 
	 * @ejb.create-method
	 * @ejb.permission role-name = "systemaccess,administrator,accesscode,basf_assessment,clientreporter,cepareporter"
	 *  
	 */
	public void ejbCreate() throws javax.ejb.CreateException {
	}
	
	/**
	 * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess,accesscode,clientreporter"
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public AssesmentAttributes findAssesmentAttributes(Integer id,UserSessionData userSessionData) throws Exception {
		if (userSessionData == null) {
			throw new DeslogedException("findAssesmentAttributes","userSessionData = null");
		}
		if (id == null) {
			throw new InvalidDataException("findAssesmentAttributes","id = null");
		}
		try {
            AssesmentReportHome home = AssesmentReportUtil.getHome();
            AssesmentReport assesmentReport = home.create();
			return assesmentReport.findAssesmentAttributes(id, userSessionData);
		}catch (Exception e) {
            handler.handleException("findAssesmentAttributes",e);
		}
		return null;
	}
	
    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess"
     * @param id
     * @return
     * @throws Exception
     */
    public AssesmentData findAssesment(Integer id,UserSessionData userSessionData) throws Exception {
        if (userSessionData == null) {
            throw new DeslogedException("findAssesment","userSessionData = null");
        }
        if (id == null) {
            throw new InvalidDataException("findAssesment","id = null");
        }
        try {
            AssesmentReportHome home = AssesmentReportUtil.getHome();
            AssesmentReport assesmentReport = home.create();
            long start = System.currentTimeMillis();
            AssesmentData data = assesmentReport.findAssesment(id, userSessionData);
            System.out.println("Dur 1: "+String.valueOf(System.currentTimeMillis() - start));
            return data;
        }catch (Exception e) {
            handler.handleException("findAssesment",e);
        }
        return null;
    }
    
    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess,clientreporter,cepareporter"
     */
    public Collection<AssesmentAttributes>[] getAssessments(UserSessionData userSessionData) throws Exception {
        try {
            AssesmentReportHome home = AssesmentReportUtil.getHome();
            AssesmentReport assesmentReport = home.create();
            return assesmentReport.getAssessments(userSessionData);
        }catch (Exception e) {
            handler.handleException("findAssesments",e);
        }
        return null;
    }
    
    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess,clientreporter,cepareporter"
     */
    public ListResult findAssesments(String name,String corporation,UserSessionData userSessionData) throws Exception {
    	return findAssesments(name,corporation,"0",userSessionData);
    }
    
    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess,clientreporter,cepareporter"
     */
    public ListResult findAssesments(String name,String corporation,String archived,UserSessionData userSessionData) throws Exception {
        if (userSessionData == null) {
            throw new DeslogedException("findAssesments","userSessionData = null");
        }
        try {
            AssesmentReportHome home = AssesmentReportUtil.getHome();
            AssesmentReport assesmentReport = home.create();
            return assesmentReport.findAssesments(name,corporation,archived,userSessionData);
        }catch (Exception e) {
            handler.handleException("findAssesments",e);
        }
        return null;
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,accesscode,systemaccess"
     */
    public AccessCodeData getAccessCode(String accesscode,UserSessionData userSessionData) throws Exception {
        if (userSessionData == null) {
            throw new DeslogedException("getAccessCode","userSessionData = null");
        }
        try {
            AssesmentReport assesmentReport = AssesmentReportUtil.getHome().create();
            return assesmentReport.getAccessCode(accesscode,userSessionData);
        }catch (Exception e) {
            handler.handleException("getAccessCode",e);
        }
        return null;
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator"
     */
    public Collection getAccessCodes(UserSessionData userSessionData, Text messages) throws Exception {
        if (userSessionData == null) {
            throw new DeslogedException("getAccessCodes","userSessionData = null");
        }
        try {
            AssesmentReport assesmentReport = AssesmentReportUtil.getHome().create();
            return assesmentReport.getAccessCodes(userSessionData, messages);
        }catch (Exception e) {
            handler.handleException("getAccessCodes",e);
        }
        return null;
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public String[] findReportData(Integer assesment, UserSessionData userSessionData) throws Exception {
        if (userSessionData == null) {
            throw new DeslogedException("findReportData","userSessionData = null");
        }
        try {
            AssesmentReport assesmentReport = AssesmentReportUtil.getHome().create();
            return assesmentReport.findReportData(assesment,userSessionData);
        }catch (Exception e) {
            handler.handleException("findReportData",e);
        }
        return null;
    }
    
    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public Collection getAssesmentFeedback(Integer assesment, UserSessionData userSessionData) throws Exception {
        Collection feedback = new LinkedList();
        try {
            AssesmentReport assesmentReport = AssesmentReportUtil.getHome().create();
            Iterator it = assesmentReport.getAssesmentFeedback(assesment,userSessionData).iterator();
            while(it.hasNext()) {
                FeedbackData data = (FeedbackData)it.next();
                boolean found = false;
                Iterator aux = feedback.iterator();
                while(aux.hasNext() && !found) {
                    FeedbackData feedbackData = (FeedbackData)aux.next();
                    if(feedbackData.getEmail().equals(data.getEmail())) {
                        found = true;
                        feedbackData.addReport(data);
                    }
                }
                if(!found) {
                    feedback.add(data);
                }
            }
        }catch (Exception e) {
            handler.handleException("getAssesmentFeedback",e);
        }
        return feedback;
    }
    
    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public FeedbackData getFeedback(Integer assesment, String email, UserSessionData userSessionData) throws Exception {
        try {
            AssesmentReport assesmentReport = AssesmentReportUtil.getHome().create();
            return assesmentReport.getFeedback(assesment,email,userSessionData);
        }catch (Exception e) {
            handler.handleException("getFeedback",e);
        }
        return null;
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public Integer getAssesmentsQuestionCount(AssesmentAttributes attributes, UserSessionData userSessionData) throws Exception {
        try {
            AssesmentReport assesmentReport = AssesmentReportUtil.getHome().create();
            return assesmentReport.getAssesmentQuestionCount(attributes,userSessionData,true);
        } catch (Exception exception) {
            handler.handleException("getAssesmentsQuestionCount", exception);
        }
        return null; 
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public Integer getAssesmentsQuestionCount(AssesmentAttributes attributes, UserSessionData userSessionData,boolean all) throws Exception {
        try {
            AssesmentReport assesmentReport = AssesmentReportUtil.getHome().create();
            return assesmentReport.getAssesmentQuestionCount(attributes,userSessionData,all);
        } catch (Exception exception) {
            handler.handleException("getAssesmentsQuestionCount", exception);
        }
        return null; 
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public Integer getNewHireValue(String user, String assesment, UserSessionData userSessionData) throws Exception {
        try {
            AssesmentReport assesmentReport = AssesmentReportUtil.getHome().create();
            return assesmentReport.getNewHireValue(user,assesment,userSessionData);
        } catch (Exception exception) {
            handler.handleException("getNewHireValue", exception);
        }
        return null; 
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public Collection getWrongAnswers(String assesment, UserSessionData userSessionData) throws Exception {
        try {
            AssesmentReport assesmentReport = AssesmentReportUtil.getHome().create();
            return assesmentReport.getWrongAnswers(assesment,userSessionData);
        } catch (Exception exception) {
            handler.handleException("getWrongAnswers", exception);
        }
        return new LinkedList();
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public Hashtable getUserAnswerCount(String assesment, UserSessionData userSessionData) throws Exception {
        try {
            AssesmentReport assesmentReport = AssesmentReportUtil.getHome().create();
            return assesmentReport.getUserAnswerCount(assesment,userSessionData);
        } catch (Exception exception) {
            handler.handleException("getUserAnswerCount", exception);
        }
        return new Hashtable();
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     */
    public Collection getAssessmentResults(Integer assessment,Date start, Date end, UserSessionData userSessionData) throws Exception {
        try {
            AssesmentReport assesmentReport = AssesmentReportUtil.getHome().create();
            return assesmentReport.getAssessmentResults(assessment,start,end,userSessionData);
        } catch (Exception exception) {
            handler.handleException("getAssessmentResults", exception);
        }
        return new LinkedList();
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,basf_assessment"
     */
    public Integer[] getAvailableAssessments(String user, Integer corporation, UserSessionData userSessionData) throws Exception {
        try {
            AssesmentReport assesmentReport = AssesmentReportUtil.getHome().create();
            return assesmentReport.getAvailableAssessments(user,corporation,userSessionData);
        } catch (Exception exception) {
            handler.handleException("getAvailableAssessments", exception);
        }
        return new Integer[0];
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,clientreporter,cepareporter,systemaccess"
     */
    public AssessmentReportData getAssessmentReport(Integer assessment, UserSessionData userSessionData) throws Exception {
    	AssessmentReportData reportData = new AssessmentReportData();
    	try {
    		AssesmentReport assessmentReport = AssesmentReportUtil.getHome().create();
    		reportData.setAssessment(assessmentReport.findAssesment(assessment, userSessionData));
    		reportData.setAnswerTestCount(assessmentReport.getAssesmentQuestionCount(reportData.getAssessment(), userSessionData, false));
    		
    		UsReport userReport = UsReportUtil.getHome().create();
    		Collection userValues = userReport.getUsersReport(assessment,userSessionData);
    		HashMap<String, HashMap<Integer, String>> wrtAswers = userReport.getWRTUserAnswers(assessment, userSessionData);
    		Iterator it = userValues.iterator();
    		HashMap<String,UserReportData> mapUsers = new HashMap<String,UserReportData>();
    		HashMap<Integer,ModuleReportData> mapModules = new HashMap<Integer,ModuleReportData>();
    		while(it.hasNext()) {
    			Object[] data = (Object[])it.next();
    			if(mapUsers.containsKey(data[0])) {
    				mapUsers.get(data[0]).addResult(((Integer)data[6]).intValue(), ((Integer)data[7]).intValue());
    			}else {
    				UserReportData userReportData = new UserReportData((String)data[0],(String)data[1],(String)data[2],(String)data[3],(Date)data[4]);
    				userReportData.addResult(((Integer)data[6]).intValue(), ((Integer)data[7]).intValue());
    				userReportData.setPSI(data);
    				if(wrtAswers.containsKey(userReportData.getLogin())) {
    					userReportData.setWRTAnswers(wrtAswers.get(userReportData.getLogin()));
    				}
    				mapUsers.put(userReportData.getLogin(), userReportData);
    			}
    			if(data[4] != null) {
	    			if(mapModules.containsKey(data[5])) {
	    				mapModules.get(data[5]).addUserResult((String)data[0], (Integer)data[6], (Integer)data[7]);
	    			}else {
	    				ModuleReportData moduleReportData = new ModuleReportData((Integer)data[5]);
	    				moduleReportData.addUserResult((String)data[0], (Integer)data[6], (Integer)data[7]);
	    				mapModules.put((Integer)data[5], moduleReportData);
	    			}
    			}
    		}

     		if(assessment.intValue() == AssesmentData.UPL_NEWHIRE) {
     			Collection results2 = userReport.findResults(assessment, 2, userSessionData);
     			it = results2.iterator();
     			while(it.hasNext()) {
     				Object[] data = (Object[])it.next();
     				mapUsers.get((String)data[0]).addSecondUser((Integer)data[1], (Integer)data[2]);
     			}
     		}
    		
    		QuestionReport questionReport = QuestionReportUtil.getHome().create();
    		Collection questionValues = questionReport.getQuestionReport(assessment,userSessionData);
    		it = questionValues.iterator();
     		while(it.hasNext()) {
     			Object[] data = (Object[])it.next();
     			mapModules.get(data[0]).addQuestion(data);
     		}

     		reportData.setModules(mapModules.values());

     		Iterator<UserReportData> itUsers = mapUsers.values().iterator();
    		while(itUsers.hasNext()) {
    			reportData.addUser(itUsers.next());
    		}
     		
    		Collection userNotStarted = userReport.getNotStartedUsersReport(assessment,userSessionData);
    		Iterator itNotStarted = userNotStarted.iterator();
    		while(itNotStarted.hasNext()) {
    			Object[] data = (Object[])itNotStarted.next();
    			reportData.addNotStartedUser(new UserReportData((String)data[0],(String)data[1],(String)data[2],(String)data[3],null,true));
    		}

    	} catch (Exception e) {
    		e.printStackTrace();
            handler.handleException("getAssessmentReport", e);
		}
    	return reportData;
    }


    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,clientreporter,cepareporter,systemaccess"
     */
    public AssessmentReportData getAssessmentReport(Integer assessment, Integer group, UserSessionData userSessionData) throws Exception {
    	AssessmentReportData reportData = new AssessmentReportData();
    	try {
    		AssesmentReport assessmentReport = AssesmentReportUtil.getHome().create();
    		reportData.setAssessment(assessmentReport.findAssesment(assessment, userSessionData));
    		reportData.setAnswerTestCount(assessmentReport.getAssesmentQuestionCount(reportData.getAssessment(), userSessionData, false));
    		
    		UsReport userReport = UsReportUtil.getHome().create();
    		Collection userValues = userReport.getUsersReport(assessment, group, userSessionData);
    		HashMap<String, HashMap<Integer, String>> wrtAswers = userReport.getWRTUserAnswers(assessment, group, userSessionData);
    		Iterator it = userValues.iterator();
    		HashMap<String,UserReportData> mapUsers = new HashMap<String,UserReportData>();
    		HashMap<Integer,ModuleReportData> mapModules = new HashMap<Integer,ModuleReportData>();
    		while(it.hasNext()) {
    			Object[] data = (Object[])it.next();
    			if(mapUsers.containsKey(data[0])) {
    				mapUsers.get(data[0]).addResult(((Integer)data[6]).intValue(), ((Integer)data[7]).intValue());
    			}else {
    				UserReportData userReportData = new UserReportData((String)data[0],(String)data[1],(String)data[2],(String)data[3],(Date)data[4]);
    				userReportData.addResult(((Integer)data[6]).intValue(), ((Integer)data[7]).intValue());
    				userReportData.setPSI(data);
    				if(wrtAswers.containsKey(userReportData.getLogin())) {
    					userReportData.setWRTAnswers(wrtAswers.get(userReportData.getLogin()));
    				}
    				mapUsers.put(userReportData.getLogin(), userReportData);
    			}
    			if(data[4] != null) {
	    			if(mapModules.containsKey(data[5])) {
	    				mapModules.get(data[5]).addUserResult((String)data[0], (Integer)data[6], (Integer)data[7]);
	    			}else {
	    				ModuleReportData moduleReportData = new ModuleReportData((Integer)data[5]);
	    				moduleReportData.addUserResult((String)data[0], (Integer)data[6], (Integer)data[7]);
	    				mapModules.put((Integer)data[5], moduleReportData);
	    			}
    			}
    		}

     		if(assessment.intValue() == AssesmentData.UPL_NEWHIRE) {
     			Collection results2 = userReport.findResults(assessment, 2, userSessionData);
     			it = results2.iterator();
     			while(it.hasNext()) {
     				Object[] data = (Object[])it.next();
     				mapUsers.get((String)data[0]).addSecondUser((Integer)data[1], (Integer)data[2]);
     			}
     		}
    		
    		QuestionReport questionReport = QuestionReportUtil.getHome().create();
    		Collection questionValues = questionReport.getQuestionReport(assessment,group,userSessionData);
    		it = questionValues.iterator();
     		while(it.hasNext()) {
     			Object[] data = (Object[])it.next();
     			mapModules.get(data[0]).addQuestion(data);
     		}

     		reportData.setModules(mapModules.values());

     		Iterator<UserReportData> itUsers = mapUsers.values().iterator();
    		while(itUsers.hasNext()) {
    			reportData.addUser(itUsers.next());
    		}
     		
    		Collection userNotStarted = userReport.getNotStartedUsersReport(assessment,group,userSessionData);
    		Iterator itNotStarted = userNotStarted.iterator();
    		while(itNotStarted.hasNext()) {
    			Object[] data = (Object[])itNotStarted.next();
    			reportData.addNotStartedUser(new UserReportData((String)data[0],(String)data[1],(String)data[2],(String)data[3],null,true));
    		}

    	} catch (Exception e) {
    		e.printStackTrace();
            handler.handleException("getAssessmentReport", e);
		}
    	return reportData;
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     */
    public Collection<AssessmentUserData> getAssessmentUsers (Integer assessment, UserSessionData userSessionData) throws Exception {
    	try {
    		return AssesmentReportUtil.getHome().create().getAssessmentUsers(assessment, userSessionData);
    	} catch (Exception e) {
    		e.printStackTrace();
            handler.handleException("getAssessmentUsers", e);
		}
    	return new LinkedList<AssessmentUserData>();
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
   public GroupData getUserGroup(String user, UserSessionData userSessionData) throws Exception {
    	try {
    		return AssesmentReportUtil.getHome().create().getUserGroup(user, userSessionData);
    	} catch (Exception e) {
    		e.printStackTrace();
            handler.handleException("getUserGroup", e);
		}
    	return null;
    }


   /**
    * @ejb.interface-method
    * @ejb.permission role-name = "administrator,systemaccess"
    */
  public GroupData findGroup(Integer id, UserSessionData userSessionData) throws Exception {
	  	try {
			return AssesmentReportUtil.getHome().create().findGroup(id, userSessionData);
		} catch (Exception e) {
			e.printStackTrace();
	        handler.handleException("findGroup", e);
		}
		return null;
	}


  /**
   * @ejb.interface-method
   * @ejb.permission role-name = "administrator,systemaccess"
   */
  public GroupData findNoPdfGroup(Integer id, UserSessionData userSessionData) throws Exception {
	  	try {
			return AssesmentReportUtil.getHome().create().findNoPdfGroup(id, userSessionData);
		} catch (Exception e) {
			e.printStackTrace();
	        handler.handleException("findGroup", e);
		}
		return null;
	}

  /**
   * @ejb.interface-method
   * @ejb.permission role-name = "administrator,systemaccess"
   */
 public CategoryData findCategory(Integer id, UserSessionData userSessionData) throws Exception {
	  	try {
			return AssesmentReportUtil.getHome().create().findCategory(id, userSessionData);
		} catch (Exception e) {
			e.printStackTrace();
	        handler.handleException("findCategory", e);
		}
		return null;
	}

  /**
   * @ejb.interface-method
   * @ejb.permission role-name = "administrator,systemaccess"
   */
  	public Collection<AssesmentAttributes> findAssessmentForGroup(Integer group, UserSessionData userSessionData) throws Exception {
	  	try {
			return AssesmentReportUtil.getHome().create().findAssessmentForGroup(group, userSessionData);
		} catch (Exception e) {
			e.printStackTrace();
	        handler.handleException("findAssessmentForGroup", e);
		}
		return null;
	}

	
    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess"
     * @param id
     * @return
     * @throws Exception
     */
    public AssesmentData findAssesmentByModule(Integer moduleId,UserSessionData userSessionData) throws Exception {
        if (userSessionData == null) {
            throw new DeslogedException("findAssesment","userSessionData = null");
        }
        if (moduleId == null) {
            throw new InvalidDataException("findAssesment","id = null");
        }
        try {
            AssesmentReportHome home = AssesmentReportUtil.getHome();
            AssesmentReport assesmentReport = home.create();
            return assesmentReport.findAssesmentByModule(moduleId, userSessionData);
        }catch (Exception e) {
            handler.handleException("findAssesment",e);
        }
        return null;
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess"
     * @param id
     * @return
     * @throws Exception
     */
    public HashMap<String, HashMap<Integer, Object[]>> getUserGroupResults(Integer groupId,UserSessionData userSessionData) throws Exception {
        if (userSessionData == null) {
            throw new DeslogedException("getUserGroupResult   s","userSessionData = null");
        }
        if (groupId == null) {
            throw new InvalidDataException("getUserGroupResults","id = null");
        }
        try {
        	AssesmentReport report = AssesmentReportUtil.getHome().create();
        	if(isNewGroup(groupId)) {
        		return report.getNewUserGroupResults(groupId, userSessionData);
        	}else {
        		return report.getUserGroupResults(groupId, userSessionData);
        	}
        }catch (Exception e) {
            handler.handleException("getUserGroupResults",e);
        }
        return null;
    }

    private boolean isNewGroup(int groupId) {
		return groupId == GroupData.ACHE || groupId == GroupData.MERCADOLIBRE || groupId == GroupData.MERCADOLIVRE;
	}
    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess"
     * @param id
     * @return
     * @throws Exception
     */
    public HashMap<String, HashMap<Integer, Object[]>> getUserCediResults(Integer groupId,Integer cedi,UserSessionData userSessionData) throws Exception {
        if (userSessionData == null) {
            throw new DeslogedException("getUserCediResults   s","userSessionData = null");
        }
        if (groupId == null) {
            throw new InvalidDataException("getUserCediResults","id = null");
        }
        if (cedi == null) {
            throw new InvalidDataException("getUserCediResults","cedi = null");
        }
        try {
        	AssesmentReport report = AssesmentReportUtil.getHome().create();
        	return report.getUserCediResults(groupId,cedi, userSessionData);
        	
        }catch (Exception e) {
            handler.handleException("getUserCediResults",e);
        }
        return null;
    }

    
	/**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess"
     * @param name
     * @param userRequest
     * @return
     * @throws Exception
     */
    public UserMultiAssessmentData getMultiAssessment(String user, Integer assessmentId, UserSessionData userSessionData) throws Exception {
        try {
            return AssesmentReportUtil.getHome().create().getMultiAssessment(user, assessmentId, userSessionData);
        }catch (Exception e) {
            handler.handleException("getMultiAssessment",e);
        }
        return null;
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess"
     * @param name
     * @param userRequest
     * @return
     * @throws Exception
     */
    public UserMultiAssessmentData getMultiAssessment(UserSessionData userSessionData) throws Exception {
        try {
            return AssesmentReportUtil.getHome().create().getMultiAssessment(userSessionData);
        }catch (Exception e) {
            handler.handleException("getMultiAssessment",e);
        }
        return null;
    }


	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,systemaccess"
	 */
	public Collection findGroups(String name, Integer corporation, UserSessionData userSessionData) throws Exception {
        try {
            return AssesmentReportUtil.getHome().create().findGroups(name, corporation,userSessionData);
        }catch (Exception e) {
            handler.handleException("getMultiAssessment",e);
        }
        return null;
    }
	
	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,systemaccess"
	 */
	public Collection findWebinars(String wbCode, String wb1, String wb2, String wb3,String wb4,String wb5, UserSessionData userSessionData) throws Exception {
        try {
            return AssesmentReportUtil.getHome().create().findWebinars(wbCode, wb1, wb2, wb3, wb4, wb5,userSessionData);
        }catch (Exception e) {
            handler.handleException("findwebinars",e);
        }
        return null;
    }
	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,systemaccess"
	 */
	public String[] getWebinarAdvance(String wbCode, String assesmentId, String login, UserSessionData userSessionData) throws Exception {
        try {
            return AssesmentReportUtil.getHome().create().getWebinarAdvance(wbCode, assesmentId,login, userSessionData);
        }catch (Exception e) {
            handler.handleException("getWebinarAdvance",e);
        }
        return null;
    }
	  /**
	   * @ejb.interface-method 
	   * @ejb.permission role-name = "administrator,systemaccess"
	   * @param id
	   * @return
	   * @throws Exception
	   */
	  public HashMap<String, HashMap<Integer, Object[]>> getUserSpecificQuestion(String questions, String login, UserSessionData userSessionData) throws Exception {
		  try {
			  return AssesmentReportUtil.getHome().create().getUserSpecificQuestion(questions, login, userSessionData);
		  }catch (Exception e) {
			  handler.handleException("getUserSpecificQuestion",e);
	      }
	      return null;
	  }
	  

	    /**
	     * @ejb.interface-method 
	     * @ejb.permission role-name = "administrator,systemaccess"
	     * @param id
	     * @return
	     * @throws Exception
	     */
	    public HashMap<String, HashMap<Integer, Object[]>> getUserCediResults(Integer[] cedi,UserSessionData userSessionData) throws Exception {
	        if (userSessionData == null) {
	            throw new DeslogedException("getUserCediResults   s","userSessionData = null");
	        }
	        if (cedi == null) {
	            throw new InvalidDataException("getUserCediResults","id = null");
	        }
	        try {
	        	AssesmentReport report = AssesmentReportUtil.getHome().create();
        		return report.getUserCediResults(cedi, userSessionData);
	        }catch (Exception e) {
	            handler.handleException("getUserCediResults",e);
	        }
	        return null;
	    }


	    /**
	     * @ejb.interface-method 
	     * @ejb.permission role-name = "administrator,systemaccess"
	     * @param id
	     * @return
	     * @throws Exception
	     *
	    public HashMap<String, HashMap<Integer, Object[]>> getUserCediResults(UserSessionData userSessionData) throws Exception {
	        if (userSessionData == null) {
	            throw new DeslogedException("getUserCediResults   s","userSessionData = null");
	        }
	        try {
	        	AssesmentReport report = AssesmentReportUtil.getHome().create();
        		return report.getUserCediResults(userSessionData);
	        }catch (Exception e) {
	            handler.handleException("getUserCediResults",e);
	        }
	        return null;
	    }	  */
	    /**
	     * @ejb.interface-method
	     * @ejb.permission role-name = "administrator,systemaccess"
	     */
	    public Integer getAssesmentId(String user, String webinarCode, UserSessionData userSessionData) throws Exception {
	        try {
	            AssesmentReport assesmentReport = AssesmentReportUtil.getHome().create();
	            return assesmentReport.getAssesmentId(user, webinarCode, userSessionData);
	        } catch (Exception exception) {
	            handler.handleException("getAssesmentId", exception);
	        }
	        return null;
	    }
	    
		/**
		 * @ejb.interface-method
		 * @ejb.permission role-name = "administrator,systemaccess, clientreporter"
		 */
		public Collection findMutualAssesmentResults(Integer assesment,Integer cedi, UserSessionData userSessionData) throws Exception {
	        try {
	            return AssesmentReportUtil.getHome().create().findMutualAssesmentResults(assesment,cedi, userSessionData);
	        }catch (Exception e) {
	            handler.handleException("findMutualAssesmentResults",e);
	        }
	        return null;
	    }


		/**
		 * @ejb.interface-method
		 * @ejb.permission role-name = "administrator,systemaccess"
		 */
		public Collection getWebinarPersonalData(String login, UserSessionData userSessionData) throws Exception {
	        try {
	            return AssesmentReportUtil.getHome().create().getWebinarPersonalData(login, userSessionData);
	        }catch (Exception e) {
	            handler.handleException("getWebinarPersonalData",e);
	        }
	        return new LinkedList();
	    }
}