/*
 * Created on 08-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import assesment.business.AssesmentAccess;
import assesment.communication.question.QuestionData;
import assesment.presentation.translator.web.util.AbstractAction;

public class QuestionChangeOrderAction extends AbstractAction {


    public ActionForward cancel(HttpSession session, ActionMapping mapping, ActionForm form) {
        session.setAttribute("module",((DynaActionForm)form).getString("module"));
        return mapping.findForward("cancel");
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        
        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        
        DynaActionForm questionForm = (DynaActionForm)form;
        Integer questionDown = new Integer(questionForm.getString("questionDown"));
        Integer questionUp = new Integer(questionForm.getString("questionUp"));
        
        sys.getQuestionABMFacade().moveQuestion(questionDown,1,sys.getUserSessionData(),QuestionData.NORMAL);
        sys.getQuestionABMFacade().moveQuestion(questionUp,-1,sys.getUserSessionData(),QuestionData.NORMAL);
                
        session.setAttribute("module",questionForm.getString("module"));
        return mapping.findForward("success");
    }
    
}