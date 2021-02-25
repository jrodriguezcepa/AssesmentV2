/*
 * Created on 14-nov-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.persistence.administration.tables;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.classic.Session;

import assesment.communication.administration.UserMultiAssessmentData;
import assesment.persistence.assesment.tables.Assesment;
import assesment.persistence.hibernate.HibernateAccess;
import assesment.persistence.user.tables.User;

/**
 * @author fcarriquiry
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UserMultiAssessment implements Serializable{
	
    private static final long serialVersionUID = 1L;

    private Integer id;
    
    private User loginname;
	private Assesment assessment;
	private Date startDate;
	private Date endDate;
	
	private Set answers;
	
	/**
	 * 
	 */
	public UserMultiAssessment() {
	}
	
	public UserMultiAssessment(String login, Integer assesmentId, Date date) {
		Session session = HibernateAccess.currentSession();
		loginname=(User)session.load(User.class, login);
        assessment=(Assesment)session.load(Assesment.class, assesmentId);
        this.startDate = date;
        answers = new HashSet();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getLoginname() {
		return loginname;
	}

	public void setLoginname(User loginname) {
		this.loginname = loginname;
	}

	public Assesment getAssessment() {
		return assessment;
	}

	public void setAssessment(Assesment assessment) {
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

	public Set getAnswers() {
		return answers;
	}

	public void setAnswers(Set answers) {
		this.answers = answers;
	}

	public UserMultiAssessmentData getData() {
		UserMultiAssessmentData data = new UserMultiAssessmentData();
		data.setId(id);
		data.setAssessment(assessment.getData());
		data.setUser(loginname.getUserData());
		data.setStartDate(startDate);
		data.setEndDate(endDate);
		Iterator it = answers.iterator();
		while(it.hasNext()) {
			data.addAnswer(((UserSpecificAnswer)it.next()).getData());
		}
		return data;
	}

}
