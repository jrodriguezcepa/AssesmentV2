/**
 * Created on 24-sep-2007
 * CEPA
 * DataCenter 5
 */
package assesment.communication.report;

import java.util.Hashtable;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.language.Text;

public class QuestionRankingDataSource implements JRDataSource {

    private int index = -1;
    
    private Text messages;
    private Object[][] values;
    private Hashtable count;
    
    public QuestionRankingDataSource(Object[][] values,Hashtable count, Text messages) {
        this.messages = messages;
        this.values = values;
        this.count = count;
    }

    public boolean next() throws JRException {
        if(index+1 < values.length) {
            index++;
            return true;
        }
        return false;
    }

    public Object getFieldValue(JRField field) throws JRException {
        if(field.getName().equals("module")) {
            return String.valueOf(values[index][1]);
        }
        if(field.getName().equals("question")) {
            return messages.getText(String.valueOf(values[index][2]));
        }
        if(field.getName().equals("wrong")) {
            return ((Integer)values[index][3]);
        }
        if(field.getName().equals("promR")) {
            return String.valueOf(Math.round(((Integer)values[index][3]).doubleValue() * 100.0 / ((Integer)count.get(values[index][4])).doubleValue()));
        }
        return null;
    }

}
