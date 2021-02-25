/*
 * Created on 14-nov-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.persistence.administration.tables;


import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.HibernateException;

import assesment.persistence.assesment.tables.Group;
import assesment.persistence.user.tables.User;
import assesment.persistence.user.tables.UserAssesmentData;

public class UserGroup implements Serializable {
	
    private static final long serialVersionUID = 1L;

    private UserGroupPK pk; 

    private boolean active; 
    
    public UserGroup(){}
	
	
	public UserGroup(User user,Group group)	throws HibernateException{
		this.pk=new UserGroupPK(user, group);		
	}


	public UserGroupPK getPk() {
		return pk;
	}


	public void setPk(UserGroupPK pk) {
		this.pk = pk;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}

}
