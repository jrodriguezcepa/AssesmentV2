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

import assesment.persistence.assesment.tables.Assesment;
import assesment.persistence.user.tables.User;

public class ReporterAssesment implements Serializable {
	
    private static final long serialVersionUID = 1L;

    private UserAssesmentPK pk; 
    
    public ReporterAssesment(){}
	
	
	public ReporterAssesment(User user,Assesment assesment)	throws HibernateException{
		this.pk=new UserAssesmentPK(user,assesment);		
	}
	
	/**
	 * @param pk The pk to set.
	 */
	public void setPk(UserAssesmentPK pk) {
		this.pk = pk;
	}
	
	/**
	 * @return Returns the pk.
	 */
	public UserAssesmentPK getPk() {
		return pk;
	}
}
