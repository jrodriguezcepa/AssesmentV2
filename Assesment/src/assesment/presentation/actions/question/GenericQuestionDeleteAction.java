package assesment.presentation.actions.question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import assesment.business.AssesmentAccess;
import assesment.communication.module.ModuleData;
import assesment.communication.question.QuestionData;
import assesment.presentation.translator.web.util.AbstractAction;

public class GenericQuestionDeleteAction extends AbstractAction {


    public ActionForward cancel(HttpSession session, ActionMapping mapping, ActionForm form) {
        return null;
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        
        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        DynaActionForm questionForm = (DynaActionForm)form;

        Integer questionId = new Integer(questionForm.getString("question"));

        QuestionData question = sys.getQuestionReportFacade().findQuestion(questionId,sys.getUserSessionData(),QuestionData.GENERIC);

        sys.getQuestionABMFacade().delete(questionId,sys.getUserSessionData(),QuestionData.GENERIC);
        
        ModuleData module = sys.getModuleReportFacade().findGenericModule(question.getModule(),sys.getUserSessionData());
        sys.getQuestionABMFacade().updateOrder(module,sys.getUserSessionData(),QuestionData.GENERIC);

        session.setAttribute("module",String.valueOf(module.getId()));
        return mapping.findForward("module");
    }
}