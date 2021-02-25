package assesment.presentation.actions.module;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import assesment.business.AssesmentAccess;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.exception.InvalidDataException;
import assesment.communication.language.Text;
import assesment.presentation.translator.web.util.AbstractAction;

public class ModuleDeleteAction extends AbstractAction {


    public ActionForward cancel(HttpSession session, ActionMapping mapping, ActionForm form) {
        return null;
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        Text messages = sys.getText();
        DynaActionForm moduleForm = (DynaActionForm)form;
        try {
            Integer module = new Integer(moduleForm.getString("module"));
            sys.getModuleABMFacade().delete(module,sys.getUserSessionData());
            
            AssesmentData assesment = sys.getAssesmentReportFacade().findAssesment(new Integer(moduleForm.getString("assesment")),sys.getUserSessionData());
            sys.getModuleABMFacade().updateOrder(assesment,sys.getUserSessionData());
            
            session.setAttribute("assesment",moduleForm.getString("assesment"));
            return mapping.findForward("assesment");
        }catch(Exception e) {
            Throwable thw = e;
            while (thw.getCause() != null) {
                thw = thw.getCause();
            }
            if (thw instanceof InvalidDataException) {
                session.setAttribute("Msg",messages.getText(((InvalidDataException)thw).getSystemMessage()));
                if(moduleForm.getString("target").equals("assesment")) {
                    session.setAttribute("assesment",moduleForm.getString("assesment"));
                    return mapping.findForward("assesment");
                }else {
                    session.setAttribute("module",moduleForm.getString("module"));
                    return mapping.findForward("view");
                }
            }
        }
        return null;
    }
}