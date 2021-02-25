package assesment.communication.ws;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.LinkedList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

//Only bind fields
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="UserToRegister")
public class UserToRegister {

	/*
	  <UserToRegister>
	  		<User firstname="" lastname=""
				language = "" 
				birthday = "" email = ""
				country = "" 
				sex= "" 
				dc5corporationId = "" />			
			<idLessons>
				<idLesson></idLesson>
				<idLesson></idLesson>
				<idLesson></idLesson>
				<idLesson></idLesson>
			</idLessons>
	  </UserToRegister>
	 
	 */
	
	@XmlElement(name="User")
	private UserElearning user;
	
	// @XmlElement(name="idCourse")
	// private int idCourse;
	
	@XmlElementWrapper(name="idLessons")
	@XmlElement(name="idLesson")
	private LinkedList<Integer> idLessons;
	
	/**
	 * 	
	 * @param idCourse
	 * @param idLessons
	 * @param user
	 */
	public UserToRegister(/* int idCourse, */ LinkedList<Integer> idLessons,
			UserElearning user) {
		super();
		// this.idCourse = idCourse;
		this.idLessons = idLessons;
		this.user = user;
	}
	
	/**
	 * 
	 */
	public UserToRegister() {
		super();		
	}
	
	/**
	 * @return the user
	 */
	public UserElearning getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(UserElearning user) {
		this.user = user;
	}
	/**
	 * @return the idCourse
	 */
	// public int getIdCourse() {
    // 	return idCourse;
	// }
	/**
	 * @param idCourse the idCourse to set
	 */
	//public void setIdCourse(int idCourse) {
	//	this.idCourse = idCourse;
	//}
	/**
	 * @return the idLessons
	 */
	public LinkedList<Integer> getIdLessons() {
		return idLessons;
	}
	/**
	 * @param idLessons the idLessons to set
	 */
	public void setIdLessons(LinkedList<Integer> idLessons) {
		this.idLessons = idLessons;
	}
	
	public String getIdLessonsString(){
		String result = null;
		if (this.idLessons != null && this.idLessons.size() > 0){
			Iterator<Integer> it = this.idLessons.iterator();
			while (it.hasNext()){
				Integer id = it.next();
				if (result == null){
					result = id.toString();
				}else{
					result += ","+id.toString();
				}
			}
		}	
		return result;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public String toString(){
		try{
			JAXBContext jc =  JAXBContext.newInstance(UserToRegister.class);
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
	 * @param userToRegister
	 * @return
	 */
	public UserToRegister stringToObject(String userToRegister){
		try {
			JAXBContext jc =  JAXBContext.newInstance(UserToRegister.class);
			Unmarshaller u = jc.createUnmarshaller();					
			UserToRegister userObject = (UserToRegister)u.unmarshal(new StringReader(userToRegister));			
			
			if (userObject != null && userObject.getIdLessons() != null){
				return userObject;
			}else{
				return null;
			}	
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
		return null;
	}
		
}
