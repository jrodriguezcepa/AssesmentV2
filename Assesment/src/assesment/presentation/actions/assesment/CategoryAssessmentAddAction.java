/*
 * Created on 08-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.assesment;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import assesment.business.AssesmentAccess;
import assesment.business.assesment.AssesmentABMFacade;
import assesment.business.language.LangABMFacade;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.assesment.CategoryData;
import assesment.communication.language.Text;
import assesment.persistence.util.Util;
import assesment.presentation.translator.web.util.AbstractAction;

/**
 * @author fcarriquiry
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CategoryAssessmentAddAction extends AbstractAction {


    public ActionForward cancel(HttpSession session, ActionMapping mapping, ActionForm form) {
        CategoryForm categoryForm = (CategoryForm)form;
        session.setAttribute("category",categoryForm.getId());
        return mapping.findForward("next");
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        
        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        Text messages = sys.getText();
        
        CategoryForm categoryForm = (CategoryForm)form;
        
        
        Collection assessments = Util.stringToCollection(categoryForm.getList());
        AssesmentABMFacade assessmentABM = sys.getAssesmentABMFacade(); 
        assessmentABM.asociateAssesmentCategory(new Integer(categoryForm.getId()), assessments, sys.getUserSessionData());

        session.removeAttribute("CategoryAssessmentAddForm");
        session.setAttribute("category",categoryForm.getId());
        return mapping.findForward("next");
    }
}