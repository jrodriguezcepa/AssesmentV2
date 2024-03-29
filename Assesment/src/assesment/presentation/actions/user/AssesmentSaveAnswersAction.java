/*
 * Created on 17-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import assesment.business.AssesmentAccess;
import assesment.business.administration.user.UsABMFacade;
import assesment.business.administration.user.UsReportFacade;
import assesment.business.assesment.AssesmentReportFacade;
import assesment.communication.administration.AccessCodeData;
import assesment.communication.administration.UserAnswerData;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.exception.InvalidDataException;
import assesment.communication.language.Text;
import assesment.communication.module.ModuleData;
import assesment.communication.question.QuestionData;
import assesment.communication.security.SecurityConstants;
import assesment.communication.user.UserData;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;


public class AssesmentSaveAnswersAction extends AbstractAction {

    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        return mapping.findForward("logout");
    }

    public ActionForward action(ActionMapping mapping, ActionForm answers, HttpServletRequest request, HttpServletResponse response) throws Throwable {

        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        UserSessionData userSessionData = sys.getUserSessionData();
        Text messages = sys.getText();
        
        DynaActionForm answerData = (DynaActionForm) answers;
        String assesmentId = answerData.getString("assesment");

        try {
            String webinarCode=request.getParameter("webinarCode").trim().toUpperCase();
            if (!validateCode(webinarCode)) {
                session.setAttribute("Msg","user.error.invalidCode");
          		return mapping.findForward("error");
            }
    		session.setAttribute("webinarcode", webinarCode);

            Connection connDC = (SecurityConstants.isProductionServer()) ? DriverManager.getConnection("jdbc:postgresql://18.229.182.37:5432/datacenter5","postgres","pr0v1s0r1A") : DriverManager.getConnection("jdbc:postgresql://localhost:5432/datacenter5","postgres","pr0v1s0r1A");
            Statement stDC = connDC.createStatement();

            boolean existCode = false;
            ResultSet set = stDC.executeQuery("SELECT state FROM cepaactivity WHERE code = '"+webinarCode+"'");
            if(set.next()) {
        		String activityState = set.getString(1).toLowerCase();
        		if(activityState.equals("datatype.scheduledactivitystate.prevista")) {
        			existCode = true;
        		} 
            }
            connDC.close();

            
            UsReportFacade userReport = sys.getUserReportFacade();
            UsABMFacade usABMFacade = sys.getUserABMFacade();
            
            String firstName = "User";
            String lastName = "Webinar";
            
            AssesmentData assessment = sys.getAssesmentReportFacade().findAssesment(new Integer(assesmentId), userSessionData);
            Collection<UserAnswerData> answerList = new LinkedList<UserAnswerData>();
            HashMap<Integer, String> answersForm= new HashMap<Integer, String>();
            Iterator<ModuleData> modules = assessment.getModuleIterator();
            boolean message=false;
            while(modules.hasNext()) {
            	ModuleData module = modules.next();
                Iterator<QuestionData> questions = module.getQuestionIterator();
            	while(questions.hasNext()) {
            		QuestionData question = questions.next();
                    String answerId = request.getParameter(String.valueOf(question.getId()));
    				int questionType =question.getType().intValue();
                 	
                    UserAnswerData usrAnswerData=new UserAnswerData();
                	usrAnswerData.setQuestion(question.getId());
                 	if(questionType == QuestionData.LIST || questionType == QuestionData.EXCLUDED_OPTIONS || questionType == QuestionData.COUNTRY) {
                 		if(Util.isNumber(answerId)) {
                 			answersForm.put(question.getId(), String.valueOf(answerId));
                 			session.setAttribute("answersHash", answersForm);
                     		usrAnswerData.setAnswer(new Integer(answerId));
                 		} else {
                 			if(!message) {
                     			session.setAttribute("Msg", messages.getText("report.userresult.pending")+" '"+messages.getText(question.getKey())+"'");
                     			message=true;
                 			}
                 		}
                	} else if(questionType == QuestionData.DATE || questionType == QuestionData.BIRTHDATE){
                		Date dateValue = formatDateGuion(answerId);
	                	if(dateValue != null) {
	                		if(questionType == QuestionData.BIRTHDATE) {
	                			if(isAdult(dateValue)) {
	                        		usrAnswerData.setDate(dateValue);
	                     			answersForm.put(question.getId(),answerId);

	                			}else {
	                     			if(!message) {
		                     			session.setAttribute("Msg", messages.getText("report.userresult.wrong")+" '"+messages.getText(question.getKey())+"'");
	                         			message=true;
	                     			}
	                			}
		                	}else {
                        		usrAnswerData.setDate(dateValue);
                     			answersForm.put(question.getId(),answerId);
		                	}
	                	}else {
	                		if(!message) {
	                 			session.setAttribute("Msg", messages.getText("report.userresult.pending")+" '"+messages.getText(question.getKey())+"'");
                     			message=true;
                 			}
	                	}
                	} else if(questionType == QuestionData.EMAIL){
                		if(Util.empty(answerId)) {
	                		if(!message) {
	                			session.setAttribute("Msg", messages.getText("report.userresult.pending")+" '"+messages.getText(question.getKey())+"'");
                     			message=true;
                 			}
                		}
                		if(Util.checkEmail(answerId)) {
                    		usrAnswerData.setText(answerId);
                 			answersForm.put(question.getId(), String.valueOf(answerId));
                		}else {
	                		if(!message) {
	                 			session.setAttribute("Msg", messages.getText("report.userresult.wrong")+": '"+messages.getText(question.getKey())+"'");
                     			message=true;
                 			}
                		}
                	} else if(questionType == QuestionData.KILOMETERS){
                		usrAnswerData.setText(answerId);
             			answersForm.put(question.getId(), String.valueOf(answerId));

                	} else {
                		if(Util.empty(answerId)) {
	                		if(!message) {
	                 			session.setAttribute("Msg", messages.getText("report.userresult.pending")+" '"+messages.getText(question.getKey())+"'");
                     			message=true;
                 			}
                		}
             			answersForm.put(question.getId(), String.valueOf(answerId));
             			usrAnswerData.setText(answerId);
                	}
                 	
            		if(QuestionData.isFirstNameQuestion(question.getId()))
            			firstName = answerId;
            		if(QuestionData.isLastNameQuestion(question.getId()))
            			lastName = answerId;
                 	answerList.add(usrAnswerData);
            	}
            }
            if(message) {
                return mapping.findForward("error");

            }else {
            	String lng = userSessionData.getLenguage();
        		String accesscode = "generate_"+lng+"_"+assesmentId;
        		AccessCodeData accessCodeData = sys.getAssesmentReportFacade().getAccessCode(accesscode, userSessionData);
        		Integer code = userReport.getNextCode(accesscode,userSessionData);
        		code = (code == null) ? 1 : code + 1;
        		String userName = "generate_"+lng+"_"+code+"_"+assesmentId;
        		UserData userData = new UserData(userName,userName,firstName, lastName,accessCodeData.getLanguage(),"cepasafedrive@gmail.com","systemaccess",null);
        		userData.setExtraData3(webinarCode);
        		if(!existCode)
        			userData.setExtraData2(webinarCode);
        		
        		usABMFacade.userCreateFromAC(userData,new Integer(assesmentId),accesscode,userSessionData);
                
        		usABMFacade.saveCompleteAnswers(userName, new Integer(assesmentId), answerList, userSessionData);
        		usABMFacade.setEndDate(userName, new Integer(assesmentId), userSessionData);
                session.setAttribute("webinaruser", userName);
                session.setAttribute("assesment", assesmentId);
                return mapping.findForward("next");
            	
            }
            
        }catch(Exception e) {
            Throwable thw = e;
            while (thw.getCause() != null) {
                thw = thw.getCause();
            }
            if (thw instanceof InvalidDataException) {
                 return mapping.findForward("error");
            }else {
                 return mapping.findForward("error");
                }
            }
       
                
    }
    
    public boolean validateCode(String code) {
    	return code.matches("[a-zA-Z0-9]*") && (code.length()==6);
    }

    protected Date formatDateGuion(String value) {
    	try {
    		if(value.contains("/")) {
		        StringTokenizer tokenizer = new StringTokenizer(value,"/");
		        Calendar c = Calendar.getInstance();
		        c.set(Calendar.DATE,Integer.parseInt(tokenizer.nextToken()));
		        c.set(Calendar.MONTH,Integer.parseInt(tokenizer.nextToken())-1);
		        int year = Integer.parseInt(tokenizer.nextToken());
		        if(year < 100) {
		        	year += 1900;
		        }
		        c.set(Calendar.YEAR,year);
		        return c.getTime();
    		}else {
		        StringTokenizer tokenizer = new StringTokenizer(value,"-");
		        Calendar c = Calendar.getInstance();
		        int year = Integer.parseInt(tokenizer.nextToken());
		        if(year < 100) {
		        	year += 1900;
		        }
		        c.set(Calendar.YEAR,year);
		        c.set(Calendar.MONTH,Integer.parseInt(tokenizer.nextToken())-1);
		        c.set(Calendar.DATE,Integer.parseInt(tokenizer.nextToken()));
		        return c.getTime();
    		}
    	}catch (Exception e) {
			return null;
		}
    }

	protected boolean isAdult(Date dateValue) {
		Calendar max = Calendar.getInstance();
		max.add(Calendar.YEAR, -100);
		Calendar now = Calendar.getInstance();
		now.add(Calendar.YEAR, -18);
		Calendar c = Calendar.getInstance();
		c.setTime(dateValue);
		return c.before(now) && max.before(c);
	}
}