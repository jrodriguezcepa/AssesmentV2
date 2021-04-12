/*
 * Created on 30-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.persistence.question;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;

import assesment.communication.administration.user.UserSessionData;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.exception.DeslogedException;
import assesment.communication.exception.InvalidDataException;
import assesment.communication.language.Text;
import assesment.communication.question.AnswerData;
import assesment.communication.question.QuestionData;
import assesment.communication.question.VideoData;
import assesment.communication.util.CountryConstants;
import assesment.persistence.administration.tables.AssessmentUserData;
import assesment.persistence.hibernate.HibernateAccess;
import assesment.persistence.question.tables.GenericQuestion;
import assesment.persistence.question.tables.PsiQuestion;
import assesment.persistence.question.tables.Question;
import assesment.persistence.question.tables.QuestionBKP;
import assesment.persistence.question.tables.Video;
import assesment.persistence.util.ExceptionHandler;
import assesment.persistence.util.Util;

/**
 * @ejb.bean name="QuestionReport"
 *           display-name="Name for QuestionReport"
 *           description="Description for QuestionReport"
 *           jndi-name="ejb/QuestionReport"
 *           type="Stateless"
 *           view-type="both"
 */
public abstract class QuestionReportBean implements SessionBean {

    private ExceptionHandler handler = new ExceptionHandler(QuestionReportBean.class);

