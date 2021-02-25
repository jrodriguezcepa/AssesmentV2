/*
 * Created on 14-nov-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.persistence.administration.tables;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UserAssesmentResult implements Serializable{
	
    private static final long serialVersionUID = 1L;

	private Integer id;

	private String login;
	private Integer assesment;
	
	private Integer type;

	private Integer correct;
	private Integer incorrect;
	
	public UserAssesmentResult() {
	}
	
	public UserAssesmentResult(String login, Integer assesment, Integer type, Integer correct, Integer incorrect) {
		this.login = login;
		this.assesment = assesment;
		this.type = type;
		this.correct = correct;
		this.incorrect = incorrect;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Integer getAssesment() {
		return assesment;
	}

	public void setAssesment(Integer assesment) {
		this.assesment = assesment;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getCorrect() {
		return correct;
	}

	public void setCorrect(Integer correct) {
		this.correct = correct;
	}

	public Integer getIncorrect() {
		return incorrect;
	}

	public void setIncorrect(Integer incorrect) {
		this.incorrect = incorrect;
	}

}
