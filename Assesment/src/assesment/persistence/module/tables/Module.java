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
import assesment.persistence.assesment.tables.Assesment;
import assesment.persistence.hibernate.HibernateAccess;
import assesment.persistence.question.tables.Question;

public class Module {

    private Integer id;
    
    private Assesment assesment;
    
    private String key;
    private Integer order;
    private Integer type;
    
    private Integer green;
    private Integer yellow;

    private Set<Question> questionSet; 
    
    public Module() {
    }

    public Module(ModuleAttribute attributes) {
        Session session = HibernateAccess.currentSession();
        id = attributes.getId();
        assesment = (Assesment)session.load(Assesment.class,attributes.getAssesment());
        key = attributes.getKey();
        order = attributes.getOrder();
        type = attributes.getType();
        green = attributes.getGreen();
        yellow = attributes.getYellow();
    }

    public Integer getId() {
        return id;
    }

    public Assesment getAssesment() {
        return assesment;
    }

    public String getKey() {
        return key;
    }

    public Integer getOrder() {
        return order;
    }

    public Set<Question> getQuestionSet() {
        return questionSet;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAssesment(Assesment assesment) {
        this.assesment = assesment;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public void setQuestionSet(Set<Question> questionSet) {
        this.questionSet = questionSet;
    }

    public ModuleData getData() {
        Collection<QuestionData> questions = new LinkedList<QuestionData>();
        Iterator<Question> it = questionSet.iterator();
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
