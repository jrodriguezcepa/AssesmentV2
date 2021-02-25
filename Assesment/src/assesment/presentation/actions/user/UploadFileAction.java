/*
 * Created on 08-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.user;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

import assesment.business.AssesmentAccess;
import assesment.business.assesment.AssesmentABMFacade;
import assesment.business.language.LangABMFacade;
import assesment.communication.administration.UserAnswerData;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.assesment.GroupData;
import assesment.communication.language.Text;
import assesment.communication.module.ModuleData;
import assesment.communication.question.QuestionData;
import assesment.persistence.util.Util;
import assesment.presentation.translator.web.util.AbstractAction;

/**
 * @author fcarriquiry
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class UploadFileAction extends AbstractAction {


    public ActionForward cancel(HttpSession session, ActionMapping mapping, ActionForm form) {
        return mapping.findForward("cancel");
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        
        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        UserSessionData userSessionData = sys.getUserSessionData();
        Text messages = sys.getText();
        
        UploadFileForm actionForm = (UploadFileForm)form;
        Integer assessmentId = new Integer(actionForm.getAssessment());
        String user = Util.formatFile(sys.getLoginUser().trim());
		
	    AssesmentData assesment = sys.getAssesmentReportFacade().findAssesment(assessmentId, userSessionData);
		Iterator it = assesment.getModuleIterator();
		Collection<UserAnswerData> answers = new LinkedList<UserAnswerData>();
		if(it.hasNext()) {
			ModuleData moduleData = (ModuleData)it.next();
			Iterator qIt = moduleData.getQuestionIterator();
			int index = 1;
			while(qIt.hasNext()) {
				QuestionData question = (QuestionData)qIt.next();
				FormFile file = actionForm.getFile(index);
		        if(file != null) {
		            String fileName = file.getFileName();
			        if(fileName != null && !fileName.trim().equals("")){
			        	String newFile = null;
			        	if(fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg")) {
			        		newFile = user+"_"+question.getId()+".jpg";
			        	}
			        	if(fileName.toLowerCase().endsWith(".png")) {
			        		newFile = user+"_"+question.getId()+".png";
			        	}
			        	
			        	if(fileName.toLowerCase().endsWith(".pdf")) {
			        		newFile = user+"_"+question.getId()+".pdf";
			        	}
			        	
			        	if(newFile != null) {
			        		InputStream inputStream = file.getInputStream();
				            FileOutputStream outputStream = new FileOutputStream(AssesmentData.FLASH_PATH+"/upload_images/"+newFile);
				        
				            int c = 0;
				            while ((c = inputStream.read()) != -1){
				                outputStream.write(c);
				            }           
				            outputStream.flush();
				            outputStream.close();

				            answers.add(new UserAnswerData(question.getId(), newFile));

			        	} else {
		                    session.setAttribute("Msg",messages.getText("messages.uploadimage.errorfile"));
		                    session.setAttribute("assessment", String.valueOf(assessmentId));
		                    return mapping.findForward("back");
			        		
			        	}
			        }
		        }        
				index++;
			}
		}
        sys.getUserABMFacade().saveModuleAnswers(user,assesment.getId(),answers,-1,-1,userSessionData,false,null,false);

        session.setAttribute("Msg",messages.getText("messages.uploadimage.success"));
        session.setAttribute("assessment", String.valueOf(assessmentId));
        return mapping.findForward("back");
    }
}