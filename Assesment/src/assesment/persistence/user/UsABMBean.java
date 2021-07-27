package assesment.persistence.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.ejb.SessionBean;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import assesment.communication.administration.UserAnswerData;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.assesment.GroupData;
import assesment.communication.corporation.CorporationData;
import assesment.communication.exception.DeslogedException;
import assesment.communication.exception.InvalidDataException;
import assesment.communication.question.AnswerData;
import assesment.communication.question.QuestionData;
import assesment.communication.security.SecurityConstants;
import assesment.communication.user.UserData;
import assesment.communication.util.MD5;
import assesment.persistence.administration.tables.AnswersUser;
import assesment.persistence.administration.tables.FailedAssessment;
import assesment.persistence.administration.tables.ReporterAssesment;
import assesment.persistence.administration.tables.UserAnswer;
import assesment.persistence.administration.tables.UserAnswerLog;
import assesment.persistence.administration.tables.UserAssesment;
import assesment.persistence.administration.tables.UserAssesmentLog;
import assesment.persistence.administration.tables.UserAssesmentPK;
import assesment.persistence.administration.tables.UserAssesmentResult;
import assesment.persistence.administration.tables.UserGroup;
import assesment.persistence.administration.tables.UserGroupPK;
import assesment.persistence.administration.tables.UserPsiAnswer;
import assesment.persistence.administration.tables.UserPsiAnswerLog;
import assesment.persistence.administration.tables.UserSpecificAnswer;
import assesment.persistence.assesment.tables.Assesment;
import assesment.persistence.assesment.tables.Group;
import assesment.persistence.hibernate.HibernateAccess;
import assesment.persistence.user.tables.User;
import assesment.persistence.user.tables.UserAccess;
import assesment.persistence.user.tables.UserCode;
import assesment.persistence.user.tables.UserLogin;
import assesment.persistence.user.tables.UserPassword;
import assesment.persistence.util.ExceptionHandler;

/**
 * @ejb.bean name="UsABM" display-name="Name for UsABM"
 *           description="Description for UsABM" jndi-name="ejb/UsABM"
 *           type="Stateless"
 *  
 */
public abstract class UsABMBean implements SessionBean {

    private ExceptionHandler handler = new ExceptionHandler(UsABMBean.class);
    
    /**
     * Init transaction
     * @ejb.create-method
     * @ejb.permission role-name = "administrator,systemaccess,accesscode,basf_assessment,systemaccess"
     */
    public void ejbCreate()
        throws javax.ejb.CreateException {
    }

    private User userSetCreate(Session session, UserData data, UserSessionData userRequest) throws Exception {
    	try {
			if (userRequest == null) {
				throw new DeslogedException("userCreate","session = null");
			}
			if (data == null) {
				throw new InvalidDataException("userCreate","generic.user.userdata.isempty");
			}
			if(!validateUserCreateData(data,userRequest)){
				throw new InvalidDataException("userCreate","generic.user.userdata.isinvalid");
			}

			Query q = session.createQuery("SELECT up FROM UserPassword up WHERE LOWER(up.loginName) = '"+data.getLoginName().toLowerCase().trim()+"'");
			Iterator it = q.iterate();
			if(it.hasNext()) {
				UserPassword pass = (UserPassword)it.next();
				pass.setEmail(data.getEmail());
				pass.setPassword(data.getPassword());
				pass.setFirstname(data.getFirstName());
				pass.setLastname(data.getLastName());
				session.update(pass);
			} else {
				UserPassword pass = new UserPassword(data.getLoginName(), data.getPassword(), data.getEmail(), false, data.getFirstName(), data.getLastName());
	    		session.save(pass);
			}

			data.setPassword(new MD5().encriptar(data.getPassword()));

            User userCreating = new User(data);
			session.save(userCreating);
            
			q = session.createQuery("SELECT access FROM UserAccess access WHERE LOWER(access.loginName) = '"+data.getLoginName().toLowerCase().trim()+"'");
			it = q.iterate();
			if(it.hasNext()) {
				UserAccess access = (UserAccess) it.next();
				access.setFirstAccess(true);
				access.setPasswordDate(new Date());
				session.update(access);
			} else {
				UserAccess access = new UserAccess(data.getLoginName(), true, new Date());
	    		session.save(access);
			}
            
            return userCreating;
		} catch (Exception e) {
            handler.getException(e, "userCreate", userRequest.getFilter().getLoginName());
            return null;
        }
    }
    
    /**
	 * @ejb.interface-method
     * @ejb.permission role-name = "administrator,accesscode,systemaccess"
	 * 
	 * @param data
	 * @param userRequest
	 * @throws Exception
	 */
	public void userCreate(UserData data,Integer assesment,UserSessionData userRequest) throws Exception {
        Session session = HibernateAccess.currentSession();
        
		User userCreating = userSetCreate(session, data, userRequest);
		
        if(assesment != null) {
            Assesment assesmentAsociated = (Assesment)session.load(Assesment.class,assesment);
            if(data.getRole().equals(SecurityConstants.CLIENT_REPORTER)) {
            	ReporterAssesment reporterAssesment = new ReporterAssesment(userCreating,assesmentAsociated);
            	session.save(reporterAssesment);
            }else {
                UserAssesment userAssesment = new UserAssesment(userCreating,assesmentAsociated);
                userAssesment.setCreationDate(new Date());
                userAssesment.setFdmRegistry(null);
                session.save(userAssesment);
                
                if(assesmentAsociated.getCorporation().getId().intValue() == CorporationData.JJ) {
                    UserCode userCode = new UserCode();
                    userCode.setLoginname(userCreating.getLoginName());
                    session.save(userCode);
                }
            }
        }
	}

    /**
	 * @ejb.interface-method
     * @ejb.permission role-name = "administrator,accesscode,systemaccess"
	 * 
	 * @param data
	 * @param userRequest
	 * @throws Exception
	 */
	public void userGroupCreate(UserData data,Integer group,UserSessionData userRequest) throws Exception {
        Session session = HibernateAccess.currentSession();
        
		User userCreating = userSetCreate(session, data, userRequest);
		Group g = (Group) session.load(Group.class, group);
		
		UserGroup ug = new UserGroup(userCreating, g);
		session.save(ug);
		
		Query q = session.createSQLQuery("SELECT assesment FROM assesmentcategories WHERE category IN (SELECT id FROM categories WHERE groupid = "+group+")").addScalar("assesment",  Hibernate.INTEGER);
        Iterator it = q.list().iterator();
        while(it.hasNext()) {
        	Integer assesment = (Integer)it.next();
			if(assesment != null) {
	            Assesment assesmentAsociated = (Assesment)session.load(Assesment.class,assesment);
	            UserAssesment userAssesment = new UserAssesment(userCreating,assesmentAsociated);
	            userAssesment.setCreationDate(new Date());
                userAssesment.setFdmRegistry(null);
	            session.save(userAssesment);
	        }
        }
	}


