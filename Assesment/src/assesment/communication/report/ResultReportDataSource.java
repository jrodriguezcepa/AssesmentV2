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

public class ResultReportDataSource implements JRDataSource {

    private int index = -1;
    
    private Hashtable hash;
    private Hashtable moduleCount;

    private Object[] modules;
    private Text messages;
    
    private int all = 0;
    private int part = 0;
    private int none = 0;

    private Integer assesment;
    
    public ResultReportDataSource(Hashtable hash, Hashtable moduleCount, Text messages,Integer assesment) {
        this.messages = messages;
        modules = new Object[hash.size()];
        Enumeration keys = hash.keys();
        int i = 0;
        while(keys.hasMoreElements()) {
            modules[i] = keys.nextElement();
            i++;
        }
        this.hash = hash;
        this.moduleCount = moduleCount;
        this.assesment = assesment;
    }

    public boolean next() throws JRException {
        if(index+1 < modules.length) {
            index++;
            return true;
        }
        return false;
    }

    public Object getFieldValue(JRField field) throws JRException {
        if(field.getName().equals("module_id")) {
            return String.valueOf(index+1);
        }
        if(field.getName().equals("module")) {
            return " "+String.valueOf(index+1)+" "+messages.getText(String.valueOf(((Object[])hash.get(modules[index]))[3]));
        }
        if(field.getName().equals("value1")) {
            return messages.getText("report.generalresult.highlevel");
        }
        if(field.getName().equals("value2")) {
            return messages.getText("report.generalresult.meddiumlevel");
        }
        if(field.getName().equals("value3")) {
            return messages.getText("report.generalresult.lowlevel");
        }
        if(field.getName().equals("redA")) {
            if(moduleCount.get(modules[index]) == null) {
                return "--";
            }
            Object[] data = (Object[])hash.get(modules[index]);
            double value = ((Integer)data[6]).doubleValue();
            value = value / ((Integer)data[0]).doubleValue();
            value = value * 100 / ((Integer)moduleCount.get(modules[index])).doubleValue();
            return String.valueOf(Math.round(value));
        }
        if(field.getName().equals("redC")) {
            return ((Object[])hash.get(modules[index]))[0];
        }
        if(field.getName().equals("greenA")) {
            if(moduleCount.get(modules[index]) == null) {
                return "--";
            }
            Object[] data = (Object[])hash.get(modules[index]);
            double value = ((Integer)data[8]).doubleValue();
            value = value / ((Integer)data[2]).doubleValue();
            value = value * 100 / ((Integer)moduleCount.get(modules[index])).doubleValue();
            return String.valueOf(Math.round(value));
        }
        if(field.getName().equals("greenC")) {
            return ((Object[])hash.get(modules[index]))[2];
        }
        if(field.getName().equals("yellowA")) {
            if(moduleCount.get(modules[index]) == null) {
                return "--";
            }
            Object[] data = (Object[])hash.get(modules[index]);
            double value = ((Integer)data[7]).doubleValue();
            value = value / ((Integer)data[1]).doubleValue();
            value = value * 100 / ((Integer)moduleCount.get(modules[index])).doubleValue();
            return String.valueOf(Math.round(value));
        }
        if(field.getName().equals("yellowC")) {
            return ((Object[])hash.get(modules[index]))[1];
        }
        if(field.getName().equals("prom")) {
            if(((Integer)modules[index]).intValue() == 0) {
                return "---";
            }
            int total = (Integer)((Object[])hash.get(modules[index]))[0] + (Integer)((Object[])hash.get(modules[index]))[1] + (Integer)((Object[])hash.get(modules[index]))[2];
            return String.valueOf(((Integer)((Object[])hash.get(modules[index]))[4]) / total) +" / "+String.valueOf(moduleCount.get(modules[index]));
        }
        if(field.getName().equals("link")) {
            Integer id = (Integer)((Object[])hash.get(modules[index]))[5];
            if(id == 0) {
                return "";
            }
            return "layout.jsp?refer=report/moduleresultreport.jsp?module="+String.valueOf(id)+"&assesment="+String.valueOf(assesment);
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


}
