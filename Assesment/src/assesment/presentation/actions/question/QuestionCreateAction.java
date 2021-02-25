 /*
 * Created on 08-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.question;

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
import assesment.business.question.QuestionABMFacade;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.language.Text;
import assesment.communication.module.ModuleData;
import assesment.communication.question.QuestionData;
import assesment.presentation.translator.web.util.AbstractAction;

public class QuestionCreateAction extends AbstractAction {

    public ActionForward cancel(HttpSession session, ActionMapping mapping, ActionForm form) {
        QuestionForm questionForm = (QuestionForm)form;
        if(questionForm.getTarget().equals("module")) {
            session.setAttribute("module",questionForm.getModule());
            return mapping.findForward("cancelModule");
        }else {
            try {
                AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
                Integer module = new Integer(questionForm.getModule());
                ModuleData moduleData = sys.getModuleReportFacade().findModule(module,sys.getUserSessionData());
                session.setAttribute("assesment",String.valueOf(moduleData.getAssesment()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mapping.findForward("cancelAssesment");
        }
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        
        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        Text messages = sys.getText();
        
        QuestionABMFacade questionABM = sys.getQuestionABMFacade();
        QuestionForm questionForm = (QuestionForm)form;
        String msg = questionForm.validate();
        if(msg != null) {
            session.setAttribute("Msg",messages.getText(msg));
            session.setAttribute("moduleId",String.valueOf(questionForm.getModule()));
            return mapping.findForward("back");
        }

        Integer module = new Integer(questionForm.getModule());
        ModuleData moduleData = sys.getModuleReportFacade().findModule(module,sys.getUserSessionData());

        String[] questionTexts = questionForm.getQuestionTexts();
        if(Integer.parseInt(questionForm.getType()) == QuestionData.VIDEO) {
        	if(questionForm.getEs_video().equals("new")) {
        		Integer id = questionABM.createVideo(questionForm.getEs_videoKey(),"es",sys.getUserSessionData());
        		FormFile fileForm = questionForm.getEs_videoFile();
	            if(id != null && fileForm != null && fileForm.getFileName() != null && !fileForm.getFileName().equals("")){                
	            	InputStream inputStream = fileForm.getInputStream();
	                FileOutputStream outputStream = new FileOutputStream(AssesmentData.FLASH_PATH+"/assessment/videos/video_"+id+".mp4");
	                int c = 0;
	                while ((c = inputStream.read()) != -1){
	                    outputStream.write(c);
	                }           
	                outputStream.flush();
	                outputStream.close();       
	            }
        		questionTexts[0] = String.valueOf(id);
        	}else {
        		questionTexts[0] = questionForm.getEs_video();
        	}
        	if(questionForm.getEn_video().equals("new")) {
        		Integer id = questionABM.createVideo(questionForm.getEn_videoKey(),"en",sys.getUserSessionData());
        		FormFile fileForm = questionForm.getEn_videoFile();
	            if(id != null && fileForm != null && fileForm.getFileName() != null && !fileForm.getFileName().equals("")){                
	            	InputStream inputStream = fileForm.getInputStream();
	                FileOutputStream outputStream = new FileOutputStream(AssesmentData.FLASH_PATH+"/assessment/videos/video_"+id+".mp4");
	                int c = 0;
	                while ((c = inputStream.read()) != -1){
	                    outputStream.write(c);
	                }           
	                outputStream.flush();
	                outputStream.close();       
	            }
        		questionTexts[1] = String.valueOf(id);
        	}else {
        		questionTexts[1] = questionForm.getEn_video();
        	}
        	if(questionForm.getPt_video().equals("new")) {
        		Integer id = questionABM.createVideo(questionForm.getPt_videoKey(),"pt",sys.getUserSessionData());
        		FormFile fileForm = questionForm.getPt_videoFile();
	            if(id != null && fileForm != null && fileForm.getFileName() != null && !fileForm.getFileName().equals("")){                
	            	InputStream inputStream = fileForm.getInputStream();
	                FileOutputStream outputStream = new FileOutputStream(AssesmentData.FLASH_PATH+"/assessment/videos/video_"+id+".mp4");
	                int c = 0;
	                while ((c = inputStream.read()) != -1){
	                    outputStream.write(c);
	                }           
	                outputStream.flush();
	                outputStream.close();       
	            }
        		questionTexts[2] = String.valueOf(id);
        	}else {
        		questionTexts[2] = questionForm.getPt_video();
        	}
        }
        String[][] answerTexts = questionForm.getAnswerTexts();
        QuestionData question = questionForm.getData(moduleData);
        
        
        questionABM.create(question,questionTexts,answerTexts,sys.getUserSessionData(),QuestionData.NORMAL);
        
        sys.reloadText();
        
        FormFile fileForm = questionForm.getImageName();
        if(fileForm != null && fileForm.getFileName() != null && !fileForm.getFileName().equals("")){

            InputStream inputStream = fileForm.getInputStream();
            String folder = (question.getType().intValue() == QuestionData.VIDEO) ? "flash" : "images";
            FileOutputStream outputStream = new FileOutputStream(AssesmentData.FLASH_PATH+"/assessment/"+folder+"/"+fileForm.getFileName());
        
            int c = 0;
            while ((c = inputStream.read()) != -1){
                outputStream.write(c);
            }           
            outputStream.flush();
            outputStream.close();       
        }

        session.removeAttribute("QuestionCreateForm");
        if(questionForm.getTarget().equals("module")) {
            session.setAttribute("module",String.valueOf(module));
            return mapping.findForward("module");
        }else {
            session.setAttribute("assesment",String.valueOf(moduleData.getAssesment()));
            return mapping.findForward("assesment");
        }
    }
}