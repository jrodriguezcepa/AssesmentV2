/**
 * Created on 24-sep-2007
 * CEPA
 * DataCenter 5
 */
package assesment.communication.report;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import assesment.communication.language.Text;

public class UsersReportDataSource implements JRDataSource {

    private int index = -1;
    private Object[][] users;
    private Integer questionCount;
    private Text messages;
    
    private int all = 0;
    private int part = 0;
    private int none = 0;
    private Integer assesment;

    private Collection names = new LinkedList();
    
    public UsersReportDataSource(Collection<UserAnswerReportData> collection, Integer assesmentQuestionCount,Text messages, Integer assesment) {
        users = new Object[collection.size()][3]; 
        questionCount = assesmentQuestionCount;
        Collections.sort((List)collection);          
        Iterator<UserAnswerReportData> it = collection.iterator();
        int i = 0;
        while(it.hasNext()) {
            UserAnswerReportData data = it.next();
            if(data.getCount() == 0) {
                none++;
            }else if(data.getCount() < questionCount) {
                part++;
            }else {
            	names.add(data.getName());
                all++;
            }
            users[i][0] = " "+data.getName();
            users[i][1] = data.getLogin();
            users[i][2] = data.getCount();
            i++;
        }
        this.messages = messages;
        this.assesment = assesment;
    }

    public boolean next() throws JRException {
        if(index+1 < users.length) {
            index++;
            return true;
        }
        return false;
    }

    public Object getFieldValue(JRField field) throws JRException {
        if(field.getName().equals("typename")) {
            if(((Integer)users[index][2]).intValue() == 0) {
                return messages.getText("report.users.not");
            }
            if(((Integer)users[index][2]).intValue() < questionCount.intValue()) {
                return messages.getText("report.users.part");
            }else {
                return messages.getText("report.users.all");
            }
        }
        if(field.getName().equals("user")) {
            return (String)users[index][0];
        }
        if(field.getName().equals("questioncount")) {
            if(((Integer)users[index][2]).intValue() > 0 && ((Integer)users[index][2]).intValue() < questionCount.intValue()) {
                return String.valueOf(users[index][2])+" "+messages.getText("generic.messages.of")+" "+String.valueOf(questionCount);
            }
        }
        if(field.getName().equals("link")) {
            return "layout.jsp?refer=report/userresultreport.jsp?user="+String.valueOf(users[index][1])+"&assesment="+String.valueOf(assesment);
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
        return users.length;
    }

    public Collection getNames() {
    	return names;
    }

	public Object[][] getUsers() {
		return users;
	}

	public Integer getQuestionCount() {
		return questionCount;
	}

	public void setQuestionCount(Integer questionCount) {
		this.questionCount = questionCount;
	}

}
