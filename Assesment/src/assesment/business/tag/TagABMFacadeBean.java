package assesment.business.tag;

import java.util.Collection;
import java.util.Iterator;

import javax.ejb.SessionBean;

import assesment.business.util.ExceptionHandler;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.exception.DeslogedException;
import assesment.communication.exception.InvalidDataException;
import assesment.communication.question.AnswerData;
import assesment.communication.question.QuestionData;
import assesment.communication.tag.TagData;
import assesment.persistence.question.QuestionReport;
import assesment.persistence.question.QuestionReportUtil;
import assesment.persistence.tag.TagABM;
import assesment.persistence.tag.TagABMUtil;

/**
 * @ejb.bean name="TagABMFacade"
 *           display-name="Name for Tag"
 *           description="Description for Tag"
 *           jndi-name="ejb/TagABMFacade"
 *           type="Stateless"
 *           view-type="remote"
 * 
 * @ejb.ejb-ref 
 *          ejb-name ="TagABM"
 *          ref-name = "ejb/TagABM"
 *          view-type ="remote"
 * 
 * @jboss.ejb-ref-jndi
 *          jndi-name = "ejb/TagABM" 
 *          ref-name = "TagABM"
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
 * @ejb.util generate="physical"
*/
public abstract class TagABMFacadeBean implements SessionBean {
	
    ExceptionHandler handler = new ExceptionHandler(TagABMFacadeBean.class);
    
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
	public void associateAssesmentTag(Integer assesment,Collection tags,UserSessionData userSessionData) throws Exception{
		if (userSessionData == null) {
			throw new DeslogedException("associateAssesmentTag","session = null");
		}
		if (assesment == null) {
			throw new InvalidDataException("associateAssesmentTag","assesment = null");
		}
		try {
            TagABM tagABM = TagABMUtil.getHome().create();
            Iterator it = tags.iterator();
            while(it.hasNext()) {
                tagABM.associateAssesment(new Integer((String)it.next()),assesment,TagData.DEFAULT_MIN,userSessionData);
            }
		} catch (Exception e) {
            handler.handleException("associateAssesmentTag",e);
		}
	}

    /**
     * @ejb.interface-method    view-type = "remote"
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "administrator"
     */
    public void associateQuestionTag(Integer questionId,Collection tags,UserSessionData userSessionData) throws Exception{
        if (userSessionData == null) {
            throw new DeslogedException("associateQuestionTag","session = null");
        }
        if (questionId == null) {
            throw new InvalidDataException("associateQuestionTag","question = null");
        }
        try {
            QuestionReport questionReport = QuestionReportUtil.getHome().create();
            QuestionData question = questionReport.findQuestion(questionId,userSessionData,QuestionData.NORMAL);
            
            TagABM tagABM = TagABMUtil.getHome().create();
            Iterator<AnswerData> answers = question.getAnswerIterator();
            while(answers.hasNext()) {
                AnswerData answer = answers.next();
                Integer value = (answer.getResultType().intValue() == AnswerData.CORRECT) ? 0 : 1;
                Iterator it = tags.iterator();
                while(it.hasNext()) {
                    tagABM.associateAnswer(new Integer((String)it.next()),answer.getId(),value,userSessionData);
                }
            }
        } catch (Exception e) {
            handler.handleException("associateQuestionTag",e);
        }
    }

    /**
     * @ejb.interface-method    view-type = "remote"
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "administrator"
     */
    public void desassociateAssesmentTag(Integer assesment,Collection tags,UserSessionData userSessionData) throws Exception{
        if (userSessionData == null) {
            throw new DeslogedException("desassociateAssesmentTag","session = null");
        }
        if (assesment == null) {
            throw new InvalidDataException("desassociateAssesmentTag","assesment = null");
        }
        try {
            TagABM tagABM = TagABMUtil.getHome().create();
            Iterator it = tags.iterator();
            while(it.hasNext()) {
                Integer tag = new Integer((String)it.next());
                tagABM.desassociateAssesmentQuestion(tag,assesment,userSessionData);
                tagABM.desassociateAssesment(tag,assesment,userSessionData);
            }
        } catch (Exception e) {
            handler.handleException("desassociateAssesmentTag",e);
        }
    }

    /**
     * @ejb.interface-method    view-type = "remote"
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "administrator"
     */
    public void desassociateQuestionTag(Integer question,Collection tags,UserSessionData userSessionData) throws Exception{
        if (userSessionData == null) {
            throw new DeslogedException("desassociateQuestionTag","session = null");
        }
        if (question == null) {
            throw new InvalidDataException("desassociateQuestionTag","assesment = null");
        }
        try {
            TagABM tagABM = TagABMUtil.getHome().create();
            Iterator it = tags.iterator();
            while(it.hasNext()) {
                Integer tag = new Integer((String)it.next());
                tagABM.desassociateQuestion(tag,question,userSessionData);
            }
        } catch (Exception e) {
            handler.handleException("desassociateQuestionTag",e);
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
        try {
            TagABM tagABM = TagABMUtil.getHome().create();
            tagABM.updateAnswerTag(tag,answer,value,userSessionData);
        } catch (Exception e) {
            handler.handleException("updateAnswerTag",e);
        }
    }
}