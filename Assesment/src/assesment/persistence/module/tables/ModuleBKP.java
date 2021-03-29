/**
 * CEPA
 * Assesment
 */
package assesment.persistence.module.tables;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import org.hibernate.Session;

import assesment.communication.module.ModuleAttribute;
import assesment.communication.module.ModuleData;
import assesment.communication.question.QuestionData;
import assesment.persistence.assesment.tables.AssesmentBKP;
import assesment.persistence.hibernate.HibernateAccess;
import assesment.persistence.question.tables.QuestionBKP;

public class ModuleBKP {

    private Integer id;
    
    private AssesmentBKP assesment;
    
    private String key;
    private Integer order;
    private Integer type;
    
    private Set<QuestionBKP> questionSet; 
    
    public ModuleBKP() {
    }

    public ModuleBKP(Module module, AssesmentBKP assesment) {
    	this.id=module.getId();
    	this.assesment=assesment;
    	this.key=module.getKey();
    	this.order=module.getOrder();
    	this.type=module.getType();
    }

    public Integer getId() {
        return id;
    }

    public AssesmentBKP getAssesment() {
        return assesment;
    }

    public String getKey() {
        return key;
    }

    public Integer getOrder() {
        return order;
    }

    public Set<QuestionBKP> getQuestionSet() {
        return questionSet;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAssesment(AssesmentBKP assesment) {
        this.assesment = assesment;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public void setQuestionSet(Set<QuestionBKP> questionSet) {
        this.questionSet = questionSet;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
