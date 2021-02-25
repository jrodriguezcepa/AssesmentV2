package assesment.communication.ws;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="User",propOrder={"firstname","lastname",
		"language","birthday","email",
		"country","sex","dc5Corporationid"})
@XmlAccessorType(XmlAccessType.FIELD)
public class UserElearning {

	@XmlAttribute(required = true)
	private String firstname;
	@XmlAttribute(required = true)
	private String lastname;
	@XmlAttribute(required = true)
	private String language; // es, pt, en	
	@XmlAttribute(required = true)
	private String birthday; // formato: YYYY-MM-DD
	@XmlAttribute(required = true)
	private String email;
	@XmlAttribute(required = true)
	private int country; // country id
	@XmlAttribute(required = true)
	private String sex; // datatype.sex.male or datatype.se.female
	@XmlAttribute(required = true)
	private int dc5Corporationid; // id de la corporacion del usuario.
	
	
	/**
	 * Constructor por defecto
	 */
	public UserElearning() {
		super();
	}
	
	/**
	 * 
	 * @param birthday
	 * @param corporation
	 * @param country
	 * @param email
	 * @param firstname
	 * @param iduser
	 * @param language
	 * @param lastname
	 * @param password
	 * @param sex
	 */
	public UserElearning(String birthday, int country,
			String email, String firstname, String language,
			String lastname,  String sex, int dc5corporationId) {
		super();
		this.birthday = birthday;		
		this.country = country;
		this.email = email;
		this.firstname = firstname;		
		this.language = language;
		this.lastname = lastname;		
		this.sex = sex;
		this.dc5Corporationid = dc5corporationId;
	}
	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}
	/**
	 * @param firstname the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}
	/**
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}
	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	
	/**
	 * @return the birthday
	 */
	public String getBirthday() {
		return birthday;
	}
	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @return the country
	 */
	public int getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(int country) {
		this.country = country;
	}
	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public String toString(){
		try{
			JAXBContext jc =  JAXBContext.newInstance(UserElearning.class);
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
	 * @param userElearning
	 * @return
	 */
	public UserElearning stringToObject(String userElearning){
		try {
			JAXBContext jc =  JAXBContext.newInstance(UserElearning.class);
			Unmarshaller u = jc.createUnmarshaller();					
			UserElearning user = (UserElearning)u.unmarshal(new StringReader(userElearning));			
			
			if (user != null && user.getFirstname() != null){
				return user;
			}else{
				return null;
			}	
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @return the dc5corporationId
	 */
	public int getDc5CorporationId() {
		return dc5Corporationid;
	}

	/**
	 * @param dc5corporationId the dc5corporationId to set
	 */
	public void setDc5Corporationid(int dc5Corporationid) {
		this.dc5Corporationid = dc5Corporationid;
	}
	
	
}
