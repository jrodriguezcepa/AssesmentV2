/*
 * Created on 08-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.report;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import assesment.business.AssesmentAccess;
import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.language.Text;
import assesment.communication.report.CompleteUserReportDataSource;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

public class CompleteUserResultAction extends AbstractAction {

    public ActionForward cancel(HttpSession session, ActionMapping mapping, ActionForm form) {
        return null;
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        
        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        Text messages = sys.getText();
        
        DynaActionForm assesmentForm = (DynaActionForm)form;
        
        String assesment = assesmentForm.getString("assesment");
        if(Util.empty(assesment)) {
            session.setAttribute("Msg",messages.getText("report.error.selectassesment"));
            return mapping.findForward("back");
        }
        Date start = Util.getDate(assesmentForm.getString("startDate"), assesmentForm.getString("startMonth"), assesmentForm.getString("startYear"));
        Date end = Util.getDate(assesmentForm.getString("endDate"), assesmentForm.getString("endMonth"), assesmentForm.getString("endYear"));
        
        String[] reportData = sys.getAssesmentReportFacade().findReportData(new Integer(assesment),sys.getUserSessionData());
        AssesmentAttributes attributes = sys.getAssesmentReportFacade().findAssesmentAttributes(new Integer(assesment),sys.getUserSessionData());
        
        Collection results = sys.getAssesmentReportFacade().getAssessmentResults(new Integer(assesment),start,end,sys.getUserSessionData());
        CompleteUserReportDataSource datasource = new CompleteUserReportDataSource(results,20,messages);

        HashMap parameters = new HashMap();
        parameters.put("title",messages.getText("assesment.report.completeuser"));
        parameters.put("assessment",messages.getText("generic.assesment")+": ");
        parameters.put("assessmentText",reportData[0]);
        parameters.put("corporation",messages.getText("generic.data.corporation")+": ");
        parameters.put("corporationText",reportData[1]);
        parameters.put("start",messages.getText("generic.messages.from"));
        if(start == null) {
            parameters.put("startValue","---");
        }else {
        	parameters.put("startValue",Util.formatDate(start));
        }
        parameters.put("end",messages.getText("generic.messages.to"));
        if(end == null) {
            parameters.put("endValue","---");
        }else {
        	parameters.put("endtValue",Util.formatDate(end));
        }
        parameters.put("superviser",messages.getText("assessment.jj.champion"));
        parameters.put("name",messages.getText("report.users.name"));
        parameters.put("last",messages.getText("user.data.lastname"));
        parameters.put("country",messages.getText("user.data.country"));
        parameters.put("op_group","Op group");
        parameters.put("psi",messages.getText("assessment.psi"));
        parameters.put("quiz",messages.getText("assessment.quiz"));
        parameters.put("lessons",messages.getText("assessment.lessons"));        
        parameters.put("Date",Util.dateToString(messages,Calendar.getInstance(),sys.getUserSessionData().getLenguage()));
        
        ReportUtil util = new ReportUtil();
        String name = "jasper/CompleteUserResult";
        if(assesmentForm.getString("output").equals("PDF")) {
        	name += "PDF";
        }
        if(attributes.getId().intValue() == AssesmentData.JJ) {
        	name += "_JJ";
        }
        return util.executeReport(name+".jasper",assesmentForm.getString("output"),parameters,datasource,session,mapping,response,"results");
   }
}