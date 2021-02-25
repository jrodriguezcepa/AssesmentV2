/*
 * Created on 08-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.report;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import assesment.business.AssesmentAccess;
import assesment.business.assesment.AssesmentReportFacade;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.language.Text;
import assesment.communication.report.QuestionRankingDataSource;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

public class QuestionRankingReportAction extends AbstractAction {

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
        
        AssesmentReportFacade report = sys.getAssesmentReportFacade();
        AssesmentData ad = report.findAssesment(new Integer(assesment), sys.getUserSessionData());
        int moduleCount = ad.getModules().size();
        Collection rankingData = report.getWrongAnswers(assesment,sys.getUserSessionData());
        Object[][] data = new Object[rankingData.size()][5];
        String[] modules = new String[moduleCount];
        Iterator it = rankingData.iterator();
        int i = 0;
        while(it.hasNext()) {
            data[i] = (Object[])it.next();
            modules[Integer.parseInt(String.valueOf(data[i][1]))-1] = String.valueOf(data[i][1]) + " - " + messages.getText(String.valueOf(data[i][0]));
            i++;
        }
        AssesmentData assesmentData = report.findAssesment(new Integer(assesment),sys.getUserSessionData());
        Hashtable count = report.getUserAnswerCount(assesment,sys.getUserSessionData());
        
        QuestionRankingDataSource rankingDataSource = new QuestionRankingDataSource(data,count,sys.getText());
        String[] reportData = sys.getAssesmentReportFacade().findReportData(new Integer(assesment),sys.getUserSessionData());

        HashMap parameters = new HashMap();
        parameters.put("Title",messages.getText("report.ranking.title"));
        parameters.put("Module",messages.getText("generic.module"));
        parameters.put("Question"," "+messages.getText("generic.question"));
        parameters.put("Wrong",messages.getText("report.userresult.wrong"));
        parameters.put("Modules",messages.getText("assesment.modules"));
        parameters.put("Background",AssesmentData.FLASH_PATH+"/images/13_1.jpg");
        int j = 1;
        for(i = 0; i < moduleCount; i++) {
            if(!Util.empty(modules[i])) {
                parameters.put("Module"+j,modules[i]);
                j++;
            }
        }
        parameters.put("Percent","%");

        parameters.put("AssessmentText",messages.getText("generic.assesment")+": "+reportData[0]);
        parameters.put("CorporationText",messages.getText("generic.data.corporation")+": "+reportData[1]);
        parameters.put("Logo",AssesmentData.FLASH_PATH+"/images/logo"+String.valueOf(assesmentData.getCorporationId())+".jpg");
        parameters.put("LogoCEPA",AssesmentData.FLASH_PATH+"/images/logo-cepa.jpg");
        parameters.put("Date",Util.dateToString(messages,Calendar.getInstance(),sys.getUserSessionData().getLenguage()));
        
        ReportUtil util = new ReportUtil();
        return util.executeReport("jasper/questionRanking"+assesmentForm.getString("output")+".jasper",assesmentForm.getString("output"),parameters,rankingDataSource,session,mapping,response,"assesment");
    }
}