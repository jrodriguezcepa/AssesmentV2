/*
 * Created on 30-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.persistence.assesment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;

import assesment.communication.administration.AccessCodeData;
import assesment.communication.administration.UserMultiAssessmentData;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.assesment.CategoryData;
import assesment.communication.assesment.FeedbackData;
import assesment.communication.assesment.GroupData;
import assesment.communication.assesment.GroupUsersData;
import assesment.communication.assesment.ResultLine;
import assesment.communication.assesment.ResultLineJJ;
import assesment.communication.exception.DeslogedException;
import assesment.communication.exception.InvalidDataException;
import assesment.communication.language.Text;
import assesment.communication.question.AnswerData;
import assesment.communication.question.QuestionData;
import assesment.communication.report.UserMutualReportData;
import assesment.communication.security.SecurityConstants;
import assesment.communication.user.UserData;
import assesment.communication.util.ListResult;
import assesment.persistence.administration.tables.AccessCode;
import assesment.persistence.administration.tables.AssessmentUserData;
import assesment.persistence.administration.tables.UserMultiAssessment;
import assesment.persistence.assesment.tables.Assesment;
import assesment.persistence.assesment.tables.AssesmentBKP;
import assesment.persistence.assesment.tables.Category;
import assesment.persistence.assesment.tables.Group;
import assesment.persistence.hibernate.HibernateAccess;
import assesment.persistence.util.ExceptionHandler;
import assesment.persistence.util.PersistenceUtil;
import assesment.persistence.util.Util;

/**
 * @ejb.bean name="AssesmentReport"
 *           display-name="Name for AssesmentReport"
 *           description="Description for AssesmentReport"
 *           jndi-name="ejb/AssesmentReport"
 *           type="Stateless"
 *           view-type="both"
 */
public abstract class AssesmentReportBean implements SessionBean {

    private ExceptionHandler handler = new ExceptionHandler(AssesmentReportBean.class);

