/*
 * Created on 17-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.administration;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import assesment.business.AssesmentAccess;
import assesment.communication.administration.UserAnswerData;
import assesment.communication.language.Text;
import assesment.communication.module.ModuleData;
import assesment.communication.question.AnswerData;
import assesment.communication.question.QuestionData;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

public class EmeaUserAnswerAction extends AbstractAction {

    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        return mapping.findForward("menu");
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        Text messages = sys.getText();
        
        try {
            int[] questionIds = null;
            String[][] answerIds = null;
            ModuleData moduleData = sys.getModuleReportFacade().findModule(new Integer(81),sys.getUserSessionData());
            
            Collection<UserAnswerData> answerList = new LinkedList<UserAnswerData>();
            Iterator it = moduleData.getQuestionIterator();
            while(it.hasNext()) {
                QuestionData question = (QuestionData)it.next();
                if(question.getType().intValue() == QuestionData.DATE || question.getType().intValue() == QuestionData.OPTIONAL_DATE) {
                	Date date = Util.getDate(request.getParameter("questionDay"+String.valueOf(question.getId())), request.getParameter("questionMonth"+String.valueOf(question.getId())), request.getParameter("questionYear"+String.valueOf(question.getId())));
                	if(date == null) {
                		session.setAttribute("Msg", messages.getText("assesment.test.missingdata")+" "+messages.getText(question.getKey()));
                		return mapping.findForward("back");
                	}else {
                		answerList.add(new UserAnswerData(question.getId(),date));
                	}
                }else if(question.getType().intValue() == QuestionData.EMAIL) {
                    String value = request.getParameter("question"+String.valueOf(question.getId()));
                    if(!Util.checkEmail(value)) {
                		session.setAttribute("Msg", messages.getText("assesment.test.missingdata")+" "+messages.getText(question.getKey()));
                		return mapping.findForward("back");
                    }else {
                        answerList.add(new UserAnswerData(question.getId(),value));
                    }
                }else if(question.getType().intValue() == QuestionData.TEXT) {
                	String value = request.getParameter("question"+String.valueOf(question.getId()));
                	switch(question.getId().intValue()) {
                		case 1552:case 1643:case 1644:case 1645:case 1646:case 1647:
	                        answerList.add(new UserAnswerData(question.getId(),value));
	                        break;
                		case 1556:
                			if(!request.getParameter("question1555").equals("6227")) {
    	                        answerList.add(new UserAnswerData(question.getId(),""));
                			}else {
                				if(Util.empty(value) || value.equals(messages.getText("messages.emea.writesite"))) {
                					session.setAttribute("Msg", messages.getText("assesment.test.missingdata")+" "+messages.getText(question.getKey()));
                					return mapping.findForward("back");
                				}else {
                					answerList.add(new UserAnswerData(question.getId(),value));
                				}
    	                    }
                			break;
                		case 1557:
                			if(!request.getParameter("question1532").equals("6269")) {
    	                        answerList.add(new UserAnswerData(question.getId(),""));
                			}else {
                    			if(Util.empty(value) || value.equals(messages.getText("messages.emea.writemodel"))) {
        	                		session.setAttribute("Msg", messages.getText("assesment.test.missingdata")+" "+messages.getText(question.getKey()));
        	                		return mapping.findForward("back");
        	                    }else {
        	                        answerList.add(new UserAnswerData(question.getId(),value));
        	                    }
                			}
                			break;
                		case 1558:
                			if(!request.getParameter("question1531").equals("6232")) {
    	                        answerList.add(new UserAnswerData(question.getId(),""));
                			}else {
        	                    if(Util.empty(value) || value.equals(messages.getText("messages.emea.writemake"))) {
        	                		session.setAttribute("Msg", messages.getText("assesment.test.missingdata")+" "+messages.getText(question.getKey()));
        	                		return mapping.findForward("back");
        	                    }else {
        	                        answerList.add(new UserAnswerData(question.getId(),value));
        	                    }
                			}
                			break;
                		default:
    	                    if(Util.empty(value)) {
    	                		session.setAttribute("Msg", messages.getText("assesment.test.missingdata")+" "+messages.getText(question.getKey()));
    	                		return mapping.findForward("back");
    	                    }else {
    	                        answerList.add(new UserAnswerData(question.getId(),value));
    	                    }
                			break;
                	}
                }else if(question.getType().intValue() == QuestionData.EXCLUDED_OPTIONS || question.getType().intValue() == QuestionData.LIST) {
                    String value = request.getParameter("question"+String.valueOf(question.getId()));
                    boolean validate = true;
                    switch(question.getId().intValue()) {
                    	case 1649:
                    		validate = (request.getParameter("answer6062") != null && request.getParameter("answer6062").equals("on"));
                    		break;
                    	case 1650:
                    		validate = (request.getParameter("answer6063") != null && request.getParameter("answer6063").equals("on"));
                    		break;
                    	case 1651:
                    		validate = (request.getParameter("answer6064") != null && request.getParameter("answer6064").equals("on"));
                    		break;
                    	case 1652:
                    		validate = (request.getParameter("answer6065") != null && request.getParameter("answer6065").equals("on"));
                    		break;
                    	case 1653:
                    		validate = (request.getParameter("answer6066") != null && request.getParameter("answer6066").equals("on"));
                    		break;
                    	case 1654:
                    		validate = (request.getParameter("answer6067") != null && request.getParameter("answer6067").equals("on"));
                    		break;
                    	case 1655:
                    		validate = (request.getParameter("answer6270") != null && request.getParameter("answer6270").equals("on"));
                    		break;
                    }
                    if(validate && (Util.empty(value) || value.equals("-1"))) {
                		session.setAttribute("Msg", messages.getText("assesment.test.missingdata")+" "+messages.getText(question.getKey()));
                		return mapping.findForward("back");
                    }else {
                    	if(!validate) {
                    		answerList.add(new UserAnswerData(question.getId()));
                    	}else {
                    		answerList.add(new UserAnswerData(question.getId(),new Integer(value)));
                    	}
                    }
                }else if(question.getType().intValue() == QuestionData.INCLUDED_OPTIONS) {
                    Iterator<AnswerData> answers = question.getAnswerIterator();
                    Collection<Integer> options = new LinkedList<Integer>();
                    while(answers.hasNext()) {
                        AnswerData answer = answers.next();
                        if(request.getParameter("answer"+String.valueOf(answer.getId())) != null) {
                            if(request.getParameter("answer"+String.valueOf(answer.getId())).equals("on")) {
                                options.add(answer.getId());
                            }
                        }
                    }
                    answerList.add(new UserAnswerData(question.getId(),options));
                }else if(question.getType().intValue() == QuestionData.KILOMETERS) {
                    UserAnswerData data = new UserAnswerData(question.getId());
                    String value = request.getParameter("question"+String.valueOf(question.getId()));
                    if(Util.empty(value)) {
                		session.setAttribute("Msg", messages.getText("assesment.test.missingdata")+" "+messages.getText(question.getKey()));
                		return mapping.findForward("back");
                    }else if(!Util.isNumber(value)) {
                		session.setAttribute("Msg", messages.getText(question.getKey())+" "+messages.getText("assessment.emea.wrongnumber"));
                		return mapping.findForward("back");
                    }else {
                    	data.setDistance(new Integer(request.getParameter("unit"+String.valueOf(question.getId()))));
                    	data.setText(value);
                    	answerList.add(data);
                    }
                }
            }
            
            sys.getUserABMFacade().saveModuleAnswers(sys.getUserSessionData().getFilter().getLoginName(),sys.getUserSessionData().getFilter().getAssesment(),answerList,-1,-1,sys.getUserSessionData(),false,null,false);
            
        }catch(Exception e) {
            e.printStackTrace();
        }

        return mapping.findForward("success");
    }


}