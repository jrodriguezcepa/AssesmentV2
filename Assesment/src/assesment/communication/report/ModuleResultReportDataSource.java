/**
 * Created on 24-sep-2007
 * CEPA
 * DataCenter 5
 */
package assesment.communication.report;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.language.Text;

public class ModuleResultReportDataSource implements JRDataSource {

    private int index = -1;
    
    private Text messages;
    private Object[][] values;
    
    private String moduleName;
    private AssesmentAttributes assesment;
    private boolean first = true;
    private boolean empty = false;
    
    public ModuleResultReportDataSource(Object[][] values,Text messages,String moduleName,AssesmentAttributes assesment) {
        this.messages = messages;
        this.values = values;
        this.moduleName = moduleName;
        this.assesment = assesment;
    }

    public boolean next() throws JRException {
        if(index+1 < values.length) {
            index++;
            first = false;
            return true;
        }
        if(first) {
            empty = true;
            first = false;
            return true;
        }
        return false;
    }

    public Object getFieldValue(JRField field) throws JRException {
        if(field.getName().equals("question")) {
            if(empty) {
                return "";
            }
            return " "+String.valueOf(index+1)+" "+messages.getText(String.valueOf(values[index][0]));
        }
        if(field.getName().equals("questionId")) {
            return String.valueOf(index+1);
        }
        if(field.getName().equals("right")) {
            if(empty) {
                return new Integer(0);
            }
            return (Integer)values[index][1];
        }
        if(field.getName().equals("wrong")) {
            if(empty) {
                return new Integer(0);
            }
            return (Integer)values[index][2];
        }
        if(field.getName().equals("result1")) {
            return messages.getText("question.result.correct");
        }
        if(field.getName().equals("result2")) {
            return messages.getText("question.result.incorrect");
        }
        if(field.getName().equals("link")) {
            if(empty) {
                return "";
            }
            return "layout.jsp?refer=report/qresultreport.jsp?question="+String.valueOf(values[index][3]);
        }
        if(field.getName().equals("promR")) {
            if(empty) {
                return "0";
            }
            double value = ((Integer)values[index][1]).doubleValue() / (((Integer)values[index][1]).doubleValue() + ((Integer)values[index][2]).doubleValue());
            return String.valueOf(100 - Math.round(value * 100));
        }
        if(field.getName().equals("promY")) {
            return null;
        }
        if(field.getName().equals("promG")) {
            if(empty) {
                return null;
            }
            double value = ((Integer)values[index][1]).doubleValue() / (((Integer)values[index][1]).doubleValue() + ((Integer)values[index][2]).doubleValue());
            return String.valueOf(Math.round(value * 100));
        }
        return null;
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getAssesmentName() {
        return assesment.getName();
    }
}
