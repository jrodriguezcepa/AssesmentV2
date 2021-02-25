/**
 * Created on 24-sep-2007
 * CEPA
 * DataCenter 5
 */
package assesment.communication.report;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import assesment.communication.language.Text;

public class UserWrongAnswerReportDataSource implements JRDataSource {

    private int index = -1;
    private String[][] data;
    private Text messages;
    
    public UserWrongAnswerReportDataSource(String[][] data, Text messages) {
        this.messages = messages;
        this.data = data;
    }

    public boolean next() throws JRException {
        if(index+1 < data.length) {
            index++;
            return true;
        }
        return false;
    }

    public Object getFieldValue(JRField field) throws JRException {
        if(field.getName().equals("Module")) {
            return messages.getText(data[index][0]);
        }
        if(field.getName().equals("Question")) {
            return messages.getText(data[index][1]);
        }
        if(field.getName().equals("Answered")) {
            return messages.getText(data[index][2]);
        }
        if(field.getName().equals("Correct")) {
            return messages.getText(data[index][3]);
        }
        return null;
    }

    public int getTotal() {
        return data.length;
    }
}
