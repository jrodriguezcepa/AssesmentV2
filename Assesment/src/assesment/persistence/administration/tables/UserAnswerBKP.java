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
import assesment.persistence.question.tables.QuestionBKP;

public class UserAnswerBKP {

    private Integer id;
    private QuestionBKP question;
    private AnswerBKP answer;
    private String text;
    private Date date;
    private Integer distance;
    private Integer unit;
    private Integer country;
    private boolean never;
    private Set multioptions;
    
    public UserAnswerBKP() {
    }


    public AnswerBKP getAnswer() {
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

    public QuestionBKP getQuestion() {
        return question;
    }

    public String getText() {
        return text;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setAnswer(AnswerBKP answer) {
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

    public void setQuestion(QuestionBKP question) {
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

}
