/*
 * Created on 17-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.user;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import assesment.business.AssesmentAccess;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.user.UserData;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

public class LuminAction extends AbstractAction {

    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        return mapping.findForward("logout");
    }

    public ActionForward action(ActionMapping mapping, ActionForm createForm, HttpServletRequest request, HttpServletResponse response) throws Throwable {

        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));

        DynaActionForm createData = (DynaActionForm) createForm;
        String code = createData.getString("code");
        if(code == null || code.trim().length() == 0) {
        	session.setAttribute("Msg", "Formato o largo incorrecto");
            return mapping.findForward("error");
        }

        String user = "";
        for(int i = 0; i < code.length(); i++) {
        	String s = String.valueOf(code.charAt(i));
        	if(Util.isNumber(s)) 
        		user += s;
        }
        
        UserSessionData userSessionData = sys.getUserSessionData();
        if(user == null || !(user.length() >= 7 && user.length() <= 8)) {
        	session.setAttribute("Msg", "Formato o largo incorrecto");
        	return mapping.findForward("error");
        }else {
       	 	String login = "charla_"+AssesmentData.LUMIN_CHARLA+"_"+user;
       	 	UserData userData = sys.getUserReportFacade().findUserByPrimaryKey(login, userSessionData);
       	 	if(userData == null) {
            	session.setAttribute("Msg", "Usuario no registrado");
                return mapping.findForward("error");
       	 	}else {
       	 		Collection assesments = sys.getUserReportFacade().findUserAssesments(login, userSessionData);
       	 		if(assesments.size() > 0) {
       	 			Iterator it = assesments.iterator();
       	 			while(it.hasNext()) {
       	 				if(((AssesmentAttributes)it.next()).getId().intValue() == AssesmentData.LUMIN_CHARLA) {
       			            session.setAttribute("user",login);
       			            session.setAttribute("password",login);
       	 					return mapping.findForward("next");
       	 				}
       	 			}
       	 		}
       	 	}
        }
   	 	return mapping.findForward("next");
    }
}

