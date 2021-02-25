/*
 * Created on 08-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.report;

import java.io.FileInputStream;
import java.util.Calendar;
import java.util.Collection;
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
import assesment.business.assesment.AssesmentReportFacade;
import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.language.Text;
import assesment.communication.module.ModuleData;
import assesment.communication.report.NewHirePersonalDataReportDataSource;
import assesment.communication.report.TotalUserResultReportDataSource;
import assesment.communication.report.UserWrongAnswerReportDataSource;
import assesment.communication.user.UserData;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

public class NewHireReportAction extends AbstractAction {

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
        
        AssesmentReportFacade assesmentReport = sys.getAssesmentReportFacade();
        
        String[] reportData = assesmentReport.findReportData(new Integer(assesment),sys.getUserSessionData());
        AssesmentAttributes attributes = assesmentReport.findAssesmentAttributes(new Integer(assesment),sys.getUserSessionData());
        
        UserData userData = sys.getUserReportFacade().findUserByPrimaryKey(user,sys.getUserSessionData());
        String[][] data = sys.getQuestionReportFacade().getWrongAnswers(new Integer(assesment),user,sys.getUserSessionData());
        Integer total = sys.getAssesmentReportFacade().getAssesmentsQuestionCount(attributes,sys.getUserSessionData(),false);

        Integer newHire = assesmentReport.getNewHireValue(user,assesment,sys.getUserSessionData());
        
        UserWrongAnswerReportDataSource dataSource3 = new UserWrongAnswerReportDataSource(data,messages);
        FileInputStream input2 = new FileInputStream(QuestionResultAction.class.getResource("jasper/newHireModule"+assesmentForm.getString("output")+".jasper").getFile());
        JasperReport subreport2 = (JasperReport)JRLoader.loadObject(input2);

        TotalUserResultReportDataSource dataSource2 = new TotalUserResultReportDataSource(dataSource3.getTotal(),total - dataSource3.getTotal(),messages);
        FileInputStream input3 = new FileInputStream(QuestionResultAction.class.getResource("jasper/newHireSubreport1"+assesmentForm.getString("output")+".jasper").getFile());
        JasperReport subreport1 = (JasperReport)JRLoader.loadObject(input3);

        
        String module0Text = "";
        String semaphore = "";
        if(newHire <= 5) {
            semaphore = AssesmentData.FLASH_PATH+"/images/luz_verde.gif";
            module0Text = "report.newhire.reporttext8";
        }else if(newHire == 6) {
            semaphore = AssesmentData.FLASH_PATH+"/images/luz_verde.gif";
            module0Text = "report.newhire.reporttext7";
        }else if(newHire == 7) {
            semaphore = AssesmentData.FLASH_PATH+"/images/luz_verde.gif";
            module0Text = "report.newhire.reporttext6";
        }else if(newHire == 8) {
            semaphore = AssesmentData.FLASH_PATH+"/images/luz_amarilla.gif";
            module0Text = "report.newhire.reporttext5";
        }else if(newHire == 9) {
            semaphore = AssesmentData.FLASH_PATH+"/images/luz_amarilla.gif";
            module0Text = "report.newhire.reporttext4";
        }else if(newHire == 10) {
            semaphore = AssesmentData.FLASH_PATH+"/images/luz_roja.gif";
            module0Text = "report.newhire.reporttext3";
        }else if(newHire == 11) {
            semaphore = AssesmentData.FLASH_PATH+"/images/luz_roja.gif";
            module0Text = "report.newhire.reporttext2";
        }else {
            semaphore = AssesmentData.FLASH_PATH+"/images/luz_roja.gif";
            module0Text = "report.newhire.reporttext1";
        }
        
        ModuleData module = sys.getModuleReportFacade().getPersonalDataModule(new Integer(assesment),sys.getUserSessionData());
        Collection answers = sys.getUserReportFacade().findModuleResult(user,module.getId(),sys.getUserSessionData());
        
        NewHirePersonalDataReportDataSource dataSource1 = new NewHirePersonalDataReportDataSource(module,answers,messages);

        HashMap parameters = new HashMap();
        parameters.put("Title","New Hire Assessment Report");
        parameters.put("Logo",AssesmentData.FLASH_PATH+"/images/logo"+String.valueOf(attributes.getCorporationId())+".jpg");

        parameters.put("UserText",messages.getText("report.newhire.user")+": "+userData.getFirstName()+" "+userData.getLastName());
        parameters.put("Date",Util.dateToString(messages,Calendar.getInstance(),sys.getUserSessionData().getLenguage()));
        parameters.put("CorporationText",messages.getText("generic.data.corporation")+": "+reportData[1]);
        parameters.put("AssessmentText",messages.getText("generic.assesment")+": "+reportData[0]);
        
        parameters.put("Module0Text","ESTIMATED POTENTIAL TRAFFIC RISK LEVEL "+String.valueOf(newHire));
        parameters.put("subreport1",subreport1);
        parameters.put("dataSource1",dataSource2);
        parameters.put("subreport2",subreport2);
        parameters.put("dataSource2",dataSource3);
        parameters.put("ResultText",messages.getText(module0Text));
        parameters.put("RightC",dataSource2.getRightAnswers());
        parameters.put("WrongC",dataSource2.getWrongAnswers());
        parameters.put("RightP",dataSource2.getRightPercent());
        parameters.put("WrongP",dataSource2.getWrongPercent());
        parameters.put("semaphore",semaphore);
        
        ReportUtil util = new ReportUtil();
        return util.executeReport("jasper/TotalnewHireReport"+assesmentForm.getString("output")+".jasper",assesmentForm.getString("output"),parameters,dataSource1,session,mapping,response,"newhire");
   }
}
