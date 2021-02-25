package assesment.communication.administration.user;

import java.io.Serializable;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Set;

import assesment.communication.assesment.AssesmentData;
import assesment.communication.corporation.CorporationAttributes;
import assesment.communication.corporation.CorporationData;
import assesment.communication.security.SecurityConstants;
import assesment.communication.user.UserData;
import assesment.communication.util.Filter;

/**
 * <p>
 * </p>
 */
public class UserSessionData implements Serializable{
	
    private static final long serialVersionUID = 1L;

    private Filter filter;
	private String lenguage;
    private Hashtable permissions;
    private String role;
    
    private boolean psiAccept = false;
		/**
	 * 
	 */   
	public UserSessionData() {
		// TODO Auto-generated constructor stub
	}
	
	public Filter getFilter() {
		return filter;
	}
	
	public void setFilter(Filter filter) {
		this.filter = filter;
	}
	
	public String getLenguage() {
		return lenguage;
	}
	
	public void setLenguage(String lenguage) {
		this.lenguage = lenguage;
	}

    /**
     * @param permissions
     */
    public void setPermissions(Hashtable permissions) {
        this.permissions = permissions;
    }

    /**
     * @return true if the logged user has permissions for action.
     */
    public boolean checkPermission(String action) {
        return role.equals(action) || role.equals(UserData.ADMINISTRATOR);
    }
    
    public Set getPermissions() {
        return permissions.keySet();
    }

    public Collection getPermissionsData() {
        Enumeration en = permissions.elements();
        Collection elements = new LinkedList();
        while(en.hasMoreElements()) {
            elements.add(en.nextElement());
        }
        return elements;
    }

    public boolean isAdministrator() {
    	return role.equals(UserData.ADMINISTRATOR);
    }

    /**
     * @return Returns the role.
     */
    public String getRole() {
        return role;
    }

    public void setRole(String data) {
        role = data;
    }

    public boolean isAccessUser() {
    	return role.equals(SecurityConstants.ACCESS_TO_SYSTEM) || role.equals(SecurityConstants.MULTI_ASSESSMENT);
    }

	public boolean isPsiAccept() {
		return psiAccept;
	}

	public void setPsiAccept(boolean psiAccept) {
		this.psiAccept = psiAccept;
	}

	public boolean isMutual() {
		if(filter != null && filter.getAssesment() != null)
			return filter.getAssesment().equals(AssesmentData.MUTUAL_DA);
		return false;
	}
}