    /**
     * @ejb.create-method
     * @ejb.permission role-name = "systemaccess,administrator,accesscode,pepsico_candidatos,basf_assessment,clientreporter,cepareporter"
     * @throws CreateException
     */
    public void ejbCreate() throws CreateException{
    }
    
    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess,accesscode,clientreporter"
     * @param name
     * @param userRequest
     * @return
     * @throws Exception
     */
    public AssesmentAttributes findAssesmentAttributes(Integer id, UserSessionData userSessionData) throws Exception {
        if (id == null) {
            throw new InvalidDataException("findAssesmentAttributes","id = null");
        }
        if (userSessionData == null) {
            throw new DeslogedException("findAssesmentAttributes","session = null");
        }

        Session session = null;
        try {
            session = HibernateAccess.currentSession();
            Assesment assesment = (Assesment)session.load(Assesment.class,id);
            return assesment.getAssesment();
        } catch (Exception e) {
            handler.getException(e,"findAssesmentAttributes",userSessionData.getFilter().getLoginName());
        }
        return null;
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess,basf_assessment,clientreporter,cepareporter"
     * @param name
     * @param userRequest
     * @return
     * @throws Exception
     */
    public AssesmentData findAssesment(Integer id, UserSessionData userSessionData) throws Exception {
        if (id == null) {
            throw new InvalidDataException("findAssesment","id = null");
        }
        if (userSessionData == null) {
            throw new DeslogedException("findAssesment","session = null");
        }

        Session session = null;
        try {
            session = HibernateAccess.currentSession();
            Assesment assesment = (Assesment)session.load(Assesment.class,id);
            return assesment.getData();
        } catch (Exception e) {
            handler.getException(e,"findAssesment",userSessionData.getFilter().getLoginName());
        }
        return null;
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess,clientreporter,cepareporter"
     */
    public Collection<AssesmentAttributes>[] getAssessments(UserSessionData userSessionData) throws Exception {
        Session session = null;
        Collection<AssesmentAttributes>[] assessments = new LinkedList[2];
        assessments[0] = new LinkedList<AssesmentAttributes>();
        assessments[1] = new LinkedList<AssesmentAttributes>();
        try {
            session = HibernateAccess.currentSession();
            String queryCountStr = "SELECT id,name,archived FROM assesments ORDER BY name";
            Query query = session.createSQLQuery(queryCountStr).addScalar("id",Hibernate.INTEGER).addScalar("name",Hibernate.STRING).addScalar("archived",Hibernate.BOOLEAN);
            Iterator iter = query.list().iterator();
            Collection<Object[]> list = new LinkedList<Object[]>();
            while (iter.hasNext()) {
                Object[] data = (Object[])iter.next();
                AssesmentAttributes attr = new AssesmentAttributes((Integer)data[0]);
                attr.setName((String)data[1]);
                if(((Boolean)data[2]).booleanValue()) {
                	assessments[1].add(attr);
                }else {
                	assessments[0].add(attr);
                }
            }
        } catch (Exception e) {
            handler.getException(e,"findAssesment",userSessionData.getFilter().getLoginName());
        }
        return assessments;
    }
    
    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess,clientreporter,cepareporter"
     */
    public ListResult findAssesments(String name,String corporation,String archived,UserSessionData userSessionData) throws Exception {
        Session session = null;
        try {
 
            session = HibernateAccess.currentSession();
            String queryCountStr = "SELECT COUNT(*) AS count FROM assesments AS a JOIN generalmessages gm ON gm.labelkey = a.name WHERE gm.language = '"+userSessionData.getLenguage()+"' AND lower(gm.text) like lower(:name) ";
            if(!PersistenceUtil.empty(corporation)) {
            	queryCountStr += "AND a.corporation = "+corporation;
            }
            switch(Integer.parseInt(archived)) {
	            case 0:
	            	queryCountStr += " AND NOT a.archived"; 
	            	break;
	            case 1:
	            	queryCountStr += " AND a.archived"; 
	        }
            
            Query queryCount = session.createSQLQuery(queryCountStr).addScalar("count",Hibernate.INTEGER);
            queryCount.setParameter("name", "%"+name+"%");
            Integer total = (Integer)queryCount.uniqueResult();
            if(total == null) {
                total = 0;
            }

            String queryStr = "SELECT a.id,a.name as assesment,c.name as corporation,a.astart,a.aend,count(question.id)as count FROM assesments AS a " +
                    "JOIN corporations c ON a.corporation = c.id " +
                    "JOIN generalmessages gm ON gm.labelkey = a.name " +
                    "LEFT JOIN modules m ON m.assesment = a.id " +
                    "LEFT JOIN questions question on question.module = m.id "+
                    "WHERE gm.language = '"+userSessionData.getLenguage()+"' AND lower(gm.text) like lower(:name) ";
            if(!PersistenceUtil.empty(corporation)) {
                queryStr += "AND a.corporation = "+corporation;
            }
            switch(Integer.parseInt(archived)) {
	            case 0:
	            	queryStr += " AND NOT a.archived"; 
	            	break;
	            case 1:
	            	queryStr += " AND a.archived"; 
            }
            queryStr += " GROUP BY a.id,a.name,a.astart,a.aend,c.name ORDER BY a.name";
            Query query = session.createSQLQuery(queryStr).addScalar("id",Hibernate.INTEGER).addScalar("assesment",Hibernate.STRING).addScalar("corporation",Hibernate.STRING).addScalar("aend",Hibernate.DATE).addScalar("astart",Hibernate.DATE).addScalar("count",Hibernate.INTEGER);
            query.setParameter("name", "%"+name+"%");
            
            Iterator iter = query.list().iterator();
            Collection<Object[]> list = new LinkedList<Object[]>();
            while (iter.hasNext()) {
                list.add((Object[])iter.next());
            }
            
            return new ListResult(list,total);
        }catch (Exception e) {
            handler.getException(e,"findAssesments",userSessionData.getFilter().getLoginName());
        }
        return null;
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess,pepsico_candidatos,clientreporter,cepareporter"
     */
    public Integer getAssesmentQuestionCount(AssesmentAttributes assesment,UserSessionData userSessionData, boolean all) throws Exception {
        Session session = null;
        try {
            session = HibernateAccess.currentSession();
            String queryStr = "SELECT COUNT(*) AS count FROM questions q JOIN modules AS m ON m.id = q.module WHERE m.assesment = "+String.valueOf(assesment.getId());
            if(!all) {
                queryStr += " AND q.testtype = "+String.valueOf(QuestionData.TEST_QUESTION);
            }
            Query query = session.createSQLQuery(queryStr).addScalar("count",Hibernate.INTEGER);
            int value = ((Integer)query.uniqueResult()).intValue();
            if(assesment.isPsitest() && all) {
                value += 48;
            }
            return new Integer(value);
        }catch (Exception e) {
            handler.getException(e,"getAssesmentQuestionCount",userSessionData.getFilter().getLoginName());
        }
        return null;
    }
    
    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,accesscode,systemaccess"
     */
    public AccessCodeData getAccessCode(String accesscode,UserSessionData userSessionData) throws Exception {
        Session session = null;
        try {
            session = HibernateAccess.currentSession();
            Query query = session.createQuery("SELECT COUNT(*) AS count FROM AccessCode WHERE code = '"+accesscode+"'");
            if(((Long)query.uniqueResult()).intValue() == 0) {
                return null;
            }
            AccessCode code = (AccessCode)session.load(AccessCode.class,accesscode);
            return code.getAccessCodeData();
        }catch (Exception e) {
            handler.getException(e,"getAccessCode",userSessionData.getFilter().getLoginName());
        }
        return null;
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator"
     */
    public Collection getAccessCodes(UserSessionData userSessionData, Text messages) throws Exception {
        Session session = null;
        Collection result = new LinkedList();
        try {
            session = HibernateAccess.currentSession();
            Query q = session.createQuery("SELECT ac FROM AccessCode ac");
            List list = q.list();
            if(list.size() > 0) {
                Iterator it = list.iterator();
                while(it.hasNext()) {
                    AccessCode code = (AccessCode)it.next();
                    if(code.getAssesment() != null) {
	                    AccessCodeData data = code.getAccessCodeData();
	                    data.setAssesmentName(messages.getText(code.getAssesment().getName()).trim());
	                    if(!code.getAssesment().isArchived())
	                    	result.add(data);
                    }
                }
            }
        }catch (Exception e) {
            handler.getException(e,"getAccessCodes",userSessionData.getFilter().getLoginName());
        }
        return result;
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public String[] findReportData(Integer assesment, UserSessionData userSessionData) throws Exception {
        String queryStr = "SELECT a.name as assesment,c.name as corporation FROM assesments AS a JOIN corporations c ON a.corporation = c.id WHERE a.id = "+String.valueOf(assesment);
        Session session = null;
        try {
            session = HibernateAccess.currentSession();
            Query query = session.createSQLQuery(queryStr).addScalar("assesment",Hibernate.STRING).addScalar("corporation",Hibernate.STRING);
            List list = query.list();
            if(list.size() > 0) {
                Object[] data = (Object[])list.iterator().next();
                String[] s = {(String)data[0],(String)data[1]};
                return s;
            }
        }catch (Exception e) {
            handler.getException(e,"findReportData",userSessionData.getFilter().getLoginName());
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
            Session session = HibernateAccess.currentSession();
            Query query = session.createSQLQuery("SELECT DISTINCT email FROM feedbacks WHERE assesment = "+String.valueOf(assesment)).addScalar("email",Hibernate.STRING);
            Iterator it = query.list().iterator();
            while(it.hasNext()) {
                feedback.add(new FeedbackData((String)it.next(),FeedbackData.GENERAL_RESULT_REPORT));
            }
        }catch (Exception e) {
            handler.getException(e,"getAssesmentFeedback",userSessionData.getFilter().getLoginName());
        }
        return feedback;
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public FeedbackData getFeedback(Integer assesment, String email, UserSessionData userSessionData) throws Exception {
        FeedbackData feedback = new FeedbackData();
        feedback.setEmail(email);
        try {
            Session session = HibernateAccess.currentSession();
            Query query = session.createSQLQuery("SELECT type FROM feedbacks WHERE assesment = "+String.valueOf(assesment)+" AND email = '"+email+"'").addScalar("type",Hibernate.INTEGER);
            Iterator it = query.list().iterator();
            while(it.hasNext()) {
                feedback.getReports().add(it.next());
            }
        }catch (Exception e) {
            handler.getException(e,"getFeedback",userSessionData.getFilter().getLoginName());
        }
        return feedback;
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public Integer getNewHireValue(String user, String assesment, UserSessionData userSessionData) throws Exception {
        try {
            Session session = HibernateAccess.currentSession();
            Query query = session.createSQLQuery("SELECT newhire FROM userassesments WHERE assesment = "+String.valueOf(assesment)+" AND loginname = '"+user+"'").addScalar("newhire",Hibernate.INTEGER);
            return (Integer)query.uniqueResult();
        } catch (Exception e) {
            handler.getException(e,"getNewHireValue", userSessionData.getFilter().getLoginName());
        }
        return null; 
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public Collection getWrongAnswers(String assesment, UserSessionData userSessionData) throws Exception {
        try {
            String sql = "SELECT m.key as mname,m.moduleorder,q.key as qname,COUNT(*) AS c,q.id " +
            "FROM answerdata ad " +
            "JOIN useranswers ua ON ua.answer = ad.id " +
            "JOIN questions q ON q.id = ad.question " +
            "JOIN answers a ON a.id = ad.answer " +
            "JOIN modules m ON m.id = q.module " +
            "WHERE ua.assesment = "+assesment+
            " AND q.testtype = "+QuestionData.TEST_QUESTION+
            " AND a.type = "+AnswerData.INCORRECT+
            " GROUP BY m.key,m.moduleorder,q.key,q.id" +
            " ORDER BY c DESC";
            Session session = HibernateAccess.currentSession();
            SQLQuery query = session.createSQLQuery(sql);
            query.addScalar("mname",Hibernate.STRING);
            query.addScalar("moduleorder",Hibernate.INTEGER);
            query.addScalar("qname",Hibernate.STRING);
            query.addScalar("c",Hibernate.INTEGER);
            query.addScalar("id",Hibernate.INTEGER);
            
            return query.list();
        } catch (Exception e) {
            handler.getException(e,"getNewHireValue", userSessionData.getFilter().getLoginName());
        }
        return new LinkedList(); 
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public Hashtable getUserAnswerCount(String assesment, UserSessionData userSessionData) throws Exception {
        Hashtable questions = new Hashtable();
        try {
            Session session = HibernateAccess.currentSession();
            Query query = session.createSQLQuery("SELECT ad.question,COUNT(*) AS c FROM answerdata ad JOIN useranswers ua ON ua.answer = ad.id WHERE ua.assesment = "+assesment+" GROUP BY question").addScalar("question",Hibernate.INTEGER).addScalar("c",Hibernate.INTEGER);
            Iterator it = query.list().iterator();
            while(it.hasNext()) {
                Object[] data = (Object[])it.next();
                questions.put(data[0],data[1]);
            }
        } catch (Exception e) {
            handler.getException(e,"getNewHireValue", userSessionData.getFilter().getLoginName());
        }
        return questions; 
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     */
    public Collection getAssessmentResults(Integer assessment,Date start, Date end, UserSessionData userSessionData) throws Exception {
        Collection answers = new LinkedList();
        try {
            Session session = HibernateAccess.currentSession();
	        String sql = "SELECT u.loginname,u.firstname,u.lastname,u.country,u.extradata," +
			"ua.psiresult1,ua.psiresult2,ua.psiresult3,ua.psiresult4,ua.psiresult5,ua.psiresult6," +
			"a.type,COUNT(*) AS count " +
			"FROM users u " +
			"JOIN userassesments ua ON ua.loginname = u.loginname " +
			"JOIN useranswers uan ON uan.loginname = u.loginname " +
			"JOIN answerdata ad ON ad.id = uan.answer " +
			"JOIN questions q ON q.id = ad.question " +
			"JOIN answers a ON a.id = ad.answer " +
			"WHERE ua.assesment = " + String.valueOf(assessment) + " " +
			"AND q.testtype = " + QuestionData.TEST_QUESTION + " ";
			if(start != null) {
				sql += " AND ua.enddate >= '" + Util.formatDate(start)+"' ";
			}
			if(end != null) {
				sql += " AND ua.enddate <= '" + Util.formatDate(end)+"' ";
			}
			sql += "GROUP BY u.loginname,u.firstname,u.lastname,u.country," +
			"u.extradata,ua.psiresult1,ua.psiresult2,ua.psiresult3," +
			"ua.psiresult4,ua.psiresult5,ua.psiresult6,a.type " +
			"ORDER BY u.loginname";

			SQLQuery query = session.createSQLQuery(sql);
            query.addScalar("loginname",Hibernate.STRING);
            query.addScalar("firstname",Hibernate.STRING);
            query.addScalar("lastname",Hibernate.STRING);
            query.addScalar("country",Hibernate.INTEGER);
            query.addScalar("extradata",Hibernate.INTEGER);
            query.addScalar("psiresult1",Hibernate.INTEGER);
            query.addScalar("psiresult2",Hibernate.INTEGER);
            query.addScalar("psiresult3",Hibernate.INTEGER);
            query.addScalar("psiresult4",Hibernate.INTEGER);
            query.addScalar("psiresult5",Hibernate.INTEGER);
            query.addScalar("psiresult6",Hibernate.INTEGER);
            query.addScalar("type",Hibernate.INTEGER);
            query.addScalar("count",Hibernate.INTEGER);
            
        	Hashtable values = new Hashtable();

        	Iterator it = query.list().iterator();
        	while(it.hasNext()) {
        		Object[] data = (Object[])it.next();
        		if(values.containsKey(data[0])) {
        			((ResultLine)values.get(data[0])).addQuestions(data);
        		}else {
        			if(AssesmentData.isJJ(assessment.intValue())) {
        				values.put(data[0],new ResultLineJJ(data));
        			}else {
        				values.put(data[0],new ResultLine(data));
        			}
        		}
        	}

        	sql = "SELECT DISTINCT u.loginname,ant.tag FROM answerdata ad " +
			"JOIN useranswers ua ON ad.id = ua.answer " +
			"JOIN userassesments uas ON ua.loginname = uas.loginname " +
			"JOIN answertags ant ON ad.answer = ant.answer " +
			"JOIN assesmenttags ast ON ant.tag = ast.tag " +
			"JOIN users u ON u.loginname = ua.loginname " +
			"WHERE ua.assesment = " +String.valueOf(assessment)+
			" AND ast.assesment = " +String.valueOf(assessment);
			if(start != null) {
				sql += " AND uas.enddate >= '" + Util.formatDate(start)+"' ";
			}
			if(end != null) {
				sql += " AND uas.enddate <= '" + Util.formatDate(end)+"' ";
			}
			sql += " GROUP BY u.loginname,ant.tag " +
			"HAVING sum(ant.value) > 0 " +
			"ORDER BY loginname,tag";
			query = session.createSQLQuery(sql);
            query.addScalar("loginname",Hibernate.STRING);
            query.addScalar("tag",Hibernate.INTEGER);
        	it = query.list().iterator();
        	while(it.hasNext()) {
        		Object[] data = (Object[])it.next();
        		if(values.containsKey(data[0])) {
        			((ResultLine)values.get(data[0])).addLesson(String.valueOf(data[1]));
        		}
        	}
	
        	Enumeration enumeration = values.elements();
        	while(enumeration.hasMoreElements()) {
        		answers.add(enumeration.nextElement());
        	}
        } catch (Exception e) {
            handler.getException(e,"getAssessmentResults", userSessionData.getFilter().getLoginName());
        }
    	return answers;
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,basf_assessment"
     */
    public Integer[] getAvailableAssessments(String user, Integer corporation, UserSessionData userSessionData) throws Exception {
    	Collection list = null;
    	try {
            Session session = HibernateAccess.currentSession();
	        String sql = null;
	    	if(user == null) {
	    		sql = "select id from assesments where corporation = "+corporation+" and status = " + AssesmentAttributes.NO_EDITABLE;
	    		Query q = session.createSQLQuery(sql).addScalar("id", Hibernate.INTEGER);
	    		list = q.list();
	    	}else {
	    		sql = "select assesment from userassesments where loginname = '"+user+"' and assesment in (select id from assesments where corporation = "+corporation+" and status = " + AssesmentAttributes.NO_EDITABLE+") and enddate is null";
	    		Query q = session.createSQLQuery(sql).addScalar("assesment", Hibernate.INTEGER);
	    		list = q.list();
	    		if(list.size() == 0) {
		    		sql = "select id from assesments where corporation = "+corporation+" and status = " + AssesmentAttributes.NO_EDITABLE+" and id not in (select assesment from userassesments where loginname = '"+user+"')";
		    		q = session.createSQLQuery(sql).addScalar("id", Hibernate.INTEGER);
		    		list = q.list();
	    		}
	    	}
        } catch (Exception e) {
            handler.getException(e,"getAvailableAssessments", userSessionData.getFilter().getLoginName());
        }
		return (list == null) ? new Integer[0] : (Integer[]) list.toArray(new Integer[list.size()]);
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     */
    public Collection<AssessmentUserData> getAssessmentUsers (Integer assessment, UserSessionData userSessionData) throws Exception {
    	LinkedList<AssessmentUserData> list = new LinkedList<AssessmentUserData>();
    	try {
            Session session = HibernateAccess.currentSession();
            SQLQuery query = session.createSQLQuery("SELECT firstname, lastname, u.loginname, u.email, u.extraData3, enddate IS NOT NULL AS finished, psitestid IS NOT NULL AS psi, sr.id, sr.sended, sr.certificate, ua.assesment, uar.correct, uar.incorrect " +
            		"FROM userassesments ua " +
            		"LEFT JOIN sendedreports sr ON sr.login = ua.loginname AND sr.assessment = ua.assesment " +
            		"LEFT JOIN userassesmentresults uar on uar.login = ua.loginname AND uar.assesment = ua.assesment " + 
            		"JOIN users u ON u.loginname = ua.loginname " +
            		"WHERE ua.assesment = "+assessment);
            query.addScalar("firstname", Hibernate.STRING);
            query.addScalar("lastname", Hibernate.STRING);
            query.addScalar("loginname", Hibernate.STRING);
            query.addScalar("email", Hibernate.STRING);
            query.addScalar("extraData3", Hibernate.STRING);
            query.addScalar("finished", Hibernate.BOOLEAN);
            query.addScalar("psi", Hibernate.BOOLEAN);
            query.addScalar("id", Hibernate.INTEGER);
            query.addScalar("sended", Hibernate.BOOLEAN);
            query.addScalar("certificate", Hibernate.BOOLEAN);
            query.addScalar("assesment", Hibernate.INTEGER);
            query.addScalar("correct", Hibernate.INTEGER);
            query.addScalar("incorrect", Hibernate.INTEGER);
            
            HashMap<String, AssessmentUserData> values = new HashMap<String, AssessmentUserData>();
            Iterator it = query.list().iterator();
            while(it.hasNext()) {
            	Object[] data = (Object[])it.next();
            	values.put((String)data[2], new AssessmentUserData(data));
            }
            
            query = session.createSQLQuery("SELECT loginname, COUNT(*) AS c FROM useranswers WHERE assesment = "+assessment+" GROUP BY loginname").addScalar("loginname", Hibernate.STRING).addScalar("c", Hibernate.INTEGER);
            it = query.list().iterator();
            while(it.hasNext()) {
            	Object[] data = (Object[])it.next();
            	if(values.containsKey(data[0])) {
            		values.get(data[0]).setAnswers((Integer) data[1]);
            	}
            }
            
            query = session.createSQLQuery("SELECT loginname, count(*) AS c FROM useranswers ua " +
            		"JOIN answerdata ad ON ad.id = ua.answer " +
            		"JOIN questions q ON ad.question = q.id " +
            		"JOIN answers a ON a.id = ad.answer " +
            		"WHERE assesment = "+assessment+" AND q.testtype = 1 AND a.type = 0 " +
            		"GROUP BY loginname").addScalar("loginname", Hibernate.STRING).addScalar("c", Hibernate.INTEGER);
            it = query.list().iterator();
            while(it.hasNext()) {
            	Object[] data = (Object[])it.next();
            	if(values.containsKey(data[0])) {
            		values.get(data[0]).setCorrect((Integer) data[1]);
            	}
            }

            list.addAll(values.values());
        } catch (Exception e) {
            handler.getException(e,"getAssessmentUsers", userSessionData.getFilter().getLoginName());
        }
    	return list;
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
   public GroupData getUserGroup(String user, UserSessionData userSessionData) throws Exception {
	   	GroupData group = null;
    	try {
            Session session = HibernateAccess.currentSession();
    		Query q = session.createQuery("SELECT ug.pk.groupId FROM UserGroup ug WHERE ug.pk.user.loginName = '"+user+"'");
    		Iterator it = q.iterate();
    		if(it.hasNext()) {
    			Group g = (Group)it.next();
    			if(g.getId().intValue() == GroupData.GRUPO_MODELO) {
    				group = g.getData(session, user);
    			}else {
    				group = g.getData(session);
    			}
    		}
    	} catch (Exception e) {
            handler.getException(e,"getUserGroup", userSessionData.getFilter().getLoginName());
		}
    	return group;
    }

   /**
    * @ejb.interface-method
    * @ejb.permission role-name = "administrator,systemaccess"
    */
  public GroupData findGroup(Integer id, UserSessionData userSessionData) throws Exception {
	  	GroupData group = null;
   		try {
           Session session = HibernateAccess.currentSession();
           Group g = (Group)session.load(Group.class, id);
           group = g.getData(session);
   		} catch (Exception e) {
           handler.getException(e,"findGroup", userSessionData.getFilter().getLoginName());
		}
   		return group;
   }
  

	  /**
	   * @ejb.interface-method
	   * @ejb.permission role-name = "administrator,systemaccess"
	   */
	 public GroupData findNoPdfGroup(Integer id, UserSessionData userSessionData) throws Exception {
	  	GroupData group = null;
  		try {
          Session session = HibernateAccess.currentSession();
          Group g = (Group)session.load(Group.class, id);
          group = g.getNoPdfData(session);
  		} catch (Exception e) {
          handler.getException(e,"findGroup", userSessionData.getFilter().getLoginName());
		}
  		return group;
	 }
  

  /**
   * @ejb.interface-method
   * @ejb.permission role-name = "administrator,systemaccess"
   */
  	public CategoryData findCategory(Integer id, UserSessionData userSessionData) throws Exception {
	  	CategoryData category = null;
  		try {
          Session session = HibernateAccess.currentSession();
          Category c = (Category)session.load(Category.class, id);
          category = c.getData(session);
  		} catch (Exception e) {
          handler.getException(e,"findCategory", userSessionData.getFilter().getLoginName());
		}
  		return category;
  	}

  /**
   * @ejb.interface-method
   * @ejb.permission role-name = "administrator,systemaccess"
   */
  public Collection<AssesmentAttributes> findAssessmentForGroup(Integer group, UserSessionData userSessionData) throws Exception {
	  	Collection<AssesmentAttributes> list = new LinkedList<AssesmentAttributes>();
 		try {
            Session session = HibernateAccess.currentSession();
            Query q = session.createSQLQuery("SELECT id, name FROM assesments WHERE corporation IN (SELECT corporation FROM groups WHERE id = "+group+") "
            		+ "AND id NOT IN (SELECT assesment FROM assesmentcategories WHERE category IN (SELECT id FROM categories WHERE groupid = "+group+")) "
            		+ "AND NOT archived").addScalar("id", Hibernate.INTEGER).addScalar("name", Hibernate.STRING);
            Iterator it = q.list().iterator();
            while(it.hasNext()) {
            	Object[] data = (Object[]) it.next();
            	list.add(new AssesmentAttributes((Integer)data[0], (String)data[1]));
            }
   		} catch (Exception e) {
            handler.getException(e,"findAssessmentForGroup", userSessionData.getFilter().getLoginName());
 		}
    	return list;
  }


  /**
   * @ejb.interface-method 
   * @ejb.permission role-name = "administrator,systemaccess,basf_assessment,clientreporter,cepareporter"
   * @param name
   * @param userRequest
   * @return
   * @throws Exception
   */
  public AssesmentData findAssesmentByModule(Integer moduleId, UserSessionData userSessionData) throws Exception {
      if (moduleId == null) {
          throw new InvalidDataException("findAssesment","id = null");
      }
      if (userSessionData == null) {
          throw new DeslogedException("findAssesment","session = null");
      }

      Session session = null;
      try {
          session = HibernateAccess.currentSession();
          
          Query q = session.createSQLQuery("SELECT assesment FROM modules WHERE id = "+moduleId).addScalar("assesment", Hibernate.INTEGER);
          
          List l = q.list();
          if(l.size() > 0) {
	            Assesment assesment = (Assesment)session.load(Assesment.class, (Integer)l.iterator().next());
	            return assesment.getData();
          }
      } catch (Exception e) {
          handler.getException(e,"findAssesment",userSessionData.getFilter().getLoginName());
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
  public HashMap<String, HashMap<Integer, Object[]>> getNewUserGroupResults(Integer groupId,UserSessionData userSessionData) throws Exception {
      Session session = null;
      HashMap<String, HashMap<Integer, Object[]>> results = new HashMap<String, HashMap<Integer,Object[]>>();
      try {
          session = HibernateAccess.currentSession();
          
          String sql1 = "SELECT DISTINCT u.loginname, ua.assesment, ua.enddate, uar.correct, uar.incorrect, uar.type "
          		+ "FROM usergroups ug " + 
          		"JOIN users u ON u.loginname = ug.loginname " + 
          		"JOIN userassesments ua ON u.loginname = ua.loginname " + 
          		"JOIN assesmentcategories ac ON ua.assesment = ac.assesment " + 
          		"JOIN categories c ON c.id = ac.category " + 
          		"LEFT JOIN userassesmentresults uar ON uar.assesment = ua.assesment AND uar.login = ua.loginname "+ 
          		"WHERE ug.groupid = " + groupId +
          		" AND c.groupid = c.groupid " + 
          		"AND u.role = '"+SecurityConstants.GROUP_ASSESSMENT+"'";  
          Query q = session.createSQLQuery(sql1).addScalar("loginname", Hibernate.STRING).addScalar("assesment", Hibernate.INTEGER).addScalar("enddate", Hibernate.DATE)
        		  .addScalar("correct", Hibernate.INTEGER).addScalar("incorrect", Hibernate.INTEGER).addScalar("type", Hibernate.INTEGER);
          Iterator it = q.list().iterator();
          while(it.hasNext()) {
        	  Object[] data = (Object[])it.next();
        	  if(data[5] == null || ((Integer)data[5]).intValue() == 1) {
        		  String user = (String) data[0];
        		  Integer assessment = (Integer) data[1];
        		  Date end = (Date) data[2];
        		  Integer correct = (Integer) data[3];
        		  Integer incorrect = (Integer) data[4];
        		  
        		  int r = (end == null) ? 0 : 1;
          		  Object[] values = {new Integer(r), end, correct, incorrect};
            	  if(!results.containsKey(user)) {
            		  HashMap<Integer, Object[]> result = new HashMap<Integer, Object[]>();
            		  result.put(assessment, values);
            		  results.put(user, result);
            	  }else {
            		  results.get(user).put(assessment, values);
            	  }
        	  }
          }

      } catch (Exception e) {
          handler.getException(e,"getNewUserGroupResults",userSessionData.getFilter().getLoginName());
      }
      return results;
  }
  
  /**
   * @ejb.interface-method 
   * @ejb.permission role-name = "administrator,systemaccess"
   * @param id
   * @return
   * @throws Exception
   */
  public HashMap<String, HashMap<Integer, Object[]>> getUserGroupResults(Integer groupId,UserSessionData userSessionData) throws Exception {
      Session session = null;
      HashMap<String, HashMap<Integer, Object[]>> results = new HashMap<String, HashMap<Integer,Object[]>>();
      try {
          session = HibernateAccess.currentSession();
          
          String sql1 = "SELECT DISTINCT u.loginname, ua.assesment FROM usergroups ug " + 
          		"JOIN users u ON u.loginname = ug.loginname " + 
          		"JOIN userassesments ua ON u.loginname = ua.loginname " + 
          		"JOIN assesmentcategories ac ON ua.assesment = ac.assesment " + 
          		"JOIN categories c ON c.id = ac.category " + 
          		"WHERE ug.groupid = " + groupId +
          		" AND c.groupid = c.groupid " + 
          		"AND u.role = '"+SecurityConstants.GROUP_ASSESSMENT+"' " + 
          		"AND enddate IS NULL"; 
          Query q = session.createSQLQuery(sql1).addScalar("loginname", Hibernate.STRING).addScalar("assesment", Hibernate.INTEGER);
          Iterator it = q.list().iterator();
          while(it.hasNext()) {
        	  Object[] data = (Object[])it.next();
        	  String user = (String) data[0];
        	  Integer assessment = (Integer) data[1];
    		  Object[] values = {new Integer(0), null, new Integer(0), new Integer(0)};
        	  if(!results.containsKey(user)) {
        		  HashMap<Integer, Object[]> result = new HashMap<Integer, Object[]>();
        		  result.put(assessment, values);
        		  results.put(user, result);
        	  }else {
        		  results.get(user).put(assessment, values);
        	  }
          }
          
          String sql2 = "SELECT DISTINCT u.loginname, ua.assesment, ua.enddate, a.type, COUNT(*) AS c " +
          		    "FROM usergroups ug " + 
            		"JOIN users u ON u.loginname = ug.loginname " + 
            		"JOIN userassesments ua ON u.loginname = ua.loginname " + 
            		"JOIN useranswers uas ON uas.loginname = ua.loginname AND uas.assesment = ua.assesment " + 
            		"JOIN answerdata ad ON ad.id = uas.answer "+
            		"JOIN questions q ON q.id = ad.question "+
            		"JOIN answers a ON a.id = ad.answer "+
            		"JOIN assesmentcategories ac ON ua.assesment = ac.assesment " + 
            		"JOIN categories c ON c.id = ac.category " + 
            		"WHERE ug.groupid = " + groupId +
            		" AND c.groupid = c.groupid " + 
            		"AND u.role = '"+SecurityConstants.GROUP_ASSESSMENT+"' " + 
            		"AND enddate IS NOT NULL AND q.testtype = "+QuestionData.TEST_QUESTION +
            		" GROUP BY u.loginname, ua.assesment, ua.enddate, a.type"; 
            Query q2 = session.createSQLQuery(sql2).addScalar("loginname", Hibernate.STRING).addScalar("assesment", Hibernate.INTEGER).addScalar("enddate", Hibernate.DATE).addScalar("type", Hibernate.INTEGER).addScalar("c", Hibernate.INTEGER);
            it = q2.list().iterator();
            while(it.hasNext()) {
          	  Object[] data = (Object[])it.next();
          	  String user = (String) data[0];
          	  Integer assessment = (Integer) data[1];
          	  Date enddate = (Date) data[2];
          	  Integer type = (Integer) data[3];
          	  Integer count = (Integer) data[4];
      		  Object[] values = {new Integer(1), enddate, new Integer(0), new Integer(0)};
      		  values[type+2] = count;
          	  if(!results.containsKey(user)) {
          		  HashMap<Integer, Object[]> result = new HashMap<Integer, Object[]>();
          		  result.put(assessment, values);
          		  results.put(user, result);
          	  }else {
          		HashMap<Integer, Object[]> result = results.get(user);
          		if(result.containsKey(assessment)) {
          			result.get(assessment)[type+2] = count;
          		}else {
          			result.put(assessment, values);
          		}
          	  }
            }
            
            Query q3 = session.createSQLQuery("SELECT DISTINCT ac.assesment FROM assesmentcategories ac "
            		+ "JOIN categories c ON c.id = ac.category "
            		+ "WHERE c.groupid = "+groupId+" AND ac.assesment NOT IN (SELECT DISTINCT m.assesment "
            				+ "FROM questions q JOIN modules m ON m.id = q.module WHERE testtype = 1)").addScalar("assesment", Hibernate.INTEGER);
            it = q3.list().iterator();
            if(it.hasNext()) {
            	String ids = String.valueOf(it.next());
                while(it.hasNext()) {
                	ids += " ,"+it.next();
                }
                
                String sql4 = "SELECT DISTINCT u.loginname, ua.assesment, ua.enddate, "+AnswerData.CORRECT+" AS type, COUNT(*) AS c " +
              		    "FROM usergroups ug " + 
                		"JOIN users u ON u.loginname = ug.loginname " + 
                		"JOIN userassesments ua ON u.loginname = ua.loginname " + 
                		"JOIN useranswers uas ON uas.loginname = ua.loginname AND uas.assesment = ua.assesment " + 
                		"JOIN assesmentcategories ac ON ua.assesment = ac.assesment " + 
                		"JOIN categories c ON c.id = ac.category " + 
                		"WHERE ug.groupid = " + groupId +
                		" AND c.groupid = c.groupid " + 
                		"AND u.role = '"+SecurityConstants.GROUP_ASSESSMENT+"' " + 
                		"AND enddate IS NOT NULL AND ua.assesment IN ("+ids+")"+
                		" GROUP BY u.loginname, ua.assesment, ua.enddate"; 
                Query q4 = session.createSQLQuery(sql4).addScalar("loginname", Hibernate.STRING).addScalar("assesment", Hibernate.INTEGER).addScalar("enddate", Hibernate.DATE).addScalar("type", Hibernate.INTEGER).addScalar("c", Hibernate.INTEGER);
                it = q4.list().iterator();
                while(it.hasNext()) {
              	  Object[] data = (Object[])it.next();
              	  String user = (String) data[0];
              	  Integer assessment = (Integer) data[1];
              	  Date enddate = (Date) data[2];
              	  Integer type = (Integer) data[3];
              	  Integer count = (Integer) data[4];
          		  Object[] values = {new Integer(1), enddate, new Integer(0), new Integer(0)};
          		  values[type+2] = count;
              	  if(!results.containsKey(user)) {
              		  HashMap<Integer, Object[]> result = new HashMap<Integer, Object[]>();
              		  result.put(assessment, values);
              		  results.put(user, result);
              	  }else {
              		HashMap<Integer, Object[]> result = results.get(user);
              		if(result.containsKey(assessment)) {
              			result.get(assessment)[type+2] = count;
              		}else {
              			result.put(assessment, values);
              		}
              	  }
                }
            }
            


      } catch (Exception e) {
          handler.getException(e,"getUserGroupResults",userSessionData.getFilter().getLoginName());
      }
      return results;
  }

  /**
   * @ejb.interface-method 
   * @ejb.permission role-name = "administrator,systemaccess"
   * @param id
   * @return
   * @throws Exception
   */
  public HashMap<String, HashMap<Integer, Object[]>> getUserCediResults(Integer groupId,Integer cedi,UserSessionData userSessionData) throws Exception {
      Session session = null;
      HashMap<String, HashMap<Integer, Object[]>> results = new HashMap<String, HashMap<Integer,Object[]>>();
      try {
          session = HibernateAccess.currentSession();
          
          String sql1 = "SELECT DISTINCT u.loginname, ua.assesment FROM usergroups ug " + 
          		"JOIN users u ON u.loginname = ug.loginname " + 
          		"JOIN userassesments ua ON u.loginname = ua.loginname " + 
          		"JOIN assesmentcategories ac ON ua.assesment = ac.assesment " + 
          		"JOIN categories c ON c.id = ac.category " + 
          		"WHERE ug.groupid = " + groupId +
          		" AND c.groupid = c.groupid " + 
          		" AND u.location = " + cedi +
          		"AND u.role = '"+SecurityConstants.GROUP_ASSESSMENT+"' " + 
          		"AND enddate IS NULL"; 
          Query q = session.createSQLQuery(sql1).addScalar("loginname", Hibernate.STRING).addScalar("assesment", Hibernate.INTEGER);
          Iterator it = q.list().iterator();
          while(it.hasNext()) {
        	  Object[] data = (Object[])it.next();
        	  String user = (String) data[0];
        	  Integer assessment = (Integer) data[1];
    		  Object[] values = {new Integer(0), null, new Integer(0), new Integer(0)};
        	  if(!results.containsKey(user)) {
        		  HashMap<Integer, Object[]> result = new HashMap<Integer, Object[]>();
        		  result.put(assessment, values);
        		  results.put(user, result);
        	  }else {
        		  results.get(user).put(assessment, values);
        	  }
          }
          
          String sql2 = "SELECT DISTINCT u.loginname, ua.assesment, ua.enddate, a.type, COUNT(*) AS c " +
          		    "FROM usergroups ug " + 
            		"JOIN users u ON u.loginname = ug.loginname " + 
            		"JOIN userassesments ua ON u.loginname = ua.loginname " + 
            		"JOIN useranswers uas ON uas.loginname = ua.loginname AND uas.assesment = ua.assesment " + 
            		"JOIN answerdata ad ON ad.id = uas.answer "+
            		"JOIN questions q ON q.id = ad.question "+
            		"JOIN answers a ON a.id = ad.answer "+
            		"JOIN assesmentcategories ac ON ua.assesment = ac.assesment " + 
            		"JOIN categories c ON c.id = ac.category " + 
            		"WHERE ug.groupid = " + groupId +
            		" AND c.groupid = c.groupid " +
            		" AND u.location = " + cedi + 
            		"AND u.role = '"+SecurityConstants.GROUP_ASSESSMENT+"' " + 
            		"AND enddate IS NOT NULL AND q.testtype = "+QuestionData.TEST_QUESTION +
            		" GROUP BY u.loginname, ua.assesment, ua.enddate, a.type"; 
            Query q2 = session.createSQLQuery(sql2).addScalar("loginname", Hibernate.STRING).addScalar("assesment", Hibernate.INTEGER).addScalar("enddate", Hibernate.DATE).addScalar("type", Hibernate.INTEGER).addScalar("c", Hibernate.INTEGER);
            it = q2.list().iterator();
            while(it.hasNext()) {
          	  Object[] data = (Object[])it.next();
          	  String user = (String) data[0];
          	  Integer assessment = (Integer) data[1];
          	  Date enddate = (Date) data[2];
          	  Integer type = (Integer) data[3];
          	  Integer count = (Integer) data[4];
      		  Object[] values = {new Integer(1), enddate, new Integer(0), new Integer(0)};
      		  values[type+2] = count;
          	  if(!results.containsKey(user)) {
          		  HashMap<Integer, Object[]> result = new HashMap<Integer, Object[]>();
          		  result.put(assessment, values);
          		  results.put(user, result);
          	  }else {
          		HashMap<Integer, Object[]> result = results.get(user);
          		if(result.containsKey(assessment)) {
          			result.get(assessment)[type+2] = count;
          		}else {
          			result.put(assessment, values);
          		}
          	  }
            }
            
            Query q3 = session.createSQLQuery("SELECT DISTINCT ac.assesment FROM assesmentcategories ac "
            		+ "JOIN categories c ON c.id = ac.category "
            		+ "WHERE c.groupid = "+groupId+" AND ac.assesment NOT IN (SELECT DISTINCT m.assesment "
            				+ "FROM questions q JOIN modules m ON m.id = q.module WHERE testtype = 1)").addScalar("assesment", Hibernate.INTEGER);
            it = q3.list().iterator();
            if(it.hasNext()) {
            	String ids = String.valueOf(it.next());
                while(it.hasNext()) {
                	ids += " ,"+it.next();
                }
                
                String sql4 = "SELECT DISTINCT u.loginname, ua.assesment, ua.enddate, "+AnswerData.CORRECT+" AS type, COUNT(*) AS c " +
              		    "FROM usergroups ug " + 
                		"JOIN users u ON u.loginname = ug.loginname " + 
                		"JOIN userassesments ua ON u.loginname = ua.loginname " + 
                		"JOIN useranswers uas ON uas.loginname = ua.loginname AND uas.assesment = ua.assesment " + 
                		"JOIN assesmentcategories ac ON ua.assesment = ac.assesment " + 
                		"JOIN categories c ON c.id = ac.category " + 
                		"WHERE ug.groupid = " + groupId +
                		" AND c.groupid = c.groupid " + 
                		" AND u.location = " + cedi +
                		"AND u.role = '"+SecurityConstants.GROUP_ASSESSMENT+"' " + 
                		"AND enddate IS NOT NULL AND ua.assesment IN ("+ids+")"+
                		" GROUP BY u.loginname, ua.assesment, ua.enddate"; 
                Query q4 = session.createSQLQuery(sql4).addScalar("loginname", Hibernate.STRING).addScalar("assesment", Hibernate.INTEGER).addScalar("enddate", Hibernate.DATE).addScalar("type", Hibernate.INTEGER).addScalar("c", Hibernate.INTEGER);
                it = q4.list().iterator();
                while(it.hasNext()) {
              	  Object[] data = (Object[])it.next();
              	  String user = (String) data[0];
              	  Integer assessment = (Integer) data[1];
              	  Date enddate = (Date) data[2];
              	  Integer type = (Integer) data[3];
              	  Integer count = (Integer) data[4];
          		  Object[] values = {new Integer(1), enddate, new Integer(0), new Integer(0)};
          		  values[type+2] = count;
              	  if(!results.containsKey(user)) {
              		  HashMap<Integer, Object[]> result = new HashMap<Integer, Object[]>();
              		  result.put(assessment, values);
              		  results.put(user, result);
              	  }else {
              		HashMap<Integer, Object[]> result = results.get(user);
              		if(result.containsKey(assessment)) {
              			result.get(assessment)[type+2] = count;
              		}else {
              			result.put(assessment, values);
              		}
              	  }
                }
            }
            


      } catch (Exception e) {
          handler.getException(e,"getUserCediResults",userSessionData.getFilter().getLoginName());
      }
      return results;
  }

  /**
   * @ejb.interface-method 
   * @ejb.permission role-name = "administrator,systemaccess"
   * @param id
   * @return
   * @throws Exception
   */
  public HashMap<String, HashMap<Integer, Object[]>> getUserSpecificQuestion(String questions, String login, UserSessionData userSessionData) throws Exception {
      Session session = null;
      HashMap<String, HashMap<Integer, Object[]>> results = new HashMap<String, HashMap<Integer,Object[]>>();
      try {
          session = HibernateAccess.currentSession();
          String sql = "SELECT ua.loginname, ad.question, ad.text, ad.answer, a.key, a.type "
          		+ "FROM answerdata ad "
          		+ "JOIN useranswers ua ON ua.answer = ad.id "
          		+ "LEFT JOIN answers a ON a.id = ad.answer "
          		+ "WHERE ad.question IN ("+questions+")"; 
          if(login != null) {
        	  sql += " AND ua.loginname = '"+login+"'";
          }
          Query q = session.createSQLQuery(sql)
                		.addScalar("loginname", Hibernate.STRING)
                		.addScalar("question", Hibernate.INTEGER)
                		.addScalar("text", Hibernate.STRING)
                		.addScalar("answer", Hibernate.INTEGER)
                		.addScalar("key", Hibernate.STRING)
                		.addScalar("type", Hibernate.INTEGER);
          Iterator it = q.list().iterator();
          while(it.hasNext()) {
        	  Object[] data = (Object[])it.next();
              String user = (String) data[0];
              Integer question = (Integer) data[1];
              String text = (String) data[2];
              Integer answer = (Integer) data[3];
              String key = (String) data[4];
              Integer type = (Integer) data[5];
              Object[] values = {text, answer, key, type};
              if(!results.containsKey(user)) {
            	  HashMap<Integer, Object[]> result = new HashMap<Integer, Object[]>();
            	  result.put(question, values);
            	  results.put(user, result);
              }else {
            	  results.get(user).put(question, values);
              }
          }
      } catch (Exception e) {
          handler.getException(e,"getUserSpecificQuestion",userSessionData.getFilter().getLoginName());
      }
      return results;
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
		  Session session = session = HibernateAccess.currentSession();
		  UserMultiAssessment userMA = new UserMultiAssessment(user, assessmentId, Calendar.getInstance().getTime());
		  Integer id = (Integer)session.save(userMA);
		  userMA.setId(id);
		  return userMA.getData();
	  } catch (Exception e) {
          handler.getException(e,"getMultiAssessment",userSessionData.getFilter().getLoginName());
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
		  Session session = session = HibernateAccess.currentSession();
		  UserMultiAssessment userMA = (UserMultiAssessment) session.load(UserMultiAssessment.class, userSessionData.getFilter().getMulti());
		  return userMA.getData();
	  } catch (Exception e) {
          handler.getException(e,"getMultiAssessment",userSessionData.getFilter().getLoginName());
      }
      return null;
  }

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,systemaccess"
	 */
	public Collection findGroups(String name, Integer corporation, UserSessionData userSessionData) throws Exception {
		Session session = null;
		Collection result = new LinkedList();
		try {
			session = HibernateAccess.currentSession();

			String queryStr = "SELECT g.id, g.name AS gName, c.name AS cName " + 
					"FROM groups g, corporations c WHERE c.id = g.corporation "; 
			if(name != null && name.trim().length() > 0) {
				queryStr += " AND LOWER(TRIM(g.name)) LIKE '%"+name.trim().toLowerCase()+"%'";
			}
			if(corporation != null) {
				queryStr += " AND c.id = "+corporation;
			}
			queryStr += " ORDER BY g.name";
			Query query = session.createSQLQuery(queryStr).addScalar("id", Hibernate.INTEGER).addScalar("gName", Hibernate.STRING).addScalar("cName", Hibernate.STRING);

			List list = query.list();
			if (list != null && list.size() > 0) {
				Iterator iter = list.iterator();
				while (iter.hasNext()) {
					result.add((Object[]) iter.next());
				}
			}
		} catch (Exception e) {
			handler.getException(e, "findGroups", userSessionData.getFilter().getLoginName());
		}
		return result;
	}
	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,systemaccess"
	 */
	public Collection findWebinars(String wbCode, String wb1,String wb2,String wb3,String wb4, String wb5, UserSessionData userSessionData) throws Exception {
		Session session = null;
		Collection result = new LinkedList();
		Collection<String[]> codes = new LinkedList<String[]>();

		try {
			session = HibernateAccess.currentSession();
			String sql="";
			if (wbCode.equals("")&&wb1.equals("")&&wb2.equals("")&&wb3.equals("")&&wb4.equals("")&&wb5.equals("")) {
				sql = "select distinct u.extradata3, a2.name, a2.id from userassesments ua join users u on u.loginname= ua.loginname join assesments a2 on a2.id=ua.assesment where ua.assesment in (1149, 1270, 1324, 1327, 1471) order by a2.name" ;
			}else {
				int cont=0;
				sql="select distinct u.extradata3, a2.name, a2.id from userassesments ua join users u on u.loginname= ua.loginname join assesments a2 on a2.id=ua.assesment where " ;
				if(!wbCode.equals("")) {
					sql+="u.extradata3 like '%"+wbCode+"%' ";
					cont++;
				}
				String assesments="";
				int j=0;
				if(!wb1.equals("")) {
					assesments+=wb1;
					j++;
				}
				if(!wb2.equals("")) {
					if(j>0) {
						assesments+=", ";
					}
					assesments+=wb2;
					j++;
				}
				if(!wb3.equals("")) {
					if(j>0) {
						assesments+=", ";
					}
					assesments+=wb3;
					j++;
				}
				if(!wb4.equals("")) {
					if(j>0) {
						assesments+=", ";
					}
					assesments+=wb4;
					j++;
				}
				if(!wb5.equals("")) {
					if(j>0) {
						assesments+=", ";
					}
					assesments+=wb5;
					j++;
				}
				if (cont>0 && j>0) {
					sql += "and ua.assesment in ("+assesments+")";
				}else if (cont==0 && j>0) {
					
					sql += " ua.assesment in ("+assesments+")";
				}
				}

			Query q = session.createSQLQuery(sql).addScalar("name", Hibernate.STRING).addScalar("extraData3", Hibernate.STRING).addScalar("id", Hibernate.INTEGER);
			q.setMaxResults(30);
			List list = q.list();
			if (list != null) {
				Iterator it = list.iterator();
				while (it.hasNext()) {
					Object[] data = (Object[]) it.next();
					String codigo=(String)data[1];
			        String sql2="select count(u) from users u where extradata3='"+codigo+"'";
					Query q2 = session.createSQLQuery(sql2);
					List l=q2.list();
		        	String count=String.valueOf(l.get(0));
		        	String [] dat= {(String)data[0],(String)data[1],String.valueOf(data[2]), count};
					codes.add(dat);
				}
			
			}

		} catch (Exception e) {
			handler.getException(e, "findWebinars", userSessionData.getFilter().getLoginName());
		}
		return codes;
	}
	
		/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,systemaccess"
	 */
	public String[] getWebinarAdvance(String wbCode, String assesmentId,String login, UserSessionData userSessionData) throws Exception {
		Session session = null;
		String[] result = new String[5];
		
		try {
			session = HibernateAccess.currentSession();
			String sql="select  ua.enddate, uar.correct, uar.incorrect "+
						"from userassesments ua "+
						"join userassesmentresults uar on uar.login=ua.loginname "+
						"join users u on u.loginname=ua.loginname "+
						"where uar.login='"+login+"' "+
						"and u.extradata3='"+wbCode+"' "+
						" and ua.assesment="+assesmentId;
			
			Query q = session.createSQLQuery(sql);
			List list = q.list();
			String percentString="";
			if (list.size() != 0) {
				Object[] data = (Object[]) list.get(0);
				if(data[0]!=null) {
					result[0]="Completo";
					int total=((Integer)data[1])+((Integer)data[2]); 
					int correct=(Integer)data[1];
					float percent=(((float)correct)/total*100);
					percentString = "" +String.valueOf(percent)+ "%";
					
				}else {
					result[0]="Incompleto";
				}

				result[1]=String.valueOf(data[0]);
				result[2]=String.valueOf(data[1]);
				result[3]=String.valueOf(data[2]);
				result[4]=percentString;

			
			}else {
				result[0]="Pendiente";
				result[1]="Pendiente";
				result[2]="Pendiente";
				result[3]="Pendiente";
			}

		} catch (Exception e) {
			handler.getException(e, "getWebinarAdvance", userSessionData.getFilter().getLoginName());
		}
		return result;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,systemaccess"
	 */
	public Collection getWebinarPersonalData(String login, UserSessionData userSessionData) throws Exception {
		Session session = null;
		String[] result = new String[5];
		
		try {
			session = HibernateAccess.currentSession();
			String sql = "select q.id, q.key,  q.type, ad.question, ad.answer, ad.text, ad.date, uas.fdmregistry " + 
					"from useranswers ua " + 
					"join userassesments uas on uas.assesment = ua.assesment and ua.loginname = uas.loginname " + 
					"join answerdata ad on ad.id = ua.answer " + 
					"join questions q on q.id = ad.question " + 
					"join modules m on m.id = q.module " + 
					"where ua.loginname = '"+login+"' " + 
					"and m.moduleorder = 1 ORDER BY questionorder";
			SQLQuery query = session.createSQLQuery(sql);
			query.addScalar("id", Hibernate.INTEGER);
			query.addScalar("key", Hibernate.STRING);
			query.addScalar("type", Hibernate.INTEGER);
			query.addScalar("question", Hibernate.INTEGER);
			query.addScalar("answer", Hibernate.INTEGER);
			query.addScalar("text", Hibernate.STRING);
			query.addScalar("date", Hibernate.DATE);
			query.addScalar("text", Hibernate.STRING);
			query.addScalar("fdmregistry", Hibernate.INTEGER);

			return query.list();
		} catch (Exception e) {
			handler.getException(e, "getWebinarPersonalData", userSessionData.getFilter().getLoginName());
		}
		return new LinkedList();
	}

	  /**
	   * @ejb.interface-method 
	   * @ejb.permission role-name = "administrator,systemaccess"
	   * @param id
	   * @return
	   * @throws Exception
	   */
	  public HashMap<String, HashMap<Integer, Object[]>> getUserCediResults(Integer[] cediId,UserSessionData userSessionData) throws Exception {
	      Session session = null;
	      HashMap<String, HashMap<Integer, Object[]>> results = new HashMap<String, HashMap<Integer,Object[]>>();
	      try {
	          session = HibernateAccess.currentSession();
	          
	          String sql1 = "SELECT DISTINCT u.loginname, ua.assesment, ua.enddate, uar.correct, uar.incorrect, uar.type "
	          		+ "FROM usergroups ug " + 
	          		"JOIN users u ON u.loginname = ug.loginname " + 
	          		"JOIN userassesments ua ON u.loginname = ua.loginname " + 
	          		"JOIN assesmentcategories ac ON ua.assesment = ac.assesment " + 
	          		"JOIN categories c ON c.id = ac.category " + 
	          		"LEFT JOIN userassesmentresults uar ON uar.assesment = ua.assesment AND uar.login = ua.loginname "+ 
	          		"WHERE ug.groupid = " + GroupData.GRUPO_MODELO +
	          		" AND c.groupid = c.groupid " + 
	          		" AND u.role = '"+SecurityConstants.GROUP_ASSESSMENT+"' " +
	          		" AND ua.assesment != " +AssesmentData.GRUPO_MODELO_FOTO;  
	          if(cediId != null && cediId.length > 0) {
	        	  sql1 += " AND u.location IN ("+cediId[0];
	        	  for(int i = 0; i < cediId.length; i++)
	        		  sql1 += ", "+cediId[i];
	        	  sql1 += ") ";
	          }
	          Query q = session.createSQLQuery(sql1).addScalar("loginname", Hibernate.STRING).addScalar("assesment", Hibernate.INTEGER).addScalar("enddate", Hibernate.DATE)
	        		  .addScalar("correct", Hibernate.INTEGER).addScalar("incorrect", Hibernate.INTEGER).addScalar("type", Hibernate.INTEGER);
	          Iterator it = q.list().iterator();
	          while(it.hasNext()) {
	        	  Object[] data = (Object[])it.next();
	        	  if(((Integer)data[1]).equals(AssesmentData.GRUPO_MODELO_EBTW)) {
		        	  if(data[5] == null || ((Integer)data[5]).intValue() == 1) {
		        		  String user = (String) data[0];
		        		  Integer da = (Integer)data[1];
		        		  Date end = (Date) data[2];
		        		  Integer correct = (Integer) data[3];
		        		  Integer incorrect = (Integer) data[4];
		        		  
		        		  int r = (end == null) ? 0 : 1;
		          		  Object[] values = {new Integer(r), end, correct, incorrect, da};
		            	  if(!results.containsKey(user)) {
		            		  HashMap<Integer, Object[]> result = new HashMap<Integer, Object[]>();
		            		  result.put(2, values);
		            		  results.put(user, result);
		            	  }else {
		            		  results.get(user).put(2, values);
		            	  }
		        	  }
	        	  }else {
		        	  if(data[5] == null || ((Integer)data[5]).intValue() == 1) {
		        		  String user = (String) data[0];
		        		  Integer da = (Integer)data[1];
		        		  Date end = (Date) data[2];
		        		  Integer correct = (Integer) data[3];
		        		  Integer incorrect = (Integer) data[4];
		        		  
		        		  int r = (end == null) ? 0 : 1;
		          		  Object[] values = {new Integer(r), end, correct, incorrect, da};
		            	  if(!results.containsKey(user)) {
		            		  HashMap<Integer, Object[]> result = new HashMap<Integer, Object[]>();
		            		  result.put(1, values);
		            		  results.put(user, result);
		            	  }else {
		            		  results.get(user).put(1, values);
		            	  }
		        	  }
	        	  }	        	  
	          }
        	  String sql2 = "SELECT uas.loginname, q.questionorder, q.type, ad.text, ad.date, a.key " + 
        	  		"FROM useranswers uas " + 
        	  		"JOIN answerdata ad ON ad.id = uas.answer " + 
        	  		"JOIN users u ON u.loginname = uas.loginname " +
        	  		"JOIN questions q ON q.id = ad.question " + 
        	  		"LEFT JOIN answers a ON a.id = ad.answer " + 
        	  		"WHERE uas.loginname IN (SELECT loginname FROM usergroups WHERE groupid = "+GroupData.GRUPO_MODELO+") " + 
        	  		"AND uas.assesment != "+AssesmentData.GRUPO_MODELO_EBTW;
	          if(cediId != null && cediId.length > 0) {
	        	  sql2 += " AND u.location IN ("+cediId[0];
	        	  for(int i = 0; i < cediId.length; i++)
	        		  sql2 += ", "+cediId[i];
	        	  sql2 += ") ";
	          }

	          sql2 += " AND q.testtype = 0 ORDER BY uas.loginname";
        	  Query q2 = session.createSQLQuery(sql2).addScalar("loginname", Hibernate.STRING).addScalar("questionorder", Hibernate.INTEGER)
        			  .addScalar("type", Hibernate.INTEGER).addScalar("text", Hibernate.STRING).addScalar("date", Hibernate.DATE).addScalar("key", Hibernate.STRING);
        	  Iterator it2 = q2.list().iterator();
        	  String lastLogin = "";
        	  Object[] values = new Object[5];
        	  while(it2.hasNext()) {
	        	  Object[] data2 = (Object[])it2.next();
        		  String user = (String) data2[0];
        		  if(!user.equals(lastLogin) && lastLogin.length() != 0) {
        			  if(!results.containsKey(lastLogin)) {
	            		  HashMap<Integer, Object[]> result = new HashMap<Integer, Object[]>();
	            		  result.put(0, values);
	            		  results.put(lastLogin, result);
	            	  }else {
	            		  results.get(lastLogin).put(0, values);
	            	  }
        			  values = new Object[5];
        		  }
        		  lastLogin = user;
        		  Integer questionorder = (Integer) data2[1];
        		  Integer type = (Integer) data2[2];
        		  String text = (String) data2[3];
        		  Date date = (Date) data2[4];
        		  String key = (String) data2[5];
        		  switch(type) {
        		  		case 1:
        		  			values[0] = text;
        		  			break;
        		  		case 2:
        		  			values[questionorder] = date;
        		  			break;
        		  		case 3:
        		  			values[questionorder] = key;
        		  			break;
        		  } 
	          }
    		  if(lastLogin.length() != 0) {
    			  if(!results.containsKey(lastLogin)) {
            		  HashMap<Integer, Object[]> result = new HashMap<Integer, Object[]>();
            		  result.put(0, values);
            		  results.put(lastLogin, result);
            	  }else {
            		  results.get(lastLogin).put(0, values);
            	  }
    			  values = new Object[5];
    		  }

	      } catch (Exception e) {
	          handler.getException(e,"getNewUserGroupResults",userSessionData.getFilter().getLoginName());
	      }
	      return results;
	  }


	  /**
	   * @ejb.interface-method 
	   * @ejb.permission role-name = "administrator,systemaccess"
	   * @param id
	   * @return
	   * @throws Exception
	   *
	  public HashMap<String, HashMap<Integer, Object[]>> getUserCediResults(Integer[] cedis, UserSessionData userSessionData) throws Exception {
	      Session session = null;
	      HashMap<String, HashMap<Integer, Object[]>> results = new HashMap<String, HashMap<Integer,Object[]>>();
	      try {
	          session = HibernateAccess.currentSession();
	          
	          String sql1 = "SELECT DISTINCT u.loginname, ua.assesment, ua.enddate, uar.correct, uar.incorrect, uar.type "
	          		+ "FROM usergroups ug " + 
	          		"JOIN users u ON u.loginname = ug.loginname " + 
	          		"JOIN userassesments ua ON u.loginname = ua.loginname " + 
	          		"JOIN assesmentcategories ac ON ua.assesment = ac.assesment " + 
	          		"JOIN categories c ON c.id = ac.category " + 
	          		"LEFT JOIN userassesmentresults uar ON uar.assesment = ua.assesment AND uar.login = ua.loginname "+ 
	          		"WHERE ug.groupid = " + GroupData.GRUPO_MODELO +
	          		" AND c.groupid = c.groupid " + 
	          		" AND u.role = '"+SecurityConstants.GROUP_ASSESSMENT+"' " +
	          		" AND ua.assesment != " +AssesmentData.GRUPO_MODELO_FOTO;  
	          Query q = session.createSQLQuery(sql1).addScalar("loginname", Hibernate.STRING).addScalar("assesment", Hibernate.INTEGER).addScalar("enddate", Hibernate.DATE)
	        		  .addScalar("correct", Hibernate.INTEGER).addScalar("incorrect", Hibernate.INTEGER).addScalar("type", Hibernate.INTEGER);
	          Iterator it = q.list().iterator();
	          while(it.hasNext()) {
	        	  Object[] data = (Object[])it.next();
	        	  if(data[5] == null || ((Integer)data[5]).intValue() == 1) {
	        		  String user = (String) data[0];
	        		  Date end = (Date) data[2];
	        		  Integer correct = (Integer) data[3];
	        		  Integer incorrect = (Integer) data[4];
	        		  
	        		  int r = (end == null) ? 0 : 1;
	          		  Object[] values = {new Integer(r), end, correct, incorrect};
	            	  if(!results.containsKey(user)) {
	            		  HashMap<Integer, Object[]> result = new HashMap<Integer, Object[]>();
	            		  result.put(1, values);
	            		  results.put(user, result);
	            	  }else {
	            		  results.get(user).put(1, values);
	            	  }
	        	  }
	          }
          
        	  String sql2 = "SELECT uas.loginname, q.questionorder, q.type, ad.text, ad.date, a.key " + 
        	  		"FROM useranswers uas " + 
        	  		"JOIN answerdata ad ON ad.id = uas.answer " + 
        	  		"JOIN questions q ON q.id = ad.question " + 
        	  		"LEFT JOIN answers a ON a.id = ad.answer " + 
        	  		"WHERE uas.loginname IN (SELECT loginname FROM usergroups WHERE groupid = "+GroupData.GRUPO_MODELO+") " + 
        	  		"AND q.testtype = 0 ORDER BY uas.loginname";
        	  Query q2 = session.createSQLQuery(sql2).addScalar("loginname", Hibernate.STRING).addScalar("questionorder", Hibernate.INTEGER)
        			  .addScalar("type", Hibernate.INTEGER).addScalar("text", Hibernate.STRING).addScalar("date", Hibernate.DATE).addScalar("key", Hibernate.STRING);
        	  Iterator it2 = q2.list().iterator();
        	  String lastLogin = "";
        	  Object[] values = new Object[5];
        	  while(it2.hasNext()) {
	        	  Object[] data2 = (Object[])it2.next();
        		  String user = (String) data2[0];
        		  if(!user.equals(lastLogin) && lastLogin.length() != 0) {
        			  if(!results.containsKey(lastLogin)) {
	            		  HashMap<Integer, Object[]> result = new HashMap<Integer, Object[]>();
	            		  result.put(0, values);
	            		  results.put(lastLogin, result);
	            	  }else {
	            		  results.get(lastLogin).put(0, values);
	            	  }
        			  values = new Object[5];
        		  }
        		  lastLogin = user;
        		  Integer questionorder = (Integer) data2[1];
        		  Integer type = (Integer) data2[2];
        		  String text = (String) data2[3];
        		  Date date = (Date) data2[4];
        		  String key = (String) data2[5];
        		  switch(type) {
        		  		case 1:
        		  			values[0] = text;
        		  			break;
        		  		case 2:
        		  			values[questionorder] = date;
        		  			break;
        		  		case 3:
        		  			values[questionorder] = key;
        		  			break;
        		  } 
	          }
    		  if(lastLogin.length() != 0) {
    			  if(!results.containsKey(lastLogin)) {
            		  HashMap<Integer, Object[]> result = new HashMap<Integer, Object[]>();
            		  result.put(0, values);
            		  results.put(lastLogin, result);
            	  }else {
            		  results.get(lastLogin).put(0, values);
            	  }
    			  values = new Object[5];
    		  }

	      } catch (Exception e) {
	          handler.getException(e,"getNewUserGroupResults",userSessionData.getFilter().getLoginName());
	      }
	      return results;
	  }*/

	  /**
	   * @ejb.interface-method 
	   * @ejb.permission role-name = "administrator,systemaccess"
	   * @param id
	   * @return
	   * @throws Exception
	   */
	  public Integer getAssesmentId(String user, String webinarCode, UserSessionData userSessionData) throws Exception {
	      Session session = null;
	      Integer assesmentId=null;
	      try {
	          session = HibernateAccess.currentSession();
	          String sql = "select assesment from users u join userassesments ua on u.loginname=ua.loginname where u.extradata3='"+webinarCode+"' and u.loginname='"+user+"'";  
	          Query query = session.createSQLQuery(sql).addScalar("assesment",Hibernate.INTEGER);

	          Iterator it = query.list().iterator();
        	  while(it.hasNext()) {
	        	  Integer d = (Integer)it.next();
	        	  assesmentId=d;}
	      }
	      catch (Exception e){
	          handler.getException(e,"getAssesmentId",userSessionData.getFilter().getLoginName());
	      	}
	  return assesmentId;    
	  }
	  
		/**
		 * @ejb.interface-method
		 * @ejb.permission role-name = "administrator,systemaccess, clientreporter"
		 */
		public Collection findMutualAssesmentResults(Integer assesment, Integer cedi, String from, String to, UserSessionData userSessionData) throws Exception {
			Session session = null;
			String date= "";
			 System.out.println("***");
			 System.out.println(from);
			 System.out.println(to);

			 date=(from!=null&&!from.equals("--"))?(" AND enddate >= '"+from+"' "): "";
			 date+=(to!=null&&!to.equals("--"))?" AND enddate <= '"+to+"' ":"";
			 Collection result = new LinkedList();
			try {
				session = HibernateAccess.currentSession();
				Query qryMod = session.createSQLQuery("select id from modules where assesment="+assesment+" order by moduleorder").addScalar("id", Hibernate.INTEGER);
				List listModules = qryMod.list();
				int size=0;
				if (listModules != null && listModules.size() > 0) {
					size=listModules.size();
				}
				String[] modules=new String[size];
				if (listModules != null && listModules.size() > 0) {				
					Iterator iter = listModules.iterator();
					int pos=0;
					while (iter.hasNext()) {
						Integer data=(Integer) iter.next();
						modules[pos]=String.valueOf(data);
						pos++;
					}
				}
				Integer lengthRes=(size*2)+8;
			
				String queryStr="";
				if(cedi!=null ||assesment==AssesmentData.ABBOTT_NEWDRIVERS ||assesment==AssesmentData.ABBEVIE_LATAM||assesment==AssesmentData.SUMITOMO) {
					queryStr = "SELECT u.firstname, u.lastname, u.email, ua.loginname,u.location, (psiresult1+psiresult2+psiresult3+psiresult4+psiresult5+psiresult6)/6 as behaviour, u.country, ua.enddate "+
							"FROM userassesments ua "
							+ "JOIN users u ON u.loginname=ua.loginname "
							+ "WHERE assesment = " + assesment+ date;

						//	+ " AND ua.enddate IS NOT NULL "
						//	+ "AND psiresult1+psiresult2+psiresult3+psiresult4+psiresult5+psiresult6 IS NOT NULL "
					if(cedi!=null) queryStr+= " AND u.location="+cedi; 
				}else if(assesment==AssesmentData.MUTUAL_DA ) {
					
					queryStr = "SELECT u.firstname, u.lastname, u.email, ua.loginname,u.location, (psiresult1+psiresult2+psiresult3+psiresult4+psiresult5+psiresult6)/6 as behaviour, u.country, ua.enddate  "+
							"FROM userassesments ua "
							+ "JOIN users u ON u.loginname=ua.loginname "
							+ "WHERE assesment = " + assesment
							+ date;
							//+ " AND ua.enddate IS NOT NULL "
							//+ "AND psiresult1+psiresult2+psiresult3+psiresult4+psiresult5+psiresult6 IS NOT NULL"; 
				}
				Query query = session.createSQLQuery(queryStr).addScalar("firstname", Hibernate.STRING).addScalar("lastname", Hibernate.STRING).addScalar("email", Hibernate.STRING).addScalar("loginname", Hibernate.STRING).addScalar("location", Hibernate.STRING).addScalar("behaviour", Hibernate.STRING).addScalar("country", Hibernate.STRING).addScalar("enddate", Hibernate.STRING);
				
				List list = query.list();
				if (list != null && list.size() > 0) {
					Iterator iter = list.iterator();
					while (iter.hasNext()) {
						int pos=7;
						String[] ret=new String[lengthRes];
						Object[] data=(Object[]) iter.next();
						String loginName=(String)data[3];
						if(loginName!=null) {
							boolean noResult=true;
							for(int i=0; i<data.length; i++) {
								ret[i]=(String)data[i];
							}
							for(int i=0; i<modules.length; i++) {
								String q = "SELECT uar.correct, uar.incorrect from userassesmentresults uar WHERE uar.login='"+loginName+"' AND uar.type="+modules[i]+" AND uar.assesment="+assesment;
								query = session.createSQLQuery(q).addScalar("correct", Hibernate.INTEGER).addScalar("incorrect", Hibernate.INTEGER);
								query.setMaxResults(1);
								if(query.list()!=null && query.list().size() > 0) {
									Object[] results=(Object[]) query.list().get(0);
									ret[pos+1]=String.valueOf(results[0]);
									ret[pos+2]=String.valueOf(results[1]);
									pos=pos+2;
									noResult=false;
								
									
								}else {
									ret[pos+1]=null;
									ret[pos+2]=null;
									pos=pos+2;
									noResult=false;
								}
							}
							if(!noResult&&(assesment==AssesmentData.MUTUAL_DA||assesment==AssesmentData.SUMITOMO)) {
								result.add(new UserMutualReportData(ret));	
							}else if(!noResult&&assesment==AssesmentData.ABBOTT_NEWDRIVERS) {
								result.add(new UserMutualReportData(ret, true));	
							}
							else if(!noResult&&assesment==AssesmentData.ABBEVIE_LATAM) {
								result.add(new UserMutualReportData(ret, true, true));	
							}
						}
					}
				}
			} catch (Exception e) {
				handler.getException(e, "findMutualAssesmentResults", userSessionData.getFilter().getLoginName());
			}
			return result;
		}

		 /**
		   * @ejb.interface-method 
		   * @ejb.permission role-name = "administrator,systemaccess"
		   * @param id
		   * @return
		   * @throws Exception
		   */
		  public GroupUsersData getGroupUsersResults(Integer groupId,UserSessionData userSessionData) throws Exception {
		      Session session = null;
		      GroupUsersData results = null;
		      ArrayList<ArrayList<String>> auxiliar= new ArrayList<ArrayList<String>>();
		      
		      try {
		          session = HibernateAccess.currentSession();
		          
		          String sql1 = "SELECT DISTINCT u.country FROM users u "+
		        		  		"JOIN usergroups g ON g.loginname=u.loginname "+
		        		  		"WHERE g.groupid="+groupId;
		          		 
		          Query q = session.createSQLQuery(sql1).addScalar("country", Hibernate.INTEGER);
		          Iterator it = q.list().iterator();
		          ArrayList<Integer> countries=new ArrayList<Integer>();
		          while(it.hasNext()) {
		        	  Integer c = (Integer)it.next();
		        	  if(!countries.contains(c)) {
		        		  countries.add(c);
		        	  }
		          }
		          
		          String sql2 = "SELECT DISTINCT u.extradata2 FROM users u "+
	        		  		"JOIN usergroups g ON g.loginname=u.loginname "+
	        		  		"WHERE g.groupid="+groupId+
	        		  		" ORDER BY u.extradata2";
		            Query q2 = session.createSQLQuery(sql2).addScalar("extradata2", Hibernate.STRING);
		            it = q2.list().iterator();
			          ArrayList<String> divisions=new ArrayList<String>();
			          while(it.hasNext()) {
			        	  String d = (String)it.next();
			        	  if(!divisions.contains(d)) {
			        		  divisions.add(d);
			        	  }
			          }
			        String sql3 = "select a.id from categories  c join assesmentcategories ac on c.id=ac.category join assesments a on a.id= ac.assesment where groupid="+groupId;
		            Query q3 = session.createSQLQuery(sql3).addScalar("id", Hibernate.INTEGER);
		            it = q3.list().iterator();
			         ArrayList<AssesmentAttributes> assesments=new ArrayList<AssesmentAttributes>();

			         while(it.hasNext()) {
			        	Integer id= (Integer)it.next();
			            Assesment assesment = (Assesment)session.load(Assesment.class,id);
			            assesments.add(assesment.getAssesment());
			          }
		          
		        //select a.id from categories  c join assesmentcategories ac on c.id=ac.category join assesments a on a.id= ac.assesment where groupid=123
		            results=new GroupUsersData(groupId,countries, divisions, getUserGroupResults(groupId,userSessionData), assesments);

		      } catch (Exception e) {
		          handler.getException(e,"getUserGroupResults",userSessionData.getFilter().getLoginName());
		      }
		      return results;
		  }
		    /**
		     * @ejb.interface-method 
		     * @ejb.permission role-name = "administrator,systemaccess,clientreporter,cepareporter"
		     */
		    public ListResult findAssesmentsbkp(String name,String corporation,String archived,UserSessionData userSessionData) throws Exception {
		        Session session = null;
		        try {
		 
		            session = HibernateAccess.currentSession();
		            String queryCountStr = "SELECT COUNT(*) AS count FROM assesmentsbkp AS a JOIN generalmessagesbkp gm ON gm.labelkey = a.name WHERE gm.language = '"+userSessionData.getLenguage()+"' AND lower(gm.text) like lower(:name) ";
		            if(!PersistenceUtil.empty(corporation)) {
		            	queryCountStr += "AND a.corporation = "+corporation;
		            }
		            switch(Integer.parseInt(archived)) {
			            case 0:
			            	queryCountStr += " AND NOT a.archived"; 
			            	break;
			            case 1:
			            	queryCountStr += " AND a.archived"; 
			        }
		            
		            Query queryCount = session.createSQLQuery(queryCountStr).addScalar("count",Hibernate.INTEGER);
		            queryCount.setParameter("name", "%"+name+"%");
		            Integer total = (Integer)queryCount.uniqueResult();
		            if(total == null) {
		                total = 0;
		            }

		            String queryStr = "SELECT a.id,a.name as assesment,c.name as corporation,a.astart,a.aend,count(question.id)as count FROM assesmentsbkp AS a " +
		                    "JOIN corporations c ON a.corporation = c.id " +
		                    "JOIN generalmessagesbkp gm ON gm.labelkey = a.name " +
		                    "LEFT JOIN modulesbkp m ON m.assesment = a.id " +
		                    "LEFT JOIN questionsbkp question on question.module = m.id "+
		                    "WHERE gm.language = '"+userSessionData.getLenguage()+"' AND lower(gm.text) like lower(:name) ";
		            if(!PersistenceUtil.empty(corporation)) {
		                queryStr += "AND a.corporation = "+corporation;
		            }
		            switch(Integer.parseInt(archived)) {
			            case 0:
			            	queryStr += " AND NOT a.archived"; 
			            	break;
			            case 1:
			            	queryStr += " AND a.archived"; 
		            }
		            queryStr += " GROUP BY a.id,a.name,a.astart,a.aend,c.name ORDER BY a.name";
		            Query query = session.createSQLQuery(queryStr).addScalar("id",Hibernate.INTEGER).addScalar("assesment",Hibernate.STRING).addScalar("corporation",Hibernate.STRING).addScalar("aend",Hibernate.DATE).addScalar("astart",Hibernate.DATE).addScalar("count",Hibernate.INTEGER);
		            query.setParameter("name", "%"+name+"%");
		            
		            Iterator iter = query.list().iterator();
		            Collection<Object[]> list = new LinkedList<Object[]>();
		            while (iter.hasNext()) {
		                list.add((Object[])iter.next());
		            }
		            
		            return new ListResult(list,total);
		        }catch (Exception e) {
		            handler.getException(e,"findAssesmentsbkp",userSessionData.getFilter().getLoginName());
		        }
		        return null;
		    }
		    
		    
	    /**
	     * @ejb.interface-method 
	     * @ejb.permission role-name = "administrator,systemaccess,basf_assessment,clientreporter,cepareporter"
	     * @param name
	     * @param userRequest
	     * @return
	     * @throws Exception
	     */
	    public AssesmentData findAssesmentbkp(Integer id, UserSessionData userSessionData) throws Exception {
	        if (id == null) {
	            throw new InvalidDataException("findAssesment","id = null");
	        }
	        if (userSessionData == null) {
	            throw new DeslogedException("findAssesment","session = null");
	        }

	        Session session = null;
	        try {
	            session = HibernateAccess.currentSession();
	            AssesmentBKP assesment = (AssesmentBKP)session.load(AssesmentBKP.class,id);
	            return assesment.getData();
	        } catch (Exception e) {
	            handler.getException(e,"findAssesment",userSessionData.getFilter().getLoginName());
	        }
	        return null;
	    }	
	    
	    /**
	     * @ejb.interface-method 
	     * @ejb.permission role-name = "administrator,systemaccess,accesscode,clientreporter"
	     * @param name
	     * @param userRequest
	     * @return
	     * @throws Exception
	     */
	    public AssesmentAttributes findAssesmentAttributesbkp(Integer id, UserSessionData userSessionData) throws Exception {
	        if (id == null) {
	            throw new InvalidDataException("findAssesmentAttributesbkp","id = null");
	        }
	        if (userSessionData == null) {
	            throw new DeslogedException("findAssesmentAttributesbkp","session = null");
	        }

	        Session session = null;
	        try {
	            session = HibernateAccess.currentSession();
	            AssesmentBKP assesment = (AssesmentBKP)session.load(AssesmentBKP.class,id);
	            return assesment.getAssesment();
	        } catch (Exception e) {
	            handler.getException(e,"findAssesmentAttributesbkp",userSessionData.getFilter().getLoginName());
	        }
	        return null;
	    }
	    
	    /**
	     * @ejb.interface-method 
	     * @ejb.permission role-name = "administrator,systemaccess,pepsico_candidatos,clientreporter,cepareporter"
	     */
	    public Integer getAssesmentQuestionCountbkp(AssesmentAttributes assesment,UserSessionData userSessionData, boolean all) throws Exception {
	        Session session = null;
	        try {
	            session = HibernateAccess.currentSession();
	            String queryStr = "SELECT COUNT(*) AS count FROM questionsbkp q JOIN modulesbkp AS m ON m.id = q.module WHERE m.assesment = "+String.valueOf(assesment.getId());
	            if(!all) {
	                queryStr += " AND q.testtype = "+String.valueOf(QuestionData.TEST_QUESTION);
	            }
	            Query query = session.createSQLQuery(queryStr).addScalar("count",Hibernate.INTEGER);
	            int value = ((Integer)query.uniqueResult()).intValue();
	            if(assesment.isPsitest() && all) {
	                value += 48;
	            }
	            return new Integer(value);
	        }catch (Exception e) {
	            handler.getException(e,"getAssesmentQuestionCountbkp",userSessionData.getFilter().getLoginName());
	        }
	        return null;
	    }
	    
	    /**
	     * @ejb.interface-method
	     * @ejb.permission role-name = "administrator"
	     */
	    public Collection<AssessmentUserData> getAssessmentUsersBKP (Integer assessment, UserSessionData userSessionData) throws Exception {
	    	LinkedList<AssessmentUserData> list = new LinkedList<AssessmentUserData>();
	    	try {
	            Session session = HibernateAccess.currentSession();
	            SQLQuery query = session.createSQLQuery("SELECT firstname, lastname, u.loginname, u.email, u.extraData3, enddate IS NOT NULL AS finished, psitestid IS NOT NULL AS psi, sr.id, sr.sended, sr.certificate, ua.assesment, uar.correct, uar.incorrect " +
	            		"FROM userassesmentsbkp ua " +
	            		"LEFT JOIN sendedreports sr ON sr.login = ua.loginname AND sr.assessment = ua.assesment " +
	            		"LEFT JOIN userassesmentresults uar on uar.login = ua.loginname AND uar.assesment = ua.assesment " + 
	            		"JOIN users u ON u.loginname = ua.loginname " +
	            		"WHERE ua.assesment = "+assessment);
	            query.addScalar("firstname", Hibernate.STRING);
	            query.addScalar("lastname", Hibernate.STRING);
	            query.addScalar("loginname", Hibernate.STRING);
	            query.addScalar("email", Hibernate.STRING);
	            query.addScalar("extraData3", Hibernate.STRING);
	            query.addScalar("finished", Hibernate.BOOLEAN);
	            query.addScalar("psi", Hibernate.BOOLEAN);
	            query.addScalar("id", Hibernate.INTEGER);
	            query.addScalar("sended", Hibernate.BOOLEAN);
	            query.addScalar("certificate", Hibernate.BOOLEAN);
	            query.addScalar("assesment", Hibernate.INTEGER);
	            query.addScalar("correct", Hibernate.INTEGER);
	            query.addScalar("incorrect", Hibernate.INTEGER);
	            
	            HashMap<String, AssessmentUserData> values = new HashMap<String, AssessmentUserData>();
	            Iterator it = query.list().iterator();
	            while(it.hasNext()) {
	            	Object[] data = (Object[])it.next();
	            	values.put((String)data[2], new AssessmentUserData(data));
	            }
	            
	            query = session.createSQLQuery("SELECT loginname, COUNT(*) AS c FROM useranswersbkp WHERE assesment = "+assessment+" GROUP BY loginname").addScalar("loginname", Hibernate.STRING).addScalar("c", Hibernate.INTEGER);
	            it = query.list().iterator();
	            while(it.hasNext()) {
	            	Object[] data = (Object[])it.next();
	            	if(values.containsKey(data[0])) {
	            		values.get(data[0]).setAnswers((Integer) data[1]);
	            	}
	            }
	            
	            query = session.createSQLQuery("SELECT loginname, count(*) AS c FROM useranswersbkp ua " +
	            		"JOIN answerdatabkp ad ON ad.id = ua.answer " +
	            		"JOIN questionsbkp q ON ad.question = q.id " +
	            		"JOIN answersbkp a ON a.id = ad.answer " +
	            		"WHERE assesment = "+assessment+" AND q.testtype = 1 AND a.type = 0 " +
	            		"GROUP BY loginname").addScalar("loginname", Hibernate.STRING).addScalar("c", Hibernate.INTEGER);
	            it = query.list().iterator();
	            while(it.hasNext()) {
	            	Object[] data = (Object[])it.next();
	            	if(values.containsKey(data[0])) {
	            		values.get(data[0]).setCorrect((Integer) data[1]);
	            	}
	            }

	            list.addAll(values.values());
	        } catch (Exception e) {
	            handler.getException(e,"getAssessmentUsersBKP", userSessionData.getFilter().getLoginName());
	        }
	    	return list;
	    }

}
