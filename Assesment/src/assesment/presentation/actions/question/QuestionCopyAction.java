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
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

public class QuestionCopyAction extends AbstractAction {

    public ActionForward cancel(HttpSession session, ActionMapping mapping, ActionForm form) {
        DynaActionForm moduleForm = (DynaActionForm)form;
        String module = moduleForm.getString("module");
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        if(moduleForm.getString("target").equals("module")) {
            session.setAttribute("module",module);
            return mapping.findForward("cancelModule");
        }else {
            ModuleData moduleData;
            try {
                moduleData = sys.getModuleReportFacade().findModule(new Integer(module),sys.getUserSessionData());
                session.setAttribute("assesment",String.valueOf(moduleData.getAssesment()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mapping.findForward("cancelAssesment");
        }
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        
        DynaActionForm moduleForm = (DynaActionForm)form;
        String ids = moduleForm.getString("questions");
        String module = moduleForm.getString("module");
        Collection list = Util.stringToCollection(ids);
        sys.getQuestionABMFacade().copyQuestion(new Integer(module),list,sys.getUserSessionData());
        
        sys.reloadText();
        if(moduleForm.getString("target").equals("module")) {
            session.setAttribute("module",module);
            return mapping.findForward("module");
        }else {
            ModuleData moduleData = sys.getModuleReportFacade().findModule(new Integer(module),sys.getUserSessionData());
            session.setAttribute("assesment",String.valueOf(moduleData.getAssesment()));
            return mapping.findForward("assesment");
        }
    }
    
}