package assesment.communication.util;

import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends javax.mail.Authenticator
{

	private String email;
	private String password;
	
	public PasswordAuthentication getPasswordAuthentication()
    {
            return new PasswordAuthentication(email, password);
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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public SMTPAuthenticator(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public SMTPAuthenticator() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
        
        
}