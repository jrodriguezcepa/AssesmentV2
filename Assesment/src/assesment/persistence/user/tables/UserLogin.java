package assesment.persistence.user.tables;

import java.io.Serializable;


public class UserLogin implements Serializable {

    private static final long serialVersionUID = 1L;

	private UserLoginPK pk;
    
	public UserLogin() {
	}

    public UserLogin(String loginName, String date) {
        pk = new UserLoginPK(loginName, date);
    }

    public UserLoginPK getPk() {
        return pk;
    }

    public void setPk(UserLoginPK pk) {
        this.pk = pk;
    }

}