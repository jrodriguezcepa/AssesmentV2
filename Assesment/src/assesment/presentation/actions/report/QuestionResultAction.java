/*
 * Created on 08-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.report;

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
import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.language.Text;
import assesment.communication.report.QuestionResultReportDataSource;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

public class QuestionResultAction extends AbstractAction {

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

        String question = assesmentForm.getString("question");
        if(Util.empty(question)) {
            session.setAttribute("Msg",messages.getText("question.resultreport.error"));
            return mapping.findForward("back");
        }
        
        QuestionResultReportDataSource dataSource = sys.getQuestionReportFacade().findQuestionResult(new Integer(question),sys.getUserSessionData(),messages);

        AssesmentAttributes attributes = sys.getAssesmentReportFacade().findAssesmentAttributes(new Integer(assesment),sys.getUserSessionData());
        String[] reportData = sys.getAssesmentReportFacade().findReportData(new Integer(assesment),sys.getUserSessionData());
        
        HashMap parameters = new HashMap();
        parameters.put("Title",messages.getText("question.resultreport.title"));
        parameters.put("Question",messages.getText(dataSource.getQuestion()));
        parameters.put("Answer",messages.getText("question.resultreport.answer"));
        parameters.put("Count"," "+messages.getText("question.resultreport.count"));
        parameters.put("Logo",AssesmentData.FLASH_PATH+"/images/logo"+String.valueOf(attributes.getCorporationId())+".jpg");
        parameters.put("LogoCEPA",AssesmentData.FLASH_PATH+"/images/logo-cepa.jpg");
        parameters.put("AssessmentText",messages.getText("generic.assesment")+": "+reportData[0]);
        parameters.put("CorporationText",messages.getText("generic.data.corporation")+": "+reportData[1]);
        parameters.put("Date",Util.dateToString(messages,Calendar.getInstance(),sys.getUserSessionData().getLenguage()));
        
        String fileName = null; 
        if(dataSource.getQuestionSize() > 10) {
            fileName = "jasper/questionResultMultiOptions"+assesmentForm.getString("output")+".jasper";
        }else {
            fileName = "jasper/questionResultOptions"+assesmentForm.getString("output")+".jasper";
        }
        ReportUtil util = new ReportUtil();
        return util.executeReport(fileName,assesmentForm.getString("output"),parameters,dataSource,session,mapping,response,"usersresult");
    }
}