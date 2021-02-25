package assesment.presentation.actions.report;


import java.util.Collection;
import java.util.HashMap;
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
import assesment.communication.module.ModuleData;
import assesment.communication.question.AnswerData;
import assesment.communication.question.QuestionData;
import assesment.communication.report.AssessmentReportData;
import assesment.communication.report.UserReportData;
import assesment.presentation.translator.web.util.AbstractAction;

public class DownloadUserReportAction  extends AbstractAction {

    public ActionForward cancel(HttpSession session, ActionMapping mapping, ActionForm form) {
        return null;
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
		try {
	        HttpSession session = request.getSession();
	    	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	        Text messages = sys.getText();
	    	AssessmentReportData dataSource = (AssessmentReportData)sys.getValue();
	        AssesmentData assesment = dataSource.getAssessment();
	        
	        response.setHeader("Content-Type", "application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "inline; filename="+messages.getText(assesment.getName())+".xls");
	        
	        WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
	        WritableSheet s = w.createSheet(messages.getText(assesment.getName()), 0);

	        s.addCell(new Label(0,0,messages.getText("user.data.nickname")));
	        s.addCell(new Label(1,0,messages.getText("user.data.firstname")));
	        s.addCell(new Label(2,0,messages.getText("user.data.lastname")));
	        s.addCell(new Label(3,0,messages.getText("user.data.mail")));
	        s.addCell(new Label(4,0,messages.getText("module.resultreport.right")));
	        s.addCell(new Label(5,0,messages.getText("module.resultreport.wrong")));
	        s.addCell(new Label(6,0,messages.getText("generic.report.level")));
			if(assesment.isPsitest()) {
				s.addCell(new Label(7,0,messages.getText("assessment.psi")));
				s.addCell(new Label(8,0,messages.getText("generic.report.ending")));
			}else {
				s.addCell(new Label(7,0,messages.getText("generic.report.ending")));
			}
			
			Collection totalUsers = dataSource.getTotalUsers();
			Iterator<UserReportData> itUser = totalUsers.iterator();
			int index = 1;
			while(itUser.hasNext()) {
				UserReportData userReportData = itUser.next();
				s.addCell(new Label(0,index,userReportData.getLogin()));
				s.addCell(new Label(1,index,userReportData.getFirstName()));
				s.addCell(new Label(2,index,userReportData.getLastName()));
				s.addCell(new Label(3,index,userReportData.getEmail()));
				s.addCell(new Label(4,index,String.valueOf(userReportData.getCorrect())));
				s.addCell(new Label(5,index,String.valueOf(userReportData.getIncorrect())));
		        s.addCell(new Label(6,index,userReportData.getLevel(messages)));
				if(assesment.isPsitest()) {
					s.addCell(new Label(7,index,userReportData.getLevelPsi(messages)));
					s.addCell(new Label(8,index,userReportData.getEndDateStr()));
				}else {
					s.addCell(new Label(7,index,userReportData.getEndDateStr()));
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