/*
 * Created on 30-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.business.question;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.ejb.SessionBean;

import net.sf.jasperreports.engine.JRDataSource;
import assesment.business.util.ExceptionHandler;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.exception.DeslogedException;
import assesment.communication.exception.InvalidDataException;
import assesment.communication.language.Text;
import assesment.communication.module.ModuleData;
import assesment.communication.question.AnswerData;
import assesment.communication.question.QuestionData;
import assesment.communication.question.VideoData;
import assesment.communication.report.ModuleResultReportDataSource;
import assesment.communication.report.QuestionModuleResultDataSource;
import assesment.communication.report.QuestionResultReportDataSource;
import assesment.persistence.assesment.AssesmentReport;
import assesment.persistence.assesment.AssesmentReportUtil;
import assesment.persistence.module.ModuleReport;
import assesment.persistence.module.ModuleReportUtil;
import assesment.persistence.question.QuestionReport;
import assesment.persistence.question.QuestionReportHome;
import assesment.persistence.question.QuestionReportUtil;

/**
 * @ejb.bean name="QuestionReportFacade"
 *           display-name="Name for QuestionReportFacade"
 *           description="Description for QuestionReportFacade"
 *           jndi-name="ejb/QuestionReportFacade"
 *           type="Stateless"
 *           view-type="both"
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
 *          ejb-name ="AssesmentReport"
 *          ref-name = "ejb/AssesmentReport"
 *          view-type ="remote"
 * 
 * @jboss.ejb-ref-jndi
 *          jndi-name = "ejb/AssesmentReport" 
 *          ref-name = "AssesmentReport"
 *
 * @ejb.util generate="physical"
 *
 */
public abstract class QuestionReportFacadeBean implements SessionBean {

    ExceptionHandler handler = new ExceptionHandler(QuestionReportFacadeBean.class);

    /**
	 * Create method
	 * 
	 * @ejb.create-method
	 * @ejb.permission role-name = "systemaccess,administrator,clientreporter,cepareporter"
	 *  
	 */
	public void ejbCreate() throws javax.ejb.CreateException {
	}
	
