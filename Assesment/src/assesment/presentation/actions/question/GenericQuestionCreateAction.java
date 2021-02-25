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

import assesment.business.AssesmentAccess;
import assesment.communication.language.Text;
import assesment.communication.module.ModuleData;
import assesment.communication.question.QuestionData;
import assesment.presentation.translator.web.util.AbstractAction;

/**
 * @author fcarriquiry
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class GenericQuestionCreateAction extends AbstractAction {


    public ActionForward cancel(HttpSession session, ActionMapping mapping, ActionForm form) {
        session.setAttribute("module",String.valueOf(((QuestionForm)form).getModule()));
        return mapping.findForward("cancel");
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        
        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        Text messages = sys.getText();
        
        QuestionForm questionForm = (QuestionForm)form;
        String msg = questionForm.validate();
        if(msg != null) {
            session.setAttribute("Msg",messages.getText(msg));
            return mapping.findForward("back");
        }

        Integer module = new Integer(questionForm.getModule());
        ModuleData moduleData = sys.getModuleReportFacade().findGenericModule(module,sys.getUserSessionData());

        String[] questionTexts = questionForm.getQuestionTexts();
        String[][] answerTexts = questionForm.getAnswerTexts();
        QuestionData question = questionForm.getData(moduleData);
        
        sys.getQuestionABMFacade().create(question,questionTexts,answerTexts,sys.getUserSessionData(),QuestionData.GENERIC);
        
        sys.reloadText();
        
        session.removeAttribute("GenericQuestionCreateForm");
        session.setAttribute("module",String.valueOf(module));
        return mapping.findForward("module");
    }
}