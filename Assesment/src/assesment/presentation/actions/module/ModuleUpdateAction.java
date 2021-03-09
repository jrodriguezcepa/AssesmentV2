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
import assesment.communication.module.ModuleXML;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

/**
 * @author fcarriquiry
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ModuleUpdateAction extends AbstractAction {


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
        String greenStr = moduleForm.getString("green");
        if(Util.empty(greenStr)) {
            session.setAttribute("Msg",messages.getText("assesment.error.emptygreen"));
            return mapping.findForward("back");
        }else if(!Util.isNumber(greenStr)){
            session.setAttribute("Msg",messages.getText("assesment.error.wronggreen"));
            return mapping.findForward("back");
        }
        String yellowStr = moduleForm.getString("yellow");
        if(Util.empty(yellowStr)) {
            session.setAttribute("Msg",messages.getText("assesment.error.emptyred"));
            return mapping.findForward("back");
        }else if(!Util.isNumber(yellowStr)){
            session.setAttribute("Msg",messages.getText("assesment.error.wrongred"));
            return mapping.findForward("back");
        }
        ModuleAttribute moduleAttribute = sys.getModuleReportFacade().findModuleAttributes(module,sys.getUserSessionData());
        sys.getLanguageABMFacade().updateTexts(texts,moduleAttribute.getKey(),sys.getUserSessionData());

        Integer type = new Integer(moduleForm.getString("type"));
        moduleAttribute.setType(type);
        moduleAttribute.setGreen(new Integer(greenStr));
        moduleAttribute.setYellow(new Integer(yellowStr));
        sys.getModuleABMFacade().update(moduleAttribute,sys.getUserSessionData(),ModuleData.NORMAL);
        
        sys.reloadText();
        session.setAttribute("module",moduleForm.getString("module"));
        return mapping.findForward("module");
    }
    
    private boolean validate(DynaActionForm form) {
        return (Util.empty(form.getString("es_name")) || Util.empty(form.getString("en_name")) || Util.empty(form.getString("pt_name")));
    }
}