/*
 * Created on 17-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.user;

import java.util.Calendar;

import org.apache.struts.action.ActionForm;
import org.omg.CORBA.UserException;

import assesment.communication.user.UserData;

/**
 * @author fcarriquiry
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class UpdateUserCodeForm extends ActionForm {

	/**
     * 
     */
    private static final long serialVersionUID = 1L;

	private String loginname;
	private String firstName;
	private String lastName;
	private String email;
    private String extraData3;
    public UpdateUserCodeForm() {	    
	}
	
	public UpdateUserCodeForm(UserData data) {
	    loginname = data.getLoginName();
	    firstName = data.getFirstName();
	    lastName = data.getLastName();
	    email = data.getEmail();
	    loginname = data.getLoginName();
        extraData3 = data.getExtraData3();
	}
	
    /**
     * @return Returns the email.
     */
    public String getEmail() {
        return email;
    }
    /**
     * @param email The email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * @return Returns the firstName.
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * @param firstName The firstName to set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return Returns the lastName.
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * @param lastName The lastName to set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * @return Returns the loginname.
     */
    public String getLoginname() {
        return loginname;
    }
    /**
     * @param loginname The loginname to set.
     */
    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }



	public String getExtraData3() {
		return extraData3;
	}

	public void setExtraData3(String extraData3) {
		this.extraData3 = extraData3;
	}

    
}