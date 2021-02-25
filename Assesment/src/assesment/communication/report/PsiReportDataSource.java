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

public class PsiReportDataSource implements JRDataSource {

    private int index = 0;
    private Text messages;
    private Integer[] values;
    private int[] indexes;
    private String errorMsg;
    
    public PsiReportDataSource(Integer[] values,Text messages) {
        this(values,messages,"psi.error.text");
    }

    public PsiReportDataSource(Integer[] values,Text messages, String errorMsg) {
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
        this.errorMsg = errorMsg;
    }

    public boolean next() throws JRException {
        if((index < indexes.length) || (indexes.length == 0 && index == 0)) {
            index++;
            return true;
        }
        return false;
    }

    public Object getFieldValue(JRField field) throws JRException {
        if(field.getName().equals("Title1")) {
        	if(indexes.length == 0) {
        		return "";
        	}
        	return messages.getText("psi.comment")+" "+index;
        }
        if(field.getName().equals("Text1")) {
        	if(indexes.length == 0) {
        		return "";
        	}
        	return messages.getText(errorMsg+indexes[index-1]);
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
