/**
 * Created on 23-nov-2007
 * CEPA
 * DataCenter 5
 */
package assesment.communication.util;

public class CountryData implements Comparable {
    
    private String id;
    private String name;
    
    public CountryData(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int compareTo(Object o) {
        return name.compareTo(((CountryData)o).name);
    }
}
