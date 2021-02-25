/**
 * CEPA
 * Assesment
 */
package assesment.communication.module;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import assesment.communication.question.QuestionData;

public class ModuleData extends ModuleAttribute {

    public static final int PSICO = 0;
    
    public static final int PERSONAL_DATA = 1;
    public static final int GENERAL_CULTURE = 2;
    public static final int SECURITY_POLICIES = 3;
    public static final int VEHICLE_SECURITY = 4;
    public static final int OTHER = 5;
    public static final int FINAL_MODULE = 6;

    public static final int GENERIC = 0;
    public static final int NORMAL = 1;

    private Collection<QuestionData> questions;
    private Integer answered;
    
    public ModuleData() {
    }

    /**
     * @param id
     * @param key
     * @param order
     * @param type 
     * @param questions
     */
    public ModuleData(Integer id,String key,Integer order,Integer assesment,Integer type, Collection<QuestionData> questions) {
        super(id,key,order,assesment,type);
        this.questions = questions;
    }

    public Collection<QuestionData> getQuestions() {
        return questions;
    }

    public void setQuestions(Collection<QuestionData> questions) {
        this.questions = questions;
    }

    public Iterator getQuestionIterator() {
        Collections.sort((List)questions);
        return questions.iterator();
    }

    public Iterator getQuestionDisorder() {
    	Collection<Integer> used = new LinkedList<Integer>();
    	Collection<QuestionData> disorder = new LinkedList<QuestionData>();
    	QuestionData[] array = questions.toArray(new QuestionData[0]);
    	while(used.size() < questions.size()) {
    		Integer x = new Integer((int)Math.round(Math.random()*(array.length-1)));
    		while(used.contains(x)) {
    			x = new Integer((int)Math.round(Math.random()*(array.length-1)));
    		}
    		disorder.add(array[x.intValue()]);
    		used.add(x);
    	}

        return disorder.iterator();
    }

    public int getQuestionSize() {
        if(questions == null) {
            return 0;
        }
        return questions.size();
    }

    public int getRelevantQuestionSize() {
        if(questions == null) {
            return 0;
        }
        int count = 0;
        Iterator it = questions.iterator();
        while(it.hasNext()) {
            if(((QuestionData)it.next()).getTestType().intValue() == QuestionData.TEST_QUESTION) {
                count++;
            }
        }
        return count;
    }

    public int getIrelevantAnswerSize() {
        if(questions == null) {
            return 0;
        }
        int count = 0;
        Iterator it = questions.iterator();
        while(it.hasNext()) {
            QuestionData question = (QuestionData)it.next();
            if(question.getTestType().intValue() == QuestionData.NOT_TEST_QUESTION && 
                    (question.getType().intValue() == QuestionData.EXCLUDED_OPTIONS 
                            || question.getType().intValue() == QuestionData.INCLUDED_OPTIONS
                            || question.getType().intValue() == QuestionData.LIST)) {
                count += question.getAnswerSize();
            }
        }
        return count;
    }

	public Integer getAnswered() {
		return answered;
	}

	public void setAnswered(Integer answered) {
		this.answered = answered;
	}
    
	public QuestionData findQuestion(Integer question) {
		Iterator<QuestionData> it = questions.iterator();
		while(it.hasNext()) {
			QuestionData questionData = it.next();
			if(questionData.getId().equals(question))
				return questionData;
		}
		return null;
	}
    
}
