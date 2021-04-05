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

public class PsiQuestionBKP {

    private Integer id;
    
    private String key;
    private Integer order;
    
    private Set<PsiAnswerBKP> answerSet;
    
    public PsiQuestionBKP() {
    }
    public PsiQuestionBKP(Object[] data) {
    	this.id=(Integer)data[0];
    	this.key=(String)data[1];
    	this.order=(Integer)data[2];


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

    public Set<PsiAnswerBKP> getAnswerSet() {
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

    public void setAnswerSet(Set<PsiAnswerBKP> answerSet) {
        this.answerSet = answerSet;
    }

}
