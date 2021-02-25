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

import net.sf.jasperreports.engine.JRDataSource;
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
import assesment.communication.report.ModuleResultReportDataSource;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

public class ModuleResultAction extends AbstractAction {

    public ActionForward cancel(HttpSession session, ActionMapping mapping, ActionForm form) {
        return null;
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        
        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        Text messages = sys.getText();
        
        DynaActionForm assesmentForm = (DynaActionForm)form;
        
        String assement = assesmentForm.getString("assesment");
        
        String module = assesmentForm.getString("module");
        if(Util.empty(module)) {
            session.setAttribute("Msg",messages.getText("question.resultreport.error"));
            return mapping.findForward("back");
        }
        
        AssesmentAttributes attributes = sys.getAssesmentReportFacade().findAssesmentAttributes(new Integer(assement),sys.getUserSessionData());
        JRDataSource[] dataSources = sys.getQuestionReportFacade().findQuestionReportByModule(new Integer(module),new Integer(assement),sys.getUserSessionData(),messages);
        String[] reportData = sys.getAssesmentReportFacade().findReportData(new Integer(assement),sys.getUserSessionData());
        
        FileInputStream input = new FileInputStream(QuestionResultAction.class.getResource("jasper/questionModule"+assesmentForm.getString("output")+".jasper").getFile());
        JasperReport subReport = (JasperReport)JRLoader.loadObject(input);
        
        HashMap parameters = new HashMap();
        parameters.put("questionDS",dataSources[1]);
        parameters.put("subreport",subReport);
        parameters.put("Title",messages.getText("module.resultreport.title"));
        parameters.put("Percent",messages.getText("report.generalresult2.average"));
        parameters.put("AssesmentText",messages.getText("module.resultreport.assesment")+": "+((ModuleResultReportDataSource)dataSources[0]).getAssesmentName());
        parameters.put("ModuleText",messages.getText("module.resultreport.module")+": "+((ModuleResultReportDataSource)dataSources[0]).getModuleName());
        parameters.put("Question",messages.getText("module.resultreport.question"));
        parameters.put("Wrong",messages.getText("module.resultreport.wrong"));
        parameters.put("Count",messages.getText("module.resultreport.right"));
        parameters.put("Logo",AssesmentData.FLASH_PATH+"/images/logo"+String.valueOf(attributes.getCorporationId())+".jpg");
        parameters.put("LogoCEPA",AssesmentData.FLASH_PATH+"/images/logo-cepa.jpg");
        parameters.put("AssessmentText",messages.getText("generic.assesment")+": "+reportData[0]);
        parameters.put("CorporationText",messages.getText("generic.data.corporation")+": "+reportData[1]);
        parameters.put("Date",Util.dateToString(messages,Calendar.getInstance(),sys.getUserSessionData().getLenguage()));
        
        ReportUtil util = new ReportUtil();
        return util.executeReport("jasper/moduleResult"+assesmentForm.getString("output")+".jasper",assesmentForm.getString("output"),parameters,dataSources[0],session,mapping,response,"usersresult");
    }
}