    /**
	 * @ejb.interface-method
     * @ejb.permission role-name = "administrator,accesscode,systemaccess"
	 * 
	 * @param data
	 * @param userRequest
	 * @throws Exception
	 */
	public void userCediGroupCreate(UserData data,UserSessionData userRequest) throws Exception {
        Session session = HibernateAccess.currentSession();
        
		Group g = (Group) session.load(Group.class, GroupData.GRUPO_MODELO);
		User u = (User) session.load(User.class, data.getLoginName());
		
		UserGroup ug = new UserGroup(u, g);
		session.save(ug);
	}
    
    /**
	 * @ejb.interface-method
     * @ejb.permission role-name = "administrator,accesscode,systemaccess"
	 * 
	 * @param data
	 * @param userRequest
	 * @throws Exception
	 */
	public void userCreate(UserData data,String[] assesments,UserSessionData userRequest) throws Exception {
        Session session = HibernateAccess.currentSession();
        
		User userCreating = userSetCreate(session, data, userRequest);
		
		for(int i = 0; i < assesments.length; i++) {
			Integer assesment = new Integer(assesments[i]);
	        if(assesment != null) {
	            Assesment assesmentAsociated = (Assesment)session.load(Assesment.class,assesment);
	            if(data.getRole().equals(SecurityConstants.CLIENT_REPORTER)) {
	            	ReporterAssesment reporterAssesment = new ReporterAssesment(userCreating,assesmentAsociated);
	            	session.save(reporterAssesment);
	            }else {
	                UserAssesment userAssesment = new UserAssesment(userCreating,assesmentAsociated);
	                userAssesment.setCreationDate(new Date());
	                userAssesment.setFdmRegistry(null);
	                session.save(userAssesment);
	                
	                if(assesmentAsociated.getCorporation().getId().intValue() == CorporationData.JJ) {
	                    UserCode userCode = new UserCode();
	                    userCode.setLoginname(userCreating.getLoginName());
	                    session.save(userCode);
	                }
	            }
	        }
		}
	}


    /**
	 * @ejb.interface-method
     * @ejb.permission role-name = "administrator,accesscode"
	 * 
	 * @param data
	 * @param userRequest
	 * @throws Exception
	 */
	public void userCreateFromFile(UserData data, Integer assesment, UserSessionData userRequest) throws Exception {
        Session session = HibernateAccess.currentSession();

        User user = userSetCreate(session, data, userRequest);
		
		
        if(assesment != null) {
            Assesment assesmentAsociated = (Assesment)session.load(Assesment.class,assesment);
            if(data.getRole().equals(SecurityConstants.CLIENT_REPORTER)) {
            	ReporterAssesment reporterAssesment = new ReporterAssesment(user,assesmentAsociated);
            	session.save(reporterAssesment);
            }else {
                UserAssesment userAssesment = new UserAssesment(user,assesmentAsociated);
                userAssesment.setCreationDate(new Date());
                userAssesment.setFdmRegistry(null);
                session.save(userAssesment);
                
                if(assesmentAsociated.getCorporation().getId().intValue() == CorporationData.JJ) {
                    UserCode userCode = new UserCode();
                    userCode.setLoginname(user.getLoginName());
                    session.save(userCode);
                }
            }
        }
		UserPassword pass = new UserPassword(user.getLoginName(), user.getPassword(), user.getEmail(), false, user.getFirstName(), user.getLastName());
		session.save(pass);
/*		Class.forName("org.postgresql.Driver");
        Connection conn1 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/assesment","postgres","pr0v1s0r1A");
        Statement st = conn1.createStatement();
        String insert = "INSERT INTO userpassword (loginname, password, firstname, lastname, email) " +
				"VALUES ('"+user.getLoginName()+"','"+user.getPassword()+"','"+user.getFirstName()+"','"+user.getLastName()+"','"+user.getEmail()+"');\n";
		st.execute(insert);
		conn1.close();*/
	}

	/**
	 * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
	 */
	public void userUpdate(UserData data, Integer corporationId, UserSessionData userSessionData, boolean validate) throws Exception {
		try {
			if (userSessionData == null) {
				throw new DeslogedException("userUpdate","session = null");
			}
			if (data == null) {
				throw new InvalidDataException("userUpdate","data = null");
			}
			if (!userExist(data.getLoginName(), userSessionData)) {
				throw new InvalidDataException("userUpdate","generic.user.notexist");
			}
			Session session = HibernateAccess.currentSession();
            
			User user = (User)session.load(User.class,data.getLoginName());
			user.setFirstName(data.getFirstName());
			user.setLastName(data.getLastName());
			user.setLanguage(data.getLanguage());
            user.setEmail(data.getEmail());
            user.setRole(data.getRole());
            user.setSex(data.getSex());
            user.setBirthDate(data.getBirthDate());
            user.setCountry(data.getCountry());
            user.setNationality(data.getNationality());
            user.setStartDate(data.getStartDate());
            user.setLicenseExpiry(data.getLicenseExpiry());
            user.setVehicle(data.getVehicle());
            user.setLocation(data.getLocation());
            user.setExpiry(data.getExpiry());
            if(data.getExtraData() != null) {
                user.setExtraData(data.getExtraData());
            }
            if(data.getExtraData2() != null) {
                user.setExtraData2(data.getExtraData2());
            }
            if(data.getExtraData3() != null) {
                user.setExtraData3(data.getExtraData3());
            }
            user.setDatacenter(data.getDatacenter());

            session.update(user);
		} catch (Exception e) {
            handler.getException(e, "userUpdate", userSessionData.getFilter().getLoginName());
        }
	}
	
	/**
	 * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
	 */
	public void updateUserName(String firstName, String lastName, UserSessionData userSessionData) throws Exception {
		try {
			Session session = HibernateAccess.currentSession();
	        
			User user = (User)session.load(User.class,userSessionData.getFilter().getLoginName());
			if(firstName != null && firstName.length() > 0)
				user.setFirstName(firstName);
			if(lastName != null && lastName.length() > 0)
				user.setLastName(lastName);
            session.update(user);
		} catch (Exception e) {
            handler.getException(e, "updateUserName", userSessionData.getFilter().getLoginName());
        }
	}
	
