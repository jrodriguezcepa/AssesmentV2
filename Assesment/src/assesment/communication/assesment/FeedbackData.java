/**
 * Created on 23-oct-2008
 * CEPA
 * DataCenter 5
 */
package assesment.communication.assesment;

import java.util.Collection;
import java.util.LinkedList;

public class FeedbackData {

    public static final int GENERAL_RESULT_REPORT = 1;
    public static final int PSI_RESULT_REPORT = 2;
    public static final int ERROR_RESULT_REPORT = 3;
    public static final int PERSONAL_DATA_REPORT = 4;
    
    private String email;
    private Collection reports;
    
    public FeedbackData() {
        reports = new LinkedList();
    }

    public FeedbackData(String email,Integer report) {
        this.email = email;
        reports = new LinkedList();
        reports.add(report);
    }

    public String getEmail() {
        return email;
    }

    public Collection getReports() {
        return reports;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setReports(Collection reports) {
        this.reports = reports;
    }

    public void addReport(FeedbackData data) {
        reports.addAll(data.getReports());
    }
}
