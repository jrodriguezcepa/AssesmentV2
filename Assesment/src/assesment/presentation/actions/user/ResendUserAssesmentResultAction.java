/*
 * Created on 19-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.user;

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

import assesment.business.AssesmentAccess;
import assesment.business.administration.user.UsReportFacade;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.corporation.CorporationData;
import assesment.presentation.actions.administration.AnswerUtil;
import assesment.presentation.actions.administration.AnswerUtilACU;
import assesment.presentation.actions.administration.AnswerUtilAZ;
import assesment.presentation.actions.administration.AnswerUtilAbitab;
import assesment.presentation.actions.administration.AnswerUtilAnglo;
import assesment.presentation.actions.administration.AnswerUtilBasf;
import assesment.presentation.actions.administration.AnswerUtilDOW;
import assesment.presentation.actions.administration.AnswerUtilFacebook;
import assesment.presentation.actions.administration.AnswerUtilGM;
import assesment.presentation.actions.administration.AnswerUtilImesevi;
import assesment.presentation.actions.administration.AnswerUtilJJ;
import assesment.presentation.actions.administration.AnswerUtilJanssen;
import assesment.presentation.actions.administration.AnswerUtilMichelin;
import assesment.presentation.actions.administration.AnswerUtilMonsantoBR;
import assesment.presentation.actions.administration.AnswerUtilMonsantoLAN;
import assesment.presentation.actions.administration.AnswerUtilNewHire;
import assesment.presentation.actions.administration.AnswerUtilNycomed;
import assesment.presentation.actions.administration.AnswerUtilPepsico;
import assesment.presentation.actions.administration.AnswerUtilSanofi;
import assesment.presentation.actions.administration.AnswerUtilSchering;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

/**
 * @author jrodriguez
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ResendUserAssesmentResultAction extends AbstractAction {

    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        DynaActionForm userDeleteForm = (DynaActionForm) form;
        String assesment = (String) userDeleteForm.get("assesment");
        session.setAttribute("id", assesment);
        return mapping.findForward("users");
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {

        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        
        DynaActionForm userReSendForm = (DynaActionForm) form;
        Iterator users = Util.stringToCollection((String) userReSendForm.get("user")).iterator();
        int type = Integer.parseInt((String) userReSendForm.get("type"));
        String email = (type == 1) ? null : (String)userReSendForm.get("email");
        
        String assessmentId = (String) userReSendForm.get("assesment");
 	 	AssesmentData assesment = sys.getAssesmentReportFacade().findAssesment(new Integer(assessmentId),sys.getUserSessionData());
        
        while(users.hasNext()) {
        	String user = (String) users.next();
	        AnswerUtil answerUtil = null;
	        boolean generic = false;
	        if(assesment.getCorporationId() == CorporationData.BASF) {
	        	answerUtil = new AnswerUtilBasf();
	        }else {
	 	        switch(assesment.getId().intValue()) {
	 	            case AssesmentData.MONSANTO_NEW_HIRE: case AssesmentData.NEW_HIRE:
	 	                answerUtil = new AnswerUtilNewHire();
	 	                break;
	 	            case AssesmentData.JJ: case AssesmentData.JJ_2: case AssesmentData.JJ_3:  
	 	            case AssesmentData.JJ_4: case AssesmentData.JJ_5: case AssesmentData.JJ_6:
	 	            case AssesmentData.JJ_7: case AssesmentData.JJ_8:
	 	                answerUtil = new AnswerUtilJJ();
	 	                break;
	 	            case AssesmentData.JANSSEN: case AssesmentData.JANSSEN_2:
	 	                answerUtil = new AnswerUtilJanssen();
	 	                break;
	 	            case AssesmentData.FACEBOOK:
	 	                answerUtil = new AnswerUtilFacebook();
	 	                break;
	 	            case AssesmentData.ABITAB:
	 	                answerUtil = new AnswerUtilAbitab();
	 	                break;
	 	            case AssesmentData.MICHELIN: 
	 	            case AssesmentData.MICHELIN_3:
	 	            case AssesmentData.MICHELIN_5:
	 	            case AssesmentData.MICHELIN_6:
	 	            case AssesmentData.MICHELIN_7:
	 	            case AssesmentData.MICHELIN_8:
	 	            case AssesmentData.MICHELIN_9:
	 	                answerUtil = new AnswerUtilMichelin();
	 	                break;
	 	            case AssesmentData.MONSANTO_BRAZIL: case AssesmentData.MONSANTO_ARGENTINA:
	 	                answerUtil = new AnswerUtilMonsantoBR();
	 	                break;
	 	            case AssesmentData.PEPSICO: case AssesmentData.PEPSICO_CANDIDATOS: case AssesmentData.PEPSICO_CEPA_SYSTEM:  
	 	                answerUtil = new AnswerUtilPepsico();
	 	                break;
	 	            case AssesmentData.MONSANTO_LAN: case AssesmentData.MAMUT_ANDINO:
	 	            case AssesmentData.NALCO: case AssesmentData.DNB: case AssesmentData.TRANSMETA:
	 	                answerUtil = new AnswerUtilMonsantoLAN();
	 	                break;
	 	            case AssesmentData.SCHERING:
	 	                answerUtil = new AnswerUtilSchering();
	 	                break;
	 	            case AssesmentData.GM:
	 	                answerUtil = new AnswerUtilGM();
	 	                break;
	 	            case AssesmentData.ACU:
	 	                answerUtil = new AnswerUtilACU();
	 	                break;
	 	            case AssesmentData.DOW:
	 	                answerUtil = new AnswerUtilDOW();
	 	                break;
	 	            case AssesmentData.IMESEVI:
	 	                answerUtil = new AnswerUtilImesevi();
	 	                break;
	 	            case AssesmentData.ASTRAZENECA: case AssesmentData.ASTRAZENECA_2:
	 	           case AssesmentData.ASTRAZENECA_2013:
	 	                answerUtil = new AnswerUtilAZ();
	 	                break;
	 	            case AssesmentData.NYCOMED:
	 	                answerUtil = new AnswerUtilNycomed();
	 	                break;
	 	            case AssesmentData.ANGLO: case AssesmentData.ANGLO_3:
	 	                answerUtil = new AnswerUtilAnglo();
	 	                break;
	 	            case AssesmentData.SANOFI_BRASIL:
	 	                answerUtil = new AnswerUtilSanofi();
	 	                break;
	 	            default:
	 	                answerUtil = new AnswerUtil();
	 	            	generic = true;
	 	        }
 	        }
	        if(generic) {
	        	String redirect = sys.getUserReportFacade().getElearningURL(user,assesment.getId(),sys.getUserSessionData());
	        	answerUtil.newFeedback(sys,assesment,user,email,false,redirect);
	        }else {
	        	answerUtil.resend(sys,assesment,user,email);
	        }
        }
        return mapping.findForward("users");
    }

    private Collection stringToCollection(String stringList){
        String[] stringArray=stringList.split("<");
        Collection col=new LinkedList();
        
        for(int i=0; i < stringArray.length; i++){
            col.add(stringArray[i]);
        }
        
        return col;
    }

}