/*
 * Created on 17-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.user;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/**
 * @author fcarriquiry
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class UserCreateFileForm extends ActionForm {

	/**
     * 
     */
    private static final long serialVersionUID = 1L;

    private FormFile file;
	private String columnCount;
	private String column4;
	private String column5;
	private String column6;
	private String column7;
	private String column8;
	private String column9;
	private String extra;
	private String language;
	private String country;
	private String userFormat;
	private String role;

    private String assesment;
    private String[] assesments;
    private String group;

    public UserCreateFileForm() {	    
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}

	public String getColumnCount() {
		return columnCount;
	}

	public void setColumnCount(String columnCount) {
		this.columnCount = columnCount;
	}

	public String getColumn4() {
		return column4;
	}

	public void setColumn4(String column4) {
		this.column4 = column4;
	}

	public String getColumn5() {
		return column5;
	}

	public void setColumn5(String column5) {
		this.column5 = column5;
	}

	public String getColumn6() {
		return column6;
	}

	public void setColumn6(String column6) {
		this.column6 = column6;
	}

	public String getColumn7() {
		return column7;
	}

	public void setColumn7(String column7) {
		this.column7 = column7;
	}

	public String getColumn8() {
		return column8;
	}

	public void setColumn8(String column8) {
		this.column8 = column8;
	}

	public String getColumn9() {
		return column9;
	}

	public void setColumn9(String column9) {
		this.column9 = column9;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getUserFormat() {
		return userFormat;
	}

	public void setUserFormat(String userFormat) {
		this.userFormat = userFormat;
	}

	public String getAssesment() {
		return assesment;
	}

	public void setAssesment(String assesment) {
		this.assesment = assesment;
	}

	public String[] getAssesments() {
		return assesments;
	}

	public void setAssesments(String[] assesments) {
		this.assesments = assesments;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getColumn(int i) {
		switch(i) {
			case 4:
				return column4;
			case 5:
				return column5;
			case 6:
				return column6;
			case 7:
				return column7;
			case 8:
				return column8;
			case 9:
				return column9;
		}
		return null;
	}
    
}