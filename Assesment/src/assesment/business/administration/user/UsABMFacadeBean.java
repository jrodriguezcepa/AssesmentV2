package assesment.business.administration.user;

import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;

import org.apache.struts.upload.FormFile;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.csvreader.CsvReader;

import assesment.business.util.ExceptionHandler;
import assesment.communication.administration.UserAnswerData;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.exception.AlreadyExistsException;
import assesment.communication.exception.DeslogedException;
import assesment.communication.exception.InvalidDataException;
import assesment.communication.module.ModuleData;
import assesment.communication.question.QuestionData;
import assesment.communication.security.SecurityConstants;
import assesment.communication.user.UserData;
import assesment.communication.util.MD5;
import assesment.persistence.assesment.AssesmentABM;
import assesment.persistence.assesment.AssesmentABMUtil;
import assesment.persistence.assesment.AssesmentReport;
import assesment.persistence.assesment.AssesmentReportUtil;
import assesment.persistence.hibernate.HibernateAccess;
import assesment.persistence.user.UsABM;
import assesment.persistence.user.UsABMHome;
import assesment.persistence.user.UsABMUtil;
import assesment.persistence.user.UsReport;
import assesment.persistence.user.UsReportUtil;
import assesment.persistence.user.tables.UserPassword;

/**
 * @ejb.bean name = "UsABMFacade" jndi-name = "ejb/UsABMFacade" type =
 *           "Stateless" transaction-type = "Container"
 * 
 * @ejb.ejb-ref ejb-name = "UsABM" ref-name = "ejb/UsABM" view-type =
 *              "remote"
 * 
 * @jboss.ejb-ref-jndi jndi-name = "ejb/UsABM" ref-name = "UsABM"
 *  
 * @ejb.ejb-ref ejb-name = "UsReport" ref-name = "ejb/UsReport" view-type =
 *              "remote"
 * 
 * @jboss.ejb-ref-jndi jndi-name = "ejb/UsReport" ref-name = "UsReport"
 *  
 * @ejb.ejb-ref ejb-name = "AssesmentABM" ref-name = "ejb/AssesmentABM" view-type =
 *              "remote"
 * 
 * @jboss.ejb-ref-jndi jndi-name = "ejb/AssesmentReport" ref-name = "AssesmentReport"
 *  
 * @ejb.ejb-ref ejb-name = "AssesmentReport" ref-name = "ejb/AssesmentReport" view-type =
 *              "remote"
 * 
 * @jboss.ejb-ref-jndi jndi-name = "ejb/AssesmentReport" ref-name = "AssesmentReport"
 *  
 * @ejb.util generate="physical"
 * 
 */
public abstract class UsABMFacadeBean implements javax.ejb.SessionBean {
    private ExceptionHandler handler = new ExceptionHandler(UsABMFacadeBean.class);

	/**
	 * Create method
	 * 
	 * @ejb.create-method
     * @ejb.permission role-name = "administrator,systemaccess,accesscode,basf_assessment"	 
     */
	public void ejbCreate() {

	}
	
	/**
	 * @ejb.interface-method view-type = "remote"
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "administrator"
	 */
	public void userCreate(UserData data,Integer assesment,UserSessionData userSessionData) throws Exception {
		try {
			if (userSessionData == null) {
				throw new DeslogedException("userCreate","session = null");
			}
			if (data == null) {
				throw new InvalidDataException("userCreate","data = null");
			}
			if (!validate(data,userSessionData)) {
				throw new InvalidDataException("userCreate","generic.user.userdata.isinvalid");
			}

            UsABM userABM = UsABMUtil.getHome().create();
            if (userABM.userExist(data.getLoginName(), userSessionData)) {
                throw new AlreadyExistsException("Has ocurred error in class persistence.user.UserABM(userCreate), user already exist","generic.user.alreadyexist");
            }
			userABM.userCreate(data,assesment,userSessionData);
            
		}  catch (Exception exception) {
            handler.handleException("userCreate", exception);
        } 
	}

	/**
	 * @ejb.interface-method view-type = "remote"
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "administrator"
	 */
	public void userCreate(UserData data,String[] assesments,UserSessionData userSessionData) throws Exception {
		try {
			if (userSessionData == null) {
				throw new DeslogedException("userCreate","session = null");
			}
			if (data == null) {
				throw new InvalidDataException("userCreate","data = null");
			}
			if (!validate(data,userSessionData)) {
				throw new InvalidDataException("userCreate","generic.user.userdata.isinvalid");
			}

            UsABM userABM = UsABMUtil.getHome().create();
            if (userABM.userExist(data.getLoginName(), userSessionData)) {
                throw new AlreadyExistsException("Has ocurred error in class persistence.user.UserABM(userCreate), user already exist","generic.user.alreadyexist");
            }
			userABM.userCreate(data,assesments,userSessionData);
            
		}  catch (Exception exception) {
            handler.handleException("userCreate", exception);
        } 
	}

	/**
	 * @ejb.interface-method view-type = "remote"
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "administrator, accesscode"
	 */
	public void userGroupCreate(UserData data,Integer group,UserSessionData userSessionData) throws Exception {
		try {
			if (userSessionData == null) {
				throw new DeslogedException("userCreate","session = null");
			}
			if (data == null) {
				throw new InvalidDataException("userCreate","data = null");
			}
			if (!validate(data,userSessionData)) {
				throw new InvalidDataException("userCreate","generic.user.userdata.isinvalid");
			}

            UsABM userABM = UsABMUtil.getHome().create();
            if (userABM.userExist(data.getLoginName(), userSessionData)) {
                throw new AlreadyExistsException("Has ocurred error in class persistence.user.UserABM(userCreate), user already exist","generic.user.alreadyexist");
            }
			userABM.userGroupCreate(data,group,userSessionData);
            
		}  catch (Exception exception) {
            handler.handleException("userGroupCreate", exception);
        } 
	}

