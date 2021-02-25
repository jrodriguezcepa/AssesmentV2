/**
 * Created on 18-nov-2008
 * CEPA
 * DataCenter 5
 */
package assesment.communication.report;

public class UserAdvanceJJ implements Comparable {

    private String login;
    private String fullName;
    private Integer code;
    private Integer country;
    private Integer company;
    private Integer correct;
    private Integer psi;
    
    public UserAdvanceJJ(Object[] data) {
        login = (String)data[0];
        fullName = data[1]+" "+data[2];
        code = (Integer)data[11];
        country = (Integer)data[3];
        company = new Integer((String)data[4]);
        psi = 0;
        for(int i = 5; i < 11; i++)
            psi += (Integer)data[i];
        correct = (Integer)data[12];
    }

    public Integer getCode() {
        return code;
    }

    public Integer getCompany() {
        return company;
    }

    public Integer getCorrect() {
        return correct;
    }

    public Integer getCountry() {
        return country;
    }

    public String getFullName() {
        return fullName;
    }

    public String getLogin() {
        return login;
    }

    public Integer getPsi() {
        return psi;
    }

    public int compareTo(Object o) {
        UserAdvanceJJ element = (UserAdvanceJJ)o;
        if(!element.company.equals(company))
            return element.company.compareTo(company);
        if(!element.country.equals(country))
            return element.country.compareTo(country);
        return element.fullName.compareTo(fullName);
    }

    
}