    /**
     * @ejb.create-method
     * @ejb.permission role-name = "systemaccess,administrator,clientreporter,cepareporter"
     * @throws CreateException
     */
    public void ejbCreate() throws CreateException{
    }
    
    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public QuestionData findQuestion(Integer id, UserSessionData userSessionData,int type) throws Exception {
        if (id == null) {
            throw new InvalidDataException("findQuestion","id = null");
        }
        if (userSessionData == null) {
            throw new DeslogedException("findQuestion","session = null");
        }

        Session session = null;
        try {
            session = HibernateAccess.currentSession();
            if(type == QuestionData.NORMAL) {
                Question question = (Question)session.load(Question.class,id);
                return question.getData();
            }else {
                GenericQuestion question = (GenericQuestion)session.load(GenericQuestion.class,id);
                return question.getData();
            }
        } catch (Exception e) {
            handler.getException(e,"findQuestion",userSessionData.getFilter().getLoginName());
        }
        return null;
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public Collection<QuestionData> getPsicoQuestions(UserSessionData userSessionData) throws Exception {
        Collection<QuestionData> list = new LinkedList<QuestionData>();
        if (userSessionData == null) {
            throw new DeslogedException("findQuestion","session = null");
        }

        Session session = null;
        try {
            session = HibernateAccess.currentSession();
            Query q = session.createQuery("SELECT q FROM PsiQuestion q");
            Iterator it = q.list().iterator();
            while(it.hasNext()) {
                list.add(((PsiQuestion)it.next()).getData());
            }
        } catch (Exception e) {
            handler.getException(e,"findQuestion",userSessionData.getFilter().getLoginName());
        }
        return list;
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public Collection findQuestionResults(Integer question, int type, UserSessionData userSessionData) throws Exception {
        if (userSessionData == null) {
            throw new DeslogedException("findQuestionResults","session = null");
        }
        if (question == null) {
            throw new DeslogedException("findQuestionResults","question = null");
        }
        Session session = null;
        try {
            Query q = null;
            session = HibernateAccess.currentSession();
            if(type == QuestionData.INCLUDED_OPTIONS) {
                q = session.createSQLQuery("SELECT mo.answer,COUNT(*) as count " +
                        "FROM multioptions AS mo JOIN answerdata AS ad ON ad.id = mo.id " +
                        "WHERE ad.question = "+String.valueOf(question)+
                        " GROUP BY mo.answer").addScalar("answer",Hibernate.INTEGER).addScalar("count",Hibernate.INTEGER);
            }else {
                q = session.createSQLQuery("SELECT a.id,COUNT(*) as count FROM questions AS q " +
                    "JOIN answers AS a ON q.id = a.question " +
                    "JOIN answerdata AS ad ON a.id = ad.answer " +
                    "WHERE q.id = "+String.valueOf(question)+
                    " GROUP BY a.id").addScalar("id",Hibernate.INTEGER).addScalar("count",Hibernate.INTEGER);
            }
            return q.list();
        } catch (Exception e) {
            handler.getException(e,"findQuestionResults",userSessionData.getFilter().getLoginName());
        }
        return null;
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator"
     */
    public Collection findQuestionReportByModule(Integer module, UserSessionData userSessionData) throws Exception {
        if (userSessionData == null) {
            throw new DeslogedException("findQuestionByModule","session = null");
        }
        if (module == null) {
            throw new DeslogedException("findQuestionByModule","module = null");
        }
        Session session = null;
        try {
            Query q = null;
            session = HibernateAccess.currentSession();
            q = session.createSQLQuery("SELECT q.key, a.type, COUNT(*) AS count FROM answerdata ad " +
                    "JOIN questions AS q ON ad.question = q.id JOIN answers AS a ON ad.answer = a.id " +
                    "WHERE q.module = " + String.valueOf(module) + " AND q.testtype = " + String.valueOf(QuestionData.TEST_QUESTION) +
                    " GROUP BY q.key, a.type ORDER BY q.key, a.type").addScalar("key",Hibernate.STRING).addScalar("type",Hibernate.INTEGER).addScalar("count",Hibernate.INTEGER);
            return q.list();
        } catch (Exception e) {
            handler.getException(e,"findQuestionByModule",userSessionData.getFilter().getLoginName());
        }
        return null;
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator"
     */
    public Collection findIrelevantQuestionReportByModule(Integer module, UserSessionData userSessionData) throws Exception {
        if (userSessionData == null) {
            throw new DeslogedException("findIrelevantQuestionByModule","session = null");
        }
        if (module == null) {
            throw new DeslogedException("findIrelevantQuestionByModule","module = null");
        }
        Session session = null;
        try {
            Query q = null;
            session = HibernateAccess.currentSession();
            q = session.createSQLQuery("SELECT question,answer, COUNT(*) AS count " +
                    "FROM answerdata ad JOIN questions AS q ON ad.question = q.id " +
                    "WHERE q.module = "+String.valueOf(module)+" " +
                    "AND q.testtype = "+String.valueOf(QuestionData.NOT_TEST_QUESTION)+" "+
                    "GROUP BY question,answer ORDER BY question,answer").addScalar("question",Hibernate.STRING).addScalar("answer",Hibernate.STRING).addScalar("count",Hibernate.INTEGER);
            List l = q.list();
            
            q = session.createSQLQuery("SELECT ad.question,mo.answer, COUNT(*) AS count " +
                    "FROM answerdata ad " +
                    "JOIN questions AS q ON ad.question = q.id " +
                    "JOIN multioptions AS mo ON mo.id = ad.id " +
                    "WHERE q.module = "+String.valueOf(module)+" " +
                    "AND q.testtype = "+String.valueOf(QuestionData.NOT_TEST_QUESTION)+" " +
                    "GROUP BY question,mo.answer ORDER BY question,mo.answer").addScalar("question",Hibernate.STRING).addScalar("answer",Hibernate.STRING).addScalar("count",Hibernate.INTEGER);
            l.addAll(q.list());
            return l;
            
        } catch (Exception e) {
            handler.getException(e,"findQuestionByModule",userSessionData.getFilter().getLoginName());
        }
        return null;
    }
    
    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator"
     */
    public Integer findAssesmentId(Integer question, UserSessionData userSessionData) throws Exception{
        Session session = null;
        try {
            Query q = null;
            session = HibernateAccess.currentSession();
            q = session.createSQLQuery("select m.assesment from questions q join modules m on q.module = m.id where q.id = " + String.valueOf(question)).addScalar("assesment",Hibernate.INTEGER);
            Iterator it = q.list().iterator();
            if(it.hasNext()) {
                return (Integer)it.next();
            }
        } catch (Exception e) {
            handler.getException(e,"findQuestionByModule",userSessionData.getFilter().getLoginName());
        }
        return null;
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public String[] getAgenSex(String user, Integer assesment,UserSessionData userSessionData) throws Exception {
        Session session = null;
        String[] s = new String[2];
        try {
            Query q = null;
            session = HibernateAccess.currentSession();
            q = session.createSQLQuery("SELECT gm2.text FROM answerdata ad " +
                    "JOIN useranswers ua ON ad.id = ua.answer " +
                    "JOIN questions q on q.id = ad.question " +
                    "JOIN answers a on a.id = ad.answer " +
                    "JOIN generalmessages gm1 ON gm1.labelkey = q.key " +
                    "JOIN generalmessages gm2 ON gm2.labelkey = a.key " +
                    "WHERE ua.loginname = '"+user+"' " +
                    "and ua.assesment = "+String.valueOf(assesment)+" " +
                    "and gm1.language = 'es' " +
                    "and gm1.text = 'Sexo' " +
                    "and gm2.language = 'es'").addScalar("text",Hibernate.STRING);
            Iterator it = q.list().iterator();
            if(it.hasNext()) {
                s[0] = ((String)it.next()).substring(0,1);
            }else {
                q = session.createSQLQuery("SELECT sex FROM users WHERE loginname = '"+user+"'").addScalar("sex",Hibernate.INTEGER);
                it = q.list().iterator();
                try {
	                if(it.hasNext()) {
	                    s[0] = (((Integer)it.next()).intValue() == 1) ? "F" : "M";
	                }else {
						s[0] = "M";
	                }
                } catch (Exception e) {
					s[0] = "M";
				}
            }
            
            if(assesment.intValue() == AssesmentData.PHILLIP_MORRIS_MX_2015 || assesment.intValue() == AssesmentData.CIEMSA_2015
            		|| assesment.intValue() == AssesmentData.TIMAC_BRASIL) {
	            q = session.createSQLQuery("SELECT ad.answer FROM answerdata ad " +
	                    "JOIN useranswers ua ON ad.id = ua.answer " +
	                    "WHERE ua.loginname = '"+user+"' " +
	                    "and ua.assesment = "+String.valueOf(assesment)+" AND ad.question IN (15757,15822,16973,17031)").addScalar("answer",Hibernate.INTEGER);
	            it = q.list().iterator();
	            int age = 30;
	            if(it.hasNext()) {
	            	switch(((Integer)it.next()).intValue()) {
	            		case 55828: case 59602: case 59800:
		            		age = 19;
		            		break;
		            	case 55603:
		            		age = 22;
		            		break;
		            	case 55826: case 59597: case 59799:
		            		age = 23;
		            		break;
		            	case 55605:
		            		age = 27;
		            		break;
		            	case 55827: case 59599: case 59801:
		            		age = 28;
		            		break;
		            	case 55604: case 59598: case 59798:
		            		age = 32;
		            		break;
		            	case 55606: case 59601: case 59796:
		            		age = 37;
		            		break;
		            	case 55607: case 55829: case 59600: case 59797:
		            		age = 42;
		            		break;
		            	case 56000:
		            		age = 55;
		            		break;
	            	}
	            }
	            s[1] = String.valueOf(age);
            }else {
	            q = session.createSQLQuery("SELECT ad.date FROM answerdata ad " +
	                    "JOIN useranswers ua ON ad.id = ua.answer " +
	                    "JOIN questions q ON q.id = ad.question " +
	                    "JOIN generalmessages gm1 ON gm1.labelkey = q.key " +
	                    "WHERE ua.loginname = '"+user+"' " +
	                    "and ua.assesment = "+String.valueOf(assesment)+" " +
	                    "AND gm1.language = 'es' " +
	                    "AND gm1.text = 'Fecha de Nacimiento'").addScalar("date",Hibernate.DATE);
	            it = q.list().iterator();
	            if(it.hasNext()) {
	                Calendar birth = Calendar.getInstance();
	                birth.setTime((Date)it.next());
	                Calendar now = Calendar.getInstance();
	                int age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
	                birth.set(Calendar.YEAR,now.get(Calendar.YEAR));
	                if(birth.after(now)) {
	                    age--;
	                }
	                s[1] = String.valueOf(age);
	            }else {
	                q = session.createSQLQuery("SELECT brithdate FROM users WHERE loginname = '"+user+"'").addScalar("brithdate",Hibernate.DATE);
	                it = q.list().iterator();
	                if(it.hasNext()) {
	                    Calendar birth = Calendar.getInstance();
	                    birth.setTime((Date)it.next());
	                    Calendar now = Calendar.getInstance();
	                    int age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
	                    birth.set(Calendar.YEAR,now.get(Calendar.YEAR));
	                    if(birth.after(now)) {
	                        age--;
	                    }
	                    s[1] = String.valueOf(age);
	                }
	            }
            }
        } catch (Exception e) {
            handler.getException(e,"getAgenSex",userSessionData.getFilter().getLoginName());
        }
        return s;
    }
    
    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public String[][] getWrongAnswers(Integer assessment,String user,UserSessionData userSessionData) throws Exception {
        try {
            String sql = "SELECT m.key AS module,q.key AS question," +
                    "a.key AS answered,a2.key AS correct " +
                    "FROM useranswers ua " +
                    "JOIN answerdata ad ON ua.answer = ad.id " +
                    "JOIN answers a ON ad.answer = a.id " +
                    "JOIN questions q ON a.question = q.id " +
                    "JOIN answers a2 ON a2.question = q.id " +
                    "JOIN modules m ON q.module = m.id " +
                    "WHERE ua.loginname = '"+user+"' "+
                    "AND ua.assesment = "+String.valueOf(assessment)+
                    " AND a.type = "+String.valueOf(AnswerData.INCORRECT)+
                    " AND a2.type = "+String.valueOf(AnswerData.CORRECT)+
                    " AND q.testtype = "+String.valueOf(QuestionData.TEST_QUESTION)+
                    " ORDER BY m.moduleorder,q.questionorder";
            Session session = HibernateAccess.currentSession();
            SQLQuery q = session.createSQLQuery(sql);
            q.addScalar("module",Hibernate.STRING);
            q.addScalar("question",Hibernate.STRING);
            q.addScalar("answered",Hibernate.STRING);
            q.addScalar("correct",Hibernate.STRING);
            List l = q.list();
            String[][] result = new String[l.size()][4];
            Iterator it = l.iterator();
            int i = 0;
            while(it.hasNext()) {
                Object[] data = (Object[])it.next();
                for(int j = 0; j < 4; j++) {
                    result[i][j] = String.valueOf(data[j]);
                }
                i++;
            }
            return result;
        }catch (Exception e) {
            handler.getException(e,"getWrongAnswers",userSessionData.getFilter().getLoginName());
        }
        return new String[0][0];
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public Collection getEmailByQuestion(Integer assessment, String user, UserSessionData userSessionData) throws Exception {
    	try {
            String sql = "SELECT q.key,ad.text " +
            		"FROM useranswers ua " +
            		"JOIN answerdata ad ON ua.answer = ad.id " +
            		"JOIN questions q ON q.id = ad.question " +
            		"WHERE ua.loginname = '"+user+"' "+
            		"AND ua.assesment = "+String.valueOf(assessment)+
            		" AND q.type = "+String.valueOf(QuestionData.EMAIL);
            Session session = HibernateAccess.currentSession();
            SQLQuery q = session.createSQLQuery(sql);
            q.addScalar("key",Hibernate.STRING);
            q.addScalar("text",Hibernate.STRING);
            return q.list();
    	}catch (Exception e) {
            handler.getException(e,"getEmailByQuestion",userSessionData.getFilter().getLoginName());
		}
    	return new LinkedList();
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public String getQuestionImage(Integer module, Integer group, UserSessionData userSessionData) throws Exception {
    	try {
            String sql = "SELECT image, questionorder " +
            		"FROM questions " +
            		"WHERE module = "+module+
            		" AND groupid = "+group+
            		" ORDER BY questionorder";
            Session session = HibernateAccess.currentSession();
            SQLQuery q = session.createSQLQuery(sql);
            q.addScalar("image",Hibernate.STRING);
            q.addScalar("questionorder",Hibernate.STRING);
            Collection list = q.list();
            if(list != null && list.size() > 0) {
            	return String.valueOf(((Object[])list.iterator().next())[0]);
            }
    	}catch (Exception e) {
            handler.getException(e,"getQuestionImage",userSessionData.getFilter().getLoginName());
		}
    	return null;
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,clientreporter,cepareporter,systemaccess"
     */
    public Collection getQuestionReport(Integer assessment, UserSessionData userSessionData) throws Exception {
    	try {
			String filter = "";
			if(userSessionData.getFilter().getLoginName().startsWith("charla")) {
				filter = " AND u.loginname IN (SELECT loginname FROM usersaccess WHERE passworddate > NOW() - INTERVAL '2 hour' AND loginname LIKE 'generate_es_%_"+assessment+"') ";
			}
			if(assessment.intValue() == AssesmentData.TIMAC_BRASIL || assessment.intValue() == AssesmentData.TIMAC_BRASIL_EBTWPLUS) {
				filter = " AND u.firstname != 'DESLIGADO' ";
			}
            String sql = "select m.id AS mid,m.key AS mkey,m.moduleorder,q.id AS qid,q.key AS qkey,q.questionorder,a.type, count(*) AS count " +
            		"from userassesments ua " +
            		"join users u on u.loginname = ua.loginname " +
            		"join useranswers uas on uas.loginname = ua.loginname " +
            		"join answerdata ad on ad.id = uas.answer " +
            		"join questions q on ad.question = q.id " +
            		"join answers a on ad.answer = a.id " +
            		"join modules m on m.id = q.module " +
            		"where ua.assesment = "+assessment+" and uas.assesment = ua.assesment and ua.enddate is not null " + filter +
            		"and q.testtype = "+QuestionData.TEST_QUESTION+
            		" group by m.id,m.key,m.moduleorder,q.id,q.key,q.questionorder,a.type";
            Session session = HibernateAccess.currentSession();
            SQLQuery q = session.createSQLQuery(sql);
            q.addScalar("mid",Hibernate.INTEGER);
            q.addScalar("mkey",Hibernate.STRING);
            q.addScalar("moduleorder",Hibernate.INTEGER);
            q.addScalar("qid",Hibernate.INTEGER);
            q.addScalar("qkey",Hibernate.STRING);
            q.addScalar("questionorder",Hibernate.INTEGER);
            q.addScalar("type",Hibernate.INTEGER);
            q.addScalar("count",Hibernate.INTEGER);
            return q.list();
    	}catch (Exception e) {
            handler.getException(e,"getQuestionReport",userSessionData.getFilter().getLoginName());
		}
    	return null;
    }
    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,clientreporter,cepareporter,systemaccess"
     */
    public Collection getQuestionReportByCedi(Integer assessment,Integer cedi, UserSessionData userSessionData) throws Exception {
    	try {
			String filter = cedi==null?"":(" AND location="+cedi);
		
            String sql = "select m.id AS mid,m.key AS mkey,m.moduleorder,q.id AS qid,q.key AS qkey,q.questionorder,a.type, count(*) AS count " +
            		"from userassesments ua " +
            		"join users u on u.loginname = ua.loginname " +
            		"join useranswers uas on uas.loginname = ua.loginname " +
            		"join answerdata ad on ad.id = uas.answer " +
            		"join questions q on ad.question = q.id " +
            		"join answers a on ad.answer = a.id " +
            		"join modules m on m.id = q.module " +
            		"where ua.assesment = "+assessment+" and uas.assesment = ua.assesment and ua.enddate is not null " + filter +
            		"and q.testtype = "+QuestionData.TEST_QUESTION+
            		" group by m.id,m.key,m.moduleorder,q.id,q.key,q.questionorder,a.type";
            Session session = HibernateAccess.currentSession();
            SQLQuery q = session.createSQLQuery(sql);
            q.addScalar("mid",Hibernate.INTEGER);
            q.addScalar("mkey",Hibernate.STRING);
            q.addScalar("moduleorder",Hibernate.INTEGER);
            q.addScalar("qid",Hibernate.INTEGER);
            q.addScalar("qkey",Hibernate.STRING);
            q.addScalar("questionorder",Hibernate.INTEGER);
            q.addScalar("type",Hibernate.INTEGER);
            q.addScalar("count",Hibernate.INTEGER);
            return q.list();
    	}catch (Exception e) {
            handler.getException(e,"getQuestionReport",userSessionData.getFilter().getLoginName());
		}
    	return null;
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,clientreporter,cepareporter,systemaccess"
     */
    public Collection getQuestionReport(Integer assessment, Integer group, UserSessionData userSessionData) throws Exception {
    	try {
			String filter = "";
			if(userSessionData.getFilter().getLoginName().startsWith("charla")) {
				filter = " AND u.loginname IN (SELECT loginname FROM usersaccess WHERE passworddate > NOW() - INTERVAL '2 hour' AND loginname LIKE 'generate_es_%_"+assessment+"') ";
			}
			if(assessment.intValue() == AssesmentData.TIMAC_BRASIL || assessment.intValue() == AssesmentData.TIMAC_BRASIL_EBTWPLUS) {
				filter = " AND u.firstname != 'DESLIGADO' ";
			}
            String sql = "select m.id AS mid,m.key AS mkey,m.moduleorder,q.id AS qid,q.key AS qkey,q.questionorder,a.type, count(*) AS count " +
            		"from userassesments ua " +
            		"join users u on u.loginname = ua.loginname " +
            		"join useranswers uas on uas.loginname = ua.loginname " +
            		"join answerdata ad on ad.id = uas.answer " +
            		"join questions q on ad.question = q.id " +
            		"join answers a on ad.answer = a.id " +
            		"join modules m on m.id = q.module " +
            		"where ua.assesment = "+assessment+" and uas.assesment = ua.assesment and ua.enddate is not null " + filter +
            		"and q.testtype = "+QuestionData.TEST_QUESTION +
            		" and u.loginname in (select loginname from usergroups where groupid = " + group + ")"+
            		" group by m.id,m.key,m.moduleorder,q.id,q.key,q.questionorder,a.type";
            Session session = HibernateAccess.currentSession();
            SQLQuery q = session.createSQLQuery(sql);
            q.addScalar("mid",Hibernate.INTEGER);
            q.addScalar("mkey",Hibernate.STRING);
            q.addScalar("moduleorder",Hibernate.INTEGER);
            q.addScalar("qid",Hibernate.INTEGER);
            q.addScalar("qkey",Hibernate.STRING);
            q.addScalar("questionorder",Hibernate.INTEGER);
            q.addScalar("type",Hibernate.INTEGER);
            q.addScalar("count",Hibernate.INTEGER);
            return q.list();
    	}catch (Exception e) {
            handler.getException(e,"getQuestionReport",userSessionData.getFilter().getLoginName());
		}
    	return null;
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,clientreporter,cepareporter"
     */
   public HashMap<String, Object> getFullAnswers(Integer assessment, UserSessionData userSessionData) throws Exception {
	   HashMap<String, Object> map = new HashMap<String, Object>();
	   try {
   			String sql = "select ua.loginname, ad.*, mo.answer as mo " +
   					"from useranswers ua " +
   					"join answerdata ad on ad.id = ua.answer " +
   					"left join multioptions mo on mo.id = ad.id " +
   					"where assesment = "+assessment;
	        Session session = HibernateAccess.currentSession();
	        SQLQuery q = session.createSQLQuery(sql);
	        q.addScalar("loginname",Hibernate.STRING);
	        q.addScalar("id",Hibernate.INTEGER);
	        q.addScalar("question",Hibernate.INTEGER);
	        q.addScalar("answer",Hibernate.INTEGER);
	        q.addScalar("text",Hibernate.STRING);
	        q.addScalar("date",Hibernate.DATE);
	        q.addScalar("distance",Hibernate.INTEGER);
	        q.addScalar("unit",Hibernate.INTEGER);
	        q.addScalar("country",Hibernate.INTEGER);
	        q.addScalar("mo",Hibernate.INTEGER);
	        q.addScalar("never",Hibernate.BOOLEAN);
	        Iterator it = q.list().iterator(); 
	        while(it.hasNext()) {
	        	Object[] data = (Object[])it.next();
	        	String key = data[0] + "-" + data[2];
	        	if(data[9] != null) {
	        		if(map.containsKey(key)) {
	        			((Collection)map.get(key)).add(data[9]);
	        		}else {
	        			Collection<Integer> answers = new LinkedList<Integer>();
	        			answers.add((Integer)data[9]);
	        			map.put(key, answers);
	        		}
	        	} else if(data[3] != null) {
		        	map.put(key, data[3]);
	        	} else if(data[4] != null && ((String)data[4]).length() > 0) {
        			map.put(key, data[4]);
	        	} else if(data[5] != null) {
	        		Object[] values = {data[5],data[9]};
        			map.put(key, values);
	        	} else if(data[6] != null) {
	        		Object[] values = {data[6],data[7]};
        			map.put(key, values);
	        	} else if(data[8] != null) {
        			map.put(key, data[8]);
	        	}
	        }
		}catch (Exception e) {
	        handler.getException(e,"getFullAnswers",userSessionData.getFilter().getLoginName());
		}
		return map;
    }

   /**
    * @ejb.interface-method 
    * @ejb.permission role-name = "administrator,systemaccess"
    */
  	public String[][] getCompleteAnswers(Integer assessment, String login, UserSessionData userSessionData) throws Exception {
 	   try {
  			String sql = "select q.key as question, ad.text, ad.date, a.key as answer" +
  					" from useranswers ua " +
  					"join answerdata ad on ua.answer = ad.id " +
  					"join questions q on q.id = ad.question " +
  					"join modules m on m.id = q.module " +
  					"left join answers a on a.id = ad.answer " +
  					"where ua.assesment = "+assessment.toString()+" and loginname = '"+login+"'" +
  					"order by moduleorder, questionorder";
	        Session session = HibernateAccess.currentSession();
	        SQLQuery q = session.createSQLQuery(sql);
	        q.addScalar("question",Hibernate.STRING);
	        q.addScalar("text",Hibernate.STRING);
	        q.addScalar("date",Hibernate.DATE);
	        q.addScalar("answer",Hibernate.STRING);
	        
	        Collection set = q.list();
	        String[][] values = new String[set.size()][2];
	        int index = 0;
	        Iterator it = set.iterator();
	        while(it.hasNext()) {
	        	Object[] data = (Object[])it.next();
	        	values[index][0] = (String) data[0];
	        	if(data[1] != null) {
		        	values[index][1] = (String) data[1];
	        	} else if(data[2] != null) {
		        	values[index][1] = Util.formatDate((Date) data[2]);
	        	} else {
		        	values[index][1] = (String) data[3];
	        	}
	        	index++;
	        }
	        return values;
		}catch (Exception e) {
	        handler.getException(e,"getCompleteAnswers",userSessionData.getFilter().getLoginName());
		}
 		return null;
   	}

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess,clientreporter,cepareporter"
     */
   	public Integer[][] getOptionAnswers(Integer assessment, String login, UserSessionData userSessionData) throws Exception {
  	   try {
   			String sql = "select q.id as question, a.id as answer, a.type as result" +
   					" from useranswers ua " +
   					"join answerdata ad on ua.answer = ad.id " +
   					"join questions q on q.id = ad.question " +
   					"join modules m on m.id = q.module " +
   					"left join answers a on a.id = ad.answer " +
   					"where ua.assesment = "+assessment.toString()+" and loginname = '"+login+"'" +
   					"order by moduleorder, questionorder";
 	        Session session = HibernateAccess.currentSession();
 	        SQLQuery q = session.createSQLQuery(sql);
 	        q.addScalar("question",Hibernate.INTEGER);
 	        q.addScalar("answer",Hibernate.INTEGER);
 	        q.addScalar("result",Hibernate.INTEGER);
 	        
 	        Collection set = q.list();
 	        Integer[][] values = new Integer[set.size()][3];
 	        int index = 0;
 	        Iterator it = set.iterator();
 	        while(it.hasNext()) {
 	        	Object[] data = (Object[])it.next();
 	        	for(int i = 0; i < data.length; i++)
 	        		values[index][i] = (Integer) data[i];
 	        	index++;
 	        }
 	        return values;
 		}catch (Exception e) {
 	        handler.getException(e,"getOptionAnswers",userSessionData.getFilter().getLoginName());
 		}
  		return null;
    }

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess"
     */
   	public Collection<VideoData> getVideos(UserSessionData userSessionData) throws Exception {
   		Collection<VideoData> videos = new LinkedList<VideoData>();
   		try {
 	        Session session = HibernateAccess.currentSession();
 	        Query q = session.createQuery("SELECT v FROM Video v");
 	        
 	        Iterator<Video> it = q.list().iterator();
 	        while(it.hasNext()) {
 	        	videos.add(it.next().getData());
 	        }
 		}catch (Exception e) {
 	        handler.getException(e,"getVideos",userSessionData.getFilter().getLoginName());
 		}
  		return videos;
    }
 
    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess"
     */
   	public Collection getSurveyResult(String login, UserSessionData userSessionData) throws Exception {
   		try {
 	        Session session = HibernateAccess.currentSession();
 	        String sql = "SELECT q.module, a.id, a.answerorder " +
   				"FROM useranswers ua " +
   				"JOIN answerdata ad ON ua.answer = ad.id " +
   				"JOIN answers a ON ad.answer = a.id " +
   				"JOIN questions q ON q.id = ad.question " +
   				"WHERE loginname = '"+login+"' AND ua.assesment = "+AssesmentData.SURVEY;
 	        Query q = session.createSQLQuery(sql)
 	        		.addScalar("module", Hibernate.INTEGER)
 	        		.addScalar("id", Hibernate.INTEGER)
 	        		.addScalar("answerorder", Hibernate.INTEGER);
 	        
 	        return q.list();
 		}catch (Exception e) {
 	        handler.getException(e,"getSurveyResult",userSessionData.getFilter().getLoginName());
 		}
  		return new LinkedList();
   	}

    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator"
     */
   	public Collection getTotalAnswers(Integer assessment, Text messages, UserSessionData userSessionData) throws Exception {
   		Collection userValues = new LinkedList();
   		try {
 	        Session session = HibernateAccess.currentSession();
 	        
            SQLQuery query = session.createSQLQuery("SELECT firstname, lastname, u.loginname, u.email, u.extradata3, enddate " +
            		"FROM userassesments ua " +
            		"JOIN users u ON u.loginname = ua.loginname " +
            		"WHERE ua.assesment = "+assessment);
            query.addScalar("firstname", Hibernate.STRING);
            query.addScalar("lastname", Hibernate.STRING);
            query.addScalar("loginname", Hibernate.STRING);
            query.addScalar("email", Hibernate.STRING);
            query.addScalar("extradata3", Hibernate.STRING);            
            query.addScalar("enddate", Hibernate.DATE);
            
            HashMap<String, AssessmentUserData> values = new HashMap<String, AssessmentUserData>();
            Iterator it = query.list().iterator();
            while(it.hasNext()) {
            	Object[] data = (Object[])it.next();
            	values.put((String)data[2], new AssessmentUserData(data));
            }

 	        String sql = "SELECT ua.loginname, question, text, date, distance, unit, country, never, NULL AS aText, 0 AS aResult " +
 	        		"FROM useranswers ua " +
 	        		"JOIN answerdata ad ON ad.id = ua.answer " +
 	        		"WHERE assesment = "+assessment+" AND ad.answer IS NULL " +
 	        		"UNION (SELECT ua.loginname, ad.question, text, date, distance, unit, country, never, a.key, a.type " +
 	        		"FROM useranswers ua " +
 	        		"JOIN answerdata ad ON ad.id = ua.answer " +
 	        		"JOIN answers a ON ad.answer = a.id " +
 	        		"WHERE assesment = "+assessment+") " +
 	        		"UNION (SELECT ua.loginname, ad.question, text, date, distance, unit, country, never, a.key, a.type " + 
 	        		"FROM useranswers ua " + 
 	        		"JOIN answerdata ad ON ad.id = ua.answer " + 
 	        		"join multioptions mu on mu.id = ad.id " + 
 	        		"JOIN answers a ON mu.answer = a.id " + 
 	        		"WHERE assesment = "+assessment+")";
 	       SQLQuery q = session.createSQLQuery(sql);
           query.addScalar("loginname", Hibernate.STRING);
           query.addScalar("question", Hibernate.INTEGER);
           query.addScalar("text", Hibernate.STRING);
           query.addScalar("date", Hibernate.DATE);
           query.addScalar("distance", Hibernate.INTEGER);
           query.addScalar("unit", Hibernate.INTEGER);
           query.addScalar("country", Hibernate.INTEGER);
           query.addScalar("never", Hibernate.BOOLEAN);
           query.addScalar("aText", Hibernate.STRING);
           query.addScalar("aResult", Hibernate.INTEGER);
 	       it = q.list().iterator();
 	       while(it.hasNext()) {
 	    	   Object[] data = (Object[])it.next();
 	    	   if(values.containsKey(data[0])) {
 	    		  values.get(data[0]).addQuestionValues(data, new CountryConstants(), messages);
 	    	   }
 	       }
 	       userValues.addAll(values.values());
 		}catch (Exception e) {
 	        handler.getException(e,"getUserAnswers",userSessionData.getFilter().getLoginName());
 		}
  		return userValues;
   	}
   	
    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public QuestionData findQuestionBKP(Integer id, UserSessionData userSessionData,int type) throws Exception {
        if (id == null) {
            throw new InvalidDataException("findQuestionBKP","id = null");
        }
        if (userSessionData == null) {
            throw new DeslogedException("findQuestionBKP","session = null");
        }

        Session session = null;
        try {
            session = HibernateAccess.currentSession();
            if(type == QuestionData.NORMAL) {
                QuestionBKP question = (QuestionBKP)session.load(QuestionBKP.class,id);
                return question.getData();
            }else {
                GenericQuestion question = (GenericQuestion)session.load(GenericQuestion.class,id);
                return question.getData();
            }
        } catch (Exception e) {
            handler.getException(e,"findQuestionBKP",userSessionData.getFilter().getLoginName());
        }
        return null;
    }
    
    /**
     * @ejb.interface-method 
     * @ejb.permission role-name = "administrator,systemaccess"
     */
   	public String[][] getCompleteAnswersBKP(Integer assessment, String login, UserSessionData userSessionData) throws Exception {
  	   try {
   			String sql = "select q.key as question, ad.text, ad.date, a.key as answer" +
   					" from useranswersbkp ua " +
   					"join answerdatabkp ad on ua.answer = ad.id " +
   					"join questionsbkp q on q.id = ad.question " +
   					"join modulesbkp m on m.id = q.module " +
   					"left join answersbkp a on a.id = ad.answer " +
   					"where ua.assesment = "+assessment.toString()+" and loginname = '"+login+"'" +
   					"order by moduleorder, questionorder";
 	        Session session = HibernateAccess.currentSession();
 	        SQLQuery q = session.createSQLQuery(sql);
 	        q.addScalar("question",Hibernate.STRING);
 	        q.addScalar("text",Hibernate.STRING);
 	        q.addScalar("date",Hibernate.DATE);
 	        q.addScalar("answer",Hibernate.STRING);
 	        
 	        Collection set = q.list();
 	        String[][] values = new String[set.size()][2];
 	        int index = 0;
 	        Iterator it = set.iterator();
 	        while(it.hasNext()) {
 	        	Object[] data = (Object[])it.next();
 	        	values[index][0] = (String) data[0];
 	        	if(data[1] != null) {
 		        	values[index][1] = (String) data[1];
 	        	} else if(data[2] != null) {
 		        	values[index][1] = Util.formatDate((Date) data[2]);
 	        	} else {
 		        	values[index][1] = (String) data[3];
 	        	}
 	        	index++;
 	        }
 	        return values;
 		}catch (Exception e) {
 	        handler.getException(e,"getCompleteAnswersBKP",userSessionData.getFilter().getLoginName());
 		}
  		return null;
    	}
}