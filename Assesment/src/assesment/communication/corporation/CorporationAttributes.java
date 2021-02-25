/*
 * Created on 01-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.communication.corporation;

import java.io.Serializable;

/**
 * @author fcarriquiry
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CorporationAttributes implements Serializable, Comparable {
	
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String logo;
    private Integer dc5id;

    public CorporationAttributes() {
        super();
    }

    /**
     * @param name
     * @param id
     */
    public CorporationAttributes(Integer id,String name,String logo, Integer dc5id) {
        this.name = name;
        this.id = id;
        this.logo = logo;
        this.dc5id = dc5id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

	/**
	 * @return the dc5id
	 */
	public Integer getDc5id() {
		return dc5id;
	}

	/**
	 * @param dc5id the dc5id to set
	 */
	public void setDc5id(Integer dc5id) {
		this.dc5id = dc5id;
	}

	@Override
	public int compareTo(Object arg0) {
		return name.compareTo(((CorporationAttributes)arg0).name);
	}

    

}