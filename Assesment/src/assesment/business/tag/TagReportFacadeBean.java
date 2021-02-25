package assesment.business.tag;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.SessionBean;

import assesment.business.util.ExceptionHandler;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.exception.DeslogedException;
import assesment.communication.language.Text;
import assesment.persistence.tag.TagReport;
import assesment.persistence.tag.TagReportUtil;

/**
 * @ejb.bean name="TagReportFacade"
 *           display-name="Name for Tag"
 *           description="Description for Tag"
 *           jndi-name="ejb/TagReportFacade"
 *           type="Stateless"
 *           view-type="remote"
 * 
 * @ejb.ejb-ref 
 *          ejb-name ="TagReport"
 *          ref-name = "ejb/TagReport"
 *          view-type ="remote"
 * 
 * @jboss.ejb-ref-jndi
 *          jndi-name = "ejb/TagReport" 
 *          ref-name = "TagReport"
 * 
 * @ejb.util generate="physical"
*/
public abstract class TagReportFacadeBean implements SessionBean {
	
    ExceptionHandler handler = new ExceptionHandler(TagReportFacadeBean.class);
    
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
	public Collection getListNotAssociated(Integer assesment,Text messages,UserSessionData userSessionData) throws Exception{
		if (userSessionData == null) {
			throw new DeslogedException("getListNotAssociated","session = null");
		}
		try {
            TagReport tagReport = TagReportUtil.getHome().create();
            return tagReport.getListNotAssociated(assesment,messages,userSessionData);
		} catch (Exception e) {
            handler.handleException("getListNotAssociated",e);
		}
        return new LinkedList();
	}

    /**
     * @ejb.interface-method    view-type = "remote"
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "administrator"
     */
    public Collection getListAssociated(Integer assesment,UserSessionData userSessionData) throws Exception{
        if (userSessionData == null) {
            throw new DeslogedException("getListAssociated","session = null");
        }
        try {
            TagReport tagReport = TagReportUtil.getHome().create();
            return tagReport.getListAssociated(assesment,userSessionData);
        } catch (Exception e) {
            handler.handleException("getListAssociated",e);
        }
        return new LinkedList();
    }

    /**
     * @ejb.interface-method    view-type = "remote"
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "administrator"
     */
    public Collection getListNotAssociatedQuestion(Integer assesment,Integer question,UserSessionData userSessionData) throws Exception{
        if (userSessionData == null) {
            throw new DeslogedException("getListNotAssociatedQuestion","session = null");
        }
        try {
            TagReport tagReport = TagReportUtil.getHome().create();
            return tagReport.getListNotAssociatedQuestion(assesment,question,userSessionData);
        } catch (Exception e) {
            handler.handleException("getListNotAssociatedQuestion",e);
        }
        return new LinkedList();
    }

    /**
     * @ejb.interface-method    view-type = "remote"
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "administrator"
     */
    public Collection getListAssociatedQuestion(Integer question,UserSessionData userSessionData) throws Exception{
        if (userSessionData == null) {
            throw new DeslogedException("getListAssociatedQuestion","session = null");
        }
        try {
            TagReport tagReport = TagReportUtil.getHome().create();
            return tagReport.getListAssociatedQuestion(question,userSessionData);
        } catch (Exception e) {
            handler.handleException("getListAssociatedQuestion",e);
        }
        return new LinkedList();
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     * @throws Exception
     */
    public String[] getAnswerTag(Integer tag,Integer answer,UserSessionData userSessionData) throws Exception {
        if (userSessionData == null) {
            throw new DeslogedException("getAnswerTag","session = null");
        }
        try {
            TagReport tagReport = TagReportUtil.getHome().create();
            return tagReport.getAnswerTag(tag,answer,userSessionData);
        } catch (Exception e) {
            handler.handleException("getAnswerTag",e);
        }
        return null;
    }

    /**
     * @ejb.interface-method    view-type = "remote"
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public Collection getAssociatedLessons(String user,Integer assesment,UserSessionData userSessionData) throws Exception {
        if (userSessionData == null) {
            throw new DeslogedException("getAssociatedLessons","session = null");
        }
        if (user == null) {
            throw new DeslogedException("getAssociatedLessons","user = null");
        }
        if (assesment == null) {
            throw new DeslogedException("getAssociatedLessons","assesment = null");
        }
        try {
            TagReport tagReport = TagReportUtil.getHome().create();
            return tagReport.getAssociatedLessons(user,assesment,userSessionData);
        } catch (Exception e) {
            handler.handleException("getAssociatedLessons",e);
        }
        return null;
    }

    /**
     * @ejb.interface-method    view-type = "remote"
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public Collection getTextAssociatedLessons(String user,Integer assesment,UserSessionData userSessionData) throws Exception {
        if (userSessionData == null) {
            throw new DeslogedException("getTextAssociatedLessons","session = null");
        }
        if (user == null) {
            throw new DeslogedException("getTextAssociatedLessons","user = null");
        }
        if (assesment == null) {
            throw new DeslogedException("getTextAssociatedLessons","assesment = null");
        }
        try {
            TagReport tagReport = TagReportUtil.getHome().create();
            return tagReport.getTextAssociatedLessons(user,assesment,userSessionData);
        } catch (Exception e) {
            handler.handleException("getTextAssociatedLessons",e);
        }
        return null;
    }
}