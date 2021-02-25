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

public class QuestionModuleResultDataSource implements JRDataSource {

    private int index = -1;
    
    private Text messages;
    private Object[][] values;
        
    public QuestionModuleResultDataSource(Object[][] values,Text messages) {
        this.messages = messages;
        this.values = values;
    }

    public boolean next() throws JRException {
        if(index+1 < values.length) {
            index++;
            return true;
        }
        return false;
    }

    public Object getFieldValue(JRField field) throws JRException {
        if(field.getName().equals("question")) {
            return " "+messages.getText(String.valueOf(values[index][1]));
        }
        if(field.getName().equals("answer")) {
            return " "+messages.getText(String.valueOf(values[index][3]));
        }
        if(field.getName().equals("count")) {
            return (Integer)values[index][4];
        }
        return null;
    }
}
