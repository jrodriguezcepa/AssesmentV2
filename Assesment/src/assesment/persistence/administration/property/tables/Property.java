package assesment.persistence.administration.property.tables;

import java.io.Serializable;

import assesment.communication.administration.property.PropertyData;

public class Property implements Serializable{

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
	
	public Property(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}
	
	public Property() {
		super();
	}
	
	public Property(PropertyData data){
		this.name = data.getName();
		this.value = data.getValue();
	}
	
	public PropertyData getPropertyData(){
		return new PropertyData(name,value);
	}
	
	
	
	
}
