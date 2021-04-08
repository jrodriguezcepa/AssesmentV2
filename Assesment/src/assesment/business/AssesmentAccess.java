/*
 * Created on 11-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.business;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

import assesment.business.administration.corporation.CorpABMFacade;
import assesment.business.administration.corporation.CorpABMFacadeHome;
import assesment.business.administration.corporation.CorpABMFacadeUtil;
import assesment.business.administration.corporation.CorpReportFacade;
import assesment.business.administration.corporation.CorpReportFacadeHome;
import assesment.business.administration.corporation.CorpReportFacadeUtil;
import assesment.business.administration.property.PropertyABMFacade;
import assesment.business.administration.property.PropertyABMFacadeHome;
import assesment.business.administration.property.PropertyABMFacadeUtil;
import assesment.business.administration.property.PropertyReportFacade;
import assesment.business.administration.property.PropertyReportFacadeHome;
import assesment.business.administration.property.PropertyReportFacadeUtil;
import assesment.business.administration.user.UsABMFacade;
import assesment.business.administration.user.UsABMFacadeHome;
import assesment.business.administration.user.UsABMFacadeUtil;
import assesment.business.administration.user.UsReportFacade;
import assesment.business.administration.user.UsReportFacadeHome;
import assesment.business.administration.user.UsReportFacadeUtil;
import assesment.business.assesment.AssesmentABMFacade;
import assesment.business.assesment.AssesmentABMFacadeHome;
import assesment.business.assesment.AssesmentABMFacadeUtil;
import assesment.business.assesment.AssesmentReportFacade;
import assesment.business.assesment.AssesmentReportFacadeHome;
import assesment.business.assesment.AssesmentReportFacadeUtil;
import assesment.business.language.LangABMFacade;
import assesment.business.language.LangABMFacadeHome;
import assesment.business.language.LangABMFacadeUtil;
import assesment.business.language.LangReportFacade;
import assesment.business.language.LangReportFacadeHome;
import assesment.business.language.LangReportFacadeUtil;
import assesment.business.module.ModuleABMFacade;
import assesment.business.module.ModuleABMFacadeHome;
import assesment.business.module.ModuleABMFacadeUtil;
import assesment.business.module.ModuleReportFacade;
import assesment.business.module.ModuleReportFacadeHome;
import assesment.business.module.ModuleReportFacadeUtil;
import assesment.business.question.QuestionABMFacade;
import assesment.business.question.QuestionABMFacadeHome;
import assesment.business.question.QuestionABMFacadeUtil;
import assesment.business.question.QuestionReportFacade;
import assesment.business.question.QuestionReportFacadeHome;
import assesment.business.question.QuestionReportFacadeUtil;
import assesment.business.tag.TagABMFacade;
import assesment.business.tag.TagABMFacadeHome;
import assesment.business.tag.TagABMFacadeUtil;
import assesment.business.tag.TagReportFacade;
import assesment.business.tag.TagReportFacadeHome;
import assesment.business.tag.TagReportFacadeUtil;
import assesment.business.util.ExceptionHandler;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.exception.CommunicationProblemException;
import assesment.communication.exception.DCSecurityException;
import assesment.communication.exception.DeslogedException;
import assesment.communication.exception.InvalidDataException;
import assesment.communication.language.Text;
import assesment.communication.language.TextBKP;
import assesment.communication.language.TextContainer;
import assesment.communication.language.Text_vt;
import assesment.communication.security.SecurityConstants;
import assesment.communication.user.UserData;
import assesment.communication.util.Filter;
import assesment.persistence.util.LoggerFormatting;

public class AssesmentAccess { 

	private static final long serialVersionUID = 1L;
	/**
	 * Logger for this class
	 */
	private Logger logger = Logger.getLogger(AssesmentAccess.class);
	private LoggerFormatting logFormatting=new LoggerFormatting();
	
	private ExceptionHandler handler = new ExceptionHandler(AssesmentAccess.class);
    
	private UserSessionData userSessionData;
	
	private Text text;

	private Object value;
	
	/**
	 * 
	 * @param user
	 * @throws Exception
	 */
    public AssesmentAccess(String user) throws Exception {
        try {
            userSessionData = new UserSessionData();
        	try {
        		loadUser(user);
        		loadText();
        		//SystemAccessController.getInstance().suscribe (this,user);    		
        	}catch(Exception e) {
        	    handler.handleException("SystemAccess()",e);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * 
	 * @param user
	 * @throws Exception
	 */
    public AssesmentAccess(String user,Integer assessment) throws Exception {
        try {
            userSessionData = new UserSessionData();
        	try {
        		loadUser(user,assessment);
        		loadText();
        		//SystemAccessController.getInstance().suscribe (this,user);    		
        	}catch(Exception e) {
        	    handler.handleException("SystemAccess()",e);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
	
	public void unSuscribe() throws Exception{};
	
	/**
	 * 
	 * @return
	 */
	public Object getReloadKey(){
		Object returnObject = userSessionData.getFilter().getLoginName();
		return returnObject;
	}
	
	/**
	 * 
	 * @param login
	 * @throws Exception
	 */
	public void loadUser(String login) throws Exception {
	
		userSessionData = new UserSessionData();
		
		UsReportFacade userReportFacade = getUserReportFacade();
		UserData data= userReportFacade.findUserByPrimaryKey(login,userSessionData);
        Integer assesment = null;
        if(data.getRole().equals(SecurityConstants.GROUP_ASSESSMENT)) { 
        	getUserABMFacade().checkGroupAssesments(data.getLoginName(), userSessionData);
        }
        if(!data.getRole().equals(SecurityConstants.WEBINAR)) {
	        Iterator it = userReportFacade.findUserAssesments(login,userSessionData).iterator();
	        if(data.getRole().equals(SecurityConstants.ACCESS_TO_SYSTEM)) {
	            Calendar c = Calendar.getInstance();
	            while(it.hasNext()) {
	                AssesmentAttributes assesmentAttr = (AssesmentAttributes)it.next();
	                if((assesmentAttr.getStatus().intValue() == AssesmentAttributes.NO_EDITABLE) 
	                      && assesmentAttr.getStart().before(c.getTime()) && assesmentAttr.getEnd().after(c.getTime())) {
	                    assesment = assesmentAttr.getId();
	                }
	            }
	        }
        }
        Filter filter = new Filter(login,assesment);
		userSessionData.setFilter(filter);
		userSessionData.setLenguage(data.getLanguage());
        userSessionData.setRole(data.getRole());
	}	
	
	/**
	 * 
	 * @param login
	 * @throws Exception
	 */
	public void loadUser(String login, Integer assessment) throws Exception {
	
		userSessionData = new UserSessionData();
		
		UsReportFacade userReportFacade = getUserReportFacade();
		UserData data= userReportFacade.findUserByPrimaryKey(login,userSessionData);
        Filter filter = new Filter(login,assessment);
		userSessionData.setFilter(filter);
		userSessionData.setLenguage(data.getLanguage());
        userSessionData.setRole(data.getRole());
	}	

	/**
	 * 
	 * @throws DeslogedException
	 * @throws InvalidDataException
	 * @throws RemoteException
	 * @throws CommunicationProblemException
	 * @throws NamingException
	 * @throws CreateException
	 */
	public void loadText() throws DeslogedException, InvalidDataException,RemoteException,
	   CommunicationProblemException, NamingException, CreateException {
	    long start = System.currentTimeMillis();
	    if(userSessionData.getLenguage().equals("vt") || userSessionData.getLenguage().equals("in") || userSessionData.getLenguage().equals("id") || userSessionData.getLenguage().equals("ph") || userSessionData.getLenguage().equals("pk")) {
			text = new Text_vt(userSessionData.getLenguage());
			text.setContents(TextContainer.getInstance().getTexts("en",this,userSessionData));
	    }else {
			text = new Text();
			text.setContents(TextContainer.getInstance().getTexts(userSessionData.getLenguage(),this,userSessionData));
	    }
		System.out.println("-------->>>>> "+String.valueOf(System.currentTimeMillis()-start));
	}
	
	/**
	 * 
	 * @throws DeslogedException
	 * @throws InvalidDataException
	 * @throws RemoteException
	 * @throws CommunicationProblemException
	 * @throws NamingException
	 * @throws CreateException
	 */
	public void reloadText() throws DeslogedException, InvalidDataException,RemoteException,
	   CommunicationProblemException, NamingException, CreateException {
		
	    text = new Text();
		text.setContents(TextContainer.getInstance().
				reloadTexts(userSessionData.getLenguage(),this,userSessionData));
	}	
	
	public void updateLanguage(Text text) 	
	throws DeslogedException, InvalidDataException,RemoteException,
	   CommunicationProblemException, NamingException, CreateException {
	    text = new Text();
		text.setContents(TextContainer.getInstance().getTexts(userSessionData.getLenguage(),this,userSessionData));			
	}

	public void updatePermissions() 	
	throws Exception {
		UsReportFacade userReportFacade = getUserReportFacade();
		UserData data= userReportFacade.findUserByPrimaryKey(userSessionData.getFilter().getLoginName(),userSessionData);
        userSessionData.setRole(data.getRole());
	}
	   
    public void updatePermissions(UserData data) throws Exception {
        userSessionData.setRole(data.getRole());
    }

    public void updateFilter() throws Exception {
        UsReportFacade userReportFacade = getUserReportFacade();
        UserData data= userReportFacade.findUserByPrimaryKey(userSessionData.getFilter().getLoginName(),userSessionData);
        updateFilter(data);
    }

    public void updateFilter(UserData data) throws Exception {
        UsReportFacade userReportFacade = getUserReportFacade();
        Integer assesment = null;
        if(data.getRole().equals(SecurityConstants.ACCESS_TO_SYSTEM)) {
            Calendar c = Calendar.getInstance();
            Iterator it = userReportFacade.findUserAssesments(userSessionData.getFilter().getLoginName(),userSessionData).iterator();
            while(it.hasNext()) {
                AssesmentAttributes assesmentAttr = (AssesmentAttributes)it.next();
                if((assesmentAttr.getStatus().intValue() == AssesmentAttributes.NO_EDITABLE) 
                      && assesmentAttr.getStart().before(c.getTime()) && assesmentAttr.getEnd().after(c.getTime())) {
                    assesment = assesmentAttr.getId();
                }
            }
        }
        Filter filter = new Filter(data.getLoginName(),assesment);
        userSessionData.setFilter(filter);
    }

    public UserSessionData getUserSessionData() {
		return userSessionData;
	}

	


	public Text getText() {
		return text;
	}
	

	public UsABMFacade getUserABMFacade() throws DCSecurityException, DeslogedException, CommunicationProblemException {
		UsABMFacade userABMFacade = null;
	
		if (userSessionData == null) {
			DeslogedException de = new DeslogedException("Class business.SystemAccess(getUserABMFacade) " +
					", session data is empty","generic.error.deslogued");
			throw de;
		}

		try {
			UsABMFacadeHome home = UsABMFacadeUtil.getHome();			
			userABMFacade = home.create();
		
		} catch (NamingException naming) {
			logger.fatal(logFormatting.formattingText(naming,"getUserABMFacade()","NamingException",userSessionData.getFilter().getLoginName()), naming);
			throw new CommunicationProblemException("Has ocurred error in business.SystemAccess(getUserABMFacade) , can not access UserABM, NamingException","business.systemaccess.notaccess.userabmfacade");
		} catch (CreateException create) {
			logger.fatal(logFormatting.formattingText(create,"getUserABMFacade()","CreateException",userSessionData.getFilter().getLoginName()), create);
			throw new CommunicationProblemException("Has ocurred error in business.SystemAccess(getUserABMFacade), can not access UserABM, CreateException","business.systemaccess.notaccess.userabmfacade");
		} catch (RemoteException remote) {
			if(remote.getCause().toString().indexOf("SecurityException" )<=0){
				
				String loginName = "";
				if (userSessionData != null &&
					userSessionData.getFilter() != null &&
					userSessionData.getFilter().getLoginName() != null){
					loginName = userSessionData.getFilter().getLoginName(); 
				}
				
				logger.fatal(logFormatting.formattingText(remote,"getUserABMFacade()",
						"RemoteException",loginName), remote);
				throw new CommunicationProblemException("Has ocurred error in business.SystemAccess(getUserABMFacade), " +
						"can not access UserABM, RemoteException","business.systemaccess.notaccess.userabmfacade");
			}
			else{
				
				logger.fatal(logFormatting.formattingText(remote,"getUserABMFacade()","SecutityException",userSessionData.getFilter().getLoginName()), remote);
				throw new DCSecurityException("Class business.SystemAccess(getUserABMFacade()), can not access getUserABMFacadeHome , SecurityException","general.messages.securityexception");
			}

		}

		return userABMFacade;
	}


	public UsReportFacade getUserReportFacade() throws DCSecurityException, DeslogedException, CommunicationProblemException {

		UsReportFacade userReportFacade = null;

		if (userSessionData == null) {
			throw new DeslogedException("Class business.SystemAccess(getUserReportFacade), session data is empty","generic.error.deslogued");
		}

		try {
			
			UsReportFacadeHome home = UsReportFacadeUtil.getHome();
			userReportFacade = home.create();

		} catch (NamingException naming) {
			logger.fatal(logFormatting.formattingText(naming,"getUserReportFacade()","NamingException",userSessionData.getFilter().getLoginName()), naming);

			throw new CommunicationProblemException("Has ocurred error in business.SystemAccess(getUserReportFacade), can not access UserReportFacadeHome, NamingException","business.systemaccess.notaccess.userreportfacade");
		} catch (CreateException create) {
			logger.fatal(logFormatting.formattingText(create,"getUserReportFacade()","CreateException",userSessionData.getFilter().getLoginName()), create);

			throw new CommunicationProblemException("Has ocurred error in business.SystemAccess(getUserReportFacade), can not access UserReportFacade, CreateException","business.systemacess.notaccess.userreportfacade");
		} catch (RemoteException remote) {
			if(remote.getCause().toString().indexOf("SecurityException" )<=0){
				
				String loginName = "";
				if (userSessionData != null &&
					userSessionData.getFilter() != null &&
					userSessionData.getFilter().getLoginName() != null){
					loginName = userSessionData.getFilter().getLoginName(); 
				}
				
				logger.fatal(logFormatting.formattingText(remote,"getUserReportFacade()",
						"RemoteException",loginName), remote);
				throw new CommunicationProblemException("Has ocurred error in business.SystemAccess(getUserReportFacade), can not access UserReportFacadeHome , RemoteException","business.systemacess.notaccess.userreportfacade");
			}
			else{
				
				logger.fatal(logFormatting.formattingText(remote,"getUserReportFacade()","SecutityException",userSessionData.getFilter().getLoginName()), remote);
				throw new DCSecurityException("Class business.SystemAccess(getUserReportFacade()), can not access getUserReportFacadeHome , SecurityException","general.messages.securityexception");
			}

		}

		return userReportFacade;
	}




	public CorpABMFacade getCorporationABMFacade()
		throws DeslogedException, CommunicationProblemException, DCSecurityException {

		CorpABMFacade corporationABMFacade = null;

		if (userSessionData == null) {
			DeslogedException de=new DeslogedException("Class business.SystemAccess(getCorporationABMFacade), session data is empty","generic.error.deslogued"); 
			throw de;
		}

		try {
			CorpABMFacadeHome home = CorpABMFacadeUtil.getHome();
			corporationABMFacade = home.create();

		} catch (NamingException naming) {
			logger.fatal(logFormatting.formattingText(naming,"getCorporationABMFacade()","NamingException",userSessionData.getFilter().getLoginName()), naming);
			throw new CommunicationProblemException("Has ocurred error in business.SystemAccess(getCorporationABMFacade), can not access CorporationABMFacadeHome, NamingException","business.systemaccess.notaccess.corporationabmfacade");
		} catch (CreateException create) {
			logger.fatal(logFormatting.formattingText(create,"getCorporationABMFacade()","CreateException",userSessionData.getFilter().getLoginName()), create);
			throw new CommunicationProblemException("Has ocurred error in business.SystemAccess(getCorporationABMFacade), can not access CorporationABMFacadeFacade, CreateException","business.systemaccess.notaccess.corporationabmfacade");
		} catch (RemoteException remote) {
			if(remote.getCause().toString().indexOf("SecurityException" )<=0){
				
				logger.fatal(logFormatting.formattingText(remote,"getCorporationABMFacade()","RemoteException",userSessionData.getFilter().getLoginName()), remote);
				throw new CommunicationProblemException("Has ocurred error in business.SystemAccess(getCorporationABMFacade), can not access CorporationABMFacadeHome , RemoteException","business.systemaccessnotaccess.corporationabmfacade");
			}
			else{
				
				logger.fatal(logFormatting.formattingText(remote,"getCorporationABMFacade()","SecutityException",userSessionData.getFilter().getLoginName()), remote);
				throw new DCSecurityException("Class business.SystemAccess(getCorporationABMFacade()), can not access CorporationABMFacadeHome , SecurityException","general.messages.securityexception");
			}

		}
		
		return corporationABMFacade;
	}


	public CorpReportFacade getCorporationReportFacade()
			throws DCSecurityException, DeslogedException, CommunicationProblemException {

		CorpReportFacade corporationReportFacade = null;

		if (userSessionData == null) {
			DeslogedException de=new DeslogedException("Class business.SystemAccess(getCorporationRepoortFacade), session data is empty","generic.error.deslogued");
			throw de;
		}

		try {
			CorpReportFacadeHome home = CorpReportFacadeUtil.getHome();
			corporationReportFacade = home.create();

		} catch (NamingException naming) {
			logger.fatal(logFormatting.formattingText(naming,"getCorporationReportFacade()","NamingException",userSessionData.getFilter().getLoginName()), naming);
			throw new CommunicationProblemException("Class business.SystemAccess(getCorporationReportFacde), can not access CorporationReportFacadeHome, NamingException","business.systemacess.notaccess.corporationreportfacde");
		} catch (CreateException create) {
			logger.fatal(logFormatting.formattingText(create,"getCorporationReportFacade()","CreateException",userSessionData.getFilter().getLoginName()), create);
			throw new CommunicationProblemException("Class business.SystemAccess(getCorporationReportFacde), can not access CorporationReportFacadeFacade, CreateException","business.systemacess.notaccess.corporationreportfacde");
		} catch (RemoteException remote) {
			if(remote.getCause().toString().indexOf("SecurityException" )<=0){
				
				logger.fatal(logFormatting.formattingText(remote,"getCorporationReportFacade()","RemoteException",userSessionData.getFilter().getLoginName()), remote);
				throw new CommunicationProblemException("Class business.SystemAccess(getCorporationReportFacde), can not access CorporationReportFacadeHome , RemoteException","business.systemacess.notaccess.corporationreportfacde");
			}
			else{
				
				logger.fatal(logFormatting.formattingText(remote,"getCorporationReportFacde()","SecutityException",userSessionData.getFilter().getLoginName()), remote);
				throw new DCSecurityException("Class business.SystemAccess(getCorporationReportFacde), can not access CorporationReportFacadeHome , SecurityException","general.messages.securityexception");
			}
		}

		return corporationReportFacade;
		
	}

    public AssesmentABMFacade getAssesmentABMFacade() throws Exception {
        
        AssesmentABMFacade assesmentABMFacade = null;
        if (userSessionData == null) {
            DeslogedException de=new DeslogedException("getAssesmentABMFacade","session = null");
            throw de;
        }
        try {
            AssesmentABMFacadeHome home = AssesmentABMFacadeUtil.getHome();
            assesmentABMFacade = home.create();
        
        } catch (Exception e) {
            logger.fatal(logFormatting.formattingText(e,"getAssesmentABMFacade","exception",userSessionData.getFilter().getLoginName()), e);
            throw new DCSecurityException("getAssesmentABMFacade","exception");
        }
        return assesmentABMFacade;
    }

    public AssesmentReportFacade getAssesmentReportFacade() throws Exception {
        
        AssesmentReportFacade assesmentReportFacade = null;
        if (userSessionData == null) {
            DeslogedException de=new DeslogedException("getAssesmentReportFacade","session = null");
            throw de;
        }
        try {
            AssesmentReportFacadeHome home = AssesmentReportFacadeUtil.getHome();
            assesmentReportFacade = home.create();
        
        } catch (Exception e) {
            logger.fatal(logFormatting.formattingText(e,"getCorporationReportFacade()","exception",userSessionData.getFilter().getLoginName()), e);
            throw new DCSecurityException("getCorporationReportFacde()","exception");
        }
        return assesmentReportFacade;
    }
    
    public LangReportFacade getLanguageReportFacade() throws Exception {
        if (userSessionData == null) {
            throw new DeslogedException("getLanguageReportFacade()","session = null");
        }
        try {
            LangReportFacadeHome home = LangReportFacadeUtil.getHome();
            LangReportFacade languageReportFacade = home.create();
            return languageReportFacade;
        } catch (Exception e) {
            handler.handleException("getLanguageReportFacade()",e);
        }
        return null;
    }
    
    public LangABMFacade getLanguageABMFacade() throws Exception {
        if (userSessionData == null) {
            throw new DeslogedException("getLanguageABMFacade()","session = null");
        }
        try {
            LangABMFacadeHome home = LangABMFacadeUtil.getHome();
            LangABMFacade languageABMFacade = home.create();
            return languageABMFacade;
        } catch (Exception e) {
            handler.handleException("getLanguageABMFacade()",e);
        }
        return null;
    }

    public ModuleABMFacade getModuleABMFacade() throws Exception {
        ModuleABMFacade moduleABMFacade = null;
        if (userSessionData == null) {
            throw new DeslogedException("getModuleABMFacade","session = null");
        }
        try {
            ModuleABMFacadeHome home = ModuleABMFacadeUtil.getHome();
            moduleABMFacade = home.create();
        } catch (Exception e) {
            throw new DCSecurityException("getModuleABMFacade","exception");
        }
        return moduleABMFacade;
    }

    public ModuleReportFacade getModuleReportFacade() throws Exception {
        ModuleReportFacade moduleReportFacade = null;
        if (userSessionData == null) {
            throw new DeslogedException("getModuleReportFacade","session = null");
        }
        try {
            ModuleReportFacadeHome home = ModuleReportFacadeUtil.getHome();
            moduleReportFacade = home.create();
        } catch (Exception e) {
            throw new DCSecurityException("getModuleReportFacade","exception");
        }
        return moduleReportFacade;
    }

    public QuestionABMFacade getQuestionABMFacade() throws Exception {
        QuestionABMFacade questionABMFacade = null;
        if (userSessionData == null) {
            throw new DeslogedException("getQuestionABMFacade ","session = null");
        }
        try {
            QuestionABMFacadeHome home = QuestionABMFacadeUtil.getHome();
            questionABMFacade = home.create();
        } catch (Exception e) {
            throw new DCSecurityException("getQuestionABMFacade","exception");
        }
        return questionABMFacade;
    }

    public QuestionReportFacade getQuestionReportFacade() throws Exception {
        QuestionReportFacade questionReportFacade = null;
        if (userSessionData == null) {
            throw new DeslogedException("getQuestionReportFacade ","session = null");
        }
        try {
            QuestionReportFacadeHome home = QuestionReportFacadeUtil.getHome();
            questionReportFacade = home.create();
        } catch (Exception e) {
            throw new DCSecurityException("getQuestionReportFacade","exception");
        }
        return questionReportFacade;
    }

    public TagReportFacade getTagReportFacade() throws Exception {
        TagReportFacade tagReportFacade = null;
        if (userSessionData == null) {
            throw new DeslogedException("getTagReportFacade ","session = null");
        }
        try {
            TagReportFacadeHome home = TagReportFacadeUtil.getHome();
            tagReportFacade = home.create();
        } catch (Exception e) {
            throw new DCSecurityException("getTagReportFacade","exception");
        }
        return tagReportFacade;
    }

    public TagABMFacade getTagABMFacade() throws Exception {
        TagABMFacade tagABMFacade = null;
        if (userSessionData == null) {
            throw new DeslogedException("getTagABMFacade ","session = null");
        }
        try {
            TagABMFacadeHome home = TagABMFacadeUtil.getHome();
            tagABMFacade = home.create();
        } catch (Exception e) {
            throw new DCSecurityException("getTagABMFacade","exception");
        }
        return tagABMFacade;
    }

    /**
     * 
     * @return PropertyReportFacade
     */
	public PropertyReportFacade getPropertyReportFacade() throws Exception{
		 PropertyReportFacade propertyReportFacade = null;
        if (userSessionData == null) {
            throw new DeslogedException("getPropertyReportFacade ","session = null");
        }
        try {
            PropertyReportFacadeHome home = PropertyReportFacadeUtil.getHome();
            propertyReportFacade = home.create();
        } catch (Exception e) {
            throw new DCSecurityException("getPropertyReportFacade","exception");
        }
        return propertyReportFacade;
	}
	
	 /**
     * 
     * @return PropertyABMFacade
     */
	public PropertyABMFacade getPropertyABMFacade() throws Exception{
		
		PropertyABMFacade propertyABMFacade = null;
        if (userSessionData == null) {
            throw new DeslogedException("getPropertyABMFacade ","session = null");
        }
        try {
            PropertyABMFacadeHome home = PropertyABMFacadeUtil.getHome();
            propertyABMFacade = home.create();
        } catch (Exception e) {
            throw new DCSecurityException("getPropertyABMFacade","exception");
        }
        return propertyABMFacade;
	}
	
	public boolean hasAssessment() {
		return userSessionData.getFilter().getAssesment() != null;
	}

	public String getLoginUser() {
		return userSessionData.getFilter().getLoginName();
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
	public boolean isTelematics() {
		int id = (hasAssessment()) ? userSessionData.getFilter().getAssesment().intValue() : 0;
		return (id == AssesmentData.REPORTE_ACCIDENTE || id == AssesmentData.COACHING_TELEMATICS_1 || id == AssesmentData.COACHING_TELEMATICS_2 || id == AssesmentData.COACHING_TELEMATICS_3 || id == AssesmentData.COACHING_TELEMATICS_4 || id == AssesmentData.COACHING_TELEMATICS_5);
	}
	

}