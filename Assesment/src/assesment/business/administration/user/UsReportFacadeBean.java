/*
 * Created on 22-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.business.administration.user;

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

import javax.ejb.CreateException;
import javax.ejb.SessionBean;

import assesment.business.AssesmentAccess;
import assesment.business.util.ExceptionHandler;
import assesment.communication.administration.MultiAnswerUserData;
import assesment.communication.administration.UserAnswerData;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.assesment.GroupData;
import assesment.communication.exception.CommunicationProblemException;
import assesment.communication.exception.DeslogedException;
import assesment.communication.exception.InvalidDataException;
import assesment.communication.language.Text;
import assesment.communication.question.AnswerData;
import assesment.communication.report.AssessmentReportData;
import assesment.communication.report.ModuleReportData;
import assesment.communication.report.QuestionReportData;
import assesment.communication.report.ResultReportDataSource;
import assesment.communication.report.TotalResultReportDataSource;
import assesment.communication.report.UserAdvanceJJ;
import assesment.communication.report.UserReportData;
import assesment.communication.report.UserResultReportDataSource;
import assesment.communication.report.UsersReportDataSource;
import assesment.communication.user.UserData;
import assesment.communication.util.GenerateReport;
import assesment.communication.util.ListResult;
import assesment.persistence.administration.tables.AssessmentUserData;
import assesment.persistence.assesment.AssesmentReport;
import assesment.persistence.assesment.AssesmentReportUtil;
import assesment.persistence.user.UsReport;
import assesment.persistence.user.UsReportHome;
import assesment.persistence.user.UsReportUtil;
import assesment.persistence.util.Util;

/**
 * @ejb.bean name="UsReportFacade" display-name="Name for UsReportFacade"
 *           description="Description for UsReportFacade"
 *           jndi-name="ejb/UsReportFacade" type="Stateless" view-type="remote"
 * 
 * @ejb.ejb-ref ejb-name = "UsReport" ref-name = "ejb/UsReport" view-type =
 *              "remote"
 * 
 * @jboss.ejb-ref-jndi jndi-name = "ejb/UsReport" ref-name = "UsReport"
 * 
 * @ejb.ejb-ref ejb-name = "AssesmentReport" ref-name = "ejb/AssesmentReport" view-type =
 *              "remote"
 * 
 * @jboss.ejb-ref-jndi jndi-name = "ejb/AssesmentReport" ref-name = "AssesmentReport"
 * 
 * @ejb.util generate="physical"
 *  
 */
public abstract class UsReportFacadeBean implements SessionBean {	
    private ExceptionHandler handler = new ExceptionHandler(UsABMFacadeBean.class);
    
	/**
	 * Default create method
	 * 
	 * @throws CreateException
	 * @ejb.create-method
     * @ejb.permission role-name = "systemaccess,administrator,resetpassword,accesscode,pepsico_candidatos,basf_assessment,clientreporter,cepareporter,webinar"
	 */
	public void ejbCreate() throws CreateException {
		// TODO Auto-generated method stub

	}

	/**
	 * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator,resetpassword,accesscode,pepsico_candidatos,basf_assessment,clientreporter,cepareporter,webinar"
	 * 
	 * @param userPK
	 * @return  UserData
 	 * @throws InvalidDataException
	 * @throws CommunicationProblemException
	 * @throws DeslogedException
	 */
	public UserData findUserByPrimaryKey(String userPK, UserSessionData userSessionData) throws Exception {
		
		UserData data=null;
		UsReport userReport=null;
		try {
			if (userPK == null) {
				throw new InvalidDataException("Class business.user.UserReportFacade(findUserByPrimaryKey), primary Key is empty","generic.user.");
			}
			
			if(userPK==null){
				throw new InvalidDataException("Class business.user.UserReportFacade(findUserByPrimaryKey), nick name is empty","generic.user.userdata.nickname.isempty");
			}

			UsReportHome urHome = UsReportUtil.getHome();
			userReport = urHome.create();
			data=userReport.findUserByPrimaryKey(userPK,null,userSessionData);
			
			return data;			
		} catch (Exception exception) {
            handler.handleException("findUserByPrimaryKey", exception);
        } 
		return null;		
	}
	
	/**
	 * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator,resetpassword,accesscode"
	 */
	public Collection findAbitabUser(String userPK, UserSessionData userSessionData) throws Exception {
		
		try {
			if (userPK == null) {
				throw new InvalidDataException("findAbitabUser","generic.user.");
			}
			
			if(userPK==null){
				throw new InvalidDataException("findAbitabUser","generic.user.userdata.nickname.isempty");
			}

			return UsReportUtil.getHome().create().findAbitabUser(userPK,userSessionData);
			
		} catch (Exception exception) {
            handler.handleException("findAbitabUser", exception);
        } 
		return new LinkedList();		
	}
	
	/**
	 * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator,resetpassword,accesscode"
	 */
	public Collection findAbitabUser(String userPK, String email, UserSessionData userSessionData) throws Exception {
		
		try {
			if (userPK == null) {
				throw new InvalidDataException("findAbitabUser","generic.user.");
			}
			
			if(userPK==null){
				throw new InvalidDataException("findAbitabUser","generic.user.userdata.nickname.isempty");
			}

			return UsReportUtil.getHome().create().findAbitabUser(userPK,email,userSessionData);
			
		} catch (Exception exception) {
            handler.handleException("findAbitabUser", exception);
        } 
		return new LinkedList();		
	}
	
	/**
	 * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
	 * @throws Exception
	 */
	public Collection<UserData> findList(HashMap<String, String> parameters, UserSessionData userSessionData) throws Exception {
		try {
			return UsReportUtil.getHome().create().findList(parameters,userSessionData);
		} catch (Exception exception) {
            handler.handleException("findUserList", exception);
        } 
		return null;		
	}
	
	/**
	 * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
	 * @throws Exception
	 */
	public Collection<UserData> findListWrongCode(HashMap<String, String> parameters, UserSessionData userSessionData) throws Exception {
		try {
			return UsReportUtil.getHome().create().findListWrongCode(parameters,userSessionData);
		} catch (Exception exception) {
            handler.handleException("findUserList", exception);
        } 
		return null;		
	}
	
