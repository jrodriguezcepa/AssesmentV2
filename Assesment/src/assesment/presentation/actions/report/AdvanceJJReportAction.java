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
import assesment.communication.report.AdvanceJJDataSource;
import assesment.communication.report.TotalResultReportDataSource;
import assesment.communication.report.UsersReportDataSource;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

public class AdvanceJJReportAction extends AbstractAction {

    public ActionForward cancel(HttpSession session, ActionMapping mapping, ActionForm form) {
        return null;
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        
        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        Text messages = sys.getText();
        
        DynaActionForm advanceForm = (DynaActionForm)form;
        
        String type = advanceForm.getString("type");
        String champion = advanceForm.getString("champion");
        
        String countries = null;
        Integer company = null; 
        if(!type.equals("all")) {
            if(Util.empty(champion)) {
                session.setAttribute("Msg",messages.getText("generic.error.selectchampion"));
                return mapping.findForward("back");
            }
            if(champion.equals("dsanto18@its.jnj.com")) {
                company = new Integer(4460);
            }
            if(champion.equals("SRomero@its.jnj.com")) {
                company = new Integer(4461);
                countries = "31,33,34,54";
            }
            if(champion.equals("SRolon@its.jnj.com")) {
                company = new Integer(4461);
                countries = "57,66,69";
            }
            if(champion.equals("Jtota@its.jnj.com")) {
                company = new Integer(4461);
                countries = "32";
            }
            if(champion.equals("jaltaful@its.jnj.com")) {
                company = new Integer(4461);
                countries = "56,67,59,61,63";
            }
            if(champion.equals("JCRUZ81@its.jnj.com")) {
                company = new Integer(4461);
                countries = "55,37";
            }
            if(champion.equals("AOWEN2@its.jnj.com")) {
                company = new Integer(4461);
                countries = "42";
            }
            if(champion.equals("LCardo@its.jnj.com")) {
                company = new Integer(4461);
                countries = "64";
            }
            if(champion.equals("MLozano2@JNJVE.JNJ.com")) {
                company = new Integer(4461);
                countries = "39";
            }
            if(champion.equals("gmarrese@its.jnj.com")) {
                company = new Integer(4462);
                countries = "31,64,54";
            }
            if(champion.equals("SCALELLO@its.jnj.com")) {
                company = new Integer(4462);
                countries = "33";
            }
            if(champion.equals("AMANZANO@its.jnj.com")) {
                company = new Integer(4462);
                countries = "32";
            }
            if(champion.equals("GAGUILER@its.jnj.com")) {
                company = new Integer(4462);
                countries = "55";
            }
            if(champion.equals("LCISNER2@its.jnj.com")) {
                company = new Integer(4462);
                countries = "42,57,66,69,56,67,59,61,62,63,85,170";
            }
            if(champion.equals("lafrabot@its.jnj.com")) {
                company = new Integer(4462);
                countries = "39";
            }

            if(champion.equals("ACASTRO@its.jnj.com")) {
                company = new Integer(4463);
                countries = "31,33,54";
            }
            if(champion.equals("jyung1@its.jnj.com")) {
                company = new Integer(4463);
                countries = "37,39,55,64";
            }
            if(champion.equals("MBENITE1@its.jnj.com")) {
                company = new Integer(4463);
                countries = "42";
            }
            if(champion.equals("famartin@its.jnj.com")) {
                company = new Integer(4463);
                countries = "85,57,66";
            }
            if(champion.equals("JFERNAN7@its.jnj.com")) {
                company = new Integer(4463);
                countries = "32";
            }
        }

        AssesmentAttributes assesmentData = sys.getAssesmentReportFacade().findAssesmentAttributes(new Integer(AssesmentData.JJ),sys.getUserSessionData());
        Integer questionCount = sys.getAssesmentReportFacade().getAssesmentsQuestionCount(assesmentData,sys.getUserSessionData());
        if(assesmentData.isPsitest()) {
            questionCount -= 48;
        }
        AdvanceJJDataSource dataSource = new AdvanceJJDataSource(sys.getUserReportFacade().getChampionResult(questionCount,countries,company,sys.getUserSessionData()),questionCount,assesmentData.getGreen(),assesmentData.getYellow(),messages);
        
        String[] reportData = sys.getAssesmentReportFacade().findReportData(new Integer(AssesmentData.JJ),sys.getUserSessionData());
        
        HashMap parameters = new HashMap();
        parameters.put("Title",messages.getText("report.users.title"));
        parameters.put("AssessmentText",messages.getText("generic.assesment")+": "+reportData[0]);
        parameters.put("CorporationText",messages.getText("generic.data.corporation")+": "+reportData[1]);
        parameters.put("ChampionText",getChampionName(champion));
        parameters.put("Logo",AssesmentData.FLASH_PATH+"/images/logo"+String.valueOf(assesmentData.getCorporationId())+".jpg");
        parameters.put("Date",Util.dateToString(messages,Calendar.getInstance(),sys.getUserSessionData().getLenguage()));
        parameters.put("LogoCEPA",AssesmentData.FLASH_PATH+"/images/logo-cepa.jpg");
        parameters.put("user",messages.getText("report.users.name"));
        parameters.put("reference",messages.getText("report.userresult.reference"));
        parameters.put("percent","%");
        parameters.put("psi",messages.getText("report.advancejj.psi"));
        parameters.put("result",messages.getText("report.advancejj.result"));
        
        String reportName = "jasper/driverListJJ2.jasper";
        //String reportName = "jasper/prueba.jasper";
        ReportUtil util = new ReportUtil();
        return util.executeReport(reportName,"PDF",parameters,dataSource,session,mapping,response,"users");
   }

    private Object getChampionName(String champion) {
        return null;
    }
}