	/**
	 * @ejb.interface-method 
     * @ejb.permission role-name = "administrator"
	 */
	public QuestionData findQuestion(Integer id,UserSessionData userSessionData,int type) throws Exception {
		if (userSessionData == null) {
			throw new DeslogedException("findQuestion","userSessionData = null");
		}
		if (id == null) {
			throw new InvalidDataException("findQuestion","id = null");
		}
		try {
            QuestionReportHome home = QuestionReportUtil.getHome();
            QuestionReport questionReport = home.create();
			return questionReport.findQuestion(id, userSessionData, type);
		}catch (Exception e) {
            handler.handleException("findQuestion",e);
		}
		return null;
	}
	
    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator"
     */
    public QuestionResultReportDataSource findQuestionResult(Integer question, UserSessionData userSessionData, Text messages) throws Exception {
        if (userSessionData == null) {
            throw new DeslogedException("findQuestionResult","userSessionData = null");
        }
        if (question == null) {
            throw new InvalidDataException("findQuestionResult","question = null");
        }
        try {
            QuestionReport questionReport = QuestionReportUtil.getHome().create();
            
            QuestionData questionData = questionReport.findQuestion(question,userSessionData,QuestionData.NORMAL);
            Object[][] values = new Object[questionData.getAnswerSize()][3];
            Iterator<AnswerData> answers = questionData.getAnswerIterator();
            int i = 0;
            while(answers.hasNext()) {
                AnswerData answer = answers.next();
                values[i][0] = answer.getId();
                values[i][1] = answer.getKey();
                values[i][2] = new Integer(0);
                i++;
            }
            
            Collection list = questionReport.findQuestionResults(question, questionData.getType().intValue(), userSessionData);
            if(list != null) {
                Iterator it = list.iterator();
                while(it.hasNext()) {
                    Object[] data = (Object[])it.next();
                    boolean find = false;
                    i = 0; 
                    while(!find && i < values.length) {
                        if(find = ((Integer)values[i][0]).intValue() == ((Integer)data[0]).intValue()) {
                            values[i][2] = data[1];                            
                        }
                        i++;
                    }
                }
            }
            return new QuestionResultReportDataSource(values,questionData.getKey(),questionData.getAnswerSize(),messages);
        }catch (Exception e) {
            handler.handleException("findQuestionResult",e);
        }
        return null;
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator"
     */
    public JRDataSource[] findQuestionReportByModule(Integer module, Integer assesment, UserSessionData userSessionData, Text messages) throws Exception {
        JRDataSource[] result = new JRDataSource[2];
        if (userSessionData == null) {
            throw new DeslogedException("findQuestionReportByModule","userSessionData = null");
        }
        if (module == null) {
            throw new InvalidDataException("findQuestionReportByModule","module = null");
        }
        try {
           
            
            AssesmentReport assesmentReport = AssesmentReportUtil.getHome().create();
            AssesmentAttributes assesmentAttributes = assesmentReport.findAssesmentAttributes(assesment,userSessionData);
            
            ModuleReport moduleReport = ModuleReportUtil.getHome().create();
            ModuleData moduleData = moduleReport.findModule(module,userSessionData);
            Object[][] data = new Object[moduleData.getRelevantQuestionSize()][4];
            Object[][] dataQuestions = new Object[moduleData.getIrelevantAnswerSize()][5];

            Iterator questions = moduleData.getQuestionIterator();
            int i1 = 0;
            int i2 = 0;
            while(questions.hasNext()) {
                QuestionData question = (QuestionData)questions.next();
                if(question.getTestType().intValue() == QuestionData.TEST_QUESTION) {
                    data[i1][0] = question.getKey();
                    data[i1][1] = new Integer(0);
                    data[i1][2] = new Integer(0);
                    data[i1][3] = question.getId();
                    i1++;
                }else {
                    if(question.getType().intValue() == QuestionData.EXCLUDED_OPTIONS
                            || question.getType().intValue() == QuestionData.INCLUDED_OPTIONS
                            || question.getType().intValue() == QuestionData.LIST) {
                        Iterator answers = question.getAnswerIterator();
                        while(answers.hasNext()) {
                            AnswerData answer = (AnswerData) answers.next();
                            dataQuestions[i2][0] = question.getId();
                            dataQuestions[i2][1] = question.getKey();
                            dataQuestions[i2][2] = answer.getId();
                            dataQuestions[i2][3] = answer.getKey();
                            dataQuestions[i2][4] = new Integer(0);
                            i2++;
                        }
                    }
                }
            }
            
            QuestionReport questionReport = QuestionReportUtil.getHome().create();
            Collection list = questionReport.findQuestionReportByModule(module,userSessionData);
            if(list != null) {
                Iterator it = list.iterator();
                while(it.hasNext()) {
                    Object[] values = (Object[])it.next();
                    boolean find = false;
                    for(int j = 0; j < data.length && !find; j++) {
                        if(data[j][0].equals(values[0])) {
                            data[j][((Integer)values[1]).intValue()+1] = values[2];
                            find = true;
                        }
                    }
                }
            }
            
            list = questionReport.findIrelevantQuestionReportByModule(module,userSessionData);
            if(list != null) {
                Iterator it = list.iterator();
                while(it.hasNext()) {
                    Object[] values = (Object[])it.next();
                    boolean find = false;
                    for(int j = 0; j < dataQuestions.length && !find; j++) {
                        if(values[1] != null && ((Integer)dataQuestions[j][0]).intValue() == Integer.parseInt((String)values[0]) 
                                && ((Integer)dataQuestions[j][2]).intValue() == Integer.parseInt((String)values[1])) {
                            dataQuestions[j][4] = values[2];
                            find = true;
                        }
                    }
                }
            }
            
            result[0] = new ModuleResultReportDataSource(data,messages,messages.getText(moduleData.getKey()),assesmentAttributes);
            result[1] = new QuestionModuleResultDataSource(dataQuestions,messages);
            
        }catch (Exception e) {
            handler.handleException("findQuestionReportByModule",e);
        }
        return result;
    }
    
    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator"
     */
    public Integer findAssesmentId(Integer question, UserSessionData userSessionData) throws Exception{
        try {
            QuestionReportHome home = QuestionReportUtil.getHome();
            QuestionReport questionReport = home.create();
            return questionReport.findAssesmentId(question,userSessionData);
        }catch (Exception e) {
            handler.handleException("findAssesmentId",e);
        }
        return null;
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public String[] getAgenSex(String user, Integer assesment,UserSessionData userSessionData) throws Exception {
        try {
            QuestionReportHome home = QuestionReportUtil.getHome();
            QuestionReport questionReport = home.create();
            return questionReport.getAgenSex(user,assesment,userSessionData);
        }catch (Exception e) {
            handler.handleException("findAssesmentId",e);
        }
        return null;
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public String[][] getWrongAnswers(Integer assessment,String user,UserSessionData userSessionData) throws Exception {
        try {
            QuestionReportHome home = QuestionReportUtil.getHome();
            QuestionReport questionReport = home.create();
            return questionReport.getWrongAnswers(assessment,user,userSessionData);
        }catch (Exception e) {
            handler.handleException("getWrongAnswers",e);
        }
        return new String[0][0];
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public String getEmailByQuestion(Integer assessment, String user, Text messages, UserSessionData userSessionData) throws Exception {
    	String email = "";
    	try {
            QuestionReportHome home = QuestionReportUtil.getHome();
            QuestionReport questionReport = home.create();
            Collection list = questionReport.getEmailByQuestion(assessment,user,userSessionData);
            if(list.size() > 0) {
            	if(list.size() > 1) {
            		email = (String)((Object[])list.iterator().next())[1];
            	}else {
            		Iterator it = list.iterator();
            		while(it.hasNext()) {
            			Object[] data = (Object[])it.next();
            			if(messages.getText((String)data[0]).equals("Email")) {
            				email = (String)data[1];
            			}
            		}
            	}
            }
    	}catch (Exception e) {
            handler.handleException("getEmailByQuestion",e);
		}
    	return email;
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public String getImage(Integer questionId,UserSessionData userSessionData) throws Exception {
    	try {
            QuestionReportHome home = QuestionReportUtil.getHome();
            QuestionReport questionReport = home.create();
            QuestionData question = questionReport.findQuestion(questionId, userSessionData, QuestionData.NORMAL);
            if(question != null) {
            	if(question.getImage() != null && question.getImage().length() > 0) {
            		return question.getImage();
            	}else {
            		if(question.getGroup() != null && question.getGroup().intValue() > 0) {
            			return questionReport.getQuestionImage(question.getModule(),question.getGroup(),userSessionData);
            		}
            	}
            }
    	}catch (Exception e) {
            handler.handleException("QuestionData.NORMAL",e);
		}
    	return null;
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,clientreporter,cepareporter"
     */
   public HashMap<String, Object> getFullAnswers(Integer assessment, UserSessionData userSessionData) throws Exception {
       return QuestionReportUtil.getHome().create().getFullAnswers(assessment,userSessionData);
   }

   /**
    * @ejb.interface-method 
    * @ejb.permission role-name = "administrator,systemaccess"
    */
  	public String[][] getCompleteAnswers(Integer assessment, String login, UserSessionData userSessionData) throws Exception {
        return QuestionReportUtil.getHome().create().getCompleteAnswers(assessment,login,userSessionData);
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess,clientreporter,cepareporter"
     */
   	public Integer[][] getOptionAnswers(Integer assessment, String login, UserSessionData userSessionData) throws Exception {
         return QuestionReportUtil.getHome().create().getOptionAnswers(assessment,login,userSessionData);
     }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess"
     */
   	public Collection<VideoData> getVideos(UserSessionData userSessionData) throws Exception {
         return QuestionReportUtil.getHome().create().getVideos(userSessionData);
     }

    
    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess"
     */
   	public Collection getSurveyResult(String login, UserSessionData userSessionData) throws Exception {
        return QuestionReportUtil.getHome().create().getSurveyResult(login, userSessionData);
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess"
     */
   	public Collection getTotalAnswers(Integer assessment, Text messages, UserSessionData userSessionData) throws Exception {
        return QuestionReportUtil.getHome().create().getTotalAnswers(assessment, messages, userSessionData);
    }
}