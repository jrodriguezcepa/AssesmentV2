package assesment.business.ws.client.security;

import java.io.Serializable;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userlogin;
	private String userpassword;
	private String fullName;
	private Long cKey;
	private String[][] messages;

	private String[] flags;
	
	private Integer driver;
	private boolean cepaUser;
	
	public User(){
		
	}
	
	public User(String u, String p){
		this.userlogin=u;
		this.userpassword=p;
		
	}
	
	public String[] getFlags() {
		return flags;
	}

	public void setFlags(String[] flags) {
		this.flags = flags;
	}

	public boolean isCepaUser() {
		return cepaUser;
	}

	public void setCepaUser(boolean cepaUser) {
		this.cepaUser = cepaUser;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Long getcKey() {
		return cKey;
	}

	public void setcKey(Long cKey) {
		this.cKey = cKey;
	}

	public String getUserlogin() {
		return userlogin;
	}
	public void setUserlogin(String userlogin) {
		this.userlogin = userlogin;
	}
	public String getUserpassword() {
		return userpassword;
	}
	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}

	public String[][] getMessages() {
		return messages;
	}

	public void setMessages(String[][] messages) {
		this.messages = messages;
	}

	public void setDriver(Integer driver) {
		this.driver = driver;
	}

	public Integer getDriver() {
		return driver;
	}

}
