/*
 * Created on 08-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.module;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import assesment.business.AssesmentAccess;
import assesment.presentation.translator.web.util.AbstractAction;

/**
 * @author fcarriquiry
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ModuleChangeOrderAction extends AbstractAction {


    public ActionForward cancel(HttpSession session, ActionMapping mapping, ActionForm form) {
        session.setAttribute("module",((DynaActionForm)form).getString("module"));
        return mapping.findForward("cancel");
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        
        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        
        DynaActionForm moduleForm = (DynaActionForm)form;
        Integer moduleDown = new Integer(moduleForm.getString("moduleDown"));
        Integer moduleUp = new Integer(moduleForm.getString("moduleUp"));
        
        sys.getModuleABMFacade().moveModule(moduleDown,1,sys.getUserSessionData());
        sys.getModuleABMFacade().moveModule(moduleUp,-1,sys.getUserSessionData());
                
        session.setAttribute("assesment",moduleForm.getString("assesment"));
        return mapping.findForward("success");
    }
    
}