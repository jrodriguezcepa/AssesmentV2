package assesment.communication.user;

import java.io.Serializable;
import java.util.Date;

import assesment.persistence.administration.tables.AssessmentUserData;

public class UserData implements Serializable, Comparable<UserData> {

    private static final long serialVersionUID = 1L;

    public static final String VEIBRAS = "veibras.usercreate";

    public static final String TIMAC = "timac.useraccess";
    public static final String DAIMLER = "sprinter25.useraccess";
    public static final String GRUPO_MODELO = "grupomodelo.useraccess";
    public static final String MUTUAL = "mutual.access";

    public static final String REGISTROUPM = "charlaupm.registro";
    public static final String CHARLAUPM = "charlaupm.ingreso";
    public static final String HABILITARUPM = "charlaupm.habilitar";
    
    public static final String REGISTROMDP = "charlamdp.registro";
    public static final String CHARLAMDP = "charlamdp.ingreso";

    public static final String ADMINISTRATOR = "administrator";
    public static final String SYSTEMACCESS = "systemaccess";
    public static final String FIRSTACCESS = "firstAccess";
    public static final String MULTIASSESSMENT = "multiassessment";
    public static final String GROUP_ASSESSMENT = "groupassessment";
    public static final String CLIENT_GROUP = "clientgroupreporter";

    public static final int FEMALE = 1;
    public static final int MALE = 2;

    public static final int ACOM = 1;
    public static final int GENERAL = 2;
    public static final int NON_SALES = 3;
    public static final int PLANT = 4;
    public static final int SALES = 5;

    public static final int PERMANENT = 0;
    public static final int WITH_EXPIRY = 1;

    public static final int MANUFACTURING = 1;
    public static final int COMMERCIAL = 2;
    public static final int AGROESTE = 3;

	public static final int ASSESMENT_USER = 0;
	public static final int ELEARNING_INVALID__USER = 1;
	public static final int ELEARNING_VALID_USER = 2;

	public static final String DEFAULT_EMAIL = "cepasafedrive@gmail.com";

    private String loginName;
    private String password;
    private String firstName;
    private String lastName;
    private String language;
    private String email;
    private Date birthDate;
    private Integer sex;
    private Integer country;
    private Integer nationality;
    private Date startDate;
    private Date licenseExpiry;
    private String vehicle;
    private Integer location;   
    private String role;
    private Date expiry;

    private String extraData;
    private String extraData2;
    private String extraData3;

	private Integer datacenter;

	private Integer telematics;
	private boolean coaching;
	
	public UserData(String loginName,String password,String firstName,String lastName,String language,String email,String role, Date expiry) {
        this.loginName = loginName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.language = language;
        this.email = email;
        this.role = role;
        this.expiry = expiry;
	}
    
    
    /**
     * @param loginName
     * @param password
     * @param firstName
     * @param lastName
     * @param language
     * @param email
     * @param birthDate
     * @param sex
     * @param country
     * @param nationality
     * @param startDate
     * @param licenseExpiry
     * @param vehicle
     * @param location
     * @param role
     */
    public UserData(String loginName, String password, String firstName, String lastName, String language, String email, Date birthDate, Integer sex, Integer country, Integer nationality, Date startDate, Date licenseExpiry, String vehicle, Integer location, String role, Date expiry) {
        super();
        this.loginName = loginName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.language = language;
        this.email = email;
        this.birthDate = birthDate;
        this.sex = sex;
        this.country = country;
        this.nationality = nationality;
        this.startDate = startDate;
        this.licenseExpiry = licenseExpiry;
        this.vehicle = vehicle;
        this.location = location;
        this.role = role;
        this.expiry = expiry;
    }


    public UserData() {
    }


    public UserData(Object[] data) {
        this.loginName = (String)data[0];
        this.firstName = (String)data[1];
        this.lastName = (String)data[2];
        this.email = (String)data[3];
        this.language = (String)data[4];
        if(data.length > 5)
        	this.role = (String)data[5];
        if(data.length > 6)
        	this.extraData = (String)data[6];
        if(data.length > 7)
        	this.extraData2 = (String)data[7];
        if(data.length > 8)
        	this.country = (Integer)data[8];
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

    public String getLoginName() {
        return loginName;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
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

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @return true if the user has permissions for action.
     */
    public boolean checkPermission(String action) {
        return role.equals(action);
    }

    public static String validatePassword(String passwd) {
        if(passwd.length() < 6) {
            return "user.error.shortpassword";
        }
        return null;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Integer getCountry() {
        return country;
    }

    public Date getLicenseExpiry() {
        return licenseExpiry;
    }

    public Integer getLocation() {
        return location;
    }

    public Integer getNationality() {
        return nationality;
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

    public void setLicenseExpiry(Date licenseExpiry) {
        this.licenseExpiry = licenseExpiry;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public void setNationality(Integer nationality) {
        this.nationality = nationality;
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

    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }

    public String getExtraData() {
        return extraData;
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

	public Integer getTelematics() {
		return telematics;
	}

	public void setDatacenter(Integer datacenter) {
		this.datacenter = datacenter;
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


	@Override
	public int compareTo(UserData o) {
		return new String(firstName+" "+lastName).compareTo(new String(o.firstName+" "+o.lastName));
	}
}