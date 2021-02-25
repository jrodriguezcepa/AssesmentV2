package assesment.communication.administration.property;

import java.io.Serializable;

public class PropertyData implements Serializable{

	public static final String LOTE_ABITAB = "property.loteabitab";
	
	private String name;
	private String value;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	public PropertyData(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}
	
	public PropertyData() {
		super();
	}
	
	
	
	
}
