/**
 * Created on 24-sep-2007
 * CEPA
 * DataCenter 5
 */
package assesment.communication.report;

import java.awt.Color;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import assesment.communication.language.Text;

public class UserPsiReportDataSource implements JRDataSource {

    private int index = -1;
    private Text messages;
    private Integer[] values;
    private int[] indexes;
    
    public UserPsiReportDataSource(Integer[] values,Text messages) {
        this.messages = messages;
        this.values = values;
        int count = 0;
        for(int i = 0; i < values.length; i++) {
            if(values[i] >= 4) {
                count++;
            }
        }
        indexes = new int[count];
        int j = 0;
        for(int i = 0; i < values.length; i++) {
            if(values[i] >= 4) {
                indexes[j] = i+1;
                j++;
            }
        }
    }

    public boolean next() throws JRException {
        if(index < indexes.length) {
            index++;
            return true;
        }
        return false;
    }

    public Object getFieldValue(JRField field) throws JRException {
        if(field.getName().equals("Title1")) {
            if(index == 0){
                return messages.getText("psi.error.title0");
            }            
        }
        if(field.getName().equals("Title")) {
            if(index != 0){
                return messages.getText("psi.error.title"+indexes[index-1]);
            }
        }
        if(field.getName().equals("Text1")) {
            if(index == 0){
                return messages.getText("psi.error.text0");
            }            
        }
        if(field.getName().equals("Text")) {
            if(index != 0){
                return messages.getText("psi.error.text"+indexes[index-1]);
            }
        }
        if(field.getName().equals("LastPage")) {
            if(index == indexes.length){
                return "true";
            }
        }
        return null;
    }

    public Color getColor(int i) {
        if(((Integer)values[i]).intValue() < 3) {
            return new Color(14,181,97);
        }else if(((Integer)values[i]).intValue() < 4) {
            return new Color(239,247,21);
        }else {
            return new Color(245,19,19);
        }
    }

    public double getValue(int i) {
        return values[i].doubleValue();
    }

    public int getIndexCount() {
        return indexes.length;
    }
}
