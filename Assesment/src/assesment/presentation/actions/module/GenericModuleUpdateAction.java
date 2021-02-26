/*
 * Created on 08-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.module;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import assesment.business.AssesmentAccess;
import assesment.communication.language.Text;
import assesment.communication.module.ModuleAttribute;
import assesment.communication.module.ModuleData;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

/**
 * @author fcarriquiry
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class GenericModuleUpdateAction extends AbstractAction {


    public ActionForward cancel(HttpSession session, ActionMapping mapping, ActionForm form) {
        session.setAttribute("module",((DynaActionForm)form).getString("module"));
        return mapping.findForward("cancel");
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        
        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        Text messages = sys.getText();
        
        DynaActionForm moduleForm = (DynaActionForm)form;
        if(validate(moduleForm)) {
            session.setAttribute("Msg",messages.getText("module.name.empty"));
            return mapping.findForward("back");
        }
        String[] texts = {moduleForm.getString("es_name"),moduleForm.getString("en_name"),moduleForm.getString("pt_name")};
        Integer module = new Integer(moduleForm.getString("module"));
        ModuleAttribute moduleAttribute = sys.getModuleReportFacade().findGenericModuleAttributes(module,sys.getUserSessionData());
        sys.getLanguageABMFacade().updateTexts(texts,moduleAttribute.getKey(),sys.getUserSessionData());
        
        Integer type = new Integer(moduleForm.getString("type"));
        if(moduleAttribute.getType().intValue() != type.intValue()) {
            moduleAttribute.setType(type);
            sys.getModuleABMFacade().update(moduleAttribute,sys.getUserSessionData(),ModuleData.GENERIC);
        }

        sys.reloadText();
        session.setAttribute("module",((DynaActionForm)form).getString("module"));
        return mapping.findForward("module");
    }
    
    private boolean validate(DynaActionForm form) {
        return (Util.empty(form.getString("es_name")) || Util.empty(form.getString("en_name")) || Util.empty(form.getString("pt_name")));
    }
}