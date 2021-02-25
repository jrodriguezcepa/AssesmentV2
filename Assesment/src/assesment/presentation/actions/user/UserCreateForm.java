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
public class UserCreateForm extends ActionForm {

	/**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String password;
	private String rePassword;
	private String firstName;
	private String lastName;
	private String email;
	private String language;
	private String loginname;
	private String info;
    private String role;
    private String type;
    private String birthDay;
    private String birthMonth;
    private String birthYear;
    private String sex;
    private String country;
    private String nationality;
    private String startDay;
    private String startMonth;
    private String startYear;
    private String expiryDay;
    private String expiryMonth;
    private String expiryYear;
    private String vehicle;
    private String location;   

    private String assesment;
    private String[] assesments;
    private String group;

    private String expiryType;

    private String userExpiryDay;
    private String userExpiryMonth;
    private String userExpiryYear;
    
    private String company;

    private String countryConfirmation;
    private String companyConfirmation;
    private String emailConfirmation;
    
    private String extraData;
    private String extraData2;
    private String extraData3;

    private String fdm;

    public UserCreateForm() {	    
	}
	
	public UserCreateForm(UserData data) {
	    loginname = data.getLoginName();
	    firstName = data.getFirstName();
	    lastName = data.getLastName();
	    email = data.getEmail();
	    loginname = data.getLoginName();
	    language = data.getLanguage();
	    role = data.getRole();
        Calendar c = Calendar.getInstance();
        if(data.getBirthDate() != null) {
            c.setTime(data.getBirthDate());
            birthDay = String.valueOf(c.get(Calendar.DATE));
            birthMonth = String.valueOf(c.get(Calendar.MONTH)+1);
            birthYear = String.valueOf(c.get(Calendar.YEAR));
        }
        sex = String.valueOf(data.getSex());
        country = String.valueOf(data.getCountry());
        nationality = String.valueOf(data.getNationality());
        if(data.getStartDate() != null) {
            c.setTime(data.getStartDate());
            startDay = String.valueOf(c.get(Calendar.DATE));
            startMonth = String.valueOf(c.get(Calendar.MONTH)+1);
            startYear = String.valueOf(c.get(Calendar.YEAR));
        }
        if(data.getLicenseExpiry() != null) {
            c.setTime(data.getLicenseExpiry());
            expiryDay = String.valueOf(c.get(Calendar.DATE));
            expiryMonth = String.valueOf(c.get(Calendar.MONTH)+1);
            expiryYear = String.valueOf(c.get(Calendar.YEAR));
        }
        vehicle = data.getVehicle();
        location = String.valueOf(data.getLocation());   
        if(data.getExpiry() != null) {
            c.setTime(data.getExpiry());
            userExpiryDay = String.valueOf(c.get(Calendar.DATE));
            userExpiryMonth = String.valueOf(c.get(Calendar.MONTH)+1);
            userExpiryYear = String.valueOf(c.get(Calendar.YEAR));
            expiryType = String.valueOf(UserData.WITH_EXPIRY);
        }else {
            expiryType = String.valueOf(UserData.PERMANENT);
        }
        extraData = data.getExtraData();
        extraData2 = data.getExtraData2();
        extraData3 = data.getExtraData3();
        fdm = (data.getDatacenter() == null) ? "" : String.valueOf(data.getDatacenter());
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
     * @return Returns the language.
     */
    public String getLanguage() {
        return language;
    }
    /**
     * @param language The language to set.
     */
    public void setLanguage(String language) {
        this.language = language;
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
    /**
     * @return Returns the password.
     */
    public String getPassword() {
        return password;
    }
    /**
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * @return Returns the rePassword.
     */
    public String getRePassword() {
        return rePassword;
    }
    /**
     * @param rePassword The rePassword to set.
     */
    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }
    
    /**
     * @return Returns the info.
     */
    public String getInfo() {
        return info;
    }

    /**
     * @param info The info to set.
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * @return Returns the user's role.
     */
    public String getRole() {
        return role;
    }

    /**
     * @param info The role to set the user.
     */
    public void setRole(String role) {
        this.role = role;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAssesment() {
        return assesment;
    }

    public void setAssesment(String assesment) {
        this.assesment = assesment;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public String getBirthMonth() {
        return birthMonth;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public String getCountry() {
        return country;
    }

    public String getExpiryDay() {
        return expiryDay;
    }

    public String getExpiryMonth() {
        return expiryMonth;
    }

    public String getExpiryYear() {
        return expiryYear;
    }

    public String getLocation() {
        return location;
    }

    public String getNationality() {
        return nationality;
    }

    public String getSex() {
        return sex;
    }

    public String getStartDay() {
        return startDay;
    }

    public String getStartMonth() {
        return startMonth;
    }

    public String getStartYear() {
        return startYear;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public void setBirthMonth(String birthMonth) {
        this.birthMonth = birthMonth;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setExpiryDay(String expiryDay) {
        this.expiryDay = expiryDay;
    }

    public void setExpiryMonth(String expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public void setExpiryYear(String expiryYear) {
        this.expiryYear = expiryYear;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getExpiryType() {
        return expiryType;
    }

    public void setExpiryType(String expiryType) {
        this.expiryType = expiryType;
    }

    public String getUserExpiryDay() {
        return userExpiryDay;
    }

    public String getUserExpiryMonth() {
        return userExpiryMonth;
    }

    public String getUserExpiryYear() {
        return userExpiryYear;
    }

    public void setUserExpiryDay(String userExpiryDay) {
        this.userExpiryDay = userExpiryDay;
    }

    public void setUserExpiryMonth(String userExpiryMonth) {
        this.userExpiryMonth = userExpiryMonth;
    }

    public void setUserExpiryYear(String userExpiryYear) {
        this.userExpiryYear = userExpiryYear;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanyConfirmation() {
        return companyConfirmation;
    }

    public String getCountryConfirmation() {
        return countryConfirmation;
    }

    public void setCompanyConfirmation(String companyConfirmation) {
        this.companyConfirmation = companyConfirmation;
    }

    public void setCountryConfirmation(String countryConfirmation) {
        this.countryConfirmation = countryConfirmation;
    }

    public String getEmailConfirmation() {
        return emailConfirmation;
    }

    public void setEmailConfirmation(String emailConfirmation) {
        this.emailConfirmation = emailConfirmation;
    }

	public String getExtraData2() {
		return extraData2;
	}

	public void setExtraData2(String extraData2) {
		this.extraData2 = extraData2;
	}

	public String getExtraData3() {
		return extraData3;
	}

	public void setExtraData3(String extraData3) {
		this.extraData3 = extraData3;
	}

	public String getFdm() {
		return fdm;
	}

	public void setFdm(String fdm) {
		this.fdm = fdm;
	}

	public String getExtraData() {
		return extraData;
	}

	public void setExtraData(String extraData) {
		this.extraData = extraData;
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
    
}