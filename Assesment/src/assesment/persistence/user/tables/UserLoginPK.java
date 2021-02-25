package assesment.persistence.user.tables;

import java.io.Serializable;


public class UserLoginPK implements Serializable {

    private static final long serialVersionUID = 1L;

	private String loginName;
	private String loginDate;
    
	public UserLoginPK() {
	}

    public UserLoginPK(String loginName, String loginDate) {
        this.loginName = loginName;
        this.loginDate = loginDate;
    }

    public String getLoginDate() {
        return loginDate;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}