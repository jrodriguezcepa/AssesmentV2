/**
 * CEPA
 * Assesment
 */
package assesment.persistence.module.tables;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import assesment.communication.module.ModuleAttribute;
import assesment.communication.module.ModuleData;
import assesment.communication.question.QuestionData;
import assesment.persistence.question.tables.GenericQuestion;

public class GenericModule {

    private Integer id;
    
    private String key;
    private Integer type;
    
    private Set<GenericQuestion> questionSet; 
    
    public GenericModule() {
    }

    public GenericModule(ModuleAttribute attributes) {
        id = attributes.getId();
        key = attributes.getKey();
        type = attributes.getType();
    }

    public Integer getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ModuleData getData() {
        Collection<QuestionData> questions = new LinkedList<QuestionData>();
        Iterator<GenericQuestion> it = questionSet.iterator();
        while(it.hasNext()) {
            questions.add(it.next().getData());
        }
        return new ModuleData(id,key,null,null,type,questions);
    }

    public ModuleAttribute getAttributes() {
        return new ModuleAttribute(id,key,null,null,type);
    }

    public Set<GenericQuestion> getQuestionSet() {
        return questionSet;
    }

    public void setQuestionSet(Set<GenericQuestion> questionSet) {
        this.questionSet = questionSet;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
