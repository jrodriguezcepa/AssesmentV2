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

import net.sf.jasperreports.engine.JREmptyDataSource;
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
import assesment.communication.report.UserWrongAnswerReportDataSource;
import assesment.communication.user.UserData;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

public class UserErrorAction extends AbstractAction {

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
        String user = assesmentForm.getString("user");
        if(Util.empty(user)) {
            session.setAttribute("Msg",messages.getText("report.error.selectassesment"));
            return mapping.findForward("back");
        }
        
        String[] reportData = sys.getAssesmentReportFacade().findReportData(new Integer(assesment),sys.getUserSessionData());
        AssesmentAttributes attributes = sys.getAssesmentReportFacade().findAssesmentAttributes(new Integer(assesment),sys.getUserSessionData());
        
        UserData userData = sys.getUserReportFacade().findUserByPrimaryKey(user,sys.getUserSessionData());
        String[][] data = sys.getQuestionReportFacade().getWrongAnswers(new Integer(assesment),user,sys.getUserSessionData());
        
        UserWrongAnswerReportDataSource moduleDS = new UserWrongAnswerReportDataSource(data,messages);

        FileInputStream input = new FileInputStream(QuestionResultAction.class.getResource("jasper/errorModule"+assesmentForm.getString("output")+".jasper").getFile());
        JasperReport subreport = (JasperReport)JRLoader.loadObject(input);

        
        HashMap parameters = new HashMap();
        parameters.put("moduleDS",moduleDS);
        parameters.put("subreport",subreport);
        parameters.put("user",user);
        parameters.put("Title",messages.getText("report.usererror.title"));
        parameters.put("ModuleText"," "+messages.getText("report.userresult.module"));
        parameters.put("RedMessage1"," "+messages.getText("report.usererror.redmessage1"));
        parameters.put("RedMessage2",messages.getText("report.usererror.redmessage2"));
        parameters.put("GreenMessage1"," "+messages.getText("report.usererror.greenmessage1"));
        parameters.put("GreenMessage2",messages.getText("report.usererror.greenmessage2"));
        parameters.put("UserText",messages.getText("report.userresult.user")+": "+userData.getFirstName()+" "+userData.getLastName());
        parameters.put("AssessmentText",messages.getText("generic.assesment")+": "+reportData[0]);
        parameters.put("CorporationText",messages.getText("generic.data.corporation")+": "+reportData[1]);
        parameters.put("Logo",AssesmentData.FLASH_PATH+"/images/logo"+String.valueOf(attributes.getCorporationId())+".jpg");
        parameters.put("LogoCEPA",AssesmentData.FLASH_PATH+"/images/logo-cepa.jpg");
        parameters.put("Date",Util.dateToString(messages,Calendar.getInstance(),sys.getUserSessionData().getLenguage()));
        
        ReportUtil util = new ReportUtil();
        return util.executeReport("jasper/userError"+assesmentForm.getString("output")+".jasper",assesmentForm.getString("output"),parameters,new JREmptyDataSource(),session,mapping,response,"userserror");
   }
}