/*
 * Created on 22-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.user;

import java.rmi.RemoteException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import assesment.business.AssesmentAccess;
import assesment.business.administration.user.UsABMFacade;
import assesment.business.administration.user.UsReportFacade;
import assesment.communication.exception.CommunicationProblemException;
import assesment.communication.exception.DCSecurityException;
import assesment.communication.exception.DeslogedException;
import assesment.communication.language.Text;
import assesment.communication.user.UserData;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

/**
 * @author fcarriquiry
 */
public class UpdateUserCodeAction extends AbstractAction {

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        Text messages = sys.getText();

        UpdateUserCodeForm updateData = (UpdateUserCodeForm) form;
		String msg = validate(messages,updateData.getExtraData3(), sys);
		if (msg != null) {
			session.setAttribute("Msg", msg);
			session.setAttribute("user",updateData.getLoginname());
            return mapping.findForward("back");

		}else {
			String code = updateData.getExtraData3().trim().toUpperCase();
            UsReportFacade userReport=sys.getUserReportFacade();
			UserData data=userReport.findUserByPrimaryKey(updateData.getLoginname(),sys.getUserSessionData());			
			data.setExtraData3(updateData.getExtraData3());
     
            UsABMFacade userABM = sys.getUserABMFacade();
			userABM.userUpdate(data,false,sys.getUserSessionData());
			userABM.deleteSendedReport(updateData.getLoginname(), sys.getUserSessionData());
            session.setAttribute("user",data.getLoginName());
			return mapping.findForward("view");
        }
	}
	
    private String validate(Text messages,String code, AssesmentAccess sys) throws DeslogedException, CommunicationProblemException, DCSecurityException {
        String webinarCode="";
    	for(int i = 0; i < code.length(); i++) {
        	char c = code.charAt(i);
        	if(Character.isLetterOrDigit(c)) {
        		webinarCode += String.valueOf(c);
        	}
    	}
            if(webinarCode == null || webinarCode.trim().length() != 6) {

                return messages.getText("generic.user.webinar.error1");
            }else {
            	//valido que este en fdm 
                UsReportFacade userReportFacade = sys.getUserReportFacade();
                try {
                	if(!userReportFacade.validateWebinarCode(webinarCode.toUpperCase().trim() ,sys.getUserSessionData())) {
                        return messages.getText("generic.user.webinar.codenotexists");
					}
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	
            }
        
        return null;
    }

    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        session.setAttribute("user",((UpdateUserCodeForm)form).getLoginname());
        return mapping.findForward("view");
    }

}