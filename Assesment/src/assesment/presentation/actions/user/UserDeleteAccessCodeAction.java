/*
 * Created on 17-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.user;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import assesment.business.AssesmentAccess;
import assesment.communication.administration.AccessCodeData;
import assesment.presentation.translator.web.util.AbstractAction;

public class UserDeleteAccessCodeAction extends AbstractAction {

    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        return mapping.findForward("home");
    }

    public ActionForward action(ActionMapping mapping, ActionForm createForm, HttpServletRequest request, HttpServletResponse response) throws Throwable {

        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));

        Collection list = sys.getAssesmentReportFacade().getAccessCodes(sys.getUserSessionData(), sys.getText());
        Iterator codes = list.iterator();
        Collection delete = new LinkedList();
        while(codes.hasNext()){
            AccessCodeData data = (AccessCodeData)codes.next();
            if(request.getParameter(data.getCode()) != null && request.getParameter(data.getCode()).equals("on")) {
                delete.add(data.getCode());
            }
        }

        if(delete.size() > 0) {
            sys.getAssesmentABMFacade().deleteAccessCode(delete,sys.getUserSessionData());
        }
        return mapping.findForward("success");
    }
}

