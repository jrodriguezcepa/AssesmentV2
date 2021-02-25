/*
 * Created on 01-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.communication.corporation;

import java.io.Serializable;

/**
 * @author fcarriquiry
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CediAttributes implements Serializable, Comparable {
	
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String accessCode;
    private String uen;
    private String drv;
    private String manager;
    private String managerMail;
    private String loginName;
    private Integer company;

    public CediAttributes() {
        super();
    }

    /**
     * @param name
     * @param id
     */
    public CediAttributes(Integer id,String name,String accessCode,String uen,String drv, String manager, String managerMail,  String loginName, Integer company) {
        this.name = name;
        this.id = id;
        this.manager=manager;
        this.accessCode=accessCode;
        this.uen=uen;
        this.drv=drv;
        this.managerMail=managerMail;
        this.loginName=loginName;
        this.company = company;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
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


	public Integer getCompany() {
		return company;
	}

	public void setCompany(Integer company) {
		this.company = company;
	}

	@Override
	public int compareTo(Object arg0) {
		return name.compareTo(((CediAttributes)arg0).name);
	}

    

}