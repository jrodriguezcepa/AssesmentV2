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
import assesment.communication.assesment.AssesmentData;
import assesment.communication.language.Text;
import assesment.communication.module.ModuleData;
import assesment.communication.question.QuestionData;
import assesment.presentation.translator.web.util.AbstractAction;

/**
 * @author fcarriquiry
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class QuestionUpdateAction extends AbstractAction {


    public ActionForward cancel(HttpSession session, ActionMapping mapping, ActionForm form) {
        session.setAttribute("question",((QuestionForm)form).getQuestion());
        return mapping.findForward("cancel");
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        
        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        Text messages = sys.getText();
        
        QuestionForm questionForm = (QuestionForm)form;
        String msg = questionForm.validate();
        if(msg != null) {
            session.setAttribute("Msg",messages.getText(msg));
            return mapping.findForward("back");
        }

        Integer module = new Integer(questionForm.getModule());
        ModuleData moduleData = sys.getModuleReportFacade().findModule(module,sys.getUserSessionData());

        String[] questionTexts = questionForm.getQuestionTexts();
        String[][] answerTexts = questionForm.getAnswerTexts();
        QuestionData question = questionForm.getData(moduleData);
        
        sys.getQuestionABMFacade().update(question,questionTexts,answerTexts,sys.getUserSessionData(),QuestionData.NORMAL);
        
        FormFile fileForm = questionForm.getImageName();
        if(fileForm != null && fileForm.getFileName() != null && !fileForm.getFileName().equals("")){

            InputStream inputStream = fileForm.getInputStream();
            FileOutputStream outputStream = new FileOutputStream(AssesmentData.FLASH_PATH+"/assessment/images/"+fileForm.getFileName());
        
            int c = 0;
            while ((c = inputStream.read()) != -1){
                outputStream.write(c);
            }           
            outputStream.flush();
            outputStream.close();       
        }
        
        sys.reloadText();
        
        session.setAttribute("question",String.valueOf(question.getId()));        
        return mapping.findForward("view");
    }
}