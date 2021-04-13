/**
 * CEPA
 * Assesment
 */
package assesment.communication.question;

public class AnswerData implements Comparable {

    public static final int CORRECT = 0;
    public static final int INCORRECT = 1;
    
    private Integer id;
    
    private String key;
    private Integer order;
    private Integer resultType;
    private Float points;


    public AnswerData() {
    }

    /**
     * @param id
     * @param key
     * @param order
     * @param resultType
     */
    public AnswerData(Integer id, String key, Integer order, Integer resultType, Float points) {
        super();
        this.id = id;
        this.key = key;
        this.order = order;
        this.resultType = resultType;
        this.points = points;

    }


    public Integer getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public Integer getOrder() {
        return order;
    }

    public Integer getResultType() {
        return resultType;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public void setResultType(Integer resultType) {
        this.resultType = resultType;
    }

    public int compareTo(Object o) {
        return order.compareTo(((AnswerData)o).order);
    }
    
	public Float getPoints() {
		return points;
	}

	public void setPoints(Float points) {
		this.points = points;
	}
}
