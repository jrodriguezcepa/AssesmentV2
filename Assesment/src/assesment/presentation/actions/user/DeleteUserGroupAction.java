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
public class DeleteUserGroupAction extends AbstractAction {

    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        UserCreateForm userDeleteForm = (UserCreateForm) form;
        session.setAttribute("group", userDeleteForm.getGroup());
        return mapping.findForward("users");
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {

        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        
        UserCreateForm userDeleteForm = (UserCreateForm) form;
        Collection users = Util.stringToCollection((String) userDeleteForm.getLoginname());
        sys.getUserABMFacade().deleteGroupUsers(new Integer(userDeleteForm.getGroup()),users,sys.getUserSessionData());
        
        session.setAttribute("group", userDeleteForm.getGroup());
        return mapping.findForward("users");
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