package assesment.presentation.actions.question;

import java.util.Collection;

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
import assesment.presentation.translator.web.util.Util;

public class QuestionDeleteListAction extends AbstractAction {


    public ActionForward cancel(HttpSession session, ActionMapping mapping, ActionForm form) {
        return null;
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        
        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        DynaActionForm questionForm = (DynaActionForm)form;

        Collection list = Util.stringToCollection(questionForm.getString("questions"));
        String module = questionForm.getString("module");

        sys.getQuestionABMFacade().deleteList(list,sys.getUserSessionData(),QuestionData.NORMAL);
        
        ModuleData moduleData = sys.getModuleReportFacade().findModule(new Integer(module),sys.getUserSessionData());
        sys.getQuestionABMFacade().updateOrder(moduleData,sys.getUserSessionData(),QuestionData.NORMAL);

        session.setAttribute("module",module);
        return mapping.findForward("module");
    }
}