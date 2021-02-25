package assesment.persistence.user.tables;

import java.io.Serializable;
import java.util.Date;

import assesment.communication.exception.InvalidDataException;
import assesment.communication.user.UserData;


public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String loginName;
    private String password;
	private String firstName;
	private String lastName;
    private Date birthDate;
    private Integer sex;
	private String email;
    private Integer country;
    private Integer nationality;
    private Date startDate;
    private Date licenseExpiry;
    private String vehicle;
    private Integer location;	
    private String language;
    private String role;
    private Date expiry;
    
    private String extraData;
    private String extraData2;
    private String extraData3;
   
    private Integer datacenter;	
    private Integer telematics;	
    
    private boolean coaching;
    
    private Date acceptTerms;
    
	public User() {
	}

	public User(UserData attrs) throws InvalidDataException {
        this.loginName = attrs.getLoginName();
        this.password = attrs.getPassword();
		this.firstName = attrs.getFirstName();
		this.lastName = attrs.getLastName();
		this.email = attrs.getEmail();
        this.sex = attrs.getSex();
        this.birthDate = attrs.getBirthDate();
        this.country = attrs.getCountry();
        this.nationality = attrs.getNationality();
        this.startDate = attrs.getStartDate();
        this.licenseExpiry = attrs.getLicenseExpiry();
        this.vehicle = attrs.getVehicle();
        this.location = attrs.getLocation();
		this.language = attrs.getLanguage();
        this.role = attrs.getRole();
        this.expiry = attrs.getExpiry();
        if(attrs.getExtraData() != null) {
            this.extraData = attrs.getExtraData();
        }
        if(attrs.getExtraData2() != null) {
            this.extraData2 = attrs.getExtraData2();
        }
        if(attrs.getExtraData3() != null) {
            this.extraData3 = attrs.getExtraData3();
        }
        datacenter = attrs.getDatacenter();
        telematics = attrs.getTelematics();
        coaching = false;
	}

	public UserData getUserData() {
        UserData data = new UserData(loginName,password,firstName,lastName,language,email,role,expiry);
        data.setSex(sex);
        data.setBirthDate(birthDate);
        data.setCountry(country);
        data.setNationality(nationality);
        data.setStartDate(startDate);
        data.setLicenseExpiry(licenseExpiry);
        data.setVehicle(vehicle);
        data.setLocation(location);
        if(extraData != null) {
            data.setExtraData(extraData);
        }
        if(extraData2 != null) {
            data.setExtraData2(extraData2);
        }
        if(extraData3 != null) {
            data.setExtraData3(extraData3);
        }
        data.setDatacenter(datacenter);
        data.setTelematics(telematics);
        data.setCoaching(coaching);
        return data;
	}
	
	public Date getBirthDate() {
        return birthDate;
    }

    public Integer getCountry() {
        return country;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLanguage() {
        return language;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getLicenseExpiry() {
        return licenseExpiry;
    }

    public Integer getLocation() {
        return location;
    }

    public String getLoginName() {
        return loginName;
    }

    public Integer getNationality() {
        return nationality;
    }

    public String getPassword() {
        return password;
    }

    public Integer getSex() {
        return sex;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setLicenseExpiry(Date licenseExpiry) {
        this.licenseExpiry = licenseExpiry;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public void setNationality(Integer nationality) {
        this.nationality = nationality;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public boolean equals(Object arg0) {
		User u = (User) arg0;
		if (u != null && u.getLoginName() != null) {
			if (this.loginName != null
					&& this.loginName.compareTo(u.getLoginName()) == 0) {
				return true;
			}
		}
		return false;
	}

    /**
     * @param roleName2
     */
    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
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

	public Integer getDatacenter() {
		return datacenter;
	}

	public void setDatacenter(Integer datacenter) {
		this.datacenter = datacenter;
	}

	public Integer getTelematics() {
		return telematics;
	}

	public void setTelematics(Integer telematics) {
		this.telematics = telematics;
	}

	public boolean isCoaching() {
		return coaching;
	}

	public void setCoaching(boolean coaching) {
		this.coaching = coaching;
	}

	public Date getAcceptTerms() {
		return acceptTerms;
	}

	public void setAcceptTerms(Date acceptTerms) {
		this.acceptTerms = acceptTerms;
	}
    
}