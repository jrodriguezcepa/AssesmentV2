package assesment.presentation.actions.question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import assesment.business.AssesmentAccess;
import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.module.ModuleData;
import assesment.communication.question.QuestionData;
import assesment.presentation.translator.web.util.AbstractAction;

public class QuestionDeleteAction extends AbstractAction {


    public ActionForward cancel(HttpSession session, ActionMapping mapping, ActionForm form) {
        return null;
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        
        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        DynaActionForm questionForm = (DynaActionForm)form;
/*
 *         if(msg != null) {
            session.setAttribute("Msg",messages.getText(msg));
            return mapping.findForward("back");
        }

 */
        String target = questionForm.getString("target");
        Integer questionId = new Integer(questionForm.getString("question"));

        QuestionData question = sys.getQuestionReportFacade().findQuestion(questionId,sys.getUserSessionData(),QuestionData.NORMAL);

        sys.getQuestionABMFacade().delete(questionId,sys.getUserSessionData(),QuestionData.NORMAL);
        
        ModuleData module = sys.getModuleReportFacade().findModule(question.getModule(),sys.getUserSessionData());
        sys.getQuestionABMFacade().updateOrder(module,sys.getUserSessionData(),QuestionData.NORMAL);

        if(target.equals("module")) {
            session.setAttribute("module",String.valueOf(module.getId()));
            return mapping.findForward("module");
        }else {
            AssesmentAttributes assesment = sys.getAssesmentReportFacade().findAssesmentAttributes(module.getAssesment(),sys.getUserSessionData());
            session.setAttribute("assesment",String.valueOf(assesment.getId()));
            return mapping.findForward("assesment");
        }
    }
}