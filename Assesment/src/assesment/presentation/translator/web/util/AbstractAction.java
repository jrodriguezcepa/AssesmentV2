/*
 * Created on 27-oct-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.translator.web.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author jrodriguez
 */
public abstract class AbstractAction extends Action {

	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            request.setAttribute("className",getClass().getName());
            if(isCancelled(request)) {
                return cancel(request.getSession(),mapping,form);
            }
            ActionForward forward = action(mapping,form,request,response);        
            request.removeAttribute("className");
            return forward;
        }catch(Throwable e) {
        	e.printStackTrace();
            throw new Exception(e);
        }
	}

    public abstract ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form);

    public abstract ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable;
    
}
