/**
 * Created on 24-sep-2007
 * CEPA
 * DataCenter 5
 */
package assesment.communication.report;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import assesment.communication.assesment.ResultLine;
import assesment.communication.assesment.ResultLineJJ;
import assesment.communication.language.Text;

public class CompleteUserReportDataSource implements JRDataSource {

    private int index = -1;
    private Text messages;
    private ResultLine[] lines;
    private int questionCount;
    
    public CompleteUserReportDataSource(Collection results,int questionCount,Text messages) {
        this.messages = messages;
        this.questionCount = questionCount;
        Collections.sort((List)results);
        Iterator it = results.iterator();
        int i = 0;
        lines = new ResultLine[results.size()];
        while(it.hasNext()) {
        	lines[i] = (ResultLine)it.next();
        	i++;
        }
    }

    public boolean next() throws JRException {
        if(index < lines.length-1) {
            index++;
            return true;
        }
        return false;
    }
    
    public Object getFieldValue(JRField field) throws JRException {
        if(field.getName().equals("superviser")) {
        	return ((ResultLineJJ)lines[index]).getSupervisor();
        }
        if(field.getName().equals("name")) {
        	return lines[index].getFirstName();
        }
        if(field.getName().equals("last")) {
        	return lines[index].getLastName();
        }
        if(field.getName().equals("country")) {
        	return ((ResultLineJJ)lines[index]).getCountryName();
        }
        if(field.getName().equals("op_group")) {
        	return ((ResultLineJJ)lines[index]).getOp_group();
        }
        if(field.getName().equals("psi")) {
        	if(lines[index].getPsi().intValue() == 0) {
        		return messages.getText("assessment.psi.didnot");
        	}else {
        		return String.valueOf(lines[index].getPsi());
        	}
        }
        if(field.getName().equals("quiz")) {
        	if(lines[index].getTotal().intValue() == 0) {
        		return messages.getText("assessment.psi.didnot");
        	}else if(lines[index].getTotal().intValue() < questionCount) {
        		return messages.getText("assessment.test.incomplete");
        	}else {
    			double value = new Double(String.valueOf(lines[index].getTest())).doubleValue();
    			value = value * 100.0 / new Double(String.valueOf(questionCount)).doubleValue();
    			return String.valueOf(Math.round(value));
        	}
        }
        if(field.getName().equals("lessons")) {
        	return lines[index].getLessons();
        }
        return null;
    }
}
