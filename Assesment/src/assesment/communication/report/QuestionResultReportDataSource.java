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

public class QuestionResultReportDataSource implements JRDataSource {

    private int index = -1;
    private Text messages;
    private Object[][] values;
    private String questionText;
    private int questionSize;
    
    public QuestionResultReportDataSource(Object[][] values,String questionText,int questionSize,Text messages) {
        this.messages = messages;
        this.values = values;
        this.questionText = questionText;
        this.questionSize = questionSize;
    }

    public boolean next() throws JRException {
        if(index+1 < values.length) {
            index++;
            return true;
        }
        return false;
    }

    public Object getFieldValue(JRField field) throws JRException {
        if(field.getName().equals("Answer")) {
            return " "+messages.getText(String.valueOf(values[index][1]));
        }
        if(field.getName().equals("Count")) {
            return (Integer)values[index][2];
        }
        return null;
    }

    public String getQuestion() {
        return questionText;
    }

    public int getQuestionSize() {
        return questionSize;
    }

}
