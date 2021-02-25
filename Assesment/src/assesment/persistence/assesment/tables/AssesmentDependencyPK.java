/**
 * CEPA
 * Assesment
 */
package assesment.persistence.assesment.tables;

import java.io.Serializable;

import org.hibernate.classic.Session;

public class AssesmentDependencyPK implements Serializable {

    private Integer assesment;
    private Integer previous;
    
    public AssesmentDependencyPK() {
    }

    public AssesmentDependencyPK(Integer assesment, Integer previous) {
        this.assesment = assesment;
        this.previous = previous;
    }

	public Integer getAssesment() {
		return assesment;
	}

	public void setAssesment(Integer assesment) {
		this.assesment = assesment;
	}

	public Integer getPrevious() {
		return previous;
	}

	public void setPrevious(Integer previous) {
		this.previous = previous;
	}
    
}
