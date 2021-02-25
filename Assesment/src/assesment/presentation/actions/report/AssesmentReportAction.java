/*
 * Created on 08-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.report;

import java.io.FileInputStream;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import assesment.business.AssesmentAccess;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.language.Text;
import assesment.communication.report.AssesmentReportDataSource;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

public class AssesmentReportAction extends AbstractAction {

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
            session.setAttribute("Msg",messages.getText("generic.error.selectassessment"));
            return mapping.findForward("back");
        }
        
        String[] reportData = sys.getAssesmentReportFacade().findReportData(new Integer(assesment),sys.getUserSessionData());
        AssesmentData assesmentData = sys.getAssesmentReportFacade().findAssesment(new Integer(assesment),sys.getUserSessionData());
        
        
        AssesmentReportDataSource assesmentDataSource = new AssesmentReportDataSource(assesmentData,sys.getText());

        HashMap parameters = new HashMap();
        parameters.put("Title",messages.getText("report.assesment.title"));
        parameters.put("QuestionText",messages.getText("report.assesment.totalquestions")+": "+String.valueOf(assesmentData.getQuestionCount()));
        parameters.put("AssessmentText",messages.getText("generic.assesment")+": "+reportData[0]);
        parameters.put("CorporationText",messages.getText("generic.data.corporation")+": "+reportData[1]);
        parameters.put("Logo",AssesmentData.FLASH_PATH+"/images/logo"+String.valueOf(assesmentData.getCorporationId())+".png");
        parameters.put("LogoCEPA",AssesmentData.FLASH_PATH+"/images/logo-cepa.jpg");
        parameters.put("background",AssesmentData.FLASH_PATH+"/images/13_1.jpg");
        parameters.put("Date",Util.dateToString(messages,Calendar.getInstance(),sys.getUserSessionData().getLenguage()));
        
        ReportUtil util = new ReportUtil();
        FileInputStream input = new FileInputStream(AssesmentData.JASPER_PATH+"AssesmentReportHTML.jasper");
        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(input);
        
        return util.executeReport(jasperReport,assesmentForm.getString("output"),parameters,assesmentDataSource,session,mapping,response,"assesment");        
    }
}