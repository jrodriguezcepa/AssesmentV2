/**
 * CEPA
 * Assesment
 */
package assesment.persistence.question.tables;

import assesment.communication.question.AnswerData;

public class PsiAnswerBKP {

    private Integer id;
    
    private PsiQuestionBKP psiquestion;
    
    private String key;
    private Integer order;
    
    public PsiAnswerBKP() {
    }

    public Integer getId() {
        return id;
    }

    public PsiQuestionBKP getPsiquestion() {
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

    public void setPsiquestion(PsiQuestionBKP psiquestion) {
        this.psiquestion = psiquestion;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

}
