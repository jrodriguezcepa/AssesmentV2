package assesment.communication.report;


import java.util.HashMap;

import assesment.communication.language.Text;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class ErrorReportDataSourceBKP implements JRDataSource {

    private String[][] values;
    private int index = 0;
    private int full;
	private HashMap messages;
    
    public ErrorReportDataSourceBKP(String[][] values, HashMap messages) {
    	this.values = values;
    	full = values.length;
    	this.messages = messages;
    }
    
    public ErrorReportDataSourceBKP(int modules) {
    	values = new String[modules][4];
    }

    public void addQuestion(String modulo, String pregunta, String correcta, String incorrecta) {
    	values[full][0] = modulo;
    	values[full][1] = pregunta;
    	values[full][2] = incorrecta;
    	values[full][3] = correcta;
    	full++;
    }

    public boolean next() throws JRException {
        if(index < full) {
        	index++;
        	return true;
        }
        return false;
    }

    public Object getFieldValue(JRField field) throws JRException {
    	if(field.getName().equals("modulo")) {
    		return messages.get(values[index-1][0]);
    	}
    	if(field.getName().equals("pregunta")) {
    		return messages.get(values[index-1][1]);
    	}
    	if(field.getName().equals("error")) {
    		String txt = (String)messages.get(values[index-1][2]);
    		if(txt.length() > 150)
    			return null;
    		return messages.get(values[index-1][2]);
    	}
    	if(field.getName().equals("errorL")) {
    		return messages.get(values[index-1][2]);
    	}
    	if(field.getName().equals("correcta")) {
    		String txt =(String) messages.get(values[index-1][3]);
    		if(txt.length() > 150)
    			return null;
    		return messages.get(values[index-1][3]);
    	}
    	if(field.getName().equals("correctaL")) {
    		return messages.get(values[index-1][3]);
    	}
    	return null;
    }


}
