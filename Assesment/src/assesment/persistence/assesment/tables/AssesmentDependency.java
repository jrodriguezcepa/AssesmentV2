/**
 * CEPA
 * Assesment
 */
package assesment.persistence.assesment.tables;

import org.hibernate.classic.Session;


public class AssesmentDependency {

    private AssesmentDependencyPK pk;
    
    public AssesmentDependency() {
    }

    public AssesmentDependency(Integer assesment, Integer previous) {
        pk = new AssesmentDependencyPK(assesment, previous);
    }

    public AssesmentDependencyPK getPk() {
        return pk;
    }

    public void setPk(AssesmentDependencyPK pk) {
        this.pk = pk;
    }

}
