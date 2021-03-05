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
import assesment.persistence.module.tables.ModuleBKP;

public class QuestionBKP {

    private Integer id;
    
    private ModuleBKP module;
    
    private String key;
    private Integer order;
    private Integer type;
    
    private String image;
    private Integer testType;
    private Integer groupId;
    
    private boolean wrt;
    
    private Set<AnswerBKP> answerSet;
    
    public QuestionBKP() {
    }

    public Integer getId() {
        return id;
    }

    public ModuleBKP getModule() {
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

    public Set<AnswerBKP> getAnswerSet() {
        return answerSet;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setModule(ModuleBKP module) {
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

    public void setAnswerSet(Set<AnswerBKP> answerSet) {
        this.answerSet = answerSet;
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