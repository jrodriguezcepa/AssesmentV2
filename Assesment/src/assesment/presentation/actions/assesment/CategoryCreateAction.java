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
public class CategoryCreateAction extends AbstractAction {


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
            return mapping.findForward("back");
        }
        
        String[] texts = {categoryForm.getEs_Text(), categoryForm.getEn_Text(), categoryForm.getPt_Text()};

        CategoryData category = categoryForm.createData();
        if(!Util.empty(texts[0]) || !Util.empty(texts[1]) || !Util.empty(texts[2])) {
            for(int i = 0; i < 3; i++) {
            	if(Util.empty(texts[i])) {
                    session.setAttribute("Msg",messages.getText("group.initialtext.emptyorall"));
                    return mapping.findForward("back");
            	}
            }
        }
        
        Collection assessments = Util.stringToCollection(categoryForm.getList());
        AssesmentABMFacade assessmentABM = sys.getAssesmentABMFacade(); 
        Integer categoryId = assessmentABM.createCategory(category, assessments, sys.getUserSessionData());
        category.setId(categoryId);
        
       	FormFile fileForm = categoryForm.getLogo();
        if(fileForm != null) {
            String fileName = fileForm.getFileName();
	        if(fileName != null && !fileName.trim().equals("")){
	        	String file = null;
	        	if(fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg")) {
	        		file = "group"+category.getGroupId()+"_category"+categoryId+".jpg";
	        	}
	        	if(fileName.toLowerCase().endsWith(".png")) {
	        		file = "group"+category.getGroupId()+"_category"+categoryId+".png";
	        	}
	        	
	        	if(file != null) {
	        		InputStream inputStream = fileForm.getInputStream();
		            FileOutputStream outputStream = new FileOutputStream(AssesmentData.FLASH_PATH+"/images/"+file);
		            category.setImage(file);
		        
		            int c = 0;
		            while ((c = inputStream.read()) != -1){
		                outputStream.write(c);
		            }           
		            outputStream.flush();
		            outputStream.close();
	        	}

	        }
        }        

    	String key = "group"+category.getGroupId()+".category"+categoryId+".text";
    	category.setKey(key);

    	LangABMFacade languageFacade = sys.getLanguageABMFacade();
        languageFacade.saveText(key,"es",texts[0], sys.getUserSessionData());
        languageFacade.saveText(key,"en",texts[1], sys.getUserSessionData());
        languageFacade.saveText(key,"pt",texts[2], sys.getUserSessionData());

    	assessmentABM.updateCategory(category, sys.getUserSessionData());

    	sys.reloadText();

        session.removeAttribute("GroupCreateForm");
        session.setAttribute("group",categoryForm.getGroup());
        return mapping.findForward("next");
    }
}