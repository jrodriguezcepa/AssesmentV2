/*
 * Created on 17-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.assesment;

import java.util.Calendar;
import java.util.Date;

import org.apache.struts.action.ActionForm;

import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.assesment.FeedbackData;
import assesment.presentation.translator.web.util.Util;

public class FeedbackForm extends ActionForm {

    private static final long serialVersionUID = 1L;

    private String assessment;
    private String email;
    private String report1;
    private String report2;
    private String report3;
    private String report4;

    public FeedbackForm() {	    
	}
	
	public FeedbackForm(String assessment,FeedbackData feedback) {
	    this.assessment = assessment;
        this.email = feedback.getEmail();
        report1 = String.valueOf(feedback.getReports().contains(new Integer(FeedbackData.GENERAL_RESULT_REPORT)));
        report2 = String.valueOf(feedback.getReports().contains(new Integer(FeedbackData.PSI_RESULT_REPORT)));
        report3 = String.valueOf(feedback.getReports().contains(new Integer(FeedbackData.ERROR_RESULT_REPORT)));
        report4 = String.valueOf(feedback.getReports().contains(new Integer(FeedbackData.PERSONAL_DATA_REPORT)));
	}

    public String getAssessment() {
        return assessment;
    }

    public String getEmail() {
        return email;
    }

    public String getReport1() {
        return report1;
    }

    public String getReport2() {
        return report2;
    }

    public String getReport3() {
        return report3;
    }

    public String getReport4() {
        return report4;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setReport1(String report1) {
        this.report1 = report1;
    }

    public void setReport2(String report2) {
        this.report2 = report2;
    }

    public void setReport3(String report3) {
        this.report3 = report3;
    }

    public void setReport4(String report4) {
        this.report4 = report4;
    }


}