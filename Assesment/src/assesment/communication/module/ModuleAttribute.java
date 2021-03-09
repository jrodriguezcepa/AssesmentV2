/**
 * CEPA
 * Assesment
 */
package assesment.communication.module;


public class ModuleAttribute implements Comparable {

    private Integer id;
    
    private String key;
    private Integer order;

    private Integer assesment;
    private Integer type;
    
    private Integer green;
    private Integer yellow;

    public ModuleAttribute() {
    }

    public ModuleAttribute(Integer id, String key, Integer order, Integer assesment, Integer type, Integer green, Integer yellow) {
        this.id = id;
        this.key = key;
        this.order = order;
        this.type = type;
        this.assesment = assesment;
        this.green = green;
        this.yellow = yellow;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public int compareTo(Object o) {
    	if(type.intValue() == ModuleData.FINAL_MODULE) {
    		return 1;
    	}
    	if(((ModuleAttribute)o).type.intValue() == ModuleData.FINAL_MODULE) {
    		return -1;
    	}
        return order.compareTo(((ModuleAttribute)o).order);
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

	public Integer getGreen() {
		return green;
	}

	public void setGreen(Integer green) {
		this.green = green;
	}

	public Integer getYellow() {
		return yellow;
	}

	public void setYellow(Integer yellow) {
		this.yellow = yellow;
	}

}
