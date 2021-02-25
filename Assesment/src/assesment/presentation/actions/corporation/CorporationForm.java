/*
 * Created on 17-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.corporation;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class CorporationForm extends ActionForm {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
	private FormFile logo;

    public CorporationForm() {	    
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

}