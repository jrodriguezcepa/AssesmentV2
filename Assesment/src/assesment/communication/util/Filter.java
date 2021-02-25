/*
 * Created on 03-nov-2005
 */
package assesment.communication.util;

import java.io.Serializable;

import assesment.communication.assesment.AssesmentData;

/**
 * @author jrodriguez
 */
public class Filter implements Serializable {

    private static final long serialVersionUID = 1L;

    private String loginName;
    private Integer assesment;
    private Integer group;
    private AssesmentData assessmentData;
    private Integer multi;
	
    public Filter(String loginName, Integer assesment) {       
        this.loginName = loginName;
        this.assesment = assesment;
    }
    
	public String getLoginName() {
		return loginName;
	}

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Integer getAssesment() {
        return assesment;
    }

    public void setAssesment(Integer assesment) {
        this.assesment = assesment;
    }

	public AssesmentData getAssessmentData() {
		return assessmentData;
	}

	public void setAssessmentData(AssesmentData assessmentData) {
		this.assessmentData = assessmentData;
		this.assesment = assessmentData.getId();
	}

	public Integer getMulti() {
		return multi;
	}

	public void setMulti(Integer multi) {
		this.multi = multi;
	}

	public Integer getGroup() {
		return group;
	}

	public void setGroup(Integer group) {
		this.group = group;
	}

}
