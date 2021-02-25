package assesment.presentation.actions.report;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import assesment.business.AssesmentAccess;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.language.Text;
import assesment.communication.question.AnswerData;
import assesment.communication.question.QuestionData;
import assesment.communication.report.AssessmentReportData;
import assesment.communication.report.UserMutualReportData;
import assesment.communication.report.UserReportData;
import assesment.presentation.translator.web.util.AbstractAction;

public class DownloadMutualReportAction  extends AbstractAction {

    public ActionForward cancel(HttpSession session, ActionMapping mapping, ActionForm form) {
        return null;
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
		try {
	        HttpSession session = request.getSession();
	    	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	        Text messages = sys.getText();
			Collection r = sys.getAssesmentReportFacade().findMutualAssesmentResults(null, sys.getUserSessionData());

	    	
	        response.setHeader("Content-Type", "application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "inline; filename=ReporteMutualSeguridad.xls");
	        
	        WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
	        WritableSheet s = w.createSheet("Mutual de Seguridad", 0);

	        s.addCell(new Label(0,0,messages.getText("user.data.firstname")));
	        s.addCell(new Label(1,0,messages.getText("user.data.lastname")));
	        s.addCell(new Label(2,0,messages.getText("user.data.mail")));
	        s.addCell(new Label(3,0,messages.getText("generic.data.username")));
	        s.addCell(new Label(4,0,messages.getText("assesment1613.module4354.name")));
	        s.addCell(new Label(5,0,messages.getText("assesment1613.module4356.name")));
	        s.addCell(new Label(6,0,messages.getText("assesment1613.module4355.name")));
	        s.addCell(new Label(7,0,messages.getText("assesment1613.module4357.name")));
	        s.addCell(new Label(8,0,messages.getText("assessment.psi")));
	        s.addCell(new Label(9,0,messages.getText("generic.data.ranking")));
	        s.addCell(new Label(10,0,messages.getText("assesment1613.module4354.name")+messages.getText("generic.data.recommendation")));
	        s.addCell(new Label(11,0,messages.getText("assesment1613.module4356.name")+messages.getText("generic.data.recommendation")));
	        s.addCell(new Label(12,0,messages.getText("assesment1613.module4355.name")+messages.getText("generic.data.recommendation")));
	        s.addCell(new Label(13,0,messages.getText("assesment1613.module4357.name")+messages.getText("generic.data.recommendation")));
			int length = 13;

	    	Iterator it = r.iterator();

			int index = 1;
			while(it.hasNext()) {
				UserMutualReportData result = (UserMutualReportData)it.next();
				for(int i = 0; i <= length; i++) {
					s.addCell(new Label(i,index, result.getValue(i,messages)));
				}
				index++;
			}
			w.write();
			w.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
}