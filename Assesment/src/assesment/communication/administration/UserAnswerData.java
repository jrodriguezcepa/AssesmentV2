/**
 * Created on 25-sep-2007
 * CEPA
 * DataCenter 5
 */
package assesment.communication.administration;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

public class UserAnswerData {

    private Integer id;
    private Integer question;
    private Integer answer;
    private String text;
    private Date date;
    private Integer distance;
    private Integer unit;
    private Collection<Integer> options;
    private boolean never;
    private Integer country;
    
    public UserAnswerData() {
        super();
    }

    public UserAnswerData(Integer question, Integer answer) {
        this.question = question;
        this.answer = answer;
        options = new LinkedList<Integer>();
    }

    public UserAnswerData(Integer question, String text) {
        this.question = question;
        this.text = text;
    }

    public UserAnswerData(Integer question, Date date) {
        this.question = question;
        this.date = date;
    }

    public UserAnswerData(Integer question, Collection<Integer> options) {
        this.question = question;
        this.options = options;
    }

    public UserAnswerData(Integer question, boolean never) {
        this.question = question;
        this.never = never;
    }

    public UserAnswerData(Integer question) {
        this.question = question;
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

    public Collection<Integer> getOptions() {
        return options;
    }

    public void setOptions(Collection<Integer> options) {
        this.options = options;
    }

    public boolean isNever() {
        return never;
    }

    public void setNever(boolean never) {
        this.never = never;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    public Integer getCountry() {
        return country;
    }

    public boolean containsAnswer(Integer value) {
    	return options.contains(value);
    }
}
