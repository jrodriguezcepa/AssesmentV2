/**
 * Created on 25-sep-2007
 * CEPA
 * DataCenter 5
 */
package assesment.persistence.administration.tables;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.classic.Session;

import assesment.communication.administration.UserAnswerData;
import assesment.persistence.hibernate.HibernateAccess;
import assesment.persistence.question.tables.AnswerBKP;
import assesment.persistence.question.tables.PsiAnswerBKP;
import assesment.persistence.question.tables.PsiQuestionBKP;
import assesment.persistence.question.tables.QuestionBKP;

public class UserAnswerBKP {

    private Integer id;
    private Integer question;
    private Integer answer;
    private String text;
    private Date date;
    private Integer distance;
    private Integer unit;
    private Integer country;
    private boolean never;
    private Set multioptions;
    
    public UserAnswerBKP() {
    }

    public UserAnswerBKP(Object[] data) {
        this.id=(Integer)data[0];
        this.question=(Integer)data[1];
        this.answer=(Integer)data[2];
        this.text=(String)data[3];
        this.date=(Date)data[4];
        this.distance=(Integer)data[5];
        this.unit=(Integer)data[6];
        this.country=(Integer)data[7];
        this.never=(Boolean)data[8];
    }

    public UserAnswerBKP(UserAnswer userAnswer) {
        this.id=userAnswer.getId();
        this.question=userAnswer.getQuestion().getId();
        if(userAnswer.getAnswer() != null)
        	this.answer=userAnswer.getAnswer().getId();
        this.text=userAnswer.getText();
        this.date=userAnswer.getDate();
        this.distance=userAnswer.getDistance();
        this.unit=userAnswer.getUnit();
        this.country=userAnswer.getCountry();
        this.never=userAnswer.isNever();
        multioptions = new HashSet();
	}

	public Integer getAnswer() {
        return answer;
    }

    public Date getDate() {
        return date;
    }

    public Integer getDistance() {
        return distance;
    }

    public Integer getId() {
        return id;
    }

    public Integer getQuestion() {
        return question;
    }

    public String getText() {
        return text;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setQuestion(Integer question) {
        this.question = question;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public Set getMultioptions() {
        return multioptions;
    }

    public void setMultioptions(Set multioptions) {
        this.multioptions = multioptions;
    }

    public Integer getCountry() {
        return country;
    }

    public boolean isNever() {
        return never;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    public void setNever(boolean never) {
        this.never = never;
    }

    public void addMultioption(AnswerBKP answer) {
    	multioptions.add(answer);
    }
}
