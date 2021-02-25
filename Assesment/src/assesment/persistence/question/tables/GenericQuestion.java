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
import assesment.persistence.module.tables.GenericModule;

public class GenericQuestion {

    private Integer id;
    
    private GenericModule module;
    
    private String key;
    private Integer order;
    private Integer type;
    
    private String image;
    private Integer testType;
    private Integer groupId;
    
    private Set<GenericAnswer> answerSet;

    public GenericQuestion() {
    }

    public GenericQuestion(QuestionData questionData) {
        id = questionData.getId();
        setData(questionData);
        answerSet = new HashSet<GenericAnswer>();
        image = questionData.getImage();
        testType = questionData.getTestType();
        Iterator<AnswerData> it = questionData.getAnswerIterator();
        while(it.hasNext()) {
            answerSet.add(new GenericAnswer(it.next(),this));
        }
        groupId = questionData.getGroup();
    }

    public void setData(QuestionData questionData) {
        Session session = HibernateAccess.currentSession();
        module = (GenericModule)session.load(GenericModule.class,questionData.getModule());
        key = questionData.getKey();
        order = questionData.getOrder();
        type = questionData.getType();
        if(questionData.getImage() != null) {
        	image = questionData.getImage();
        }
        testType = questionData.getTestType();
        groupId = questionData.getGroup();
    }

    public Integer getId() {
        return id;
    }

    public GenericModule getModule() {
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

    public Set<GenericAnswer> getAnswerSet() {
        return answerSet;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setModule(GenericModule module) {
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

    public void setAnswerSet(Set<GenericAnswer> answerSet) {
        this.answerSet = answerSet;
    }

    public QuestionData getData() {
        Collection<AnswerData> answers = new LinkedList<AnswerData>();
        Iterator<GenericAnswer> it = answerSet.iterator();
        while(it.hasNext()) {
            answers.add(it.next().getData());
        }
        return new QuestionData(id,key,module.getId(),order,type,image,testType,groupId,answers);
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

}
