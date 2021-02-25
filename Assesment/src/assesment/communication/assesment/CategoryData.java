package assesment.communication.assesment;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

public class CategoryData implements Comparable<CategoryData> {

	private Integer id;
	private String key;
    private Integer type; 
	private String image;
	private Integer order;
	
    private Integer titleColor;
    private Integer itemColor;

    private Collection<AssesmentAttributes> assesments;
	private Integer groupId; 
	
	private String esInitialText;
	private String enInitialText;
	private String ptInitialText;

	public CategoryData(Integer id, Integer groupId, String key, Integer type, String image, Integer order, Integer titleColor, Integer itemColor) {
		this.id = id;
		this.groupId = groupId;
		this.key = key;
		this.type = type;
		this.image = image;
		this.order = order;
		this.titleColor = titleColor;
		this.itemColor = itemColor;
		assesments = new LinkedList<AssesmentAttributes>();
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public void addAssessment(AssesmentAttributes assessment) {
		assesments.add(assessment);
	}

	@Override
	public int compareTo(CategoryData o) {
		return order.compareTo(o.order);
	}

	public Iterator<AssesmentAttributes> getOrderedAssesments() {
		Collections.sort((List) assesments);
		return assesments.iterator();
	}

	public String getTypeName() {
		switch(type) {
			case 1:
				return "dicas";
			case 2:
				return "desafios";
			case 3:
				return "pontuacao";
		}
		return "";
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getTitleColor() {
		return titleColor;
	}

	public void setTitleColor(Integer titleColor) {
		this.titleColor = titleColor;
	}

	public Integer getItemColor() {
		return itemColor;
	}

	public void setItemColor(Integer itemColor) {
		this.itemColor = itemColor;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public Collection<AssesmentAttributes> getAssesments() {
		return assesments;
	}

	public void setAssesments(Collection<AssesmentAttributes> assesments) {
		this.assesments = assesments;
	}

	public String getEsInitialText() {
		return esInitialText;
	}

	public void setEsInitialText(String esInitialText) {
		this.esInitialText = esInitialText;
	}

	public String getEnInitialText() {
		return enInitialText;
	}

	public void setEnInitialText(String enInitialText) {
		this.enInitialText = enInitialText;
	}

	public String getPtInitialText() {
		return ptInitialText;
	}

	public void setPtInitialText(String ptInitialText) {
		this.ptInitialText = ptInitialText;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

}
