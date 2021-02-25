/*
 * Created on 08-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.assesment;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;

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
import assesment.communication.assesment.GroupData;
import assesment.communication.language.Text;
import assesment.communication.module.ModuleData;
import assesment.persistence.language.LangABM;
import assesment.persistence.language.LangABMHome;
import assesment.persistence.language.LangABMUtil;
import assesment.persistence.util.Util;
import assesment.presentation.translator.web.util.AbstractAction;

/**
 * @author fcarriquiry
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class GroupUpdateAction extends AbstractAction {


    public ActionForward cancel(HttpSession session, ActionMapping mapping, ActionForm form) {
        GroupForm groupForm = (GroupForm)form;
        session.setAttribute("id",groupForm.getId());
        return mapping.findForward("cancel");
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        
        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        Text messages = sys.getText();
        
        GroupForm groupForm = (GroupForm)form;
        
        String msg = groupForm.validate();
        if(msg != null) {
            session.setAttribute("Msg",messages.getText(msg));
            return mapping.findForward("back");
        }
        
        GroupData groupData = sys.getAssesmentReportFacade().findGroup(new Integer(groupForm.getId()), sys.getUserSessionData());
        LangABMFacade langFacade = sys.getLanguageABMFacade();
        if(!groupData.getEsInitialText().equals(groupForm.getEs_initialText()) || !groupData.getEnInitialText().equals(groupForm.getEn_initialText()) || !groupData.getPtInitialText().equals(groupForm.getPt_initialText())) {
            String[] texts = {groupForm.getEs_initialText(), groupForm.getEn_initialText(), groupForm.getPt_initialText()};
            String key = "assessment.group.id"+groupData.getId();
            langFacade.updateTexts(texts, key, sys.getUserSessionData());
            sys.reloadText();
        }

        AssesmentABMFacade assessmentABM = sys.getAssesmentABMFacade(); 

        groupData.setName(groupForm.getName());
        groupData.setCorporation(new Integer(groupForm.getCorporation()));
        groupData.setLayout(new Integer(groupForm.getLayout()));
        groupData.setRepeatable(groupForm.getRepeatable().equals("1"));
        
       	FormFile fileForm = groupForm.getLogo();
        if(fileForm != null) {
            String fileName = fileForm.getFileName();
	        if(fileName != null && !fileName.trim().equals("")){
	        	String file = null;
	        	if(fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg")) {
	        		file = "assessment_group"+groupData.getId()+".jpg";
	        	}
	        	if(fileName.toLowerCase().endsWith(".png")) {
	        		file = "assessment_group"+groupData.getId()+".png";
	        	}
	        	
	        	if(file != null) {
	        		InputStream inputStream = fileForm.getInputStream();
		            FileOutputStream outputStream = new FileOutputStream(AssesmentData.FLASH_PATH+"/images/"+file);
		            groupData.setImage(file);
		        
		            int c = 0;
		            while ((c = inputStream.read()) != -1){
		                outputStream.write(c);
		            }           
		            outputStream.flush();
		            outputStream.close();
	        	}

	        }
        }        
    	assessmentABM.updateGroup(groupData, sys.getUserSessionData());


        session.removeAttribute("GroupUpdateForm");
        session.setAttribute("group",groupData.getId().toString());
        return mapping.findForward("cancel");
    }
}