	/**
	 * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator"
	 * @param attrName
	 * @param attrValue
	 * @param first
	 * @param count
	 * @return
	 * @throws InvalidDataException
	 * @throws CommunicationProblemException
	 * @throws DeslogedException
	 */
	public ListResult findUserList(String attrName, String attrValue, int first, int count, 
			UserSessionData userSessionData) throws Exception {
		try {
			if (attrName == null) {
				throw new InvalidDataException("findUserList", "AttrName == null");
			}
			UsReportHome userHome = UsReportUtil.getHome();
			UsReport userReport = userHome.create();
            int attribute = 0;
            if (attrName.equals("firstName")) {
                attribute = 0;
            }else if (attrName.equals("lastName")) {
                attribute = 1;
            }else if (attrName.equals("nick")) {
                attribute = 2;
            }else if (attrName.equals("mail")) {
                attribute = 3;
            }
			return userReport.findList(attrValue,userSessionData,attribute,first,count);
		} catch (Exception exception) {
            handler.handleException("findUserList", exception);
        } 
		return null;		
	}

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "resetpassword"
     */
    public UserData findUserByEmail(String email, UserSessionData userSessionData) throws Exception {
        try {
            if (email == null) {
                throw new InvalidDataException("findUserByEmail", "email == null");
            }
            UsReportHome userHome = UsReportUtil.getHome();
            UsReport userReport = userHome.create();
            return userReport.findUserByEmail(email, userSessionData);
        } catch (Exception exception) {
            handler.handleException("findUserByEmail", exception);
        } 
        return null;        
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,basf_assessment,systemaccess"
     */
    public UserData findBasfUser(String ng, String cpf, UserSessionData userSessionData) throws Exception {
    	try {
            UsReportHome userHome = UsReportUtil.getHome();
            UsReport userReport = userHome.create();
            return userReport.findBasfUser(ng, cpf, userSessionData);
        } catch (Exception exception) {
            handler.handleException("findBasfUser", exception);
        } 
        return null;  
    }
    /**
     * @throws Exception 
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public boolean isFirstAccess(String user) throws Exception {
        try {
            UsReportHome urHome = UsReportUtil.getHome();
            UsReport userReport = urHome.create();
            return userReport.isFirstAccess(user);            
        } catch (Exception exception) {
            handler.handleException("isFirstAccess", exception);
        }
        return false; 
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess,accesscode,pepsico_candidatos,basf_assessment,clientreporter,cepareporter"
     */
    public Collection findUserAssesments(String user,UserSessionData userSessionData) throws Exception {
        try {
            UsReportHome urHome = UsReportUtil.getHome();
            UsReport userReport = urHome.create();
            return userReport.findUserAssesments(user,true,userSessionData);            
        } catch (Exception exception) {
            handler.handleException("findUserAssesments", exception);
        }
        return null; 
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess,accesscode,pepsico_candidatos,basf_assessment,clientreporter,cepareporter"
     */
    public Collection findPendingUserAssesments(String user,UserSessionData userSessionData) throws Exception {
        try {
            UsReportHome urHome = UsReportUtil.getHome();
            UsReport userReport = urHome.create();
            return userReport.findUserAssesments(user,false,userSessionData);            
        } catch (Exception exception) {
            handler.handleException("findUserAssesments", exception);
        }
        return null; 
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess,accesscode,pepsico_candidatos,basf_assessment,clientreporter,cepareporter"
     */
    public Collection findTotalUserAssesments(String user,UserSessionData userSessionData) throws Exception {
        try {
            UsReportHome urHome = UsReportUtil.getHome();
            UsReport userReport = urHome.create();
            return userReport.findTotalUserAssesments(user,userSessionData);            
        } catch (Exception exception) {
            handler.handleException("findUserAssesments", exception);
        }
        return null; 
    }


    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess,accesscode,pepsico_candidatos,basf_assessment,clientreporter,cepareporter"
     */
    public Collection findWebinarParticipants(String code, String assesment,UserSessionData userSessionData) throws Exception {
        try {
            UsReportHome urHome = UsReportUtil.getHome();
            UsReport userReport = urHome.create();
            return userReport.findWebinarParticipants(code, assesment, userSessionData);            
        } catch (Exception exception) {
            handler.handleException("findWebinarParticipants", exception);
        }
        return null; 
    }


    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess,accesscode,pepsico_candidatos,basf_assessment,clientreporter,cepareporter"
     */
    public Collection findAllWebinarParticipants(String wbCode,String firstName,String lastName,UserSessionData userSessionData) throws Exception {
        try {
            UsReportHome urHome = UsReportUtil.getHome();
            UsReport userReport = urHome.create();
            return userReport.findAllWebinarParticipants(wbCode,firstName,lastName,userSessionData);            
        } catch (Exception exception) {
            handler.handleException("findAllWebinarParticipants", exception);
        }
        return null; 
    }
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess,accesscode,pepsico_candidatos,basf_assessment,clientreporter,cepareporter"
     */
    public Collection findUserReportAssesments(String user,UserSessionData userSessionData) throws Exception {
        try {
            UsReportHome urHome = UsReportUtil.getHome();
            UsReport userReport = urHome.create();
            return userReport.findUserReportAssesments(user,userSessionData);            
        } catch (Exception exception) {
            handler.handleException("findUserReportAssesments", exception);
        }
        return null; 
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public Collection findActiveAssesments(String user,UserSessionData userSessionData) throws Exception {
        try {
            UsReportHome urHome = UsReportUtil.getHome();
            UsReport userReport = urHome.create();
            return userReport.findActiveAssesments(user,userSessionData);            
        } catch (Exception exception) {
            handler.handleException("findActiveAssesments", exception);
        }
        return null; 
    }


    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public Collection findGroups(UserSessionData userSessionData) throws Exception {
        try {
            UsReportHome urHome = UsReportUtil.getHome();
            UsReport userReport = urHome.create();
            return userReport.findGroups(userSessionData);            
        } catch (Exception exception) {
            handler.handleException("findGroups", exception);
        }
        return null; 
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public UsersReportDataSource findUsersAssesment(Integer assesment, UserSessionData userSessionData,Text messages) throws Exception {
        try {
            AssesmentReport assesmentReport = AssesmentReportUtil.getHome().create();
            AssesmentAttributes attributes = assesmentReport.findAssesmentAttributes(assesment,userSessionData);
            UsReport userReport = UsReportUtil.getHome().create();
            UsersReportDataSource datasource = new UsersReportDataSource(userReport.getUserAssesmentsCount(attributes,userSessionData),assesmentReport.getAssesmentQuestionCount(attributes,userSessionData,true),messages,assesment);;
            return datasource;
        } catch (Exception exception) {
            handler.handleException("findUsersAssesment", exception);
        }
        return null; 
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public Collection<String[]> getUserAssesments(Integer driver, UserSessionData userSessionData) throws Exception {
        try {
            return UsReportUtil.getHome().create().getUserAssesments(driver,userSessionData);
        } catch (Exception exception) {
            handler.handleException("getUserAssesments", exception);
        }
        return new LinkedList<String[]>(); 
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public Integer[] getUserPsicoResult(String user,Integer assesment,UserSessionData userSessionData) throws Exception {
        UsReport userReport = UsReportUtil.getHome().create();
        return userReport.getUserPsicoResult(user,assesment,userSessionData);
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public ResultReportDataSource findGeneralResult(Integer assesment, UserSessionData userSessionData,Text messages) throws Exception {
        try {
            Hashtable hash = new Hashtable();
            UsReport userReport = UsReportUtil.getHome().create();
            Hashtable moduleCount = userReport.getModulesCount(assesment,userSessionData);
            AssesmentReport assesmentReport = AssesmentReportUtil.getHome().create();
            AssesmentAttributes assesmentAttr = assesmentReport.findAssesmentAttributes(assesment,userSessionData);
            Collection results = userReport.findGeneralResults(assesment,assesmentAttr.isPsitest(),userSessionData);
            if(results != null) {
                Iterator it = results.iterator();
                while(it.hasNext()) {
                    Object[] data = (Object[])it.next();
                    Integer moduleId = (Integer)data[0];
                    Object[] dataResult =  {0,0,0,data[3],0,moduleId,0,0,0};
                    if(hash.containsKey(moduleId)) {
                        dataResult =  (Object[])hash.get(moduleId);
                    }else {
                        hash.put(moduleId,dataResult);
                    }
                    double value = ((Integer)data[2]).doubleValue() / ((Integer)moduleCount.get(moduleId)).doubleValue();
                    if(value*100 < assesmentAttr.getYellow().doubleValue()) {
                        dataResult[0] = new Integer(((Integer)dataResult[0]).intValue() + 1);
                        dataResult[6] = new Integer(((Integer)dataResult[6]).intValue() + ((Integer)data[2]).intValue());
                    }else if(value*100 < assesmentAttr.getGreen().doubleValue()) {
                        dataResult[1] = new Integer(((Integer)dataResult[1]).intValue() + 1);
                        dataResult[7] = new Integer(((Integer)dataResult[7]).intValue() + ((Integer)data[2]).intValue());
                    }else {
                        dataResult[2] = new Integer(((Integer)dataResult[2]).intValue() + 1);
                        dataResult[8] = new Integer(((Integer)dataResult[8]).intValue() + ((Integer)data[2]).intValue());
                    }
                    dataResult[4] = new Integer(((Integer)dataResult[1]).intValue() + ((Integer)data[2]).intValue());
                }
                if(assesmentAttr.isPsitest()) {
                    Collection psicoResults = userReport.getPsicoResult(assesment,userSessionData);
                    it = psicoResults.iterator();
                    Object[] dataResult =  {0,0,0,"assesment.module.psicologic",0,0,0,0,0};
                    while(it.hasNext()) {
                        Object[] data = (Object[])it.next();
                        double sum = 0;
                        for(int i = 1; i < 7; i++) {
                            sum += ((Integer)data[i]).doubleValue();
                        }
                        sum = sum / 6.0;
                        if(sum >= 4.0) {
                            dataResult[0] = new Integer(((Integer)dataResult[0]).intValue() + 1);
                        }else if(sum >= 3.0) {
                            dataResult[1] = new Integer(((Integer)dataResult[1]).intValue() + 1);
                        }else {
                            dataResult[2] = new Integer(((Integer)dataResult[2]).intValue() + 1);
                        }
                    }
                    hash.put(new Integer(0),dataResult);
                }
            }
            ResultReportDataSource datasource = new ResultReportDataSource(hash,moduleCount,messages,assesment);
            return datasource;
        } catch (Exception exception) {
            handler.handleException("findGeneralResult", exception);
        }
        return null; 
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public UserResultReportDataSource findUsersResult(Integer assesment, String user, UserSessionData userSessionData,Text messages) throws Exception {
        try {
            Hashtable hash = new Hashtable();
            UsReport userReport = UsReportUtil.getHome().create();
            Collection results = userReport.findUserResults(assesment,user,userSessionData);
            if(results != null) {
                Iterator it = results.iterator();
                while(it.hasNext()) {
                    Object[] data = (Object[])it.next();
                    Integer moduleId = (Integer)data[0];
                    Object[] dataResult =  {data[1],0,0};
                    if(hash.containsKey(moduleId)) {
                        dataResult =  (Object[])hash.get(moduleId);
                    }else {
                        hash.put(moduleId,dataResult);
                    }
                    if(((Integer)data[2]).intValue() == AnswerData.INCORRECT) {
                        dataResult[1] = new Integer(((Integer)dataResult[1]).intValue() + ((Integer)data[3]).intValue());
                    }else {
                        dataResult[2] = new Integer(((Integer)dataResult[2]).intValue() + ((Integer)data[3]).intValue());
                    }
                }
            }
            UserResultReportDataSource datasource = new UserResultReportDataSource(hash,messages);
            return datasource;
        } catch (Exception exception) {
            handler.handleException("findUsersResult", exception);
        }
        return null; 
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public HashMap findUsersResults(Integer assesment, String user, UserSessionData userSessionData) throws Exception {
        HashMap hash = new HashMap();
        try {
            UsReport userReport = UsReportUtil.getHome().create();
            Collection results = userReport.findUserResults(assesment,user,userSessionData);
            if(results != null) {
                Iterator it = results.iterator();
                while(it.hasNext()) {
                    Object[] data = (Object[])it.next();
                    Integer moduleId = (Integer)data[0];
                    Object[] dataResult =  {data[1],0,0};
                    if(hash.containsKey(moduleId)) {
                        dataResult =  (Object[])hash.get(moduleId);
                    }else {
                        hash.put(moduleId,dataResult);
                    }
                    if(((Integer)data[2]).intValue() == AnswerData.INCORRECT) {
                        dataResult[1] = new Integer(((Integer)dataResult[1]).intValue() + ((Integer)data[3]).intValue());
                    }else {
                        dataResult[2] = new Integer(((Integer)dataResult[2]).intValue() + ((Integer)data[3]).intValue());
                    }
                }
            }
        } catch (Exception exception) {
            handler.handleException("findUsersResult", exception);
        }
        return hash;
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public Collection getUserModules(String user,Integer assesment,UserSessionData userSessionData,boolean feedback) throws Exception {
        try {
            if(userSessionData == null) {
                throw new DeslogedException("getUserModules","session is closed");
            }
            if(user == null) {
                throw new InvalidDataException("getUserModules","user is null");
            }
            if(assesment == null) {
                throw new InvalidDataException("getUserModules","assesment is null");
            }
            UsReport userReport = UsReportUtil.getHome().create();
            return userReport.getUserModules(user,assesment,userSessionData,feedback);
        } catch (Exception exception) {
            handler.handleException("getUserModules", exception);
        }
        return null; 
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public int[] getUserModule(String user,Integer module,UserSessionData userSessionData) throws Exception {
        try {
            if(userSessionData == null) {
                throw new DeslogedException("getUserModule","session is closed");
            }
            if(user == null) {
                throw new InvalidDataException("getUserModule","user is null");
            }
            if(module == null) {
                throw new InvalidDataException("getUserModule","module is null");
            }
            UsReport userReport = UsReportUtil.getHome().create();
            return userReport.getUserModule(user,module,userSessionData);
        } catch (Exception exception) {
            handler.handleException("getUserModule", exception);
        }
        return null; 
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     */
    public Collection findAssesmentUsers(String user,Integer assesment,UserSessionData userSessionData) throws Exception {
        try {
            if(userSessionData == null) {
                throw new DeslogedException("findAssesmentUsers","session is closed");
            }
            if(user == null) {
                throw new InvalidDataException("findAssesmentUsers","user is null");
            }
            if(assesment == null) {
                throw new InvalidDataException("findAssesmentUsers","assesment is null");
            }
            UsReport userReport = UsReportUtil.getHome().create();
            return userReport.findAssesmentUsers(user,assesment,userSessionData);
        } catch (Exception exception) {
            handler.handleException("findAssesmentUsers", exception);
        }
        return null; 
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     */
    public Collection findAssesmentPsiUsers(String user,Integer assesment,UserSessionData userSessionData) throws Exception {
        try {
            if(userSessionData == null) {
                throw new DeslogedException("findAssesmentUsers","session is closed");
            }
            if(user == null) {
                throw new InvalidDataException("findAssesmentUsers","user is null");
            }
            if(assesment == null) {
                throw new InvalidDataException("findAssesmentUsers","assesment is null");
            }
            UsReport userReport = UsReportUtil.getHome().create();
            return userReport.findAssesmentPsiUsers(user,assesment,userSessionData);
        } catch (Exception exception) {
            handler.handleException("findAssesmentUsers", exception);
        }
        return null; 
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     */
    public Collection findAssesmentPDUsers(String user,Integer assesment,UserSessionData userSessionData) throws Exception {
        try {
            if(userSessionData == null) {
                throw new DeslogedException("findAssesmentUsers","session is closed");
            }
            if(user == null) {
                throw new InvalidDataException("findAssesmentUsers","user is null");
            }
            if(assesment == null) {
                throw new InvalidDataException("findAssesmentUsers","assesment is null");
            }
            UsReport userReport = UsReportUtil.getHome().create();
            return userReport.findAssesmentPDUsers(user,assesment,userSessionData);
        } catch (Exception exception) {
            handler.handleException("findAssesmentUsers", exception);
        }
        return null; 
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public TotalResultReportDataSource findTotalResult(Integer assesment, UserSessionData userSessionData,Text messages) throws Exception {
        int red = 0;
        int green = 0;
        int yellow = 0;
        int prom_red = 0;
        int prom_green = 0;
        int prom_yellow = 0;
        int prom_total = 0;
        Integer assesmentCount = null;
        try {
            UsReport userReport = UsReportUtil.getHome().create();
            AssesmentReport assesmentReport = AssesmentReportUtil.getHome().create();
            AssesmentAttributes attributes = new AssesmentAttributes(assesment);
            assesmentCount = assesmentReport.getAssesmentQuestionCount(attributes,userSessionData,false);
            AssesmentAttributes assesmentAttr = assesmentReport.findAssesmentAttributes(assesment,userSessionData);
            Collection results = userReport.findTotalResults(assesment,assesmentAttr.isPsitest(),userSessionData);
            if(results != null) {
                Iterator it = results.iterator();
                while(it.hasNext()) {
                    Object[] data = (Object[])it.next();
                    double value = ((Integer)data[1]).doubleValue() / assesmentCount.doubleValue();
                    if(value*100 < assesmentAttr.getYellow().doubleValue()) {
                        prom_red += ((Integer)data[1]).intValue();
                        red++;
                    }else if(value*100 < assesmentAttr.getGreen().doubleValue()) {
                        prom_yellow += ((Integer)data[1]).intValue();
                        yellow++;
                    }else {
                        prom_green += ((Integer)data[1]).intValue();
                        green++;
                    }
                    prom_total += ((Integer)data[1]).intValue();
                }
                prom_red = (red == 0) ? 0 : prom_red / red;
                prom_green = (green == 0) ? 0 : prom_green / green;
                prom_yellow = (yellow == 0) ? 0 : prom_yellow / yellow;
                prom_total = (results.size() == 0) ? 0 :prom_total / results.size();
            }
        } catch (Exception exception) {
            handler.handleException("findGeneralResult", exception);
        }
        return new TotalResultReportDataSource(red,yellow,green,prom_red,prom_yellow,prom_green,prom_total,assesmentCount.intValue(),messages); 
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public boolean isPsicologicDone(String user,Integer assesment,UserSessionData userSessionData) throws Exception {
        boolean done = false;
        try {
            UsReport userReport = UsReportUtil.getHome().create();
            done = userReport.isPsicologicDone(user,assesment,userSessionData);
        } catch (Exception exception) {
            handler.handleException("isPsicologicDone", exception);
        }
        return done;
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public boolean isPersonalDataDone(String user,Integer assesment,UserSessionData userSessionData) throws Exception {
        boolean done = false;
        try {
            UsReport userReport = UsReportUtil.getHome().create();
            done = userReport.isPersonalDataModuleDone(user,assesment,userSessionData);
        } catch (Exception exception) {
            handler.handleException("isPersonalDataDone", exception);
        }
        return done;
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     */
    public Collection getCorporationsDC(UserSessionData userSessionData) throws Exception {
        try {
            UsReport userReport = UsReportUtil.getHome().create();
            return userReport.getCorporationsDC(userSessionData);
        } catch (Exception exception) {
            handler.handleException("getCorporationsDC", exception);
        }
        return null;
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public HashMap getModuleHashResult(String user, Integer module, UserSessionData userSessionData) throws Exception {
       	HashMap map = new HashMap();
    	Collection results = findModuleResult(user, module, userSessionData);
    	if(results != null) {
    		Iterator it = results.iterator();
    		while(it.hasNext()) {
    			UserAnswerData answerData = (UserAnswerData)it.next();
    			map.put(answerData.getQuestion(), answerData);
    		}
    	}
    	return map;
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public HashMap findPsicoModuleResult(String user, Integer assesment, UserSessionData userSessionData) throws Exception {
       	HashMap map = new HashMap();
       	UsReport userReport = UsReportUtil.getHome().create();
        Collection results = userReport.findPsicoModuleResult(user, assesment, userSessionData);
    	if(results != null) {
    		Iterator it = results.iterator();
    		while(it.hasNext()) {
    			UserAnswerData answerData = (UserAnswerData)it.next();
    			map.put(answerData.getQuestion(), answerData);
    		}
    	}
    	return map;
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public Collection findModuleResult(String user, Integer module, UserSessionData userSessionData) throws Exception {
        try {
            if(userSessionData == null) {
                throw new DeslogedException("findModuleResult","session is closed");
            }
            if(user == null) {
                throw new InvalidDataException("findModuleResult","user is null");
            }
            if(module == null) {
                throw new InvalidDataException("findModuleResult","module is null");
            }
            UsReport userReport = UsReportUtil.getHome().create();
            return userReport.findModuleResult(user,module,userSessionData);
        } catch (Exception exception) {
            handler.handleException("findModuleResult", exception);
        }
        return null; 
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public boolean isAssessmentDone(String user,Integer assesment,UserSessionData userSessionData,boolean isPSI) throws Exception {
        boolean done = false;
        try {
            UsReport userReport = UsReportUtil.getHome().create();
            done = userReport.isAssessmentDone(user,assesment,userSessionData);
            if(!done) {
                return false;
            }
            if(isPSI) {
                return userReport.isPsicologicDone(user,assesment,userSessionData);
            }
        } catch (Exception exception) {
            handler.handleException("isAssessmentDone", exception);
        }
        return done;
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public Date isAssessmentDone(UserSessionData userSessionData) throws Exception {
        try {
            UsReport userReport = UsReportUtil.getHome().create();
            return userReport.isAssessmentDone(userSessionData);
        } catch (Exception exception) {
            handler.handleException("isAssessmentDone", exception);
        }
        return null;
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public boolean hasResults(String user, Integer module, UserSessionData userSessionData) throws Exception {
        try {
            UsReport userReport = UsReportUtil.getHome().create();
            return userReport.hasResults(user,module,userSessionData);
        } catch (Exception exception) {
            handler.handleException("hasResults", exception);
        }
        return true;
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public Integer getUserCode(String user, UserSessionData userSessionData) throws Exception {
        try {
            UsReport userReport = UsReportUtil.getHome().create();
            return userReport.getUserCode(user,userSessionData);
        }catch(Exception e) {
            handler.handleException("getUserCode",e);
        }
        return null;
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess,accesscode"
     */
    public Integer getNextCode(String accesscode, UserSessionData userSessionData) throws Exception {
        try {
            UsReport userReport = UsReportUtil.getHome().create();
            return userReport.getNextCode(accesscode,userSessionData);
        }catch(Exception e) {
            handler.handleException("getNextCode",e);
        }
        return null;
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public boolean isPsiGreen(String user, Integer assessment, UserSessionData userSessionData) throws Exception {
        try {
            UsReport userReport = UsReportUtil.getHome().create();
            Integer[] values = userReport.getUserPsicoResult(user,assessment,userSessionData);
            double sum = 0.0;
            for(int i = 0; i < values.length; i++)
                sum += values[i].doubleValue();
            sum = sum / 6.0;
            return sum <= 3.0;
        }catch(Exception e) {
            handler.handleException("isPsiGreen",e);
        }
        return false;
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public boolean isPsiRed(String user, Integer assessment, UserSessionData userSessionData) throws Exception {
        try {
            UsReport userReport = UsReportUtil.getHome().create();
            Integer[] values = userReport.getUserPsicoResult(user,assessment,userSessionData);
            double sum = 0.0;
            for(int i = 0; i < values.length; i++)
                sum += values[i].doubleValue();
            sum = sum / 6.0;
            return sum > 4.0;
        }catch(Exception e) {
            handler.handleException("isPsiRed",e);
        }
        return false;
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public boolean isResultGreen(String user, Integer assessment, UserSessionData userSessionData) throws Exception {
        try {
            UsReport userReport = UsReportUtil.getHome().create();
            Integer[] values = userReport.getCompletedResultCount(user,assessment,userSessionData);
            AssesmentReport assessmentReport = AssesmentReportUtil.getHome().create();
            AssesmentAttributes assessmentAttr = assessmentReport.findAssesmentAttributes(assessment,userSessionData);
    		if(values[AnswerData.CORRECT] == 0 && values[AnswerData.INCORRECT] == 0) {
    			return true;
    		}
    		if(values[AnswerData.CORRECT] > 0 || values[AnswerData.INCORRECT] > 0) {
	            double value = values[AnswerData.CORRECT].doubleValue() / (values[AnswerData.CORRECT].doubleValue() + values[AnswerData.INCORRECT].doubleValue());
	            return (value*100.0) >= assessmentAttr.getGreen().doubleValue();
    		}
        }catch(Exception e) {
            handler.handleException("isResultGreen",e);
        }
        return false;
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public boolean isResultRed(String user, Integer assessment, UserSessionData userSessionData) throws Exception {
        try {
            UsReport userReport = UsReportUtil.getHome().create();
            Integer[] values = userReport.getResultCount(user,assessment,userSessionData);
            AssesmentReport assessmentReport = AssesmentReportUtil.getHome().create();
            AssesmentAttributes assessmentAttr = assessmentReport.findAssesmentAttributes(assessment,userSessionData);
            double value = values[AnswerData.CORRECT].doubleValue() / (values[AnswerData.CORRECT].doubleValue() + values[AnswerData.INCORRECT].doubleValue());
            return (value*100.0) < assessmentAttr.getYellow().doubleValue();
        }catch(Exception e) {
            handler.handleException("isResultRed",e);
        }
        return false;
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public Collection getChampionResult(Integer questionCount, String countries, Integer company, UserSessionData userSessionData) throws Exception {
        Collection list = new LinkedList();
        UsReport userReport = UsReportUtil.getHome().create();
        Iterator it = userReport.getChampionResult(questionCount,countries,company,userSessionData).iterator();
        while(it.hasNext()) {
            UserAdvanceJJ user = new UserAdvanceJJ((Object[])it.next());
            list.add(user);
        }
        return list;
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public String getRedirect(String user,Integer assesment,UserSessionData userSessionData) throws Exception {
        try {
            UsReport userReport = UsReportUtil.getHome().create();
            return userReport.getRedirect(user,assesment,userSessionData);
        }catch(Exception e) {
            handler.handleException("getRedirect",e);
        }
        return null;
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess,accesscode"
     */
    public Collection getAccessCode(int assesment,UserSessionData userSessionData) throws Exception {
        try {
            UsReport userReport = UsReportUtil.getHome().create();
            return userReport.getAccessCode(assesment,userSessionData);
        }catch(Exception e) {
            handler.handleException("getAccessCode",e);
        }
        return null;
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess,accesscode"
     */
    public Collection getAssessmentUsers(Integer assesment,UserSessionData userSessionData) throws Exception {
        try {
            UsReport userReport = UsReportUtil.getHome().create();
            return userReport.getAssessmentUsers(assesment,userSessionData);
        }catch(Exception e) {
            handler.handleException("getAssessmentUsers",e);
        }
        return null;
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess,accesscode"
     */
    public String getElearningURL(String user,Integer assesment,UserSessionData userSessionData) throws Exception {
        try {
            UsReport userReport = UsReportUtil.getHome().create();
            return userReport.getElearningURL(user,assesment,userSessionData);
        }catch(Exception e) {
            handler.handleException("getElearningURL",e);
        }
        return null;
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,pepsico_candidatos,basf_assessment"
     */
    public String[][] getCandidatos(UserSessionData userSessionData) throws Exception {
        String[][] values = new String[0][0];
    	try {
            AssesmentReport assesmentReport = AssesmentReportUtil.getHome().create();
            AssesmentAttributes attributes = new AssesmentAttributes(new Integer(AssesmentData.PEPSICO_CANDIDATOS));
            Integer count = assesmentReport.getAssesmentQuestionCount(attributes,userSessionData,true);

            UsReport userReport = UsReportUtil.getHome().create();
            HashMap<String, Calendar> map = userReport.getLastAccess(new Integer(AssesmentData.PEPSICO_CANDIDATOS), userSessionData);

            Collection list = userReport.getCandidatos(userSessionData);
            values = new String[list.size()][6];
            Iterator it = list.iterator();
            int index = 0;
            while(it.hasNext()) {
            	Object[] data = (Object[])it.next();
            	for(int i = 0; i < 4; i++) {
            		values[index][i] = (String)data[i+1];
            	}
            	Integer psi = (Integer) data[5];
            	Integer userCount = (Integer) data[6];
            	if(userCount == null || userCount == 0) {
            		values[index][4] = "No iniciado";
            	}else {
                	if(userCount.intValue() == count.intValue() && psi != null) {
                		values[index][4] = "Completado";
                	}else {
                		values[index][4] = "Incompleto";
                	}
            	}
            	if(map.containsKey((String)data[0])) {
            		values[index][5] = Util.formatDate(map.get((String)data[0]).getTime());
            	}else {
            		values[index][5] = "---";
            	}
            	index++;
            }
        }catch(Exception e) {
            handler.handleException("getCandidatos",e);
        }
        return values;
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public Integer getQuestionCount(String user, Integer module, Integer assesment, UserSessionData userSessionData) throws Exception {
       	try {
            UsReport userReport = UsReportUtil.getHome().create();
            return userReport.getQuestionCount(user,module,assesment,userSessionData);
        }catch(Exception e) {
            handler.handleException("getQuestionCount",e);
        }
        return new Integer(0);
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator,clientreporter,cepareporter"
     */
    public AssessmentReportData getAssessmentReport(String user, Integer assessment, UserSessionData userSessionData) throws Exception {
    	AssessmentReportData reportData = new AssessmentReportData();
    	try {
    		AssesmentReport assessmentReport = AssesmentReportUtil.getHome().create();
    		reportData.setAssessment(assessmentReport.findAssesment(assessment, userSessionData));
    		reportData.setAnswerTestCount(assessmentReport.getAssesmentQuestionCount(reportData.getAssessment(), userSessionData, false));
    		
    		UsReport userReport = UsReportUtil.getHome().create();
    		Collection userValues = userReport.getUserReport(user,assessment,userSessionData);
    		Iterator it = userValues.iterator();
    		HashMap<Integer,ModuleReportData> mapModules = new HashMap<Integer,ModuleReportData>();
    		boolean first = true;
    		while(it.hasNext()) {
    			Object[] data = (Object[])it.next();
    			if(first) {
    				UserReportData userReportData = new UserReportData(user,null,null,null,(Date)data[0]);
    				if(reportData.getAssessment().isPsitest()) {
    					userReportData.setValuePsi1(((Integer)data[5]).doubleValue()+((Integer)data[6]).doubleValue()+((Integer)data[7]).doubleValue());
    					userReportData.setValuePsi2(((Integer)data[8]).doubleValue()+((Integer)data[9]).doubleValue()+((Integer)data[10]).doubleValue());
    				}
    				reportData.getUsers().add(userReportData);
    				first = false;
    			}
    			if(!mapModules.containsKey(data[1])) {
    				mapModules.put((Integer)data[1], new ModuleReportData((Integer)data[1]));
    			}
				ModuleReportData moduleReportData = mapModules.get(data[1]);
				moduleReportData.addResult((Integer)data[3]);
				if(data[3].equals(AnswerData.INCORRECT)) {
					QuestionReportData questionData = new QuestionReportData((Integer)data[4],null,(Integer)data[2]);
					questionData.setAnswer((Integer)data[11]);
					moduleReportData.addWrongQuestion(questionData);
				}
    		}
    		Iterator<ModuleReportData> itModules = mapModules.values().iterator();
    		while(itModules.hasNext()) {
    			reportData.addModule(itModules.next());
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
            handler.handleException("getAssessmentReport", e);
		}
    	return reportData;
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator,clientreporter,cepareporter"
     */
    public Integer terms(Integer assessment, String login, UserSessionData userSessionData) throws Exception {
  	  try {
  		  return UsReportUtil.getHome().create().terms(assessment, login, userSessionData);
  	  } catch (Exception e) {
  		  e.printStackTrace();
  		  handler.handleException("terms", e);
  	  }
  	  return 0;
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator,clientreporter,cepareporter"
     */
    public Integer terms(String login, UserSessionData userSessionData) throws Exception {
  	  try {
  		  return UsReportUtil.getHome().create().terms(login, userSessionData);
  	  } catch (Exception e) {
  		  e.printStackTrace();
  		  handler.handleException("terms", e);
  	  }
  	  return 0;
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator,clientreporter,cepareporter"
     */
    public int getSendedReportId(String loginname, Integer assessment,UserSessionData userSessionData) throws Exception {
    	  try {
      		  return UsReportUtil.getHome().create().getSendedReportId(loginname, assessment, userSessionData);
      	  } catch (Exception e) {
      		  e.printStackTrace();
      		  handler.handleException("terms", e);
      	  }
      	  return 0;
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
  	  		list.addAll(UsReportUtil.getHome().create().getUserList(value, userSessionData));
  	  	} catch (Exception e) {
  	  		e.printStackTrace();
  	  		handler.handleException("terms", e);
  	  	}
  	  	return list;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name =
	 *                 "systemaccess,administrator,clientreporter,cepareporter"
	 */
	public String[] getPSIanswers(String loginname, Integer assessment, UserSessionData userSessionData) throws Exception {
        try{
        	return UsReportUtil.getHome().create().getPSIanswers(loginname, assessment,userSessionData);
        } catch (Exception e) {
            handler.handleException("getPSIanswers", e);
        }
        return null;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "systemaccess, administrator"
	 */
	public String checkDependencies(String loginname, Integer assessment, UserSessionData userSessionData) throws Exception {
        try{
        	return UsReportUtil.getHome().create().checkDependencies(loginname, assessment,userSessionData);
        } catch (Exception e) {
            handler.handleException("checkDependencies", e);
        }
        return null;
	}


	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "systemaccess, administrator"
	 */
	public HashMap<Integer,Date> getUserAssessmentStatus(String user, UserSessionData userSessionData) throws Exception {
        try{
        	return UsReportUtil.getHome().create().getUserAssessmentStatus(user,userSessionData);
        } catch (Exception e) {
            handler.handleException("checkDependencies", e);
        }
        return new HashMap<Integer,Date>();
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator, systemaccess"
	 * @throws Exception
	 */
	public Collection<UserData> findGroupUsers(Integer group, UserSessionData userSessionData) throws Exception {
        try{
        	return UsReportUtil.getHome().create().findGroupUsers(group,userSessionData);
        } catch (Exception e) {
            handler.handleException("checkDependencies", e);
        }
        return new LinkedList<UserData>();
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator, systemaccess"
	 * @throws Exception
	 */
	public HashMap<Integer, Integer> getMultiAssessmentResult(Integer multiId, UserSessionData userSessionData) throws Exception {
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        try{
        	map = UsReportUtil.getHome().create().getMultiAssessmentResult(multiId,userSessionData);
        } catch (Exception e) {
            handler.handleException("getMultiAssessmentResult", e);
        }
        return map;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator, systemaccess"
	 * @throws Exception
	 */
	public boolean hasErrors(Integer multiId, UserSessionData userSessionData) throws Exception {
        try{
        	return UsReportUtil.getHome().create().hasErrors(multiId,userSessionData);
        } catch (Exception e) {
            handler.handleException("hasErrors", e);
        }
        return false;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator, systemaccess, clientreporter"
	 * @throws Exception
	 */
	public Collection<MultiAnswerUserData> findMultiAnswerUsers(Integer assessmentId, Date from, Date to, UserSessionData userSessionData) throws Exception {
		Collection<MultiAnswerUserData> list = new LinkedList<MultiAnswerUserData>();
        try{
        	HashMap<Integer, MultiAnswerUserData> map = new HashMap<Integer, MultiAnswerUserData>();
        	Collection values = UsReportUtil.getHome().create().findMultiAnswerUsers(assessmentId, from, to, userSessionData);
        	Iterator it = values.iterator();
        	while(it.hasNext()) {
        		Object[] data = (Object[]) it.next();
        		Integer id = (Integer)data[0];
        		if(map.containsKey(id)) {
        			map.get(id).addAnswer(data);
        		}else {
        			map.put(id, new MultiAnswerUserData(data));
        		}
        	}
        	list.addAll(map.values());
        } catch (Exception e) {
            handler.handleException("findMultiAnswerUsers", e);
        }
        return list;
	}


    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public Integer getFailedAssesments(String user, Integer assesment, UserSessionData userSessionData) throws Exception {
        try{
        	return UsReportUtil.getHome().create().getFailedAssesments(user,assesment,userSessionData);
        } catch (Exception e) {
            handler.handleException("getFailedAssesments", e);
        }
        return 0;
   }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     */
    public HashMap<String, Collection<UserData>> getGroupUsers(Integer group, UserSessionData userSessionData) throws Exception {
        try{
        	return UsReportUtil.getHome().create().getGroupUsers(group, userSessionData);
        } catch (Exception e) {
            handler.handleException("getGroupUsers", e);
        }
        return new HashMap<String, Collection<UserData>>();
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     */
    public HashMap<String, Integer> getUserGroupCount(Integer group, UserSessionData userSessionData) throws Exception {
        try{
        	return UsReportUtil.getHome().create().getUserGroupCount(group, userSessionData);
        } catch (Exception e) {
            handler.handleException("getUserGroupCount", e);
        }
        return new HashMap<String, Integer>();
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator,accesscode"
     */
    public boolean userExist(String login, UserSessionData userSessionData) throws Exception {
        try{
        	return UsReportUtil.getHome().create().userExist(login, userSessionData);
        } catch (Exception e) {
            handler.handleException("userExist", e);
        }
        return false;
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator,accesscode"
     */
    public boolean userExistsIdNumber(String idNumber, UserSessionData userSessionData) throws Exception {
        try{
        	return UsReportUtil.getHome().create().userExistsIdNumber(idNumber, userSessionData);
        } catch (Exception e) {
            handler.handleException("userExist", e);
        }
        return false;
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator,accesscode,clientreporter"
     */
    public GroupData findUserGroup(String user, UserSessionData userSessionData) throws Exception {
    	try {
        	return UsReportUtil.getHome().create().findUserGroup(user,userSessionData);
        } catch (Exception e) {
            handler.handleException("findUserGroup", e);
        }
    	return null;
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator,accesscode,clientreporter"
     */
    public Integer cediUser(String user, UserSessionData userSessionData) throws Exception {
    	try {
        	return UsReportUtil.getHome().create().cediUser(user,userSessionData);
        } catch (Exception e) {
            handler.handleException("cediUser", e);
        }
    	return null;
    }
    
	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator, systemaccess"
	 * @throws Exception
	 */
	public Collection<UserData> findCediUsers(Integer cedi, UserSessionData userSessionData) throws Exception {
        try{
        	return UsReportUtil.getHome().create().findCediUsers(cedi,userSessionData);
        } catch (Exception e) {
            handler.handleException("findCediUsers", e);
        }
        return new LinkedList<UserData>();
	}
    
	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator, systemaccess"
	 * @throws Exception
	 */
	public Collection<UserData> findCediUsers(Integer[] cedis, String cedi, String firstname, String lastname, String userName, UserSessionData userSessionData) throws Exception {
        try{
        	return UsReportUtil.getHome().create().findCediUsers(cedis, cedi, firstname, lastname, userName, userSessionData);
        } catch (Exception e) {
            handler.handleException("findCediUsers", e);
        }
        return new LinkedList<UserData>();
	}


	   /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator"
     */
    public String getCurrentTimacUser(String cpf, UserSessionData userSessionData) throws Exception {
    	try{
        	return UsReportUtil.getHome().create().getCurrentTimacUser(cpf, userSessionData);
        } catch (Exception e) {
            handler.handleException("getCurrentTimacUser", e);
        }
    	return null;
    }
	   /**
  * @ejb.interface-method
  * @ejb.permission role-name = "systemaccess,administrator"
  */
 public boolean validateWebinarCode(String webinarCode, UserSessionData userSessionData) throws Exception {
 	try{
     	return UsReportUtil.getHome().create().validateWebinarCode(webinarCode, userSessionData);
     } catch (Exception e) {
         handler.handleException("validateWebinarCode", e);
     }
 	return false;
 }
 
 /**
  * @ejb.interface-method
  * @ejb.permission role-name = "administrator,systemaccess,accesscode,pepsico_candidatos,basf_assessment,clientreporter,cepareporter"
  */
 public AssessmentUserData getAssessmentUserData(String user, String code, String assesment,UserSessionData userSessionData) throws Exception {
     try {
         UsReportHome urHome = UsReportUtil.getHome();
         UsReport userReport = urHome.create();
         return userReport.getAssessmentUserData(user, code, assesment,userSessionData);            
     } catch (Exception exception) {
         handler.handleException("getAssesmentUserData", exception);
     }
     return null; 
 }

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator, systemaccess"
	 * @throws Exception
	 */
	public Collection<UserData> findCediMissingUsers(Integer[] cedis, Integer type, UserSessionData userSessionData) throws Exception {
        try{
        	return UsReportUtil.getHome().create().findCediMissingUsers(cedis, type, userSessionData);
        } catch (Exception e) {
            handler.handleException("findCediUsers", e);
        }
        return new LinkedList<UserData>();
	}
	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "administrator, systemaccess"
	 * @throws Exception
	 */
	public Collection<UserData> findCediMissingUsers(String cedi, String firstName, String lastName, String userName, Integer[] cedis, Integer type, UserSessionData userSessionData) throws Exception {
        try{
        	return UsReportUtil.getHome().create().findCediMissingUsers(cedi,firstName, lastName, userName, cedis, type, userSessionData);
        } catch (Exception e) {
            handler.handleException("findCediUsers", e);
        }
        return new LinkedList<UserData>();
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
        try{
        	values = UsReportUtil.getHome().create().existTimacUser(id,userSessionData);
        } catch (Exception e) {
            handler.handleException("findUserByParent", e);
        }
        return values;
 	}
 	
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public UsersReportDataSource findUsersAssesmentbkp(Integer assesment, UserSessionData userSessionData,Text messages) throws Exception {
        try {
            AssesmentReport assesmentReport = AssesmentReportUtil.getHome().create();
            AssesmentAttributes attributes = assesmentReport.findAssesmentAttributesbkp(assesment,userSessionData);
            UsReport userReport = UsReportUtil.getHome().create();
            UsersReportDataSource datasource = new UsersReportDataSource(userReport.getUserAssesmentsCountbkp(attributes,userSessionData),assesmentReport.getAssesmentQuestionCountbkp(attributes,userSessionData,true),messages,assesment);
            return datasource;
        } catch (Exception exception) {
            handler.handleException("findUsersAssesmentbkp", exception);
        }
        return null; 
    }
    
}