	/**
	 * @ejb.interface-method view-type = "remote"
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "administrator, accesscode"
	 */
	public void userGroupCreate(UserData data, Integer group, String accessCode, UserSessionData userSessionData) throws Exception {
		try {
			if (userSessionData == null) {
				throw new DeslogedException("userCreate","session = null");
			}
			if (data == null) {
				throw new InvalidDataException("userCreate","data = null");
			}
			if (!validate(data,userSessionData)) {
				throw new InvalidDataException("userCreate","generic.user.userdata.isinvalid");
			}

            UsABM userABM = UsABMUtil.getHome().create();
            if (userABM.userExist(data.getLoginName(), userSessionData)) {
                throw new AlreadyExistsException("Has ocurred error in class persistence.user.UserABM(userCreate), user already exist","generic.user.alreadyexist");
            }
			userABM.userGroupCreate(data,group,userSessionData);
            
            AssesmentABM assesmentABM = AssesmentABMUtil.getHome().create();
            assesmentABM.decreaseAccessCode(accessCode,userSessionData);

		}  catch (Exception exception) {
            handler.handleException("userGroupCreate", exception);
        } 
	}

    /**
     * @ejb.interface-method view-type = "remote"
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "administrator,accesscode,systemaccess"
     */
    public void userCreateFromAC(UserData data,Integer assesment,String accessCode,UserSessionData userSessionData) throws Exception {
        try {
            if (userSessionData == null) {
                throw new DeslogedException("userCreateFromAC","session = null");
            }
            if (data == null) {
                throw new InvalidDataException("userCreateFromAC","data = null");
            }
            if (!validate(data,userSessionData)) {
                throw new InvalidDataException("userCreateFromAC","generic.user.userdata.isinvalid");
            }

            UsABM userABM = UsABMUtil.getHome().create();
            if (userABM.userExist(data.getLoginName(), userSessionData)) {
                throw new AlreadyExistsException("Has ocurred error in class persistence.user.UserABM(userCreate), user already exist","generic.user.alreadyexist");
            }
            userABM.userCreate(data,assesment,userSessionData);
            
            AssesmentABM assesmentABM = AssesmentABMUtil.getHome().create();
            assesmentABM.decreaseAccessCode(accessCode,userSessionData);
            
        }  catch (Exception exception) {
            handler.handleException("userCreateFromAC", exception);
        } 
    }

    /**
     * @ejb.interface-method view-type = "remote"
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "administrator,accesscode,systemaccess"
     */
    public void userCreateFromAC(UserData data,int[] assesments,String accessCode,UserSessionData userSessionData) throws Exception {
        try {
            if (userSessionData == null) {
                throw new DeslogedException("userCreateFromAC","session = null");
            }
            if (data == null) {
                throw new InvalidDataException("userCreateFromAC","data = null");
            }
            if (!validate(data,userSessionData)) {
                throw new InvalidDataException("userCreateFromAC","generic.user.userdata.isinvalid");
            }

            UsABM userABM = UsABMUtil.getHome().create();
            if (userABM.userExist(data.getLoginName(), userSessionData)) {
                throw new AlreadyExistsException("Has ocurred error in class persistence.user.UserABM(userCreate), user already exist","generic.user.alreadyexist");
            }
            userABM.userCreate(data,assesments[0],userSessionData);
            for(int i = 1; i < assesments.length; i++) {
            	userABM.associateAssesment(data.getLoginName(), assesments[i], userSessionData);
            }
            
            AssesmentABM assesmentABM = AssesmentABMUtil.getHome().create();
            assesmentABM.decreaseAccessCode(accessCode,userSessionData);
            
        }  catch (Exception exception) {
            handler.handleException("userCreateFromAC", exception);
        } 
    }

