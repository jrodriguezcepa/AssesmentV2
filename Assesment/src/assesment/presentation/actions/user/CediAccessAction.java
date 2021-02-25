/*
 * Created on 17-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import assesment.business.AssesmentAccess;
import assesment.business.administration.user.UsReportFacade;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.assesment.GroupData;
import assesment.communication.language.Text;
import assesment.communication.user.UserData;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

public class CediAccessAction extends AbstractAction {

    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        return mapping.findForward("logout");
    }

    public ActionForward action(ActionMapping mapping, ActionForm createForm, HttpServletRequest request, HttpServletResponse response) throws Throwable {

        HttpSession session = request.getSession();
        AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
        Text messages = sys.getText();
        
        try {

            DynaActionForm createData = (DynaActionForm) createForm;
            String code = createData.getString("code");
            String company = createData.getString("company");
            if(code == null || code.trim().length() == 0) {
            	session.setAttribute("Msg", "CEDI incorrecto");
                return mapping.findForward("error");
            }

            String cedi = "";
            for(int i = 0; i < code.length(); i++) {
            	char c = code.charAt(i);
            	if(Character.isLetterOrDigit(c))
            		cedi += String.valueOf(c);
            }
            
            Integer id = sys.getCorporationReportFacade().findCediCode(cedi, new Integer(company), sys.getUserSessionData());
            if(id == null) {
            	session.setAttribute("Msg", "CEDI incorrecto");
                return mapping.findForward("error");
            }
	            
            session.setAttribute("cedi",id);
            return mapping.findForward("next");
            
        }catch(Exception e) {
        	session.setAttribute("Msg", "CEDI incorrecto");
        	e.printStackTrace();
        }
        return mapping.findForward("error");
    }
}