	/**
	 * @ejb.interface-method
     * @ejb.permission role-name = "administrator,userchangepassword"
	 * 
	 * @param data
	 * @param userRequest
	 * @throws Exception
	 */
	public void userResetPassword(UserData data, UserSessionData userRequest) throws Exception {
		if (userRequest == null) {
			throw new DeslogedException("Class persistence.user.UserABM(userResetPassword), Session data is empty","generic.error.deslogued");
		}
		if (data == null) {
			throw new InvalidDataException("Class persistence.user.UserABM(userResetPassword), UserData is empty","generic.user.userdata.isempty");
		}
		if (data.getPassword()==null) {
			throw new InvalidDataException("Class persistence.user.UserABM(userResetPassword), password is empty","generic.user.userdata.pass.isempty");
		}
		if (data.getLoginName()==null || data.getLoginName().trim().length()==0) {
			throw new InvalidDataException("Class persistence.user.UserABM(userResetPassword), primary key is empty","persistence.user.userdata.primarykey.isempty");
		}
		if (!userExist(data.getLoginName(), userRequest)) {
			throw new InvalidDataException("Class persistence.user.UserABM(userRresetPassword), user not exist","generic.user.notexist");
		}
		modifyPassword(data.getLoginName().trim(),data.getPassword(),userRequest);
	}
	
	/**
	 * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
	 * Modify the password of the logged user.
	 * @param password	- New Password
	 * @param userRequest	- Logged user 	
	 * @throws Exception
	 */
	public void userResetOwnPassword(String password, UserSessionData userRequest) throws Exception {

		if (userRequest == null) {
			throw new DeslogedException("Class persistence.user.UserABM(userResetPassword), Session data is empty","generic.error.deslogued");
		}
		if (password == null || password.trim().length() == 0) {
			throw new InvalidDataException("Class persistence.user.UserABM(userResetPassword), password is empty","generic.user.userdata.pass.isempty");
		}

		modifyPassword(userRequest.getFilter().getLoginName(),password,userRequest);
		
	}

