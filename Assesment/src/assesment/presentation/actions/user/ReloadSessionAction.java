/*
 * Created on 17-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.user;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import assesment.business.AssesmentAccess;
import assesment.communication.administration.AccessCodeData;
import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.language.Text;
import assesment.communication.security.SecurityConstants;
import assesment.communication.user.UserData;
import assesment.communication.util.MD5;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

public class ReloadSessionAction extends AbstractAction {

    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        return mapping.findForward("cancel");
    }

    public ActionForward action(ActionMapping mapping, ActionForm createForm, HttpServletRequest request, HttpServletResponse response) throws Throwable {

        HttpSession session = request.getSession();
        AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
        Text messages = sys.getText();

        DynaActionForm form = (DynaActionForm) createForm;
        
        String nick = form.getString("loginname");
        String mail = form.getString("email");
        try {
        	Collection list = sys.getUserReportFacade().findAbitabUser(nick, mail, sys.getUserSessionData());
        	switch(list.size()) {
        		case 0:
        			return mapping.findForward("error");
        		case 1:
        			UserData user = (UserData)list.iterator().next();
                    session.setAttribute("user",user.getLoginName());
                    session.setAttribute("password",new MD5().encriptar(user.getLoginName()));
                    return mapping.findForward("next");
                default:
                	session.setAttribute("user",nick);
    				session.setAttribute("mail",mail);
                    return mapping.findForward("select");
        	}
                
        }catch(Exception e) {
			return mapping.findForward("error");
        }
    }
}