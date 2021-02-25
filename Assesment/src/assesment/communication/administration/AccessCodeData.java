/**
 * Created on 26-oct-2007
 * CEPA
 * DataCenter 5
 */
package assesment.communication.administration;

public class AccessCodeData implements Comparable<AccessCodeData> {

    private String code;
    private Integer assesment;
    private Integer number;
    private Integer used;
    private Integer extension;
    private String language;
    
    private String assesmentName;
    
    public AccessCodeData() {
    }

    public AccessCodeData(String code, Integer assesment, Integer number, Integer used, Integer extension, String assesmentName, String language) {
        this.code = code;
        this.assesment = assesment;
        this.number = number;
        this.used = used;
        this.assesmentName = assesmentName;
        this.extension = extension;
        this.language = language;
    }

    public Integer getAssesment() {
        return assesment;
    }

    public Integer getUsed() {
        return used;
    }

    public String getCode() {
        return code;
    }

    public Integer getNumber() {
        return number;
    }

    public void setAssesment(Integer assesment) {
        this.assesment = assesment;
    }

    public void setUsed(Integer used) {
        this.used = used;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getAssesmentName() {
        return assesmentName;
    }

    public void setAssesmentName(String assesmentName) {
        this.assesmentName = assesmentName;
    }

    public Integer getExtension() {
        return extension;
    }

    public void setExtension(Integer extension) {
        this.extension = extension;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

	@Override
	public int compareTo(AccessCodeData o) {
		if(assesmentName != null && o.assesmentName != null)
			return assesmentName.compareTo(o.assesmentName);
		return 0;
	}

}
