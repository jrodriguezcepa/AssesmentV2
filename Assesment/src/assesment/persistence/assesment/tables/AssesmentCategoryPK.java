/**
 * CEPA
 * Assesment
 */
package assesment.persistence.assesment.tables;

import java.io.Serializable;

public class AssesmentCategoryPK implements Serializable {

    private Integer assesment;
    private Integer category;
    
    public AssesmentCategoryPK() {
    }

    public AssesmentCategoryPK(Integer assesment, Integer category) {
        this.assesment = assesment;
        this.category = category;
    }

	public Integer getAssesment() {
		return assesment;
	}

	public void setAssesment(Integer assesment) {
		this.assesment = assesment;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

}
