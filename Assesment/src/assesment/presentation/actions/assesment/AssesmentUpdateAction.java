/*
 * Created on 08-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.assesment;

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
import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.language.Text;
import assesment.communication.module.ModuleAttribute;
import assesment.presentation.translator.web.util.AbstractAction;

/**
 * @author fcarriquiry
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class AssesmentUpdateAction extends AbstractAction {


    public ActionForward cancel(HttpSession session, ActionMapping mapping, ActionForm form) {
        AssesmentForm assesmentForm = (AssesmentForm)form;
        session.setAttribute("assesment",assesmentForm.getId());
        return mapping.findForward("cancel");
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        
        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        Text messages = sys.getText();
        
        AssesmentForm assesmentForm = (AssesmentForm)form;
        
        String msg = assesmentForm.validate();
        if(msg != null) {
            session.setAttribute("Msg",messages.getText(msg));
            session.setAttribute("assesment",assesmentForm.getId());
            return mapping.findForward("view");
        }

        AssesmentAttributes assesment = assesmentForm.createAssesment();

        String[] texts = {assesmentForm.getName_es(),assesmentForm.getName_en(),assesmentForm.getName_pt()};
        sys.getLanguageABMFacade().updateTexts(texts, assesment.getName(), sys.getUserSessionData());

        switch(Integer.parseInt(assesmentForm.getCertificate())) {
	        case 1:
	            assesment.setCertificateImageES(null);
	            assesment.setCertificateImageEN(null);
	            assesment.setCertificateImagePT(null);
	        	break;
	        case 2:
	        	FormFile fileForm = assesmentForm.getCertificateES();
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
		        	}
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
		        	}
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
		        	}
		        }
        }        
        
    	FormFile fileForm = assesmentForm.getIcon();
        if(fileForm != null) {
            String fileNameES = fileForm.getFileName();
        	if(fileNameES != null && !fileNameES.equals("")){
	            InputStream inputStream = fileForm.getInputStream();
	            FileOutputStream outputStream = new FileOutputStream(AssesmentData.FLASH_PATH+"/images/icon_"+assesment.getId()+".png");
	            int c = 0;
	            while ((c = inputStream.read()) != -1){
	                outputStream.write(c);
	            }           
	            outputStream.flush();
	            outputStream.close();
	            
	            assesment.setIcon(true);
        	}
        }

    	fileForm = assesmentForm.getAttachesPDF();
        if(fileForm != null) {
            String fileNameES = fileForm.getFileName();
        	if(fileNameES != null && !fileNameES.equals("")){
	            InputStream inputStream = fileForm.getInputStream();
	            FileOutputStream outputStream = new FileOutputStream(AssesmentData.FLASH_PATH+"/pdf/assessment_es_"+assesment.getId()+".pdf");
	            int c = 0;
	            while ((c = inputStream.read()) != -1){
	                outputStream.write(c);
	            }           
	            outputStream.flush();
	            outputStream.close();
	            
	            assesment.setAttachesPDF(true);
        	}
        }

    	fileForm = assesmentForm.getAttachenPDF();
        if(fileForm != null) {
            String fileNameES = fileForm.getFileName();
        	if(fileNameES != null && !fileNameES.equals("")){
	            InputStream inputStream = fileForm.getInputStream();
	            FileOutputStream outputStream = new FileOutputStream(AssesmentData.FLASH_PATH+"/pdf/assessment_en_"+assesment.getId()+".pdf");
	            int c = 0;
	            while ((c = inputStream.read()) != -1){
	                outputStream.write(c);
	            }           
	            outputStream.flush();
	            outputStream.close();
	            
	            assesment.setAttachenPDF(true);
        	}
        }

    	fileForm = assesmentForm.getAttachptPDF();
        if(fileForm != null) {
            String fileNameES = fileForm.getFileName();
        	if(fileNameES != null && !fileNameES.equals("")){
	            InputStream inputStream = fileForm.getInputStream();
	            FileOutputStream outputStream = new FileOutputStream(AssesmentData.FLASH_PATH+"/pdf/assessment_pt_"+assesment.getId()+".pdf");
	            int c = 0;
	            while ((c = inputStream.read()) != -1){
	                outputStream.write(c);
	            }           
	            outputStream.flush();
	            outputStream.close();
	            
	            assesment.setAttachptPDF(true);
        	}
        }

        sys.getAssesmentABMFacade().update(assesment,sys.getUserSessionData());
                
        sys.reloadText();
        session.setAttribute("assesment",assesmentForm.getId());
        return mapping.findForward("view");
    }
}