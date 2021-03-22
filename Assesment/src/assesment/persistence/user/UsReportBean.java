package assesment.persistence.user;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.CreateException;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;

import assesment.communication.administration.UserAnswerData;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.assesment.GroupData;
import assesment.communication.exception.DeslogedException;
import assesment.communication.exception.InvalidDataException;
import assesment.communication.question.AnswerData;
import assesment.communication.question.QuestionData;
import assesment.communication.report.UserAnswerReportData;
import assesment.communication.security.SecurityConstants;
import assesment.communication.user.UserData;
import assesment.communication.util.CountryConstants;
import assesment.communication.util.ListResult;
import assesment.communication.util.MD5;
import assesment.persistence.administration.tables.AssessmentUserData;
import assesment.persistence.administration.tables.UserAssesment;
import assesment.persistence.administration.tables.UserMultiAssessment;
import assesment.persistence.assesment.tables.Assesment;
import assesment.persistence.hibernate.HibernateAccess;
import assesment.persistence.user.tables.User;
import assesment.persistence.util.ExceptionHandler;
import assesment.persistence.util.PersistenceUtil;
import assesment.persistence.util.Util;

/**
 * 
 * @ejb.bean name="UsReport" jndi-name="ejb/UsReport" type="Stateless"
 *           transaction-type="Container"
 * 
 *           <!-- end-xdoclet-definition -->
 * @generated
 */
public abstract class UsReportBean implements javax.ejb.SessionBean {

	private ExceptionHandler handler = new ExceptionHandler(UsReportBean.class);

