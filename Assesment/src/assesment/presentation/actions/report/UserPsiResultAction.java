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
import assesment.business.administration.user.UsReportFacade;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.corporation.CorporationData;
import assesment.communication.language.Text;
import assesment.communication.report.UserPsiReportDataSource;
import assesment.communication.user.UserData;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

public class UserPsiResultAction extends AbstractAction {

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
        
        UserData userData = sys.getUserReportFacade().findUserByPrimaryKey(user,sys.getUserSessionData());
        Integer[] values = sys.getUserReportFacade().getUserPsicoResult(userData.getLoginName(),new Integer(assesment),sys.getUserSessionData());
        double average1 = 0;
        double average2 = 0;
        for(int i = 0; i < 3; i++) {
            average1 = average1+values[i];
        }
        for(int i = 3; i < 6; i++) {
            average2 = average2+values[i];
        }
        average1 = average1 / 3.0;
        average2 = average2 / 3.0;

        String[] reportData = sys.getAssesmentReportFacade().findReportData(new Integer(assesment),sys.getUserSessionData());
        AssesmentAttributes attributes = sys.getAssesmentReportFacade().findAssesmentAttributes(new Integer(assesment),sys.getUserSessionData());
        
        UserPsiReportDataSource dataSource = new UserPsiReportDataSource(values,messages);
        
        HashMap parameters = new HashMap();
        parameters.put("Title",messages.getText("report.psiresults.title"));
        parameters.put("average1",new Double(average1));
        parameters.put("average2",new Double(average2));
        parameters.put("UserText",messages.getText("report.userresult.user")+": "+userData.getFirstName()+" "+userData.getLastName());
        parameters.put("AssessmentText",messages.getText("generic.assesment")+": "+reportData[0]);
        parameters.put("CorporationText",messages.getText("generic.data.corporation")+": "+reportData[1]);
        parameters.put("Average1Text1",messages.getText("generic.assessment.average1text1"));
        parameters.put("Average1Text2",messages.getText("generic.assessment.average1text2"));
        parameters.put("Average2Text1",messages.getText("generic.assessment.average2text1"));
        parameters.put("Average2Text2",messages.getText("generic.assessment.average2text2"));
        parameters.put("ResultText",messages.getText("generic.assessment.psiresult"));
        parameters.put("Logo",AssesmentData.FLASH_PATH+"/images/logo"+String.valueOf(attributes.getCorporationId())+".jpg");
        parameters.put("LogoCEPA",AssesmentData.FLASH_PATH+"/images/logo-cepa.jpg");
        parameters.put("Background",AssesmentData.FLASH_PATH+"/images/13_1.jpg");
        parameters.put("Date",Util.dateToString(messages,Calendar.getInstance(),sys.getUserSessionData().getLenguage()));
        
        ReportUtil util = new ReportUtil();

        if(attributes.getCorporationId().intValue() == CorporationData.JJ) {
            parameters.put("ReferenceText",messages.getText("report.userresult.reference")+": "+getReference(userData,sys.getUserReportFacade(),sys.getUserSessionData()));
            if(dataSource.getIndexCount() == 0) {
                parameters.put("allOK","true");
            }
            parameters.put("FooterText1",messages.getText("assesment.report.footermessage1"));
            parameters.put("FooterText2",messages.getText("assesment.report.footermessage2"));
        }

        String report = "jasper/userPsiPDFJJ";
        if(AssesmentData.isJJ(attributes.getId().intValue())) {
            parameters.put("ReferenceText",messages.getText("report.userresult.reference")+": "+getReference(userData,sys.getUserReportFacade(),sys.getUserSessionData()));
            if(dataSource.getIndexCount() == 0) {
                parameters.put("allOK","true");
            }
            parameters.put("FooterText1",messages.getText("assesment.report.footermessage1"));
            parameters.put("FooterText2",messages.getText("assesment.report.footermessage2"));
        }

        FileInputStream input = new FileInputStream(QuestionResultAction.class.getResource(report+"_2.jasper").getFile());

        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(input);
        
        return util.executeReport(jasperReport,assesmentForm.getString("output"),parameters,dataSource,session,mapping,response,"usersresult");
   }

    private String getReference(UserData user, UsReportFacade userReport, UserSessionData userSessionData) throws Exception {
        Integer code = userReport.getUserCode(user.getLoginName(),userSessionData);
        switch(Integer.parseInt(user.getExtraData())) {
            case 4460:
                return "VK"+String.valueOf(3000+code.intValue());
            case 4461:
                return "CN"+String.valueOf(2000+code.intValue());
            case 4462:
                return "MD&D"+String.valueOf(1000+code.intValue());
            case 4463:
                return "Janse"+String.valueOf(4000+code.intValue());
        }
        return null;
    }
}