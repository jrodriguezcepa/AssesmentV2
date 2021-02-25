/*
 * Created on 31-oct-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.assesment;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import assesment.business.AssesmentAccess;
import assesment.business.assesment.AssesmentABMFacade;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.exception.InvalidDataException;
import assesment.communication.language.Text;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

/**
 * @author jrodriguez
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AssesmentDeleteAction extends AbstractAction {

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
        Text messages = sys.getText();
        DynaActionForm assesmentDeleteForm = (DynaActionForm) form;
        try {
            UserSessionData userSessionData = sys.getUserSessionData();
    		String ids = assesmentDeleteForm.getString("assesment");
            Collection list = Util.stringToCollection(ids);
    			
    		AssesmentABMFacade assesmentABM = sys.getAssesmentABMFacade();
            assesmentABM.delete(list,userSessionData);
    			
            if(assesmentDeleteForm.getString("target").equals("corporation")) {
                session.setAttribute("corporation",assesmentDeleteForm.getString("corporation"));
                return mapping.findForward("corporation");
            }else {
                return mapping.findForward("list");
            }
        }catch(Exception e) {
            Throwable thw = e;
            while (thw.getCause() != null) {
                thw = thw.getCause();
            }
            if (thw instanceof InvalidDataException) {
                session.setAttribute("Msg",messages.getText(((InvalidDataException)thw).getSystemMessage()));
                if(assesmentDeleteForm.getString("target").equals("corporation")) {
                    session.setAttribute("corporation",assesmentDeleteForm.getString("corporation"));
                    return mapping.findForward("corporation");
                }else if(assesmentDeleteForm.getString("target").equals("list")) {
                    return mapping.findForward("list");
                }else {
                    session.setAttribute("assesment",assesmentDeleteForm.getString("assesment"));
                    return mapping.findForward("view");
                }
            }
        }
        return mapping.findForward("list");
	}
	
    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        return null;
    }	

}
