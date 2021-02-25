package assesment.persistence.user.tables;

import java.io.Serializable;

public class UserPassword implements Serializable {

    private static final long serialVersionUID = 1L;

	private String loginName;
	private String password;
	private String email;
	private boolean mailSended;
	private String firstname;
	private String lastname;
    
	public UserPassword() {
	}

    public UserPassword(String loginName, String password, String email, boolean mailSended, String firstname, String lastname) {
        this.loginName = loginName;
        this.password = password;
        this.email = email;
        this.mailSended = mailSended;
        this.firstname = firstname;
        this.lastname = lastname;
    }

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isMailSended() {
		return mailSended;
	}

	public void setMailSended(boolean mailSended) {
		this.mailSended = mailSended;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}