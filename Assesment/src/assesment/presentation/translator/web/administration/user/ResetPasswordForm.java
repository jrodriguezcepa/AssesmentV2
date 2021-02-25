/*
 * Created on 17-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.translator.web.administration.user;

import org.apache.struts.action.ActionForm;

/**
 * @author fcarriquiry
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ResetPasswordForm extends ActionForm {

    private static final long serialVersionUID = 1L;

	private String email;
	private String language;

	public ResetPasswordForm() {
        language = "en";
	}

    public String getEmail() {
        return email;
    }

    public String getLanguage() {
        return language;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
	
}