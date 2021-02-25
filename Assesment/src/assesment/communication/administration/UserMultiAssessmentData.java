/*
 * Created on 14-nov-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.communication.administration;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import assesment.communication.assesment.AssesmentData;
import assesment.communication.user.UserData;

/**
 * @author fcarriquiry
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UserMultiAssessmentData implements Serializable{
	
    private static final long serialVersionUID = 1L;

    private Integer id;
    
    private UserData user;
	private AssesmentData assessment;
	private Date startDate;
	private Date endDate;
	
	private Collection answers;
	
	/**
	 * 
	 */
	public UserMultiAssessmentData() {
		answers = new LinkedList();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserData getUser() {
		return user;
	}

	public void setUser(UserData loginname) {
		this.user = loginname;
	}

	public AssesmentData getAssessment() {
		return assessment;
	}

	public void setAssessment(AssesmentData assessment) {
		this.assessment = assessment;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Collection getAnswers() {
		return answers;
	}

	public void setAnswers(Collection answers) {
		this.answers = answers;
	}

	public void addAnswer(SpecificAnswerData data) {
		answers.add(data);
	}
}

