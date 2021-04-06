/**
 * Created on 25-sep-2007
 * CEPA
 * DataCenter 5
 */
package assesment.persistence.administration.tables;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.classic.Session;

import assesment.communication.administration.UserAnswerData;
import assesment.persistence.hibernate.HibernateAccess;
import assesment.persistence.question.tables.Answer;
import assesment.persistence.question.tables.Question;

public class AnswersUserBKP implements Serializable{

    private Integer assesment;
    private String loginName;
    private Integer answer;
    
    public AnswersUserBKP() {
    	
    }
    public AnswersUserBKP(Object[] data) {
    	this.assesment=(Integer)data[0];
    	this.loginName=(String)data[1];
    	this.answer=(Integer)data[2];
    }
	
	public Integer getAssesment() {
		return assesment;
	}
	public void setAssesment(Integer assesment) {
		this.assesment = assesment;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public Integer getAnswer() {
		return answer;
	}
	public void setAnswer(Integer answer) {
		this.answer = answer;
	}
    
  
}
