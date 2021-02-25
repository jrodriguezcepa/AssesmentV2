/**
 * CEPA
 * Assesment
 */
package assesment.persistence.question.tables;

import assesment.communication.question.VideoData;

public class Video {

    private Integer id;
    private String key;
    private String language;
    
    public Video() {
    }

    public Video(VideoData videoData) {
        id = videoData.getId();
        key = videoData.getKey();
        language = videoData.getLanguage();
    }

    public void setData(VideoData videoData) {
        key = videoData.getKey();
        language = videoData.getLanguage();
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

	public VideoData getData() {
        return new VideoData(id,key,language);
    }
}
