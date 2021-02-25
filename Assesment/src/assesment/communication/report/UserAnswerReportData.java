/**
 * Created on 08-ene-2008
 * CEPA
 * DataCenter 5
 */
package assesment.communication.report;

public class UserAnswerReportData implements Comparable {

    private String name;
    private String login;
    private Integer count;

    public UserAnswerReportData() {
    }

    /**
     * @param name
     * @param login
     * @param count
     */
    public UserAnswerReportData(String name, String login, Integer count) {
        this.name = name;
        this.login = login;
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int compareTo(Object o) {
        if(count.intValue() != ((UserAnswerReportData)o).count.intValue()) {
            return count.compareTo(((UserAnswerReportData)o).count);
        }
        return name.compareTo(((UserAnswerReportData)o).name);
    }
    
    
}
