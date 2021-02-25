/**
 * Created on 24-sep-2007
 * CEPA
 * DataCenter 5
 */
package assesment.communication.report;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import assesment.communication.administration.UserAnswerData;
import assesment.communication.language.Text;
import assesment.communication.module.ModuleData;
import assesment.communication.question.AnswerData;
import assesment.communication.question.QuestionData;
import assesment.communication.util.CountryConstants;

public class UserPersonalDataReportDataSource implements JRDataSource {

    private int index = -1;
    private String[][] texts;
    private Text messages;
    
    public UserPersonalDataReportDataSource(ModuleData module, Collection answers, Text messages) {
        this.messages = messages;
        Collection<String[]> elements = new LinkedList<String[]>();
        Iterator questionIt = module.getQuestionIterator();
        while(questionIt.hasNext()) {
            QuestionData question = (QuestionData)questionIt.next();
            Iterator it = answers.iterator();
            boolean found = false;
            while(it.hasNext() && !found) {
                UserAnswerData answer = (UserAnswerData)it.next();
                if(answer.getQuestion().equals(question.getId())) {
                    found = true;
                    if(answer.getCountry() != null) {
                        CountryConstants cc = new CountryConstants(messages);
                        String[] data = {messages.getText(question.getKey()),cc.find(String.valueOf(answer.getCountry()))};
                        elements.add(data);
                    }
                    if(answer.getText() != null) {
                        String[] data = {messages.getText(question.getKey()),answer.getText()};
                        elements.add(data);
                    }
                    if(answer.getDate() != null) {
                        String[] data = {messages.getText(question.getKey()),formatDate(answer.getDate())};
                        elements.add(data);
                    }
                    if(answer.getUnit() != null) {
                        String[] data = {messages.getText(question.getKey()),String.valueOf(answer.getUnit())};
                        elements.add(data);
                    }
                    if(answer.getAnswer() != null) {
                        boolean find = false;
                        Iterator answerIt = question.getAnswerIterator();
                        while(answerIt.hasNext() && !find) {
                            AnswerData answerData = (AnswerData)answerIt.next();
                            if(answerData.getId().equals(answer.getAnswer())) {
                                find = true;
                                String[] data = {messages.getText(question.getKey()),messages.getText(answerData.getKey())};
                                elements.add(data);
                            }
                        }
                    }
                    if(answer.getOptions() != null) {
                        Iterator<Integer> options = answer.getOptions().iterator();
                        while(options.hasNext()) {
                            Integer optionId = options.next();
                            boolean find = false;
                            Iterator answerIt = question.getAnswerIterator();
                            while(answerIt.hasNext() && !find) {
                                AnswerData answerData = (AnswerData)answerIt.next();
                                if(answerData.getId().equals(optionId)) {
                                    find = true;
                                    String[] data = {messages.getText(question.getKey()),messages.getText(answerData.getKey())};
                                    elements.add(data);
                                }
                            }
                        }
                    }
                }
            }
        }
        texts = (String[][])elements.toArray(new String[0][0]);
    }

    public boolean next() throws JRException {
        if(index+1 < texts.length) {
            index++;
            return true;
        }
        return false;
    }

    public Object getFieldValue(JRField field) throws JRException {
        if(field.getName().equals("questiontext")) {
            return texts[index][0];
        }
        if(field.getName().equals("answertext")) {
            return texts[index][1];
        }
        return null;
    }

    private String formatDate(Date date) {
        if(date == null) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.YEAR);
    }
}
