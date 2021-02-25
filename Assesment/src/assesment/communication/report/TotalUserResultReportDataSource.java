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

public class TotalUserResultReportDataSource implements JRDataSource {

    private int index = 0;
    private Text messages;
    
    private int red = 0;
    private int green = 0;
    
    public TotalUserResultReportDataSource(int red,int green,Text messages) {
        this.messages = messages;
        this.green = green;
        this.red = red;
    }

    public boolean next() throws JRException {
        if(index < 2) {
            index++;
            return true;
        }
        return false;
    }

    public Object getFieldValue(JRField field) throws JRException {
        if(field.getName().equals("color")) {
            switch(index) {
                case 1:
                    return messages.getText("question.result.correct")+" "+getRightPercent()+"%";
                case 2:
                    return messages.getText("question.result.incorrect")+" "+getWrongPercent()+"%";
            }
        }
        if(field.getName().equals("value")) {
            switch(index) {
                case 1:
                    return new Integer(green);
                case 2:
                    return new Integer(red);
            }
        }
        if(field.getName().equals("red")) {
            return String.valueOf(red);
        }
        if(field.getName().equals("green")) {
            return String.valueOf(green);
        }
        if(field.getName().equals("total")) {
            return String.valueOf(red+green);
        }
        return null;
    }

    public String getRightAnswers() {
        return String.valueOf(green);
    }

    public String getWrongAnswers() {
        return String.valueOf(red);
    }

    public String getRightPercent() {
        double value = (double)green / (double)(green + red);
        return String.valueOf(Math.round(value * 100.0));
    }

    public String getWrongPercent() {
        double value = (double)green / (double)(green + red);
        return String.valueOf(100 - Math.round(value * 100.0));
    }
}
