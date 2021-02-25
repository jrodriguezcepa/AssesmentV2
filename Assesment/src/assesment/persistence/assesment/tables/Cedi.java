/**
 * CEPA
 * Assesment
 */
package assesment.persistence.assesment.tables;

import assesment.communication.corporation.CediAttributes;
import assesment.communication.corporation.CediData;

public class Cedi {

    private Integer id;
    private String name;
    private String accessCode;
    private String uen;
    private String drv;
    private String manager;
    private String managerMail;
    private String loginName;
    private Integer company;
    
    public Cedi() {
    }

	public Cedi(CediAttributes data) {
	    this.name = data.getName();
	    this.accessCode = data.getAccessCode();
	    this.uen = data.getUen();
	    this.drv = data.getDrv();
	    this.manager = data.getManager();
	    this.managerMail = data.getManagerMail();
	    this.loginName = data.getLoginName();
	    this.company = data.getCompany();
	}
	
    public CediData getData() {
        return new CediData(id,name,accessCode, uen, drv,manager,managerMail, loginName, company);
    }
    
    public CediAttributes getAttributes() {
        return new CediAttributes(id,name,accessCode,uen,drv,manager,managerMail, loginName, company);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public String getManager() {
        return manager;
    }

    public String getManagerMail() {
        return managerMail;
    }

    public String getLoginName() {
        return loginName;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
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
    

    public void setManager(String manager) {
        this.manager = manager;
    }

    public void setManagerMail(String managerMail) {
        this.managerMail = managerMail;
    }

    public void setLoginName(String login) {
        this.loginName = login;
    }

	public Integer getCompany() {
		return company;
	}

	public void setCompany(Integer company) {
		this.company = company;
	}

}
