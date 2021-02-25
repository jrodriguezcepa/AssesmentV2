/*
 * Created on 17-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.user;

import java.util.Iterator;

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
import assesment.communication.assesment.AssesmentData;
import assesment.communication.module.ModuleData;
import assesment.presentation.translator.web.util.AbstractAction;

public class SelectUserAssessmentAction extends AbstractAction {

    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        return mapping.findForward("logout");
    }

    public ActionForward action(ActionMapping mapping, ActionForm createForm, HttpServletRequest request, HttpServletResponse response) throws Throwable {

        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        UserSessionData userSession = sys.getUserSessionData(); 
        
        UsReportFacade userReport = sys.getUserReportFacade();
        String user = userSession.getFilter().getLoginName();

        DynaActionForm createData = (DynaActionForm) createForm;
        String assessmentId = createData.getString("assessment").trim();
        
        AssesmentData assesment = sys.getAssesmentReportFacade().findAssesment(new Integer(assessmentId), sys.getUserSessionData());
		Iterator it = assesment.getModuleIterator();
		while(it.hasNext()) {
			ModuleData module = (ModuleData)it.next();
			module.setAnswered(userReport.getQuestionCount(user,module.getId(),assesment.getId(),userSession));
		}
		if(assesment.isPsitest()) {
			assesment.setPsiCount(userReport.getQuestionCount(user,new Integer(0),assesment.getId(),userSession));
		}

		userSession.getFilter().setAssessmentData(assesment);
		userSession.getFilter().setAssesment(assesment.getId());
/*		Iterator it = assesment.getModuleIterator();
		while(it.hasNext()) {
			ModuleData module = (ModuleData)it.next();
			module.setAnswered(userReport.getQuestionCount(user,module.getId(),userSession.getFilter().getAssesment(),userSession));
		}
		if(assesment.isPsitest()) {
			assesment.setPsiCount(userReport.getQuestionCount(user,new Integer(0),userSession.getFilter().getAssesment(),userSession));
		}*/
        return mapping.findForward("next");
    }
}

