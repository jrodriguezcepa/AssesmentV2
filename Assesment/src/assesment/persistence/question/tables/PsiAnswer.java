/**
 * CEPA
 * Assesment
 */
package assesment.persistence.question.tables;

import assesment.communication.question.AnswerData;

public class PsiAnswer {

    private Integer id;
    
    private PsiQuestion psiquestion;
    
    private String key;
    private Integer order;
    
    public PsiAnswer() {
    }

    public PsiAnswer(AnswerData data, PsiQuestion psiquestion) {
        id = data.getId();
        this.psiquestion = psiquestion;
        key = data.getKey();
        order = data.getOrder();
    }

    public Integer getId() {
        return id;
    }

    public PsiQuestion getPsiquestion() {
        return psiquestion;
    }

    public String getKey() {
        return key;
    }

    public Integer getOrder() {
        return order;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPsiquestion(PsiQuestion psiquestion) {
        this.psiquestion = psiquestion;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public AnswerData getData() {
        return new AnswerData(id,key,order,null, null);
    }

    public void setData(AnswerData data) {
        key = data.getKey();
        order = data.getOrder();
    }
}
