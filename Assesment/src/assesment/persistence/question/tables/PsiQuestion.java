/**
 * CEPA
 * Assesment
 */
package assesment.persistence.question.tables;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import org.hibernate.Session;

import assesment.communication.question.AnswerData;
import assesment.communication.question.QuestionData;
import assesment.persistence.hibernate.HibernateAccess;
import assesment.persistence.module.tables.Module;

public class PsiQuestion {

    private Integer id;
    
    private String key;
    private Integer order;
    
    private Set<PsiAnswer> answerSet;
    
    public PsiQuestion() {
    }

    public PsiQuestion(QuestionData questionData) {
        id = questionData.getId();
        setData(questionData);
        Iterator<AnswerData> it = questionData.getAnswerIterator();
        answerSet = new HashSet<PsiAnswer>();
        while(it.hasNext()) {
            answerSet.add(new PsiAnswer(it.next(),this));
        }
    }

    public void setData(QuestionData questionData) {
        key = questionData.getKey();
        order = questionData.getOrder();
    }

    public Integer getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public Integer getOrder() {
        return order;
    }

    public Set<PsiAnswer> getAnswerSet() {
        return answerSet;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public void setAnswerSet(Set<PsiAnswer> answerSet) {
        this.answerSet = answerSet;
    }

    public QuestionData getData() {
        Collection<AnswerData> answers = new LinkedList<AnswerData>();
        Iterator<PsiAnswer> it = answerSet.iterator();
        while(it.hasNext()) {
            answers.add(it.next().getData());
        }
        return new QuestionData(id,key,null,order,new Integer(QuestionData.EXCLUDED_OPTIONS),null,null,0,answers);
    }
}
