/*
 * Created on 31-oct-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.assesment;

import java.util.Collection;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import assesment.business.AssesmentAccess;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.language.Text;
import assesment.presentation.translator.web.util.AbstractAction;

/**
 * @author jrodriguez
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AssesmentDeleteResultAction extends AbstractAction {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward action(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        DynaActionForm deleteResultForm = (DynaActionForm) form;
        try {
            UserSessionData userSessionData = sys.getUserSessionData();

            String id = deleteResultForm.getString("assessment"); 
            Collection<String> list = new LinkedList<String>();
			list.add(userSessionData.getFilter().getLoginName());

			sys.getAssesmentABMFacade().deleteResults(new Integer(id), list, 1, userSessionData);
			sys.getUserABMFacade().failAssessment(new Integer(id), userSessionData.getFilter().getLoginName(), null, userSessionData);

        }catch(Exception e) {
        	e.printStackTrace();
        }
        return mapping.findForward("next");
	}
	
    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        return null;
    }	

}
