/*
 * Created on 08-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.assesment;

import java.util.Collection;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import assesment.business.AssesmentAccess;
import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.assesment.FeedbackData;
import assesment.communication.language.Text;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

/**
 * @author fcarriquiry
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FeedbackAction extends AbstractAction {


    public ActionForward cancel(HttpSession session, ActionMapping mapping, ActionForm form) {
        session.setAttribute("assesment",((FeedbackForm)form).getAssessment());
        return mapping.findForward("success");
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        
        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        Text messages = sys.getText();
        
        FeedbackForm feedbackForm = (FeedbackForm)form;
        
        if(!Util.checkEmail(feedbackForm.getEmail())) {
            session.setAttribute("Msg",messages.getText("feedback.error.email"));
            return mapping.findForward("back");
        }

        Collection<Integer> reports = new LinkedList<Integer>();
        reports.add(new Integer(FeedbackData.GENERAL_RESULT_REPORT));
        reports.add(new Integer(FeedbackData.PSI_RESULT_REPORT));
        reports.add(new Integer(FeedbackData.ERROR_RESULT_REPORT));
        reports.add(new Integer(FeedbackData.PERSONAL_DATA_REPORT));

        if(reports.size() == 0) {
            session.setAttribute("Msg",messages.getText("feedback.error.report"));
            return mapping.findForward("back");
        }

        sys.getAssesmentABMFacade().updateFeedback(new Integer(feedbackForm.getAssessment()),feedbackForm.getEmail(),reports,sys.getUserSessionData());
        
        session.removeAttribute("AssesmentFeedbackForm");
        session.setAttribute("assesment",feedbackForm.getAssessment());
        return mapping.findForward("success");
    }
}