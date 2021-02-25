package assesment.presentation.translator.web.administration.user;

import org.apache.struts.action.ActionForm;

public class LoginForm extends ActionForm {

    private static final long serialVersionUID = 1L;

    private String nick;
	private String passwd;

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

}