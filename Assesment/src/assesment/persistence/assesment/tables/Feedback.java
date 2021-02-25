/**
 * CEPA
 * Assesment
 */
package assesment.persistence.assesment.tables;

import org.hibernate.classic.Session;


public class Feedback {

    private FeedbackPK pk;
    
    public Feedback() {
    }

    public Feedback(Integer assesment, String email, Integer report, Session session) {
        pk = new FeedbackPK(assesment, email, report, session);
    }

    public FeedbackPK getPk() {
        return pk;
    }

    public void setPk(FeedbackPK pk) {
        this.pk = pk;
    }

}
