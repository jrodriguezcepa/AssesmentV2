/*
 * Created on 14-nov-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.persistence.administration.tables;

import java.io.Serializable;

import assesment.persistence.assesment.tables.Assesment;
import assesment.persistence.assesment.tables.Group;
import assesment.persistence.user.tables.User;

/**
 * @author fcarriquiry
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UserGroupPK implements Serializable{
	
    private static final long serialVersionUID = 1L;

    private User user;
	private Group groupId;
	
	/**
	 * 
	 */
	public UserGroupPK() {
	}
	
	public UserGroupPK(User user, Group groupId) {
		this.user=user;
		this.groupId=groupId;
	}
	
	public UserGroupPK(String loginName, Integer group) {
		user=new User();
		user.setLoginName(loginName);
		groupId=new Group();
		groupId.setId(group);
	}

	/**
	 * @return Returns the user.
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * @param user The user to set.
	 */
	public void setUser(User user) {
		this.user = user;
	}

	public Group getGroupId() {
		return groupId;
	}

	public void setGroupId(Group groupId) {
		this.groupId = groupId;
	}
	
}
