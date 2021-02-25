package assesment.persistence.user.tables;

import java.io.Serializable;
import java.util.Date;


public class UserAccess implements Serializable {

    private static final long serialVersionUID = 1L;

	private String loginName;
	private boolean firstAccess;
	private Date passwordDate;
    
	public UserAccess() {
	}

    public UserAccess(String loginName, boolean firstAccess, Date passwordDate) {
        this.loginName = loginName;
        this.firstAccess = firstAccess;
        this.passwordDate = passwordDate;
    }

    public boolean isFirstAccess() {
        return firstAccess;
    }

    public String getLoginName() {
        return loginName;
    }

    public Date getPasswordDate() {
        return passwordDate;
    }

    public void setFirstAccess(boolean firstAccess) {
        this.firstAccess = firstAccess;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public void setPasswordDate(Date passwordDate) {
        this.passwordDate = passwordDate;
    }
   
}