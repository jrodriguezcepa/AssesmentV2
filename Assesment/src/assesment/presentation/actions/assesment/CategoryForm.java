/*
 * Created on 17-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.assesment;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import assesment.communication.assesment.CategoryData;
import assesment.communication.assesment.GroupData;
import assesment.persistence.util.Util;

public class CategoryForm extends ActionForm {

    private static final long serialVersionUID = 1L;

    private String id;
    private String group;
    private String type;
    private String es_Text;
    private String en_Text;
    private String pt_Text;
	private FormFile logo;

    private String ord;
    private String titleColor;
    private String itemColor;

    private String list;

    public CategoryForm() {	    
	}

    public CategoryForm(CategoryData category) {
    	id = String.valueOf(category.getId());
    	type = String.valueOf(category.getType());
    	ord = String.valueOf(category.getOrder());
    	titleColor = String.valueOf(category.getTitleColor());
    	itemColor = String.valueOf(category.getItemColor());
    	
    	es_Text = category.getEsInitialText();
    	en_Text = category.getEnInitialText();
    	pt_Text = category.getPtInitialText();
    	
    	logo = null;
	}
	
    public String getId() {
        return id;
    }

    
    public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEs_Text() {
		return es_Text;
	}

	public void setEs_Text(String es_Text) {
		this.es_Text = es_Text;
	}

	public String getEn_Text() {
		return en_Text;
	}

	public void setEn_Text(String en_Text) {
		this.en_Text = en_Text;
	}

	public String getPt_Text() {
		return pt_Text;
	}

	public void setPt_Text(String pt_Text) {
		this.pt_Text = pt_Text;
	}

	public FormFile getLogo() {
		return logo;
	}

	public void setLogo(FormFile logo) {
		this.logo = logo;
	}

	public String getTitleColor() {
		return titleColor;
	}

	public void setTitleColor(String titleColor) {
		this.titleColor = titleColor;
	}

	public String getItemColor() {
		return itemColor;
	}

	public void setItemColor(String itemColor) {
		this.itemColor = itemColor;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public String getOrd() {
		return ord;
	}

	public void setOrd(String ord) {
		this.ord = ord;
	}

	public String validate() {
	    if(Util.empty(es_Text) || Util.empty(en_Text) || Util.empty(pt_Text)) {
			return "group.category.emptyname";
		}
		if(Util.empty(ord)) {
			return "group.category.emptyord";
		}
		return null;
	}

	public CategoryData createData() {
		Integer idC = (!Util.isNumber(id)) ? null : new Integer(id);
		return new CategoryData(idC, new Integer(group), "", new Integer(type), "", new Integer(ord), new Integer(titleColor), new Integer(itemColor));
	}

	public String getList() {
		return list;
	}

	public void setList(String list) {
		this.list = list;
	}

	
}