    /**
     * @ejb.interface-method view-type = "remote"
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "administrator,accesscode"
     */
    public String[] createMonsantoEMEAUser(String accessCode,String language,UserSessionData userSessionData) throws Exception {
        try {
            if (userSessionData == null) {
                throw new DeslogedException("createMonsantoEMEAUser","session = null");
            }
            if (language == null) {
                throw new InvalidDataException("createMonsantoEMEAUser","language = null");
            }

            UsReport userReport = UsReportUtil.getHome().create();
            int count = userReport.getUserAssesmentsCount(AssesmentData.MONSANTO_EMEA, userSessionData).intValue()+1;
            
            String password = String.valueOf(System.currentTimeMillis())+String.valueOf(count);
            UserData userData = new UserData("emea"+count,password,"emea"+count,"emea"+count,language,null,UserData.SYSTEMACCESS,null);
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, 1990);
            userData.setBirthDate(c.getTime());
            UsABM userABM = UsABMUtil.getHome().create();
            userABM.userCreate(userData,AssesmentData.MONSANTO_EMEA,userSessionData);
            
            AssesmentABM assesmentABM = AssesmentABMUtil.getHome().create();
            assesmentABM.decreaseAccessCode(accessCode,userSessionData);
            
            String[] data = {"emea"+count,password};
            return data;
        }  catch (Exception exception) {
            handler.handleException("userCreateFromAC", exception);
        } 
        return new String[2];
    }

    /**
	 * @ejb.interface-method view-type = "remote"
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "administrator"
	 */
	public void userResetPassword(UserData data,UserSessionData userSessionData) throws Exception {

		UsABM userABM=null;
		try {
			if (userSessionData == null) {
				throw new DeslogedException("Class business.user.UserABMFacade(userResetPassword), session data is empty","generic.error.deslogued");
			}
			if (data == null) {
				throw new InvalidDataException("Class business.user.UsReportFacade(userResetPassword), user data is empty","generic.user.userdata.isempty");
			}
			
			if (data.getPassword()==null) {
				throw new InvalidDataException("Class business.user.UsReportFacade(userResetPassword), password is empty","generic.user.userdata.pass.isempty");
			}

			UsABMHome home = UsABMUtil.getHome();
			userABM = home.create();
			userABM.userResetPassword(data, userSessionData);
		} catch (Exception exception) {
            handler.handleException("userResetPassword", exception);
        } 
	}

	/**
	 * @ejb.interface-method view-type = "remote"
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "administrator,systemaccess,firstaccess"
	 * 
	 * @param data
	 * @throws Exception
	 */
	public void userResetOwnPassword(String password,UserSessionData userSessionData) throws Exception {

		UsABM userABM=null;
		try {
			if (userSessionData == null) {
				throw new DeslogedException("Class business.user.UsABMFacade(userResetPassword), session data is empty","generic.error.deslogued");
			}
			if (password == null) {
				throw new InvalidDataException("Class business.user.UsABMFacade(userResetPassword), password is empty","generic.user.userdata.pass.isempty");
			}

            UsABMHome home = UsABMUtil.getHome();
			userABM = home.create();
			userABM.userResetOwnPassword(password,userSessionData);
		}  catch (Exception exception) {
            handler.handleException("userResetOwnPassword", exception);
        } 
	}

    /**
	 * @ejb.interface-method view-type = "remote"
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "administrator,systemaccess"
	 * 
	 * @param lngName is a valid language name ISO 639 two letter codes
	 * @throws Exception
	 */
	public void userSwitchLanguage(String lngName,UserSessionData userSessionData) throws Exception {

        UsABM userABM=null;
		try {
			if (userSessionData == null) {
				throw new DeslogedException("Class business.user.UsABMFacade(userSwitchLanguage), session data is empty","generic.error.deslogued");
			}
			
            UsABMHome home = UsABMUtil.getHome();
			userABM = home.create();
			
			userABM.userSwitchLanguage(lngName,userSessionData);
			userSessionData.setLenguage(lngName);
			
		}  catch (Exception exception) {
            handler.handleException("userSwitchLanguage", exception);
        } 
	}

	/**
	 * @ejb.interface-method
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "administrator,userdelete"
	 * 
	 * @param data
	 * @throws Exception
	 */
	public void userDelete(Collection users,UserSessionData userSessionData) throws Exception {
		
        UsABM userABM=null;
		try {
			if (userSessionData == null) {
				throw new DeslogedException("userDelete","session = null");
			}
			if (users == null) {
				throw new InvalidDataException("userDelete","users = null");
			}
            UsABMHome home = UsABMUtil.getHome();
			userABM = home.create();
            Iterator it = users.iterator();
            while (it.hasNext()) {
                userABM.userDelete((String) it.next(), userSessionData);
            }
		}  catch (Exception exception) {
            handler.handleException("userDelete", exception);
        } 
	}

	/**
	 * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess,userupdate,userupdateaccess"
	 * 
	 * @param data
	 * @throws Exception
	 */
	public void userUpdate(UserData data,UserSessionData userSessionData) throws Exception {
	    userUpdate(data,true,userSessionData);
	}
	
	/**
	 * @ejb.interface-method
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "administrator,systemaccess,userupdate,userupdateaccess"
	 * 
	 * @param data
	 * @throws Exception
	 */
	public void userUpdate(UserData data, boolean validate, UserSessionData userSessionData) throws Exception {

        UsABM userABM=null;
		try {
			if (userSessionData == null) {
				throw new DeslogedException("Class business.user.UsABMFacade(userUpdate), session data is empty","generic.error.deslogued");
			}
			if (data == null) {
				throw new InvalidDataException("Class business.user.UsABMFacade(userUpdate), user data is empty","generic.user.usuerdata.isempty");
			}
	        if (!validate(data,userSessionData)) {
	            throw new InvalidDataException("Class business.user.UsABMFacade(userCreate), invalid user data","generic.user.userdata.isinvalid");
	        }

            UsABMHome home = UsABMUtil.getHome();
			userABM = home.create();
			
			userABM.userUpdate(data,null,userSessionData,validate);
		}  catch (Exception exception) {
            handler.handleException("userUpdate", exception);
        } 
	}

	private boolean validate(UserData data,UserSessionData userSessionData) {

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
        if (data.getRole() == null || data.getRole() == null
                || data.getRole().trim().length() == 0) {
            return false;
        }

		return true;

	}

    /**
     * @ejb.interface-method
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "resetpassword"
     */
	public String resetPassword(UserData userData, UserSessionData userSessionData) throws Exception {
        try {
            String password = getNewPass();
            UsABMHome userHome = UsABMUtil.getHome();
            UsABM userABM = userHome.create();
            userABM.resetPassword(userData,new MD5().encriptar(password),userSessionData);
            return password;
        }  catch (Exception exception) {
            handler.handleException("resetPassword", exception);
        } 
        return null;
    }

    /**
     * @ejb.interface-method
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "administrator,systemaccess,resetpassword"
     */
	public String resetPassword(UserData userData, String password, UserSessionData userSessionData) throws Exception {
        try {
            UsABMHome userHome = UsABMUtil.getHome();
            UsABM userABM = userHome.create();
            userABM.resetPassword(userData,new MD5().encriptar(password),userSessionData);
            return password;
        }  catch (Exception exception) {
            handler.handleException("resetPassword", exception);
        } 
        return null;
    }

    /**
     * @ejb.interface-method
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "resetpassword,administrator"
     */
    public String getNewPass(){
        String randomCharacterSet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789@#$%^&+=";
        String result;     
        do {
            result = "";
            for (int i=1; i<=9; i++) {  
                int x = (int)Math.round(Math.random()*(randomCharacterSet.length()-1));
                result+=randomCharacterSet.charAt(x);
            }
        }while(UserData.validatePassword(result) != null);
        return(result);
    }

    /**
     * @ejb.interface-method
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public void saveLogin(long date,UserSessionData userSessionData) throws Exception {
        try{    
            UsABMHome userHome = UsABMUtil.getHome();
            UsABM userABM = userHome.create();
            userABM.saveLogin(date,userSessionData);
        } catch (Exception e) {
            handler.handleException("addUserGroup",e);
        } 
    }

    /**
     * @ejb.interface-method
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "administrator,systemaccess"
     */
    public void acceptTerms(UserSessionData userSessionData) throws Exception {
        try{    
            UsABMUtil.getHome().create().acceptTerms(userSessionData);
        } catch (Exception e) {
            handler.handleException("acceptTerms",e);
        } 
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator, systemaccess"
     */
    public void associateAssesment(String user,Integer assesment,UserSessionData userSessionData) throws Exception {
        try {
            if (userSessionData == null) {
                throw new DeslogedException("associateAssesment","session = null");
            }
            if (user == null) {
                throw new InvalidDataException("associateAssesment","user is null");
            }
            if (assesment == null) {
                throw new InvalidDataException("associateAssesment","assesment is null");
            }
            UsABMUtil.getHome().create().associateAssesment(user,assesment,userSessionData);
        } catch (Exception e) {
            handler.handleException("associateAssesment",e);
        } 
    }


    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator, systemaccess"
     */
    public void associateAssesment(String user,String[] assesments,UserSessionData userSessionData) throws Exception {
        try {
            if (userSessionData == null) {
                throw new DeslogedException("associateAssesment","session = null");
            }
            if (user == null) {
                throw new InvalidDataException("associateAssesment","user is null");
            }
            if (assesments == null) {
                throw new InvalidDataException("associateAssesment","assesment is null");
            }
            UsABM usABM = UsABMUtil.getHome().create();
            for(int i = 0; i < assesments.length; i++)
            	usABM.associateAssesment(user,new Integer(assesments[i]),userSessionData);
        } catch (Exception e) {
            handler.handleException("associateAssesment",e);
        } 
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     */
    public void deleteAssociatedAssesment(String user,Collection<String> assesments,UserSessionData userSessionData) throws Exception {
        try {
            if (userSessionData == null) {
                throw new DeslogedException("deleteAssociatedAssesment","session = null");
            }
            if (user == null) {
                throw new InvalidDataException("deleteAssociatedAssesment","user is null");
            }
            if (assesments == null) {
                throw new InvalidDataException("deleteAssociatedAssesment","assesments is null");
            }
            UsABM userABM = UsABMUtil.getHome().create();
            Iterator<String> it = assesments.iterator();
            while(it.hasNext()) {
                String assesment = (String)it.next();
                userABM.deleteAssociatedAssesment(user,new Integer(assesment),userSessionData);
            }
        } catch (Exception e) {
            handler.handleException("deleteAssociatedAssesment",e);
        } 
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator,basf_assessment"
     */
    public int[] saveModuleAnswers(String user,Integer assesment,Collection answers,int psiId, int psiTestId, UserSessionData userSessionData,boolean psico,int[] values,boolean feedback) throws Exception {
    	return saveModuleAnswers(user,assesment,answers,psiId,psiTestId,userSessionData,psico,values,feedback,null);
    }
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator,basf_assessment"
     */
    public int[] saveModuleAnswers(String user,Integer assesment,Collection answers,int psiId, int psiTestId, UserSessionData userSessionData,boolean psico,int[] values,boolean feedback, Integer newHire) throws Exception {
        int[] resultValues = {0,0};
        try {
            if(userSessionData == null) {
                throw new DeslogedException("saveModuleAnswers","session = null");
            }
            if(user == null) {
                throw new InvalidDataException("saveModuleAnswers","user = null");
            }
            if(assesment == null) {
                throw new InvalidDataException("saveModuleAnswers","assesment = null");
            }
            Iterator it = answers.iterator();
            UsABM userABM = UsABMUtil.getHome().create();
            while(it.hasNext()) {
            	UserAnswerData userAnswerData = (UserAnswerData)it.next();
                int[] result = userABM.saveAnswer(user,assesment,userAnswerData,userSessionData,psico,feedback);
                int questionId = userAnswerData.getQuestion().intValue();
                //if(userAnswerData.getQuestion().isFirstNameQuestion() ) {
                if(QuestionData.isFirstNameQuestion(userAnswerData.getQuestion())) {
                	userABM.updateUserName(userAnswerData.getText(), null, userSessionData);
                }
                if(QuestionData.isLastNameQuestion(userAnswerData.getQuestion())) {
                	userABM.updateUserName(null, userAnswerData.getText(), userSessionData);
                }
                if(feedback) {
                    resultValues[0] += result[0];
                    resultValues[1] += result[1];
                }
            }
            if(psico) {
                userABM.savePsicoResults(user,assesment,values,psiId,psiTestId,userSessionData);
            }
            if(newHire != null) {
                userABM.saveNewHire(user,assesment,newHire,userSessionData);
            }

        } catch (Exception e) {
            handler.handleException("saveModuleAnswers",e);
        } 
        return resultValues;
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator,basf_assessment"
     */
    public void savePsicoAnswers(String user,Integer assesment,Collection answers,UserSessionData userSessionData) throws Exception {
        int[] resultValues = {0,0};
        try {
            if(userSessionData == null) {
                throw new DeslogedException("saveModuleAnswers","session = null");
            }
            if(user == null) {
                throw new InvalidDataException("saveModuleAnswers","user = null");
            }
            if(assesment == null) {
                throw new InvalidDataException("saveModuleAnswers","assesment = null");
            }
            Iterator it = answers.iterator();
            UsABM userABM = UsABMUtil.getHome().create();
            while(it.hasNext()) {
                userABM.saveAnswer(user,assesment,(UserAnswerData)it.next(),userSessionData,true,false);
            }
        } catch (Exception e) {
            handler.handleException("saveModuleAnswers",e);
        } 
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator"
     */
    public int[] saveModuleAnswers(String user, Integer assesment, Collection answerList, Integer newHireValue, boolean saveNewHire, boolean feedback, UserSessionData userSessionData) throws Exception {
        int[] resultValues = {0,0};
        try {
            if(userSessionData == null) {
                throw new DeslogedException("saveModuleAnswers","session = null");
            }
            if(user == null) {
                throw new InvalidDataException("saveModuleAnswers","user = null");
            }
            if(assesment == null) {
                throw new InvalidDataException("saveModuleAnswers","assesment = null");
            }
            Iterator it = answerList.iterator();
            UsABM userABM = UsABMUtil.getHome().create();
            while(it.hasNext()) {
                int[] result = userABM.saveAnswer(user,assesment,(UserAnswerData)it.next(),userSessionData,false,feedback);
                if(feedback) {
                    resultValues[0] += result[0];
                    resultValues[1] += result[1];
                }
            }
            if(saveNewHire) {
                userABM.saveNewHire(user,assesment,newHireValue,userSessionData);
            }
        } catch (Exception e) {
            handler.handleException("saveModuleAnswers",e);
        } 
        return resultValues;
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
     */
    public Collection createUsersFromDC(Integer corporation,Integer assesment,String password,UserSessionData userSessionData) throws Exception {
        try {
            if(userSessionData == null) {
                throw new DeslogedException("createUsersFromDC","session = null");
            }
            if(assesment == null) {
                throw new InvalidDataException("createUsersFromDC","assesment = null");
            }
            if(corporation == null) {
                throw new InvalidDataException("createUsersFromDC","corporation = null");
            }
            UsReport userReport = UsReportUtil.getHome().create();
            UsABM userABM = UsABMUtil.getHome().create();
            Collection list = userReport.getDriversDC(corporation,userSessionData);
            Iterator it = list.iterator();
            while(it.hasNext()) {
                UserData userData = (UserData)it.next();
                if(password == null) {
                    userData.setPassword(getNewPass());
                }else {
                    userData.setPassword(password);
                }
                userData.setLocation(UserData.GENERAL);
                userData.setRole(SecurityConstants.ACCESS_TO_SYSTEM);
                userData.setLanguage(userSessionData.getLenguage());
                userABM.userCreate(userData,assesment,userSessionData);
                if(password == null) {
                    userData.setPassword(getNewPass());
                }else {
                    userData.setPassword(password);
                }
            }
            return list;
        } catch (Exception e) {
            handler.handleException("createUsersFromDC",e);
        } 
        return null;
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator"
     */
    public void setRedirect(String userId,String redirect,Integer eLearningUser,
    		Boolean eLearningEnabled, Integer assesmentId, UserSessionData userSessionData) throws Exception {
        try{
            UsABM userABM = UsABMUtil.getHome().create();
            userABM.setRedirect(userId,redirect,eLearningUser, eLearningEnabled, assesmentId, userSessionData);
        } catch (Exception e) {
            handler.handleException("setRedirect",e);
        } 
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator"
     */
    public void setEndDate(String userId,Integer assesmentId,UserSessionData userSessionData) throws Exception {
        try{
            UsABM userABM = UsABMUtil.getHome().create();
            userABM.setEndDate(userId,assesmentId, userSessionData);
        } catch (Exception e) {
            handler.handleException("setEndDate",e);
        } 
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "systemaccess,administrator"
     */
    public void setElearningEnabled(String user, Integer assessment, boolean result, UserSessionData userSessionData) throws Exception {
        try{
            UsABM userABM = UsABMUtil.getHome().create();
            //userABM.setElearningEnabled(user,assessment,result,userSessionData);
        } catch (Exception e) {
            handler.handleException("setElearningEnabled",e);
        } 
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,basf_assessment"
     */
    public String createBasfUser(Integer assessment,String copyUser,UserSessionData userSessionData) throws Exception {
        try{

        	UsABM userABM = UsABMUtil.getHome().create();
            UserData userData = userABM.createBasfUser(assessment,copyUser,userSessionData);
            if(copyUser != null) {
            	copyBasfPersonalData(userData, assessment, userSessionData);
            }
            
            return userData.getLoginName();
        } catch (Exception e) {
            handler.handleException("createBasfUser",e);
        } 
        return null;
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name = "administrator,basf_assessment"
     */
    public void basfUserAssessment(String login,Integer assessment,UserSessionData userSessionData) throws Exception {
        try{
            
            if(!UsReportUtil.getHome().create().existUserAssessment(login,assessment,userSessionData)) {
            	
                UsABM userABM = UsABMUtil.getHome().create();
	            UserData userData = userABM.createBasfUserAssessment(login, assessment, userSessionData);
	            
	            copyBasfPersonalData(userData, assessment, userSessionData);
            }

        } catch (Exception e) {
            handler.handleException("basfUserAssessment",e);
        } 
    }

	private void copyBasfPersonalData(UserData userData, Integer assessment, UserSessionData userSessionData) throws Exception {
		
		AssesmentReport assessmentReport = AssesmentReportUtil.getHome().create();
		AssesmentData assessmentData = assessmentReport.findAssesment(assessment, userSessionData);
		
		Collection<UserAnswerData> answerList = new LinkedList<UserAnswerData>();

		ModuleData moduleData = (ModuleData)assessmentData.getModuleIterator().next();
		Iterator it = moduleData.getQuestionIterator();
		while(it.hasNext()) {
			QuestionData questionData = (QuestionData)it.next();
			switch(questionData.getOrder()) {
		    	case 1:
		            answerList.add(new UserAnswerData(questionData.getId(),userData.getFirstName()));
		    		break;
		    	case 2:
		            answerList.add(new UserAnswerData(questionData.getId(),userData.getLastName()));
		    		break;
		    	case 3:
		            answerList.add(new UserAnswerData(questionData.getId(),userData.getBirthDate()));
		    		break;
		    	case 4:
		            answerList.add(new UserAnswerData(questionData.getId(),userData.getExtraData()));
		    		break;
		    	case 5:
		            answerList.add(new UserAnswerData(questionData.getId(),userData.getExtraData2()));
		    		break;
		    	case 6:
		            answerList.add(new UserAnswerData(questionData.getId(),userData.getVehicle()));
		    		break;
		    	case 7:
		            answerList.add(new UserAnswerData(questionData.getId(),userData.getExtraData3()));
		    		break;
			}
		}
		saveModuleAnswers(userData.getLoginName(),assessment,answerList,-1,-1,userSessionData,false,null,false);
	}
	
	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "systemaccess,administrator,resetpassword,accesscode,pepsico_candidatos,basf_assessment,clientreporter,cepareporter"
	 */
	public Collection<UserData> createUsersFromFile(Integer group, Collection<UserData> list, String ending, UserSessionData userSessionData) throws Exception {
		Collection<UserData> saved = new LinkedList<UserData>();
		try {
            if(userSessionData == null) {
                throw new DeslogedException("createUsersFromFile","session = null");
            }
            if(group == null) {
                throw new InvalidDataException("createUsersFromFile","assesment = null");
            }
            UsABM userABM = UsABMUtil.getHome().create();
            Collection users = UsReportUtil.getHome().create().getUserList(ending, userSessionData);

            Iterator<UserData> it = list.iterator();
            while(it.hasNext()) {
                UserData userData = (UserData)it.next();
                userData.setRole(SecurityConstants.GROUP_ASSESSMENT);
                String login = userData.getLoginName();
                String aux = login;
                int i = 1;
                while(users.contains(aux)) {
                	aux = login + "." + i;
                	i++;
                }
                users.add(aux);
                userData.setLoginName(aux);
                String password = userData.getPassword();
                userABM.userGroupCreate(userData, group, userSessionData);
                userData.setPassword(password);
                saved.add(userData);
            }
            
            return list;
        } catch (Exception e) {
            handler.handleException("createUsersFromFile",e);
        } 
        return saved;
	}
	
	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "systemaccess,administrator,resetpassword,accesscode,pepsico_candidatos,basf_assessment,clientreporter,cepareporter"
	 */
	public Collection<UserData> createUsersFromFile(Collection assesments, String role, Collection<UserData> list, String ending, UserSessionData userSessionData) throws Exception {
		Collection<UserData> saved = new LinkedList<UserData>();
		try {
            if(userSessionData == null) {
                throw new DeslogedException("createUsersFromFile","session = null");
            }
            if(assesments == null || assesments.size() == 0) {
                throw new InvalidDataException("createUsersFromFile","assesment = null");
            }
            UsABM userABM = UsABMUtil.getHome().create();
            Collection users = UsReportUtil.getHome().create().getUserList(ending, userSessionData);

            Iterator<UserData> it = list.iterator();
            while(it.hasNext()) {
                UserData userData = (UserData)it.next();
                userData.setRole(role);
                String login = userData.getLoginName();
                String aux = login;
                int i = 1;
                while(users.contains(aux)) {
                	aux = login + "." + i;
                	i++;
                }
                users.add(aux);
                userData.setLoginName(aux);
                String password = userData.getPassword();
                userABM.userCreate(userData, (String[])assesments.toArray(new String[0]), userSessionData);
                userData.setPassword(password);
                saved.add(userData);
            }
            
            return list;
        } catch (Exception e) {
            handler.handleException("createUsersFromFile",e);
        } 
        return saved;
	}

	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "systemaccess,administrator,resetpassword,accesscode,pepsico_candidatos,basf_assessment,clientreporter,cepareporter"
	 */
	public UserPassword getUserPassword(String loginname) throws Exception {
		UsABM userABM = UsABMUtil.getHome().create();
		return userABM.getUserPassword(loginname);
	}
	
	/**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "systemaccess,administrator,resetpassword,accesscode,pepsico_candidatos,basf_assessment,clientreporter,cepareporter"
	 */
	public void createUserPassword(UserPassword userPassword) throws Exception {
		UsABM userABM = UsABMUtil.getHome().create();
		userABM.userPasswordCreate(userPassword);
	}

    /**
	 * @ejb.interface-method
	 * @ejb.permission role-name = "systemaccess,administrator"
	 */
    public void savePSIaux(String answers, int[] result, UserSessionData userSessionData) throws Exception {
    	try {
    		UsABMUtil.getHome().create().savePSIaux(answers, result, userSessionData);
        } catch (Exception e) {
            handler.handleException("savePSIaux",e);
        } 
    }

    /**
 	 * @ejb.interface-method
 	 * @ejb.permission role-name = "systemaccess,administrator"
 	 */
     public void failAssessment(Integer assessmentId, String user, Date date, UserSessionData userSessionData) throws Exception {
     	try {
    		UsABMUtil.getHome().create().failAssessment(assessmentId, user, date, userSessionData);
        } catch (Exception e) {
            handler.handleException("failAssessment",e);
        } 
     }

     /**
  	 * @ejb.interface-method
  	 * @ejb.permission role-name = "systemaccess,administrator"
  	 */
     public Object[] saveMultiAssessment(HashMap answerMap, Integer multiId, UserSessionData userSessionData) throws Exception {
        Collection<String> valids = new LinkedList<String>();
        Collection<String[]> errors = new LinkedList<String[]>();
      	try {
     		UsABM usABM = UsABMUtil.getHome().create();
      		Iterator it = answerMap.keySet().iterator();
      		while(it.hasNext()) {
            	Integer questionId = (Integer)it.next();
            	String answerId = (String)answerMap.get(questionId);
            	try {
            		usABM.saveMultiAnswer(questionId, new Integer(answerId), multiId, userSessionData);
            		valids.add(questionId.toString());
            	} catch (Exception e) {
            		errors.add(new String[]{questionId.toString(),e.getMessage()});
            	}
      		}
        } catch (Exception e) {
             handler.handleException("failAssessment",e);
        } 
        String[] valid = (String[])valids.toArray(new String[0]);
        String[][] error = (String[][])errors.toArray(new String[0][0]);
      	return  new Object[]{valid,error};
      }
     
     /**
      * @ejb.interface-method
      * @ejb.permission role-name = "systemaccess,administrator"
      */
     public void deleteGroupUsers(Integer group, Collection users, UserSessionData userSessionData) throws Exception {
         try {
             UsABM userABM = UsABMUtil.getHome().create();
             UsReport userReport = UsReportUtil.getHome().create();
             AssesmentABM assesmentABM = AssesmentABMUtil.getHome().create();
            
             Iterator it = users.iterator();
             while(it.hasNext()) {
             	String user = (String) it.next();
 	            userABM.deleteGroupUsers(group,user,userSessionData);       

 	            assesmentABM.deleteResults(user, userSessionData);
             }
         }catch (Exception e) {
             handler.handleException("deleteGroupUsers",e);
         }
     }

     /**
      * @ejb.interface-method
      * @ejb.permission role-name = "systemaccess,administrator"
      */
     public void checkGroupAssesments(String user, UserSessionData userSessionData) throws Exception {
     	try {
        	UsABMUtil.getHome().create().checkGroupAssesments(user, userSessionData);
     	}catch (Exception e) {
     		handler.handleException("checkGroupAssesments",e);
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
 	public void userChangeGroup(String user,Integer groupOld, Integer groupNew, UserSessionData userSessionData) throws Exception {
     	try {
        	UsABMUtil.getHome().create().userChangeGroup(user, groupOld, groupNew, userSessionData);
     	}catch (Exception e) {
     		handler.handleException("userChangeGroup",e);
     	}
 	}

    /**
	 * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
	 * @param data
	 * @param userRequest
	 * @throws Exception
	 */
 	public Integer saveTimacUser(String id, String firstName, String lastName, String email, UserSessionData userSessionData) throws Exception {
     	try {
     		String login = "tmc_"+id;
     		
     		UsReport usReport = UsReportUtil.getHome().create();
     		
     		if(usReport.userExist(login, userSessionData)) {
     			return 2;
     		}else {
	     		UserData user = new UserData(login, new MD5().encriptar(login), firstName, lastName, "pt", email, UserData.MULTIASSESSMENT, null);
	     		user.setStartDate(Calendar.getInstance().getTime());
	     		
	     		String[] assesments = {String.valueOf(AssesmentData.TIMAC_BRASIL_DA_2020), String.valueOf(AssesmentData.TIMAC_BRASIL_EBTW_2020)};
	        	UsABMUtil.getHome().create().userCreate(user, assesments, userSessionData);
	        	return 1;
     		}
     	}catch (Exception e) {
     		handler.handleException("saveTimacUser",e);
     	}
     	return 0;
	}

    /**
	 * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
	 * @param data
	 * @param userRequest
	 * @throws Exception
	 */
 	public Integer existTimacUser(String id, UserSessionData userSessionData) throws Exception {
     	try {
     		String login = "tmc_"+id;
     		
     		UsReport usReport = UsReportUtil.getHome().create();
     		
     		if(usReport.userExist(login, userSessionData)) {
     			return 2;
     		}else {
	        	return 1;
     		}
     	}catch (Exception e) {
     		handler.handleException("existTimacUser",e);
     	}
     	return 0;
	}


    /**
	 * @ejb.interface-method
     * @ejb.permission role-name = "administrator"
	 * @param data
	 * @param userRequest
	 * @throws Exception
	 */
 	public String[][] saveTimacUsers(Collection<UserData> users, UserSessionData userSessionData) throws Exception {
 		Collection<String> created = new LinkedList<String>();
 		Collection<String> exist = new LinkedList<String>();
 		try {
     		UsReport usReport = UsReportUtil.getHome().create();
     		UsABM usABM = UsABMUtil.getHome().create();
     		Iterator<UserData> itUsers = users.iterator();
     		
     		MD5 md5 = new MD5();
     		while(itUsers.hasNext()) {
     			UserData user = itUsers.next();
     		
     			String login = usReport.getNextTimacUser(user.getLoginName(), userSessionData);
     			
	     		if(login == null) {
	     			exist.add(user.getLoginName());
	     		}else {
		        	created.add(user.getLoginName());

		        	user.setLoginName(login);
		        	user.setPassword(md5.encriptar(login));
		     		user.setStartDate(Calendar.getInstance().getTime());
		     		
		     		String[] assesments = {String.valueOf(AssesmentData.TIMAC_BRASIL_DA_2020), String.valueOf(AssesmentData.TIMAC_BRASIL_EBTW_2020)};
		        	usABM.userCreate(user, assesments, userSessionData);
	     		}
     		}
     	}catch (Exception e) {
     		handler.handleException("saveTimacUser",e);
     	}
     	String[][] s = {(String[])created.toArray(new String[0]),(String[])exist.toArray(new String[0])};
     	return s;
	}

    /**
	 * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
	 * @param data
	 * @param userRequest
	 * @throws Exception
	 */
 	public void userCediCreate(UserData data, int type, int ebtw, UserSessionData userSessionData) throws Exception {
     	try {

	        UsABM userABM = UsABMUtil.getHome().create();
	        if (userABM.userExist(data.getLoginName(), userSessionData)) {
	            throw new AlreadyExistsException("Has ocurred error in class persistence.user.UserABM(userCreate), user already exist","generic.user.alreadyexist");
	        }
	        
	        Collection<String> assesmentIds = new LinkedList<String>();
        	assesmentIds.add(String.valueOf(AssesmentData.GRUPO_MODELO_FOTO));
	        
	        int[] assesments = new int[0];
	        
	        if(type < 3) {
		        if(type == 1) {
		        	assesments = new int[] {1553, 1557, 1558, 1559, 1560, 1569, 1570, 1571, 1572, 1573};
		        }else {
		        	assesments = new int[] {1561, 1562, 1563, 1564, 1565, 1574, 1575, 1576, 1577, 1578};
		        }
		        int x = (int)Math.round(Math.random()*(assesments.length-1));
		        assesmentIds.add(String.valueOf(assesments[x]));
	        }
	        
	        if(ebtw == 1) {
	        	assesmentIds.add(String.valueOf(AssesmentData.GRUPO_MODELO_EBTW));
	        	assesmentIds.add(String.valueOf(AssesmentData.GRUPO_MODELO_CEPATIP));
	        	assesmentIds.add(String.valueOf(AssesmentData.GRUPO_MODELO_CUESTIONARIO));
	        }
			userABM.userCreate(data, (String[])assesmentIds.toArray(new String[0]), userSessionData);
			
			userABM.userCediGroupCreate(data, userSessionData);
			
     	}catch (Exception e) {
     		handler.handleException("userCediCreate",e);
     	}
	}


    /**
	 * @ejb.interface-method
     * @ejb.permission role-name = "administrator,systemaccess"
	 * @param data
	 * @param userRequest
	 * @throws Exception
	 */
 	public void userCediAsociate(Collection users, int type, UserSessionData userSessionData) throws Exception {
     	try {

	        UsABM userABM = UsABMUtil.getHome().create();

	        Iterator it = users.iterator();
	        
	        while(it.hasNext()) {
	        	String user = (String)it.next();
	        	int assessmentId = 0;
		        if(type < 3) {
			        int[] assesments = new int[0];
			        if(type == 1) {
			        	assesments = new int[] {1553, 1557, 1558, 1559, 1560, 1569, 1570, 1571, 1572, 1573};
			        }else {
			        	assesments = new int[] {1561, 1562, 1563, 1564, 1565, 1574, 1575, 1576, 1577, 1578};
			        }
			        int x = (int)Math.round(Math.random()*(assesments.length-1));
			        assessmentId = assesments[x];
		        }else {
			        assessmentId = AssesmentData.GRUPO_MODELO_EBTW;
			    	userABM.associateAssesment(user, 1646, userSessionData);
		        	userABM.associateAssesment(user, 1652, userSessionData);
		        }
		        
		        if(assessmentId != 0) {
					userABM.associateAssesment(user, assessmentId, userSessionData);
		        }
		 
	        }

	       

     	}catch (Exception e) {
     		handler.handleException("userCediCreate",e);
     	}
	}

	/**
	 * @ejb.interface-method view-type = "remote"
     * @ejb.transaction type="Required" s
     * @ejb.permission role-name = "administrator, systemaccess"
	 */
    public void saveCompleteAnswers(String user, Integer assesment, Collection<UserAnswerData> answers, UserSessionData userSessionData) throws Exception {
		try {
			if (userSessionData == null) {
				throw new DeslogedException("saveCompleteAnswers","session = null");
			}
			if (user == null) {
				throw new InvalidDataException("saveCompleteAnswers","user = null");
			}

            UsABM userABM = UsABMUtil.getHome().create();
            Iterator<UserAnswerData> it = answers.iterator();
            while(it.hasNext()) {
            	userABM.saveAnswer(user, assesment, it.next(), userSessionData, false, false);   
            }
		}  catch (Exception exception) {
            handler.handleException("saveCompleteAnswers", exception);
        } 
	}
	/**
	 * @ejb.interface-method
     * @ejb.transaction type="Required" 
     * @ejb.permission role-name = "administrator,userdelete"
	 * 
	 * @param data
	 * @throws Exception
	 */
	public void deleteSendedReport(String user,UserSessionData userSessionData) throws Exception {
		
        UsABM userABM=null;
		try {
			if (userSessionData == null) {
				throw new DeslogedException("deleteSendedReport","session = null");
			}
			if (user == null) {
				throw new InvalidDataException("deleteSendedReport","user = null");
			}
            UsABMHome home = UsABMUtil.getHome();
			userABM = home.create();
			userABM.deleteSendedReport(user, userSessionData);
            
		}  catch (Exception exception) {
            handler.handleException("deleteSendedReport", exception);
        } 
	}    
}