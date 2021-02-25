/**
 * Created on 12-ene-2009
 * CEPA
 * DataCenter 5
 */
package assesment.communication.tag;

public class AnswerTagData {

    private Integer answer;
    private Integer question;
    private TagData tag;
    private Integer value;
    
    public AnswerTagData() {
    }

    /**
     * @param answer
     * @param tag
     * @param value
     */
    public AnswerTagData(Integer answer, Integer question, TagData tag, Integer value) {
        this.answer = answer;
        this.question = question;
        this.tag = tag;
        this.value = value;
    }

    public Integer getAnswer() {
        return answer;
    }

    public TagData getTag() {
        return tag;
    }

    public Integer getValue() {
        return value;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }

    public void setTag(TagData tag) {
        this.tag = tag;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getQuestion() {
        return question;
    }

    public void setQuestion(Integer question) {
        this.question = question;
    }

}
