/*
 * Created on 08-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.corporation;

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
import assesment.communication.corporation.CorporationAttributes;
import assesment.communication.language.Text;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

/**
 * @author fcarriquiry
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CorporationCreateAction extends AbstractAction {


    public ActionForward cancel(HttpSession session, ActionMapping mapping, ActionForm form) {
        return mapping.findForward("cancel");
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        
        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        Text messages = sys.getText();
        
        CorporationForm corporationForm = (CorporationForm)form;
        
        if(Util.empty(corporationForm.getName())) {
            session.setAttribute("Msg",messages.getText("corporation.name.invalid"));
            return mapping.findForward("back");
        }
        
        FormFile fileForm = corporationForm.getLogo();
        if(fileForm != null && fileForm.getFileName() != null && !fileForm.getFileName().equals("")){

        	if(!fileForm.getFileName().toLowerCase().endsWith(".png")) {
                session.setAttribute("Msg","Logo PNG");
                return mapping.findForward("back");
        	}
        	
            String logo = (corporationForm.getLogo() != null) ? corporationForm.getLogo().getFileName() : null;
            CorporationAttributes corporation = new CorporationAttributes(null,corporationForm.getName(),logo,null);
            Integer corporationId = sys.getCorporationABMFacade().create(corporation,sys.getUserSessionData());

            InputStream inputStream = fileForm.getInputStream();
            FileOutputStream outputStream1 = new FileOutputStream(AssesmentData.FLASH_PATH+"/flash.war/assessment/images/logo_"+String.valueOf(corporationId)+".png");
            FileOutputStream outputStream2 = new FileOutputStream(AssesmentData.FLASH_PATH+"/flash.war/images/logo"+String.valueOf(corporationId)+".png");
            FileOutputStream outputStream3 = new FileOutputStream(AssesmentData.FLASH_PATH+"/assessment/images/logo_"+String.valueOf(corporationId)+".png");
            FileOutputStream outputStream4 = new FileOutputStream(AssesmentData.FLASH_PATH+"/images/logo"+String.valueOf(corporationId)+".png");
        
            int c = 0;
            while ((c = inputStream.read()) != -1){
                outputStream1.write(c);
                outputStream2.write(c);
                outputStream3.write(c);
                outputStream4.write(c);
            }           
            outputStream1.flush();
            outputStream1.close();       
            outputStream2.flush();
            outputStream2.close();       
            outputStream3.flush();
            outputStream3.close();       
            outputStream4.flush();
            outputStream4.close();       
        }

        return mapping.findForward("list");
    }
}