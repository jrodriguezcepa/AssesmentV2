package assesment.communication.report;


import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import assesment.communication.language.Text;

public class LogisticaReportDataSource implements JRDataSource {

    private String[][] answers;
    private int index = -1;
	private Text messages;
    
    public LogisticaReportDataSource(String[][] answers, Text messages) {
        this.answers = answers;
        this.messages = messages;
    }

    public boolean next() throws JRException {
        if(index < answers.length-1) {
        	index++;
        	return true;
        }
        return false;
    }

    public Object getFieldValue(JRField field) throws JRException {
    	if(field.getName().equals("pregunta")) {
    		return messages.getText(answers[index][0]);
    	}
    	if(field.getName().equals("respuesta")) {
    		return messages.getText(answers[index][1]);
    	}
    	return null;
    }

}
