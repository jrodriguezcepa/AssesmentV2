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

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import assesment.business.AssesmentAccess;
import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.language.Text;
import assesment.communication.report.ResultReportDataSource;
import assesment.communication.report.TotalResultReportDataSource;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

public class ResultAction extends AbstractAction {

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
        
        AssesmentAttributes attributes = sys.getAssesmentReportFacade().findAssesmentAttributes(new Integer(assesment),sys.getUserSessionData());
        String[] reportData = sys.getAssesmentReportFacade().findReportData(new Integer(assesment),sys.getUserSessionData());

        ResultReportDataSource moduleDS = sys.getUserReportFacade().findGeneralResult(new Integer(assesment),sys.getUserSessionData(),messages);
        TotalResultReportDataSource dataSource = sys.getUserReportFacade().findTotalResult(new Integer(assesment),sys.getUserSessionData(),messages);
        
        FileInputStream input = new FileInputStream(QuestionResultAction.class.getResource("jasper/subGeneralResult"+assesmentForm.getString("output")+".jasper").getFile());
        JasperReport subReport = (JasperReport)JRLoader.loadObject(input);
        
        HashMap parameters = new HashMap();
        parameters.put("moduleDS",moduleDS);
        parameters.put("subreport",subReport);
        parameters.put("prom_green",dataSource.getProm_green());
        parameters.put("prom_red",dataSource.getProm_red());
        parameters.put("prom_yellow",dataSource.getProm_yellow());
        parameters.put("prom_total",dataSource.getProm_total());
        parameters.put("Title",messages.getText("report.generalresult.title"));
        parameters.put("subreportTitle",messages.getText("report.userresult.moduletitle"));
        parameters.put("Count",messages.getText("report.generalresult2.count"));
        parameters.put("Average",messages.getText("report.generalresult2.average"));
        parameters.put("CountText",messages.getText("report.generalresult2.counttext"));
        parameters.put("AverageText",messages.getText("report.generalresult2.averagetext"));
        parameters.put("lowlevel"," "+messages.getText("report.generalresult.lowlevel"));
        parameters.put("meddiumlevel"," "+messages.getText("report.generalresult.meddiumlevel"));
        parameters.put("highlevel"," "+messages.getText("report.generalresult.highlevel"));
        parameters.put("total"," "+messages.getText("report.generalresult.total"));
        parameters.put("module"," "+messages.getText("report.generalresult.module"));
        parameters.put("AssessmentText",messages.getText("generic.assesment")+": "+reportData[0]);
        parameters.put("CorporationText",messages.getText("generic.data.corporation")+": "+reportData[1]);
        parameters.put("Logo",AssesmentData.FLASH_PATH+"/images/logo"+String.valueOf(attributes.getCorporationId())+".jpg");
        parameters.put("LogoCEPA",AssesmentData.FLASH_PATH+"/images/logo-cepa.jpg");
        parameters.put("Background",AssesmentData.FLASH_PATH+"/images/13_1.jpg");
        parameters.put("Date",Util.dateToString(messages,Calendar.getInstance(),sys.getUserSessionData().getLenguage()));
        
        ReportUtil util = new ReportUtil();
        
        return util.executeReport("jasper/generalResult"+assesmentForm.getString("output")+".jasper",assesmentForm.getString("output"),parameters,dataSource,session,mapping,response,"generalResult");
    }

}