	/**
	 * @ejb.create-method
	 * @ejb.permission role-name =
	 *                 "systemaccess,administrator,resetpassword,accesscode,pepsico_candidatos,basf_assessment,clientreporter,cepareporter,webinar"
	 * @throws CreateException
	 */
	public void ejbCreate() throws CreateException {
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name =
	 *                 "systemaccess,administrator,accesscode,clientreporter,cepareporter"
	 * 
	 * @param loginName
	 * @param passwd
	 * @return boolean
	 */
	public boolean login(String loginName, String passwd) throws Exception {
		try {
			Session session = HibernateAccess.currentSession();
			User usr = (User) session.load(User.class, loginName.trim());
			return (usr.getPassword().compareTo(new MD5().encriptar(passwd.trim())) == 0);
		} catch (Exception e) {
			handler.getException(e, "login", loginName);
			return false;
		}
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name =
	 *                 "systemaccess,administrator,resetpassword,accesscode,pepsico_candidatos,basf_assessment,clientreporter,cepareporter,webinar"
	 * @param loginName
	 * @param userRequest
	 * @return
	 * @throws Exception
	 */
	public UserData findUserByPrimaryKey(String loginName, Integer corporationId, UserSessionData userSessionData)
			throws Exception {
		UserData data = null;
		try {
			if (loginName == null) {
				throw new InvalidDataException("findUserByPrimaryKey", "loginname = null");
			}
			if (userSessionData == null) {
				throw new DeslogedException("findUserByPrimaryKey", "userRequest = null");
			}

			Session session = HibernateAccess.currentSession();
			Query query = session.createQuery("SELECT user FROM User user WHERE lower(user.loginName) = lower(:nick)");
			query.setParameter("nick", loginName);
			User user = (User) query.uniqueResult();
			if (user != null) {
				data = user.getUserData();
			}
		} catch (Exception e) {
			handler.getException(e, "findUserByPrimaryKey", userSessionData.getFilter().getLoginName());
		}
		return data;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name =
	 *                 "systemaccess,administrator,resetpassword,accesscode,pepsico_candidatos,basf_assessment,clientreporter,cepareporter"
	 * @param loginName
	 * @param userRequest
	 * @return
	 * @throws Exception
	 */
	public Collection getUserList(String value, UserSessionData userSessionData) throws Exception {
		Collection list = new LinkedList();
		try {
			if (userSessionData == null) {
				throw new DeslogedException("findUserByPrimaryKey", "userRequest = null");
			}

			Session session = HibernateAccess.currentSession();
			String sql = "SELECT loginname FROM users ";
			if(value != null && value.trim().length() > 0) {
				sql += "WHERE lower(loginname) LIKE '%"+value.trim().toLowerCase()+"%'";
			}
			Query query = session.createSQLQuery(sql).addScalar("loginname", Hibernate.STRING);
			list.addAll(query.list());
		} catch (Exception e) {
			handler.getException(e, "getUserList", userSessionData.getFilter().getLoginName());
		}
		return list;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name =
	 *                 "systemaccess,administrator,resetpassword,accesscode"
	 */
	public Collection findAbitabUser(String loginName, UserSessionData userSessionData) throws Exception {
		try {
			if (loginName == null) {
				throw new InvalidDataException("findAbitabUser", "loginname = null");
			}
			if (userSessionData == null) {
				throw new DeslogedException("findAbitabUser", "userRequest = null");
			}

			Session session = HibernateAccess.currentSession();
			Query query = session
					.createSQLQuery(
							"SELECT loginname FROM users WHERE lower(loginname) like 'abitab_" + loginName + "_%'")
					.addScalar("loginname", Hibernate.STRING);
			return query.list();
		} catch (Exception e) {
			handler.getException(e, "findAbitabUser", userSessionData.getFilter().getLoginName());
		}
		return new LinkedList();
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name =
	 *                 "systemaccess,administrator,resetpassword,accesscode"
	 */
	public Collection findAbitabUser(String loginName, String email, UserSessionData userSessionData) throws Exception {
		Collection list = new LinkedList();
		try {
			if (loginName == null) {
				throw new InvalidDataException("findAbitabUser", "loginname = null");
			}
			if (userSessionData == null) {
				throw new DeslogedException("findAbitabUser", "userRequest = null");
			}

			Session session = HibernateAccess.currentSession();
			Query query = session
					.createSQLQuery(
							"SELECT u.loginname,u.startdate FROM users u JOIN userassesments ua ON u.loginname = ua.loginname WHERE lower(u.loginname) like 'abitab_"
									+ loginName + "_%' AND lower(u.email) = '" + email.toLowerCase()
									+ "' AND ua.assesment = " + AssesmentData.ABITAB)
					.addScalar("loginname", Hibernate.STRING).addScalar("startdate", Hibernate.DATE);
			Iterator it = query.list().iterator();
			while (it.hasNext()) {
				Object[] data = (Object[]) it.next();
				UserData user = new UserData();
				user.setLoginName((String) data[0]);
				user.setStartDate((Date) data[1]);
				list.add(user);
			}
		} catch (Exception e) {
			handler.getException(e, "findAbitabUser", userSessionData.getFilter().getLoginName());
		}
		return list;
	}

	/**
	 * @param queryStr
	 * @param attribute
	 * @param value
	 * @param userRequest
	 * @return
	 */
	private Query getQuery(String queryStr, int attribute, String value, UserSessionData userRequest, boolean order) {
		Session session = HibernateAccess.currentSession();
		switch (attribute) {
		case 0:
			queryStr += " and lower(u.firstName) like lower(:value) ";
			break;
		case 1:
			queryStr += " and lower(u.lastName) like lower(:value) ";
			break;
		case 2:
			queryStr += " and lower(u.loginName) like lower(:value) ";
			break;
		}
		if (order) {
			queryStr += " order by u.firstName,u.loginName ";
		}
		Query query = session.createQuery(queryStr);
		query.setParameter("value", "%" + value + "%");
		return query;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator"
	 * @throws Exception
	 */
	public Collection<UserData> findList(HashMap<String, String> parameters, UserSessionData userSessionData)
			throws Exception {
		Collection<UserData> users = new LinkedList<UserData>();
		try {
			Session session = HibernateAccess.currentSession();
			String sql = "SELECT DISTINCT u.loginname, u.firstname, u.lastname, u.email, u.language, u.role FROM users u ";
			String role = parameters.get("role");
			if(role.length() == 0) {
				sql += "WHERE u.role IN ('" + SecurityConstants.ACCESS_TO_SYSTEM + "', '" + SecurityConstants.MULTI_ASSESSMENT + "', '" + SecurityConstants.GROUP_ASSESSMENT + "', '" + SecurityConstants.CLIENT_REPORTER + "', '" + SecurityConstants.CEPA_REPORTER + "', '" + SecurityConstants.CLIENTGROUP_REPORTER + "','" + SecurityConstants.ADMINISTRATOR + "') ";
			}else {
				if (role.equals(SecurityConstants.ACCESS_TO_SYSTEM) || role.equals(SecurityConstants.CLIENT_REPORTER)) {
					sql += " JOIN userassesments ua ON u.loginname = ua.loginname JOIN assesments a ON a.id = ua.assesment ";
				}
				if (role.equals(SecurityConstants.GROUP_ASSESSMENT) || role.equals(SecurityConstants.CLIENTGROUP_REPORTER)) {
					sql += " JOIN usergroups ua ON u.loginname = ua.loginname ";
				}
				if (role.equals(SecurityConstants.ACCESS_TO_SYSTEM)) {
					sql += "WHERE u.role IN ('" + SecurityConstants.ACCESS_TO_SYSTEM + "', '" + SecurityConstants.MULTI_ASSESSMENT + "', '" + SecurityConstants.GROUP_ASSESSMENT + "')";
				}else {	
					sql += "WHERE u.role = '" + role + "'";
				}
			}
			if (parameters.containsKey("login")) {
				sql += " AND LOWER(u.loginname) LIKE '%" + parameters.get("login").toLowerCase() + "%' ";
			}
			if (parameters.containsKey("firstName")) {
				sql += " AND LOWER(u.firstname) LIKE '%" + parameters.get("firstName").toLowerCase() + "%' ";
			}
			if (parameters.containsKey("lastName")) {
				sql += " AND LOWER(u.lastname) LIKE '%" + parameters.get("lastName").toLowerCase() + "%' ";
			}
			if (parameters.containsKey("email")) {
				sql += " AND LOWER(u.email) LIKE '%" + parameters.get("email").toLowerCase() + "%' ";
			}
			if (role.equals(SecurityConstants.ACCESS_TO_SYSTEM) || role.equals(SecurityConstants.CLIENT_REPORTER)) {
				sql += (parameters.get("archived").equals("0")) ? " AND NOT a.archived" : " AND a.archived";
			}
			if ((role.equals(SecurityConstants.ACCESS_TO_SYSTEM) || role.equals(SecurityConstants.CLIENT_REPORTER))
					&& parameters.containsKey("assessment")) {
				sql += " AND ua.assesment = " + parameters.get("assessment");
			}
			sql += " ORDER BY firstname, lastname";
			Query q = session.createSQLQuery(sql).addScalar("loginname", Hibernate.STRING)
					.addScalar("firstname", Hibernate.STRING).addScalar("lastname", Hibernate.STRING)
					.addScalar("email", Hibernate.STRING).addScalar("language", Hibernate.STRING).addScalar("role", Hibernate.STRING);
			q.setMaxResults(50);
			List list = q.list();
			if (list != null) {
				Iterator it = list.iterator();
				while (it.hasNext()) {
					users.add(new UserData((Object[]) it.next()));
				}
			}

		} catch (Exception e) {
			handler.getException(e, "findList", userSessionData.getFilter().getLoginName());
		}
		return users;
	}
	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator"
	 * @throws Exception
	 */
	public Collection<UserData> findListWrongCode(HashMap<String, String> parameters, UserSessionData userSessionData)
			throws Exception {
		Collection<UserData> users = new LinkedList<UserData>();
		try {
			Session session = HibernateAccess.currentSession();
	        Calendar c = Calendar.getInstance();
	        c.add(Calendar.MONTH, -2);
	        String cal="";
	       // if(c == null) cal  = "";
	        //else cal= c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH);
	         cal= "2020-10-01";
	   
			String sql = "select u.firstname, u.lastname, u.email, ua.loginname, u.extradata3 from userassesments ua join users u on u.loginname= ua.loginname join sendedreports sr on sr.assessment=ua.assesment and sr.login=ua.loginname where sr.assessment in (1149, 1270, 1324, 1327, 1471) and sr.sended=true and sr.certificate=false and enddate > '"+cal+"' order by firstname, lastname" ;
			/*String role = parameters.get("role");
			if(role.length() == 0) {
				sql += "WHERE u.role IN ('" + SecurityConstants.ACCESS_TO_SYSTEM + "', '" + SecurityConstants.MULTI_ASSESSMENT + "', '" + SecurityConstants.GROUP_ASSESSMENT + "', '" + SecurityConstants.CLIENT_REPORTER + "', '" + SecurityConstants.CEPA_REPORTER + "', '" + SecurityConstants.CLIENTGROUP_REPORTER + "','" + SecurityConstants.ADMINISTRATOR + "') ";
			}else {
				if (role.equals(SecurityConstants.ACCESS_TO_SYSTEM) || role.equals(SecurityConstants.CLIENT_REPORTER)) {
					sql += " JOIN userassesments ua ON u.loginname = ua.loginname JOIN assesments a ON a.id = ua.assesment ";
				}
				if (role.equals(SecurityConstants.GROUP_ASSESSMENT) || role.equals(SecurityConstants.CLIENTGROUP_REPORTER)) {
					sql += " JOIN usergroups ua ON u.loginname = ua.loginname ";
				}
				if (role.equals(SecurityConstants.ACCESS_TO_SYSTEM)) {
					sql += "WHERE u.role IN ('" + SecurityConstants.ACCESS_TO_SYSTEM + "', '" + SecurityConstants.MULTI_ASSESSMENT + "', '" + SecurityConstants.GROUP_ASSESSMENT + "')";
				}else {	
					sql += "WHERE u.role = '" + role + "'";
				}
			}
			if (parameters.containsKey("login")) {
				sql += " AND LOWER(u.loginname) LIKE '%" + parameters.get("login").toLowerCase() + "%' ";
			}
			if (parameters.containsKey("firstName")) {
				sql += " AND LOWER(u.firstname) LIKE '%" + parameters.get("firstName").toLowerCase() + "%' ";
			}
			if (parameters.containsKey("lastName")) {
				sql += " AND LOWER(u.lastname) LIKE '%" + parameters.get("lastName").toLowerCase() + "%' ";
			}
			if (parameters.containsKey("email")) {
				sql += " AND LOWER(u.email) LIKE '%" + parameters.get("email").toLowerCase() + "%' ";
			}
			if (role.equals(SecurityConstants.ACCESS_TO_SYSTEM) || role.equals(SecurityConstants.CLIENT_REPORTER)) {
				sql += (parameters.get("archived").equals("0")) ? " AND NOT a.archived" : " AND a.archived";
			}
			if ((role.equals(SecurityConstants.ACCESS_TO_SYSTEM) || role.equals(SecurityConstants.CLIENT_REPORTER))
					&& parameters.containsKey("assessment")) {
				sql += " AND ua.assesment = " + parameters.get("assessment");
			}
			sql += " ORDER BY firstname, lastname";*/
			Query q = session.createSQLQuery(sql).addScalar("firstname", Hibernate.STRING).addScalar("lastname", Hibernate.STRING)
					.addScalar("email", Hibernate.STRING).addScalar("loginname", Hibernate.STRING).addScalar("extraData3", Hibernate.STRING);
			//q.setMaxResults(50);
			List list = q.list();
			if (list != null) {
				Iterator it = list.iterator();
				while (it.hasNext()) {
					Object[] data = (Object[]) it.next();
					UserData userData = new UserData();
					userData.setLoginName((String) data[3]);
					userData.setFirstName((String) data[0]);
					userData.setLastName((String) data[1]);
					userData.setEmail((String) data[2]);
					userData.setExtraData3((String) data[4]);
					users.add(userData);
				}
			}

		} catch (Exception e) {
			handler.getException(e, "findList", userSessionData.getFilter().getLoginName());
		}
		return users;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "systemaccess,administrator"
	 * @param value
	 * @param userRequest
	 * @param attribute
	 * @param first
	 * @param count
	 * @return
	 * @throws Exception
	 */
	public ListResult findList(String value, UserSessionData userSessionData, int attribute, int first, int count)
			throws Exception {
		try {
			Session session = HibernateAccess.currentSession();
			if (value == null) {
				throw new InvalidDataException("findList", "value = null");
			}
			if (userSessionData == null) {
				throw new DeslogedException("findList", "userSessionData = null");
			}
			String queryStr = "select count(*) from User u " + " where u.loginName != 'resetpassword' ";

			Query query = getQuery(queryStr, attribute, value, userSessionData, false);
			Integer total = (Integer) query.uniqueResult();

			queryStr = "select u from User u " + " where u.loginName != 'resetpassword' ";
			query = getQuery(queryStr, attribute, value, userSessionData, true);
			if (count != -1) {
				if (total.intValue() < first) {
					if (total.intValue() % count == 0) {
						first = total.intValue() - count;
					} else {
						first = total.intValue() - (total.intValue() % count);
					}
				}
				query.setFirstResult(first);
				query.setMaxResults(count);
			}
			List userColl = query.list();
			Collection result = new LinkedList();
			Iterator iter = userColl.iterator();
			while (iter.hasNext()) {
				User user = (User) iter.next();
				result.add(user.getUserData());
			}
			session.close();

			return new ListResult(result, total.intValue());
		} catch (Exception e) {
			handler.getException(e, "findList", userSessionData.getFilter().getLoginName());
		}
		return new ListResult(new LinkedList(), 0);
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "resetpassword"
	 */
	public UserData findUserByEmail(String email, UserSessionData userSessionData) throws Exception {
		UserData data = null;
		try {
			// Check if inited transaction
			if (email == null) {
				throw new InvalidDataException("findUserByEmail", "email = null");
			}
			if (userSessionData == null) {
				throw new DeslogedException("findUserByEmail", "userSessionData = null");
			}
			Session session = HibernateAccess.currentSession();

			Query query = session
					.createQuery("select user from User user  " + "where lower(user.email) = lower(:email)");
			query.setParameter("email", email);
			Iterator it = query.list().iterator();
			if (it.hasNext()) {
				User user = (User) it.next();
				if (user != null) {
					data = user.getUserData();
				}
			}
		} catch (Exception e) {
			handler.getException(e, "findUserByEmail", userSessionData.getFilter().getLoginName());
		}
		return data;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,basf_assessment,systemaccess"
	 */
	public UserData findBasfUser(String ng, String cpf, UserSessionData userSessionData) throws Exception {
		UserData userData = null;
		try {
			String q = "select u.loginname,firstname,lastname, count(*) as c " + "from users u "
					+ "join userassesments ua on u.loginname = ua.loginname " + "where trim(extradata) = '" + ng + "' "
					+ "and trim(extradata2) = '" + cpf + "' "
					+ "and ua.assesment in (select id from assesments where corporation = 42) "
					+ "group by u.loginname,firstname,lastname " + "order by c";
			Session session = HibernateAccess.currentSession();
			Query query = session.createSQLQuery(q).addScalar("loginname", Hibernate.STRING)
					.addScalar("firstname", Hibernate.STRING).addScalar("lastname", Hibernate.STRING)
					.addScalar("c", Hibernate.INTEGER);
			Iterator it = query.list().iterator();
			if (it.hasNext()) {
				Object[] data = (Object[]) it.next();
				userData = new UserData();
				userData.setLoginName((String) data[0]);
				userData.setFirstName((String) data[1]);
				userData.setLastName((String) data[2]);
			}
		} catch (Exception e) {
			handler.getException(e, "findUserByEmail", userSessionData.getFilter().getLoginName());
		}
		return userData;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,systemaccess"
	 */
	public boolean isFirstAccess(String user) throws Exception {
		try {
			Session session = HibernateAccess.currentSession();
			Query query = session
					.createQuery("SELECT firstAccess,passwordDate FROM UserAccess WHERE loginName = '" + user + "'");
			Iterator it = query.list().iterator();
			if (it.hasNext()) {
				Object[] data = (Object[]) it.next();
				if (((Boolean) data[0]).booleanValue()) {
					return true;
				} else {
					Calendar today = Calendar.getInstance();
					Calendar access = Calendar.getInstance();
					access.setTime((Date) data[1]);
					access.add(Calendar.DATE, 90);
					return access.before(today);
				}
			}
		} catch (Exception e) {
			handler.getException(e, "isFirstAccess", user);
		}
		return false;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name =
	 *                 "administrator,systemaccess,accesscode,pepsico_candidatos,basf_assessment,clientreporter,cepareporter"
	 */
	public Collection findUserAssesments(String user, boolean all, UserSessionData userSessionData) throws Exception {
		Collection result = new LinkedList();
		try {
			if (user == null) {
				throw new InvalidDataException("findUserAssesments", "user = null");
			}
			if (userSessionData == null) {
				throw new DeslogedException("findUserAssesments", "userSessionData = null");
			}
			Session session = HibernateAccess.currentSession();
			String q = "SELECT ua.pk.assesment FROM UserAssesment ua WHERE ua.pk.user = '" + user+ "'";
			if(!all)
				 q += " AND ua.endDate IS NULL";
			Query query = session.createQuery(q);
			List listResult = query.list();
			if (listResult != null && listResult.size() > 0) {
				Iterator it = listResult.iterator();
				while (it.hasNext()) {
					result.add(((Assesment) it.next()).getAssesment());
				}
			}

			if (result.size() == 0) {
				query = session
						.createQuery("SELECT ua.pk.assesment FROM UserAssesment ua WHERE ua.pk.user = '" + user + "'");
				listResult = query.list();
				if (listResult != null && listResult.size() > 0) {
					Iterator it = listResult.iterator();
					while (it.hasNext()) {
						result.add(((Assesment) it.next()).getAssesment());
					}
				}
			}
		} catch (Exception e) {
			handler.getException(e, "findUserAssesments", user);
		}
		return result;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name =
	 *                 "administrator,systemaccess,accesscode,pepsico_candidatos,basf_assessment,clientreporter,cepareporter"
	 */
	public Collection findTotalUserAssesments(String user, UserSessionData userSessionData) throws Exception {
		Collection result = new LinkedList();
		try {
			if (user == null) {
				throw new InvalidDataException("findUserAssesments", "user = null");
			}
			if (userSessionData == null) {
				throw new DeslogedException("findUserAssesments", "userSessionData = null");
			}
			Session session = HibernateAccess.currentSession();
			Query query = session.createQuery("SELECT ua FROM UserAssesment ua WHERE ua.pk.user = '" + user + "' ORDER BY ua.pk.assesment.start");
			List listResult = query.list();
			if (listResult != null && listResult.size() > 0) {
				Iterator it = listResult.iterator();
				while (it.hasNext()) {
					result.add(((UserAssesment) it.next()).getUserAssesmentData());
				}
			}
		} catch (Exception e) {
			handler.getException(e, "findTotalUserAssesments", user);
		}
		return result;
	}
	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name =
	 *                 "administrator,systemaccess,accesscode,pepsico_candidatos,basf_assessment,clientreporter,cepareporter"
	 */
	public Collection<AssessmentUserData> findWebinarParticipants(String code, String assesment, UserSessionData userSessionData) throws Exception {
		Session session = HibernateAccess.currentSession();
    	LinkedList<AssessmentUserData> list = new LinkedList<AssessmentUserData>();
		try {
			if (code == null) {
				throw new InvalidDataException("findWebinarParticipants", "code = null");
			}
			if (userSessionData == null) {
				throw new DeslogedException("findWebinarParticipants", "userSessionData = null");
			}
			
            SQLQuery query = session.createSQLQuery("SELECT firstname, lastname, u.loginname, u.email, u.extradata3, enddate IS NOT NULL AS finished, psitestid IS NOT NULL AS psi, sr.id, sr.sended, sr.certificate, ua.assesment, uar.correct, uar.incorrect " +
            		"FROM userassesments ua " +
            		"LEFT JOIN sendedreports sr ON sr.login = ua.loginname AND sr.assessment = ua.assesment " +
            		"LEFT JOIN userassesmentresults uar on uar.login = ua.loginname AND uar.assesment = ua.assesment " + 
            		"JOIN users u ON u.loginname = ua.loginname " +
            		"WHERE u.extradata3='"+code+"' and  ua.assesment = "+assesment);
            query.addScalar("firstname", Hibernate.STRING);
            query.addScalar("lastname", Hibernate.STRING);
            query.addScalar("loginname", Hibernate.STRING);
            query.addScalar("email", Hibernate.STRING);
            query.addScalar("extradata3", Hibernate.STRING);
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
            
            query = session.createSQLQuery("SELECT loginname, COUNT(*) AS c FROM useranswers WHERE assesment = "+assesment+" GROUP BY loginname").addScalar("loginname", Hibernate.STRING).addScalar("c", Hibernate.INTEGER);
            it = query.list().iterator();
            while(it.hasNext()) {
            	Object[] data = (Object[])it.next();
            	if(values.containsKey(data[0])) {
            		values.get(data[0]).setAnswers((Integer) data[1]);
            	}
            }
            
          /*  query = session.createSQLQuery("SELECT loginname, count(*) AS c FROM useranswers ua " +
            		"JOIN answerdata ad ON ad.id = ua.answer " +
            		"JOIN questions q ON ad.question = q.id " +
            		"JOIN answers a ON a.id = ad.answer " +
            		"WHERE assesment = "+assesment+" AND q.testtype = 1 AND a.type = 0 " +
            		"GROUP BY loginname").addScalar("loginname", Hibernate.STRING).addScalar("c", Hibernate.INTEGER);
            it = query.list().iterator();
            while(it.hasNext()) {
            	Object[] data = (Object[])it.next();
            	if(values.containsKey(data[0])) {
            		values.get(data[0]).setCorrect((Integer) data[1]);
            	}
            }*/

            list.addAll(values.values());
		} catch (Exception e) {
			handler.getException(e, "findWebinarParticipants", code);
		}
		return list;
	}
	
	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name =
	 *                 "administrator,systemaccess,accesscode,pepsico_candidatos,basf_assessment,clientreporter,cepareporter"
	 */
	public Collection findAllWebinarParticipants(String wbCode,String firstName,String lastName,UserSessionData userSessionData) throws Exception {
    	LinkedList<AssessmentUserData> list = new LinkedList<AssessmentUserData>();

		try {
			if (wbCode == null) {
				throw new InvalidDataException("findAllWebinarParticipants", "code = null");
			}
			if (userSessionData == null) {
				throw new DeslogedException("findAllWebinarParticipants", "userSessionData = null");
			}
			
			String extra = "AND u.extradata3 IS NOT NULL ";
			if(!Util.empty(wbCode)) {
				extra += "AND UPPER(u.extradata3) LIKE '%"+wbCode.toUpperCase()+"%' ";
			}
			if(!Util.empty(firstName)) {
				extra += "AND UPPER(u.firstname) LIKE '%"+firstName.toUpperCase()+"%' ";
			}
			if(!Util.empty(lastName)) {
				extra += "AND UPPER(u.lastName) LIKE '%"+lastName.toUpperCase()+"%' ";
			}
			Session session = HibernateAccess.currentSession();
			String sql="SELECT firstname, lastname, u.loginname, u.email, u.extradata3, ua.assesment,enddate IS NOT NULL AS finished, psitestid IS NOT NULL AS psi, sr.id, sr.sended, sr.certificate, uar.correct, uar.incorrect " + 
					            		"FROM userassesments ua " + 
					            		"LEFT JOIN sendedreports sr ON sr.login = ua.loginname AND sr.assessment = ua.assesment " + 
					            		"LEFT JOIN userassesmentresults uar on uar.login = ua.loginname AND uar.assesment = ua.assesment " +
					            		"JOIN users u ON u.loginname = ua.loginname " + 
					            		"WHERE ua.assesment in (1149, 1270, 1324, 1327, 1471) " + extra;
			sql+= " ORDER BY ua.enddate DESC";
			SQLQuery query = session.createSQLQuery(sql);
            query.addScalar("firstname", Hibernate.STRING);
            query.addScalar("lastname", Hibernate.STRING);
            query.addScalar("loginname", Hibernate.STRING);
            query.addScalar("email", Hibernate.STRING);
            query.addScalar("extradata3", Hibernate.STRING);
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
            
            /*sql = "SELECT ua.loginname, COUNT(*) AS c "
            		+ "FROM useranswers ua "
            		+ "JOIN users u ON u.loginname=ua.loginname "
            		+ "WHERE assesment in (1149, 1270, 1324, 1327, 1471) AND enddate IS NULL " 
            		+ "AND u.extradata3 IS NOT NULL " + extra +" GROUP BY ua.loginname";
            query = session.createSQLQuery(sql).addScalar("loginname", Hibernate.STRING).addScalar("c", Hibernate.INTEGER);
			if(wbCode.equals("")&&firstName.equals("")&&lastName.equals("")) {
	            query.setMaxResults(5);
			}

            it = query.list().iterator();
            while(it.hasNext()) {
            	Object[] data = (Object[])it.next();
            	if(values.containsKey(data[0])) {
            		values.get(data[0]).setAnswers((Integer) data[1]);
            	}
            }*/
            
            list.addAll(values.values());
			
			} catch (Exception e) {
				handler.getException(e, "findAllWebinarParticipants", wbCode);
			}
			return list;

	}


	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,systemaccess"
	 */
	public Collection<String[]> getUserAssesments(Integer driver, UserSessionData userSessionData) throws Exception {
		Collection<String[]> values = new LinkedList<String[]>();
		try {
			Session session = HibernateAccess.currentSession();
			String queryStr = "SELECT a.id,a.name as assesment,u.loginname,ua.enddate FROM assesments AS a "
					+ "JOIN userassesments ua ON ua.assesment = a.id " + "JOIN users u ON u.loginname = ua.loginname "
					+ "WHERE u.datacenter = " + driver;
			Query query = session.createSQLQuery(queryStr).addScalar("id", Hibernate.INTEGER)
					.addScalar("assesment", Hibernate.STRING).addScalar("loginname", Hibernate.STRING)
					.addScalar("enddate", Hibernate.DATE);
			Iterator it = query.list().iterator();
			while (it.hasNext()) {
				Object[] data = (Object[]) it.next();

				String sql2 = "SELECT COUNT(*) AS count FROM modules " + " WHERE assesment = " + String.valueOf(data[0])
						+ " AND id NOT IN (SELECT DISTINCT(q.module) AS module FROM useranswers ua "
						+ " JOIN answerdata AS ad ON ad.id = ua.answer " + " JOIN questions AS q ON q.id = ad.question "
						+ " WHERE ua.assesment = " + String.valueOf(data[0]) + " AND ua.loginname = '"
						+ String.valueOf(data[2]) + "')";
				SQLQuery query2 = session.createSQLQuery(sql2);
				query2.addScalar("count", Hibernate.INTEGER);
				boolean isComplete = ((Integer) query2.uniqueResult()).intValue() == 0;

				values.add(new String[] { String.valueOf(data[0]), (String) data[1], (String) data[2],
						Util.formatDate((Date) data[3]), String.valueOf(isComplete) });
			}
		} catch (Exception e) {
			handler.getException(e, "getUserAssesments", userSessionData.getFilter().getLoginName());
		}
		return values;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,clientreporter,cepareporter"
	 */
	public Collection findUserReportAssesments(String user, UserSessionData userSessionData) throws Exception {
		Collection result = new LinkedList();
		try {
			if (user == null) {
				throw new InvalidDataException("findUserReportAssesments", "user = null");
			}
			if (userSessionData == null) {
				throw new DeslogedException("findUserReportAssesments", "userSessionData = null");
			}
			Session session = HibernateAccess.currentSession();
			Query query = session
					.createQuery("SELECT ua.pk.assesment FROM ReporterAssesment ua WHERE ua.pk.user = '" + user + "'");
			List listResult = query.list();
			if (listResult != null && listResult.size() > 0) {
				Iterator it = listResult.iterator();
				while (it.hasNext()) {
					result.add(((Assesment) it.next()).getAssesment());
				}
			}
		} catch (Exception e) {
			handler.getException(e, "findUserReportAssesments", user);
		}
		return result;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,systemaccess"
	 */
	public Collection findActiveAssesments(String user, UserSessionData userSessionData) throws Exception {
		Session session = null;
		Collection result = new LinkedList();
		try {
			session = HibernateAccess.currentSession();

			String queryStr = "SELECT a.id,a.name as assesment FROM assesments AS a ";
			queryStr += "WHERE NOT a.archived AND a.status NOT IN (" + String.valueOf(AssesmentAttributes.ENDED) + ") ";
			if (!PersistenceUtil.empty(user)) {
				queryStr += "AND a.id NOT IN (SELECT ua.assesment FROM userassesments AS ua WHERE ua.loginname = '"
						+ user + "') ";
			}
			queryStr += "ORDER BY a.name";
			Query query = session.createSQLQuery(queryStr).addScalar("id", Hibernate.INTEGER).addScalar("assesment",
					Hibernate.STRING);

			List list = query.list();
			if (list != null && list.size() > 0) {
				Iterator iter = list.iterator();
				while (iter.hasNext()) {
					result.add((Object[]) iter.next());
				}
			}
		} catch (Exception e) {
			handler.getException(e, "findActiveAssesments", userSessionData.getFilter().getLoginName());
		}
		return result;
	}


	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,systemaccess"
	 */
	public Collection findGroups(UserSessionData userSessionData) throws Exception {
		Session session = null;
		Collection result = new LinkedList();
		try {
			session = HibernateAccess.currentSession();

			String queryStr = "SELECT g.id, g.name AS gName, c.name AS cName " + 
					"FROM groups g " + 
					"JOIN corporations c ON c.id = g.corporation " + 
					"ORDER BY g.name";
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
	 * @ejb.permission role-name = "administrator"
	 */
	public Collection<UserAnswerReportData> getUserAssesmentsCount(AssesmentAttributes assesment,
			UserSessionData userSessionData) throws Exception {
		Collection<UserAnswerReportData> result = new LinkedList<UserAnswerReportData>();
		try {
			Session session = HibernateAccess.currentSession();

			String queryStr = "SELECT u.firstname,u.lastname,u.loginname as login FROM userassesments ua "
					+ "JOIN users AS u ON ua.loginname = u.loginname " + "WHERE assesment = "
					+ String.valueOf(assesment.getId())
					+ " AND ua.loginname NOT IN (SELECT loginname FROM useranswers WHERE assesment = "
					+ String.valueOf(assesment.getId()) + ")";
			Query query = session.createSQLQuery(queryStr).addScalar("firstname", Hibernate.STRING)
					.addScalar("lastname", Hibernate.STRING).addScalar("login", Hibernate.STRING);
			Iterator it = query.list().iterator();
			while (it.hasNext()) {
				Object[] data = (Object[]) it.next();
				result.add(new UserAnswerReportData(data[0] + " " + data[1], (String) data[2], 0));
			}

			queryStr = "SELECT firstname,lastname,count(*) AS count, u.loginname as login,psiresult1 "
					+ "FROM userassesments ua " + "JOIN users AS u ON ua.loginname = u.loginname "
					+ "JOIN useranswers AS a on ua.loginname = a.loginname " + "WHERE ua.assesment = "
					+ String.valueOf(assesment.getId()) + " AND a.assesment = " + String.valueOf(assesment.getId())
					+ " GROUP BY firstname,lastname,login,psiresult1";
			query = session.createSQLQuery(queryStr).addScalar("firstname", Hibernate.STRING)
					.addScalar("lastname", Hibernate.STRING).addScalar("count", Hibernate.INTEGER)
					.addScalar("login", Hibernate.STRING).addScalar("psiresult1", Hibernate.INTEGER);
			it = query.list().iterator();
			while (it.hasNext()) {
				Object[] data = (Object[]) it.next();
				int count = ((Integer) data[2]).intValue();
				if (data[4] != null && assesment.isPsitest()) {
					count += 48;
				}
				result.add(new UserAnswerReportData(data[0] + " " + data[1], (String) data[3], count));
			}
		} catch (Exception e) {
			handler.getException(e, "getUserAssesmentsCount", userSessionData.getFilter().getLoginName());
		}
		return result;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "systemaccess,accesscode"
	 */
	public Integer getUserAssesmentsCount(Integer assesment, UserSessionData userSessionData) throws Exception {
		try {
			Session session = HibernateAccess.currentSession();

			Query query = session.createSQLQuery(
					"SELECT COUNT(*) AS count FROM userassesments WHERE assesment = " + String.valueOf(assesment))
					.addScalar("count", Hibernate.INTEGER);
			return (Integer) query.uniqueResult();
		} catch (Exception e) {
			handler.getException(e, "getUserAssesmentsCount", userSessionData.getFilter().getLoginName());
		}
		return 0;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "systemaccess,accesscode,administrator"
	 */
	public Integer getNextCode(String accesscode, UserSessionData userSessionData) throws Exception {
		try {
			Session session = HibernateAccess.currentSession();

			Query query = session.createSQLQuery("select used from accesscodes where code = '" + accesscode + "'")
					.addScalar("used", Hibernate.INTEGER);
			return (Integer) query.uniqueResult();
		} catch (Exception e) {
			handler.getException(e, "getNextCode", userSessionData.getFilter().getLoginName());
		}
		return 0;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,systemaccess"
	 */
	public Collection getUserModules(String user, Integer assesment, UserSessionData userSessionData, boolean feedback)
			throws Exception {
		Collection result = new LinkedList();
		try {
			Session session = HibernateAccess.currentSession();
			String queryStr = "SELECT DISTINCT(q.module) AS module FROM useranswers ua "
					+ "JOIN answerdata AS ad ON ad.id = ua.answer " + "JOIN questions AS q ON q.id = ad.question "
					+ "WHERE ua.assesment = " + String.valueOf(assesment) + " AND ua.loginname = '" + user + "'";
			Query query = session.createSQLQuery(queryStr).addScalar("module", Hibernate.INTEGER);
			Iterator it = query.list().iterator();
			while (it.hasNext()) {
				Integer data = (Integer) it.next();
				result.add(new int[] { data.intValue(), 0, 0 });
			}

			if (feedback && result.size() > 1) {
				queryStr = "SELECT q.module AS module,count(*) as count, a.type as type FROM useranswers ua JOIN answerdata AS ad ON ad.id = ua.answer "
						+ "JOIN questions AS q ON q.id = ad.question JOIN answers AS a ON a.id = ad.answer "
						+ "WHERE ua.assesment = " + String.valueOf(assesment) + " AND ua.loginname = '" + user + "' "
						+ " AND q.testtype = " + QuestionData.TEST_QUESTION + " GROUP BY module,a.type";
				query = session.createSQLQuery(queryStr).addScalar("module", Hibernate.INTEGER)
						.addScalar("count", Hibernate.INTEGER).addScalar("type", Hibernate.INTEGER);
				it = query.list().iterator();
				while (it.hasNext()) {
					Object[] data = (Object[]) it.next();
					if (((Integer) data[2]).intValue() == AnswerData.CORRECT) {
						Iterator it2 = result.iterator();
						boolean find = false;
						while (it2.hasNext() && !find) {
							int[] values = (int[]) it2.next();
							if (((Integer) data[0]).intValue() == values[0]) {
								values[1] += ((Integer) data[1]).intValue();
								values[2] += ((Integer) data[1]).intValue();
								find = true;
							}
						}
					} else {
						Iterator it2 = result.iterator();
						boolean find = false;
						while (it2.hasNext() && !find) {
							int[] values = (int[]) it2.next();
							if (((Integer) data[0]).intValue() == values[0]) {
								values[2] += ((Integer) data[1]).intValue();
								find = true;
							}
						}
					}
				}
			}

		} catch (Exception e) {
			handler.getException(e, "getUserModules", userSessionData.getFilter().getLoginName());
		}
		return result;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,systemaccess"
	 */
	public int[] getUserModule(String user, Integer module, UserSessionData userSessionData) throws Exception {
		int[] values = new int[] { 0, 0 };
		try {
			Session session = HibernateAccess.currentSession();

			String queryStr = "SELECT q.module AS module,count(*) as count, a.type as type "
					+ "FROM useranswers ua JOIN answerdata AS ad ON ad.id = ua.answer "
					+ "JOIN questions AS q ON q.id = ad.question " + "JOIN answers AS a ON a.id = ad.answer "
					+ "WHERE q.module = " + String.valueOf(module) + " AND ua.loginname = '" + user + "' "
					+ " AND q.testtype = " + QuestionData.TEST_QUESTION + " GROUP BY module,a.type";
			Query query = session.createSQLQuery(queryStr).addScalar("module", Hibernate.INTEGER)
					.addScalar("count", Hibernate.INTEGER).addScalar("type", Hibernate.INTEGER);
			Iterator it = query.list().iterator();
			while (it.hasNext()) {
				Object[] data = (Object[]) it.next();
				if (((Integer) data[2]).intValue() == AnswerData.CORRECT) {
					values[0] += ((Integer) data[1]).intValue();
				}
				values[1] += ((Integer) data[1]).intValue();
			}

		} catch (Exception e) {
			handler.getException(e, "getUserModule", userSessionData.getFilter().getLoginName());
		}
		return values;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator"
	 */
	public Collection findGeneralResults(Integer assesment, boolean psico, UserSessionData userSessionData)
			throws Exception {
		try {
			Session session = HibernateAccess.currentSession();
			String queryStr = "SELECT module,loginname,COUNT(*) AS count,m.key " + "FROM useranswers AS ua "
					+ "JOIN answerdata AS ad ON ad.id = ua.answer " + "JOIN questions AS q ON q.id = ad.question "
					+ "JOIN answers AS a ON a.id = ad.answer " + "JOIN modules AS m ON m.id = q.module "
					+ "WHERE loginname IN " + "(SELECT loginname FROM " + "(SELECT loginname,count(*) AS c "
					+ "FROM useranswers AS ua " + "GROUP BY loginname) complete " + "WHERE complete.c = "
					+ "(SELECT count(*) FROM questions AS q " + "JOIN modules AS m ON q.module = m.id "
					+ "WHERE m.assesment = " + String.valueOf(assesment) + ")) " + "AND q.testtype = "
					+ String.valueOf(QuestionData.TEST_QUESTION) + " AND ua.assesment = " + String.valueOf(assesment)
					+ " AND a.type = " + String.valueOf(AnswerData.CORRECT);
			if (psico) {
				queryStr += " AND loginname IN (SELECT loginname FROM userassesments WHERE assesment = "
						+ String.valueOf(assesment) + " AND psiresult1 IS NOT NULL) ";
			}
			queryStr += " GROUP BY module,m.key,loginname ORDER BY module,m.key,loginname";
			Query query = session.createSQLQuery(queryStr).addScalar("module", Hibernate.INTEGER)
					.addScalar("loginname", Hibernate.STRING).addScalar("count", Hibernate.INTEGER)
					.addScalar("key", Hibernate.STRING);
			return query.list();
		} catch (Exception e) {
			handler.getException(e, "findGeneralResults", userSessionData.getFilter().getLoginName());
		}
		return null;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator"
	 */
	public Collection findTotalResults(Integer assesment, boolean psico, UserSessionData userSessionData)
			throws Exception {
		try {
			Session session = HibernateAccess.currentSession();
			String queryStr = "SELECT loginname,COUNT(*) AS count " + "FROM useranswers AS ua "
					+ "JOIN answerdata AS ad ON ad.id = ua.answer " + "JOIN questions AS q ON q.id = ad.question "
					+ "JOIN answers AS a ON a.id = ad.answer " + "WHERE loginname IN " + "(SELECT loginname FROM "
					+ "(SELECT loginname,count(*) AS c " + "FROM useranswers AS ua " + "GROUP BY loginname) complete "
					+ "WHERE complete.c = " + "(SELECT count(*) FROM questions AS q "
					+ "JOIN modules AS m ON q.module = m.id " + "WHERE m.assesment = " + String.valueOf(assesment)
					+ ")) " + "AND q.testtype = " + String.valueOf(QuestionData.TEST_QUESTION) + " AND ua.assesment = "
					+ String.valueOf(assesment) + " AND a.type = " + String.valueOf(AnswerData.CORRECT);
			if (psico) {
				queryStr += " AND loginname IN (SELECT loginname FROM userassesments WHERE assesment = "
						+ String.valueOf(assesment) + " AND psiresult1 IS NOT NULL) ";
			}
			queryStr += "GROUP BY loginname ORDER BY loginname";
			Query query = session.createSQLQuery(queryStr).addScalar("loginname", Hibernate.STRING).addScalar("count",
					Hibernate.INTEGER);
			return query.list();
		} catch (Exception e) {
			handler.getException(e, "getUserModules", userSessionData.getFilter().getLoginName());
		}
		return null;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,systemaccess,clientreporter"
	 */
	public Collection findUserResults(Integer assesment, String user, UserSessionData userSessionData)
			throws Exception {
		try {
			Session session = HibernateAccess.currentSession();
			String queryStr = "SELECT module,m.key,a.type,count(*) AS count " + "FROM useranswers AS ua "
					+ "JOIN answerdata AS ad ON ua.answer = ad.id " + "JOIN questions AS q ON ad.question = q.id "
					+ "JOIN answers AS a ON ad.answer = a.id " + "JOIN modules AS m ON q.module = m.id "
					+ "WHERE ua.loginname = '" + user + "' " + "AND ua.assesment = " + String.valueOf(assesment) + " "
					+ "AND q.testtype = " + String.valueOf(QuestionData.TEST_QUESTION) + " "
					+ "GROUP BY module,m.key,a.type " + "ORDER BY module,m.key,a.type ";
			Query query = session.createSQLQuery(queryStr).addScalar("module", Hibernate.INTEGER)
					.addScalar("key", Hibernate.STRING).addScalar("type", Hibernate.INTEGER)
					.addScalar("count", Hibernate.INTEGER);
			return query.list();
		} catch (Exception e) {
			handler.getException(e, "getUserModules", userSessionData.getFilter().getLoginName());
		}
		return null;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator"
	 */
	public Hashtable getModulesCount(Integer assesment, UserSessionData userSessionData) throws Exception {
		Hashtable hash = new Hashtable();
		try {
			Session session = HibernateAccess.currentSession();
			String queryStr = "SELECT q.module,count(*) AS count "
					+ "FROM questions as q JOIN modules AS m ON q.module = m.id " + "WHERE m.assesment = "
					+ String.valueOf(assesment) + " GROUP BY q.module";
			Query query = session.createSQLQuery(queryStr).addScalar("module", Hibernate.INTEGER).addScalar("count",
					Hibernate.INTEGER);
			Iterator it = query.list().iterator();
			while (it.hasNext()) {
				Object[] data = (Object[]) it.next();
				hash.put(data[0], data[1]);
			}
		} catch (Exception e) {
			handler.getException(e, "getModulesCount", userSessionData.getFilter().getLoginName());
		}
		return hash;

	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator"
	 */
	public Collection findAssesmentUsers(String user, Integer assesment, UserSessionData userSessionData)
			throws Exception {
		Collection list = new LinkedList();
		try {
			Session session = HibernateAccess.currentSession();
			String queryStr = "SELECT u.loginname,firstname,lastname " + "FROM users AS u "
					+ "JOIN userassesments AS ua ON u.loginname = ua.loginname " + "WHERE ua.assesment = "
					+ String.valueOf(assesment) + " AND (lower(u.firstname) LIKE '%" + user.toLowerCase() + "%' "
					+ "OR lower(u.lastname) LIKE '%" + user.toLowerCase() + "%')";

			Query query = session.createSQLQuery(queryStr).addScalar("loginname", Hibernate.STRING)
					.addScalar("firstname", Hibernate.STRING).addScalar("lastname", Hibernate.STRING);
			return query.list();
		} catch (Exception e) {
			handler.getException(e, "findAssesmentUsers", userSessionData.getFilter().getLoginName());
		}
		return list;

	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator"
	 */
	public Collection findAssesmentPsiUsers(String user, Integer assesment, UserSessionData userSessionData)
			throws Exception {
		Collection list = new LinkedList();
		try {
			Session session = HibernateAccess.currentSession();
			String queryStr = "SELECT u.loginname,firstname,lastname " + "FROM users AS u "
					+ "JOIN userassesments AS ua ON u.loginname = ua.loginname " + "WHERE ua.assesment = "
					+ String.valueOf(assesment) + " AND (lower(u.firstname) LIKE '%" + user.toLowerCase() + "%' "
					+ "OR lower(u.lastname) LIKE '%" + user.toLowerCase() + "%') AND ua.psiresult1 is not null";

			Query query = session.createSQLQuery(queryStr).addScalar("loginname", Hibernate.STRING)
					.addScalar("firstname", Hibernate.STRING).addScalar("lastname", Hibernate.STRING);
			return query.list();
		} catch (Exception e) {
			handler.getException(e, "findAssesmentUsers", userSessionData.getFilter().getLoginName());
		}
		return list;

	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator"
	 */
	public Collection findAssesmentPDUsers(String user, Integer assesment, UserSessionData userSessionData)
			throws Exception {
		Collection list = new LinkedList();
		try {
			Session session = HibernateAccess.currentSession();
			String queryStr = "SELECT u.loginname,firstname,lastname " + "FROM users AS u "
					+ "JOIN userassesments AS ua ON u.loginname = ua.loginname " + "WHERE ua.assesment = "
					+ String.valueOf(assesment) + " AND (lower(u.firstname) LIKE '%" + user.toLowerCase() + "%' "
					+ "OR lower(u.lastname) LIKE '%" + user.toLowerCase()
					+ "%') AND u.loginname IN (SELECT loginname FROM useranswers WHERE assesment = "
					+ String.valueOf(assesment) + ")";

			Query query = session.createSQLQuery(queryStr).addScalar("loginname", Hibernate.STRING)
					.addScalar("firstname", Hibernate.STRING).addScalar("lastname", Hibernate.STRING);
			return query.list();
		} catch (Exception e) {
			handler.getException(e, "findAssesmentUsers", userSessionData.getFilter().getLoginName());
		}
		return list;

	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,systemaccess"
	 */
	public boolean isPsicologicDone(String user, Integer assesment, UserSessionData userSessionData) throws Exception {
		try {
			Session session = HibernateAccess.currentSession();
			String queryStr = "SELECT COUNT(*) AS count FROM userpsianswers " + "WHERE loginname = '" + user + "' "
					+ "AND assesment = " + String.valueOf(assesment);
			Query query = session.createSQLQuery(queryStr).addScalar("count", Hibernate.INTEGER);
			return (((Integer) query.uniqueResult()).intValue() >= 48);
		} catch (Exception e) {
			handler.getException(e, "isPsicologicDone", userSessionData.getFilter().getLoginName());
		}
		return false;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,systemaccess"
	 */
	public boolean isPersonalDataModuleDone(String user, Integer assesment, UserSessionData userSessionData)
			throws Exception {
		try {
			Session session = HibernateAccess.currentSession();
			String queryStr = "SELECT COUNT(*) AS count FROM useranswers " + "WHERE loginname = '" + user + "' "
					+ "AND assesment = " + String.valueOf(assesment);
			Query query = session.createSQLQuery(queryStr).addScalar("count", Hibernate.INTEGER);
			return (((Integer) query.uniqueResult()).intValue() > 0);
		} catch (Exception e) {
			handler.getException(e, "isPsicologicDone", userSessionData.getFilter().getLoginName());
		}
		return false;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,systemaccess"
	 */
	public Collection getPsicoResult(Integer assesment, UserSessionData userSessionData) throws Exception {
		try {
			Session session = HibernateAccess.currentSession();
			String sql = "SELECT loginname,psiresult1,psiresult2,psiresult3,"
					+ "psiresult4,psiresult5,psiresult6 FROM userassesments " + "WHERE assesment = "
					+ String.valueOf(assesment) + " AND psiresult1 IS NOT NULL " + "AND loginname IN "
					+ "(SELECT loginname FROM " + "(SELECT loginname,count(*) AS c " + "FROM useranswers AS ua "
					+ "GROUP BY loginname) complete " + "WHERE complete.c = " + "(SELECT count(*) FROM questions AS q "
					+ "JOIN modules AS m ON q.module = m.id " + "WHERE m.assesment = " + String.valueOf(assesment)
					+ "))";
			Query query = session.createSQLQuery(sql).addScalar("loginname", Hibernate.STRING)
					.addScalar("psiresult1", Hibernate.INTEGER).addScalar("psiresult2", Hibernate.INTEGER)
					.addScalar("psiresult3", Hibernate.INTEGER).addScalar("psiresult4", Hibernate.INTEGER)
					.addScalar("psiresult5", Hibernate.INTEGER).addScalar("psiresult6", Hibernate.INTEGER);
			return query.list();
		} catch (Exception e) {
			handler.getException(e, "getPsicoResult", userSessionData.getFilter().getLoginName());
		}
		return null;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,systemaccess"
	 */
	public Integer[] getUserPsicoResult(String user, Integer assesment, UserSessionData userSessionData)
			throws Exception {
		Integer[] values = new Integer[6];
		try {
			Session session = HibernateAccess.currentSession();
			String sql = "SELECT psiresult1,psiresult2,psiresult3,"
					+ "psiresult4,psiresult5,psiresult6 FROM userassesments " + "WHERE assesment = "
					+ String.valueOf(assesment) + " AND loginname = '" + user + "' " + "AND psiresult1 IS NOT NULL";
			Query query = session.createSQLQuery(sql).addScalar("psiresult1", Hibernate.INTEGER)
					.addScalar("psiresult2", Hibernate.INTEGER).addScalar("psiresult3", Hibernate.INTEGER)
					.addScalar("psiresult4", Hibernate.INTEGER).addScalar("psiresult5", Hibernate.INTEGER)
					.addScalar("psiresult6", Hibernate.INTEGER);
			Object[] data = (Object[]) query.uniqueResult();
			for (int i = 0; i < 6; i++) {
				values[i] = (Integer) data[i];
			}
		} catch (Exception e) {
			handler.getException(e, "getPsicoResult", userSessionData.getFilter().getLoginName());
		}
		return values;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator"
	 */
	public Collection getCorporationsDC(UserSessionData userSessionData) throws Exception {
		Class.forName("org.postgresql.Driver");
		Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/respaldo", "postgres",
				"postgres");
		Statement st = conn.createStatement();

		Collection<String[]> list = new LinkedList<String[]>();
		ResultSet set = st.executeQuery(
				"SELECT d.corporation,c.name,COUNT(*) AS count FROM drivers d JOIN corporations c ON c.id = d.corporation GROUP BY d.corporation,c.name");
		while (set.next()) {
			String[] data = { set.getString("corporation"), set.getString("name"), set.getString("count") };
			list.add(data);
		}

		conn.close();
		return list;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator"
	 */
	public Collection getDriversDC(Integer corporation, UserSessionData userSessionData) throws Exception {
		Class.forName("org.postgresql.Driver");
		Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/respaldo", "postgres",
				"postgres");
		Statement st = conn.createStatement();

		Collection<UserData> list = new LinkedList<UserData>();
		ResultSet set = st.executeQuery(
				"SELECT d.id,d.firstname,d.lastname,d.email,d.birthdate,d.sex,d.country,d.nationality,d.inclusiondate,d.licenseexpiry,v.matricula "
						+ " FROM drivers AS d LEFT JOIN vehicles AS v ON v.driver = d.id WHERE d.corporation = "
						+ String.valueOf(corporation));
		Collection users = new LinkedList();
		while (set.next()) {
			String id = set.getString("id");
			if (!users.contains(id)) {
				users.add(id);
				UserData data = new UserData();
				data.setLoginName("test" + id);
				data.setFirstName(set.getString("firstname"));
				data.setLastName(set.getString("lastname"));
				data.setEmail(set.getString("email"));
				data.setBirthDate(set.getDate("birthdate"));
				if (set.getString("sex").equals("datatype.sex.male")) {
					data.setSex(new Integer(UserData.MALE));
				} else {
					data.setSex(new Integer(UserData.FEMALE));
				}
				data.setCountry(new Integer(set.getInt("country")));
				data.setNationality(data.getCountry());
				data.setStartDate(set.getDate("inclusiondate"));
				data.setLicenseExpiry(set.getDate("licenseexpiry"));
				data.setVehicle(set.getString("matricula"));
				list.add(data);
			}
		}

		conn.close();
		return list;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,systemaccess"
	 */
	public Collection findModuleResult(String user, Integer module, UserSessionData userSessionData) throws Exception {
		Collection result = new LinkedList();
		try {
			if (userSessionData == null) {
				throw new DeslogedException("findModuleResult", "session is closed");
			}
			if (user == null) {
				throw new InvalidDataException("findModuleResult", "user is null");
			}
			if (module == null) {
				throw new InvalidDataException("findModuleResult", "module is null");
			}
			Session session = HibernateAccess.currentSession();
			Query q = session
					.createSQLQuery("SELECT ad.question,ad.answer,ad.text,ad.date,ad.unit,ad.country,m.answer as multi "
							+ "FROM answerdata ad " + "JOIN questions q ON q.id = ad.question "
							+ "LEFT JOIN multioptions m ON m.id = ad.id " + "WHERE q.module =  "
							+ String.valueOf(module)
							+ " AND ad.id IN (SELECT answer FROM useranswers WHERE loginname = '" + user + "')")
					.addScalar("question", Hibernate.INTEGER).addScalar("answer", Hibernate.INTEGER)
					.addScalar("text", Hibernate.STRING).addScalar("date", Hibernate.DATE)
					.addScalar("unit", Hibernate.INTEGER).addScalar("country", Hibernate.INTEGER)
					.addScalar("multi", Hibernate.INTEGER);
			List list = q.list();
			if (list != null && list.size() > 0) {
				Iterator it = list.iterator();
				while (it.hasNext()) {
					Object[] data = (Object[]) it.next();
					UserAnswerData answerData = new UserAnswerData((Integer) data[0], (Integer) data[1]);
					if (data[2] != null) {
						answerData.setText((String) data[2]);
					}
					if (data[3] != null) {
						answerData.setDate((Date) data[3]);
					}
					if (data[4] != null) {
						answerData.setUnit((Integer) data[4]);
					}
					if (data[5] != null) {
						answerData.setCountry((Integer) data[5]);
					}
					if (data[6] != null) {
						Iterator itResult = result.iterator();
						boolean find = false;
						while (itResult.hasNext() && !find) {
							UserAnswerData resultAnswerData = (UserAnswerData) itResult.next();
							if (resultAnswerData.getQuestion().equals(answerData.getQuestion())) {
								find = true;
								resultAnswerData.getOptions().add((Integer) data[6]);
							}
						}
						if (!find) {
							Collection<Integer> answers = new LinkedList<Integer>();
							answers.add((Integer) data[6]);
							answerData.setOptions(answers);
							result.add(answerData);
						}
					} else {
						result.add(answerData);
					}
				}
			}
		} catch (Exception e) {
			handler.getException(e, "findModuleResult", userSessionData.getFilter().getLoginName());
		}
		return result;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,systemaccess"
	 */
	public Collection findPsicoModuleResult(String user, Integer assesment, UserSessionData userSessionData)
			throws Exception {
		Collection result = new LinkedList();
		try {
			if (userSessionData == null) {
				throw new DeslogedException("findPsicoModuleResult", "session is closed");
			}
			if (user == null) {
				throw new InvalidDataException("findPsicoModuleResult", "user is null");
			}
			if (assesment == null) {
				throw new InvalidDataException("findPsicoModuleResult", "module is null");
			}
			Session session = HibernateAccess.currentSession();
			Query q = session
					.createSQLQuery("SELECT pa.psiquestion,pa.psianswer FROM userpsianswers upa "
							+ "join psianswerdata pa on pa.id = upa.psianswer " + "where assesment = " + assesment + " "
							+ "and loginname = '" + user + "'")
					.addScalar("psiquestion", Hibernate.INTEGER).addScalar("psianswer", Hibernate.INTEGER);
			List list = q.list();
			if (list != null && list.size() > 0) {
				Iterator it = list.iterator();
				while (it.hasNext()) {
					Object[] data = (Object[]) it.next();
					UserAnswerData answerData = new UserAnswerData((Integer) data[0], (Integer) data[1]);
					result.add(answerData);
				}
			}
		} catch (Exception e) {
			handler.getException(e, "findPsicoModuleResult", userSessionData.getFilter().getLoginName());
		}
		return result;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,systemaccess"
	 */
	public boolean isAssessmentDone(String user, Integer assesment, UserSessionData userSessionData) throws Exception {
		try {
			Session session = HibernateAccess.currentSession();
			String queryStr = "SELECT COUNT(*) FROM questions q " + "JOIN modules m ON m.id = q.module "
					+ "WHERE m.assesment = " + String.valueOf(assesment)
					+ " AND q.id NOT IN (SELECT DISTINCT(ad.question) AS module " + "FROM useranswers ua "
					+ "JOIN answerdata AS ad ON ad.id = ua.answer " + "WHERE ua.assesment = "
					+ String.valueOf(assesment) + " AND ua.loginname = '" + user + "')";

			Query query = session.createSQLQuery(queryStr).addScalar("count", Hibernate.INTEGER);
			return (((Integer) query.uniqueResult()).intValue() == 0);
		} catch (Exception e) {
			handler.getException(e, "isAssessmentDone", userSessionData.getFilter().getLoginName());
		}
		return false;
	}


	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,systemaccess"
	 */
	public Date isAssessmentDone(UserSessionData userSessionData) throws Exception {
		try {
			Session session = HibernateAccess.currentSession();
			String queryStr = "SELECT COUNT(*) FROM questions q "
					+ "WHERE module IN (SELECT id FROM modules WHERE assesment = "+userSessionData.getFilter().getAssesment()+") "
					+ "AND id NOT IN (SELECT question FROM specificanswers WHERE multianswer = "+userSessionData.getFilter().getMulti()+")";

			Query query = session.createSQLQuery(queryStr).addScalar("count", Hibernate.INTEGER);
			if(((Integer) query.uniqueResult()).intValue() == 0) {
				UserMultiAssessment userMA = (UserMultiAssessment) session.load(UserMultiAssessment.class, userSessionData.getFilter().getMulti());
				Date end = Calendar.getInstance().getTime();
				userMA.setEndDate(end);
				session.update(userMA);
				return end;
			}else {
				return null;
			}
		} catch (Exception e) {
			handler.getException(e, "isAssessmentDone", userSessionData.getFilter().getLoginName());
		}
		return null;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,systemaccess"
	 */
	public boolean hasResults(String user, Integer module, UserSessionData userSessionData) throws Exception {
		try {
			Session session = HibernateAccess.currentSession();
			String sql = "SELECT COUNT(*) AS count FROM useranswers ua " + "JOIN answerdata ad ON ua.answer = ad.id "
					+ "JOIN questions q ON ad.question = q.id " + "WHERE loginname = '" + user + "' AND q.module = "
					+ String.valueOf(module);
			Query query = session.createSQLQuery(sql).addScalar("count", Hibernate.INTEGER);
			return (((Integer) query.uniqueResult()).intValue() > 0);
		} catch (Exception e) {
			handler.getException(e, "hasResults", userSessionData.getFilter().getLoginName());
		}
		return false;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,systemaccess"
	 */
	public Integer getUserCode(String user, UserSessionData userSessionData) throws Exception {
		try {
			Session session = HibernateAccess.currentSession();
			Query query = session.createSQLQuery("SELECT code FROM usercodes WHERE loginname = '" + user + "'")
					.addScalar("code", Hibernate.INTEGER);
			List list = query.list();
			if (list.size() > 0) {
				return (Integer) list.iterator().next();
			}
		} catch (Exception e) {
			handler.getException(e, "getUserCode", userSessionData.getFilter().getLoginName());
		}
		return null;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,systemaccess"
	 */
	public Integer[] getResultCount(String user, Integer assessment, UserSessionData userSessionData) throws Exception {
		Integer[] values = { new Integer(0), new Integer(0) };
		try {
			Session session = HibernateAccess.currentSession();
			String sql = "SELECT a.type,COUNT(*) FROM useranswers ua " + "JOIN answerdata ad ON ua.answer = ad.id "
					+ "JOIN questions q ON ad.question = q.id " + "JOIN answers a ON ad.answer = a.id "
					+ "WHERE ua.loginname = '" + user + "' " + "AND ua.assesment = " + String.valueOf(assessment)
					+ " AND q.testtype = " + String.valueOf(QuestionData.TEST_QUESTION) + " GROUP BY a.type";
			Query query = session.createSQLQuery(sql).addScalar("type", Hibernate.INTEGER).addScalar("count",
					Hibernate.INTEGER);
			Iterator it = query.list().iterator();
			while (it.hasNext()) {
				Object[] data = (Object[]) it.next();
				values[((Integer) data[0]).intValue()] = (Integer) data[1];
			}
		} catch (Exception e) {
			handler.getException(e, "getResultCount", userSessionData.getFilter().getLoginName());
		}
		return values;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,systemaccess"
	 */
	public Integer[] getCompletedResultCount(String user, Integer assessment, UserSessionData userSessionData) throws Exception {
		Integer[] values = { new Integer(0), new Integer(0) };
		try {
			Session session = HibernateAccess.currentSession();
			String sql = "SELECT a.type,COUNT(*) FROM useranswers ua "  
					+ "JOIN userassesments uas ON uas.loginname = ua.loginname AND uas.assesment = ua.assesment "
					+ "JOIN answerdata ad ON ua.answer = ad.id "
					+ "JOIN questions q ON ad.question = q.id " + "JOIN answers a ON ad.answer = a.id "
					+ "WHERE uas.enddate IS NOT NULL AND ua.loginname = '" + user + "' " + "AND ua.assesment = " + String.valueOf(assessment)
					+ " AND q.testtype = " + String.valueOf(QuestionData.TEST_QUESTION) + " GROUP BY a.type";
			Query query = session.createSQLQuery(sql).addScalar("type", Hibernate.INTEGER).addScalar("count",
					Hibernate.INTEGER);
			Iterator it = query.list().iterator();
			while (it.hasNext()) {
				Object[] data = (Object[]) it.next();
				values[((Integer) data[0]).intValue()] = (Integer) data[1];
			}
		} catch (Exception e) {
			handler.getException(e, "getResultCount", userSessionData.getFilter().getLoginName());
		}
		return values;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,systemaccess"
	 */
	public Collection getChampionResult(Integer questionCount, String countries, Integer company,
			UserSessionData userSessionData) throws Exception {
		Collection list = new LinkedList();
		try {
			Session session = HibernateAccess.currentSession();
			String sql = "SELECT ua.loginname,u.firstname,u.lastname,u.country,u.extradata,uas.psiresult1,uas.psiresult2,uas.psiresult3,"
					+ "uas.psiresult4,uas.psiresult5,uas.psiresult6,uc.code,COUNT(*) " + "FROM useranswers ua "
					+ "JOIN answerdata ad ON ua.answer = ad.id " + "JOIN questions q ON ad.question = q.id "
					+ "JOIN answers a ON ad.answer = a.id " + "JOIN usercodes uc ON uc.loginname = ua.loginname "
					+ "JOIN userassesments uas ON uas.loginname = ua.loginname "
					+ "JOIN users u ON u.loginname = ua.loginname " + " AND ua.assesment = "
					+ String.valueOf(AssesmentData.JJ) + " AND q.testtype = "
					+ String.valueOf(QuestionData.TEST_QUESTION) + " AND a.type = " + String.valueOf(AnswerData.CORRECT)
					+ " AND uas.psiresult1 IS NOT null "
					+ " AND ua.loginname IN (SELECT loginname FROM useranswers WHERE assesment = "
					+ String.valueOf(AssesmentData.JJ) + " GROUP BY loginname HAVING COUNT(*) >= "
					+ String.valueOf(questionCount) + ") ";
			if (countries != null && countries.length() > 0) {
				sql += " AND u.country IN (" + countries + ") ";
			}
			if (company != null) {
				sql += " AND u.extradata = '" + String.valueOf(company) + "' ";
			}
			sql += " GROUP BY ua.loginname,u.firstname,u.lastname,u.country,u.extradata,uas.psiresult1,uas.psiresult2,uas.psiresult3,"
					+ "uas.psiresult4,uas.psiresult5,uas.psiresult6,uc.code " + "ORDER BY ua.loginname";
			SQLQuery query = session.createSQLQuery(sql);
			query.addScalar("loginname", Hibernate.STRING);
			query.addScalar("firstname", Hibernate.STRING);
			query.addScalar("lastname", Hibernate.STRING);
			query.addScalar("country", Hibernate.INTEGER);
			query.addScalar("extradata", Hibernate.STRING);
			for (int i = 1; i <= 6; i++) {
				query.addScalar("psiresult" + i, Hibernate.INTEGER);
			}
			query.addScalar("code", Hibernate.INTEGER);
			query.addScalar("count", Hibernate.INTEGER);
			list = query.list();
		} catch (Exception e) {
			handler.getException(e, "getResultCount", userSessionData.getFilter().getLoginName());
		}
		return list;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,systemaccess"
	 */
	public String getRedirect(String user, Integer assesment, UserSessionData userSessionData) throws Exception {
		try {
			Session session = HibernateAccess.currentSession();
			Query q = session.createSQLQuery(

					"SELECT elearningredirect " + "FROM userassesments ua "
							+ "INNER JOIN assesments a ON ua.assesment = a.id " + "WHERE loginname = '" + user
							+ "' and ua.assesment = " + assesment + " and a.status = 1 " + "LIMIT 1 ")
					.addScalar("elearningredirect", Hibernate.STRING);

			List l = q.list();
			if (l.size() > 0) {
				return (String) l.get(0);
			}
		} catch (Exception e) {
			handler.getException(e, "getResultCount", userSessionData.getFilter().getLoginName());
		}
		return null;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,systemaccess,accesscode"
	 */
	public Collection getAccessCode(int assesment, UserSessionData userSessionData) throws Exception {
		try {
			Session session = HibernateAccess.currentSession();
			Query q = session.createSQLQuery("select code from accesscodes where assesment = " + assesment)
					.addScalar("code", Hibernate.STRING);
			return q.list();
		} catch (Exception e) {
			handler.getException(e, "getAccessCode", userSessionData.getFilter().getLoginName());
		}
		return null;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,systemaccess,accesscode"
	 */
	public Collection getAssessmentUsers(Integer assesment, UserSessionData userSessionData) throws Exception {
		Collection result = new LinkedList();
		try {
			Session session = HibernateAccess.currentSession();

			String queryStr = "SELECT u.loginname,firstname,lastname,language,email,brithdate,sex,country "
					+ "FROM users AS u " + "JOIN userassesments AS ua ON u.loginname = ua.loginname "
					+ "WHERE ua.assesment = " + String.valueOf(assesment);
			Query query = session.createSQLQuery(queryStr).addScalar("loginname", Hibernate.STRING)
					.addScalar("firstname", Hibernate.STRING).addScalar("lastname", Hibernate.STRING)
					.addScalar("language", Hibernate.STRING).addScalar("email", Hibernate.STRING)
					.addScalar("brithdate", Hibernate.DATE).addScalar("sex", Hibernate.INTEGER)
					.addScalar("country", Hibernate.INTEGER);
			Iterator it = query.list().iterator();
			while (it.hasNext()) {
				Object[] data = (Object[]) it.next();
				result.add(new UserData((String) data[0], "", (String) data[1], (String) data[2], (String) data[3],
						(String) data[4], (Date) data[5], (Integer) data[6], (Integer) data[7], null, null, null, null,
						null, null, null));
			}
		} catch (Exception e) {
			handler.getException(e, "getAssessmentUsers", userSessionData.getFilter().getLoginName());
		}
		return result;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,systemaccess,accesscode"
	 */
	public String getElearningURL(String user, Integer assesment, UserSessionData userSessionData) throws Exception {
		try {
			Session session = HibernateAccess.currentSession();

			String queryStr = "SELECT elearningredirect FROM userassesments " + "WHERE loginname = '" + user + "' "
					+ "AND assesment = " + String.valueOf(assesment);
			Query query = session.createSQLQuery(queryStr).addScalar("elearningredirect", Hibernate.STRING);
			Iterator it = query.list().iterator();
			if (it.hasNext()) {
				return (String) it.next();
			}
		} catch (Exception e) {
			handler.getException(e, "getElearningURL", userSessionData.getFilter().getLoginName());
		}
		return null;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name =
	 *                 "administrator,pepsico_candidatos,basf_assessment"
	 */
	public Collection getCandidatos(UserSessionData userSessionData) throws Exception {
		try {
			Session session = HibernateAccess.currentSession();
			String sqlData = "SELECT u.loginname,u.firstname,u.lastname,u.extraData2,u.extradata3,"
					+ " ua.psiresult1,COUNT(*) AS count " + " FROM users u "
					+ " JOIN userassesments ua ON ua.loginname = u.loginname"
					+ " JOIN useranswers uan ON uan.loginname = u.loginname "
					+ " JOIN answerdata ad ON ad.id = uan.answer " + " JOIN questions q ON q.id = ad.question "
					+ " WHERE ua.assesment = " + AssesmentData.PEPSICO_CANDIDATOS + " AND uan.assesment = "
					+ AssesmentData.PEPSICO_CANDIDATOS + " GROUP BY u.loginname,u.firstname,u.lastname,u.extraData2,"
					+ " u.extradata3,ua.psiresult1 " + " UNION ("
					+ " SELECT DISTINCT u.loginname,u.firstname,u.lastname,u.extraData2,u.extradata3,"
					+ " ua.psiresult1,0" + " FROM users u  " + " JOIN userassesments ua ON ua.loginname = u.loginname"
					+ " WHERE ua.assesment = " + AssesmentData.PEPSICO_CANDIDATOS
					+ " AND u.loginname NOT IN (SELECT DISTINCT uan.loginname" + " FROM useranswers uan  "
					+ " JOIN answerdata ad ON uan.answer = ad.id" + " JOIN questions q ON q.id = ad.question  "
					+ " WHERE uan.assesment = " + AssesmentData.PEPSICO_CANDIDATOS + ")) "
					+ " ORDER BY firstname,lastname ";
			Query query = session.createSQLQuery(sqlData).addScalar("loginname", Hibernate.STRING)
					.addScalar("firstname", Hibernate.STRING).addScalar("lastname", Hibernate.STRING)
					.addScalar("extraData2", Hibernate.STRING).addScalar("extradata3", Hibernate.STRING)
					.addScalar("psiresult1", Hibernate.INTEGER).addScalar("count", Hibernate.INTEGER);
			return query.list();
		} catch (Exception e) {
			handler.getException(e, "getCandidatos", userSessionData.getFilter().getLoginName());
		}
		return null;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name =
	 *                 "administrator,pepsico_candidatos,basf_assessment"
	 */
	public HashMap<String, Calendar> getLastAccess(Integer assessment, UserSessionData userSessionData)
			throws Exception {
		HashMap<String, Calendar> map = new HashMap<String, Calendar>();
		try {
			Session session = HibernateAccess.currentSession();
			String sql = "SELECT loginname,MAX(logindate) AS lastdate FROM userslogins WHERE loginname IN (SELECT loginname FROM userassesments WHERE assesment = "
					+ assessment + ") GROUP BY loginname";
			Query query = session.createSQLQuery(sql).addScalar("loginname", Hibernate.STRING).addScalar("lastdate",
					Hibernate.STRING);
			Iterator it = query.list().iterator();
			while (it.hasNext()) {
				Object[] data = (Object[]) it.next();
				Calendar c = Calendar.getInstance();
				c.setTimeInMillis(Long.parseLong((String) data[1]));
				map.put((String) data[0], c);
			}
		} catch (Exception e) {
			handler.getException(e, "getLastAccess", userSessionData.getFilter().getLoginName());
		}
		return map;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,basf_assessment"
	 */
	public boolean existUserAssessment(String login, Integer assessment, UserSessionData userSessionData)
			throws Exception {
		try {
			Session session = HibernateAccess.currentSession();
			String sql = "SELECT COUNT(*) AS c FROM userassesments WHERE loginname = '" + login + "' AND assesment = "
					+ assessment;
			return ((Integer) session.createSQLQuery(sql).addScalar("c", Hibernate.INTEGER).uniqueResult()) > 0;
		} catch (Exception e) {
			handler.getException(e, "existUserAssessment", userSessionData.getFilter().getLoginName());
		}
		return false;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,systemaccess"
	 */
	public Integer getQuestionCount(String user, Integer module, Integer assesment, UserSessionData userSessionData)
			throws Exception {
		try {
			String sql = null;
			if (module.intValue() != 0) {
				sql = "select count(*) AS c from useranswers ua " + "join answerdata ad on ua.answer = ad.id "
						+ "where loginname = '" + user + "' "
						+ "and ad.question in (select id from questions where module = " + module + ")";
			} else {
				sql = "select count(*) AS c from userpsianswers " + "where loginname = '" + user + "' "
						+ "and assesment = " + assesment;
			}
			Session session = HibernateAccess.currentSession();
			return (Integer) session.createSQLQuery(sql).addScalar("c", Hibernate.INTEGER).uniqueResult();
		} catch (Exception e) {
			handler.getException(e, "existUserAssessment", userSessionData.getFilter().getLoginName());
		}
		return new Integer(0);
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,clientreporter,cepareporter,systemaccess"
	 */
	public Collection getUsersReport(Integer assessment, UserSessionData userSessionData) throws Exception {
		try {
			String filter = "";
			if(userSessionData.getFilter().getLoginName().startsWith("charla")) {
				filter = " AND u.loginname IN (SELECT loginname FROM usersaccess WHERE passworddate > NOW() - INTERVAL '2 hour' AND loginname LIKE 'generate_es_%_"+assessment+"') ";
			}
			if(assessment.intValue() == AssesmentData.TIMAC_BRASIL || assessment.intValue() == AssesmentData.TIMAC_BRASIL_EBTWPLUS) {
				filter = " AND u.firstname != 'DESLIGADO' ";
			}
			String sql = "select u.loginname,u.firstname,u.lastname,u.email,ua.enddate,q.module,a.type,count(*) AS c,ua.psiresult1,ua.psiresult2,ua.psiresult3,ua.psiresult4,ua.psiresult5,ua.psiresult6 "
					+ "from userassesments ua " + "join users u on u.loginname = ua.loginname "
					+ "join useranswers uas on uas.loginname = ua.loginname and uas.assesment = ua.assesment "  
					+ "join answerdata ad on ad.id = uas.answer " + "join questions q on ad.question = q.id "
					+ "join answers a on ad.answer = a.id " + "where ua.assesment = " + assessment + filter
					+ " and q.testtype = " + QuestionData.TEST_QUESTION + " "
					+ "group by u.loginname,u.firstname,u.lastname,u.email,ua.enddate,q.module,a.type,ua.psiresult1,ua.psiresult2,ua.psiresult3,ua.psiresult4,ua.psiresult5,ua.psiresult6 "
					+ "UNION (select distinct u.loginname,u.firstname,u.lastname,u.email,ua.enddate,q.module,0 AS type,0,ua.psiresult1,ua.psiresult2,ua.psiresult3,ua.psiresult4,ua.psiresult5,ua.psiresult6 "
					+ "from users u " + "join userassesments ua on u.loginname = ua.loginname "
					+ "join useranswers uas on u.loginname = uas.loginname and uas.assesment = ua.assesment  "
					+ "join answerdata ad on ad.id = uas.answer " + "join questions q on ad.question = q.id "
					+ "where ua.assesment = " + assessment + filter
					+ " and u.loginname not in (select loginname from useranswers ua join answerdata ad on ad.id = ua.answer join questions q on q.id = ad.question where ua.assesment = "
					+ assessment + " and q.testtype = " + QuestionData.TEST_QUESTION + ")) "
					+ "order by loginname,type";
			Session session = HibernateAccess.currentSession();
			SQLQuery question = session.createSQLQuery(sql);
			question.addScalar("loginname", Hibernate.STRING);
			question.addScalar("firstname", Hibernate.STRING);
			question.addScalar("lastname", Hibernate.STRING);
			question.addScalar("email", Hibernate.STRING);
			question.addScalar("enddate", Hibernate.DATE);
			question.addScalar("module", Hibernate.INTEGER);
			question.addScalar("type", Hibernate.INTEGER);
			question.addScalar("c", Hibernate.INTEGER);
			question.addScalar("psiresult1", Hibernate.INTEGER);
			question.addScalar("psiresult2", Hibernate.INTEGER);
			question.addScalar("psiresult3", Hibernate.INTEGER);
			question.addScalar("psiresult4", Hibernate.INTEGER);
			question.addScalar("psiresult5", Hibernate.INTEGER);
			question.addScalar("psiresult6", Hibernate.INTEGER);
			return question.list();
		} catch (Exception e) {
			handler.getException(e, "getUsersReport", userSessionData.getFilter().getLoginName());
		}
		return new LinkedList();
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,clientreporter,cepareporter,systemaccess"
	 */
	public Collection getUsersReport(Integer assessment, Integer group, UserSessionData userSessionData) throws Exception {
		try {
			String filter = "";
			if(userSessionData.getFilter().getLoginName().startsWith("charla")) {
				filter = " AND u.loginname IN (SELECT loginname FROM usersaccess WHERE passworddate > NOW() - INTERVAL '2 hour' AND loginname LIKE 'generate_es_%_"+assessment+"') ";
			}
			if(assessment.intValue() == AssesmentData.TIMAC_BRASIL || assessment.intValue() == AssesmentData.TIMAC_BRASIL_EBTWPLUS) {
				filter = " AND u.firstname != 'DESLIGADO' ";
			}
			String sql = "select u.loginname,u.firstname,u.lastname,u.email,ua.enddate,q.module,a.type,count(*) AS c,ua.psiresult1,ua.psiresult2,ua.psiresult3,ua.psiresult4,ua.psiresult5,ua.psiresult6 "
					+ "from userassesments ua " + "join users u on u.loginname = ua.loginname "
					+ "join useranswers uas on uas.loginname = ua.loginname and uas.assesment = ua.assesment "  
					+ "join answerdata ad on ad.id = uas.answer " + "join questions q on ad.question = q.id "
					+ "join answers a on ad.answer = a.id " + "where ua.assesment = " + assessment + filter
					+ " and q.testtype = " + QuestionData.TEST_QUESTION + " "
					+ "and u.loginname in (select loginname from usergroups where groupid = " + group + ") "
					+ "group by u.loginname,u.firstname,u.lastname,u.email,ua.enddate,q.module,a.type,ua.psiresult1,ua.psiresult2,ua.psiresult3,ua.psiresult4,ua.psiresult5,ua.psiresult6 "
					+ "UNION (select distinct u.loginname,u.firstname,u.lastname,u.email,ua.enddate,q.module,0 AS type,0,ua.psiresult1,ua.psiresult2,ua.psiresult3,ua.psiresult4,ua.psiresult5,ua.psiresult6 "
					+ "from users u " + "join userassesments ua on u.loginname = ua.loginname "
					+ "join useranswers uas on u.loginname = uas.loginname and uas.assesment = ua.assesment  "
					+ "join answerdata ad on ad.id = uas.answer " + "join questions q on ad.question = q.id "
					+ "where ua.assesment = " + assessment + filter
					+ " and u.loginname not in (select loginname from useranswers ua join answerdata ad on ad.id = ua.answer join questions q on q.id = ad.question where ua.assesment = " + assessment 
					+ " and q.testtype = " + QuestionData.TEST_QUESTION + ") and u.loginname in (select loginname from usergroups where groupid = " + group + ")) "
					+ "order by loginname,type";
			Session session = HibernateAccess.currentSession();
			SQLQuery question = session.createSQLQuery(sql);
			question.addScalar("loginname", Hibernate.STRING);
			question.addScalar("firstname", Hibernate.STRING);
			question.addScalar("lastname", Hibernate.STRING);
			question.addScalar("email", Hibernate.STRING);
			question.addScalar("enddate", Hibernate.DATE);
			question.addScalar("module", Hibernate.INTEGER);
			question.addScalar("type", Hibernate.INTEGER);
			question.addScalar("c", Hibernate.INTEGER);
			question.addScalar("psiresult1", Hibernate.INTEGER);
			question.addScalar("psiresult2", Hibernate.INTEGER);
			question.addScalar("psiresult3", Hibernate.INTEGER);
			question.addScalar("psiresult4", Hibernate.INTEGER);
			question.addScalar("psiresult5", Hibernate.INTEGER);
			question.addScalar("psiresult6", Hibernate.INTEGER);
			return question.list();
		} catch (Exception e) {
			handler.getException(e, "getUsersReport", userSessionData.getFilter().getLoginName());
		}
		return new LinkedList();
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,clientreporter,cepareporter,systemaccess"
	 */
	public Collection getNotStartedUsersReport(Integer assessment, UserSessionData userSessionData) throws Exception {
		try {
			String filter = "";
			if(userSessionData.getFilter().getLoginName().startsWith("charla")) {
				filter = " AND u.loginname IN (SELECT loginname FROM usersaccess WHERE passworddate > NOW() - INTERVAL '2 hour' AND loginname LIKE 'generate_es_%_"+assessment+"') ";
			}
			if(assessment.intValue() == AssesmentData.TIMAC_BRASIL || assessment.intValue() == AssesmentData.TIMAC_BRASIL_EBTWPLUS) {
				filter = " AND u.firstname != 'DESLIGADO' ";
			}
			String sql = "select u.loginname,u.firstname,u.lastname,u.email " + "from userassesments ua "
					+ "join users u on u.loginname = ua.loginname " + "where ua.assesment = " + assessment + filter 
					+ " and u.loginname not in (select distinct loginname from useranswers where assesment = "
					+ assessment + ")";
			Session session = HibernateAccess.currentSession();
			SQLQuery question = session.createSQLQuery(sql);
			question.addScalar("loginname", Hibernate.STRING);
			question.addScalar("firstname", Hibernate.STRING);
			question.addScalar("lastname", Hibernate.STRING);
			question.addScalar("email", Hibernate.STRING);
			return question.list();
		} catch (Exception e) {
			handler.getException(e, "getUsersReport", userSessionData.getFilter().getLoginName());
		}
		return new LinkedList();
	}


	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,clientreporter,cepareporter,systemaccess"
	 */
	public Collection getNotStartedUsersReport(Integer assessment, Integer group, UserSessionData userSessionData) throws Exception {
		try {
			String filter = "";
			if(userSessionData.getFilter().getLoginName().startsWith("charla")) {
				filter = " AND u.loginname IN (SELECT loginname FROM usersaccess WHERE passworddate > NOW() - INTERVAL '2 hour' AND loginname LIKE 'generate_es_%_"+assessment+"') ";
			}
			if(assessment.intValue() == AssesmentData.TIMAC_BRASIL || assessment.intValue() == AssesmentData.TIMAC_BRASIL_EBTWPLUS) {
				filter = " AND u.firstname != 'DESLIGADO' ";
			}
			String sql = "select u.loginname,u.firstname,u.lastname,u.email " + "from userassesments ua "
					+ "join users u on u.loginname = ua.loginname " + "where ua.assesment = " + assessment + filter 
					+ " and u.loginname not in (select distinct loginname from useranswers where assesment = " + assessment + ")"
					+ " and u.loginname in (select loginname from usergroups where groupid = " + group + ") AND u.role = 'groupassessment'";
			Session session = HibernateAccess.currentSession();
			SQLQuery question = session.createSQLQuery(sql);
			question.addScalar("loginname", Hibernate.STRING);
			question.addScalar("firstname", Hibernate.STRING);
			question.addScalar("lastname", Hibernate.STRING);
			question.addScalar("email", Hibernate.STRING);
			return question.list();
		} catch (Exception e) {
			handler.getException(e, "getUsersReport", userSessionData.getFilter().getLoginName());
		}
		return new LinkedList();
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name =
	 *                 "systemaccess,administrator,clientreporter,cepareporter"
	 */
	public Collection getUserReport(String user, Integer assessment, UserSessionData userSessionData) throws Exception {
		try {
			String sql = "select ua.enddate,q.module,q.questionorder,a.type,q.id AS question,ua.psiresult1,ua.psiresult2,ua.psiresult3,ua.psiresult4,ua.psiresult5,ua.psiresult6,a.id AS answer "
					+ "from userassesments ua " + "join useranswers uas on uas.loginname = ua.loginname "
					+ "join answerdata ad on ad.id = uas.answer " + "join questions q on ad.question = q.id "
					+ "join answers a on ad.answer = a.id " + "where ua.assesment = " + assessment
					+ " and uas.assesment = ua.assesment " + "and q.testtype = " + QuestionData.TEST_QUESTION + " "
					+ "and ua.loginname = '" + user + "' " + "order by module,questionorder";
			Session session = HibernateAccess.currentSession();
			SQLQuery question = session.createSQLQuery(sql);
			question.addScalar("enddate", Hibernate.DATE);
			question.addScalar("module", Hibernate.INTEGER);
			question.addScalar("questionorder", Hibernate.INTEGER);
			question.addScalar("type", Hibernate.INTEGER);
			question.addScalar("question", Hibernate.INTEGER);
			question.addScalar("psiresult1", Hibernate.INTEGER);
			question.addScalar("psiresult2", Hibernate.INTEGER);
			question.addScalar("psiresult3", Hibernate.INTEGER);
			question.addScalar("psiresult4", Hibernate.INTEGER);
			question.addScalar("psiresult5", Hibernate.INTEGER);
			question.addScalar("psiresult6", Hibernate.INTEGER);
			question.addScalar("answer", Hibernate.INTEGER);
			return question.list();
		} catch (Exception e) {
			handler.getException(e, "getUsersReport", userSessionData.getFilter().getLoginName());
		}
		return new LinkedList();
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,clientreporter,cepareporter,systemaccess"
	 */
	public HashMap<String, HashMap<Integer, String>> getWRTUserAnswers(Integer assessment, UserSessionData userSessionData) throws Exception {
		HashMap<String, HashMap<Integer, String>> values = new HashMap<String, HashMap<Integer, String>>();
		try {
			String filter = "";
			if(userSessionData.getFilter().getLoginName().startsWith("charla")) {
				filter = " AND ua.loginname IN (SELECT loginname FROM usersaccess WHERE passworddate > NOW() - INTERVAL '2 hour' AND loginname LIKE 'generate_es_%_"+assessment+"') ";
			}
			if(assessment.intValue() == AssesmentData.TIMAC_BRASIL || assessment.intValue() == AssesmentData.TIMAC_BRASIL_EBTWPLUS) {
				filter = " AND ua.loginname IN (SELECT loginname FROM users WHERE firstname = 'DESLIGADO') ";
			}
			String sql = "SELECT ua.loginname,ad.question,ad.text,ad.date,ad.distance, ad.unit, ad.country, ad.never, a.key "
					+ "FROM answerdata ad " + "JOIN useranswers ua ON ua.answer = ad.id "
					+ "JOIN questions q ON q.id = ad.question " + "LEFT JOIN answers a ON a.id = ad.answer "
					+ "WHERE ua.assesment = " + assessment + filter + " AND q.wrt";
			Session session = HibernateAccess.currentSession();
			SQLQuery question = session.createSQLQuery(sql);
			question.addScalar("loginname", Hibernate.STRING);
			question.addScalar("question", Hibernate.INTEGER);
			question.addScalar("text", Hibernate.STRING);
			question.addScalar("date", Hibernate.DATE);
			question.addScalar("distance", Hibernate.INTEGER);
			question.addScalar("unit", Hibernate.INTEGER);
			question.addScalar("country", Hibernate.INTEGER);
			question.addScalar("never", Hibernate.BOOLEAN);
			question.addScalar("key", Hibernate.STRING);
			Iterator it = question.list().iterator();
			while (it.hasNext()) {
				Object[] data = (Object[]) it.next();
				String txt = "";
				if (data[2] != null) {
					txt = (String) data[2];
				} else if (data[8] != null) {
					txt = (String) data[8];
				} else if (data[3] != null) {
					txt = Util.formatDate((Date) data[3]);
				} else if (data[4] != null) {
					txt = String.valueOf(data[4]);
				} else if (data[6] != null) {
					txt = new CountryConstants().find(String.valueOf(data[4]));
				}
				if (values.containsKey(data[0])) {
					values.get(data[0]).put((Integer) data[1], txt);
				} else {
					HashMap<Integer, String> v = new HashMap<Integer, String>();
					v.put((Integer) data[1], txt);
					values.put((String) data[0], v);
				}
			}
		} catch (Exception e) {
			handler.getException(e, "getUsersReport", userSessionData.getFilter().getLoginName());
		}
		return values;
	}


	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator,clientreporter,cepareporter,systemaccess"
	 */
	public HashMap<String, HashMap<Integer, String>> getWRTUserAnswers(Integer assessment, Integer group, UserSessionData userSessionData) throws Exception {
		HashMap<String, HashMap<Integer, String>> values = new HashMap<String, HashMap<Integer, String>>();
		try {
			String filter = "";
			if(userSessionData.getFilter().getLoginName().startsWith("charla")) {
				filter = " AND ua.loginname IN (SELECT loginname FROM usersaccess WHERE passworddate > NOW() - INTERVAL '2 hour' AND loginname LIKE 'generate_es_%_"+assessment+"') ";
			}
			if(assessment.intValue() == AssesmentData.TIMAC_BRASIL || assessment.intValue() == AssesmentData.TIMAC_BRASIL_EBTWPLUS) {
				filter = " AND ua.loginname IN (SELECT loginname FROM users WHERE firstname = 'DESLIGADO') ";
			}
			String sql = "SELECT ua.loginname,ad.question,ad.text,ad.date,ad.distance, ad.unit, ad.country, ad.never, a.key "
					+ "FROM answerdata ad " + "JOIN useranswers ua ON ua.answer = ad.id "
					+ "JOIN questions q ON q.id = ad.question " + "LEFT JOIN answers a ON a.id = ad.answer "
					+ "WHERE ua.assesment = " + assessment + filter + " AND q.wrt "
					+ "AND ua.loginname in (select loginname from usergroups where groupid = " + group + ")";
			Session session = HibernateAccess.currentSession();
			SQLQuery question = session.createSQLQuery(sql);
			question.addScalar("loginname", Hibernate.STRING);
			question.addScalar("question", Hibernate.INTEGER);
			question.addScalar("text", Hibernate.STRING);
			question.addScalar("date", Hibernate.DATE);
			question.addScalar("distance", Hibernate.INTEGER);
			question.addScalar("unit", Hibernate.INTEGER);
			question.addScalar("country", Hibernate.INTEGER);
			question.addScalar("never", Hibernate.BOOLEAN);
			question.addScalar("key", Hibernate.STRING);
			Iterator it = question.list().iterator();
			while (it.hasNext()) {
				Object[] data = (Object[]) it.next();
				String txt = "";
				if (data[2] != null) {
					txt = (String) data[2];
				} else if (data[8] != null) {
					txt = (String) data[8];
				} else if (data[3] != null) {
					txt = Util.formatDate((Date) data[3]);
				} else if (data[4] != null) {
					txt = String.valueOf(data[4]);
				} else if (data[6] != null) {
					txt = new CountryConstants().find(String.valueOf(data[4]));
				}
				if (values.containsKey(data[0])) {
					values.get(data[0]).put((Integer) data[1], txt);
				} else {
					HashMap<Integer, String> v = new HashMap<Integer, String>();
					v.put((Integer) data[1], txt);
					values.put((String) data[0], v);
				}
			}
		} catch (Exception e) {
			handler.getException(e, "getUsersReport", userSessionData.getFilter().getLoginName());
		}
		return values;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name =
	 *                 "systemaccess,administrator,clientreporter,cepareporter"
	 */
	public Integer terms(Integer assessment, String login, UserSessionData userSessionData) throws Exception {
		try {
			String sql = "SELECT terms FROM assesments a " + "JOIN userassesments ua ON ua.assesment = a.id "
					+ "WHERE ua.acceptterms IS NULL " + "AND a.terms > 0 " + "AND a.id = " + assessment
					+ " AND ua.loginname = '" + login + "'";
			Session session = HibernateAccess.currentSession();
			SQLQuery question = session.createSQLQuery(sql);
			question.addScalar("terms", Hibernate.INTEGER);
			Iterator it = question.list().iterator();
			if (it.hasNext()) {
				return (Integer) it.next();
			}
		} catch (Exception e) {
			handler.getException(e, "getUsersReport", userSessionData.getFilter().getLoginName());
		}
		return 0;
	}


	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name =
	 *                 "systemaccess,administrator,clientreporter,cepareporter"
	 */
	public Integer terms(String login, UserSessionData userSessionData) throws Exception {
		try {
			String sql = "SELECT terms FROM assesments a "
					+ "JOIN userassesments ua ON ua.assesment = a.id "
					+ "JOIN users u ON u.loginname = ua.loginname "
					+ "WHERE u.acceptterms IS NULL " 
					+ "AND u.loginname = '" + login + "' ORDER BY terms";
			Session session = HibernateAccess.currentSession();
			SQLQuery question = session.createSQLQuery(sql);
			question.addScalar("terms", Hibernate.INTEGER);
			Iterator it = question.list().iterator();
			if (it.hasNext()) {
				Integer term = (Integer) it.next();
				return (term == null) ? 3 : term;
			}
		} catch (Exception e) {
			handler.getException(e, "getUsersReport", userSessionData.getFilter().getLoginName());
		}
		return 0;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name =
	 *                 "systemaccess,administrator,clientreporter,cepareporter"
	 */
	public int getSendedReportId(String loginname, Integer assessment, UserSessionData userSessionData)
			throws Exception {
		try {
			String sql = "SELECT id FROM sendedreports " + "WHERE assessment = " + assessment + " AND login = '"
					+ loginname + "'";
			Session session = HibernateAccess.currentSession();
			SQLQuery question = session.createSQLQuery(sql);
			question.addScalar("id", Hibernate.INTEGER);
			Iterator it = question.list().iterator();
			if (it.hasNext()) {
				return ((Integer) it.next()).intValue();
			}
		} catch (Exception e) {
			handler.getException(e, "getSendedReportId", userSessionData.getFilter().getLoginName());
		}
		return 0;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name =
	 *                 "systemaccess,administrator,clientreporter,cepareporter"
	 */
	public String[] getPSIanswers(String loginname, Integer assessment, UserSessionData userSessionData) throws Exception {
		String[] s = new String[2];
		try {
			String sql = "SELECT psi.psianswer " +
					"FROM userpsianswers upi " +
					"JOIN psianswerdata psi ON psi.id = upi.psianswer " +
					"WHERE loginname = '"+loginname+"' " +
					"AND assesment = "+assessment+" ORDER BY psiquestion";
			Session session = HibernateAccess.currentSession();
			SQLQuery query = session.createSQLQuery(sql).addScalar("psianswer", Hibernate.INTEGER);
			Iterator it = query.list().iterator();
			if (it.hasNext()) {
				String key = String.valueOf(it.next());
				while(it.hasNext()) {
					key += "-"+it.next();
				}
				s[0] = key;

				sql = "SELECT result FROM psiaux WHERE answers = '"+key+"'";
				query = session.createSQLQuery(sql).addScalar("result", Hibernate.STRING);
				String v = (String)query.uniqueResult();
				if(v != null) {
					s[1] = v;
				}
			}
			
		} catch (Exception e) {
			handler.getException(e, "getPSIanswers", userSessionData.getFilter().getLoginName());
		}
		return s;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "systemaccess, administrator"
	 */
	public String checkDependencies(String loginname, Integer assessment, UserSessionData userSessionData) throws Exception {
        try{
			Session session = HibernateAccess.currentSession();
			Query q = session.createSQLQuery("SELECT a.id, a.name FROM assesmentdependencies ad "
					+ "JOIN assesments a ON a.id = ad.previous WHERE assesment = "+assessment).addScalar("id", Hibernate.INTEGER).addScalar("name", Hibernate.STRING);
			Iterator it = q.list().iterator();
			while(it.hasNext()) {
				Object[] data = (Object[]) it.next();
				Query q2 = session.createSQLQuery("SELECT COUNT(*) AS c FROM userassesments WHERE loginname = '"+loginname+"' AND assesment = "+data[0]+" AND enddate IS NOT NULL");
				if(((BigInteger)q2.uniqueResult()).intValue() == 0) {
					return (String) data[1];
				}
			}
		} catch (Exception e) {
			handler.getException(e, "checkDependencies", userSessionData.getFilter().getLoginName());
		}
        return null;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "systemaccess, administrator"
	 */
	public HashMap<Integer,Date> getUserAssessmentStatus(String user, UserSessionData userSessionData) throws Exception {
		HashMap<Integer,Date> map = new HashMap<Integer,Date>();
        try{
			Session session = HibernateAccess.currentSession();
			Query q = session.createSQLQuery("SELECT assesment, enddate FROM userassesments ua WHERE loginname = '"+user+"'").addScalar("assesment", Hibernate.INTEGER).addScalar("enddate", Hibernate.DATE);
			Iterator it = q.list().iterator();
			while(it.hasNext()) {
				Object[] data = (Object[])it.next();
				map.put((Integer)data[0], (Date)data[1]);
			}
		} catch (Exception e) {
			handler.getException(e, "getUserAssessmentStatus", userSessionData.getFilter().getLoginName());
		}
        return map;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator, systemaccess"
	 * @throws Exception
	 */
	public Collection<UserData> findGroupUsers(Integer group, UserSessionData userSessionData)
			throws Exception {
		Collection<UserData> users = new LinkedList<UserData>();
		try {
			Session session = HibernateAccess.currentSession();
			String sql = "SELECT DISTINCT loginname, firstname, lastname, email, language, role, extradata, extradata2 FROM users "
					+ "WHERE role = '" + SecurityConstants.GROUP_ASSESSMENT + "' "
					+ "AND loginname IN (SELECT loginname FROM usergroups WHERE groupid = "+group+")";
			sql += " ORDER BY firstname, lastname";
			Query q = session.createSQLQuery(sql).addScalar("loginname", Hibernate.STRING)
					.addScalar("firstname", Hibernate.STRING).addScalar("lastname", Hibernate.STRING)
					.addScalar("email", Hibernate.STRING).addScalar("language", Hibernate.STRING)
					.addScalar("role", Hibernate.STRING).addScalar("extradata", Hibernate.STRING).addScalar("extradata2", Hibernate.STRING);
			List list = q.list();
			if (list != null) {
				Iterator it = list.iterator();
				while (it.hasNext()) {
					users.add(new UserData((Object[]) it.next()));
				}
			}

		} catch (Exception e) {
			handler.getException(e, "findList", userSessionData.getFilter().getLoginName());
		}
		return users;
	}


	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator, systemaccess"
	 * @throws Exception
	 */
	public HashMap<Integer, Integer> getMultiAssessmentResult(Integer multiId, UserSessionData userSessionData) throws Exception {
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		try {
			Session session = HibernateAccess.currentSession();
			Query q = session.createSQLQuery("SELECT question, answer FROM specificanswers WHERE multianswer = "+multiId).addScalar("question", Hibernate.INTEGER).addScalar("answer", Hibernate.INTEGER);
			Iterator it = q.list().iterator();
			while(it.hasNext()) {
				Object[] data = (Object[]) it.next();
				map.put((Integer)data[0], (Integer)data[1]);
			}
		} catch (Exception e) {
			handler.getException(e, "getMultiAssessmentResult", userSessionData.getFilter().getLoginName());
		}
		return map;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator, systemaccess"
	 * @throws Exception
	 */
	public boolean hasErrors(Integer multiId, UserSessionData userSessionData) throws Exception {
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		try {
			Session session = HibernateAccess.currentSession();
			Query q = session.createSQLQuery("SELECT COUNT(*) AS c FROM answers " + 
					"WHERE id IN (SELECT answer FROM specificanswers WHERE multianswer = "+multiId+") AND type = "+AnswerData.INCORRECT).addScalar("c", Hibernate.INTEGER);
			if(((Integer) q.uniqueResult()).intValue() > 0) {
				return true;
			}
		} catch (Exception e) {
			handler.getException(e, "hasErrors", userSessionData.getFilter().getLoginName());
		}
		return false;
	}


	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator, systemaccess, clientreporter"
	 * @throws Exception
	 */
	public Collection findMultiAnswerUsers(Integer assessmentId, Date from, Date to, UserSessionData userSessionData) throws Exception {
        try{
        	String sql = "SELECT uma.id, u.loginname, u.firstname, u.lastname, uma.enddate, q.key AS qName, q.questionorder AS qOrder, a.key AS aName, a.type " + 
        			"FROM usermultiassessments uma " + 
        			"JOIN users u ON u.loginname = uma.loginname " + 
        			"JOIN specificanswers sa ON sa.multianswer = uma.id " + 
        			"JOIN questions q ON q.id = sa.question " + 
        			"JOIN answers a ON a.id = sa.answer " + 
        			"WHERE uma.assessment = "+assessmentId;
        	if(from != null) {
        		sql += " AND uma.enddate >= "+Util.formatSQLFull(from, 0);
        	}
        	if(from != null) {
        		sql += " AND uma.enddate < "+Util.formatSQLFull(to, 1);
        	}
        	
        	Session session = HibernateAccess.currentSession();
			Query q = session.createSQLQuery(sql).addScalar("id", Hibernate.INTEGER).addScalar("loginname", Hibernate.STRING).addScalar("firstname", Hibernate.STRING).addScalar("lastname", Hibernate.STRING).addScalar("enddate", Hibernate.TIMESTAMP).addScalar("qName", Hibernate.STRING).addScalar("qOrder", Hibernate.INTEGER).addScalar("aName", Hibernate.STRING).addScalar("type", Hibernate.INTEGER);
        	return q.list();
        } catch (Exception e) {
			handler.getException(e, "findMultiAnswerUsers", userSessionData.getFilter().getLoginName());
        }
        return new LinkedList();
	}

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public Integer getFailedAssesments(String user, Integer assesment, UserSessionData userSessionData) throws Exception {
        try{
        	Session session = HibernateAccess.currentSession();
            Query query = session.createSQLQuery("SELECT COUNT(*) AS c FROM failedassessments WHERE loginname = '"+user+"' and assessment = "+assesment).addScalar("c", Hibernate.INTEGER);
            return (Integer)query.uniqueResult();
        } catch (Exception e) {
			handler.getException(e, "getFailedAssesments", userSessionData.getFilter().getLoginName());
        }
        return null;
   }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     */
    public HashMap<String, Integer> getUserGroupCount(Integer group, UserSessionData userSessionData) throws Exception {
    	HashMap<String, Integer> values = new HashMap<String, Integer>();
        try{
        	Session session = HibernateAccess.currentSession();
            Query query = session.createSQLQuery("SELECT role, COUNT(*) AS c FROM usergroups ug "
            		+ "JOIN users u ON u.loginname = ug.loginname " + 
            		" WHERE ug.groupid = " + group + 
            		" GROUP BY role").addScalar("role", Hibernate.STRING).addScalar("c", Hibernate.INTEGER);
            Iterator it = query.list().iterator();
            while(it.hasNext()) {
            	Object[] data = (Object[]) it.next();
            	values.put((String)data[0], (Integer)data[1]);
            }
        } catch (Exception e) {
			handler.getException(e, "getUserGroupCount", userSessionData.getFilter().getLoginName());
        }
        return values;
   }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     */
    public HashMap<String, Collection<UserData>> getGroupUsers(Integer group, UserSessionData userSessionData) throws Exception {
    	HashMap<String, Collection<UserData>> values = new HashMap<String, Collection<UserData>>();
        try{
        	Session session = HibernateAccess.currentSession();
            Query query = session.createSQLQuery("SELECT u.loginname, u.firstname, u.lastname, u.email, u.language, u.role "
            		+ "FROM usergroups ug "
            		+ "JOIN users u ON u.loginname = ug.loginname " + 
            		" WHERE ug.groupid = " + group).addScalar("loginname", Hibernate.STRING).addScalar("firstname", Hibernate.STRING).addScalar("lastname", Hibernate.STRING).addScalar("email", Hibernate.STRING).addScalar("language", Hibernate.STRING).addScalar("role", Hibernate.STRING);
            Iterator it = query.list().iterator();
            while(it.hasNext()) {
            	Object[] data = (Object[]) it.next();
            	UserData user = new UserData(data);
            	if(values.containsKey(user.getRole())) {
            		values.get(user.getRole()).add(user);
            	}else {
            		Collection<UserData> users = new LinkedList<UserData>();
            		users.add(user);
                	values.put(user.getRole(), users);
            	}
            }
        } catch (Exception e) {
			handler.getException(e, "getGroupUsers", userSessionData.getFilter().getLoginName());
        }
        return values;
   }


    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator,accesscode"
     */
    public boolean userExist(String login, UserSessionData userSessionData) throws Exception {
        try{
        	Session session = HibernateAccess.currentSession();
            Query query = session.createSQLQuery("SELECT COUNT(*) AS count FROM users WHERE LOWER(loginname) = '" + login.toLowerCase() +"'").addScalar("count",Hibernate.INTEGER);
            return ((Integer)query.uniqueResult()).intValue() > 0;
        } catch (Exception e) {
			handler.getException(e, "userExist", userSessionData.getFilter().getLoginName());
        }
        return false;
    }


    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator,accesscode,clientreporter"
     */
    public Collection findResults(Integer assessment, int result, UserSessionData userSessionData) throws Exception {
    	try {
    		Session session = HibernateAccess.currentSession();
            Query query = session.createSQLQuery("SELECT login, correct, incorrect FROM userassesmentresults WHERE assesment = "+assessment+" AND type = "+result);
            return query.list();
        } catch (Exception e) {
			handler.getException(e, "findResults", userSessionData.getFilter().getLoginName());
        }
    	return new LinkedList();
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator,accesscode,clientreporter"
     */
    public GroupData findUserGroup(String user, UserSessionData userSessionData) throws Exception {
    	try {
			Session session = HibernateAccess.currentSession();
			Query q = session.createSQLQuery("SELECT g.id, g.name FROM usergroups ug JOIN groups g ON g.id = ug.groupid WHERE loginname = '"+user+"'").addScalar("id", Hibernate.INTEGER).addScalar("name", Hibernate.STRING);
			Iterator it = q.list().iterator();
			if(it.hasNext()) {
				Object[] data = (Object[])it.next();
				return new GroupData((Integer)data[0], null, (String)data[1], false, null, null, false);
			}
        } catch (Exception e) {
			handler.getException(e, "findUserGroup", userSessionData.getFilter().getLoginName());
        }
    	return null;
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator,accesscode,clientreporter"
     */
    public Integer cediUser(String user, UserSessionData userSessionData) throws Exception {
    	try {
			Session session = HibernateAccess.currentSession();
			Query q = session.createSQLQuery("SELECT id FROM cedi WHERE loginname = '"+user+"'").addScalar("id", Hibernate.INTEGER);
			Iterator it = q.list().iterator();
			if(it.hasNext()) {
				return (Integer)it.next();
			}
        } catch (Exception e) {
            handler.getException(e, "cediUser", userSessionData.getFilter().getLoginName());
        }
    	return 0;
    }
    
    
	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator, systemaccess"
	 * @throws Exception
	 */
	public Collection<UserData> findCediUsers(Integer cedi, UserSessionData userSessionData) throws Exception {
		Collection<UserData> users = new LinkedList<UserData>();
		try {
			Session session = HibernateAccess.currentSession();
			String sql = "SELECT DISTINCT loginname, firstname, lastname, email, language, role, extradata, extradata2, location FROM users "
					+ "WHERE loginname IN (SELECT loginname FROM usergroups WHERE groupid = "+GroupData.GRUPO_MODELO+") AND location = '"+cedi+"' AND role = '"+UserData.GROUP_ASSESSMENT+"'";
			sql += " ORDER BY firstname, lastname";
			Query q = session.createSQLQuery(sql).addScalar("loginname", Hibernate.STRING)
					.addScalar("firstname", Hibernate.STRING).addScalar("lastname", Hibernate.STRING)
					.addScalar("email", Hibernate.STRING).addScalar("language", Hibernate.STRING)
					.addScalar("role", Hibernate.STRING).addScalar("extradata", Hibernate.STRING).addScalar("extradata2", Hibernate.STRING).addScalar("location", Hibernate.INTEGER);
			List list = q.list();
			if (list != null) {
				Iterator it = list.iterator();
				while (it.hasNext()) {
					users.add(new UserData((Object[]) it.next()));
				}
			}
        } catch (Exception e) {
            handler.getException(e, "findCediUsers", userSessionData.getFilter().getLoginName());
        }
		return users;
	}
    
/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator, systemaccess"
	 * @throws Exception
	 */
	public Collection<UserData> findCediUsers(Integer[] cedis, String cedi, String firstname, String lastname, UserSessionData userSessionData) throws Exception {
		Collection<UserData> users = new LinkedList<UserData>();
		try {
			Session session = HibernateAccess.currentSession();
			String sql = "SELECT DISTINCT u.loginname, firstname, lastname, email, language, role, extradata, c.name "
					+ "FROM users u "
					+ "JOIN cedi c ON c.id = u.location "
					+ "WHERE u.loginname IN (SELECT loginname FROM usergroups WHERE groupid = "+GroupData.GRUPO_MODELO+") "
					+ "AND lower(u.firstname) like '%"+firstname.toLowerCase()+"%' "
					+ "AND lower(u.lastname) like '%"+lastname.toLowerCase()+"%' "
					+ "AND lower(c.name) like '%"+cedi.toLowerCase()+"%' "
					+ "AND role = '"+UserData.GROUP_ASSESSMENT+"' ";
			if(cedis != null && cedis.length > 0) {
				sql += "AND c.id IN ("+cedis[0];
				for(int i = 1; i < cedis.length; i++) {
					sql += ", "+cedis[i];
				}
				sql += ") ";
			}
			sql += " ORDER BY firstname, lastname";
			Query q = session.createSQLQuery(sql).addScalar("loginname", Hibernate.STRING)
					.addScalar("firstname", Hibernate.STRING).addScalar("lastname", Hibernate.STRING)
					.addScalar("email", Hibernate.STRING).addScalar("language", Hibernate.STRING)
					.addScalar("role", Hibernate.STRING).addScalar("extradata", Hibernate.STRING).addScalar("name", Hibernate.STRING);
			List list = q.list();
			if (list != null) {
				Iterator it = list.iterator();
				while (it.hasNext()) {
					users.add(new UserData((Object[]) it.next()));
				}
			}
        } catch (Exception e) {
            handler.getException(e, "findCediUsers", userSessionData.getFilter().getLoginName());
        }
		return users;
	}

    
	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator, systemaccess"
	 * @throws Exception
	 */
	public Collection<UserData> findCediMissingUsers(Integer[] cedis, Integer type, UserSessionData userSessionData) throws Exception {
		Collection<UserData> users = new LinkedList<UserData>();
		try {
			Session session = HibernateAccess.currentSession();
			String sql = "SELECT DISTINCT u.loginname, firstname, lastname, email, language, role, extradata, c.name "
					+ "FROM users u "
					+ "JOIN cedi c ON c.id = u.location "
					+ "WHERE u.loginname IN (SELECT loginname FROM usergroups WHERE groupid = "+GroupData.GRUPO_MODELO+") "
					+ "AND role = '"+UserData.GROUP_ASSESSMENT+"' ";
			if(cedis != null && cedis.length > 0) {
				sql += "AND c.id IN ("+cedis[0];
				for(int i = 1; i < cedis.length; i++) {
					sql += ", "+cedis[i];
				}
				sql += ") ";
			}
			if(type == 1) {
				sql += "AND u.loginname NOT IN (SELECT loginname FROM userassesments WHERE assesment IN (1553, 1557, 1558, 1559, 1560, 1569, 1570, 1571, 1572, 1573,1561, 1562, 1563, 1564, 1565, 1574, 1575, 1576, 1577, 1578)) "; 
			} else {
				sql += "AND u.loginname NOT IN (SELECT loginname FROM userassesments WHERE assesment = 1614) ";
			}
			sql += " ORDER BY firstname, lastname";
			Query q = session.createSQLQuery(sql).addScalar("loginname", Hibernate.STRING)
					.addScalar("firstname", Hibernate.STRING).addScalar("lastname", Hibernate.STRING)
					.addScalar("email", Hibernate.STRING).addScalar("language", Hibernate.STRING)
					.addScalar("role", Hibernate.STRING).addScalar("extradata", Hibernate.STRING).addScalar("name", Hibernate.STRING);
			List list = q.list();
			if (list != null) {
				Iterator it = list.iterator();
				while (it.hasNext()) {
					users.add(new UserData((Object[]) it.next()));
				}
			}
        } catch (Exception e) {
            handler.getException(e, "findCediUsers", userSessionData.getFilter().getLoginName());
        }
		return users;
	}
	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator, systemaccess"
	 * @throws Exception
	 */
	public Collection<UserData> findCediMissingUsers(String cedi, String firstName, String lastName,  Integer[] cedis, Integer type, UserSessionData userSessionData) throws Exception {
		Collection<UserData> users = new LinkedList<UserData>();
		try {
			Session session = HibernateAccess.currentSession();
			String sql = "SELECT DISTINCT u.loginname, firstname, lastname, email, language, role, extradata, c.name "
					+ "FROM users u "
					+ "JOIN cedi c ON c.id = u.location "
					+ "WHERE u.loginname IN (SELECT loginname FROM usergroups WHERE groupid = "+GroupData.GRUPO_MODELO+") "
					+ "AND  LOWER(firstname) like '%"+firstName.toLowerCase()+"%' "
					+ "AND  LOWER(lastname) like '%"+lastName.toLowerCase()+"%' "
					+"AND LOWER(c.name) like '%"+cedi.toLowerCase()+"%' "
					+ "AND role = '"+UserData.GROUP_ASSESSMENT+"' ";
			if(cedis != null && cedis.length > 0) {
				sql += "AND c.id IN ("+cedis[0];
				for(int i = 1; i < cedis.length; i++) {
					sql += ", "+cedis[i];
				}
				sql += ") ";
			}
			if(type == 1) {
				sql += "AND u.loginname NOT IN (SELECT loginname FROM userassesments WHERE assesment IN (1553, 1557, 1558, 1559, 1560, 1569, 1570, 1571, 1572, 1573,1561, 1562, 1563, 1564, 1565, 1574, 1575, 1576, 1577, 1578)) "; 
			} else {
				sql += "AND u.loginname NOT IN (SELECT loginname FROM userassesments WHERE assesment = 1614) ";
			}
			sql += " ORDER BY firstname, lastname";
			Query q = session.createSQLQuery(sql).addScalar("loginname", Hibernate.STRING)
					.addScalar("firstname", Hibernate.STRING).addScalar("lastname", Hibernate.STRING)
					.addScalar("email", Hibernate.STRING).addScalar("language", Hibernate.STRING)
					.addScalar("role", Hibernate.STRING).addScalar("extradata", Hibernate.STRING).addScalar("name", Hibernate.STRING);
			List list = q.list();
			if (list != null) {
				Iterator it = list.iterator();
				while (it.hasNext()) {
					users.add(new UserData((Object[]) it.next()));
				}
			}
        } catch (Exception e) {
            handler.getException(e, "findCediUsers", userSessionData.getFilter().getLoginName());
        }
		return users;
	}

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator,accesscode"
     */
    public boolean userExistsIdNumber(String idNumber, UserSessionData userSessionData) throws Exception {
        try{
        	Session session = HibernateAccess.currentSession();
            Query query = session.createSQLQuery("SELECT COUNT(*) AS count FROM users WHERE LOWER(extradata3) = '" + idNumber.toLowerCase() +"'").addScalar("count",Hibernate.INTEGER);
            return ((Integer)query.uniqueResult()).intValue() > 0;
        } catch (Exception e) {
			handler.getException(e, "userExist", userSessionData.getFilter().getLoginName());
        }
        return false;
    }


    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator"
     */
    public String getNextTimacUser(String cpf, UserSessionData userSessionData) throws Exception {
        try{
        	String prefix = "";
        	Session session = HibernateAccess.currentSession();
            Query query = session.createSQLQuery("SELECT loginname FROM users WHERE loginname LIKE 'tmc_%"+cpf+"' AND role IN ('"+UserData.SYSTEMACCESS+"', '"+UserData.MULTIASSESSMENT+"', '"+UserData.GROUP_ASSESSMENT+"') ORDER BY loginname DESC").addScalar("loginname",Hibernate.STRING);
            Iterator it = query.list().iterator();
            if(it.hasNext()) {
            	String user = (String)it.next();
            	Query query2 = session.createSQLQuery("SELECT COUNT(*) AS c FROM userassesments WHERE loginname = '"+user+"' AND assesment IN ("+AssesmentData.TIMAC_BRASIL_DA_2020+", "+AssesmentData.TIMAC_BRASIL_EBTW_2020+") AND enddate IS NULL").addScalar("c", Hibernate.INTEGER);
                int c = ((Integer)query2.uniqueResult()).intValue();
                if(c > 0) {
                	return null;
                }else {
                	char current = user.charAt(4);
                	if(Character.isDigit(current)) {
                		prefix = "a";
                	}else {
                		prefix = String.valueOf((char)((int)current + 1));
                	}
                }
            	
            }
        	return "tmc_"+prefix+cpf;
        } catch (Exception e) {
			handler.getException(e, "userExist", userSessionData.getFilter().getLoginName());
        }
        return null;
    }


    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator"
     */
    public String getCurrentTimacUser(String cpf, UserSessionData userSessionData) throws Exception {
        try{
        	String prefix = "";
        	Session session = HibernateAccess.currentSession();
            Query query = session.createSQLQuery("SELECT DISTINCT u.loginname FROM users u " + 
            		"JOIN userassesments ua ON u.loginname = ua.loginname " + 
            		"WHERE u.loginname LIKE 'tmc_%"+cpf+"' " + 
            		"AND ua.assesment in ("+AssesmentData.TIMAC_BRASIL_DA_2020+", "+AssesmentData.TIMAC_BRASIL_EBTW_2020+") " +
            		"AND u.role IN ('"+UserData.SYSTEMACCESS+"', '"+UserData.MULTIASSESSMENT+"', '"+UserData.GROUP_ASSESSMENT+"') " +
            	    "AND enddate IS NULL").addScalar("loginname",Hibernate.STRING);
            Iterator it = query.list().iterator();
            if(it.hasNext()) {
            	return (String)it.next();
            }
            query = session.createSQLQuery("SELECT DISTINCT u.loginname FROM users u " + 
            		"WHERE u.loginname LIKE 'tmc_%"+cpf+"' " + 
            		"AND u.role IN ('"+UserData.SYSTEMACCESS+"', '"+UserData.MULTIASSESSMENT+"', '"+UserData.GROUP_ASSESSMENT+"')").addScalar("loginname",Hibernate.STRING);
            it = query.list().iterator();
            if(it.hasNext()) {
            	return (String)it.next();
            }
        } catch (Exception e) {
			handler.getException(e, "getCurrentTimacUser", userSessionData.getFilter().getLoginName());
        }
        return null;
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator"
     */
    public boolean validateWebinarCode(String webinarCode, UserSessionData userSessionData) throws Exception {
        try{
        	String prefix = "";
        	Session session = HibernateAccess.currentSession();
        	Class.forName("org.postgresql.Driver");
            
            String datacenter="datacenter";
         	// Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+ assesment ,"postgres","pr0v1s0r1A");
             Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/datacenter5","postgres","pr0v1s0r1A");
             //"jdbc:postgresql://localhost:5432/datacenter","postgres","pr0v1s0r1A");

        	Statement st = conn.createStatement();
        	ResultSet set = st.executeQuery("SELECT code FROM cepaactivity ca " + 
            		"WHERE ca.code='"+webinarCode+"'");
            if(set.next()) {
            	conn.close();
            	return true;
            }
            else {         
            	conn.close();
            	return false;}
           
        } catch (Exception e) {
			handler.getException(e, "validateWebinarCode", userSessionData.getFilter().getLoginName());
        }
        return false;
    }
    
    /**
	 * @ejb.interface-method
	 * @ejb.permission role-name =
	 *                 "administrator,systemaccess,accesscode,pepsico_candidatos,basf_assessment,clientreporter,cepareporter"
	 */
	public AssessmentUserData getAssessmentUserData(String user, String code,String assesment,UserSessionData userSessionData) throws Exception {
		Session session = HibernateAccess.currentSession();
    	LinkedList<AssessmentUserData> list = new LinkedList<AssessmentUserData>();
		try {
			if (code == null) {
				throw new InvalidDataException("getAssessmentUserData", "code = null");
			}
			if (userSessionData == null) {
				throw new DeslogedException("getAssessmentUserData", "userSessionData = null");
			}
			
            SQLQuery query = session.createSQLQuery("SELECT firstname, lastname, u.loginname, u.email, u.extradata3, enddate IS NOT NULL AS finished, psitestid IS NOT NULL AS psi, sr.id, sr.sended, sr.certificate " +
            		"FROM userassesments ua " +
            		"LEFT JOIN sendedreports sr ON sr.login = ua.loginname AND sr.assessment = ua.assesment " +
            		"JOIN users u ON u.loginname = ua.loginname " +
            		"WHERE u.extradata3='"+code+"' and  ua.assesment = "+assesment+" and ua.loginname='"+user+"'");
            query.addScalar("firstname", Hibernate.STRING);
            query.addScalar("lastname", Hibernate.STRING);
            query.addScalar("loginname", Hibernate.STRING);
            query.addScalar("email", Hibernate.STRING);
            query.addScalar("extradata3", Hibernate.STRING);
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
            
            query = session.createSQLQuery("SELECT ua.loginname, COUNT(*) AS c "
            		+ "FROM useranswers ua "
            		+ "JOIN users u ON u.loginngame = ua.loginname "
            		+ "JOIN userassesments uas ON uas.assesment = ua.assesment AND uas.loginname = ua.loginname "
            		+ "WHERE uas.enddate IS NULL AND u.extradata3='"+code+"' AND assesment = "+assesment+" and ua.loginname='"+user+"' GROUP BY ua.loginname").addScalar("loginname", Hibernate.STRING).addScalar("c", Hibernate.INTEGER);
            it = query.list().iterator();
            while(it.hasNext()) {
            	Object[] data = (Object[])it.next();
            	if(values.containsKey(data[0])) {
            		values.get(data[0]).setAnswers((Integer) data[1]);
            	}
            }
            
           /* query = session.createSQLQuery("SELECT loginname, count(*) AS c FROM useranswers ua " +
            		"JOIN answerdata ad ON ad.id = ua.answer " +
            		"JOIN questions q ON ad.question = q.id " +
            		"JOIN answers a ON a.id = ad.answer " +
            		"WHERE assesment = "+assesment+" AND q.testtype = 1 AND a.type = 0 and ua.loginname='"+user+"' " +
            		"GROUP BY loginname").addScalar("loginname", Hibernate.STRING).addScalar("c", Hibernate.INTEGER);
            it = query.list().iterator();
            while(it.hasNext()) {
            	Object[] data = (Object[])it.next();
            	if(values.containsKey(data[0])) {
            		values.get(data[0]).setCorrect((Integer) data[1]);
            	}
            }*/

            list.addAll(values.values());
		} catch (Exception e) {
			handler.getException(e, "findWebinarParticipants", code);
		}
		return list.get(0);
	}


    /**
	 * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
	 * @param data
	 * @param userRequest
	 * @throws Exception
	 */
 	public Object[] existTimacUser(String id, UserSessionData userSessionData) throws Exception {
 		Object[] values = {0, null, null, 0};
     	try {
     		Session session = HibernateAccess.currentSession();
     		SQLQuery q = session.createSQLQuery("SELECT u.loginname, ua.assesment, ua.enddate, uar.correct, uar.incorrect, a.green FROM users u " + 
     						"JOIN userassesments ua ON u.loginname = ua.loginname " + 
     						"JOIN assesments a ON a.id = ua.assesment " +
     						"LEFT JOIN userassesmentresults uar ON uar.login = ua.loginname AND uar.assesment = ua.assesment " + 
     						"WHERE u.loginname LIKE 'tmc_%"+id+"' ORDER BY u.loginname, ua.assesment");
     		q.addScalar("loginname", Hibernate.STRING);
     		q.addScalar("assesment", Hibernate.INTEGER);
     		q.addScalar("enddate", Hibernate.DATE);
     		q.addScalar("correct", Hibernate.INTEGER);
     		q.addScalar("incorrect", Hibernate.INTEGER);
     		q.addScalar("green", Hibernate.INTEGER);
     		
     		String lastLogin = "";
     		Iterator it = q.list().iterator();
     		while(it.hasNext()) {
     			Object[] data = (Object[])it.next();
     			String login = (String)data[0];
     			if(!lastLogin.equals(login)) {
         			values[0] = 1;
         			values[1] = null;
         			values[2] = null;
     				values[3] = ((Integer)values[3]).intValue() + 1;
     				lastLogin = login;
     			}
     			Integer assesment = (Integer)data[1];
     			Date end = (Date)data[2];
     			if(assesment.equals(AssesmentData.TIMAC_BRASIL_DA_2020) && end != null) {
     				int percent = ((Integer)data[3]).intValue() * 100 / (((Integer)data[3]).intValue() + ((Integer)data[4]).intValue());
     				if(percent >= ((Integer)data[5]).doubleValue()) {
             			values[0] = 2;
     				}else {
             			values[0] = 3;
     				}
         			values[1] = end;
         			values[2] = percent;
     			}
     		}
     		
     	}catch (Exception e) {
			handler.getException(e, "existTimacUser", userSessionData.getFilter().getLoginName());
     	}
     	return values;
	}
}