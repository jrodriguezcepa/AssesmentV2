package assesment.business.ws.cache;

import java.util.Date;

import assesment.business.AssesmentAccessRemote;

public class SysCache{
	
	private Date lastAccess;
	private AssesmentAccessRemote sys;
	
	public SysCache(){
		this.lastAccess=new Date();
	}
	
	public Date getLastAccess() {
		return lastAccess;
	}
	public void setLastAccess(Date lastAccess) {
		this.lastAccess = lastAccess;
	}
	public AssesmentAccessRemote getSys() {
		return sys;
	}
	public void setSys(AssesmentAccessRemote sys) {
		this.sys = sys;
	}
	
	
	
	

}
