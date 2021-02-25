package assesment.presentation.actions.module;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import assesment.business.AssesmentAccess;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

public class ModuleCopyAction extends AbstractAction {


    public ActionForward cancel(HttpSession session, ActionMapping mapping, ActionForm form) {
        session.setAttribute("assesment",((DynaActionForm)form).getString("assesment"));
        return mapping.findForward("assesment");
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        
        DynaActionForm moduleForm = (DynaActionForm)form;
        String ids = moduleForm.getString("modules");
        String assesment = moduleForm.getString("assesment");
        Collection list = Util.stringToCollection(ids);
        sys.getModuleABMFacade().copyModule(new Integer(assesment),list,sys.getUserSessionData());
        
        sys.reloadText();
        session.setAttribute("assesment",assesment);
        return mapping.findForward("assesment");
    }
    
}