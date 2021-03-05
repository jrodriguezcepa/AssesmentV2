/**
 * CEPA
 * Assesment
 */
package assesment.persistence.assesment.tables;

import java.util.Date;


public class AssesmentCategory {

    private AssesmentCategoryPK pk;
    private Date start;
    private Date end;
    private Boolean changeName;
    private Boolean showReport;
    
    public AssesmentCategory() {
    }

    public AssesmentCategory(Integer assesment, Integer category) {
        pk = new AssesmentCategoryPK(assesment, category);
        changeName = false;
    }

    public AssesmentCategoryPK getPk() {
        return pk;
    }

    public void setPk(AssesmentCategoryPK pk) {
        this.pk = pk;
    }

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Boolean getChangeName() {
		return changeName;
	}

	public void setChangeName(Boolean changeName) {
		this.changeName = changeName;
	}

	public Boolean getShowReport() {
		return showReport;
	}

	public void setShowReport(Boolean showReport) {
		this.showReport = showReport;
	}

}
