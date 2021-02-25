/*
 * Created on 30-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.corporation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import assesment.business.AssesmentAccess;
import assesment.communication.corporation.CorporationAttributes;
import assesment.communication.language.Text;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

/**
 * @author fcarriquiry
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CorporationUpdateAction extends AbstractAction {

    public ActionForward cancel(HttpSession session, ActionMapping mapping, ActionForm form) {
        CorporationForm corporationForm = (CorporationForm)form;
        session.setAttribute("corporation", corporationForm.getId());
        return mapping.findForward("cancel");
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        
        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        Text messages = sys.getText();
        
        CorporationForm corporationForm = (CorporationForm)form;
        Integer id = new Integer(corporationForm.getId());
        
        if(Util.empty(corporationForm.getName())) {
            session.setAttribute("Msg",messages.getText("corporation.name.invalid"));
            session.setAttribute("corporation",((DynaActionForm)form).getString("corporation"));
            return mapping.findForward("back");
        }
        String logo = (corporationForm.getLogo() != null) ? corporationForm.getLogo().getFileName() : null;
        CorporationAttributes corporation = new CorporationAttributes(id,corporationForm.getName(),logo,null);
        sys.getCorporationABMFacade().update(corporation,sys.getUserSessionData());
        
        session.setAttribute("corporation", corporationForm.getId());
        return mapping.findForward("view");
    }
}
