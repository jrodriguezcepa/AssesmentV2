/**
 * CEPA
 * Assesment
 */
package assesment.persistence.question.tables;

import assesment.communication.question.AnswerData;

public class Answer {

    private Integer id;
    
    private Question question;
    
    private String key;
    private Integer order;
    private Integer resultType;
    private Float points;
    public Answer() {
    }

    public Answer(AnswerData data, Question question) {
        id = data.getId();
        this.question = question;
        key = data.getKey();
        order = data.getOrder();
        resultType = data.getResultType();
    }

    public Integer getId() {
        return id;
    }

    public Question getQuestion() {
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

    public void setQuestion(Question question) {
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
        return new AnswerData(id,key,order,resultType, points);
    }

    public void setData(AnswerData data) {
        key = data.getKey();
        order = data.getOrder();
        resultType = data.getResultType();
    }

	public Float getPoints() {
		return points;
	}

	public void setPoints(Float points) {
		this.points = points;
	}
}
