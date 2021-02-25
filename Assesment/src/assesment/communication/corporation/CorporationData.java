/**
 * Assesment
 */
package assesment.communication.corporation;

import java.util.Collection;

import assesment.communication.assesment.AssesmentData;

public class CorporationData extends CorporationAttributes {

    public static final int JJ = 5;
    public static final int MICHELIN = 11;
	public static final int BASF = 42;
	public static final int MUTUAL = 189;
    
    private Collection<AssesmentData> assesments;
	private Integer assessmentCount;
	private Integer groupCount;
    
    public CorporationData() {
        super();
    }

    public CorporationData(Integer id, String name, String logo, Collection<AssesmentData> assesments, Integer dc5id) {
        super(id, name, logo,dc5id);
        this.assesments = assesments;
    }

    public Collection<AssesmentData> getAssesments() {
        return assesments;
    }

    public void setAssesments(Collection<AssesmentData> assesments) {
        this.assesments = assesments;
    }

	public void setAssessmentCount(Integer count) {
		assessmentCount = count;
	}

	public void setGroupCount(Integer count) {
		groupCount = count;
	}

	public Integer getAssessmentCount() {
		return assessmentCount;
	}

	public Integer getGroupCount() {
		return groupCount;
	}

}
