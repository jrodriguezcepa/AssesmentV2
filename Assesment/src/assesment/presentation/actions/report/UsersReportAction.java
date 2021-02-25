/*
 * Created on 08-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.fill.JRFileVirtualizer;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import assesment.business.AssesmentAccess;
import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.language.Text;
import assesment.communication.report.UsersReportDataSource;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

public class UsersReportAction extends AbstractAction {

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
        
        AssesmentAttributes assesmentData = sys.getAssesmentReportFacade().findAssesmentAttributes(new Integer(assesment),sys.getUserSessionData());

        UsersReportDataSource dataSource = sys.getUserReportFacade().findUsersAssesment(new Integer(assesment),sys.getUserSessionData(),messages);
        String[] reportData = sys.getAssesmentReportFacade().findReportData(new Integer(assesment),sys.getUserSessionData());
        
        HashMap parameters = new HashMap();
        parameters.put("Total",String.valueOf(dataSource.getTotal()));
        parameters.put("All",String.valueOf(dataSource.getAll()));
        parameters.put("Part",String.valueOf(dataSource.getPart()));
        parameters.put("None",String.valueOf(dataSource.getNone()));

        parameters.put("AssessmentText",messages.getText("generic.assesment")+": "+reportData[0]);
        parameters.put("CorporationText",messages.getText("generic.data.corporation")+": "+reportData[1]);

        parameters.put("background",AssesmentData.FLASH_PATH+"/images/13_1.jpg");
        parameters.put("Logo",AssesmentData.FLASH_PATH+"/images/logo"+String.valueOf(assesmentData.getCorporationId())+".jpg");
        parameters.put("LogoCEPA",AssesmentData.FLASH_PATH+"/images/logo-cepa.jpg");
        parameters.put("Count"," "+messages.getText("question.resultreport.count"));
        parameters.put("Title",messages.getText("report.users.title"));
        parameters.put("Date",Util.dateToString(messages,Calendar.getInstance(),sys.getUserSessionData().getLenguage()));
        
        parameters.put("TotalCount",messages.getText("report.users.total.count"));
        parameters.put("AllText",messages.getText("report.users.all"));
        parameters.put("PartText",messages.getText("report.users.part"));
        parameters.put("NoneText",messages.getText("report.users.none"));
        parameters.put("Name"," "+messages.getText("report.users.name"));
        parameters.put("Answered",messages.getText("report.users.answered"));

        String reportName = "jasper/UsersReport"+assesmentForm.getString("output")+".jasper";
        //String reportName = "jasper/prueba.jasper";
        ReportUtil util = new ReportUtil();
        return util.executeReport(reportName,assesmentForm.getString("output"),parameters,dataSource,session,mapping,response,"users");

   }
}