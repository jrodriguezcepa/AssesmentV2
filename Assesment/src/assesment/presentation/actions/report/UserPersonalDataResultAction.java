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
import assesment.communication.module.ModuleData;
import assesment.communication.report.NewHirePersonalDataReportDataSource;
import assesment.communication.report.UserPersonalDataReportDataSource;
import assesment.communication.user.UserData;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

public class UserPersonalDataResultAction extends AbstractAction {

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
            session.setAttribute("Msg",messages.getText("report.error.selectuser"));
            return mapping.findForward("back");
        }
        
        AssesmentAttributes attributes = sys.getAssesmentReportFacade().findAssesmentAttributes(new Integer(assesment),sys.getUserSessionData());
        String[] reportData = sys.getAssesmentReportFacade().findReportData(new Integer(assesment),sys.getUserSessionData());
        
        UserData userData = sys.getUserReportFacade().findUserByPrimaryKey(user,sys.getUserSessionData());
        ModuleData module = sys.getModuleReportFacade().getPersonalDataModule(new Integer(assesment),sys.getUserSessionData());
        
        String report = "jasper/PersonalDataReport"+assesmentForm.getString("output")+".jasper";
        HashMap parameters = new HashMap();
        JRDataSource dataSource = null;
        Collection answers = sys.getUserReportFacade().findModuleResult(user,module.getId(),sys.getUserSessionData());
        
        if(attributes.getId().intValue() != AssesmentData.MONSANTO_NEW_HIRE) {
            dataSource = new UserPersonalDataReportDataSource(module,answers,messages);
        
            FileInputStream input = new FileInputStream(QuestionResultAction.class.getResource("jasper/subPersonalData"+assesmentForm.getString("output")+".jasper").getFile());
            JasperReport subReport = (JasperReport)JRLoader.loadObject(input);
        
            parameters.put("datasource",dataSource);
            parameters.put("subreport",subReport);
            
            parameters.put("Title",messages.getText("driver.result.personaldata"));
            parameters.put("UserText",messages.getText("report.userresult.user")+": "+userData.getFirstName()+" "+userData.getLastName());
        }else {
            dataSource = new NewHirePersonalDataReportDataSource(module,answers,messages);
            report = "jasper/TotalnewHireReport"+assesmentForm.getString("output")+".jasper";
            parameters.put("Title",messages.getText("report.newhire.title"));
            parameters.put("UserText",messages.getText("report.newhire.user")+": "+userData.getFirstName()+" "+userData.getLastName());
        }
        
        parameters.put("AssessmentText",messages.getText("generic.assesment")+": "+reportData[0]);
        parameters.put("CorporationText",messages.getText("generic.data.corporation")+": "+reportData[1]);
        parameters.put("Logo",AssesmentData.FLASH_PATH+"/images/logo"+String.valueOf(attributes.getCorporationId())+".jpg");
        parameters.put("LogoCEPA",AssesmentData.FLASH_PATH+"/images/logo-cepa.jpg");
        parameters.put("Date",Util.dateToString(messages,Calendar.getInstance(),sys.getUserSessionData().getLenguage()));
        
        ReportUtil util = new ReportUtil();
        return util.executeReport(report,assesmentForm.getString("output"),parameters,dataSource,session,mapping,response,"usersresult");
   }
}