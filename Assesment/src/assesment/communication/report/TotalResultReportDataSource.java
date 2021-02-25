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

public class TotalResultReportDataSource implements JRDataSource {

    private int index = 0;
    private Text messages;
    
    private int red = 0;
    private int yellow = 0;
    private int green = 0;
    
    private int prom_red = 0;
    private int prom_yellow = 0;
    private int prom_green = 0;
    private int prom_total = 0;
    private int questionCount;
    
    public TotalResultReportDataSource(int red,int yellow,int green,int prom_red, int prom_yellow, int prom_green, int prom_total, int count, Text messages) {
        this.messages = messages;
        this.green = green;
        this.red = red;
        this.yellow = yellow;
        this.prom_green = prom_green;
        this.prom_yellow = prom_yellow;
        this.prom_red = prom_red;
        this.prom_total = prom_total;
        questionCount = count;
    }

    public boolean next() throws JRException {
        if(index < 3) {
            index++;
            return true;
        }
        return false;
    }

    public Object getFieldValue(JRField field) throws JRException {
        if(field.getName().equals("color")) {
            switch(index) {
                case 1:
                    return messages.getText("question.result.incorrect");
                case 2:
                    return messages.getText("question.result.half");
                case 3:
                    return messages.getText("question.result.correct");
            }
        }
        if(field.getName().equals("value")) {
            switch(index) {
                case 1:
                    return new Integer(red);
                case 2:
                    return new Integer(yellow);
                case 3:
                    return new Integer(green);
            }
        }
        if(field.getName().equals("red")) {
            return String.valueOf(red);
        }
        if(field.getName().equals("green")) {
            return String.valueOf(green);
        }
        if(field.getName().equals("yellow")) {
            return String.valueOf(yellow);
        }
        if(field.getName().equals("total")) {
            return String.valueOf(red+green+yellow);
        }
        return null;
    }

    public String getProm_green() {
        int value = prom_green * 100 / questionCount;
        return String.valueOf(value);
    }

    public String getProm_red() {
        int value = prom_red * 100 / questionCount;
        return String.valueOf(value);
    }

    public String getProm_total() {
        int value = prom_total * 100 / questionCount;
        return String.valueOf(value);
    }

    public String getProm_yellow() {
        int value = prom_yellow * 100 / questionCount;
        return String.valueOf(value);
    }

	public int getRed() {
		return red;
	}

	public int getYellow() {
		return yellow;
	}

	public int getGreen() {
		return green;
	}

    
}