	/**
     * @param password
     * @param nick
     * @throws Exception
     */
    private void modifyPassword(String nick, String password, UserSessionData userRequest) throws Exception {
        try {
            Session session = HibernateAccess.currentSession();
            
			String pass = new MD5().encriptar(password);
			User user =null;
			user=(User)session.load(User.class,nick);
			user.setPassword(pass);
			session.update(user);

            UserAccess access = new UserAccess(nick,false,new Date());
            session.saveOrUpdate(access);
            
		} catch (Exception e) {
            handler.getException(e, "persistence.user.modifyPassword", userRequest.getFilter().getLoginName());
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
	 * 
	 * @param lngName
	 * @param userRequest
	 * @throws Exception
	 */
	public void userSwitchLanguage(String lngName, UserSessionData userRequest) throws Exception {
		
		//Check integration parameters
		if(!isLanguage(lngName)){
			throw new InvalidDataException("Has ocurred error in class persistence.user.UserABM(userSwitchLanguage), lngName is not a valid language name","persistence.user.userdata.language.isinvalid");
		}
		
		//Check integration data
		if (userRequest == null) {
			throw new DeslogedException("Class persistence.user.UserABM(userSwitchLanguage), Session data is empty","generic.error.deslogued");
		}
		
		try {
            Session session = HibernateAccess.currentSession();
			String nick=userRequest.getFilter().getLoginName();
			User user =null;
			user=(User)session.load(User.class,nick);
			user.setLanguage(lngName);
			session.update(user);
		} catch (Exception e) {
            handler.getException(e, "persistence.user.switchLanguage", userRequest.getFilter().getLoginName());
        }
	}

	/**
	 * @ejb.interface-method
     * @ejb.permission role-name = "administrator,userdelete"
	 */
	public void userDelete(String user, UserSessionData userSessionData) throws Exception {
		try {
			//Check integrity data
			if (user == null) {
				throw new InvalidDataException("userDelete","user ==null");
			}
			if (userSessionData == null) {
				throw new DeslogedException("userDelete","session = null");
			}
			if (!userExist(user, userSessionData)) {
				throw new InvalidDataException("Class persistence.user.UserABM(userDelete), user not exist","generic.user.notexist");
			}

            Session session = HibernateAccess.currentSession();
            
            Query query = session.createQuery("DELETE FROM UserAssesment WHERE loginname = :user");
            query.setParameter("user", user);
            query.executeUpdate();

            User userObj = (User)session.load(User.class, user);         
            if(userObj.getBirthDate() == null) {
            	userObj.setBirthDate(new Date());
            	session.update(userObj);
            }
			session.delete(userObj);

		} catch (Exception e) {
            handler.getException(e, "userDelete", userSessionData.getFilter().getLoginName());
        }		
	}
	
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,usercreate,resetpassword,accesscode,systemaccess"
     */
	public boolean userExist(String nick, UserSessionData userRequest) throws Exception {
		boolean result=false;
		
		try{
            Session session = HibernateAccess.currentSession();
            
			Query query=session.createSQLQuery("select count(*) as c from users " +
											"where lower(loginname)='"+nick.toLowerCase()+"'").addScalar("c", Hibernate.INTEGER);
			Integer count = (Integer)query.uniqueResult();
			return count.intValue() > 0;
			
		} catch (Exception e) {
            handler.getException(e, "persistence.user.exists", userRequest.getFilter().getLoginName());
        }
		return result;
	}
	
	/**
	 * @param lngName
	 * @return boolean indicate is lngName a valid language name
	 */
	private boolean isLanguage(String lngName){
		boolean result=false;
		
		try{
			//Language lng=(Language)session.load(Language.class,lngName);
			
			//The lngName is a valid language name
			
			result=true;
		} catch(HibernateException he){
			//The lngName is a not valid language name
			result=false;
		}
	
		return result;
	}
	
	public boolean validateUserCreateData(UserData data,UserSessionData userRequest){
		System.out.println(data.getPassword()+" "+data.getFirstName()+" "+data.getLastName()+" "+data.getLanguage());
		if (data.getPassword() == null || data.getPassword().trim().length() == 0) {
			return false;
		}

		if (data.getFirstName() == null
				|| data.getFirstName().trim().length() == 0) {
			return false;
		}

		if (data.getLastName() == null
				|| data.getLastName().trim().length() == 0) {
			return false;
		}
		if (data.getLanguage() == null
				|| data.getLanguage().trim().length() == 0) {
			return false;
		}

		return true;

	}

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess,resetpassword"
     */
    public void resetPassword(UserData userData, String password, UserSessionData userSessionData) throws Exception {
        try {
            Session session = HibernateAccess.currentSession();
            User user = (User)session.load(User.class,userData.getLoginName());
            user.setPassword(password);
            session.save(user);
            
        } catch (Exception e) {
            handler.getException(e,"resetPassword",userSessionData.getFilter().getLoginName());
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public void saveLogin(long date,UserSessionData userSessionData) throws Exception {
        try{    
            Session session = HibernateAccess.currentSession();
            UserLogin login = new UserLogin(userSessionData.getFilter().getLoginName(),String.valueOf(date));
            session.save(login);
        } catch (Exception e) {
            handler.getException(e,"saveLogin",userSessionData.getFilter().getLoginName());
        } 
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator, systemaccess, accesscode"
     */
    public void associateAssesment(String user,Integer assesment,UserSessionData userRequest) throws Exception {
        try {
            if (userRequest == null) {
                throw new DeslogedException("associateAssesment","session = null");
            }
            if (user == null) {
                throw new InvalidDataException("associateAssesment","user is null");
            }
            if (assesment == null) {
                throw new InvalidDataException("associateAssesment","assesment is null");
            }

            Session session = HibernateAccess.currentSession();
            User userObj = (User)session.load(User.class,user);
            Assesment assesmentObj = (Assesment)session.load(Assesment.class,assesment);
            Query query=session.createSQLQuery("select count(*) as c from userassesments " +
					"where lower(loginname)='"+user.toLowerCase()+"' and assesment="+assesment).addScalar("c", Hibernate.INTEGER);
            Integer count = (Integer)query.uniqueResult();
            if (count==0) {
                UserAssesment userAssesment = new UserAssesment(userObj,assesmentObj);
                userAssesment.setCreationDate(new Date());
                userAssesment.setFdmRegistry(null);
                session.save(userAssesment);	
            }

            
        } catch (Exception e) {
            handler.getException(e, "associateAssesment", userRequest.getFilter().getLoginName());
        }
    }
    

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     */
    public void deleteAssociatedAssesment(String user,Integer assesment,UserSessionData userRequest) throws Exception {
        try {
            if (userRequest == null) {
                throw new DeslogedException("deleteAssociateAssesment","session = null");
            }
            if (user == null) {
                throw new InvalidDataException("deleteAssociateAssesment","user is null");
            }
            if (assesment == null) {
                throw new InvalidDataException("deleteAssociateAssesment","assesment is null");
            }

            Session session = HibernateAccess.currentSession();
            User userObj = (User)session.load(User.class,user);
            Assesment assesmentObj = (Assesment)session.load(Assesment.class,assesment);

            UserAssesment userAssesment = (UserAssesment)session.load(UserAssesment.class,new UserAssesmentPK(userObj,assesmentObj));
            UserAssesmentLog log = new UserAssesmentLog(userAssesment,userRequest.getFilter().getLoginName(),new Date(), 2, null);
        	Iterator it=userAssesment.getAnswers().iterator();
        	while(it.hasNext()) {
        		UserAnswer au=(UserAnswer)it.next();
        		Date d=new Date();
        		if(au.getAnswer()!=null) {
        			UserAnswerLog logAnsw = new UserAnswerLog(au, assesment ,userAssesment.getPk().getUser().getLoginName(), userRequest.getFilter().getLoginName(),new Date(), 2, null);
            		session.save(logAnsw);
        		}
        	}
        	it=userAssesment.getPsianswers().iterator();
        	while(it.hasNext()) {
        		UserPsiAnswer au=(UserPsiAnswer)it.next();
        		Date d=new Date();
        		UserPsiAnswerLog logPsiAnsw = new UserPsiAnswerLog(au, assesment ,userAssesment.getPk().getUser().getLoginName(), userRequest.getFilter().getLoginName(),new Date(), 2, null);
            	session.save(logPsiAnsw);
        	}
            session.save(log);
            session.delete(userAssesment);
            
        } catch (Exception e) {
            handler.getException(e, "deleteAssociateAssesment", userRequest.getFilter().getLoginName());
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator,basf_assessment"
     */
    public int[] saveAnswer(String user,Integer assesment,UserAnswerData answer,UserSessionData userSessionData,boolean psico,boolean feedback) throws Exception {
        int[] values = {0,0};
        try {
            if (userSessionData == null) {
                throw new DeslogedException("saveAnswer","session = null");
            }
            if (user == null) {
                throw new InvalidDataException("saveAnswer","user is null");
            }
            if (assesment == null) {
                throw new InvalidDataException("saveAnswer","assesment is null");
            }
            if (answer == null) {
                throw new InvalidDataException("saveAnswer","answer is null");
            }
            
            Session session = HibernateAccess.currentSession();
            
            UserAssesment userAssesment = (UserAssesment)session.load(UserAssesment.class,new UserAssesmentPK(user,assesment));
            if(psico) {
                UserPsiAnswer userAnswer = userAssesment.getPsiAnsweredQuestion(answer.getQuestion()); 
                if(userAnswer == null) {
	                UserPsiAnswer userPsiAnswer = new UserPsiAnswer(answer);
	                Integer id = (Integer)session.save(userPsiAnswer);
	                userPsiAnswer.setId(id);
    
	                userAssesment.getPsianswers().add(userPsiAnswer);
	                session.update(userAssesment);
                }else {
                	userAnswer.updatePsiData(answer);
                	session.update(userAnswer);
                }
            }else {
                UserAnswer userAnswer = userAssesment.getAnsweredQuestion(answer.getQuestion()); 
                
                if(userAnswer == null) {
	                userAnswer = new UserAnswer(answer);
	                Integer id = (Integer)session.save(userAnswer);
	                userAnswer.setId(id);
    
	                userAssesment.getAnswers().add(userAnswer);
	                session.update(userAssesment);
                }else {
                	userAnswer.updateData(answer);
                	session.update(userAnswer);
                }
                
                if(feedback) {
                    if(userAnswer.getQuestion().getTestType() == QuestionData.TEST_QUESTION) {
                        values[1] = 1;
                        if(userAnswer.getAnswer().getResultType() == AnswerData.CORRECT) {
                            values[0] = 1;
                        }
                    }
                }
            }
            
            if(assesment.intValue() == AssesmentData.MUTUAL_DA ||assesment.intValue() == AssesmentData.ABBOTT_NEWDRIVERS
            		||assesment.intValue() == AssesmentData.ABBEVIE_LATAM ||assesment.intValue() == AssesmentData.SUMITOMO
            		|| assesment.intValue() == AssesmentData.GUINEZ_INGENIERIA_V3) {
            	int right = 0;
	            int wrong = 0;
	            int module = 0;
	            float points = 0;
	            String modules="select distinct(id) from modules where assesment="+ assesment;
	            Query q2 = session.createSQLQuery(modules).addScalar("id", Hibernate.INTEGER);
	            Iterator it2 = q2.list().iterator();
	            while(it2.hasNext()) {
	            	Object d=(Object)it2.next();
	                Integer m=(Integer)d;
                	
	                String sqlTotal = "SELECT COUNT(*) AS totaluser, (select count (*) from questions where module="+m+"  AND testtype =" +QuestionData.TEST_QUESTION+") AS totalquestion "
                		+ "FROM useranswers ua " + 
                		"JOIN answerdata ad ON ad.id = ua.answer " + 
                		"JOIN questions q ON q.id = ad.question " + 
                		"JOIN answers a ON a.id = ad.answer " + 
                		"WHERE loginname = '"+user+"' AND assesment = " +assesment + " AND q.module="+m+ 
                		" AND q.testtype = " +QuestionData.TEST_QUESTION; 
                		
                	q2 = session.createSQLQuery(sqlTotal).addScalar("totaluser", Hibernate.INTEGER).addScalar("totalquestion", Hibernate.INTEGER);
                	Iterator it4 = q2.list().iterator();
	               	while (it4.hasNext()) {
	            	   Object[] dataq=(Object[])it4.next();
	            	   Integer totalusers=(Integer)dataq[0];
	            	   Integer totalquestions=(Integer)dataq[1];

            	   		if(totalusers.equals(totalquestions)) {
                			String sqlResult = "SELECT q.module, a.type, COUNT(*) AS c, SUM(a.points) AS p "
		                		+ "FROM useranswers ua " + 
		                		"JOIN answerdata ad ON ad.id = ua.answer " + 
		                		"JOIN questions q ON q.id = ad.question " + 
		                		"JOIN answers a ON a.id = ad.answer " + 
		                		"WHERE loginname = '"+user+"' AND q.module="+m+" AND assesment = " +assesment + 
		                		" AND q.testtype = " +QuestionData.TEST_QUESTION + 
		                		" GROUP BY q.module, a.type" +
		                		" ORDER BY q.module, a.type";
			                Query q = session.createSQLQuery(sqlResult).addScalar("module", Hibernate.INTEGER).addScalar("type", Hibernate.INTEGER).addScalar("c", Hibernate.INTEGER).addScalar("p", Hibernate.FLOAT);
			                Iterator it = q.list().iterator();
			                while(it.hasNext()) {
			                	Object[] data = (Object[]) it.next();
			                	int moduleId = ((Integer)data[0]).intValue(); 
			                	if(moduleId != module) {
			                		if(module != 0) {
			                			Query q3 = session.createQuery("SELECT r FROM UserAssesmentResult r WHERE r.assesment = "+assesment+" AND r.login = '"+user+"' AND r.type = "+module);
			                            Iterator it3 = q3.list().iterator();
			                            if(it3.hasNext()) {
			                            	UserAssesmentResult r = (UserAssesmentResult)it3.next();
			                                r.setCorrect(right);
			                                r.setIncorrect(wrong);
			                                session.update(r);
			                            }else {
			                            	UserAssesmentResult result = new UserAssesmentResult(user, assesment, module, right, wrong, points);
			                    	        session.save(result);
			                            }
			                            right = 0;
			                            wrong = 0;
			                            points = 0;
			                    	}
			                    	module = moduleId;
			                    }
			                    if(((Integer)data[1]).intValue() == AnswerData.CORRECT) {
			                    	right = ((Integer)data[2]).intValue();
			                    }
			                    if(((Integer)data[1]).intValue() == AnswerData.INCORRECT) {
			                    	wrong = ((Integer)data[2]).intValue();
			                    }
			                    points += ((Float)data[3]).floatValue();
			                }
			                Query q3 = session.createQuery("SELECT r FROM UserAssesmentResult r WHERE r.assesment = "+assesment+" AND r.login = '"+user+"' AND r.type = "+module);
			                Iterator it3 = q3.list().iterator();
			                if(it3.hasNext()) {
			                	UserAssesmentResult r = (UserAssesmentResult)it3.next();
			                    r.setCorrect(right);
			                    r.setIncorrect(wrong);
			                    session.update(r);
                			}else {
                				UserAssesmentResult result = new UserAssesmentResult(user, assesment, module, right, wrong, points);
        	        			session.save(result);
                			}
                		}
               		} 
               }
            }
        } catch (Exception e) {
            handler.getException(e, "saveAnswer", userSessionData.getFilter().getLoginName());
        }
        return values;
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator"
     */
    public void savePsicoResults(String user, Integer assesment, int[] values, int psiId, int psiTestId, UserSessionData userSessionData) throws Exception {
        try {
            if (userSessionData == null) {
                throw new DeslogedException("savePsicoResults","session = null");
            }
            if (user == null) {
                throw new InvalidDataException("savePsicoResults","user is null");
            }
            if (assesment == null) {
                throw new InvalidDataException("savePsicoResults","assesment is null");
            }
            
            Session session = HibernateAccess.currentSession();
            
            UserAssesment userAssesment = (UserAssesment)session.load(UserAssesment.class,new UserAssesmentPK(user,assesment));
            for(int i = 0; i < 6; i++) {
                userAssesment.setPsicoResult(i,values[i]);
            }
            userAssesment.setPsiId(new Integer(psiId));
            userAssesment.setPsiTestId(new Integer(psiTestId));
            
            session.update(userAssesment);
            
        } catch (Exception e) {
            handler.getException(e, "savePsicoResults", userSessionData.getFilter().getLoginName());
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator"
     */
    public void saveNewHire(String user, Integer assesment, Integer value, UserSessionData userSessionData) throws Exception {
        try {
            if (userSessionData == null) {
                throw new DeslogedException("saveNewHire","session = null");
            }
            if (user == null) {
                throw new InvalidDataException("saveNewHire","user is null");
            }
            if (assesment == null) {
                throw new InvalidDataException("saveNewHire","assesment is null");
            }
            
            Session session = HibernateAccess.currentSession();
            
            UserAssesment userAssesment = (UserAssesment)session.load(UserAssesment.class,new UserAssesmentPK(user,assesment));
            userAssesment.setNewHire(value);
            
            session.update(userAssesment);
            
        } catch (Exception e) {
            handler.getException(e, "saveNewHire", userSessionData.getFilter().getLoginName());
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator"
     */
    public void setRedirect(String userId,String redirect,Integer eLearningUser,
    		Boolean eLearningEnabled, Integer assesmentId, UserSessionData userSessionData) throws Exception {
        try {
            if (userSessionData == null) {
                throw new DeslogedException("setRedirect","session = null");
            }
            if (userId == null) {
                throw new InvalidDataException("setRedirect","user is null");
            }
            if (redirect == null) {
                throw new InvalidDataException("setRedirect","redirect is null");
            }
            if (eLearningUser == null) {
                throw new InvalidDataException("setRedirect","user is null");
            }
            
            Session session = HibernateAccess.currentSession();
            UserAssesmentPK pk = new UserAssesmentPK(userId,assesmentId);
            UserAssesment user = (UserAssesment)session.load(UserAssesment.class,pk);
            user.setElearningInstance(eLearningUser);
            user.setElearningRedirect(redirect);
            user.setElearningEnabled(eLearningEnabled);
            session.save(user);
            
        } catch (Exception e) {
            handler.getException(e, "saveNewHire", userSessionData.getFilter().getLoginName());
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator"
     */
    public void setEndDate(String userId,Integer assesmentId, UserSessionData userSessionData) throws Exception {
        try {
            if (userSessionData == null) {
                throw new DeslogedException("setEndDate","session = null");
            }
            if (userId == null) {
                throw new InvalidDataException("setEndDate","user is null");
            }
            
            Session session = HibernateAccess.currentSession();
            UserAssesmentPK pk = new UserAssesmentPK(userId,assesmentId);
            UserAssesment user = (UserAssesment)session.load(UserAssesment.class,pk);
            if(user.getEndDate() == null) {
            	user.setEndDate(Calendar.getInstance().getTime());
            	session.update(user);
            }
            
            int right = 0;
            int wrong = 0;
            float points = 0;
            String sqlResult = "SELECT a.type, COUNT(*) AS c, SUM(a.points) AS p "
            		+ "FROM useranswers ua " + 
            		"JOIN answerdata ad ON ad.id = ua.answer " + 
            		"JOIN questions q ON q.id = ad.question " + 
            		"JOIN answers a ON a.id = ad.answer " + 
            		"WHERE loginname = '"+userId+"' AND assesment = " +assesmentId + 
            		" AND q.testtype = " +QuestionData.TEST_QUESTION + 
            		" GROUP BY a.type";
            Query q = session.createSQLQuery(sqlResult).addScalar("type", Hibernate.INTEGER).addScalar("c", Hibernate.INTEGER).addScalar("p", Hibernate.FLOAT);
            Iterator it = q.list().iterator();
            while(it.hasNext()) {
            	Object[] data = (Object[]) it.next();
            	if(((Integer)data[0]).intValue() == AnswerData.CORRECT) {
            		right = ((Integer)data[1]).intValue();
            	}
            	if(((Integer)data[0]).intValue() == AnswerData.INCORRECT) {
            		wrong = ((Integer)data[1]).intValue();
            	}
        		points += ((Float)data[2]).floatValue();
            }
            
            q = session.createQuery("SELECT r FROM UserAssesmentResult r WHERE r.assesment = "+assesmentId+" AND r.login = '"+userId+"' AND r.type = 1");
            it = q.list().iterator();
            if(it.hasNext()) {
            	UserAssesmentResult r = (UserAssesmentResult)it.next();
            	r.setCorrect(right);
            	r.setIncorrect(wrong);
            	session.update(r);
            }else {
	            UserAssesmentResult result = new UserAssesmentResult(userId, assesmentId, 1, right, wrong, points);
	            session.save(result);
            }
            
            if(assesmentId.intValue() == AssesmentData.UPL_NEWHIRE) {
                right = 0;
                wrong = 0;
                points = 0;
            	sqlResult = "SELECT ad.question, ad.answer "
                		+ "FROM useranswers ua " + 
                		"JOIN answerdata ad ON ad.id = ua.answer " + 
                		"JOIN questions q ON q.id = ad.question " + 
                		"WHERE loginname = '"+userId+"' AND assesment = " +assesmentId + 
                		" AND q.testtype = " +QuestionData.NOT_TEST_QUESTION ;
                q = session.createSQLQuery(sqlResult).addScalar("question", Hibernate.INTEGER).addScalar("answer", Hibernate.INTEGER);
                it = q.list().iterator();
                while(it.hasNext()) {
                	Object[] data = (Object[]) it.next();
                	int v = QuestionData.getUPLValue((Integer)data[0], (Integer)data[1]);  
                	if(v != -1)
                		wrong += v;
                }            	
                right=20-wrong;
                
                UserAssesmentResult result = new UserAssesmentResult(userId, assesmentId, 2, right, wrong, points);
                session.save(result);
            }
            
  /*          if(assesmentId.intValue() == AssesmentData.MUTUAL_DA ||assesmentId.intValue() == AssesmentData.ABBOTT_NEWDRIVERS ||assesmentId.intValue() == AssesmentData.ABBEVIE_LATAM) {
                right = 0;
                wrong = 0;
                int module = 0;
                sqlResult = "SELECT q.module, a.type, COUNT(*) AS c "
                		+ "FROM useranswers ua " + 
                		"JOIN answerdata ad ON ad.id = ua.answer " + 
                		"JOIN questions q ON q.id = ad.question " + 
                		"JOIN answers a ON a.id = ad.answer " + 
                		"WHERE loginname = '"+userId+"' AND assesment = " +assesmentId + 
                		" AND q.testtype = " +QuestionData.TEST_QUESTION + 
                		" GROUP BY q.module, a.type" +
                		" ORDER BY q.module, a.type";
                q = session.createSQLQuery(sqlResult).addScalar("module", Hibernate.INTEGER).addScalar("type", Hibernate.INTEGER).addScalar("c", Hibernate.INTEGER);
                it = q.list().iterator();
                while(it.hasNext()) {
                	Object[] data = (Object[]) it.next();
                	int moduleId = ((Integer)data[0]).intValue(); 
                	if(moduleId != module) {
                		if(module != 0) {
                            Query q2 = session.createQuery("SELECT r FROM UserAssesmentResult r WHERE r.assesment = "+assesmentId+" AND r.login = '"+userId+"' AND r.type = "+module);
                            Iterator it2 = q2.list().iterator();
                            if(it2.hasNext()) {
                            	UserAssesmentResult r = (UserAssesmentResult)it2.next();
                            	r.setCorrect(right);
                            	r.setIncorrect(wrong);
                            	session.update(r);
                            }else {
                	            UserAssesmentResult result = new UserAssesmentResult(userId, assesmentId, module, right, wrong);
                	            session.save(result);
                            }
                            right = 0;
                            wrong = 0;
                		}
                		module = moduleId;
                	}
                	if(((Integer)data[1]).intValue() == AnswerData.CORRECT) {
                		right = ((Integer)data[2]).intValue();
                	}
                	if(((Integer)data[1]).intValue() == AnswerData.INCORRECT) {
                		wrong = ((Integer)data[2]).intValue();
                	}
                }
                Query q2 = session.createQuery("SELECT r FROM UserAssesmentResult r WHERE r.assesment = "+assesmentId+" AND r.login = '"+userId+"' AND r.type = "+module);
                Iterator it2 = q2.list().iterator();
                if(it2.hasNext()) {
                	UserAssesmentResult r = (UserAssesmentResult)it2.next();
                	r.setCorrect(right);
                	r.setIncorrect(wrong);
                	session.update(r);
                }else {
    	            UserAssesmentResult result = new UserAssesmentResult(userId, assesmentId, module, right, wrong);
    	            session.save(result);
                }
            }*/
        } catch (Exception e) {
            handler.getException(e, "setEndDate", userSessionData.getFilter().getLoginName());
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator"
     */
    public void setElearningEnabled(String user, Integer assessment, boolean result, UserSessionData userSessionData) throws Exception {
        try {
            if (userSessionData == null) {
                throw new DeslogedException("setElearningEnabled","session = null");
            }
            if (user == null) {
                throw new InvalidDataException("setElearningEnabled","user is null");
            }
            
            Session session = HibernateAccess.currentSession();
            UserAssesment userAssessment = (UserAssesment)session.load(UserAssesment.class,new UserAssesmentPK(user,assessment));
            userAssessment.setElearningEnabled(result);
            session.update(user);
            
        } catch (Exception e) {
            handler.getException(e, "setElearningEnabled", userSessionData.getFilter().getLoginName());
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,basf_assessment"
     */
    public UserData createBasfUser(Integer assesment,String copyUser,UserSessionData userSessionData) throws Exception {
    	String login = null;
        try {
	        Session session = HibernateAccess.currentSession();
	        Query q = session.createSQLQuery("SELECT COUNT(*) AS c FROM userassesments WHERE assesment IN (SELECT id FROM assesments WHERE corporation = "+CorporationData.BASF+")").addScalar("c", Hibernate.INTEGER);
	    	login = "BASFUser_"+q.uniqueResult();
	    	User user = new User();
	    	user.setLoginName(login);
			String pass = new MD5().encriptar(login);
	    	user.setPassword(pass);
	    	if(copyUser == null) {
		    	user.setFirstName("BASF");
		    	user.setLastName("Assessment");
	    	}else {
	    		User first = (User)session.load(User.class, copyUser);
	    		user.setFirstName(first.getFirstName());
	    		user.setLastName(first.getLastName());
	    		user.setBirthDate(first.getBirthDate());
	    		user.setExtraData(first.getExtraData());
	    		user.setVehicle(first.getVehicle());
	    		user.setExtraData2(first.getExtraData2());
	    		user.setExtraData3(first.getExtraData3());
	    	}
	    	user.setRole("systemaccess");
	    	user.setLanguage("pt");
	    	Calendar birth = Calendar.getInstance();
	    	birth.add(Calendar.YEAR, -18);
	    	user.setBirthDate(birth.getTime());
	    	session.save(user);
	    	
	    	UserAssesment userA = new UserAssesment(user,(Assesment)session.load(Assesment.class, assesment));
	    	userA.setCreationDate(new Date());
	    	userA.setFdmRegistry(null);
	    	session.save(userA);
	        return user.getUserData();
        } catch (Exception e) {
            handler.getException(e, "createBasfUser", userSessionData.getFilter().getLoginName());
        }
        return null;
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,basf_assessment"
     */
    public UserData createBasfUserAssessment(String login,Integer assesment,UserSessionData userSessionData) throws Exception {
        try {
	        Session session = HibernateAccess.currentSession();
	        User user = (User)session.load(User.class, login);
	    	UserAssesment userA = new UserAssesment(user,(Assesment)session.load(Assesment.class, assesment));
	    	userA.setCreationDate(new Date());
	    	userA.setFdmRegistry(null);
	    	session.save(userA);
	    	
	    	return user.getUserData();
        } catch (Exception e) {
            handler.getException(e, "createBasfUserAssessment", userSessionData.getFilter().getLoginName());
        }
        return null;
    }

    /**
     * @ejb.interface-method
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public void acceptTerms(UserSessionData userSessionData) throws Exception {
        try{    
	        Session session = HibernateAccess.currentSession();
	        User user = (User)session.load(User.class, userSessionData.getFilter().getLoginName());
	    	user.setAcceptTerms(Calendar.getInstance().getTime());
	    	session.update(user);
        } catch (Exception e) {
            handler.getException(e, "acceptTerms", userSessionData.getFilter().getLoginName());
        } 
    }

    /**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "systemaccess,administrator,resetpassword,accesscode,pepsico_candidatos,basf_assessment,clientreporter,cepareporter"
	 */
    public UserPassword getUserPassword(String loginname) throws Exception {
    	UserPassword result = new UserPassword();
        Class.forName("org.postgresql.Driver");
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/assesment","postgres","pr0v1s0r1A");
        Statement st =conn.createStatement();

        ResultSet set = st.executeQuery("SELECT loginname, password, email, 'mailSended' FROM userpassword WHERE lower(loginname) = '"+loginname.toLowerCase()+"'");

        while(set.next()) {
        	result.setEmail(set.getString("email"));
        	result.setLoginName(set.getString("loginName"));
        	result.setMailSended(set.getBoolean("mailSended"));
        	result.setPassword(set.getString("password"));
        }

        conn.close();
        return result;
    }

    /**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "systemaccess,administrator"
	 */
    public void userPasswordCreate(UserPassword userPassword) throws Exception {
    	Session session = HibernateAccess.currentSession();
    	session.save(userPassword);
    	session.close();
    }
    
    /**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "systemaccess,administrator"
	 */
    public void savePSIaux(String answers, int[] values, UserSessionData userSessionData) throws Exception {
    	try {
	    	Session session = HibernateAccess.currentSession();
	    	if(values != null) {
		    	String key = String.valueOf(values[0]);
		    	for(int i = 1; i < 6; i++)
		    		key += "-"+values[i];
		    	Query q = session.createSQLQuery("INSERT INTO psiaux VALUES ('"+answers+"','"+key+"')");
		    	q.executeUpdate();
	    	}
        } catch (Exception e) {
            handler.getException(e, "savePSIaux", userSessionData.getFilter().getLoginName());
        } 
    }
    
    /**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "systemaccess,administrator"
	 */
    public void failAssessment(Integer assessmentId, String user, Date date, UserSessionData userSessionData) throws Exception {
    	try {
	    	Session session = HibernateAccess.currentSession();
	    	FailedAssessment fa = new FailedAssessment();
	    	fa.setAssessment(assessmentId);
	    	fa.setLoginName(user);
	    	if(date == null) {
	    		fa.setEndDate(Calendar.getInstance().getTime());
	    	}else {
	    		fa.setEndDate(date);
	    	}
	    	session.save(fa);
        } catch (Exception e) {
            handler.getException(e, "savePSIaux", userSessionData.getFilter().getLoginName());
        } 
    }

    /**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "systemaccess,administrator"
	 */
    public void saveMultiAnswer(Integer questionId, Integer answerId, Integer multiId, UserSessionData userSessionData) throws Exception {
    	try {
	    	Session session = HibernateAccess.currentSession();
	    	UserSpecificAnswer sp = new UserSpecificAnswer(questionId, answerId, multiId);
	    	session.save(sp);
        } catch (Exception e) {
            handler.getException(e, "saveMultiAnswer", userSessionData.getFilter().getLoginName());
        } 
    }

    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator"
     */
    public void deleteGroupUsers(Integer group, String user, UserSessionData userSessionData) throws Exception {
        try {
            Session session = HibernateAccess.currentSession();
            
        	Query sqlUpdate = session.createQuery("SELECT ug from UserGroup ug where ug.pk.user.loginName = '"+user+"' AND ug.pk.groupId.id = "+group+"");
        	Iterator it = sqlUpdate.list().iterator();
        	if(it.hasNext()) {
        		UserGroup ug = (UserGroup)it.next();
        		session.delete(ug);
        	}
        }catch (Exception e) {
            handler.getException(e,"deleteGroupUsers",userSessionData.getFilter().getLoginName());
        }

    }


    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator"
     */
    public void checkGroupAssesments(String user, UserSessionData userSessionData) throws Exception {
    	try {
	    	Session session = HibernateAccess.currentSession();
    		String sql = "SELECT ac.assesment FROM usergroups ug " + 
    				"JOIN categories c ON c.groupid = ug.groupid " + 
    				"JOIN assesmentcategories ac ON ac.category = c.id " + 
    				"WHERE loginname = '"+user+"' " + 
    				"AND assesment NOT IN (SELECT assesment FROM userassesments WHERE loginname = '"+user+"') "
    				+ "AND ug.groupid NOT IN ("+GroupData.GRUPO_MODELO+")";
    		Query q = session.createSQLQuery(sql).addScalar("assesment", Hibernate.INTEGER);
    		Iterator it = q.list().iterator();
    		while(it.hasNext()) {
    			Integer assesmentId = (Integer)it.next();
    			UserAssesment ua = new UserAssesment(user,assesmentId);
    	    	ua.setCreationDate(new Date());
    	    	ua.setFdmRegistry(null);
    			session.save(ua);
    		}
    	}catch (Exception e) {
            handler.getException(e,"checkGroupAssesments",userSessionData.getFilter().getLoginName());
    	}
    }


    /**
	 * @ejb.interface-method
     * @ejb.permission role-name = "administrator,accesscode,systemaccess"
	 * 
	 * @param data
	 * @param userRequest
	 * @throws Exception
	 */
	public void userChangeGroup(String user,Integer groupOld, Integer groupNew, UserSessionData userRequest) throws Exception {
        Session session = HibernateAccess.currentSession();
        
        UserGroup ugOld = (UserGroup) session.load(UserGroup.class, new UserGroupPK(user, groupOld));
        session.delete(ugOld);
        
		User u = (User) session.load(User.class, user);
		Group g = (Group) session.load(Group.class, groupNew);
		
		UserGroup ug = new UserGroup(u, g);
		session.save(ug);
		
		String sql = "SELECT DISTINCT ac.assesment FROM usergroups ug " + 
				"JOIN categories c ON c.groupid = ug.groupid " + 
				"JOIN assesmentcategories ac ON ac.category = c.id " + 
				"WHERE ug.groupid = " + groupNew +
				" AND assesment NOT IN (SELECT assesment FROM userassesments WHERE loginname = '"+user+"')";
		Query q = session.createSQLQuery(sql).addScalar("assesment", Hibernate.INTEGER);
		Iterator it = q.list().iterator();
		while(it.hasNext()) {
			Integer assesmentId = (Integer)it.next();
			UserAssesment ua = new UserAssesment(user,assesmentId);
			ua.setCreationDate(new Date());
			ua.setFdmRegistry(null);
			session.save(ua);
		}
	}
	/**
	 * @ejb.interface-method
     * @ejb.permission role-name = "administrator,userdelete"
	 */
	public void deleteSendedReport(String user, UserSessionData userSessionData) throws Exception {
		try {
			//Check integrity data
			if (user == null) {
				throw new InvalidDataException("userDelete","user ==null");
			}
			if (userSessionData == null) {
				throw new DeslogedException("userDelete","session = null");
			}
			if (!userExist(user, userSessionData)) {
				throw new InvalidDataException("Class persistence.user.UserABM(userDelete), user not exist","generic.user.notexist");
			}

            Session session = HibernateAccess.currentSession();
            Query query = session.createSQLQuery("DELETE FROM sendedreports WHERE login = '"+user+"' AND certificate=false AND sended=true");
            query.executeUpdate();

		} catch (Exception e) {
            handler.getException(e, "deleteSendedReport", userSessionData.getFilter().getLoginName());
        }		
	}
	
}