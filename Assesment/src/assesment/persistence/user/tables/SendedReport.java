package assesment.persistence.user.tables;

import java.io.Serializable;

public class SendedReport implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private Integer assessment;
	private String login;
	private boolean sended;
	private boolean certificate;
	
	public SendedReport() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAssessment() {
		return assessment;
	}

	public void setAssessment(Integer assessment) {
		this.assessment = assessment;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public boolean isSended() {
		return sended;
	}

	public void setSended(boolean sended) {
		this.sended = sended;
	}

	public boolean isCertificate() {
		return certificate;
	}

	public void setCertificate(boolean certificate) {
		this.certificate = certificate;
	}
	
	
	
}
