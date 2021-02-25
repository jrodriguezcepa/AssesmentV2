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

public class Question {

    private Integer id;
    
    private Module module;
    
    private String key;
    private Integer order;
    private Integer type;
    
    private String image;
    private Integer testType;
    private Integer groupId;
    
    private boolean wrt;
    
    private Set<Answer> answerSet;
    
    public Question() {
    }

    public Question(QuestionData questionData) {
        id = questionData.getId();
        setData(questionData);
        Iterator<AnswerData> it = questionData.getAnswerIterator();
        answerSet = new HashSet<Answer>();
        if(questionData.getImage() != null && questionData.getImage().trim().length() > 0) {
            image = questionData.getImage();
        }
        testType = questionData.getTestType();
        while(it.hasNext()) {
            answerSet.add(new Answer(it.next(),this));
        }
        groupId = questionData.getGroup();
        wrt = questionData.isWrt();
    }

    public void setData(QuestionData questionData) {
        Session session = HibernateAccess.currentSession();
        module = (Module)session.load(Module.class,questionData.getModule());
        key = questionData.getKey();
        order = questionData.getOrder();
        type = questionData.getType();
        if(questionData.getImage() != null && questionData.getImage().length() > 0) {
            image = questionData.getImage();
        }
        testType = questionData.getTestType();
        groupId = questionData.getGroup();
        wrt = questionData.isWrt();
    }

    public Integer getId() {
        return id;
    }

    public Module getModule() {
        return module;
    }

    public String getKey() {
        return key;
    }

    public Integer getOrder() {
        return order;
    }

    public Integer getType() {
        return type;
    }

    public Set<Answer> getAnswerSet() {
        return answerSet;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setAnswerSet(Set<Answer> answerSet) {
        this.answerSet = answerSet;
    }

    public QuestionData getData() {
        Collection<AnswerData> answers = new LinkedList<AnswerData>();
        Iterator<Answer> it = answerSet.iterator();
        while(it.hasNext()) {
            answers.add(it.next().getData());
        }
        return new QuestionData(id,key,module.getId(),order,type,image,testType,groupId,wrt,answers);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getTestType() {
        return testType;
    }

    public void setTestType(Integer testType) {
        this.testType = testType;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

	public boolean isWrt() {
		return wrt;
	}

	public void setWrt(boolean wrt) {
		this.wrt = wrt;
	}

}
