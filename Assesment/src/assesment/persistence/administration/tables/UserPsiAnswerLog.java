/**
 * CEPA
 * Assesment
 */
package assesment.persistence.administration.tables;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import org.hibernate.Session;

import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.module.ModuleData;
import assesment.persistence.corporation.tables.Corporation;
import assesment.persistence.hibernate.HibernateAccess;
import assesment.persistence.module.tables.Module;
import assesment.persistence.question.tables.Answer;
import assesment.persistence.question.tables.Question;

public class UserPsiAnswerLog implements Serializable{

	private String userid;
    private Date date; 
    private String comment;
    private Integer action;
    private Integer assesment;
    private Integer id;
    private String loginName;
    private Integer psiquestion;
    private Integer psianswer;


    public UserPsiAnswerLog() {
    }

	public UserPsiAnswerLog(UserPsiAnswer psiAnswer, Integer assesment, String user, String loginName,Date date, Integer action, String comment){
		this.userid=loginName;
		this.date=date;
		this.comment=comment;
		this.action=action;
		this.loginName=user;
		this.assesment=assesment;
		this.id=psiAnswer.getId();
		this.psiquestion=psiAnswer.getPsiquestion().getId();
		this.psianswer=psiAnswer.getPsianswer().getId();
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getAction() {
		return action;
	}

	public void setAction(Integer action) {
		this.action = action;
	}

	public Integer getAssesment() {
		return assesment;
	}

	public void setAssesment(Integer assesment) {
		this.assesment = assesment;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Integer getPsiquestion() {
		return psiquestion;
	}

	public void setPsiquestion(Integer psiquestion) {
		this.psiquestion = psiquestion;
	}

	public Integer getPsianswer() {
		return psianswer;
	}

	public void setPsianswer(Integer psianswer) {
		this.psianswer = psianswer;
	}
	

}
