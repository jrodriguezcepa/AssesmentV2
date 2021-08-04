/**
 * Created on 25-sep-2007
 * CEPA
 * DataCenter 5
 */
package assesment.persistence.user.tables;

import java.util.Date;

public class ForgotPassword {

    private Integer id;
    private String key;
    private String login;
    private Date date;
    private Boolean used;
    
    public ForgotPassword() {
	}
    
	public ForgotPassword(String key, String login, Date date) {
		this.key = key;
		this.login = login;
		this.date = date;
		this.used = false;
	}


	public Integer getId() {
		return id;
	}
	
    public void setId(Integer id) {
		this.id = id;
	}
	
    public String getKey() {
		return key;
	}
	
    public void setKey(String key) {
		this.key = key;
	}
	
    public String getLogin() {
		return login;
	}
	
    public void setLogin(String login) {
		this.login = login;
	}
	
    public Date getDate() {
		return date;
	}
	
    public void setDate(Date date) {
		this.date = date;
	}

	public Boolean getUsed() {
		return used;
	}

	public void setUsed(Boolean used) {
		this.used = used;
	}

}
