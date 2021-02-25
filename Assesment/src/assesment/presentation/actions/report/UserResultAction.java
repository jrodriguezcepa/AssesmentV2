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
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import assesment.business.AssesmentAccess;
import assesment.business.administration.user.UsReportFacade;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.language.Text;
import assesment.communication.module.ModuleData;
import assesment.communication.report.ErrorReportDataSource;
import assesment.communication.report.ModuleReportDataSource;
import assesment.communication.report.PsiReportDataSource;
import assesment.communication.report.TotalReportDataSource;
import assesment.communication.report.TotalUserResultReportDataSource;
import assesment.communication.report.UserResultReportDataSource;
import assesment.communication.user.UserData;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

public class UserResultAction extends AbstractAction {

    public ActionForward cancel(HttpSession session, ActionMapping mapping, ActionForm form) {
        return null;
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        
        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        UserSessionData userSessionData = sys.getUserSessionData();
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
        
        String[] reportData = sys.getAssesmentReportFacade().findReportData(new Integer(assesment),userSessionData);
        AssesmentData attributes = sys.getAssesmentReportFacade().findAssesment(new Integer(assesment),userSessionData);
        
        UsReportFacade usReport = sys.getUserReportFacade();
        UserData userData = usReport.findUserByPrimaryKey(user,userSessionData);
        HashMap map = usReport.findUsersResults(new Integer(assesment), user, userSessionData);
        
        //JasperCompileManager.compileReportToFile("C:/Proyectos JAVA/Assesment/src/assesment/presentation/actions/report/jrxml/ModuleSubreport.jrxml","C:/Proyectos JAVA/Assesment/src/assesment/presentation/actions/report/jasper/ModuleSubreport.jasper");
        FileInputStream input = new FileInputStream(QuestionResultAction.class.getResource("jasper/ModuleSubreport.jasper").getFile());
        JasperReport moduleSubreport = (JasperReport)JRLoader.loadObject(input);
        ModuleReportDataSource moduleDataSource = new ModuleReportDataSource(map.size());
        Iterator it = attributes.getModuleIterator();
        Integer correctas = new Integer(0);
        Integer incorrectas = new Integer(0);
        while(it.hasNext()) {
        	ModuleData module = (ModuleData)it.next();
        	if(map.containsKey(module.getId())) {
        		Object[] data = (Object[])map.get(module.getId());
        		incorrectas += (Integer)data[1];
        		correctas += (Integer)data[2];
        		moduleDataSource.addModule(messages.getText(module.getKey()), (Integer)data[2], (Integer)data[1]);
        	}
        }
        
        TotalReportDataSource totalDataSource = new TotalReportDataSource(correctas,incorrectas,messages);
        
        String[][] data = sys.getQuestionReportFacade().getWrongAnswers(new Integer(assesment),user,userSessionData);

        HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("logo_empresa", AssesmentData.FLASH_PATH+"/images/logo"+String.valueOf(attributes.getCorporationId())+".png");
		params.put("logo_cepa", AssesmentData.FLASH_PATH+"/images/logo_cepa.png");
		params.put("header", AssesmentData.FLASH_PATH+"/images/header.jpg");
		params.put("footer", AssesmentData.FLASH_PATH+"/images/footer.jpg");
		params.put("corporacion", messages.getText("generic.corporation") + ": " + attributes.getName());
		params.put("nombre", messages.getText("generic.assesment") + ": " + attributes.getName() );
		params.put("usuario", messages.getText("user.data.nickname") + ": " + userData.getFirstName() + " " + userData.getLastName());
		params.put("fecha", Util.dateToString(messages,Calendar.getInstance(),userData.getLanguage()));
		params.put("titulo_resultado", messages.getText("generic.user.results").toUpperCase());
		params.put("cantidad", messages.getText("question.resultreport.count").toUpperCase());
		params.put("porcentaje", messages.getText("report.userresult.percent").toUpperCase());
		params.put("correctas", messages.getText("report.userresult.right").toUpperCase());
		params.put("incorrectas", messages.getText("report.userresult.wrong").toUpperCase());
		params.put("cantidad_correctas", correctas);
		params.put("cantidad_incorrectas", incorrectas);
		params.put("porcentaje_correctas", totalDataSource.getPorcentaje_correctas());
		params.put("porcentaje_incorrectas", totalDataSource.getPorcentaje_correctas());
		params.put("subreport1", moduleDataSource);
		params.put("sub1", moduleSubreport);
		params.put("resultados_modulo",messages.getText("report.userresult.moduletitle").toUpperCase());
		params.put("total",messages.getText("report.users.total.count"));
		params.put("modulo",messages.getText("generic.module"));
		params.put("texto_rojo",messages.getText("user.report.redtext"));
		params.put("texto_verde",messages.getText("user.report.greentext"));
		int indexPSI = 3;
		if(data.length > 0) {
	        FileInputStream input2 = new FileInputStream(QuestionResultAction.class.getResource("jasper/ErrorSubreport.jasper").getFile());
	        JasperReport errorSubreport = (JasperReport)JRLoader.loadObject(input2);
	        ErrorReportDataSource errorDatasource = new ErrorReportDataSource(data,messages);
			params.put("errores",messages.getText("user.report.errortitle"));
			params.put("imagen_rojo", AssesmentData.FLASH_PATH+"/images/imagen_error.png");
			params.put("imagen_verde", AssesmentData.FLASH_PATH+"/images/imagen_correcto.png");
			params.put("subreport2", errorDatasource);
			params.put("sub2", errorSubreport);
		}else {
			indexPSI = 2;
		}
        if(attributes.isPsitest()) {
	        FileInputStream input3 = new FileInputStream(QuestionResultAction.class.getResource("jasper/PsiSubreport.jasper").getFile());
	        JasperReport psiSubreport = (JasperReport)JRLoader.loadObject(input3);
	        Integer[] values = sys.getUserReportFacade().getUserPsicoResult(user,new Integer(assesment),userSessionData);
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
	        PsiReportDataSource psiDatasource = new PsiReportDataSource(values,messages);
			params.put("psi1",new Double(average1));
			params.put("psi2",new Double(average2));
			params.put("psi_titulo",messages.getText("user.report.psititle"));
			params.put("psi_subtitulo1",messages.getText("generic.assessment.average1text1")+" "+messages.getText("generic.assessment.average1text2"));
			params.put("psi_subtitulo2",messages.getText("generic.assessment.average2text1")+" "+messages.getText("generic.assessment.average2text2"));
			params.put("psi_texto1",messages.getText("psi.error.title0"));
			params.put("psi_texto2",messages.getText("user.report.psitexto2"));
			params.put("psi_texto21",messages.getText("user.report.psitexto21"));
			params.put("psi_texto3",messages.getText("user.report.psitexto3"));
			params.put("psi_texto4",messages.getText("user.report.psitexto4"));
			params.put("psi_texto5",messages.getText("user.report.psitexto5"));
			params.put("psi_texto6",messages.getText("user.report.psitexto6"));
			params.put("psi_texto7",messages.getText("user.report.psitexto7"));
			params.put("psi_texto71",messages.getText("user.report.psitexto71"));
			params.put("sub"+indexPSI, psiSubreport);
			params.put("subreport"+indexPSI, psiDatasource);
        }
        
        UserResultReportDataSource moduleDS = sys.getUserReportFacade().findUsersResult(new Integer(assesment),user,sys.getUserSessionData(),messages);
        input = new FileInputStream(QuestionResultAction.class.getResource("jasper/userResultModule"+assesmentForm.getString("output")+".jasper").getFile());
        JasperReport subreport = (JasperReport)JRLoader.loadObject(input);

        TotalUserResultReportDataSource dataSource = new TotalUserResultReportDataSource(moduleDS.getRed(),moduleDS.getGreen(),messages);
        
        HashMap parameters = new HashMap();
        parameters.put("moduleDS",moduleDS);
        parameters.put("subreport",subreport);
        parameters.put("user",user);
        parameters.put("Title",messages.getText("report.userresult.title"));
        parameters.put("Module"," "+messages.getText("report.userresult.module"));
        parameters.put("ModuleTitle",messages.getText("report.userresult.moduletitle"));
        parameters.put("RightText"," "+messages.getText("report.userresult.right"));
        parameters.put("WrongText"," "+messages.getText("report.userresult.wrong"));
        parameters.put("RightC",dataSource.getRightAnswers());
        parameters.put("WrongC",dataSource.getWrongAnswers());
        parameters.put("RightP",dataSource.getRightPercent());
        parameters.put("WrongP",dataSource.getWrongPercent());
        parameters.put("Right"," "+messages.getText("report.userresult.right"));
        parameters.put("Wrong"," "+messages.getText("report.userresult.wrong"));
        parameters.put("Percent",messages.getText("report.userresult.percent"));
        parameters.put("Count",messages.getText("question.resultreport.count"));
        parameters.put("Total",messages.getText("report.generalresult.total"));
        parameters.put("UserText",messages.getText("report.userresult.user")+": "+userData.getFirstName()+" "+userData.getLastName());
        parameters.put("AssessmentText",messages.getText("generic.assesment")+": "+reportData[0]);
        parameters.put("CorporationText",messages.getText("generic.data.corporation")+": "+reportData[1]);
        parameters.put("Logo",AssesmentData.FLASH_PATH+"/images/logo"+String.valueOf(attributes.getCorporationId())+".jpg");
        parameters.put("LogoCEPA",AssesmentData.FLASH_PATH+"/images/logo-cepa.jpg");
        parameters.put("Date",Util.dateToString(messages,Calendar.getInstance(),sys.getUserSessionData().getLenguage()));
        if(assesmentForm.getString("output").equals("HTML") && sys.getUserReportFacade().isPsicologicDone(user,new Integer(assesment),sys.getUserSessionData())) {
            parameters.put("PsiResultText",messages.getText("report.userresult.viewpsiresult"));
            parameters.put("PsiResultLink","layout.jsp?refer=report/userpsiresultreport.jsp?user="+user+"&assesment="+String.valueOf(assesment));
        }
        if(assesmentForm.getString("output").equals("HTML") && sys.getUserReportFacade().isPersonalDataDone(user,new Integer(assesment),sys.getUserSessionData())) {
            parameters.put("PersonalDataText",messages.getText("report.userresult.viewpersonaldata"));
            parameters.put("PersonalDataLink","layout.jsp?refer=report/userpersonaldataresultreport.jsp?user="+user+"&assesment="+String.valueOf(assesment));
        }
      
        ReportUtil util = new ReportUtil();
        JasperCompileManager.compileReportToFile("C:/Proyectos JAVA/Assesment/src/assesment/presentation/actions/report/jrxml/TotalReport.jrxml","C:/Proyectos JAVA/Assesment/src/assesment/presentation/actions/report/jasper/TotalReport.jasper");
        FileInputStream input2 = new FileInputStream(QuestionResultAction.class.getResource("jasper/TotalReport.jasper").getFile());
        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(input2);
        return util.executeReport(jasperReport,assesmentForm.getString("output"),params,totalDataSource,session,mapping,response,"Report");
   }
}