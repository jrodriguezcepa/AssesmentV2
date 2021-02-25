/*
 * Created on 19-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.user;

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
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

/**
 * @author jrodriguez
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DeleteUserAssesmentAction extends AbstractAction {

    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        DynaActionForm userDeleteForm = (DynaActionForm) form;
        String assesment = (String) userDeleteForm.get("assesment");
        session.setAttribute("id", assesment);
        return mapping.findForward("users");
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {

        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        
        DynaActionForm userDeleteForm = (DynaActionForm) form;
        Collection users = Util.stringToCollection((String) userDeleteForm.get("user"));
        String type = (String) userDeleteForm.get("type");
        String assesment = (String) userDeleteForm.get("assesment");
        sys.getAssesmentABMFacade().deleteResults(new Integer(assesment),users,new Integer(type),sys.getUserSessionData());
        
        Collection usersAssessment = sys.getUserReportFacade().findAssesmentUsers("", new Integer(assesment), sys.getUserSessionData());
        session.setAttribute("id", assesment);
        if(usersAssessment != null && usersAssessment.size() > 0) {
        	return mapping.findForward("users");
        }else {
        	return mapping.findForward("view");
        }
    }

    private Collection stringToCollection(String stringList){
        String[] stringArray=stringList.split("<");
        Collection col=new LinkedList();
        
        for(int i=0; i < stringArray.length; i++){
            col.add(stringArray[i]);
        }
        
        return col;
    }

}