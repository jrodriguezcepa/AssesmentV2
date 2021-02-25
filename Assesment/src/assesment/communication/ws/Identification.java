package assesment.communication.ws;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * Nota:
 * Las librerias javax.xml.bind : jaxb-api.jar
 * Las librerias javax.xml.stream.xmlstreamwriter: stax-api-jar
 * @author gbenaderet
 *
 */

@XmlRootElement(name="Identification")
@XmlAccessorType(XmlAccessType.FIELD)
public class Identification {

	@XmlAttribute(required = true)
	private int id; // Instancia
	@XmlAttribute(required = true)
	private String key; // password
	
	public Identification() {
		super();	
	}
	
	public Identification(int id, String key) {
		super();
		this.id = id;
		this.key = key;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public String toString(){
		try{
			JAXBContext jc =  JAXBContext.newInstance(Identification.class);
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
			StringWriter w = new StringWriter();
			m.marshal(this,w);			
			System.out.println(w.toString());
			return w.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @param identification
	 * @return
	 */
	public Identification stringToObject(String identification){
		try {
			JAXBContext jc =  JAXBContext.newInstance(UserToRegister.class);
			Unmarshaller u = jc.createUnmarshaller();					
			Identification id = (Identification)u.unmarshal(new StringReader(identification));			
			
			if (id != null && id.getKey() != null){
				return id;
			}else{
				return null;
			}	
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Constructor a partir de un objeto serializado.
	 * @param identification
	 * @return
	 */
	public Identification(String identification){
		try {
			if (identification != null){
				JAXBContext jc =  JAXBContext.newInstance(Identification.class);
				Unmarshaller u = jc.createUnmarshaller();					
				Identification id = (Identification)u.unmarshal(new StringReader(identification));			
				
				if (id != null && id.getKey() != null){
					this.setId(id.getId());
					this.setKey(id.getKey());
				}
			}
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}
	
}
