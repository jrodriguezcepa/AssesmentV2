/**
 * CEPA
 * Assesment
 */
package assesment.persistence.module.tables;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import org.hibernate.Session;

import assesment.communication.module.ModuleAttribute;
import assesment.communication.module.ModuleData;
import assesment.communication.question.QuestionData;
import assesment.persistence.assesment.tables.AssesmentBKP;
import assesment.persistence.hibernate.HibernateAccess;
import assesment.persistence.question.tables.Question;
import assesment.persistence.question.tables.QuestionBKP;

public class ModuleBKP {

    private Integer id;
    
    private AssesmentBKP assesment;
    
    private String key;
    private Integer order;
    private Integer type;
    private Integer green;
    private Integer yellow;
    private Set<QuestionBKP> questionSet; 
    
    public ModuleBKP() {
    }

    public ModuleBKP(Module module, AssesmentBKP assesment) {
    	this.id=module.getId();
    	this.assesment=assesment;
    	this.key=module.getKey();
    	this.order=module.getOrder();
    	this.type=module.getType();
    	this.green=module.getGreen();
    	this.yellow=module.getYellow();
    	questionSet = new HashSet<QuestionBKP>();
    }
    
    public ModuleData getData() {
        Collection<QuestionData> questions = new LinkedList<QuestionData>();
        Iterator<QuestionBKP> it = questionSet.iterator();
        while(it.hasNext()) {
            questions.add(it.next().getData());
        }
        int greenV = (green != null) ? green : assesment.getGreen();
        int yellowV = (yellow != null) ? yellow : assesment.getYellow();
        return new ModuleData(id,key,order,assesment.getId(),type,questions, greenV, yellowV);
    }

    public ModuleAttribute getAttributes() {
        int greenV = (green != null) ? green : assesment.getGreen();
        int yellowV = (yellow != null) ? yellow : assesment.getYellow();
        return new ModuleAttribute(id,key,order,assesment.getId(),type,greenV,yellowV);
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

	public void addQuestion(QuestionBKP questionBKP) {
		questionSet.add(questionBKP);
	}

	public Integer getGreen() {
		return green;
	}

	public void setGreen(Integer green) {
		this.green = green;
	}

	public Integer getYellow() {
		return yellow;
	}

	public void setYellow(Integer yellow) {
		this.yellow = yellow;
	}

}
