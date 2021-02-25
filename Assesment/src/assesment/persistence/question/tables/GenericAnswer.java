/**
 * CEPA
 * Assesment
 */
package assesment.persistence.question.tables;

import assesment.communication.question.AnswerData;

public class GenericAnswer {

    private Integer id;
    
    private GenericQuestion question;
    
    private String key;
    private Integer order;
    private Integer resultType;
    
    public GenericAnswer() {
    }

    public GenericAnswer(AnswerData data, GenericQuestion question) {
        id = data.getId();
        this.question = question;
        key = data.getKey();
        order = data.getOrder();
        resultType = data.getResultType();
    }

    public Integer getId() {
        return id;
    }

    public GenericQuestion getQuestion() {
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

    public void setQuestion(GenericQuestion question) {
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
    
    public AnswerData getData() {
        return new AnswerData(id,key,order,resultType);
    }

    public void setData(AnswerData data) {
        key = data.getKey();
        order = data.getOrder();
        resultType = data.getResultType();
    }
}
