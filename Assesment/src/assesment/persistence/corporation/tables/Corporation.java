/*
 * Created on 01-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.persistence.corporation.tables;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import assesment.communication.assesment.AssesmentData;
import assesment.communication.corporation.CorporationAttributes;
import assesment.communication.corporation.CorporationData;
import assesment.persistence.assesment.tables.Assesment;

/**
 * @author fcarriquiry
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Corporation implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
	private String name;
    private String logo;
    private Integer dc5id;
	
    private Set<Assesment> assesmentSet;
    
	/**
	 *  
	 */
	public Corporation() {
	}

	public Corporation(CorporationAttributes data) {
	    this.name = data.getName();
        this.logo = data.getLogo();
	}
	
    public Integer getId() {
        return id;
    }

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

    public Set<Assesment> getAssesmentSet() {
        return assesmentSet;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

    public void setAssesmentSet(Set<Assesment> assesmentSet) {
        this.assesmentSet = assesmentSet;
    }

    public CorporationAttributes getAttributes() {
        return new CorporationAttributes(id,name,logo,dc5id);
    }

    public CorporationData getData() {
        Collection<AssesmentData> assesments = new LinkedList<AssesmentData>();
        Iterator<Assesment> it = assesmentSet.iterator();
        while(it.hasNext()) {
            assesments.add(it.next().getData());
        }
        return new CorporationData(id,name,logo,assesments,dc5id);
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


    
}