/*
 * Created on 14-nov-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.persistence.administration.tables;

import java.io.Serializable;

import assesment.persistence.assesment.tables.Assesment;
import assesment.persistence.assesment.tables.AssesmentBKP;
import assesment.persistence.user.tables.User;

/**
 * @author fcarriquiry
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UserAssesmentBKPPK implements Serializable{
	
    private static final long serialVersionUID = 1L;

    private User user;
	private AssesmentBKP assesment;
	
	/**
	 * 
	 */
	public UserAssesmentBKPPK() {
	}
	
	public UserAssesmentBKPPK(User user, AssesmentBKP assesment) {
		this.user=user;
		this.assesment=assesment;
	}
	
	public UserAssesmentBKPPK(String loginName, Integer assesmentId) {
		user=new User();
		user.setLoginName(loginName);
        assesment=new AssesmentBKP();
        assesment.setId(assesmentId);
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
	
	/**
	 * @return Returns the assesment.
	 */
	public AssesmentBKP getAssesment() {
		return assesment;
	}
	
	/**
	 * @param assesment The assesment to set.
	 */
	public void setAssesment(AssesmentBKP assesment) {
		this.assesment = assesment;
	}
	
	
}
