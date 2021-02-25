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

import assesment.business.AssesmentAccess;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

/**
 * @author fcarriquiry
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class AssesmentChangeDateAction extends AbstractAction {


    public ActionForward cancel(HttpSession session, ActionMapping mapping, ActionForm form) {
    	AssesmentForm assesmentForm = (AssesmentForm) form;
    	session.setAttribute("group", assesmentForm.getTarget());
        return mapping.findForward("cancel");
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        
        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        
        AssesmentForm assesmentForm = (AssesmentForm) form;
        Date start = assesmentForm.getStartDate();
        Date end = assesmentForm.getEndDate();
        
        sys.getAssesmentABMFacade().changeAssesmentGroupDate(new Integer(assesmentForm.getId()), new Integer(assesmentForm.getCorporation()), start, end, sys.getUserSessionData());
        session.setAttribute("group", assesmentForm.getTarget());
        return mapping.findForward("view");
    }
}