/*
 * Created on 17-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.assesment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import assesment.business.AssesmentAccess;
import assesment.communication.language.Text;
import assesment.presentation.translator.web.util.AbstractAction;

public class CreateAssessmentLinkAction extends AbstractAction {

    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        return mapping.findForward("home");
    }

    public ActionForward action(ActionMapping mapping, ActionForm createForm, HttpServletRequest request, HttpServletResponse response) throws Throwable {

        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        Text messages = sys.getText();

        DynaActionForm linkData = (DynaActionForm) createForm;
        String assessment = linkData.getString("assessment");
        
        String language = linkData.getString("language");
        String first = linkData.getString("firstName"+language.toUpperCase());
        String last = linkData.getString("lastName"+language.toUpperCase());

        sys.getAssesmentABMFacade().createLink(new Integer(assessment), language, first, last, sys.getUserSessionData());
        session.setAttribute("assesment", assessment);
        return mapping.findForward("next");
    }
}

