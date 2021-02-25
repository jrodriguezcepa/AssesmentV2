package assesment.communication.report;


import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class ModuleReportDataSource implements JRDataSource {

    private Object[][] values;
    private int index = 0;
    private int full;
    
    public ModuleReportDataSource(int modules) {
    	values = new Object[modules][3];
    }
    
    public void addModule(String module, Integer correctas, Integer incorrectas) {
    	values[full][0] = module;
    	values[full][1] = incorrectas;
    	values[full][2] = correctas;
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
    	if(field.getName().equals("numero_modulo")) {
    		return String.valueOf(index);
    	}
    	if(field.getName().equals("modulo")) {
    		return values[index-1][0];
    	}
    	if(field.getName().equals("total")) {
    		return ((Integer)values[index-1][1]) + ((Integer)values[index-1][2]);
    	}
    	if(field.getName().equals("incorrectas")) {
    		return values[index-1][1];
    	}
    	if(field.getName().equals("correctas")) {
    		return values[index-1][2];
    	}
    	if(field.getName().equals("porcentaje")) {
    		return Math.round(((Integer)values[index-1][2]) * 100 / (((Integer)values[index-1][1]) + ((Integer)values[index-1][2])));
    	}
    	if(field.getName().equals("porcentaje_in")) {
    		return new Integer(100 - Math.round(((Integer)values[index-1][2]) * 100 / (((Integer)values[index-1][1]) + ((Integer)values[index-1][2]))));
    	}
    	if(field.getName().equals("porcentaje_total")) {
    		return new Integer(100);
    	}
    	if(field.getName().equals("color")) {
    		return "#F4F4F4";
    	}
    	return null;
    }


}
