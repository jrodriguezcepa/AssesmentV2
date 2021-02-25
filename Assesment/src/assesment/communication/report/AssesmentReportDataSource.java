/**
 * Created on 24-sep-2007
 * CEPA
 * DataCenter 5
 */
package assesment.communication.report;

import java.util.Iterator;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.language.Text;
import assesment.communication.module.ModuleData;
import assesment.communication.question.AnswerData;
import assesment.communication.question.QuestionData;

public class AssesmentReportDataSource implements JRDataSource {

    private int index = 0;
    private int qindex = -1;
    private int aindex = 0;
    private ModuleData[] modules;
    private QuestionData[] questions;
    private String module;
    private int questionCount;
    private Text messages;
    
    public AssesmentReportDataSource(AssesmentData assesmentData,Text messages) {
        modules = new ModuleData[assesmentData.getModules().size()];
        Iterator it = assesmentData.getModuleIterator();
        int i = 0;
        while(it.hasNext()) {
            modules[i] = (ModuleData)it.next();
            i++;
        }
        if(modules.length > 0) {
            loadQuestions(index);
            module = messages.getText(modules[index].getKey());
            questionCount = modules[index].getQuestionSize();
        }else {
            questions = new QuestionData[0];
        }
        this.messages = messages;
    }

    public boolean next() throws JRException {
        if(qindex+1 < questions.length) {
            if(qindex == -1) {
                qindex++;
            }else {
                if(questions[qindex].getAnswerSize() == 0) {
                    qindex++;
                }else {
                    if(aindex >= questions[qindex].getAnswerSize()){
                        aindex = 0;
                        qindex++;
                    }
                }
            }
            return true;
        }else {
            if(aindex < questions[qindex].getAnswerSize()){
                return true;
            }
            if(index+1 < modules.length) {
                index++;
                loadQuestions(index);
                module = messages.getText(modules[index].getKey());
                questionCount = modules[index].getQuestionSize();
                qindex = 0;
                aindex = 0;
                return true;
            }
        }
        return false;
    }

    public Object getFieldValue(JRField field) throws JRException {
        if(field.getName().equals("modulename")) {
            return messages.getText(modules[index].getKey());
        }
        if(field.getName().equals("questiontext")) {
            return messages.getText(questions[qindex].getKey());
        }
        if(field.getName().equals("questioncount")) {
            return String.valueOf(questionCount)+" "+messages.getText("system.questions");
        }
        if(field.getName().equals("answertext")) {
            if(questions[qindex].getAnswerSize() > 0) {
                Iterator<AnswerData> it = questions[qindex].getAnswerIterator();
                for(int i = 0; i < aindex; i++) {
                    it.next();
                }
                aindex++;
                String s = it.next().getKey();
                return messages.getText(s);
            }            
        }
        return null;
    }

    private void loadQuestions(int mIndex) {
        questions = new QuestionData[modules[mIndex].getQuestionSize()];
        Iterator it = modules[mIndex].getQuestionIterator();
        int i = 0;
        while(it.hasNext()) {
            questions[i] = (QuestionData)it.next();
            i++;
        }

    }
}
