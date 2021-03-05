/*
 * Created on 08-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.assesment;

import java.util.Collection;
import java.util.Date;

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

/**
 * @author fcarriquiry
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class AssessmentShowReportAction extends AbstractAction {


    public ActionForward cancel(HttpSession session, ActionMapping mapping, ActionForm form) {
    	DynaActionForm assesmentForm = (DynaActionForm) form;
    	session.setAttribute("group", assesmentForm.getString("group"));
        return mapping.findForward("cancel");
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        
        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        
    	DynaActionForm assesmentForm = (DynaActionForm) form;
        
        sys.getAssesmentABMFacade().changeAssesmentReportView(new Integer(assesmentForm.getString("assesment")), new Integer(assesmentForm.getString("category")),  new Integer(assesmentForm.getString("show")), sys.getUserSessionData());
    	session.setAttribute("group", assesmentForm.getString("group"));
        return mapping.findForward("view");
    }
}