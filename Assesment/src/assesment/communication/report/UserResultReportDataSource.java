/**
 * Created on 24-sep-2007
 * CEPA
 * DataCenter 5
 */
package assesment.communication.report;

import java.util.Enumeration;
import java.util.Hashtable;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import assesment.communication.language.Text;

public class UserResultReportDataSource implements JRDataSource {

    private int index = -1;
    private Hashtable hash;
    private Integer[] modules;
    private Text messages;
    
    private int all = 0;
    private int part = 0;
    private int none = 0;
    
    
    public UserResultReportDataSource(Hashtable hash, Text messages) {
        this.messages = messages;
        modules = new Integer[hash.size()];
        Enumeration keys = hash.keys();
        int i = 0;
        while(keys.hasMoreElements()) {
            modules[i] = (Integer)keys.nextElement();
            i++;
        }
        this.hash = hash;
    }

    public boolean next() throws JRException {
        if(index+1 < modules.length) {
            index++;
            return true;
        }
        return false;
    }

    public Object getFieldValue(JRField field) throws JRException {
        if(field.getName().equals("city")) {
            return " "+String.valueOf(index+1)+" "+messages.getText((String)((Object[])hash.get(modules[index]))[0]);
        }
        if(field.getName().equals("index")) {
            return String.valueOf(index+1);
        }
        if(field.getName().equals("model1")) {
            return messages.getText("question.result.correct");
        }
        if(field.getName().equals("model2")) {
            return messages.getText("question.result.incorrect");
        }
        if(field.getName().equals("total")) {
            return new Integer(((Integer)((Object[])hash.get(modules[index]))[1]).intValue() + ((Integer)((Object[])hash.get(modules[index]))[2]).intValue());
        }
        if(field.getName().equals("red")) {
            return ((Object[])hash.get(modules[index]))[1];
        }
        if(field.getName().equals("green")) {
            return ((Object[])hash.get(modules[index]))[2];
        }
        if(field.getName().equals("percent")) {
            double red = ((Integer)((Object[])hash.get(modules[index]))[1]).doubleValue();
            double green = ((Integer)((Object[])hash.get(modules[index]))[2]).doubleValue();
            double value = (double)green / (double)(green + red);
            return new Integer((int)Math.round(value * 100.0));
        }
        if(field.getName().equals("percentW")) {
            double red = ((Integer)((Object[])hash.get(modules[index]))[1]).doubleValue();
            double green = ((Integer)((Object[])hash.get(modules[index]))[2]).doubleValue();
            double value = (double)green / (double)(green + red);
            return new Integer((int)(100 - Math.round(value * 100.0)));
        }
        return null;
    }

    public int getAll() {
        return all;
    }

    public int getNone() {
        return none;
    }

    public int getPart() {
        return part;
    }

    public int getTotal() {
        return modules.length;
    }

    public int getRed() {
        int red = 0;
        for(int i = 0; i < modules.length; i++) {
            red += ((Integer)((Object[])hash.get(modules[i]))[1]).intValue();
        }
        return red;
    }

    public int getGreen() {
        int green = 0;
        for(int i = 0; i < modules.length; i++) {
            green += ((Integer)((Object[])hash.get(modules[i]))[2]).intValue();
        }
        return green;
    }
}
