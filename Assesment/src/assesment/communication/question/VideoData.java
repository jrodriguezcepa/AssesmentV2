/**
 * CEPA
 * Assesment
 */
package assesment.communication.question;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class VideoData implements Comparable {

    public static final int VIDEO = 0;
    public static final int FLASH = 1;

    private Integer id;
    private String key;
    private String language;

    public VideoData() {
    }

    /**
     * @param id
     * @param key
     * @param order
     * @param type
     * @param image 
     * @param testType 
     * @param group 
     * @param answers
     */
    public VideoData(Integer id, String key, String language) {
        this.id = id;
        this.key = key;
        this.language = language;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public int compareTo(Object arg0) {
		return id.compareTo(((VideoData)arg0).id);
	}

}
