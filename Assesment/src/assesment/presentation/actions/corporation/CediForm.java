/*
 * Created on 17-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.corporation;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class CediForm extends ActionForm {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String accessCode;
    private String uen;
    private String drv;
    private String manager;
    private String managerMail;
    private String loginName;
    private String company;

    public CediForm() {	    
	}
	
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }
    public String getUen() {
        return uen;
    }

    public void setUen(String uen) {
        this.uen = uen;
    }
    public String getDrv() {
        return drv;
    }

    public void setDrv(String drv) {
        this.drv = drv;
    }
    
    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }
    
    public String getManagerMail() {
        return managerMail;
    }

    public void setManagerMail(String managerMail) {
        this.managerMail = managerMail;
    }
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

}