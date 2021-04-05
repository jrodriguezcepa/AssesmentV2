/**
 * CEPA
 * Assesment
 */
package assesment.persistence.question.tables;

import assesment.communication.question.AnswerData;

public class PsiAnswerBKP {

    private Integer id;
    
    private Integer psiquestion;
    
    private String key;
    private Integer order;
    
    public PsiAnswerBKP() {
    }
    
    public PsiAnswerBKP(Object[] data) {
    	this.id=(Integer)data[0];
    	this.psiquestion=(Integer)data[1];
    	this.key=(String)data[2];
    	this.order=(Integer)data[3];


    }

    public Integer getId() {
        return id;
    }

    public Integer getPsiquestion() {
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

    public void setPsiquestion(Integer psiquestion) {
        this.psiquestion = psiquestion;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

}
