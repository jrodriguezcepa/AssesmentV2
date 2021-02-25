/**
 * CEPA
 * Assesment
 */
package assesment.persistence.assesment.tables;

import java.io.Serializable;

import org.hibernate.classic.Session;

public class FeedbackPK implements Serializable {

    private Assesment assesment;
    private String email;
    private Integer type;
    
    public FeedbackPK() {
    }

    public FeedbackPK(Integer assesmentId, String email, Integer report, Session session) {
        assesment = (Assesment)session.load(Assesment.class,assesmentId);
        this.email = email;
        this.type = report;
    }

    public Assesment getAssesment() {
        return assesment;
    }

    public String getEmail() {
        return email;
    }

    public Integer getType() {
        return type;
    }

    public void setAssesment(Assesment assesment) {
        this.assesment = assesment;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
