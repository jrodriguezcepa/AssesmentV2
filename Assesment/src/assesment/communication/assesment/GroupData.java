package assesment.communication.assesment;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class GroupData {

    private Integer id;    
    private String name;
    private boolean initialText;
    private String image;

    private Collection<CategoryData> categories;
	private Integer corporation;
	private String corporationName;
	
	private String esInitialText;
	private String enInitialText;
	private String ptInitialText;
	private Integer layout;
	private boolean repeatable;

	public static final int GROUP = 1;
	public static final int CLASIC = 2;
	
	public static final int ACHE = 4;
	
	public static final int MONDELEZ = 5;
	public static final int MONDELEZ_NEWDRIVERS = 62;
	public static final int MONDELEZ_PROVISIONALDRIVERS = 90;
	public static final int MONDELEZ_LIDERES = 95;

	public static final int MERCADOLIVRE = 11;
	public static final int MERCADOLIBRE = 12;
	public static final int ECOLAB = 27;
	public static final int GALDERMA = 52;
	public static final int VEIBRAS = 61;
	public static final int BAT = 69;
	public static final int DAIMLER = 70;
	public static final int MUTUAL = 94;
	public static final int GRUPO_MODELO = 97;
	public static final int FMB_MENTORIA = 110;
	public static final int GUINEZ_ADMINISTRACION = 131;
	public static final int GUINEZ_FAENA = 132;
	
	public GroupData(Integer id, Integer corporation, String name, boolean initialText, String image, Integer layout, boolean repeatable) {
		this.id = id;
		this.corporation = corporation; 
		this.name = name;
		this.initialText = initialText;
		this.image = image; 
		this.layout = layout;
		this.repeatable = repeatable;
		categories = new LinkedList<CategoryData>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<CategoryData> getCategories() {
		return categories;
	}

	public void setCategories(Collection<CategoryData> categories) {
		this.categories = categories;
	}
    
	public void addCategory(CategoryData category) {
		categories.add(category);
	}

	
	public boolean isInitialText() {
		return initialText;
	}

	public void setInitialText(boolean initialText) {
		this.initialText = initialText;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Iterator<CategoryData> getOrderedCategories() {
		Collections.sort((List) categories);
		return categories.iterator();
	}

	public Integer getCorporation() {
		return corporation;
	}

	public void setCorporation(Integer corporation) {
		this.corporation = corporation;
	}

	public void setCorporationName(String corporationName) {
		this.corporationName = corporationName;
	}

	public String getCorporationName() {
		return corporationName;
	}

	public void setEsInitialText(String string) {
		esInitialText = string;
	}

	public void setEnInitialText(String string) {
		enInitialText = string;
	}

	public void setPtInitialText(String string) {
		ptInitialText = string;
	}

	public String getEsInitialText() {
		return esInitialText;
	}

	public String getEnInitialText() {
		return enInitialText;
	}

	public String getPtInitialText() {
		return ptInitialText;
	}

	public Integer getLayout() {
		return layout;
	}

	public void setLayout(Integer layout) {
		this.layout = layout;
	}

	public boolean containsAssessment(Integer assessment) {
		Iterator<CategoryData> it = categories.iterator();
		while(it.hasNext()) {
			CategoryData c = it.next();
			Iterator<AssesmentAttributes> it2 = c.getAssesments().iterator();
			while(it2.hasNext()) {
				AssesmentAttributes a =	it2.next();
				if(a.getId().equals(assessment))
					return true;
			}
		}
		return false;
	}

	public int getAssessmentCount() {
		int c = 0;
		Iterator<CategoryData> it = categories.iterator();
		while(it.hasNext()) {
			c +=  it.next().getAssesments().size();
		}
		return c;
	}

	public void setRepeatable(boolean repeatable) {
		this.repeatable = repeatable;
	}

	public boolean isRepeatable() {
		return repeatable;
	}

	public int getVisibleAssessmentCount() {
		int c = 0;
		Iterator<CategoryData> it = categories.iterator();
		while(it.hasNext()) {
			c +=  it.next().getShowAssesmentsSize();
		}
		return c;
	}
	
	
}

