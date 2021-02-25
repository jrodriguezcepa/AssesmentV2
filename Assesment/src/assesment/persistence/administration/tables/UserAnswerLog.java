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

public class UserAnswerLog implements Serializable{

	private String userid;
    private Date date; 
    private Integer assesment;
    private Integer id;
    private String loginName;
    private Integer question;
    private Integer answer;
    private String text;
    private Date datead; 
    private Integer country;
    private Integer distance;
    private Integer unit;
    private boolean never;
    private String comment;
    private Integer action;


    public UserAnswerLog() {
    }

	public UserAnswerLog(UserAnswer answerdata, Integer assesment, String user, String loginName,Date date, Integer action, String comment){
		this.userid=loginName;
		this.date=date;
		this.comment=comment;
		this.action=action;
		this.loginName=user;
		this.assesment=assesment;
		this.id=answerdata.getAnswer().getId();
		this.question=answerdata.getQuestion().getId();
		this.text=answerdata.getText();
		this.datead=answerdata.getDate();
		this.distance=answerdata.getDistance();
		this.unit=answerdata.getUnit();
		this.country=answerdata.getCountry();
		this.never=answerdata.isNever();
	}	
	
	public Integer getAssesment() {
		return assesment;
	}


	public void setAssesment(Integer assesment) {
		this.assesment = assesment;
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


	public Integer getQuestion() {
		return question;
	}


	public void setQuestion(Integer question) {
		this.question = question;
	}


	public Integer getAnswer() {
		return answer;
	}


	public void setAnswer(Integer answer) {
		this.answer = answer;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	public Date getDatead() {
		return datead;
	}


	public void setDatead(Date datead) {
		this.datead = datead;
	}


	public Integer getCountry() {
		return country;
	}


	public void setCountry(Integer country) {
		this.country = country;
	}


	public Integer getDistance() {
		return distance;
	}


	public void setDistance(Integer distance) {
		this.distance = distance;
	}


	public Integer getUnit() {
		return unit;
	}


	public void setUnit(Integer unit) {
		this.unit = unit;
	}


	public boolean isNever() {
		return never;
	}


	public void setNever(boolean never) {
		this.never = never;
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

   
}
