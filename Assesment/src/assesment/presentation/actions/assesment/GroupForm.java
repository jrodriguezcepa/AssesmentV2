/*
 * Created on 17-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.assesment;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import assesment.communication.assesment.GroupData;
import assesment.persistence.util.Util;

public class GroupForm extends ActionForm {

    private static final long serialVersionUID = 1L;

    private String id;
    private String corporation;
    private String name;
    private String layout;
    private String es_initialText;
    private String en_initialText;
    private String pt_initialText;
    private String repeatable;
    
	private FormFile logo;

    public GroupForm() {	    
	}
	
    public GroupForm(GroupData group) {
    	id = String.valueOf(group.getId());
    	corporation = String.valueOf(group.getCorporation());
    	name = group.getName();
    	es_initialText = group.getEsInitialText();
    	en_initialText = group.getEnInitialText();
    	pt_initialText = group.getPtInitialText();
    	repeatable = (group.isRepeatable()) ? "1" : "0";
    	layout = String.valueOf(group.getLayout());
	}
	
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public FormFile getLogo() {
        return logo;
    }

    public void setLogo(FormFile logo) {
        this.logo = logo;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getCorporation() {
		return corporation;
	}

	public void setCorporation(String corporation) {
		this.corporation = corporation;
	}

	public String getEs_initialText() {
		return es_initialText;
	}

	public void setEs_initialText(String es_initialText) {
		this.es_initialText = es_initialText;
	}

	public String getEn_initialText() {
		return en_initialText;
	}

	public void setEn_initialText(String en_initialText) {
		this.en_initialText = en_initialText;
	}

	public String getPt_initialText() {
		return pt_initialText;
	}

	public void setPt_initialText(String pt_initialText) {
		this.pt_initialText = pt_initialText;
	}

	
	public String getRepeatable() {
		return repeatable;
	}

	public void setRepeatable(String repeatable) {
		this.repeatable = repeatable;
	}

	public String validate() {
		if(Util.empty(name)) {
			return "group.emptyname";
		}
		if(Util.empty(corporation)) {
			return "group.emptycorporation";
		}
		return null;
	}

	
	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public GroupData createData() {
		Integer idG = (!Util.isNumber(id)) ? null : new Integer(id);
        boolean r = repeatable != null && repeatable.equals("1");

		return new GroupData(idG, new Integer(corporation), name, false, null, new Integer(layout),r);
	}

}