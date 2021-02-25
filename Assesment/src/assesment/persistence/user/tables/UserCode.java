/**
 * Created on 25-sep-2007
 * CEPA
 * DataCenter 5
 */
package assesment.persistence.user.tables;


public class UserCode {

    private Integer code;
    private String loginname;
    
    public UserCode() {
    }

    public Integer getCode() {
        return code;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }
}
