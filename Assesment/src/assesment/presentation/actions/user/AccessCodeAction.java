/*
 * Created on 17-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import assesment.business.AssesmentAccess;
import assesment.communication.administration.AccessCodeData;
import assesment.communication.assesment.AssesmentData;
import assesment.presentation.translator.web.util.AbstractAction;

public class AccessCodeAction extends AbstractAction {

    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        return mapping.findForward("logout");
    }

    public ActionForward action(ActionMapping mapping, ActionForm createForm, HttpServletRequest request, HttpServletResponse response) throws Throwable {

        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));

        DynaActionForm createData = (DynaActionForm) createForm;
        String accesscode = createData.getString("accesscode").trim();
        if(accesscode.length() == 0) {
            return mapping.findForward("error1");
        }
        AccessCodeData codeData = sys.getAssesmentReportFacade().getAccessCode(accesscode,sys.getUserSessionData());
        
        if(codeData == null) {
            return mapping.findForward("error2");
        }else {
            if(codeData.getUsed().intValue() < codeData.getNumber().intValue()) {
                if(!codeData.getLanguage().equals("es")) {
                    sys.getUserSessionData().setLenguage(codeData.getLanguage());
                    sys.loadText();
                }
                session.setAttribute("assesment",codeData.getAssesment());
                session.setAttribute("accesscode",codeData.getCode());
                if(codeData.getAssesment().intValue() == AssesmentData.MONSANTO_NEW_HIRE) {
                    return mapping.findForward("nextNewHire");
                }
                if(AssesmentData.isJJ(codeData.getAssesment().intValue())) {
                    return mapping.findForward("nextJJ");
                }
                if(codeData.getAssesment().intValue() == AssesmentData.MONSANTO_EMEA) {
                    return mapping.findForward("emeaMonsanto");
                }
                if(codeData.getAssesment().intValue() == AssesmentData.MONSANTO_BRAZIL) {
                    return mapping.findForward("nextMonsantoBR");
                }
                if(codeData.getAssesment().intValue() == AssesmentData.PEPSICO ||
                	codeData.getAssesment().intValue() == AssesmentData.PEPSICO_CEPA_SYSTEM) {
                    return mapping.findForward("nextPepsico");
                }
                if(codeData.getAssesment().intValue() == AssesmentData.PEPSICO_CANDIDATOS) {
	                return mapping.findForward("nextPepsicoCandidatos");
	            }
                if(codeData.getAssesment().intValue() == AssesmentData.DNB) {
                    return mapping.findForward("nextDNB");
                }
                return mapping.findForward("next");
            }else {
                return mapping.findForward("error3");
            }
        }
    }
}

