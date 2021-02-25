/*
 * Created on 08-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.corporation;

import java.io.FileOutputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import assesment.business.AssesmentAccess;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.corporation.CediAttributes;
import assesment.communication.corporation.CediData;
import assesment.communication.corporation.CorporationAttributes;
import assesment.communication.language.Text;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

/**
 * @author fcarriquiry
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CediCreateAction extends AbstractAction {


    public ActionForward cancel(HttpSession session, ActionMapping mapping, ActionForm form) {
        CediForm cediForm = (CediForm)form;
        Integer company = new Integer(cediForm.getCompany());
		if(company.equals(CediData.GRUPO_MODELO)) {
			return mapping.findForward("listCedi");
		}else {
			return mapping.findForward("listMutual");
		}
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        
        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        Text messages = sys.getText();
        
        CediForm cediForm = (CediForm)form;
        Integer company = new Integer(cediForm.getCompany());
        
        if(Util.empty(cediForm.getName())) {
            session.setAttribute("Msg",messages.getText("corporation.name.invalid"));
            session.setAttribute("company", String.valueOf(company));
            return mapping.findForward("back");
        }
        
        CediAttributes cedi = new CediAttributes(null,cediForm.getName(),cediForm.getAccessCode(),cediForm.getUen(), cediForm.getDrv(), cediForm.getManager(), cediForm.getManagerMail(), cediForm.getLoginName(), company);
        Integer cediId = sys.getCorporationABMFacade().createCedi(cedi,sys.getUserSessionData());

		if(company.equals(CediData.GRUPO_MODELO)) {
			return mapping.findForward("listCedi");
		}else {
			return mapping.findForward("listMutual");
		}
    }
}