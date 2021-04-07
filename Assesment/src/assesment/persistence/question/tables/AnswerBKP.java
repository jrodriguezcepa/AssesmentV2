/**
 * CEPA
 * Assesment
 */
package assesment.persistence.question.tables;

import assesment.communication.question.AnswerData;

public class AnswerBKP {

    private Integer id;
    
    private QuestionBKP question;
    
    private String key;
    private Integer order;
    private Integer resultType;
    
    public AnswerBKP() {
    }
    
    public AnswerBKP(Answer answer, QuestionBKP question) {
    	this.id=answer.getId();
    	this.question=question;
    	this.key=answer.getKey();
    	this.order=answer.getOrder();
    	this.resultType=answer.getResultType();
    }

    public AnswerData getData() {
        return new AnswerData(id,key,order,resultType);
    }
    public Integer getId() {
        return id;
    }

    public QuestionBKP getQuestion() {
        return question;
    }

    public String getKey() {
        return key;
    }

    public Integer getOrder() {
        return order;
    }

    public Integer getResultType() {
        return resultType;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setQuestion(QuestionBKP question) {
        this.question = question;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public void setResultType(Integer resultType) {
        this.resultType = resultType;
    }

}
