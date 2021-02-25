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
import assesment.communication.assesment.GroupData;
import assesment.communication.language.Text;
import assesment.persistence.util.Util;
import assesment.presentation.translator.web.util.AbstractAction;

/**
 * @author fcarriquiry
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CategoryUpdateAction extends AbstractAction {


    public ActionForward cancel(HttpSession session, ActionMapping mapping, ActionForm form) {
        CategoryForm categoryForm = (CategoryForm)form;
        session.setAttribute("group",categoryForm.getGroup());
        return mapping.findForward("next");
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        
        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        Text messages = sys.getText();
        
        CategoryForm categoryForm = (CategoryForm)form;
        
        String msg = categoryForm.validate();
        if(msg != null) {
            session.setAttribute("Msg",messages.getText(msg));
            return mapping.findForward("next");
        }
        
        CategoryData categoryData = sys.getAssesmentReportFacade().findCategory(new Integer(categoryForm.getId()), sys.getUserSessionData());
        LangABMFacade langFacade = sys.getLanguageABMFacade();
        if(!categoryData.getEsInitialText().equals(categoryForm.getEs_Text()) || !categoryData.getEnInitialText().equals(categoryForm.getEn_Text()) || !categoryData.getPtInitialText().equals(categoryForm.getPt_Text())) {
            String[] texts = {categoryForm.getEs_Text(), categoryForm.getEn_Text(), categoryForm.getPt_Text()};
            langFacade.updateTexts(texts, categoryData.getKey(), sys.getUserSessionData());
            sys.reloadText();
        }

        categoryData.setType(new Integer(categoryForm.getType()));
        categoryData.setOrder(new Integer(categoryForm.getOrd()));
        categoryData.setTitleColor(new Integer(categoryForm.getTitleColor()));
        categoryData.setItemColor(new Integer(categoryForm.getItemColor()));
        
        
       	FormFile fileForm = categoryForm.getLogo();
        if(fileForm != null) {
            String fileName = fileForm.getFileName();
	        if(fileName != null && !fileName.trim().equals("")){
	        	String file = null;
	        	if(fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg")) {
	        		file = "group"+categoryData.getGroupId()+"_category"+categoryData.getId()+".jpg";
	        	}
	        	if(fileName.toLowerCase().endsWith(".png")) {
	        		file = "group"+categoryData.getGroupId()+"_category"+categoryData.getId()+".png";
	        	}
	        	
	        	if(file != null) {
	        		InputStream inputStream = fileForm.getInputStream();
		            FileOutputStream outputStream = new FileOutputStream(AssesmentData.FLASH_PATH+"/images/"+file);
		            categoryData.setImage(file);
		        
		            int c = 0;
		            while ((c = inputStream.read()) != -1){
		                outputStream.write(c);
		            }           
		            outputStream.flush();
		            outputStream.close();
	        	}

	        }
        }        

    	sys.getAssesmentABMFacade().updateCategory(categoryData, sys.getUserSessionData());

        session.removeAttribute("CategoryUpdateForm");
        session.setAttribute("category",categoryForm.getId());
        return mapping.findForward("next");
    }
}