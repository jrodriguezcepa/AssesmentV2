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
public class GroupCreateAction extends AbstractAction {


    public ActionForward cancel(HttpSession session, ActionMapping mapping, ActionForm form) {
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
        
        String[] texts = {groupForm.getEs_initialText(), groupForm.getEn_initialText(), groupForm.getPt_initialText()};

        GroupData group = groupForm.createData();
        if(!Util.empty(texts[0]) || !Util.empty(texts[1]) || !Util.empty(texts[2])) {
            for(int i = 0; i < 3; i++) {
            	if(Util.empty(texts[i])) {
                    session.setAttribute("Msg",messages.getText("group.initialtext.emptyorall"));
                    return mapping.findForward("back");
            	}
            }
        	group.setInitialText(true);
        }
        
        AssesmentABMFacade assessmentABM = sys.getAssesmentABMFacade(); 
        Integer groupId = assessmentABM.createGroup(group, sys.getUserSessionData());
        group.setId(groupId);
        
       	FormFile fileForm = groupForm.getLogo();
        if(fileForm != null) {
            String fileName = fileForm.getFileName();
	        if(fileName != null && !fileName.trim().equals("")){
	        	String file = null;
	        	if(fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg")) {
	        		file = "assessment_group"+groupId+".jpg";
	        	}
	        	if(fileName.toLowerCase().endsWith(".png")) {
	        		file = "assessment_group"+groupId+".png";
	        	}
	        	
	        	if(file != null) {
	        		InputStream inputStream = fileForm.getInputStream();
		            FileOutputStream outputStream = new FileOutputStream(AssesmentData.FLASH_PATH+"/images/"+file);
		            group.setImage(file);
		        
		            int c = 0;
		            while ((c = inputStream.read()) != -1){
		                outputStream.write(c);
		            }           
		            outputStream.flush();
		            outputStream.close();
	        	}

	        	assessmentABM.updateGroup(group, sys.getUserSessionData());
	        }
        }        

        if(!Util.empty(texts[0])) {
        	String key = "assessment.group.id"+groupId;
            LangABMFacade languageFacade = sys.getLanguageABMFacade();
            languageFacade.saveText(key,"es",texts[0], sys.getUserSessionData());
            languageFacade.saveText(key,"en",texts[1], sys.getUserSessionData());
            languageFacade.saveText(key,"pt",texts[2], sys.getUserSessionData());

            sys.reloadText();
        }
        session.removeAttribute("GroupCreateForm");
        session.setAttribute("id",groupId.toString());
        return mapping.findForward("list");
    }
}