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
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.language.Text;
import assesment.communication.module.ModuleData;
import assesment.presentation.translator.web.util.AbstractAction;

/**
 * @author fcarriquiry
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class AssesmentCreateAction extends AbstractAction {


    public ActionForward cancel(HttpSession session, ActionMapping mapping, ActionForm form) {
        return mapping.findForward("cancel");
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        
        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        UserSessionData userSessionData = sys.getUserSessionData();
        Text messages = sys.getText();
        
        AssesmentForm assesmentForm = (AssesmentForm)form;
        
        String msg = assesmentForm.validate();
        if(msg != null) {
            session.setAttribute("Msg",messages.getText(msg));
            return mapping.findForward("back");
        }
        
        AssesmentAttributes assesment = assesmentForm.createAssesment();
        AssesmentABMFacade assessmentABM = sys.getAssesmentABMFacade(); 
        Integer assessmentId = assessmentABM.create(assesment,userSessionData);
        assesment.setId(assessmentId);
        
        boolean update = false;
    	FormFile fileForm = assesmentForm.getIcon();
        if(fileForm != null) {
            String fileNameES = fileForm.getFileName();
        	if(fileNameES != null && !fileNameES.equals("")){
	            InputStream inputStream = fileForm.getInputStream();
	            FileOutputStream outputStream = new FileOutputStream(AssesmentData.FLASH_PATH+"/images/icon_"+assessmentId+".png");
	            int c = 0;
	            while ((c = inputStream.read()) != -1){
	                outputStream.write(c);
	            }           
	            outputStream.flush();
	            outputStream.close();
	            
	            assesment.setIcon(true);
	            update = true;
        	}
        }

    	fileForm = assesmentForm.getAttachesPDF();
        if(fileForm != null) {
            String fileNameES = fileForm.getFileName();
        	if(fileNameES != null && !fileNameES.equals("")){
	            InputStream inputStream = fileForm.getInputStream();
	            FileOutputStream outputStream = new FileOutputStream(AssesmentData.FLASH_PATH+"/pdf/assessment_es_"+assessmentId+".pdf");
	            int c = 0;
	            while ((c = inputStream.read()) != -1){
	                outputStream.write(c);
	            }           
	            outputStream.flush();
	            outputStream.close();
	            
	            assesment.setAttachesPDF(true);
	            update = true;
        	}
        }

    	fileForm = assesmentForm.getAttachenPDF();
        if(fileForm != null) {
            String fileNameES = fileForm.getFileName();
        	if(fileNameES != null && !fileNameES.equals("")){
	            InputStream inputStream = fileForm.getInputStream();
	            FileOutputStream outputStream = new FileOutputStream(AssesmentData.FLASH_PATH+"/pdf/assessment_en_"+assessmentId+".pdf");
	            int c = 0;
	            while ((c = inputStream.read()) != -1){
	                outputStream.write(c);
	            }           
	            outputStream.flush();
	            outputStream.close();
	            
	            assesment.setAttachenPDF(true);
	            update = true;
        	}
        }

    	fileForm = assesmentForm.getAttachptPDF();
        if(fileForm != null) {
            String fileNameES = fileForm.getFileName();
        	if(fileNameES != null && !fileNameES.equals("")){
	            InputStream inputStream = fileForm.getInputStream();
	            FileOutputStream outputStream = new FileOutputStream(AssesmentData.FLASH_PATH+"/pdf/assessment_pt_"+assessmentId+".pdf");
	            int c = 0;
	            while ((c = inputStream.read()) != -1){
	                outputStream.write(c);
	            }           
	            outputStream.flush();
	            outputStream.close();
	            
	            assesment.setAttachptPDF(true);
	            update = true;
        	}
        }

        if(Integer.parseInt(assesmentForm.getCertificate()) == 2) {
        	fileForm = assesmentForm.getCertificateES();
	        if(fileForm != null) {
	            String fileNameES = fileForm.getFileName();
	        	if(fileNameES != null && !fileNameES.equals("")){
		            InputStream inputStream = fileForm.getInputStream();
		            FileOutputStream outputStream = new FileOutputStream(AssesmentData.FLASH_PATH+"/images/"+fileForm.getFileName());
		            assesment.setCertificateImageES(fileNameES);
		        
		            int c = 0;
		            while ((c = inputStream.read()) != -1){
		                outputStream.write(c);
		            }           
		            outputStream.flush();
		            outputStream.close();
		            update = true;
	        	}
	        }else {
	            assesment.setCertificateImagePT(null);
	        }
	        
	        fileForm = assesmentForm.getCertificateEN();
	        if(fileForm != null) {
	            String fileNameEN = fileForm.getFileName();
	        	if(fileNameEN != null && !fileNameEN.equals("")){
		            InputStream inputStream = fileForm.getInputStream();
		            FileOutputStream outputStream = new FileOutputStream(AssesmentData.FLASH_PATH+"/images/"+fileForm.getFileName());
		            assesment.setCertificateImageEN(fileNameEN);
		        
		            int c = 0;
		            while ((c = inputStream.read()) != -1){
		                outputStream.write(c);
		            }           
		            outputStream.flush();
		            outputStream.close();
		            update = true;
	        	}
	        }else {
	            assesment.setCertificateImageEN(null);
	        }
	        
	        fileForm = assesmentForm.getCertificatePT();
	        if(fileForm != null) {
	            String fileNamePT = fileForm.getFileName();
	        	if(fileNamePT != null && !fileNamePT.equals("")){
		            InputStream inputStream = fileForm.getInputStream();
		            FileOutputStream outputStream = new FileOutputStream(AssesmentData.FLASH_PATH+"/images/"+fileForm.getFileName());
		            assesment.setCertificateImagePT(fileNamePT);
		        
		            int c = 0;
		            while ((c = inputStream.read()) != -1){
		                outputStream.write(c);
		            }           
		            outputStream.flush();
		            outputStream.close();
		            update = true;
	        	}
	        }else {
	            assesment.setCertificateImagePT(null);
	        }

        }        

        String key = "assessment"+assessmentId+".name";
        String[] texts = {assesmentForm.getName_es(), assesmentForm.getName_en(), assesmentForm.getName_pt()};

        LangABMFacade languageABM = sys.getLanguageABMFacade();
        languageABM.saveText(key,"es",texts[0], userSessionData);
        languageABM.saveText(key,"en",texts[1], userSessionData);
        languageABM.saveText(key,"pt",texts[2], userSessionData);

        assesment.setName(key);
        assessmentABM.update(assesment, userSessionData);

        int copy = Integer.parseInt(assesmentForm.getCopy());
        if(copy > 0) {
        	AssesmentData copyAssessment = sys.getAssesmentReportFacade().findAssesment(new Integer(copy), userSessionData);
        	Iterator it = copyAssessment.getModuleIterator();
        	while(it.hasNext()) {
        		assessmentABM.addExistingModule(assessmentId, ((ModuleData)it.next()).getId(), userSessionData);
        	}
            sys.reloadText();
        }
                
        sys.reloadText();
        session.removeAttribute("AssesmentCreateForm");
        session.setAttribute("assesment",assessmentId.toString());
        return mapping.findForward("assesment");
    }
}